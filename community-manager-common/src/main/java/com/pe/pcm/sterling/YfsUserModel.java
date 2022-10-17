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

package com.pe.pcm.sterling;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kiran Reddy.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class YfsUserModel implements Serializable {

    private String userName;
    private String password;
    private String userType;
    private String surname;
    private String givenName;
    @JsonIgnore
    private Timestamp pwdLastChangeDon;
    @JsonIgnore
    private String organizationKey;
    private String parentUserKey;
    private Integer sessionTimeOut;
    @JsonIgnore
    private String pwdPolicyKey;
    private String pwdPolicyId;

    @JsonIgnore
    private String firstName;
    @JsonIgnore
    private String lastName;
    private String emailId;

    private boolean mergeUser;
    private String userIdentity;
    private List<String> groups = new ArrayList<>();
    private List<String> permissions = new ArrayList<>();
    private List<String> authorizedUserKeys = new ArrayList<>();
    private String policy;
    private boolean resetPermissions;
    @JsonIgnore
    private List<String> permissionsToDelete;
    private String authenticationHost;

    private boolean pcmUserLogin;

    public String getUserName() {
        return userName;
    }

    public YfsUserModel setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public YfsUserModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getUserType() {
        return userType;
    }

    public YfsUserModel setUserType(String userType) {
        this.userType = userType;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public YfsUserModel setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getGivenName() {
        return givenName;
    }

    public YfsUserModel setGivenName(String givenName) {
        this.givenName = givenName;
        return this;
    }

    public Timestamp getPwdLastChangeDon() {
        return pwdLastChangeDon;
    }

    public YfsUserModel setPwdLastChangeDon(Timestamp pwdLastChangeDon) {
        this.pwdLastChangeDon = pwdLastChangeDon;
        return this;
    }

    public String getOrganizationKey() {
        return organizationKey;
    }

    public YfsUserModel setOrganizationKey(String organizationKey) {
        this.organizationKey = organizationKey;
        return this;
    }

    public String getParentUserKey() {
        return parentUserKey;
    }

    public YfsUserModel setParentUserKey(String parentUserKey) {
        this.parentUserKey = parentUserKey;
        return this;
    }

    public Integer getSessionTimeOut() {
        return sessionTimeOut;
    }

    public YfsUserModel setSessionTimeOut(Integer sessionTimeOut) {
        this.sessionTimeOut = sessionTimeOut;
        return this;
    }

    public String getPwdPolicyKey() {
        return pwdPolicyKey;
    }

    public YfsUserModel setPwdPolicyKey(String pwdPolicyKey) {
        this.pwdPolicyKey = pwdPolicyKey;
        return this;
    }

    public String getPwdPolicyId() {
        return pwdPolicyId;
    }

    public YfsUserModel setPwdPolicyId(String pwdPolicyId) {
        this.pwdPolicyId = pwdPolicyId;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public YfsUserModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public YfsUserModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmailId() {
        return emailId;
    }

    public YfsUserModel setEmailId(String emailId) {
        this.emailId = emailId;
        return this;
    }

    public boolean isMergeUser() {
        return mergeUser;
    }

    public YfsUserModel setMergeUser(boolean mergeUser) {
        this.mergeUser = mergeUser;
        return this;
    }

    public String getUserIdentity() {
        return userIdentity;
    }

    public YfsUserModel setUserIdentity(String userIdentity) {
        this.userIdentity = userIdentity;
        return this;
    }

    public List<String> getGroups() {
        return groups;
    }

    public YfsUserModel setGroups(List<String> groups) {
        this.groups = groups;
        return this;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public YfsUserModel setPermissions(List<String> permissions) {
        this.permissions = permissions;
        return this;
    }

    public List<String> getAuthorizedUserKeys() {
        return authorizedUserKeys;
    }

    public YfsUserModel setAuthorizedUserKeys(List<String> authorizedUserKeys) {
        this.authorizedUserKeys = authorizedUserKeys;
        return this;
    }

    public String getPolicy() {
        return policy;
    }

    public YfsUserModel setPolicy(String policy) {
        this.policy = policy;
        return this;
    }

    public boolean isResetPermissions() {
        return resetPermissions;
    }

    public YfsUserModel setResetPermissions(boolean resetPermissions) {
        this.resetPermissions = resetPermissions;
        return this;
    }

    public List<String> getPermissionsToDelete() {
        return permissionsToDelete;
    }

    public YfsUserModel setPermissionsToDelete(List<String> permissionsToDelete) {
        this.permissionsToDelete = permissionsToDelete;
        return this;
    }

    public String getAuthenticationHost() {
        return authenticationHost;
    }

    public YfsUserModel setAuthenticationHost(String authenticationHost) {
        this.authenticationHost = authenticationHost;
        return this;
    }

    public boolean isPcmUserLogin() {
        return pcmUserLogin;
    }

    public YfsUserModel setPcmUserLogin(boolean pcmUserLogin) {
        this.pcmUserLogin = pcmUserLogin;
        return this;
    }
}
