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
import com.pe.pcm.enums.Protocol;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.WebserviceModel;
import com.pe.pcm.protocol.WebserviceService;
import com.pe.pcm.protocol.webservice.entity.WebserviceEntity;
import com.pe.pcm.utils.KeyGeneratorUtil;
import com.pe.pcm.utils.PCMConstants;
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
import static com.pe.pcm.utils.CommonFunctions.convertStringToBoolean;
import static com.pe.pcm.utils.KeyGeneratorUtil.getChildPrimaryKey;

/**
 * @author Chenchu Kiran.
 */
@Service
public class WsApplicationService {

    private ApplicationService applicationService;
    private ActivityHistoryService activityHistoryService;
    private WebserviceService webserviceService;
    private ProcessService processService;
    private ManageProtocolService manageProtocolService;
    private ObjectMapper mapper = new ObjectMapper();

    private BiFunction<ApplicationEntity, WebserviceEntity, WebserviceModel> serialize = WsApplicationService::apply;

    @Autowired
    public WsApplicationService(ApplicationService applicationService, ActivityHistoryService activityHistoryService,
                                WebserviceService webserviceService, ProcessService processService, ManageProtocolService manageProtocolService) {
        this.applicationService = applicationService;
        this.activityHistoryService = activityHistoryService;
        this.webserviceService = webserviceService;
        this.processService = processService;
        this.manageProtocolService = manageProtocolService;
    }

    private static WebserviceModel apply(ApplicationEntity applicationEntity, WebserviceEntity webserviceEntity) {
        WebserviceModel webserviceModel = new WebserviceModel();
        webserviceModel.setPkId(applicationEntity.getPkId());
        webserviceModel.setProfileName(applicationEntity.getApplicationName());
        webserviceModel.setProfileId(applicationEntity.getApplicationId());
        webserviceModel.setProtocol(applicationEntity.getAppIntegrationProtocol());
        webserviceModel.setEmailId(applicationEntity.getEmailId());
        webserviceModel.setPhone(applicationEntity.getPhone());
        webserviceModel.setStatus(convertStringToBoolean(applicationEntity.getAppIsActive()));
        webserviceModel.setPgpInfo(applicationEntity.getPgpInfo());
        webserviceModel.setName(webserviceEntity.getWebserviceName());
        webserviceModel.setInMailBox(webserviceEntity.getInDirectory());
        webserviceModel.setOutBoundUrl(webserviceEntity.getOutboundUrl());
        webserviceModel.setCertificateId(webserviceEntity.getCertificate());
        webserviceModel.setPoolingInterval(webserviceEntity.getPoolingIntervalMins());
        webserviceModel.setAdapterName(webserviceEntity.getAdapterName());
        webserviceModel.setHubInfo(convertStringToBoolean(webserviceEntity.getIsHubInfo()));
        webserviceModel.setPemIdentifier(applicationEntity.getPemIdentifier());
        return webserviceModel;
    }

    void duplicateApplication(String applicationId) {
        applicationService.find(applicationId).ifPresent(applicationEntity -> {
            throw conflict("Application");
        });
    }

    @Transactional
    public String save(WebserviceModel webserviceModel) {
        if (!Optional.ofNullable(Protocol.findProtocol(webserviceModel.getProtocol())).isPresent()) {
            throw unknownProtocol();
        }
        duplicateApplication(webserviceModel.getProfileId());
        String parentPrimaryKey = KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.APP_PKEY_PRE_APPEND,
                PCMConstants.APP_PKEY_RANDOM_COUNT);
        String childPrimaryKey = KeyGeneratorUtil.getChildPrimaryKey(webserviceModel.getProtocol());
        webserviceService.saveProtocol(webserviceModel, parentPrimaryKey, childPrimaryKey, "APP");
        ApplicationModel applicationModel = new ApplicationModel();
        BeanUtils.copyProperties(webserviceModel, applicationModel);
        applicationService.save(applicationModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.saveApplicationActivity(parentPrimaryKey, "Application created.");
        return parentPrimaryKey;
    }

    @Transactional
    public void update(WebserviceModel webserviceModel) {
        ApplicationEntity oldApplicationEntity = SerializationUtils.clone(applicationService.get(webserviceModel.getPkId()));
        if (!oldApplicationEntity.getApplicationId().equalsIgnoreCase(webserviceModel.getProfileId())) {
            duplicateApplication(webserviceModel.getProfileId());
        }
        String parentPrimaryKey = oldApplicationEntity.getPkId();
        String childPrimaryKey = oldApplicationEntity.getAppProtocolRef();
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(oldApplicationEntity.getAppIntegrationProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
        Map<String, String> oldWsEntityMap = new LinkedHashMap<>();
        boolean isUpdate = true;
        if (!protocol.getProtocol().equalsIgnoreCase(webserviceModel.getProtocol())) {
            manageProtocolService.deleteProtocol(oldApplicationEntity.getAppIntegrationProtocol(), oldApplicationEntity.getPkId());
            childPrimaryKey = getChildPrimaryKey(webserviceModel.getProtocol());
            isUpdate = false;
        } else {
            oldWsEntityMap = mapper.convertValue(webserviceService.get(parentPrimaryKey),
                    new TypeReference<Map<String, String>>() {
                    });
        }
        Map<String, String> newWsEntityMap = mapper.convertValue(webserviceService.saveProtocol(webserviceModel, parentPrimaryKey, childPrimaryKey, "APP"),
                new TypeReference<Map<String, String>>() {
                });
        ApplicationModel applicationModel = new ApplicationModel();
        BeanUtils.copyProperties(webserviceModel, applicationModel);
        ApplicationEntity newapplicationEntity = applicationService.save(applicationModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.updateApplicationActivity(oldApplicationEntity, newapplicationEntity, oldWsEntityMap, newWsEntityMap, isUpdate);
    }

    public WebserviceModel get(String pkId) {
        ApplicationEntity applicationEntity = applicationService.get(pkId);
        WebserviceEntity webserviceEntity = webserviceService.get(pkId);
        return serialize.apply(applicationEntity, webserviceEntity);
    }

    @Transactional
    public void delete(String pkId) {
        if (!processService.findByApplicationProfile(pkId).isEmpty()) {
            throw internalServerError("Unable to Delete this Application, Because it has workflow.");
        }
        ApplicationEntity partnerEntity = applicationService.get(pkId);
        webserviceService.delete(pkId);
        applicationService.delete(partnerEntity);
    }
}
