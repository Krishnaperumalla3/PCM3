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

package com.pe.pcm.protocol.ec.entity;

import com.pe.pcm.audit.Auditable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "PETPE_EC_STAGING")
public class EcStagingEntity extends Auditable {
	

	@Id
	private String pkId;
	
	@NotNull
	private String subscriberType;
	
	@NotNull	
	private String subscriberId;

	@NotNull
	private String isActive;

	@NotNull
	private String isHubInfo;
	
	private String ecPartnerPriority;
	
	private String ecProtocol;

	private String ecProtocolRef;

	public String getPkId() {
		return pkId;
	}

	public EcStagingEntity setPkId(String pkId) {
		this.pkId = pkId;
		return this;
	}

	public String getSubscriberType() {
		return subscriberType;
	}

	public EcStagingEntity setSubscriberType(String subscriberType) {
		this.subscriberType = subscriberType;
		return this;
	}

	public String getSubscriberId() {
		return subscriberId;
	}

	public EcStagingEntity setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
		return this;
	}

	public String getIsActive() {
		return isActive;
	}

	public EcStagingEntity setIsActive(String isActive) {
		this.isActive = isActive;
		return this;
	}

	public String getIsHubInfo() {
		return isHubInfo;
	}

	public EcStagingEntity setIsHubInfo(String isHubInfo) {
		this.isHubInfo = isHubInfo;
		return this;
	}

	public String getEcPartnerPriority() {
		return ecPartnerPriority;
	}

	public EcStagingEntity setEcPartnerPriority(String ecPartnerPriority) {
		this.ecPartnerPriority = ecPartnerPriority;
		return this;
	}

	public String getEcProtocol() {
		return ecProtocol;
	}

	public EcStagingEntity setEcProtocol(String ecProtocol) {
		this.ecProtocol = ecProtocol;
		return this;
	}

	public String getEcProtocolRef() {
		return ecProtocolRef;
	}

	public EcStagingEntity setEcProtocolRef(String ecProtocolRef) {
		this.ecProtocolRef = ecProtocolRef;
		return this;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ECEntity [pkId=" + pkId + ", subscriberType=" + subscriberType + ", subscriberId=" + subscriberId
				+ ", isActive=" + isActive + ", isHubInfo=" + isHubInfo + ", ecPartnerPriority=" + ecPartnerPriority
				+ ", ecProtocol=" + ecProtocol + ", ecProtocolRef=" + ecProtocolRef + ", createdBy=" + createdBy
				+ ", lastUpdatedBy=" + lastUpdatedBy + ", lastUpdatedDt=" + lastUpdatedDt + ", getProcessDocPkId()=" + getPkId()
				+ ", getSubscriberType()=" + getSubscriberType() + ", getSubscriberId()=" + getSubscriberId()
				+ ", getIsActive()=" + getIsActive() + ", getIsHubInfo()=" + getIsHubInfo()
				+ ", getEcPartnerPriority()=" + getEcPartnerPriority() + ", getEcProtocol()=" + getEcProtocol()
				+ ", getEcProtocolRef()=" + getEcProtocolRef() + ", getCreatedBy()=" + getCreatedBy()
				+ ", getLastUpdatedBy()=" + getLastUpdatedBy() + ", getLastUpdatedDt()=" + getLastUpdatedDt()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
}
