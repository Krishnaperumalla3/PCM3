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

package com.pe.pcm.protocol.connectdirect.entity;

import com.pe.pcm.audit.Auditable;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Kiran Reddy.
 */
@Entity
@Table(name = "PETPE_CD")
public class ConnectDirectEntity extends Auditable {

    @Id
    private String pkId;

    @NotNull
    private String subscriberType;

    @NotNull
    private String subscriberId;

    private String remoteHost;
    private String remotePort;
    private String remoteUser;
    private String remotePassword;
    private String transferType;
    private String localNodeName;
    private String remoteNodeName;
    private String codePageFrom;
    private String codePageTo;
    private String localXlate;
    private String dcb;
    private String sysOpts;
    private String securityProtocol;
    private String securePlus;
    private String caCertificate;
    private String caCertificateId;
    private String cipherSuits;
    private String adapterName;

    @Column(name = "POLLING_INTERVAL")
    private String poolingInterval;

    private String isActive;
    private String isHubInfo;

    public String getPkId() {
        return pkId;
    }

    public ConnectDirectEntity setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getSubscriberType() {
        return subscriberType;
    }

    public ConnectDirectEntity setSubscriberType(String subscriberType) {
        this.subscriberType = subscriberType;
        return this;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public ConnectDirectEntity setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
        return this;
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public ConnectDirectEntity setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
        return this;
    }

    public String getRemotePort() {
        return remotePort;
    }

    public ConnectDirectEntity setRemotePort(String remotePort) {
        this.remotePort = remotePort;
        return this;
    }

    public String getRemoteUser() {
        return remoteUser;
    }

    public ConnectDirectEntity setRemoteUser(String remoteUser) {
        this.remoteUser = remoteUser;
        return this;
    }

    public String getRemotePassword() {
        return remotePassword;
    }

    public ConnectDirectEntity setRemotePassword(String remotePassword) {
        this.remotePassword = remotePassword;
        return this;
    }

    public String getTransferType() {
        return transferType;
    }

    public ConnectDirectEntity setTransferType(String transferType) {
        this.transferType = transferType;
        return this;
    }

    public String getLocalNodeName() {
        return localNodeName;
    }

    public ConnectDirectEntity setLocalNodeName(String localNodeName) {
        this.localNodeName = localNodeName;
        return this;
    }

    public String getRemoteNodeName() {
        return remoteNodeName;
    }

    public ConnectDirectEntity setRemoteNodeName(String remoteNodeName) {
        this.remoteNodeName = remoteNodeName;
        return this;
    }

    public String getCodePageFrom() {
        return codePageFrom;
    }

    public ConnectDirectEntity setCodePageFrom(String codePageFrom) {
        this.codePageFrom = codePageFrom;
        return this;
    }

    public String getCodePageTo() {
        return codePageTo;
    }

    public ConnectDirectEntity setCodePageTo(String codePageTo) {
        this.codePageTo = codePageTo;
        return this;
    }

    public String getLocalXlate() {
        return localXlate;
    }

    public ConnectDirectEntity setLocalXlate(String localXlate) {
        this.localXlate = localXlate;
        return this;
    }

    public String getDcb() {
        return dcb;
    }

    public ConnectDirectEntity setDcb(String dcb) {
        this.dcb = dcb;
        return this;
    }

    public String getSysOpts() {
        return sysOpts;
    }

    public ConnectDirectEntity setSysOpts(String sysOpts) {
        this.sysOpts = sysOpts;
        return this;
    }

    public String getSecurityProtocol() {
        return securityProtocol;
    }

    public ConnectDirectEntity setSecurityProtocol(String securityProtocol) {
        this.securityProtocol = securityProtocol;
        return this;
    }

    public String getSecurePlus() {
        return securePlus;
    }

    public ConnectDirectEntity setSecurePlus(String securePlus) {
        this.securePlus = securePlus;
        return this;
    }

    public String getCaCertificate() {
        return caCertificate;
    }

    public ConnectDirectEntity setCaCertificate(String caCertificate) {
        this.caCertificate = caCertificate;
        return this;
    }

    public String getCaCertificateId() {
        return caCertificateId;
    }

    public ConnectDirectEntity setCaCertificateId(String caCertificateId) {
        this.caCertificateId = caCertificateId;
        return this;
    }

    public String getCipherSuits() {
        return cipherSuits;
    }

    public ConnectDirectEntity setCipherSuits(String cipherSuits) {
        this.cipherSuits = cipherSuits;
        return this;
    }

    public String getAdapterName() {
        return adapterName;
    }

    public ConnectDirectEntity setAdapterName(String adapterName) {
        this.adapterName = adapterName;
        return this;
    }

    public String getPoolingInterval() {
        return poolingInterval;
    }

    public ConnectDirectEntity setPoolingInterval(String poolingInterval) {
        this.poolingInterval = poolingInterval;
        return this;
    }

    public String getIsActive() {
        return isActive;
    }

    public ConnectDirectEntity setIsActive(String isActive) {
        this.isActive = isActive;
        return this;
    }

    public String getIsHubInfo() {
        return isHubInfo;
    }

    public ConnectDirectEntity setIsHubInfo(String isHubInfo) {
        this.isHubInfo = isHubInfo;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("pkId", pkId)
                .append("subscriberType", subscriberType)
                .append("subscriberId", subscriberId)
                .append("remoteHost", remoteHost)
                .append("remotePort", remotePort)
                .append("remoteUser", remoteUser)
                .append("remotePassword", remotePassword)
                .append("transferType", transferType)
                .append("localNodeName", localNodeName)
                .append("remoteNodeName", remoteNodeName)
                .append("codePageFrom", codePageFrom)
                .append("codePageTo", codePageTo)
                .append("localXlate", localXlate)
                .append("dcb", dcb)
                .append("sysOpts", sysOpts)
                .append("securityProtocol", securityProtocol)
                .append("securePlus", securePlus)
                .append("caCertificate", caCertificate)
                .append("caCertificateId", caCertificateId)
                .append("cipherSuits", cipherSuits)
                .append("adapterName", adapterName)
                .append("poolingInterval", poolingInterval)
                .append("isActive", isActive)
                .append("isHubInfo", isHubInfo)
                .toString();
    }
}
