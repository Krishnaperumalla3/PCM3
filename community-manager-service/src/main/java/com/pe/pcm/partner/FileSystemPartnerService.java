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
import com.pe.pcm.miscellaneous.PasswordUtilityService;
import com.pe.pcm.miscellaneous.UserUtilityService;
import com.pe.pcm.partner.entity.PartnerEntity;
import com.pe.pcm.protocol.FileSystemModel;
import com.pe.pcm.protocol.FileSystemService;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.filesystem.entity.FileSystemEntity;
import com.pe.pcm.protocol.function.SterlingFunctions;
import com.pe.pcm.sterling.mailbox.SterlingMailboxService;
import com.pe.pcm.sterling.mailbox.routingrule.SterlingRoutingRuleService;
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
public class FileSystemPartnerService {

    private final PartnerService partnerService;
    private final FileSystemService fileSystemService;
    private final ActivityHistoryService activityHistoryService;
    private final ProcessService processService;
    private final ManageProtocolService manageProtocolService;
    private final UserUtilityService userUtilityService;
    private final SterlingRoutingRuleService sterlingRoutingRuleService;
    private final SterlingMailboxService sterlingMailboxService;
    private final PasswordUtilityService passwordUtilityService;

    private final ObjectMapper mapper = new ObjectMapper();
    private final BiFunction<PartnerEntity, FileSystemEntity, FileSystemModel> serialize = FileSystemPartnerService::apply;

    @Autowired
    public FileSystemPartnerService(PartnerService partnerService,
                                    FileSystemService fileSystemService,
                                    ActivityHistoryService activityHistoryService,
                                    ProcessService processService,
                                    ManageProtocolService manageProtocolService,
                                    UserUtilityService userUtilityService,
                                    SterlingRoutingRuleService sterlingRoutingRuleService,
                                    SterlingMailboxService sterlingMailboxService,
                                    PasswordUtilityService passwordUtilityService) {
        this.partnerService = partnerService;
        this.fileSystemService = fileSystemService;
        this.activityHistoryService = activityHistoryService;
        this.processService = processService;
        this.manageProtocolService = manageProtocolService;
        this.userUtilityService = userUtilityService;
        this.sterlingRoutingRuleService = sterlingRoutingRuleService;
        this.sterlingMailboxService = sterlingMailboxService;
        this.passwordUtilityService = passwordUtilityService;
    }

    private static FileSystemModel apply(PartnerEntity tradingPartner, FileSystemEntity fileSystemEntity) {
        FileSystemModel fileSystemModel = new FileSystemModel();
        fileSystemModel.setPkId(tradingPartner.getPkId())
                .setProfileName(tradingPartner.getTpName())
                .setProfileId(tradingPartner.getTpId())
                .setPkId(tradingPartner.getPkId())
                .setAddressLine1(tradingPartner.getAddressLine1())
                .setAddressLine2(tradingPartner.getAddressLine2())
                .setProtocol(tradingPartner.getTpProtocol())
                .setEmailId(tradingPartner.getEmail())
                .setHubInfo(convertStringToBoolean(fileSystemEntity.getIsHubInfo()))
                .setPhone(tradingPartner.getPhone())
                .setPgpInfo(tradingPartner.getPgpInfo())
                .setStatus(convertStringToBoolean(tradingPartner.getStatus()))
                .setPemIdentifier(tradingPartner.getPemIdentifier());

        return fileSystemModel.setAdapterName(fileSystemEntity.getFsaAdapter())
                .setUserName(fileSystemEntity.getUserName())
                .setPassword(fileSystemEntity.getPassword())
                .setFileType(fileSystemEntity.getFileType())
                .setDeleteAfterCollection(convertStringToBoolean(fileSystemEntity.getDeleteAfterCollection()))
                .setAdapterName(fileSystemEntity.getFsaAdapter())
                .setPoolingInterval(fileSystemEntity.getPoolingIntervalMins())
                .setInDirectory(fileSystemEntity.getInDirectory())
                .setOutDirectory(fileSystemEntity.getOutDirectory());
    }

    void duplicatePartner(String partnerId) {
        Optional<PartnerEntity> tradingPartnerEntityOptional = partnerService.find(partnerId);
        tradingPartnerEntityOptional.ifPresent(tradingPartnerEntity -> {
            throw conflict("Partner");
        });
    }

    @Transactional
    public String save(FileSystemModel fileSystemModel) {
        if (!Optional.ofNullable(Protocol.findProtocol(fileSystemModel.getProtocol())).isPresent()) {
            throw unknownProtocol();
        }
        duplicatePartner(fileSystemModel.getProfileId());
        String parentPrimaryKey = KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.TP_PKEY_PRE_APPEND,
                PCMConstants.TP_PKEY_RANDOM_COUNT);
        String childPrimaryKey = KeyGeneratorUtil.getChildPrimaryKey(fileSystemModel.getProtocol());
        saveProtocol(fileSystemModel, parentPrimaryKey, childPrimaryKey, fileSystemModel.getProfileName(), "");
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(fileSystemModel, partnerModel);
        partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.savePartnerActivity(parentPrimaryKey, "Trading Partner created.");
        userUtilityService.addPartnerToCurrentUser(parentPrimaryKey, TP);
        return parentPrimaryKey;
    }

    @Transactional
    public void update(FileSystemModel fileSystemModel) {
        PartnerEntity oldPartnerEntity = SerializationUtils.clone(partnerService.get(fileSystemModel.getPkId()));
        if (!oldPartnerEntity.getTpId().equalsIgnoreCase(fileSystemModel.getProfileId())) {
            duplicatePartner(fileSystemModel.getProfileId());
        }
        String parentPrimaryKey = oldPartnerEntity.getPkId();
        String childPrimaryKey = oldPartnerEntity.getPartnerProtocolRef();
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(oldPartnerEntity.getTpProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
        Map<String, String> oldHttpEntityMap = new LinkedHashMap<>();
        boolean isUpdate = true;
        if (!protocol.getProtocol().equalsIgnoreCase(fileSystemModel.getProtocol())) {
            manageProtocolService.deleteProtocol(oldPartnerEntity.getTpProtocol(), oldPartnerEntity.getPkId());
            childPrimaryKey = getChildPrimaryKey(fileSystemModel.getProtocol());
            isUpdate = false;
        } else {
            oldHttpEntityMap = mapper.convertValue(fileSystemService.get(parentPrimaryKey),
                    new TypeReference<Map<String, String>>() {
                    });
        }
        Map<String, String> newHttpEntityMap =
                mapper.convertValue(saveProtocol(fileSystemModel, parentPrimaryKey, childPrimaryKey, oldPartnerEntity.getTpName(), oldHttpEntityMap.get("poolingIntervalMins")),
                        new TypeReference<Map<String, String>>() {
                        });
        fileSystemModel.setPemIdentifier(isNotNull(fileSystemModel.getPemIdentifier())
                ? fileSystemModel.getPemIdentifier() : oldPartnerEntity.getPemIdentifier());
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(fileSystemModel, partnerModel);
        PartnerEntity newPartnerEntity = partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.updatePartnerActivity(oldPartnerEntity,
                newPartnerEntity, oldHttpEntityMap, newHttpEntityMap, isUpdate);
    }

    public FileSystemModel get(String pkId) {
        PartnerEntity partnerEntity = partnerService.get(pkId);
        FileSystemEntity fileSystemEntity = fileSystemService.get(pkId);
        return serialize.apply(partnerEntity, fileSystemEntity);
    }

    @Transactional
    public void delete(String pkId) {
        if (!processService.findByPartnerProfile(pkId).isEmpty()) {
            throw internalServerError("Unable to delete this partner, Because it has workflow.");
        }
        fileSystemService.delete(pkId);
        partnerService.delete(partnerService.findPartnerById(pkId));
    }

    private FileSystemEntity saveProtocol(FileSystemModel fileSystemModel, String parentPrimaryKey, String childPrimaryKey, String oldProfileName, String oldPollingInterval) {
        fileSystemModel.setSubscriberType(TP);
        fileSystemModel.setPassword(passwordUtilityService.getEncryptedValue(fileSystemModel.getPassword()));

        sterlingMailboxService.createAll(SterlingFunctions.convertFileSystemModelToSterlingMailBoxModels.apply(fileSystemModel));

        if (fileSystemModel.getPoolingInterval().equalsIgnoreCase("ON") && fileSystemModel.getInDirectory().startsWith("/")) {
            sterlingRoutingRuleService.save(fileSystemModel.getInDirectory(), fileSystemModel.getProfileName(), oldProfileName, fileSystemModel.getSubscriberType(), null, fileSystemModel.getRoutingRuleName());
        } else {
            if (isNotNull(oldPollingInterval) && oldPollingInterval.equalsIgnoreCase("ON")) {
                sterlingRoutingRuleService.delete(oldProfileName);
            }
        }
        return fileSystemService.saveProtocol(fileSystemModel, parentPrimaryKey, childPrimaryKey, TP);
    }

}
