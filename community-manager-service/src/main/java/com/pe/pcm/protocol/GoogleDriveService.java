package com.pe.pcm.protocol;

import com.pe.pcm.googledrive.GoogleDriveModel;
import com.pe.pcm.googledrive.GoogleDriveRepository;
import com.pe.pcm.googledrive.entity.GoogleDriveEntity;
import com.pe.pcm.miscellaneous.PasswordUtilityService;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;

import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;
import static com.pe.pcm.protocol.function.ProtocolFunctions.mapperToGDriveEntity;
import static org.springframework.util.StringUtils.hasText;

@Service
public class GoogleDriveService {
    private final GoogleDriveRepository googleDriveRepository;
    private final PasswordUtilityService passwordUtilityService;

    @Autowired
    public GoogleDriveService(GoogleDriveRepository googleDriveRepository, PasswordUtilityService passwordUtilityService) {
        this.googleDriveRepository = googleDriveRepository;
        this.passwordUtilityService = passwordUtilityService;
    }

    public GoogleDriveEntity get(String pkId) {
        return googleDriveRepository.findBySubscriberId(pkId).orElseThrow(() -> notFound("Protocol"));
    }

    public void delete(String pkId) {
        googleDriveRepository.findBySubscriberId(pkId).ifPresent(googleDriveRepository::delete);
    }

    public GoogleDriveEntity saveProtocol(GoogleDriveModel googleDriveModel, String parentPrimaryKey, String childPrimaryKey, MultipartFile file, String subscriberType) {
        String encAuthJson;
        String clientId;
        String clientEmail;
        try {
            if (file != null && !file.isEmpty() && hasText(new String(file.getBytes()))) {
                JSONObject obj = new JSONObject(new String(file.getBytes()));
                clientId = obj.getString("client_id");
                clientEmail = obj.getString("client_email");
                encAuthJson = passwordUtilityService.encryptFileWithJava8(new String(file.getBytes()));
            } else {
                GoogleDriveEntity driveEntity = get(parentPrimaryKey);
                clientId = driveEntity.getClientId();
                clientEmail = driveEntity.getClientEmail();
                encAuthJson = driveEntity.getAuthJson();
            }

            GoogleDriveEntity googleDriveEntity = mapperToGDriveEntity.apply(googleDriveModel);
            googleDriveEntity.setPkId(childPrimaryKey)
                    .setSubscriberId(parentPrimaryKey)
                    .setSubscriberType(subscriberType)
                    .setClientId(clientId)
                    .setClientEmail(clientEmail)
                    .setAuthJson(encAuthJson)
                    .setIsHubInfo("N");
            return googleDriveRepository.save(googleDriveEntity);
        } catch (Exception e) {
            e.printStackTrace();
            throw internalServerError("unable to save the protocol");
        }
    }

    public GoogleDriveEntity savePcmProtocol(GoogleDriveModel googleDriveModel, String parentPrimaryKey, String childPrimaryKey, String subscriberType) {
        String encAuthJson;
        String clientId;
        String clientEmail;
        byte[] decoded = Base64.decodeBase64(googleDriveModel.getFileInput());
        encAuthJson = new String(decoded, StandardCharsets.UTF_8);
        try {
            if (googleDriveModel.getFileInput() != null && !googleDriveModel.getFileInput().isEmpty()) {
                JSONObject obj = new JSONObject(encAuthJson);
                clientId = obj.getString("client_id");
                clientEmail = obj.getString("client_email");
                encAuthJson = passwordUtilityService.encryptFileWithJava8(encAuthJson);
            } else {
                GoogleDriveEntity driveEntity = get(parentPrimaryKey);
                clientId = driveEntity.getClientId();
                clientEmail = driveEntity.getClientEmail();
                encAuthJson = driveEntity.getAuthJson();
            }

            GoogleDriveEntity googleDriveEntity = mapperToGDriveEntity.apply(googleDriveModel);
            googleDriveEntity.setPkId(childPrimaryKey)
                    .setSubscriberId(parentPrimaryKey)
                    .setSubscriberType(subscriberType)
                    .setClientId(clientId)
                    .setClientEmail(clientEmail)
                    .setAuthJson(encAuthJson)
                    .setIsHubInfo("N");
            return googleDriveRepository.save(googleDriveEntity);

        } catch (Exception e) {
            e.printStackTrace();
            throw internalServerError("unable to save the protocol");
        }

    }

}
