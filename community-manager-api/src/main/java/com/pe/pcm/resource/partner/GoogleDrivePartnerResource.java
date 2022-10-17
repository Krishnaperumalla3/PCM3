package com.pe.pcm.resource.partner;

import com.pe.pcm.common.CommunityManagerResponse;
import com.pe.pcm.googledrive.GoogleDriveModel;
import com.pe.pcm.partner.GoogleDrivePartnerService;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA;
import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA_BU;
import static javax.servlet.http.HttpServletResponse.*;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/pcm/partner/google-drive")
@Api(tags = {"Partner Profile With Google-Drive"}, description = "Google Drive Resource")
public class GoogleDrivePartnerResource {

    private final GoogleDrivePartnerService googleDrivePartnerService;

    public GoogleDrivePartnerResource(GoogleDrivePartnerService googleDrivePartnerService) {
        this.googleDrivePartnerService = googleDrivePartnerService;
    }

    @ApiOperation(value = "Create Partner", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PostMapping(consumes = {"multipart/form-data"})
    @PreAuthorize(SA_OB_BA)
    public ResponseEntity<CommunityManagerResponse> create(@Validated @RequestPart("file") MultipartFile file,@RequestPart("googleDriveModel") GoogleDriveModel googleDriveModel) {
        googleDrivePartnerService.create(file,googleDriveModel);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Partner Created Successfully!"));
    }

    @ApiOperation(value = "Update Partner Details ", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PutMapping(consumes = {"multipart/form-data"})
    @PreAuthorize(SA_OB_BA)
    public ResponseEntity<CommunityManagerResponse> update(@Validated @RequestPart("file") MultipartFile file,@RequestPart GoogleDriveModel googleDriveModel){
        googleDrivePartnerService.update(file,googleDriveModel);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "partner updated successfully!"));
    }

    @ApiOperation(value = "Delete Partner", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @DeleteMapping(path = "/{pkId}", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize(SA_OB_BA)
    public ResponseEntity<CommunityManagerResponse> delete(@PathVariable("pkId") String pkId) {
        googleDrivePartnerService.delete(pkId);
        return ResponseEntity.ok(new CommunityManagerResponse(OK.value(), "Partner deleted successfully"));
    }

    @ApiOperation(value = "Get Partner  ", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "no records found")
    })
    @GetMapping(path = "/{pkId}", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize(SA_OB_BA_BU)
    public ResponseEntity<GoogleDriveModel> get(@PathVariable("pkId") String pkId) {
        return ResponseEntity.ok(googleDrivePartnerService.get(pkId));
    }


    @ApiOperation(value = "Create PEM Partner", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PostMapping(path = "/PemPartnerCreate")
    @PreAuthorize(SA_OB_BA)
    public ResponseEntity<CommunityManagerResponse> pemPartnerCreate(@RequestBody GoogleDriveModel googleDriveModel) {
        googleDrivePartnerService.pcmPartneCreate(googleDriveModel);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Partner Created Successfully!"));
    }

    @ApiOperation(value = "Update PEM Partner", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PutMapping(path = "/PemPartnerUpdate")
    @PreAuthorize(SA_OB_BA)
    public ResponseEntity<CommunityManagerResponse> pemPartnerUpdate(@RequestBody GoogleDriveModel googleDriveModel){
        googleDrivePartnerService.pcmPartnerUpdate(googleDriveModel);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "partner updated successfully!"));
    }
}
