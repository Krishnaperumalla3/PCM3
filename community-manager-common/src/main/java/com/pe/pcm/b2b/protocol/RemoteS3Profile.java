package com.pe.pcm.b2b.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.protocol.AwsS3Model;
import io.swagger.models.auth.In;

import java.io.Serializable;

import static com.pe.pcm.utils.CommonFunctions.convertPoolingIntervalToMinutes;
import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.PCMConstants.PRAGMA_EDGE_S;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RemoteS3Profile implements Serializable {

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
    private Boolean asciiArmor;
    private Boolean doesRequireEncryptedData;
    private Boolean doesRequireSignedData;
    private Boolean textMode;
    private String publicKeyID;
    private Boolean isInitiatingConsumer;
    private Boolean isInitiatingProducer;
    private Boolean isListeningConsumer;
    private Boolean isListeningProducer;
    private Boolean doesUseSSH;
    private ProducerS3Configuration producerS3Configuration;
    private ConsumerS3Configuration consumerS3Configuration;

    public RemoteS3Profile(AwsS3Model awsS3Model, Boolean isUpdate, String community) {
        this.authenticationType = isNotNull(awsS3Model.getAuthenticationType()) ? awsS3Model.getAuthenticationType() : "Local";
        this.authenticationHost = isNotNull(awsS3Model.getAuthenticationHost()) ? awsS3Model.getAuthenticationHost() : "";
        this.emailAddress = awsS3Model.getEmailId();
        this.givenName = awsS3Model.getProfileName();
        this.addressLine1 = awsS3Model.getAddressLine1();
        this.addressLine2 = awsS3Model.getAddressLine2();
        this.surname = awsS3Model.getProfileName();
        this.username = awsS3Model.getProfileUserName();
        this.password = isUpdate ? (awsS3Model.getProfileUserPassword().equalsIgnoreCase(PRAGMA_EDGE_S) ? "" : awsS3Model.getProfileUserPassword()) : awsS3Model.getProfileUserPassword();
        this.phone = awsS3Model.getPhone();
        this.partnerName = awsS3Model.getProfileName();
        if (isNotNull(awsS3Model.getCustomProfileName())) {
            this.partnerName = awsS3Model.getCustomProfileName();
        }

        this.community = community;
        this.isInitiatingConsumer = isNotNull(awsS3Model.getInitiatingConsumer()) ? awsS3Model.getInitiatingConsumer() : FALSE;
        this.isInitiatingProducer = isNotNull(awsS3Model.getInitiatingProducer()) ? awsS3Model.getInitiatingProducer() : TRUE;
        this.isListeningConsumer = isNotNull(awsS3Model.getListeningConsumer()) ? awsS3Model.getListeningConsumer() : FALSE;
        this.isListeningProducer = isNotNull(awsS3Model.getListeningProducer()) ? awsS3Model.getListeningProducer() : FALSE;
        this.doesUseSSH = awsS3Model.isDoesUseSSH();

        if (this.isListeningConsumer || this.isListeningProducer) {
            if (awsS3Model.getPoolingInterval() == null || awsS3Model.getPoolingInterval().equalsIgnoreCase("ON")) {
                throw GlobalExceptionHandler.internalServerError("Polling interval should not be null or OnArrival");
            }
            this.pollingInterval = convertPoolingIntervalToMinutes(awsS3Model.getPoolingInterval());
        } else {
            this.pollingInterval = awsS3Model.getPoolingInterval().equalsIgnoreCase("ON") ? null :
                    convertPoolingIntervalToMinutes(awsS3Model.getPoolingInterval());
        }
        this.asciiArmor = isNotNull(awsS3Model.getAsciiArmor()) ? awsS3Model.getAsciiArmor() : Boolean.FALSE;
        this.doesRequireEncryptedData = isNotNull(awsS3Model.getDoesRequireEncryptedData()) ? awsS3Model.getDoesRequireEncryptedData() : Boolean.FALSE;
        this.doesRequireSignedData = isNotNull(awsS3Model.getDoesRequireSignedData()) ? awsS3Model.getDoesRequireSignedData() : Boolean.FALSE;
        this.textMode = isNotNull(awsS3Model.getTextMode()) ? awsS3Model.getTextMode() : null;
        this.publicKeyID = isNotNull(awsS3Model.getPgpInfo()) ? awsS3Model.getPgpInfo() : null;
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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public Integer getPollingInterval() {
        return pollingInterval;
    }

    public void setPollingInterval(Integer pollingInterval) {
        this.pollingInterval = pollingInterval;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public Boolean getIsInitiatingConsumer() {
        return isInitiatingConsumer;
    }

    public RemoteS3Profile setIsInitiatingConsumer(Boolean initiatingConsumer) {
        isInitiatingConsumer = initiatingConsumer;
        return this;
    }

    public Boolean getIsInitiatingProducer() {
        return isInitiatingProducer;
    }

    public RemoteS3Profile setIsInitiatingProducer(Boolean initiatingProducer) {
        isInitiatingProducer = initiatingProducer;
        return this;
    }

    public Boolean getIsListeningConsumer() {
        return isListeningConsumer;
    }

    public RemoteS3Profile setIsListeningConsumer(Boolean listeningConsumer) {
        isListeningConsumer = listeningConsumer;
        return this;
    }

    public Boolean getIsListeningProducer() {
        return isListeningProducer;
    }

    public RemoteS3Profile setIsListeningProducer(Boolean listeningProducer) {
        isListeningProducer = listeningProducer;
        return this;
    }

    public Boolean getDoesUseSSH() {
        return doesUseSSH;
    }

    public void setDoesUseSSH(Boolean doesUseSSH) {
        this.doesUseSSH = doesUseSSH;
    }

    public ProducerS3Configuration getProducerS3Configuration() {
        return producerS3Configuration;
    }

    public void setProducerS3Configuration(ProducerS3Configuration producerS3Configuration) {
        this.producerS3Configuration = producerS3Configuration;
    }

    public ConsumerS3Configuration getConsumerS3Configuration() {
        return consumerS3Configuration;
    }

    public void setConsumerS3Configuration(ConsumerS3Configuration consumerS3Configuration) {
        this.consumerS3Configuration = consumerS3Configuration;
    }

    public Boolean getAsciiArmor() {
        return asciiArmor;
    }

    public RemoteS3Profile setAsciiArmor(Boolean asciiArmor) {
        this.asciiArmor = asciiArmor;
        return this;
    }

    public Boolean getDoesRequireEncryptedData() {
        return doesRequireEncryptedData;
    }

    public RemoteS3Profile setDoesRequireEncryptedData(Boolean doesRequireEncryptedData) {
        this.doesRequireEncryptedData = doesRequireEncryptedData;
        return this;
    }

    public Boolean getDoesRequireSignedData() {
        return doesRequireSignedData;
    }

    public RemoteS3Profile setDoesRequireSignedData(Boolean doesRequireSignedData) {
        this.doesRequireSignedData = doesRequireSignedData;
        return this;
    }

    public Boolean getTextMode() {
        return textMode;
    }

    public RemoteS3Profile setTextMode(Boolean textMode) {
        this.textMode = textMode;
        return this;
    }

    public String getPublicKeyID() {
        return publicKeyID;
    }

    public RemoteS3Profile setPublicKeyID(String publicKeyID) {
        this.publicKeyID = publicKeyID;
        return this;
    }
}
