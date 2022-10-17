package com.pe.pcm.apiconnect;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenAuthModel implements Serializable {
    private String username;
    private String password;
    private String tokenApiUrl;
    private String tokenKey;
    private String tokenPrefix;
    private String tokenHeader;

    public String getTokenApiUrl() {
        return tokenApiUrl;
    }

    public TokenAuthModel setTokenApiUrl(String tokenApiUrl) {
        this.tokenApiUrl = tokenApiUrl;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public TokenAuthModel setUsername(String userName) {
        this.username = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public TokenAuthModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getTokenKey() {
        return tokenKey;
    }

    public TokenAuthModel setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
        return this;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public TokenAuthModel setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
        return this;
    }

    public String getTokenHeader() {
        return tokenHeader;
    }

    public TokenAuthModel setTokenHeader(String tokenHeader) {
        this.tokenHeader = tokenHeader;
        return this;
    }
}
