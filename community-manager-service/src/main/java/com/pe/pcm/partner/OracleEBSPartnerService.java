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
import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.KeyGeneratorUtil.getChildPrimaryKey;
import static com.pe.pcm.utils.PCMConstants.TP;

/**
 * @author Kiran Reddy.
 */
@Service
public class OracleEBSPartnerService {

    private final PartnerService partnerService;
    private final ActivityHistoryService activityHistoryService;
    private final OracleEbsService oracleEbsService;
    private final ProcessService processService;
    private final ManageProtocolService manageProtocolService;
    private final UserUtilityService userUtilityService;

    private final ObjectMapper mapper = new ObjectMapper();

    private final BiFunction<PartnerEntity, OracleEbsEntity, OracleEbsModel> serialize = OracleEBSPartnerService::apply;

    @Autowired
    public OracleEBSPartnerService(PartnerService partnerService,
                                   ActivityHistoryService activityHistoryService,
                                   OracleEbsService oracleEbsService,
                                   ProcessService processService,
                                   UserUtilityService userUtilityService,
                                   ManageProtocolService manageProtocolService) {
        this.partnerService = partnerService;
        this.activityHistoryService = activityHistoryService;
        this.oracleEbsService = oracleEbsService;
        this.processService = processService;
        this.manageProtocolService = manageProtocolService;
        this.userUtilityService = userUtilityService;
    }

    void duplicatePartner(String partnerId) {
        Optional<PartnerEntity> tradingPartnerEntityOptional = partnerService.find(partnerId);
        tradingPartnerEntityOptional.ifPresent(tradingPartnerEntity -> {
            throw conflict("Partner");
        });
    }

    @Transactional
    public String save(OracleEbsModel oracleEBSModel) {
        if (!Optional.ofNullable(Protocol.findProtocol(oracleEBSModel.getProtocol())).isPresent()) {
            throw unknownProtocol();
        }
        duplicatePartner(oracleEBSModel.getProfileId());
        String parentPrimaryKey = KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.TP_PKEY_PRE_APPEND, PCMConstants.TP_PKEY_RANDOM_COUNT);
        String childPrimaryKey = KeyGeneratorUtil.getChildPrimaryKey(oracleEBSModel.getProtocol());
        saveProtocol(oracleEBSModel, parentPrimaryKey, childPrimaryKey);
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(oracleEBSModel, partnerModel);
        partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.savePartnerActivity(parentPrimaryKey, "Trading Partner created.");
        userUtilityService.addPartnerToCurrentUser(parentPrimaryKey, TP);
        return parentPrimaryKey;
    }

    @Transactional
    public void update(OracleEbsModel oracleEBSModel) {
        PartnerEntity oldPartnerEntity = SerializationUtils.clone(partnerService.get(oracleEBSModel.getPkId()));
        if (!oldPartnerEntity.getTpId().equalsIgnoreCase(oracleEBSModel.getProfileId())) {
            duplicatePartner(oracleEBSModel.getProfileId());
        }
        String parentPrimaryKey = oldPartnerEntity.getPkId();
        String childPrimaryKey = oldPartnerEntity.getPartnerProtocolRef();
        Optional<Protocol> protocolOptional = Optional.ofNullable(Protocol.findProtocol(oracleEBSModel.getProtocol()));
        if (!protocolOptional.isPresent()) {
            throw unknownProtocol();
        }
        Protocol protocol = protocolOptional.get();
        Map<String, String> oldSmtpEntityMap = new LinkedHashMap<>();
        boolean isUpdate = true;
        if (!protocol.getProtocol().equalsIgnoreCase(oldPartnerEntity.getTpProtocol())) {
            manageProtocolService.deleteProtocol(oldPartnerEntity.getTpProtocol(), oldPartnerEntity.getPkId());
            childPrimaryKey = getChildPrimaryKey(oracleEBSModel.getProtocol());
            isUpdate = false;
        } else {
            oldSmtpEntityMap = mapper.convertValue(oracleEbsService.get(parentPrimaryKey), new TypeReference<Map<String, String>>() {
            });
        }
        Map<String, String> newSmtpEntityMap = mapper.convertValue(saveProtocol(oracleEBSModel, parentPrimaryKey, childPrimaryKey), new TypeReference<Map<String, String>>() {
        });

        oracleEBSModel.setPemIdentifier(isNotNull(oracleEBSModel.getPemIdentifier())
                ? oracleEBSModel.getPemIdentifier() : oldPartnerEntity.getPemIdentifier());
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(oracleEBSModel, partnerModel);
        PartnerEntity newPartnerEntity = partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.updatePartnerActivity(oldPartnerEntity, newPartnerEntity, oldSmtpEntityMap, newSmtpEntityMap, isUpdate);
    }

    @Transactional
    public void delete(String pkId) {
        if (!processService.findByPartnerProfile(pkId).isEmpty()) {
            throw internalServerError("Unable to delete this partner, Because it has workflow..");
        }
        PartnerEntity partnerEntity = partnerService.findPartnerById(pkId);
        oracleEbsService.delete(pkId);
        partnerService.delete(partnerEntity);
    }

    public OracleEbsModel get(String pkId) {
        PartnerEntity partnerEntity = partnerService.get(pkId);
        OracleEbsEntity oracleEBSEntity = oracleEbsService.get(pkId);
        return serialize.apply(partnerEntity, oracleEBSEntity);
    }

    public OracleEbsEntity saveProtocol(OracleEbsModel oracleEBSModel, String parentPrimaryKey, String childPrimaryKey) {
        return oracleEbsService.saveProtocol(oracleEBSModel, parentPrimaryKey, childPrimaryKey, "TP");
    }


    private static OracleEbsModel apply(PartnerEntity tradingPartner, OracleEbsEntity oracleEBSEntity) {
        OracleEbsModel oracleEBSModel = new OracleEbsModel();
        oracleEBSModel.setPkId(tradingPartner.getPkId())
                .setProfileId(tradingPartner.getTpId())
                .setProfileName(tradingPartner.getTpName())
                .setAddressLine1(tradingPartner.getAddressLine1())
                .setAddressLine2(tradingPartner.getAddressLine2())
                .setProtocol(tradingPartner.getTpProtocol())
                .setEmailId(tradingPartner.getEmail())
                .setPhone(tradingPartner.getPhone())
                .setPemIdentifier(tradingPartner.getPemIdentifier())
                .setStatus(convertStringToBoolean(tradingPartner.getStatus()))
                .setPgpInfo(tradingPartner.getPgpInfo());

        return oracleEBSModel.setName(oracleEBSEntity.getName())
                .setOrProtocol(oracleEBSEntity.getProtocol())
                .setNameBod(oracleEBSEntity.getNameBod())
                .setUserName(oracleEBSEntity.getUserName())
                .setPassword(oracleEBSEntity.getPassword())
                .setAutoSendBodRecMsgs(oracleEBSEntity.getAutoSendBodRecMsgs())
                .setBpRecMsgs(oracleEBSEntity.getBpRecMsgs())
                .setBpUnknownBods(oracleEBSEntity.getBpUnknownBods())
                .setDateTimeOag(oracleEBSEntity.getDateTimeOag())
                .setDirectoryBod(oracleEBSEntity.getDirectoryBod())
                .setHttpEndpoint(oracleEBSEntity.getHttpEndpoint())
                .setRequestType(oracleEBSEntity.getRequestType())
                .setTimeoutBod(oracleEBSEntity.getTimeoutBod())
                .setTpContractSend(oracleEBSEntity.getTpContractSend());
    }

}
