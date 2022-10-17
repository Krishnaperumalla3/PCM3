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

package com.pe.pcm.pem.b2bproxy.consumerconfiguration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * @author Shameer.v.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsumerFtpConfiguration implements Serializable {

    private String baseDirectory;
    private String connectionType;
    private String controlPortRange;
    private String hostname;
    private int listenPort;
    private String localPortRange;
    private int numberOfRetries;
    private String password;
    private int retryInterval;
    private Boolean uploadFileUnderTemporaryNameFirst;
    private String username;


    public String getBaseDirectory() {
        return baseDirectory;
    }

    public ConsumerFtpConfiguration setBaseDirectory(String baseDirectory) {
        this.baseDirectory = baseDirectory;
        return this;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public ConsumerFtpConfiguration setConnectionType(String connectionType) {
        this.connectionType = connectionType;
        return this;
    }

    public String getControlPortRange() {
        return controlPortRange;
    }

    public ConsumerFtpConfiguration setControlPortRange(String controlPortRange) {
        this.controlPortRange = controlPortRange;
        return this;
    }

    public String getHostname() {
        return hostname;
    }

    public ConsumerFtpConfiguration setHostname(String hostname) {
        this.hostname = hostname;
        return this;
    }

    public int getListenPort() {
        return listenPort;
    }

    public ConsumerFtpConfiguration setListenPort(int listenPort) {
        this.listenPort = listenPort;
        return this;
    }

    public String getLocalPortRange() {
        return localPortRange;
    }

    public ConsumerFtpConfiguration setLocalPortRange(String localPortRange) {
        this.localPortRange = localPortRange;
        return this;
    }

    public int getNumberOfRetries() {
        return numberOfRetries;
    }

    public ConsumerFtpConfiguration setNumberOfRetries(int numberOfRetries) {
        this.numberOfRetries = numberOfRetries;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public ConsumerFtpConfiguration setPassword(String password) {
        this.password = password;
        return this;
    }

    public int getRetryInterval() {
        return retryInterval;
    }

    public ConsumerFtpConfiguration setRetryInterval(int retryInterval) {
        this.retryInterval = retryInterval;
        return this;
    }

    public Boolean getUploadFileUnderTemporaryNameFirst() {
        return uploadFileUnderTemporaryNameFirst;
    }

    public ConsumerFtpConfiguration setUploadFileUnderTemporaryNameFirst(Boolean uploadFileUnderTemporaryNameFirst) {
        this.uploadFileUnderTemporaryNameFirst = uploadFileUnderTemporaryNameFirst;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public ConsumerFtpConfiguration setUsername(String username) {
        this.username = username;
        return this;
    }
}
