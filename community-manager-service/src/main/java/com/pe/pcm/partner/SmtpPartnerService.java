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
import com.pe.pcm.miscellaneous.UserUtilityService;
import com.pe.pcm.partner.entity.PartnerEntity;
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
import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.KeyGeneratorUtil.getChildPrimaryKey;
import static com.pe.pcm.utils.PCMConstants.TP;

/**
 * @author Shameer.
 */

@Service
public class SmtpPartnerService {

    private final PartnerService partnerService;
    private final ActivityHistoryService activityHistoryService;
    private final ObjectMapper mapper = new ObjectMapper();
    private final SmtpService smtpService;
    private final ProcessService processService;
    private final ManageProtocolService manageProtocolService;
    private final UserUtilityService userUtilityService;

    private final BiFunction<PartnerEntity, SmtpEntity, SmtpModel> serialize = SmtpPartnerService::apply;

    @Autowired
    public SmtpPartnerService(PartnerService partnerService,
                              ActivityHistoryService activityHistoryService,
                              SmtpService smtpService,
                              ProcessService processService,
                              ManageProtocolService manageProtocolService,
                              UserUtilityService userUtilityService) {
        this.partnerService = partnerService;
        this.activityHistoryService = activityHistoryService;
        this.smtpService = smtpService;
        this.processService = processService;
        this.manageProtocolService = manageProtocolService;
        this.userUtilityService = userUtilityService;
    }

    private void duplicatePartner(String partnerId) {
        Optional<PartnerEntity> tradingPartnerEntityOptional = partnerService.find(partnerId);
        tradingPartnerEntityOptional.ifPresent(tradingPartnerEntity -> {
            throw conflict("Partner");
        });
    }

    @Transactional
    public String save(SmtpModel smtpModel) {
        if (!Optional.ofNullable(Protocol.findProtocol(smtpModel.getProtocol())).isPresent()) {
            throw unknownProtocol();
        }
        duplicatePartner(smtpModel.getProfileId());
        String parentPrimaryKey = KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.TP_PKEY_PRE_APPEND, PCMConstants.TP_PKEY_RANDOM_COUNT);
        String childPrimaryKey = KeyGeneratorUtil.getChildPrimaryKey(smtpModel.getProtocol());
        saveProtocol(smtpModel, parentPrimaryKey, childPrimaryKey);
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(smtpModel, partnerModel);
        partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.savePartnerActivity(parentPrimaryKey, "Trading Partner created.");
        userUtilityService.addPartnerToCurrentUser(parentPrimaryKey, TP);
        return parentPrimaryKey;
    }

    @Transactional
    public void update(SmtpModel smtpModel) {
        PartnerEntity oldPartnerEntity = SerializationUtils.clone(partnerService.get(smtpModel.getPkId()));
        if (!oldPartnerEntity.getTpId().equalsIgnoreCase(smtpModel.getProfileId())) {
            duplicatePartner(smtpModel.getProfileId());
        }
        String parentPrimaryKey = oldPartnerEntity.getPkId();
        String childPrimaryKey = oldPartnerEntity.getPartnerProtocolRef();
        Optional<Protocol> protocolOptional = Optional.ofNullable(Protocol.findProtocol(smtpModel.getProtocol()));
        if (!protocolOptional.isPresent()) {
            throw unknownProtocol();
        }
        Protocol protocol = protocolOptional.get();
        Map<String, String> oldSmtpEntityMap = new LinkedHashMap<>();
        boolean isUpdate = true;
        if (!protocol.getProtocol().equalsIgnoreCase(oldPartnerEntity.getTpProtocol())) {
            manageProtocolService.deleteProtocol(oldPartnerEntity.getTpProtocol(), oldPartnerEntity.getPkId());
            childPrimaryKey = getChildPrimaryKey(smtpModel.getProtocol());
            isUpdate = false;
        } else {
            oldSmtpEntityMap = mapper.convertValue(smtpService.get(parentPrimaryKey), new TypeReference<Map<String, String>>() {
            });
        }
        Map<String, String> newSmtpEntityMap = mapper.convertValue(saveProtocol(smtpModel, parentPrimaryKey, childPrimaryKey), new TypeReference<Map<String, String>>() {
        });
        smtpModel.setPemIdentifier(isNotNull(smtpModel.getPemIdentifier())
                ? smtpModel.getPemIdentifier() : oldPartnerEntity.getPemIdentifier());
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(smtpModel, partnerModel);
        PartnerEntity newPartnerEntity = partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.updatePartnerActivity(oldPartnerEntity, newPartnerEntity, oldSmtpEntityMap, newSmtpEntityMap, isUpdate);
    }

    @Transactional
    public void delete(String pkId) {
        if (!processService.findByPartnerProfile(pkId).isEmpty()) {
            throw internalServerError("Unable to delete this partner, Because it has workflow..");
        }
        PartnerEntity partnerEntity = partnerService.findPartnerById(pkId);
        smtpService.delete(pkId);
        partnerService.delete(partnerEntity);
    }

    public SmtpModel get(String pkId) {
        PartnerEntity partnerEntity = partnerService.get(pkId);
        SmtpEntity smtpEntity = smtpService.get(pkId);
        return serialize.apply(partnerEntity, smtpEntity);
    }

    public SmtpEntity saveProtocol(SmtpModel smtpModel, String parentPrimaryKey, String childPrimaryKey) {
        return smtpService.saveProtocol(smtpModel, parentPrimaryKey, childPrimaryKey, "TP");
    }


    private static SmtpModel apply(PartnerEntity tradingPartner, SmtpEntity smtpEntity) {
        SmtpModel smtpModel = new SmtpModel();
        smtpModel.setPkId(tradingPartner.getPkId())
                .setProfileName(tradingPartner.getTpName())
                .setProfileId(tradingPartner.getTpId())
                .setEmailId(tradingPartner.getEmail())
                .setPhone(tradingPartner.getPhone())
                .setAddressLine1(tradingPartner.getAddressLine1())
                .setAddressLine2(tradingPartner.getAddressLine2())
                .setProtocol(tradingPartner.getTpProtocol())
                .setStatus(convertStringToBoolean(tradingPartner.getStatus()))
                .setPgpInfo(tradingPartner.getPgpInfo())
                .setPemIdentifier(tradingPartner.getPemIdentifier());

        return smtpModel.setName(smtpEntity.getName())
                .setAccessProtocol(smtpEntity.getAccessProtocol())
                .setMailServer(smtpEntity.getMailServer())
                .setMailServerPort(smtpEntity.getMailServerPort())
                .setUserName(smtpEntity.getUserName())
                .setPassword(smtpEntity.getPassword())
                .setConnectionRetries(smtpEntity.getConnectionRetries())
                .setRetryInterval(smtpEntity.getRetryInterval())
                .setMaxMsgsSession(smtpEntity.getMaxMsgsSession())
                .setRemoveMailMsgs(smtpEntity.getRemoveInboxMailMsgs())
                .setSsl(smtpEntity.getSsl())
                .setKeyCertPassPhrase(smtpEntity.getkeyCertPassPhrase())
                .setCipherStrength(smtpEntity.getCipherStrength())
                .setKeyCert(smtpEntity.getKeyCert())
                .setCaCertificates(smtpEntity.getCaCertificates())
                .setUriName(smtpEntity.getUriName())
                .setPoolingInterval(smtpEntity.getPoolingInterval());
    }
}
