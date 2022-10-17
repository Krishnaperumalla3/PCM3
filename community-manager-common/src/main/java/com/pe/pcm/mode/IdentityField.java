package com.pe.pcm.mode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
@JsonIgnoreProperties(ignoreUnknown = true)
public class IdentityField implements Serializable {

    private Integer clientId;
    private String  description;
    private Integer displayOrder;
    private Integer id;
    private String  key;
    private Boolean required;

    public Integer getClientId() {
        return clientId;
    }

    public IdentityField setClientId(Integer clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public IdentityField setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public IdentityField setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public IdentityField setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getKey() {
        return key;
    }

    public IdentityField setKey(String key) {
        this.key = key;
        return this;
    }

    public Boolean getRequired() {
        return required;
    }

    public IdentityField setRequired(Boolean required) {
        this.required = required;
        return this;
    }
}
