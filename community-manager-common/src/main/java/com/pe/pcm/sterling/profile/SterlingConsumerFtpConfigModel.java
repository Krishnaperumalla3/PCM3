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

package com.pe.pcm.sterling.profile;

import java.io.Serializable;

/**
 * @author Chenchu Kiran.
 */
public class SterlingConsumerFtpConfigModel implements Serializable {

    private String connectionType;
    private String hostName;
    private String listenPort;
    private Integer controlPortRange;
    private Integer localPortRange;
    private String numberOfRetries;
    private String password;
    private String retryInterval;
    private Boolean uploadFileUnderTemporaryNameFirst = false;
    private String userName;

    public String getConnectionType() {
        return connectionType;
    }

    public SterlingConsumerFtpConfigModel setConnectionType(String connectionType) {
        this.connectionType = connectionType;
        return this;
    }

    public String getHostName() {
        return hostName;
    }

    public SterlingConsumerFtpConfigModel setHostName(String hostName) {
        this.hostName = hostName;
        return this;
    }

    public String getListenPort() {
        return listenPort;
    }

    public SterlingConsumerFtpConfigModel setListenPort(String listenPort) {
        this.listenPort = listenPort;
        return this;
    }

    public Integer getControlPortRange() {
        return controlPortRange;
    }

    public SterlingConsumerFtpConfigModel setControlPortRange(Integer controlPortRange) {
        this.controlPortRange = controlPortRange;
        return this;
    }

    public Integer getLocalPortRange() {
        return localPortRange;
    }

    public SterlingConsumerFtpConfigModel setLocalPortRange(Integer localPortRange) {
        this.localPortRange = localPortRange;
        return this;
    }

    public String getNumberOfRetries() {
        return numberOfRetries;
    }

    public SterlingConsumerFtpConfigModel setNumberOfRetries(String numberOfRetries) {
        this.numberOfRetries = numberOfRetries;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public SterlingConsumerFtpConfigModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getRetryInterval() {
        return retryInterval;
    }

    public SterlingConsumerFtpConfigModel setRetryInterval(String retryInterval) {
        this.retryInterval = retryInterval;
        return this;
    }

    public Boolean getUploadFileUnderTemporaryNameFirst() {
        return uploadFileUnderTemporaryNameFirst;
    }

    public SterlingConsumerFtpConfigModel setUploadFileUnderTemporaryNameFirst(Boolean uploadFileUnderTemporaryNameFirst) {
        this.uploadFileUnderTemporaryNameFirst = uploadFileUnderTemporaryNameFirst;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public SterlingConsumerFtpConfigModel setUserName(String userName) {
        this.userName = userName;
        return this;
    }
}
