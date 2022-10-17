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
import com.pe.pcm.pem.b2bproxy.consumerconfiguration.ConsumerCdConfiguration;
import com.pe.pcm.pem.b2bproxy.producerconfiguration.ProducerCdConfiguration;
import com.pe.pcm.protocol.RemoteCdModel;

import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.PCMConstants.PRAGMA_EDGE_S;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RemoteConnectDirectProfile {

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
    private String pollingInterval;
    private String community;
    private Boolean isInitiatingConsumer;
    private Boolean isInitiatingProducer;
    private Boolean isListeningConsumer;
    private Boolean isListeningProducer;
    private ConsumerCdConfiguration consumerCdConfiguration;
    private ProducerCdConfiguration producerCdConfiguration;
    private String remoteFilePattern;
    private Boolean doesUseSSH;
    private Boolean asciiArmor;
    private Boolean doesRequireEncryptedData;
    private Boolean doesRequireSignedData;
    private Boolean textMode;
    private String publicKeyID;

    public RemoteConnectDirectProfile(RemoteCdModel remoteCdModel, Boolean isUpdate, String community) {
        this.authenticationType = isNotNull(remoteCdModel.getAuthenticationType()) ? remoteCdModel.getAuthenticationType() : "Local";
        this.authenticationHost = isNotNull(remoteCdModel.getAuthenticationHost()) ? remoteCdModel.getAuthenticationHost() : "";
        this.emailAddress = remoteCdModel.getEmailId();
        this.givenName = remoteCdModel.getProfileName();
        this.addressLine1 = remoteCdModel.getAddressLine1();
        this.addressLine2 = remoteCdModel.getAddressLine2();
        this.surname = remoteCdModel.getProfileName();
        this.username = remoteCdModel.getProfileUserName();
        this.password = isUpdate ? (remoteCdModel.getProfileUserPassword().equalsIgnoreCase(PRAGMA_EDGE_S) ? "" : remoteCdModel.getProfileUserPassword()) : remoteCdModel.getProfileUserPassword();
        this.phone = remoteCdModel.getPhone();
        this.partnerName = remoteCdModel.getProfileName();
        if (isNotNull(remoteCdModel.getCustomProfileName())) {
            this.partnerName = remoteCdModel.getCustomProfileName();
        }
        this.pollingInterval = remoteCdModel.getPoolingInterval();
        this.community = community;
        this.isInitiatingConsumer = isNotNull(remoteCdModel.getIsInitiatingConsumer()) ? remoteCdModel.getIsInitiatingConsumer() : FALSE;
        this.isListeningConsumer = isNotNull(remoteCdModel.getIsListeningConsumer()) ? remoteCdModel.getIsListeningConsumer() : TRUE;
        this.isInitiatingProducer = isNotNull(remoteCdModel.getIsInitiatingProducer()) ? remoteCdModel.getIsInitiatingProducer() : FALSE;
        this.isListeningProducer = isNotNull(remoteCdModel.getIsListeningProducer()) ? remoteCdModel.getIsListeningProducer() : FALSE;
        if (isNotNull(remoteCdModel.getDirectory())) {
            if (isNotNull(remoteCdModel.getRemoteFileName())) {
                this.remoteFilePattern = remoteCdModel.getDirectory() + "/" + remoteCdModel.getRemoteFileName();
            } else {
                this.remoteFilePattern = remoteCdModel.getDirectory();
            }
        }
        this.doesUseSSH = remoteCdModel.isDoesUseSSH();
        this.asciiArmor = isNotNull(remoteCdModel.getAsciiArmor()) ? remoteCdModel.getAsciiArmor() : Boolean.FALSE;
        this.doesRequireEncryptedData = isNotNull(remoteCdModel.getDoesRequireEncryptedData()) ? remoteCdModel.getDoesRequireEncryptedData() : Boolean.FALSE;
        this.doesRequireSignedData = isNotNull(remoteCdModel.getDoesRequireSignedData()) ? remoteCdModel.getDoesRequireSignedData() : Boolean.FALSE;
        this.textMode = isNotNull(remoteCdModel.getTextMode()) ? remoteCdModel.getTextMode() : null;
        this.publicKeyID = isNotNull(remoteCdModel.getPgpInfo()) ? remoteCdModel.getPgpInfo() : null;
    }

    public String getAuthenticationType() {
        return authenticationType;
    }

    public RemoteConnectDirectProfile setAuthenticationType(String authenticationType) {
        this.authenticationType = authenticationType;
        return this;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public RemoteConnectDirectProfile setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public String getGivenName() {
        return givenName;
    }

    public RemoteConnectDirectProfile setGivenName(String givenName) {
        this.givenName = givenName;
        return this;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public RemoteConnectDirectProfile setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
        return this;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public RemoteConnectDirectProfile setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public RemoteConnectDirectProfile setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public RemoteConnectDirectProfile setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RemoteConnectDirectProfile setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public RemoteConnectDirectProfile setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public RemoteConnectDirectProfile setPartnerName(String partnerName) {
        this.partnerName = partnerName;
        return this;
    }

    public String getPollingInterval() {
        return pollingInterval;
    }

    public RemoteConnectDirectProfile setPollingInterval(String pollingInterval) {
        this.pollingInterval = pollingInterval;
        return this;
    }

    public String getCommunity() {
        return community;
    }

    public RemoteConnectDirectProfile setCommunity(String community) {
        this.community = community;
        return this;
    }

    public Boolean getIsInitiatingConsumer() {
        return isInitiatingConsumer;
    }

    public RemoteConnectDirectProfile setIsInitiatingConsumer(Boolean initiatingConsumer) {
        isInitiatingConsumer = initiatingConsumer;
        return this;
    }

    public Boolean getIsInitiatingProducer() {
        return isInitiatingProducer;
    }

    public RemoteConnectDirectProfile setIsInitiatingProducer(Boolean initiatingProducer) {
        isInitiatingProducer = initiatingProducer;
        return this;
    }

    public Boolean getIsListeningConsumer() {
        return isListeningConsumer;
    }

    public RemoteConnectDirectProfile setIsListeningConsumer(Boolean listeningConsumer) {
        isListeningConsumer = listeningConsumer;
        return this;
    }

    public Boolean getIsListeningProducer() {
        return isListeningProducer;
    }

    public RemoteConnectDirectProfile setIsListeningProducer(Boolean listeningProducer) {
        isListeningProducer = listeningProducer;
        return this;
    }

    public ConsumerCdConfiguration getConsumerCdConfiguration() {
        return consumerCdConfiguration;
    }

    public RemoteConnectDirectProfile setConsumerCdConfiguration(ConsumerCdConfiguration consumerCdConfiguration) {
        this.consumerCdConfiguration = consumerCdConfiguration;
        return this;
    }

    public ProducerCdConfiguration getProducerCdConfiguration() {
        return producerCdConfiguration;
    }

    public RemoteConnectDirectProfile setProducerCdConfiguration(ProducerCdConfiguration producerCdConfiguration) {
        this.producerCdConfiguration = producerCdConfiguration;
        return this;
    }

    public String getRemoteFilePattern() {
        return remoteFilePattern;
    }

    public RemoteConnectDirectProfile setRemoteFilePattern(String remoteFilePattern) {
        this.remoteFilePattern = remoteFilePattern;
        return this;
    }

    public Boolean getDoesUseSSH() {
        return doesUseSSH;
    }

    public RemoteConnectDirectProfile setDoesUseSSH(Boolean doesUseSSH) {
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

    public RemoteConnectDirectProfile setAsciiArmor(Boolean asciiArmor) {
        this.asciiArmor = asciiArmor;
        return this;
    }

    public Boolean getDoesRequireEncryptedData() {
        return doesRequireEncryptedData;
    }

    public RemoteConnectDirectProfile setDoesRequireEncryptedData(Boolean doesRequireEncryptedData) {
        this.doesRequireEncryptedData = doesRequireEncryptedData;
        return this;
    }

    public Boolean getDoesRequireSignedData() {
        return doesRequireSignedData;
    }

    public RemoteConnectDirectProfile setDoesRequireSignedData(Boolean doesRequireSignedData) {
        this.doesRequireSignedData = doesRequireSignedData;
        return this;
    }

    public Boolean getTextMode() {
        return textMode;
    }

    public RemoteConnectDirectProfile setTextMode(Boolean textMode) {
        this.textMode = textMode;
        return this;
    }

    public String getPublicKeyID() {
        return publicKeyID;
    }

    public RemoteConnectDirectProfile setPublicKeyID(String publicKeyID) {
        this.publicKeyID = publicKeyID;
        return this;
    }
}
