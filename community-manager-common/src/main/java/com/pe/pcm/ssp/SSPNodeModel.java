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

package com.pe.pcm.ssp;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class SSPNodeModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private Address addresses;
    @XmlElement
    private String compression;
    @XmlElement
    private String description;
    @XmlElement
    private String destinationServiceName;
    @XmlElement
    private String forceToUnlock;
    @XmlElement
    private String formatVersion;
    @XmlElement
    private String knownHostKey;
    @XmlElement
    private String knownHostKeyStore;
    @XmlElement
    private String logLevel;
    @XmlElement
    private String name;
    @XmlElement
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<OutboundACLNode> outboundACLNodes;
    @XmlElement
    private String password;
    @XmlElement
    private String preferredCipher;
    @XmlElement
    private String preferredKeyExchange;
    @XmlElement
    private String preferredMAC;
    @XmlElement
    private String peerAddressPattern;
    @XmlElement
    private String remoteClientKey;
    @XmlElement
    private String remoteClientKeyStore;
    @XmlElement
    private String policyId;
    @XmlElement
    private String routingName;
    @XmlElement
    private String routingNode;
    @XmlElement
    private Integer port;
    @XmlElement
    private String secureConnection;
    @XmlElement
    private String userId;
    @XmlElement
    private String validDestination;
    @XmlElement
    private String validDestinationPort;
    @XmlElement
    private SecureTagModel sslInfo;
    @XmlElement
    private String serverAddress;
    @XmlElement
    private String stepInjection;
    @XmlElement
    private Integer tcpTimeout;
    @XmlElement
    private String verStamp;

    public SSPNodeModel() {
    }

    public SSPNodeModel(Address addresses, String compression, String description, String destinationServiceName, String forceToUnlock, String formatVersion,
                        String knownHostKey, String knownHostKeyStore, String logLevel, String name, List<OutboundACLNode> outboundACLNodes, String password, String preferredCipher, String preferredKeyExchange,
                        String preferredMAC, String peerAddressPattern, String remoteClientKey, String remoteClientKeyStore, String policyId, String routingName, String routingNode,
                        Integer port, String secureConnection, String userId, String validDestination, String validDestinationPort,
                        SecureTagModel sslInfo, String serverAddress, String stepInjection, Integer tcpTimeout, String verStamp) {
        this.addresses = addresses;
        this.compression = compression;
        this.description = description;
        this.destinationServiceName = destinationServiceName;
        this.forceToUnlock = forceToUnlock;
        this.formatVersion = formatVersion;
        this.knownHostKey = knownHostKey;
        this.knownHostKeyStore = knownHostKeyStore;
        this.logLevel = logLevel;
        this.name = name;
        this.outboundACLNodes = outboundACLNodes;
        this.password = password;
        this.preferredCipher = preferredCipher;
        this.preferredKeyExchange = preferredKeyExchange;
        this.preferredMAC = preferredMAC;
        this.peerAddressPattern = peerAddressPattern;
        this.remoteClientKey = remoteClientKey;
        this.remoteClientKeyStore = remoteClientKeyStore;
        this.policyId = policyId;
        this.routingName = routingName;
        this.routingNode = routingNode;
        this.port = port;
        this.secureConnection = secureConnection;
        this.userId = userId;
        this.validDestination = validDestination;
        this.validDestinationPort = validDestinationPort;
        this.sslInfo = sslInfo;
        this.serverAddress = serverAddress;
        this.stepInjection = stepInjection;
        this.tcpTimeout = tcpTimeout;
        this.verStamp = verStamp;
    }

    public String getDescription() {
        return description;
    }

    public SSPNodeModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getDestinationServiceName() {
        return destinationServiceName;
    }

    public SSPNodeModel setDestinationServiceName(String destinationServiceName) {
        this.destinationServiceName = destinationServiceName;
        return this;
    }

    public String getForceToUnlock() {
        return forceToUnlock;
    }

    public SSPNodeModel setForceToUnlock(String forceToUnlock) {
        this.forceToUnlock = forceToUnlock;
        return this;
    }

    public String getFormatVersion() {
        return formatVersion;
    }

    public SSPNodeModel setFormatVersion(String formatVersion) {
        this.formatVersion = formatVersion;
        return this;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public SSPNodeModel setLogLevel(String logLevel) {
        this.logLevel = logLevel;
        return this;
    }

    public String getName() {
        return name;
    }

    public SSPNodeModel setName(String name) {
        this.name = name;
        return this;
    }

    public List<OutboundACLNode> getOutboundACLNodes() {
        return outboundACLNodes;
    }

    public SSPNodeModel setOutboundACLNodes(List<OutboundACLNode> outboundACLNodes) {
        this.outboundACLNodes = outboundACLNodes;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public SSPNodeModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPeerAddressPattern() {
        return peerAddressPattern;
    }

    public SSPNodeModel setPeerAddressPattern(String peerAddressPattern) {
        this.peerAddressPattern = peerAddressPattern;
        return this;
    }

    public String getSecureConnection() {
        return secureConnection;
    }

    public SSPNodeModel setSecureConnection(String secureConnection) {
        this.secureConnection = secureConnection;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public SSPNodeModel setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getValidDestination() {
        return validDestination;
    }

    public SSPNodeModel setValidDestination(String validDestination) {
        this.validDestination = validDestination;
        return this;
    }

    public String getValidDestinationPort() {
        return validDestinationPort;
    }

    public SSPNodeModel setValidDestinationPort(String validDestinationPort) {
        this.validDestinationPort = validDestinationPort;
        return this;
    }

    public SecureTagModel getSslInfo() {
        return sslInfo;
    }

    public SSPNodeModel setSslInfo(SecureTagModel sslInfo) {
        this.sslInfo = sslInfo;
        return this;
    }

    public String getPolicyId() {
        return policyId;
    }

    public SSPNodeModel setPolicyId(String policyId) {
        this.policyId = policyId;
        return this;
    }

    public String getVerStamp() {
        return verStamp;
    }

    public SSPNodeModel setVerStamp(String verStamp) {
        this.verStamp = verStamp;
        return this;
    }

    public Address getAddresses() {
        return addresses;
    }

    public SSPNodeModel setAddresses(Address addresses) {
        this.addresses = addresses;
        return this;
    }

    public Integer getPort() {
        return port;
    }

    public SSPNodeModel setPort(Integer port) {
        this.port = port;
        return this;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public SSPNodeModel setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
        return this;
    }

    public Integer getTcpTimeout() {
        return tcpTimeout;
    }

    public SSPNodeModel setTcpTimeout(Integer tcpTimeout) {
        this.tcpTimeout = tcpTimeout;
        return this;
    }

    public String getKnownHostKey() {
        return knownHostKey;
    }

    public SSPNodeModel setKnownHostKey(String knownHostKey) {
        this.knownHostKey = knownHostKey;
        return this;
    }

    public String getKnownHostKeyStore() {
        return knownHostKeyStore;
    }

    public SSPNodeModel setKnownHostKeyStore(String knownHostKeyStore) {
        this.knownHostKeyStore = knownHostKeyStore;
        return this;
    }

    public String getPreferredCipher() {
        return preferredCipher;
    }

    public SSPNodeModel setPreferredCipher(String preferredCipher) {
        this.preferredCipher = preferredCipher;
        return this;
    }

    public String getPreferredKeyExchange() {
        return preferredKeyExchange;
    }

    public SSPNodeModel setPreferredKeyExchange(String preferredKeyExchange) {
        this.preferredKeyExchange = preferredKeyExchange;
        return this;
    }

    public String getPreferredMAC() {
        return preferredMAC;
    }

    public SSPNodeModel setPreferredMAC(String preferredMAC) {
        this.preferredMAC = preferredMAC;
        return this;
    }

    public String getRoutingNode() {
        return routingNode;
    }

    public SSPNodeModel setRoutingNode(String routingNode) {
        this.routingNode = routingNode;
        return this;
    }

    public String getCompression() {
        return compression;
    }

    public SSPNodeModel setCompression(String compression) {
        this.compression = compression;
        return this;
    }

    public String getRemoteClientKey() {
        return remoteClientKey;
    }

    public SSPNodeModel setRemoteClientKey(String remoteClientKey) {
        this.remoteClientKey = remoteClientKey;
        return this;
    }

    public String getRemoteClientKeyStore() {
        return remoteClientKeyStore;
    }

    public SSPNodeModel setRemoteClientKeyStore(String remoteClientKeyStore) {
        this.remoteClientKeyStore = remoteClientKeyStore;
        return this;
    }

    public String getRoutingName() {
        return routingName;
    }

    public SSPNodeModel setRoutingName(String routingName) {
        this.routingName = routingName;
        return this;
    }

    public String getStepInjection() {
        return stepInjection;
    }

    public SSPNodeModel setStepInjection(String stepInjection) {
        this.stepInjection = stepInjection;
        return this;
    }

    @Override
    public String toString() {
        return "SSPNodeModel{" +
                "addresses=" + addresses +
                ", compression='" + compression + '\'' +
                ", description='" + description + '\'' +
                ", destinationServiceName='" + destinationServiceName + '\'' +
                ", forceToUnlock='" + forceToUnlock + '\'' +
                ", formatVersion='" + formatVersion + '\'' +
                ", knownHostKey='" + knownHostKey + '\'' +
                ", knownHostKeyStore='" + knownHostKeyStore + '\'' +
                ", logLevel='" + logLevel + '\'' +
                ", name='" + name + '\'' +
                ", outboundACLNodes=" + outboundACLNodes +
                ", password='" + password + '\'' +
                ", preferredCipher='" + preferredCipher + '\'' +
                ", preferredKeyExchange='" + preferredKeyExchange + '\'' +
                ", preferredMAC='" + preferredMAC + '\'' +
                ", peerAddressPattern='" + peerAddressPattern + '\'' +
                ", remoteClientKey='" + remoteClientKey + '\'' +
                ", remoteClientKeyStore='" + remoteClientKeyStore + '\'' +
                ", policyId='" + policyId + '\'' +
                ", routingName='" + routingName + '\'' +
                ", routingNode='" + routingNode + '\'' +
                ", port=" + port +
                ", secureConnection='" + secureConnection + '\'' +
                ", userId='" + userId + '\'' +
                ", validDestination='" + validDestination + '\'' +
                ", validDestinationPort='" + validDestinationPort + '\'' +
                ", sslInfo=" + sslInfo +
                ", serverAddress='" + serverAddress + '\'' +
                ", stepInjection='" + stepInjection + '\'' +
                ", tcpTimeout=" + tcpTimeout +
                ", verStamp='" + verStamp + '\'' +
                '}';
    }
}
