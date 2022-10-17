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
import com.pe.pcm.protocol.MailboxModel;
import com.pe.pcm.protocol.MailboxService;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.function.SterlingFunctions;
import com.pe.pcm.protocol.mailbox.entity.MailboxEntity;
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
import static com.pe.pcm.utils.CommunityManagerValidator.validate;
import static com.pe.pcm.utils.KeyGeneratorUtil.getChildPrimaryKey;
import static com.pe.pcm.utils.PCMConstants.APP;
import static java.lang.Boolean.TRUE;

/**
 * @author Chenchu Kiran.
 */
@Service
public class MailboxApplicationService {

    private final ProcessService processService;
    private final MailboxService mailboxService;
    private final ApplicationService applicationService;
    private final ManageProtocolService manageProtocolService;
    private final ActivityHistoryService activityHistoryService;
    private final SterlingMailboxService sterlingMailboxService;
    private final SterlingRoutingRuleService sterlingRoutingRuleService;

    private final ObjectMapper mapper = new ObjectMapper();
    private final BiFunction<ApplicationEntity, MailboxEntity, MailboxModel> serialize = MailboxApplicationService::apply;

    @Autowired
    public MailboxApplicationService(ProcessService processService, MailboxService mailboxService, ApplicationService applicationService,
                                     ManageProtocolService manageProtocolService, ActivityHistoryService activityHistoryService,
                                     SterlingMailboxService sterlingMailboxService, SterlingRoutingRuleService sterlingRoutingRuleService) {
        this.processService = processService;
        this.mailboxService = mailboxService;
        this.applicationService = applicationService;
        this.manageProtocolService = manageProtocolService;
        this.activityHistoryService = activityHistoryService;
        this.sterlingMailboxService = sterlingMailboxService;
        this.sterlingRoutingRuleService = sterlingRoutingRuleService;
    }


    private static MailboxModel apply(ApplicationEntity applicationEntity, MailboxEntity mailboxEntity) {
        MailboxModel mailboxModel = new MailboxModel();
        mailboxModel.setPkId(applicationEntity.getPkId());
        mailboxModel.setProfileName(applicationEntity.getApplicationName());
        mailboxModel.setProfileId(applicationEntity.getApplicationId());
        mailboxModel.setProtocol(applicationEntity.getAppIntegrationProtocol());
        mailboxModel.setEmailId(applicationEntity.getEmailId());
        mailboxModel.setPhone(applicationEntity.getPhone());
        mailboxModel.setStatus(convertStringToBoolean(applicationEntity.getAppIsActive()));
        mailboxModel.setPgpInfo(applicationEntity.getPgpInfo());
        mailboxModel.setInMailBox(mailboxEntity.getInMailbox());
        mailboxModel.setOutMailBox(mailboxEntity.getOutMailbox());
        mailboxModel.setFilter(mailboxEntity.getFilter());
        mailboxModel.setPoolingInterval(mailboxEntity.getPoolingIntervalMins());
        mailboxModel.setHubInfo(convertStringToBoolean(mailboxEntity.getIsHubInfo()));
        mailboxModel.setPemIdentifier(applicationEntity.getPemIdentifier());
        mailboxModel.setCreateMailBoxInSI(TRUE);
        return mailboxModel;
    }

    private void duplicateApplication(String applicationId) {
        applicationService.find(applicationId).ifPresent(applicationEntity -> {
            throw conflict("Application");
        });
    }

    @Transactional
    public String save(MailboxModel mailboxModel) {
        validate(mailboxModel, false);
        if (!Optional.ofNullable(Protocol.findProtocol(mailboxModel.getProtocol())).isPresent()) {
            throw unknownProtocol();
        }
        duplicateApplication(mailboxModel.getProfileId());
        String parentPrimaryKey = KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.APP_PKEY_PRE_APPEND,
                PCMConstants.APP_PKEY_RANDOM_COUNT);
        String childPrimaryKey = KeyGeneratorUtil.getChildPrimaryKey(mailboxModel.getProtocol());
        saveProtocol(mailboxModel, parentPrimaryKey, childPrimaryKey, mailboxModel.getProfileName(), "");
        ApplicationModel applicationModel = new ApplicationModel();
        BeanUtils.copyProperties(mailboxModel, applicationModel);
        applicationService.save(applicationModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.saveApplicationActivity(parentPrimaryKey, "Application created.");
        return parentPrimaryKey;
    }

    @Transactional
    public void update(MailboxModel mailboxModel) {
        validate(mailboxModel, false);
        ApplicationEntity oldApplicationEntity = SerializationUtils.clone(applicationService.get(mailboxModel.getPkId()));
        if (!oldApplicationEntity.getApplicationId().equalsIgnoreCase(mailboxModel.getProfileId())) {
            duplicateApplication(mailboxModel.getProfileId());
        }
        String parentPrimaryKey = oldApplicationEntity.getPkId();
        String childPrimaryKey = oldApplicationEntity.getAppProtocolRef();
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(oldApplicationEntity.getAppIntegrationProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
        Map<String, String> oldMailboxEntityMap = new LinkedHashMap<>();
        boolean isUpdate = true;
        if (!protocol.getProtocol().equalsIgnoreCase(mailboxModel.getProtocol())) {
            manageProtocolService.deleteProtocol(oldApplicationEntity.getAppIntegrationProtocol(), oldApplicationEntity.getPkId());
            childPrimaryKey = getChildPrimaryKey(mailboxModel.getProtocol());
            isUpdate = false;
        } else {
            oldMailboxEntityMap = mapper.convertValue(mailboxService.get(parentPrimaryKey),
                    new TypeReference<Map<String, String>>() {
                    });
        }
        Map<String, String> newMailboxEntityMap =
                mapper.convertValue(saveProtocol(mailboxModel, parentPrimaryKey, childPrimaryKey, oldApplicationEntity.getApplicationName(), oldMailboxEntityMap.get("poolingIntervalMins")),
                        new TypeReference<Map<String, String>>() {
                        });
        ApplicationModel applicationModel = new ApplicationModel();
        BeanUtils.copyProperties(mailboxModel, applicationModel);
        ApplicationEntity newApplicationEntity = applicationService.save(applicationModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.updateApplicationActivity(oldApplicationEntity, newApplicationEntity, oldMailboxEntityMap, newMailboxEntityMap, isUpdate);
    }

    public MailboxModel get(String pkId) {
        ApplicationEntity applicationEntity = applicationService.get(pkId);
        MailboxEntity mailboxEntity = mailboxService.get(pkId);
        return serialize.apply(applicationEntity, mailboxEntity);
    }

    @Transactional
    public void delete(String pkId) {
        if (!processService.findByApplicationProfile(pkId).isEmpty()) {
            throw internalServerError("Unable to Delete this Application, Because it has workflow.");
        }
        ApplicationEntity applicationEntity = applicationService.get(pkId);
        mailboxService.delete(pkId);
        applicationService.delete(applicationEntity);
    }

    private MailboxEntity saveProtocol(MailboxModel mailboxModel, String parentPrimaryKey, String childPrimaryKey, String oldProfileName, String oldPollingInterval) {
        mailboxModel.setSubscriberType(APP);
        if (mailboxModel.getCreateMailBoxInSI() || mailboxModel.getPoolingInterval().equalsIgnoreCase("ON")) {
            sterlingMailboxService.createAll(SterlingFunctions.convertMailboxModelToSterlingMailBoxModels.apply(mailboxModel));
        }
        if (mailboxModel.getPoolingInterval().equalsIgnoreCase("ON")) {
            sterlingRoutingRuleService.save(mailboxModel.getOutMailBox(), mailboxModel.getProfileName(), oldProfileName, mailboxModel.getSubscriberType(),null,mailboxModel.getRoutingRuleName());
        } else {
            if (isNotNull(oldPollingInterval) && oldPollingInterval.equalsIgnoreCase("ON")) {
                sterlingRoutingRuleService.delete(oldProfileName);
            }
        }
        return mailboxService.saveProtocol(mailboxModel, parentPrimaryKey, childPrimaryKey, APP);
    }
}
