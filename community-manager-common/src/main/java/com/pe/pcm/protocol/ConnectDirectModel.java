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

package com.pe.pcm.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pe.pcm.b2b.other.CaCertGetModel;
import com.pe.pcm.b2b.other.CipherSuiteNameGetModel;
import com.pe.pcm.profile.ProfileModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Chenchu Kiran.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConnectDirectModel extends ProfileModel implements Serializable {

    private String remoteHost;
    private String remotePort;
    private String remoteUser;
    private String remotePassword;
    private String transferType;
    private String localNodeName;
    private String remoteNodeName;
    private String codePageFrom;
    private String codePageTo;
    private String localXLate;
    private String dcb;
    private String sysOpts;
    private String securityProtocol;
    private Boolean securePlus;
    private List<CaCertGetModel> caCertificate = new ArrayList<>();
    private List<CipherSuiteNameGetModel> cipherSuits = new ArrayList<>();
    private String adapterName;
    private String poolingInterval;

    public String getRemoteHost() {
        return remoteHost;
    }

    public ConnectDirectModel setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
        return this;
    }

    public String getRemotePort() {
        return remotePort;
    }

    public ConnectDirectModel setRemotePort(String remotePort) {
        this.remotePort = remotePort;
        return this;
    }

    public String getRemoteUser() {
        return remoteUser;
    }

    public ConnectDirectModel setRemoteUser(String remoteUser) {
        this.remoteUser = remoteUser;
        return this;
    }

    public String getRemotePassword() {
        return remotePassword;
    }

    public ConnectDirectModel setRemotePassword(String remotePassword) {
        this.remotePassword = remotePassword;
        return this;
    }

    public String getTransferType() {
        return transferType;
    }

    public ConnectDirectModel setTransferType(String transferType) {
        this.transferType = transferType;
        return this;
    }

    public String getLocalNodeName() {
        return localNodeName;
    }

    public ConnectDirectModel setLocalNodeName(String localNodeName) {
        this.localNodeName = localNodeName;
        return this;
    }

    public String getRemoteNodeName() {
        return remoteNodeName;
    }

    public ConnectDirectModel setRemoteNodeName(String remoteNodeName) {
        this.remoteNodeName = remoteNodeName;
        return this;
    }

    public String getCodePageFrom() {
        return codePageFrom;
    }

    public ConnectDirectModel setCodePageFrom(String codePageFrom) {
        this.codePageFrom = codePageFrom;
        return this;
    }

    public String getCodePageTo() {
        return codePageTo;
    }

    public ConnectDirectModel setCodePageTo(String codePageTo) {
        this.codePageTo = codePageTo;
        return this;
    }

    public String getLocalXLate() {
        return localXLate;
    }

    public ConnectDirectModel setLocalXLate(String localXLate) {
        this.localXLate = localXLate;
        return this;
    }

    public String getDcb() {
        return dcb;
    }

    public ConnectDirectModel setDcb(String dcb) {
        this.dcb = dcb;
        return this;
    }

    public String getSysOpts() {
        return sysOpts;
    }

    public ConnectDirectModel setSysOpts(String sysOpts) {
        this.sysOpts = sysOpts;
        return this;
    }

    public String getSecurityProtocol() {
        return securityProtocol;
    }

    public ConnectDirectModel setSecurityProtocol(String securityProtocol) {
        this.securityProtocol = securityProtocol;
        return this;
    }

    public Boolean getSecurePlus() {
        return securePlus;
    }

    public ConnectDirectModel setSecurePlus(Boolean securePlus) {
        this.securePlus = securePlus;
        return this;
    }

    public List<CaCertGetModel> getCaCertificate() {
        return caCertificate;
    }

    public ConnectDirectModel setCaCertificate(List<CaCertGetModel> caCertificate) {
        this.caCertificate = caCertificate;
        return this;
    }

    public List<CipherSuiteNameGetModel> getCipherSuits() {
        return cipherSuits;
    }

    public ConnectDirectModel setCipherSuits(List<CipherSuiteNameGetModel> cipherSuits) {
        this.cipherSuits = cipherSuits;
        return this;
    }

    public String getAdapterName() {
        return adapterName;
    }

    public ConnectDirectModel setAdapterName(String adapterName) {
        this.adapterName = adapterName;
        return this;
    }

    public String getPoolingInterval() {
        return poolingInterval;
    }

    public ConnectDirectModel setPoolingInterval(String poolingInterval) {
        this.poolingInterval = poolingInterval;
        return this;
    }

}
