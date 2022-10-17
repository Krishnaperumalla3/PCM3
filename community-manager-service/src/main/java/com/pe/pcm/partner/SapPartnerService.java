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
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.SapModel;
import com.pe.pcm.protocol.SapService;
import com.pe.pcm.protocol.sap.entity.SapEntity;
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
public class SapPartnerService {

    private final PartnerService partnerService;
    private final SapService sapService;
    private final ActivityHistoryService activityHistoryService;
    private final ProcessService processService;
    private final ManageProtocolService manageProtocolService;
    private final UserUtilityService userUtilityService;
    private final ObjectMapper mapper = new ObjectMapper();

    private final BiFunction<PartnerEntity, SapEntity, SapModel> serialize = SapPartnerService::apply;

    @Autowired
    public SapPartnerService(PartnerService partnerService,
                             SapService sapService,
                             ActivityHistoryService activityHistoryService,
                             ProcessService processService,
                             ManageProtocolService manageProtocolService,
                             UserUtilityService userUtilityService) {
        this.partnerService = partnerService;
        this.sapService = sapService;
        this.activityHistoryService = activityHistoryService;
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
    public String save(SapModel sapModel) {
        if (!Optional.ofNullable(Protocol.findProtocol(sapModel.getProtocol())).isPresent()) {
            throw unknownProtocol();
        }
        duplicatePartner(sapModel.getProfileId());
        String parentPrimaryKey = KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.TP_PKEY_PRE_APPEND,
                PCMConstants.TP_PKEY_RANDOM_COUNT);
        String childPrimaryKey = KeyGeneratorUtil.getChildPrimaryKey(sapModel.getProtocol());
        sapService.saveProtocol(sapModel, parentPrimaryKey, childPrimaryKey, "TP");
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(sapModel, partnerModel);
        partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.savePartnerActivity(parentPrimaryKey, "Trading Partner created.");
        userUtilityService.addPartnerToCurrentUser(parentPrimaryKey, TP);
        return parentPrimaryKey;
    }

    @Transactional
    public void update(SapModel sapModel) {
        PartnerEntity oldPartnerEntity = SerializationUtils.clone(partnerService.get(sapModel.getPkId()));
        if (!oldPartnerEntity.getTpId().equalsIgnoreCase(sapModel.getProfileId())) {
            duplicatePartner(sapModel.getProfileId());
        }
        String parentPrimaryKey = oldPartnerEntity.getPkId();
        String childPrimaryKey = oldPartnerEntity.getPartnerProtocolRef();
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(oldPartnerEntity.getTpProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
        Map<String, String> oldSapEntityMap = new LinkedHashMap<>();
        boolean isUpdate = true;
        if (!protocol.getProtocol().equalsIgnoreCase(sapModel.getProtocol())) {
            manageProtocolService.deleteProtocol(oldPartnerEntity.getTpProtocol(), oldPartnerEntity.getPkId());
            childPrimaryKey = getChildPrimaryKey(sapModel.getProtocol());
            isUpdate = false;

        } else {
            oldSapEntityMap = mapper.convertValue(sapService.get(parentPrimaryKey),
                    new TypeReference<Map<String, String>>() {
                    });
        }
        Map<String, String> newSapEntityMap = mapper.convertValue(sapService.saveProtocol(sapModel, parentPrimaryKey, childPrimaryKey, "TP"),
                new TypeReference<Map<String, String>>() {
                });
        sapModel.setPemIdentifier(isNotNull(sapModel.getPemIdentifier())
                ? sapModel.getPemIdentifier() : oldPartnerEntity.getPemIdentifier());
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(sapModel, partnerModel);
        PartnerEntity newPartnerEntity = partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.updatePartnerActivity(oldPartnerEntity,
                newPartnerEntity, oldSapEntityMap, newSapEntityMap, isUpdate);
    }

    public SapModel get(String pkId) {
        return serialize.apply(partnerService.get(pkId), sapService.get(pkId));
    }

    @Transactional
    public void delete(String pkId) {
        if (!processService.findByPartnerProfile(pkId).isEmpty()) {
            throw internalServerError("Unable to delete this partner, Because it has workflow.");
        }
        PartnerEntity partnerEntity = partnerService.findPartnerById(pkId);
        sapService.delete(pkId);
        partnerService.delete(partnerEntity);
    }

    private static SapModel apply(PartnerEntity partnerEntity, SapEntity sapEntity) {
        SapModel sapModel = new SapModel();
        sapModel.setPkId(partnerEntity.getPkId())
                .setProfileName(partnerEntity.getTpName())
                .setProfileId(partnerEntity.getTpId())
                .setProtocol(partnerEntity.getTpProtocol())
                .setAddressLine1(partnerEntity.getAddressLine1())
                .setAddressLine2(partnerEntity.getAddressLine2())
                .setEmailId(partnerEntity.getEmail())
                .setPhone(partnerEntity.getPhone())
                .setStatus(convertStringToBoolean(partnerEntity.getStatus()))
                .setHubInfo(convertStringToBoolean(sapEntity.getIsHubInfo()))
                .setPgpInfo(partnerEntity.getPgpInfo())
                .setPemIdentifier(partnerEntity.getPemIdentifier());

        return sapModel.setAdapterName(sapEntity.getSapAdapterName())
                .setSapRoute(sapEntity.getSapRoute());
    }
}
