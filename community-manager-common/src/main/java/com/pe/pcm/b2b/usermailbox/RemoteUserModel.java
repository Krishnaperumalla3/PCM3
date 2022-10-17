package com.pe.pcm.b2b.usermailbox;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pe.pcm.common.CommunityManagerNameModel;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RemoteUserModel {

    private String authenticationHost;
    private String authenticationType;
    private String email;
    private String givenName;
    private List<CommunityManagerNameModel> groups;
    private List<CommunityManagerNameModel> authorizedUserKeys;
    private String managerId;
    private String pager;
    private String password;
    private List<CommunityManagerNameModel> permissions;
    private String policy;
    private String preferredLanguage;
    private String sessionTimeout;
    private String surname;
    private String userId;
    private String userIdentity;

    public String getAuthenticationHost() {
        return authenticationHost;
    }

    public RemoteUserModel setAuthenticationHost(String authenticationHost) {
        this.authenticationHost = authenticationHost;
        return this;
    }

    public String getAuthenticationType() {
        return authenticationType;
    }

    public RemoteUserModel setAuthenticationType(String authenticationType) {
        this.authenticationType = authenticationType;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public RemoteUserModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getGivenName() {
        return givenName;
    }

    public RemoteUserModel setGivenName(String givenName) {
        this.givenName = givenName;
        return this;
    }

    public List<CommunityManagerNameModel> getGroups() {
        return groups;
    }

    public RemoteUserModel setGroups(List<CommunityManagerNameModel> groups) {
        this.groups = groups;
        return this;
    }

    public List<CommunityManagerNameModel> getAuthorizedUserKeys() {
        return authorizedUserKeys;
    }

    public RemoteUserModel setAuthorizedUserKeys(List<CommunityManagerNameModel> authorizedUserKeys) {
        this.authorizedUserKeys = authorizedUserKeys;
        return this;
    }

    public String getManagerId() {
        return managerId;
    }

    public RemoteUserModel setManagerId(String managerId) {
        this.managerId = managerId;
        return this;
    }

    public String getPager() {
        return pager;
    }

    public RemoteUserModel setPager(String pager) {
        this.pager = pager;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RemoteUserModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public List<CommunityManagerNameModel> getPermissions() {
        return permissions;
    }

    public RemoteUserModel setPermissions(List<CommunityManagerNameModel> permissions) {
        this.permissions = permissions;
        return this;
    }

    public String getPolicy() {
        return policy;
    }

    public RemoteUserModel setPolicy(String policy) {
        this.policy = policy;
        return this;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public RemoteUserModel setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
        return this;
    }

    public String getSessionTimeout() {
        return sessionTimeout;
    }

    public RemoteUserModel setSessionTimeout(String sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public RemoteUserModel setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public RemoteUserModel setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getUserIdentity() {
        return userIdentity;
    }

    public RemoteUserModel setUserIdentity(String userIdentity) {
        this.userIdentity = userIdentity;
        return this;
    }

    @Override
    public String toString() {
        return "RemoteUserModel{" +
                "authenticationHost='" + authenticationHost + '\'' +
                ", authenticationType='" + authenticationType + '\'' +
                ", email='" + email + '\'' +
                ", givenName='" + givenName + '\'' +
                ", groups=" + groups +
                ", authorizedUserKeys=" + authorizedUserKeys +
                ", managerId='" + managerId + '\'' +
                ", pager='" + pager + '\'' +
                ", password='" + password + '\'' +
                ", permissions=" + permissions +
                ", policy='" + policy + '\'' +
                ", preferredLanguage='" + preferredLanguage + '\'' +
                ", sessionTimeout='" + sessionTimeout + '\'' +
                ", surname='" + surname + '\'' +
                ", userId='" + userId + '\'' +
                ", userIdentity='" + userIdentity + '\'' +
                '}';
    }
}
