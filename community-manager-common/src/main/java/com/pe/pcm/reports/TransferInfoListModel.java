package com.pe.pcm.reports;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransferInfoListModel {

    private String dateRangeStart;
    private String dateRangeEnd;
    private String status;
    private String srcFileName;
    private String destFileName;

    private List<String> partner;
    private List<String> application;
    private List<String> uid;
    private List<String> app;
    private List<String> bu;
    private List<String> pnode;
    private List<String> snode;
    private String reportType;

    public List<String> getPnode() {
        return pnode;
    }

    public void setPnode(List<String> pnode) {
        this.pnode = pnode;
    }

    public List<String> getSnode() {
        return snode;
    }

    public void setSnode(List<String> snode) {
        this.snode = snode;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public List<String> getApp() {return app;}

    public void setApp(List<String> app) {this.app = app;}

    public List<String> getBu() {return bu;}

    public void setBu(List<String> bu) {this.bu = bu;}

    public List<String> getUid() {
        return uid;
    }

    public void setUid(List<String> uid) {
        this.uid = uid;
    }

    public String getDateRangeStart() {
        return dateRangeStart;
    }

    public void setDateRangeStart(String dateRangeStart) {
        this.dateRangeStart = dateRangeStart;
    }

    public String getDateRangeEnd() {
        return dateRangeEnd;
    }

    public void setDateRangeEnd(String dateRangeEnd) {
        this.dateRangeEnd = dateRangeEnd;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSrcFileName() {
        return srcFileName;
    }

    public void setSrcFileName(String srcFileName) {
        this.srcFileName = srcFileName;
    }

    public String getDestFileName() {
        return destFileName;
    }

    public void setDestFileName(String destFileName) {
        this.destFileName = destFileName;
    }

    public List<String> getPartner() {
        return partner;
    }

    public void setPartner(List<String> partner) {
        this.partner = partner;
    }

    public List<String> getApplication() {
        return application;
    }

    public void setApplication(List<String> application) {
        this.application = application;
    }

    public TransferInfoListModel() {
    }

    @Override
    public String toString() {
        return "TransferInfoListModel{" +
                "dateRangeStart='" + dateRangeStart + '\'' +
                ", dateRangeEnd='" + dateRangeEnd + '\'' +
                ", status='" + status + '\'' +
                ", srcFileName='" + srcFileName + '\'' +
                ", destFileName='" + destFileName + '\'' +
                ", partner=" + partner +
                ", application=" + application +
                ", uid=" + uid +
                ", app=" + app +
                ", bu=" + bu +
                ", pnode=" + pnode +
                ", snode=" + snode +
                ", reportType=" + reportType +
                '}';
    }
}
