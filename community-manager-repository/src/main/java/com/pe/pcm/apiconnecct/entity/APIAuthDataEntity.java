/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://pragmaedge.com/licenseagreement
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pe.pcm.apiconnecct.entity;

import com.pe.pcm.audit.Auditable;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Kiran Reddy.
 */
@Entity
@Table(name = "PETPE_API_AUTH_DATA")
public class APIAuthDataEntity extends Auditable implements Serializable {
    @Id
    private String pkId;
    private String apiId;
    private String apiConfigType;
    private String authType;
    private String username;
    private String password;
    private String tokenApiUrl;
    private String tokenKey;
    private String tokenPrefix;
    private String tokenHeader;

    private String clientId;

    private String clientSecret;

    private String grantType;

    private String resourceOauth;

    private String scopeOauth;

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "api_id", insertable = false, updatable = false)
    private APIProxyEndpointEntity apiProxyEndpointEntity;*/

    public String getClientId() {
        return clientId;
    }

    public APIAuthDataEntity setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public APIAuthDataEntity setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public String getGrantType() {
        return grantType;
    }

    public APIAuthDataEntity setGrantType(String grantType) {
        this.grantType = grantType;
        return this;
    }


    public String getResourceOauth() {
        return resourceOauth;
    }

    public APIAuthDataEntity setResourceOauth(String resourceOauth) {
        this.resourceOauth = resourceOauth;
        return this;
    }

    public String getScopeOauth() {
        return scopeOauth;
    }

    public APIAuthDataEntity setScopeOauth(String scopeOauth) {
        this.scopeOauth = scopeOauth;
        return this;
    }

    public String getPkId() {
        return pkId;
    }

    public APIAuthDataEntity setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getApiId() {
        return apiId;
    }

    public APIAuthDataEntity setApiId(String apiId) {
        this.apiId = apiId;
        return this;
    }

    public String getApiConfigType() {
        return apiConfigType;
    }

    public APIAuthDataEntity setApiConfigType(String apiConfigType) {
        this.apiConfigType = apiConfigType;
        return this;
    }

    public String getAuthType() {
        return authType;
    }

    public APIAuthDataEntity setAuthType(String authType) {
        this.authType = authType;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public APIAuthDataEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public APIAuthDataEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getTokenApiUrl() {
        return tokenApiUrl;
    }

    public APIAuthDataEntity setTokenApiUrl(String tokenApiUrl) {
        this.tokenApiUrl = tokenApiUrl;
        return this;
    }

    public String getTokenKey() {
        return tokenKey;
    }

    public APIAuthDataEntity setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
        return this;
    }



    public String getClientSecret() {
        return clientSecret;
    }

    /* public APIProxyEndpointEntity getApiProxyEndpointEntity() {
        return apiProxyEndpointEntity;
    }

    public APIAuthDataEntity setApiProxyEndpointEntity(APIProxyEndpointEntity apiProxyEndpointEntity) {
        this.apiProxyEndpointEntity = apiProxyEndpointEntity;
        return this;
    }*/

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public APIAuthDataEntity setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
        return this;
    }

    public String getTokenHeader() {
        return tokenHeader;
    }

    public APIAuthDataEntity setTokenHeader(String tokenHeader) {
        this.tokenHeader = tokenHeader;
        return this;
    }

    @Override
    public String toString() {
        return "APIAuthDataEntity{" +
                "pkId='" + pkId + '\'' +
                ", apiId='" + apiId + '\'' +
                ", apiConfigType='" + apiConfigType + '\'' +
                ", authType='" + authType + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", tokenApiUrl='" + tokenApiUrl + '\'' +
                ", tokenKey='" + tokenKey + '\'' +
                '}';
    }
}
