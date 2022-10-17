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

import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.protocol.RemoteProfileModel;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.pe.pcm.utils.PCMConstants.PRAGMA_EDGE_S;

public class RemoteSftpProfile implements Serializable {

    private String profileName;
    private String remoteHost;
    private String remotePort;
    private String remoteUser;
    private List<CommunityManagerNameModel> knownHostKeys;
    private String preferredAuthenticationType;
    private String preferredCipher;
    private String sshPassword;
    private String userIdentityKey;
    private String directory;
    private String characterEncoding;
    private String compression;
    private String connectionRetryCount;
    private String retryDelay;
    private String responseTimeOut;
    private String preferredMacAlgorithm;
    private String localPortRange;

    public RemoteSftpProfile(RemoteProfileModel remoteProfileModel, Boolean isUpdate) {

        this.profileName = remoteProfileModel.getProfileName();
        if (remoteProfileModel.getCustomProfileName() != null &&
                remoteProfileModel.getCustomProfileName().replace(" ", "").length() > 0) {
            this.profileName = remoteProfileModel.getCustomProfileName();
        }
        this.remoteHost = remoteProfileModel.getRemoteHost();
        this.remotePort = remoteProfileModel.getRemotePort();
        this.remoteUser = remoteProfileModel.getUserName();
        if (!StringUtils.isEmpty(remoteProfileModel.getKnownHostKey())) {
            this.knownHostKeys = new ArrayList<>();
            knownHostKeys.add(new CommunityManagerNameModel(remoteProfileModel.getKnownHostKey()));
        } else {
            this.knownHostKeys = null;
        }
        this.preferredAuthenticationType = remoteProfileModel.getPreferredAuthenticationType();
        this.preferredCipher = remoteProfileModel.getPreferredCipher();
        if (isUpdate) {
            if (remoteProfileModel.getPassword() != null &&
                    remoteProfileModel.getPassword().replace(" ", "").length() > 0) {
                this.sshPassword = remoteProfileModel.getPassword().equalsIgnoreCase(PRAGMA_EDGE_S) ? null : remoteProfileModel.getPassword();
            }
        } else {
            this.sshPassword = remoteProfileModel.getPassword();
        }
        this.userIdentityKey = remoteProfileModel.getUserIdentityKey();
        this.directory = remoteProfileModel.getSubscriberType().equals("TP") ? remoteProfileModel.getInDirectory() : remoteProfileModel.getOutDirectory();
        this.characterEncoding = remoteProfileModel.getCharacterEncoding();
        this.compression = remoteProfileModel.getCompression();
        this.connectionRetryCount = remoteProfileModel.getConnectionRetryCount();
        this.retryDelay = remoteProfileModel.getRetryDelay();
        this.responseTimeOut = remoteProfileModel.getResponseTimeOut();
        this.preferredMacAlgorithm = remoteProfileModel.getPreferredMacAlgorithm();
        this.localPortRange = remoteProfileModel.getLocalPortRange();
    }

    public String getProfileName() {
        return profileName;
    }

    public RemoteSftpProfile setProfileName(String profileName) {
        this.profileName = profileName;
        return this;
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public RemoteSftpProfile setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
        return this;
    }

    public String getRemotePort() {
        return remotePort;
    }

    public RemoteSftpProfile setRemotePort(String remotePort) {
        this.remotePort = remotePort;
        return this;
    }

    public String getRemoteUser() {
        return remoteUser;
    }

    public RemoteSftpProfile setRemoteUser(String remoteUser) {
        this.remoteUser = remoteUser;
        return this;
    }

    public List<CommunityManagerNameModel> getKnownHostKeys() {
        return knownHostKeys;
    }

    public RemoteSftpProfile setKnownHostKeys(List<CommunityManagerNameModel> knownHostKeys) {
        this.knownHostKeys = knownHostKeys;
        return this;
    }

    public String getPreferredAuthenticationType() {
        return preferredAuthenticationType;
    }

    public RemoteSftpProfile setPreferredAuthenticationType(String preferredAuthenticationType) {
        this.preferredAuthenticationType = preferredAuthenticationType;
        return this;
    }

    public String getPreferredCipher() {
        return preferredCipher;
    }

    public RemoteSftpProfile setPreferredCipher(String preferredCipher) {
        this.preferredCipher = preferredCipher;
        return this;
    }

    public String getSshPassword() {
        return sshPassword;
    }

    public RemoteSftpProfile setSshPassword(String sshPassword) {
        this.sshPassword = sshPassword;
        return this;
    }

    public String getUserIdentityKey() {
        return userIdentityKey;
    }

    public RemoteSftpProfile setUserIdentityKey(String userIdentityKey) {
        this.userIdentityKey = userIdentityKey;
        return this;
    }

    public String getDirectory() {
        return directory;
    }

    public RemoteSftpProfile setDirectory(String directory) {
        this.directory = directory;
        return this;
    }

    public String getCharacterEncoding() {
        return characterEncoding;
    }

    public RemoteSftpProfile setCharacterEncoding(String characterEncoding) {
        this.characterEncoding = characterEncoding;
        return this;
    }

    public String getCompression() {
        return compression;
    }

    public RemoteSftpProfile setCompression(String compression) {
        this.compression = compression;
        return this;
    }

    public String getConnectionRetryCount() {
        return connectionRetryCount;
    }

    public RemoteSftpProfile setConnectionRetryCount(String connectionRetryCount) {
        this.connectionRetryCount = connectionRetryCount;
        return this;
    }

    public String getRetryDelay() {
        return retryDelay;
    }

    public RemoteSftpProfile setRetryDelay(String retryDelay) {
        this.retryDelay = retryDelay;
        return this;
    }

    public String getResponseTimeOut() {
        return responseTimeOut;
    }

    public RemoteSftpProfile setResponseTimeOut(String responseTimeOut) {
        this.responseTimeOut = responseTimeOut;
        return this;
    }

    public String getPreferredMacAlgorithm() {
        return preferredMacAlgorithm;
    }

    public RemoteSftpProfile setPreferredMacAlgorithm(String preferredMacAlgorithm) {
        this.preferredMacAlgorithm = preferredMacAlgorithm;
        return this;
    }

    public String getLocalPortRange() {
        return localPortRange;
    }

    public RemoteSftpProfile setLocalPortRange(String localPortRange) {
        this.localPortRange = localPortRange;
        return this;
    }
}
