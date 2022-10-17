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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.pe.pcm.profile.ProfileModel;

import java.io.Serializable;

@JacksonXmlRootElement(localName = "PARTNER_AS2")
@JsonPropertyOrder({"pkId", "profileName", "profileId", "emailId", "phone",
        "protocol", "addressLine1", "addressLine2", "status", "as2Identifier", "compressData",
        "senderId", "senderQualifier", "endPoint", "responseTimeout", "payloadType", "mimeType",
        "mimeSubType", "sslType", "cipherStrength", "exchangeCertificate",
        "signingCertification", "keyCertification", "systemCertificate", "caCertificate",
        "keyCertificatePassphrase", "payloadEncryptionAlgorithm", "payloadSecurity", "encryptionAlgorithm",
        "signatureAlgorithm", "mdn", "mdnType", "mdnEncryption", "receiptToAddress",
        "hubInfo", "httpclientAdapter", "username", "password",
        "additionalServerCommunication", "as2AdditionalServerModel"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class As2Model extends ProfileModel implements Serializable {

    private String as2Identifier;
    private String compressData;
    private String cipherStrength;
    private String senderId;
    private String senderQualifier;
    private String endPoint;
    private Integer responseTimeout;
    private String payloadType;
    private String mimeType;
    private String mimeSubType;
    private String sslType;
    private String as2EmailAddress;

    //    private Integer retryInterval
    //    private Integer numberOfRetries
    private String username;
    private String password;
    private String httpclientAdapter;

    //Certificates
    private String exchangeCertificate;
    private String exchangeCertificateName;
    private String signingCertification;
    private String signingCertificateName;
    private String keyCertification;
    private String systemCertificate;
    private String caCertificate;

    //Encryption
    private String keyCertificatePassphrase;
    //Need to remove: not using this
    private String payloadEncryptionAlgorithm;
    private String payloadSecurity;
    private String encryptionAlgorithm;
    private String signatureAlgorithm;

    //MDN
    private Boolean mdn;
    private String mdnType;
    private String mdnEncryption;
    private String receiptToAddress;

    private String profileUserName;
    private String profileUserPassword;
    private Boolean isSIProfile;
    private Boolean isInitiatingConsumer;
    private Boolean isInitiatingProducer;
    private Boolean isListeningConsumer;
    private Boolean isListeningProducer;
    @JsonIgnore
    private String subscriberType;

    private boolean additionalServerCommunication;
    private As2AdditionalServerModel as2AdditionalServerModel;
    private String authenticationType;
    private String authenticationHost;
    private Boolean asciiArmor;
    private Boolean doesRequireEncryptedData;
    private Boolean doesRequireSignedData;
    private Boolean textMode;
    private String identityName;



    public As2Model() {
        super();
    }

    public String getAs2Identifier() {
        return as2Identifier;
    }

    public As2Model setAs2Identifier(String as2Identifier) {
        this.as2Identifier = as2Identifier;
        return this;
    }

    public String getProfileUserName() {
        return profileUserName;
    }

    public As2Model setProfileUserName(String profileUserName) {
        this.profileUserName = profileUserName;
        return this;
    }

    public String getProfileUserPassword() {
        return profileUserPassword;
    }

    public As2Model setProfileUserPassword(String profileUserPassword) {
        this.profileUserPassword = profileUserPassword;
        return this;
    }

    public Boolean getIsInitiatingConsumer() {
        return isInitiatingConsumer;
    }

    public As2Model setIsInitiatingConsumer(Boolean initiatingConsumer) {
        isInitiatingConsumer = initiatingConsumer;
        return this;
    }

    public Boolean getIsInitiatingProducer() {
        return isInitiatingProducer;
    }

    public As2Model setIsInitiatingProducer(Boolean initiatingProducer) {
        isInitiatingProducer = initiatingProducer;
        return this;
    }

    public Boolean getIsListeningConsumer() {
        return isListeningConsumer;
    }

    public As2Model setIsListeningConsumer(Boolean listeningConsumer) {
        isListeningConsumer = listeningConsumer;
        return this;
    }

    public Boolean getIsListeningProducer() {
        return isListeningProducer;
    }

    public As2Model setIsListeningProducer(Boolean listeningProducer) {
        isListeningProducer = listeningProducer;
        return this;
    }

    @JacksonXmlProperty(localName = "COMPRESS_DATA")
    public String getCompressData() {
        return compressData;
    }

    public As2Model setCompressData(String compressData) {
        this.compressData = compressData;
        return this;
    }

    @JacksonXmlProperty(localName = "SENDER_ID")
    public String getSenderId() {
        return senderId;
    }

    public As2Model setSenderId(String senderId) {
        this.senderId = senderId;
        return this;
    }

    @JacksonXmlProperty(localName = "SENDER_QUALIFIER")
    public String getSenderQualifier() {
        return senderQualifier;
    }

    public As2Model setSenderQualifier(String senderQualifier) {
        this.senderQualifier = senderQualifier;
        return this;
    }

    @JacksonXmlProperty(localName = "END_POINT")
    public String getEndPoint() {
        return endPoint;
    }

    public As2Model setEndPoint(String endPoint) {
        this.endPoint = endPoint;
        return this;
    }

    @JacksonXmlProperty(localName = "RESPONSE_TIMEOUT")
    public Integer getResponseTimeout() {
        return responseTimeout;
    }

    public As2Model setResponseTimeout(Integer responseTimeout) {
        this.responseTimeout = responseTimeout;
        return this;
    }

    @JacksonXmlProperty(localName = "PAY_LOAD_TYPE")
    public String getPayloadType() {
        return payloadType;
    }

    public As2Model setPayloadType(String payloadType) {
        this.payloadType = payloadType;
        return this;
    }

    @JacksonXmlProperty(localName = "MIME_TYPE")
    public String getMimeType() {
        return mimeType;
    }

    public As2Model setMimeType(String mimeType) {
        this.mimeType = mimeType;
        return this;
    }

    @JacksonXmlProperty(localName = "MIME_SUB_TYPE")
    public String getMimeSubType() {
        return mimeSubType;
    }

    public As2Model setMimeSubType(String mimeSubType) {
        this.mimeSubType = mimeSubType;
        return this;
    }

    @JacksonXmlProperty(localName = "SSL_TYPE")
    public String getSslType() {
        return sslType;
    }

    public As2Model setSslType(String sslType) {
        this.sslType = sslType;
        return this;
    }

    @JacksonXmlProperty(localName = "CIPHER_STRENGTH")
    public String getCipherStrength() {
        return cipherStrength;
    }

    public As2Model setCipherStrength(String cipherStrength) {
        this.cipherStrength = cipherStrength;
        return this;
    }

    @JacksonXmlProperty(localName = "EXCHANGE_CERTIFICATE")
    public String getExchangeCertificate() {
        return exchangeCertificate;
    }

    public As2Model setExchangeCertificate(String exchangeCertificate) {
        this.exchangeCertificate = exchangeCertificate;
        return this;
    }

    @JacksonXmlProperty(localName = "SIGNING_CERTIFICATION")
    public String getSigningCertification() {
        return signingCertification;
    }

    public As2Model setSigningCertification(String signingCertification) {
        this.signingCertification = signingCertification;
        return this;
    }

    @JacksonXmlProperty(localName = "KEY_CERTIFICATION")
    public String getKeyCertification() {
        return keyCertification;
    }

    public As2Model setKeyCertification(String keyCertification) {
        this.keyCertification = keyCertification;
        return this;
    }

    @JacksonXmlProperty(localName = "SYSTEM_CERTIFICATE")
    public String getSystemCertificate() {
        return systemCertificate;
    }

    public As2Model setSystemCertificate(String systemCertificate) {
        this.systemCertificate = systemCertificate;
        return this;
    }

    @JacksonXmlProperty(localName = "CA_CERTIFICATE")
    public String getCaCertificate() {
        return caCertificate;
    }

    public As2Model setCaCertificate(String caCertificate) {
        this.caCertificate = caCertificate;
        return this;
    }

    @JacksonXmlProperty(localName = "KEY_CERTIFICATE_PASSPHRASE")
    public String getKeyCertificatePassphrase() {
        return keyCertificatePassphrase;
    }

    public As2Model setKeyCertificatePassphrase(String keyCertificatePassphrase) {
        this.keyCertificatePassphrase = keyCertificatePassphrase;
        return this;
    }

    @JacksonXmlProperty(localName = "PAYLOAD_ENCRYPTION_ALGORITHM")
    public String getPayloadEncryptionAlgorithm() {
        return payloadEncryptionAlgorithm;
    }

    public As2Model setPayloadEncryptionAlgorithm(String payloadEncryptionAlgorithm) {
        this.payloadEncryptionAlgorithm = payloadEncryptionAlgorithm;
        return this;
    }

    @JacksonXmlProperty(localName = "PAYLOAD_SECURITY")
    public String getPayloadSecurity() {
        return payloadSecurity;
    }

    public As2Model setPayloadSecurity(String payloadSecurity) {
        this.payloadSecurity = payloadSecurity;
        return this;
    }

    @JacksonXmlProperty(localName = "ENCRYPTION_ALGORITHM")
    public String getEncryptionAlgorithm() {
        return encryptionAlgorithm;
    }

    public As2Model setEncryptionAlgorithm(String encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
        return this;
    }

    @JacksonXmlProperty(localName = "SIGNATURE_ALGORITHM")
    public String getSignatureAlgorithm() {
        return signatureAlgorithm;
    }

    public As2Model setSignatureAlgorithm(String signatureAlgorithm) {
        this.signatureAlgorithm = signatureAlgorithm;
        return this;
    }

    @JacksonXmlProperty(localName = "MDN")
    public Boolean getMdn() {
        return mdn;
    }

    public As2Model setMdn(Boolean mdn) {
        this.mdn = mdn;
        return this;
    }

    @JacksonXmlProperty(localName = "MDN_TYPE")
    public String getMdnType() {
        return mdnType;
    }

    public As2Model setMdnType(String mdnType) {
        this.mdnType = mdnType;
        return this;
    }

    @JacksonXmlProperty(localName = "MDN_ENCRYPTION")
    public String getMdnEncryption() {
        return mdnEncryption;
    }

    public As2Model setMdnEncryption(String mdnEncryption) {
        this.mdnEncryption = mdnEncryption;
        return this;
    }

    @JacksonXmlProperty(localName = "RECEIPT_TO_ADDRESS")
    public String getReceiptToAddress() {
        return receiptToAddress;
    }

    public As2Model setReceiptToAddress(String receiptToAddress) {
        this.receiptToAddress = receiptToAddress;
        return this;
    }

    @JacksonXmlProperty(localName = "ADAPTER_NAME")
    public String getHttpclientAdapter() {
        return httpclientAdapter;
    }

    public As2Model setHttpclientAdapter(String httpclientAdapter) {
        this.httpclientAdapter = httpclientAdapter;
        return this;
    }

    @JacksonXmlProperty(localName = "USERNAME")
    public String getUsername() {
        return username;
    }

    public As2Model setUsername(String username) {
        this.username = username;
        return this;
    }

    @JacksonXmlProperty(localName = "PASSWORD")
    public String getPassword() {
        return password;
    }

    public As2Model setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getAs2EmailAddress() {
        return as2EmailAddress;
    }

    public As2Model setAs2EmailAddress(String as2EmailAddress) {
        this.as2EmailAddress = as2EmailAddress;
        return this;
    }

    public String getSubscriberType() {
        return subscriberType;
    }

    public As2Model setSubscriberType(String subscriberType) {
        this.subscriberType = subscriberType;
        return this;
    }

    public Boolean getIsSIProfile() {
        return isSIProfile;
    }

    public As2Model setIsSIProfile(Boolean sIProfile) {
        isSIProfile = sIProfile;
        return this;
    }

    @JacksonXmlProperty(localName = "ADDITIONAL_SERVER_COMMUNICATION")
    public boolean isAdditionalServerCommunication() {
        return additionalServerCommunication;
    }

    public As2Model setAdditionalServerCommunication(boolean additionalServerCommunication) {
        this.additionalServerCommunication = additionalServerCommunication;
        return this;
    }

    @JacksonXmlProperty(localName = "AS2_ADDITIONAL_SERVER")
    public As2AdditionalServerModel getAs2AdditionalServerModel() {
        return as2AdditionalServerModel;
    }

    public As2Model setAs2AdditionalServerModel(As2AdditionalServerModel as2AdditionalServerModel) {
        this.as2AdditionalServerModel = as2AdditionalServerModel;
        return this;
    }

    public String getAuthenticationType() {
        return authenticationType;
    }

    public void setAuthenticationType(String authenticationType) {
        this.authenticationType = authenticationType;
    }

    public String getAuthenticationHost() {
        return authenticationHost;
    }

    public void setAuthenticationHost(String authenticationHost) {
        this.authenticationHost = authenticationHost;
    }

    public String getExchangeCertificateName() {
        return exchangeCertificateName;
    }

    public As2Model setExchangeCertificateName(String exchangeCertificateName) {
        this.exchangeCertificateName = exchangeCertificateName;
        return this;
    }

    public String getSigningCertificateName() {
        return signingCertificateName;
    }

    public As2Model setSigningCertificateName(String signingCertificateName) {
        this.signingCertificateName = signingCertificateName;
        return this;
    }

    public Boolean getAsciiArmor() {
        return asciiArmor;
    }

    public As2Model setAsciiArmor(Boolean asciiArmor) {
        this.asciiArmor = asciiArmor;
        return this;
    }

    public Boolean getDoesRequireEncryptedData() {
        return doesRequireEncryptedData;
    }

    public As2Model setDoesRequireEncryptedData(Boolean doesRequireEncryptedData) {
        this.doesRequireEncryptedData = doesRequireEncryptedData;
        return this;
    }

    public Boolean getDoesRequireSignedData() {
        return doesRequireSignedData;
    }

    public As2Model setDoesRequireSignedData(Boolean doesRequireSignedData) {
        this.doesRequireSignedData = doesRequireSignedData;
        return this;
    }

    public Boolean getTextMode() {
        return textMode;
    }

    public As2Model setTextMode(Boolean textMode) {
        this.textMode = textMode;
        return this;
    }

    public String getIdentityName() {
        return identityName;
    }

    public As2Model setIdentityName(String identityName) {
        this.identityName = identityName;
        return this;
    }
}
