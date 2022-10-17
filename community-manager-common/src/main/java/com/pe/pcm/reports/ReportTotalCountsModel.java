package com.pe.pcm.reports;

public class ReportTotalCountsModel {

    String totalFileCount;
    String totalFileSize;
    String maxFileSize;
    String minFileSize;
    String totalChargeback;

    public String getTotalChargeback() {
        return totalChargeback;
    }

    public void setTotalChargeback(String totalChargeback) {
        this.totalChargeback = totalChargeback;
    }

    public String getTotalFileCount() {
        return totalFileCount;
    }

    public void setTotalFileCount(String totalFileCount) {
        this.totalFileCount = totalFileCount;
    }

    public String getTotalFileSize() {
        return totalFileSize;
    }

    public void setTotalFileSize(String totalFileSize) {
        this.totalFileSize = totalFileSize;
    }

    public String getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(String maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    public String getMinFileSize() {
        return minFileSize;
    }

    public void setMinFileSize(String minFileSize) {
        this.minFileSize = minFileSize;
    }

    public ReportTotalCountsModel(String totalFileCount, String totalFileSize, String maxFileSize, String minFileSize, String totalChargeback) {
        this.totalFileCount = totalFileCount;
        this.totalFileSize = totalFileSize;
        this.maxFileSize = maxFileSize;
        this.minFileSize = minFileSize;
        this.totalChargeback = totalChargeback;
    }

    @Override
    public String toString() {
        return "ReportTotalCountsModel{" +
                "totalFileCount='" + totalFileCount + '\'' +
                ", totalFileSize='" + totalFileSize + '\'' +
                ", maxFileSize='" + maxFileSize + '\'' +
                ", minFileSize='" + minFileSize + '\'' +
                ", totalChargeback='" + totalChargeback + '\'' +
                '}';
    }
}
