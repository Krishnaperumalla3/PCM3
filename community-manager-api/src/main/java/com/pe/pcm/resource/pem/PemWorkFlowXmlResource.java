package com.pe.pcm.resource.pem;

import com.pe.pcm.common.CommunityManagerResponse;
import com.pe.pcm.pem.PemStringDataModel;
import com.pe.pcm.workflow.ManageWorkFlowService;
import com.pe.pcm.workflow.pem.PemContentWorkFlowModel;
import com.pe.pcm.workflow.pem.PemContentWorkFlowXmlModel;
import io.swagger.annotations.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA;
import static com.pe.pcm.utils.PCMConstants.REQUEST_PROCESSED_OK;
import static javax.servlet.http.HttpServletResponse.*;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;


/**
 * @author Shameer.v.
 */
@RestController
@RequestMapping(value = "pem", consumes = APPLICATION_XML_VALUE, produces = APPLICATION_XML_VALUE)
@Api(tags = {"PEM Workflow Resource With XML"})
@PreAuthorize(SA_OB_BA)
public class PemWorkFlowXmlResource {

    private final ManageWorkFlowService manageWorkFlowService;

    @Autowired
    public PemWorkFlowXmlResource(ManageWorkFlowService manageWorkFlowService) {
        this.manageWorkFlowService = manageWorkFlowService;
    }

    @ApiOperation(value = "Create WorkFlow(Base 64)", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PostMapping(path = "workflow/base-64-xml/create", consumes = APPLICATION_XML_VALUE, produces = APPLICATION_XML_VALUE)
    public ResponseEntity<CommunityManagerResponse> createWorkFlowBase64(@RequestBody PemStringDataModel pemStringDataModel) {
        manageWorkFlowService.pemCreateWorkFlowBase64(pemStringDataModel);
        return ResponseEntity.ok(new CommunityManagerResponse(200, REQUEST_PROCESSED_OK));
    }

    @ApiOperation(value = "Add FileTypes(Base 64) WorkFlow", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PostMapping(path = "workflow/base-64-xml/add-file-types", consumes = APPLICATION_XML_VALUE, produces = APPLICATION_XML_VALUE)
    public ResponseEntity<CommunityManagerResponse> pemUpdateWorkflowBase64(@RequestBody PemStringDataModel pemStringDataModel) {
        manageWorkFlowService.addFileTypesBase64(pemStringDataModel);
        return ResponseEntity.ok(new CommunityManagerResponse(200, REQUEST_PROCESSED_OK));
    }

    @ApiOperation(value = "Update WorkFlow(FileTypes-Base 64)", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PutMapping(path = "workflow/base-64-xml/update-file-types", consumes = APPLICATION_XML_VALUE, produces = APPLICATION_XML_VALUE)
    public ResponseEntity<CommunityManagerResponse> updateFileTypesBase64(@RequestBody PemStringDataModel pemStringDataModel) {
        manageWorkFlowService.pemUpdateWorkFlowFileTypesBase64(pemStringDataModel);
        return ResponseEntity.ok(new CommunityManagerResponse(200, REQUEST_PROCESSED_OK));
    }

    @ApiOperation(value = "Remove File Type(Input is Base 64)", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PostMapping(path = "workflow/base-64-xml/remove-fileTypes", consumes = APPLICATION_XML_VALUE, produces = APPLICATION_XML_VALUE)
    public ResponseEntity<CommunityManagerResponse> removeFileTypeOfWorkflowBase64(@RequestBody PemStringDataModel pemStringDataModel) {
        manageWorkFlowService.removeFileTypesBase64(pemStringDataModel);
        return ResponseEntity.ok(new CommunityManagerResponse(200, REQUEST_PROCESSED_OK));
    }

    @ApiOperation(value = "encode workflow model XML to Apache Base 64", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PostMapping(path = "encode-workflow-model-xml-to-apache-base64", consumes = APPLICATION_XML_VALUE, produces = APPLICATION_XML_VALUE)
    public ResponseEntity<PemStringDataModel> encodeWorkflowModelXmlToApacheBase64(@RequestBody PemContentWorkFlowXmlModel pemContentWorkFlowModel) {
        PemContentWorkFlowModel pemContentWorkFlowModel1 = new PemContentWorkFlowModel();
        BeanUtils.copyProperties(pemContentWorkFlowModel, pemContentWorkFlowModel1);
        return ResponseEntity.ok(new PemStringDataModel().setData(Base64.encodeBase64String(pemContentWorkFlowModel1.toJsonString().getBytes())));
    }
}
