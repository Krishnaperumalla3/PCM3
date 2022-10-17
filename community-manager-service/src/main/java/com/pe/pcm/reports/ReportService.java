package com.pe.pcm.reports;

import com.pe.pcm.common.CommunityManagerNameModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public List<FileNameCountModel> getProducerData(TransferInfoListModel transferInfoListModel) {
        return reportRepository.getProducerData(transferInfoListModel);
    }

    public List<FileNameCountModel> getConsumerData(TransferInfoListModel transferInfoListModel) {
        return reportRepository.getConsumerData(transferInfoListModel);
    }

    public List<CommunityManagerNameModel> getUidData(TransferInfoListModel transferInfoListModel) {
        return reportRepository.getUidData(transferInfoListModel);
    }

    public ReportTotalCountsModel getTotalCountData(TransferInfoListModel transferInfoListModel) {
        return reportRepository.getTotalCountData(transferInfoListModel);
    }

    public List<DataBarChartModel> getProducerConsumerData(TransferInfoListModel transferInfoListModel) {
        return reportRepository.getProducerConsumerData(transferInfoListModel);
    }

    public List<DataModel> getDateFileCountFileSizeData(TransferInfoListModel transferInfoListModel) {
        return reportRepository.getDateFileCountFileSizeData(transferInfoListModel);
    }

    public ExternalInternalTotalCounts getExtIntTotalCounts(TransferInfoListModel transferInfoListModel) {
        return reportRepository.getExtIntTotalCounts(transferInfoListModel);
    }

    public List<CommunityManagerNameModel> getFileSizeData(TransferInfoListModel transferInfoListModel) {
        return reportRepository.getFileSizeData(transferInfoListModel);
    }

    public List<CommunityManagerNameModel> getChargebackData(TransferInfoListModel transferInfoListModel) {
        return reportRepository.getChargebackData(transferInfoListModel);
    }

    public List<CommunityManagerNameModel> getAppData(TransferInfoListModel transferInfoListModel) {
        return reportRepository.getAppData(transferInfoListModel);
    }

    public List<CommunityManagerNameModel> getBuData(TransferInfoListModel transferInfoListModel) {
        return reportRepository.getBuData(transferInfoListModel);
    }

    public List<DataModel> getMonthlyData(TransferInfoListModel transferInfoListModel) {
        return reportRepository.getMonthlyData(transferInfoListModel);
    }

    public List<DataModel> getQuarterlyData(TransferInfoListModel transferInfoListModel) {
        return reportRepository.getQuarterlyData(transferInfoListModel);
    }

    public List<CommunityManagerNameModel> getPNODEData(TransferInfoListModel transferInfoListModel) {
        return reportRepository.getPNODEData(transferInfoListModel);
    }

    public List<CommunityManagerNameModel> getSNODEData(TransferInfoListModel transferInfoListModel) {
        return reportRepository.getSNODEData(transferInfoListModel);
    }

    public ProducerConsumerCountsModel getProducerConsumerTotalCount(TransferInfoListModel transferInfoListModel) {
        return reportRepository.getProducerConsumerTotalCount(transferInfoListModel);
    }

    public List<DataTableModel> getSrcDestFilenameTotalCount(TransferInfoListModel transferInfoListModel) {
        return reportRepository.getSrcDestFilenameTotalCount(transferInfoListModel);
    }

}
