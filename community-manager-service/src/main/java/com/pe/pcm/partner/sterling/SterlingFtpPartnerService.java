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

package com.pe.pcm.partner.sterling;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.enums.Protocol;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.miscellaneous.UserUtilityService;
import com.pe.pcm.partner.PartnerModel;
import com.pe.pcm.partner.PartnerService;
import com.pe.pcm.partner.entity.PartnerEntity;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.RemoteProfileModel;
import com.pe.pcm.protocol.remoteftp.entity.RemoteFtpEntity;
import com.pe.pcm.protocol.si.RemoteFtpServiceDup;
import com.pe.pcm.sterling.partner.sci.SterlingPartnerProfileService;
import com.pe.pcm.utils.PCMConstants;
import com.pe.pcm.workflow.ProcessService;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static com.pe.pcm.exception.GlobalExceptionHandler.*;
import static com.pe.pcm.protocol.function.SterlingFunctions.applyRemoteProfileModelDefaultValues;
import static com.pe.pcm.utils.CommonFunctions.*;
import static com.pe.pcm.utils.KeyGeneratorUtil.getChildPrimaryKey;
import static com.pe.pcm.utils.KeyGeneratorUtil.getPrimaryKey;
import static com.pe.pcm.utils.PCMConstants.PRAGMA_EDGE_S;
import static com.pe.pcm.utils.PCMConstants.TP;
import static java.lang.Boolean.TRUE;

/**
 * @author Kiran Reddy.
 */
@Service
public class SterlingFtpPartnerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SterlingFtpPartnerService.class);

    private final PartnerService partnerService;
    private final ActivityHistoryService activityHistoryService;
    private final UserUtilityService userUtilityService;
    private final ManageProtocolService manageProtocolService;
    private final ProcessService processService;
    private final SterlingPartnerProfileService sterlingPartnerProfileService;
    private final RemoteFtpServiceDup remoteFtpServiceDup;

    private final ObjectMapper mapper = new ObjectMapper();
    private final BiFunction<PartnerEntity, RemoteFtpEntity, RemoteProfileModel> serialize = SterlingFtpPartnerService::apply;

    @Autowired
    public SterlingFtpPartnerService(PartnerService partnerService,
                                     ActivityHistoryService activityHistoryService,
                                     UserUtilityService userUtilityService,
                                     ManageProtocolService manageProtocolService,
                                     ProcessService processService,
                                     SterlingPartnerProfileService sterlingPartnerProfileService,
                                     RemoteFtpServiceDup remoteFtpServiceDup) {
        this.partnerService = partnerService;
        this.activityHistoryService = activityHistoryService;
        this.userUtilityService = userUtilityService;
        this.manageProtocolService = manageProtocolService;
        this.processService = processService;
        this.sterlingPartnerProfileService = sterlingPartnerProfileService;
        this.remoteFtpServiceDup = remoteFtpServiceDup;
    }

    @Transactional
    public String create(RemoteProfileModel remoteProfileModel) {
        LOGGER.info("Sterling Profile save() Method.");
        if (!Optional.ofNullable(Protocol.findProtocol(remoteProfileModel.getProtocol())).isPresent()) {
            throw unknownProtocol();
        }
        applyRemoteProfileModelDefaultValues.apply(remoteProfileModel);
        remoteProfileModel.setCustomProfileName(isNotNull(remoteProfileModel.getCustomProfileName()) ? remoteProfileModel.getCustomProfileName() : remoteProfileModel.getProfileName());
        duplicatePartner(remoteProfileModel.getProfileId());
        String parentPrimaryKey = getPrimaryKey.apply(PCMConstants.TP_PKEY_PRE_APPEND, PCMConstants.TP_PKEY_RANDOM_COUNT);
        String childPrimaryKey = getChildPrimaryKey(remoteProfileModel.getProtocol());
        pwdLastChangeDoneUpdate(remoteProfileModel, false, null);
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(remoteProfileModel.getProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
        saveProtocol(remoteProfileModel, protocol, parentPrimaryKey, childPrimaryKey, remoteProfileModel.getCustomProfileName(), false);
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(remoteProfileModel, partnerModel);
        partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.savePartnerActivity(parentPrimaryKey, "Trading Partner created.");
        userUtilityService.addPartnerToCurrentUser(parentPrimaryKey, TP);
        LOGGER.info("Completed Sterling Profile save() Method.");
        return parentPrimaryKey;
    }

    @Transactional
    public void update(RemoteProfileModel remoteProfileModel) {
        PartnerEntity oldPartnerEntity = SerializationUtils.clone(partnerService.get(remoteProfileModel.getPkId()));
        if (!oldPartnerEntity.getTpId().equalsIgnoreCase(remoteProfileModel.getProfileId())) {
            duplicatePartner(remoteProfileModel.getProfileId());
        }
        applyRemoteProfileModelDefaultValues.apply(remoteProfileModel);
        remoteProfileModel.setCustomProfileName(isNotNull(remoteProfileModel.getCustomProfileName()) ? remoteProfileModel.getCustomProfileName() : remoteProfileModel.getProfileName());
        String parentPrimaryKey = oldPartnerEntity.getPkId();
        String childPrimaryKey = oldPartnerEntity.getPartnerProtocolRef();
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(remoteProfileModel.getProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
        Map<String, String> oldFtpEntityMap = new LinkedHashMap<>();
        boolean isUpdate;
        if (!protocol.getProtocol().equalsIgnoreCase(oldPartnerEntity.getTpProtocol())) {
            Protocol oldProtocol = Optional.ofNullable(Protocol.findProtocol(oldPartnerEntity.getTpProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
            if (!convertStringToBoolean(oldPartnerEntity.getIsProtocolHubInfo()) &&
                    (oldProtocol.getProtocol().equals(Protocol.AS2.getProtocol()) ||
                            oldProtocol.getProtocol().equals(Protocol.SFG_FTP.getProtocol()) ||
                            oldProtocol.getProtocol().equals(Protocol.SFG_FTPS.getProtocol()) ||
                            oldProtocol.getProtocol().equals(Protocol.SFG_SFTP.getProtocol()))) {
                sterlingPartnerProfileService.deleteSterlingProfiles(oldPartnerEntity.getCustomTpName(),
                        oldPartnerEntity.getTpProtocol().equals(Protocol.AS2.getProtocol()) ? manageProtocolService.getAs2Identifier(oldPartnerEntity.getPkId()) : null,
                        oldProtocol);
            }
            manageProtocolService.deleteProtocol(oldPartnerEntity.getTpProtocol(), oldPartnerEntity.getPkId());
            childPrimaryKey = getChildPrimaryKey(remoteProfileModel.getProtocol());
            remoteProfileModel.setMergeUser(TRUE);
            isUpdate = false;
        } else {
            oldFtpEntityMap = mapper.convertValue(remoteFtpServiceDup.get(parentPrimaryKey), new TypeReference<Map<String, String>>() {
            });
            isUpdate = true;
        }
        if (protocol.getProtocol().equalsIgnoreCase(oldPartnerEntity.getTpProtocol()) &&
                !oldFtpEntityMap.isEmpty() &&
                !convertBooleanToString(remoteProfileModel.getHubInfo()).equalsIgnoreCase(oldFtpEntityMap.get("isHubInfo"))) {
            if (remoteProfileModel.getHubInfo()) {
                sterlingPartnerProfileService.deleteSterlingProfiles(oldPartnerEntity.getCustomTpName(), null,
                        Optional.ofNullable(Protocol.findProtocol(oldPartnerEntity.getTpProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol));
            } else {
                if (isNotNull(oldFtpEntityMap.get("userId"))) {
                    sterlingPartnerProfileService.deleteUser(oldFtpEntityMap.get("userId"));
                }
            }
            isUpdate = false;
        }
        pwdLastChangeDoneUpdate(remoteProfileModel, isUpdate, oldFtpEntityMap.get("pwdLastChangeDone"));
        //This profileId will be used while reading all entities from SI tables(we are avoiding dependency on profile name)
        LOGGER.info("ProfileId: {}", oldFtpEntityMap.get("profileId"));
        remoteProfileModel.setSciProfileObjectId(oldFtpEntityMap.get("profileId"));
        Map<String, String> newFtpEntityMap = mapper.convertValue(
                saveProtocol(remoteProfileModel, protocol, parentPrimaryKey, childPrimaryKey, isNotNull(oldPartnerEntity.getCustomTpName()) ? oldPartnerEntity.getCustomTpName() : oldPartnerEntity.getTpName(), isUpdate),
                new TypeReference<Map<String, String>>() {
                }
        );
        remoteProfileModel.setPemIdentifier(isNotNull(remoteProfileModel.getPemIdentifier()) ? remoteProfileModel.getPemIdentifier() : oldPartnerEntity.getPemIdentifier());
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(remoteProfileModel, partnerModel);
        PartnerEntity newPartnerEntity = partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.updatePartnerActivity(oldPartnerEntity, newPartnerEntity, oldFtpEntityMap, newFtpEntityMap, isUpdate);

    }

    public RemoteProfileModel get(String pkId) {
        RemoteFtpEntity remoteFtpEntity = remoteFtpServiceDup.get(pkId).setSubscriberType("TP");
        RemoteProfileModel remoteProfileModel =
                sterlingPartnerProfileService.get(serialize.apply(partnerService.get(pkId), remoteFtpEntity));

        //This is for SFG Profiles, when the B2Bi tables don't have Values then we are pulling the data from PCM tables and adding to model
        remoteProfileModel.setRemoteHost(isNotNull(remoteProfileModel.getRemoteHost()) ? remoteProfileModel.getRemoteHost() : remoteFtpEntity.getHostName());
        remoteProfileModel.setRemotePort(isNotNull(remoteProfileModel.getRemotePort()) ? remoteProfileModel.getRemotePort() : remoteFtpEntity.getPortNo());
        remoteProfileModel.setUserName(isNotNull(remoteProfileModel.getUserName()) ? remoteProfileModel.getUserName() : remoteFtpEntity.getUserId());
        remoteProfileModel.setInDirectory(isNotNull(remoteProfileModel.getInDirectory()) ? remoteProfileModel.getInDirectory() : remoteFtpEntity.getInDirectory());

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
        if (!processService.findByPartnerProfile(pkId).isEmpty()) {
            throw internalServerError("Unable to delete this partner, Because it has workflows..");
        }
        PartnerEntity partnerEntity = partnerService.findPartnerById(pkId);
        RemoteFtpEntity remoteFtpEntity = remoteFtpServiceDup.get(pkId);
        sterlingPartnerProfileService.delete(partnerEntity.getTpName(),
                null,
                Optional.ofNullable(Protocol.findProtocol(partnerEntity.getTpProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol),
                convertStringToBoolean(partnerEntity.getIsProtocolHubInfo()),
                deleteUser,
                remoteFtpEntity.getUserId(),
                deleteMailboxes,
                remoteFtpEntity.getInDirectory(),
                remoteFtpEntity.getOutDirectory()
        );
        remoteFtpServiceDup.delete(pkId);
        partnerService.delete(partnerEntity);
    }

    public void changeStatus(String pkId, boolean status) {
        partnerService.save(partnerService.get(pkId).setStatus(convertBooleanToString(status)));
        remoteFtpServiceDup.save(remoteFtpServiceDup.get(pkId).setIsActive(convertBooleanToString(status)));
    }

    private RemoteFtpEntity saveProtocol(RemoteProfileModel remoteProfileModel,
                                         Protocol protocol,
                                         String parentPrimaryKey,
                                         String childPrimaryKey,
                                         String oldProfileName,
                                         Boolean isUpdate) {
        remoteProfileModel.setSubscriberType(TP);
        remoteProfileModel.setCreateUserInSI(TRUE);
        /*Save into PCM Table*/
        return remoteFtpServiceDup.saveProtocol(remoteProfileModel,
                parentPrimaryKey,
                childPrimaryKey,
                remoteProfileModel.isOnlyPCM() ? "" /*Need to write Logic Here*/ : sterlingPartnerProfileService.save(remoteProfileModel, protocol, oldProfileName, isUpdate)
        );
    }

    private void duplicatePartner(String partnerId) {
        Optional<PartnerEntity> tradingPartnerEntityOptional = partnerService.find(partnerId);
        tradingPartnerEntityOptional.ifPresent(tradingPartnerEntity -> {
            throw conflict("Partner");
        });
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


    private static RemoteProfileModel apply(PartnerEntity tradingPartner, RemoteFtpEntity remoteFtpEntity) {
        RemoteProfileModel remoteProfileModel = new RemoteProfileModel();
        remoteProfileModel.setPkId(tradingPartner.getPkId())
                .setProfileName(tradingPartner.getTpName())
                .setCustomProfileName(tradingPartner.getCustomTpName())
                .setProfileId(tradingPartner.getTpId())
                .setAddressLine1(tradingPartner.getAddressLine1())
                .setPhone(tradingPartner.getPhone())
                .setAddressLine2(tradingPartner.getAddressLine2())
                .setProtocol(tradingPartner.getTpProtocol())
                .setEmailId(tradingPartner.getEmail())
                .setStatus(convertStringToBoolean(tradingPartner.getStatus()))
                .setHubInfo(convertStringToBoolean(remoteFtpEntity.getIsHubInfo()))
                .setPgpInfo(tradingPartner.getPgpInfo())
                .setPemIdentifier(tradingPartner.getPemIdentifier())
                .setOnlyPCM(isNotNull(tradingPartner.getIsOnlyPcm()) ? convertStringToBoolean(tradingPartner.getIsOnlyPcm()) : Boolean.FALSE);
        return remoteProfileModel.setUserName(remoteFtpEntity.getUserId())
                .setPassword(PRAGMA_EDGE_S)
                .setTransferType(remoteFtpEntity.getTransferType())
                .setMergeUser(convertStringToBoolean(remoteFtpEntity.getMergeUser()))
                //Start : Extra adding for Bulk uploads
                .setRemoteHost(remoteFtpEntity.getHostName())
                .setRemotePort(remoteFtpEntity.getPortNo())
                .setCertificateId(remoteFtpEntity.getCertificateId())
                .setCaCertificateNames(isNotNull(remoteFtpEntity.getCertificateId()) ?
                        Arrays.stream(remoteFtpEntity.getCertificateId().split(","))
                                .map(CommunityManagerNameModel::new)
                                .collect(Collectors.toList())
                        : new ArrayList<>())
                .setUserIdentityKey(remoteFtpEntity.getUserIdentityKey())
                .setKnownHostKey(remoteFtpEntity.getKnownHostKey())
                .setConnectionType(remoteFtpEntity.getConnectionType())
                .setEncryptionStrength(remoteFtpEntity.getEncryptionStrength())
                .setRetryInterval(remoteFtpEntity.getRetryInterval())
                .setNoOfRetries(remoteFtpEntity.getNoOfRetries())
                .setUseCCC(convertStringToBoolean(remoteFtpEntity.getUseCcc()))
                .setUseImplicitSSL(convertIMPSSLStrToBool.apply(remoteFtpEntity.getUseImplicitSsl()))
                //END : Extra adding for Bulk uploads
                .setInDirectory(remoteFtpEntity.getInDirectory())
                .setOutDirectory(remoteFtpEntity.getOutDirectory())
                .setCreateDirectoryInSI(TRUE)
                .setFileType(remoteFtpEntity.getFileType())
                .setDeleteAfterCollection(convertStringToBoolean(remoteFtpEntity.getDeleteAfterCollection()))
                .setAdapterName(remoteFtpEntity.getAdapterName())
                .setPoolingInterval(remoteFtpEntity.getPoolingIntervalMins())
                .setSubscriberType("TP")
                .setAuthorizedUserKeys(isNotNull(remoteFtpEntity.getAuthUserKeys()) ?
                        Arrays.stream(remoteFtpEntity.getAuthUserKeys().split(","))
                                .map(CommunityManagerNameModel::new)
                                .collect(Collectors.toList())
                        : new ArrayList<>())
                .setPreferredAuthenticationType(remoteFtpEntity.getPrfAuthType())
                .setConnectionType(remoteFtpEntity.getConnectionType())
                .setSciProfileObjectId(remoteFtpEntity.getProfileId());
    }

}
