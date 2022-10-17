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
import com.pe.pcm.b2b.B2BApiService;
import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.enums.Protocol;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.partner.entity.PartnerEntity;
import com.pe.pcm.protocol.CustomProtocolModel;
import com.pe.pcm.protocol.CustomProtocolService;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.customprotocol.entity.CustomProtocolEntity;
import com.pe.pcm.workflow.ProcessService;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static com.pe.pcm.exception.GlobalExceptionHandler.*;
import static com.pe.pcm.utils.CommonFunctions.convertStringToBoolean;
import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.KeyGeneratorUtil.getChildPrimaryKey;
import static com.pe.pcm.utils.KeyGeneratorUtil.getPrimaryKey;
import static com.pe.pcm.utils.PCMConstants.TP_PKEY_PRE_APPEND;
import static com.pe.pcm.utils.PCMConstants.TP_PKEY_RANDOM_COUNT;

/**
 * @author Shameer.v.
 */

@Service
public class CustomProtocolPartnerService {

    private final PartnerService partnerService;
    private final B2BApiService b2BApiService;
    private final ManageProtocolService manageProtocolService;
    private final CustomProtocolService customProtocolService;
    private final ActivityHistoryService activityHistoryService;
    private final ProcessService processService;
    private String communityName;
    private final ObjectMapper mapper = new ObjectMapper();
    private final BiFunction<PartnerEntity, CustomProtocolEntity, CustomProtocolModel> serialize = CustomProtocolPartnerService::apply;
    private final Logger logger = LoggerFactory.getLogger(CustomProtocolPartnerService.class);

    @Autowired
    public CustomProtocolPartnerService(@Value("${sterling-b2bi.b2bi-api.b2bi-sfg-api.community-name}") String communityName,
                                        PartnerService partnerService,
                                        B2BApiService b2BApiService,
                                        ManageProtocolService manageProtocolService,
                                        CustomProtocolService customProtocolService,
                                        ActivityHistoryService activityHistoryService,
                                        ProcessService processService) {
        this.partnerService = partnerService;
        this.b2BApiService = b2BApiService;
        this.communityName = communityName;
        this.manageProtocolService = manageProtocolService;
        this.customProtocolService = customProtocolService;
        this.activityHistoryService = activityHistoryService;
        this.processService = processService;
    }

    private void duplicatePartner(String partnerId) {
        Optional<PartnerEntity> tradingPartnerEntityOptional = partnerService.find(partnerId);
        tradingPartnerEntityOptional.ifPresent(tradingPartnerEntity -> {
            throw conflict("Partner");
        });
    }

    @Transactional
    public void save(CustomProtocolModel customProtocolModel) {
        logger.info("In Custom protocol Partner Profile save() Method.");
        customProtocolModel.setCommunity(communityName);
        if (!Optional.ofNullable(Protocol.findProtocol(customProtocolModel.getProtocol())).isPresent()) {
            throw unknownProtocol();
        }
        duplicatePartner(customProtocolModel.getProfileId());
        String parentPrimaryKey = getPrimaryKey.apply(TP_PKEY_PRE_APPEND, TP_PKEY_RANDOM_COUNT);
        String childPrimaryKey = getChildPrimaryKey(customProtocolModel.getProtocol());
        saveProtocol(customProtocolModel, parentPrimaryKey, childPrimaryKey);
        b2BApiService.createProxyProfile(customProtocolModel);
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(customProtocolModel, partnerModel);
        partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.savePartnerActivity(parentPrimaryKey, "Trading Partner created.");
        logger.info("Completed Custom protocol Partner Profile save() Method.");
    }

    @Transactional
    public void update(CustomProtocolModel customProtocolModel) {
        logger.info("In Custom Protocol Profile update() Method.");
        customProtocolModel.setCommunity(communityName);
        PartnerEntity oldPartnerEntity = SerializationUtils.clone(partnerService.get(customProtocolModel.getPkId()));
        if (!oldPartnerEntity.getTpId().equalsIgnoreCase(customProtocolModel.getProfileId())) {
            duplicatePartner(customProtocolModel.getProfileId());
        }
        String parentPrimaryKey = oldPartnerEntity.getPkId();
        String childPrimaryKey = oldPartnerEntity.getPartnerProtocolRef();
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(oldPartnerEntity.getTpProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
        Map<String, String> oldCpEntityMap = new LinkedHashMap<>();
        boolean isUpdate = true;
        if (!protocol.getProtocol().equalsIgnoreCase(customProtocolModel.getProtocol())) {
            manageProtocolService.deleteProtocol(oldPartnerEntity.getTpProtocol(), oldPartnerEntity.getPkId());
            childPrimaryKey = getChildPrimaryKey(customProtocolModel.getProtocol());
            isUpdate = false;
        } else {
            oldCpEntityMap = mapper.convertValue(customProtocolService.get(parentPrimaryKey),
                    new TypeReference<Map<String, String>>() {
                    });
        }
        Map<String, String> newCpEntityMap = mapper.convertValue(customProtocolService.saveProtocol(customProtocolModel, parentPrimaryKey, childPrimaryKey,
                "TP"), new TypeReference<Map<String, String>>() {
        });
        b2BApiService.updateProxyProfile(customProtocolModel, oldPartnerEntity.getTpName());
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(customProtocolModel, partnerModel);
        PartnerEntity newPartnerEntity = partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.updatePartnerActivity(oldPartnerEntity,
                newPartnerEntity, oldCpEntityMap, newCpEntityMap, isUpdate);
        logger.info("Completed Custom Protocol Profile update() Method.");
    }

    @Transactional
    public CustomProtocolModel get(String pkId) {
        return serialize.apply(partnerService.get(pkId), customProtocolService.get(pkId));
    }

    @Transactional
    public void delete(String pkId) {
        if (!processService.findByPartnerProfile(pkId).isEmpty()) {
            throw internalServerError("Unable to delete this partner, Because it has workflow.");
        }
        PartnerEntity partnerEntity = partnerService.findPartnerById(pkId);
        logger.info("Delete Custom Protocol Profile");
        customProtocolService.delete(partnerEntity.getPkId());
        partnerService.delete(partnerEntity);
        b2BApiService.deleteProxyProfile(partnerEntity.getTpName());
    }


    private void saveProtocol(CustomProtocolModel customProtocolModel, String parentPrimaryKey, String childPrimaryKey) {
        customProtocolService.saveProtocol(customProtocolModel, parentPrimaryKey, childPrimaryKey, "TP");
    }

    public static CustomProtocolModel apply(PartnerEntity tradingPartner, CustomProtocolEntity customProtocolEntity) {
        CustomProtocolModel customProtocolModel = new CustomProtocolModel();
        customProtocolModel.setAddressLine1(tradingPartner.getAddressLine1())
                .setAddressLine2(tradingPartner.getAddressLine1())
                .setEmailId(tradingPartner.getEmail())
                .setPhone(tradingPartner.getPhone())
                .setProfileName(tradingPartner.getTpName())
                .setProfileId(tradingPartner.getTpId())
                .setStatus(convertStringToBoolean(tradingPartner.getStatus()))
                .setProtocol(tradingPartner.getTpProtocol());

        return customProtocolModel
                .setAppendSuffixToUsername(convertStringToBoolean(customProtocolEntity.getAppendSuffixToUsername()))
                .setAsciiArmor(convertStringToBoolean(customProtocolEntity.getAsciiArmor()))
                .setAuthenticationHost(customProtocolEntity.getAuthenticationHost())
                .setAuthenticationType(customProtocolEntity.getAuthenticationType())
                .setAuthorizedUserKeyName(customProtocolEntity.getAuthorizedUserKeyName())
                .setCity(customProtocolEntity.getCity())
                .setCode(customProtocolEntity.getCode())
                .setCommunity(customProtocolEntity.getCommunity())
                .setCountryOrRegion(customProtocolEntity.getCountryOrRegion())
                .setCustomProtocolExtensions(customProtocolEntity.getCustomProtocolExtensions())
                .setCustomProtocolName(customProtocolEntity.getCustomProtocolName())
                .setDoesRequireCompressedData(convertStringToBoolean(customProtocolEntity.getDoesRequireCompressedData()))
                .setDoesRequireEncryptedData(convertStringToBoolean(customProtocolEntity.getDoesRequireEncryptedData()))
                .setDoesRequireSignedData(convertStringToBoolean(customProtocolEntity.getDoesRequireSignedData()))
                .setDoesUseSSH(convertStringToBoolean(customProtocolEntity.getDoesUseSsh()))
                .setGivenName(customProtocolEntity.getGivenName())
                .setIsInitiatingConsumer(convertStringToBoolean(customProtocolEntity.getIsInitiatingConsumer()))
                .setIsInitiatingProducer(convertStringToBoolean(customProtocolEntity.getIsInitiatingProducer()))
                .setIsListeningConsumer(convertStringToBoolean(customProtocolEntity.getIsListeningConsumer()))
                .setIsListeningProducer(convertStringToBoolean(customProtocolEntity.getIsListeningProducer()))
                .setKeyEnabled(convertStringToBoolean(customProtocolEntity.getKeyEnabled()))
                .setMailbox(customProtocolEntity.getMailbox())
                .setPassword(customProtocolEntity.getPassword())
                .setPasswordPolicy(customProtocolEntity.getPasswordPolicy())
                .setPollingInterval(customProtocolEntity.getPollingInterval())
                .setPostalCode(customProtocolEntity.getPostalCode())
                .setPublicKeyID(customProtocolEntity.getPublicKeyId())
                .setRemoteFilePattern(customProtocolEntity.getRemoteFilePattern())
                .setSessionTimeout(customProtocolEntity.getSessionTimeout())
                .setStateOrProvince(customProtocolEntity.getStateOrProvince())
                .setSurname(customProtocolEntity.getSurname())
                .setTextMode(convertStringToBoolean(customProtocolEntity.getTextMode()))
                .setTimeZone(customProtocolEntity.getTimeZone())
                .setUseGlobalMailbox(convertStringToBoolean(customProtocolEntity.getUseGlobalMailbox()))
                .setUsername(customProtocolEntity.getUsername());
    }
}