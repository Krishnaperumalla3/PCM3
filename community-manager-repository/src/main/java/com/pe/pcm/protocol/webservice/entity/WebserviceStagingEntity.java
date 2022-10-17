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

package com.pe.pcm.protocol.webservice.entity;

import com.pe.pcm.audit.Auditable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Chenchu Kiran.
 *
 */
@Entity
@Table(name = "PETPE_WEBSERVICE_STAGING")
public class WebserviceStagingEntity extends Auditable{

	@Id
	private String pkId;

	@NotNull
	private String subscriberType;

	@NotNull
	private String subscriberId;

	@NotNull
	private String webserviceName;

	@NotNull
	private String isActive;

	@NotNull
	private String isHubInfo;
	
	private String outboundUrl;
	
	private String inDirectory;
	
	private String certificate;
	
	private String adapterName;
	
	private String poolingIntervalMins;

	public String getPkId() {
		return pkId;
	}

	public WebserviceStagingEntity setPkId(String pkId) {
		this.pkId = pkId;
		return this;
	}

	public String getSubscriberType() {
		return subscriberType;
	}

	public WebserviceStagingEntity setSubscriberType(String subscriberType) {
		this.subscriberType = subscriberType;
		return this;
	}

	public String getSubscriberId() {
		return subscriberId;
	}

	public WebserviceStagingEntity setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
		return this;
	}

	public String getWebserviceName() {
		return webserviceName;
	}

	public WebserviceStagingEntity setWebserviceName(String webserviceName) {
		this.webserviceName = webserviceName;
		return this;
	}

	public String getIsActive() {
		return isActive;
	}

	public WebserviceStagingEntity setIsActive(String isActive) {
		this.isActive = isActive;
		return this;
	}

	public String getIsHubInfo() {
		return isHubInfo;
	}

	public WebserviceStagingEntity setIsHubInfo(String isHubInfo) {
		this.isHubInfo = isHubInfo;
		return this;
	}

	public String getOutboundUrl() {
		return outboundUrl;
	}

	public WebserviceStagingEntity setOutboundUrl(String outboundUrl) {
		this.outboundUrl = outboundUrl;
		return this;
	}

	public String getInDirectory() {
		return inDirectory;
	}

	public WebserviceStagingEntity setInDirectory(String inDirectory) {
		this.inDirectory = inDirectory;
		return this;
	}

	public String getCertificate() {
		return certificate;
	}

	public WebserviceStagingEntity setCertificate(String certificate) {
		this.certificate = certificate;
		return this;
	}

	public String getAdapterName() {
		return adapterName;
	}

	public WebserviceStagingEntity setAdapterName(String adapterName) {
		this.adapterName = adapterName;
		return this;
	}

	public String getPoolingIntervalMins() {
		return poolingIntervalMins;
	}

	public WebserviceStagingEntity setPoolingIntervalMins(String poolingIntervalMins) {
		this.poolingIntervalMins = poolingIntervalMins;
		return this;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "WebserviceEntity [pkId=" + pkId + ", subscriberType=" + subscriberType + ", subscriberId="
				+ subscriberId + ", webserviceName=" + webserviceName + ", isActive=" + isActive + ", isHubInfo="
				+ isHubInfo + ", outboundUrl=" + outboundUrl + ", inDirectory=" + inDirectory + ", certificate="
				+ certificate + ", adapterName=" + adapterName + ", poolingIntervalMins=" + poolingIntervalMins + "]";
	}
	
	

}
