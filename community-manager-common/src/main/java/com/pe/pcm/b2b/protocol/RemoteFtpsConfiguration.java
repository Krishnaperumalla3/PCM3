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
import com.pe.pcm.protocol.RemoteProfileModel;
import com.pe.pcm.utils.PCMConstants;

import java.io.Serializable;

import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.PCMConstants.PRAGMA_EDGE_S;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RemoteFtpsConfiguration implements Serializable {

    private String assignedCaCertificates;
    private String baseDirectory;
    private String connectionType;
    private String controlPortRange;                    //Not using by UI
    private String encryptionStrength = "ALL";
    private String hostname;
    private Integer listenPort;
    private String localPortRange;                      //Not using by UI
    private Integer numberOfRetries;
    private String username;
    private String password;
    private Integer retryInterval;
    private boolean uploadFileUnderTemporaryNameFirst = false;  // false
    private boolean useCcc;                             // false
    private boolean useImplicitSsl;                     // false

    public RemoteFtpsConfiguration(RemoteProfileModel remoteProfileModel, Boolean isUpdate) {
        if (remoteProfileModel.getSubscriberType().equals(PCMConstants.TP)) {
            if (isNotNull(remoteProfileModel.getOutDirectory())) {
                this.baseDirectory = remoteProfileModel.getOutDirectory();
            } else {
                this.baseDirectory = remoteProfileModel.getInDirectory();
            }
        } else {
            if (isNotNull(remoteProfileModel.getInDirectory())) {
                this.baseDirectory = remoteProfileModel.getInDirectory();
            } else {
                this.baseDirectory = remoteProfileModel.getOutDirectory();
            }
        }
        this.assignedCaCertificates = remoteProfileModel.getCertificateId();
        this.connectionType = remoteProfileModel.getConnectionType();
        this.encryptionStrength = remoteProfileModel.getEncryptionStrength();
        this.hostname = remoteProfileModel.getRemoteHost();
        this.listenPort = Integer.valueOf(remoteProfileModel.getRemotePort());
        this.numberOfRetries = Integer.valueOf(remoteProfileModel.getNoOfRetries());
        this.username = remoteProfileModel.getUserName();
        this.password = isUpdate ? (remoteProfileModel.getPassword().equalsIgnoreCase(PRAGMA_EDGE_S) ? null : remoteProfileModel.getPassword()) : remoteProfileModel.getPassword();
        this.retryInterval = Integer.valueOf(remoteProfileModel.getRetryInterval());
        this.useCcc = remoteProfileModel.getUseCCC();
        this.useImplicitSsl = remoteProfileModel.getUseImplicitSSL();
    }

    public String getAssignedCaCertificates() {
        return assignedCaCertificates;
    }

    public RemoteFtpsConfiguration setAssignedCaCertificates(String assignedCaCertificates) {
        this.assignedCaCertificates = assignedCaCertificates;
        return this;
    }

    public String getBaseDirectory() {
        return baseDirectory;
    }

    public RemoteFtpsConfiguration setBaseDirectory(String baseDirectory) {
        this.baseDirectory = baseDirectory;
        return this;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public RemoteFtpsConfiguration setConnectionType(String connectionType) {
        this.connectionType = connectionType;
        return this;
    }

    public String getControlPortRange() {
        return controlPortRange;
    }

    public RemoteFtpsConfiguration setControlPortRange(String controlPortRange) {
        this.controlPortRange = controlPortRange;
        return this;
    }

    public String getEncryptionStrength() {
        return encryptionStrength;
    }

    public RemoteFtpsConfiguration setEncryptionStrength(String encryptionStrength) {
        this.encryptionStrength = encryptionStrength;
        return this;
    }

    public String getHostname() {
        return hostname;
    }

    public RemoteFtpsConfiguration setHostname(String hostname) {
        this.hostname = hostname;
        return this;
    }

    public Integer getListenPort() {
        return listenPort;
    }

    public RemoteFtpsConfiguration setListenPort(Integer listenPort) {
        this.listenPort = listenPort;
        return this;
    }

    public String getLocalPortRange() {
        return localPortRange;
    }

    public RemoteFtpsConfiguration setLocalPortRange(String localPortRange) {
        this.localPortRange = localPortRange;
        return this;
    }

    public Integer getNumberOfRetries() {
        return numberOfRetries;
    }

    public RemoteFtpsConfiguration setNumberOfRetries(Integer numberOfRetries) {
        this.numberOfRetries = numberOfRetries;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public RemoteFtpsConfiguration setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RemoteFtpsConfiguration setPassword(String password) {
        this.password = password;
        return this;
    }

    public Integer getRetryInterval() {
        return retryInterval;
    }

    public RemoteFtpsConfiguration setRetryInterval(Integer retryInterval) {
        this.retryInterval = retryInterval;
        return this;
    }

    public boolean isUploadFileUnderTemporaryNameFirst() {
        return uploadFileUnderTemporaryNameFirst;
    }

    public RemoteFtpsConfiguration setUploadFileUnderTemporaryNameFirst(boolean uploadFileUnderTemporaryNameFirst) {
        this.uploadFileUnderTemporaryNameFirst = uploadFileUnderTemporaryNameFirst;
        return this;
    }

    public boolean isUseCcc() {
        return useCcc;
    }

    public RemoteFtpsConfiguration setUseCcc(boolean useCcc) {
        this.useCcc = useCcc;
        return this;
    }

    public boolean isUseImplicitSsl() {
        return useImplicitSsl;
    }

    public RemoteFtpsConfiguration setUseImplicitSsl(boolean useImplicitSsl) {
        this.useImplicitSsl = useImplicitSsl;
        return this;
    }
}
