package com.pe.pcm.b2b.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pe.pcm.protocol.As2Model;
import com.pe.pcm.utils.PCMConstants;

import java.io.Serializable;

import static com.pe.pcm.utils.CommonFunctions.isNotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RemoteAs2Profile implements Serializable {
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
    private String community;
    private String mailbox;
    private Boolean doesUseSSH;
    private Boolean asciiArmor;
    private Boolean doesRequireEncryptedData;
    private Boolean doesRequireSignedData;
    private Boolean textMode;
    private String publicKeyID;
    private Boolean isInitiatingConsumer;
    private Boolean isInitiatingProducer;
    private Boolean isListeningConsumer;
    private Boolean isListeningProducer;


    public RemoteAs2Profile(As2Model as2Model,Boolean isUpdate,String community) {
        this.authenticationType = isNotNull(as2Model.getAuthenticationType()) ? as2Model.getAuthenticationType() : "Local";
        this.authenticationHost = isNotNull(as2Model.getAuthenticationHost()) ? as2Model.getAuthenticationHost() : "";
        this.emailAddress = as2Model.getEmailId();
        this.givenName = as2Model.getProfileName();
        this.addressLine1 = as2Model.getAddressLine1();
        this.addressLine2 = as2Model.getAddressLine2();
        this.surname = as2Model.getProfileName();
        this.username = as2Model.getProfileUserName();
        this.password = isUpdate ? (as2Model.getProfileUserPassword().equalsIgnoreCase(PCMConstants.PRAGMA_EDGE_S) ? "" : as2Model.getProfileUserPassword()) : as2Model.getProfileUserPassword();
        this.phone = as2Model.getPhone();
        this.partnerName = as2Model.getProfileName() + "-ref";
        if (isNotNull(as2Model.getCustomProfileName())) {
            this.partnerName = as2Model.getCustomProfileName() + "-ref";
        }
        this.community = community;
        this.doesUseSSH = false;
        this.isInitiatingConsumer = isNotNull(as2Model.getIsInitiatingConsumer()) ? as2Model.getIsInitiatingConsumer() : false;
        this.isListeningConsumer = isNotNull(as2Model.getIsListeningConsumer()) ? as2Model.getIsListeningConsumer() : false;
        this.isInitiatingProducer = isNotNull(as2Model.getIsInitiatingProducer()) ? as2Model.getIsInitiatingProducer() : true;
        this.isListeningProducer = isNotNull(as2Model.getIsListeningProducer()) ? as2Model.getIsListeningProducer() : false;
        this.asciiArmor = isNotNull(as2Model.getAsciiArmor()) ? as2Model.getAsciiArmor() : Boolean.FALSE;
        this.doesRequireEncryptedData = isNotNull(as2Model.getDoesRequireEncryptedData()) ? as2Model.getDoesRequireEncryptedData() : Boolean.FALSE;
        this.doesRequireSignedData = isNotNull(as2Model.getDoesRequireSignedData()) ? as2Model.getDoesRequireSignedData() : Boolean.FALSE;
        this.textMode = isNotNull(as2Model.getTextMode()) ? as2Model.getTextMode() : null;
        this.publicKeyID = isNotNull(as2Model.getPgpInfo()) ? as2Model.getPgpInfo() : null;

    }

    public String getAuthenticationType() {
        return authenticationType;
    }

    public RemoteAs2Profile setAuthenticationType(String authenticationType) {
        this.authenticationType = authenticationType;
        return this;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public RemoteAs2Profile setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public String getGivenName() {
        return givenName;
    }

    public RemoteAs2Profile setGivenName(String givenName) {
        this.givenName = givenName;
        return this;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public RemoteAs2Profile setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
        return this;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public RemoteAs2Profile setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public RemoteAs2Profile setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public RemoteAs2Profile setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RemoteAs2Profile setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public RemoteAs2Profile setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public RemoteAs2Profile setPartnerName(String partnerName) {
        this.partnerName = partnerName;
        return this;
    }

    public String getCommunity() {
        return community;
    }

    public RemoteAs2Profile setCommunity(String community) {
        this.community = community;
        return this;
    }

    public String getMailbox() {
        return mailbox;
    }

    public RemoteAs2Profile setMailbox(String mailbox) {
        this.mailbox = mailbox;
        return this;
    }

    public Boolean getIsInitiatingConsumer() {
        return isInitiatingConsumer;
    }

    public RemoteAs2Profile setIsInitiatingConsumer(Boolean initiatingConsumer) {
        isInitiatingConsumer = initiatingConsumer;
        return this;
    }

    public Boolean getIsInitiatingProducer() {
        return isInitiatingProducer;
    }

    public RemoteAs2Profile setIsInitiatingProducer(Boolean initiatingProducer) {
        isInitiatingProducer = initiatingProducer;
        return this;
    }

    public Boolean getIsListeningConsumer() {
        return isListeningConsumer;
    }

    public RemoteAs2Profile setIsListeningConsumer(Boolean listeningConsumer) {
        isListeningConsumer = listeningConsumer;
        return this;
    }

    public Boolean getIsListeningProducer() {
        return isListeningProducer;
    }

    public RemoteAs2Profile setIsListeningProducer(Boolean listeningProducer) {
        isListeningProducer = listeningProducer;
        return this;
    }

    public Boolean getDoesUseSSH() {
        return doesUseSSH;
    }

    public RemoteAs2Profile setDoesUseSSH(Boolean doesUseSSH) {
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

    public RemoteAs2Profile setAsciiArmor(Boolean asciiArmor) {
        this.asciiArmor = asciiArmor;
        return this;
    }

    public Boolean getDoesRequireEncryptedData() {
        return doesRequireEncryptedData;
    }

    public RemoteAs2Profile setDoesRequireEncryptedData(Boolean doesRequireEncryptedData) {
        this.doesRequireEncryptedData = doesRequireEncryptedData;
        return this;
    }

    public Boolean getDoesRequireSignedData() {
        return doesRequireSignedData;
    }

    public RemoteAs2Profile setDoesRequireSignedData(Boolean doesRequireSignedData) {
        this.doesRequireSignedData = doesRequireSignedData;
        return this;
    }

    public Boolean getTextMode() {
        return textMode;
    }

    public RemoteAs2Profile setTextMode(Boolean textMode) {
        this.textMode = textMode;
        return this;
    }

    public String getPublicKeyID() {
        return publicKeyID;
    }

    public RemoteAs2Profile setPublicKeyID(String publicKeyID) {
        this.publicKeyID = publicKeyID;
        return this;
    }
}
