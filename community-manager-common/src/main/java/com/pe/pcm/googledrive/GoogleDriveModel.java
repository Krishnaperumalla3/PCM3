package com.pe.pcm.googledrive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.pe.pcm.profile.ProfileModel;


@JacksonXmlRootElement(localName = "PARTNER_HTTP")
@JsonPropertyOrder({"pkId", "profileName", "profileId", "emailId", "phone",
        "protocol", "addressLine1", "addressLine2", "status", "inMailBox",
        "outBoundUrl", "poolingInterval", "adapterName", "hubInfo", "certificate"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleDriveModel extends ProfileModel {

    private String clientId;
    private String projectId;
    private String clientEmail;
    private String inDirectory;
    private String outDirectory;
    private String fileType;
    private Boolean deleteAfterCollection;
    private String poolingInterval;
    private String fileInput;

    @JacksonXmlProperty(localName = "CLIENT_ID")
    public String getClientId() {
        return clientId;
    }

    @JacksonXmlProperty(localName = "POOLING_INTERVAL")
    public String getPoolingInterval() {
        return poolingInterval;
    }

    public GoogleDriveModel setPoolingInterval(String poolingInterval) {
        this.poolingInterval = poolingInterval;
        return this;
    }

    public GoogleDriveModel setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    @JacksonXmlProperty(localName = "PROJECT_ID")
    public String getProjectId() {
        return projectId;
    }

    public GoogleDriveModel setProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }
    @JacksonXmlProperty(localName = "CLIENT_EMAIL")
    public String getClientEmail() {
        return clientEmail;
    }

    public GoogleDriveModel setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
        return this;
    }
    @JacksonXmlProperty(localName = "IN_DIRECTORY")
    public String getInDirectory() {
        return inDirectory;
    }

    public GoogleDriveModel setInDirectory(String inDirectory) {
        this.inDirectory = inDirectory;
        return this;
    }
    @JacksonXmlProperty(localName = "OUT_DIRECTORY")
    public String getOutDirectory() {
        return outDirectory;
    }

    public GoogleDriveModel setOutDirectory(String outDirectory) {
        this.outDirectory = outDirectory;
        return this;
    }
    @JacksonXmlProperty(localName = "FILE_TYPE")
    public String getFileType() {
        return fileType;
    }

    public GoogleDriveModel setFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }


    @JacksonXmlProperty(localName = "DELETION_AFTER_COLLECTION")
    public Boolean getDeleteAfterCollection() {
        return deleteAfterCollection;
    }

    public GoogleDriveModel setDeleteAfterCollection(Boolean deleteAfterCollection) {
        this.deleteAfterCollection = deleteAfterCollection;
        return this;
    }

    public String getFileInput() {
        return fileInput;
    }

    public void setFileInput(String fileInput) {
        this.fileInput = fileInput;
    }

}


