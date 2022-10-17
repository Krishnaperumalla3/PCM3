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
package com.pe.pcm.protocol.mq.entity;

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
@Table(name = "PETPE_MQ_STAGING")
public class MqStagingEntity extends Auditable{

	@Id
	private String pkId;
	
	@NotNull
	private String subscriberType;
	
	@NotNull
	private String subscriberId;
	
	@NotNull
	private String queueManager;
	
	@NotNull
	private String queueName;
	
	private String poolingIntervalMins;
	
	private String fileType;
	
	@NotNull
	private String isActive;
	
	@NotNull
	private String isHubInfo;
	
	private String hostName;
	
	private String adapterName;

	public String getPkId() {
		return pkId;
	}

	public MqStagingEntity setPkId(String pkId) {
		this.pkId = pkId;
		return this;
	}

	public String getSubscriberType() {
		return subscriberType;
	}

	public MqStagingEntity setSubscriberType(String subscriberType) {
		this.subscriberType = subscriberType;
		return this;
	}

	public String getSubscriberId() {
		return subscriberId;
	}

	public MqStagingEntity setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
		return this;
	}

	public String getQueueManager() {
		return queueManager;
	}

	public MqStagingEntity setQueueManager(String queueManager) {
		this.queueManager = queueManager;
		return this;
	}

	public String getQueueName() {
		return queueName;
	}

	public MqStagingEntity setQueueName(String queueName) {
		this.queueName = queueName;
		return this;
	}

	public String getPoolingIntervalMins() {
		return poolingIntervalMins;
	}

	public MqStagingEntity setPoolingIntervalMins(String poolingIntervalMins) {
		this.poolingIntervalMins = poolingIntervalMins;
		return this;
	}

	public String getFileType() {
		return fileType;
	}

	public MqStagingEntity setFileType(String fileType) {
		this.fileType = fileType;
		return this;
	}

	public String getIsActive() {
		return isActive;
	}

	public MqStagingEntity setIsActive(String isActive) {
		this.isActive = isActive;
		return this;
	}

	public String getIsHubInfo() {
		return isHubInfo;
	}

	public MqStagingEntity setIsHubInfo(String isHubInfo) {
		this.isHubInfo = isHubInfo;
		return this;
	}

	public String getHostName() {
		return hostName;
	}

	public MqStagingEntity setHostName(String hostName) {
		this.hostName = hostName;
		return this;
	}

	public String getAdapterName() {
		return adapterName;
	}

	public MqStagingEntity setAdapterName(String adapterName) {
		this.adapterName = adapterName;
		return this;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MqEntity [pkId=" + pkId + ", subscriberType=" + subscriberType + ", subscriberId=" + subscriberId
				+ ", queueManager=" + queueManager + ", queueName=" + queueName + ", poolingIntervalMins="
				+ poolingIntervalMins + ", fileType=" + fileType + ", isActive=" + isActive + ", isHubInfo=" + isHubInfo
				+ ", hostName=" + hostName + ", adapterName=" + adapterName + "]";
	}
	
	
	
}
