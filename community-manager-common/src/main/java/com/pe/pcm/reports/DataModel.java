package com.pe.pcm.reports;

import java.io.Serializable;

public class DataModel implements Serializable {

    private String Date;
    private String FileCount;
    private String FileSize;
    private String Chargeback;

    public String getChargeback() {
        return Chargeback;
    }

    public void setChargeback(String chargeback) {
        Chargeback = chargeback;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getFileCount() {
        return FileCount;
    }

    public void setFileCount(String fileCount) {
        FileCount = fileCount;
    }

    public String getFileSize() {
        return FileSize;
    }

    public void setFileSize(String fileSize) {
        FileSize = fileSize;
    }

    @Override
    public String toString() {
        return "DataLineChartModel{" +
                "Date='" + Date + '\'' +
                ", FileCount='" + FileCount + '\'' +
                ", FileSize='" + FileSize + '\'' +
                ", Chargeback='" + Chargeback + '\'' +
                '}';
    }

    public DataModel(String Date, String FileCount, String FileSize, String Chargeback) {
        this.Date = Date;
        this.FileCount = FileCount;
        this.FileSize = FileSize;
        this.Chargeback = Chargeback;
    }

    public DataModel() {
    }
}
