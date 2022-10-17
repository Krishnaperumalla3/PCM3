package com.pe.pcm.resource.reports;

import com.pe.pcm.profile.ProfileModel;
import com.pe.pcm.reports.BasicReportRequestModel;
import com.pe.pcm.reports.BasicReportsService;
import com.pe.pcm.reports.CountModel;
import com.pe.pcm.reports.NameCountModel;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.pe.pcm.constants.AuthoritiesConstants.SA_OB_BA_BU_DP_DPR;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

/**
 * @author Kiran Reddy.
 */
@RestController
@RequestMapping(path = "pcm/reports")
@Api(tags = {"File Transfer Basic Reports Resource"})
public class FileTransferReportsResource {

    private final BasicReportsService basicReportsService;

    @Autowired
    public FileTransferReportsResource(BasicReportsService basicReportsService) {
        this.basicReportsService = basicReportsService;
    }

    @ApiOperation(value = "Number of files processed by Month for last 36 months", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU_DP_DPR)
    @PostMapping(path = "/files-processed/by-month/last-36-months")
    public ResponseEntity<List<NameCountModel>> fileProcessByMonthLast36Months(@RequestBody BasicReportRequestModel basicReportRequestModel) {
        return ResponseEntity.ok(basicReportsService.fileProcessByMonthLast36Months(basicReportRequestModel));
    }

    @ApiOperation(value = "Number of files processed by Day for last 12 months", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU_DP_DPR)
    @PostMapping(path = "/files-processed/by-day/last-12-months")
    public ResponseEntity<List<NameCountModel>> fileProcessByDayLast12Months(@RequestBody BasicReportRequestModel basicReportRequestModel) {
        return ResponseEntity.ok(basicReportsService.fileProcessByDayLast12Months(basicReportRequestModel));
    }

    @ApiOperation(value = "Number of files processed by Hour for last 30 Days", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU_DP_DPR)
    @PostMapping(path = "/files-processed/by-hour/last-30-days")
    public ResponseEntity<List<NameCountModel>> fileProcessByHourLast30Days(@RequestBody BasicReportRequestModel basicReportRequestModel) {
        return ResponseEntity.ok(basicReportsService.fileProcessByHourLast30Days(basicReportRequestModel));
    }

    @ApiOperation(value = "Number of files processed by Day in a week, Average of last 4 weeks by day", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU_DP_DPR)
    @PostMapping(path = "/files-processed/by-day-week/avg-last-4-weeks-by-day")
    public ResponseEntity<List<NameCountModel>> fileProcessByDayInWeekAvgLast4Weeks(@RequestBody BasicReportRequestModel basicReportRequestModel) {
        return ResponseEntity.ok(basicReportsService.fileProcessByDayInWeekAvgLast4Weeks(basicReportRequestModel));
    }


    @ApiOperation(value = "Number of files processed by Month for last 36 months", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU_DP_DPR)
    @PostMapping(path = "/files-size-processed/by-month/last-36-months")
    public ResponseEntity<List<NameCountModel>> fileSizeProcessByMonthLast36Months(@RequestBody BasicReportRequestModel basicReportRequestModel) {
        return ResponseEntity.ok(basicReportsService.fileSizeProcessByMonthLast36Months(basicReportRequestModel));
    }

    @ApiOperation(value = "Number of files processed by Day for last 12 months", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU_DP_DPR)
    @PostMapping(path = "/files-size-processed/by-day/last-12-months")
    public ResponseEntity<List<NameCountModel>> fileSizeProcessByDayLast12Months(@RequestBody BasicReportRequestModel basicReportRequestModel) {
        return ResponseEntity.ok(basicReportsService.fileSizeProcessByDayLast12Months(basicReportRequestModel));
    }

    @ApiOperation(value = "Number of files processed by Hour for last 30 Days", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU_DP_DPR)
    @PostMapping(path = "/files-size-processed/by-hour/last-30-days")
    public ResponseEntity<List<NameCountModel>> fileSizeProcessByHourLast30Days(@RequestBody BasicReportRequestModel basicReportRequestModel) {
        return ResponseEntity.ok(basicReportsService.fileSizeProcessByHourLast30Days(basicReportRequestModel));
    }


    @ApiOperation(value = "Identify the Peak hour of file process for last 31 days", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU_DP_DPR)
    @PostMapping(path = "/files-processed/peak-hours/last-31-days")
    public ResponseEntity<List<NameCountModel>> fileProcessPeakHoursLast31Days(@RequestBody BasicReportRequestModel basicReportRequestModel) {
        return ResponseEntity.ok(basicReportsService.fileProcessPeakHoursLast31Days(basicReportRequestModel));
    }

    @ApiOperation(value = "Inactive Partners without file transfers for One Year", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU_DP_DPR)
    @GetMapping(path = "inactive-partners/last-n-days")
    public ResponseEntity<List<ProfileModel>> inactivePartnersLastOneYear(Integer days) {
        return ResponseEntity.ok(basicReportsService.inactivePartnersLastOneYear(days));
    }

    @ApiOperation(value = "Number of files processed this Hour", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU_DP_DPR)
    @PostMapping(path = "files-processed/this-hour")
    public ResponseEntity<CountModel> filesProcessedThisHour(@RequestBody BasicReportRequestModel basicReportRequestModel) {
        return ResponseEntity.ok(new CountModel(basicReportsService.filesProcessedThisHour(basicReportRequestModel)));
    }

    @ApiOperation(value = "Number of files processed Today", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU_DP_DPR)
    @PostMapping(path = "files-processed/this-day")
    public ResponseEntity<CountModel> filesProcessedThisDay(@RequestBody BasicReportRequestModel basicReportRequestModel) {
        return ResponseEntity.ok(new CountModel(basicReportsService.filesProcessedThisDay(basicReportRequestModel)));
    }

    @ApiOperation(value = "Number of files processed this Week", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU_DP_DPR)
    @PostMapping(path = "files-processed/this-week")
    public ResponseEntity<CountModel> filesProcessedThisWeek(@RequestBody BasicReportRequestModel basicReportRequestModel) {
        return ResponseEntity.ok(new CountModel(basicReportsService.filesProcessedThisWeek(basicReportRequestModel)));
    }

    @ApiOperation(value = "Number of files processed this Month", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PreAuthorize(SA_OB_BA_BU_DP_DPR)
    @PostMapping(path = "files-processed/this-month")
    public ResponseEntity<CountModel> filesProcessedThisMonth(@RequestBody BasicReportRequestModel basicReportRequestModel) {
        return ResponseEntity.ok(new CountModel(basicReportsService.filesProcessedThisMonth(basicReportRequestModel)));
    }

}
