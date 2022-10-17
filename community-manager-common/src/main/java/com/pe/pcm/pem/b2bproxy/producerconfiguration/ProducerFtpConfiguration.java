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

package com.pe.pcm.pem.b2bproxy.producerconfiguration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pe.pcm.protocol.RemoteProfileModel;
import com.pe.pcm.utils.PCMConstants;

import java.io.Serializable;

import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.PCMConstants.PRAGMA_EDGE_S;

/**
 * @author Shameer.v.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProducerFtpConfiguration implements Serializable {

    private String connectionType;
    private String directory;
    private String hostname;
    private int listenPort;
    private int numberOfRetries;
    private String password;
    private int retryInterval;
    private String siteCommand;
    private String username;

    public ProducerFtpConfiguration(RemoteProfileModel remoteProfileModel, Boolean isUpdate) {
        this.directory = remoteProfileModel.getSubscriberType().equals(PCMConstants.TP) ? remoteProfileModel.getInDirectory() : remoteProfileModel.getOutDirectory();
        this.connectionType = remoteProfileModel.getConnectionType();
        this.hostname = remoteProfileModel.getRemoteHost();
        this.listenPort = Integer.parseInt(remoteProfileModel.getRemotePort());
        this.numberOfRetries = Integer.parseInt(remoteProfileModel.getNoOfRetries());
        this.password = isUpdate ? (remoteProfileModel.getPassword().equalsIgnoreCase(PRAGMA_EDGE_S) ? null : remoteProfileModel.getPassword()) : remoteProfileModel.getPassword();
        this.retryInterval = Integer.parseInt(remoteProfileModel.getRetryInterval());
        this.siteCommand = remoteProfileModel.getSiteCommand();
        this.username = remoteProfileModel.getUserName();
    }

    public String getConnectionType() {
        return connectionType;
    }

    public ProducerFtpConfiguration setConnectionType(String connectionType) {
        this.connectionType = connectionType;
        return this;
    }

    public String getDirectory() {
        return directory;
    }

    public ProducerFtpConfiguration setDirectory(String directory) {
        this.directory = directory;
        return this;
    }

    public String getHostname() {
        return hostname;
    }

    public ProducerFtpConfiguration setHostname(String hostname) {
        this.hostname = hostname;
        return this;
    }

    public int getListenPort() {
        return listenPort;
    }

    public ProducerFtpConfiguration setListenPort(int listenPort) {
        this.listenPort = listenPort;
        return this;
    }

    public int getNumberOfRetries() {
        return numberOfRetries;
    }

    public ProducerFtpConfiguration setNumberOfRetries(int numberOfRetries) {
        this.numberOfRetries = numberOfRetries;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public ProducerFtpConfiguration setPassword(String password) {
        this.password = password;
        return this;
    }

    public int getRetryInterval() {
        return retryInterval;
    }

    public ProducerFtpConfiguration setRetryInterval(int retryInterval) {
        this.retryInterval = retryInterval;
        return this;
    }

    public String getSiteCommand() {
        return siteCommand;
    }

    public ProducerFtpConfiguration setSiteCommand(String siteCommand) {
        this.siteCommand = siteCommand;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public ProducerFtpConfiguration setUsername(String username) {
        this.username = username;
        return this;
    }
}
