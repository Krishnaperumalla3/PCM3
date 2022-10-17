package com.pe.pcm.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pe.pcm.config.s3.S3InfoModel;

import java.io.Serializable;

/**
 * @author Kiran Reddy.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileSchedulerModel implements Serializable {

    private String pkId;
    private String fileAge;
    private boolean active;
    private String cloudName;

    private S3InfoModel s3SchedulerConfig;

    public String getPkId() {
        return pkId;
    }

    public FileSchedulerModel setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getFileAge() {
        return fileAge;
    }

    public FileSchedulerModel setFileAge(String fileAge) {
        this.fileAge = fileAge;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public FileSchedulerModel setActive(boolean active) {
        this.active = active;
        return this;
    }

    public String getCloudName() {
        return cloudName;
    }

    public FileSchedulerModel setCloudName(String cloudName) {
        this.cloudName = cloudName;
        return this;
    }

    public S3InfoModel getS3SchedulerConfig() {
        return s3SchedulerConfig;
    }

    public FileSchedulerModel setS3SchedulerConfig(S3InfoModel s3SchedulerConfig) {
        this.s3SchedulerConfig = s3SchedulerConfig;
        return this;
    }

    @Override
    public String toString() {
        return "FileSchedulerModel{" +
                "pkId='" + pkId + '\'' +
                ", fileAge='" + fileAge + '\'' +
                ", active=" + active +
                ", cloudName='" + cloudName + '\'' +
                ", s3SchedulerConfig=" + s3SchedulerConfig +
                '}';
    }
}
