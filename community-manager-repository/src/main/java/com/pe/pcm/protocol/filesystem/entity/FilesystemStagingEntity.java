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
package com.pe.pcm.protocol.filesystem.entity;

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
@Table(name = "PETPE_FILESYSTEM_STAGING")
public class FilesystemStagingEntity extends Auditable {
	@Id
	private String pkId;

	@NotNull
	private String subscriberType;

	@NotNull
	private String subscriberId;
	@NotNull
	private String fsaAdapter;
	private String inDirectory;
	private String outDirectory;

	private String fileType;
	@NotNull
	private String deleteAfterCollection;
	@NotNull
	private String poolingIntervalMins;
	@NotNull
	private String isActive;

	@NotNull
	private String isHubInfo;

	private String userName;
	private String password;

	private String fspartnerPriority;

	public String getPkId() {
		return pkId;
	}

	public FilesystemStagingEntity setPkId(String pkId) {
		this.pkId = pkId;
		return this;
	}

	public String getSubscriberType() {
		return subscriberType;
	}

	public FilesystemStagingEntity setSubscriberType(String subscriberType) {
		this.subscriberType = subscriberType;
		return this;
	}

	public String getSubscriberId() {
		return subscriberId;
	}

	public FilesystemStagingEntity setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
		return this;
	}

	public String getFsaAdapter() {
		return fsaAdapter;
	}

	public FilesystemStagingEntity setFsaAdapter(String fsaAdapter) {
		this.fsaAdapter = fsaAdapter;
		return this;
	}

	public String getInDirectory() {
		return inDirectory;
	}

	public FilesystemStagingEntity setInDirectory(String inDirectory) {
		this.inDirectory = inDirectory;
		return this;
	}

	public String getOutDirectory() {
		return outDirectory;
	}

	public FilesystemStagingEntity setOutDirectory(String outDirectory) {
		this.outDirectory = outDirectory;
		return this;
	}

	public String getFileType() {
		return fileType;
	}

	public FilesystemStagingEntity setFileType(String fileType) {
		this.fileType = fileType;
		return this;
	}

	public String getDeleteAfterCollection() {
		return deleteAfterCollection;
	}

	public FilesystemStagingEntity setDeleteAfterCollection(String deleteAfterCollection) {
		this.deleteAfterCollection = deleteAfterCollection;
		return this;
	}

	public String getPoolingIntervalMins() {
		return poolingIntervalMins;
	}

	public FilesystemStagingEntity setPoolingIntervalMins(String poolingIntervalMins) {
		this.poolingIntervalMins = poolingIntervalMins;
		return this;
	}

	public String getIsActive() {
		return isActive;
	}

	public FilesystemStagingEntity setIsActive(String isActive) {
		this.isActive = isActive;
		return this;
	}

	public String getIsHubInfo() {
		return isHubInfo;
	}

	public FilesystemStagingEntity setIsHubInfo(String isHubInfo) {
		this.isHubInfo = isHubInfo;
		return this;
	}

	public String getUserName() {
		return userName;
	}

	public FilesystemStagingEntity setUserName(String userName) {
		this.userName = userName;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public FilesystemStagingEntity setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getFspartnerPriority() {
		return fspartnerPriority;
	}

	public FilesystemStagingEntity setFspartnerPriority(String fspartnerPriority) {
		this.fspartnerPriority = fspartnerPriority;
		return this;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FileSystemEntity [pkId=" + pkId + ", subscriberType=" + subscriberType + ", subscriberId="
				+ subscriberId + ", fsaAdapter=" + fsaAdapter + ", inDirectory=" + inDirectory + ", outDirectory="
				+ outDirectory + ", fileType=" + fileType + ", deleteAfterCollection=" + deleteAfterCollection
				+ ", poolingIntervalMins=" + poolingIntervalMins + ", isActive=" + isActive + ", isHubInfo=" + isHubInfo
				+ ", userName=" + userName + ", password=" + password + ", fspartnerPriority=" + fspartnerPriority
				+ "]";
	}
	
	
}
