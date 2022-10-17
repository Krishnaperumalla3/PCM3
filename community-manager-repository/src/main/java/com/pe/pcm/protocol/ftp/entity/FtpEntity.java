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

package com.pe.pcm.protocol.ftp.entity;

import com.pe.pcm.audit.Auditable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Chenchu Kiran.
 *
 */
@Entity
@Table(name = "PETPE_FTP")
public class FtpEntity extends Auditable {

    @Id
    private String pkId;

    @NotNull
    private String subscriberType;

    @NotNull
    private String subscriberId;

    @NotNull
    private String protocolType;

    @NotNull
    private String hostName;

    @NotNull
    private String portNo;

    @NotNull
    private String userId;

    private String password;

    private String inDirectory;

    private String outDirectory;

    private String fileType;

    @NotNull
    private String deleteAfterCollection;

    private String transferType;

    @NotNull
    private String poolingIntervalMins;

    @NotNull
    private String certificateId;

    private String caCertName;
    private String knownHostKeyId;
    private String sshIdentityKeyName;

    private String knownHostKey;

    private String userIdentityKey;

    @NotNull
    private String isHubInfo;

    @NotNull
    private String isActive;

    private String adapterName;

    private String cwdUp;
    private String quote;
    private String site;
    private String connectionType;
    private String mbDestination;
    private String mbDestinationLookup;
    private String sslSocket;
    private String sslCipher;
    private String sshAuthentication;
    private String sshCipher;
    private String sshCompression;
    private String sshMac;
    private String sshIdentityKeyId;


    public String getPkId() {
        return pkId;
    }

    public FtpEntity setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getSubscriberType() {
        return subscriberType;
    }

    public FtpEntity setSubscriberType(String subscriberType) {
        this.subscriberType = subscriberType;
        return this;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public FtpEntity setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
        return this;
    }

    public String getProtocolType() {
        return protocolType;
    }

    public FtpEntity setProtocolType(String protocolType) {
        this.protocolType = protocolType;
        return this;
    }

    public String getHostName() {
        return hostName;
    }

    public FtpEntity setHostName(String hostName) {
        this.hostName = hostName;
        return this;
    }

    public String getPortNo() {
        return portNo;
    }

    public FtpEntity setPortNo(String portNo) {
        this.portNo = portNo;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public FtpEntity setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public FtpEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getInDirectory() {
        return inDirectory;
    }

    public FtpEntity setInDirectory(String inDirectory) {
        this.inDirectory = inDirectory;
        return this;
    }

    public String getOutDirectory() {
        return outDirectory;
    }

    public FtpEntity setOutDirectory(String outDirectory) {
        this.outDirectory = outDirectory;
        return this;
    }

    public String getFileType() {
        return fileType;
    }

    public FtpEntity setFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public String getDeleteAfterCollection() {
        return deleteAfterCollection;
    }

    public FtpEntity setDeleteAfterCollection(String deleteAfterCollection) {
        this.deleteAfterCollection = deleteAfterCollection;
        return this;
    }

    public String getTransferType() {
        return transferType;
    }

    public FtpEntity setTransferType(String transferType) {
        this.transferType = transferType;
        return this;
    }

    public String getPoolingIntervalMins() {
        return poolingIntervalMins;
    }

    public FtpEntity setPoolingIntervalMins(String poolingIntervalMins) {
        this.poolingIntervalMins = poolingIntervalMins;
        return this;
    }

    public String getCertificateId() {
        return certificateId;
    }

    public FtpEntity setCertificateId(String certificateId) {
        this.certificateId = certificateId;
        return this;
    }

    public String getKnownHostKey() {
        return knownHostKey;
    }

    public FtpEntity setKnownHostKey(String knownHostKey) {
        this.knownHostKey = knownHostKey;
        return this;
    }

    public String getUserIdentityKey() {
        return userIdentityKey;
    }

    public FtpEntity setUserIdentityKey(String userIdentityKey) {
        this.userIdentityKey = userIdentityKey;
        return this;
    }

    public String getIsHubInfo() {
        return isHubInfo;
    }

    public FtpEntity setIsHubInfo(String isHubInfo) {
        this.isHubInfo = isHubInfo;
        return this;
    }

    public String getIsActive() {
        return isActive;
    }

    public FtpEntity setIsActive(String isActive) {
        this.isActive = isActive;
        return this;
    }

    public String getAdapterName() {
        return adapterName;
    }

    public FtpEntity setAdapterName(String adapterName) {
        this.adapterName = adapterName;
        return this;
    }

    public String getCwdUp() {
        return cwdUp;
    }

    public FtpEntity setCwdUp(String cwdUp) {
        this.cwdUp = cwdUp;
        return this;
    }

    public String getQuote() {
        return quote;
    }

    public FtpEntity setQuote(String quote) {
        this.quote = quote;
        return this;
    }

    public String getSite() {
        return site;
    }

    public FtpEntity setSite(String site) {
        this.site = site;
        return this;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public FtpEntity setConnectionType(String connectionType) {
        this.connectionType = connectionType;
        return this;
    }

    public String getMbDestination() {
        return mbDestination;
    }

    public FtpEntity setMbDestination(String mbDestination) {
        this.mbDestination = mbDestination;
        return this;
    }

    public String getMbDestinationLookup() {
        return mbDestinationLookup;
    }

    public FtpEntity setMbDestinationLookup(String mbDestinationLookup) {
        this.mbDestinationLookup = mbDestinationLookup;
        return this;
    }

    public String getSslSocket() {
        return sslSocket;
    }

    public FtpEntity setSslSocket(String sslSocket) {
        this.sslSocket = sslSocket;
        return this;
    }

    public String getSslCipher() {
        return sslCipher;
    }

    public FtpEntity setSslCipher(String sslCipher) {
        this.sslCipher = sslCipher;
        return this;
    }

    public String getSshAuthentication() {
        return sshAuthentication;
    }

    public FtpEntity setSshAuthentication(String sshAuthentication) {
        this.sshAuthentication = sshAuthentication;
        return this;
    }

    public String getSshCipher() {
        return sshCipher;
    }

    public FtpEntity setSshCipher(String sshCipher) {
        this.sshCipher = sshCipher;
        return this;
    }

    public String getSshCompression() {
        return sshCompression;
    }

    public FtpEntity setSshCompression(String sshCompression) {
        this.sshCompression = sshCompression;
        return this;
    }

    public String getSshMac() {
        return sshMac;
    }

    public FtpEntity setSshMac(String sshMac) {
        this.sshMac = sshMac;
        return this;
    }

    public String getSshIdentityKeyId() {
        return sshIdentityKeyId;
    }

    public FtpEntity setSshIdentityKeyId(String sshIdentityKeyId) {
        this.sshIdentityKeyId = sshIdentityKeyId;
        return this;
    }

    public String getCaCertName() {
        return caCertName;
    }

    public FtpEntity setCaCertName(String caCertName) {
        this.caCertName = caCertName;
        return this;
    }

    public String getKnownHostKeyId() {
        return knownHostKeyId;
    }

    public FtpEntity setKnownHostKeyId(String knownHostKeyId) {
        this.knownHostKeyId = knownHostKeyId;
        return this;
    }

    public String getSshIdentityKeyName() {
        return sshIdentityKeyName;
    }

    public FtpEntity setSshIdentityKeyName(String sshIdentityKeyName) {
        this.sshIdentityKeyName = sshIdentityKeyName;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FtpEntity{");
        sb.append("pkId='").append(pkId).append('\'');
        sb.append(", subscriberType='").append(subscriberType).append('\'');
        sb.append(", subscriberId='").append(subscriberId).append('\'');
        sb.append(", protocolType='").append(protocolType).append('\'');
        sb.append(", hostName='").append(hostName).append('\'');
        sb.append(", portNo='").append(portNo).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", inDirectory='").append(inDirectory).append('\'');
        sb.append(", outDirectory='").append(outDirectory).append('\'');
        sb.append(", fileType='").append(fileType).append('\'');
        sb.append(", deleteAfterCollection='").append(deleteAfterCollection).append('\'');
        sb.append(", transferType='").append(transferType).append('\'');
        sb.append(", poolingIntervalMins='").append(poolingIntervalMins).append('\'');
        sb.append(", certificateId='").append(certificateId).append('\'');
        sb.append(", caCertName='").append(caCertName).append('\'');
        sb.append(", knownHostKeyId='").append(knownHostKeyId).append('\'');
        sb.append(", sshIdentityKeyName='").append(sshIdentityKeyName).append('\'');
        sb.append(", knownHostKey='").append(knownHostKey).append('\'');
        sb.append(", userIdentityKey='").append(userIdentityKey).append('\'');
        sb.append(", isHubInfo='").append(isHubInfo).append('\'');
        sb.append(", isActive='").append(isActive).append('\'');
        sb.append(", adapterName='").append(adapterName).append('\'');
        sb.append(", cwdUp='").append(cwdUp).append('\'');
        sb.append(", quote='").append(quote).append('\'');
        sb.append(", site='").append(site).append('\'');
        sb.append(", connectionType='").append(connectionType).append('\'');
        sb.append(", mbDestination='").append(mbDestination).append('\'');
        sb.append(", mbDestinationLookup='").append(mbDestinationLookup).append('\'');
        sb.append(", sslSocket='").append(sslSocket).append('\'');
        sb.append(", sslCipher='").append(sslCipher).append('\'');
        sb.append(", sshAuthentication='").append(sshAuthentication).append('\'');
        sb.append(", sshCipher='").append(sshCipher).append('\'');
        sb.append(", sshCompression='").append(sshCompression).append('\'');
        sb.append(", sshMac='").append(sshMac).append('\'');
        sb.append(", sshIdentityKeyId='").append(sshIdentityKeyId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
