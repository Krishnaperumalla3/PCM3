/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc, Version 6.1 (the "License");
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

package com.pe.pcm.b2b.deserialize;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pe.pcm.common.CommunityManagerNameModel;

import java.io.Serializable;
import java.util.List;

/**
 * @author Chenchu Kiran.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RemoteUserDeserializeModel implements Serializable {

    private String userId;
    private String surname;
    private String givenName;
    private String email;
    private String policy;
    private String userIdentity;
    private String sessionTimeout;
    private String authenticationHost;
    private List<CommunityManagerNameModel> groups;
    private List<CommunityManagerNameModel> permissions;
    private List<CommunityManagerNameModel> authorizedUserKeys;
    private B2bCodeAndDisplayModel preferredLanguage;
    private B2bCodeAndDisplayModel authenticationType;

    @JsonIgnore
    private String password;

    public String getUserId() {
        return userId;
    }

    public RemoteUserDeserializeModel setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public RemoteUserDeserializeModel setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getGivenName() {
        return givenName;
    }

    public RemoteUserDeserializeModel setGivenName(String givenName) {
        this.givenName = givenName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public RemoteUserDeserializeModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPolicy() {
        return policy;
    }

    public RemoteUserDeserializeModel setPolicy(String policy) {
        this.policy = policy;
        return this;
    }

    public String getUserIdentity() {
        return userIdentity;
    }

    public RemoteUserDeserializeModel setUserIdentity(String userIdentity) {
        this.userIdentity = userIdentity;
        return this;
    }

    public String getSessionTimeout() {
        return sessionTimeout;
    }

    public RemoteUserDeserializeModel setSessionTimeout(String sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
        return this;
    }

    public String getAuthenticationHost() {
        return authenticationHost;
    }

    public RemoteUserDeserializeModel setAuthenticationHost(String authenticationHost) {
        this.authenticationHost = authenticationHost;
        return this;
    }

    public List<CommunityManagerNameModel> getGroups() {
        return groups;
    }

    public RemoteUserDeserializeModel setGroups(List<CommunityManagerNameModel> groups) {
        this.groups = groups;
        return this;
    }

    public List<CommunityManagerNameModel> getPermissions() {
        return permissions;
    }

    public RemoteUserDeserializeModel setPermissions(List<CommunityManagerNameModel> permissions) {
        this.permissions = permissions;
        return this;
    }

    public List<CommunityManagerNameModel> getAuthorizedUserKeys() {
        return authorizedUserKeys;
    }

    public RemoteUserDeserializeModel setAuthorizedUserKeys(List<CommunityManagerNameModel> authorizedUserKeys) {
        this.authorizedUserKeys = authorizedUserKeys;
        return this;
    }

    public B2bCodeAndDisplayModel getPreferredLanguage() {
        return preferredLanguage;
    }

    public RemoteUserDeserializeModel setPreferredLanguage(B2bCodeAndDisplayModel preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
        return this;
    }

    public B2bCodeAndDisplayModel getAuthenticationType() {
        return authenticationType;
    }

    public RemoteUserDeserializeModel setAuthenticationType(B2bCodeAndDisplayModel authenticationType) {
        this.authenticationType = authenticationType;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RemoteUserDeserializeModel setPassword(String password) {
        this.password = password;
        return this;
    }

}
