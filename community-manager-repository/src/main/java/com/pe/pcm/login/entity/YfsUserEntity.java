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

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Chenchu Kiran.
 */
@Entity
@Table(name = "YFS_USER")
public class YfsUserEntity extends PcmAudit implements Serializable {

	private static final long serialVersionUID = 6127086631263597810L;

	@Id
	private String userKey;

	private String loginid;

	private String password;

	private String isPasswordEncrypted;

	private String username;

	private String businessKey;

	private String usergroupKey;

	private String contactaddressKey;

	private String billingaddressKey;

	private String imagefile;

	private String usertype;

	private String noteKey;

	private String preferenceKey;

	private Timestamp pwdlastchangedon;

	private String activateflag;

	private String longdesc;

	private String localecode;

	private String organizationKey;

	private String parentUserKey;

	private String menuId;

	private String theme;

	private String creatorOrganizationKey;

	private String systemname;

	private String dataSecurityGroupId;

	private String departmentKey;

	private String passwordPolicyId;

	private Integer sessionTimeout;

	private String superUser;

	private String confirmValue;

	private Integer changePassNext;

	private String salt;

	private String pwdPolicyKey;

	public String getUserKey() {
		return userKey;
	}

	public YfsUserEntity setUserKey(String userKey) {
		this.userKey = userKey;
		return this;
	}

	public String getLoginid() {
		return loginid;
	}

	public YfsUserEntity setLoginid(String loginid) {
		this.loginid = loginid;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public YfsUserEntity setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getIsPasswordEncrypted() {
		return isPasswordEncrypted;
	}

	public YfsUserEntity setIsPasswordEncrypted(String isPasswordEncrypted) {
		this.isPasswordEncrypted = isPasswordEncrypted;
		return this;
	}

	public String getUsername() {
		return username;
	}

	public YfsUserEntity setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public YfsUserEntity setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
		return this;
	}

	public String getUsergroupKey() {
		return usergroupKey;
	}

	public YfsUserEntity setUsergroupKey(String usergroupKey) {
		this.usergroupKey = usergroupKey;
		return this;
	}

	public String getContactaddressKey() {
		return contactaddressKey;
	}

	public YfsUserEntity setContactaddressKey(String contactaddressKey) {
		this.contactaddressKey = contactaddressKey;
		return this;
	}

	public String getBillingaddressKey() {
		return billingaddressKey;
	}

	public YfsUserEntity setBillingaddressKey(String billingaddressKey) {
		this.billingaddressKey = billingaddressKey;
		return this;
	}

	public String getImagefile() {
		return imagefile;
	}

	public YfsUserEntity setImagefile(String imagefile) {
		this.imagefile = imagefile;
		return this;
	}

	public String getUsertype() {
		return usertype;
	}

	public YfsUserEntity setUsertype(String usertype) {
		this.usertype = usertype;
		return this;
	}

	public String getNoteKey() {
		return noteKey;
	}

	public YfsUserEntity setNoteKey(String noteKey) {
		this.noteKey = noteKey;
		return this;
	}

	public String getPreferenceKey() {
		return preferenceKey;
	}

	public YfsUserEntity setPreferenceKey(String preferenceKey) {
		this.preferenceKey = preferenceKey;
		return this;
	}

	public Timestamp getPwdlastchangedon() {
		return pwdlastchangedon;
	}

	public YfsUserEntity setPwdlastchangedon(Timestamp pwdlastchangedon) {
		this.pwdlastchangedon = pwdlastchangedon;
		return this;
	}

	public String getActivateflag() {
		return activateflag;
	}

	public YfsUserEntity setActivateflag(String activateflag) {
		this.activateflag = activateflag;
		return this;
	}

	public String getLongdesc() {
		return longdesc;
	}

	public YfsUserEntity setLongdesc(String longdesc) {
		this.longdesc = longdesc;
		return this;
	}

	public String getLocalecode() {
		return localecode;
	}

	public YfsUserEntity setLocalecode(String localecode) {
		this.localecode = localecode;
		return this;
	}

	public String getOrganizationKey() {
		return organizationKey;
	}

	public YfsUserEntity setOrganizationKey(String organizationKey) {
		this.organizationKey = organizationKey;
		return this;
	}

	public String getParentUserKey() {
		return parentUserKey;
	}

	public YfsUserEntity setParentUserKey(String parentUserKey) {
		this.parentUserKey = parentUserKey;
		return this;
	}

	public String getMenuId() {
		return menuId;
	}

	public YfsUserEntity setMenuId(String menuId) {
		this.menuId = menuId;
		return this;
	}

	public String getTheme() {
		return theme;
	}

	public YfsUserEntity setTheme(String theme) {
		this.theme = theme;
		return this;
	}

	public String getCreatorOrganizationKey() {
		return creatorOrganizationKey;
	}

	public YfsUserEntity setCreatorOrganizationKey(String creatorOrganizationKey) {
		this.creatorOrganizationKey = creatorOrganizationKey;
		return this;
	}

	public String getSystemname() {
		return systemname;
	}

	public YfsUserEntity setSystemname(String systemname) {
		this.systemname = systemname;
		return this;
	}

	public String getDataSecurityGroupId() {
		return dataSecurityGroupId;
	}

	public YfsUserEntity setDataSecurityGroupId(String dataSecurityGroupId) {
		this.dataSecurityGroupId = dataSecurityGroupId;
		return this;
	}

	public String getDepartmentKey() {
		return departmentKey;
	}

	public YfsUserEntity setDepartmentKey(String departmentKey) {
		this.departmentKey = departmentKey;
		return this;
	}

	public String getPasswordPolicyId() {
		return passwordPolicyId;
	}

	public YfsUserEntity setPasswordPolicyId(String passwordPolicyId) {
		this.passwordPolicyId = passwordPolicyId;
		return this;
	}

	public Integer getSessionTimeout() {
		return sessionTimeout;
	}

	public YfsUserEntity setSessionTimeout(Integer sessionTimeout) {
		this.sessionTimeout = sessionTimeout;
		return this;
	}

	public String getSuperUser() {
		return superUser;
	}

	public YfsUserEntity setSuperUser(String superUser) {
		this.superUser = superUser;
		return this;
	}

	public String getConfirmValue() {
		return confirmValue;
	}

	public YfsUserEntity setConfirmValue(String confirmValue) {
		this.confirmValue = confirmValue;
		return this;
	}

	public Integer getChangePassNext() {
		return changePassNext;
	}

	public YfsUserEntity setChangePassNext(Integer changePassNext) {
		this.changePassNext = changePassNext;
		return this;
	}

	public String getSalt() {
		return salt;
	}

	public YfsUserEntity setSalt(String salt) {
		this.salt = salt;
		return this;
	}

	public String getPwdPolicyKey() {
		return pwdPolicyKey;
	}

	public YfsUserEntity setPwdPolicyKey(String pwdPolicyKey) {
		this.pwdPolicyKey = pwdPolicyKey;
		return this;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("YfsUserEntity{");
		sb.append("userKey='").append(userKey).append('\'');
		sb.append(", loginid='").append(loginid).append('\'');
		sb.append(", password='").append(password).append('\'');
		sb.append(", isPasswordEncrypted='").append(isPasswordEncrypted).append('\'');
		sb.append(", username='").append(username).append('\'');
		sb.append(", businessKey='").append(businessKey).append('\'');
		sb.append(", usergroupKey='").append(usergroupKey).append('\'');
		sb.append(", contactaddressKey='").append(contactaddressKey).append('\'');
		sb.append(", billingaddressKey='").append(billingaddressKey).append('\'');
		sb.append(", imagefile='").append(imagefile).append('\'');
		sb.append(", usertype='").append(usertype).append('\'');
		sb.append(", noteKey='").append(noteKey).append('\'');
		sb.append(", preferenceKey='").append(preferenceKey).append('\'');
		sb.append(", pwdlastchangedon=").append(pwdlastchangedon);
		sb.append(", activateflag='").append(activateflag).append('\'');
		sb.append(", longdesc='").append(longdesc).append('\'');
		sb.append(", localecode='").append(localecode).append('\'');
		sb.append(", organizationKey='").append(organizationKey).append('\'');
		sb.append(", parentUserKey='").append(parentUserKey).append('\'');
		sb.append(", menuId='").append(menuId).append('\'');
		sb.append(", theme='").append(theme).append('\'');
		sb.append(", creatorOrganizationKey='").append(creatorOrganizationKey).append('\'');
		sb.append(", systemname='").append(systemname).append('\'');
		sb.append(", dataSecurityGroupId='").append(dataSecurityGroupId).append('\'');
		sb.append(", departmentKey='").append(departmentKey).append('\'');
		sb.append(", passwordPolicyId='").append(passwordPolicyId).append('\'');
		sb.append(", sessionTimeout=").append(sessionTimeout);
		sb.append(", superUser='").append(superUser).append('\'');
		sb.append(", confirmValue='").append(confirmValue).append('\'');
		sb.append(", changePassNext=").append(changePassNext);
		sb.append(", salt='").append(salt).append('\'');
		sb.append(", pwdPolicyKey='").append(pwdPolicyKey).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
