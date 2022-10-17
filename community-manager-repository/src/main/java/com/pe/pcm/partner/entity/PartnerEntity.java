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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "PETPE_TRADINGPARTNER")
public class PartnerEntity extends Auditable implements Serializable {

    private static final long serialVersionUID = 7422574264557894633L;

    @Id
    @JsonProperty(value = "pkId")
    private String pkId;

    @NotNull
    private String tpName;

    private String customTpName;

    private String pgpInfo;

    private String ipWhitelist;

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
    @Column(name = "tp_is_active")
    private String status;

    @NotNull
    private String isProtocolHubInfo;

    private String isOnlyPcm;

    private String pemIdentifier;

    public String getPkId() {
        return pkId;
    }

    public PartnerEntity setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getTpName() {
        return tpName;
    }

    public PartnerEntity setTpName(String tpName) {
        this.tpName = tpName;
        return this;
    }

    public String getTpId() {
        return tpId;
    }

    public PartnerEntity setTpId(String tpId) {
        this.tpId = tpId;
        return this;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public PartnerEntity setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
        return this;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public PartnerEntity setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public PartnerEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public PartnerEntity setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getTpProtocol() {
        return tpProtocol;
    }

    public PartnerEntity setTpProtocol(String tpProtocol) {
        this.tpProtocol = tpProtocol;
        return this;
    }

    public String getTpPickupFiles() {
        return tpPickupFiles;
    }

    public PartnerEntity setTpPickupFiles(String tpPickupFiles) {
        this.tpPickupFiles = tpPickupFiles;
        return this;
    }

    public String getFileTpServer() {
        return fileTpServer;
    }

    public PartnerEntity setFileTpServer(String fileTpServer) {
        this.fileTpServer = fileTpServer;
        return this;
    }

    public String getPartnerProtocolRef() {
        return partnerProtocolRef;
    }

    public PartnerEntity setPartnerProtocolRef(String partnerProtocolRef) {
        this.partnerProtocolRef = partnerProtocolRef;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public PartnerEntity setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getIsProtocolHubInfo() {
        return isProtocolHubInfo;
    }

    public PartnerEntity setIsProtocolHubInfo(String isProtocolHubInfo) {
        this.isProtocolHubInfo = isProtocolHubInfo;
        return this;
    }

    public String getPgpInfo() {
        return pgpInfo;
    }

    public PartnerEntity setPgpInfo(String pgpInfo) {
        this.pgpInfo = pgpInfo;
        return this;
    }

    public String getIpWhitelist() {
        return ipWhitelist;
    }

    public PartnerEntity setIpWhitelist(String ipWhitelist) {
        this.ipWhitelist = ipWhitelist;
        return this;
    }

    public String getCustomTpName() {
        return customTpName;
    }

    public PartnerEntity setCustomTpName(String customTpName) {
        this.customTpName = customTpName;
        return this;
    }

    public String getIsOnlyPcm() {
        return isOnlyPcm;
    }

    public PartnerEntity setIsOnlyPcm(String isOnlyPcm) {
        this.isOnlyPcm = isOnlyPcm;
        return this;
    }

    public String getPemIdentifier() {
        return pemIdentifier;
    }

    public PartnerEntity setPemIdentifier(String pemIdentifier) {
        this.pemIdentifier = pemIdentifier;
        return this;
    }
}
