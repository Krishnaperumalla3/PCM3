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
import com.pe.pcm.b2b.B2BCdNodeService;
import com.pe.pcm.b2b.other.CaCertGetModel;
import com.pe.pcm.b2b.other.CipherSuiteNameGetModel;
import com.pe.pcm.b2b.protocol.RemoteConnectDirectProfile;
import com.pe.pcm.enums.Protocol;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.miscellaneous.UserUtilityService;
import com.pe.pcm.partner.entity.PartnerEntity;
import com.pe.pcm.pem.b2bproxy.producerconfiguration.ProducerCdConfiguration;
import com.pe.pcm.pem.b2bproxy.consumerconfiguration.ConsumerCdConfiguration;
import com.pe.pcm.protocol.CdMainFrameModel;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.RemoteCdModel;
import com.pe.pcm.protocol.RemoteConnectDirectService;
import com.pe.pcm.protocol.remotecd.entity.RemoteConnectDirectEntity;
import com.pe.pcm.utils.KeyGeneratorUtil;
import com.pe.pcm.utils.PCMConstants;
import com.pe.pcm.workflow.ProcessService;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static com.pe.pcm.exception.GlobalExceptionHandler.*;
import static com.pe.pcm.utils.CommonFunctions.convertStringToBoolean;
import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.KeyGeneratorUtil.getChildPrimaryKey;
import static com.pe.pcm.utils.PCMConstants.TP;
import static java.lang.Boolean.*;

/**
 * @author Kiran Reddy.
 */
@Service
public class RemoteConnectDirectPartnerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RemoteConnectDirectPartnerService.class);

    private final RemoteConnectDirectService remoteConnectDirectService;
    private final B2BApiService b2BApiService;
    private final PartnerService partnerService;
    private final ProcessService processService;
    private final UserUtilityService userUtilityService;
    private final ManageProtocolService manageProtocolService;
    private final ActivityHistoryService activityHistoryService;
    private final B2BCdNodeService b2BCdNodeService;
    private final String community;

    private final ObjectMapper mapper = new ObjectMapper();

    private final BiFunction<PartnerEntity, RemoteConnectDirectEntity, RemoteCdModel> serialize = RemoteConnectDirectPartnerService::apply;

    @Autowired
    public RemoteConnectDirectPartnerService(RemoteConnectDirectService remoteConnectDirectService,
                                             B2BApiService b2BApiService,
                                             PartnerService partnerService,
                                             ProcessService processService,
                                             UserUtilityService userUtilityService,
                                             ManageProtocolService manageProtocolService,
                                             ActivityHistoryService activityHistoryService,
                                             B2BCdNodeService b2BCdNodeService,
                                             @Value("${sterling-b2bi.b2bi-api.b2bi-sfg-api.community-name}") String community) {
        this.remoteConnectDirectService = remoteConnectDirectService;
        this.b2BApiService = b2BApiService;
        this.partnerService = partnerService;
        this.processService = processService;
        this.userUtilityService = userUtilityService;
        this.manageProtocolService = manageProtocolService;
        this.activityHistoryService = activityHistoryService;
        this.b2BCdNodeService = b2BCdNodeService;
        this.community = community;
    }

    private static RemoteCdModel apply(PartnerEntity tradingPartner, RemoteConnectDirectEntity remoteConnectDirectEntity) {
        RemoteCdModel remoteCdModel = new RemoteCdModel();
        remoteCdModel.setPkId(tradingPartner.getPkId())
                .setProfileName(tradingPartner.getTpName())
                .setProfileId(tradingPartner.getTpId())
                .setAddressLine1(tradingPartner.getAddressLine1())
                .setAddressLine2(tradingPartner.getAddressLine2())
                .setProtocol(tradingPartner.getTpProtocol())
                .setEmailId(tradingPartner.getEmail())
                .setPhone(tradingPartner.getPhone())
                .setStatus(convertStringToBoolean(tradingPartner.getStatus()))
                .setHubInfo(convertStringToBoolean(remoteConnectDirectEntity.getIsHubInfo()))
                .setPemIdentifier(tradingPartner.getPemIdentifier())
                .setPgpInfo(tradingPartner.getPgpInfo());

        remoteCdModel.setLocalNodeName(remoteConnectDirectEntity.getLocalNodeName())
                .setsNodeId(remoteConnectDirectEntity.getSnodeId())
                .setsNodeIdPassword(remoteConnectDirectEntity.getSnodeIdPassword())
                .setNodeName(remoteConnectDirectEntity.getNodeName())
                .setOperatingSystem(remoteConnectDirectEntity.getOperatingSystem())
                .setDirectory(remoteConnectDirectEntity.getDirectory())
                .setHostName(remoteConnectDirectEntity.getHostName())
                .setPort(Integer.parseInt(remoteConnectDirectEntity.getPort()))
                .setCopySisOpts(remoteConnectDirectEntity.getCopySisOpts())
                .setCheckPoint(remoteConnectDirectEntity.getCheckPoint())
                .setCompressionLevel(remoteConnectDirectEntity.getCompressionLevel())
                .setDisposition(remoteConnectDirectEntity.getDisposition())
                .setSubmit(remoteConnectDirectEntity.getSubmit())
                .setSecure(convertStringToBoolean(remoteConnectDirectEntity.getSecure()))
                .setRunJob(remoteConnectDirectEntity.getRunJob())
                .setRunTask(remoteConnectDirectEntity.getRunTask())
                .setSecurityProtocol(remoteConnectDirectEntity.getSecurityProtocol())
                .setPoolingInterval(remoteConnectDirectEntity.getPoolingIntervalMins())
                .setAdapterName(remoteConnectDirectEntity.getAdapterName())
                .setRemoteFileName(remoteConnectDirectEntity.getRemoteFileName())
                .setSSP(convertStringToBoolean(remoteConnectDirectEntity.getIsSsp()))
                .setOutDirectory(remoteConnectDirectEntity.getOutDirectory())
                .setInboundConnectionType(isNotNull(remoteConnectDirectEntity.getInboundConnectionType()) ? convertStringToBoolean(remoteConnectDirectEntity.getInboundConnectionType()) : FALSE)
                .setOutboundConnectionType(isNotNull(remoteConnectDirectEntity.getOutboundConnectionType()) ? convertStringToBoolean(remoteConnectDirectEntity.getOutboundConnectionType()) : FALSE);

        if (isNotNull(remoteConnectDirectEntity.getCaCertificateName())) {
            remoteCdModel.setCaCertName(Arrays.stream(remoteConnectDirectEntity.getCaCertificateName().split(",")).map(CaCertGetModel::new).collect(Collectors.toList()));
        }
        if (isNotNull(remoteConnectDirectEntity.getCipherSuits())) {
            remoteCdModel.setCipherSuits(Arrays.stream(remoteConnectDirectEntity.getCipherSuits().split(",")).map(CipherSuiteNameGetModel::new).collect(Collectors.toList()));
        }
        remoteCdModel.setCdMainFrameModel(
                new CdMainFrameModel().setDcbParam(remoteConnectDirectEntity.getDcbParam())
                        .setDnsName(remoteConnectDirectEntity.getDnsName())
                        .setSpace(remoteConnectDirectEntity.getSpace())
                        .setStorageClass(remoteConnectDirectEntity.getStorageClass())
                        .setUnit(remoteConnectDirectEntity.getUnit())
        );

        return remoteCdModel;
    }

    private void duplicatePartner(String partnerId) {
        partnerService.find(partnerId).ifPresent(tradingPartnerEntity -> {
            throw conflict("Partner");
        });
    }

    @Transactional
    public String save(RemoteCdModel remoteCdModel) {
        if (!Optional.ofNullable(Protocol.findProtocol(remoteCdModel.getProtocol().trim())).isPresent()) {
            throw unknownProtocol();
        }
        remoteCdModel.setHubInfo(isNotNull(remoteCdModel.getHubInfo()) ? remoteCdModel.getHubInfo() : FALSE);
        duplicatePartner(remoteCdModel.getProfileId());
        String parentPrimaryKey = KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.TP_PKEY_PRE_APPEND,
                PCMConstants.TP_PKEY_RANDOM_COUNT);
        String childPrimaryKey = KeyGeneratorUtil.getChildPrimaryKey(remoteCdModel.getProtocol());
        remoteConnectDirectService.saveProtocol(remoteCdModel, parentPrimaryKey, childPrimaryKey, TP);
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(remoteCdModel, partnerModel);
        partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
        boolean isProfileCreated = false;
        RemoteConnectDirectProfile remoteConnectDirectProfile = new RemoteConnectDirectProfile(remoteCdModel, false, community);
        if (!remoteCdModel.isOnlyPCM()) {
            if (isNotNull(remoteCdModel.getIsSIProfile())) {
                if (remoteConnectDirectProfile.getIsInitiatingConsumer() || remoteConnectDirectProfile.getIsListeningConsumer()) {
                    remoteConnectDirectProfile.setConsumerCdConfiguration(new ConsumerCdConfiguration(remoteCdModel));
                }
                if (remoteConnectDirectProfile.getIsInitiatingProducer() || remoteConnectDirectProfile.getIsListeningProducer()) {
                    remoteConnectDirectProfile.setProducerCdConfiguration(new ProducerCdConfiguration(remoteCdModel));
                }
                b2BApiService.createRemoteCDProfile(remoteConnectDirectProfile);
                isProfileCreated = true;
            }
            if (isNotNull(remoteCdModel.getInboundConnectionType()) && remoteCdModel.getInboundConnectionType()) {
                b2BApiService.createMailBoxInSI(true, remoteCdModel.getDirectory(), null);
            }
            if (isNotNull(remoteCdModel.getOutboundConnectionType()) && remoteCdModel.getOutboundConnectionType()) {
                b2BApiService.createMailBoxInSI(true, null, remoteCdModel.getOutDirectory());
            }
            try {
                b2BCdNodeService.createNodeInSI(remoteCdModel);
            } catch (CommunityManagerServiceException ex) {
                if (ex.getStatusCode() >= 300 && isProfileCreated) {
                    b2BApiService.deleteRemoteCDProfile(remoteConnectDirectProfile.getPartnerName());
                    throw new CommunityManagerServiceException(ex.getStatusCode(), ex.getErrorMessage());
                }
            }

        }
        activityHistoryService.savePartnerActivity(parentPrimaryKey, "Trading Partner created.");
        userUtilityService.addPartnerToCurrentUser(parentPrimaryKey, TP);
        return parentPrimaryKey;
    }

    @Transactional
    public void update(RemoteCdModel remoteCdModel) {
        PartnerEntity oldPartnerEntity = SerializationUtils.clone(partnerService.get(remoteCdModel.getPkId()));
        if (!oldPartnerEntity.getTpId().equalsIgnoreCase(remoteCdModel.getProfileId())) {
            duplicatePartner(remoteCdModel.getProfileId());
        }
        String parentPrimaryKey = oldPartnerEntity.getPkId();
        String childPrimaryKey = oldPartnerEntity.getPartnerProtocolRef();
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(remoteCdModel.getProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
        Map<String, String> oldCdEntityMap = new LinkedHashMap<>();
        boolean isUpdate = true;
        if (!protocol.getProtocol().trim().equalsIgnoreCase(oldPartnerEntity.getTpProtocol().trim())) {
            LOGGER.info("Protocol has been changes old: {}, new: {}", protocol.getProtocol(), oldPartnerEntity.getTpProtocol());
            LOGGER.info("Deleting old protocol");
            manageProtocolService.deleteProtocol(oldPartnerEntity.getTpProtocol(), oldPartnerEntity.getPkId());
            childPrimaryKey = getChildPrimaryKey(remoteCdModel.getProtocol());
            isUpdate = false;
        } else {
            oldCdEntityMap = mapper.convertValue(remoteConnectDirectService.get(parentPrimaryKey),
                    new TypeReference<Map<String, String>>() {
                    });
        }

        Map<String, String> newCdEntityMap = mapper.convertValue(remoteConnectDirectService.saveProtocol(remoteCdModel, parentPrimaryKey, childPrimaryKey, "TP"),
                new TypeReference<Map<String, String>>() {
                });
        remoteCdModel.setPemIdentifier(isNotNull(remoteCdModel.getPemIdentifier())
                ? remoteCdModel.getPemIdentifier() : oldPartnerEntity.getPemIdentifier());
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(remoteCdModel, partnerModel);
        PartnerEntity newPartnerEntity = partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
        if (isUpdate) {
            if (isNotNull(remoteCdModel.getIsSIProfile()) && remoteCdModel.getIsSIProfile()) {
                RemoteConnectDirectProfile remoteConnectDirectProfile = new RemoteConnectDirectProfile(remoteCdModel, isUpdate, community);
                if (remoteConnectDirectProfile.getIsInitiatingConsumer() || remoteConnectDirectProfile.getIsListeningConsumer()) {
                    remoteConnectDirectProfile.setConsumerCdConfiguration(new ConsumerCdConfiguration(remoteCdModel));
                }
                if (remoteConnectDirectProfile.getIsInitiatingProducer() || remoteConnectDirectProfile.getIsListeningProducer()) {
                    remoteConnectDirectProfile.setProducerCdConfiguration(new ProducerCdConfiguration(remoteCdModel));
                }
                b2BApiService.updateRemoteCDProfile(remoteConnectDirectProfile, isNotNull(oldPartnerEntity.getCustomTpName()) ? oldPartnerEntity.getCustomTpName() : oldPartnerEntity.getTpName());
            }
            try {
                if (remoteCdModel.getNodeName().equalsIgnoreCase(oldCdEntityMap.get("nodeName"))) {
                    b2BCdNodeService.updateNodeInSI(remoteCdModel, oldCdEntityMap.get("nodeName"));
                } else {
                    b2BCdNodeService.createNodeInSI(remoteCdModel);
                }
            } catch (CommunityManagerServiceException e) {
                if (e.getErrorMessage().contains("API000464")) {
                    b2BCdNodeService.createNodeInSI(remoteCdModel);
                } else {
                    throw internalServerError(e.getErrorMessage());
                }
            }

        } else {
            b2BCdNodeService.createNodeInSI(remoteCdModel);
        }

        activityHistoryService.updatePartnerActivity(oldPartnerEntity, newPartnerEntity, oldCdEntityMap, newCdEntityMap, isUpdate);

        if (isNotNull(remoteCdModel.getInboundConnectionType()) && remoteCdModel.getInboundConnectionType()) {
            b2BApiService.createMailBoxInSI(true, remoteCdModel.getDirectory(), null);
        }
        if (isNotNull(remoteCdModel.getOutboundConnectionType()) && remoteCdModel.getOutboundConnectionType()) {
            b2BApiService.createMailBoxInSI(true, null, remoteCdModel.getOutDirectory());
        }

    }

    public RemoteCdModel get(String pkId) {
        PartnerEntity partnerEntity = partnerService.get(pkId);
        RemoteConnectDirectEntity remoteConnectDirectEntity = remoteConnectDirectService.get(pkId);
        RemoteCdModel remoteCdModel = serialize.apply(partnerEntity, remoteConnectDirectEntity);
        if (!remoteCdModel.isSSP()) {
            RemoteCdModel cdNodeModel = b2BCdNodeService.getNodeInSI(remoteCdModel);
            remoteCdModel.setNodeName(cdNodeModel.getNodeName());
            remoteCdModel.setSecure(cdNodeModel.isSecure());
            remoteCdModel.setPort(cdNodeModel.getPort());
            remoteCdModel.setHostName(cdNodeModel.getHostName());
            //remoteCdModel.setSecurityProtocol(cdNodeModel.getSecurityProtocol())
            remoteCdModel.setSystemCertificate(cdNodeModel.getSystemCertificate());
            remoteCdModel.setCipherSuits(cdNodeModel.getCipherSuits());
            remoteCdModel.setCaCertName(cdNodeModel.getCaCertName());
        }
        return remoteCdModel;
    }

    @Transactional
    public void delete(String pkId, Boolean isDeleteInSI) {
        if (!processService.findByPartnerProfile(pkId).isEmpty()) {
            throw internalServerError("Unable to delete this partner, Because it has workflow.");
        }
        PartnerEntity partnerEntity = partnerService.findPartnerById(pkId);
        if (isDeleteInSI) {
            try {
                b2BApiService.deleteRemoteCDProfile(isNotNull(partnerEntity.getCustomTpName()) ? partnerEntity.getCustomTpName() : partnerEntity.getTpName());
            } catch (Exception e) {
                //Do nothing
            }
            b2BCdNodeService.deleteNodeInSI(remoteConnectDirectService.get(pkId).getNodeName());
        }
        partnerService.delete(partnerEntity);
        remoteConnectDirectService.delete(pkId);
    }
}
