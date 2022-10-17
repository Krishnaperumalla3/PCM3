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

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Chenchu Kiran.
 */
@Entity
@Table(name = "PETPE_SFG_FTP_STAGING")
public class RemoteFtpStagingEntity extends Auditable {

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
    private String inDirectory;
    private String outDirectory;
    private String fileType;
    private String deleteAfterCollection;
    private String transferType;
    @NotNull
    private String poolingIntervalMins;
    private String certificateId;
    private String knownHostKey;
    private String userIdentityKey;
    private String isHubInfo;
    @NotNull
    private String isActive;
    private String adapterName;

    public String getPkId() {
        return pkId;
    }

    public RemoteFtpStagingEntity setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getSubscriberType() {
        return subscriberType;
    }

    public RemoteFtpStagingEntity setSubscriberType(String subscriberType) {
        this.subscriberType = subscriberType;
        return this;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public RemoteFtpStagingEntity setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
        return this;
    }

    public String getProtocolType() {
        return protocolType;
    }

    public RemoteFtpStagingEntity setProtocolType(String protocolType) {
        this.protocolType = protocolType;
        return this;
    }

    public String getProfileId() {
        return profileId;
    }

    public RemoteFtpStagingEntity setProfileId(String profileId) {
        this.profileId = profileId;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public RemoteFtpStagingEntity setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RemoteFtpStagingEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getInDirectory() {
        return inDirectory;
    }

    public RemoteFtpStagingEntity setInDirectory(String inDirectory) {
        this.inDirectory = inDirectory;
        return this;
    }

    public String getOutDirectory() {
        return outDirectory;
    }

    public RemoteFtpStagingEntity setOutDirectory(String outDirectory) {
        this.outDirectory = outDirectory;
        return this;
    }

    public String getFileType() {
        return fileType;
    }

    public RemoteFtpStagingEntity setFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public String getDeleteAfterCollection() {
        return deleteAfterCollection;
    }

    public RemoteFtpStagingEntity setDeleteAfterCollection(String deleteAfterCollection) {
        this.deleteAfterCollection = deleteAfterCollection;
        return this;
    }

    public String getTransferType() {
        return transferType;
    }

    public RemoteFtpStagingEntity setTransferType(String transferType) {
        this.transferType = transferType;
        return this;
    }

    public String getPoolingIntervalMins() {
        return poolingIntervalMins;
    }

    public RemoteFtpStagingEntity setPoolingIntervalMins(String poolingIntervalMins) {
        this.poolingIntervalMins = poolingIntervalMins;
        return this;
    }

    public String getCertificateId() {
        return certificateId;
    }

    public RemoteFtpStagingEntity setCertificateId(String certificateId) {
        this.certificateId = certificateId;
        return this;
    }

    public String getKnownHostKey() {
        return knownHostKey;
    }

    public RemoteFtpStagingEntity setKnownHostKey(String knownHostKey) {
        this.knownHostKey = knownHostKey;
        return this;
    }

    public String getUserIdentityKey() {
        return userIdentityKey;
    }

    public RemoteFtpStagingEntity setUserIdentityKey(String userIdentityKey) {
        this.userIdentityKey = userIdentityKey;
        return this;
    }

    public String getIsHubInfo() {
        return isHubInfo;
    }

    public RemoteFtpStagingEntity setIsHubInfo(String isHubInfo) {
        this.isHubInfo = isHubInfo;
        return this;
    }

    public String getIsActive() {
        return isActive;
    }

    public RemoteFtpStagingEntity setIsActive(String isActive) {
        this.isActive = isActive;
        return this;
    }

    public String getAdapterName() {
        return adapterName;
    }

    public RemoteFtpStagingEntity setAdapterName(String adapterName) {
        this.adapterName = adapterName;
        return this;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "RemoteFtpEntity [pkId=" + pkId + ", subscriberType=" + subscriberType + ", subscriberId=" + subscriberId
                + ", protocolType=" + protocolType + ", profileId=" + profileId + ", userId=" + userId + ", password="
                + password + ", inDirectory=" + inDirectory + ", outDirectory=" + outDirectory + ", fileType="
                + fileType + ", deleteAfterCollection=" + deleteAfterCollection + ", transferType=" + transferType
                + ", poolingIntervalMins=" + poolingIntervalMins + ", certificateId=" + certificateId
                + ", knownHostKey=" + knownHostKey + ", userIdentityKey=" + userIdentityKey + ", isHubInfo=" + isHubInfo
                + ", isActive=" + isActive + ", adapterName=" + adapterName + "]";
    }


}
