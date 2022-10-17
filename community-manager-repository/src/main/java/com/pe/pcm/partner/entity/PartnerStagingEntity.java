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

package com.pe.pcm.partner.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pe.pcm.audit.Auditable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.StringJoiner;

@Entity
@Table(name = "PETPE_TRADINGPARTNER_STAGING")
public class PartnerStagingEntity extends Auditable implements Serializable {

	private static final long serialVersionUID = 7422574264557894633L;

	@Id
	@JsonProperty(value = "pkId")
	private String pkId;

	@NotNull
	private String tpName;

	@NotNull
	private String tpId;

	private String addressLine1;

	private String addressLine2;

	@NotNull
	private String email;

	@NotNull
	private String phone;

	@NotNull
	private String tpProtocol;

	@NotNull
	private String tpPickupFiles;

	@NotNull
	private String fileTpServer;

	@NotNull
	private String partnerProtocolRef;

	@NotNull
	@JsonProperty(value = "status")
	private String tpIsActive;

	@NotNull
	private String isProtocolHubInfo;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getPkId() {
		return pkId;
	}

	public PartnerStagingEntity setPkId(String pkId) {
		this.pkId = pkId;
		return this;
	}

	public String getTpName() {
		return tpName;
	}

	public PartnerStagingEntity setTpName(String tpName) {
		this.tpName = tpName;
		return this;
	}

	public String getTpId() {
		return tpId;
	}

	public PartnerStagingEntity setTpId(String tpId) {
		this.tpId = tpId;
		return this;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public PartnerStagingEntity setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
		return this;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public PartnerStagingEntity setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
		return this;
	}

	public String getEmail() {
		return email;
	}

	public PartnerStagingEntity setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getPhone() {
		return phone;
	}

	public PartnerStagingEntity setPhone(String phone) {
		this.phone = phone;
		return this;
	}

	public String getTpProtocol() {
		return tpProtocol;
	}

	public PartnerStagingEntity setTpProtocol(String tpProtocol) {
		this.tpProtocol = tpProtocol;
		return this;
	}

	public String getTpPickupFiles() {
		return tpPickupFiles;
	}

	public PartnerStagingEntity setTpPickupFiles(String tpPickupFiles) {
		this.tpPickupFiles = tpPickupFiles;
		return this;
	}

	public String getFileTpServer() {
		return fileTpServer;
	}

	public PartnerStagingEntity setFileTpServer(String fileTpServer) {
		this.fileTpServer = fileTpServer;
		return this;
	}

	public String getPartnerProtocolRef() {
		return partnerProtocolRef;
	}

	public PartnerStagingEntity setPartnerProtocolRef(String partnerProtocolRef) {
		this.partnerProtocolRef = partnerProtocolRef;
		return this;
	}

	public String getTpIsActive() {
		return tpIsActive;
	}

	public PartnerStagingEntity setTpIsActive(String tpIsActive) {
		this.tpIsActive = tpIsActive;
		return this;
	}

	public String getIsProtocolHubInfo() {
		return isProtocolHubInfo;
	}

	public PartnerStagingEntity setIsProtocolHubInfo(String isProtocolHubInfo) {
		this.isProtocolHubInfo = isProtocolHubInfo;
		return this;
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", PartnerStagingEntity.class.getSimpleName() + "[", "]")
				.add("pkId='" + pkId + "'")
				.add("tpName='" + tpName + "'")
				.add("tpId='" + tpId + "'")
				.add("addressLine1='" + addressLine1 + "'")
				.add("addressLine2='" + addressLine2 + "'")
				.add("email='" + email + "'")
				.add("phone='" + phone + "'")
				.add("tpProtocol='" + tpProtocol + "'")
				.add("tpPickupFiles='" + tpPickupFiles + "'")
				.add("fileTpServer='" + fileTpServer + "'")
				.add("partnerProtocolRef='" + partnerProtocolRef + "'")
				.add("tpIsActive='" + tpIsActive + "'")
				.add("isProtocolHubInfo='" + isProtocolHubInfo + "'")
				.add("createdBy='" + createdBy + "'")
				.add("lastUpdatedBy='" + lastUpdatedBy + "'")
				.add("lastUpdatedDt=" + lastUpdatedDt)
				.toString();
	}
}
