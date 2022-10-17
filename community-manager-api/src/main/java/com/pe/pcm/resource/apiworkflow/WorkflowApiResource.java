package com.pe.pcm.resource.apiworkflow;

import com.pe.pcm.apiworkflow.ManageApiWorkflowService;
import com.pe.pcm.common.CommunityManagerResponse;
import com.pe.pcm.workflow.api.WorkflowAPIUIModel;
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

import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA;
import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA_BU;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Kiran Reddy.
 */
@RestController
@RequestMapping(path = "pcm/api/workflow")
@Api(tags = {"WorkFlow API Resource"})
public class WorkflowApiResource {

    private final ManageApiWorkflowService manageApiWorkflowService;

    @Autowired
    public WorkflowApiResource(ManageApiWorkflowService manageApiWorkflowService) {
        this.manageApiWorkflowService = manageApiWorkflowService;
    }

    @ApiOperation(value = "Create/Update Workflow", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA)
    @PostMapping(path = "create", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerResponse> save(@Validated @RequestBody WorkflowAPIUIModel workflowAPIUIModel) {
        manageApiWorkflowService.save(workflowAPIUIModel);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Workflow created successfully"));
    }

    @ApiOperation(value = "Get Workflow", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @GetMapping
    @PreAuthorize(SA_OB_BA_BU)
    public ResponseEntity<WorkflowAPIUIModel> get(@Validated @RequestParam("profileId") String profileId) {
        return ResponseEntity.ok(manageApiWorkflowService.get(profileId));
    }

    @ApiOperation(value = "Delete Workflow", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @DeleteMapping
    @PreAuthorize(SA_OB_BA)
    public ResponseEntity<CommunityManagerResponse> delete(@Validated @RequestParam("seqId") String seqId) {
        manageApiWorkflowService.delete(seqId.trim());
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Workflow deleted successfully"));
    }

    @ApiOperation(value = "Get Api WorkFlow Activity History", authorizations = {@Authorization(value = "apiKey"),@Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "activity/{processRefId}", produces = APPLICATION_JSON_VALUE)
    public Page<WorkFlowActivityHistoryEntity> getHistory(@Validated @PathVariable("processRefId") String processRefId,
                                                          @PageableDefault(size = 10, page = 0, sort = {"activityDt"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return manageApiWorkflowService.getHistory(processRefId, pageable);
    }

}
