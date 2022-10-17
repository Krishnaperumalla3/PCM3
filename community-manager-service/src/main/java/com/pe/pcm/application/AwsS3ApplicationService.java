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
import com.pe.pcm.protocol.AwsS3Model;
import com.pe.pcm.protocol.AwsS3Service;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.awss3.entity.AwsS3Entity;
import com.pe.pcm.utils.CommonFunctions;
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
 * @author  Shameer
 *          Kiran Reddy
 */
@Service
public class AwsS3ApplicationService {

    private final ApplicationService applicationService;
    private final ActivityHistoryService activityHistoryService;
    private final ProcessService processService;
    private final AwsS3Service awsS3Service;
    private final ManageProtocolService manageProtocolService;
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public AwsS3ApplicationService(ApplicationService applicationService, ActivityHistoryService activityHistoryService, ProcessService processService, AwsS3Service awsS3Service, ManageProtocolService manageProtocolService) {
        this.applicationService = applicationService;
        this.activityHistoryService = activityHistoryService;
        this.processService = processService;
        this.manageProtocolService = manageProtocolService;
        this.awsS3Service = awsS3Service;
    }

    public void duplicateApplication(String applicationId) {
        applicationService.find(applicationId).ifPresent(applicationEntity -> {
            throw conflict("Application");
        });
    }

    private final BiFunction<ApplicationEntity, AwsS3Entity, AwsS3Model> serialize = AwsS3ApplicationService::apply;

    @Transactional
    public String save(AwsS3Model awsS3Model) {
        if (!Optional.ofNullable(Protocol.findProtocol(awsS3Model.getProtocol())).isPresent()) {
            throw unknownProtocol();
        }
        duplicateApplication(awsS3Model.getProfileId());
        String parentPrimaryKey = KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.APP_PKEY_PRE_APPEND, PCMConstants.APP_PKEY_RANDOM_COUNT);
        String childPrimaryKey = KeyGeneratorUtil.getChildPrimaryKey(awsS3Model.getProtocol());
        awsS3Service.saveProtocol(awsS3Model, parentPrimaryKey, childPrimaryKey, "APP");
        ApplicationModel applicationModel = new ApplicationModel();
        BeanUtils.copyProperties(awsS3Model, applicationModel);
        applicationService.save(applicationModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.saveApplicationActivity(parentPrimaryKey, "Application created.");
        return parentPrimaryKey;
    }

    @Transactional
    public void update(AwsS3Model awsS3Model) {
        ApplicationEntity oldApplicationEntity = SerializationUtils.clone(applicationService.get(awsS3Model.getPkId()));
        if (!oldApplicationEntity.getApplicationId().equalsIgnoreCase(awsS3Model.getProfileId())) {
            duplicateApplication(awsS3Model.getProfileId());
        }
        String parentPrimaryKey = oldApplicationEntity.getPkId();
        String childPrimaryKey = oldApplicationEntity.getAppProtocolRef();
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(oldApplicationEntity.getAppIntegrationProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
        Map<String, String> oldProtocolEntityMap = new LinkedHashMap<>();
        boolean isUpdate = true;
        if (!protocol.getProtocol().equalsIgnoreCase(awsS3Model.getProtocol())) {
            manageProtocolService.deleteProtocol(oldApplicationEntity.getAppIntegrationProtocol(), oldApplicationEntity.getPkId());
            childPrimaryKey = getChildPrimaryKey(awsS3Model.getProtocol());
            isUpdate = false;
        } else {
            oldProtocolEntityMap = mapper.convertValue(awsS3Service.get(parentPrimaryKey),
                    new TypeReference<Map<String, String>>() {
                    });
        }
        Map<String, String> newProtocolEntityMap = mapper.convertValue(awsS3Service.saveProtocol(awsS3Model, parentPrimaryKey, childPrimaryKey, "APP"),
                new TypeReference<Map<String, String>>() {
                });
        ApplicationModel applicationModel = new ApplicationModel();
        BeanUtils.copyProperties(awsS3Model, applicationModel);
        ApplicationEntity newApplicationEntity = applicationService.save(applicationModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.updateApplicationActivity(oldApplicationEntity, newApplicationEntity, oldProtocolEntityMap, newProtocolEntityMap, isUpdate);
    }

    @Transactional
    public void delete(String pkId) {
        if (!processService.findByApplicationProfile(pkId).isEmpty()) {
            throw internalServerError("Unable to Delete this Application, Because it has workflow.");
        }
        ApplicationEntity applicationEntity = applicationService.get(pkId);
        awsS3Service.delete(pkId);
        applicationService.delete(applicationEntity);
    }

    public AwsS3Model get(String pkId) {
        ApplicationEntity applicationEntity = applicationService.get(pkId);
        AwsS3Entity awsS3Entity = awsS3Service.get(pkId);
        return serialize.apply(applicationEntity, awsS3Entity);
    }


    private static AwsS3Model apply(ApplicationEntity applicationEntity, AwsS3Entity awsS3Entity) {
        AwsS3Model awsS3Model = new AwsS3Model();
        awsS3Model.setPkId(applicationEntity.getPkId());
        awsS3Model.setProfileName(applicationEntity.getApplicationName());
        awsS3Model.setProfileId(applicationEntity.getApplicationId());
        awsS3Model.setProtocol(applicationEntity.getAppIntegrationProtocol());
        awsS3Model.setEmailId(applicationEntity.getEmailId());
        awsS3Model.setPhone(applicationEntity.getPhone());
        awsS3Model.setStatus(convertStringToBoolean(applicationEntity.getAppIsActive()));
        awsS3Model.setPgpInfo(applicationEntity.getPgpInfo());
        awsS3Model.setHubInfo(convertStringToBoolean(awsS3Entity.getIsHubInfo()));
        awsS3Model.setFileType(awsS3Entity.getFileType());
        awsS3Model.setAdapterName(awsS3Entity.getAdapterName());
        awsS3Model.setPoolingInterval(awsS3Entity.getPoolingIntervalMins());
        awsS3Model.setAccessKey(awsS3Entity.getAccessKey());
        awsS3Model.setBucketName(awsS3Entity.getBucketName());
        awsS3Model.setEndpoint(awsS3Entity.getEndpoint());
        awsS3Model.setFileName(awsS3Entity.getFileName());
        awsS3Model.setInMailbox(awsS3Entity.getInMailbox());
        awsS3Model.setSecretKey(awsS3Entity.getSecretKey());
        awsS3Model.setSourcePath(awsS3Entity.getSourcePath());
        awsS3Model.setRegion(awsS3Entity.getRegion());
        awsS3Model.setQueueName(awsS3Entity.getQueueName());
        awsS3Model.setFolderName(awsS3Entity.getFolderName());
        awsS3Model.setEndPointUrl(awsS3Entity.getEndPointUrl());
        awsS3Model.setPemIdentifier(applicationEntity.getPemIdentifier());
        awsS3Model.setDeleteAfterCollection(CommonFunctions.convertStringToBoolean(awsS3Entity.getDeleteAfterCollection()));
        return awsS3Model;
    }

}
