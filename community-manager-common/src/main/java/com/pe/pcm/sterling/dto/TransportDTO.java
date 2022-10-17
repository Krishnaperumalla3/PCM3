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

package com.pe.pcm.sterling.dto;

import com.pe.pcm.enums.Protocol;

import java.io.Serializable;

/**
 * @author Chenchu Kiran.
 */
public class TransportDTO implements Serializable {

    /*Keys*/
    private String objectId;
    private String entityId;
    private String transportKey;
    private String emailId;
    private String profileId;
    private String profileName;
    private Boolean hubInfo;
    private String keyCertification;
    private String keyCertificatePassphrase;
    private String endPoint;
    private String sslOption;
    private String cipherStrength;
    private String responseTimeout;
    private String mailBox;
    private String directory;
    private String userId;
    private String password;
    private String hostName;
    private String portNumber;
    private String securityProtocol;
    private String protocolMode;
    private String receivingProtocol;
    private String profileType;
    private String transferMode;
    private boolean normalProfile;
    private Protocol protocol;

    public String getTransferMode() {
        return transferMode;
    }

    public TransportDTO setTransferMode(String transferMode) {
        this.transferMode = transferMode;
        return this;
    }

    public String getObjectId() {
        return objectId;
    }

    public TransportDTO setObjectId(String objectId) {
        this.objectId = objectId;
        return this;
    }

    public String getEntityId() {
        return entityId;
    }

    public TransportDTO setEntityId(String entityId) {
        this.entityId = entityId;
        return this;
    }

    public String getTransportKey() {
        return transportKey;
    }

    public TransportDTO setTransportKey(String transportKey) {
        this.transportKey = transportKey;
        return this;
    }

    public String getEmailId() {
        return emailId;
    }

    public TransportDTO setEmailId(String emailId) {
        this.emailId = emailId;
        return this;
    }

    public String getProfileId() {
        return profileId;
    }

    public TransportDTO setProfileId(String profileId) {
        this.profileId = profileId;
        return this;
    }

    public String getProfileName() {
        return profileName;
    }

    public TransportDTO setProfileName(String profileName) {
        this.profileName = profileName;
        return this;
    }

    public Boolean getHubInfo() {
        return hubInfo;
    }

    public TransportDTO setHubInfo(Boolean hubInfo) {
        this.hubInfo = hubInfo;
        return this;
    }

    public String getKeyCertification() {
        return keyCertification;
    }

    public TransportDTO setKeyCertification(String keyCertification) {
        this.keyCertification = keyCertification;
        return this;
    }

    public String getKeyCertificatePassphrase() {
        return keyCertificatePassphrase;
    }

    public TransportDTO setKeyCertificatePassphrase(String keyCertificatePassphrase) {
        this.keyCertificatePassphrase = keyCertificatePassphrase;
        return this;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public TransportDTO setEndPoint(String endPoint) {
        this.endPoint = endPoint;
        return this;
    }

    public String getSslOption() {
        return sslOption;
    }

    public TransportDTO setSslOption(String sslOption) {
        this.sslOption = sslOption;
        return this;
    }

    public String getCipherStrength() {
        return cipherStrength;
    }

    public TransportDTO setCipherStrength(String cipherStrength) {
        this.cipherStrength = cipherStrength;
        return this;
    }

    public String getResponseTimeout() {
        return responseTimeout;
    }

    public TransportDTO setResponseTimeout(String responseTimeout) {
        this.responseTimeout = responseTimeout;
        return this;
    }

    public String getMailBox() {
        return mailBox;
    }

    public TransportDTO setMailBox(String mailBox) {
        this.mailBox = mailBox;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public TransportDTO setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public TransportDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getHostName() {
        return hostName;
    }

    public TransportDTO setHostName(String hostName) {
        this.hostName = hostName;
        return this;
    }

    public String getPortNumber() {
        return portNumber;
    }

    public TransportDTO setPortNumber(String portNumber) {
        this.portNumber = portNumber;
        return this;
    }

    public String getDirectory() {
        return directory;
    }

    public TransportDTO setDirectory(String directory) {
        this.directory = directory;
        return this;
    }

    public String getSecurityProtocol() {
        return securityProtocol;
    }

    public TransportDTO setSecurityProtocol(String securityProtocol) {
        this.securityProtocol = securityProtocol;
        return this;
    }

    public String getProtocolMode() {
        return protocolMode;
    }

    public TransportDTO setProtocolMode(String protocolMode) {
        this.protocolMode = protocolMode;
        return this;
    }

    public String getReceivingProtocol() {
        return receivingProtocol;
    }

    public TransportDTO setReceivingProtocol(String receivingProtocol) {
        this.receivingProtocol = receivingProtocol;
        return this;
    }

    public String getProfileType() {
        return profileType;
    }

    public TransportDTO setProfileType(String profileType) {
        this.profileType = profileType;
        return this;
    }

    public boolean isNormalProfile() {
        return normalProfile;
    }

    public TransportDTO setNormalProfile(boolean normalProfile) {
        this.normalProfile = normalProfile;
        return this;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public TransportDTO setProtocol(Protocol protocol) {
        this.protocol = protocol;
        return this;
    }
}
