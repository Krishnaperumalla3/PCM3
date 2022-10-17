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

package com.pe.pcm.protocol.as2.si.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Chenchu Kiran.
 */
@Entity
@Table(name = "SFTP_PROF")
public class SftpProfileEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String profileId;
    private String name;
    private int connectionRetries;
    private int responseTimeout;
    private String remoteHost;
    private int remotePort;
    private String remoteUser;
    /*This column is useless as per my understanding*/
    @Column(name = "KHOST_KEY_ID")
    private String knownHostKeyId;
    @Column(name = "LUSER_KEY_ID")
    private String userIdentityKeyName;
    private String remotePassword;
    private int retryDelay;
    private String preferredCipher;
    private String preferredMac;
    private String preferredAuth;
    private String compressBool;
    private String localPortRange;
    private String changeDirectory;
    private String characterEncoding;

    public String getProfileId() {
        return profileId;
    }

    public SftpProfileEntity setProfileId(String profileId) {
        this.profileId = profileId;
        return this;
    }

    public String getName() {
        return name;
    }

    public SftpProfileEntity setName(String name) {
        this.name = name;
        return this;
    }

    public int getConnectionRetries() {
        return connectionRetries;
    }

    public SftpProfileEntity setConnectionRetries(int connectionRetries) {
        this.connectionRetries = connectionRetries;
        return this;
    }

    public int getResponseTimeout() {
        return responseTimeout;
    }

    public SftpProfileEntity setResponseTimeout(int responseTimeout) {
        this.responseTimeout = responseTimeout;
        return this;
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public SftpProfileEntity setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
        return this;
    }

    public int getRemotePort() {
        return remotePort;
    }

    public SftpProfileEntity setRemotePort(int remotePort) {
        this.remotePort = remotePort;
        return this;
    }

    public String getRemoteUser() {
        return remoteUser;
    }

    public SftpProfileEntity setRemoteUser(String remoteUser) {
        this.remoteUser = remoteUser;
        return this;
    }

    public String getKnownHostKeyId() {
        return knownHostKeyId;
    }

    public SftpProfileEntity setKnownHostKeyId(String knownHostKeyId) {
        this.knownHostKeyId = knownHostKeyId;
        return this;
    }

    public String getUserIdentityKeyName() {
        return userIdentityKeyName;
    }

    public SftpProfileEntity setUserIdentityKeyName(String userIdentityKeyName) {
        this.userIdentityKeyName = userIdentityKeyName;
        return this;
    }

    public String getRemotePassword() {
        return remotePassword;
    }

    public SftpProfileEntity setRemotePassword(String remotePassword) {
        this.remotePassword = remotePassword;
        return this;
    }

    public int getRetryDelay() {
        return retryDelay;
    }

    public SftpProfileEntity setRetryDelay(int retryDelay) {
        this.retryDelay = retryDelay;
        return this;
    }

    public String getPreferredCipher() {
        return preferredCipher;
    }

    public SftpProfileEntity setPreferredCipher(String preferredCipher) {
        this.preferredCipher = preferredCipher;
        return this;
    }

    public String getPreferredMac() {
        return preferredMac;
    }

    public SftpProfileEntity setPreferredMac(String preferredMac) {
        this.preferredMac = preferredMac;
        return this;
    }

    public String getPreferredAuth() {
        return preferredAuth;
    }

    public SftpProfileEntity setPreferredAuth(String preferredAuth) {
        this.preferredAuth = preferredAuth;
        return this;
    }

    public String getCompressBool() {
        return compressBool;
    }

    public SftpProfileEntity setCompressBool(String compressBool) {
        this.compressBool = compressBool;
        return this;
    }

    public String getLocalPortRange() {
        return localPortRange;
    }

    public SftpProfileEntity setLocalPortRange(String localPortRange) {
        this.localPortRange = localPortRange;
        return this;
    }

    public String getChangeDirectory() {
        return changeDirectory;
    }

    public SftpProfileEntity setChangeDirectory(String changeDirectory) {
        this.changeDirectory = changeDirectory;
        return this;
    }

    public String getCharacterEncoding() {
        return characterEncoding;
    }

    public SftpProfileEntity setCharacterEncoding(String characterEncoding) {
        this.characterEncoding = characterEncoding;
        return this;
    }

    @Override
    public String toString() {
        return "SftpProfileEntity{" +
                "profileId='" + profileId + '\'' +
                ", name='" + name + '\'' +
                ", connectionRetries=" + connectionRetries +
                ", responseTimeout=" + responseTimeout +
                ", remoteHost='" + remoteHost + '\'' +
                ", remotePort=" + remotePort +
                ", remoteUser='" + remoteUser + '\'' +
                ", knownHostKeyId='" + knownHostKeyId + '\'' +
                ", userIdentityKeyName='" + userIdentityKeyName + '\'' +
                ", remotePassword='" + remotePassword + '\'' +
                ", retryDelay=" + retryDelay +
                ", preferredCipher='" + preferredCipher + '\'' +
                ", preferredMac='" + preferredMac + '\'' +
                ", preferredAuth='" + preferredAuth + '\'' +
                ", compressBool='" + compressBool + '\'' +
                ", localPortRange='" + localPortRange + '\'' +
                ", changeDirectory='" + changeDirectory + '\'' +
                ", characterEncoding='" + characterEncoding + '\'' +
                '}';
    }
}
