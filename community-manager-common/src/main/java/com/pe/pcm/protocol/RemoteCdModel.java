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
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.pe.pcm.b2b.other.CaCertGetModel;
import com.pe.pcm.b2b.other.CipherSuiteNameGetModel;
import com.pe.pcm.profile.ProfileModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JacksonXmlRootElement(localName = "PARTNER_CONNECT_DIRECT")
@JsonPropertyOrder({"pkId", "profileName", "profileId", "emailId", "phone",
        "protocol", "addressLine1", "addressLine2", "status", "hubInfo", "localNodeName", "remoteFileName",
        "nodeName", "sNodeId", "sNodeIdPassword", "operatingSystem", "directory", "hostName",
        "port", "copySisOpts", "checkPoint", "compressionLevel", "disposition", "submit", "secure",
        "runJob", "runTask", "caCertificate", "cipherSuits", "cdMainFrameModel", "poolingInterval", "adapterName", "securityProtocol", "caCertName"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class RemoteCdModel extends ProfileModel implements Serializable {


    private String localNodeName;
    private String remoteFileName;
    private String nodeName;
    private String sNodeId;
    private String sNodeIdPassword;
    private String operatingSystem;
    private String directory;
    private String hostName;
    private int port;
    private String copySisOpts;
    private String checkPoint;
    private String compressionLevel;
    private String disposition;
    private String submit;
    private boolean secure;
    private String runJob;
    private String runTask;
    private List<CipherSuiteNameGetModel> cipherSuits = new ArrayList<>();
    private CdMainFrameModel cdMainFrameModel;
    private String poolingInterval;
    private String adapterName;
    private String securityProtocol;
    private String systemCertificate;
    private String maxLocallyInitiatedPnodeSessionsAllowed;
    private String maxRemotelyInitiatedSnodeSessionsAllowed;
    private String requireClientAuthentication;
    private List<CaCertGetModel> caCertName = new ArrayList<>();
    private boolean internal;
    private boolean isSSP;
    private Boolean isSIProfile;
    private Boolean isInitiatingConsumer;
    private Boolean isInitiatingProducer;
    private Boolean isListeningConsumer;
    private Boolean isListeningProducer;
    private String profileUserName;
    private String profileUserPassword;
    private String outDirectory;
    private Boolean inboundConnectionType;
    private Boolean outboundConnectionType;
    private boolean doesUseSSH;
    private Boolean asciiArmor;
    private Boolean doesRequireEncryptedData;
    private Boolean doesRequireSignedData;
    private Boolean textMode;
    private String authenticationType;
    private String authenticationHost;

    public RemoteCdModel() {
        // To get initialize
    }

    public boolean isSSP() {
        return isSSP;
    }

    public RemoteCdModel setSSP(boolean SSP) {
        isSSP = SSP;
        return this;
    }

    public Boolean getIsSIProfile() {
        return isSIProfile;
    }

    public RemoteCdModel setIsSIProfile(Boolean isSIProfile) {
        this.isSIProfile = isSIProfile;
        return this;
    }

    public String getRequireClientAuthentication() {
        return requireClientAuthentication;
    }

    public RemoteCdModel setRequireClientAuthentication(String requireClientAuthentication) {
        this.requireClientAuthentication = requireClientAuthentication;
        return this;
    }

    public String getMaxLocallyInitiatedPnodeSessionsAllowed() {
        return maxLocallyInitiatedPnodeSessionsAllowed;
    }

    public RemoteCdModel setMaxLocallyInitiatedPnodeSessionsAllowed(String maxLocallyInitiatedPnodeSessionsAllowed) {
        this.maxLocallyInitiatedPnodeSessionsAllowed = maxLocallyInitiatedPnodeSessionsAllowed;
        return this;
    }

    public String getMaxRemotelyInitiatedSnodeSessionsAllowed() {
        return maxRemotelyInitiatedSnodeSessionsAllowed;
    }

    public RemoteCdModel setMaxRemotelyInitiatedSnodeSessionsAllowed(String maxRemotelyInitiatedSnodeSessionsAllowed) {
        this.maxRemotelyInitiatedSnodeSessionsAllowed = maxRemotelyInitiatedSnodeSessionsAllowed;
        return this;
    }

    public String getSystemCertificate() {
        return systemCertificate;
    }

    public RemoteCdModel setSystemCertificate(String systemCertificate) {
        this.systemCertificate = systemCertificate;
        return this;
    }

    @JacksonXmlProperty(localName = "SECURITY_PROTOCOL")
    public String getSecurityProtocol() {
        return securityProtocol;
    }

    public RemoteCdModel setSecurityProtocol(String securityProtocol) {
        this.securityProtocol = securityProtocol;
        return this;
    }

    @JacksonXmlProperty(localName = "CA_CERT_NAME")
    public List<CaCertGetModel> getCaCertName() {
        return caCertName;
    }

    public RemoteCdModel setCaCertName(List<CaCertGetModel> caCertName) {
        this.caCertName = caCertName;
        return this;
    }

    @JacksonXmlProperty(localName = "LOCAL_NODE_NAME")
    public String getLocalNodeName() {
        return localNodeName;
    }

    public RemoteCdModel setLocalNodeName(String localNodeName) {
        this.localNodeName = localNodeName;
        return this;
    }

    @JacksonXmlProperty(localName = "NODE_NAME")
    public String getNodeName() {
        return nodeName;
    }

    public RemoteCdModel setNodeName(String nodeName) {
        this.nodeName = nodeName;
        return this;
    }

    @JacksonXmlProperty(localName = "REMOTE_FILE_NAME")
    public String getRemoteFileName() {
        return remoteFileName;
    }

    public RemoteCdModel setRemoteFileName(String remoteFileName) {
        this.remoteFileName = remoteFileName;
        return this;
    }

    @JacksonXmlProperty(localName = "DIRECTORY")
    public String getDirectory() {
        return directory;
    }

    public RemoteCdModel setDirectory(String directory) {
        this.directory = directory;
        return this;
    }

    @JacksonXmlProperty(localName = "S_NODE_ID")
    public String getsNodeId() {
        return sNodeId;
    }

    public RemoteCdModel setsNodeId(String sNodeId) {
        this.sNodeId = sNodeId;
        return this;
    }

    @JacksonXmlProperty(localName = "S_NODE_ID_PASSWORD")
    public String getsNodeIdPassword() {
        return sNodeIdPassword;
    }

    public RemoteCdModel setsNodeIdPassword(String sNodeIdPassword) {
        this.sNodeIdPassword = sNodeIdPassword;
        return this;
    }

    @JacksonXmlProperty(localName = "POOLING_INTERVAL")
    public String getPoolingInterval() {
        return poolingInterval;
    }

    public RemoteCdModel setPoolingInterval(String poolingInterval) {
        this.poolingInterval = poolingInterval;
        return this;
    }

    @JacksonXmlProperty(localName = "ADAPTER_NAME")
    public String getAdapterName() {
        return adapterName;
    }

    public RemoteCdModel setAdapterName(String adapterName) {
        this.adapterName = adapterName;
        return this;
    }

    @JacksonXmlProperty(localName = "OPERATING_SYSTEM")
    public String getOperatingSystem() {
        return operatingSystem;
    }

    public RemoteCdModel setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
        return this;
    }

    @JacksonXmlProperty(localName = "HOST_NAME")
    public String getHostName() {
        return hostName;
    }

    public RemoteCdModel setHostName(String hostName) {
        this.hostName = hostName;
        return this;
    }

    @JacksonXmlProperty(localName = "PORT")
    public int getPort() {
        return port;
    }

    public RemoteCdModel setPort(int port) {
        this.port = port;
        return this;
    }

    @JacksonXmlProperty(localName = "COPY_SIS_OPTS")
    public String getCopySisOpts() {
        return copySisOpts;
    }

    public RemoteCdModel setCopySisOpts(String copySisOpts) {
        this.copySisOpts = copySisOpts;
        return this;
    }

    @JacksonXmlProperty(localName = "CHECK_POINT")
    public String getCheckPoint() {
        return checkPoint;
    }

    public RemoteCdModel setCheckPoint(String checkPoint) {
        this.checkPoint = checkPoint;
        return this;
    }

    @JacksonXmlProperty(localName = "COMPRESSION_LEVEL")
    public String getCompressionLevel() {
        return compressionLevel;
    }

    public RemoteCdModel setCompressionLevel(String compressionLevel) {
        this.compressionLevel = compressionLevel;
        return this;
    }

    @JacksonXmlProperty(localName = "DISPOSITION")
    public String getDisposition() {
        return disposition;
    }

    public RemoteCdModel setDisposition(String disposition) {
        this.disposition = disposition;
        return this;
    }

    @JacksonXmlProperty(localName = "SUBMIT")
    public String getSubmit() {
        return submit;
    }

    public RemoteCdModel setSubmit(String submit) {
        this.submit = submit;
        return this;
    }

    @JacksonXmlProperty(localName = "IS_SECURE")
    public boolean isSecure() {
        return secure;
    }

    public RemoteCdModel setSecure(boolean secure) {
        this.secure = secure;
        return this;
    }

    @JacksonXmlProperty(localName = "RUN_JOB")
    public String getRunJob() {
        return runJob;
    }

    public RemoteCdModel setRunJob(String runJob) {
        this.runJob = runJob;
        return this;
    }

    @JacksonXmlProperty(localName = "RUN_TASK")
    public String getRunTask() {
        return runTask;
    }

    public RemoteCdModel setRunTask(String runTask) {
        this.runTask = runTask;
        return this;
    }

    @JacksonXmlProperty(localName = "CIPHER_SUITS")
    public List<CipherSuiteNameGetModel> getCipherSuits() {
        return cipherSuits;
    }

    public RemoteCdModel setCipherSuits(List<CipherSuiteNameGetModel> cipherSuits) {
        this.cipherSuits = cipherSuits;
        return this;
    }

    @JacksonXmlProperty(localName = "MAIN_FRAME_MODEL")
    public CdMainFrameModel getCdMainFrameModel() {
        return cdMainFrameModel;
    }

    public RemoteCdModel setCdMainFrameModel(CdMainFrameModel cdMainFrameModel) {
        this.cdMainFrameModel = cdMainFrameModel;
        return this;
    }

    public boolean isInternal() {
        return internal;
    }

    public RemoteCdModel setInternal(boolean internal) {
        this.internal = internal;
        return this;
    }

    public Boolean getIsInitiatingConsumer() {
        return isInitiatingConsumer;
    }

    public RemoteCdModel setIsInitiatingConsumer(Boolean initiatingConsumer) {
        isInitiatingConsumer = initiatingConsumer;
        return this;
    }

    public Boolean getIsInitiatingProducer() {
        return isInitiatingProducer;
    }

    public RemoteCdModel setIsInitiatingProducer(Boolean initiatingProducer) {
        isInitiatingProducer = initiatingProducer;
        return this;
    }

    public Boolean getIsListeningConsumer() {
        return isListeningConsumer;
    }

    public RemoteCdModel setIsListeningConsumer(Boolean listeningConsumer) {
        isListeningConsumer = listeningConsumer;
        return this;
    }

    public Boolean getIsListeningProducer() {
        return isListeningProducer;
    }

    public RemoteCdModel setIsListeningProducer(Boolean listeningProducer) {
        isListeningProducer = listeningProducer;
        return this;
    }

    public String getProfileUserName() {
        return profileUserName;
    }

    public RemoteCdModel setProfileUserName(String profileUserName) {
        this.profileUserName = profileUserName;
        return this;
    }

    public String getProfileUserPassword() {
        return profileUserPassword;
    }

    public RemoteCdModel setProfileUserPassword(String profileUserPassword) {
        this.profileUserPassword = profileUserPassword;
        return this;
    }

    public String getOutDirectory() {
        return outDirectory;
    }

    public RemoteCdModel setOutDirectory(String outDirectory) {
        this.outDirectory = outDirectory;
        return this;
    }

    public Boolean getInboundConnectionType() {
        return inboundConnectionType;
    }

    public RemoteCdModel setInboundConnectionType(Boolean inboundConnectionType) {
        this.inboundConnectionType = inboundConnectionType;
        return this;
    }

    public Boolean getOutboundConnectionType() {
        return outboundConnectionType;
    }

    public RemoteCdModel setOutboundConnectionType(Boolean outboundConnectionType) {
        this.outboundConnectionType = outboundConnectionType;
        return this;
    }

    public boolean isDoesUseSSH() {
        return doesUseSSH;
    }

    public RemoteCdModel setDoesUseSSH(boolean doesUseSSH) {
        this.doesUseSSH = doesUseSSH;
        return this;
    }

    public String getAuthenticationType() {
        return authenticationType;
    }

    public void setAuthenticationType(String authenticationType) {
        this.authenticationType = authenticationType;
    }

    public String getAuthenticationHost() {
        return authenticationHost;
    }

    public void setAuthenticationHost(String authenticationHost) {
        this.authenticationHost = authenticationHost;
    }

    public Boolean getAsciiArmor() {
        return asciiArmor;
    }

    public RemoteCdModel setAsciiArmor(Boolean asciiArmor) {
        this.asciiArmor = asciiArmor;
        return this;
    }

    public Boolean getDoesRequireEncryptedData() {
        return doesRequireEncryptedData;
    }

    public RemoteCdModel setDoesRequireEncryptedData(Boolean doesRequireEncryptedData) {
        this.doesRequireEncryptedData = doesRequireEncryptedData;
        return this;
    }

    public Boolean getDoesRequireSignedData() {
        return doesRequireSignedData;
    }

    public RemoteCdModel setDoesRequireSignedData(Boolean doesRequireSignedData) {
        this.doesRequireSignedData = doesRequireSignedData;
        return this;
    }

    public Boolean getTextMode() {
        return textMode;
    }

    public RemoteCdModel setTextMode(Boolean textMode) {
        this.textMode = textMode;
        return this;
    }
}
