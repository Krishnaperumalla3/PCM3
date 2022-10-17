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

package com.pe.pcm.login;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pe.pcm.common.CommunityManagerKeyValueModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kiran Reddy.
 */
public class CommunityManagerUserModel {

    private String userId;
    private String token;
    private String email;
    private String userRole;
    private String userName;

    private boolean b2bUser;
    private boolean faxUser;
    private boolean siUser;
    private String faxQueue;
    private String faxQueueAccess;
    private String faxQueueName;

    private String lang;
    private String color;
    private String appVersion;
    private String appCustomName;

    private String dbInfo;
    private boolean sfgEnabled;
    private boolean isCmDeployment;
    private String errorInfo;
    private boolean apiConnect;
    private boolean sfgPcDReports;

    private List<CommunityManagerKeyValueModel> partnerList = new ArrayList<>();

    @JsonIgnore
    private List<String> partnersList = new ArrayList<>();
    @JsonIgnore
    private List<String> groupsList = new ArrayList<>();

    @JsonIgnore
    private Boolean authenticated;
    @JsonIgnore
    private List<String> roles = new ArrayList<>();
    @JsonIgnore
    private String phone;
    @JsonIgnore
    private Boolean validated;
    @JsonIgnore
    private String firstName;
    @JsonIgnore
    private String lastName;
    @JsonIgnore
    private String externalId;
    @JsonIgnore
    private Boolean status;
    @JsonIgnore
    private Boolean accountNonLocked;


    public CommunityManagerUserModel() {
    }

    public CommunityManagerUserModel(String userId, String userRole) {
        this.userId = userId;
        this.userRole = userRole;
    }

    public String getUserId() {
        return userId;
    }

    public CommunityManagerUserModel setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getToken() {
        return token;
    }

    public CommunityManagerUserModel setToken(String token) {
        this.token = token;
        return this;
    }

    public String getUserRole() {
        return userRole;
    }

    public CommunityManagerUserModel setUserRole(String userRole) {
        this.userRole = userRole;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public CommunityManagerUserModel setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public boolean isB2bUser() {
        return b2bUser;
    }

    public CommunityManagerUserModel setB2bUser(boolean b2bUser) {
        this.b2bUser = b2bUser;
        return this;
    }

    public boolean isFaxUser() {
        return faxUser;
    }

    public CommunityManagerUserModel setFaxUser(boolean faxUser) {
        this.faxUser = faxUser;
        return this;
    }

    public boolean isSiUser() {
        return siUser;
    }

    public CommunityManagerUserModel setSiUser(boolean siUser) {
        this.siUser = siUser;
        return this;
    }

    public String getFaxQueue() {
        return faxQueue;
    }

    public CommunityManagerUserModel setFaxQueue(String faxQueue) {
        this.faxQueue = faxQueue;
        return this;
    }

    public String getFaxQueueAccess() {
        return faxQueueAccess;
    }

    public CommunityManagerUserModel setFaxQueueAccess(String faxQueueAccess) {
        this.faxQueueAccess = faxQueueAccess;
        return this;
    }

    public String getFaxQueueName() {
        return faxQueueName;
    }

    public CommunityManagerUserModel setFaxQueueName(String faxQueueName) {
        this.faxQueueName = faxQueueName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public CommunityManagerUserModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public CommunityManagerUserModel setAppVersion(String appVersion) {
        this.appVersion = appVersion;
        return this;
    }

    public List<CommunityManagerKeyValueModel> getPartnerList() {
        return partnerList;
    }

    public CommunityManagerUserModel setPartnerList(List<CommunityManagerKeyValueModel> partnerList) {
        this.partnerList = partnerList;
        return this;
    }


    public String getColor() {
        return color;
    }

    public CommunityManagerUserModel setColor(String color) {
        this.color = color;
        return this;
    }

    public String getAppCustomName() {
        return appCustomName;
    }

    public CommunityManagerUserModel setAppCustomName(String appCustomName) {
        this.appCustomName = appCustomName;
        return this;
    }

    public String getLang() {
        return lang;
    }

    public CommunityManagerUserModel setLang(String lang) {
        this.lang = lang;
        return this;
    }

    public boolean isSfgEnabled() {
        return sfgEnabled;
    }

    public CommunityManagerUserModel setSfgEnabled(boolean sfgEnabled) {
        this.sfgEnabled = sfgEnabled;
        return this;
    }

    public boolean isCmDeployment() {
        return isCmDeployment;
    }

    public CommunityManagerUserModel setCmDeployment(boolean cmDeployment) {
        isCmDeployment = cmDeployment;
        return this;
    }

    public String getDbInfo() {
        return dbInfo;
    }

    public CommunityManagerUserModel setDbInfo(String dbInfo) {
        this.dbInfo = dbInfo;
        return this;
    }

    public Boolean getAuthenticated() {
        return authenticated;
    }

    public CommunityManagerUserModel setAuthenticated(Boolean authenticated) {
        this.authenticated = authenticated;
        return this;
    }

    public List<String> getRoles() {
        return roles;
    }

    public CommunityManagerUserModel setRoles(List<String> roles) {
        this.roles = roles;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public CommunityManagerUserModel setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public Boolean getValidated() {
        return validated;
    }

    public CommunityManagerUserModel setValidated(Boolean validated) {
        this.validated = validated;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public CommunityManagerUserModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public CommunityManagerUserModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getExternalId() {
        return externalId;
    }

    public CommunityManagerUserModel setExternalId(String externalId) {
        this.externalId = externalId;
        return this;
    }

    public Boolean getStatus() {
        return status;
    }

    public CommunityManagerUserModel setStatus(Boolean status) {
        this.status = status;
        return this;
    }

    public List<String> getPartnersList() {
        return partnersList;
    }

    public CommunityManagerUserModel setPartnersList(List<String> partnersList) {
        this.partnersList = partnersList;
        return this;
    }

    public List<String> getGroupsList() {
        return groupsList;
    }

    public CommunityManagerUserModel setGroupsList(List<String> groupsList) {
        this.groupsList = groupsList;
        return this;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public CommunityManagerUserModel setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
        return this;
    }

    public boolean isApiConnect() {
        return apiConnect;
    }

    public CommunityManagerUserModel setApiConnect(boolean apiConnect) {
        this.apiConnect = apiConnect;
        return this;
    }

    public boolean isSfgPcDReports() {
        return sfgPcDReports;
    }

    public CommunityManagerUserModel setSfgPcDReports(boolean sfgPcDReports) {
        this.sfgPcDReports = sfgPcDReports;
        return this;
    }

    public Boolean getAccountNonLocked() {
        return accountNonLocked;
    }

    public CommunityManagerUserModel setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
        return this;
    }

    @Override
    public String toString() {
        return "CommunityManagerUserModel{" +
                "userId='" + userId + '\'' +
                ", token='" + token + '\'' +
                ", email='" + email + '\'' +
                ", userRole='" + userRole + '\'' +
                ", userName='" + userName + '\'' +
                ", b2bUser=" + b2bUser +
                ", faxUser=" + faxUser +
                ", siUser=" + siUser +
                ", faxQueue='" + faxQueue + '\'' +
                ", faxQueueAccess='" + faxQueueAccess + '\'' +
                ", faxQueueName='" + faxQueueName + '\'' +
                ", lang='" + lang + '\'' +
                ", color='" + color + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", appCustomName='" + appCustomName + '\'' +
                ", dbInfo='" + dbInfo + '\'' +
                ", sfgEnabled=" + sfgEnabled +
                ", isCmDeployment=" + isCmDeployment +
                ", errorInfo='" + errorInfo + '\'' +
                ", apiConnect=" + apiConnect +
                ", sfgPcDReports=" + sfgPcDReports +
                ", partnerList=" + partnerList +
                ", partnersList=" + partnersList +
                ", groupsList=" + groupsList +
                ", authenticated=" + authenticated +
                ", roles=" + roles +
                ", phone='" + phone + '\'' +
                ", validated=" + validated +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", externalId='" + externalId + '\'' +
                ", status=" + status +
                ", AccountNonLocked=" + accountNonLocked +
                '}';
    }
}
