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
import com.pe.pcm.b2b.other.CaCertGetModel;
import com.pe.pcm.b2b.other.CipherSuiteNameGetModel;
import com.pe.pcm.enums.Protocol;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.partner.PartnerModel;
import com.pe.pcm.protocol.ConnectDirectModel;
import com.pe.pcm.protocol.ConnectDirectService;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.connectdirect.entity.ConnectDirectEntity;
import com.pe.pcm.utils.KeyGeneratorUtil;
import com.pe.pcm.utils.PCMConstants;
import com.pe.pcm.workflow.ProcessService;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

/**
 * @author Shameer.
 */
@Service
public class ConnectDirectApplicationService {

    private ApplicationService applicationService;

    private ActivityHistoryService activityHistoryService;

    private ObjectMapper mapper = new ObjectMapper();

    private ConnectDirectService connectDirectService;

    private ProcessService processService;

    private ManageProtocolService manageProtocolService;

    private BiFunction<ApplicationEntity, ConnectDirectEntity, ConnectDirectModel> serialize = ConnectDirectApplicationService::apply;

    @Autowired
    public ConnectDirectApplicationService(ApplicationService applicationService, ActivityHistoryService activityHistoryService, ConnectDirectService connectDirectService, ProcessService processService, ManageProtocolService manageProtocolService) {
        this.applicationService = applicationService;
        this.activityHistoryService = activityHistoryService;
        this.connectDirectService = connectDirectService;
        this.processService = processService;
        this.manageProtocolService = manageProtocolService;
    }

    private void duplicateApplication(String applicationId) {
        applicationService.find(applicationId).ifPresent(applicationEntity -> {
            throw conflict("Application");
        });
    }

    @Transactional
    public String save(ConnectDirectModel connectDirectModel) {
        if (!Optional.ofNullable(Protocol.findProtocol(connectDirectModel.getProtocol())).isPresent()) {
            throw unknownProtocol();
        }
        duplicateApplication(connectDirectModel.getProfileId());
        String parentPrimaryKey = KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.APP_PKEY_PRE_APPEND,
                PCMConstants.APP_PKEY_RANDOM_COUNT);
        String childPrimaryKey = KeyGeneratorUtil.getChildPrimaryKey(connectDirectModel.getProtocol());
        saveProtocol(connectDirectModel, parentPrimaryKey, childPrimaryKey);
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(connectDirectModel, partnerModel);
        applicationService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.saveApplicationActivity(parentPrimaryKey, "Application created.");
        return parentPrimaryKey;
    }

    @Transactional
    public void update(ConnectDirectModel connectDirectModel) {
        ApplicationEntity oldApplicationEntity = SerializationUtils.clone(applicationService.get(connectDirectModel.getPkId()));
        if (!oldApplicationEntity.getApplicationId().equalsIgnoreCase(connectDirectModel.getProfileId())) {
            duplicateApplication(connectDirectModel.getProfileId());
        }
        String parentPrimaryKey = oldApplicationEntity.getPkId();
        String childPrimaryKey = oldApplicationEntity.getAppProtocolRef();
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(connectDirectModel.getProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
        Map<String, String> oldCDEntityMap = new LinkedHashMap<>();
        boolean isUpdate = true;
        if (!protocol.getProtocol().equalsIgnoreCase(oldApplicationEntity.getAppIntegrationProtocol())) {
            manageProtocolService.deleteProtocol(oldApplicationEntity.getAppIntegrationProtocol(), oldApplicationEntity.getPkId());
            childPrimaryKey = getChildPrimaryKey(connectDirectModel.getProtocol());
            isUpdate = false;
        } else {
            oldCDEntityMap = mapper.convertValue(connectDirectService.get(parentPrimaryKey), new TypeReference<Map<String, String>>() {
            });
        }

        Map<String, String> newCDEntityMap = mapper.convertValue(saveProtocol(connectDirectModel, parentPrimaryKey, childPrimaryKey), new TypeReference<Map<String, String>>() {
        });
        ApplicationModel applicationModel = new ApplicationModel();
        BeanUtils.copyProperties(connectDirectModel, applicationModel);
        ApplicationEntity newApplicationEntity = applicationService.save(applicationModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.updateApplicationActivity(oldApplicationEntity, newApplicationEntity, oldCDEntityMap, newCDEntityMap, isUpdate);
    }

    public ConnectDirectEntity saveProtocol(ConnectDirectModel connectDirectModel, String parentPrimaryKey, String childPrimaryKey) {
        return connectDirectService.saveProtocol(connectDirectModel, parentPrimaryKey, childPrimaryKey, "APP");
    }

    @Transactional
    public void delete(String pkId) {
        if (!processService.findByPartnerProfile(pkId).isEmpty()) {
            throw internalServerError("Unable to delete this Application, Because it has workflow..");
        }
        ApplicationEntity applicationEntity = applicationService.get(pkId);
        connectDirectService.delete(pkId);
        applicationService.delete(applicationEntity);
    }

    public ConnectDirectModel get(String pkId) {
        ApplicationEntity applicationEntity = applicationService.get(pkId);
        ConnectDirectEntity connectDirectEntity = connectDirectService.get(pkId);
        return serialize.apply(applicationEntity, connectDirectEntity);
    }


    private static ConnectDirectModel apply(ApplicationEntity applicationEntity, ConnectDirectEntity connectDirectEntity) {
        ConnectDirectModel connectDirectModel = new ConnectDirectModel();
        connectDirectModel.setPkId(applicationEntity.getPkId())
                .setProfileName(applicationEntity.getApplicationName())
                .setProfileId(applicationEntity.getApplicationId())
                .setProtocol(applicationEntity.getAppIntegrationProtocol())
                .setEmailId(applicationEntity.getEmailId())
                .setPhone(applicationEntity.getPhone())
                .setStatus(convertStringToBoolean(applicationEntity.getAppIsActive()))
                .setHubInfo(convertStringToBoolean(connectDirectEntity.getIsHubInfo()))
                .setPemIdentifier(applicationEntity.getPemIdentifier())
                .setPgpInfo(applicationEntity.getPgpInfo());
        if (isNotNull(connectDirectEntity.getCaCertificate())) {
            connectDirectModel.setCaCertificate(Arrays.stream(connectDirectEntity.getCaCertificate().split(",")).map(CaCertGetModel::new).collect(Collectors.toList()));
        }
        if (isNotNull(connectDirectEntity.getCipherSuits())) {
            connectDirectModel.setCipherSuits(Arrays.stream(connectDirectEntity.getCipherSuits().split(",")).map(CipherSuiteNameGetModel::new).collect(Collectors.toList()));
        }
        return connectDirectModel.setAdapterName(connectDirectEntity.getAdapterName())
                .setCodePageFrom(connectDirectEntity.getCodePageFrom())
                .setCodePageTo(connectDirectEntity.getCodePageTo())
                .setDcb(connectDirectEntity.getDcb())
                .setLocalNodeName(connectDirectEntity.getLocalNodeName())
                .setLocalXLate(connectDirectEntity.getLocalXlate())
                .setPoolingInterval(connectDirectEntity.getPoolingInterval())
                .setRemoteHost(connectDirectEntity.getRemoteHost())
                .setRemoteNodeName(connectDirectEntity.getRemoteNodeName())
                .setRemoteUser(connectDirectEntity.getRemoteUser())
                .setRemotePassword(connectDirectEntity.getRemotePassword())
                .setRemotePort(connectDirectEntity.getRemotePort())
                .setSecurePlus(isNotNull(connectDirectEntity.getSecurePlus()) ? convertStringToBoolean(connectDirectEntity.getSecurePlus()) : null)
                .setSecurityProtocol(connectDirectEntity.getSecurityProtocol())
                .setSysOpts(connectDirectEntity.getSysOpts())
                .setTransferType(connectDirectEntity.getTransferType());
    }


}
