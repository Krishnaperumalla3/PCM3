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

package com.pe.pcm.application.sfg;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.application.ApplicationModel;
import com.pe.pcm.application.ApplicationService;
import com.pe.pcm.application.entity.ApplicationEntity;
import com.pe.pcm.application.entity.RemoteApplicationStagingEntity;
import com.pe.pcm.b2b.B2BRemoteFtpService;
import com.pe.pcm.enums.Protocol;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.partner.PartnerModel;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.RemoteFtpService;
import com.pe.pcm.protocol.RemoteProfileModel;
import com.pe.pcm.protocol.remoteftp.entity.RemoteFtpEntity;
import com.pe.pcm.utils.KeyGeneratorUtil;
import com.pe.pcm.workflow.ProcessService;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

import static com.pe.pcm.exception.GlobalExceptionHandler.*;
import static com.pe.pcm.protocol.function.SterlingFunctions.applyRemoteProfileModelDefaultValues;
import static com.pe.pcm.utils.CommonFunctions.*;
import static com.pe.pcm.utils.KeyGeneratorUtil.getChildPrimaryKey;
import static com.pe.pcm.utils.PCMConstants.APP_PKEY_PRE_APPEND;
import static com.pe.pcm.utils.PCMConstants.APP_PKEY_RANDOM_COUNT;

/**
 * @author Chenchu Kiran.
 */
@Service
public class RemoteFtpApplicationService {

    private ApplicationService applicationService;
    private RemoteFtpService remoteFtpService;
    private ActivityHistoryService activityHistoryService;
    private B2BRemoteFtpService b2BRemoteFtpService;
    private ProcessService processService;
    private ManageProtocolService manageProtocolService;
    private ObjectMapper mapper = new ObjectMapper();
    private BiFunction<ApplicationEntity, RemoteFtpEntity, RemoteProfileModel> serialize = RemoteFtpApplicationService::apply;

    @Autowired
    public RemoteFtpApplicationService(ApplicationService applicationService, RemoteFtpService remoteFtpService,
                                       ActivityHistoryService activityHistoryService, B2BRemoteFtpService b2BRemoteFtpService, ProcessService processService, ManageProtocolService manageProtocolService) {
        this.applicationService = applicationService;
        this.remoteFtpService = remoteFtpService;
        this.activityHistoryService = activityHistoryService;
        this.b2BRemoteFtpService = b2BRemoteFtpService;
        this.processService = processService;
        this.manageProtocolService = manageProtocolService;
    }

    private void duplicateApplication(String applicationId) {
        applicationService.find(applicationId).ifPresent(applicationEntity -> {
            throw conflict("Application");
        });
    }

    @Transactional
    public String save(RemoteProfileModel remoteProfileModel) {
        if (!Optional.ofNullable(Protocol.findProtocol(remoteProfileModel.getProtocol())).isPresent()) {
            throw unknownProtocol();
        }
        duplicateApplication(remoteProfileModel.getProfileId());
        remoteProfileModel.setEmailId(formatMail.apply(remoteProfileModel.getEmailId()));
        applyRemoteProfileModelDefaultValues.apply(remoteProfileModel);
        String parentPrimaryKey = KeyGeneratorUtil.getPrimaryKey.apply(APP_PKEY_PRE_APPEND, APP_PKEY_RANDOM_COUNT);
        String childPrimaryKey = KeyGeneratorUtil.getChildPrimaryKey(remoteProfileModel.getProtocol());
        saveProtocol(remoteProfileModel, parentPrimaryKey, childPrimaryKey);
        ApplicationModel applicationModel = new ApplicationModel();
        BeanUtils.copyProperties(remoteProfileModel, applicationModel);
        applicationService.save(applicationModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.saveApplicationActivity(parentPrimaryKey, "Application created.");
        return parentPrimaryKey;
    }

    private void saveProtocol(RemoteProfileModel remoteProfileModel, String parentPrimaryKey, String childPrimaryKey) {
        remoteFtpService.saveProtocol(remoteProfileModel, parentPrimaryKey, childPrimaryKey, false, "APP", "");
    }

    @Transactional
    public void changeStatus(String pkId, boolean status) {
        ApplicationEntity newApplicationEntity = applicationService.get(pkId);
        ApplicationEntity oldApplicationEntity = SerializationUtils.clone(newApplicationEntity);
        newApplicationEntity.setAppIsActive(convertBooleanToString(status));
        RemoteFtpEntity remoteFtpEntity = remoteFtpService.get(pkId);
        Map<String, String> oldEntityMap = getEntityMap(remoteFtpEntity);
        remoteFtpEntity.setIsActive(convertBooleanToString(status));
        Map<String, String> newEntityMap = getEntityMap(remoteFtpEntity);
        activityHistoryService.updateApplicationActivity(oldApplicationEntity,
                newApplicationEntity, oldEntityMap, newEntityMap, true);
    }

    @Transactional
    public void update(RemoteProfileModel remoteProfileModel) {
        ApplicationEntity oldApplicationEntity = SerializationUtils.clone(applicationService.get(remoteProfileModel.getPkId()));
        if (!oldApplicationEntity.getApplicationId().equalsIgnoreCase(remoteProfileModel.getProfileId())) {
            duplicateApplication(remoteProfileModel.getProfileId());
        }
        remoteProfileModel.setEmailId(formatMail.apply(remoteProfileModel.getEmailId()));
        applyRemoteProfileModelDefaultValues.apply(remoteProfileModel);
        String parentPrimaryKey = oldApplicationEntity.getPkId();
        String childPrimaryKey = oldApplicationEntity.getAppProtocolRef();
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(oldApplicationEntity.getAppIntegrationProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
        Map<String, String> oldHttpEntityMap = new LinkedHashMap<>();
        boolean isUpdate = true;
        if (!protocol.getProtocol().equalsIgnoreCase(remoteProfileModel.getProtocol())) {
            manageProtocolService.deleteProtocol(oldApplicationEntity.getAppIntegrationProtocol(), oldApplicationEntity.getPkId());
            childPrimaryKey = getChildPrimaryKey(remoteProfileModel.getProtocol());
            isUpdate = false;
        } else {
            oldHttpEntityMap = mapper.convertValue(remoteFtpService.get(parentPrimaryKey),
                    new TypeReference<Map<String, String>>() {
                    });
        }
        if (isNotNull(remoteProfileModel.getHubInfo()) && !convertBooleanToString(remoteProfileModel.getHubInfo()).equalsIgnoreCase(oldHttpEntityMap.get("isHubInfo"))) {
            if (!remoteProfileModel.getHubInfo()) {
                isUpdate = false;
            }
        }
        Map<String, String> newRemoteFtpEntityMap = mapper.convertValue(remoteFtpService.saveProtocol(remoteProfileModel, parentPrimaryKey, childPrimaryKey,
                isUpdate, "APP", oldApplicationEntity.getApplicationName()), new TypeReference<Map<String, String>>() {
        });
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(remoteProfileModel, partnerModel);
        ApplicationEntity newApplicationEntity = applicationService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.updateApplicationActivity(oldApplicationEntity,
                newApplicationEntity, oldHttpEntityMap, newRemoteFtpEntityMap, isUpdate);
    }

    @Transactional
    public RemoteProfileModel get(String pkId) {
        RemoteProfileModel remoteProfileModel = serialize.apply(applicationService.get(pkId), remoteFtpService.get(pkId));
        if (!remoteProfileModel.getHubInfo()) {
            return remoteFtpService.getRemoteFtp(remoteProfileModel);
        } else {
            remoteFtpService.setB2bUserParams(remoteProfileModel);
        }
        return remoteProfileModel;
    }

    @Transactional
    public void delete(String pkId, Boolean isDeleteInSI) {
        if (!processService.findByApplicationProfile(pkId).isEmpty()) {
            throw internalServerError("Unable to Delete this Application, Because it has workflow.");
        }
        ApplicationEntity applicationEntity = applicationService.get(pkId);
        remoteFtpService.deleteProtocol(applicationEntity.getPkId(), applicationEntity.getApplicationName(), applicationEntity.getAppIntegrationProtocol(), isDeleteInSI);
        applicationService.delete(applicationEntity);
    }

    private static RemoteProfileModel apply(ApplicationEntity applicationEntity, RemoteFtpEntity remoteFtpEntity) {
        RemoteProfileModel remoteProfileModel = new RemoteProfileModel();
        remoteProfileModel.setPkId(applicationEntity.getPkId());
        remoteProfileModel.setProfileName(applicationEntity.getApplicationName());
        remoteProfileModel.setProfileId(applicationEntity.getApplicationId());
        remoteProfileModel.setProtocol(applicationEntity.getAppIntegrationProtocol());
        remoteProfileModel.setEmailId(applicationEntity.getEmailId());
        remoteProfileModel.setPhone(applicationEntity.getPhone());
        remoteProfileModel.setStatus(convertStringToBoolean(applicationEntity.getAppIsActive()));
        remoteProfileModel.setUserName(remoteFtpEntity.getUserId());
        remoteProfileModel.setPassword(remoteFtpEntity.getPassword());
        remoteProfileModel.setTransferType(remoteFtpEntity.getTransferType());
        remoteProfileModel.setInDirectory(remoteFtpEntity.getInDirectory());
        remoteProfileModel.setOutDirectory(remoteFtpEntity.getOutDirectory());
        remoteProfileModel.setFileType(remoteFtpEntity.getFileType());
        //Start : Extra adding for Bulk uploads
        remoteProfileModel.setRemoteHost(remoteFtpEntity.getHostName());
        remoteProfileModel.setRemotePort(remoteFtpEntity.getPortNo());
        remoteProfileModel.setCertificateId(remoteFtpEntity.getCertificateId());
        remoteProfileModel.setUserIdentityKey(remoteFtpEntity.getUserIdentityKey());
        remoteProfileModel.setKnownHostKey(remoteFtpEntity.getKnownHostKey());
        //End : Extra adding for Bulk uploads
        remoteProfileModel.setDeleteAfterCollection(convertStringToBoolean(remoteFtpEntity.getDeleteAfterCollection()));
        remoteProfileModel.setAdapterName(remoteFtpEntity.getAdapterName());
        remoteProfileModel.setPoolingInterval(remoteFtpEntity.getPoolingIntervalMins());
        remoteProfileModel.setHubInfo(convertStringToBoolean(remoteFtpEntity.getIsHubInfo()));
        remoteProfileModel.setSubscriberType("APP");
        remoteProfileModel.setPemIdentifier(applicationEntity.getPemIdentifier());
        if (remoteProfileModel.getHubInfo()) {
            remoteProfileModel.setPreferredAuthenticationType(isNotNull(remoteFtpEntity.getPrfAuthType()) ? remoteFtpEntity.getPrfAuthType() : "PASSWORD");
        }
        return remoteProfileModel;
    }

    @Transactional
    public void saveRemoteStagingProfile(RemoteApplicationStagingEntity remoteApplicationStagingEntity, RemoteFtpEntity remoteFtpEntity) {
        ApplicationEntity applicationEntity = new ApplicationEntity();
        BeanUtils.copyProperties(remoteApplicationStagingEntity, applicationEntity);
        RemoteProfileModel remoteProfileModel = serialize.apply(applicationEntity, remoteFtpEntity);
        String profileId = b2BRemoteFtpService.saveRemoteFtpProfile(remoteProfileModel, false, "APP", null);
        remoteFtpService.save(remoteFtpEntity.setProfileId(profileId));
        activityHistoryService.saveApplicationActivity(remoteApplicationStagingEntity.getPkId(), "Application created.");
    }
}
