package com.pe.pcm.application;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.application.entity.ApplicationEntity;
import com.pe.pcm.enums.Protocol;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.googledrive.GoogleDriveModel;
import com.pe.pcm.googledrive.entity.GoogleDriveEntity;
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
import static com.pe.pcm.utils.PCMConstants.APPLICATION_CREATED;

@Service
public class GoogleDriveApplicationService {
    private final GoogleDriveService googleDriveService;
    private final ApplicationService applicationService;
    private final ActivityHistoryService activityHistoryService;
    private final ManageProtocolService manageProtocolService;
    private final ObjectMapper mapper = new ObjectMapper();
    private final BiFunction<ApplicationEntity, GoogleDriveEntity, GoogleDriveModel> deSerialize = GoogleDriveApplicationService::apply;

    @Autowired
    public GoogleDriveApplicationService(GoogleDriveService googleDriveService,
                                         ApplicationService applicationService,
                                         ActivityHistoryService activityHistoryService,
                                         ManageProtocolService manageProtocolService) {
        this.googleDriveService = googleDriveService;
        this.applicationService = applicationService;
        this.activityHistoryService = activityHistoryService;
        this.manageProtocolService = manageProtocolService;
    }

    @Transactional
    public void create(MultipartFile file, GoogleDriveModel googleDriveModel) {
        String parentPrimaryKey = KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.TP_PKEY_PRE_APPEND,
                PCMConstants.TP_PKEY_RANDOM_COUNT);
        String childPrimaryKey = KeyGeneratorUtil.getChildPrimaryKey(googleDriveModel.getProtocol());
        duplicateApplication(googleDriveModel.getProfileId());
        googleDriveService.saveProtocol(googleDriveModel, parentPrimaryKey, childPrimaryKey, file, "APP");
        ApplicationModel application = new ApplicationModel();
        BeanUtils.copyProperties(googleDriveModel, application);
        applicationService.save(application, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.saveApplicationActivity(parentPrimaryKey, APPLICATION_CREATED);
    }
    public void duplicateApplication(String applicationId) {
        applicationService.find(applicationId).ifPresent(applicationEntity -> {
            throw conflict("Application");
        });
    }

    @Transactional
    public void update(MultipartFile file, GoogleDriveModel googleDrivemodel) {
        ApplicationEntity oldApplicationEntity = SerializationUtils.clone(applicationService.get(googleDrivemodel.getPkId()));
        if (!oldApplicationEntity.getApplicationId().equalsIgnoreCase(googleDrivemodel.getProfileId())) {
            duplicateApplication(googleDrivemodel.getProfileId());
        }
        String parentPrimaryKey = oldApplicationEntity.getPkId();
        String childPrimaryKey = oldApplicationEntity.getAppProtocolRef();
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(oldApplicationEntity.getAppIntegrationProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
        Map<String, String> oldGoogleDriveEntityMap = new LinkedHashMap<>();
        boolean isUpdate = true;
        if (!protocol.getProtocol().equalsIgnoreCase(googleDrivemodel.getProtocol())) {
            manageProtocolService.deleteProtocol(oldApplicationEntity.getAppIntegrationProtocol(), oldApplicationEntity.getPkId());
            childPrimaryKey = getChildPrimaryKey(googleDrivemodel.getProtocol());
        } else {
            oldGoogleDriveEntityMap = mapper.convertValue(googleDriveService.get(parentPrimaryKey), new TypeReference<Map<String, String>>() {
            });
        }
        Map<String, String> newGoolgeDriveEntityMap =
                mapper.convertValue(googleDriveService.saveProtocol(googleDrivemodel, parentPrimaryKey, childPrimaryKey, file, "APP"),
                        new TypeReference<Map<String, String>>() {
                        });
        googleDrivemodel.setPemIdentifier(isNotNull(googleDrivemodel.getPemIdentifier())
                ? googleDrivemodel.getPemIdentifier() : oldApplicationEntity.getPemIdentifier());
        ApplicationModel applicationModel = new ApplicationModel();
        BeanUtils.copyProperties(googleDrivemodel, applicationModel);
        ApplicationEntity newApplicationEntity = applicationService.save(applicationModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.updateApplicationActivity(oldApplicationEntity,
                newApplicationEntity, oldGoogleDriveEntityMap, newGoolgeDriveEntityMap, isUpdate);
    }

    @Transactional
    public void delete(String pkId) {
        applicationService.delete(applicationService.get(pkId));
        googleDriveService.delete(pkId);
    }

    public GoogleDriveModel get(String pkId) {
        return deSerialize.apply(applicationService.get(pkId), googleDriveService.get(pkId));
    }


    @Transactional
    public void pemApplicationCreate(GoogleDriveModel googleDriveModel) {
        duplicateApplication(googleDriveModel.getProfileId());
        String parentPrimaryKey = KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.TP_PKEY_PRE_APPEND,
                PCMConstants.TP_PKEY_RANDOM_COUNT);
        String childPrimaryKey = KeyGeneratorUtil.getChildPrimaryKey(googleDriveModel.getProtocol());
        googleDriveService.savePcmProtocol(googleDriveModel, parentPrimaryKey, childPrimaryKey, "APP");
        ApplicationModel application = new ApplicationModel();
        BeanUtils.copyProperties(googleDriveModel, application);
        applicationService.save(application, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.saveApplicationActivity(parentPrimaryKey, APPLICATION_CREATED);
    }

    @Transactional
    public void PemApplicationUpdate(GoogleDriveModel googleDrivemodel) {

        ApplicationEntity oldApplicationEntity = SerializationUtils.clone(applicationService.get(googleDrivemodel.getPkId()));
        if (!oldApplicationEntity.getApplicationId().equalsIgnoreCase(googleDrivemodel.getProfileId())) {
            duplicateApplication(googleDrivemodel.getProfileId());
        }
        String parentPrimaryKey = oldApplicationEntity.getPkId();
        String childPrimaryKey = oldApplicationEntity.getAppProtocolRef();
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(oldApplicationEntity.getAppIntegrationProtocol())).orElseThrow(GlobalExceptionHandler::unknownProtocol);
        Map<String, String> oldGoogleDriveEntityMap = new LinkedHashMap<>();
        boolean isUpdate = true;
        if (!protocol.getProtocol().equalsIgnoreCase(googleDrivemodel.getProtocol())) {
            manageProtocolService.deleteProtocol(oldApplicationEntity.getAppIntegrationProtocol(), oldApplicationEntity.getPkId());
            childPrimaryKey = getChildPrimaryKey(googleDrivemodel.getProtocol());
        } else {
            oldGoogleDriveEntityMap = mapper.convertValue(googleDriveService.get(parentPrimaryKey), new TypeReference<Map<String, String>>() {
            });
        }
        Map<String, String> newGoolgeDriveEntityMap =
                mapper.convertValue(googleDriveService.savePcmProtocol(googleDrivemodel, parentPrimaryKey, childPrimaryKey, "APP"),
                        new TypeReference<Map<String, String>>() {
                        });
        googleDrivemodel.setPemIdentifier(isNotNull(googleDrivemodel.getPemIdentifier())
                ? googleDrivemodel.getPemIdentifier() : oldApplicationEntity.getPemIdentifier());
        ApplicationModel applicationModel = new ApplicationModel();
        BeanUtils.copyProperties(googleDrivemodel, applicationModel);
        ApplicationEntity newApplicationEntity = applicationService.save(applicationModel, parentPrimaryKey, childPrimaryKey);
        activityHistoryService.updateApplicationActivity(oldApplicationEntity,
                newApplicationEntity, oldGoogleDriveEntityMap, newGoolgeDriveEntityMap, isUpdate);
    }

    private static GoogleDriveModel apply(ApplicationEntity tradingApplication, GoogleDriveEntity dEntity) {
        GoogleDriveModel gModel = new GoogleDriveModel();
        gModel.setPkId(tradingApplication.getPkId())
                .setPkId(tradingApplication.getPkId())
                .setProfileName(tradingApplication.getApplicationName())
                .setProfileId(tradingApplication.getApplicationId())
                .setProtocol(tradingApplication.getAppIntegrationProtocol())
                .setEmailId(tradingApplication.getEmailId())
                .setPhone(tradingApplication.getPhone())
                .setHubInfo(convertStringToBoolean(dEntity.getIsHubInfo()))
                .setStatus(convertStringToBoolean(tradingApplication.getAppIsActive()))
                .setPhone(tradingApplication.getPhone())
                .setPemIdentifier(tradingApplication.getPemIdentifier())
                .setPgpInfo(tradingApplication.getPgpInfo())
                .setPgpInfo(tradingApplication.getPgpInfo())
                .setPemIdentifier(tradingApplication.getPemIdentifier());

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
