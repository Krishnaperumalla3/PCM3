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
import com.pe.pcm.b2b.B2BApiService;
import com.pe.pcm.b2b.B2BCdNodeService;
import com.pe.pcm.b2b.other.CaCertGetModel;
import com.pe.pcm.b2b.other.CipherSuiteNameGetModel;
import com.pe.pcm.b2b.protocol.RemoteConnectDirectProfile;
import com.pe.pcm.enums.Protocol;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.pem.b2bproxy.consumerconfiguration.ConsumerCdConfiguration;
import com.pe.pcm.pem.b2bproxy.producerconfiguration.ProducerCdConfiguration;
import com.pe.pcm.protocol.CdMainFrameModel;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.RemoteCdModel;
import com.pe.pcm.protocol.RemoteConnectDirectService;
import com.pe.pcm.protocol.remotecd.entity.RemoteConnectDirectEntity;
import com.pe.pcm.utils.KeyGeneratorUtil;
import com.pe.pcm.utils.PCMConstants;
import com.pe.pcm.workflow.ProcessService;
import org.apache.commons.lang3.SerializationUtils;
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
import static java.lang.Boolean.FALSE;

/**
 * @author Chenchu Kiran Reddy.
 */
@Service
public class RemoteConnectDirectApplicationService {

    private final RemoteConnectDirectService remoteConnectDirectService;

    private final ActivityHistoryService activityHistoryService;

    private final ObjectMapper mapper = new ObjectMapper();

    private final B2BApiService b2BApiService;

    private final ApplicationService applicationService;

    private final ProcessService processService;

    private final ManageProtocolService manageProtocolService;

    private final B2BCdNodeService b2BCdNodeService;

    private final String community;

    private final BiFunction<ApplicationEntity, RemoteConnectDirectEntity, RemoteCdModel> serialize = RemoteConnectDirectApplicationService::apply;

    @Autowired
    public RemoteConnectDirectApplicationService(RemoteConnectDirectService remoteConnectDirectService, ActivityHistoryService activityHistoryService,
                                                 B2BApiService b2BApiService, ApplicationService applicationService, ProcessService processService,
                                                 ManageProtocolService manageProtocolService, B2BCdNodeService b2BCdNodeService,
                                                 @Value("${sterling-b2bi.b2bi-api.b2bi-sfg-api.community-name}") String community) {
        this.remoteConnectDirectService = remoteConnectDirectService;
        this.activityHistoryService = activityHistoryService;
        this.b2BApiService = b2BApiService;
        this.applicationService = applicationService;
        this.processService = processService;
        this.manageProtocolService = manageProtocolService;
        this.b2BCdNodeService = b2BCdNodeService;
        this.community = community;
    }

    private static RemoteCdModel apply(ApplicationEntity applicationEntity, RemoteConnectDirectEntity remoteConnectDirectEntity) {

        RemoteCdModel remoteCdModel = new RemoteCdModel();
        remoteCdModel.setPkId(applicationEntity.getPkId());
        remoteCdModel.setProfileName(applicationEntity.getApplicationName());
        remoteCdModel.setProfileId(applicationEntity.getApplicationId());
        remoteCdModel.setEmailId(applicationEntity.getEmailId());
        remoteCdModel.setPhone(applicationEntity.getPhone());
        remoteCdModel.setPgpInfo(applicationEntity.getPgpInfo());
        remoteCdModel.setProtocol(applicationEntity.getAppIntegrationProtocol());
        remoteCdModel.setStatus(convertStringToBoolean(applicationEntity.getAppIsActive()));
        remoteCdModel.setHubInfo(convertStringToBoolean(remoteConnectDirectEntity.getIsHubInfo()));
        remoteCdModel.setLocalNodeName(remoteConnectDirectEntity.getLocalNodeName());
        remoteCdModel.setsNodeId(remoteConnectDirectEntity.getSnodeId());
        remoteCdModel.setSSP(convertStringToBoolean(remoteConnectDirectEntity.getIsSsp()));
        remoteCdModel.setsNodeIdPassword(remoteConnectDirectEntity.getSnodeIdPassword());
        remoteCdModel.setNodeName(remoteConnectDirectEntity.getNodeName());
        remoteCdModel.setOperatingSystem(remoteConnectDirectEntity.getOperatingSystem());
        remoteCdModel.setDirectory(remoteConnectDirectEntity.getDirectory());
        remoteCdModel.setHostName(remoteConnectDirectEntity.getHostName());
        remoteCdModel.setPort(Integer.parseInt(remoteConnectDirectEntity.getPort()));
        remoteCdModel.setCopySisOpts(remoteConnectDirectEntity.getCopySisOpts());
        remoteCdModel.setCheckPoint(remoteConnectDirectEntity.getCheckPoint());
        remoteCdModel.setCompressionLevel(remoteConnectDirectEntity.getCompressionLevel());
        remoteCdModel.setDisposition(remoteConnectDirectEntity.getDisposition());
        remoteCdModel.setSubmit(remoteConnectDirectEntity.getSubmit());
        remoteCdModel.setSecure(convertStringToBoolean(remoteConnectDirectEntity.getSecure()));
        remoteCdModel.setSecurityProtocol(remoteConnectDirectEntity.getSecurityProtocol());
        remoteCdModel.setRunJob(remoteConnectDirectEntity.getRunJob());
        remoteCdModel.setRunTask(remoteConnectDirectEntity.getRunTask());
        remoteCdModel.setOutDirectory(remoteConnectDirectEntity.getOutDirectory());
        remoteCdModel.setPemIdentifier(applicationEntity.getPemIdentifier());
        remoteCdModel.setRemoteFileName(remoteConnectDirectEntity.getRemoteFileName());
        remoteCdModel.setInboundConnectionType(isNotNull(remoteConnectDirectEntity.getInboundConnectionType()) ? convertStringToBoolean(remoteConnectDirectEntity.getInboundConnectionType()) : FALSE);
        remoteCdModel.setOutboundConnectionType(isNotNull(remoteConnectDirectEntity.getOutboundConnectionType()) ? convertStringToBoolean(remoteConnectDirectEntity.getOutboundConnectionType()) : FALSE);
        if (isNotNull(remoteConnectDirectEntity.getCipherSuits())) {
            remoteCdModel.setCipherSuits(Arrays.stream(remoteConnectDirectEntity.getCipherSuits().split(",")).map(CipherSuiteNameGetModel::new).collect(Collectors.toList()));
        }
        if (isNotNull(remoteConnectDirectEntity.getCaCertificateName())) {
            remoteCdModel.setCaCertName(Arrays.stream(remoteConnectDirectEntity.getCaCertificateName().split(",")).map(CaCertGetModel::new).collect(Collectors.toList()));
        }

        remoteCdModel.setCdMainFrameModel(
                new CdMainFrameModel().setDcbParam(remoteConnectDirectEntity.getDcbParam())
                        .setDnsName(remoteConnectDirectEntity.getDnsName())
                        .setSpace(remoteConnectDirectEntity.getSpace())
                        .setUnit(remoteConnectDirectEntity.getUnit())
                        .setStorageClass(remoteConnectDirectEntity.getStorageClass())
        );
        remoteCdModel.setPoolingInterval(remoteConnectDirectEntity.getPoolingIntervalMins());
        remoteCdModel.setAdapterName(remoteConnectDirectEntity.getAdapterName());
        return remoteCdModel;
    }

    private void duplicateApplication(String applicationId) {
        applicationService.find(applicationId).ifPresent(applicationEntity -> {
            throw conflict("Application");
        });
    }

    @Transactional
    public String save(RemoteCdModel remoteCdModel) {
        if (!Optional.ofNullable(Protocol.findProtocol(remoteCdModel.getProtocol())).isPresent()) {
            throw unknownProtocol();
        }
        remoteCdModel.setHubInfo(isNotNull(remoteCdModel.getHubInfo()) ? remoteCdModel.getHubInfo() : FALSE);
        duplicateApplication(remoteCdModel.getProfileId());
        String parentPrimaryKey = KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.APP_PKEY_PRE_APPEND,
                PCMConstants.APP_PKEY_RANDOM_COUNT);
        String childPrimaryKey = KeyGeneratorUtil.getChildPrimaryKey(remoteCdModel.getProtocol());
        remoteConnectDirectService.saveProtocol(remoteCdModel, parentPrimaryKey, childPrimaryKey, "APP");
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
        ApplicationModel applicationModel = new ApplicationModel();
        BeanUtils.copyProperties(remoteCdModel, applicationModel);
        applicationService.save(applicationModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.saveApplicationActivity(parentPrimaryKey, "Application created.");


        return parentPrimaryKey;
    }

    @Transactional
    public void update(RemoteCdModel remoteCdModel) {
        ApplicationEntity oldApplicationEntity = SerializationUtils.clone(applicationService.get(remoteCdModel.getPkId()));
        if (!oldApplicationEntity.getApplicationId().equalsIgnoreCase(remoteCdModel.getProfileId())) {
            duplicateApplication(remoteCdModel.getProfileId());
        }
        String parentPrimaryKey = oldApplicationEntity.getPkId();
        String childPrimaryKey = oldApplicationEntity.getAppProtocolRef();
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(oldApplicationEntity.getAppIntegrationProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
        Map<String, String> oldCdEntityMap = new LinkedHashMap<>();
        boolean isUpdate = true;
        if (!protocol.getProtocol().equalsIgnoreCase(remoteCdModel.getProtocol())) {
            manageProtocolService.deleteProtocol(oldApplicationEntity.getAppIntegrationProtocol(), oldApplicationEntity.getPkId());
            childPrimaryKey = getChildPrimaryKey(remoteCdModel.getProtocol());
            isUpdate = false;
        } else {
            oldCdEntityMap = mapper.convertValue(remoteConnectDirectService.get(parentPrimaryKey),
                    new TypeReference<Map<String, String>>() {
                    });
        }
        Map<String, String> newCdEntityMap = mapper.convertValue(remoteConnectDirectService.saveProtocol(remoteCdModel, parentPrimaryKey, childPrimaryKey, "APP"),
                new TypeReference<Map<String, String>>() {
                });
        ApplicationModel applicationModel = new ApplicationModel();
        BeanUtils.copyProperties(remoteCdModel, applicationModel);
        ApplicationEntity newApplicationEntity = applicationService.save(applicationModel, parentPrimaryKey, childPrimaryKey);
        if (isUpdate) {
            if (isNotNull(remoteCdModel.getIsSIProfile())) {
                RemoteConnectDirectProfile remoteConnectDirectProfile = new RemoteConnectDirectProfile(remoteCdModel, isUpdate, community);
                if (remoteConnectDirectProfile.getIsInitiatingConsumer() || remoteConnectDirectProfile.getIsListeningConsumer()) {
                    remoteConnectDirectProfile.setConsumerCdConfiguration(new ConsumerCdConfiguration(remoteCdModel));
                }
                if (remoteConnectDirectProfile.getIsInitiatingProducer() || remoteConnectDirectProfile.getIsListeningProducer()) {
                    remoteConnectDirectProfile.setProducerCdConfiguration(new ProducerCdConfiguration(remoteCdModel));
                }
                b2BApiService.updateRemoteCDProfile(remoteConnectDirectProfile, oldApplicationEntity.getApplicationName());
            }
            b2BCdNodeService.updateNodeInSI(remoteCdModel, oldCdEntityMap.get("nodeName"));
        } else {
            b2BCdNodeService.createNodeInSI(remoteCdModel);
        }
        activityHistoryService.updateApplicationActivity(oldApplicationEntity, newApplicationEntity, oldCdEntityMap, newCdEntityMap, isUpdate);

        if (isNotNull(remoteCdModel.getInboundConnectionType()) && remoteCdModel.getInboundConnectionType()) {
            b2BApiService.createMailBoxInSI(true, remoteCdModel.getDirectory(), null);
        }
        if (isNotNull(remoteCdModel.getOutboundConnectionType()) && remoteCdModel.getOutboundConnectionType()) {
            b2BApiService.createMailBoxInSI(true, null, remoteCdModel.getOutDirectory());
        }
    }

    public RemoteCdModel get(String pkId) {
        ApplicationEntity applicationEntity = applicationService.get(pkId);
        RemoteConnectDirectEntity remoteConnectDirectEntity = remoteConnectDirectService.get(pkId);
        RemoteCdModel remoteCdModel = serialize.apply(applicationEntity, remoteConnectDirectEntity);
        if (!remoteCdModel.isSSP()) {
            RemoteCdModel cdNodeModel = b2BCdNodeService.getNodeInSI(remoteCdModel);
            remoteCdModel.setNodeName(cdNodeModel.getNodeName());
            remoteCdModel.setPort(cdNodeModel.getPort());
            remoteCdModel.setSecure(cdNodeModel.isSecure());
            remoteCdModel.setHostName(cdNodeModel.getHostName());
            remoteCdModel.setSecurityProtocol(cdNodeModel.getSecurityProtocol());
            remoteCdModel.setSystemCertificate(cdNodeModel.getSystemCertificate());
            remoteCdModel.setCaCertName(cdNodeModel.getCaCertName());
            remoteCdModel.setCipherSuits(cdNodeModel.getCipherSuits());
        }
        return remoteCdModel;
    }

    @Transactional
    public void delete(String pkId, Boolean isDeleteInSI) {
        if (!processService.findByApplicationProfile(pkId).isEmpty()) {
            throw internalServerError("Unable to delete this Application, Because it has workflow.");
        }
        if (isDeleteInSI) {
            b2BCdNodeService.deleteNodeInSI(remoteConnectDirectService.get(pkId).getNodeName());
        }
        remoteConnectDirectService.delete(pkId);
        applicationService.delete(applicationService.get(pkId));
    }

}
