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

package com.pe.pcm.protocol.smtp.entity;

import com.pe.pcm.audit.Auditable;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Shameer.
 */

@Entity
@Table(name = "PETPE_SMTP")
public class SmtpEntity extends Auditable {

    @Id
    private String pkId;
    private String subscriberType;
    private String subscriberId;
    private String name;
    private String accessProtocol;
    private String mailServer;
    private String mailServerPort;
    private String userName;
    private String password;
    private String connectionRetries;
    private String retryInterval;
    private String maxMsgsSession;
    private String removeInboxMailMsgs;
    private String ssl;
    private String keyCertPassPhrase;
    private String cipherStrength;
    private String keyCert;
    private String caCertificates;
    private String uriName;
    private String poolingInterval;

    public String getPkId() {
        return pkId;
    }

    public SmtpEntity setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getSubscriberType() {
        return subscriberType;
    }

    public SmtpEntity setSubscriberType(String subscriberType) {
        this.subscriberType = subscriberType;
        return this;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public SmtpEntity setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
        return this;
    }

    public String getName() {
        return name;
    }

    public SmtpEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getAccessProtocol() {
        return accessProtocol;
    }

    public SmtpEntity setAccessProtocol(String accessProtocol) {
        this.accessProtocol = accessProtocol;
        return this;
    }

    public String getMailServer() {
        return mailServer;
    }

    public SmtpEntity setMailServer(String mailServer) {
        this.mailServer = mailServer;
        return this;
    }

    public String getMailServerPort() {
        return mailServerPort;
    }

    public SmtpEntity setMailServerPort(String mailServerPort) {
        this.mailServerPort = mailServerPort;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public SmtpEntity setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public SmtpEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getConnectionRetries() {
        return connectionRetries;
    }

    public SmtpEntity setConnectionRetries(String connectionRetries) {
        this.connectionRetries = connectionRetries;
        return this;
    }

    public String getRetryInterval() {
        return retryInterval;
    }

    public SmtpEntity setRetryInterval(String retryInterval) {
        this.retryInterval = retryInterval;
        return this;
    }

    public String getMaxMsgsSession() {
        return maxMsgsSession;
    }

    public SmtpEntity setMaxMsgsSession(String maxMsgsSession) {
        this.maxMsgsSession = maxMsgsSession;
        return this;
    }

    public String getRemoveInboxMailMsgs() {
        return removeInboxMailMsgs;
    }

    public SmtpEntity setRemoveInboxMailMsgs(String removeInboxMailMsgs) {
        this.removeInboxMailMsgs = removeInboxMailMsgs;
        return this;
    }

    public String getSsl() {
        return ssl;
    }

    public SmtpEntity setSsl(String ssl) {
        this.ssl = ssl;
        return this;
    }

    public String getkeyCertPassPhrase() {
        return keyCertPassPhrase;
    }

    public SmtpEntity setkeyCertPassPhrase(String keyCertPassPhrase) {
        this.keyCertPassPhrase = keyCertPassPhrase;
        return this;
    }

    public String getCipherStrength() {
        return cipherStrength;
    }

    public SmtpEntity setCipherStrength(String cipherStrength) {
        this.cipherStrength = cipherStrength;
        return this;
    }

    public String getKeyCert() {
        return keyCert;
    }

    public SmtpEntity setKeyCert(String keyCert) {
        this.keyCert = keyCert;
        return this;
    }

    public String getCaCertificates() {
        return caCertificates;
    }

    public SmtpEntity setCaCertificates(String caCertificates) {
        this.caCertificates = caCertificates;
        return this;
    }

    public String getUriName() {
        return uriName;
    }

    public SmtpEntity setUriName(String uriName) {
        this.uriName = uriName;
        return this;
    }

    public String getPoolingInterval() {
        return poolingInterval;
    }

    public SmtpEntity setPoolingInterval(String poolingInterval) {
        this.poolingInterval = poolingInterval;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("pkId", pkId)
                .append("subscriberType", subscriberType)
                .append("subscriberId", subscriberId)
                .append("name", name)
                .append("accessProtocol", accessProtocol)
                .append("mailServer", mailServer)
                .append("mailServerPort", mailServerPort)
                .append("userName", userName)
                .append("password", password)
                .append("connectionRetries", connectionRetries)
                .append("retryInterval", retryInterval)
                .append("maxMsgsSession", maxMsgsSession)
                .append("removeInboxMailMsgs", removeInboxMailMsgs)
                .append("ssl", ssl)
                .append("keyCertPassPhrase", keyCertPassPhrase)
                .append("cipherStrength", cipherStrength)
                .append("keyCert", keyCert)
                .append("caCertificates", caCertificates)
                .append("uriName", uriName)
                .append("poolingInterval", poolingInterval)
                .toString();
    }
}
