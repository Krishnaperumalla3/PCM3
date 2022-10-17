package com.pe.pcm.resource.application;

import com.pe.pcm.application.GoogleDriveApplicationService;
import com.pe.pcm.common.CommunityManagerResponse;
import com.pe.pcm.googledrive.GoogleDriveModel;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA;
import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA_BU;
import static javax.servlet.http.HttpServletResponse.*;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(path = "/pcm/application/google-drive")
@Api(tags = {"Application Profile with Google-Drive"}, description = "Google Drive Application Resource")
public class GoogleDriveApplicationResource {
    private final GoogleDriveApplicationService googleDriveApplicationService;

    @Autowired
    public GoogleDriveApplicationResource(GoogleDriveApplicationService googleDriveApplicationService) {
        this.googleDriveApplicationService = googleDriveApplicationService;
    }

    @ApiOperation(value = "Create Google-Drive Application Profile", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PostMapping
    @PreAuthorize(SA_OB_BA)
    public ResponseEntity<CommunityManagerResponse> create(@RequestPart("file") MultipartFile file, @RequestPart("googleDriveModel") GoogleDriveModel googleDriveModel) {
        googleDriveApplicationService.create(file, googleDriveModel);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Application Created Successfully!"));
    }

    @ApiOperation(value = "Update Google-Drive Application Profile ", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PutMapping
    @PreAuthorize(SA_OB_BA)
    public ResponseEntity<CommunityManagerResponse> update(@RequestPart("file") MultipartFile file, @RequestPart("googleDriveModel") GoogleDriveModel googleDriveModel) {
        googleDriveApplicationService.update(file, googleDriveModel);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Application Updated Successfully!"));
    }

    @ApiOperation(value = "Delete Google-Drive Application Profile", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @DeleteMapping(path = "/{pkId}", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize(SA_OB_BA)
    public ResponseEntity<CommunityManagerResponse> delete(@PathVariable("pkId") String pkId) {
        googleDriveApplicationService.delete(pkId);
        return ResponseEntity.ok(new CommunityManagerResponse(OK.value(), "Application deleted successfully"));
    }

    @ApiOperation(value = "Get Google-Drive Application Profile  ", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "no records found")
    })
    @GetMapping(path = "/{pkId}", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize(SA_OB_BA_BU)
    public ResponseEntity<GoogleDriveModel> get(@PathVariable("pkId") String pkId) {
        return ResponseEntity.ok(googleDriveApplicationService.get(pkId));
    }

    @ApiOperation(value = "Create Google-Drive PEM Application Profile", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PostMapping(path = "/PemApplicationCreate")
    @PreAuthorize(SA_OB_BA)
    public ResponseEntity<CommunityManagerResponse> PemApplicationCreate(@RequestBody GoogleDriveModel googleDriveModel) {
        googleDriveApplicationService.pemApplicationCreate( googleDriveModel);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Application Created Successfully!"));
    }

    @ApiOperation(value = "Update Google-Drive PEM Application Profile ", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_CONFLICT, message = "resource already exist"),
            @ApiResponse(code = SC_NOT_IMPLEMENTED, message = "protocol not implemented")
    })
    @PutMapping(path = "/PemApplicationUpdate")
    @PreAuthorize(SA_OB_BA)
    public ResponseEntity<CommunityManagerResponse> PemApplicationUpdate(@RequestBody GoogleDriveModel googleDriveModel) {
        googleDriveApplicationService.PemApplicationUpdate(googleDriveModel);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "Application Updated Successfully!"));
    }
}
