package com.pe.pcm.mode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
@JsonIgnoreProperties(ignoreUnknown = true)
public class IdentitySearchModel implements Serializable {

    private Boolean authenticated;
    private String authorities;
    private Object credentials;
    private Object details;
    private Boolean identOnly;
    private Object principal;

    public Boolean getAuthenticated() {
        return authenticated;
    }

    public IdentitySearchModel setAuthenticated(Boolean authenticated) {
        this.authenticated = authenticated;
        return this;
    }

    public String getAuthorities() {
        return authorities;
    }

    public IdentitySearchModel setAuthorities(String authorities) {
        this.authorities = authorities;
        return this;
    }

    public Object getCredentials() {
        return credentials;
    }

    public IdentitySearchModel setCredentials(Object credentials) {
        this.credentials = credentials;
        return this;
    }

    public Object getDetails() {
        return details;
    }

    public IdentitySearchModel setDetails(Object details) {
        this.details = details;
        return this;
    }

    public Boolean getIdentOnly() {
        return identOnly;
    }

    public IdentitySearchModel setIdentOnly(Boolean identOnly) {
        this.identOnly = identOnly;
        return this;
    }

    public Object getPrincipal() {
        return principal;
    }

    public IdentitySearchModel setPrincipal(Object principal) {
        this.principal = principal;
        return this;
    }
}
