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

import static com.pe.pcm.utils.PCMConstants.PRAGMA_EDGE_S;

/**
 * @author Shameer.v.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProducerFtpsConfiguration implements Serializable {

    private String caCertificates;
    private String connectionType;
    private String directory;
    private String encryptionStrength;
    private String hostname;
    private int listenPort;
    private int numberOfRetries;
    private String password;
    private int retryInterval;
    private String siteCommand;
    private Boolean useCcc;
    private Boolean useImplicitSsl;
    private String username;

    public ProducerFtpsConfiguration(RemoteProfileModel remoteProfileModel, Boolean isUpdate) {
        this.caCertificates = remoteProfileModel.getCertificateId();
        this.connectionType = remoteProfileModel.getConnectionType();
        this.directory = remoteProfileModel.getSubscriberType().equals(PCMConstants.TP) ? remoteProfileModel.getInDirectory() : remoteProfileModel.getOutDirectory();
        this.encryptionStrength = remoteProfileModel.getEncryptionStrength();
        this.hostname = remoteProfileModel.getRemoteHost();
        this.listenPort = Integer.parseInt(remoteProfileModel.getRemotePort());
        this.numberOfRetries = Integer.parseInt(remoteProfileModel.getNoOfRetries());
        this.password = isUpdate ? (remoteProfileModel.getPassword().equalsIgnoreCase(PRAGMA_EDGE_S) ? null : remoteProfileModel.getPassword()) : remoteProfileModel.getPassword();
        this.retryInterval = Integer.parseInt(remoteProfileModel.getRetryInterval());
        this.siteCommand = remoteProfileModel.getSiteCommand();
        this.useCcc = remoteProfileModel.getUseCCC();
        this.useImplicitSsl = remoteProfileModel.getUseImplicitSSL();
        this.username = remoteProfileModel.getUserName();
    }

    public String getCaCertificates() {
        return caCertificates;
    }

    public ProducerFtpsConfiguration setCaCertificates(String caCertificates) {
        this.caCertificates = caCertificates;
        return this;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public ProducerFtpsConfiguration setConnectionType(String connectionType) {
        this.connectionType = connectionType;
        return this;
    }

    public String getDirectory() {
        return directory;
    }

    public ProducerFtpsConfiguration setDirectory(String directory) {
        this.directory = directory;
        return this;
    }

    public String getEncryptionStrength() {
        return encryptionStrength;
    }

    public ProducerFtpsConfiguration setEncryptionStrength(String encryptionStrength) {
        this.encryptionStrength = encryptionStrength;
        return this;
    }

    public String getHostname() {
        return hostname;
    }

    public ProducerFtpsConfiguration setHostname(String hostname) {
        this.hostname = hostname;
        return this;
    }

    public int getListenPort() {
        return listenPort;
    }

    public ProducerFtpsConfiguration setListenPort(int listenPort) {
        this.listenPort = listenPort;
        return this;
    }

    public int getNumberOfRetries() {
        return numberOfRetries;
    }

    public ProducerFtpsConfiguration setNumberOfRetries(int numberOfRetries) {
        this.numberOfRetries = numberOfRetries;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public ProducerFtpsConfiguration setPassword(String password) {
        this.password = password;
        return this;
    }

    public int getRetryInterval() {
        return retryInterval;
    }

    public ProducerFtpsConfiguration setRetryInterval(int retryInterval) {
        this.retryInterval = retryInterval;
        return this;
    }

    public String getSiteCommand() {
        return siteCommand;
    }

    public ProducerFtpsConfiguration setSiteCommand(String siteCommand) {
        this.siteCommand = siteCommand;
        return this;
    }

    public Boolean getUseCcc() {
        return useCcc;
    }

    public ProducerFtpsConfiguration setUseCcc(Boolean useCcc) {
        this.useCcc = useCcc;
        return this;
    }

    public Boolean getUseImplicitSsl() {
        return useImplicitSsl;
    }

    public ProducerFtpsConfiguration setUseImplicitSsl(Boolean useImplicitSsl) {
        this.useImplicitSsl = useImplicitSsl;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public ProducerFtpsConfiguration setUsername(String username) {
        this.username = username;
        return this;
    }
}
