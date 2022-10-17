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
import com.pe.pcm.protocol.SapModel;
import com.pe.pcm.protocol.SapService;
import com.pe.pcm.protocol.sap.entity.SapEntity;
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
public class SapApplicationService {
    private ApplicationService applicationService;
    private SapService sapService;
    private ProcessService processService;
    private ActivityHistoryService activityHistoryService;
    private ManageProtocolService manageProtocolService;

    private ObjectMapper mapper = new ObjectMapper();

    private BiFunction<ApplicationEntity, SapEntity, SapModel> serialize = SapApplicationService::apply;

    @Autowired
    public SapApplicationService(ApplicationService applicationService, SapService sapService, ProcessService processService, ActivityHistoryService activityHistoryService, ManageProtocolService manageProtocolService) {
        this.applicationService = applicationService;
        this.sapService = sapService;
        this.processService = processService;
        this.activityHistoryService = activityHistoryService;
        this.manageProtocolService = manageProtocolService;
    }

    private void duplicateApplication(String applicationId){
        applicationService.find(applicationId).ifPresent(applicationEntity ->  {throw conflict("Application");});
    }

    @Transactional
    public String save(SapModel sapModel) {
        if (!Optional.ofNullable(Protocol.findProtocol(sapModel.getProtocol())).isPresent()) {
            throw unknownProtocol();
        }
       duplicateApplication(sapModel.getProfileId());
        String parentPrimaryKey = KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.TP_PKEY_PRE_APPEND,
                PCMConstants.TP_PKEY_RANDOM_COUNT);
        String childPrimaryKey = KeyGeneratorUtil.getChildPrimaryKey(sapModel.getProtocol());
        sapService.saveProtocol(sapModel, parentPrimaryKey, childPrimaryKey, "APP");
        ApplicationModel applicationModel = new ApplicationModel();
        BeanUtils.copyProperties(sapModel, applicationModel);
        applicationService.save(applicationModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.saveApplicationActivity(parentPrimaryKey, "Application created.");
        return parentPrimaryKey;
    }

    @Transactional
    public void update(SapModel sapModel) {
        ApplicationEntity oldApplicationEntity = SerializationUtils.clone(applicationService.get(sapModel.getPkId()));
        if(!oldApplicationEntity.getApplicationId().equalsIgnoreCase(sapModel.getProfileId())){
            duplicateApplication(sapModel.getProfileId());
        }
        String parentPrimaryKey = oldApplicationEntity.getPkId();
        String childPrimaryKey = oldApplicationEntity.getAppProtocolRef();
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(oldApplicationEntity.getAppIntegrationProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
        Map<String, String> oldSapEntityMap = new LinkedHashMap<>();
        boolean isUpdate = true;
        if (!protocol.getProtocol().equalsIgnoreCase(sapModel.getProtocol())) {
            manageProtocolService.deleteProtocol(oldApplicationEntity.getAppIntegrationProtocol(), oldApplicationEntity.getPkId());
            childPrimaryKey = getChildPrimaryKey(sapModel.getProtocol());
            isUpdate = false;
        }else{
            oldSapEntityMap = mapper.convertValue(sapService.get(parentPrimaryKey),
                    new TypeReference<Map<String, String>>() {
                    });
        }
        Map<String, String> newSapEntityMap = mapper.convertValue(sapService.saveProtocol(sapModel, parentPrimaryKey, childPrimaryKey, "APP"),
                new TypeReference<Map<String, String>>() {
                });
        ApplicationModel applicationModel = new ApplicationModel();
        BeanUtils.copyProperties(sapModel, applicationModel);
        ApplicationEntity newApplicationEntity = applicationService.save(applicationModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.updateApplicationActivity(oldApplicationEntity,
                newApplicationEntity, oldSapEntityMap, newSapEntityMap, isUpdate);
    }

    public SapModel get(String pkId) {
        return serialize.apply(applicationService.get(pkId), sapService.get(pkId));
    }

    @Transactional
    public void delete(String pkId) {
        if (!processService.findByApplicationProfile(pkId).isEmpty()) {
            throw internalServerError("Unable to Delete this Application, Because it has workflow.");
        }
        ApplicationEntity applicationEntity = applicationService.get(pkId);
        sapService.delete(pkId);
        applicationService.delete(applicationEntity);
    }

    private static SapModel apply(ApplicationEntity applicationEntity, SapEntity sapEntity) {
        SapModel sapModel = new SapModel();
        sapModel.setPkId(applicationEntity.getPkId());
        sapModel.setProfileName(applicationEntity.getApplicationName());
        sapModel.setProfileId(applicationEntity.getApplicationId());
        sapModel.setPkId(applicationEntity.getPkId());
        sapModel.setProtocol(applicationEntity.getAppIntegrationProtocol());
        sapModel.setEmailId(applicationEntity.getEmailId());
        sapModel.setPhone(applicationEntity.getPhone());
        sapModel.setStatus(convertStringToBoolean(applicationEntity.getAppIsActive()));
        sapModel.setPgpInfo(applicationEntity.getPgpInfo());
        sapModel.setAdapterName(sapEntity.getSapAdapterName());
        sapModel.setSapRoute(sapEntity.getSapRoute());
        sapModel.setHubInfo(convertStringToBoolean(sapEntity.getIsHubInfo()));
        sapModel.setPemIdentifier(applicationEntity.getPemIdentifier());
        return sapModel;
    }
}
