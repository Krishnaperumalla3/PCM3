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
import com.pe.pcm.b2b.certificate.As2GenericCertModel;

import java.io.Serializable;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author Chenchu Kiran.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RemoteAs2PartnerProfile implements Serializable {

    private String as2Identifier;
    private String identityName;
    private String profileName;
    //private String additionalHttpCommunication
    //private String CACertSelectionPolicy
    private List<As2GenericCertModel> caCertificate;
    private String cipherStrength;
    private String compressData;
    private String deliveryMode;
    private String encryptionAlgorithm;
    private String endPoint;
    private String exchangeCertSelectionPolicy;
    private List<As2GenericCertModel> exchangeCertificate;
    private String firewallConnectCount;
    private String firewallProxy;
    private String httpClientAdapter = "HTTPClientAdapter";
    private List<As2GenericCertModel> keyCertificate;
    private String keyCertificatePassphrase;
    private String keyCertificateSelectionPolicy;
    private Boolean mdnReceipt;
    private String mimeSubType;
    private String mimeType;
    private String password;
    private String payloadType;
    private String receiptSignatureType;
    private int receiptTimeout;
    private String receiptToAddress;
    private int responseTimeout;
    private Boolean selectNewIdentity;
    private Boolean setUpAdditionalServerCommunication;
    private String signingAlgorithm;
    private String signingCertSelectionPolicy;
    private List<As2GenericCertModel> signingCertificate;
    private String socketTimeout;
    private String ssl;
    private String useExistingIdentity;
    private String userId;

    public String getAs2Identifier() {
        return as2Identifier;
    }

    public RemoteAs2PartnerProfile setAs2Identifier(String as2Identifier) {
        this.as2Identifier = as2Identifier;
        return this;
    }

    public String getIdentityName() {
        return identityName;
    }

    public RemoteAs2PartnerProfile setIdentityName(String identityName) {
        this.identityName = identityName;
        return this;
    }

    public String getProfileName() {
        return profileName;
    }

    public RemoteAs2PartnerProfile setProfileName(String profileName) {
        this.profileName = profileName;
        return this;
    }

    public List<As2GenericCertModel> getCaCertificate() {
        return caCertificate;
    }

    public RemoteAs2PartnerProfile setCaCertificate(List<As2GenericCertModel> caCertificate) {
        this.caCertificate = caCertificate;
        return this;
    }

    public String getCipherStrength() {
        return cipherStrength;
    }

    public RemoteAs2PartnerProfile setCipherStrength(String cipherStrength) {
        this.cipherStrength = cipherStrength;
        return this;
    }

    public String getCompressData() {
        return compressData;
    }

    public RemoteAs2PartnerProfile setCompressData(String compressData) {
        this.compressData = compressData;
        return this;
    }

    public String getDeliveryMode() {
        return deliveryMode;
    }

    public RemoteAs2PartnerProfile setDeliveryMode(String deliveryMode) {
        this.deliveryMode = deliveryMode;
        return this;
    }

    public String getEncryptionAlgorithm() {
        return encryptionAlgorithm;
    }

    public RemoteAs2PartnerProfile setEncryptionAlgorithm(String encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
        return this;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public RemoteAs2PartnerProfile setEndPoint(String endPoint) {
        this.endPoint = endPoint;
        return this;
    }

    public String getExchangeCertSelectionPolicy() {
        return exchangeCertSelectionPolicy;
    }

    public RemoteAs2PartnerProfile setExchangeCertSelectionPolicy(String exchangeCertSelectionPolicy) {
        this.exchangeCertSelectionPolicy = exchangeCertSelectionPolicy;
        return this;
    }

    public List<As2GenericCertModel> getExchangeCertificate() {
        return exchangeCertificate;
    }

    public RemoteAs2PartnerProfile setExchangeCertificate(List<As2GenericCertModel> exchangeCertificate) {
        this.exchangeCertificate = exchangeCertificate;
        return this;
    }

    public String getFirewallConnectCount() {
        return firewallConnectCount;
    }

    public RemoteAs2PartnerProfile setFirewallConnectCount(String firewallConnectCount) {
        this.firewallConnectCount = firewallConnectCount;
        return this;
    }

    public String getFirewallProxy() {
        return firewallProxy;
    }

    public RemoteAs2PartnerProfile setFirewallProxy(String firewallProxy) {
        this.firewallProxy = firewallProxy;
        return this;
    }

    public String getHttpClientAdapter() {
        return httpClientAdapter;
    }

    public RemoteAs2PartnerProfile setHttpClientAdapter(String httpClientAdapter) {
        this.httpClientAdapter = httpClientAdapter;
        return this;
    }

    public List<As2GenericCertModel> getKeyCertificate() {
        return keyCertificate;
    }

    public RemoteAs2PartnerProfile setKeyCertificate(List<As2GenericCertModel> keyCertificate) {
        this.keyCertificate = keyCertificate;
        return this;
    }

    public String getKeyCertificatePassphrase() {
        return keyCertificatePassphrase;
    }

    public RemoteAs2PartnerProfile setKeyCertificatePassphrase(String keyCertificatePassphrase) {
        this.keyCertificatePassphrase = keyCertificatePassphrase;
        return this;
    }

    public String getKeyCertificateSelectionPolicy() {
        return keyCertificateSelectionPolicy;
    }

    public RemoteAs2PartnerProfile setKeyCertificateSelectionPolicy(String keyCertificateSelectionPolicy) {
        this.keyCertificateSelectionPolicy = keyCertificateSelectionPolicy;
        return this;
    }

    public Boolean getMdnReceipt() {
        return mdnReceipt;
    }

    public RemoteAs2PartnerProfile setMdnReceipt(Boolean mdnReceipt) {
        this.mdnReceipt = mdnReceipt;
        return this;
    }

    public String getMimeSubType() {
        return mimeSubType;
    }

    public RemoteAs2PartnerProfile setMimeSubType(String mimeSubType) {
        this.mimeSubType = mimeSubType;
        return this;
    }

    public String getMimeType() {
        return mimeType;
    }

    public RemoteAs2PartnerProfile setMimeType(String mimeType) {
        this.mimeType = mimeType;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RemoteAs2PartnerProfile setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPayloadType() {
        return payloadType;
    }

    public RemoteAs2PartnerProfile setPayloadType(String payloadType) {
        this.payloadType = payloadType;
        return this;
    }

    public String getReceiptSignatureType() {
        return receiptSignatureType;
    }

    public RemoteAs2PartnerProfile setReceiptSignatureType(String receiptSignatureType) {
        this.receiptSignatureType = receiptSignatureType;
        return this;
    }

    public int getReceiptTimeout() {
        return receiptTimeout;
    }

    public RemoteAs2PartnerProfile setReceiptTimeout(int receiptTimeout) {
        this.receiptTimeout = receiptTimeout;
        return this;
    }

    public String getReceiptToAddress() {
        return receiptToAddress;
    }

    public RemoteAs2PartnerProfile setReceiptToAddress(String receiptToAddress) {
        this.receiptToAddress = receiptToAddress;
        return this;
    }

    public int getResponseTimeout() {
        return responseTimeout;
    }

    public RemoteAs2PartnerProfile setResponseTimeout(int responseTimeout) {
        this.responseTimeout = responseTimeout;
        return this;
    }

    public Boolean getSelectNewIdentity() {
        return selectNewIdentity;
    }

    public RemoteAs2PartnerProfile setSelectNewIdentity(Boolean selectNewIdentity) {
        this.selectNewIdentity = selectNewIdentity;
        return this;
    }

    public Boolean getSetUpAdditionalServerCommunication() {
        return setUpAdditionalServerCommunication;
    }

    public RemoteAs2PartnerProfile setSetUpAdditionalServerCommunication(Boolean setUpAdditionalServerCommunication) {
        this.setUpAdditionalServerCommunication = setUpAdditionalServerCommunication;
        return this;
    }

    public String getSigningAlgorithm() {
        return signingAlgorithm;
    }

    public RemoteAs2PartnerProfile setSigningAlgorithm(String signingAlgorithm) {
        this.signingAlgorithm = signingAlgorithm;
        return this;
    }

    public String getSigningCertSelectionPolicy() {
        return signingCertSelectionPolicy;
    }

    public RemoteAs2PartnerProfile setSigningCertSelectionPolicy(String signingCertSelectionPolicy) {
        this.signingCertSelectionPolicy = signingCertSelectionPolicy;
        return this;
    }

    public List<As2GenericCertModel> getSigningCertificate() {
        return signingCertificate;
    }

    public RemoteAs2PartnerProfile setSigningCertificate(List<As2GenericCertModel> signingCertificate) {
        this.signingCertificate = signingCertificate;
        return this;
    }

    public String getSocketTimeout() {
        return socketTimeout;
    }

    public RemoteAs2PartnerProfile setSocketTimeout(String socketTimeout) {
        this.socketTimeout = socketTimeout;
        return this;
    }

    public String getSsl() {
        return ssl;
    }

    public RemoteAs2PartnerProfile setSsl(String ssl) {
        this.ssl = ssl;
        return this;
    }

    public String getUseExistingIdentity() {
        return useExistingIdentity;
    }

    public RemoteAs2PartnerProfile setUseExistingIdentity(String useExistingIdentity) {
        this.useExistingIdentity = useExistingIdentity;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public RemoteAs2PartnerProfile setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RemoteAs2PartnerProfile.class.getSimpleName() + "[", "]")
                .add("as2Identifier='" + as2Identifier + "'")
                .add("identityName='" + identityName + "'")
                .add("profileName='" + profileName + "'")
                .add("caCertificate=" + caCertificate)
                .add("cipherStrength='" + cipherStrength + "'")
                .add("compressData='" + compressData + "'")
                .add("deliveryMode='" + deliveryMode + "'")
                .add("encryptionAlgorithm='" + encryptionAlgorithm + "'")
                .add("endPoint='" + endPoint + "'")
                .add("exchangeCertSelectionPolicy='" + exchangeCertSelectionPolicy + "'")
                .add("exchangeCertificate=" + exchangeCertificate)
                .add("firewallConnectCount='" + firewallConnectCount + "'")
                .add("firewallProxy='" + firewallProxy + "'")
                .add("httpClientAdapter='" + httpClientAdapter + "'")
                .add("keyCertificate=" + keyCertificate)
                .add("keyCertificatePassphrase='" + keyCertificatePassphrase + "'")
                .add("keyCertificateSelectionPolicy='" + keyCertificateSelectionPolicy + "'")
                .add("mdnReceipt=" + mdnReceipt)
                .add("mimeSubType='" + mimeSubType + "'")
                .add("mimeType='" + mimeType + "'")
                .add("password='" + password + "'")
                .add("payloadType='" + payloadType + "'")
                .add("receiptSignatureType='" + receiptSignatureType + "'")
                .add("receiptTimeout=" + receiptTimeout)
                .add("receiptToAddress='" + receiptToAddress + "'")
                .add("responseTimeout=" + responseTimeout)
                .add("selectNewIdentity=" + selectNewIdentity)
                .add("setUpAdditionalServerCommunication=" + setUpAdditionalServerCommunication)
                .add("signingAlgorithm='" + signingAlgorithm + "'")
                .add("signingCertSelectionPolicy='" + signingCertSelectionPolicy + "'")
                .add("signingCertificate=" + signingCertificate)
                .add("socketTimeout='" + socketTimeout + "'")
                .add("ssl='" + ssl + "'")
                .add("useExistingIdentity='" + useExistingIdentity + "'")
                .add("userId='" + userId + "'")
                .toString();
    }
}
