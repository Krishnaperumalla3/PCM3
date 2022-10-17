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
import com.pe.pcm.protocol.FileSystemModel;
import com.pe.pcm.protocol.FileSystemService;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.filesystem.entity.FileSystemEntity;
import com.pe.pcm.protocol.function.SterlingFunctions;
import com.pe.pcm.sterling.mailbox.SterlingMailboxService;
import com.pe.pcm.sterling.mailbox.routingrule.SterlingRoutingRuleService;
import com.pe.pcm.utils.KeyGeneratorUtil;
import com.pe.pcm.utils.PCMConstants;
import com.pe.pcm.workflow.ProcessService;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.BeanUtils;
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
import static com.pe.pcm.utils.PCMConstants.APP;

/**
 * @author Chenchu Kiran.
 */
@Service
public class FileSystemApplicationService {

    private final ProcessService processService;
    private final FileSystemService fileSystemService;
    private final ApplicationService applicationService;
    private final ManageProtocolService manageProtocolService;
    private final ActivityHistoryService activityHistoryService;
    private final SterlingMailboxService sterlingMailboxService;
    private final SterlingRoutingRuleService sterlingRoutingRuleService;

    private final ObjectMapper mapper = new ObjectMapper();
    private final BiFunction<ApplicationEntity, FileSystemEntity, FileSystemModel> serialize = FileSystemApplicationService::apply;

    public FileSystemApplicationService(ProcessService processService, FileSystemService fileSystemService, ApplicationService applicationService, ManageProtocolService manageProtocolService, ActivityHistoryService activityHistoryService, SterlingMailboxService sterlingMailboxService, SterlingRoutingRuleService sterlingRoutingRuleService) {
        this.processService = processService;
        this.fileSystemService = fileSystemService;
        this.applicationService = applicationService;
        this.manageProtocolService = manageProtocolService;
        this.activityHistoryService = activityHistoryService;
        this.sterlingMailboxService = sterlingMailboxService;
        this.sterlingRoutingRuleService = sterlingRoutingRuleService;
    }

    private static FileSystemModel apply(ApplicationEntity applicationEntity, FileSystemEntity fileSystemEntity) {
        FileSystemModel fileSystemModel = new FileSystemModel();
        fileSystemModel.setPkId(applicationEntity.getPkId());
        fileSystemModel.setProfileName(applicationEntity.getApplicationName());
        fileSystemModel.setProfileId(applicationEntity.getApplicationId());
        fileSystemModel.setPhone(applicationEntity.getPhone());
        fileSystemModel.setProtocol(applicationEntity.getAppIntegrationProtocol());
        fileSystemModel.setEmailId(applicationEntity.getEmailId());
        fileSystemModel.setPgpInfo(applicationEntity.getPgpInfo());
        fileSystemModel.setAdapterName(fileSystemEntity.getFsaAdapter());
        fileSystemModel.setUserName(fileSystemEntity.getUserName());
        fileSystemModel.setPassword(fileSystemEntity.getPassword());
        fileSystemModel.setFileType(fileSystemEntity.getFileType());
        fileSystemModel.setDeleteAfterCollection(convertStringToBoolean(fileSystemEntity.getDeleteAfterCollection()));
        fileSystemModel.setAdapterName(fileSystemEntity.getFsaAdapter());
        fileSystemModel.setPoolingInterval(fileSystemEntity.getPoolingIntervalMins());
        fileSystemModel.setStatus(convertStringToBoolean(applicationEntity.getAppIsActive()));
        fileSystemModel.setInDirectory(fileSystemEntity.getInDirectory());
        fileSystemModel.setOutDirectory(fileSystemEntity.getOutDirectory());
        fileSystemModel.setHubInfo(convertStringToBoolean(fileSystemEntity.getIsHubInfo()));
        fileSystemModel.setPemIdentifier(applicationEntity.getPemIdentifier());
        return fileSystemModel;
    }

    private void duplicateApplication(String applicationId) {
        applicationService.find(applicationId).ifPresent(applicationEntity -> {
            throw conflict("Application");
        });
    }

    @Transactional
    public String save(FileSystemModel fileSystemModel) {
        if (!Optional.ofNullable(Protocol.findProtocol(fileSystemModel.getProtocol())).isPresent()) {
            throw unknownProtocol();
        }
        duplicateApplication(fileSystemModel.getProfileId());
        String parentPrimaryKey = KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.TP_PKEY_PRE_APPEND,
                PCMConstants.TP_PKEY_RANDOM_COUNT);
        String childPrimaryKey = KeyGeneratorUtil.getChildPrimaryKey(fileSystemModel.getProtocol());
        saveProtocol(fileSystemModel, parentPrimaryKey, childPrimaryKey, fileSystemModel.getProfileName(), "");
        ApplicationModel applicationModel = new ApplicationModel();
        BeanUtils.copyProperties(fileSystemModel, applicationModel);
        applicationService.save(applicationModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.saveApplicationActivity(parentPrimaryKey, "Application created.");
        return parentPrimaryKey;
    }

    @Transactional
    public void update(FileSystemModel fileSystemModel) {
        ApplicationEntity oldApplicationEntity = SerializationUtils.clone(applicationService.get(fileSystemModel.getPkId()));
        if (!oldApplicationEntity.getApplicationId().equalsIgnoreCase(fileSystemModel.getProfileId())) {
            duplicateApplication(fileSystemModel.getProfileId());
        }
        String parentPrimaryKey = oldApplicationEntity.getPkId();
        String childPrimaryKey = oldApplicationEntity.getAppProtocolRef();
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(oldApplicationEntity.getAppIntegrationProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
        Map<String, String> oldFsEntityMap = new LinkedHashMap<>();
        boolean isUpdate = true;
        if (!protocol.getProtocol().equalsIgnoreCase(fileSystemModel.getProtocol())) {
            manageProtocolService.deleteProtocol(oldApplicationEntity.getAppIntegrationProtocol(), oldApplicationEntity.getPkId());
            childPrimaryKey = getChildPrimaryKey(fileSystemModel.getProtocol());
            isUpdate = false;
        } else {
            oldFsEntityMap = mapper.convertValue(fileSystemService.get(parentPrimaryKey),
                    new TypeReference<Map<String, String>>() {
                    });
        }
        Map<String, String> newFsEntityMap =
                mapper.convertValue(saveProtocol(fileSystemModel, parentPrimaryKey, childPrimaryKey,
                        oldApplicationEntity.getApplicationName(), oldFsEntityMap.get("poolingIntervalMins")),
                        new TypeReference<Map<String, String>>() {
                        });
        ApplicationModel applicationModel = new ApplicationModel();
        BeanUtils.copyProperties(fileSystemModel, applicationModel);
        ApplicationEntity newApplicationEntity = applicationService.save(applicationModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.updateApplicationActivity(oldApplicationEntity, newApplicationEntity, oldFsEntityMap, newFsEntityMap, isUpdate);
    }

    public FileSystemModel get(String pkId) {
        ApplicationEntity applicationEntity = applicationService.get(pkId);
        FileSystemEntity fileSystemEntity = fileSystemService.get(pkId);
        return serialize.apply(applicationEntity, fileSystemEntity);
    }

    @Transactional
    public void delete(String pkId) {
        if (!processService.findByApplicationProfile(pkId).isEmpty()) {
            throw internalServerError("Unable to Delete this Application, Because it has workflow.");
        }
        fileSystemService.delete(pkId);
        applicationService.delete(applicationService.get(pkId));
    }

    private FileSystemEntity saveProtocol(FileSystemModel fileSystemModel, String parentPrimaryKey, String childPrimaryKey, String oldProfileName, String oldPollingInterval) {
        fileSystemModel.setSubscriberType(APP);

        sterlingMailboxService.createAll(SterlingFunctions.convertFileSystemModelToSterlingMailBoxModels.apply(fileSystemModel));

        if (fileSystemModel.getPoolingInterval().equalsIgnoreCase("ON") && fileSystemModel.getOutDirectory().startsWith("/")) {
            sterlingRoutingRuleService.save(fileSystemModel.getOutDirectory(), fileSystemModel.getProfileName(), oldProfileName,fileSystemModel.getSubscriberType(), null,fileSystemModel.getRoutingRuleName());
        } else {
            if (isNotNull(oldPollingInterval) && oldPollingInterval.equalsIgnoreCase("ON")) {
                sterlingRoutingRuleService.delete(oldProfileName);
            }
        }
        return fileSystemService.saveProtocol(fileSystemModel, parentPrimaryKey, childPrimaryKey, APP);
    }
}
