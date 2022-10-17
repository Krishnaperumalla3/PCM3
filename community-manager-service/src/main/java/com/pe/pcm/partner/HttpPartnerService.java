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
import com.pe.pcm.partner.entity.PartnerEntity;
import com.pe.pcm.protocol.HttpModel;
import com.pe.pcm.protocol.HttpService;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.function.SterlingFunctions;
import com.pe.pcm.protocol.http.entity.HttpEntity;
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
import static com.pe.pcm.utils.PCMConstants.TP;

/**
 * @author Kiran Reddy.
 */
@Service
public class HttpPartnerService {


    private final HttpService httpService;
    private final ProcessService processService;
    private final PartnerService partnerService;
    private final ActivityHistoryService activityHistoryService;
    private final ManageProtocolService manageProtocolService;
    private final SterlingMailboxService sterlingMailboxService;
    private final SterlingRoutingRuleService sterlingRoutingRuleService;

    private final ObjectMapper mapper = new ObjectMapper();
    private final BiFunction<PartnerEntity, HttpEntity, HttpModel> serialize = HttpPartnerService::apply;

    public HttpPartnerService(HttpService httpService,
                              ProcessService processService,
                              PartnerService partnerService,
                              ActivityHistoryService activityHistoryService,
                              ManageProtocolService manageProtocolService,
                              SterlingMailboxService sterlingMailboxService,
                              SterlingRoutingRuleService sterlingRoutingRuleService) {
        this.httpService = httpService;
        this.processService = processService;
        this.partnerService = partnerService;
        this.activityHistoryService = activityHistoryService;
        this.manageProtocolService = manageProtocolService;
        this.sterlingMailboxService = sterlingMailboxService;
        this.sterlingRoutingRuleService = sterlingRoutingRuleService;
    }

    private static HttpModel apply(PartnerEntity tradingPartner, HttpEntity httpEntity) {
        HttpModel httpModel = new HttpModel();
        httpModel.setPkId(tradingPartner.getPkId())
                .setProfileName(tradingPartner.getTpName())
                .setProfileId(tradingPartner.getTpId())
                .setAddressLine1(tradingPartner.getAddressLine1())
                .setAddressLine2(tradingPartner.getAddressLine2())
                .setProtocol(tradingPartner.getTpProtocol())
                .setHubInfo(convertStringToBoolean(httpEntity.getIsHubInfo()))
                .setEmailId(tradingPartner.getEmail())
                .setPhone(tradingPartner.getPhone())
                .setStatus(convertStringToBoolean(tradingPartner.getStatus()))
                .setPgpInfo(tradingPartner.getPgpInfo())
                .setPemIdentifier(tradingPartner.getPemIdentifier());

        return httpModel.setAdapterName(httpEntity.getAdapterName())
                .setPoolingInterval(httpEntity.getPoolingIntervalMins())
                .setCertificate(httpEntity.getCertificate())
                .setInMailBox(httpEntity.getInMailbox())
                .setOutBoundUrl(httpEntity.getOutboundUrl());
    }

    private void duplicatePartner(String partnerId) {
        Optional<PartnerEntity> tradingPartnerEntityOptional = partnerService.find(partnerId);
        tradingPartnerEntityOptional.ifPresent(tradingPartnerEntity -> {
            throw conflict("Partner");
        });
    }

    @Transactional
    public String save(HttpModel httpModel) {
        if (!Optional.ofNullable(Protocol.findProtocol(httpModel.getProtocol())).isPresent()) {
            throw unknownProtocol();
        }
        duplicatePartner(httpModel.getProfileId());
        String parentPrimaryKey = KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.TP_PKEY_PRE_APPEND,
                PCMConstants.TP_PKEY_RANDOM_COUNT);
        String childPrimaryKey = KeyGeneratorUtil.getChildPrimaryKey(httpModel.getProtocol());
        saveProtocol(httpModel, parentPrimaryKey, childPrimaryKey, httpModel.getProfileName(), "");
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(httpModel, partnerModel);
        partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.savePartnerActivity(parentPrimaryKey, "Trading Partner created.");
        return parentPrimaryKey;
    }

    @Transactional
    public void update(HttpModel httpModel) {
        PartnerEntity oldPartnerEntity = SerializationUtils.clone(partnerService.get(httpModel.getPkId()));
        if (!oldPartnerEntity.getTpId().equalsIgnoreCase(httpModel.getProfileId())) {
            duplicatePartner(httpModel.getProfileId());
        }
        String parentPrimaryKey = oldPartnerEntity.getPkId();
        String childPrimaryKey = oldPartnerEntity.getPartnerProtocolRef();
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(oldPartnerEntity.getTpProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);

        Map<String, String> oldHttpEntityMap = new LinkedHashMap<>();
        boolean isUpdate = true;
        if (!protocol.getProtocol().equalsIgnoreCase(httpModel.getProtocol())) {
            manageProtocolService.deleteProtocol(oldPartnerEntity.getTpProtocol(), oldPartnerEntity.getPkId());
            childPrimaryKey = getChildPrimaryKey(httpModel.getProtocol());
            isUpdate = false;
        } else {
            oldHttpEntityMap = mapper.convertValue(httpService.get(parentPrimaryKey), new TypeReference<Map<String, String>>() {
            });
        }

        Map<String, String> newHttpEntityMap =
                mapper.convertValue(saveProtocol(httpModel, parentPrimaryKey, childPrimaryKey, oldPartnerEntity.getTpName(), oldHttpEntityMap.get("poolingIntervalMins")),
                        new TypeReference<Map<String, String>>() {
                        });
        httpModel.setPemIdentifier(isNotNull(httpModel.getPemIdentifier())
                ? httpModel.getPemIdentifier() : oldPartnerEntity.getPemIdentifier());
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(httpModel, partnerModel);
        PartnerEntity newPartnerEntity = partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);

        activityHistoryService.updatePartnerActivity(oldPartnerEntity, newPartnerEntity, oldHttpEntityMap, newHttpEntityMap, isUpdate);
    }

    public HttpModel get(String pkId) {
        return serialize.apply(partnerService.get(pkId), httpService.get(pkId));
    }

    @Transactional
    public void delete(String pkId) {
        if (!processService.findByPartnerProfile(pkId).isEmpty()) {
            throw internalServerError("Unable to delete this partner, Because it has workflow.");
        }
        PartnerEntity partnerEntity = partnerService.findPartnerById(pkId);
        partnerService.delete(partnerEntity);
        httpService.delete(pkId);
    }

    private HttpEntity saveProtocol(HttpModel httpModel, String parentPrimaryKey, String childPrimaryKey, String oldProfileName, String oldPollingInterval) {

        sterlingMailboxService.createAll(SterlingFunctions.convertHttpModelToSterlingMailBoxModels.apply(httpModel));
        if (isNotNull(httpModel.getInMailBox()) && httpModel.getPoolingInterval().equalsIgnoreCase("ON")) {
            sterlingRoutingRuleService.save(httpModel.getInMailBox(), httpModel.getProfileName(), oldProfileName, "TP", null, httpModel.getRoutingRuleName());
        } else {
            if (isNotNull(oldPollingInterval) && oldPollingInterval.equalsIgnoreCase("ON")) {
                sterlingRoutingRuleService.delete(oldProfileName);
            }
        }
        return httpService.saveProtocol(httpModel, parentPrimaryKey, childPrimaryKey, TP);
    }

}
