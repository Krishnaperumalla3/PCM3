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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.pe.pcm.utils.PCMConstants.DB2;
import static com.pe.pcm.utils.ReportsCommonFunctions.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * @author Kiran Reddy.
 */
@Repository
public class Db2ReportRepository implements ReportRepository {

    private static final String TRANSFER_INFO_AND_D_QUERY = "SELECT * " +
            "FROM (SELECT inner2_.*, rownumber() OVER(ORDER BY ORDER OF inner2_) AS row_number_" +
            "      FROM (SELECT *" +
            "            FROM ((SELECT * FROM PETPE_TRANSFERINFO FILTER)" +
            "                  UNION ALL" +
            "                  (SELECT * FROM PETPE_TRANSFERINFO_STAGING FILTER))" +
            "            ORDER BY SORTING_DETAILS FETCH FIRST ? ROWS ONLY) AS inner2_) AS inner1_ " +
            "WHERE row_number_ > ? " +
            "ORDER BY row_number_ ";
    private final JdbcTemplateComponent jdbcTemplateComponent;

    public Db2ReportRepository(JdbcTemplateComponent jdbcTemplateComponent) {
        this.jdbcTemplateComponent = jdbcTemplateComponent;
    }

    //TODO This has to be changed as like Workflow reports
    public Page<OverDueMapper> findAll997OverDues(OverDueReportModel overDueReportModel, Pageable pageable, List<String> partnerNameList) {
        StringBuilder queryBuilder = new StringBuilder("Select * From (SELECT CS.VALUE ,PE.FILEARRIVED ,PE.DESTFILENAME,PE.PARTNER,PE.DOCTRANS,PE.SENDERID,PE.RECIVERID,CS.WF_ID, TO_DATE(PE.FILEARRIVED,'YYYY-MM-DD HH24:MI:SS') - TO_DATE(SYSDATE,'YYYY-MM-DD HH24:MI:SS') as OVERDUETIME  FROM CORRELATION_SET CS INNER JOIN PETPE_TRANSFERINFO PE ON CS.WF_ID=PE.COREBPID where cs.name='GroupAckStatus' AND PE.STATUS = 'Success' ) a INNER JOIN (SELECT CORRELATION_SET.WF_ID, CORRELATION_SET.VALUE GroupCount FROM CORRELATION_SET WHERE NAME='GroupCount') b ON A.WF_ID=b.WF_ID");
        StringBuilder rowCountQueryBuilder = new StringBuilder("Select count(*) From (SELECT CS.VALUE ,PE.FILEARRIVED ,PE.DESTFILENAME,PE.PARTNER,PE.DOCTRANS,PE.SENDERID,PE.RECIVERID,CS.WF_ID FROM CORRELATION_SET CS INNER JOIN PETPE_TRANSFERINFO PE ON CS.WF_ID=PE.COREBPID where cs.name='GroupAckStatus' AND PE.STATUS = 'Success' ) a INNER JOIN (SELECT CORRELATION_SET.WF_ID, CORRELATION_SET.VALUE GroupCount FROM CORRELATION_SET WHERE NAME='GroupCount') b ON A.WF_ID=b.WF_ID");

        List<String> queryParamValues = new ArrayList<>();

        addRequiredFiltersFor997Report(queryBuilder, overDueReportModel, partnerNameList, false, queryParamValues, DB2);
        addRequiredFiltersFor997Report(rowCountQueryBuilder, overDueReportModel, partnerNameList, true, queryParamValues, DB2);

        String[] sort = pageable.getSort().toString().split(":");
        queryBuilder.append(" ORDER BY ").append(getDbColumnName(sort[0].trim())).append(sort[1]);
        //Below should be handel for non oracle support
        queryBuilder.append(" OFFSET ? ").append(" ROWS FETCH FIRST ? ").append(" ROWS ONLY");
        return new PageImpl<>(jdbcTemplateComponent.getOverDueReports(queryBuilder.toString(), overDueReportModel, pageable, DB2, partnerNameList), pageable, jdbcTemplateComponent.getCount(rowCountQueryBuilder.toString(), queryParamValues));
    }

    public Page<DataFlowMapper> findAllWorkFlows(DataFlowModel dataFlowModel, Pageable pageable, String environment, Boolean isOnlyFlows, List<String> partnerPkIdList) {
        return jdbcTemplateComponent.findAllWorkFlows(dataFlowModel, pageable, DB2, environment, isOnlyFlows, partnerPkIdList);
    }

    public List<DataFlowMapper> exportAllWorkFlows(List<String> partnerPkIdList) {
        return jdbcTemplateComponent.getWorkFlowsReport(getExportDataFlowQuery(DB2, TRUE, FALSE, partnerPkIdList).append(") A ").toString(), new DataFlowModel(), partnerPkIdList, null, DB2, null, FALSE);
    }

    public String purgeTransactions(Long days) {
        return jdbcTemplateComponent.purgeTransactions(DB2, days);
    }

    public String generateUniqueKey() {
        return jdbcTemplateComponent.generateUniqueKey(DB2);
    }

    // ## EFX Reports

    public List<FileNameCountModel> getProducerData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getProducerData(transferInfoListModel, DB2);
    }

    public List<FileNameCountModel> getConsumerData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getConsumerData(transferInfoListModel, DB2);
    }

    public List<CommunityManagerNameModel> getUidData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getUidData(transferInfoListModel, DB2);
    }

    public ReportTotalCountsModel getTotalCountData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getTotalCountData(transferInfoListModel, DB2);
    }

    public List<DataBarChartModel> getProducerConsumerData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getProducerConsumerData(transferInfoListModel, DB2);
    }

    public List<DataModel> getDateFileCountFileSizeData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getDateFileCountFileSizeData(transferInfoListModel, DB2);
    }

    public ExternalInternalTotalCounts getExtIntTotalCounts(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getExtIntTotalCounts(transferInfoListModel, DB2);
    }

    public List<CommunityManagerNameModel> getFileSizeData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getFileSizeData(transferInfoListModel, DB2);
    }

    public List<CommunityManagerNameModel> getChargebackData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getChargebackData(transferInfoListModel, DB2);
    }

    public List<CommunityManagerNameModel> getAppData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getAppData(transferInfoListModel, DB2);
    }

    public List<CommunityManagerNameModel> getBuData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getBuData(transferInfoListModel, DB2);
    }

    public List<DataModel> getMonthlyData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getMonthlyData(transferInfoListModel, DB2);
    }

    public List<DataModel> getQuarterlyData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getQuarterlyData(transferInfoListModel, DB2);
    }

    public List<CommunityManagerNameModel> getPNODEData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getPNODEData(transferInfoListModel, DB2);
    }

    public List<CommunityManagerNameModel> getSNODEData(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getSNODEData(transferInfoListModel, DB2);
    }

    public ProducerConsumerCountsModel getProducerConsumerTotalCount(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getProducerConsumerTotalCount(transferInfoListModel, DB2);
    }

    public List<DataTableModel> getSrcDestFilenameTotalCount(TransferInfoListModel transferInfoListModel) {
        return jdbcTemplateComponent.getSrcDestFilenameTotalCount(transferInfoListModel, DB2);
    }

    public Map<String, Long> queryForGroupBy(String query, String groupByColumnName, String startDate, String endDate, List<String> partnerNames) {
        return jdbcTemplateComponent.queryForGroupBy(query, groupByColumnName, startDate, endDate, partnerNames);
    }

    public Page<TransferInfoEntity> transInfoAndTransInfoD(TransferInfoModel transferInfoModel, Pageable pageable, List<String> partnerNames, boolean isPEM) {
        return jdbcTemplateComponent.transInfoAndTransInfoD(TRANSFER_INFO_AND_D_QUERY, transferInfoModel, pageable, partnerNames, isPEM, DB2);
    }

}
