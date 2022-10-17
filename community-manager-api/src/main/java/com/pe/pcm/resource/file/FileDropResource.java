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
import com.pe.pcm.miscellaneous.FileDropMailboxService;
import io.swagger.annotations.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA_BU_DP;
import static javax.servlet.http.HttpServletResponse.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Kiran Reddy.
 */
@RestController
@RequestMapping(value = "pcm/mailbox/file-drop", produces = APPLICATION_JSON_VALUE)
@Api(tags = {"Upload file to Mailbox"})
//@PreAuthorize(DP_DPR)
public class FileDropResource {

    private final FileDropMailboxService fileDropMailboxService;

    @Autowired
    public FileDropResource(FileDropMailboxService fileDropMailboxService) {
        this.fileDropMailboxService = fileDropMailboxService;
    }

    @ApiOperation(value = "Upload File to Mailbox", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_FORBIDDEN, message = "forbidden access"),
    })
    @PostMapping(consumes = "multipart/form-data")
    @PreAuthorize(SA_OB_BA_BU_DP)
    public ResponseEntity<CommunityManagerResponse> uploadFiles(@Validated @RequestParam("partnerPkId") String partnerPkId,
                                                                @Validated @RequestParam("files") MultipartFile[] files) {
        File[] tempFiles = null;
        String[] fileNames = null;
        if (files != null && files.length > 0) {
            tempFiles = new File[files.length];
            fileNames = new String[files.length];
            for (int index = 0; index < files.length; index++) {
                MultipartFile multipartFile = files[index];
                if (StringUtils.hasText(multipartFile.getOriginalFilename())) {
                    try {
                        File tempFile = File.createTempFile(multipartFile.getOriginalFilename(), "");
                        FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), tempFile);
                        //multipartFile.transferTo(tempFile); this also can be used to move the file
                        tempFiles[index] = tempFile;
                        fileNames[index] = multipartFile.getOriginalFilename();
                    } catch (IOException io) {
                        throw GlobalExceptionHandler.internalServerError(io.getMessage());
                    }
                }
            }
        }
        fileDropMailboxService.uploadFiles(partnerPkId, tempFiles, fileNames);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Files is pushing to mailbox. Please wait, this process will take time(depends on files size)"));
    }
}
