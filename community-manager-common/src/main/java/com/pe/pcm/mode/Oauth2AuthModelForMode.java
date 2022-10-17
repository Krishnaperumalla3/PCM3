package com.pe.pcm.mode;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pe.pcm.apiconnect.TokenAuthModel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * @author Shameer.V.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Oauth2AuthModelForMode {

    private String clientID;
    private String clientSecret;
    private String username;
    private String password;
    private String scope;
    private String grantType;

    private String tokenApiUrl;

    private String tokenKey;

    private String tokenPrefix;

    private String tokenHeader;

    public String getClientID() {
        return clientID;
    }

    public Oauth2AuthModelForMode setClientID(String clientID) {
        this.clientID = clientID;
        return this;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public Oauth2AuthModelForMode setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public Oauth2AuthModelForMode setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Oauth2AuthModelForMode setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getScope() {
        return scope;
    }

    public Oauth2AuthModelForMode setScope(String scope) {
        this.scope = scope;
        return this;
    }

    public String getGrantType() {
        return grantType;
    }

    public Oauth2AuthModelForMode setGrantType(String grantType) {
        this.grantType = grantType;
        return this;
    }

    public String getTokenApiUrl() {
        return tokenApiUrl;
    }

    public Oauth2AuthModelForMode setTokenApiUrl(String tokenApiUrl) {
        this.tokenApiUrl = tokenApiUrl;
        return this;
    }

    public String getTokenKey() {
        return tokenKey;
    }

    public Oauth2AuthModelForMode setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
        return this;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public Oauth2AuthModelForMode setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
        return this;
    }

    public String getTokenHeader() {
        return tokenHeader;
    }

    public Oauth2AuthModelForMode setTokenHeader(String tokenHeader) {
        this.tokenHeader = tokenHeader;
        return this;
    }
}
