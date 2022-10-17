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
import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.enums.Protocol;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.miscellaneous.UserUtilityService;
import com.pe.pcm.partner.entity.PartnerEntity;
import com.pe.pcm.partner.entity.RemotePartnerStagingEntity;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.RemoteFtpService;
import com.pe.pcm.protocol.RemoteProfileModel;
import com.pe.pcm.protocol.remoteftp.entity.RemoteFtpEntity;
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
import static com.pe.pcm.utils.PCMConstants.*;

/**
 * @author Kiran Reddy.
 */

@Service
public class RemoteFtpPartnerService {

    private final Logger logger = LoggerFactory.getLogger(RemoteFtpPartnerService.class);

    private final PartnerService partnerService;
    private final RemoteFtpService remoteFtpService;
    private final ActivityHistoryService activityHistoryService;
    private final ProcessService processService;
    private final ManageProtocolService manageProtocolService;
    private final UserUtilityService userUtilityService;
    private final ObjectMapper mapper = new ObjectMapper();
    private final BiFunction<PartnerEntity, RemoteFtpEntity, RemoteProfileModel> serialize = RemoteFtpPartnerService::apply;

    @Autowired
    public RemoteFtpPartnerService(PartnerService partnerService,
                                   RemoteFtpService remoteFtpService,
                                   ActivityHistoryService activityHistoryService,
                                   ProcessService processService,
                                   ManageProtocolService manageProtocolService,
                                   UserUtilityService userUtilityService) {
        this.partnerService = partnerService;
        this.remoteFtpService = remoteFtpService;
        this.activityHistoryService = activityHistoryService;
        this.processService = processService;
        this.manageProtocolService = manageProtocolService;
        this.userUtilityService = userUtilityService;
    }

    private void duplicatePartner(String partnerId) {
        Optional<PartnerEntity> tradingPartnerEntityOptional = partnerService.find(partnerId);
        tradingPartnerEntityOptional.ifPresent(tradingPartnerEntity -> {
            throw conflict("Partner");
        });
    }

    @Transactional
    public String save(RemoteProfileModel remoteProfileModel) {
        logger.info("In SFG Profile save() Method.");
        if (!Optional.ofNullable(Protocol.findProtocol(remoteProfileModel.getProtocol())).isPresent()) {
            throw unknownProtocol();
        }
        remoteProfileModel.setEmailId(formatMail.apply(remoteProfileModel.getEmailId()));
        applyRemoteProfileModelDefaultValues.apply(remoteProfileModel);
        duplicatePartner(remoteProfileModel.getProfileId());
        String parentPrimaryKey = getPrimaryKey.apply(TP_PKEY_PRE_APPEND, TP_PKEY_RANDOM_COUNT);
        String childPrimaryKey = getChildPrimaryKey(remoteProfileModel.getProtocol());
        pwdLastChangeDoneUpdate(remoteProfileModel, false, null);
        saveProtocol(remoteProfileModel, parentPrimaryKey, childPrimaryKey);
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(remoteProfileModel, partnerModel);
        partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.savePartnerActivity(parentPrimaryKey, "Trading Partner created.");
        userUtilityService.addPartnerToCurrentUser(parentPrimaryKey, TP);
        logger.info("Completed SFG Profile save() Method.");
        return parentPrimaryKey;
    }

    private void saveProtocol(RemoteProfileModel remoteProfileModel, String parentPrimaryKey, String childPrimaryKey) {
        remoteFtpService.saveProtocol(remoteProfileModel, parentPrimaryKey, childPrimaryKey, false, "TP", null);
    }

    @Transactional
    public void changeStatus(String pkId, boolean status) {
        PartnerEntity newPartnerEntity = partnerService.get(pkId);
        PartnerEntity oldPartnerEntity = SerializationUtils.clone(newPartnerEntity);
        newPartnerEntity.setStatus(convertBooleanToString(status));
        RemoteFtpEntity remoteFtpEntity = remoteFtpService.get(pkId);
        Map<String, String> oldEntityMap = getEntityMap(remoteFtpEntity);
        remoteFtpEntity.setIsActive(convertBooleanToString(status));
        Map<String, String> newEntityMap = getEntityMap(remoteFtpEntity);
        activityHistoryService.updatePartnerActivity(oldPartnerEntity,
                newPartnerEntity, oldEntityMap, newEntityMap, true);
    }

    @Transactional
    public void update(RemoteProfileModel remoteProfileModel) {
        logger.info("In SFG Profile update() Method.");
        PartnerEntity oldPartnerEntity = SerializationUtils.clone(partnerService.get(remoteProfileModel.getPkId()));
        if (!oldPartnerEntity.getTpId().equalsIgnoreCase(remoteProfileModel.getProfileId())) {
            duplicatePartner(remoteProfileModel.getProfileId());
        }
        remoteProfileModel.setEmailId(formatMail.apply(remoteProfileModel.getEmailId()));
        applyRemoteProfileModelDefaultValues.apply(remoteProfileModel);
        String parentPrimaryKey = oldPartnerEntity.getPkId();
        String childPrimaryKey = oldPartnerEntity.getPartnerProtocolRef();
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(oldPartnerEntity.getTpProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
        Map<String, String> oldHttpEntityMap = new LinkedHashMap<>();
        RemoteFtpEntity remoteFtpEntity = new RemoteFtpEntity();
        boolean isUpdate = true;
        if (!protocol.getProtocol().equalsIgnoreCase(remoteProfileModel.getProtocol())) {
            manageProtocolService.deleteProtocol(oldPartnerEntity.getTpProtocol(), oldPartnerEntity.getPkId());
            childPrimaryKey = getChildPrimaryKey(remoteProfileModel.getProtocol());
            isUpdate = false;
        } else {
            remoteFtpEntity = remoteFtpService.get(parentPrimaryKey);
            oldHttpEntityMap = mapper.convertValue(remoteFtpEntity,
                    new TypeReference<Map<String, String>>() {
                    });
        }
        if (!convertBooleanToString(remoteProfileModel.getHubInfo()).equalsIgnoreCase(oldPartnerEntity.getIsProtocolHubInfo())) {
            if (!remoteProfileModel.getHubInfo()) {
                isUpdate = false;
            }
        }
        pwdLastChangeDoneUpdate(remoteProfileModel, isUpdate,
                isNotNull(remoteFtpEntity.getPwdLastChangeDone()) ? remoteFtpEntity.getPwdLastChangeDone().toString() : "");
        remoteProfileModel.setPemIdentifier(isNotNull(remoteProfileModel.getPemIdentifier())
                ? remoteProfileModel.getPemIdentifier() : oldPartnerEntity.getPemIdentifier());
        logger.info("isUpdate : {}", isUpdate);
        Map<String, String> newRemoteFtpEntityMap = mapper.convertValue(remoteFtpService.saveProtocol(remoteProfileModel, parentPrimaryKey, childPrimaryKey,
                isUpdate, "TP", isNotNull(oldPartnerEntity.getCustomTpName()) ? oldPartnerEntity.getCustomTpName() : oldPartnerEntity.getTpName()), new TypeReference<Map<String, String>>() {
        });
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(remoteProfileModel, partnerModel);
        PartnerEntity newPartnerEntity = partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.updatePartnerActivity(oldPartnerEntity,
                newPartnerEntity, oldHttpEntityMap, newRemoteFtpEntityMap, isUpdate);
        logger.info("Completed SFG Profile update() Method.");
    }

    @Transactional
    public RemoteProfileModel get(String pkId) {

        RemoteProfileModel remoteProfileModel = serialize.apply(partnerService.get(pkId), remoteFtpService.get(pkId));

        if (!remoteProfileModel.getHubInfo()) {
            return remoteFtpService.getRemoteFtp(remoteProfileModel);
        } else {
            remoteFtpService.setB2bUserParams(remoteProfileModel);
        }

        return remoteProfileModel;
    }

    @Transactional
    public void delete(String pkId, boolean deleteRemoteProfile, boolean deleteUser, boolean deleteMailboxes, boolean deleteCert) {
        logger.info("pkId: {}, deleteRemoteProfile: {}, deleteUser: {}, deleteMailboxes: {}, deleteCert: {}", pkId, deleteRemoteProfile, deleteUser, deleteMailboxes, deleteCert);
        if (!processService.findByPartnerProfile(pkId).isEmpty()) {
            throw internalServerError("Unable to delete this partner, Because it has workflow.");
        }
        PartnerEntity partnerEntity = partnerService.findPartnerById(pkId);
        remoteFtpService.deleteUserMailboxCertificates(serialize.apply(partnerEntity, remoteFtpService.get(pkId)), deleteUser, deleteMailboxes, deleteCert);
        logger.info("Partner HubInfo : {}", convertStringToBoolean(partnerEntity.getIsProtocolHubInfo()));
        if (!convertStringToBoolean(partnerEntity.getIsProtocolHubInfo())) {
            remoteFtpService.deleteProtocol(partnerEntity.getPkId(),
                    isNotNull(partnerEntity.getCustomTpName()) ? partnerEntity.getCustomTpName() : partnerEntity.getTpName(),
                    partnerEntity.getTpProtocol(), deleteRemoteProfile);
        } else {
            logger.info("Delete Profile in CM only");
            remoteFtpService.delete(partnerEntity.getPkId());
        }
        partnerService.delete(partnerEntity);
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

        if (remoteProfileModel.getHubInfo()) {
            remoteProfileModel.setPreferredAuthenticationType(isNotNull(remoteFtpEntity.getPrfAuthType()) ? remoteFtpEntity.getPrfAuthType() : "PASSWORD");
        }

        return remoteProfileModel.setUserName(remoteFtpEntity.getUserId())
                .setPassword(remoteFtpEntity.getPassword())
                .setTransferType(remoteFtpEntity.getTransferType())
                //Start : Extra adding for Bulk uploads
                .setRemoteHost(remoteFtpEntity.getHostName())
                .setRemotePort(remoteFtpEntity.getPortNo())
                .setCertificateId(remoteFtpEntity.getCertificateId())
                .setCaCertificateName(remoteFtpEntity.getCaCertId()) // added
                .setUserIdentityKey(remoteFtpEntity.getUserIdentityKey())
                .setKnownHostKey(remoteFtpEntity.getKnownHostKey())
                .setConnectionType(remoteFtpEntity.getConnectionType())
                .setEncryptionStrength(remoteFtpEntity.getEncryptionStrength())
                .setRetryInterval(remoteFtpEntity.getRetryInterval())
                .setNoOfRetries(remoteFtpEntity.getNoOfRetries())
                .setUseCCC(convertStringToBoolean(remoteFtpEntity.getUseCcc()))
                .setUseImplicitSSL(convertStringToBoolean(remoteFtpEntity.getUseImplicitSsl()))
                //END : Extra adding for Bulk uploads
                .setInDirectory(remoteFtpEntity.getInDirectory())
                .setOutDirectory(remoteFtpEntity.getOutDirectory())
                .setFileType(remoteFtpEntity.getFileType())
                .setDeleteAfterCollection(convertStringToBoolean(remoteFtpEntity.getDeleteAfterCollection()))
                .setAdapterName(remoteFtpEntity.getAdapterName())
                .setPoolingInterval(remoteFtpEntity.getPoolingIntervalMins())
                .setSubscriberType("TP")
                .setAuthorizedUserKeys(isNotNull(remoteFtpEntity.getAuthUserKeys()) ?
                        Arrays.stream(remoteFtpEntity.getAuthUserKeys().split(","))
                                .map(CommunityManagerNameModel::new)
                                .collect(Collectors.toList())
                        : new ArrayList<>());
    }

    @Transactional
    public void saveRemoteStagingProfile(RemotePartnerStagingEntity remotePartnerStagingEntity, RemoteFtpEntity remoteFtpEntity) {
        PartnerEntity partnerEntity = new PartnerEntity();
        BeanUtils.copyProperties(remotePartnerStagingEntity, partnerEntity);
        RemoteProfileModel remoteProfileModel = serialize.apply(partnerEntity, remoteFtpEntity);
        remoteFtpService.save(remoteFtpEntity.setProfileId(remoteFtpService.saveRemoteFtpProfile(remoteProfileModel, false, "TP", null)));
        activityHistoryService.savePartnerActivity(remotePartnerStagingEntity.getPkId(), "Trading Partner created.");
    }

    List<RemoteProfileModel> findAllByIsHubInfoAndPrfAuthTypeLike(Boolean isHubInfo, String prfAuthType) {
        List<RemoteProfileModel> remoteProfileModels = new ArrayList<>();
        List<RemoteFtpEntity> remoteFtpEntities = remoteFtpService.findAllByIsHubInfoAndPrfAuthTypeLike(convertBooleanToString(isHubInfo), prfAuthType).orElse(new ArrayList<>());

        if (!remoteFtpEntities.isEmpty()) {
            getPartitions(999, remoteFtpEntities.stream()
                    .map(RemoteFtpEntity::getSubscriberId)
                    .distinct()
                    .collect(Collectors.toList())
            ).forEach(pkIds -> partnerService.findAllByPkIdIn(pkIds)
                    .forEach(partnerEntity ->
                            remoteProfileModels.add(
                                    apply(partnerEntity, remoteFtpEntities.stream()
                                            .filter(remoteFtpEntity -> remoteFtpEntity.getSubscriberId().equals(partnerEntity.getPkId()))
                                            .collect(Collectors.toList())
                                            .get(0)
                                    )
                            )
                    )
            );
        }
        return remoteProfileModels;
    }

    private void pwdLastChangeDoneUpdate(RemoteProfileModel remoteProfileModel, boolean isUpdate, String lastChangeDone) {
        if (!remoteProfileModel.getHubInfo()) {
            if (isUpdate) {
                if (isNotNull(remoteProfileModel.getPassword())) {
                    if (remoteProfileModel.getPassword().equals(PCMConstants.PRAGMA_EDGE_S)) {
                        if (isNotNull(lastChangeDone)) {
                            remoteProfileModel.setPwdLastChangeDone(Timestamp.valueOf(lastChangeDone));
                        } else {
                            remoteProfileModel.setPwdLastChangeDone(new Timestamp(System.currentTimeMillis()));
                        }
                    } else {
                        remoteProfileModel.setPwdLastChangeDone(new Timestamp(System.currentTimeMillis()));
                    }
                } else {
                    if (isNotNull(lastChangeDone)) {
                        remoteProfileModel.setPwdLastChangeDone(Timestamp.valueOf(lastChangeDone));
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
