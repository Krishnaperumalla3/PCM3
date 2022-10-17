/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc, Version 6.1 (the "License");
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

package com.pe.pcm.b2b.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pe.pcm.b2b.other.CaCertGetModel;
import com.pe.pcm.b2b.other.CipherSuiteNameGetModel;
import com.pe.pcm.protocol.RemoteCdModel;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.pe.pcm.utils.CommonFunctions.isNotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CdNodeConfiguration implements Serializable {

    private String alternateCommInfo;
    private List<CaCertGetModel> caCertificates = new ArrayList<>();
    private String certificateCommonName;
    private List<CipherSuiteNameGetModel> cipherSuites = new ArrayList<>();
    private String maxLocallyInitiatedPnodeSessionsAllowed;
    private String maxRemotelyInitiatedSnodeSessionsAllowed;
    private String requireClientAuthentication;
    private String securePlusOption;
    private Integer securityProtocol;
    private String serverHost;
    private String serverNodeName;
    private Integer serverPort;
    private String systemCertificate;

    public CdNodeConfiguration(RemoteCdModel remoteCdModel) {
        this.serverNodeName = remoteCdModel.getNodeName();
        this.maxLocallyInitiatedPnodeSessionsAllowed = remoteCdModel.getMaxLocallyInitiatedPnodeSessionsAllowed();
        this.maxRemotelyInitiatedSnodeSessionsAllowed = remoteCdModel.getMaxRemotelyInitiatedSnodeSessionsAllowed();
        this.requireClientAuthentication = isNotNull(remoteCdModel.getRequireClientAuthentication()) ? remoteCdModel.getRequireClientAuthentication() : "NO";
    }

    public String getAlternateCommInfo() {
        return alternateCommInfo;
    }

    public CdNodeConfiguration setAlternateCommInfo(String alternateCommInfo) {
        this.alternateCommInfo = alternateCommInfo;
        return this;
    }

    public List<CaCertGetModel> getCaCertificates() {
        return caCertificates;
    }

    public CdNodeConfiguration setCaCertificates(List<CaCertGetModel> caCertificates) {
        this.caCertificates = caCertificates;
        return this;
    }

    public String getCertificateCommonName() {
        return certificateCommonName;
    }

    public CdNodeConfiguration setCertificateCommonName(String certificateCommonName) {
        this.certificateCommonName = certificateCommonName;
        return this;
    }

    public List<CipherSuiteNameGetModel> getCipherSuites() {
        return cipherSuites;
    }

    public CdNodeConfiguration setCipherSuites(List<CipherSuiteNameGetModel> cipherSuites) {
        this.cipherSuites = cipherSuites;
        return this;
    }



    public String getRequireClientAuthentication() {
        return requireClientAuthentication;
    }

    public CdNodeConfiguration setRequireClientAuthentication(String requireClientAuthentication) {
        this.requireClientAuthentication = requireClientAuthentication;
        return this;
    }

    public String getSecurePlusOption() {
        return securePlusOption;
    }

    public CdNodeConfiguration setSecurePlusOption(String securePlusOption) {
        this.securePlusOption = securePlusOption;
        return this;
    }

    public Integer getSecurityProtocol() {
        return securityProtocol;
    }

    public CdNodeConfiguration setSecurityProtocol(Integer securityProtocol) {
        this.securityProtocol = securityProtocol;
        return this;
    }

    public String getServerHost() {
        return serverHost;
    }

    public CdNodeConfiguration setServerHost(String serverHost) {
        this.serverHost = serverHost;
        return this;
    }

    public String getServerNodeName() {
        return serverNodeName;
    }

    public CdNodeConfiguration setServerNodeName(String serverNodeName) {
        this.serverNodeName = serverNodeName;
        return this;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public CdNodeConfiguration setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
        return this;
    }

    public String getSystemCertificate() {
        return systemCertificate;
    }

    public CdNodeConfiguration setSystemCertificate(String systemCertificate) {
        this.systemCertificate = systemCertificate;
        return this;
    }

    public String getMaxLocallyInitiatedPnodeSessionsAllowed() {
        return maxLocallyInitiatedPnodeSessionsAllowed;
    }

    public CdNodeConfiguration setMaxLocallyInitiatedPnodeSessionsAllowed(String maxLocallyInitiatedPnodeSessionsAllowed) {
        this.maxLocallyInitiatedPnodeSessionsAllowed = maxLocallyInitiatedPnodeSessionsAllowed;
        return this;
    }

    public String getMaxRemotelyInitiatedSnodeSessionsAllowed() {
        return maxRemotelyInitiatedSnodeSessionsAllowed;
    }

    public CdNodeConfiguration setMaxRemotelyInitiatedSnodeSessionsAllowed(String maxRemotelyInitiatedSnodeSessionsAllowed) {
        this.maxRemotelyInitiatedSnodeSessionsAllowed = maxRemotelyInitiatedSnodeSessionsAllowed;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("alternateCommInfo", alternateCommInfo)
                .append("caCertificates", caCertificates)
                .append("certificateCommonName", certificateCommonName)
                .append("cipherSuites", cipherSuites)
                .append("maxLocallyInitiatedPnodeSessionsAllowed", maxLocallyInitiatedPnodeSessionsAllowed)
                .append("maxRemotelyInitiatedSnodeSessionsAllowed", maxRemotelyInitiatedSnodeSessionsAllowed)
                .append("requireClientAuthentication", requireClientAuthentication)
                .append("securePlusOption", securePlusOption)
                .append("securityProtocol", securityProtocol)
                .append("serverHost", serverHost)
                .append("serverNodeName", serverNodeName)
                .append("serverPort", serverPort)
                .append("systemCertificate", systemCertificate)
                .toString();
    }
}
