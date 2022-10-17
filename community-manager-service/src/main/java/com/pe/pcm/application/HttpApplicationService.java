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
import com.pe.pcm.protocol.HttpModel;
import com.pe.pcm.protocol.HttpService;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.function.SterlingFunctions;
import com.pe.pcm.protocol.http.entity.HttpEntity;
import com.pe.pcm.sterling.mailbox.SterlingMailboxService;
import com.pe.pcm.sterling.mailbox.routingrule.SterlingRoutingRuleService;
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
import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.KeyGeneratorUtil.getChildPrimaryKey;
import static com.pe.pcm.utils.PCMConstants.*;

/**
 * @author Chenchu Kiran.
 */
@Service
public class HttpApplicationService {

    private final HttpService httpService;
    private final ProcessService processService;
    private final ApplicationService applicationService;
    private final ManageProtocolService manageProtocolService;
    private final ActivityHistoryService activityHistoryService;
    private final SterlingMailboxService sterlingMailboxService;
    private final SterlingRoutingRuleService sterlingRoutingRuleService;


    private final ObjectMapper mapper = new ObjectMapper();
    private final BiFunction<ApplicationEntity, HttpEntity, HttpModel> serialize = HttpApplicationService::apply;

    @Autowired
    public HttpApplicationService(ApplicationService applicationService, ActivityHistoryService activityHistoryService, HttpService httpService, ProcessService processService, ManageProtocolService manageProtocolService, SterlingMailboxService sterlingMailboxService, SterlingRoutingRuleService sterlingRoutingRuleService) {
        this.applicationService = applicationService;
        this.activityHistoryService = activityHistoryService;
        this.httpService = httpService;
        this.processService = processService;
        this.manageProtocolService = manageProtocolService;
        this.sterlingMailboxService = sterlingMailboxService;
        this.sterlingRoutingRuleService = sterlingRoutingRuleService;
    }

    private void duplicateApplication(String applicationId) {
        applicationService.find(applicationId).ifPresent(applicationEntity -> {
            throw conflict("Application");
        });
    }

    @Transactional
    public String save(HttpModel httpModel) {
        if (!Optional.ofNullable(Protocol.findProtocol(httpModel.getProtocol())).isPresent()) {
            throw unknownProtocol();
        }
        duplicateApplication(httpModel.getProfileId());
        String parentPrimaryKey = KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.APP_PKEY_PRE_APPEND,
                PCMConstants.APP_PKEY_RANDOM_COUNT);
        String childPrimaryKey = KeyGeneratorUtil.getChildPrimaryKey(httpModel.getProtocol());
        saveProtocol(httpModel, parentPrimaryKey, childPrimaryKey, httpModel.getProfileName(), "");
        ApplicationModel applicationModel = new ApplicationModel();
        BeanUtils.copyProperties(httpModel, applicationModel);
        applicationService.save(applicationModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.saveApplicationActivity(parentPrimaryKey, APPLICATION_CREATED);
        return parentPrimaryKey;
    }

    @Transactional
    public void update(HttpModel httpModel) {
        ApplicationEntity oldApplicationEntity = SerializationUtils.clone(applicationService.get(httpModel.getPkId()));
        if (!oldApplicationEntity.getApplicationId().equalsIgnoreCase(httpModel.getProfileId())) {
            duplicateApplication(httpModel.getProfileId());
        }
        String parentPrimaryKey = oldApplicationEntity.getPkId();
        String childPrimaryKey = oldApplicationEntity.getAppProtocolRef();
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(oldApplicationEntity.getAppIntegrationProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
        Map<String, String> oldHttpEntityMap = new LinkedHashMap<>();
        boolean isUpdate = true;
        if (!protocol.getProtocol().equalsIgnoreCase(httpModel.getProtocol())) {
            manageProtocolService.deleteProtocol(oldApplicationEntity.getAppIntegrationProtocol(), oldApplicationEntity.getPkId());
            childPrimaryKey = getChildPrimaryKey(httpModel.getProtocol());
            isUpdate = false;
        } else {
            oldHttpEntityMap = mapper.convertValue(httpService.get(parentPrimaryKey),
                    new TypeReference<Map<String, String>>() {
                    });
        }
        Map<String, String> newHttpEntityMap =
                mapper.convertValue(saveProtocol(httpModel, parentPrimaryKey, childPrimaryKey, oldApplicationEntity.getApplicationName(), oldHttpEntityMap.get("poolingIntervalMins")),
                        new TypeReference<Map<String, String>>() {
                        });
        ApplicationModel applicationModel = new ApplicationModel();
        BeanUtils.copyProperties(httpModel, applicationModel);
        ApplicationEntity newApplicationEntity = applicationService.save(applicationModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.updateApplicationActivity(oldApplicationEntity,
                newApplicationEntity, oldHttpEntityMap, newHttpEntityMap, isUpdate);
    }

    public HttpModel get(String pkId) {
        ApplicationEntity applicationEntity = applicationService.get(pkId);
        HttpEntity httpEntity = httpService.get(pkId);
        return serialize.apply(applicationEntity, httpEntity);
    }

    @Transactional
    public void delete(String pkId) {
        if (!processService.findByApplicationProfile(pkId).isEmpty()) {
            throw internalServerError(APP_WORKFLOW_EXIST);
        }
        ApplicationEntity applicationEntity = applicationService.get(pkId);
        httpService.delete(pkId);
        applicationService.delete(applicationEntity);
    }

    private static HttpModel apply(ApplicationEntity applicationEntity, HttpEntity httpEntity) {
        HttpModel httpModel = new HttpModel();
        httpModel.setPkId(applicationEntity.getPkId());
        httpModel.setProfileName(applicationEntity.getApplicationName());
        httpModel.setProfileId(applicationEntity.getApplicationId());
        httpModel.setProtocol(applicationEntity.getAppIntegrationProtocol());
        httpModel.setEmailId(applicationEntity.getEmailId());
        httpModel.setPhone(applicationEntity.getPhone());
        httpModel.setStatus(convertStringToBoolean(applicationEntity.getAppIsActive()));
        httpModel.setAdapterName(httpEntity.getAdapterName());
        httpModel.setPoolingInterval(httpEntity.getPoolingIntervalMins());
        httpModel.setCertificate(httpEntity.getCertificate());
        httpModel.setInMailBox(httpEntity.getInMailbox());
        httpModel.setOutBoundUrl(httpEntity.getOutboundUrl());
        httpModel.setHubInfo(convertStringToBoolean(httpEntity.getIsHubInfo()));
        httpModel.setPemIdentifier(applicationEntity.getPemIdentifier());
        httpModel.setPgpInfo(applicationEntity.getPgpInfo());
        return httpModel;
    }

    private HttpEntity saveProtocol(HttpModel httpModel, String parentPrimaryKey, String childPrimaryKey, String oldProfileName, String oldPollingInterval) {

        sterlingMailboxService.createAll(SterlingFunctions.convertHttpModelToSterlingMailBoxModels.apply(httpModel));
        if (isNotNull(httpModel.getInMailBox()) && httpModel.getPoolingInterval().equalsIgnoreCase("ON")) {
            sterlingRoutingRuleService.save(httpModel.getInMailBox(), httpModel.getProfileName(), oldProfileName,"APP", null,httpModel.getRoutingRuleName());
        } else {
            if (isNotNull(oldPollingInterval) && oldPollingInterval.equalsIgnoreCase("ON")) {
                sterlingRoutingRuleService.delete(oldProfileName);
            }
        }
        return httpService.saveProtocol(httpModel, parentPrimaryKey, childPrimaryKey, APP);
    }
}
