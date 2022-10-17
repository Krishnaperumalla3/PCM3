package com.pe.pcm.reports;

import com.pe.pcm.profile.ProfileModel;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Kiran Reddy.
 */
@Service
public class BasicReportsService {

    private final BasicReportsRepository basicReportsRepository;

    public BasicReportsService(BasicReportsRepository basicReportsRepository) {
        this.basicReportsRepository = basicReportsRepository;
    }

    public List<NameCountModel> fileProcessByMonthLast36Months(BasicReportRequestModel basicReportRequestModel) {
        return basicReportsRepository.fileProcessByMonthLast36Months(basicReportRequestModel);
    }

    public List<NameCountModel> fileProcessByDayLast12Months(BasicReportRequestModel basicReportRequestModel) {
        return basicReportsRepository.fileProcessByDayLast12Months(basicReportRequestModel);
    }

    public List<NameCountModel> fileProcessByHourLast30Days(BasicReportRequestModel basicReportRequestModel) {
        return basicReportsRepository.fileProcessByHourLast30Days(basicReportRequestModel);
    }

    public List<NameCountModel> fileProcessByDayInWeekAvgLast4Weeks(BasicReportRequestModel basicReportRequestModel) {
        return basicReportsRepository.fileProcessByDayInWeekAvgLast4Weeks(basicReportRequestModel);
    }

    public List<NameCountModel> fileSizeProcessByMonthLast36Months(BasicReportRequestModel basicReportRequestModel) {
        return basicReportsRepository.fileSizeProcessByMonthLast36Months(basicReportRequestModel);
    }

    public List<NameCountModel> fileSizeProcessByDayLast12Months(BasicReportRequestModel basicReportRequestModel) {
        return basicReportsRepository.fileSizeProcessByDayLast12Months(basicReportRequestModel);
    }

    public List<NameCountModel> fileSizeProcessByHourLast30Days(BasicReportRequestModel basicReportRequestModel) {
        return basicReportsRepository.fileSizeProcessByHourLast30Days(basicReportRequestModel);
    }

    public List<NameCountModel> fileProcessPeakHoursLast31Days(BasicReportRequestModel basicReportRequestModel) {
        return basicReportsRepository.fileProcessPeakHoursLast31Days(basicReportRequestModel);
    }

    public List<ProfileModel> inactivePartnersLastOneYear(Integer days) {
        return basicReportsRepository.inactivePartnersLastOneYear(days);
    }

    public int filesProcessedThisHour(BasicReportRequestModel basicReportRequestModel) {
        return basicReportsRepository.filesProcessedThisHour(basicReportRequestModel);
    }

    public int filesProcessedThisDay(BasicReportRequestModel basicReportRequestModel) {
        return basicReportsRepository.filesProcessedThisDay(basicReportRequestModel);
    }

    public int filesProcessedThisWeek(BasicReportRequestModel basicReportRequestModel) {
        return basicReportsRepository.filesProcessedThisWeek(basicReportRequestModel);
    }

    public int filesProcessedThisMonth(BasicReportRequestModel basicReportRequestModel) {
        return basicReportsRepository.filesProcessedThisMonth(basicReportRequestModel);
    }

}
