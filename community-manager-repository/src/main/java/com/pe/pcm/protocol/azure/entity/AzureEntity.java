package com.pe.pcm.protocol.azure.entity;

import com.pe.pcm.audit.Auditable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Kiran Reddy.
 */
@Entity
@Table(name = "PETPE_AZURE")
public class AzureEntity extends Auditable {

    @Id
    private String pkId;

    @NotNull
    private String subscriberType;

    @NotNull
    @Column(unique = true)
    private String subscriberId;

    @NotNull
    private String isActive;

    @NotNull
    private String isHubInfo;

    private String authType;
    private String accountName;
    private String accountKey;
    private String endpointSuffix;
    private String endpoint;
    private String bucketName;
    private String folderName;
    private String adapterName;
    private String fileName;
    private String inMailbox;
    private String inboundPush;
    private String outboundPull;
    private String deleteAfterCollection;

    @NotNull
    private String poolingIntervalMins;


    public String getPkId() {
        return pkId;
    }

    public AzureEntity setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getSubscriberType() {
        return subscriberType;
    }

    public AzureEntity setSubscriberType(String subscriberType) {
        this.subscriberType = subscriberType;
        return this;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public AzureEntity setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
        return this;
    }

    public String getIsActive() {
        return isActive;
    }

    public AzureEntity setIsActive(String isActive) {
        this.isActive = isActive;
        return this;
    }

    public String getIsHubInfo() {
        return isHubInfo;
    }

    public AzureEntity setIsHubInfo(String isHubInfo) {
        this.isHubInfo = isHubInfo;
        return this;
    }

    public String getAuthType() {
        return authType;
    }

    public AzureEntity setAuthType(String authType) {
        this.authType = authType;
        return this;
    }

    public String getAccountName() {
        return accountName;
    }

    public AzureEntity setAccountName(String accountName) {
        this.accountName = accountName;
        return this;
    }

    public String getAccountKey() {
        return accountKey;
    }

    public AzureEntity setAccountKey(String accountKey) {
        this.accountKey = accountKey;
        return this;
    }

    public String getEndpointSuffix() {
        return endpointSuffix;
    }

    public AzureEntity setEndpointSuffix(String endpointSuffix) {
        this.endpointSuffix = endpointSuffix;
        return this;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public AzureEntity setEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public String getBucketName() {
        return bucketName;
    }

    public AzureEntity setBucketName(String bucketName) {
        this.bucketName = bucketName;
        return this;
    }

    public String getFolderName() {
        return folderName;
    }

    public AzureEntity setFolderName(String folderName) {
        this.folderName = folderName;
        return this;
    }

    public String getAdapterName() {
        return adapterName;
    }

    public AzureEntity setAdapterName(String adapterName) {
        this.adapterName = adapterName;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public AzureEntity setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getInMailbox() {
        return inMailbox;
    }

    public AzureEntity setInMailbox(String inMailbox) {
        this.inMailbox = inMailbox;
        return this;
    }

    public String getInboundPush() {
        return inboundPush;
    }

    public AzureEntity setInboundPush(String inboundPush) {
        this.inboundPush = inboundPush;
        return this;
    }

    public String getOutboundPull() {
        return outboundPull;
    }

    public AzureEntity setOutboundPull(String outboundPull) {
        this.outboundPull = outboundPull;
        return this;
    }

    public String getPoolingIntervalMins() {
        return poolingIntervalMins;
    }

    public AzureEntity setPoolingIntervalMins(String poolingIntervalMins) {
        this.poolingIntervalMins = poolingIntervalMins;
        return this;
    }

    public String getDeleteAfterCollection() {
        return deleteAfterCollection;
    }

    public AzureEntity setDeleteAfterCollection(String deleteAfterCollection) {
        this.deleteAfterCollection = deleteAfterCollection;
        return this;
    }
}
