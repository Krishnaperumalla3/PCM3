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

/**
 * 
 */
package com.pe.pcm.protocol.ftp.entity;

import com.pe.pcm.audit.Auditable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author PragmaEdge Inc.
 *
 */
@Entity
@Table(name = "PETPE_FTP_STAGING")
public class FtpStagingEntity extends Auditable {

	@Id
	private String pkId;

	@NotNull
	private String subscriberType;

	@NotNull
	private String subscriberId;

	@NotNull
	private String protocolType;

	@NotNull
	private String hostName;

	@NotNull
	private String portNo;

	@NotNull
	private String userId;

	private String password;

	private String inDirectory;

	private String outDirectory;

	private String fileType;

	@NotNull
	private String deleteAfterCollection;

	@NotNull
	private String transferType;

	@NotNull
	private String poolingIntervalMins;

	@NotNull
	private String certificateId;

	private String knownHostKey;

	private String userIdentityKey;

	@NotNull
	private String isHubInfo;

	@NotNull
	private String isActive;

	private String adapterName;

	private String partnerPriority;

	public String getPkId() {
		return pkId;
	}

	public FtpStagingEntity setPkId(String pkId) {
		this.pkId = pkId;
		return this;
	}

	public String getSubscriberType() {
		return subscriberType;
	}

	public FtpStagingEntity setSubscriberType(String subscriberType) {
		this.subscriberType = subscriberType;
		return this;
	}

	public String getSubscriberId() {
		return subscriberId;
	}

	public FtpStagingEntity setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
		return this;
	}

	public String getProtocolType() {
		return protocolType;
	}

	public FtpStagingEntity setProtocolType(String protocolType) {
		this.protocolType = protocolType;
		return this;
	}

	public String getHostName() {
		return hostName;
	}

	public FtpStagingEntity setHostName(String hostName) {
		this.hostName = hostName;
		return this;
	}

	public String getPortNo() {
		return portNo;
	}

	public FtpStagingEntity setPortNo(String portNo) {
		this.portNo = portNo;
		return this;
	}

	public String getUserId() {
		return userId;
	}

	public FtpStagingEntity setUserId(String userId) {
		this.userId = userId;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public FtpStagingEntity setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getInDirectory() {
		return inDirectory;
	}

	public FtpStagingEntity setInDirectory(String inDirectory) {
		this.inDirectory = inDirectory;
		return this;
	}

	public String getOutDirectory() {
		return outDirectory;
	}

	public FtpStagingEntity setOutDirectory(String outDirectory) {
		this.outDirectory = outDirectory;
		return this;
	}

	public String getFileType() {
		return fileType;
	}

	public FtpStagingEntity setFileType(String fileType) {
		this.fileType = fileType;
		return this;
	}

	public String getDeleteAfterCollection() {
		return deleteAfterCollection;
	}

	public FtpStagingEntity setDeleteAfterCollection(String deleteAfterCollection) {
		this.deleteAfterCollection = deleteAfterCollection;
		return this;
	}

	public String getTransferType() {
		return transferType;
	}

	public FtpStagingEntity setTransferType(String transferType) {
		this.transferType = transferType;
		return this;
	}

	public String getPoolingIntervalMins() {
		return poolingIntervalMins;
	}

	public FtpStagingEntity setPoolingIntervalMins(String poolingIntervalMins) {
		this.poolingIntervalMins = poolingIntervalMins;
		return this;
	}

	public String getCertificateId() {
		return certificateId;
	}

	public FtpStagingEntity setCertificateId(String certificateId) {
		this.certificateId = certificateId;
		return this;
	}

	public String getKnownHostKey() {
		return knownHostKey;
	}

	public FtpStagingEntity setKnownHostKey(String knownHostKey) {
		this.knownHostKey = knownHostKey;
		return this;
	}

	public String getUserIdentityKey() {
		return userIdentityKey;
	}

	public FtpStagingEntity setUserIdentityKey(String userIdentityKey) {
		this.userIdentityKey = userIdentityKey;
		return this;
	}

	public String getIsHubInfo() {
		return isHubInfo;
	}

	public FtpStagingEntity setIsHubInfo(String isHubInfo) {
		this.isHubInfo = isHubInfo;
		return this;
	}

	public String getIsActive() {
		return isActive;
	}

	public FtpStagingEntity setIsActive(String isActive) {
		this.isActive = isActive;
		return this;
	}

	public String getAdapterName() {
		return adapterName;
	}

	public FtpStagingEntity setAdapterName(String adapterName) {
		this.adapterName = adapterName;
		return this;
	}

	public String getPartnerPriority() {
		return partnerPriority;
	}

	public FtpStagingEntity setPartnerPriority(String partnerPriority) {
		this.partnerPriority = partnerPriority;
		return this;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FTPEntity [pkId=" + pkId + ", subscriberType=" + subscriberType + ", subscriberId=" + subscriberId
				+ ", protocolType=" + protocolType + ", hostName=" + hostName + ", portNo=" + portNo + ", userId="
				+ userId + ", password=" + password + ", inDirectory=" + inDirectory + ", outDirectory=" + outDirectory
				+ ", fileType=" + fileType + ", deleteAfterCollection=" + deleteAfterCollection + ", transferType="
				+ transferType + ", poolingIntervalMins=" + poolingIntervalMins + ", certificateId=" + certificateId
				+ ", knownHostKey=" + knownHostKey + ", userIdentityKey=" + userIdentityKey + ", isHubInfo=" + isHubInfo
				+ ", isActive=" + isActive + ", adapterName=" + adapterName + ", partnerPriority=" + partnerPriority
				+ "]";
	}
}
