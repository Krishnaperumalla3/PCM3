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
import com.pe.pcm.pem.b2bproxy.consumerconfiguration.*;
import com.pe.pcm.pem.b2bproxy.producerconfiguration.ProducerCdConfiguration;
import com.pe.pcm.pem.b2bproxy.producerconfiguration.ProducerFtpConfiguration;
import com.pe.pcm.pem.b2bproxy.producerconfiguration.ProducerFtpsConfiguration;
import com.pe.pcm.pem.b2bproxy.producerconfiguration.ProducerSshConfiguration;
import com.pe.pcm.profile.ProfileModel;

import java.io.Serializable;

/**
 * @author Shameer.v.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomProtocolModel extends ProfileModel implements Serializable {

    private Boolean appendSuffixToUsername;
    private Boolean asciiArmor;
    private String authenticationHost;
    private String authenticationType;
    private String authorizedUserKeyName;
    private String city;
    private String code;
    private String community;
    private ConsumerCdConfiguration consumerCdConfiguration;
    private ConsumerFtpConfiguration consumerFtpConfiguration;
    private ConsumerFtpsConfiguration consumerFtpsConfiguration;
    private ConsumerSshConfiguration consumerSshConfiguration;
    private ConsumerWsConfiguration consumerWsConfiguration;
    private String countryOrRegion;
    private String customProtocolExtensions;
    private String customProtocolName;
    private Boolean doesRequireCompressedData;
    private Boolean doesRequireEncryptedData;
    private Boolean doesRequireSignedData;
    private Boolean doesUseSSH;
    private String givenName;
    private Boolean isInitiatingConsumer;
    private Boolean isInitiatingProducer;
    private Boolean isListeningConsumer;
    private Boolean isListeningProducer;
    private Boolean keyEnabled;
    private String mailbox;
    private String password;
    private String passwordPolicy;
    private int pollingInterval;
    private String postalCode;
    private ProducerCdConfiguration producerCdConfiguration;
    private ProducerFtpConfiguration producerFtpConfiguration;
    private ProducerFtpsConfiguration producerFtpsConfiguration;
    private ProducerSshConfiguration producerSshConfiguration;
    private String publicKeyID;
    private String remoteFilePattern;
    private int sessionTimeout;
    private String stateOrProvince;
    private String surname;
    private Boolean textMode;
    private String timeZone;
    private Boolean useGlobalMailbox;
    private String username;


    public Boolean getAppendSuffixToUsername() {
        return appendSuffixToUsername;
    }

    public CustomProtocolModel setAppendSuffixToUsername(Boolean appendSuffixToUsername) {
        this.appendSuffixToUsername = appendSuffixToUsername;
        return this;
    }

    public Boolean getAsciiArmor() {
        return asciiArmor;
    }

    public CustomProtocolModel setAsciiArmor(Boolean asciiArmor) {
        this.asciiArmor = asciiArmor;
        return this;
    }

    public String getAuthenticationHost() {
        return authenticationHost;
    }

    public CustomProtocolModel setAuthenticationHost(String authenticationHost) {
        this.authenticationHost = authenticationHost;
        return this;
    }

    public String getAuthenticationType() {
        return authenticationType;
    }

    public CustomProtocolModel setAuthenticationType(String authenticationType) {
        this.authenticationType = authenticationType;
        return this;
    }

    public String getAuthorizedUserKeyName() {
        return authorizedUserKeyName;
    }

    public CustomProtocolModel setAuthorizedUserKeyName(String authorizedUserKeyName) {
        this.authorizedUserKeyName = authorizedUserKeyName;
        return this;
    }

    public String getCity() {
        return city;
    }

    public CustomProtocolModel setCity(String city) {
        this.city = city;
        return this;
    }

    public String getCode() {
        return code;
    }

    public CustomProtocolModel setCode(String code) {
        this.code = code;
        return this;
    }

    public String getCommunity() {
        return community;
    }

    public CustomProtocolModel setCommunity(String community) {
        this.community = community;
        return this;
    }

    public ConsumerCdConfiguration getConsumerCdConfiguration() {
        return consumerCdConfiguration;
    }

    public CustomProtocolModel setConsumerCdConfiguration(ConsumerCdConfiguration consumerCdConfiguration) {
        this.consumerCdConfiguration = consumerCdConfiguration;
        return this;
    }

    public ConsumerFtpConfiguration getConsumerFtpConfiguration() {
        return consumerFtpConfiguration;
    }

    public CustomProtocolModel setConsumerFtpConfiguration(ConsumerFtpConfiguration consumerFtpConfiguration) {
        this.consumerFtpConfiguration = consumerFtpConfiguration;
        return this;
    }

    public ConsumerFtpsConfiguration getConsumerFtpsConfiguration() {
        return consumerFtpsConfiguration;
    }

    public CustomProtocolModel setConsumerFtpsConfiguration(ConsumerFtpsConfiguration consumerFtpsConfiguration) {
        this.consumerFtpsConfiguration = consumerFtpsConfiguration;
        return this;
    }

    public ConsumerSshConfiguration getConsumerSshConfiguration() {
        return consumerSshConfiguration;
    }

    public CustomProtocolModel setConsumerSshConfiguration(ConsumerSshConfiguration consumerSshConfiguration) {
        this.consumerSshConfiguration = consumerSshConfiguration;
        return this;
    }

    public ConsumerWsConfiguration getConsumerWsConfiguration() {
        return consumerWsConfiguration;
    }

    public CustomProtocolModel setConsumerWsConfiguration(ConsumerWsConfiguration consumerWsConfiguration) {
        this.consumerWsConfiguration = consumerWsConfiguration;
        return this;
    }

    public String getCountryOrRegion() {
        return countryOrRegion;
    }

    public CustomProtocolModel setCountryOrRegion(String countryOrRegion) {
        this.countryOrRegion = countryOrRegion;
        return this;
    }

    public String getCustomProtocolExtensions() {
        return customProtocolExtensions;
    }

    public CustomProtocolModel setCustomProtocolExtensions(String customProtocolExtensions) {
        this.customProtocolExtensions = customProtocolExtensions;
        return this;
    }

    public String getCustomProtocolName() {
        return customProtocolName;
    }

    public CustomProtocolModel setCustomProtocolName(String customProtocolName) {
        this.customProtocolName = customProtocolName;
        return this;
    }

    public Boolean getDoesRequireCompressedData() {
        return doesRequireCompressedData;
    }

    public CustomProtocolModel setDoesRequireCompressedData(Boolean doesRequireCompressedData) {
        this.doesRequireCompressedData = doesRequireCompressedData;
        return this;
    }

    public Boolean getDoesRequireEncryptedData() {
        return doesRequireEncryptedData;
    }

    public CustomProtocolModel setDoesRequireEncryptedData(Boolean doesRequireEncryptedData) {
        this.doesRequireEncryptedData = doesRequireEncryptedData;
        return this;
    }

    public Boolean getDoesRequireSignedData() {
        return doesRequireSignedData;
    }

    public CustomProtocolModel setDoesRequireSignedData(Boolean doesRequireSignedData) {
        this.doesRequireSignedData = doesRequireSignedData;
        return this;
    }

    public Boolean getDoesUseSSH() {
        return doesUseSSH;
    }

    public CustomProtocolModel setDoesUseSSH(Boolean doesUseSSH) {
        this.doesUseSSH = doesUseSSH;
        return this;
    }

    public String getGivenName() {
        return givenName;
    }

    public CustomProtocolModel setGivenName(String givenName) {
        this.givenName = givenName;
        return this;
    }

    public Boolean getIsInitiatingConsumer() {
        return isInitiatingConsumer;
    }

    public CustomProtocolModel setIsInitiatingConsumer(Boolean initiatingConsumer) {
        isInitiatingConsumer = initiatingConsumer;
        return this;
    }

    public Boolean getIsInitiatingProducer() {
        return isInitiatingProducer;
    }

    public CustomProtocolModel setIsInitiatingProducer(Boolean initiatingProducer) {
        isInitiatingProducer = initiatingProducer;
        return this;
    }

    public Boolean getIsListeningConsumer() {
        return isListeningConsumer;
    }

    public CustomProtocolModel setIsListeningConsumer(Boolean listeningConsumer) {
        isListeningConsumer = listeningConsumer;
        return this;
    }

    public Boolean getIsListeningProducer() {
        return isListeningProducer;
    }

    public CustomProtocolModel setIsListeningProducer(Boolean listeningProducer) {
        isListeningProducer = listeningProducer;
        return this;
    }

    public Boolean getKeyEnabled() {
        return keyEnabled;
    }

    public CustomProtocolModel setKeyEnabled(Boolean keyEnabled) {
        this.keyEnabled = keyEnabled;
        return this;
    }

    public String getMailbox() {
        return mailbox;
    }

    public CustomProtocolModel setMailbox(String mailbox) {
        this.mailbox = mailbox;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public CustomProtocolModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPasswordPolicy() {
        return passwordPolicy;
    }

    public CustomProtocolModel setPasswordPolicy(String passwordPolicy) {
        this.passwordPolicy = passwordPolicy;
        return this;
    }

    public int getPollingInterval() {
        return pollingInterval;
    }

    public CustomProtocolModel setPollingInterval(int pollingInterval) {
        this.pollingInterval = pollingInterval;
        return this;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public CustomProtocolModel setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public ProducerCdConfiguration getProducerCdConfiguration() {
        return producerCdConfiguration;
    }

    public CustomProtocolModel setProducerCdConfiguration(ProducerCdConfiguration producerCdConfiguration) {
        this.producerCdConfiguration = producerCdConfiguration;
        return this;
    }

    public ProducerFtpConfiguration getProducerFtpConfiguration() {
        return producerFtpConfiguration;
    }

    public CustomProtocolModel setProducerFtpConfiguration(ProducerFtpConfiguration producerFtpConfiguration) {
        this.producerFtpConfiguration = producerFtpConfiguration;
        return this;
    }

    public ProducerFtpsConfiguration getProducerFtpsConfiguration() {
        return producerFtpsConfiguration;
    }

    public CustomProtocolModel setProducerFtpsConfiguration(ProducerFtpsConfiguration producerFtpsConfiguration) {
        this.producerFtpsConfiguration = producerFtpsConfiguration;
        return this;
    }

    public ProducerSshConfiguration getProducerSshConfiguration() {
        return producerSshConfiguration;
    }

    public CustomProtocolModel setProducerSshConfiguration(ProducerSshConfiguration producerSshConfiguration) {
        this.producerSshConfiguration = producerSshConfiguration;
        return this;
    }

    public String getPublicKeyID() {
        return publicKeyID;
    }

    public CustomProtocolModel setPublicKeyID(String publicKeyID) {
        this.publicKeyID = publicKeyID;
        return this;
    }

    public String getRemoteFilePattern() {
        return remoteFilePattern;
    }

    public CustomProtocolModel setRemoteFilePattern(String remoteFilePattern) {
        this.remoteFilePattern = remoteFilePattern;
        return this;
    }

    public int getSessionTimeout() {
        return sessionTimeout;
    }

    public CustomProtocolModel setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
        return this;
    }

    public String getStateOrProvince() {
        return stateOrProvince;
    }

    public CustomProtocolModel setStateOrProvince(String stateOrProvince) {
        this.stateOrProvince = stateOrProvince;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public CustomProtocolModel setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public Boolean getTextMode() {
        return textMode;
    }

    public CustomProtocolModel setTextMode(Boolean textMode) {
        this.textMode = textMode;
        return this;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public CustomProtocolModel setTimeZone(String timeZone) {
        this.timeZone = timeZone;
        return this;
    }

    public Boolean getUseGlobalMailbox() {
        return useGlobalMailbox;
    }

    public CustomProtocolModel setUseGlobalMailbox(Boolean useGlobalMailbox) {
        this.useGlobalMailbox = useGlobalMailbox;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public CustomProtocolModel setUsername(String username) {
        this.username = username;
        return this;
    }
}
