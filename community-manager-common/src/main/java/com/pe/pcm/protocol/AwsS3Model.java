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

package com.pe.pcm.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.pe.pcm.b2b.protocol.ConsumerS3Configuration;
import com.pe.pcm.b2b.protocol.ProducerS3Configuration;
import com.pe.pcm.profile.ProfileModel;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * @author Kiran Reddy.
 */

@JsonPropertyOrder({"pkId", "profileName", "profileId", "emailId", "phone",
        "protocol", "addressLine1", "addressLine2", "status", "sourcepath",
        "filetype", "bucketname", "filename", "accessKey", "secretKey",
        "endpoint", "region", "isActive", "inMailbox",
        "hubInfo", "poolingInterval", "adapterName"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class AwsS3Model extends ProfileModel implements Serializable {

    private String poolingInterval;

    private String sourcePath;

    private String fileType;

    private String bucketName;

    private String fileName;

    private String accessKey;

    private String secretKey;

    private String endpoint;

    private String region;

    private String isActive;

    private String isHubInfo;

    private String inMailbox;

    private String adapterName;

    private String endPointUrl;
    private String folderName;
    private Boolean isInitiatingConsumer;
    private Boolean isInitiatingProducer;
    private Boolean isListeningConsumer;
    private Boolean isListeningProducer;
    private Boolean inboundConnectionType;
    private Boolean outboundConnectionType;
    private String queueName;
    private Boolean isSIProfile;
    private String authenticationType;
    private String authenticationHost;
    private String profileUserName;
    private String profileUserPassword;
    private boolean doesUseSSH;
    private Boolean asciiArmor;
    private Boolean doesRequireEncryptedData;
    private Boolean doesRequireSignedData;
    private Boolean textMode;
    private ProducerS3Configuration producerS3Configuration;
    private ConsumerS3Configuration consumerS3Configuration;
    private Boolean deleteAfterCollection;


    @JacksonXmlProperty(localName = "POOLING_INTERVAL")
    public String getPoolingInterval() {
        return poolingInterval;
    }

    public AwsS3Model setPoolingInterval(String poolingInterval) {
        this.poolingInterval = poolingInterval;
        return this;
    }

    @JacksonXmlProperty(localName = "SOURCE_PATH")
    public String getSourcePath() {
        return sourcePath;
    }

    public AwsS3Model setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
        return this;
    }

    @JacksonXmlProperty(localName = "FILE_TYPE")
    public String getFileType() {
        return fileType;
    }

    public AwsS3Model setFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    @JacksonXmlProperty(localName = "BUCKET_NAME")
    public String getBucketName() {
        return bucketName;
    }

    public AwsS3Model setBucketName(String bucketName) {
        this.bucketName = bucketName;
        return this;
    }

    @JacksonXmlProperty(localName = "FILE_NAME")
    public String getFileName() {
        return fileName;
    }

    public AwsS3Model setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    @JacksonXmlProperty(localName = "ACCESS_KEY")
    public String getAccessKey() {
        return accessKey;
    }

    public AwsS3Model setAccessKey(String accessKey) {
        this.accessKey = accessKey;
        return this;
    }

    @JacksonXmlProperty(localName = "SECRET_KEY")
    public String getSecretKey() {
        return secretKey;
    }

    public AwsS3Model setSecretKey(String secretKey) {
        this.secretKey = secretKey;
        return this;
    }

    @JacksonXmlProperty(localName = "END_POINT")
    public String getEndpoint() {
        return endpoint;
    }

    public AwsS3Model setEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    @JacksonXmlProperty(localName = "REGION")
    public String getRegion() {
        return region;
    }

    public AwsS3Model setRegion(String region) {
        this.region = region;
        return this;
    }

    @JacksonXmlProperty(localName = "IS_ACTIVE")
    public String getIsActive() {
        return isActive;
    }

    public AwsS3Model setIsActive(String isActive) {
        this.isActive = isActive;
        return this;
    }

    @JacksonXmlProperty(localName = "IS_HUBINFO")
    public String getIsHubInfo() {
        return isHubInfo;
    }

    public AwsS3Model setIsHubInfo(String isHubInfo) {
        this.isHubInfo = isHubInfo;
        return this;
    }

    @JacksonXmlProperty(localName = "IN_MAILBOX")
    public String getInMailbox() {
        return inMailbox;
    }

    public AwsS3Model setInMailbox(String inMailbox) {
        this.inMailbox = inMailbox;
        return this;
    }

    @JacksonXmlProperty(localName = "ADAPTER_NAME")
    public String getAdapterName() {
        return adapterName;
    }

    public AwsS3Model setAdapterName(String adapterName) {
        this.adapterName = adapterName;
        return this;
    }

    @JacksonXmlProperty(localName = "DELETE_AFTER_COLLECTION")
    public Boolean getDeleteAfterCollection() {
        if (deleteAfterCollection == null) {
            return Boolean.FALSE;
        }
        return deleteAfterCollection;
    }

    public AwsS3Model setDeleteAfterCollection(Boolean deleteAfterCollection) {
        this.deleteAfterCollection = deleteAfterCollection;
        return this;
    }

    public Boolean getInitiatingConsumer() {
        return isInitiatingConsumer;
    }

    public AwsS3Model setInitiatingConsumer(Boolean initiatingConsumer) {
        isInitiatingConsumer = initiatingConsumer;
        return this;
    }

    public Boolean getInitiatingProducer() {
        return isInitiatingProducer;
    }

    public AwsS3Model setInitiatingProducer(Boolean initiatingProducer) {
        isInitiatingProducer = initiatingProducer;
        return this;
    }

    public Boolean getListeningConsumer() {
        return isListeningConsumer;
    }

    public AwsS3Model setListeningConsumer(Boolean listeningConsumer) {
        isListeningConsumer = listeningConsumer;
        return this;
    }

    public Boolean getListeningProducer() {
        return isListeningProducer;
    }

    public AwsS3Model setListeningProducer(Boolean listeningProducer) {
        isListeningProducer = listeningProducer;
        return this;
    }

    public Boolean getInboundConnectionType() {
        return inboundConnectionType;
    }

    public AwsS3Model setInboundConnectionType(Boolean inboundConnectionType) {
        this.inboundConnectionType = inboundConnectionType;
        return this;
    }

    public Boolean getOutboundConnectionType() {
        return outboundConnectionType;
    }

    public AwsS3Model setOutboundConnectionType(Boolean outboundConnectionType) {
        this.outboundConnectionType = outboundConnectionType;
        return this;
    }

    public String getQueueName() {
        return queueName;
    }

    public AwsS3Model setQueueName(String queueName) {
        this.queueName = queueName;
        return this;
    }

    public Boolean getSIProfile() {
        return isSIProfile;
    }

    public AwsS3Model setSIProfile(Boolean SIProfile) {
        isSIProfile = SIProfile;
        return this;
    }

    public String getAuthenticationType() {
        return authenticationType;
    }

    public AwsS3Model setAuthenticationType(String authenticationType) {
        this.authenticationType = authenticationType;
        return this;
    }

    public String getAuthenticationHost() {
        return authenticationHost;
    }

    public AwsS3Model setAuthenticationHost(String authenticationHost) {
        this.authenticationHost = authenticationHost;
        return this;
    }

    public String getProfileUserName() {
        return profileUserName;
    }

    public AwsS3Model setProfileUserName(String profileUserName) {
        this.profileUserName = profileUserName;
        return this;
    }

    public String getProfileUserPassword() {
        return profileUserPassword;
    }

    public AwsS3Model setProfileUserPassword(String profileUserPassword) {
        this.profileUserPassword = profileUserPassword;
        return this;
    }

    public boolean isDoesUseSSH() {
        return doesUseSSH;
    }

    public AwsS3Model setDoesUseSSH(boolean doesUseSSH) {
        this.doesUseSSH = doesUseSSH;
        return this;
    }

    public ProducerS3Configuration getProducerS3Configuration() {
        return producerS3Configuration;
    }

    public AwsS3Model setProducerS3Configuration(ProducerS3Configuration producerS3Configuration) {
        this.producerS3Configuration = producerS3Configuration;
        return this;
    }

    public ConsumerS3Configuration getConsumerS3Configuration() {
        return consumerS3Configuration;
    }

    public AwsS3Model setConsumerS3Configuration(ConsumerS3Configuration consumerS3Configuration) {
        this.consumerS3Configuration = consumerS3Configuration;
        return this;
    }

    public Boolean getAsciiArmor() {
        return asciiArmor;
    }

    public AwsS3Model setAsciiArmor(Boolean asciiArmor) {
        this.asciiArmor = asciiArmor;
        return this;
    }

    public Boolean getDoesRequireEncryptedData() {
        return doesRequireEncryptedData;
    }

    public AwsS3Model setDoesRequireEncryptedData(Boolean doesRequireEncryptedData) {
        this.doesRequireEncryptedData = doesRequireEncryptedData;
        return this;
    }

    public Boolean getDoesRequireSignedData() {
        return doesRequireSignedData;
    }

    public AwsS3Model setDoesRequireSignedData(Boolean doesRequireSignedData) {
        this.doesRequireSignedData = doesRequireSignedData;
        return this;
    }

    public Boolean getTextMode() {
        return textMode;
    }

    public AwsS3Model setTextMode(Boolean textMode) {
        this.textMode = textMode;
        return this;
    }

    public String getEndPointUrl() {
        return endPointUrl;
    }

    public AwsS3Model setEndPointUrl(String endPointUrl) {
        this.endPointUrl = endPointUrl;
        return this;
    }

    public String getFolderName() {
        return folderName;
    }

    public AwsS3Model setFolderName(String folderName) {
        this.folderName = folderName;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AwsS3Model.class.getSimpleName() + "[", "]")
                .add("poolingInterval='" + poolingInterval + "'")
                .add("sourcePath='" + sourcePath + "'")
                .add("fileType='" + fileType + "'")
                .add("bucketName='" + bucketName + "'")
                .add("fileName='" + fileName + "'")
                .add("accessKey='" + accessKey + "'")
                .add("secretKey='" + secretKey + "'")
                .add("endpoint='" + endpoint + "'")
                .add("region='" + region + "'")
                .add("isActive='" + isActive + "'")
                .add("isHubInfo='" + isHubInfo + "'")
                .add("inMailbox='" + inMailbox + "'")
                .add("adapterName='" + adapterName + "'")
                .add("endPointUrl='" + endPointUrl + "'")
                .add("folderName='" + folderName + "'")
                .add("isInitiatingConsumer=" + isInitiatingConsumer)
                .add("isInitiatingProducer=" + isInitiatingProducer)
                .add("isListeningConsumer=" + isListeningConsumer)
                .add("isListeningProducer=" + isListeningProducer)
                .add("inboundConnectionType=" + inboundConnectionType)
                .add("outboundConnectionType=" + outboundConnectionType)
                .add("queueName='" + queueName + "'")
                .add("isSIProfile=" + isSIProfile)
                .add("authenticationType='" + authenticationType + "'")
                .add("authenticationHost='" + authenticationHost + "'")
                .add("profileUserName='" + profileUserName + "'")
                .add("profileUserPassword='" + profileUserPassword + "'")
                .add("doesUseSSH=" + doesUseSSH)
                .add("asciiArmor=" + asciiArmor)
                .add("doesRequireEncryptedData=" + doesRequireEncryptedData)
                .add("doesRequireSignedData=" + doesRequireSignedData)
                .add("textMode=" + textMode)
                .add("producerS3Configuration=" + producerS3Configuration)
                .add("consumerS3Configuration=" + consumerS3Configuration)
                .add("deleteAfterCollection=" + deleteAfterCollection)
                .toString();
    }
}
