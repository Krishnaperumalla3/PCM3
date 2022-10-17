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
package com.pe.pcm.protocol.mailbox.entity;

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
@Table(name = "PETPE_MAILBOX_STAGING")
public class MailboxStagingEntity extends Auditable {

	@Id
	private String pkId;

	@NotNull
	private String subscriberType;

	@NotNull
	private String subscriberId;

	private String inMailbox;

	private String outMailbox;

	@NotNull
	private String poolingIntervalMins;

	@NotNull
	private String protocolType;

	@NotNull
	private String isActive;

	private String isHubInfo;

	public String getPkId() {
		return pkId;
	}

	public MailboxStagingEntity setPkId(String pkId) {
		this.pkId = pkId;
		return this;
	}

	public String getSubscriberType() {
		return subscriberType;
	}

	public MailboxStagingEntity setSubscriberType(String subscriberType) {
		this.subscriberType = subscriberType;
		return this;
	}

	public String getSubscriberId() {
		return subscriberId;
	}

	public MailboxStagingEntity setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
		return this;
	}

	public String getInMailbox() {
		return inMailbox;
	}

	public MailboxStagingEntity setInMailbox(String inMailbox) {
		this.inMailbox = inMailbox;
		return this;
	}

	public String getOutMailbox() {
		return outMailbox;
	}

	public MailboxStagingEntity setOutMailbox(String outMailbox) {
		this.outMailbox = outMailbox;
		return this;
	}

	public String getPoolingIntervalMins() {
		return poolingIntervalMins;
	}

	public MailboxStagingEntity setPoolingIntervalMins(String poolingIntervalMins) {
		this.poolingIntervalMins = poolingIntervalMins;
		return this;
	}

	public String getProtocolType() {
		return protocolType;
	}

	public MailboxStagingEntity setProtocolType(String protocolType) {
		this.protocolType = protocolType;
		return this;
	}

	public String getIsActive() {
		return isActive;
	}

	public MailboxStagingEntity setIsActive(String isActive) {
		this.isActive = isActive;
		return this;
	}

	public String getIsHubInfo() {
		return isHubInfo;
	}

	public MailboxStagingEntity setIsHubInfo(String isHubInfo) {
		this.isHubInfo = isHubInfo;
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MailBoxEntity [pkId=" + pkId + ", subscriberType=" + subscriberType + ", subscriberId=" + subscriberId
				+ ", inMailbox=" + inMailbox + ", outMailbox=" + outMailbox + ", poolingIntervalMins="
				+ poolingIntervalMins + ", protocolType=" + protocolType + ", isActive=" + isActive + ", isHubInfo="
				+ isHubInfo + "]";
	}

}
