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

package com.pe.pcm.protocol.as2.entity;

import com.pe.pcm.audit.Auditable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "PETPE_AS2")
public class As2Entity extends Auditable {

    @Id
    private String pkId;

    private String subscriberType;

    private String subscriberId;

    @NotNull
    @Column(name = "as2_identifier")
    private String as2Identifier;

    private String url;

    private String senderId;

    private String senderQualifier;

    private String payloadSecurity;

    private String payloadEncryptionAlgorithm;

    private String isHttps;

    private String username;

    private String password;

    private String sslType;

    private String encryptionAlgorithm;

    private String signatureAlgorithm;

    private String isMdnRequired;

    private String mdnType;

    private String mdnEncryption;

    private String receiptToAddress;

    @NotNull
    private String isActive;

    private String isHubInfo;

    private String identityName;

    private String profileName;

    private String sciProfileId;

    private String httpClientAdapter;

    private String keyCertPassphrase;

    private String cipherStrength;

    private String payloadType;

    private String mimeType;

    private String mimeSubType;

    private String compressData;

    private String emailAddress;

    private String emailHost;

    private String emailPort;

    private String responseTimeout;

    private String keyCert;

    private String keyCertPath;

    private String exchgCert;

    private String exchgCertPath;

    private String signingCert;

    private String signingCertPath;

    private String caCert;

    private String caCertPath;

    private String retryInterval;

    private String noOfRetries;
    private String caCertId;
    private String exchgCertName;
    private String signingCertName;

    public String getPkId() {
        return pkId;
    }

    public As2Entity setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getSubscriberType() {
        return subscriberType;
    }

    public As2Entity setSubscriberType(String subscriberType) {
        this.subscriberType = subscriberType;
        return this;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public As2Entity setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
        return this;
    }

    public String getAs2Identifier() {
        return as2Identifier;
    }

    public As2Entity setAs2Identifier(String as2Identifier) {
        this.as2Identifier = as2Identifier;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public As2Entity setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getSenderId() {
        return senderId;
    }

    public As2Entity setSenderId(String senderId) {
        this.senderId = senderId;
        return this;
    }

    public String getSenderQualifier() {
        return senderQualifier;
    }

    public As2Entity setSenderQualifier(String senderQualifier) {
        this.senderQualifier = senderQualifier;
        return this;
    }

    public String getPayloadSecurity() {
        return payloadSecurity;
    }

    public As2Entity setPayloadSecurity(String payloadSecurity) {
        this.payloadSecurity = payloadSecurity;
        return this;
    }

    public String getPayloadEncryptionAlgorithm() {
        return payloadEncryptionAlgorithm;
    }

    public As2Entity setPayloadEncryptionAlgorithm(String payloadEncryptionAlgorithm) {
        this.payloadEncryptionAlgorithm = payloadEncryptionAlgorithm;
        return this;
    }

    public String getIsHttps() {
        return isHttps;
    }

    public As2Entity setIsHttps(String isHttps) {
        this.isHttps = isHttps;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public As2Entity setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public As2Entity setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getSslType() {
        return sslType;
    }

    public As2Entity setSslType(String sslType) {
        this.sslType = sslType;
        return this;
    }

    public String getEncryptionAlgorithm() {
        return encryptionAlgorithm;
    }

    public As2Entity setEncryptionAlgorithm(String encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
        return this;
    }

    public String getSignatureAlgorithm() {
        return signatureAlgorithm;
    }

    public As2Entity setSignatureAlgorithm(String signatureAlgorithm) {
        this.signatureAlgorithm = signatureAlgorithm;
        return this;
    }

    public String getIsMdnRequired() {
        return isMdnRequired;
    }

    public As2Entity setIsMdnRequired(String isMdnRequired) {
        this.isMdnRequired = isMdnRequired;
        return this;
    }

    public String getMdnType() {
        return mdnType;
    }

    public As2Entity setMdnType(String mdnType) {
        this.mdnType = mdnType;
        return this;
    }

    public String getMdnEncryption() {
        return mdnEncryption;
    }

    public As2Entity setMdnEncryption(String mdnEncryption) {
        this.mdnEncryption = mdnEncryption;
        return this;
    }

    public String getReceiptToAddress() {
        return receiptToAddress;
    }

    public As2Entity setReceiptToAddress(String receiptToAddress) {
        this.receiptToAddress = receiptToAddress;
        return this;
    }

    public String getIsActive() {
        return isActive;
    }

    public As2Entity setIsActive(String isActive) {
        this.isActive = isActive;
        return this;
    }

    public String getIsHubInfo() {
        return isHubInfo;
    }

    public As2Entity setIsHubInfo(String isHubInfo) {
        this.isHubInfo = isHubInfo;
        return this;
    }

    public String getIdentityName() {
        return identityName;
    }

    public As2Entity setIdentityName(String identityName) {
        this.identityName = identityName;
        return this;
    }

    public String getProfileName() {
        return profileName;
    }

    public As2Entity setProfileName(String profileName) {
        this.profileName = profileName;
        return this;
    }

    public String getSciProfileId() {
        return sciProfileId;
    }

    public As2Entity setSciProfileId(String sciProfileId) {
        this.sciProfileId = sciProfileId;
        return this;
    }

    public String getHttpClientAdapter() {
        return httpClientAdapter;
    }

    public As2Entity setHttpClientAdapter(String httpClientAdapter) {
        this.httpClientAdapter = httpClientAdapter;
        return this;
    }

    public String getKeyCertPassphrase() {
        return keyCertPassphrase;
    }

    public As2Entity setKeyCertPassphrase(String keyCertPassphrase) {
        this.keyCertPassphrase = keyCertPassphrase;
        return this;
    }

    public String getCipherStrength() {
        return cipherStrength;
    }

    public As2Entity setCipherStrength(String cipherStrength) {
        this.cipherStrength = cipherStrength;
        return this;
    }

    public String getPayloadType() {
        return payloadType;
    }

    public As2Entity setPayloadType(String payloadType) {
        this.payloadType = payloadType;
        return this;
    }

    public String getMimeType() {
        return mimeType;
    }

    public As2Entity setMimeType(String mimeType) {
        this.mimeType = mimeType;
        return this;
    }

    public String getMimeSubType() {
        return mimeSubType;
    }

    public As2Entity setMimeSubType(String mimeSubType) {
        this.mimeSubType = mimeSubType;
        return this;
    }

    public String getCompressData() {
        return compressData;
    }

    public As2Entity setCompressData(String compressData) {
        this.compressData = compressData;
        return this;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public As2Entity setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public String getEmailHost() {
        return emailHost;
    }

    public As2Entity setEmailHost(String emailHost) {
        this.emailHost = emailHost;
        return this;
    }

    public String getEmailPort() {
        return emailPort;
    }

    public As2Entity setEmailPort(String emailPort) {
        this.emailPort = emailPort;
        return this;
    }

    public String getResponseTimeout() {
        return responseTimeout;
    }

    public As2Entity setResponseTimeout(String responseTimeout) {
        this.responseTimeout = responseTimeout;
        return this;
    }

    public String getKeyCert() {
        return keyCert;
    }

    public As2Entity setKeyCert(String keyCert) {
        this.keyCert = keyCert;
        return this;
    }

    public String getKeyCertPath() {
        return keyCertPath;
    }

    public As2Entity setKeyCertPath(String keyCertPath) {
        this.keyCertPath = keyCertPath;
        return this;
    }

    public String getExchgCert() {
        return exchgCert;
    }

    public As2Entity setExchgCert(String exchgCert) {
        this.exchgCert = exchgCert;
        return this;
    }

    public String getExchgCertPath() {
        return exchgCertPath;
    }

    public As2Entity setExchgCertPath(String exchgCertPath) {
        this.exchgCertPath = exchgCertPath;
        return this;
    }

    public String getSigningCert() {
        return signingCert;
    }

    public As2Entity setSigningCert(String signingCert) {
        this.signingCert = signingCert;
        return this;
    }

    public String getSigningCertPath() {
        return signingCertPath;
    }

    public As2Entity setSigningCertPath(String signingCertPath) {
        this.signingCertPath = signingCertPath;
        return this;
    }

    public String getCaCert() {
        return caCert;
    }

    public As2Entity setCaCert(String caCert) {
        this.caCert = caCert;
        return this;
    }

    public String getCaCertPath() {
        return caCertPath;
    }

    public As2Entity setCaCertPath(String caCertPath) {
        this.caCertPath = caCertPath;
        return this;
    }

    public String getRetryInterval() {
        return retryInterval;
    }

    public As2Entity setRetryInterval(String retryInterval) {
        this.retryInterval = retryInterval;
        return this;
    }

    public String getNoOfRetries() {
        return noOfRetries;
    }

    public As2Entity setNoOfRetries(String noOfRetries) {
        this.noOfRetries = noOfRetries;
        return this;
    }

    public String getCaCertId() {
        return caCertId;
    }

    public As2Entity setCaCertId(String caCertId) {
        this.caCertId = caCertId;
        return this;
    }

    public String getExchgCertName() {
        return exchgCertName;
    }

    public As2Entity setExchgCertName(String exchgCertName) {
        this.exchgCertName = exchgCertName;
        return this;
    }

    public String getSigningCertName() {
        return signingCertName;
    }

    public As2Entity setSigningCertName(String signingCertName) {
        this.signingCertName = signingCertName;
        return this;
    }


}
