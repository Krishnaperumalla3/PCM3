package com.pe.pcm.googledrive.entity;

import com.pe.pcm.audit.Auditable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "PETPE_GOOGLE_DRIVE")
public class GoogleDriveEntity extends Auditable implements Serializable {
    @Id
    private String pkId;
    private String clientId;
    private String clientEmail;
    private String projectId;
    private String subscriberType;
    private String subscriberId;
    private String fileType;
    private String inDirectory;
    private String inboundConnectionType;
    private String outDirectory;
    private String outboundConnectionType;
    private String poolingIntervalMins;
    private String deleteAfterCollection;
    private String isActive;

    private String isHubInfo;

    private String credentialDir;
    private String authJson;
    public String getPkId() {
        return pkId;
    }

    public String getCredentialDir() {
        return credentialDir;
    }

    public GoogleDriveEntity setCredentialDir(String credentialDir) {
        this.credentialDir = credentialDir;
        return this;
    }

    public GoogleDriveEntity setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getPoolingIntervalMins() {
        return poolingIntervalMins;
    }

    public GoogleDriveEntity setPoolingIntervalMins(String poolingIntervalMins) {
        this.poolingIntervalMins = poolingIntervalMins;
        return this;
    }

    public String getClientId() {
        return clientId;
    }

    public GoogleDriveEntity setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getProjectId() {
        return projectId;
    }

    public GoogleDriveEntity setProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }
    public String getClientEmail() {
        return clientEmail;
    }

    public GoogleDriveEntity setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
        return this;
    }
    public String getSubscriberType() {
        return subscriberType;
    }

    public GoogleDriveEntity setSubscriberType(String subscriberType) {
        this.subscriberType = subscriberType;
        return this;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public GoogleDriveEntity setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
        return this;
    }

    public String getFileType() {
        return fileType;
    }

    public GoogleDriveEntity setFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public String getInDirectory() {
        return inDirectory;
    }

    public GoogleDriveEntity setInDirectory(String inDirectory) {
        this.inDirectory = inDirectory;
        return this;
    }

    public String getInboundConnectionType() {
        return inboundConnectionType;
    }

    public GoogleDriveEntity setInboundConnectionType(String inboundConnectionType) {
        this.inboundConnectionType = inboundConnectionType;
        return this;
    }

    public String getOutDirectory() {
        return outDirectory;
    }

    public GoogleDriveEntity setOutDirectory(String outDirectory) {
        this.outDirectory = outDirectory;
        return this;
    }

    public String getOutboundConnectionType() {
        return outboundConnectionType;
    }

    public GoogleDriveEntity setOutboundConnectionType(String outboundConnectionType) {
        this.outboundConnectionType = outboundConnectionType;
        return this;
    }



    public String getDeleteAfterCollection() {
        return deleteAfterCollection;
    }

    public GoogleDriveEntity setDeleteAfterCollection(String deleteAfterCollection) {
        this.deleteAfterCollection = deleteAfterCollection;
        return this;
    }

    public String getIsActive() {
        return isActive;
    }

    public GoogleDriveEntity setIsActive(String isActive) {
        this.isActive = isActive;
        return this;
    }

    public String getIsHubInfo() {
        return isHubInfo;
    }

    public GoogleDriveEntity setIsHubInfo(String isHubInfo) {
        this.isHubInfo = isHubInfo;
        return this;
    }

    public String getAuthJson() {
        return authJson;
    }

    public GoogleDriveEntity setAuthJson(String authJson) {
        this.authJson = authJson;
        return this;
    }

    @Override
    public String toString() {
        return "GoogleDriveEntity{" +
                "pkId='" + pkId + '\'' +
                ", clientId='" + clientId + '\'' +
                ", clientEmail='" + clientEmail + '\'' +
                ", projectId='" + projectId + '\'' +
                ", subscriberType='" + subscriberType + '\'' +
                ", subscriberId='" + subscriberId + '\'' +
                ", fileType='" + fileType + '\'' +
                ", inDirectory='" + inDirectory + '\'' +
                ", inboundConnectionType='" + inboundConnectionType + '\'' +
                ", outDirectory='" + outDirectory + '\'' +
                ", outboundConnectionType='" + outboundConnectionType + '\'' +
                ", poolingIntervalMins='" + poolingIntervalMins + '\'' +
                ", deleteAfterCollection='" + deleteAfterCollection + '\'' +
                ", isActive='" + isActive + '\'' +
                ", isHubInfo='" + isHubInfo + '\'' +
                ", credentialDir='" + credentialDir + '\'' +
                ", authJson='" + authJson + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", lastUpdatedBy='" + lastUpdatedBy + '\'' +
                ", lastUpdatedDt=" + lastUpdatedDt +
                '}';
    }
}