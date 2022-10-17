package com.pe.pcm.pem;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pe.pcm.common.CommunityManagerNameModel;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAcctPwdUpdatedModel implements Serializable {

    private List<CommunityManagerNameModel> passwordUpdatedUsers;
    private List<CommunityManagerNameModel> usersNotFound;

    public List<CommunityManagerNameModel> getPasswordUpdatedUsers() {
        return passwordUpdatedUsers;
    }

    public UserAcctPwdUpdatedModel setPasswordUpdatedUsers(List<CommunityManagerNameModel> passwordUpdatedUsers) {
        this.passwordUpdatedUsers = passwordUpdatedUsers;
        return this;
    }

    public List<CommunityManagerNameModel> getUsersNotFound() {
        return usersNotFound;
    }

    public UserAcctPwdUpdatedModel setUsersNotFound(List<CommunityManagerNameModel> usersNotFound) {
        this.usersNotFound = usersNotFound;
        return this;
    }

}
