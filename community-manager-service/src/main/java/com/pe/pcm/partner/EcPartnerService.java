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
import com.pe.pcm.protocol.EcModel;
import com.pe.pcm.protocol.EcService;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.ec.entity.EcEntity;
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
public class EcPartnerService {

    private final EcService ecService;
    private final PartnerService partnerService;
    private final ProcessService processService;
    private final UserUtilityService userUtilityService;
    private final ManageProtocolService manageProtocolService;
    private final ActivityHistoryService activityHistoryService;

    private final ObjectMapper mapper = new ObjectMapper();
    private final BiFunction<PartnerEntity, EcEntity, EcModel> serialize = EcPartnerService::apply;

    @Autowired
    public EcPartnerService(PartnerService partnerService,
                            EcService ecService,
                            ActivityHistoryService activityHistoryService,
                            ProcessService processService,
                            ManageProtocolService manageProtocolService,
                            UserUtilityService userUtilityService) {
        this.partnerService = partnerService;
        this.ecService = ecService;
        this.activityHistoryService = activityHistoryService;
        this.processService = processService;
        this.manageProtocolService = manageProtocolService;
        this.userUtilityService = userUtilityService;
    }

    private static EcModel apply(PartnerEntity tradingPartner, EcEntity ecEntity) {
        EcModel ecModel = new EcModel();
        ecModel.setPkId(tradingPartner.getPkId())
                .setProfileName(tradingPartner.getTpName())
                .setProfileId(tradingPartner.getTpId())
                .setAddressLine1(tradingPartner.getAddressLine1())
                .setAddressLine2(tradingPartner.getAddressLine2())
                .setProtocol(tradingPartner.getTpProtocol())
                .setEmailId(tradingPartner.getEmail())
                .setPhone(tradingPartner.getPhone())
                .setStatus(convertStringToBoolean(tradingPartner.getStatus()))
                .setPgpInfo(tradingPartner.getPgpInfo())
                .setHubInfo(convertStringToBoolean(ecEntity.getIsHubInfo()))
                .setPemIdentifier(tradingPartner.getPemIdentifier());

        return ecModel.setEcProtocol(ecEntity.getEcProtocol())
                .setEcProtocolReference(ecEntity.getEcProtocolRef());
    }

    void duplicatePartner(String partnerId) {
        Optional<PartnerEntity> tradingPartnerEntityOptional = partnerService.find(partnerId);
        tradingPartnerEntityOptional.ifPresent(tradingPartnerEntity -> {
            throw conflict("Partner");
        });
    }

    @Transactional
    public String save(EcModel ecModel) {
        if (!Optional.ofNullable(Protocol.findProtocol(ecModel.getProtocol())).isPresent()) {
            throw unknownProtocol();
        }
        duplicatePartner(ecModel.getProfileId());
        String parentPrimaryKey = KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.TP_PKEY_PRE_APPEND,
                PCMConstants.TP_PKEY_RANDOM_COUNT);
        String childPrimaryKey = KeyGeneratorUtil.getChildPrimaryKey(ecModel.getProtocol());
        ecService.saveProtocol(ecModel, parentPrimaryKey, childPrimaryKey, "TP");
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(ecModel, partnerModel);
        partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.savePartnerActivity(parentPrimaryKey, "Trading Partner created.");
        userUtilityService.addPartnerToCurrentUser(parentPrimaryKey, TP);
        return parentPrimaryKey;
    }

    @Transactional
    public void update(EcModel ecModel) {
        PartnerEntity oldPartnerEntity = SerializationUtils.clone(partnerService.get(ecModel.getPkId()));
        if (!oldPartnerEntity.getTpId().equalsIgnoreCase(ecModel.getProfileId())) {
            duplicatePartner(ecModel.getProfileId());
        }
        String parentPrimaryKey = oldPartnerEntity.getPkId();
        String childPrimaryKey = oldPartnerEntity.getPartnerProtocolRef();
        Optional<Protocol> optionalProtocol = Optional.ofNullable(Protocol.findProtocol(ecModel.getProtocol()));
        if (!optionalProtocol.isPresent()) {
            throw unknownProtocol();
        }
        Protocol protocol = optionalProtocol.get();
        Map<String, String> oldEcEntityMap = new LinkedHashMap<>();
        boolean isUpdate = true;
        if (!protocol.getProtocol().equalsIgnoreCase(oldPartnerEntity.getTpProtocol())) {
            manageProtocolService.deleteProtocol(oldPartnerEntity.getTpProtocol(), oldPartnerEntity.getPkId());
            childPrimaryKey = getChildPrimaryKey(ecModel.getProtocol());
            isUpdate = false;
        } else {
            oldEcEntityMap = mapper.convertValue(ecService.get(parentPrimaryKey),
                    new TypeReference<Map<String, String>>() {
                    });
        }
        Map<String, String> newEcEntityMap = mapper.convertValue(ecService.saveProtocol(ecModel, parentPrimaryKey, childPrimaryKey, "TP"),
                new TypeReference<Map<String, String>>() {
                });
        ecModel.setPemIdentifier(isNotNull(ecModel.getPemIdentifier())
                ? ecModel.getPemIdentifier() : oldPartnerEntity.getPemIdentifier());
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(ecModel, partnerModel);
        PartnerEntity newPartnerEntity = partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.updatePartnerActivity(oldPartnerEntity,
                newPartnerEntity, oldEcEntityMap, newEcEntityMap, isUpdate);
    }

    public EcModel get(String pkId) {
        PartnerEntity partnerEntity = partnerService.get(pkId);
        EcEntity ecEntity = ecService.get(pkId);
        return serialize.apply(partnerEntity, ecEntity);
    }

    @Transactional
    public void delete(String pkId) {
        if (!processService.findByPartnerProfile(pkId).isEmpty()) {
            throw internalServerError("Unable to delete this partner, Because it has workflow.");
        }
        PartnerEntity partnerEntity = partnerService.findPartnerById(pkId);
        ecService.delete(pkId);
        partnerService.delete(partnerEntity);
    }
}
