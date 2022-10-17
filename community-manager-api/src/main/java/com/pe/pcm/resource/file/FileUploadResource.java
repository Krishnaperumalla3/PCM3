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

package com.pe.pcm.resource.file;

import com.pe.pcm.common.CommunityManagerResponse;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.file.FileUploadService;
import com.pe.pcm.miscellaneous.UserUtilityService;
import com.pe.pcm.reports.entity.TransInfoDEntity;
import io.swagger.annotations.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA_BU_DP;
import static com.pe.pcm.utils.PCMConstants.FILE_PUSH;
import static java.lang.Boolean.TRUE;
import static javax.servlet.http.HttpServletResponse.*;

/**
 * @author Kiran Reddy.
 */
@RestController
@PreAuthorize(SA_OB_BA_BU_DP)
@RequestMapping(path = "pcm/file")
@Api(tags = {"File Upload to Mailbox using chunks mechanism"})
@ConditionalOnProperty(
        value = "cm.api-connect-enabled",
        havingValue = "true") // this for just to hide the resource to precisely we need to clear this soon
public class FileUploadResource {

    private final FileUploadService fileUploadService;
    private final UserUtilityService userUtilityService;

    @Autowired
    public FileUploadResource(FileUploadService fileUploadService, UserUtilityService userUtilityService) {
        this.fileUploadService = fileUploadService;
        this.userUtilityService = userUtilityService;
    }

    @ApiOperation(value = "Create the process for Upload File(as chunks)", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @GetMapping("upload/start")
    public ResponseEntity<CommunityManagerResponse> startUploading() {
        return ResponseEntity.ok(
                new CommunityManagerResponse(200, fileUploadService.createTempDirectory())
        );
    }

    @ApiOperation(value = "Upload Chunk", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "upload/chunk", consumes = "multipart/form-data")
    public ResponseEntity<CommunityManagerResponse> uploadChunk(@Validated @RequestParam("tmpDirectoryName") String tmpDirectoryName,
                                                                @Validated @RequestParam("fileIndex") Integer fileIndex,
                                                                @Validated @RequestParam("file") MultipartFile multipartFile) {
        fileUploadService.saveChunk(tmpDirectoryName, fileIndex, multipartFile);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Chunk Uploaded Successfully!"));
    }

    @ApiOperation(value = "Merge Chunks into single file and send to Mailbox", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping("upload/end-mailbox")
    public ResponseEntity<CommunityManagerResponse> mergeChunksAndDropIntoMailbox(@Validated @RequestParam("tmpDirectoryName") String tmpDirectoryName,
                                                                                  @Validated @RequestParam("fileName") String fileName,
                                                                                  @Validated @RequestParam("mailbox") String mailbox) {
        fileUploadService.mergeChunksAndDropIntoMailbox(tmpDirectoryName, fileName, mailbox, userUtilityService.getUserOrRole(TRUE));
        return ResponseEntity.ok(new CommunityManagerResponse(200, FILE_PUSH));
    }

    @ApiOperation(value = "Cancel the chunks uploading", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping("upload/stop-process")
    public ResponseEntity<CommunityManagerResponse> cancel(@Validated @RequestParam("tmpDirectoryName") String tmpDirectoryName) {
        fileUploadService.stopTheChunkUpload(tmpDirectoryName);
        return ResponseEntity.ok(new CommunityManagerResponse(200, FILE_PUSH));
    }

    @ApiOperation(value = "Upload file", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "upload/file", consumes = "multipart/form-data")
    public ResponseEntity<CommunityManagerResponse> upload(@Validated @RequestParam("mailbox") String mailbox,
                                                           @Validated @RequestParam("file") MultipartFile multipartFile) {
        try {
            if (multipartFile.getOriginalFilename() != null) {
                File tempFile = File.createTempFile(multipartFile.getOriginalFilename(), "");
                FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), tempFile);
                fileUploadService.uploadFile(mailbox, tempFile, multipartFile.getOriginalFilename());
            } else {
                throw GlobalExceptionHandler.internalServerError("Unable to upload the file to mailbox, please try again.");
            }
        } catch (IOException io) {
            throw GlobalExceptionHandler.internalServerError(io.getMessage());
        }
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Uploading file to mailbox. Please wait, this process will take time(depends on files size)"));
    }

    @ApiOperation(value = "User File Activity", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping(path = "activity/{activityName}")
    public ResponseEntity<List<TransInfoDEntity>> getUserFileActivity(@PathVariable("activityName") String activityName,
                                                                      @RequestParam("mailbox") String mailbox) {
        return ResponseEntity.ok(fileUploadService.getFileActivity(mailbox, activityName));
    }

}
