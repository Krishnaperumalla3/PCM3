package com.pe.pcm.apiconnect;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;

/**
 * @author Shameer.V.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Oauth2AuthModel implements Serializable {

    private String tokenApiUrl;
    private String clientID;
    private String clientSecret;
    private String username;
    private String password;
    private String scope;
    private String grantType;
    private String resource;
    private String tokenKey;
    private String tokenPrefix;
    private String tokenHeader;

    public String getTokenApiUrl() {
        return tokenApiUrl;
    }

    public Oauth2AuthModel setTokenApiUrl(String tokenApiUrl) {
        this.tokenApiUrl = tokenApiUrl;
        return this;
    }

    public String getClientID() {
        return clientID;
    }

    public Oauth2AuthModel setClientID(String clientID) {
        this.clientID = clientID;
        return this;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public Oauth2AuthModel setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Oauth2AuthModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Oauth2AuthModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getScope() {
        return scope;
    }

    public Oauth2AuthModel setScope(String scope) {
        this.scope = scope;
        return this;
    }

    public String getGrantType() {
        return grantType;
    }

    public Oauth2AuthModel setGrantType(String grantType) {
        this.grantType = grantType;
        return this;
    }

    public String getResource() {
        return resource;
    }

    public Oauth2AuthModel setResource(String resource) {
        this.resource = resource;
        return this;
    }

    public String getTokenKey() {
        return tokenKey;
    }

    public Oauth2AuthModel setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
        return this;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public Oauth2AuthModel setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
        return this;
    }

    public String getTokenHeader() {
        return tokenHeader;
    }

    public Oauth2AuthModel setTokenHeader(String tokenHeader) {
        this.tokenHeader = tokenHeader;
        return this;
    }
}
