/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://pragmaedge.com/licenseagreement
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pe.pcm.application;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.application.entity.ApplicationEntity;
import com.pe.pcm.b2b.B2BApiService;
import com.pe.pcm.b2b.usermailbox.RemoteUserInfoModel;
import com.pe.pcm.enums.Protocol;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.partner.PartnerModel;
import com.pe.pcm.protocol.FtpModel;
import com.pe.pcm.protocol.FtpService;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.ftp.entity.FtpEntity;
import com.pe.pcm.sterling.yfs.YfsUserService;
import com.pe.pcm.utils.KeyGeneratorUtil;
import com.pe.pcm.utils.PCMConstants;
import com.pe.pcm.workflow.ProcessService;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

import static com.pe.pcm.exception.GlobalExceptionHandler.*;
import static com.pe.pcm.utils.CommonFunctions.convertStringToBoolean;
import static com.pe.pcm.utils.KeyGeneratorUtil.getChildPrimaryKey;
import static com.pe.pcm.utils.PCMConstants.PRAGMA_EDGE_S;
import static java.lang.Boolean.FALSE;

/**
 * @author Chenchu Kiran.
 */
@Service
public class FtpApplicationService {

    private final ApplicationService applicationService;
    private final ActivityHistoryService activityHistoryService;
    private final FtpService ftpService;
    private final B2BApiService b2BApiService;
    private final ProcessService processService;
    private final ManageProtocolService manageProtocolService;
    private final YfsUserService yfsUserService;

    private final ObjectMapper mapper = new ObjectMapper();

    private final BiFunction<ApplicationEntity, FtpEntity, FtpModel> serialize = FtpApplicationService::apply;

    @Autowired
    public FtpApplicationService(ApplicationService applicationService, ActivityHistoryService activityHistoryService, FtpService ftpService, B2BApiService b2BApiService, ProcessService processService, ManageProtocolService manageProtocolService, YfsUserService yfsUserService) {
        this.applicationService = applicationService;
        this.activityHistoryService = activityHistoryService;
        this.ftpService = ftpService;
        this.b2BApiService = b2BApiService;
        this.processService = processService;
        this.manageProtocolService = manageProtocolService;
        this.yfsUserService = yfsUserService;
    }

    private void duplicateApplication(String applicationId) {
        applicationService.find(applicationId).ifPresent(applicationEntity -> {
            throw conflict("Application");
        });
    }

    @Transactional
    public String save(FtpModel ftpModel) {
        if (!Optional.ofNullable(Protocol.findProtocol(ftpModel.getProtocol())).isPresent()) {
            throw unknownProtocol();
        }
        duplicateApplication(ftpModel.getProfileId());
        String parentPrimaryKey = KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.APP_PKEY_PRE_APPEND,
                PCMConstants.APP_PKEY_RANDOM_COUNT);
        String childPrimaryKey = KeyGeneratorUtil.getChildPrimaryKey(ftpModel.getProtocol());
        ftpService.saveProtocol(ftpModel, parentPrimaryKey, childPrimaryKey, "APP");
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(ftpModel, partnerModel);
        applicationService.save(partnerModel, parentPrimaryKey, childPrimaryKey);

        ftpModel.setCreateUserInSI(ftpModel.getCreateUserInSI() != null ? ftpModel.getCreateUserInSI() : FALSE);
        ftpModel.setCreateDirectoryInSI(ftpModel.getCreateDirectoryInSI() != null ? ftpModel.getCreateDirectoryInSI() : FALSE);
        b2BApiService.createMailBoxInSI(ftpModel.getCreateDirectoryInSI(), ftpModel.getInDirectory(), ftpModel.getOutDirectory());
        b2BApiService.createUserInSI(ftpModel.getCreateUserInSI(), mapper.convertValue(ftpModel, RemoteUserInfoModel.class));

        activityHistoryService.saveApplicationActivity(parentPrimaryKey, "Application created.");

        return parentPrimaryKey;

    }

    @Transactional
    public void update(FtpModel ftpModel) {
        ApplicationEntity oldApplicationEntity = SerializationUtils.clone(applicationService.get(ftpModel.getPkId()));
        if (!oldApplicationEntity.getApplicationId().equalsIgnoreCase(ftpModel.getProfileId())) {
            duplicateApplication(ftpModel.getProfileId());
        }
        boolean passwordNotChanged;
        String parentPrimaryKey = oldApplicationEntity.getPkId();
        String childPrimaryKey = oldApplicationEntity.getAppProtocolRef();
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(oldApplicationEntity.getAppIntegrationProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
        Map<String, String> oldFtpEntityMap = new LinkedHashMap<>();
        boolean isUpdate = true;
        if (!protocol.getProtocol().equalsIgnoreCase(ftpModel.getProtocol())) {
            manageProtocolService.deleteProtocol(oldApplicationEntity.getAppIntegrationProtocol(), oldApplicationEntity.getPkId());
            childPrimaryKey = getChildPrimaryKey(ftpModel.getProtocol());
            isUpdate = false;
        } else {
            oldFtpEntityMap = mapper.convertValue(ftpService.get(parentPrimaryKey),
                    new TypeReference<Map<String, String>>() {
                    });
        }

        //Password handling
        if (StringUtils.hasText(ftpModel.getPassword()) && ftpModel.getPassword().equals(PRAGMA_EDGE_S)) {
            passwordNotChanged = true;
            ftpModel.setPassword(oldFtpEntityMap.get("password"));
        } else {
            passwordNotChanged = false;
        }

        Map<String, String> newFtpEntityMap = mapper.convertValue(ftpService.saveProtocol(ftpModel, parentPrimaryKey, childPrimaryKey, "APP"),
                new TypeReference<Map<String, String>>() {
                });
        ApplicationModel applicationModel = new ApplicationModel();
        BeanUtils.copyProperties(ftpModel, applicationModel);
        ApplicationEntity newApplicationEntity = applicationService.save(applicationModel, parentPrimaryKey, childPrimaryKey);

        if (passwordNotChanged) {
            ftpModel.setPassword(PRAGMA_EDGE_S);
        }

        ftpModel.setCreateUserInSI(ftpModel.getCreateUserInSI() != null ? ftpModel.getCreateUserInSI() : FALSE);
        ftpModel.setCreateDirectoryInSI(ftpModel.getCreateDirectoryInSI() != null ? ftpModel.getCreateDirectoryInSI() : FALSE);
        b2BApiService.createMailBoxInSI(ftpModel.getCreateDirectoryInSI(), ftpModel.getInDirectory(), ftpModel.getOutDirectory());
        b2BApiService.mergeUserAndUpdate(ftpModel.getCreateUserInSI(), mapper.convertValue(ftpModel, RemoteUserInfoModel.class));
        if (ftpModel.getCreateUserInSI() && !passwordNotChanged) {
            yfsUserService.updatePasswordLastChangeDone(ftpModel.getUserName());
        }
        activityHistoryService.updateApplicationActivity(oldApplicationEntity, newApplicationEntity, oldFtpEntityMap, newFtpEntityMap, isUpdate);
    }

    @Transactional
    public void delete(String pkId) {
        if (!processService.findByApplicationProfile(pkId).isEmpty()) {
            throw internalServerError("Unable to Delete this Application, Because it has workflow.");
        }
        ApplicationEntity applicationEntity = applicationService.get(pkId);
        ftpService.delete(pkId);
        applicationService.delete(applicationEntity);
    }

    public FtpModel get(String pkId) {
        ApplicationEntity applicationEntity = applicationService.get(pkId);
        FtpEntity ftpEntity = ftpService.get(pkId);
        return serialize.apply(applicationEntity, ftpEntity);
    }

    private static FtpModel apply(ApplicationEntity applicationEntity, FtpEntity ftpEntity) {
        FtpModel ftpModel = new FtpModel();
        ftpModel.setPkId(applicationEntity.getPkId());
        ftpModel.setProfileName(applicationEntity.getApplicationName());
        ftpModel.setProfileId(applicationEntity.getApplicationId());
        ftpModel.setProtocol(applicationEntity.getAppIntegrationProtocol());
        ftpModel.setEmailId(applicationEntity.getEmailId());
        ftpModel.setPhone(applicationEntity.getPhone());
        ftpModel.setStatus(convertStringToBoolean(applicationEntity.getAppIsActive()));
        ftpModel.setPgpInfo(applicationEntity.getPgpInfo());
        ftpModel.setHostName(ftpEntity.getHostName());
        ftpModel.setPortNumber(ftpEntity.getPortNo());
        ftpModel.setUserName(ftpEntity.getUserId());
        ftpModel.setPassword(PRAGMA_EDGE_S);
        ftpModel.setFileType(ftpEntity.getFileType());
        ftpModel.setTransferType(ftpEntity.getTransferType());
        ftpModel.setInDirectory(ftpEntity.getInDirectory());
        ftpModel.setOutDirectory(ftpEntity.getOutDirectory());
        ftpModel.setCertificateId(ftpEntity.getCertificateId());
        ftpModel.setKnownHostKey(ftpEntity.getKnownHostKey());
        ftpModel.setAdapterName(ftpEntity.getAdapterName());
        ftpModel.setPoolingInterval(ftpEntity.getPoolingIntervalMins());
        ftpModel.setDeleteAfterCollection(convertStringToBoolean(ftpEntity.getDeleteAfterCollection()));
        ftpModel.setHubInfo(convertStringToBoolean(ftpEntity.getIsHubInfo()));
        ftpModel.setPemIdentifier(applicationEntity.getPemIdentifier());
        ftpModel.setCwdUp(ftpEntity.getCwdUp())
                .setQuote(ftpEntity.getQuote())
                .setSite(ftpEntity.getSite())
                .setConnectionType(ftpEntity.getConnectionType())
                .setMbDestination(ftpEntity.getMbDestination())
                .setMbDestinationLookup(ftpEntity.getMbDestinationLookup())
                .setSslSocket(ftpEntity.getSslSocket())
                .setSslCipher(ftpEntity.getSslCipher())
                .setSshAuthentication(ftpEntity.getSshAuthentication())
                .setSshCipher(ftpEntity.getSshCipher())
                .setSshCompression(ftpEntity.getSshCompression())
                .setSshMac(ftpEntity.getSshMac())
                .setSshIdentityKeyName(ftpEntity.getSshIdentityKeyName());

        return ftpModel;
    }
}
