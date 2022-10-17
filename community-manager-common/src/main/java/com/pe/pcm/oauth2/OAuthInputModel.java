package com.pe.pcm.oauth2;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author Kiran Reddy.
 */
public class OAuthInputModel implements Serializable {
    @JsonProperty("grant-type")
    private String grantType;
    @JsonProperty("client-id")
    private String clientId;
    @JsonProperty("client-secret")
    private String clientSecret;
    private String scope;
    private String resource;
    private String username;
    private String password;

    public String getGrantType() {
        return grantType;
    }

    public OAuthInputModel setGrantType(String grantType) {
        this.grantType = grantType;
        return this;
    }

    public String getClientId() {
        return clientId;
    }

    public OAuthInputModel setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public OAuthInputModel setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public String getScope() {
        return scope;
    }

    public OAuthInputModel setScope(String scope) {
        this.scope = scope;
        return this;
    }

    public String getResource() {
        return resource;
    }

    public OAuthInputModel setResource(String resource) {
        this.resource = resource;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public OAuthInputModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public OAuthInputModel setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return "OAuthInputModel{" +
                "grantType='" + grantType + '\'' +
                ", clientId='" + clientId + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                ", scope='" + scope + '\'' +
                ", resource='" + resource + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
