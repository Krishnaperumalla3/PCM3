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

package com.pe.pcm.resource.reports;

import com.pe.pcm.common.CommunityManagerKeyValueModel;
import com.pe.pcm.common.CommunityManagerResponse;
import com.pe.pcm.common.CommunityMangerModel;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.file.ManageFileService;
import com.pe.pcm.miscellaneous.FileDropMailboxService;
import com.pe.pcm.reports.*;
import com.pe.pcm.reports.entity.TransInfoDEntity;
import com.pe.pcm.reports.entity.TransferInfoEntity;
import io.swagger.annotations.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA_BU_DP;
import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA_BU_DP_DPR;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;
import static org.springframework.util.StringUtils.hasText;

/**
 * @author Kiran Reddy.
 */
@RestController
@RequestMapping(path = "pcm/reports")
@Api(tags = {"File Transfer Resource"})
public class FileTransferResource {

    private final FileTransferService fileTransferService;
    private final ManageFileService manageFileService;

    private final FileDropMailboxService fileDropMailboxService;

    @Autowired
    public FileTransferResource(FileTransferService fileTransferService,
                                ManageFileService manageFileService, FileDropMailboxService fileDropMailboxService) {
        this.fileTransferService = fileTransferService;
        this.manageFileService = manageFileService;
        this.fileDropMailboxService = fileDropMailboxService;
    }

    @ApiOperation(value = "File Transfer search", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU_DP_DPR)
    @PostMapping(path = "search", produces = APPLICATION_JSON_VALUE)
    public Page<TransferInfoEntity> search(@Validated @RequestBody TransferInfoModel transferInfoModel,
                                           @PageableDefault(sort = {"filearrived"}, direction = Direction.DESC) Pageable pageable) {
        return fileTransferService.search(transferInfoModel, pageable, false);
    }

    @ApiOperation(value = "File Reprocess", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU_DP)
    @PutMapping(path = {"reprocess"}, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerResponse> reprocess(@Validated @RequestParam("seqId") Long seqId,
                                                              @Validated @RequestParam("coreBpId") Long coreBpId) {
        fileTransferService.restartWorkFlow(seqId, coreBpId, "Reprocessed");
        return ResponseEntity.ok(new CommunityManagerResponse(200, "File reprocessed successfully"));
    }

    @ApiOperation(value = "File Drop Again", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU_DP)
    @PutMapping(path = {"drop-again"}, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerResponse> dropAgain(@Validated @RequestParam("seqId") Long seqId,
                                                              @Validated @RequestParam("deliveryBpId") Long deliveryBpId) {
        fileTransferService.restartWorkFlow(seqId, deliveryBpId, "Redropped");
        return ResponseEntity.ok(new CommunityManagerResponse(200, "File re-dropped  successfully"));
    }

    @ApiOperation(value = "File PickUp Now", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU_DP)
    @PutMapping(path = {"pickup-now"}, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerResponse> pickupNow(@Validated @RequestParam("seqId") Long seqId,
                                                              @Validated @RequestParam("pickupBpId") Long pickupBpId) {
        fileTransferService.restartWorkFlow(seqId, pickupBpId, "Repicked");
        return ResponseEntity.ok(new CommunityManagerResponse(200, "File re-picked successfully"));
    }

    @ApiOperation(value = "Transfer File Activity", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU_DP_DPR)
    @GetMapping(path = {"activity"}, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TransInfoDEntity>> viewActivity(@Validated @RequestParam("seqId") Long seqId,
                                                               @Validated @RequestParam("bpId") String bpId) {
        return ResponseEntity.ok(fileTransferService.getActivity(seqId, bpId));
    }

    @ApiOperation(value = "Transfer File Download", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU_DP_DPR)
    @GetMapping(path = "/download-file", produces = APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<FileSystemResource> download(@Validated @RequestParam("seqId") Long seqId,
                                                       @Validated @RequestParam("type") String type) throws IOException {
        FileSystemResource resource = manageFileService.loadFileAsFsResourceForDownload(seqId, type);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @ApiOperation(value = "Load the Transfer file as String", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU_DP_DPR)
    @GetMapping(path = "/view-file")
    public ResponseEntity<String> view(@Validated @RequestParam("seqId") Long seqId,
                                       @Validated @RequestParam("type") String type) {
        return ResponseEntity.ok(manageFileService.loadFileAsStringForView(seqId, type));
    }

    @ApiOperation(value = "Upload file", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU_DP)
    @PostMapping(path = {"upload-file"}, consumes = "multipart/form-data")
    public ResponseEntity<CommunityManagerResponse> upload(@Validated @RequestParam("seqId") Long seqId,
                                                           @Validated @RequestParam("type") String type,
                                                           @Validated @RequestParam("file") MultipartFile file) {

        if (hasText(file.getOriginalFilename())) {
            try {
                File tempFile = File.createTempFile(file.getOriginalFilename(), "");
                FileUtils.copyInputStreamToFile(file.getInputStream(), tempFile);
                //multipartFile.transferTo(tempFile); this also can be used to move the file
                fileDropMailboxService.fileUpload(fileTransferService.get(seqId), type, tempFile, file.getOriginalFilename());
            } catch (IOException io) {
                throw GlobalExceptionHandler.internalServerError(io.getMessage());
            }
        }
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Files is pushing to mailbox. Please wait, this process will take time(depends on file size)"));
    }

    @ApiOperation(value = "Reports: Data by Transaction volume", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU_DP_DPR)
    @GetMapping(path = "/transaction-volume")
    public ResponseEntity<ReportsModel> transactionVolume(@RequestParam("start") String start, @RequestParam("end") String end) {
        return ResponseEntity.ok(fileTransferService.searchVolumeReports(start, end));
    }

    @ApiOperation(value = "Reports: Data by docType volume", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU_DP_DPR)
    @GetMapping(path = "/doctype-volume")
    public ResponseEntity<ReportsModel> doctypeVolume(@RequestParam("start") String start, @RequestParam("end") String end) {
        return ResponseEntity.ok(fileTransferService.searchDocTypeVolumeReports(start.trim(), end.trim()));
    }

    @ApiOperation(value = "Reports: Data by partner by volume", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU_DP_DPR)
    @GetMapping(path = "/partner-volume")
    public ResponseEntity<ReportsModel> partnerVolume(@Nullable @RequestParam("start") String start, @Nullable @RequestParam("end") String end) {
        return ResponseEntity.ok(fileTransferService.searchPartnerVolumeReports(start, end));
    }

    @ApiOperation(value = "Reports: Overdue", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU_DP_DPR)
    @PostMapping(path = "/overdue")
    public ResponseEntity<Page<OverDueMapper>> overdue(@RequestBody OverDueReportModel overDueReportModel,
                                                       @PageableDefault(sort = {"fileArrived"}, direction = Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(fileTransferService.searchOverDue(overDueReportModel, pageable));
    }

    @ApiOperation(value = "Multiple Files Reprocess", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU_DP)
    @PostMapping(path = {"multi-reprocess"}, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerResponse> reprocessMultipleFiles(@RequestBody CommunityMangerModel<CommunityManagerKeyValueModel> communityMangerModel) {
        fileTransferService.restartWorkFlows(communityMangerModel, "Reprocessed");
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Files reprocessed successfully"));
    }

    @ApiOperation(value = "Search Transactions by workflow ID", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU_DP)
    @GetMapping(path = "search/transaction/workflow-id")
    public ResponseEntity<TransferInfoEntity> searchTransactionByWorkFlowId(@RequestParam("workflowId") String workflowId) {
        return ResponseEntity.ok(fileTransferService.searchByOldBpId(workflowId));
    }

    @ApiOperation(value = "Change file status", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU_DP_DPR)
    @PostMapping(path = {"file/status-change"}, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerResponse> changeFileStatus(@RequestBody ChangeFileStatusModel changeFileStatusModel) {
        fileTransferService.changeStatus(changeFileStatusModel);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "File status changed successfully."));
    }

    @ApiOperation(value = "Stream file download", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU_DP_DPR)
    @GetMapping(path = {"stream/download"}, produces = APPLICATION_OCTET_STREAM_VALUE)
    public void streamDownload(@Validated @RequestParam("seqId") Long seqId,
                               @Validated @RequestParam("type") String type,
                               HttpServletRequest request,
                               HttpServletResponse response) {
        manageFileService.stream(fileTransferService.get(seqId), type, request, response);
    }

    @ApiOperation(value = "Transaction Activity Report Error File Download", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU_DP_DPR)
    @GetMapping(path = "activity/download-file", produces = APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<FileSystemResource> downloadReportError(@Validated @RequestParam("filePath") String filePath) throws IOException {
        FileSystemResource resource = manageFileService.loadFileAsFsResourceForDownload(filePath);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename());
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(resource.contentLength())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

//    @ApiOperation(value = "DecryTest", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
//    @ApiResponses({
//            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
//    })
//    @GetMapping(path = "activity/decry/test", produces = APPLICATION_OCTET_STREAM_VALUE)
//    public ResponseEntity<String> decTest() {
//        manageFileService.encryptFileUsingPGP();
//        return ResponseEntity.ok("Hello");
//    }

}
