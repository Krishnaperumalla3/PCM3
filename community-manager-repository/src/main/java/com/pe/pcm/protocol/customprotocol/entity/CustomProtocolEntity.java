package com.pe.pcm.protocol.customprotocol.entity;

import com.pe.pcm.audit.Auditable;
import com.pe.pcm.pem.b2bproxy.consumerconfiguration.*;
import com.pe.pcm.pem.b2bproxy.producerconfiguration.ProducerCdConfiguration;
import com.pe.pcm.pem.b2bproxy.producerconfiguration.ProducerFtpConfiguration;
import com.pe.pcm.pem.b2bproxy.producerconfiguration.ProducerFtpsConfiguration;
import com.pe.pcm.pem.b2bproxy.producerconfiguration.ProducerSshConfiguration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * @author Shameer.
 */
@Entity
@Table(name = "PETPE_SFG_CUSTOMPROTOCOL")
public class CustomProtocolEntity extends Auditable {

    @Id
    private String pkId;
    @NotNull
    private String subscriberType;
    @NotNull
    private String subscriberId;
    @NotNull
    private String protocolType;
    @NotNull
    private String isActive;
    private String appendSuffixToUsername;
    private String asciiArmor;
    private String authenticationHost;
    private String authenticationType;
    private String authorizedUserKeyName;
    private String city;
    private String code;
    private String community;
    private String countryOrRegion;
    private String customProtocolExtensions;
    private String customProtocolName;
    @Column(name = "DOES_REQ_COMPRESSED_DATA")
    private String doesRequireCompressedData;
    @Column(name = "DOES_REQ_ENCRYPTED_DATA")
    private String doesRequireEncryptedData;
    @Column(name = "DOES_REQ_SIGNED_DATA")
    private String doesRequireSignedData;
    private String doesUseSsh;
    private String givenName;
    private String isInitiatingConsumer;
    private String isInitiatingProducer;
    private String isListeningConsumer;
    private String isListeningProducer;
    private String keyEnabled;
    private String mailbox;
    private String password;
    private String passwordPolicy;
    private int pollingInterval;
    private String postalCode;
    private String publicKeyId;
    private String remoteFilePattern;
    private int sessionTimeout;
    private String stateOrProvince;
    private String surname;
    private String textMode;
    private String timeZone;
    private String useGlobalMailbox;
    private String username;

    public String getPkId() {
        return pkId;
    }

    public CustomProtocolEntity setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getSubscriberType() {
        return subscriberType;
    }

    public CustomProtocolEntity setSubscriberType(String subscriberType) {
        this.subscriberType = subscriberType;
        return this;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public CustomProtocolEntity setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
        return this;
    }

    public String getProtocolType() {
        return protocolType;
    }

    public CustomProtocolEntity setProtocolType(String protocolType) {
        this.protocolType = protocolType;
        return this;
    }

    public String getIsActive() {
        return isActive;
    }

    public CustomProtocolEntity setIsActive(String isActive) {
        this.isActive = isActive;
        return this;
    }

    public String getAppendSuffixToUsername() {
        return appendSuffixToUsername;
    }

    public CustomProtocolEntity setAppendSuffixToUsername(String appendSuffixToUsername) {
        this.appendSuffixToUsername = appendSuffixToUsername;
        return this;
    }

    public String getAsciiArmor() {
        return asciiArmor;
    }

    public CustomProtocolEntity setAsciiArmor(String asciiArmor) {
        this.asciiArmor = asciiArmor;
        return this;
    }

    public String getAuthenticationHost() {
        return authenticationHost;
    }

    public CustomProtocolEntity setAuthenticationHost(String authenticationHost) {
        this.authenticationHost = authenticationHost;
        return this;
    }

    public String getAuthenticationType() {
        return authenticationType;
    }

    public CustomProtocolEntity setAuthenticationType(String authenticationType) {
        this.authenticationType = authenticationType;
        return this;
    }

    public String getAuthorizedUserKeyName() {
        return authorizedUserKeyName;
    }

    public CustomProtocolEntity setAuthorizedUserKeyName(String authorizedUserKeyName) {
        this.authorizedUserKeyName = authorizedUserKeyName;
        return this;
    }

    public String getCity() {
        return city;
    }

    public CustomProtocolEntity setCity(String city) {
        this.city = city;
        return this;
    }

    public String getCode() {
        return code;
    }

    public CustomProtocolEntity setCode(String code) {
        this.code = code;
        return this;
    }

    public String getCommunity() {
        return community;
    }

    public CustomProtocolEntity setCommunity(String community) {
        this.community = community;
        return this;
    }

    public String getCountryOrRegion() {
        return countryOrRegion;
    }

    public CustomProtocolEntity setCountryOrRegion(String countryOrRegion) {
        this.countryOrRegion = countryOrRegion;
        return this;
    }

    public String getCustomProtocolExtensions() {
        return customProtocolExtensions;
    }

    public CustomProtocolEntity setCustomProtocolExtensions(String customProtocolExtensions) {
        this.customProtocolExtensions = customProtocolExtensions;
        return this;
    }

    public String getCustomProtocolName() {
        return customProtocolName;
    }

    public CustomProtocolEntity setCustomProtocolName(String customProtocolName) {
        this.customProtocolName = customProtocolName;
        return this;
    }

    public String getDoesRequireCompressedData() {
        return doesRequireCompressedData;
    }

    public CustomProtocolEntity setDoesRequireCompressedData(String doesRequireCompressedData) {
        this.doesRequireCompressedData = doesRequireCompressedData;
        return this;
    }

    public String getDoesRequireEncryptedData() {
        return doesRequireEncryptedData;
    }

    public CustomProtocolEntity setDoesRequireEncryptedData(String doesRequireEncryptedData) {
        this.doesRequireEncryptedData = doesRequireEncryptedData;
        return this;
    }

    public String getDoesRequireSignedData() {
        return doesRequireSignedData;
    }

    public CustomProtocolEntity setDoesRequireSignedData(String doesRequireSignedData) {
        this.doesRequireSignedData = doesRequireSignedData;
        return this;
    }

    public String getDoesUseSsh() {
        return doesUseSsh;
    }

    public CustomProtocolEntity setDoesUseSsh(String doesUseSsh) {
        this.doesUseSsh = doesUseSsh;
        return this;
    }

    public String getGivenName() {
        return givenName;
    }

    public CustomProtocolEntity setGivenName(String givenName) {
        this.givenName = givenName;
        return this;
    }

    public String getIsInitiatingConsumer() {
        return isInitiatingConsumer;
    }

    public CustomProtocolEntity setIsInitiatingConsumer(String isInitiatingConsumer) {
        this.isInitiatingConsumer = isInitiatingConsumer;
        return this;
    }

    public String getIsInitiatingProducer() {
        return isInitiatingProducer;
    }

    public CustomProtocolEntity setIsInitiatingProducer(String isInitiatingProducer) {
        this.isInitiatingProducer = isInitiatingProducer;
        return this;
    }

    public String getIsListeningConsumer() {
        return isListeningConsumer;
    }

    public CustomProtocolEntity setIsListeningConsumer(String isListeningConsumer) {
        this.isListeningConsumer = isListeningConsumer;
        return this;
    }

    public String getIsListeningProducer() {
        return isListeningProducer;
    }

    public CustomProtocolEntity setIsListeningProducer(String isListeningProducer) {
        this.isListeningProducer = isListeningProducer;
        return this;
    }

    public String getKeyEnabled() {
        return keyEnabled;
    }

    public CustomProtocolEntity setKeyEnabled(String keyEnabled) {
        this.keyEnabled = keyEnabled;
        return this;
    }

    public String getMailbox() {
        return mailbox;
    }

    public CustomProtocolEntity setMailbox(String mailbox) {
        this.mailbox = mailbox;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public CustomProtocolEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPasswordPolicy() {
        return passwordPolicy;
    }

    public CustomProtocolEntity setPasswordPolicy(String passwordPolicy) {
        this.passwordPolicy = passwordPolicy;
        return this;
    }

    public int getPollingInterval() {
        return pollingInterval;
    }

    public CustomProtocolEntity setPollingInterval(int pollingInterval) {
        this.pollingInterval = pollingInterval;
        return this;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public CustomProtocolEntity setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public String getPublicKeyId() {
        return publicKeyId;
    }

    public CustomProtocolEntity setPublicKeyId(String publicKeyId) {
        this.publicKeyId = publicKeyId;
        return this;
    }

    public String getRemoteFilePattern() {
        return remoteFilePattern;
    }

    public CustomProtocolEntity setRemoteFilePattern(String remoteFilePattern) {
        this.remoteFilePattern = remoteFilePattern;
        return this;
    }

    public int getSessionTimeout() {
        return sessionTimeout;
    }

    public CustomProtocolEntity setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
        return this;
    }

    public String getStateOrProvince() {
        return stateOrProvince;
    }

    public CustomProtocolEntity setStateOrProvince(String stateOrProvince) {
        this.stateOrProvince = stateOrProvince;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public CustomProtocolEntity setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getTextMode() {
        return textMode;
    }

    public CustomProtocolEntity setTextMode(String textMode) {
        this.textMode = textMode;
        return this;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public CustomProtocolEntity setTimeZone(String timeZone) {
        this.timeZone = timeZone;
        return this;
    }

    public String getUseGlobalMailbox() {
        return useGlobalMailbox;
    }

    public CustomProtocolEntity setUseGlobalMailbox(String useGlobalMailbox) {
        this.useGlobalMailbox = useGlobalMailbox;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public CustomProtocolEntity setUsername(String username) {
        this.username = username;
        return this;
    }
}
