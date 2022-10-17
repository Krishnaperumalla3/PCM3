/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc.
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

package com.pe.pcm.sterling.mailbox.routingrule;

import com.pe.pcm.sterling.WfdRepository;
import com.pe.pcm.sterling.mailbox.SterlingMailboxService;
import com.pe.pcm.sterling.mailbox.routingrule.entity.*;
import com.pe.pcm.sterling.mailbox.routingrule.entity.identity.MbxMatchMbxIdentity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.KeyGeneratorUtil.getPrimaryKey;
import static com.pe.pcm.utils.PCMConstants.TP;

/**
 * @author Kiran Reddy.
 */
@Service
public class SterlingRoutingRuleService {

    private String inboundCoreBpName;
    private String outboundCoreBpName;
    private final String username;
    private final WfdRepository wfdRepository;
    private final MbxRuleRepository mbxRuleRepository;
    private final MbxActionRepository mbxActionRepository;
    private final MbxPatternRepository mbxPatternRepository;
    private final MbxMatchMbxRepository mbxMatchMbxRepository;
    private final SterlingMailboxService sterlingMailboxService;
    private final MbxNewProcActRepository mbxNewProcActRepository;
    private final MbxRuleDescriptionRepository mbxRuleDescriptionRepository;

    public SterlingRoutingRuleService(@Value("${sterling-b2bi.core-bp.inbound}") String inboundCoreBpName, @Value("${sterling-b2bi.core-bp.outbound}") String outboundCoreBpName, @Value("${sterling-b2bi.b2bi-api.api.username}") String username,
                                      WfdRepository wfdRepository, MbxRuleRepository mbxRuleRepository, MbxActionRepository mbxActionRepository,
                                      MbxPatternRepository mbxPatternRepository, SterlingMailboxService sterlingMailboxService, MbxMatchMbxRepository mbxMatchMbxRepository,
                                      MbxNewProcActRepository mbxNewProcActRepository, MbxRuleDescriptionRepository mbxRuleDescriptionRepository) {
        this.inboundCoreBpName = inboundCoreBpName;
        this.username = username;
        this.wfdRepository = wfdRepository;
        this.mbxRuleRepository = mbxRuleRepository;
        this.mbxActionRepository = mbxActionRepository;
        this.mbxPatternRepository = mbxPatternRepository;
        this.sterlingMailboxService = sterlingMailboxService;
        this.mbxMatchMbxRepository = mbxMatchMbxRepository;
        this.mbxNewProcActRepository = mbxNewProcActRepository;
        this.outboundCoreBpName = outboundCoreBpName;
        this.mbxRuleDescriptionRepository = mbxRuleDescriptionRepository;
    }

    public void save(String mailbox,
                     String profileName,
                     String oldProfileName,
                     String subscriberType,
                     String bpName,
                     String routingRule) {
        String ruleId;
        String actionName = getBusinessProcessName(bpName, subscriberType);
        Optional<MbxRuleDescriptionEntity> mbxRuleDescriptionEntityOptional = mbxRuleDescriptionRepository.findFirstByDescription(isNotNull(routingRule) ? routingRule : oldProfileName);
        if (mbxRuleDescriptionEntityOptional.isPresent()) {
            ruleId = mbxRuleDescriptionEntityOptional.get().getRuleId();
            if (!isNotNull(routingRule)) {
                mbxMatchMbxRepository.deleteAllByMbxMatchMbxIdentityRuleId(ruleId);
            }
        } else {
            ruleId = getPrimaryKey.apply("CM-", 23);
        }
        mbxRuleRepository.save(mbxRuleEntitySerialization.apply(ruleId));
        mbxMatchMbxRepository.save(mbxMatchMbxEntitySerialization.apply(ruleId, sterlingMailboxService.getMailboxId(mailbox)));
        mbxRuleDescriptionRepository.save(mbxRuleDescriptionEntitySerialization.apply(ruleId, profileName));
        mbxActionRepository.save(mbxActionEntitySerialization.apply(ruleId, username));
        mbxNewProcActRepository.save(mbxNewProcActEntitySerialization.apply(ruleId, actionName));
        mbxPatternRepository.save(mbxPatternEntitySerialization.apply(ruleId));
    }

    public void delete(String profileName) {
        if (isNotNull(profileName)) {
            mbxRuleDescriptionRepository.findFirstByDescription(profileName).ifPresent(mbxRuleDescriptionEntity -> {
                mbxRuleRepository.deleteById(mbxRuleDescriptionEntity.getRuleId());
                mbxMatchMbxRepository.deleteAllByMbxMatchMbxIdentityRuleId(mbxRuleDescriptionEntity.getRuleId());
                mbxActionRepository.deleteById(mbxRuleDescriptionEntity.getRuleId());
                mbxNewProcActRepository.deleteById(mbxRuleDescriptionEntity.getRuleId());
                mbxRuleDescriptionRepository.delete(mbxRuleDescriptionEntity);
                mbxPatternRepository.deleteById(mbxRuleDescriptionEntity.getRuleId());
            });
        }
    }

    private final Function<String, MbxPatternEntity> mbxPatternEntitySerialization = ruleId ->
            new MbxPatternEntity().setRuleId(ruleId).setMsgNamePat("%");

    private final BiFunction<String, String, MbxRuleDescriptionEntity> mbxRuleDescriptionEntitySerialization = (ruleId, description) ->
            new MbxRuleDescriptionEntity().setRuleId(ruleId).setDescription(description);

    private final BiFunction<String, Long, MbxMatchMbxEntity> mbxMatchMbxEntitySerialization = (ruleId, mailboxId) ->
            new MbxMatchMbxEntity().setMbxMatchMbxIdentity(new MbxMatchMbxIdentity().setRuleId(ruleId).setMailboxId(mailboxId));

    private final BiFunction<String, String, MbxActionEntity> mbxActionEntitySerialization = (ruleId, userName) ->
            new MbxActionEntity().setRuleId(ruleId).setUserId(userName);

    private final Function<String, MbxRuleEntity> mbxRuleEntitySerialization = ruleId ->
            new MbxRuleEntity().setRuleId(ruleId).setEvaluateAsap(1);

    private final BiFunction<String, String, MbxNewProcActEntity> mbxNewProcActEntitySerialization = (ruleId, bpName) ->
            new MbxNewProcActEntity().setRuleId(ruleId).setProcessName(isNotNull(bpName) ? bpName : inboundCoreBpName);

    private String getBusinessProcessName(String bpName, String subscriberType) {
        String name = isNotNull(bpName) ? bpName : (subscriberType.equals(TP) ? inboundCoreBpName : outboundCoreBpName);
        return wfdRepository.findFirstByName(name).orElseThrow(() -> internalServerError("Provided BP Name not available , BpName: " + name)).getName();
    }

    @PostConstruct
    public void getCoreBpName() {
        if (!isNotNull(inboundCoreBpName)) {
            inboundCoreBpName = "CM_MailBox_GET_RoutingRule_Inbound";
        }
        if (!isNotNull(outboundCoreBpName)) {
            outboundCoreBpName = "CM_MailBox_GET_RoutingRule_Outbound";
        }
    }
}
