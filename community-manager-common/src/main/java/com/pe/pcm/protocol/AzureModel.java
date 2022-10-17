package com.pe.pcm.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.pe.pcm.annotations.constraint.Required;
import com.pe.pcm.profile.ProfileModel;

import java.io.Serializable;

/**
 * @author Kiran Reddy.
 */
@JsonPropertyOrder({"pkId", "profileName", "profileId", "emailId", "phone",
        "protocol", "addressLine1", "addressLine2", "status",
        "authType", "accountName", "accountKey", "endpointSuffix", "endpoint",
        "deleteAfterCollection", "poolingInterval", "inMailbox", "inboundPush", "outboundPull"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class AzureModel extends ProfileModel implements Serializable {

    @Required(customMessage = "authType")
    private String authType;
    @Required(customMessage = "accountName")
    private String accountName;
    private String accountKey;
    @Required(customMessage = "endpointSuffix")
    private String endpointSuffix;
    private String endpoint;
    private Boolean deleteAfterCollection;
    @Required(customMessage = "poolingInterval")
    private String poolingInterval;
    @Required(customMessage = "inMailbox")
    private String inMailbox;
    private String bucketName;
    private String folderName;
    private String adapterName;
    private String fileName;

    private Boolean inboundPush;
    private Boolean outboundPull;

    @JacksonXmlProperty(localName = "AUTH_TYPE")
    public String getAuthType() {
        return authType;
    }

    public AzureModel setAuthType(String authType) {
        this.authType = authType;
        return this;
    }

    @JacksonXmlProperty(localName = "ACCOUNT_NAME")
    public String getAccountName() {
        return accountName;
    }

    public AzureModel setAccountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    @JacksonXmlProperty(localName = "ACCOUNT_KEY")
    public String getAccountKey() {
        return accountKey;
    }

    public AzureModel setAccountKey(String accountKey) {
        this.accountKey = accountKey;
        return this;
    }

    @JacksonXmlProperty(localName = "ENDPOINT_SUFFIX")
    public String getEndpointSuffix() {
        return endpointSuffix;
    }

    public AzureModel setEndpointSuffix(String endpointSuffix) {
        this.endpointSuffix = endpointSuffix;
        return this;
    }

    @JacksonXmlProperty(localName = "ENDPOINT")
    public String getEndpoint() {
        return endpoint;
    }

    public AzureModel setEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    @JacksonXmlProperty(localName = "DELETE_AFTER_COLLECTION")
    public Boolean getDeleteAfterCollection() {
        return deleteAfterCollection;
    }

    public AzureModel setDeleteAfterCollection(Boolean deleteAfterCollection) {
        this.deleteAfterCollection = deleteAfterCollection;
        return this;
    }

    @JacksonXmlProperty(localName = "POOLING_INTERVAL")
    public String getPoolingInterval() {
        return poolingInterval;
    }

    public AzureModel setPoolingInterval(String poolingInterval) {
        this.poolingInterval = poolingInterval;
        return this;
    }

    @JacksonXmlProperty(localName = "IN_MAILBOX")
    public String getInMailbox() {
        return inMailbox;
    }

    public AzureModel setInMailbox(String inMailbox) {
        this.inMailbox = inMailbox;
        return this;
    }

    @JacksonXmlProperty(localName = "INBOUND_PUSH")
    public Boolean getInboundPush() {
        return inboundPush;
    }

    public AzureModel setInboundPush(Boolean inboundPush) {
        this.inboundPush = inboundPush;
        return this;
    }

    @JacksonXmlProperty(localName = "OUTBOUND_PULL")
    public Boolean getOutboundPull() {
        return outboundPull;
    }

    public AzureModel setOutboundPull(Boolean outboundPull) {
        this.outboundPull = outboundPull;
        return this;
    }

    @JacksonXmlProperty(localName = "BUCKET_NAME")
    public String getBucketName() {
        return bucketName;
    }

    public AzureModel setBucketName(String bucketName) {
        this.bucketName = bucketName;
        return this;
    }

    @JacksonXmlProperty(localName = "FOLDER_NAME")
    public String getFolderName() {
        return folderName;
    }

    public AzureModel setFolderName(String folderName) {
        this.folderName = folderName;
        return this;
    }

    @JacksonXmlProperty(localName = "ADAPTER_NAME")
    public String getAdapterName() {
        return adapterName;
    }

    public AzureModel setAdapterName(String adapterName) {
        this.adapterName = adapterName;
        return this;
    }

    @JacksonXmlProperty(localName = "FILE_NAME")
    public String getFileName() {
        return fileName;
    }

    public AzureModel setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }
}
