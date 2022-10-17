package com.pe.pcm.resource.reports.operator;

import com.pe.pcm.reports.CountResponseModel;
import com.pe.pcm.reports.TransferInfoModel;
import com.pe.pcm.reports.entity.TransferInfoEntity;
import com.pe.pcm.reports.operator.FileOperatorReportsService;
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

import static com.pe.pcm.constants.AuthoritiesConstants.FILE_OPERATOR;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Kiran Reddy.
 */
@RestController
@RequestMapping(path = "pcm/file-operator/reports")
@Api(tags = {"File Operator Reports Resource"})
@PreAuthorize("hasAnyAuthority('" + FILE_OPERATOR + "')")
public class FileOperatorReportsResource {

    private final FileOperatorReportsService fileOperatorReportsService;

    @Autowired
    public FileOperatorReportsResource(FileOperatorReportsService fileOperatorReportsService) {
        this.fileOperatorReportsService = fileOperatorReportsService;
    }

    @ApiOperation(value = "Number of files upload/Download This Hour, This Day, This Week, and This Month", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })

    @GetMapping(path = "activity/{activityType}")
    public ResponseEntity<CountResponseModel> filesActivityReport(@PathVariable("activityType") String activityType) {
        return ResponseEntity.ok(fileOperatorReportsService.filesActivityReport(activityType.toLowerCase()));
    }

    @ApiOperation(value = "File Operator Transfer search", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "search", produces = APPLICATION_JSON_VALUE)
    public Page<TransferInfoEntity> search(@Validated @RequestBody TransferInfoModel transferInfoModel,
                                           @PageableDefault(sort = {"filearrived"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return fileOperatorReportsService.search(transferInfoModel, pageable);
    }

}
