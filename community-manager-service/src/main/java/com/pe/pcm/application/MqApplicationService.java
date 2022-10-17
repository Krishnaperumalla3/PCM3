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
import com.pe.pcm.protocol.MqModel;
import com.pe.pcm.protocol.MqService;
import com.pe.pcm.protocol.mq.entity.MqEntity;
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
public class MqApplicationService {

    private final ApplicationService applicationService;
    private final ActivityHistoryService activityHistoryService;
    private final MqService mqService;
    private final ProcessService processService;
    private final ManageProtocolService manageProtocolService;
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public MqApplicationService(ApplicationService applicationService, ActivityHistoryService activityHistoryService, MqService mqService, ProcessService processService, ManageProtocolService manageProtocolService) {
        this.applicationService = applicationService;
        this.activityHistoryService = activityHistoryService;
        this.mqService = mqService;
        this.processService = processService;
        this.manageProtocolService = manageProtocolService;
    }

    private final BiFunction<ApplicationEntity, MqEntity, MqModel> serialize = MqApplicationService::apply;

    private static MqModel apply(ApplicationEntity applicationEntity, MqEntity mqEntity) {
        MqModel mqModel = new MqModel();
        mqModel.setPkId(applicationEntity.getPkId());
        mqModel.setProfileName(applicationEntity.getApplicationName());
        mqModel.setProfileId(applicationEntity.getApplicationId());
        mqModel.setPkId(applicationEntity.getPkId());
        mqModel.setProtocol(applicationEntity.getAppIntegrationProtocol());
        mqModel.setEmailId(applicationEntity.getEmailId());
        mqModel.setPhone(applicationEntity.getPhone());
        mqModel.setStatus(convertStringToBoolean(applicationEntity.getAppIsActive()));
        mqModel.setPgpInfo(applicationEntity.getPgpInfo());
        mqModel.setHostName(mqEntity.getHostName());
        mqModel.setPort(mqEntity.getPort());
        mqModel.setFileType(mqEntity.getFileType());
        mqModel.setQueueManager(mqEntity.getQueueManager());
        mqModel.setQueueName(mqEntity.getQueueName());
        mqModel.setAdapterName(mqEntity.getAdapterName());
        mqModel.setPoolingInterval(mqEntity.getPoolingIntervalMins());
        mqModel.setHubInfo(convertStringToBoolean(mqEntity.getIsHubInfo()));
        mqModel.setUserId(mqEntity.getUserId());
        mqModel.setPassword(mqEntity.getPassword());
        mqModel.setPemIdentifier(applicationEntity.getPemIdentifier());
        mqModel.setChannelName(mqEntity.getChannelName());
        return mqModel;
    }

    private void duplicateApplication(String applicationId) {
        applicationService.find(applicationId).ifPresent(applicationEntity -> {
            throw conflict("Application");
        });
    }

    @Transactional
    public String save(MqModel mqModel) {
        if (!Optional.ofNullable(Protocol.findProtocol(mqModel.getProtocol())).isPresent()) {
            throw unknownProtocol();
        }
        duplicateApplication(mqModel.getProfileId());
        String parentPrimaryKey = KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.APP_PKEY_PRE_APPEND,
                PCMConstants.APP_PKEY_RANDOM_COUNT);
        String childPrimaryKey = KeyGeneratorUtil.getChildPrimaryKey(mqModel.getProtocol());
        mqService.saveProtocol(mqModel, parentPrimaryKey, childPrimaryKey, "APP");
        ApplicationModel applicationModel = new ApplicationModel();
        BeanUtils.copyProperties(mqModel, applicationModel);
        applicationService.save(applicationModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.saveApplicationActivity(parentPrimaryKey, "Application created.");
        return parentPrimaryKey;
    }

    @Transactional
    public void update(MqModel mqModel) {
        ApplicationEntity oldApplicationEntity = SerializationUtils.clone(applicationService.get(mqModel.getPkId()));
        if (!oldApplicationEntity.getApplicationId().equalsIgnoreCase(mqModel.getProfileId())) {
            duplicateApplication(mqModel.getProfileId());
        }
        String parentPrimaryKey = oldApplicationEntity.getPkId();
        String childPrimaryKey = oldApplicationEntity.getAppProtocolRef();
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(oldApplicationEntity.getAppIntegrationProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
        Map<String, String> oldMqEntityMap = new LinkedHashMap<>();
        boolean isUpdate = true;
        if (!protocol.getProtocol().equalsIgnoreCase(mqModel.getProtocol())) {
            manageProtocolService.deleteProtocol(oldApplicationEntity.getAppIntegrationProtocol(), oldApplicationEntity.getPkId());
            childPrimaryKey = getChildPrimaryKey(mqModel.getProtocol());
            isUpdate = false;
        } else {
            oldMqEntityMap = mapper.convertValue(mqService.get(parentPrimaryKey),
                    new TypeReference<Map<String, String>>() {
                    });
        }
        Map<String, String> newMqEntityMap = mapper.convertValue(mqService.saveProtocol(mqModel, parentPrimaryKey, childPrimaryKey, "APP"),
                new TypeReference<Map<String, String>>() {
                });
        ApplicationModel applicationModel = new ApplicationModel();
        BeanUtils.copyProperties(mqModel, applicationModel);
        ApplicationEntity newApplicationEntity = applicationService.save(applicationModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.updateApplicationActivity(oldApplicationEntity, newApplicationEntity, oldMqEntityMap, newMqEntityMap, isUpdate);
    }

    public MqModel get(String pkId) {
        return serialize.apply(applicationService.get(pkId), mqService.get(pkId));
    }

    @Transactional
    public void delete(String pkId) {
        if (!processService.findByApplicationProfile(pkId).isEmpty()) {
            throw internalServerError("Unable to Delete this Application, Because it has workflow.");
        }
        mqService.delete(pkId);
        applicationService.delete(applicationService.get(pkId));
    }
}
