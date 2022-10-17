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
import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.profile.ProfileModel;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Chenchu Kiran Reddy.
 */

@JacksonXmlRootElement(localName = "PARTNER_REMOTE_FTP")
@JsonPropertyOrder({"pkId", "profileName", "profileId", "emailId", "phone",
        "protocol", "addressLine1", "addressLine2", "status", "subscriberType",
        "preferredAuthenticationType", "preferredCipher", "characterEncoding", "compression", "preferredMacAlgorithm",
        "localPortRange", "userIdentityKey", "knownHostKey", "connectionRetryCount", "retryDelay", "responseTimeOut",
        "encryptionStrength", "useCCC", "useImplicitSSL", "certificateId", "remoteHost", "remotePort", "userName",
        "password", "inDirectory", "outDirectory", "fileType", "transferType", "connectionType", "noOfRetries",
        "retryInterval", "createUserInSI", "createDirectoryInSI", "deleteAfterCollection", "poolingInterval",
        "adapterName", "hubInfo", "userIdentity", "groups", "authorizedUserKeys"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class RemoteProfileModel extends ProfileModel implements Serializable {

    @JsonIgnore
    private String subscriberType;
    @JsonIgnore
    private String siProfileId;
    @JsonIgnore
    private String orgIdentifier;
    /* Start : For As2 */
    @JsonIgnore
    private String as2EmailAddress;
    @JsonIgnore
    private String endPoint;
    @JsonIgnore
    private String compressData;
    @JsonIgnore
    private String payloadType;
    @JsonIgnore
    private String mimeType;
    @JsonIgnore
    private String mimeSubType;
    @JsonIgnore
    private String sslType;
    @JsonIgnore
    private String cipherStrength;
    @JsonIgnore
    private String sciProfileObjectId;

    //Encryption
    @JsonIgnore
    private String keyCertificatePassphrase;
    @JsonIgnore
    private String payloadSecurity;
    @JsonIgnore
    private String encryptionAlgorithm;
    @JsonIgnore
    private String signatureAlgorithm;

    //MDN
    @JsonIgnore
    private Boolean mdn;
    @JsonIgnore
    private String mdnType;
    @JsonIgnore
    private String mdnEncryption;
    @JsonIgnore
    private String receiptToAddress;

    //Certificates
    @JsonIgnore
    private String exchangeCertificateId;
    @JsonIgnore
    private String signingCertificationId;
    @JsonIgnore
    private String caCertificateName;

    /* End : For As2 */

    //For SFTP
    private String preferredAuthenticationType;
    private String preferredCipher;
    private String characterEncoding;
    private String compression;
    private String userIdentityKey;
    private String preferredMacAlgorithm;
    private String localPortRange;
    private String knownHostKey;
    private Boolean isSIProfile;

    private String connectionRetryCount;
    private String retryDelay;
    private String responseTimeOut;

    //For FTPS
    private String encryptionStrength;
    private Boolean useCCC;
    private Boolean useImplicitSSL;
    private String certificateId;

    //Common
    private String remoteHost;
    private String remotePort;
    private String profileUserName;
    private String profileUserPassword;
    @NotNull
    private String userName;
    private String password;
    private String inDirectory;
    private String outDirectory;
    private String fileType;
    private String transferType;
    private String connectionType;
    private String noOfRetries;
    private String retryInterval;
    private Boolean createUserInSI = false;
    private Boolean createDirectoryInSI = false;
    private Boolean deleteAfterCollection;
    @NotNull
    private String poolingInterval;
    @NotNull
    private String adapterName;

    //User Details
    private String userIdentity;
    private List<CommunityManagerNameModel> groups = new ArrayList<>();
    private List<CommunityManagerNameModel> authorizedUserKeys = new ArrayList<>();
    private String pwdPolicy;
    private String sessionTimeout;
    private String surname;
    private String givenName;

    private Boolean isInitiatingConsumer;
    private Boolean isInitiatingProducer;
    private Boolean isListeningConsumer;
    private Boolean isListeningProducer;
    private boolean doesUseSSH;
    private String siteCommand;

    private boolean mergeUser;
    private String secondaryMail;

    private boolean resetPermissions;

    //New Fields Added By Kiran for new Api Design
    private String city;
    private String state;
    private String zipCode;
    private String country;
    private List<CommunityManagerNameModel> caCertificateNames = new ArrayList<>();
    private List<CommunityManagerNameModel> keyCertificateNames = new ArrayList<>();
    private List<CommunityManagerNameModel> knownHostKeyNames = new ArrayList<>();

    private boolean createSfgProfile;
    private boolean createProducerProfile;

    @JsonIgnore
    private Timestamp pwdLastChangeDone;
    
    private boolean useBaseDirectoryForVirtualRoot;
    @JsonIgnore
    private boolean isPatch;
    private String authenticationType;
    private String authenticationHost;
    private String virtualRoot;
    private String routingRuleName;
    private Boolean asciiArmor;
    private Boolean doesRequireEncryptedData;
    private Boolean doesRequireSignedData;
    private Boolean textMode;
    private List<CommunityManagerNameModel> permissions = new ArrayList<>();


    @JacksonXmlProperty(localName = "SUBSCRIBER_TYPE")
    public String getSubscriberType() {
        return subscriberType;
    }

    public RemoteProfileModel setSubscriberType(String subscriberType) {
        this.subscriberType = subscriberType;
        return this;
    }

    @JacksonXmlProperty(localName = "PREFERRED_AUTHENTICATION_TYPE")
    public String getPreferredAuthenticationType() {
        return preferredAuthenticationType;
    }

    public RemoteProfileModel setPreferredAuthenticationType(String preferredAuthenticationType) {
        this.preferredAuthenticationType = preferredAuthenticationType;
        return this;
    }

    @JacksonXmlProperty(localName = "PREFERRED_CIPHER")
    public String getPreferredCipher() {
        return preferredCipher;
    }

    public RemoteProfileModel setPreferredCipher(String preferredCipher) {
        this.preferredCipher = preferredCipher;
        return this;
    }

    @JacksonXmlProperty(localName = "CHARACTER_ENCODING")
    public String getCharacterEncoding() {
        return characterEncoding;
    }

    public RemoteProfileModel setCharacterEncoding(String characterEncoding) {
        this.characterEncoding = characterEncoding;
        return this;
    }

    @JacksonXmlProperty(localName = "COMPRESSION")
    public String getCompression() {
        return compression;
    }

    public RemoteProfileModel setCompression(String compression) {
        this.compression = compression;
        return this;
    }

    @JacksonXmlProperty(localName = "PREFERRED_MAC_ALGORITHM")
    public String getPreferredMacAlgorithm() {
        return preferredMacAlgorithm;
    }

    public RemoteProfileModel setPreferredMacAlgorithm(String preferredMacAlgorithm) {
        this.preferredMacAlgorithm = preferredMacAlgorithm;
        return this;
    }

    @JacksonXmlProperty(localName = "LOCAL_PORT_RANGE")
    public String getLocalPortRange() {
        return localPortRange;
    }

    public RemoteProfileModel setLocalPortRange(String localPortRange) {
        this.localPortRange = localPortRange;
        return this;
    }

    @JacksonXmlProperty(localName = "USER_IDENTITY_KEY")
    public String getUserIdentityKey() {
        return userIdentityKey;
    }

    public RemoteProfileModel setUserIdentityKey(String userIdentityKey) {
        this.userIdentityKey = userIdentityKey;
        return this;
    }

    @JacksonXmlProperty(localName = "KNOWNHOST_KEY")
    public String getKnownHostKey() {
        return knownHostKey;
    }

    public RemoteProfileModel setKnownHostKey(String knownHostKey) {
        this.knownHostKey = knownHostKey;
        return this;
    }

    @JacksonXmlProperty(localName = "CONNECTION_RETRY_COUNT")
    public String getConnectionRetryCount() {
        return connectionRetryCount;
    }

    public RemoteProfileModel setConnectionRetryCount(String connectionRetryCount) {
        this.connectionRetryCount = connectionRetryCount;
        return this;
    }

    @JacksonXmlProperty(localName = "RETRY_DELAY")
    public String getRetryDelay() {
        return retryDelay;
    }

    public RemoteProfileModel setRetryDelay(String retryDelay) {
        this.retryDelay = retryDelay;
        return this;
    }

    @JacksonXmlProperty(localName = "RESPONSE_TIMEOUT")
    public String getResponseTimeOut() {
        return responseTimeOut;
    }

    public RemoteProfileModel setResponseTimeOut(String responseTimeOut) {
        this.responseTimeOut = responseTimeOut;
        return this;
    }

    @JacksonXmlProperty(localName = "ENCRYPTION_STRENGTH")
    public String getEncryptionStrength() {
        return encryptionStrength;
    }

    public RemoteProfileModel setEncryptionStrength(String encryptionStrength) {
        this.encryptionStrength = encryptionStrength;
        return this;
    }

    @JacksonXmlProperty(localName = "USE_CCC")
    public Boolean getUseCCC() {
        return useCCC;
    }

    public RemoteProfileModel setUseCCC(Boolean useCCC) {
        this.useCCC = useCCC;
        return this;
    }

    @JacksonXmlProperty(localName = "USE_IMPLICIT_SSL")
    public Boolean getUseImplicitSSL() {
        return useImplicitSSL;
    }

    public RemoteProfileModel setUseImplicitSSL(Boolean useImplicitSSL) {
        this.useImplicitSSL = useImplicitSSL;
        return this;
    }

    @JacksonXmlProperty(localName = "CERTIFICATE_ID")
    public String getCertificateId() {
        return certificateId;
    }

    public RemoteProfileModel setCertificateId(String certificateId) {
        this.certificateId = certificateId;
        return this;
    }

    @JacksonXmlProperty(localName = "REMOTE_HOST")
    public String getRemoteHost() {
        return remoteHost;
    }

    public RemoteProfileModel setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
        return this;
    }

    @JacksonXmlProperty(localName = "REMOTE_PORT")
    public String getRemotePort() {
        return remotePort;
    }

    public RemoteProfileModel setRemotePort(String remotePort) {
        this.remotePort = remotePort;
        return this;
    }

    @JacksonXmlProperty(localName = "USER_NAME")
    public String getUserName() {
        return userName;
    }

    public RemoteProfileModel setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    @JacksonXmlProperty(localName = "PASSWORD")
    public String getPassword() {
        return password;
    }

    public RemoteProfileModel setPassword(String password) {
        this.password = password;
        return this;
    }

    @JacksonXmlProperty(localName = "IN_DIRECTORY")
    public String getInDirectory() {
        return inDirectory;
    }

    public RemoteProfileModel setInDirectory(String inDirectory) {
        this.inDirectory = inDirectory;
        return this;
    }

    @JacksonXmlProperty(localName = "OUT_DIRECTORY")
    public String getOutDirectory() {
        return outDirectory;
    }

    public RemoteProfileModel setOutDirectory(String outDirectory) {
        this.outDirectory = outDirectory;
        return this;
    }

    @JacksonXmlProperty(localName = "FILE_TYPE")
    public String getFileType() {
        return fileType;
    }

    public RemoteProfileModel setFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    @JacksonXmlProperty(localName = "TRANSFER_TYPE")
    public String getTransferType() {
        return transferType;
    }

    public RemoteProfileModel setTransferType(String transferType) {
        this.transferType = transferType;
        return this;
    }

    @JacksonXmlProperty(localName = "CONNECTION_TYPE")
    public String getConnectionType() {
        return connectionType;
    }

    public RemoteProfileModel setConnectionType(String connectionType) {
        this.connectionType = connectionType;
        return this;
    }

    @JacksonXmlProperty(localName = "NO_OF_RETRIES")
    public String getNoOfRetries() {
        return noOfRetries;
    }

    public RemoteProfileModel setNoOfRetries(String noOfRetries) {
        this.noOfRetries = noOfRetries;
        return this;
    }

    @JacksonXmlProperty(localName = "POOLING_INTERVAL")
    public String getPoolingInterval() {
        return poolingInterval;
    }

    public RemoteProfileModel setPoolingInterval(String poolingInterval) {
        this.poolingInterval = poolingInterval;
        return this;
    }

    @JacksonXmlProperty(localName = "ADAPTER_NAME")
    public String getAdapterName() {
        return adapterName;
    }

    public RemoteProfileModel setAdapterName(String adapterName) {
        this.adapterName = adapterName;
        return this;
    }

    @JacksonXmlProperty(localName = "RETRY_INTERVAL")
    public String getRetryInterval() {
        return retryInterval;
    }

    public RemoteProfileModel setRetryInterval(String retryInterval) {
        this.retryInterval = retryInterval;
        return this;
    }

    @JacksonXmlProperty(localName = "CREATE_USER_IN_SI")
    public Boolean getCreateUserInSI() {
        return createUserInSI;
    }

    public RemoteProfileModel setCreateUserInSI(Boolean createUserInSI) {
        this.createUserInSI = createUserInSI;
        return this;
    }

    @JacksonXmlProperty(localName = "CREATE_MAIL_BOX_IN_SI")
    public Boolean getCreateDirectoryInSI() {
        return createDirectoryInSI;
    }

    public RemoteProfileModel setCreateDirectoryInSI(Boolean createDirectoryInSI) {
        this.createDirectoryInSI = createDirectoryInSI;
        return this;
    }

    @JacksonXmlProperty(localName = "DELETE_AFTER_COLLECTION")
    public Boolean getDeleteAfterCollection() {
        return deleteAfterCollection;
    }

    public RemoteProfileModel setDeleteAfterCollection(Boolean deleteAfterCollection) {
        this.deleteAfterCollection = deleteAfterCollection;
        return this;
    }

    @JacksonXmlProperty(localName = "GROUPS")
    public List<CommunityManagerNameModel> getGroups() {
        return groups;
    }

    public RemoteProfileModel setGroups(List<CommunityManagerNameModel> groups) {
        this.groups = groups;
        return this;
    }

    @JacksonXmlProperty(localName = "AUTHORIZED_USER_KEYS")
    public List<CommunityManagerNameModel> getAuthorizedUserKeys() {
        return authorizedUserKeys;
    }

    public RemoteProfileModel setAuthorizedUserKeys(List<CommunityManagerNameModel> authorizedUserKeys) {
        this.authorizedUserKeys = authorizedUserKeys;
        return this;
    }

    @JacksonXmlProperty(localName = "USER_IDENTITY")
    public String getUserIdentity() {
        return userIdentity;
    }

    public RemoteProfileModel setUserIdentity(String userIdentity) {
        this.userIdentity = userIdentity;
        return this;
    }

    public boolean isMergeUser() {
        return mergeUser;
    }

    public RemoteProfileModel setMergeUser(boolean mergeUser) {
        this.mergeUser = mergeUser;
        return this;
    }

    public String getPwdPolicy() {
        return pwdPolicy;
    }

    public RemoteProfileModel setPwdPolicy(String pwdPolicy) {
        this.pwdPolicy = pwdPolicy;
        return this;
    }

    public String getSessionTimeout() {
        return sessionTimeout;
    }

    public RemoteProfileModel setSessionTimeout(String sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
        return this;
    }

    public String getSecondaryMail() {
        return secondaryMail;
    }

    public RemoteProfileModel setSecondaryMail(String secondaryMail) {
        this.secondaryMail = secondaryMail;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public RemoteProfileModel setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getGivenName() {
        return givenName;
    }

    public RemoteProfileModel setGivenName(String givenName) {
        this.givenName = givenName;
        return this;
    }

    public boolean isResetPermissions() {
        return resetPermissions;
    }

    public RemoteProfileModel setResetPermissions(boolean resetPermissions) {
        this.resetPermissions = resetPermissions;
        return this;
    }

    public Boolean getIsInitiatingConsumer() {
        return isInitiatingConsumer;
    }

    public RemoteProfileModel setIsInitiatingConsumer(Boolean initiatingConsumer) {
        isInitiatingConsumer = initiatingConsumer;
        return this;
    }

    public Boolean getIsInitiatingProducer() {
        return isInitiatingProducer;
    }

    public RemoteProfileModel setIsInitiatingProducer(Boolean initiatingProducer) {
        isInitiatingProducer = initiatingProducer;
        return this;
    }

    public Boolean getIsListeningConsumer() {
        return isListeningConsumer;
    }

    public RemoteProfileModel setIsListeningConsumer(Boolean listeningConsumer) {
        isListeningConsumer = listeningConsumer;
        return this;
    }

    public Boolean getIsListeningProducer() {
        return isListeningProducer;
    }

    public RemoteProfileModel setIsListeningProducer(Boolean listeningProducer) {
        isListeningProducer = listeningProducer;
        return this;
    }

    public String getCity() {
        return city;
    }

    public RemoteProfileModel setCity(String city) {
        this.city = city;
        return this;
    }

    public String getState() {
        return state;
    }

    public RemoteProfileModel setState(String state) {
        this.state = state;
        return this;
    }

    public String getZipCode() {
        return zipCode;
    }

    public RemoteProfileModel setZipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public RemoteProfileModel setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getSiteCommand() {
        return siteCommand;
    }

    public RemoteProfileModel setSiteCommand(String siteCommand) {
        this.siteCommand = siteCommand;
        return this;
    }

    public List<CommunityManagerNameModel> getCaCertificateNames() {
        return caCertificateNames;
    }

    public RemoteProfileModel setCaCertificateNames(List<CommunityManagerNameModel> caCertificateNames) {
        this.caCertificateNames = caCertificateNames;
        return this;
    }

    public List<CommunityManagerNameModel> getKeyCertificateNames() {
        return keyCertificateNames;
    }

    public RemoteProfileModel setKeyCertificateNames(List<CommunityManagerNameModel> keyCertificateNames) {
        this.keyCertificateNames = keyCertificateNames;
        return this;
    }

    public List<CommunityManagerNameModel> getKnownHostKeyNames() {
        return knownHostKeyNames;
    }

    public RemoteProfileModel setKnownHostKeyNames(List<CommunityManagerNameModel> knownHostKeyNames) {
        this.knownHostKeyNames = knownHostKeyNames;
        return this;
    }

    public Boolean getIsSIProfile() {
        return isSIProfile;
    }

    public RemoteProfileModel setIsSIProfile(Boolean isSIProfile) {
        this.isSIProfile = isSIProfile;
        return this;
    }

    public boolean isCreateSfgProfile() {
        return createSfgProfile;
    }

    public RemoteProfileModel setCreateSfgProfile(boolean createSfgProfile) {
        this.createSfgProfile = createSfgProfile;
        return this;
    }

    public boolean isCreateProducerProfile() {
        return createProducerProfile;
    }

    public RemoteProfileModel setCreateProducerProfile(boolean createProducerProfile) {
        this.createProducerProfile = createProducerProfile;
        return this;
    }

    public String getSiProfileId() {
        return siProfileId;
    }

    public RemoteProfileModel setSiProfileId(String siProfileId) {
        this.siProfileId = siProfileId;
        return this;
    }

    public String getProfileUserName() {
        return profileUserName;
    }

    public RemoteProfileModel setProfileUserName(String profileUserName) {
        this.profileUserName = profileUserName;
        return this;
    }

    public String getProfileUserPassword() {
        return profileUserPassword;
    }

    public RemoteProfileModel setProfileUserPassword(String profileUserPassword) {
        this.profileUserPassword = profileUserPassword;
        return this;
    }

    public String getOrgIdentifier() {
        return orgIdentifier;
    }

    public RemoteProfileModel setOrgIdentifier(String orgIdentifier) {
        this.orgIdentifier = orgIdentifier;
        return this;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public RemoteProfileModel setEndPoint(String endPoint) {
        this.endPoint = endPoint;
        return this;
    }

    public String getPayloadType() {
        return payloadType;
    }

    public RemoteProfileModel setPayloadType(String payloadType) {
        this.payloadType = payloadType;
        return this;
    }

    public String getMimeType() {
        return mimeType;
    }

    public RemoteProfileModel setMimeType(String mimeType) {
        this.mimeType = mimeType;
        return this;
    }

    public String getMimeSubType() {
        return mimeSubType;
    }

    public RemoteProfileModel setMimeSubType(String mimeSubType) {
        this.mimeSubType = mimeSubType;
        return this;
    }

    public String getSslType() {
        return sslType;
    }

    public RemoteProfileModel setSslType(String sslType) {
        this.sslType = sslType;
        return this;
    }

    public String getCipherStrength() {
        return cipherStrength;
    }

    public RemoteProfileModel setCipherStrength(String cipherStrength) {
        this.cipherStrength = cipherStrength;
        return this;
    }

    public String getKeyCertificatePassphrase() {
        return keyCertificatePassphrase;
    }

    public RemoteProfileModel setKeyCertificatePassphrase(String keyCertificatePassphrase) {
        this.keyCertificatePassphrase = keyCertificatePassphrase;
        return this;
    }

    public String getPayloadSecurity() {
        return payloadSecurity;
    }

    public RemoteProfileModel setPayloadSecurity(String payloadSecurity) {
        this.payloadSecurity = payloadSecurity;
        return this;
    }

    public String getEncryptionAlgorithm() {
        return encryptionAlgorithm;
    }

    public RemoteProfileModel setEncryptionAlgorithm(String encryptionAlgorithm) {
        this.encryptionAlgorithm = encryptionAlgorithm;
        return this;
    }

    public String getSignatureAlgorithm() {
        return signatureAlgorithm;
    }

    public RemoteProfileModel setSignatureAlgorithm(String signatureAlgorithm) {
        this.signatureAlgorithm = signatureAlgorithm;
        return this;
    }

    public Boolean getMdn() {
        return mdn;
    }

    public RemoteProfileModel setMdn(Boolean mdn) {
        this.mdn = mdn;
        return this;
    }

    public String getMdnType() {
        return mdnType;
    }

    public RemoteProfileModel setMdnType(String mdnType) {
        this.mdnType = mdnType;
        return this;
    }

    public String getMdnEncryption() {
        return mdnEncryption;
    }

    public RemoteProfileModel setMdnEncryption(String mdnEncryption) {
        this.mdnEncryption = mdnEncryption;
        return this;
    }

    public String getReceiptToAddress() {
        return receiptToAddress;
    }

    public RemoteProfileModel setReceiptToAddress(String receiptToAddress) {
        this.receiptToAddress = receiptToAddress;
        return this;
    }

    public String getExchangeCertificateId() {
        return exchangeCertificateId;
    }

    public RemoteProfileModel setExchangeCertificateId(String exchangeCertificateId) {
        this.exchangeCertificateId = exchangeCertificateId;
        return this;
    }

    public String getSigningCertificationId() {
        return signingCertificationId;
    }

    public RemoteProfileModel setSigningCertificationId(String signingCertificationId) {
        this.signingCertificationId = signingCertificationId;
        return this;
    }

    public String getCaCertificateName() {
        return caCertificateName;
    }

    public RemoteProfileModel setCaCertificateName(String caCertificateName) {
        this.caCertificateName = caCertificateName;
        return this;
    }

    public String getCompressData() {
        return compressData;
    }

    public RemoteProfileModel setCompressData(String compressData) {
        this.compressData = compressData;
        return this;
    }

    public String getAs2EmailAddress() {
        return as2EmailAddress;
    }

    public RemoteProfileModel setAs2EmailAddress(String as2EmailAddress) {
        this.as2EmailAddress = as2EmailAddress;
        return this;
    }

    public Timestamp getPwdLastChangeDone() {
        return pwdLastChangeDone;
    }

    public RemoteProfileModel setPwdLastChangeDone(Timestamp pwdLastChangeDone) {
        this.pwdLastChangeDone = pwdLastChangeDone;
        return this;
    }

    public boolean isDoesUseSSH() {
        return doesUseSSH;
    }

    public RemoteProfileModel setDoesUseSSH(boolean doesUseSSH) {
        this.doesUseSSH = doesUseSSH;
        return this;
    }

    public boolean isUseBaseDirectoryForVirtualRoot() {
        return useBaseDirectoryForVirtualRoot;
    }

    public RemoteProfileModel setUseBaseDirectoryForVirtualRoot(boolean useBaseDirectoryForVirtualRoot) {
        this.useBaseDirectoryForVirtualRoot = useBaseDirectoryForVirtualRoot;
        return this;
    }

    public boolean isPatch() {
        return isPatch;
    }

    public RemoteProfileModel setPatch(boolean patch) {
        isPatch = patch;
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

    public String getVirtualRoot() {
        return virtualRoot;
    }

    public RemoteProfileModel setVirtualRoot(String virtualRoot) {
        this.virtualRoot = virtualRoot;
        return this;
    }

    public String getRoutingRuleName() {
        return routingRuleName;
    }

    public void setRoutingRuleName(String routingRuleName) {
        this.routingRuleName = routingRuleName;
    }

    public Boolean getAsciiArmor() {
        return asciiArmor;
    }

    public RemoteProfileModel setAsciiArmor(Boolean asciiArmor) {
        this.asciiArmor = asciiArmor;
        return this;
    }

    public Boolean getDoesRequireEncryptedData() {
        return doesRequireEncryptedData;
    }

    public RemoteProfileModel setDoesRequireEncryptedData(Boolean doesRequireEncryptedData) {
        this.doesRequireEncryptedData = doesRequireEncryptedData;
        return this;
    }

    public Boolean getDoesRequireSignedData() {
        return doesRequireSignedData;
    }

    public RemoteProfileModel setDoesRequireSignedData(Boolean doesRequireSignedData) {
        this.doesRequireSignedData = doesRequireSignedData;
        return this;
    }

    public Boolean getTextMode() {
        return textMode;
    }

    public RemoteProfileModel setTextMode(Boolean textMode) {
        this.textMode = textMode;
        return this;
    }

    public List<CommunityManagerNameModel> getPermissions() {
        return permissions;
    }

    public RemoteProfileModel setPermissions(List<CommunityManagerNameModel> permissions) {
        this.permissions = permissions;
        return this;
    }

    public String getSciProfileObjectId() {
        return sciProfileObjectId;
    }

    public RemoteProfileModel setSciProfileObjectId(String sciProfileObjectId) {
        this.sciProfileObjectId = sciProfileObjectId;
        return this;
    }
}
