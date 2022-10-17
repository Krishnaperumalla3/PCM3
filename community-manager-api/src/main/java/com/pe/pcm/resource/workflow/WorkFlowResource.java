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

package com.pe.pcm.resource.workflow;

import com.pe.pcm.common.CommunityManagerMapModel;
import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.common.CommunityManagerResponse;
import com.pe.pcm.common.CommunityMangerModel;
import com.pe.pcm.reports.DataFlowMapper;
import com.pe.pcm.reports.DataFlowModel;
import com.pe.pcm.workflow.ManageWorkFlowService;
import com.pe.pcm.workflow.ProcessRuleModel;
import com.pe.pcm.workflow.WorkFlowUIModel;
import com.pe.pcm.workflow.entity.WorkFlowActivityHistoryEntity;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

import static com.pe.pcm.constants.AuthoritiesConstants.*;
import static com.pe.pcm.utils.CommonFunctions.exportAllOutStreamResponse;
import static com.pe.pcm.utils.CommonFunctions.outStreamResponse;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static javax.servlet.http.HttpServletResponse.SC_CONFLICT;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Kiran Reddy.
 */
@RestController
@RequestMapping(path = "pcm/workflow")
@Api(tags = {"WorkFlow Resource"})
public class WorkFlowResource {

    private final ManageWorkFlowService workFlowService;

    @Autowired
    public WorkFlowResource(ManageWorkFlowService workFlowService) {
        this.workFlowService = workFlowService;
    }

    @ApiOperation(value = "Import WorkFlow", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA)
    @PostMapping(path = "import", consumes = "multipart/form-data")
    public ResponseEntity<CommunityManagerResponse> importWorkFlow(@Validated @RequestParam("file") MultipartFile file,
                                                                   @RequestParam("partnerPkId") String profileId,
                                                                   @RequestParam("applicationPkId") String applicationId) {
        workFlowService.importWorkFlow(file, profileId, applicationId, false, null);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Import workflow completed successfully"));
    }

    @ApiOperation(value = "Import Workflow with Profiles", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")

    })
    @PreAuthorize(SA_OB_BA)
    @PostMapping(path = "import-with-profile", consumes = "multipart/form-data")
    public ResponseEntity<CommunityManagerResponse> importWithProfile(@Validated @RequestParam("file") MultipartFile file) {
        workFlowService.importWorkFlowWithProfile(file, null, false);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Import workflow completed successfully"));
    }

    @ApiOperation(value = "Export the Workflow as XML", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")

    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "export", produces = APPLICATION_JSON_VALUE)
    public void export(@Validated @RequestParam("partnerPkId") String profileId,
                       @RequestParam("applicationPkId") String applicationId, HttpServletResponse httpServletResponse) {
        String dataToStream = workFlowService.exportWorkFlow(profileId, applicationId, false, false);
        outStreamResponse(httpServletResponse, dataToStream);
    }

    @ApiOperation(value = "Export the Workflow with Profiles", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "export-with-profile", produces = APPLICATION_JSON_VALUE)
    public void exportWithProfile(@Validated @RequestParam("partnerPkId") String profileId,
                                  @RequestParam("applicationPkId") String applicationId, HttpServletResponse httpServletResponse) {
        String dataToStream = workFlowService.exportWorkFlow(profileId, applicationId, false, true);
        outStreamResponse(httpServletResponse, dataToStream);
    }

    @ApiOperation(value = "Get Workflow", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping
    @PreAuthorize(SA_OB_BA_BU)
    public ResponseEntity<WorkFlowUIModel> get(@Validated @RequestParam("partnerPkId") String profileId,
                                               @Validated @RequestParam("applicationPkId") String applicationId) {
        return ResponseEntity.ok(workFlowService.getWorkFlow(profileId, applicationId, false, false));
    }

    @ApiOperation(value = "Get Workflow by Profile Name", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "get-workflow-by-names")
    public ResponseEntity<WorkFlowUIModel> getWorkFlow(@Validated @RequestParam("partnerName") String profileName,
                                                       @Validated @RequestParam("applicationName") String applicationName) {
        return ResponseEntity.ok(workFlowService.getWorkFlow(profileName, applicationName, true, false));
    }

    @ApiOperation(value = "Create Workflow", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")

    })
    @PreAuthorize(SA_OB_BA)
    @PostMapping(path = "create", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerResponse> create(@Validated @RequestBody WorkFlowUIModel workFlowUIModel,
                                                           @RequestParam("partnerPkId") String profileId,
                                                           @RequestParam("applicationPkId") String applicationId) {
        workFlowService.createWorkflow(workFlowUIModel, profileId, applicationId);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Workflow created successfully"));
    }

    @ApiOperation(value = "Delete Workflow", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")

    })
    @DeleteMapping
    @PreAuthorize(SA_OB_BA)
    public ResponseEntity<CommunityManagerResponse> delete(@Validated @RequestParam("partnerPkId") String profileId,
                                                           @Validated @RequestParam("applicationPkId") String applicationId) {
        workFlowService.delete(profileId.trim(), applicationId.trim(), true);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Workflow deleted successfully"));
    }

    @ApiOperation(value = "Export All workflows into CSV", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")

    })
    @PreAuthorize(SA_OB_BA_BU_DP_DPR)
    @GetMapping(path = "export-all", produces = APPLICATION_JSON_VALUE)
    public void export(HttpServletResponse httpServletResponse) {
        String dataToStream = workFlowService.exportAll();
        exportAllOutStreamResponse(httpServletResponse, dataToStream);
    }

    @ApiOperation(value = "Get WorkFlow Activity History", authorizations = {@Authorization(value = "apiKey")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "activity/{processRefId}", produces = APPLICATION_JSON_VALUE)
    public Page<WorkFlowActivityHistoryEntity> getHistory(@Validated @PathVariable("processRefId") String processRefId,
                                                          @PageableDefault(size = 10, page = 0, sort = {"activityDt"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return workFlowService.getHistory(processRefId, pageable);
    }

    @ApiOperation(value = "Workflow Report: Search Flows", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")

    })
    @PreAuthorize(SA_OB_BA_BU_DP_DPR)
    @PostMapping(path = "/search", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<DataFlowMapper>> search(@Validated @RequestBody DataFlowModel dataFlowModel,
                                                       @PageableDefault(size = 10, page = 0, sort = {"partnerProfile"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(workFlowService.findAllWorkFlows(dataFlowModel, pageable, FALSE));
    }

    //Used for Add from Multiple flows
    @ApiOperation(value = "Search Workflow with Transactions", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")

    })
    @PreAuthorize(SA_OB_BA)
    @PostMapping(path = "/search-transactions", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<DataFlowMapper>> searchFlows(@Validated @RequestBody DataFlowModel dataFlowModel,
                                                            @PageableDefault(size = 10, page = 0, sort = {"partnerProfile"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(workFlowService.findAllWorkFlows(dataFlowModel, pageable, TRUE));
    }

    @ApiOperation(value = "Search Applied rules", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")

    })
    @PreAuthorize(SA_OB_BA_BU)
    @PostMapping(path = "/search-applied-rules", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityMangerModel<CommunityManagerMapModel<String, ProcessRuleModel>>> findAppliedRules(@RequestBody CommunityMangerModel<String> communityMangerModel) {
        return ResponseEntity.ok(workFlowService.getAppliedRules(communityMangerModel.getContent()));
    }

    @ApiOperation(value = "Delete All workflows by Application PKIDs", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")

    })
    @PreAuthorize(SA_OB_BA)
    @PostMapping(path = "delete-all-by-application-ids")
    public ResponseEntity<CommunityManagerResponse> deleteByApplicationIds(@RequestBody CommunityMangerModel<CommunityManagerNameModel> communityMangerModel) {
        workFlowService.deleteAllWorkflowsByApplicationIds(communityMangerModel);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Workflows deleted successfully."));
    }

    @ApiOperation(value = "Delete All Workflows by Partner PKIDs", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")

    })
    @PreAuthorize(SA_OB_BA)
    @PostMapping(path = "delete-all-by-partner-ids")
    public ResponseEntity<CommunityManagerResponse> deleteByPartnerIds(@RequestBody CommunityMangerModel<CommunityManagerNameModel> communityMangerModel) {
        workFlowService.deleteAllWorkflowsByPartnerIds(communityMangerModel);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Workflows deleted successfully."));
    }

}
