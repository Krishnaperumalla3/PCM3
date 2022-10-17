package com.pe.pcm.google.drive;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.model.File;
import com.pe.pcm.googledrive.entity.GoogleDriveEntity;
import com.pe.pcm.protocol.GoogleDriveService;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static org.springframework.util.StringUtils.cleanPath;
import static org.springframework.util.StringUtils.hasText;

@Service
public class GoogleFileManagerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoogleFileManagerService.class);
    private final GoogleDriveInstanceConfig googleDriveInstanceConfig;
    private final GoogleDriveService googleDriveService;

    @Autowired
    public GoogleFileManagerService(GoogleDriveInstanceConfig googleDriveInstanceConfig,
                                    GoogleDriveService googleDriveService) {
        this.googleDriveInstanceConfig = googleDriveInstanceConfig;
        this.googleDriveService = googleDriveService;
    }

    public List<File> listFolderContent(String parentId, String pkId) {
        GoogleDriveEntity optionalGoogleDriveEntity = googleDriveService.get(pkId);
        try {
            return googleDriveInstanceConfig.getInstance(optionalGoogleDriveEntity.getAuthJson()).files().list()
                    .setQ("'" + (parentId == null ? "root" : parentId) + "' in parents")
                    .setPageSize(10)
                    .setFields("nextPageToken, files(id, name)")
                    .execute().getFiles();
        } catch (GeneralSecurityException | IOException e) {
            throw internalServerError(e.getMessage());
        }

    }

    public void createFolder(String folderName, String pkId) {
        GoogleDriveEntity optionalGoogleDriveEntity = googleDriveService.get(pkId);
        try {
            LOGGER.info("Folder Created Successfully, Folder Id: {}",
                    googleDriveInstanceConfig.getInstance(optionalGoogleDriveEntity.getAuthJson())
                            .files()
                            .create(new File().setName(folderName).setMimeType("application/vnd.google-apps.folder"))
                            .setFields("id")
                            .execute().getId());
        } catch (GeneralSecurityException | IOException e) {
            throw internalServerError(e.getMessage());
        }
    }

    public void deleteFile(String fileId, String pkId) {
        GoogleDriveEntity optionalGoogleDriveEntity = googleDriveService.get(pkId);
        try {
            googleDriveInstanceConfig.getInstance(optionalGoogleDriveEntity.getAuthJson()).files().delete(fileId).execute();
            LOGGER.info("Folder deleted Successfully");
        } catch (GeneralSecurityException | IOException e) {
            throw internalServerError(e.getMessage());
        }
    }

    public void uploadFile(String folderId, @NotNull MultipartFile multipartFile, String pkId) {
        GoogleDriveEntity optionalGoogleDriveEntity = googleDriveService.get(pkId);
        if (hasText(multipartFile.getOriginalFilename()) && cleanPath(multipartFile.getOriginalFilename()).contains("..")) {
            throw internalServerError("Sorry! Filename contains invalid path sequence ");
        }
        try {
            java.io.File tmpFile = java.io.File.createTempFile(multipartFile.getOriginalFilename(), "");
            multipartFile.transferTo(tmpFile);
            LOGGER.info("Uploading file to Google Drive");
            googleDriveInstanceConfig.getInstance(optionalGoogleDriveEntity.getAuthJson())
                    .files()
                    .create(
                            new File().setName(multipartFile.getOriginalFilename())
                                    .setParents(Collections.singletonList(folderId)),
                            new FileContent(multipartFile.getContentType(),
                                    tmpFile))
                    .setFields("id, parents")
                    .execute();
            tmpFile.deleteOnExit();
            LOGGER.info("Folder uploaded Successfully");
        } catch (GeneralSecurityException | IOException e) {
            throw internalServerError(e.getMessage());
        }

    }

    public List<File> getFoldersAndFilesList(String pkId) {
        GoogleDriveEntity optionalGoogleDriveEntity = googleDriveService.get(pkId);
        try {
            return googleDriveInstanceConfig.getInstance(optionalGoogleDriveEntity.getAuthJson()).files().list()
                    .setPageSize(10)
                    .setFields("nextPageToken, files(id, name)")
                    .execute().getFiles();
        } catch (GeneralSecurityException | IOException e) {
            throw internalServerError(e.getMessage());
        }

    }

    public FileSystemResource downloadFile(String id, String pkId, AtomicReference<String> fileName) throws GeneralSecurityException, IOException {
        GoogleDriveEntity optionalGoogleDriveEntity = googleDriveService.get(pkId);
        java.io.File tempFile = java.io.File.createTempFile("Pragma", "");
        File file = googleDriveInstanceConfig.getInstance(optionalGoogleDriveEntity.getAuthJson()).files().get(id).execute();
        FileUtils.copyInputStreamToFile(googleDriveInstanceConfig.getInstance(optionalGoogleDriveEntity.getAuthJson()).files().get(id).executeMediaAsInputStream(), tempFile);
        if (optionalGoogleDriveEntity.getDeleteAfterCollection() != null && optionalGoogleDriveEntity.getDeleteAfterCollection().equals("Y")) {
            googleDriveInstanceConfig.getInstance(optionalGoogleDriveEntity.getAuthJson()).files().delete(file.getId()).execute();
        }
        fileName.set(file.getName());
        return new FileSystemResource(tempFile);
    }

}
