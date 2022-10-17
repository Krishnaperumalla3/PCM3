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
public class SterlingProducerFtpConfigModel implements Serializable {

    private String connectionType;
    private String userName;
    private String password;
    private String hostName;
    private String listenPort;
    private String numberOfRetries;
    private String retryInterval;
    private String siteCommand;

    public String getConnectionType() {
        return connectionType;
    }

    public SterlingProducerFtpConfigModel setConnectionType(String connectionType) {
        this.connectionType = connectionType;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public SterlingProducerFtpConfigModel setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public SterlingProducerFtpConfigModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getHostName() {
        return hostName;
    }

    public SterlingProducerFtpConfigModel setHostName(String hostName) {
        this.hostName = hostName;
        return this;
    }

    public String getListenPort() {
        return listenPort;
    }

    public SterlingProducerFtpConfigModel setListenPort(String listenPort) {
        this.listenPort = listenPort;
        return this;
    }

    public String getNumberOfRetries() {
        return numberOfRetries;
    }

    public SterlingProducerFtpConfigModel setNumberOfRetries(String numberOfRetries) {
        this.numberOfRetries = numberOfRetries;
        return this;
    }

    public String getRetryInterval() {
        return retryInterval;
    }

    public SterlingProducerFtpConfigModel setRetryInterval(String retryInterval) {
        this.retryInterval = retryInterval;
        return this;
    }

    public String getSiteCommand() {
        return siteCommand;
    }

    public SterlingProducerFtpConfigModel setSiteCommand(String siteCommand) {
        this.siteCommand = siteCommand;
        return this;
    }
}
