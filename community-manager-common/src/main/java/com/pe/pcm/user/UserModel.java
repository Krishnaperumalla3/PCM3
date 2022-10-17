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

package com.pe.pcm.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pe.pcm.annotations.constraint.Required;
import org.springframework.util.StringUtils;

import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserModel implements Serializable {

    @Required(customMessage = "userId")
    private String userId;

    @Required(customMessage = "userRole")
    private String userRole;

    @Required(customMessage = "firstName")
    private String firstName;

    @Required(customMessage = "lastName")
    private String lastName;

    private String middleName;


    @Required(customMessage = "email")
    @Email
    private String email;

    @Required(customMessage = "phone")
    private String phone;

    @Required(customMessage = "status")
    private Boolean status;

    private Boolean b2bUser = true;

    private List<String> partnersList = new ArrayList<>();

    private List<String> groupsList = new ArrayList<>();

    private String lang;

    //    @Required(customMessage = "userType")
    private String userType;

    private String externalId;
    @JsonIgnore
    private String updateBy;
    private boolean isAuth;
    @JsonIgnore
    private Boolean accountNonLocked;

    public String getUserId() {
        return userId;
    }

    public UserModel setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getUserRole() {
        return userRole;
    }

    public UserModel setUserRole(String userRole) {
        this.userRole = userRole;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getMiddleName() {
        return middleName;
    }

    public UserModel setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public UserModel setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public Boolean getStatus() {
        return status;
    }

    public UserModel setStatus(Boolean status) {
        this.status = status;
        return this;
    }

    public Boolean getB2bUser() {
        return b2bUser;
    }

    public UserModel setB2bUser(Boolean b2bUser) {
        this.b2bUser = b2bUser;
        return this;
    }

    public List<String> getPartnersList() {
        return partnersList;
    }

    public UserModel setPartnersList(List<String> partnersList) {
        this.partnersList = partnersList;
        return this;
    }

    public List<String> getGroupsList() {
        return groupsList;
    }

    public UserModel setGroupsList(List<String> groupsList) {
        this.groupsList = groupsList;
        return this;
    }

    public String getLang() {
        return lang;
    }

    public UserModel setLang(String lang) {
        this.lang = lang;
        return this;
    }

    public String getUserType() {
        if (StringUtils.hasText(userType)) {
            return userType.toUpperCase();
        }
        return userType;
    }

    public UserModel setUserType(String userType) {
        this.userType = userType;
        return this;
    }

    public String getExternalId() {
        return externalId;
    }

    public UserModel setExternalId(String externalId) {
        this.externalId = externalId;
        return this;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public UserModel setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
        return this;
    }

    public boolean isAuth() {
        return isAuth;
    }

    public UserModel setAuth(boolean auth) {
        isAuth = auth;
        return this;
    }

    public Boolean getAccountNonLocked() {
        return accountNonLocked;
    }

    public UserModel setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
        return this;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "userId='" + userId + '\'' +
                ", userRole='" + userRole + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", status=" + status +
                ", b2bUser=" + b2bUser +
                ", partnersList=" + partnersList +
                ", groupsList=" + groupsList +
                ", lang='" + lang + '\'' +
                ", userType='" + userType + '\'' +
                ", externalId='" + externalId + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", isAuth=" + isAuth +
                '}';
    }
}
