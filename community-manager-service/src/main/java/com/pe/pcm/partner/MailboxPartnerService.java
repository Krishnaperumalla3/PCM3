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

package com.pe.pcm.partner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.enums.Protocol;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.miscellaneous.UserUtilityService;
import com.pe.pcm.partner.entity.PartnerEntity;
import com.pe.pcm.protocol.MailboxModel;
import com.pe.pcm.protocol.MailboxService;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.function.SterlingFunctions;
import com.pe.pcm.protocol.mailbox.entity.MailboxEntity;
import com.pe.pcm.sterling.mailbox.SterlingMailboxService;
import com.pe.pcm.sterling.mailbox.routingrule.SterlingRoutingRuleService;
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
import static com.pe.pcm.utils.KeyGeneratorUtil.getPrimaryKey;
import static com.pe.pcm.utils.PCMConstants.*;
import static java.lang.Boolean.TRUE;

/**
 * @author Kiran Reddy.
 */
@Service
public class MailboxPartnerService {

    private final PartnerService partnerService;
    private final MailboxService mailboxService;
    private final ProcessService processService;
    private final UserUtilityService userUtilityService;
    private final ManageProtocolService manageProtocolService;
    private final ActivityHistoryService activityHistoryService;
    private final SterlingMailboxService sterlingMailboxService;
    private final SterlingRoutingRuleService sterlingRoutingRuleService;

    private final ObjectMapper mapper = new ObjectMapper();

    private final BiFunction<PartnerEntity, MailboxEntity, MailboxModel> serialize = MailboxPartnerService::apply;

    @Autowired
    public MailboxPartnerService(PartnerService partnerService,
                                 MailboxService mailboxService,
                                 ProcessService processService,
                                 UserUtilityService userUtilityService,
                                 ManageProtocolService manageProtocolService,
                                 ActivityHistoryService activityHistoryService,
                                 SterlingMailboxService sterlingMailboxService,
                                 SterlingRoutingRuleService sterlingRoutingRuleService) {
        this.partnerService = partnerService;
        this.mailboxService = mailboxService;
        this.processService = processService;
        this.userUtilityService = userUtilityService;
        this.manageProtocolService = manageProtocolService;
        this.activityHistoryService = activityHistoryService;
        this.sterlingMailboxService = sterlingMailboxService;
        this.sterlingRoutingRuleService = sterlingRoutingRuleService;
    }

    @Transactional
    public String save(MailboxModel mailboxModel) {
        validate(mailboxModel, true);
        if (!Optional.ofNullable(Protocol.findProtocol(mailboxModel.getProtocol())).isPresent()) {
            throw unknownProtocol();
        }
        duplicatePartner(mailboxModel.getProfileId());
        String parentPrimaryKey = getPrimaryKey.apply(TP_PKEY_PRE_APPEND, TP_PKEY_RANDOM_COUNT);
        String childPrimaryKey = getChildPrimaryKey(mailboxModel.getProtocol());
        saveProtocol(mailboxModel, parentPrimaryKey, childPrimaryKey, mailboxModel.getProfileName(), "");
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(mailboxModel, partnerModel);
        partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.savePartnerActivity(parentPrimaryKey, "Trading Partner created.");
        userUtilityService.addPartnerToCurrentUser(parentPrimaryKey, TP);
        return parentPrimaryKey;
    }

    @Transactional
    public void update(MailboxModel mailboxModel) {
        validate(mailboxModel, true);
        PartnerEntity oldPartnerEntity = SerializationUtils.clone(partnerService.get(mailboxModel.getPkId()));
        if (!oldPartnerEntity.getTpId().equalsIgnoreCase(mailboxModel.getProfileId())) {
            duplicatePartner(mailboxModel.getProfileId());
        }
        String parentPrimaryKey = oldPartnerEntity.getPkId();
        String childPrimaryKey = oldPartnerEntity.getPartnerProtocolRef();
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(oldPartnerEntity.getTpProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);

        Map<String, String> oldMailboxEntityMap = new LinkedHashMap<>();
        boolean isUpdate = true;

        if (!protocol.getProtocol().equalsIgnoreCase(mailboxModel.getProtocol())) {
            manageProtocolService.deleteProtocol(oldPartnerEntity.getTpProtocol(), oldPartnerEntity.getPkId());
            childPrimaryKey = getChildPrimaryKey(mailboxModel.getProtocol());
            isUpdate = false;
        } else {
            oldMailboxEntityMap = mapper.convertValue(mailboxService.get(parentPrimaryKey), new TypeReference<Map<String, String>>() {
            });
        }

        Map<String, String> newHttpEntityMap =
                mapper.convertValue(saveProtocol(mailboxModel, parentPrimaryKey, childPrimaryKey,
                        oldPartnerEntity.getTpName(), oldMailboxEntityMap.get("poolingIntervalMins")),
                        new TypeReference<Map<String, String>>() {
                        }
                );
        mailboxModel.setPemIdentifier(isNotNull(mailboxModel.getPemIdentifier())
                ? mailboxModel.getPemIdentifier() : oldPartnerEntity.getPemIdentifier());
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(mailboxModel, partnerModel);
        PartnerEntity newPartnerEntity = partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.updatePartnerActivity(oldPartnerEntity, newPartnerEntity, oldMailboxEntityMap, newHttpEntityMap, isUpdate);
    }

    public MailboxModel get(String pkId) {
        PartnerEntity partnerEntity = partnerService.get(pkId);
        MailboxEntity mailboxEntity = mailboxService.get(pkId);
        return serialize.apply(partnerEntity, mailboxEntity);
    }

    @Transactional
    public void delete(String pkId) {
        if (!processService.findByPartnerProfile(pkId).isEmpty()) {
            throw internalServerError("Unable to delete this partner, Because it has workflow.");
        }
        PartnerEntity partnerEntity = partnerService.findPartnerById(pkId);
        mailboxService.delete(pkId);
        partnerService.delete(partnerEntity);
    }

    private MailboxEntity saveProtocol(MailboxModel mailboxModel, String parentPrimaryKey, String childPrimaryKey, String oldProfileName, String oldPollingInterval) {
        mailboxModel.setSubscriberType(TP);
        if (mailboxModel.getCreateMailBoxInSI() || mailboxModel.getPoolingInterval().equalsIgnoreCase("ON")) {
            sterlingMailboxService.createAll(SterlingFunctions.convertMailboxModelToSterlingMailBoxModels.apply(mailboxModel));
        }
        if (mailboxModel.getPoolingInterval().equalsIgnoreCase("ON")) {
            sterlingRoutingRuleService.save(mailboxModel.getInMailBox(), mailboxModel.getProfileName(), oldProfileName, mailboxModel.getSubscriberType(), null, mailboxModel.getRoutingRuleName());
        } else {
            if (isNotNull(oldPollingInterval) && oldPollingInterval.equalsIgnoreCase("ON")) {
                sterlingRoutingRuleService.delete(oldProfileName);
            }
        }
        return mailboxService.saveProtocol(mailboxModel, parentPrimaryKey, childPrimaryKey, TP);
    }

    private static MailboxModel apply(PartnerEntity partnerEntity, MailboxEntity mailboxEntity) {
        MailboxModel mailboxModel = new MailboxModel();
        mailboxModel.setPkId(partnerEntity.getPkId())
                .setProfileName(partnerEntity.getTpName())
                .setProfileId(partnerEntity.getTpId())
                .setPkId(partnerEntity.getPkId())
                .setPemIdentifier(partnerEntity.getPemIdentifier())
                .setAddressLine1(partnerEntity.getAddressLine1())
                .setAddressLine2(partnerEntity.getAddressLine2())
                .setProtocol(partnerEntity.getTpProtocol())
                .setHubInfo(convertStringToBoolean(mailboxEntity.getIsHubInfo()))
                .setEmailId(partnerEntity.getEmail())
                .setPhone(partnerEntity.getPhone())
                .setStatus(convertStringToBoolean(partnerEntity.getStatus()))
                .setPgpInfo(partnerEntity.getPgpInfo());

        return mailboxModel.setInMailBox(mailboxEntity.getInMailbox())
                .setOutMailBox(mailboxEntity.getOutMailbox())
                .setFilter(mailboxEntity.getFilter())
                .setPoolingInterval(mailboxEntity.getPoolingIntervalMins())
                .setCreateMailBoxInSI(TRUE);
    }

    void duplicatePartner(String partnerId) {
        Optional<PartnerEntity> tradingPartnerEntityOptional = partnerService.find(partnerId);
        tradingPartnerEntityOptional.ifPresent(tradingPartnerEntity -> {
            throw conflict("Partner");
        });
    }
}
