package com.pe.pcm.b2b.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pe.pcm.pem.b2bproxy.consumerconfiguration.*;
import com.pe.pcm.pem.b2bproxy.producerconfiguration.ProducerCdConfiguration;
import com.pe.pcm.pem.b2bproxy.producerconfiguration.ProducerFtpConfiguration;
import com.pe.pcm.pem.b2bproxy.producerconfiguration.ProducerFtpsConfiguration;
import com.pe.pcm.pem.b2bproxy.producerconfiguration.ProducerSshConfiguration;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RemoteCustomProtocol implements Serializable {

    private String addressLine1;
    private String addressLine2;
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
    private String emailAddress;
    private String givenName;
    private Boolean isInitiatingConsumer;
    private Boolean isInitiatingProducer;
    private Boolean isListeningConsumer;
    private Boolean isListeningProducer;
    private Boolean keyEnabled;
    private String mailbox;
    private String partnerName;
    private String password;
    private String passwordPolicy;
    private String phone;
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


    public String getAddressLine1() {
        return addressLine1;
    }

    public RemoteCustomProtocol setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
        return this;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public RemoteCustomProtocol setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
        return this;
    }

    public Boolean getAppendSuffixToUsername() {
        return appendSuffixToUsername;
    }

    public RemoteCustomProtocol setAppendSuffixToUsername(Boolean appendSuffixToUsername) {
        this.appendSuffixToUsername = appendSuffixToUsername;
        return this;
    }

    public Boolean getAsciiArmor() {
        return asciiArmor;
    }

    public RemoteCustomProtocol setAsciiArmor(Boolean asciiArmor) {
        this.asciiArmor = asciiArmor;
        return this;
    }

    public String getAuthenticationHost() {
        return authenticationHost;
    }

    public RemoteCustomProtocol setAuthenticationHost(String authenticationHost) {
        this.authenticationHost = authenticationHost;
        return this;
    }

    public String getAuthenticationType() {
        return authenticationType;
    }

    public RemoteCustomProtocol setAuthenticationType(String authenticationType) {
        this.authenticationType = authenticationType;
        return this;
    }

    public String getAuthorizedUserKeyName() {
        return authorizedUserKeyName;
    }

    public RemoteCustomProtocol setAuthorizedUserKeyName(String authorizedUserKeyName) {
        this.authorizedUserKeyName = authorizedUserKeyName;
        return this;
    }

    public String getCity() {
        return city;
    }

    public RemoteCustomProtocol setCity(String city) {
        this.city = city;
        return this;
    }

    public String getCode() {
        return code;
    }

    public RemoteCustomProtocol setCode(String code) {
        this.code = code;
        return this;
    }

    public String getCommunity() {
        return community;
    }

    public RemoteCustomProtocol setCommunity(String community) {
        this.community = community;
        return this;
    }

    public ConsumerCdConfiguration getConsumerCdConfiguration() {
        return consumerCdConfiguration;
    }

    public RemoteCustomProtocol setConsumerCdConfiguration(ConsumerCdConfiguration consumerCdConfiguration) {
        this.consumerCdConfiguration = consumerCdConfiguration;
        return this;
    }

    public ConsumerFtpConfiguration getConsumerFtpConfiguration() {
        return consumerFtpConfiguration;
    }

    public RemoteCustomProtocol setConsumerFtpConfiguration(ConsumerFtpConfiguration consumerFtpConfiguration) {
        this.consumerFtpConfiguration = consumerFtpConfiguration;
        return this;
    }

    public ConsumerFtpsConfiguration getConsumerFtpsConfiguration() {
        return consumerFtpsConfiguration;
    }

    public RemoteCustomProtocol setConsumerFtpsConfiguration(ConsumerFtpsConfiguration consumerFtpsConfiguration) {
        this.consumerFtpsConfiguration = consumerFtpsConfiguration;
        return this;
    }

    public ConsumerSshConfiguration getConsumerSshConfiguration() {
        return consumerSshConfiguration;
    }

    public RemoteCustomProtocol setConsumerSshConfiguration(ConsumerSshConfiguration consumerSshConfiguration) {
        this.consumerSshConfiguration = consumerSshConfiguration;
        return this;
    }

    public ConsumerWsConfiguration getConsumerWsConfiguration() {
        return consumerWsConfiguration;
    }

    public RemoteCustomProtocol setConsumerWsConfiguration(ConsumerWsConfiguration consumerWsConfiguration) {
        this.consumerWsConfiguration = consumerWsConfiguration;
        return this;
    }

    public String getCountryOrRegion() {
        return countryOrRegion;
    }

    public RemoteCustomProtocol setCountryOrRegion(String countryOrRegion) {
        this.countryOrRegion = countryOrRegion;
        return this;
    }

    public String getCustomProtocolExtensions() {
        return customProtocolExtensions;
    }

    public RemoteCustomProtocol setCustomProtocolExtensions(String customProtocolExtensions) {
        this.customProtocolExtensions = customProtocolExtensions;
        return this;
    }

    public String getCustomProtocolName() {
        return customProtocolName;
    }

    public RemoteCustomProtocol setCustomProtocolName(String customProtocolName) {
        this.customProtocolName = customProtocolName;
        return this;
    }

    public Boolean getDoesRequireCompressedData() {
        return doesRequireCompressedData;
    }

    public RemoteCustomProtocol setDoesRequireCompressedData(Boolean doesRequireCompressedData) {
        this.doesRequireCompressedData = doesRequireCompressedData;
        return this;
    }

    public Boolean getDoesRequireEncryptedData() {
        return doesRequireEncryptedData;
    }

    public RemoteCustomProtocol setDoesRequireEncryptedData(Boolean doesRequireEncryptedData) {
        this.doesRequireEncryptedData = doesRequireEncryptedData;
        return this;
    }

    public Boolean getDoesRequireSignedData() {
        return doesRequireSignedData;
    }

    public RemoteCustomProtocol setDoesRequireSignedData(Boolean doesRequireSignedData) {
        this.doesRequireSignedData = doesRequireSignedData;
        return this;
    }

    public Boolean getDoesUseSSH() {
        return doesUseSSH;
    }

    public RemoteCustomProtocol setDoesUseSSH(Boolean doesUseSSH) {
        this.doesUseSSH = doesUseSSH;
        return this;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public RemoteCustomProtocol setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public String getGivenName() {
        return givenName;
    }

    public RemoteCustomProtocol setGivenName(String givenName) {
        this.givenName = givenName;
        return this;
    }

    public Boolean getIsInitiatingConsumer() {
        return isInitiatingConsumer;
    }

    public RemoteCustomProtocol setIsInitiatingConsumer(Boolean initiatingConsumer) {
        isInitiatingConsumer = initiatingConsumer;
        return this;
    }

    public Boolean getIsInitiatingProducer() {
        return isInitiatingProducer;
    }

    public RemoteCustomProtocol setIsInitiatingProducer(Boolean initiatingProducer) {
        isInitiatingProducer = initiatingProducer;
        return this;
    }

    public Boolean getIsListeningConsumer() {
        return isListeningConsumer;
    }

    public RemoteCustomProtocol setIsListeningConsumer(Boolean listeningConsumer) {
        isListeningConsumer = listeningConsumer;
        return this;
    }

    public Boolean getIsListeningProducer() {
        return isListeningProducer;
    }

    public RemoteCustomProtocol setIsListeningProducer(Boolean listeningProducer) {
        isListeningProducer = listeningProducer;
        return this;
    }

    public Boolean getKeyEnabled() {
        return keyEnabled;
    }

    public RemoteCustomProtocol setKeyEnabled(Boolean keyEnabled) {
        this.keyEnabled = keyEnabled;
        return this;
    }

    public String getMailbox() {
        return mailbox;
    }

    public RemoteCustomProtocol setMailbox(String mailbox) {
        this.mailbox = mailbox;
        return this;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public RemoteCustomProtocol setPartnerName(String partnerName) {
        this.partnerName = partnerName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RemoteCustomProtocol setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPasswordPolicy() {
        return passwordPolicy;
    }

    public RemoteCustomProtocol setPasswordPolicy(String passwordPolicy) {
        this.passwordPolicy = passwordPolicy;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public RemoteCustomProtocol setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public int getPollingInterval() {
        return pollingInterval;
    }

    public RemoteCustomProtocol setPollingInterval(int pollingInterval) {
        this.pollingInterval = pollingInterval;
        return this;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public RemoteCustomProtocol setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public ProducerCdConfiguration getProducerCdConfiguration() {
        return producerCdConfiguration;
    }

    public RemoteCustomProtocol setProducerCdConfiguration(ProducerCdConfiguration producerCdConfiguration) {
        this.producerCdConfiguration = producerCdConfiguration;
        return this;
    }

    public ProducerFtpConfiguration getProducerFtpConfiguration() {
        return producerFtpConfiguration;
    }

    public RemoteCustomProtocol setProducerFtpConfiguration(ProducerFtpConfiguration producerFtpConfiguration) {
        this.producerFtpConfiguration = producerFtpConfiguration;
        return this;
    }

    public ProducerFtpsConfiguration getProducerFtpsConfiguration() {
        return producerFtpsConfiguration;
    }

    public RemoteCustomProtocol setProducerFtpsConfiguration(ProducerFtpsConfiguration producerFtpsConfiguration) {
        this.producerFtpsConfiguration = producerFtpsConfiguration;
        return this;
    }

    public ProducerSshConfiguration getProducerSshConfiguration() {
        return producerSshConfiguration;
    }

    public RemoteCustomProtocol setProducerSshConfiguration(ProducerSshConfiguration producerSshConfiguration) {
        this.producerSshConfiguration = producerSshConfiguration;
        return this;
    }

    public String getPublicKeyID() {
        return publicKeyID;
    }

    public RemoteCustomProtocol setPublicKeyID(String publicKeyID) {
        this.publicKeyID = publicKeyID;
        return this;
    }

    public String getRemoteFilePattern() {
        return remoteFilePattern;
    }

    public RemoteCustomProtocol setRemoteFilePattern(String remoteFilePattern) {
        this.remoteFilePattern = remoteFilePattern;
        return this;
    }

    public int getSessionTimeout() {
        return sessionTimeout;
    }

    public RemoteCustomProtocol setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
        return this;
    }

    public String getStateOrProvince() {
        return stateOrProvince;
    }

    public RemoteCustomProtocol setStateOrProvince(String stateOrProvince) {
        this.stateOrProvince = stateOrProvince;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public RemoteCustomProtocol setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public Boolean getTextMode() {
        return textMode;
    }

    public RemoteCustomProtocol setTextMode(Boolean textMode) {
        this.textMode = textMode;
        return this;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public RemoteCustomProtocol setTimeZone(String timeZone) {
        this.timeZone = timeZone;
        return this;
    }

    public Boolean getUseGlobalMailbox() {
        return useGlobalMailbox;
    }

    public RemoteCustomProtocol setUseGlobalMailbox(Boolean useGlobalMailbox) {
        this.useGlobalMailbox = useGlobalMailbox;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public RemoteCustomProtocol setUsername(String username) {
        this.username = username;
        return this;
    }
}
