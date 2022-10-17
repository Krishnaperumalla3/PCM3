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
import com.pe.pcm.reports.*;
import com.pe.pcm.reports.entity.TransferInfoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author Kiran Reddy.
 */
public interface ReportRepository {

    Page<DataFlowMapper> findAllWorkFlows(DataFlowModel dataFlowModel, Pageable pageable, String environment, Boolean isOnlyFlows, List<String> partnerPkIdList);

    List<DataFlowMapper> exportAllWorkFlows(List<String> partnerPkIdList);

    Page<OverDueMapper> findAll997OverDues(OverDueReportModel overDueReportModel, Pageable pageable, List<String> partnerPkIdList);

    String purgeTransactions(Long days);

    String generateUniqueKey();

    // ## EFX Reports Start

    List<FileNameCountModel> getProducerData(TransferInfoListModel transferInfoListModel);

    List<FileNameCountModel> getConsumerData(TransferInfoListModel transferInfoListModel);

    List<CommunityManagerNameModel> getUidData(TransferInfoListModel transferInfoListModel);

    ReportTotalCountsModel getTotalCountData(TransferInfoListModel transferInfoListModel);

    List<DataBarChartModel> getProducerConsumerData(TransferInfoListModel transferInfoListModel);

    List<DataModel> getDateFileCountFileSizeData(TransferInfoListModel transferInfoListModel);

    ExternalInternalTotalCounts getExtIntTotalCounts(TransferInfoListModel transferInfoListModel);

    List<CommunityManagerNameModel> getFileSizeData(TransferInfoListModel transferInfoListModel);

    List<CommunityManagerNameModel> getChargebackData(TransferInfoListModel transferInfoListModel);

    List<CommunityManagerNameModel> getAppData(TransferInfoListModel transferInfoListModel);

    List<CommunityManagerNameModel> getBuData(TransferInfoListModel transferInfoListModel);

    List<DataModel> getMonthlyData(TransferInfoListModel transferInfoListModel);

    List<DataModel> getQuarterlyData(TransferInfoListModel transferInfoListModel);

    List<CommunityManagerNameModel> getPNODEData(TransferInfoListModel transferInfoListModel);

    List<CommunityManagerNameModel> getSNODEData(TransferInfoListModel transferInfoListModel);

    ProducerConsumerCountsModel getProducerConsumerTotalCount(TransferInfoListModel transferInfoListModel);

    List<DataTableModel> getSrcDestFilenameTotalCount(TransferInfoListModel transferInfoListModel);

    // ## EFX Report End
    Map<String, Long> queryForGroupBy(String query, String groupByColumnName, String startDate, String endDate, List<String> partnerNames);

    Page<TransferInfoEntity> transInfoAndTransInfoD(TransferInfoModel transferInfoModel, Pageable pageable, List<String> partners, boolean isPEM);

}
