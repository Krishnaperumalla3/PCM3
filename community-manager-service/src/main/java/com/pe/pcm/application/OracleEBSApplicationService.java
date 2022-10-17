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
import com.pe.pcm.protocol.OracleEbsModel;
import com.pe.pcm.protocol.OracleEbsService;
import com.pe.pcm.protocol.oracleebs.entity.OracleEbsEntity;
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
 * @author Shameer.
 */

@Service
public class OracleEBSApplicationService {

    private ApplicationService applicationService;
    private ActivityHistoryService activityHistoryService;
    private OracleEbsService oracleEBSService;
    private ProcessService processService;
    private ManageProtocolService manageProtocolService;
    private ObjectMapper mapper = new ObjectMapper();

    private BiFunction<ApplicationEntity, OracleEbsEntity, OracleEbsModel> serialize = OracleEBSApplicationService::apply;

    @Autowired
    public OracleEBSApplicationService(ApplicationService applicationService, ActivityHistoryService activityHistoryService, OracleEbsService oracleEBSService, ProcessService processService, ManageProtocolService manageProtocolService) {
        this.applicationService = applicationService;
        this.activityHistoryService = activityHistoryService;
        this.oracleEBSService = oracleEBSService;
        this.processService = processService;
        this.manageProtocolService = manageProtocolService;
    }

    void duplicateApplication(String applicationId) {
        applicationService.find(applicationId).ifPresent(applicationEntity -> {
            throw conflict("Application");
        });
    }

    @Transactional
    public String save(OracleEbsModel oracleEBSModel) {
        if (!Optional.ofNullable(Protocol.findProtocol(oracleEBSModel.getProtocol())).isPresent()) {
            throw unknownProtocol();
        }
        duplicateApplication(oracleEBSModel.getProfileId());
        String parentPrimaryKey = KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.APP_PKEY_PRE_APPEND,
                PCMConstants.APP_PKEY_RANDOM_COUNT);
        String childPrimaryKey = KeyGeneratorUtil.getChildPrimaryKey(oracleEBSModel.getProtocol());
        oracleEBSService.saveProtocol(oracleEBSModel, parentPrimaryKey, childPrimaryKey, "APP");
        ApplicationModel applicationModel = new ApplicationModel();
        BeanUtils.copyProperties(oracleEBSModel, applicationModel);
        applicationService.save(applicationModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.saveApplicationActivity(parentPrimaryKey, "Application created.");
        return parentPrimaryKey;

    }

    @Transactional
    public void update(OracleEbsModel oracleEBSModel) {
        ApplicationEntity oldApplicationEntity = SerializationUtils.clone(applicationService.get(oracleEBSModel.getPkId()));
        if (!oldApplicationEntity.getApplicationId().equalsIgnoreCase(oracleEBSModel.getProfileId())) {
            duplicateApplication(oracleEBSModel.getProfileId());
        }
        String parentPrimaryKey = oldApplicationEntity.getPkId();
        String childPrimaryKey = oldApplicationEntity.getAppProtocolRef();
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(oldApplicationEntity.getAppIntegrationProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
        Map<String, String> oldFtpEntityMap = new LinkedHashMap<>();
        boolean isUpdate = true;
        if (!protocol.getProtocol().equalsIgnoreCase(oracleEBSModel.getProtocol())) {
            manageProtocolService.deleteProtocol(oldApplicationEntity.getAppIntegrationProtocol(), oldApplicationEntity.getPkId());
            childPrimaryKey = getChildPrimaryKey(oracleEBSModel.getProtocol());
            isUpdate = false;
        } else {
            oldFtpEntityMap = mapper.convertValue(oracleEBSService.get(parentPrimaryKey),
                    new TypeReference<Map<String, String>>() {
                    });
        }
        Map<String, String> newFtpEntityMap = mapper.convertValue(oracleEBSService.saveProtocol(oracleEBSModel, parentPrimaryKey, childPrimaryKey, "APP"),
                new TypeReference<Map<String, String>>() {
                });
        ApplicationModel applicationModel = new ApplicationModel();
        BeanUtils.copyProperties(oracleEBSModel, applicationModel);
        ApplicationEntity newApplicationEntity = applicationService.save(applicationModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.updateApplicationActivity(oldApplicationEntity, newApplicationEntity, oldFtpEntityMap, newFtpEntityMap, isUpdate);
    }

    @Transactional
    public void delete(String pkId) {
        if (!processService.findByApplicationProfile(pkId).isEmpty()) {
            throw internalServerError("Unable to Delete this Application, Because it has workflow.");
        }
        ApplicationEntity applicationEntity = applicationService.get(pkId);
        oracleEBSService.delete(pkId);
        applicationService.delete(applicationEntity);
    }

    public OracleEbsModel get(String pkId) {
        ApplicationEntity applicationEntity = applicationService.get(pkId);
        OracleEbsEntity oracleEBSEntity = oracleEBSService.get(pkId);
        return serialize.apply(applicationEntity, oracleEBSEntity);
    }


    private static OracleEbsModel apply(ApplicationEntity applicationEntity, OracleEbsEntity oracleEBSEntity) {
        OracleEbsModel oracleEBSModel = new OracleEbsModel();
        oracleEBSModel.setPkId(applicationEntity.getPkId());
        oracleEBSModel.setProfileName(applicationEntity.getApplicationName());
        oracleEBSModel.setProfileId(applicationEntity.getApplicationId());
        oracleEBSModel.setProtocol(applicationEntity.getAppIntegrationProtocol());
        oracleEBSModel.setEmailId(applicationEntity.getEmailId());
        oracleEBSModel.setPhone(applicationEntity.getPhone());
        oracleEBSModel.setStatus(convertStringToBoolean(applicationEntity.getAppIsActive()));
        oracleEBSModel.setName(oracleEBSEntity.getName());
        oracleEBSModel.setOrProtocol(oracleEBSEntity.getProtocol());
        oracleEBSModel.setNameBod(oracleEBSEntity.getNameBod());
        oracleEBSModel.setPassword(oracleEBSEntity.getPassword());
        oracleEBSModel.setUserName(oracleEBSEntity.getUserName());
        oracleEBSModel.setAutoSendBodRecMsgs(oracleEBSEntity.getAutoSendBodRecMsgs());
        oracleEBSModel.setBpRecMsgs(oracleEBSEntity.getBpRecMsgs());
        oracleEBSModel.setBpUnknownBods(oracleEBSEntity.getBpUnknownBods());
        oracleEBSModel.setDateTimeOag(oracleEBSEntity.getDateTimeOag());
        oracleEBSModel.setDirectoryBod(oracleEBSEntity.getDirectoryBod());
        oracleEBSModel.setHttpEndpoint(oracleEBSEntity.getHttpEndpoint());
        oracleEBSModel.setRequestType(oracleEBSEntity.getRequestType());
        oracleEBSModel.setTimeoutBod(oracleEBSEntity.getTimeoutBod());
        oracleEBSModel.setTpContractSend(oracleEBSEntity.getTpContractSend());
        oracleEBSModel.setPemIdentifier(applicationEntity.getPemIdentifier());
        return oracleEBSModel;
    }

}
