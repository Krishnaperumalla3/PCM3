package com.pe.pcm.mode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IdentityModel implements Serializable {

    private String activeFlag;
    private String b2bIdentifier;
    private Integer clientId;
    private Integer id;
    private List<IdentityExtensions> identityExtensions;
    private String notes;
    private String orgName;
    private String parentIdentifier;

    public String getActiveFlag() {
        return activeFlag;
    }

    public IdentityModel setActiveFlag(String activeFlag) {
        this.activeFlag = activeFlag;
        return this;
    }

    public String getB2bIdentifier() {
        return b2bIdentifier;
    }

    public IdentityModel setB2bIdentifier(String b2bIdentifier) {
        this.b2bIdentifier = b2bIdentifier;
        return this;
    }

    public Integer getClientId() {
        return clientId;
    }

    public IdentityModel setClientId(Integer clientId) {
        this.clientId = clientId;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public IdentityModel setId(Integer id) {
        this.id = id;
        return this;
    }

    public List<IdentityExtensions> getIdentityExtensions() {
        return identityExtensions;
    }

    public IdentityModel setIdentityExtensions(List<IdentityExtensions> identityExtensions) {
        this.identityExtensions = identityExtensions;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public IdentityModel setNotes(String notes) {
        this.notes = notes;
        return this;
    }

    public String getOrgName() {
        return orgName;
    }

    public IdentityModel setOrgName(String orgName) {
        this.orgName = orgName;
        return this;
    }

    public String getParentIdentifier() {
        return parentIdentifier;
    }

    public IdentityModel setParentIdentifier(String parentIdentifier) {
        this.parentIdentifier = parentIdentifier;
        return this;
    }
}
