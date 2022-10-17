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

package com.pe.pcm.reports;

import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.reports.entity.TransferInfoEntity;
import com.pe.pcm.utils.ReportsCommonFunctions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.pe.pcm.utils.PCMConstants.SQL_SERVER;
import static com.pe.pcm.utils.ReportsCommonFunctions.addRequiredFiltersFor997Report;
import static com.pe.pcm.utils.ReportsCommonFunctions.getExportDataFlowQuery;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * @author Kiran Reddy.
 */
@Repository
public class SqlServerReportRepository implements ReportRepository {

    private static final String TRANSFER_INFO_AND_D_QUERY = "SELECT * " +
            "FROM ((SELECT * FROM PETPE_TRANSFERINFO FILTER)" +
            "      UNION ALL" +
            "      (SELECT * FROM PETPE_TRANSFERINFO_STAGING FILTER)) A " +
            "ORDER BY SORTING_DETAILS " +
            "OFFSET ? ROWS FETCH FIRST ? ROWS ONLY";
    private final JdbcTemplateComponent jdbcTemplateComponent;

    public SqlServerReportRepository(JdbcTemplateComponent jdbcTemplateComponent) {
        this.jdbcTemplateComponent = jdbcTemplateComponent;
    }

    public Page<OverDueMapper> findAll997OverDues(OverDueReportModel overDueReportModel, Pageable pageable, List<String> partnerNameList) {
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM (SELECT CS.VALUE ,PE.FILEARRIVED, PE.DESTFILENAME, PE.PARTNER, PE.DOCTRANS, PE.SENDERID, PE.RECIVERID, CS.WF_ID, TRY_CONVERT(DATETIME,PE.FILEARRIVED) - TRY_CONVERT(DATETIME,SYSDATETIME()) AS OVERDUETIME FROM CORRELATION_SET CS INNER JOIN PETPE_TRANSFERINFO PE ON CS.WF_ID = PE.COREBPID WHERE CS.NAME = 'GroupAckStatus' AND PE.STATUS = 'Success' ) A INNER JOIN ( SELECT CORRELATION_SET.WF_ID, CORRELATION_SET.VALUE GROUPCOUNT FROM CORRELATION_SET WHERE NAME = 'GroupCount') B ON A.WF_ID = B.WF_ID");
        StringBuilder rowCountQueryBuilder = new StringBuilder("SELECT COUNT(*) FROM (SELECT CS.VALUE, PE.FILEARRIVED , PE.DESTFILENAME, PE.PARTNER, PE.DOCTRANS, PE.SENDERID, PE.RECIVERID, CS.WF_ID FROM CORRELATION_SET CS INNER JOIN PETPE_TRANSFERINFO PE ON CS.WF_ID = PE.COREBPID WHERE CS.NAME = 'GroupAckStatus' AND PE.STATUS = 'Success' ) A INNER JOIN (SELECT CORRELATION_SET.WF_ID, CORRELATION_SET.VALUE GROUPCOUNT FROM CORRELATION_SET WHERE NAME='GroupCount') B ON A.WF_ID = B.WF_ID");

        List<String> queryParamValues = new ArrayList<>();

        addRequiredFiltersFor997Report(queryBuilder, overDueReportModel, partnerNameList, false, queryParamValues, SQL_SERVER);
        addRequiredFiltersFor997Report(rowCountQueryBuilder, overDueReportModel, partnerNameList, true, queryParamValues, SQL_SERVER);

        String[] sort = pageable.getSort().toString().split(":");
        queryBuilder.append(" ORDER BY ").append(ReportsCommonFunctions.getDbColumnName(sort[0].trim())).append(sort[1]);
        queryBuilder.append(" OFFSET ? ").append(" ROWS FETCH FIRST ? ").append(" ROWS ONLY");
        return new PageImpl<>(jdbcTemplateComponent.getOverDueReports(queryBuilder.toString(), overDueReportModel, pageable, SQL_SERVER, partnerNameList), pageable, jdbcTemplateComponent.getCount(rowCountQueryBuilder.toString(), queryParamValues));
    }

    public Page<DataFlowMapper> findAllWorkFlows(DataFlowModel dataFlowModel, Pageable pageable, String environment, Boolean isOnlyFlows, List<String> partnerPkIdList) {
        return jdbcTemplateComponent.findAllWorkFlows(dataFlowModel, pageable, SQL_SERVER, environment, isOnlyFlows, partnerPkIdList);
    }

    public List<DataFlowMapper> exportAllWorkFlows(List<String> partnerPkIdList) {
        return jdbcTemplateComponent.getWorkFlowsReport(getExportDataFlowQuery(SQL_SERVER, TRUE, FALSE, partnerPkIdList).append(") A ").toString(), new DataFlowModel(), partnerPkIdList, null, SQL_SERVER, null, FALSE);
    }

    public String purgeTransactions(Long days) {
        return jdbcTemplateComponent.purgeTransactions(SQL_SERVER, days);
    }

    public String generateUniqueKey() {
        return jdbcTemplateComponent.generateUniqueKey(SQL_SERVER);
    }

    // EFX Reports

    public List<FileNameCountModel> getProducerData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getProducerData(transferInfoListModel, SQL_SERVER);
    }

    public List<FileNameCountModel> getConsumerData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getConsumerData(transferInfoListModel, SQL_SERVER);
    }

    public List<CommunityManagerNameModel> getUidData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getUidData(transferInfoListModel, SQL_SERVER);
    }

    public ReportTotalCountsModel getTotalCountData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getTotalCountData(transferInfoListModel, SQL_SERVER);
    }

    public List<DataBarChartModel> getProducerConsumerData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getProducerConsumerData(transferInfoListModel, SQL_SERVER);
    }

    public List<DataModel> getDateFileCountFileSizeData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getDateFileCountFileSizeData(transferInfoListModel, SQL_SERVER);
    }

    public ExternalInternalTotalCounts getExtIntTotalCounts(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getExtIntTotalCounts(transferInfoListModel, SQL_SERVER);
    }

    public List<CommunityManagerNameModel> getFileSizeData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getFileSizeData(transferInfoListModel, SQL_SERVER);
    }

    public List<CommunityManagerNameModel> getChargebackData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getChargebackData(transferInfoListModel, SQL_SERVER);
    }

    public List<CommunityManagerNameModel> getAppData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getAppData(transferInfoListModel, SQL_SERVER);
    }

    public List<CommunityManagerNameModel> getBuData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getBuData(transferInfoListModel, SQL_SERVER);
    }

    public List<DataModel> getMonthlyData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getMonthlyData(transferInfoListModel, SQL_SERVER);
    }

    public List<DataModel> getQuarterlyData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getQuarterlyData(transferInfoListModel, SQL_SERVER);
    }

    public List<CommunityManagerNameModel> getPNODEData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getPNODEData(transferInfoListModel, SQL_SERVER);
    }

    public List<CommunityManagerNameModel> getSNODEData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getSNODEData(transferInfoListModel, SQL_SERVER);
    }

    public ProducerConsumerCountsModel getProducerConsumerTotalCount(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getProducerConsumerTotalCount(transferInfoListModel, SQL_SERVER);
    }

    public List<DataTableModel> getSrcDestFilenameTotalCount(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getSrcDestFilenameTotalCount(transferInfoListModel, SQL_SERVER);
    }

    public Map<String, Long> queryForGroupBy(String query, String groupByColumnName, String startDate, String endDate, List<String> partnerNames) {
        return jdbcTemplateComponent.queryForGroupBy(query, groupByColumnName, startDate, endDate, partnerNames);
    }

    public Page<TransferInfoEntity> transInfoAndTransInfoD(TransferInfoModel transferInfoModel, Pageable pageable, List<String> partnerNames, boolean isPEM) {
        return jdbcTemplateComponent.transInfoAndTransInfoD(TRANSFER_INFO_AND_D_QUERY, transferInfoModel, pageable, partnerNames, isPEM, SQL_SERVER);
    }

}
