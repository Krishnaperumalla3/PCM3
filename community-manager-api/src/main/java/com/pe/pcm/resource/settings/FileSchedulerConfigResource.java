package com.pe.pcm.resource.settings;

import com.pe.pcm.common.CommunityManagerResponse;
import com.pe.pcm.config.FileSchedulerModel;
import com.pe.pcm.settings.FileSchedulerConfigService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.pe.pcm.constants.AuthoritiesConstants.*;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Kiran Reddy.
 */
@RestController
@RequestMapping(path = "pcm/file/scheduler/config")
@Api(tags = {"FileTransfer Scheduler Config Resource"})
public class FileSchedulerConfigResource {

    private final FileSchedulerConfigService fileSchedulerConfigService;

    @Autowired
    public FileSchedulerConfigResource(FileSchedulerConfigService fileSchedulerConfigService) {
        this.fileSchedulerConfigService = fileSchedulerConfigService;
    }

    @ApiOperation(value = "Create FileTransfer Scheduler Config", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(ONLY_SA)
    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerResponse> create(@RequestBody FileSchedulerModel fileSchedulerModel) {
        fileSchedulerConfigService.save(fileSchedulerModel);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "FileTransfer Scheduler Config created successfully"));
    }

    @ApiOperation(value = "Update FileTransfer Scheduler Config", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(ONLY_SA)
    @PutMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CommunityManagerResponse> update(@RequestBody FileSchedulerModel fileSchedulerModel) {
        fileSchedulerConfigService.save(fileSchedulerModel);
        return ResponseEntity.ok(new CommunityManagerResponse(200, "FileTransfer Scheduler Config updated successfully"));
    }

    @ApiOperation(value = "Get FileTransfer Scheduler Config", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request"),
            @ApiResponse(code = SC_NOT_FOUND, message = "no record found")
    })
    @PreAuthorize(ONLY_SA)
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<FileSchedulerModel> get() {
        return ResponseEntity.ok(fileSchedulerConfigService.get());
    }

}
