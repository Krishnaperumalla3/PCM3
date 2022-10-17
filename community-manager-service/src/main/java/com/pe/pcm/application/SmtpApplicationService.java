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
import com.pe.pcm.protocol.SmtpModel;
import com.pe.pcm.protocol.SmtpService;
import com.pe.pcm.protocol.smtp.entity.SmtpEntity;
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
public class SmtpApplicationService {

    private ApplicationService applicationService;
    private ActivityHistoryService activityHistoryService;
    private SmtpService smtpService;
    private ProcessService processService;
    private ManageProtocolService manageProtocolService;
    private ObjectMapper mapper = new ObjectMapper();

    private BiFunction<ApplicationEntity, SmtpEntity, SmtpModel> serialize = SmtpApplicationService::apply;

    @Autowired
    public SmtpApplicationService(ApplicationService applicationService, ActivityHistoryService activityHistoryService, SmtpService smtpService,
                                  ProcessService processService, ManageProtocolService manageProtocolService) {
        this.applicationService = applicationService;
        this.activityHistoryService = activityHistoryService;
        this.smtpService = smtpService;
        this.processService = processService;
        this.manageProtocolService = manageProtocolService;
    }

    void duplicateApplication(String applicationId) {
        applicationService.find(applicationId).ifPresent(applicationEntity -> {
            throw conflict("Application");
        });
    }

    @Transactional
    public String save(SmtpModel smtpModel) {
        if (!Optional.ofNullable(Protocol.findProtocol(smtpModel.getProtocol())).isPresent()) {
            throw unknownProtocol();
        }
        duplicateApplication(smtpModel.getProfileId());
        String parentPrimaryKey = KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.APP_PKEY_PRE_APPEND,
                PCMConstants.APP_PKEY_RANDOM_COUNT);
        String childPrimaryKey = KeyGeneratorUtil.getChildPrimaryKey(smtpModel.getProtocol());
        smtpService.saveProtocol(smtpModel, parentPrimaryKey, childPrimaryKey, "APP");
        ApplicationModel applicationModel = new ApplicationModel();
        BeanUtils.copyProperties(smtpModel, applicationModel);
        applicationService.save(applicationModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.saveApplicationActivity(parentPrimaryKey, "Application created.");
        return parentPrimaryKey;

    }

    @Transactional
    public void update(SmtpModel smtpModel) {
        ApplicationEntity oldApplicationEntity = SerializationUtils.clone(applicationService.get(smtpModel.getPkId()));
        if (!oldApplicationEntity.getApplicationId().equalsIgnoreCase(smtpModel.getProfileId())) {
            duplicateApplication(smtpModel.getProfileId());
        }
        String parentPrimaryKey = oldApplicationEntity.getPkId();
        String childPrimaryKey = oldApplicationEntity.getAppProtocolRef();
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(oldApplicationEntity.getAppIntegrationProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
        Map<String, String> oldFtpEntityMap = new LinkedHashMap<>();
        boolean isUpdate = true;
        if (!protocol.getProtocol().equalsIgnoreCase(smtpModel.getProtocol())) {
            manageProtocolService.deleteProtocol(oldApplicationEntity.getAppIntegrationProtocol(), oldApplicationEntity.getPkId());
            childPrimaryKey = getChildPrimaryKey(smtpModel.getProtocol());
            isUpdate = false;
        } else {
            oldFtpEntityMap = mapper.convertValue(smtpService.get(parentPrimaryKey),
                    new TypeReference<Map<String, String>>() {
                    });
        }
        Map<String, String> newFtpEntityMap = mapper.convertValue(smtpService.saveProtocol(smtpModel, parentPrimaryKey, childPrimaryKey, "APP"),
                new TypeReference<Map<String, String>>() {
                });
        ApplicationModel applicationModel = new ApplicationModel();
        BeanUtils.copyProperties(smtpModel, applicationModel);
        ApplicationEntity newApplicationEntity = applicationService.save(applicationModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.updateApplicationActivity(oldApplicationEntity, newApplicationEntity, oldFtpEntityMap, newFtpEntityMap, isUpdate);
    }

    @Transactional
    public void delete(String pkId) {
        if (!processService.findByApplicationProfile(pkId).isEmpty()) {
            throw internalServerError("Unable to Delete this Application, Because it has workflow.");
        }
        ApplicationEntity applicationEntity = applicationService.get(pkId);
        smtpService.delete(pkId);
        applicationService.delete(applicationEntity);
    }

    public SmtpModel get(String pkId) {
        ApplicationEntity applicationEntity = applicationService.get(pkId);
        SmtpEntity smtpEntity = smtpService.get(pkId);
        return serialize.apply(applicationEntity, smtpEntity);
    }


    private static SmtpModel apply(ApplicationEntity applicationEntity, SmtpEntity smtpEntity) {
        SmtpModel smtpModel = new SmtpModel();
        smtpModel.setPkId(applicationEntity.getPkId());
        smtpModel.setProfileName(applicationEntity.getApplicationName());
        smtpModel.setProfileId(applicationEntity.getApplicationId());
        smtpModel.setPkId(applicationEntity.getPkId());
        smtpModel.setProtocol(applicationEntity.getAppIntegrationProtocol());
        smtpModel.setEmailId(applicationEntity.getEmailId());
        smtpModel.setPhone(applicationEntity.getPhone());
        smtpModel.setStatus(convertStringToBoolean(applicationEntity.getAppIsActive()));
        smtpModel.setName(smtpEntity.getName());
        smtpModel.setAccessProtocol(smtpEntity.getAccessProtocol());
        smtpModel.setMailServer(smtpEntity.getMailServer());
        smtpModel.setMailServerPort(smtpEntity.getMailServerPort());
        smtpModel.setUserName(smtpEntity.getUserName());
        smtpModel.setPassword(smtpEntity.getPassword());
        smtpModel.setConnectionRetries(smtpEntity.getConnectionRetries());
        smtpModel.setRetryInterval(smtpEntity.getRetryInterval());
        smtpModel.setMaxMsgsSession(smtpEntity.getMaxMsgsSession());
        smtpModel.setRemoveMailMsgs(smtpEntity.getRemoveInboxMailMsgs());
        smtpModel.setSsl(smtpEntity.getSsl());
        smtpModel.setKeyCertPassPhrase(smtpEntity.getkeyCertPassPhrase());
        smtpModel.setCipherStrength(smtpEntity.getCipherStrength());
        smtpModel.setKeyCert(smtpEntity.getKeyCert());
        smtpModel.setCaCertificates(smtpEntity.getCaCertificates());
        smtpModel.setUriName(smtpEntity.getUriName());
        smtpModel.setPoolingInterval(smtpEntity.getPoolingInterval());
        smtpModel.setPemIdentifier(applicationEntity.getPemIdentifier());
        return smtpModel;
    }

}
