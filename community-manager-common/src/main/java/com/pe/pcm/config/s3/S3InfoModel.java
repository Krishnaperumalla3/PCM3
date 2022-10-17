package com.pe.pcm.config.s3;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Chenchu Kiran Reddy.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class S3InfoModel implements Serializable {

    private String accessKeyId;
    private String secretKeyId;
    private String region;
    private String bucketName;
    private List<FilesPathRefModel> filesPathDetails;

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public S3InfoModel setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
        return this;
    }

    public String getSecretKeyId() {
        return secretKeyId;
    }

    public S3InfoModel setSecretKeyId(String secretKeyId) {
        this.secretKeyId = secretKeyId;
        return this;
    }

    public String getRegion() {
        return region;
    }

    public S3InfoModel setRegion(String region) {
        this.region = region;
        return this;
    }

    public String getBucketName() {
        return bucketName;
    }

    public S3InfoModel setBucketName(String bucketName) {
        this.bucketName = bucketName;
        return this;
    }

    public List<FilesPathRefModel> getFilesPathDetails() {
        if (filesPathDetails != null) {
            return filesPathDetails;
        }
        return new ArrayList<>();
    }

    public S3InfoModel setFilesPathDetails(List<FilesPathRefModel> filesPathDetails) {
        this.filesPathDetails = filesPathDetails;
        return this;
    }

    @Override
    public String toString() {
        return "S3InfoModel{" +
                "accessKeyId='" + accessKeyId + '\'' +
                ", secretKeyId='" + secretKeyId + '\'' +
                ", region='" + region + '\'' +
                ", bucketName='" + bucketName + '\'' +
                ", filesPathDetails=" + filesPathDetails +
                '}';
    }
}
