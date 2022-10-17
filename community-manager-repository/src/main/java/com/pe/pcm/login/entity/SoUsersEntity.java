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

package com.pe.pcm.login.entity;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Chenchu Kiran.
 */
@Entity
@Table(name = "PETPE_SO_USERS")
public class SoUsersEntity implements Serializable {

    @Id
    private String userid;

    @NotNull
    private String password;

    @NotNull
    private String role;

    @NotNull
    private String firstName;

    private String middleName;

    @NotNull
    private String lastName;

    @NotNull
    private String email;

    private String phone;

    @NotNull
    private String status;

    @Column(name = "is_b2b_user")
    private String isB2bUser;

    private String isFaxUser;

    private String onboardingRef;

    @NotNull
    @CreatedDate
    private String createdDate;

    private String lastUpdatedBy;

    private String lastUpdatedDt;

    private String activationKey;

    private String lang;



    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsB2bUser() {
        return isB2bUser;
    }

    public void setIsB2bUser(String isB2bUser) {
        this.isB2bUser = isB2bUser;
    }

    public String getIsFaxUser() {
        return isFaxUser;
    }

    public void setIsFaxUser(String isFaxUser) {
        this.isFaxUser = isFaxUser;
    }

    public String getOnboardingRef() {
        return onboardingRef;
    }

    public void setOnboardingRef(String onboardingRef) {
        this.onboardingRef = onboardingRef;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getLastUpdatedDt() {
        return lastUpdatedDt;
    }

    public void setLastUpdatedDt(String lastUpdatedDt) {
        this.lastUpdatedDt = lastUpdatedDt;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public SoUsersEntity setActivationKey(String activationKey) {
        this.activationKey = activationKey;
        return this;
    }

    public String getLang() {
        return lang;
    }

    public SoUsersEntity setLang(String lang) {
        this.lang = lang;
        return this;
    }

    @Override
    public String toString() {
        return "PetpeSoUsers [userid=" + userid + ", password=" + password + ", role=" + role + ", firstName="
                + firstName + ", middleName=" + middleName + ", lastName=" + lastName + ", email=" + email + ", phone="
                + phone + ", status=" + status + ", isB2bUser=" + isB2bUser + ", isFaxUser=" + isFaxUser
                + ", onboardingRef=" + onboardingRef
                + ", createdDate=" + createdDate + ", lastUpdatedBy=" + lastUpdatedBy + ", lastUpdatedDt="
                + lastUpdatedDt + "]";
    }


}
