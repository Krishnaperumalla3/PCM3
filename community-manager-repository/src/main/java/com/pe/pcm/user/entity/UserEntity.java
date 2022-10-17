/*
 *
 *  * Copyright (c) 2020 Pragma Edge Inc
 *  *
 *  * Licensed under the Pragma Edge Inc
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  * https://pragmaedge.com/licenseagreement
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.pe.pcm.user.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Kiran Reddy.
 */
@Entity
@Table(name = "PETPE_SO_USERS")
public class UserEntity implements Serializable {

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
    private String isB2bUser = "Y";

    private String isFaxUser = "N";

    private String onboardingRef;

    private String partnerRef;

    private String activationKey;

    private String otp;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdDate;

    //    @ColumnDefault(value = AuthoritiesConstants.SUPER_ADMIN)
    private String lastUpdatedBy;

    @UpdateTimestamp
    private Timestamp lastUpdatedDt;

    private String groupRef;

    @Transient
    private TpUserEntity tpUserEntity;

    private String lang;

    private String userType;
    private String externalId;
    private String accountNonLocked;

    public String getUserid() {
        return userid;
    }

    public UserEntity setUserid(String userid) {
        this.userid = userid;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getRole() {
        return role;
    }

    public UserEntity setRole(String role) {
        this.role = role;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getMiddleName() {
        return middleName;
    }

    public UserEntity setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public UserEntity setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public UserEntity setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getIsB2bUser() {
        return isB2bUser;
    }

    public UserEntity setIsB2bUser(String isB2bUser) {
        this.isB2bUser = isB2bUser;
        return this;
    }

    public String getIsFaxUser() {
        return isFaxUser;
    }

    public UserEntity setIsFaxUser(String isFaxUser) {
        this.isFaxUser = isFaxUser;
        return this;
    }

    public String getOnboardingRef() {
        return onboardingRef;
    }

    public UserEntity setOnboardingRef(String onboardingRef) {
        this.onboardingRef = onboardingRef;
        return this;
    }

    public String getPartnerRef() {
        return partnerRef;
    }

    public UserEntity setPartnerRef(String partnerRef) {
        this.partnerRef = partnerRef;
        return this;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public UserEntity setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public UserEntity setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
        return this;
    }

    public Timestamp getLastUpdatedDt() {
        return lastUpdatedDt;
    }

    public UserEntity setLastUpdatedDt(Timestamp lastUpdatedDt) {
        this.lastUpdatedDt = lastUpdatedDt;
        return this;
    }

    public String getGroupRef() {
        return groupRef;
    }

    public UserEntity setGroupRef(String groupRef) {
        this.groupRef = groupRef;
        return this;
    }

    public TpUserEntity getTpUserEntity() {
        return tpUserEntity;
    }

    public UserEntity setTpUserEntity(TpUserEntity tpUserEntity) {
        this.tpUserEntity = tpUserEntity;
        return this;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public UserEntity setActivationKey(String activationKey) {
        this.activationKey = activationKey;
        return this;
    }

    public String getOtp() {
        return otp;
    }

    public UserEntity setOtp(String otp) {
        this.otp = otp;
        return this;
    }

    public String getLang() {
        return lang;
    }

    public UserEntity setLang(String lang) {
        this.lang = lang;
        return this;
    }

    public String getUserType() {
        return userType;
    }

    public UserEntity setUserType(String userType) {
        this.userType = userType;
        return this;
    }

    public String getExternalId() {
        return externalId;
    }

    public UserEntity setExternalId(String externalId) {
        this.externalId = externalId;
        return this;
    }

    public String getAccountNonLocked() {
        return accountNonLocked;
    }

    public UserEntity setAccountNonLocked(String accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
        return this;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "userid='" + userid + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", status='" + status + '\'' +
                ", isB2bUser='" + isB2bUser + '\'' +
                ", isFaxUser='" + isFaxUser + '\'' +
                ", onboardingRef='" + onboardingRef + '\'' +
                ", partnerRef='" + partnerRef + '\'' +
                ", activationKey='" + activationKey + '\'' +
                ", otp='" + otp + '\'' +
                ", createdDate=" + createdDate +
                ", lastUpdatedBy='" + lastUpdatedBy + '\'' +
                ", lastUpdatedDt=" + lastUpdatedDt +
                ", groupRef='" + groupRef + '\'' +
                ", tpUserEntity=" + tpUserEntity +
                ", lang='" + lang + '\'' +
                ", userType='" + userType + '\'' +
                ", externalId='" + externalId + '\'' +
                '}';
    }
}
