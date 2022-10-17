package com.pe.pcm.resource.google.drive;


import com.google.api.services.drive.model.File;
import com.pe.pcm.common.CommunityManagerResponse;
import com.pe.pcm.google.drive.GoogleFileManagerService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static com.pe.pcm.constants.AuthoritiesConstants.ONLY_SA;
import static javax.servlet.http.HttpServletResponse.*;

@RestController
@RequestMapping(path = "/pcm/google/google-drive")
@Api(tags = {"Google Drive File Manage APIs"})
public class GoogleDriveFileManagerResource {
    private final GoogleFileManagerService googleFileManagerService;

    @Autowired
    public GoogleDriveFileManagerResource(GoogleFileManagerService googleFileManagerService) {
        this.googleFileManagerService = googleFileManagerService;
    }


    @ApiOperation(value = "Get The Files From Folder", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @GetMapping({"get-files-list/{parentId}/{pkId}"})
    @PreAuthorize(ONLY_SA)
    public ResponseEntity<List<File>> getFilesList(@PathVariable("parentId") String parentId, @PathVariable("pkId") String pkId) {
        return ResponseEntity.ok(googleFileManagerService.listFolderContent(parentId, pkId));
    }

    @ApiOperation(value = "Get Folders And File List", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @GetMapping({"/get-folders-and-files-list/{pkId}"})
    @PreAuthorize(ONLY_SA)
    public ResponseEntity<List<File>> getFoldersAndFilesList(@PathVariable("pkId") String pkId) {
        return ResponseEntity.ok(googleFileManagerService.getFoldersAndFilesList(pkId));
    }

    @ApiOperation(value = "Create A Folder In A GoogleDrive", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PostMapping(value = "create-folder/{folderName}/{pkId}")
    @PreAuthorize(ONLY_SA)
    public ResponseEntity<CommunityManagerResponse> createFolder(@PathVariable("folderName") String folderName, @PathVariable("pkId") String pkId) {
        googleFileManagerService.createFolder(folderName, pkId);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Folder Created successfully!"));
    }


    @ApiOperation(value = "Upload File Into Specific Folder", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PostMapping(value = "upload-file/{pkId}", consumes = "multipart/form-data")
    @PreAuthorize(ONLY_SA)
    public ResponseEntity<CommunityManagerResponse> uploadFile(@Validated @RequestParam("folderId") String folderId,
                                                               @Validated @RequestParam("file") MultipartFile multipartFile,
                                                               @PathVariable("pkId") String pkId) {
        googleFileManagerService.uploadFile(folderId, multipartFile, pkId);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "File uploaded successfully!"));
    }

    @ApiOperation(value = "Delete A Folder ", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @GetMapping("/delete/{id}/{pkId}")
    @PreAuthorize(ONLY_SA)
    public ResponseEntity<CommunityManagerResponse> delete(@PathVariable String id, @PathVariable("pkId") String pkId) {
        googleFileManagerService.deleteFile(id, pkId);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "File Deleted successfully!"));
    }

    @ApiOperation(value = "download file  ", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "no records found")
    })
    @GetMapping("/download/{id}/{pkId}")
    @PreAuthorize(ONLY_SA)
    public ResponseEntity<FileSystemResource> download(@PathVariable String id,
                                                       @PathVariable("pkId") String pkId)
            throws IOException, GeneralSecurityException {
        AtomicReference<String> fileName = new AtomicReference<>();
        FileSystemResource resource = googleFileManagerService.downloadFile(id, pkId, fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName.get());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}
