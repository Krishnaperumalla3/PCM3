package com.pe.pcm.mode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IdentityExtensions implements Serializable {

    private Integer clientId;
    private Integer id;
    private IdentityField identityField;
    private String key;
    private String value;

    public Integer getClientId() {
        return clientId;
    }

    public IdentityExtensions setClientId(Integer clientId) {
        this.clientId = clientId;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public IdentityExtensions setId(Integer id) {
        this.id = id;
        return this;
    }

    public IdentityField getIdentityField() {
        return identityField;
    }

    public IdentityExtensions setIdentityField(IdentityField identityField) {
        this.identityField = identityField;
        return this;
    }

    public String getKey() {
        return key;
    }

    public IdentityExtensions setKey(String key) {
        this.key = key;
        return this;
    }

    public String getValue() {
        return value;
    }

    public IdentityExtensions setValue(String value) {
        this.value = value;
        return this;
    }
}
