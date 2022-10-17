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

import static com.pe.pcm.utils.PCMConstants.ORACLE;
import static com.pe.pcm.utils.ReportsCommonFunctions.addRequiredFiltersFor997Report;
import static com.pe.pcm.utils.ReportsCommonFunctions.getExportDataFlowQuery;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * @author Kiran Reddy.
 */
@Repository
public class OracleReportRepository implements ReportRepository {

    private static final String TRANSFER_INFO_AND_D_QUERY = "SELECT * " +
            "FROM (SELECT A.*, ROWNUM RNUM" +
            "      FROM (SELECT *" +
            "            FROM ((SELECT * FROM PETPE_TRANSFERINFO FILTER)" +
            "                  UNION ALL" +
            "                  (SELECT * FROM PETPE_TRANSFERINFO_STAGING FILTER))" +
            "            ORDER BY SORTING_DETAILS) A" +
            "      WHERE ROWNUM <= ?) " +
            "WHERE RNUM > ?";

    private final JdbcTemplateComponent jdbcTemplateComponent;

    public OracleReportRepository(JdbcTemplateComponent jdbcTemplateComponent) {
        this.jdbcTemplateComponent = jdbcTemplateComponent;
    }

    public Page<OverDueMapper> findAll997OverDues(OverDueReportModel overDueReportModel, Pageable pageable, List<String> partnerNameList) {

        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM ( SELECT A.*, ROWNUM RNUM FROM (SELECT * FROM (SELECT CS.VALUE , PE.FILEARRIVED , PE.DESTFILENAME, PE.PARTNER, PE.DOCTRANS, PE.SENDERID, PE.RECIVERID, CS.WF_ID, TO_TIMESTAMP( FILEARRIVED,'DD-MON-RR HH.MI.SSXFF AM' ) - TO_TIMESTAMP( SYSDATE,'DD-MON-RR HH.MI.SSXFF AM' ) AS OVERDUETIME FROM CORRELATION_SET CS INNER JOIN PETPE_TRANSFERINFO PE ON CS.WF_ID = PE.COREBPID WHERE CS.NAME='GroupAckStatus' AND PE.STATUS = 'Success' ) A INNER JOIN (SELECT CORRELATION_SET.WF_ID, CORRELATION_SET.VALUE GROUPCOUNT FROM CORRELATION_SET WHERE NAME='GroupCount') B ON A.WF_ID = B.WF_ID");
        StringBuilder rowCountQueryBuilder = new StringBuilder("SELECT COUNT(*) FROM (SELECT CS.VALUE, PE.FILEARRIVED ,PE.DESTFILENAME, PE.PARTNER, PE.DOCTRANS, PE.SENDERID, PE.RECIVERID, CS.WF_ID FROM CORRELATION_SET CS INNER JOIN PETPE_TRANSFERINFO PE ON CS.WF_ID = PE.COREBPID WHERE CS.NAME = 'GroupAckStatus' AND PE.STATUS = 'Success' ) A INNER JOIN ( SELECT CORRELATION_SET.WF_ID, CORRELATION_SET.VALUE GROUPCOUNT FROM CORRELATION_SET WHERE NAME='GroupCount') B ON A.WF_ID = B.WF_ID");

        List<String> queryParamValues = new ArrayList<>();

        addRequiredFiltersFor997Report(queryBuilder, overDueReportModel, partnerNameList, false, queryParamValues, ORACLE);

        addRequiredFiltersFor997Report(rowCountQueryBuilder, overDueReportModel, partnerNameList, true, queryParamValues, ORACLE);

        String[] sort = pageable.getSort().toString().split(":");

        queryBuilder.append(" ORDER BY ").append(ReportsCommonFunctions.getDbColumnName(sort[0].trim())).append(sort[1]).append(") A")
                .append(" WHERE ROWNUM < ? ")
                .append(" ) ")
                .append(" WHERE RNUM >= ? ");
        return new PageImpl<>(jdbcTemplateComponent.getOverDueReports(queryBuilder.toString(), overDueReportModel, pageable, ORACLE, partnerNameList), pageable, jdbcTemplateComponent.getCount(rowCountQueryBuilder.toString(), queryParamValues));
    }

    public Page<DataFlowMapper> findAllWorkFlows(DataFlowModel dataFlowModel, Pageable pageable, String environment, Boolean isOnlyFlows, List<String> partnerPkIdList) {
        return jdbcTemplateComponent.findAllWorkFlows(dataFlowModel, pageable, ORACLE, environment, isOnlyFlows, partnerPkIdList);
    }

    public List<DataFlowMapper> exportAllWorkFlows(List<String> partnerPkIdList) {
        return jdbcTemplateComponent.getWorkFlowsReport(getExportDataFlowQuery(ORACLE, TRUE, FALSE, partnerPkIdList).append(") A )").toString(), new DataFlowModel(), partnerPkIdList, null, null, null, FALSE);
    }

    public String purgeTransactions(Long days) {
        return jdbcTemplateComponent.purgeTransactions(ORACLE, days);
    }

    public String generateUniqueKey() {
        return jdbcTemplateComponent.generateUniqueKey(ORACLE);
    }

    // ## EFX Reports

    public List<FileNameCountModel> getProducerData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getProducerData(transferInfoListModel, ORACLE);
    }

    public List<FileNameCountModel> getConsumerData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getConsumerData(transferInfoListModel, ORACLE);
    }

    public List<CommunityManagerNameModel> getUidData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getUidData(transferInfoListModel, ORACLE);
    }

    public ReportTotalCountsModel getTotalCountData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getTotalCountData(transferInfoListModel, ORACLE);
    }

    public List<DataBarChartModel> getProducerConsumerData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getProducerConsumerData(transferInfoListModel, ORACLE);
    }

    public List<DataModel> getDateFileCountFileSizeData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getDateFileCountFileSizeData(transferInfoListModel, ORACLE);
    }

    public ExternalInternalTotalCounts getExtIntTotalCounts(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getExtIntTotalCounts(transferInfoListModel, ORACLE);
    }

    public List<CommunityManagerNameModel> getFileSizeData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getFileSizeData(transferInfoListModel, ORACLE);
    }

    public List<CommunityManagerNameModel> getChargebackData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getChargebackData(transferInfoListModel, ORACLE);
    }

    public List<CommunityManagerNameModel> getAppData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getAppData(transferInfoListModel, ORACLE);
    }

    public List<CommunityManagerNameModel> getBuData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getBuData(transferInfoListModel, ORACLE);
    }

    public List<DataModel> getMonthlyData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getMonthlyData(transferInfoListModel, ORACLE);
    }

    public List<DataModel> getQuarterlyData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getQuarterlyData(transferInfoListModel, ORACLE);
    }

    public List<CommunityManagerNameModel> getPNODEData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getPNODEData(transferInfoListModel, ORACLE);
    }

    public List<CommunityManagerNameModel> getSNODEData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getSNODEData(transferInfoListModel, ORACLE);
    }

    public ProducerConsumerCountsModel getProducerConsumerTotalCount(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getProducerConsumerTotalCount(transferInfoListModel, ORACLE);
    }

    public List<DataTableModel> getSrcDestFilenameTotalCount(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getSrcDestFilenameTotalCount(transferInfoListModel, ORACLE);
    }

    public Map<String, Long> queryForGroupBy(String query, String groupByColumnName, String startDate, String endDate, List<String> partnerNames) {
        return jdbcTemplateComponent.queryForGroupBy(query, groupByColumnName, startDate, endDate, partnerNames);
    }

    public Page<TransferInfoEntity> transInfoAndTransInfoD(TransferInfoModel transferInfoModel, Pageable pageable, List<String> partnerNames, boolean isPEM) {
        return jdbcTemplateComponent.transInfoAndTransInfoD(TRANSFER_INFO_AND_D_QUERY, transferInfoModel, pageable, partnerNames, isPEM, ORACLE);
    }

}
