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
import com.pe.pcm.b2b.other.CaCertGetModel;
import com.pe.pcm.b2b.other.CipherSuiteNameGetModel;
import com.pe.pcm.enums.Protocol;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.miscellaneous.UserUtilityService;
import com.pe.pcm.partner.entity.PartnerEntity;
import com.pe.pcm.protocol.ConnectDirectModel;
import com.pe.pcm.protocol.ConnectDirectService;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.connectdirect.entity.ConnectDirectEntity;
import com.pe.pcm.utils.KeyGeneratorUtil;
import com.pe.pcm.utils.PCMConstants;
import com.pe.pcm.workflow.ProcessService;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static com.pe.pcm.exception.GlobalExceptionHandler.*;
import static com.pe.pcm.utils.CommonFunctions.convertStringToBoolean;
import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.KeyGeneratorUtil.getChildPrimaryKey;
import static com.pe.pcm.utils.PCMConstants.TP;

/**
 * @author Shameer.
 */
@Service
public class ConnectDirectPartnerService {
    private final ProcessService processService;
    private final PartnerService partnerService;
    private final UserUtilityService userUtilityService;
    private final ConnectDirectService connectDirectService;
    private final ManageProtocolService manageProtocolService;
    private final ActivityHistoryService activityHistoryService;

    private final ObjectMapper mapper = new ObjectMapper();
    private final BiFunction<PartnerEntity, ConnectDirectEntity, ConnectDirectModel> serialize = ConnectDirectPartnerService::apply;

    @Autowired
    public ConnectDirectPartnerService(PartnerService partnerService,
                                       ActivityHistoryService activityHistoryService,
                                       ConnectDirectService connectDirectService,
                                       ProcessService processService,
                                       UserUtilityService userUtilityService,
                                       ManageProtocolService manageProtocolService) {
        this.partnerService = partnerService;
        this.activityHistoryService = activityHistoryService;
        this.connectDirectService = connectDirectService;
        this.processService = processService;
        this.userUtilityService = userUtilityService;
        this.manageProtocolService = manageProtocolService;
    }

    private void duplicatePartner(String partnerId) {
        Optional<PartnerEntity> tradingPartnerEntityOptional = partnerService.find(partnerId);
        tradingPartnerEntityOptional.ifPresent(tradingPartnerEntity -> {
            throw conflict("Partner");
        });
    }

    @Transactional
    public String save(ConnectDirectModel connectDirectModel) {
        if (!Optional.ofNullable(Protocol.findProtocol(connectDirectModel.getProtocol())).isPresent()) {
            throw unknownProtocol();
        }
        duplicatePartner(connectDirectModel.getProfileId());
        String parentPrimaryKey = KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.TP_PKEY_PRE_APPEND,
                PCMConstants.TP_PKEY_RANDOM_COUNT);
        String childPrimaryKey = KeyGeneratorUtil.getChildPrimaryKey(connectDirectModel.getProtocol());
        saveProtocol(connectDirectModel, parentPrimaryKey, childPrimaryKey);
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(connectDirectModel, partnerModel);
        partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.savePartnerActivity(parentPrimaryKey, "Trading Partner created.");
        userUtilityService.addPartnerToCurrentUser(parentPrimaryKey, TP);
        return parentPrimaryKey;
    }

    @Transactional
    public void update(ConnectDirectModel connectDirectModel) {
        PartnerModel partnerModel = new PartnerModel();
        PartnerEntity oldPartnerEntity = SerializationUtils.clone(partnerService.get(connectDirectModel.getPkId()));
        if (!oldPartnerEntity.getTpId().equalsIgnoreCase(connectDirectModel.getProfileId())) {
            duplicatePartner(connectDirectModel.getProfileId());
        }
        String childPrimaryKey = oldPartnerEntity.getPartnerProtocolRef();
        String parentPrimaryKey = oldPartnerEntity.getPkId();
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(connectDirectModel.getProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
        Map<String, String> oldConnectDirectEntityMap = new LinkedHashMap<>();
        boolean isUpdate = true;
        if (!protocol.getProtocol().equalsIgnoreCase(oldPartnerEntity.getTpProtocol())) {
            isUpdate = false;
            manageProtocolService.deleteProtocol(oldPartnerEntity.getTpProtocol(), oldPartnerEntity.getPkId());
            childPrimaryKey = getChildPrimaryKey(connectDirectModel.getProtocol());
        } else {
            oldConnectDirectEntityMap = mapper.convertValue(connectDirectService.get(parentPrimaryKey), new TypeReference<Map<String, String>>() {
            });
        }
        Map<String, String> newConnectDirectEntityMap = mapper.convertValue(saveProtocol(connectDirectModel, parentPrimaryKey, childPrimaryKey), new TypeReference<Map<String, String>>() {
        });
        connectDirectModel.setPemIdentifier(isNotNull(connectDirectModel.getPemIdentifier())
                ? connectDirectModel.getPemIdentifier() : oldPartnerEntity.getPemIdentifier());
        BeanUtils.copyProperties(connectDirectModel, partnerModel);
        PartnerEntity newPartnerEntity = partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.updatePartnerActivity(oldPartnerEntity, newPartnerEntity, oldConnectDirectEntityMap, newConnectDirectEntityMap, isUpdate);
    }

    public ConnectDirectEntity saveProtocol(ConnectDirectModel connectDirectModel, String parentPrimaryKey, String childPrimaryKey) {
        return connectDirectService.saveProtocol(connectDirectModel, parentPrimaryKey, childPrimaryKey, "TP");
    }

    @Transactional
    public void delete(String pkId) {
        if (!processService.findByPartnerProfile(pkId).isEmpty()) {
            throw internalServerError("Unable to delete this partner, Because it has workflow..");
        }
        PartnerEntity partnerEntity = partnerService.findPartnerById(pkId);
        connectDirectService.delete(pkId);
        partnerService.delete(partnerEntity);
    }

    public ConnectDirectModel get(String pkId) {
        PartnerEntity partnerEntity = partnerService.get(pkId);
        ConnectDirectEntity connectDirectEntity = connectDirectService.get(pkId);
        return serialize.apply(partnerEntity, connectDirectEntity);
    }

    private static ConnectDirectModel apply(PartnerEntity partnerEntity, ConnectDirectEntity connectDirectEntity) {
        ConnectDirectModel connectDirectModel = new ConnectDirectModel();
        connectDirectModel.setPkId(partnerEntity.getPkId())
                .setProfileName(partnerEntity.getTpName())
                .setProfileId(partnerEntity.getTpId())
                .setPkId(partnerEntity.getPkId())
                .setAddressLine2(partnerEntity.getAddressLine2())
                .setAddressLine1(partnerEntity.getAddressLine1())
                .setProtocol(partnerEntity.getTpProtocol())
                .setEmailId(partnerEntity.getEmail())
                .setPhone(partnerEntity.getPhone())
                .setStatus(convertStringToBoolean(partnerEntity.getStatus()))
                .setHubInfo(convertStringToBoolean(connectDirectEntity.getIsHubInfo()))
                .setPgpInfo(partnerEntity.getPgpInfo())
                .setPemIdentifier(partnerEntity.getPemIdentifier());
        if (isNotNull(connectDirectEntity.getCaCertificate())) {
            connectDirectModel.setCaCertificate(Arrays.stream(connectDirectEntity.getCaCertificate().split(",")).map(CaCertGetModel::new).collect(Collectors.toList()));
        }
        if (isNotNull(connectDirectEntity.getCipherSuits())) {
            connectDirectModel.setCipherSuits(Arrays.stream(connectDirectEntity.getCipherSuits().split(",")).map(CipherSuiteNameGetModel::new).collect(Collectors.toList()));
        }
        return connectDirectModel.setAdapterName(connectDirectEntity.getAdapterName())
                .setCodePageFrom(connectDirectEntity.getCodePageFrom())
                .setCodePageTo(connectDirectEntity.getCodePageTo())
                .setDcb(connectDirectEntity.getDcb())
                .setLocalNodeName(connectDirectEntity.getLocalNodeName())
                .setLocalXLate(connectDirectEntity.getLocalXlate())
                .setPoolingInterval(connectDirectEntity.getPoolingInterval())
                .setRemoteHost(connectDirectEntity.getRemoteHost())
                .setRemoteNodeName(connectDirectEntity.getRemoteNodeName())
                .setRemoteUser(connectDirectEntity.getRemoteUser())
                .setRemotePassword(connectDirectEntity.getRemotePassword())
                .setRemotePort(connectDirectEntity.getRemotePort())
                .setSecurePlus(convertStringToBoolean(connectDirectEntity.getSecurePlus()))
                .setSecurityProtocol(connectDirectEntity.getSecurityProtocol())
                .setSysOpts(connectDirectEntity.getSysOpts())
                .setTransferType(connectDirectEntity.getTransferType());
    }

}
