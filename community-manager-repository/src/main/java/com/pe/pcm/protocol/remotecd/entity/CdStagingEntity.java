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
package com.pe.pcm.protocol.remotecd.entity;

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
@Table(name = "PETPE_CD_STAGING")
public class CdStagingEntity extends Auditable {

	@Id
	private String pkId;

	@NotNull
	private String subscriberType;

	@NotNull
	private String subscriberId;

	private String lnodeName;

	private String rnodeName;

	private String remoteFileName;

	private String remoteUserId;

	private String remotePassword;

	@NotNull
	private String isActive;

	@NotNull
	private String isHubInfo;

	private String adapterName;

	private String inDirectory;

	private String poolingIntervalMins;

	public String getPkId() {
		return pkId;
	}

	public CdStagingEntity setPkId(String pkId) {
		this.pkId = pkId;
		return this;
	}

	public String getSubscriberType() {
		return subscriberType;
	}

	public CdStagingEntity setSubscriberType(String subscriberType) {
		this.subscriberType = subscriberType;
		return this;
	}

	public String getSubscriberId() {
		return subscriberId;
	}

	public CdStagingEntity setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
		return this;
	}

	public String getLnodeName() {
		return lnodeName;
	}

	public CdStagingEntity setLnodeName(String lnodeName) {
		this.lnodeName = lnodeName;
		return this;
	}

	public String getRnodeName() {
		return rnodeName;
	}

	public CdStagingEntity setRnodeName(String rnodeName) {
		this.rnodeName = rnodeName;
		return this;
	}

	public String getRemoteFileName() {
		return remoteFileName;
	}

	public CdStagingEntity setRemoteFileName(String remoteFileName) {
		this.remoteFileName = remoteFileName;
		return this;
	}

	public String getRemoteUserId() {
		return remoteUserId;
	}

	public CdStagingEntity setRemoteUserId(String remoteUserId) {
		this.remoteUserId = remoteUserId;
		return this;
	}

	public String getRemotePassword() {
		return remotePassword;
	}

	public CdStagingEntity setRemotePassword(String remotePassword) {
		this.remotePassword = remotePassword;
		return this;
	}

	public String getIsActive() {
		return isActive;
	}

	public CdStagingEntity setIsActive(String isActive) {
		this.isActive = isActive;
		return this;
	}

	public String getIsHubInfo() {
		return isHubInfo;
	}

	public CdStagingEntity setIsHubInfo(String isHubInfo) {
		this.isHubInfo = isHubInfo;
		return this;
	}

	public String getAdapterName() {
		return adapterName;
	}

	public CdStagingEntity setAdapterName(String adapterName) {
		this.adapterName = adapterName;
		return this;
	}

	public String getInDirectory() {
		return inDirectory;
	}

	public CdStagingEntity setInDirectory(String inDirectory) {
		this.inDirectory = inDirectory;
		return this;
	}

	public String getPoolingIntervalMins() {
		return poolingIntervalMins;
	}

	public CdStagingEntity setPoolingIntervalMins(String poolingIntervalMins) {
		this.poolingIntervalMins = poolingIntervalMins;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RemoteConnectDirectEntity [pkId=" + pkId + ", subscriberType=" + subscriberType + ", subscriberId=" + subscriberId
				+ ", lnodeName=" + lnodeName + ", rnodeName=" + rnodeName + ", remoteFileName=" + remoteFileName
				+ ", remoteUserId=" + remoteUserId + ", remotePassword=" + remotePassword + ", isActive=" + isActive
				+ ", isHubInfo=" + isHubInfo + ", adapterName=" + adapterName + ", inDirectory=" + inDirectory
				+ ", poolingIntervalMins=" + poolingIntervalMins + "]";
	}

}
