/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc.
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

package com.pe.pcm.application.sterling;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.application.ApplicationModel;
import com.pe.pcm.application.ApplicationService;
import com.pe.pcm.application.entity.ApplicationEntity;
import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.enums.Protocol;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.partner.PartnerModel;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.RemoteProfileModel;
import com.pe.pcm.protocol.remoteftp.entity.RemoteFtpEntity;
import com.pe.pcm.protocol.si.RemoteFtpServiceDup;
import com.pe.pcm.sterling.partner.sci.SterlingPartnerProfileService;
import com.pe.pcm.utils.KeyGeneratorUtil;
import com.pe.pcm.utils.PCMConstants;
import com.pe.pcm.workflow.ProcessService;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static com.pe.pcm.exception.GlobalExceptionHandler.*;
import static com.pe.pcm.protocol.function.SterlingFunctions.applyRemoteProfileModelDefaultValues;
import static com.pe.pcm.utils.CommonFunctions.*;
import static com.pe.pcm.utils.KeyGeneratorUtil.getChildPrimaryKey;
import static com.pe.pcm.utils.PCMConstants.*;

/**
 * @author Kiran Reddy.
 */
@Service
public class SterlingFtpApplicationService {
    private final ApplicationService applicationService;
    private final RemoteFtpServiceDup remoteFtpServiceDup;
    private final ActivityHistoryService activityHistoryService;
    private final ProcessService processService;
    private final ManageProtocolService manageProtocolService;
    private final SterlingPartnerProfileService sterlingPartnerProfileService;
    private final ObjectMapper mapper = new ObjectMapper();
    private final BiFunction<ApplicationEntity, RemoteFtpEntity, RemoteProfileModel> serialize = SterlingFtpApplicationService::apply;

    @Autowired
    public SterlingFtpApplicationService(ApplicationService applicationService, RemoteFtpServiceDup remoteFtpServiceDup,
                                         ActivityHistoryService activityHistoryService, ProcessService processService, ManageProtocolService manageProtocolService, SterlingPartnerProfileService sterlingPartnerProfileService) {
        this.applicationService = applicationService;
        this.remoteFtpServiceDup = remoteFtpServiceDup;
        this.activityHistoryService = activityHistoryService;
        this.processService = processService;
        this.manageProtocolService = manageProtocolService;
        this.sterlingPartnerProfileService = sterlingPartnerProfileService;
    }

    private void duplicateApplication(String applicationId) {
        applicationService.find(applicationId).ifPresent(applicationEntity -> {
            throw conflict("Application");
        });
    }

    @Transactional
    public String create(RemoteProfileModel remoteProfileModel) {
        if (!Optional.ofNullable(Protocol.findProtocol(remoteProfileModel.getProtocol())).isPresent()) {
            throw unknownProtocol();
        }
        applyRemoteProfileModelDefaultValues.apply(remoteProfileModel);
        remoteProfileModel.setCustomProfileName(isNotNull(remoteProfileModel.getCustomProfileName()) ? remoteProfileModel.getCustomProfileName() : remoteProfileModel.getProfileName());
        duplicateApplication(remoteProfileModel.getProfileId());
        String parentPrimaryKey = KeyGeneratorUtil.getPrimaryKey.apply(APP_PKEY_PRE_APPEND, APP_PKEY_RANDOM_COUNT);
        String childPrimaryKey = KeyGeneratorUtil.getChildPrimaryKey(remoteProfileModel.getProtocol());
        pwdLastChangeDoneUpdate(remoteProfileModel, false, null);
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(remoteProfileModel.getProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
        saveProtocol(remoteProfileModel, protocol, parentPrimaryKey, childPrimaryKey, remoteProfileModel.getCustomProfileName(), false);
        ApplicationModel applicationModel = new ApplicationModel();
        BeanUtils.copyProperties(remoteProfileModel, applicationModel);
        applicationService.save(applicationModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.saveApplicationActivity(parentPrimaryKey, "Application created.");
        return parentPrimaryKey;
    }

    @Transactional
    public void update(RemoteProfileModel remoteProfileModel) {
        ApplicationEntity oldApplicationEntity = SerializationUtils.clone(applicationService.get(remoteProfileModel.getPkId()));
        if (!oldApplicationEntity.getApplicationId().equalsIgnoreCase(remoteProfileModel.getProfileId())) {
            duplicateApplication(remoteProfileModel.getProfileId());
        }
        applyRemoteProfileModelDefaultValues.apply(remoteProfileModel);
        remoteProfileModel.setCustomProfileName(isNotNull(remoteProfileModel.getCustomProfileName()) ? remoteProfileModel.getCustomProfileName() : remoteProfileModel.getProfileName());
        String parentPrimaryKey = oldApplicationEntity.getPkId();
        String childPrimaryKey = oldApplicationEntity.getAppProtocolRef();
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(remoteProfileModel.getProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
        Map<String, String> oldFtpEntityMap = new LinkedHashMap<>();
        boolean isUpdate;
        if (!protocol.getProtocol().equalsIgnoreCase(oldApplicationEntity.getAppIntegrationProtocol())) {
            Protocol oldProtocol = Optional.ofNullable(Protocol.findProtocol(oldApplicationEntity.getAppIntegrationProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
            if (oldProtocol.getProtocol().equals(Protocol.AS2.getProtocol()) ||
                    oldProtocol.getProtocol().equals(Protocol.SFG_FTP.getProtocol()) ||
                    oldProtocol.getProtocol().equals(Protocol.SFG_FTPS.getProtocol()) ||
                    oldProtocol.getProtocol().equals(Protocol.SFG_SFTP.getProtocol())) {
                sterlingPartnerProfileService.deleteSterlingProfiles(oldApplicationEntity.getApplicationName(),
                        null,
                        oldProtocol);
            }
            manageProtocolService.deleteProtocol(oldApplicationEntity.getAppIntegrationProtocol(), oldApplicationEntity.getPkId());
            childPrimaryKey = getChildPrimaryKey(remoteProfileModel.getProtocol());
            isUpdate = false;
        } else {
            oldFtpEntityMap = mapper.convertValue(remoteFtpServiceDup.get(parentPrimaryKey),
                    new TypeReference<Map<String, String>>() {
                    });
            isUpdate = true;
        }
        if (protocol.getProtocol().equalsIgnoreCase(oldApplicationEntity.getAppIntegrationProtocol()) &&
                !oldFtpEntityMap.isEmpty() &&
                !convertBooleanToString(remoteProfileModel.getHubInfo()).equalsIgnoreCase(oldFtpEntityMap.get("isHubInfo"))) {
            if (remoteProfileModel.getHubInfo()) {
                sterlingPartnerProfileService.deleteSterlingProfiles(oldApplicationEntity.getApplicationName(), null, protocol);
            } else {
                if (isNotNull(oldFtpEntityMap.get("userId"))) {
                    sterlingPartnerProfileService.deleteUser(oldFtpEntityMap.get("userId"));
                }
            }
            isUpdate = false;
        }

        remoteProfileModel.setSciProfileObjectId(oldFtpEntityMap.get("profileId"));
        Map<String, String> newRemoteFtpEntityMap = mapper.convertValue(saveProtocol(remoteProfileModel, protocol, parentPrimaryKey, childPrimaryKey,
                oldApplicationEntity.getApplicationName(), isUpdate), new TypeReference<Map<String, String>>() {
        });
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(remoteProfileModel, partnerModel);
        ApplicationEntity newApplicationEntity = applicationService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.updateApplicationActivity(oldApplicationEntity,
                newApplicationEntity, oldFtpEntityMap, newRemoteFtpEntityMap, isUpdate);
    }

    public RemoteProfileModel get(String pkId) {
        RemoteProfileModel remoteProfileModel = sterlingPartnerProfileService.get(serialize.apply(applicationService.get(pkId), remoteFtpServiceDup.get(pkId)).setSubscriberType("APP"));

        String authType;
        if (isNotNull(remoteProfileModel.getPreferredAuthenticationType())) {
            if (remoteProfileModel.getPreferredAuthenticationType().equalsIgnoreCase("password")) {
                authType = remoteProfileModel.getPreferredAuthenticationType().toUpperCase();
            } else {
                authType = "PUBLIC_KEY";
            }
        } else {
            authType = "PASSWORD";
        }

        remoteProfileModel.setPreferredAuthenticationType(authType);
        return remoteProfileModel.setPreferredCipher(isNotNull(remoteProfileModel.getPreferredCipher()) ? remoteProfileModel.getPreferredCipher().toUpperCase() : "")
                .setPreferredMacAlgorithm(isNotNull(remoteProfileModel.getPreferredMacAlgorithm()) ? remoteProfileModel.getPreferredMacAlgorithm().toUpperCase() : "")
                .setCompression(isNotNull(remoteProfileModel.getCompression()) ? remoteProfileModel.getCompression().toUpperCase() : "");
    }

    @Transactional
    public void delete(String pkId, Boolean deleteUser, Boolean deleteMailboxes) {
        if (!processService.findByApplicationProfile(pkId).isEmpty()) {
            throw internalServerError("Unable to Delete this Application, Because it has workflow.");
        }
        ApplicationEntity applicationEntity = applicationService.get(pkId);
        RemoteFtpEntity remoteFtpEntity = remoteFtpServiceDup.get(pkId);

        sterlingPartnerProfileService.delete(applicationEntity.getApplicationName(),
                null,
                Optional.ofNullable(Protocol.findProtocol(applicationEntity.getAppIntegrationProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol),
                convertStringToBoolean(remoteFtpEntity.getIsHubInfo()),
                deleteUser,
                remoteFtpEntity.getUserId(),
                deleteMailboxes,
                remoteFtpEntity.getInDirectory(),
                remoteFtpEntity.getOutDirectory()
        );
        remoteFtpServiceDup.delete(pkId);
        applicationService.delete(applicationEntity);
    }

    private RemoteFtpEntity saveProtocol(RemoteProfileModel remoteProfileModel, Protocol protocol, String parentPrimaryKey, String childPrimaryKey, String oldProfileName, Boolean isUpdate) {
        remoteProfileModel.setSubscriberType(APP);
        remoteProfileModel.setCreateUserInSI(Boolean.TRUE);
        /*Save into PCM Table*/
        return remoteFtpServiceDup.saveProtocol(remoteProfileModel, parentPrimaryKey, childPrimaryKey,
                remoteProfileModel.isOnlyPCM() ? "" : sterlingPartnerProfileService.save(remoteProfileModel, protocol, oldProfileName, isUpdate));
    }

    public void changeStatus(String pkId, boolean status) {
        applicationService.save(applicationService.get(pkId).setAppIsActive(convertBooleanToString(status)));
        remoteFtpServiceDup.save(remoteFtpServiceDup.get(pkId).setIsActive(convertBooleanToString(status)));
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
        remoteProfileModel.setPgpInfo(applicationEntity.getPgpInfo());
        remoteProfileModel.setUserName(remoteFtpEntity.getUserId())
                .setPassword(PRAGMA_EDGE_S);
        remoteProfileModel.setTransferType(remoteFtpEntity.getTransferType());
        remoteProfileModel.setInDirectory(remoteFtpEntity.getInDirectory());
        remoteProfileModel.setCreateDirectoryInSI(Boolean.TRUE);
        remoteProfileModel.setOutDirectory(remoteFtpEntity.getOutDirectory());
        remoteProfileModel.setFileType(remoteFtpEntity.getFileType());
        //Start : Extra adding for Bulk uploads
        remoteProfileModel.setRemoteHost(remoteFtpEntity.getHostName());
        remoteProfileModel.setRemotePort(remoteFtpEntity.getPortNo());
        remoteProfileModel.setCertificateId(remoteFtpEntity.getCertificateId());
        remoteProfileModel.setUserIdentityKey(remoteFtpEntity.getUserIdentityKey());
        remoteProfileModel.setKnownHostKey(remoteFtpEntity.getKnownHostKey());
        remoteProfileModel.setPreferredAuthenticationType(remoteFtpEntity.getPrfAuthType());
        if (isNotNull(remoteFtpEntity.getAuthUserKeys())) {
            remoteProfileModel.setAuthorizedUserKeys(Arrays
                    .stream(remoteFtpEntity.getAuthUserKeys().split(","))
                    .map(CommunityManagerNameModel::new)
                    .collect(Collectors.toList()));
        }
        //End : Extra adding for Bulk uploads
        remoteProfileModel.setDeleteAfterCollection(convertStringToBoolean(remoteFtpEntity.getDeleteAfterCollection()));
        remoteProfileModel.setAdapterName(remoteFtpEntity.getAdapterName());
        remoteProfileModel.setPoolingInterval(remoteFtpEntity.getPoolingIntervalMins());
        remoteProfileModel.setHubInfo(convertStringToBoolean(remoteFtpEntity.getIsHubInfo()));
        remoteProfileModel.setSubscriberType("APP");
        remoteProfileModel.setPemIdentifier(applicationEntity.getPemIdentifier());
        remoteProfileModel.setConnectionType(remoteFtpEntity.getConnectionType());
        remoteProfileModel.setSciProfileObjectId(remoteFtpEntity.getProfileId());
        remoteProfileModel.setUseImplicitSSL(convertIMPSSLStrToBool.apply(remoteFtpEntity.getUseImplicitSsl()));
        return remoteProfileModel;
    }

    private void pwdLastChangeDoneUpdate(RemoteProfileModel remoteProfileModel, boolean isUpdate, String lastChangeDone) {
        if (!remoteProfileModel.getHubInfo()) {
            if (isUpdate) {
                if (isNotNull(remoteProfileModel.getPassword())) {
                    if (remoteProfileModel.getPassword().equals(PCMConstants.PRAGMA_EDGE_S)) {
                        if (isNotNull(lastChangeDone)) {
                            remoteProfileModel.setPwdLastChangeDone(new Timestamp(Long.parseLong(lastChangeDone)));
                        } else {
                            remoteProfileModel.setPwdLastChangeDone(new Timestamp(System.currentTimeMillis()));
                        }
                    } else {
                        remoteProfileModel.setPwdLastChangeDone(new Timestamp(System.currentTimeMillis()));
                    }
                } else {
                    if (isNotNull(lastChangeDone)) {
                        remoteProfileModel.setPwdLastChangeDone(new Timestamp(Long.parseLong(lastChangeDone)));
                    } else {
                        remoteProfileModel.setPwdLastChangeDone(new Timestamp(System.currentTimeMillis()));
                    }
                }
            } else {
                remoteProfileModel.setPwdLastChangeDone(new Timestamp(System.currentTimeMillis()));
            }
        }

    }

}
