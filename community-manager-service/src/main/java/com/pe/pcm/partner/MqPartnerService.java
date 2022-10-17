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
import com.pe.pcm.protocol.MqModel;
import com.pe.pcm.protocol.MqService;
import com.pe.pcm.protocol.mq.entity.MqEntity;
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
public class MqPartnerService {

    private final MqService mqService;
    private final PartnerService partnerService;
    private final ProcessService processService;
    private final UserUtilityService userUtilityService;
    private final ManageProtocolService manageProtocolService;
    private final ActivityHistoryService activityHistoryService;

    private final ObjectMapper mapper = new ObjectMapper();
    private final BiFunction<PartnerEntity, MqEntity, MqModel> serialize = MqPartnerService::apply;

    @Autowired
    public MqPartnerService(PartnerService partnerService,
                            MqService mqService,
                            ActivityHistoryService activityHistoryService,
                            ProcessService processService,
                            ManageProtocolService manageProtocolService,
                            UserUtilityService userUtilityService) {
        this.partnerService = partnerService;
        this.mqService = mqService;
        this.activityHistoryService = activityHistoryService;
        this.processService = processService;
        this.manageProtocolService = manageProtocolService;
        this.userUtilityService = userUtilityService;
    }

    private static MqModel apply(PartnerEntity partnerEntity, MqEntity mqEntity) {
        MqModel mqModel = new MqModel();
        mqModel.setPkId(partnerEntity.getPkId())
                .setProfileName(partnerEntity.getTpName())
                .setProfileId(partnerEntity.getTpId())
                .setAddressLine1(partnerEntity.getAddressLine1())
                .setAddressLine2(partnerEntity.getAddressLine2())
                .setHubInfo(convertStringToBoolean(mqEntity.getIsHubInfo()))
                .setProtocol(partnerEntity.getTpProtocol())
                .setEmailId(partnerEntity.getEmail())
                .setPhone(partnerEntity.getPhone())
                .setStatus(convertStringToBoolean(partnerEntity.getStatus()))
                .setPgpInfo(partnerEntity.getPgpInfo())
                .setPemIdentifier(partnerEntity.getPemIdentifier());

        return mqModel.setHostName(mqEntity.getHostName())
                .setPort(mqEntity.getPort())
                .setFileType(mqEntity.getFileType())
                .setQueueManager(mqEntity.getQueueManager())
                .setQueueName(mqEntity.getQueueName())
                .setAdapterName(mqEntity.getAdapterName())
                .setPoolingInterval(mqEntity.getPoolingIntervalMins())
                .setUserId(mqEntity.getUserId())
                .setPassword(mqEntity.getPassword())
                .setChannelName(mqEntity.getChannelName());
    }

    private void duplicatePartner(String partnerId) {
        Optional<PartnerEntity> tradingPartnerEntityOptional = partnerService.find(partnerId);
        tradingPartnerEntityOptional.ifPresent(tradingPartnerEntity -> {
            throw conflict("Partner");
        });
    }

    @Transactional
    public String save(MqModel mqModel) {
        if (!Optional.ofNullable(Protocol.findProtocol(mqModel.getProtocol())).isPresent()) {
            throw unknownProtocol();
        }
        duplicatePartner(mqModel.getProfileId());
        String parentPrimaryKey = KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.TP_PKEY_PRE_APPEND,
                PCMConstants.TP_PKEY_RANDOM_COUNT);
        String childPrimaryKey = KeyGeneratorUtil.getChildPrimaryKey(mqModel.getProtocol());
        mqService.saveProtocol(mqModel, parentPrimaryKey, childPrimaryKey, "TP");
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(mqModel, partnerModel);
        partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.savePartnerActivity(parentPrimaryKey, "Trading Partner created.");
        userUtilityService.addPartnerToCurrentUser(parentPrimaryKey, TP);
        return parentPrimaryKey;
    }

    @Transactional
    public void update(MqModel mqModel) {
        PartnerEntity oldPartnerEntity = SerializationUtils.clone(partnerService.get(mqModel.getPkId()));
        if (!oldPartnerEntity.getTpId().equalsIgnoreCase(mqModel.getProfileId())) {
            duplicatePartner(mqModel.getProfileId());
        }
        String parentPrimaryKey = oldPartnerEntity.getPkId();
        String childPrimaryKey = oldPartnerEntity.getPartnerProtocolRef();
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(oldPartnerEntity.getTpProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
        Map<String, String> oldMqEntityMap = new LinkedHashMap<>();
        boolean isUpdate = true;
        if (!protocol.getProtocol().equalsIgnoreCase(mqModel.getProtocol())) {
            manageProtocolService.deleteProtocol(oldPartnerEntity.getTpProtocol(), oldPartnerEntity.getPkId());
            childPrimaryKey = getChildPrimaryKey(mqModel.getProtocol());
            isUpdate = false;
        } else {
            oldMqEntityMap = mapper.convertValue(mqService.get(parentPrimaryKey),
                    new TypeReference<Map<String, String>>() {
                    });
        }
        Map<String, String> newMqEntityMap = mapper.convertValue(mqService.saveProtocol(mqModel, parentPrimaryKey, childPrimaryKey, "TP"),
                new TypeReference<Map<String, String>>() {
                });
        mqModel.setPemIdentifier(isNotNull(mqModel.getPemIdentifier())
                ? mqModel.getPemIdentifier() : oldPartnerEntity.getPemIdentifier());
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(mqModel, partnerModel);
        PartnerEntity newPartnerEntity = partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.updatePartnerActivity(oldPartnerEntity,
                newPartnerEntity, oldMqEntityMap, newMqEntityMap, isUpdate);
    }

    public MqModel get(String pkId) {
        return serialize.apply(partnerService.get(pkId), mqService.get(pkId));
    }

    @Transactional
    public void delete(String pkId) {
        if (!processService.findByPartnerProfile(pkId).isEmpty()) {
            throw internalServerError("Unable to delete this partner, Because it has workflow.");
        }
        mqService.delete(pkId);
        partnerService.delete(partnerService.findPartnerById(pkId));
    }
}
