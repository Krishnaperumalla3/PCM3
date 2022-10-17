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
import com.pe.pcm.protocol.WebserviceModel;
import com.pe.pcm.protocol.WebserviceService;
import com.pe.pcm.protocol.webservice.entity.WebserviceEntity;
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
public class WsPartnerService {

    private final PartnerService partnerService;
    private final WebserviceService webserviceService;
    private final ActivityHistoryService activityHistoryService;
    private final ProcessService processService;
    private final ManageProtocolService manageProtocolService;
    private final UserUtilityService userUtilityService;
    private final ObjectMapper mapper = new ObjectMapper();

    private final BiFunction<PartnerEntity, WebserviceEntity, WebserviceModel> serialize = WsPartnerService::apply;

    public WsPartnerService(PartnerService partnerService,
                            WebserviceService webserviceService,
                            ActivityHistoryService activityHistoryService,
                            ProcessService processService,
                            ManageProtocolService manageProtocolService,
                            UserUtilityService userUtilityService) {
        this.partnerService = partnerService;
        this.webserviceService = webserviceService;
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
    public String save(WebserviceModel webserviceModel) {
        if (!Optional.ofNullable(Protocol.findProtocol(webserviceModel.getProtocol())).isPresent()) {
            throw unknownProtocol();
        }
        duplicatePartner(webserviceModel.getProfileId());
        String parentPrimaryKey = KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.TP_PKEY_PRE_APPEND,
                PCMConstants.TP_PKEY_RANDOM_COUNT);
        String childPrimaryKey = KeyGeneratorUtil.getChildPrimaryKey(webserviceModel.getProtocol());
        webserviceService.saveProtocol(webserviceModel, parentPrimaryKey, childPrimaryKey, "TP");
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(webserviceModel, partnerModel);
        partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.savePartnerActivity(parentPrimaryKey, "Trading Partner created.");
//      No RemoteMailboxModel creation in SI
        userUtilityService.addPartnerToCurrentUser(parentPrimaryKey, TP);
        return parentPrimaryKey;
    }

    @Transactional
    public void update(WebserviceModel webserviceModel) {
        PartnerEntity oldPartnerEntity = SerializationUtils.clone(partnerService.get(webserviceModel.getPkId()));
        if (!oldPartnerEntity.getTpId().equalsIgnoreCase(webserviceModel.getProfileId())) {
            duplicatePartner(webserviceModel.getProfileId());
        }
        String parentPrimaryKey = oldPartnerEntity.getPkId();
        String childPrimaryKey = oldPartnerEntity.getPartnerProtocolRef();
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(oldPartnerEntity.getTpProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
        Map<String, String> oldHttpEntityMap = new LinkedHashMap<>();
        boolean isUpdate = true;
        if (!protocol.getProtocol().equalsIgnoreCase(webserviceModel.getProtocol())) {
            manageProtocolService.deleteProtocol(oldPartnerEntity.getTpProtocol(), oldPartnerEntity.getPkId());
            childPrimaryKey = getChildPrimaryKey(webserviceModel.getProtocol());
            isUpdate = false;
        } else {
            oldHttpEntityMap = mapper.convertValue(webserviceService.get(parentPrimaryKey),
                    new TypeReference<Map<String, String>>() {
                    });
        }
        Map<String, String> newHttpEntityMap = mapper.convertValue(webserviceService.saveProtocol(webserviceModel, parentPrimaryKey, childPrimaryKey, "TP"),
                new TypeReference<Map<String, String>>() {
                });
        webserviceModel.setPemIdentifier(isNotNull(webserviceModel.getPemIdentifier())
                ? webserviceModel.getPemIdentifier() : oldPartnerEntity.getPemIdentifier());
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(webserviceModel, partnerModel);
        PartnerEntity newPartnerEntity = partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.updatePartnerActivity(oldPartnerEntity,
                newPartnerEntity, oldHttpEntityMap, newHttpEntityMap, isUpdate);
    }

    public WebserviceModel get(String pkId) {
        PartnerEntity partnerEntity = partnerService.get(pkId);
        WebserviceEntity webserviceEntity = webserviceService.get(pkId);
        return serialize.apply(partnerEntity, webserviceEntity);
    }

    @Transactional
    public void delete(String pkId) {
        if (!processService.findByPartnerProfile(pkId).isEmpty()) {
            throw internalServerError("Unable to delete this partner, Because it has workflow.");
        }
        PartnerEntity partnerEntity = partnerService.findPartnerById(pkId);
        webserviceService.delete(pkId);
        partnerService.delete(partnerEntity);
    }

    private static WebserviceModel apply(PartnerEntity partnerEntity, WebserviceEntity webserviceEntity) {
        WebserviceModel webserviceModel = new WebserviceModel();
        webserviceModel.setPkId(partnerEntity.getPkId())
                .setProfileName(partnerEntity.getTpName())
                .setPhone(partnerEntity.getPhone())
                .setEmailId(partnerEntity.getEmail())
                .setProfileId(partnerEntity.getTpId())
                .setAddressLine1(partnerEntity.getAddressLine1())
                .setAddressLine2(partnerEntity.getAddressLine2())
                .setProtocol(partnerEntity.getTpProtocol())
                .setStatus(convertStringToBoolean(partnerEntity.getStatus()))
                .setPgpInfo(partnerEntity.getPgpInfo())
                .setHubInfo(convertStringToBoolean(webserviceEntity.getIsHubInfo()))
                .setPemIdentifier(partnerEntity.getPemIdentifier());

        return webserviceModel.setName(webserviceEntity.getWebserviceName())
                .setInMailBox(webserviceEntity.getInDirectory())
                .setOutBoundUrl(webserviceEntity.getOutboundUrl())
                .setCertificateId(webserviceEntity.getCertificate())
                .setPoolingInterval(webserviceEntity.getPoolingIntervalMins())
                .setAdapterName(webserviceEntity.getAdapterName());
    }
}
