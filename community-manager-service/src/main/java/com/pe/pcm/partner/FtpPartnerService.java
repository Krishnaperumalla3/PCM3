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

package com.pe.pcm.partner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.b2b.B2BApiService;
import com.pe.pcm.b2b.usermailbox.RemoteUserInfoModel;
import com.pe.pcm.enums.Protocol;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.miscellaneous.PasswordUtilityService;
import com.pe.pcm.miscellaneous.UserUtilityService;
import com.pe.pcm.partner.entity.PartnerEntity;
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
import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.KeyGeneratorUtil.getChildPrimaryKey;
import static com.pe.pcm.utils.PCMConstants.PRAGMA_EDGE_S;
import static com.pe.pcm.utils.PCMConstants.TP;
import static java.lang.Boolean.FALSE;

/**
 * @author Kiran Reddy.
 */
@Service
public class FtpPartnerService {

    private final PartnerService partnerService;

    private final ActivityHistoryService activityHistoryService;

    private final ObjectMapper mapper = new ObjectMapper();

    private final FtpService ftpService;

    private final ProcessService processService;

    private final B2BApiService b2BApiService;

    private final UserUtilityService userUtilityService;

    private final ManageProtocolService manageProtocolService;

    private final PasswordUtilityService passwordUtilityService;
    private final YfsUserService yfsUserService;

    private final BiFunction<PartnerEntity, FtpEntity, FtpModel> serialize = FtpPartnerService::apply;

    @Autowired
    public FtpPartnerService(PartnerService partnerService,
                             ActivityHistoryService activityHistoryService,
                             FtpService ftpService,
                             ProcessService processService,
                             B2BApiService b2BApiService,
                             UserUtilityService userUtilityService,
                             ManageProtocolService manageProtocolService,
                             PasswordUtilityService passwordUtilityService,
                             YfsUserService yfsUserService) {
        this.partnerService = partnerService;
        this.activityHistoryService = activityHistoryService;
        this.ftpService = ftpService;
        this.processService = processService;
        this.b2BApiService = b2BApiService;
        this.userUtilityService = userUtilityService;
        this.manageProtocolService = manageProtocolService;
        this.passwordUtilityService = passwordUtilityService;
        this.yfsUserService = yfsUserService;
    }

    private void duplicatePartner(String partnerId) {
        Optional<PartnerEntity> tradingPartnerEntityOptional = partnerService.find(partnerId);
        tradingPartnerEntityOptional.ifPresent(tradingPartnerEntity -> {
            throw conflict("Partner");
        });
    }

    @Transactional
    public String save(FtpModel ftpModel) {
        if (!Optional.ofNullable(Protocol.findProtocol(ftpModel.getProtocol())).isPresent()) {
            throw unknownProtocol();
        }
        duplicatePartner(ftpModel.getProfileId());
        String parentPrimaryKey = KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.TP_PKEY_PRE_APPEND,
                PCMConstants.TP_PKEY_RANDOM_COUNT);
        String childPrimaryKey = KeyGeneratorUtil.getChildPrimaryKey(ftpModel.getProtocol());
        saveProtocol(ftpModel, parentPrimaryKey, childPrimaryKey);
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(ftpModel, partnerModel);
        partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
        ftpModel.setCreateUserInSI(ftpModel.getCreateUserInSI() != null ? ftpModel.getCreateUserInSI() : FALSE);
        ftpModel.setCreateDirectoryInSI(ftpModel.getCreateDirectoryInSI() != null ? ftpModel.getCreateDirectoryInSI() : FALSE);
        b2BApiService.createMailBoxInSI(ftpModel.getCreateDirectoryInSI(), ftpModel.getInDirectory(), ftpModel.getOutDirectory());
        b2BApiService.createUserInSI(ftpModel.getCreateUserInSI(), mapper.convertValue(ftpModel, RemoteUserInfoModel.class));
        activityHistoryService.savePartnerActivity(parentPrimaryKey, "Trading Partner created.");
        userUtilityService.addPartnerToCurrentUser(parentPrimaryKey, TP);
        return parentPrimaryKey;
    }

    @Transactional
    public void update(FtpModel ftpModel) {
        PartnerEntity oldPartnerEntity = SerializationUtils.clone(partnerService.get(ftpModel.getPkId()));
        if (!oldPartnerEntity.getTpId().equalsIgnoreCase(ftpModel.getProfileId())) {
            duplicatePartner(ftpModel.getProfileId());
        }
        boolean passwordNotChanged;
        String parentPrimaryKey = oldPartnerEntity.getPkId();
        String childPrimaryKey = oldPartnerEntity.getPartnerProtocolRef();
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(ftpModel.getProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
        Map<String, String> oldFtpEntityMap = new LinkedHashMap<>();
        boolean isUpdate = true;
        if (!protocol.getProtocol().equalsIgnoreCase(oldPartnerEntity.getTpProtocol())) {
            manageProtocolService.deleteProtocol(oldPartnerEntity.getTpProtocol(), oldPartnerEntity.getPkId());
            childPrimaryKey = getChildPrimaryKey(ftpModel.getProtocol());
            isUpdate = false;
        } else {
            oldFtpEntityMap = mapper.convertValue(ftpService.get(parentPrimaryKey), new TypeReference<Map<String, String>>() {
            });
        }

        //Password handling
        if (StringUtils.hasText(ftpModel.getPassword()) && ftpModel.getPassword().equals(PRAGMA_EDGE_S)) {
            passwordNotChanged = true;
            ftpModel.setPassword(oldFtpEntityMap.get("password"));
        } else {
            passwordNotChanged = false;
        }

        Map<String, String> newFtpEntityMap = mapper.convertValue(saveProtocol(ftpModel, parentPrimaryKey, childPrimaryKey), new TypeReference<Map<String, String>>() {
        });
        ftpModel.setPemIdentifier(isNotNull(ftpModel.getPemIdentifier())
                ? ftpModel.getPemIdentifier() : oldPartnerEntity.getPemIdentifier());
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(ftpModel, partnerModel);
        PartnerEntity newPartnerEntity = partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);

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
        activityHistoryService.updatePartnerActivity(oldPartnerEntity, newPartnerEntity, oldFtpEntityMap, newFtpEntityMap, isUpdate);
    }

    public FtpEntity saveProtocol(FtpModel ftpModel, String parentPrimaryKey, String childPrimaryKey) {
        return ftpService.saveProtocol(ftpModel, parentPrimaryKey, childPrimaryKey, "TP");
    }

    @Transactional
    public void delete(String pkId) {
        if (!processService.findByPartnerProfile(pkId).isEmpty()) {
            throw internalServerError("Unable to delete this partner, Because it has workflow..");
        }
        PartnerEntity partnerEntity = partnerService.findPartnerById(pkId);
        ftpService.delete(pkId);
        partnerService.delete(partnerEntity);
    }

    public FtpModel get(String pkId) {
        PartnerEntity partnerEntity = partnerService.get(pkId);
        FtpEntity ftpEntity = ftpService.get(pkId);
        return serialize.apply(partnerEntity, ftpEntity);
    }

    private static FtpModel apply(PartnerEntity tradingPartner, FtpEntity ftpEntity) {
        FtpModel ftpModel = new FtpModel();

        ftpModel.setPkId(tradingPartner.getPkId())
                .setProfileName(tradingPartner.getTpName())
                .setProfileId(tradingPartner.getTpId())
                .setPkId(tradingPartner.getPkId())
                .setAddressLine2(tradingPartner.getAddressLine2())
                .setAddressLine1(tradingPartner.getAddressLine1())
                .setProtocol(tradingPartner.getTpProtocol())
                .setEmailId(tradingPartner.getEmail())
                .setPemIdentifier(tradingPartner.getPemIdentifier())
                .setPhone(tradingPartner.getPhone())
                .setStatus(convertStringToBoolean(tradingPartner.getStatus()))
                .setHubInfo(convertStringToBoolean(ftpEntity.getIsHubInfo()))
                .setPgpInfo(tradingPartner.getPgpInfo());

        return ftpModel.setHostName(ftpEntity.getHostName())
                .setPortNumber(ftpEntity.getPortNo())
                .setUserName(ftpEntity.getUserId())
                .setPassword(PRAGMA_EDGE_S)
                .setFileType(ftpEntity.getFileType())
                .setTransferType(ftpEntity.getTransferType())
                .setInDirectory(ftpEntity.getInDirectory())
                .setOutDirectory(ftpEntity.getOutDirectory())
                .setCertificateId(ftpEntity.getCertificateId())
                .setKnownHostKey(ftpEntity.getKnownHostKey())
                .setAdapterName(ftpEntity.getAdapterName())
                .setPoolingInterval(ftpEntity.getPoolingIntervalMins())
                .setDeleteAfterCollection(convertStringToBoolean(ftpEntity.getDeleteAfterCollection()))
                .setCreateDirectoryInSI(false)
                .setCreateUserInSI(false)
                .setCwdUp(ftpEntity.getCwdUp())
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

    }

}
