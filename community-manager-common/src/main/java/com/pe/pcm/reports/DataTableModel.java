package com.pe.pcm.reports;

public class DataTableModel {

    private String srcFilename;
    private String destFilename;
    private String totalCount;

    public String getSrcFilename() {
        return srcFilename;
    }

    public void setSrcFilename(String srcFilename) {
        this.srcFilename = srcFilename;
    }

    public String getDestFilename() {
        return destFilename;
    }

    public void setDestFilename(String destFilename) {
        this.destFilename = destFilename;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public DataTableModel(String srcFilename, String destFilename, String totalCount) {
        this.srcFilename = srcFilename;
        this.destFilename = destFilename;
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "DataTableModel{" +
                "srcFilename='" + srcFilename + '\'' +
                ", destFilename='" + destFilename + '\'' +
                ", totalCount='" + totalCount + '\'' +
                '}';
    }
}
