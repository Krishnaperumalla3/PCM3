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
@Table(name = "PETPE_TRADINGPARTNER_SFG")
public class RemotePartnerStagingEntity  extends Auditable implements Serializable {

    @Id
    @JsonProperty(value = "pkId")
    private String pkId;

    @NotNull
    @JsonProperty(value = "partnerName")
    private String tpName;

    @NotNull
    @JsonProperty(value = "partnerId")
    private String tpId;

    private String addressLine1;

    private String addressLine2;

    @NotNull
    private String email;

    @NotNull
    private String phone;

    @NotNull
    @JsonProperty(value = "protocol")
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

    public String getPkId() {
        return pkId;
    }

    public RemotePartnerStagingEntity setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getTpName() {
        return tpName;
    }

    public RemotePartnerStagingEntity setTpName(String tpName) {
        this.tpName = tpName;
        return this;
    }

    public String getTpId() {
        return tpId;
    }

    public RemotePartnerStagingEntity setTpId(String tpId) {
        this.tpId = tpId;
        return this;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public RemotePartnerStagingEntity setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
        return this;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public RemotePartnerStagingEntity setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public RemotePartnerStagingEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public RemotePartnerStagingEntity setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getTpProtocol() {
        return tpProtocol;
    }

    public RemotePartnerStagingEntity setTpProtocol(String tpProtocol) {
        this.tpProtocol = tpProtocol;
        return this;
    }

    public String getTpPickupFiles() {
        return tpPickupFiles;
    }

    public RemotePartnerStagingEntity setTpPickupFiles(String tpPickupFiles) {
        this.tpPickupFiles = tpPickupFiles;
        return this;
    }

    public String getFileTpServer() {
        return fileTpServer;
    }

    public RemotePartnerStagingEntity setFileTpServer(String fileTpServer) {
        this.fileTpServer = fileTpServer;
        return this;
    }

    public String getPartnerProtocolRef() {
        return partnerProtocolRef;
    }

    public RemotePartnerStagingEntity setPartnerProtocolRef(String partnerProtocolRef) {
        this.partnerProtocolRef = partnerProtocolRef;
        return this;
    }

    public String getTpIsActive() {
        return tpIsActive;
    }

    public RemotePartnerStagingEntity setTpIsActive(String tpIsActive) {
        this.tpIsActive = tpIsActive;
        return this;
    }

    public String getIsProtocolHubInfo() {
        return isProtocolHubInfo;
    }

    public RemotePartnerStagingEntity setIsProtocolHubInfo(String isProtocolHubInfo) {
        this.isProtocolHubInfo = isProtocolHubInfo;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RemotePartnerStagingEntity.class.getSimpleName() + "[", "]")
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
                .toString();
    }
}
