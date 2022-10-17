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
import com.pe.pcm.b2b.protocol.ConsumerS3Configuration;
import com.pe.pcm.b2b.protocol.ProducerS3Configuration;
import com.pe.pcm.b2b.protocol.RemoteS3Profile;
import com.pe.pcm.enums.Protocol;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.partner.entity.PartnerEntity;
import com.pe.pcm.protocol.AwsS3Model;
import com.pe.pcm.protocol.AwsS3Service;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.awss3.entity.AwsS3Entity;
import com.pe.pcm.utils.CommonFunctions;
import com.pe.pcm.utils.KeyGeneratorUtil;
import com.pe.pcm.utils.PCMConstants;
import com.pe.pcm.workflow.ProcessService;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

/**
 * @author Shameer.
 */
@Service
public class AwsS3PartnerService {

    private final AwsS3Service awsS3Service;
    private final PartnerService partnerService;
    private final ProcessService processService;
    private final ManageProtocolService manageProtocolService;
    private final ActivityHistoryService activityHistoryService;
    private final B2BApiService b2BApiService;
    private final String community;

    private final ObjectMapper mapper = new ObjectMapper();
    private final BiFunction<PartnerEntity, AwsS3Entity, AwsS3Model> serialize = AwsS3PartnerService::apply;

    @Autowired
    public AwsS3PartnerService(PartnerService partnerService,
                               ActivityHistoryService activityHistoryService,
                               ProcessService processService,
                               AwsS3Service awsS3Service,
                               ManageProtocolService manageProtocolService,
                               B2BApiService b2BApiService,
                               @Value("${sterling-b2bi.b2bi-api.b2bi-sfg-api.community-name}") String community) {
        this.partnerService = partnerService;
        this.activityHistoryService = activityHistoryService;
        this.processService = processService;
        this.awsS3Service = awsS3Service;
        this.manageProtocolService = manageProtocolService;
        this.b2BApiService = b2BApiService;
        this.community = community;
    }

    void duplicatePartner(String partnerId) {
        Optional<PartnerEntity> tradingPartnerEntityOptional = partnerService.find(partnerId);
        tradingPartnerEntityOptional.ifPresent(tradingPartnerEntity -> {
            throw conflict("Partner");
        });
    }

    @Transactional
    public String save(AwsS3Model awsS3Model) {
        if (!Optional.ofNullable(Protocol.findProtocol(awsS3Model.getProtocol())).isPresent()) {
            throw unknownProtocol();
        }
        duplicatePartner(awsS3Model.getProfileId());
        String parentPrimaryKey = KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.TP_PKEY_PRE_APPEND, PCMConstants.TP_PKEY_RANDOM_COUNT);
        String childPrimaryKey = KeyGeneratorUtil.getChildPrimaryKey(awsS3Model.getProtocol());
        saveProtocol(awsS3Model, parentPrimaryKey, childPrimaryKey);
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(awsS3Model, partnerModel);
        partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
        RemoteS3Profile remoteS3Profile = new RemoteS3Profile(awsS3Model, false, community);
        if (!awsS3Model.isOnlyPCM()) {
            if (isNotNull(awsS3Model.getSIProfile()) && awsS3Model.getSIProfile()) {
                if (remoteS3Profile.getIsInitiatingConsumer() || remoteS3Profile.getIsListeningConsumer()) {
                    remoteS3Profile.setConsumerS3Configuration(new ConsumerS3Configuration(awsS3Model));
                }
                if (remoteS3Profile.getIsInitiatingProducer() || remoteS3Profile.getIsListeningProducer()) {
                    remoteS3Profile.setProducerS3Configuration(new ProducerS3Configuration(awsS3Model));
                }
                b2BApiService.createRemoteS3Profile(remoteS3Profile);
            }
        }
        activityHistoryService.savePartnerActivity(parentPrimaryKey, "Trading Partner created.");
        return parentPrimaryKey;
    }

    public AwsS3Entity saveProtocol(AwsS3Model awsS3Model, String parentPrimaryKey, String childPrimaryKey) {
        return awsS3Service.saveProtocol(awsS3Model, parentPrimaryKey, childPrimaryKey, "TP");
    }

    @Transactional
    public void update(AwsS3Model awsS3Model) {
        PartnerEntity oldPartnerEntity = SerializationUtils.clone(partnerService.get(awsS3Model.getPkId()));
        if (!oldPartnerEntity.getTpId().equalsIgnoreCase(awsS3Model.getProfileId())) {
            duplicatePartner(awsS3Model.getProfileId());
        }
        String parentPrimaryKey = oldPartnerEntity.getPkId();
        String childPrimaryKey = oldPartnerEntity.getPartnerProtocolRef();
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(awsS3Model.getProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
        Map<String, String> oldFtpEntityMap = new LinkedHashMap<>();
        boolean isUpdate = true;
        if (!protocol.getProtocol().equalsIgnoreCase(oldPartnerEntity.getTpProtocol())) {
            manageProtocolService.deleteProtocol(oldPartnerEntity.getTpProtocol(), oldPartnerEntity.getPkId());
            childPrimaryKey = getChildPrimaryKey(awsS3Model.getProtocol());
            isUpdate = false;
        } else {
            oldFtpEntityMap = mapper.convertValue(awsS3Service.get(parentPrimaryKey), new TypeReference<Map<String, String>>() {
            });
        }

        Map<String, String> newFtpEntityMap = mapper.convertValue(saveProtocol(awsS3Model, parentPrimaryKey, childPrimaryKey), new TypeReference<Map<String, String>>() {
        });
        awsS3Model.setPemIdentifier(isNotNull(awsS3Model.getPemIdentifier())
                ? awsS3Model.getPemIdentifier() : oldPartnerEntity.getPemIdentifier());
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(awsS3Model, partnerModel);
        PartnerEntity newPartnerEntity = partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
        if (isUpdate) {
            if (isNotNull(awsS3Model.getSIProfile()) && awsS3Model.getSIProfile()) {
                RemoteS3Profile remoteS3Profile = new RemoteS3Profile(awsS3Model, isUpdate, community);
                if (remoteS3Profile.getIsInitiatingConsumer() || remoteS3Profile.getIsListeningConsumer()) {
                    remoteS3Profile.setConsumerS3Configuration(new ConsumerS3Configuration(awsS3Model));
                }
                if (remoteS3Profile.getIsInitiatingProducer() || remoteS3Profile.getIsListeningProducer()) {
                    remoteS3Profile.setProducerS3Configuration(new ProducerS3Configuration(awsS3Model));
                }
                b2BApiService.updateRemoteS3Profile(remoteS3Profile, isNotNull(oldPartnerEntity.getCustomTpName()) ? oldPartnerEntity.getCustomTpName() : oldPartnerEntity.getTpName());
            }
        }
        activityHistoryService.updatePartnerActivity(oldPartnerEntity, newPartnerEntity, oldFtpEntityMap, newFtpEntityMap, isUpdate);
    }

    @Transactional
    public void delete(String pkId,Boolean isDeleteInSI) {
        if (!processService.findByPartnerProfile(pkId).isEmpty()) {
            throw internalServerError("Unable to delete this partner, Because it has workflow..");
        }
        PartnerEntity partnerEntity = partnerService.findPartnerById(pkId);
        if (isNotNull(isDeleteInSI) && isDeleteInSI) {
            try {
                b2BApiService.deleteRemoteS3Profile(isNotNull(partnerEntity.getCustomTpName()) ? partnerEntity.getCustomTpName() : partnerEntity.getTpName());
            } catch (Exception e) {
                //Do nothing
            }
        }
        awsS3Service.delete(pkId);
        partnerService.delete(partnerEntity);
    }

    public AwsS3Model get(String pkId,Boolean isSIProfile) {
        PartnerEntity partnerEntity = partnerService.get(pkId);
        AwsS3Entity awsS3Entity = awsS3Service.get(pkId);
        AwsS3Model awsS3Model = serialize.apply(partnerEntity, awsS3Entity);
        if (isNotNull(isSIProfile) && isSIProfile){
            awsS3Model = b2BApiService.getRemoteS3Profile(awsS3Model);
        }
       return awsS3Model;
        //return serialize.apply(partnerEntity, awsS3Entity);
    }


    private static AwsS3Model apply(PartnerEntity tradingPartner, AwsS3Entity awsS3Entity) {
        AwsS3Model awsS3Model = new AwsS3Model();
        awsS3Model.setPkId(tradingPartner.getPkId())
                .setProfileName(tradingPartner.getTpName())
                .setProfileId(tradingPartner.getTpId())
                .setCustomProfileName(tradingPartner.getCustomTpName())
                .setPkId(tradingPartner.getPkId())
                .setAddressLine1(tradingPartner.getAddressLine1())
                .setAddressLine2(tradingPartner.getAddressLine2())
                .setProtocol(tradingPartner.getTpProtocol())
                .setEmailId(tradingPartner.getEmail())
                .setPhone(tradingPartner.getPhone())
                .setStatus(convertStringToBoolean(tradingPartner.getStatus()))
                .setHubInfo(convertStringToBoolean(awsS3Entity.getIsHubInfo()))
                .setPgpInfo(tradingPartner.getPgpInfo())
                .setPemIdentifier(tradingPartner.getPemIdentifier());

        return awsS3Model.setRegion(awsS3Entity.getRegion())
                .setSourcePath(awsS3Entity.getSourcePath())
                .setSecretKey(awsS3Entity.getSecretKey())
                .setInMailbox(awsS3Entity.getInMailbox())
                .setFileType(awsS3Entity.getFileType())
                .setFileName(awsS3Entity.getFileName())
                .setEndpoint(awsS3Entity.getEndpoint())
                .setBucketName(awsS3Entity.getBucketName())
                .setAccessKey(awsS3Entity.getAccessKey())
                .setAdapterName(awsS3Entity.getAdapterName())
                .setRegion(awsS3Entity.getRegion())
                .setQueueName(awsS3Entity.getQueueName())
                .setEndPointUrl(awsS3Entity.getEndPointUrl())
                .setFolderName(awsS3Entity.getFolderName())
                .setPoolingInterval(awsS3Entity.getPoolingIntervalMins())
                .setDeleteAfterCollection(CommonFunctions.convertStringToBoolean(awsS3Entity.getDeleteAfterCollection()));
    }
}
