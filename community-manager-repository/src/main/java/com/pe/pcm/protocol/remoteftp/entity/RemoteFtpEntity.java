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

package com.pe.pcm.protocol.remoteftp.entity;

import com.pe.pcm.audit.Auditable;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * @author Chenchu Kiran.
 */
@Entity
@Table(name = "PETPE_SFG_FTP")
public class RemoteFtpEntity extends Auditable {

    @Id
    private String pkId;
    @NotNull
    private String subscriberType;
    @NotNull
    private String subscriberId;
    @NotNull
    private String protocolType;
    private String profileId;
    private String userId;
    private String password;
    private String hostName;
    private String portNo;
    private String inDirectory;
    private String outDirectory;
    private String fileType;
    private String deleteAfterCollection;
    private String transferType;
    @NotNull
    private String poolingIntervalMins;
    private String certificateId;
    private String caCertId;
    private String knownHostKey;
    private String knownHostKeyId;
    private String userIdentityKey;
    private String userIdentityKeyId;
    private String isHubInfo;
    @NotNull
    private String isActive;
    private String adapterName;

    private String connectionType;
    private String retryInterval;
    private String noOfRetries;
    private String encryptionStrength;
    private String useCcc;
    private String useImplicitSsl;
    private String prfAuthType;
    private String authUserKeys;
    private String authUserkeyId;
    private Timestamp pwdLastChangeDone;
    private String mergeUser;

    public String getPkId() {
        return pkId;
    }

    public RemoteFtpEntity setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getSubscriberType() {
        return subscriberType;
    }

    public RemoteFtpEntity setSubscriberType(String subscriberType) {
        this.subscriberType = subscriberType;
        return this;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public RemoteFtpEntity setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
        return this;
    }

    public String getProtocolType() {
        return protocolType;
    }

    public RemoteFtpEntity setProtocolType(String protocolType) {
        this.protocolType = protocolType;
        return this;
    }

    public String getProfileId() {
        return profileId;
    }

    public RemoteFtpEntity setProfileId(String profileId) {
        this.profileId = profileId;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public RemoteFtpEntity setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RemoteFtpEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getHostName() {
        return hostName;
    }

    public RemoteFtpEntity setHostName(String hostName) {
        this.hostName = hostName;
        return this;
    }

    public String getPortNo() {
        return portNo;
    }

    public RemoteFtpEntity setPortNo(String portNo) {
        this.portNo = portNo;
        return this;
    }

    public String getInDirectory() {
        return inDirectory;
    }

    public RemoteFtpEntity setInDirectory(String inDirectory) {
        this.inDirectory = inDirectory;
        return this;
    }

    public String getOutDirectory() {
        return outDirectory;
    }

    public RemoteFtpEntity setOutDirectory(String outDirectory) {
        this.outDirectory = outDirectory;
        return this;
    }

    public String getFileType() {
        return fileType;
    }

    public RemoteFtpEntity setFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public String getDeleteAfterCollection() {
        return deleteAfterCollection;
    }

    public RemoteFtpEntity setDeleteAfterCollection(String deleteAfterCollection) {
        this.deleteAfterCollection = deleteAfterCollection;
        return this;
    }

    public String getTransferType() {
        return transferType;
    }

    public RemoteFtpEntity setTransferType(String transferType) {
        this.transferType = transferType;
        return this;
    }

    public String getPoolingIntervalMins() {
        return poolingIntervalMins;
    }

    public RemoteFtpEntity setPoolingIntervalMins(String poolingIntervalMins) {
        this.poolingIntervalMins = poolingIntervalMins;
        return this;
    }

    public String getCertificateId() {
        return certificateId;
    }

    public RemoteFtpEntity setCertificateId(String certificateId) {
        this.certificateId = certificateId;
        return this;
    }

    public String getKnownHostKey() {
        return knownHostKey;
    }

    public RemoteFtpEntity setKnownHostKey(String knownHostKey) {
        this.knownHostKey = knownHostKey;
        return this;
    }

    public String getUserIdentityKey() {
        return userIdentityKey;
    }

    public RemoteFtpEntity setUserIdentityKey(String userIdentityKey) {
        this.userIdentityKey = userIdentityKey;
        return this;
    }

    public String getCaCertId() {
        return caCertId;
    }

    public RemoteFtpEntity setCaCertId(String caCertId) {
        this.caCertId = caCertId;
        return this;
    }

    public String getKnownHostKeyId() {
        return knownHostKeyId;
    }

    public RemoteFtpEntity setKnownHostKeyId(String knownHostKeyId) {
        this.knownHostKeyId = knownHostKeyId;
        return this;
    }

    public String getUserIdentityKeyId() {
        return userIdentityKeyId;
    }

    public RemoteFtpEntity setUserIdentityKeyId(String userIdentityKeyId) {
        this.userIdentityKeyId = userIdentityKeyId;
        return this;
    }

    public String getIsHubInfo() {
        return isHubInfo;
    }

    public RemoteFtpEntity setIsHubInfo(String isHubInfo) {
        this.isHubInfo = isHubInfo;
        return this;
    }

    public String getIsActive() {
        return isActive;
    }

    public RemoteFtpEntity setIsActive(String isActive) {
        this.isActive = isActive;
        return this;
    }

    public String getAdapterName() {
        return adapterName;
    }

    public RemoteFtpEntity setAdapterName(String adapterName) {
        this.adapterName = adapterName;
        return this;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public RemoteFtpEntity setConnectionType(String connectionType) {
        this.connectionType = connectionType;
        return this;
    }

    public String getRetryInterval() {
        return retryInterval;
    }

    public RemoteFtpEntity setRetryInterval(String retryInterval) {
        this.retryInterval = retryInterval;
        return this;
    }

    public String getNoOfRetries() {
        return noOfRetries;
    }

    public RemoteFtpEntity setNoOfRetries(String noOfRetries) {
        this.noOfRetries = noOfRetries;
        return this;
    }

    public String getEncryptionStrength() {
        return encryptionStrength;
    }

    public RemoteFtpEntity setEncryptionStrength(String encryptionStrength) {
        this.encryptionStrength = encryptionStrength;
        return this;
    }

    public String getUseCcc() {
        return useCcc;
    }

    public RemoteFtpEntity setUseCcc(String useCcc) {
        this.useCcc = useCcc;
        return this;
    }

    public String getUseImplicitSsl() {
        return useImplicitSsl;
    }

    public RemoteFtpEntity setUseImplicitSsl(String useImplicitSsl) {
        this.useImplicitSsl = useImplicitSsl;
        return this;
    }

    public String getPrfAuthType() {
        return prfAuthType;
    }

    public RemoteFtpEntity setPrfAuthType(String prfAuthType) {
        this.prfAuthType = prfAuthType;
        return this;
    }

    public String getAuthUserKeys() {
        return authUserKeys;
    }

    public RemoteFtpEntity setAuthUserKeys(String authUserKeys) {
        this.authUserKeys = authUserKeys;
        return this;
    }

    public String getAuthUserkeyId() {
        return authUserkeyId;
    }

    public RemoteFtpEntity setAuthUserkeyId(String authUserkeyId) {
        this.authUserkeyId = authUserkeyId;
        return this;
    }

    public Timestamp getPwdLastChangeDone() {
        return pwdLastChangeDone;
    }

    public RemoteFtpEntity setPwdLastChangeDone(Timestamp pwdLastChangeDone) {
        this.pwdLastChangeDone = pwdLastChangeDone;
        return this;
    }

    public String getMergeUser() {
        return mergeUser;
    }

    public RemoteFtpEntity setMergeUser(String mergeUser) {
        this.mergeUser = mergeUser;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("pkId", pkId)
                .append("subscriberType", subscriberType)
                .append("subscriberId", subscriberId)
                .append("protocolType", protocolType)
                .append("profileId", profileId)
                .append("userId", userId)
                .append("password", password)
                .append("hostName", hostName)
                .append("portNo", portNo)
                .append("inDirectory", inDirectory)
                .append("outDirectory", outDirectory)
                .append("fileType", fileType)
                .append("deleteAfterCollection", deleteAfterCollection)
                .append("transferType", transferType)
                .append("poolingIntervalMins", poolingIntervalMins)
                .append("certificateId", certificateId)
                .append("caCertId", caCertId)
                .append("knownHostKey", knownHostKey)
                .append("knownHostKeyId", knownHostKeyId)
                .append("userIdentityKey", userIdentityKey)
                .append("userIdentityKeyId", userIdentityKeyId)
                .append("isHubInfo", isHubInfo)
                .append("isActive", isActive)
                .append("adapterName", adapterName)
                .append("connectionType", connectionType)
                .append("retryInterval", retryInterval)
                .append("noOfRetries", noOfRetries)
                .append("encryptionStrength", encryptionStrength)
                .append("useCcc", useCcc)
                .append("useImplicitSsl", useImplicitSsl)
                .append("prfAuthType", prfAuthType)
                .append("authUserKeys", authUserKeys)
                .append("authUserkeyId", authUserkeyId)
                .append("pwdLastChangeDone", pwdLastChangeDone)
                .toString();
    }
}
