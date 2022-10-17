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
import com.pe.pcm.pem.b2bproxy.consumerconfiguration.ConsumerSshConfiguration;
import com.pe.pcm.pem.b2bproxy.producerconfiguration.ProducerFtpConfiguration;
import com.pe.pcm.pem.b2bproxy.producerconfiguration.ProducerFtpsConfiguration;
import com.pe.pcm.pem.b2bproxy.producerconfiguration.ProducerSshConfiguration;
import com.pe.pcm.protocol.RemoteProfileModel;

import java.io.Serializable;

import static com.pe.pcm.utils.CommonFunctions.convertPoolingIntervalToMinutes;
import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.PCMConstants.PRAGMA_EDGE_S;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RemoteFtpProfile implements Serializable {
    private String authenticationType;
    private String authenticationHost;
    private String emailAddress;
    private String givenName;
    private String addressLine1;
    private String addressLine2;
    private String surname;
    private String username;
    private String password;
    private String phone;
    private String partnerName;
    private Integer pollingInterval;
    private String community;
    private Boolean isInitiatingConsumer;
    private Boolean isInitiatingProducer;
    private Boolean isListeningConsumer;
    private Boolean isListeningProducer;
    private Boolean doesUseSSH;
    private Boolean asciiArmor;
    private Boolean doesRequireEncryptedData;
    private Boolean doesRequireSignedData;
    private Boolean textMode;
    private String publicKeyID;
    private RemoteFtpConfiguration consumerFtpConfiguration;
    private RemoteFtpsConfiguration consumerFtpsConfiguration;
    private ProducerFtpConfiguration producerFtpConfiguration;
    private ProducerFtpsConfiguration producerFtpsConfiguration;
    private ConsumerSshConfiguration consumerSshConfiguration;
    private ProducerSshConfiguration producerSshConfiguration;

    public RemoteFtpProfile(RemoteProfileModel remoteProfileModel, Boolean isUpdate, String community) {

        this.partnerName = remoteProfileModel.getProfileName();
        this.authenticationType = isNotNull(remoteProfileModel.getAuthenticationType()) ? remoteProfileModel.getAuthenticationType() : "Local";
        this.authenticationHost = isNotNull(remoteProfileModel.getAuthenticationHost()) ? remoteProfileModel.getAuthenticationHost() : "";
        this.emailAddress = remoteProfileModel.getEmailId();
        this.givenName = remoteProfileModel.getProfileName();
        this.surname = remoteProfileModel.getProfileName();
        this.addressLine1 = remoteProfileModel.getAddressLine1();
        this.addressLine2 = remoteProfileModel.getAddressLine2();
        this.username = isNotNull(remoteProfileModel.getProfileUserName()) ? remoteProfileModel.getProfileUserName() : remoteProfileModel.getUserName();
        if (isNotNull(remoteProfileModel.getProfileUserName())) {
            if (!isNotNull(remoteProfileModel.getProfileUserPassword())) {
                remoteProfileModel.setProfileUserPassword("");
            }
            this.password = isUpdate ? (remoteProfileModel.getProfileUserPassword().equalsIgnoreCase(PRAGMA_EDGE_S) ? null : remoteProfileModel.getProfileUserPassword()) : remoteProfileModel.getProfileUserPassword();
        } else {
            this.password = isUpdate ? (remoteProfileModel.getPassword().equalsIgnoreCase(PRAGMA_EDGE_S) ? null : remoteProfileModel.getPassword()) : remoteProfileModel.getPassword();
        }
        this.phone = remoteProfileModel.getPhone();
        this.pollingInterval = remoteProfileModel.getPoolingInterval().equalsIgnoreCase("ON") ? null :
                convertPoolingIntervalToMinutes(remoteProfileModel.getPoolingInterval());
        this.isInitiatingConsumer = isNotNull(remoteProfileModel.getIsInitiatingConsumer()) ? remoteProfileModel.getIsInitiatingConsumer() : Boolean.FALSE;
        this.isListeningConsumer = isNotNull(remoteProfileModel.getIsListeningConsumer()) ? remoteProfileModel.getIsListeningConsumer() : Boolean.TRUE;
        this.isInitiatingProducer = isNotNull(remoteProfileModel.getIsInitiatingProducer()) ? remoteProfileModel.getIsInitiatingProducer() : Boolean.FALSE;
        this.isListeningProducer = isNotNull(remoteProfileModel.getIsListeningProducer()) ? remoteProfileModel.getIsListeningProducer() : Boolean.FALSE;
        this.community = community;
        this.doesUseSSH = remoteProfileModel.isDoesUseSSH();
        this.asciiArmor = isNotNull(remoteProfileModel.getAsciiArmor()) ? remoteProfileModel.getAsciiArmor() : Boolean.FALSE;
        this.doesRequireEncryptedData = isNotNull(remoteProfileModel.getDoesRequireEncryptedData()) ? remoteProfileModel.getDoesRequireEncryptedData() : Boolean.FALSE;
        this.doesRequireSignedData = isNotNull(remoteProfileModel.getDoesRequireSignedData()) ? remoteProfileModel.getDoesRequireSignedData() : Boolean.FALSE;
        this.textMode = isNotNull(remoteProfileModel.getTextMode()) ? remoteProfileModel.getTextMode() : null;
        this.publicKeyID = isNotNull(remoteProfileModel.getPgpInfo()) ? remoteProfileModel.getPgpInfo() : null;
    }

    public Boolean getIsInitiatingConsumer() {
        return isInitiatingConsumer;
    }

    public RemoteFtpProfile setIsInitiatingConsumer(Boolean initiatingConsumer) {
        isInitiatingConsumer = initiatingConsumer;
        return this;
    }

    public Boolean getIsInitiatingProducer() {
        return isInitiatingProducer;
    }

    public RemoteFtpProfile setIsInitiatingProducer(Boolean initiatingProducer) {
        isInitiatingProducer = initiatingProducer;
        return this;
    }

    public Boolean getIsListeningConsumer() {
        return isListeningConsumer;
    }

    public RemoteFtpProfile setIsListeningConsumer(Boolean listeningConsumer) {
        isListeningConsumer = listeningConsumer;
        return this;
    }

    public Boolean getIsListeningProducer() {
        return isListeningProducer;
    }

    public RemoteFtpProfile setIsListeningProducer(Boolean listeningProducer) {
        isListeningProducer = listeningProducer;
        return this;
    }

    public ProducerFtpConfiguration getProducerFtpConfiguration() {
        return producerFtpConfiguration;
    }

    public RemoteFtpProfile setProducerFtpConfiguration(ProducerFtpConfiguration producerFtpConfiguration) {
        this.producerFtpConfiguration = producerFtpConfiguration;
        return this;
    }

    public ProducerFtpsConfiguration getProducerFtpsConfiguration() {
        return producerFtpsConfiguration;
    }

    public RemoteFtpProfile setProducerFtpsConfiguration(ProducerFtpsConfiguration producerFtpsConfiguration) {
        this.producerFtpsConfiguration = producerFtpsConfiguration;
        return this;
    }

    public ConsumerSshConfiguration getConsumerSshConfiguration() {
        return consumerSshConfiguration;
    }

    public RemoteFtpProfile setConsumerSshConfiguration(ConsumerSshConfiguration consumerSshConfiguration) {
        this.consumerSshConfiguration = consumerSshConfiguration;
        return this;
    }

    public ProducerSshConfiguration getProducerSshConfiguration() {
        return producerSshConfiguration;
    }

    public RemoteFtpProfile setProducerSshConfiguration(ProducerSshConfiguration producerSshConfiguration) {
        this.producerSshConfiguration = producerSshConfiguration;
        return this;
    }

    public String getAuthenticationType() {
        return authenticationType;
    }

    public RemoteFtpProfile setAuthenticationType(String authenticationType) {
        this.authenticationType = authenticationType;
        return this;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public RemoteFtpProfile setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public String getGivenName() {
        return givenName;
    }

    public RemoteFtpProfile setGivenName(String givenName) {
        this.givenName = givenName;
        return this;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public RemoteFtpProfile setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
        return this;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public RemoteFtpProfile setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public RemoteFtpProfile setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public RemoteFtpProfile setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RemoteFtpProfile setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public RemoteFtpProfile setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public RemoteFtpProfile setPartnerName(String partnerName) {
        this.partnerName = partnerName;
        return this;
    }

    public Integer getPollingInterval() {
        return pollingInterval;
    }

    public RemoteFtpProfile setPollingInterval(Integer pollingInterval) {
        this.pollingInterval = pollingInterval;
        return this;
    }


    public String getCommunity() {
        return community;
    }

    public RemoteFtpProfile setCommunity(String community) {
        this.community = community;
        return this;
    }

    public RemoteFtpConfiguration getConsumerFtpConfiguration() {
        return consumerFtpConfiguration;
    }

    public RemoteFtpProfile setConsumerFtpConfiguration(RemoteFtpConfiguration consumerFtpConfiguration) {
        this.consumerFtpConfiguration = consumerFtpConfiguration;
        return this;
    }

    public RemoteFtpsConfiguration getConsumerFtpsConfiguration() {
        return consumerFtpsConfiguration;
    }

    public RemoteFtpProfile setConsumerFtpsConfiguration(RemoteFtpsConfiguration consumerFtpsConfiguration) {
        this.consumerFtpsConfiguration = consumerFtpsConfiguration;
        return this;
    }

    public Boolean getDoesUseSSH() {
        return doesUseSSH;
    }

    public RemoteFtpProfile setDoesUseSSH(Boolean doesUseSSH) {
        this.doesUseSSH = doesUseSSH;
        return this;
    }

    public String getAuthenticationHost() {
        return authenticationHost;
    }

    public void setAuthenticationHost(String authenticationHost) {
        this.authenticationHost = authenticationHost;
    }

    public Boolean getAsciiArmor() {
        return asciiArmor;
    }

    public RemoteFtpProfile setAsciiArmor(Boolean asciiArmor) {
        this.asciiArmor = asciiArmor;
        return this;
    }

    public Boolean getDoesRequireEncryptedData() {
        return doesRequireEncryptedData;
    }

    public RemoteFtpProfile setDoesRequireEncryptedData(Boolean doesRequireEncryptedData) {
        this.doesRequireEncryptedData = doesRequireEncryptedData;
        return this;
    }

    public Boolean getDoesRequireSignedData() {
        return doesRequireSignedData;
    }

    public RemoteFtpProfile setDoesRequireSignedData(Boolean doesRequireSignedData) {
        this.doesRequireSignedData = doesRequireSignedData;
        return this;
    }

    public Boolean getTextMode() {
        return textMode;
    }

    public RemoteFtpProfile setTextMode(Boolean textMode) {
        this.textMode = textMode;
        return this;
    }

    public String getPublicKeyID() {
        return publicKeyID;
    }

    public RemoteFtpProfile setPublicKeyID(String publicKeyID) {
        this.publicKeyID = publicKeyID;
        return this;
    }

    @Override
    public String toString() {
        return "RemoteFtpProfile{" +
                "authenticationType='" + authenticationType + '\'' +
                ", authenticationHost='" + authenticationHost + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", givenName='" + givenName + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", surname='" + surname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", partnerName='" + partnerName + '\'' +
                ", pollingInterval=" + pollingInterval +
                ", community='" + community + '\'' +
                ", isInitiatingConsumer=" + isInitiatingConsumer +
                ", isInitiatingProducer=" + isInitiatingProducer +
                ", isListeningConsumer=" + isListeningConsumer +
                ", isListeningProducer=" + isListeningProducer +
                ", doesUseSSH=" + doesUseSSH +
                ", asciiArmor=" + asciiArmor +
                ", doesRequireEncryptedData=" + doesRequireEncryptedData +
                ", doesRequireSignedData=" + doesRequireSignedData +
                ", textMode=" + textMode +
                ", publicKeyID='" + publicKeyID + '\'' +
                ", consumerFtpConfiguration=" + consumerFtpConfiguration +
                ", consumerFtpsConfiguration=" + consumerFtpsConfiguration +
                ", producerFtpConfiguration=" + producerFtpConfiguration +
                ", producerFtpsConfiguration=" + producerFtpsConfiguration +
                ", consumerSshConfiguration=" + consumerSshConfiguration +
                ", producerSshConfiguration=" + producerSshConfiguration +
                '}';
    }
}
