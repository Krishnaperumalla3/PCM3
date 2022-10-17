package com.pe.pcm.resource.reports;


import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.reports.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.pe.pcm.constants.AuthoritiesConstants.ONLY_SA;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@RestController
@RequestMapping(path = "pcm/new/reports")
@Api(tags = {"Reports Resource"})
@PreAuthorize(ONLY_SA)
@ConditionalOnProperty(
        value = "cm.sfg-pcd-reports",
        havingValue = "true")
public class ReportResource {

    private final ReportService reportService;

    @Autowired
    public ReportResource(ReportService reportService) {
        this.reportService = reportService;
    }

    @ApiOperation(value = "Producer Data", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "/producerData")
    public List<FileNameCountModel> getProducerData(@Validated @RequestBody TransferInfoListModel transferInfoListModel) {
        return reportService.getProducerData(transferInfoListModel);
    }

    @ApiOperation(value = "Consumer Data", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "/consumerData")
    public List<FileNameCountModel> getConsumerData(@Validated @RequestBody TransferInfoListModel transferInfoListModel) {
        return reportService.getConsumerData(transferInfoListModel);
    }

    @ApiOperation(value = "UID Data", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "/uidData")
    public List<CommunityManagerNameModel> getUidData(@Validated @RequestBody TransferInfoListModel transferInfoListModel) {
        return reportService.getUidData(transferInfoListModel);
    }

    @ApiOperation(value = "Total Counts", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "/totalCountData")
    public ResponseEntity<ReportTotalCountsModel> getTotalCountData(@Validated @RequestBody TransferInfoListModel transferInfoListModel) {
        return ResponseEntity.ok(reportService.getTotalCountData(transferInfoListModel));
    }

    @ApiOperation(value = "Producer Consumer and FileCount Chart", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "/getProducerConsumerData")
    public List<DataBarChartModel> getProducerConsumerData(@Validated @RequestBody TransferInfoListModel transferInfoListModel) {
        return reportService.getProducerConsumerData(transferInfoListModel);
    }

    @ApiOperation(value = "Date FileCount and FileSize Chart", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "/getDateFileCountFileSizeData")
    public List<DataModel> getDateFileCountFileSizeData(@Validated @RequestBody TransferInfoListModel transferInfoListModel) {
        return reportService.getDateFileCountFileSizeData(transferInfoListModel);
    }

    @ApiOperation(value = "Total Count of External and Internal", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "/external-internal-count-data")
    public ResponseEntity<ExternalInternalTotalCounts> getExtIntTotalCounts(@Validated @RequestBody TransferInfoListModel transferInfoListModel) {
        return ResponseEntity.ok(reportService.getExtIntTotalCounts(transferInfoListModel));
    }

    @ApiOperation(value = "Top File Size Data", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "/top-file-size-data")
    public List<CommunityManagerNameModel> getFileSizeData(@Validated @RequestBody TransferInfoListModel transferInfoListModel) {
        return reportService.getFileSizeData(transferInfoListModel);
    }

    @ApiOperation(value = "Top Chargeback Data", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "/top-chargeback-data")
    public List<CommunityManagerNameModel> getChargebackData(@Validated @RequestBody TransferInfoListModel transferInfoListModel) {
        return reportService.getChargebackData(transferInfoListModel);
    }

    @ApiOperation(value = "APP Data", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "/app-data")
    public List<CommunityManagerNameModel> getAppData(@Validated @RequestBody TransferInfoListModel transferInfoListModel) {
        return reportService.getAppData(transferInfoListModel);
    }

    @ApiOperation(value = "BU Data", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "/bu-data")
    public List<CommunityManagerNameModel> getBuData(@Validated @RequestBody TransferInfoListModel transferInfoListModel) {
        return reportService.getBuData(transferInfoListModel);
    }

    @ApiOperation(value = "Monthly Table Data", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "/monthly-data")
    public List<DataModel> getMonthlyData(@Validated @RequestBody TransferInfoListModel transferInfoListModel) {
        return reportService.getMonthlyData(transferInfoListModel);
    }

    @ApiOperation(value = "Quarterly Table Data", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "/quarterly-data")
    public List<DataModel> getQuarterlyData(@Validated @RequestBody TransferInfoListModel transferInfoListModel) {
        return reportService.getQuarterlyData(transferInfoListModel);
    }

    @ApiOperation(value = "PNODE Data", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "/pnode-data")
    public List<CommunityManagerNameModel> getPNODEData(@Validated @RequestBody TransferInfoListModel transferInfoListModel) {
        return reportService.getPNODEData(transferInfoListModel);
    }

    @ApiOperation(value = "SNODE Data", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "/snode-data")
    public List<CommunityManagerNameModel> getSNODEData(@Validated @RequestBody TransferInfoListModel transferInfoListModel) {
        return reportService.getSNODEData(transferInfoListModel);
    }

    @ApiOperation(value = "Total Producer and Consumer Data", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "/producer-consumer-count-data")
    public ResponseEntity<ProducerConsumerCountsModel> getProducerConsumerTotalCount(@Validated @RequestBody TransferInfoListModel transferInfoListModel) {
        return ResponseEntity.ok(reportService.getProducerConsumerTotalCount(transferInfoListModel));
    }

    @ApiOperation(value = "Source and Destination filename Data", authorizations = {@Authorization(value = "apiKey"), @Authorization(value = "basicAuth")})
    @ApiResponses({
            @ApiResponse(code = SC_UNAUTHORIZED, message = "unauthorized request")
    })
    @PostMapping(path = "/srcfilename-destfilename-count-data")
    public List<DataTableModel> getSrcDestFilenameTotalCount(@Validated @RequestBody TransferInfoListModel transferInfoListModel) {
        return reportService.getSrcDestFilenameTotalCount(transferInfoListModel);
    }

}
