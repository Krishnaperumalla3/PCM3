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

package com.pe.pcm.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pe.pcm.profile.ProfileModel;

/**
 * @author Shameer.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SmtpModel extends ProfileModel {

    private String name;
    private String accessProtocol;
    private String mailServer;
    private String mailServerPort;
    private String userName;
    private String password;
    private String connectionRetries;
    private String retryInterval;
    private String maxMsgsSession;
    private String removeMailMsgs;
    private String ssl;
    private String keyCertPassPhrase;
    private String cipherStrength;
    private String keyCert;
    private String caCertificates;
    private String uriName;
    private String poolingInterval;


    public String getName() {
        return name;
    }

    public SmtpModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getAccessProtocol() {
        return accessProtocol;
    }

    public SmtpModel setAccessProtocol(String accessProtocol) {
        this.accessProtocol = accessProtocol;
        return this;
    }

    public String getMailServer() {
        return mailServer;
    }

    public SmtpModel setMailServer(String mailServer) {
        this.mailServer = mailServer;
        return this;
    }

    public String getMailServerPort() {
        return mailServerPort;
    }

    public SmtpModel setMailServerPort(String mailServerPort) {
        this.mailServerPort = mailServerPort;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public SmtpModel setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public SmtpModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConnectionRetries() {
        return connectionRetries;
    }

    public SmtpModel setConnectionRetries(String connectionRetries) {
        this.connectionRetries = connectionRetries;
        return this;
    }

    public String getRetryInterval() {
        return retryInterval;
    }

    public SmtpModel setRetryInterval(String retryInterval) {
        this.retryInterval = retryInterval;
        return this;
    }

    public String getMaxMsgsSession() {
        return maxMsgsSession;
    }

    public SmtpModel setMaxMsgsSession(String maxMsgsSession) {
        this.maxMsgsSession = maxMsgsSession;
        return this;
    }

    public String getRemoveMailMsgs() {
        return removeMailMsgs;
    }

    public SmtpModel setRemoveMailMsgs(String removeMailMsgs) {
        this.removeMailMsgs = removeMailMsgs;
        return this;
    }

    public String getSsl() {
        return ssl;
    }

    public SmtpModel setSsl(String ssl) {
        this.ssl = ssl;
        return this;
    }

    public String getKeyCertPassPhrase() {
        return keyCertPassPhrase;
    }

    public SmtpModel setKeyCertPassPhrase(String keyCertPassPhrase) {
        this.keyCertPassPhrase = keyCertPassPhrase;
        return this;
    }

    public String getCipherStrength() {
        return cipherStrength;
    }

    public SmtpModel setCipherStrength(String cipherStrength) {
        this.cipherStrength = cipherStrength;
        return this;
    }

    public String getKeyCert() {
        return keyCert;
    }

    public SmtpModel setKeyCert(String keyCert) {
        this.keyCert = keyCert;
        return this;
    }

    public String getCaCertificates() {
        return caCertificates;
    }

    public SmtpModel setCaCertificates(String caCertificates) {
        this.caCertificates = caCertificates;
        return this;
    }

    public String getUriName() {
        return uriName;
    }

    public SmtpModel setUriName(String uriName) {
        this.uriName = uriName;
        return this;
    }

    public String getPoolingInterval() {
        return poolingInterval;
    }

    public SmtpModel setPoolingInterval(String poolingInterval) {
        this.poolingInterval = poolingInterval;
        return this;
    }

}
