package com.pe.pcm.partner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.enums.Protocol;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.googledrive.GoogleDriveModel;
import com.pe.pcm.googledrive.entity.GoogleDriveEntity;
import com.pe.pcm.partner.entity.PartnerEntity;
import com.pe.pcm.protocol.GoogleDriveService;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.utils.CommonFunctions;
import com.pe.pcm.utils.KeyGeneratorUtil;
import com.pe.pcm.utils.PCMConstants;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

import static com.pe.pcm.exception.GlobalExceptionHandler.conflict;
import static com.pe.pcm.utils.CommonFunctions.convertStringToBoolean;
import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.KeyGeneratorUtil.getChildPrimaryKey;

@Service
public class GoogleDrivePartnerService {

    private final GoogleDriveService googleDriveService;
    private final PartnerService partnerService;
    private final ActivityHistoryService activityHistoryService;
    private final ManageProtocolService manageProtocolService;
    private final ObjectMapper mapper = new ObjectMapper();

    private final BiFunction<PartnerEntity, GoogleDriveEntity, GoogleDriveModel> deSerialize = GoogleDrivePartnerService::apply;

    @Autowired
    public GoogleDrivePartnerService(GoogleDriveService googleDriveService, PartnerService partnerService, ActivityHistoryService activityHistoryService, ManageProtocolService manageProtocolService) {
        this.googleDriveService = googleDriveService;
        this.partnerService = partnerService;
        this.activityHistoryService = activityHistoryService;
        this.manageProtocolService = manageProtocolService;
    }

    @Transactional
    public void create(MultipartFile file, GoogleDriveModel googleDrivemodel) {
        duplicatePartner(googleDrivemodel.getProfileId());
        String parentPrimaryKey = KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.TP_PKEY_PRE_APPEND,
                PCMConstants.TP_PKEY_RANDOM_COUNT);
        String childPrimaryKey = KeyGeneratorUtil.getChildPrimaryKey(googleDrivemodel.getProtocol());
        googleDriveService.saveProtocol(googleDrivemodel, parentPrimaryKey, childPrimaryKey, file, "TP");
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(googleDrivemodel, partnerModel);
        partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.savePartnerActivity(parentPrimaryKey, "Trading Partner created.");
    }
    void duplicatePartner(String partnerId) {
        Optional<PartnerEntity> tradingPartnerEntityOptional = partnerService.find(partnerId);
        tradingPartnerEntityOptional.ifPresent(tradingPartnerEntity -> {
            throw conflict("Partner");
        });
    }
    @Transactional
    public void pcmPartneCreate(GoogleDriveModel googleDrivemodel) {
        duplicatePartner(googleDrivemodel.getProfileId());
        String parentPrimaryKey = KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.TP_PKEY_PRE_APPEND,
                PCMConstants.TP_PKEY_RANDOM_COUNT);
        String childPrimaryKey = KeyGeneratorUtil.getChildPrimaryKey(googleDrivemodel.getProtocol());
        googleDriveService.savePcmProtocol(googleDrivemodel, parentPrimaryKey, childPrimaryKey, "TP");
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(googleDrivemodel, partnerModel);
        partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.savePartnerActivity(parentPrimaryKey, "Trading Partner created.");
    }
    @Transactional
    public void pcmPartnerUpdate(GoogleDriveModel googleDrivemodel) {
        PartnerEntity oldPartnerEntity = SerializationUtils.clone(partnerService.get(googleDrivemodel.getPkId()));
        if (!oldPartnerEntity.getTpId().equalsIgnoreCase(googleDrivemodel.getProfileId())) {
            duplicatePartner(googleDrivemodel.getProfileId());
        }
        String parentPrimaryKey = oldPartnerEntity.getPkId();
        String childPrimaryKey = oldPartnerEntity.getPartnerProtocolRef();
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(oldPartnerEntity.getTpProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
        Map<String, String> oldGoogleDriveEntityMap = new LinkedHashMap<>();
        boolean isUpdate = true;
        if (!protocol.getProtocol().equalsIgnoreCase(googleDrivemodel.getProtocol())) {
            manageProtocolService.deleteProtocol(oldPartnerEntity.getTpProtocol(), oldPartnerEntity.getPkId());
            childPrimaryKey = getChildPrimaryKey(googleDrivemodel.getProtocol());
        } else {
            oldGoogleDriveEntityMap = mapper.convertValue(googleDriveService.get(parentPrimaryKey), new TypeReference<Map<String, String>>() {
            });
        }
        Map<String, String> newGoogleDriveEntityMap =
                mapper.convertValue(googleDriveService.savePcmProtocol(googleDrivemodel, parentPrimaryKey, childPrimaryKey, "TP"),
                        new TypeReference<Map<String, String>>() {
                        });
        googleDrivemodel.setPemIdentifier(isNotNull(googleDrivemodel.getPemIdentifier())
                ? googleDrivemodel.getPemIdentifier() : oldPartnerEntity.getPemIdentifier());
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(googleDrivemodel, partnerModel);
        PartnerEntity newPartnerEntity = partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.updatePartnerActivity(oldPartnerEntity, newPartnerEntity, oldGoogleDriveEntityMap, newGoogleDriveEntityMap, isUpdate);
    }


    @Transactional
    public void update(MultipartFile file, GoogleDriveModel googleDrivemodel) {
        PartnerEntity oldPartnerEntity = SerializationUtils.clone(partnerService.get(googleDrivemodel.getPkId()));
        if (!oldPartnerEntity.getTpId().equalsIgnoreCase(googleDrivemodel.getProfileId())) {
            duplicatePartner(googleDrivemodel.getProfileId());
        }
        String parentPrimaryKey = oldPartnerEntity.getPkId();
        String childPrimaryKey = oldPartnerEntity.getPartnerProtocolRef();
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(oldPartnerEntity.getTpProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
        Map<String, String> oldGoogleDriveEntityMap = new LinkedHashMap<>();
        boolean isUpdate = true;
        if (!protocol.getProtocol().equalsIgnoreCase(googleDrivemodel.getProtocol())) {
            manageProtocolService.deleteProtocol(oldPartnerEntity.getTpProtocol(), oldPartnerEntity.getPkId());
            childPrimaryKey = getChildPrimaryKey(googleDrivemodel.getProtocol());
        } else {
            oldGoogleDriveEntityMap = mapper.convertValue(googleDriveService.get(parentPrimaryKey), new TypeReference<Map<String, String>>() {
            });
        }
        Map<String, String> newGoogleDriveEntityMap =
                mapper.convertValue(googleDriveService.saveProtocol(googleDrivemodel, parentPrimaryKey, childPrimaryKey, file, "TP"),
                        new TypeReference<Map<String, String>>() {
                        });
        googleDrivemodel.setPemIdentifier(isNotNull(googleDrivemodel.getPemIdentifier())
                ? googleDrivemodel.getPemIdentifier() : oldPartnerEntity.getPemIdentifier());
        PartnerModel partnerModel = new PartnerModel();
        BeanUtils.copyProperties(googleDrivemodel, partnerModel);
        PartnerEntity newPartnerEntity = partnerService.save(partnerModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.updatePartnerActivity(oldPartnerEntity, newPartnerEntity, oldGoogleDriveEntityMap, newGoogleDriveEntityMap, isUpdate);
    }

    @Transactional
    public void delete(String pkId) {
        partnerService.delete(partnerService.findPartnerById(pkId));
        googleDriveService.delete(pkId);
    }

    public GoogleDriveModel get(String pkId) {
        return deSerialize.apply(partnerService.get(pkId), googleDriveService.get(pkId));
    }

    private static GoogleDriveModel apply(PartnerEntity tradingPartner, GoogleDriveEntity dEntity) {
        GoogleDriveModel gModel = new GoogleDriveModel();
        gModel.setPkId(tradingPartner.getPkId())
                .setProfileName(tradingPartner.getTpName())
                .setProfileId(tradingPartner.getTpId())
                .setAddressLine1(tradingPartner.getAddressLine1())
                .setAddressLine2(tradingPartner.getAddressLine2())
                .setProtocol(tradingPartner.getTpProtocol())
                .setHubInfo(convertStringToBoolean(dEntity.getIsHubInfo()))
                .setEmailId(tradingPartner.getEmail())
                .setPhone(tradingPartner.getPhone())
                .setStatus(convertStringToBoolean(tradingPartner.getStatus()))
                .setPgpInfo(tradingPartner.getPgpInfo())
                .setPemIdentifier(tradingPartner.getPemIdentifier());

        return gModel.setProjectId(dEntity.getProjectId())
                .setClientId(dEntity.getClientId())
                .setClientEmail(dEntity.getClientEmail())
                .setInDirectory(dEntity.getInDirectory())
                .setOutDirectory(dEntity.getOutDirectory())
                .setFileType(dEntity.getFileType())
                .setDeleteAfterCollection(CommonFunctions.convertStringToBoolean(dEntity.getDeleteAfterCollection()))
                .setPoolingInterval(dEntity.getPoolingIntervalMins());

    }
}
