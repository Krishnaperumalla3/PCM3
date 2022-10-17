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

package com.pe.pcm.protocol.awss3.entity;

import com.pe.pcm.audit.Auditable;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.StringJoiner;

/**
 * @author Shameer.
 */
@Entity
@Table(name = "PETPE_AWS_S3")
public class AwsS3Entity extends Auditable {

    @Id
    private String pkId;

    @NotNull
    private String subscriberType;

    @NotNull
    private String subscriberId;

    @NotNull
    private String poolingIntervalMins;

    private String sourcePath;

    private String fileType;
    @NotNull
    private String bucketName;

    private String fileName;

    @NotNull
    private String accessKey;

    @NotNull
    private String secretKey;

    private String endpoint;

    private String region;

    private String endPointUrl;

    private String folderName;

    private String queueName;

    @NotNull
    private String isActive;

    @NotNull
    private String isHubInfo;

    private String inMailbox;

    private String adapterName;

    private String inboundConnectionType;

    private String outboundConnectionType;

    @NotNull
    private String deleteAfterCollection;

    public String getPkId() {
        return pkId;
    }

    public AwsS3Entity setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getSubscriberType() {
        return subscriberType;
    }

    public AwsS3Entity setSubscriberType(String subscriberType) {
        this.subscriberType = subscriberType;
        return this;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public AwsS3Entity setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
        return this;
    }

    public String getPoolingIntervalMins() {
        return poolingIntervalMins;
    }

    public AwsS3Entity setPoolingIntervalMins(String poolingIntervalMins) {
        this.poolingIntervalMins = poolingIntervalMins;
        return this;
    }

    public String getSourcePath() {
        return sourcePath;
    }

    public AwsS3Entity setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
        return this;
    }

    public String getFileType() {
        return fileType;
    }

    public AwsS3Entity setFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public String getBucketName() {
        return bucketName;
    }

    public AwsS3Entity setBucketName(String bucketName) {
        this.bucketName = bucketName;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public AwsS3Entity setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public AwsS3Entity setAccessKey(String accessKey) {
        this.accessKey = accessKey;
        return this;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public AwsS3Entity setSecretKey(String secretKey) {
        this.secretKey = secretKey;
        return this;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public AwsS3Entity setEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public String getRegion() {
        return region;
    }

    public AwsS3Entity setRegion(String region) {
        this.region = region;
        return this;
    }

    public String getIsActive() {
        return isActive;
    }

    public AwsS3Entity setIsActive(String isActive) {
        this.isActive = isActive;
        return this;
    }

    public String getIsHubInfo() {
        return isHubInfo;
    }

    public AwsS3Entity setIsHubInfo(String isHubInfo) {
        this.isHubInfo = isHubInfo;
        return this;
    }

    public String getInMailbox() {
        return inMailbox;
    }

    public AwsS3Entity setInMailbox(String inMailbox) {
        this.inMailbox = inMailbox;
        return this;
    }

    public String getAdapterName() {
        return adapterName;
    }

    public AwsS3Entity setAdapterName(String adapterName) {
        this.adapterName = adapterName;
        return this;
    }

    public String getInboundConnectionType() {
        return inboundConnectionType;
    }

    public AwsS3Entity setInboundConnectionType(String inboundConnectionType) {
        this.inboundConnectionType = inboundConnectionType;
        return this;
    }

    public String getOutboundConnectionType() {
        return outboundConnectionType;
    }

    public AwsS3Entity setOutboundConnectionType(String outboundConnectionType) {
        this.outboundConnectionType = outboundConnectionType;
        return this;
    }

    public String getEndPointUrl() {
        return endPointUrl;
    }

    public AwsS3Entity setEndPointUrl(String endPointUrl) {
        this.endPointUrl = endPointUrl;
        return this;
    }

    public String getFolderName() {
        return folderName;
    }

    public AwsS3Entity setFolderName(String folderName) {
        this.folderName = folderName;
        return this;
    }

    public String getQueueName() {
        return queueName;
    }

    public AwsS3Entity setQueueName(String queueName) {
        this.queueName = queueName;
        return this;
    }

    public String getDeleteAfterCollection() {
        return deleteAfterCollection;
    }

    public AwsS3Entity setDeleteAfterCollection(String deleteAfterCollection) {
        this.deleteAfterCollection = deleteAfterCollection;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AwsS3Entity.class.getSimpleName() + "[", "]")
                .add("pkId='" + pkId + "'")
                .add("subscriberType='" + subscriberType + "'")
                .add("subscriberId='" + subscriberId + "'")
                .add("poolingIntervalMins='" + poolingIntervalMins + "'")
                .add("sourcePath='" + sourcePath + "'")
                .add("fileType='" + fileType + "'")
                .add("bucketName='" + bucketName + "'")
                .add("fileName='" + fileName + "'")
                .add("accessKey='" + accessKey + "'")
                .add("secretKey='" + secretKey + "'")
                .add("endpoint='" + endpoint + "'")
                .add("region='" + region + "'")
                .add("endPointUrl='" + endPointUrl + "'")
                .add("folderName='" + folderName + "'")
                .add("queueName='" + queueName + "'")
                .add("isActive='" + isActive + "'")
                .add("isHubInfo='" + isHubInfo + "'")
                .add("inMailbox='" + inMailbox + "'")
                .add("adapterName='" + adapterName + "'")
                .add("inboundConnectionType='" + inboundConnectionType + "'")
                .add("outboundConnectionType='" + outboundConnectionType + "'")
                .add("deleteAfterCollection='" + deleteAfterCollection + "'")
                .toString();
    }
}
