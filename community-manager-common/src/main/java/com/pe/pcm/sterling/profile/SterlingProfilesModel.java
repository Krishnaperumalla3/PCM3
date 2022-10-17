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

package com.pe.pcm.sterling.profile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pe.pcm.common.CommunityManagerKeyValueModel;
import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.profile.ProfileModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Chenchu Kiran.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SterlingProfilesModel extends ProfileModel implements Serializable {

    @JsonIgnore
    private String subscriberType;
    @JsonIgnore
    private String receivingProtocol;
    private String secondaryMail;
    private String partnerCode;
    @JsonIgnore
    private Boolean consumerProfile;
    @JsonIgnore
    private boolean consumerCreated;
    @JsonIgnore
    private String profileType;
    @JsonIgnore
    private Boolean createProducer;
    @JsonIgnore
    private Boolean sfgSubDetailsLoaded;
    private String communityName;

    /*Directories details*/
    private String inDirectory;
    private String outDirectory;

    /*User related details*/
    private String userName;
    private String password;
    private String surname;
    private String givenName;
    private String userIdentity;
    private String passwordPolicy;
    private Integer sessionTimeout;
    private String authenticationType;
    private List<CommunityManagerNameModel> groups = new ArrayList<>();
    private List<CommunityManagerNameModel> authorizedUserKeys = new ArrayList<>();

    /*Custom Protocol Details*/
    private String sfgMailbox;
    private String customProtocolName;
    private List<CommunityManagerKeyValueModel> customProtocolExtensions = new ArrayList<>();

    /*Trading Partner Address Details*/
    private String city;
    private String state;
    private String zipCode;
    private String country;

    private Boolean isInitiatingConsumer = false;
    private Boolean isInitiatingProducer = false;
    private Boolean isListeningConsumer = true; /*OnlyFor Custom Protocol*/
    private Boolean isListeningProducer = false;

    private SterlingConsumerFtpConfigModel consumerFtpConfiguration = new SterlingConsumerFtpConfigModel();
    private SterlingProducerFtpConfigModel producerFtpConfiguration = new SterlingProducerFtpConfigModel();
    private SterlingConsumerFtpsConfigModel consumerFtpsConfiguration = new SterlingConsumerFtpsConfigModel();
    private SterlingProducerFtpsConfigModel producerFtpsConfiguration = new SterlingProducerFtpsConfigModel();

    private String fileType;
    private String transferType;

    private boolean mergeUser;
    private boolean createSfgProfile;
    private boolean createProducerProfile;

    private String adapterName;
    private String poolingInterval;
    private Boolean createUserInSI = false;
    private Boolean createDirectoryInSI = false;
    private Boolean deleteAfterCollection = false;


    public String getSubscriberType() {
        return subscriberType;
    }

    public SterlingProfilesModel setSubscriberType(String subscriberType) {
        this.subscriberType = subscriberType;
        return this;
    }

    public String getAuthenticationType() {
        return authenticationType;
    }

    public SterlingProfilesModel setAuthenticationType(String authenticationType) {
        this.authenticationType = authenticationType;
        return this;
    }

    public List<CommunityManagerKeyValueModel> getCustomProtocolExtensions() {
        return customProtocolExtensions;
    }

    public SterlingProfilesModel setCustomProtocolExtensions(List<CommunityManagerKeyValueModel> customProtocolExtensions) {
        this.customProtocolExtensions = customProtocolExtensions;
        return this;
    }

    public String getCustomProtocolName() {
        return customProtocolName;
    }

    public SterlingProfilesModel setCustomProtocolName(String customProtocolName) {
        this.customProtocolName = customProtocolName;
        return this;
    }

    public String getSfgMailbox() {
        return sfgMailbox;
    }

    public SterlingProfilesModel setSfgMailbox(String sfgMailbox) {
        this.sfgMailbox = sfgMailbox;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public SterlingProfilesModel setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public SterlingProfilesModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getInDirectory() {
        return inDirectory;
    }

    public SterlingProfilesModel setInDirectory(String inDirectory) {
        this.inDirectory = inDirectory;
        return this;
    }

    public String getOutDirectory() {
        return outDirectory;
    }

    public SterlingProfilesModel setOutDirectory(String outDirectory) {
        this.outDirectory = outDirectory;
        return this;
    }

    public Boolean getCreateUserInSI() {
        return createUserInSI;
    }

    public SterlingProfilesModel setCreateUserInSI(Boolean createUserInSI) {
        this.createUserInSI = createUserInSI;
        return this;
    }

    public Boolean getCreateDirectoryInSI() {
        return createDirectoryInSI;
    }

    public SterlingProfilesModel setCreateDirectoryInSI(Boolean createDirectoryInSI) {
        this.createDirectoryInSI = createDirectoryInSI;
        return this;
    }

    public Boolean getDeleteAfterCollection() {
        return deleteAfterCollection;
    }

    public SterlingProfilesModel setDeleteAfterCollection(Boolean deleteAfterCollection) {
        this.deleteAfterCollection = deleteAfterCollection;
        return this;
    }

    public String getPoolingInterval() {
        return poolingInterval;
    }

    public SterlingProfilesModel setPoolingInterval(String poolingInterval) {
        this.poolingInterval = poolingInterval;
        return this;
    }

    public String getAdapterName() {
        return adapterName;
    }

    public SterlingProfilesModel setAdapterName(String adapterName) {
        this.adapterName = adapterName;
        return this;
    }

    public String getUserIdentity() {
        return userIdentity;
    }

    public SterlingProfilesModel setUserIdentity(String userIdentity) {
        this.userIdentity = userIdentity;
        return this;
    }

    public List<CommunityManagerNameModel> getGroups() {
        return groups;
    }

    public SterlingProfilesModel setGroups(List<CommunityManagerNameModel> groups) {
        this.groups = groups;
        return this;
    }

    public List<CommunityManagerNameModel> getAuthorizedUserKeys() {
        return authorizedUserKeys;
    }

    public SterlingProfilesModel setAuthorizedUserKeys(List<CommunityManagerNameModel> authorizedUserKeys) {
        this.authorizedUserKeys = authorizedUserKeys;
        return this;
    }

    public String getPasswordPolicy() {
        return passwordPolicy;
    }

    public SterlingProfilesModel setPasswordPolicy(String passwordPolicy) {
        this.passwordPolicy = passwordPolicy;
        return this;
    }

    public Integer getSessionTimeout() {
        return sessionTimeout;
    }

    public SterlingProfilesModel setSessionTimeout(Integer sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public SterlingProfilesModel setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getGivenName() {
        return givenName;
    }

    public SterlingProfilesModel setGivenName(String givenName) {
        this.givenName = givenName;
        return this;
    }

    public Boolean getInitiatingConsumer() {
        return isInitiatingConsumer;
    }

    public SterlingProfilesModel setInitiatingConsumer(Boolean initiatingConsumer) {
        isInitiatingConsumer = initiatingConsumer;
        return this;
    }

    public Boolean getInitiatingProducer() {
        return isInitiatingProducer;
    }

    public SterlingProfilesModel setInitiatingProducer(Boolean initiatingProducer) {
        isInitiatingProducer = initiatingProducer;
        return this;
    }

    public Boolean getListeningConsumer() {
        return isListeningConsumer;
    }

    public SterlingProfilesModel setListeningConsumer(Boolean listeningConsumer) {
        isListeningConsumer = listeningConsumer;
        return this;
    }

    public Boolean getListeningProducer() {
        return isListeningProducer;
    }

    public SterlingProfilesModel setListeningProducer(Boolean listeningProducer) {
        isListeningProducer = listeningProducer;
        return this;
    }

    public String getCity() {
        return city;
    }

    public SterlingProfilesModel setCity(String city) {
        this.city = city;
        return this;
    }

    public String getState() {
        return state;
    }

    public SterlingProfilesModel setState(String state) {
        this.state = state;
        return this;
    }

    public String getZipCode() {
        return zipCode;
    }

    public SterlingProfilesModel setZipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public SterlingProfilesModel setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getSecondaryMail() {
        return secondaryMail;
    }

    public SterlingProfilesModel setSecondaryMail(String secondaryMail) {
        this.secondaryMail = secondaryMail;
        return this;
    }

    public boolean isMergeUser() {
        return mergeUser;
    }

    public SterlingProfilesModel setMergeUser(boolean mergeUser) {
        this.mergeUser = mergeUser;
        return this;
    }

    public boolean isCreateSfgProfile() {
        return createSfgProfile;
    }

    public SterlingProfilesModel setCreateSfgProfile(boolean createSfgProfile) {
        this.createSfgProfile = createSfgProfile;
        return this;
    }

    public boolean isCreateProducerProfile() {
        return createProducerProfile;
    }

    public SterlingProfilesModel setCreateProducerProfile(boolean createProducerProfile) {
        this.createProducerProfile = createProducerProfile;
        return this;
    }

    public String getFileType() {
        return fileType;
    }

    public SterlingProfilesModel setFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public String getTransferType() {
        return transferType;
    }

    public SterlingProfilesModel setTransferType(String transferType) {
        this.transferType = transferType;
        return this;
    }

    public SterlingConsumerFtpConfigModel getConsumerFtpConfiguration() {
        return consumerFtpConfiguration;
    }

    public SterlingProfilesModel setConsumerFtpConfiguration(SterlingConsumerFtpConfigModel consumerFtpConfiguration) {
        this.consumerFtpConfiguration = consumerFtpConfiguration;
        return this;
    }

    public SterlingProducerFtpConfigModel getProducerFtpConfiguration() {
        return producerFtpConfiguration;
    }

    public SterlingProfilesModel setProducerFtpConfiguration(SterlingProducerFtpConfigModel producerFtpConfiguration) {
        this.producerFtpConfiguration = producerFtpConfiguration;
        return this;
    }

    public SterlingConsumerFtpsConfigModel getConsumerFtpsConfiguration() {
        return consumerFtpsConfiguration;
    }

    public SterlingProfilesModel setConsumerFtpsConfiguration(SterlingConsumerFtpsConfigModel consumerFtpsConfiguration) {
        this.consumerFtpsConfiguration = consumerFtpsConfiguration;
        return this;
    }

    public SterlingProducerFtpsConfigModel getProducerFtpsConfiguration() {
        return producerFtpsConfiguration;
    }

    public SterlingProfilesModel setProducerFtpsConfiguration(SterlingProducerFtpsConfigModel producerFtpsConfiguration) {
        this.producerFtpsConfiguration = producerFtpsConfiguration;
        return this;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public SterlingProfilesModel setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
        return this;
    }

    public String getReceivingProtocol() {
        return receivingProtocol;
    }

    public SterlingProfilesModel setReceivingProtocol(String receivingProtocol) {
        this.receivingProtocol = receivingProtocol;
        return this;
    }

    public Boolean getConsumerProfile() {
        return consumerProfile;
    }

    public SterlingProfilesModel setConsumerProfile(Boolean consumerProfile) {
        this.consumerProfile = consumerProfile;
        return this;
    }

    public boolean isConsumerCreated() {
        return consumerCreated;
    }

    public SterlingProfilesModel setConsumerCreated(boolean consumerCreated) {
        this.consumerCreated = consumerCreated;
        return this;
    }

    public String getProfileType() {
        return profileType;
    }

    public SterlingProfilesModel setProfileType(String profileType) {
        this.profileType = profileType;
        return this;
    }

    public Boolean getCreateProducer() {
        return createProducer;
    }

    public SterlingProfilesModel setCreateProducer(Boolean createProducer) {
        this.createProducer = createProducer;
        return this;
    }

    public Boolean getSfgSubDetailsLoaded() {
        return sfgSubDetailsLoaded;
    }

    public SterlingProfilesModel setSfgSubDetailsLoaded(Boolean sfgSubDetailsLoaded) {
        this.sfgSubDetailsLoaded = sfgSubDetailsLoaded;
        return this;
    }

    public String getCommunityName() {
        return communityName;
    }

    public SterlingProfilesModel setCommunityName(String communityName) {
        this.communityName = communityName;
        return this;
    }
}
