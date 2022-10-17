package com.pe.pcm.b2b.usermailbox;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pe.pcm.common.CommunityManagerNameModel;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RemoteUserInfoModel implements Serializable {

    private String userName;
    private String password;
    private String inDirectory;
    private String outDirectory;
    private Boolean createUserInSI;
    private Boolean createDirectoryInSI;
    private String userIdentity;
    private List<CommunityManagerNameModel> groups;
    private List<CommunityManagerNameModel> authorizedUserKeys;
    private String preferredAuthenticationType;
    private String protocol;
    private String policy;
    private String sessionTimeout;
    private String email;
    private String surname;
    private String givenName;
    private String authenticationType;
    private String authenticationHost;

    public String getUserName() {
        return userName;
    }

    public RemoteUserInfoModel setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RemoteUserInfoModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getInDirectory() {
        return inDirectory;
    }

    public RemoteUserInfoModel setInDirectory(String inDirectory) {
        this.inDirectory = inDirectory;
        return this;
    }

    public String getOutDirectory() {
        return outDirectory;
    }

    public RemoteUserInfoModel setOutDirectory(String outDirectory) {
        this.outDirectory = outDirectory;
        return this;
    }

    public Boolean getCreateUserInSI() {
        return createUserInSI;
    }

    public RemoteUserInfoModel setCreateUserInSI(Boolean createUserInSI) {
        this.createUserInSI = createUserInSI;
        return this;
    }

    public Boolean getCreateDirectoryInSI() {
        return createDirectoryInSI;
    }

    public RemoteUserInfoModel setCreateDirectoryInSI(Boolean createDirectoryInSI) {
        this.createDirectoryInSI = createDirectoryInSI;
        return this;
    }

    public String getUserIdentity() {
        return userIdentity;
    }

    public RemoteUserInfoModel setUserIdentity(String userIdentity) {
        this.userIdentity = userIdentity;
        return this;
    }

    public List<CommunityManagerNameModel> getGroups() {
        return groups;
    }

    public RemoteUserInfoModel setGroups(List<CommunityManagerNameModel> groups) {
        this.groups = groups;
        return this;
    }

    public List<CommunityManagerNameModel> getAuthorizedUserKeys() {
        return authorizedUserKeys;
    }

    public RemoteUserInfoModel setAuthorizedUserKeys(List<CommunityManagerNameModel> authorizedUserKeys) {
        this.authorizedUserKeys = authorizedUserKeys;
        return this;
    }

    public String getPreferredAuthenticationType() {
        return preferredAuthenticationType;
    }

    public RemoteUserInfoModel setPreferredAuthenticationType(String preferredAuthenticationType) {
        this.preferredAuthenticationType = preferredAuthenticationType;
        return this;
    }

    public String getProtocol() {
        return protocol;
    }

    public RemoteUserInfoModel setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public String getPolicy() {
        return policy;
    }

    public RemoteUserInfoModel setPolicy(String policy) {
        this.policy = policy;
        return this;
    }

    public String getSessionTimeout() {
        return sessionTimeout;
    }

    public RemoteUserInfoModel setSessionTimeout(String sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public RemoteUserInfoModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public RemoteUserInfoModel setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getGivenName() {
        return givenName;
    }

    public RemoteUserInfoModel setGivenName(String givenName) {
        this.givenName = givenName;
        return this;
    }

    public String getAuthenticationType() {
        return authenticationType;
    }

    public void setAuthenticationType(String authenticationType) {
        this.authenticationType = authenticationType;
    }

    public String getAuthenticationHost() {
        return authenticationHost;
    }

    public void setAuthenticationHost(String authenticationHost) {
        this.authenticationHost = authenticationHost;
    }
}
