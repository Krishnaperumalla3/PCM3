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

package com.pe.pcm.protocol.http.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.pe.pcm.audit.Auditable;

import java.io.Serializable;

/**
 * @author PragmaEdge Inc.
 *
 */
@Entity
@Table(name = "PETPE_HTTP")
public class HttpEntity extends Auditable implements Serializable {

	@Id
	private String pkId;
	
	@NotNull
	private String subscriberType;
	 
	@NotNull
	private String subscriberId;
	
	@NotNull
	private String protocolType;

	private String inMailbox;
	
	private String outboundUrl;
	
	private String certificate;

	private String certificateId;
	
	@NotNull
	private String isActive;
	
	@NotNull
	private String isHubInfo;
	
	private String adapterName;
	
	private String poolingIntervalMins;

	public String getPkId() {
		return pkId;
	}

	public HttpEntity setPkId(String pkId) {
		this.pkId = pkId;
		return this;
	}

	public String getSubscriberType() {
		return subscriberType;
	}

	public HttpEntity setSubscriberType(String subscriberType) {
		this.subscriberType = subscriberType;
		return this;
	}

	public String getSubscriberId() {
		return subscriberId;
	}

	public HttpEntity setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
		return this;
	}

	public String getProtocolType() {
		return protocolType;
	}

	public HttpEntity setProtocolType(String protocolType) {
		this.protocolType = protocolType;
		return this;
	}

	public String getInMailbox() {
		return inMailbox;
	}

	public HttpEntity setInMailbox(String inMailbox) {
		this.inMailbox = inMailbox;
		return this;
	}

	public String getOutboundUrl() {
		return outboundUrl;
	}

	public HttpEntity setOutboundUrl(String outboundUrl) {
		this.outboundUrl = outboundUrl;
		return this;
	}

	public String getCertificate() {
		return certificate;
	}

	public HttpEntity setCertificate(String certificate) {
		this.certificate = certificate;
		return this;
	}

	public String getCertificateId() {
		return certificateId;
	}

	public HttpEntity setCertificateId(String certificateId) {
		this.certificateId = certificateId;
		return this;
	}

	public String getIsActive() {
		return isActive;
	}

	public HttpEntity setIsActive(String isActive) {
		this.isActive = isActive;
		return this;
	}

	public String getIsHubInfo() {
		return isHubInfo;
	}

	public HttpEntity setIsHubInfo(String isHubInfo) {
		this.isHubInfo = isHubInfo;
		return this;
	}

	public String getAdapterName() {
		return adapterName;
	}

	public HttpEntity setAdapterName(String adapterName) {
		this.adapterName = adapterName;
		return this;
	}

	public String getPoolingIntervalMins() {
		return poolingIntervalMins;
	}

	public HttpEntity setPoolingIntervalMins(String poolingIntervalMins) {
		this.poolingIntervalMins = poolingIntervalMins;
		return this;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "HttpEntity [pkId=" + pkId + ", subscriberType=" + subscriberType + ", subscriberId=" + subscriberId
				+ ", protocolType=" + protocolType + ", inMailbox=" + inMailbox + ", outboundUrl=" + outboundUrl
				+ ", certificate=" + certificate + ", isActive=" + isActive + ", isHubInfo=" + isHubInfo
				+ ", adapterName=" + adapterName + ", poolingIntervalMins=" + poolingIntervalMins + "]";
	}

	
}
