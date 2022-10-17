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

package com.pe.pcm.resource.pem;

import com.pe.pcm.adapter.AdapterNamesService;
import com.pe.pcm.application.ManageApplicationService;
import com.pe.pcm.b2b.B2BUtilityServices;
import com.pe.pcm.b2b.B2bRctSearchModel;
import com.pe.pcm.b2b.routing.channels.RoutingChannelModel;
import com.pe.pcm.common.CommunityManagerKeyValueModel;
import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.common.CommunityManagerResponse;
import com.pe.pcm.common.CommunityMangerModel;
import com.pe.pcm.mail.MailService;
import com.pe.pcm.partner.ManagePartnerService;
import com.pe.pcm.partner.entity.PartnerEntity;
import com.pe.pcm.pem.PemEmailModel;
import com.pe.pcm.pem.PemStringDataModel;
import com.pe.pcm.pem.PemStringMnplModel;
import com.pe.pcm.pem.StringConcatenationModel;
import com.pe.pcm.pem.codelist.PemCodeListModel;
import com.pe.pcm.pem.codelist.PemCodeListService;
import com.pe.pcm.pem.codelist.entity.PemCodeListEntity;
import com.pe.pcm.poolinginterval.PollingIntervalService;
import com.pe.pcm.profile.ProfileModel;
import com.pe.pcm.protocol.RemoteProfileModel;
import com.pe.pcm.reports.DataFlowMapper;
import com.pe.pcm.reports.ReportRepository;
import com.pe.pcm.settings.CorrelationService;
import com.pe.pcm.workflow.ImportModel;
import com.pe.pcm.workflow.ManageWorkFlowService;
import com.pe.pcm.workflow.pem.PemContentFileTypeModel;
import com.pe.pcm.workflow.pem.PemContentWorkFlowModel;
import com.pe.pcm.workflow.pem.PemFileTypeModel;
import com.pe.pcm.workflow.pem.PemImportFlowModel;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;

import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA;
import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA_BU;
import static com.pe.pcm.utils.CommonFunctions.convertPEMString;
import static com.pe.pcm.utils.CommonFunctions.outStreamResponse;
import static com.pe.pcm.utils.PCMConstants.REQUEST_PROCESSED_OK;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static javax.servlet.http.HttpServletResponse.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

/**
 * @author Kiran Reddy.
 */
@RestController
@RequestMapping(value = "pem", produces = APPLICATION_JSON_VALUE)
@Api(tags = {"PEM [ Partner | Application | WorkFlow | FileSearch ] Resource"})
public class PemResource {

    private final ManageWorkFlowService manageWorkFlowService;
    private final ManagePartnerService managePartnerService;
    private final ManageApplicationService manageApplicationService;
    private final ManageWorkFlowService workFlowService;
    private final B2BUtilityServices b2BUtilityServices;
    private final PemCodeListService pemCodeListService;
    private final AdapterNamesService adapterNamesService;
    private final PollingIntervalService pollingIntervalService;
    private final MailService mailService;
    private final CorrelationService correlationService;
    private final ReportRepository reportRepository;


    @Autowired
    public PemResource(ManageWorkFlowService manageWorkFlowService, ManagePartnerService managePartnerService,
                       ManageApplicationService manageApplicationService, ManageWorkFlowService workFlowService,
                       B2BUtilityServices b2BUtilityServices, PemCodeListService pemCodeListService, AdapterNamesService adapterNamesService,
                       PollingIntervalService pollingIntervalService, MailService mailService, CorrelationService correlationService,
                       ReportRepository reportRepository) {
        this.manageWorkFlowService = manageWorkFlowService;
        this.managePartnerService = managePartnerService;
        this.manageApplicationService = manageApplicationService;
        this.workFlowService = workFlowService;
        this.b2BUtilityServices = b2BUtilityServices;
        this.pemCodeListService = pemCodeListService;
        this.adapterNamesService = adapterNamesService;
        this.pollingIntervalService = pollingIntervalService;
        this.mailService = mailService;
        this.correlationService = correlationService;
        this.reportRepository = reportRepository;
    }

    @ApiOperation(value = "Get Pem Code list Profiles ", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "partner/search-code-list", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityMangerModel<PemCodeListEntity>> searchCodeListPartners() {
        return ResponseEntity.ok(new CommunityMangerModel<>(managePartnerService.finaAllPartnersAndCodeList()));
    }

    @ApiOperation(value = "Get Partner Profile Search (Template)", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @PostMapping(path = "partner/search", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityMangerModel<PartnerEntity>> getTemplateByProfileNameAndProfileId(@Validated @RequestBody PemFileTypeModel pemCopyWorkFlowModel) {
        return ResponseEntity.ok(new CommunityMangerModel<>(managePartnerService.getPartnerByPartnerNameAndPartnerId(pemCopyWorkFlowModel)));
    }

    @ApiOperation(value = "Get Partners Names List (Template)", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "partner/getnameslist", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CommunityManagerNameModel>> partnersList() {
        return ResponseEntity.ok(managePartnerService.getAllTemplateProfilesList());
    }

    @ApiOperation(value = "Get Application Names List (Template)", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "application/getnameslist", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CommunityManagerNameModel>> applicationsList() {
        return ResponseEntity.ok(manageApplicationService.getAllTemplateApplicationList());
    }

    @ApiOperation(value = "Get File Types with status ", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @PostMapping(path = "reports/search-by-pattern", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityMangerModel<PemFileTypeModel>> searchByProcessId(@Validated @RequestBody PemFileTypeModel pemCopyWorkFlowModel) {
        return ResponseEntity.ok(new CommunityMangerModel<>(manageWorkFlowService.pemTemplateFileTypesList(pemCopyWorkFlowModel, TRUE, TRUE)));
    }

    @ApiOperation(value = "Export WorkFlow with Profile", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "workflow/export-with-profile", produces = APPLICATION_JSON_VALUE)
    public void exportWithProfile(@RequestParam("partnerName") String partnerName,
                                  @RequestParam("applicationName") String applicationName, HttpServletResponse httpServletResponse) {
        String dataToStream = workFlowService.exportWorkFlow(partnerName, applicationName, true, true);
        outStreamResponse(httpServletResponse, dataToStream);
    }

    @ApiOperation(value = "Export only WorkFlow", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "workflow/export-enc", produces = APPLICATION_JSON_VALUE)
    public void export(@Validated @RequestParam("partnerPkId") String profileId,
                       @RequestParam("applicationPkId") String applicationId, HttpServletResponse httpServletResponse) {
        String dataToStream = workFlowService.exportEncryptedWorkFlow(profileId, applicationId, false, false);
        outStreamResponse(httpServletResponse, dataToStream);
    }

    @ApiOperation(value = "Import WorkFlow with Profile", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA)
    @PostMapping(path = "workflow/import-with-profile")
    public ResponseEntity<CommunityManagerResponse> importWithProfile(@Validated @RequestBody ImportModel importModel) {
        workFlowService.importWorkFlowWithProfile(null, importModel.getContent(), true);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Import workflow completed successfully"));
    }

    @ApiOperation(value = "Update Partner Profile status", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "partner/status")
    @PreAuthorize(SA_OB_BA)
    public ResponseEntity<CommunityManagerResponse> partnerStatusChange(@RequestParam("partnerName") String partnerName,
                                                                        @RequestParam("status") Boolean status) {
        managePartnerService.statusChange(partnerName, status, true);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Partner status updated successfully"));
    }

    @ApiOperation(value = "Update Application Profile status", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA)
    @PostMapping(path = "application/status")
    public ResponseEntity<CommunityManagerResponse> applicationStatusChange(@RequestParam("applicationName") String applicationName,
                                                                            @RequestParam("status") Boolean status) {
        manageApplicationService.statusChange(applicationName, status, true);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Application status updated successfully"));
    }

    @ApiOperation(value = "Delete Partner Profile", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "no record found")
    })
    @PreAuthorize(SA_OB_BA)
    @DeleteMapping(path = "partner/{partnerName}")
    public ResponseEntity<CommunityManagerResponse> deletePartner(@PathVariable String partnerName) {
        managePartnerService.delete(partnerName, true, true, false);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Partner deleted successfully"));
    }

    @ApiOperation(value = "Delete Application Profile", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "no record found")
    })
    @PreAuthorize(SA_OB_BA)
    @DeleteMapping(path = "application/{applicationName}")
    public ResponseEntity<CommunityManagerResponse> deleteApplication(@PathVariable String applicationName) {
        manageApplicationService.delete(applicationName, true, true);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Application deleted successfully"));
    }

    @ApiOperation(value = "Update Routing Rule", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "no record found")
    })
    @PreAuthorize(SA_OB_BA)
    @PutMapping(path = "routing-rules")
    public ResponseEntity<CommunityManagerResponse> updateRoutingRule(@Validated @RequestParam("name") String name,
                                                                      @RequestParam("mailBox") String mailBox) {
        b2BUtilityServices.updateRoutingRule(name, mailBox);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Routing-rule updated successfully"));
    }

    @ApiOperation(value = "Create Code List", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA)
    @PostMapping(path = "code-list-profile")
    public ResponseEntity<CommunityManagerResponse> createCodeList(@Validated @RequestBody PemCodeListModel pemCodeListModel) {
        pemCodeListService.create(pemCodeListModel);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Code-list profile created successfully"));
    }

    @ApiOperation(value = "Get Code List", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "code-list/{pkId}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PemCodeListModel> getCodeList(@PathVariable("pkId") String pkId) {
        return ResponseEntity.ok(pemCodeListService.get(pkId));
    }

    @ApiOperation(value = "Delete Code list Profile", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "no record found")
    })
    @PreAuthorize(SA_OB_BA)
    @DeleteMapping(path = "code-list/{pkId}")
    public ResponseEntity<CommunityManagerResponse> deleteCodeList(@PathVariable String pkId) {
        pemCodeListService.delete(pkId);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "CodeList deleted successfully"));
    }

    @ApiOperation(value = "Get PEM Adapter Names", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "no record found")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "pem-adapter-names")
    public ResponseEntity<List<CommunityManagerKeyValueModel>> getAdapterNames() {
        return ResponseEntity.ok(adapterNamesService.getAdapterNamesForPem());
    }

    @ApiOperation(value = "Get PoolingIntervals", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "poolingIntervals not found")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "pooling/{pInterval}")
    public ResponseEntity<List<CommunityManagerKeyValueModel>> getPoolingInterval(@PathVariable String pInterval) {
        return ResponseEntity.ok(pollingIntervalService.getPoolingInterval(pInterval));
    }

    @ApiOperation(value = "Get Work Flow (Profiles + Flows + Rules) along with updated Flows", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PreAuthorize(SA_OB_BA)
    @PostMapping(path = "workflow/edit-flows", produces = {APPLICATION_JSON_VALUE,APPLICATION_XML_VALUE},consumes = {APPLICATION_JSON_VALUE,APPLICATION_XML_VALUE})
    public ResponseEntity<CommunityMangerModel<DataFlowMapper>> editAndProduceRule(@RequestBody PemContentFileTypeModel pemContentFileTypeModel) {
        return ResponseEntity.ok(new CommunityMangerModel<>(manageWorkFlowService.editAndProduceRule(pemContentFileTypeModel)));
    }

    @ApiOperation(value = "Create WorkFlow", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PreAuthorize(SA_OB_BA)
    @PostMapping(path = "workflow/create", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerResponse> createWorkFlow(@RequestBody PemContentWorkFlowModel pemContentWorkFlowModel) {
        manageWorkFlowService.pemCreateWorkFlow(pemContentWorkFlowModel);
        return ResponseEntity.ok(new CommunityManagerResponse(200, REQUEST_PROCESSED_OK));
    }

    @ApiOperation(value = "Create WorkFlow(Base 64)", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PreAuthorize(SA_OB_BA)
    @PostMapping(path = "workflow/base-64/create", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerResponse> createWorkFlowBase64(@RequestBody PemStringDataModel pemStringDataModel) {
        manageWorkFlowService.pemCreateWorkFlowBase64(pemStringDataModel);
        return ResponseEntity.ok(new CommunityManagerResponse(200, REQUEST_PROCESSED_OK));
    }

    @ApiOperation(value = "Add FileTypes WorkFlow", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PreAuthorize(SA_OB_BA)
    @PostMapping(path = "workflow/add-file-types", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerResponse> pemUpdateWorkflow(@RequestBody PemContentWorkFlowModel pemContentWorkFlowModel) {
        manageWorkFlowService.addFileTypes(pemContentWorkFlowModel);
        return ResponseEntity.ok(new CommunityManagerResponse(200, REQUEST_PROCESSED_OK));
    }

    @ApiOperation(value = "Add FileTypes(Base 64) WorkFlow", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PreAuthorize(SA_OB_BA)
    @PostMapping(path = "workflow/base-64/add-file-types", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerResponse> pemUpdateWorkflowBase64(@RequestBody PemStringDataModel pemStringDataModel) {
        manageWorkFlowService.addFileTypesBase64(pemStringDataModel);
        return ResponseEntity.ok(new CommunityManagerResponse(200, REQUEST_PROCESSED_OK));
    }

    @ApiOperation(value = "Remove File Type", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PreAuthorize(SA_OB_BA)
    @PostMapping(path = "workflow/remove-fileTypes", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerResponse> removeFileTypeOfWorkflow(@RequestBody PemContentWorkFlowModel pemContentWorkFlowModel) {
        manageWorkFlowService.removeFileTypes(pemContentWorkFlowModel);
        return ResponseEntity.ok(new CommunityManagerResponse(200, REQUEST_PROCESSED_OK));
    }

    @ApiOperation(value = "Remove File Type(Base 64)", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PreAuthorize(SA_OB_BA)
    @PostMapping(path = "workflow/base-64/remove-fileTypes", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerResponse> removeFileTypeOfWorkflowBase64(@RequestBody PemStringDataModel pemStringDataModel) {
        manageWorkFlowService.removeFileTypesBase64(pemStringDataModel);
        return ResponseEntity.ok(new CommunityManagerResponse(200, REQUEST_PROCESSED_OK));
    }

    @ApiOperation(value = "Update WorkFlow(FileTypes)", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PreAuthorize(SA_OB_BA)
    @PutMapping(path = "workflow/update-file-types", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerResponse> updateFileTypes(@RequestBody PemContentWorkFlowModel pemContentWorkFlowModel) {
        manageWorkFlowService.pemUpdateWorkFlowFileTypes(pemContentWorkFlowModel);
        return ResponseEntity.ok(new CommunityManagerResponse(200, REQUEST_PROCESSED_OK));
    }

    @ApiOperation(value = "Update WorkFlow(FileTypes-Base 64)", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PreAuthorize(SA_OB_BA)
    @PutMapping(path = "workflow/base-64/update-file-types", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerResponse> updateFileTypesBase64(@RequestBody PemStringDataModel pemStringDataModel) {
        manageWorkFlowService.pemUpdateWorkFlowFileTypesBase64(pemStringDataModel);
        return ResponseEntity.ok(new CommunityManagerResponse(200, REQUEST_PROCESSED_OK));
    }

    @ApiOperation(value = "Get Template Flow Types", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist")

    })
    @PreAuthorize(SA_OB_BA_BU)
    @PostMapping(path = "workflow/flows-list", produces = {APPLICATION_JSON_VALUE,APPLICATION_XML_VALUE},consumes = {APPLICATION_JSON_VALUE,APPLICATION_XML_VALUE})
    public ResponseEntity<CommunityMangerModel<PemFileTypeModel>> getTemplateFileTypeList(@Validated @RequestBody PemFileTypeModel pemFileTypeModel, @RequestParam("isPartnerID") Boolean isPartnerID) {
        return ResponseEntity.ok(new CommunityMangerModel<>(manageWorkFlowService.pemTemplateFileTypesList(pemFileTypeModel, FALSE, isPartnerID)));
    }

    @ApiOperation(value = "Get calculator", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @PostMapping(path = "mathematicalCalculator", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerKeyValueModel> getExpressionList(@RequestBody LinkedHashMap<String, String> expressionList) {
        return ResponseEntity.ok((manageWorkFlowService.calculator(expressionList)));
    }

    @ApiOperation(value = "Reflect Data as it is ", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist")
    })
    @PreAuthorize(SA_OB_BA)
    @PostMapping(path = "dataStorageForPem", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<LinkedHashMap<String, String>> dataStorageForPem(@RequestBody LinkedHashMap<String, String> dataStorageMap) {
        return ResponseEntity.ok((dataStorageMap));
    }

    @ApiOperation(value = "Edit WorkFlows", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist")
    })
    @PreAuthorize(SA_OB_BA)
    @PostMapping(path = "workflow/edit-flows2", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityMangerModel<DataFlowMapper>> editAndProduceRule(@RequestBody List<DataFlowMapper> dataFlowMapperList) {
        return ResponseEntity.ok(new CommunityMangerModel<>(dataFlowMapperList));
    }

    @ApiOperation(value = "Send mail", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @PostMapping(path = "send-email", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerResponse> hubDetailsEmail(@RequestBody PemEmailModel pemEmailModel) {
        mailService.sendPemMail(pemEmailModel);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Mail sent successfully."));
    }

    @ApiOperation(value = "import Workflow (Input Data as String)", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist")
    })
    @PreAuthorize(SA_OB_BA)
    @PostMapping(path = "import-as-string", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerResponse> importWorkFlow1(@Validated @RequestBody PemImportFlowModel pemImportFlowModel) {
        workFlowService.importWorkFlow(null, pemImportFlowModel.getPartnerName(),
                pemImportFlowModel.getApplicationName(), true, pemImportFlowModel.getData());
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Import workflow completed successfully."));
    }

    @ApiOperation(value = "List to String", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist")
    })
    @PreAuthorize(SA_OB_BA)
    @PostMapping(path = "string-concatenation", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerKeyValueModel> stringConcatenation(@RequestBody StringConcatenationModel stringConcatenationModel) {
        return ResponseEntity.ok(correlationService.append(stringConcatenationModel));
    }

    @ApiOperation(value = "Find String Length", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist")
    })
    @PreAuthorize(SA_OB_BA)
    @PostMapping(path = "string-length", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerKeyValueModel> stringLength(@RequestBody CommunityManagerKeyValueModel communityManagerKeyValueModel) {
        return ResponseEntity.ok(new CommunityManagerKeyValueModel().setKey("output").setValue(String.valueOf(communityManagerKeyValueModel.getValue().length())));
    }

    @ApiOperation(value = "Convert String to LOWER/UPPER", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist")
    })
    @PreAuthorize(SA_OB_BA)
    @PostMapping(path = "string-lower-upper", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<PemStringMnplModel> spaceRemove(@RequestBody PemStringMnplModel pemStringMnplModel) {

        return ResponseEntity.ok(new PemStringMnplModel(convertPEMString.apply(pemStringMnplModel)));
    }

    @ApiOperation(value = "Get Partner Contact Details", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @PostMapping(path = "profiles/search-all-contacts", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityMangerModel<ProfileModel>> searchProfiles(@RequestBody ProfileModel profileModel, Boolean isOr, String fileTpServer) {
        return ResponseEntity.ok(new CommunityMangerModel<>(managePartnerService.getProfiles(profileModel, isOr, fileTpServer)));
    }

    @ApiOperation(value = "Get Partner Contact Details in XML", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @PostMapping(path = "profiles/search-all-contacts/xml", consumes = APPLICATION_XML_VALUE, produces = APPLICATION_XML_VALUE)
    public ResponseEntity<CommunityMangerModel<ProfileModel>> searchProfilesXmlResp(@RequestBody ProfileModel profileModel, Boolean isOr, String fileTpServer) {
        return ResponseEntity.ok(new CommunityMangerModel<>(managePartnerService.getProfiles(profileModel, isOr, fileTpServer)));
    }

    @ApiOperation(value = "Get Application Contact Details in XML", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @PostMapping(path = "app-profiles/search-all-contacts/xml", consumes = APPLICATION_XML_VALUE, produces = APPLICATION_XML_VALUE)
    public ResponseEntity<CommunityMangerModel<ProfileModel>> searchAppProfilesXmlResp(@RequestBody ProfileModel profileModel, Boolean isOr, String fileAppServer) {
        return ResponseEntity.ok(new CommunityMangerModel<>(manageApplicationService.getProfiles(profileModel, isOr, fileAppServer)));
    }

    @ApiOperation(value = "Generate Unique Key", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA)
    @GetMapping(path = "generate-unique-key")
    public ResponseEntity<CommunityManagerResponse> generateUniqueKey() {
        return ResponseEntity.ok(new CommunityManagerResponse(200, "PEM" + reportRepository.generateUniqueKey()));
    }

    @ApiOperation(value = "Get Code List", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "routing-channels/{templateName}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityMangerModel<RoutingChannelModel>> getRoutingChannelsByTemplateName(@PathVariable("templateName") String templateName) {
        return ResponseEntity.ok(new CommunityMangerModel<>(b2BUtilityServices.findRoutingChannelsByTemplateName(templateName)));
    }

    @ApiOperation(value = "Get Code List", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @PostMapping(path = "routing-channels", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityMangerModel<RoutingChannelModel>> getRoutingChannels(@RequestBody B2bRctSearchModel b2bRctSearchModel) {
        return ResponseEntity.ok(new CommunityMangerModel<>(b2BUtilityServices.findRoutingChannels(b2bRctSearchModel)));
    }

    @ApiOperation(value = "Get All Profiles Based on IsHubInfo and PrfAuthType", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @PostMapping(path = "profiles/search-remote-profiles-by-auth-hub", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityMangerModel<RemoteProfileModel>> searchRemoteProfilesByAuthTypeAndHubInfo(@RequestParam("isHubInfo") Boolean isHubInfo, @RequestParam("prfAuthType") String prfAuthType) {
        return ResponseEntity.ok(new CommunityMangerModel<>(managePartnerService.getRemoteProfiles(isHubInfo, prfAuthType)));
    }

    @ApiOperation(value = "Get All Profiles Based on IsHubInfo and PrfAuthType Xml", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @PostMapping(path = "profiles/search-remote-profiles-by-auth-hub-xml", produces = APPLICATION_XML_VALUE)
    public ResponseEntity<CommunityMangerModel<RemoteProfileModel>> searchRemoteProfilesByAuthTypeAndHubInfoWithXml(@RequestParam("isHubInfo") Boolean isHubInfo, @RequestParam("prfAuthType") String prfAuthType) {
        return ResponseEntity.ok(new CommunityMangerModel<>(managePartnerService.getRemoteProfiles(isHubInfo, prfAuthType)));
    }

    @ApiOperation(value = "Get PartnerGroup Details", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU)
    @GetMapping(path = "partner-groups", produces = APPLICATION_XML_VALUE)
    public ResponseEntity<String> getPartnerGroupDetails() {
        return ResponseEntity.ok(b2BUtilityServices.getPartnerGroupDetails());
    }

}
