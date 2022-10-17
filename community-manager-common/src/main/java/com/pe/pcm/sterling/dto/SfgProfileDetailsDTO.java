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

package com.pe.pcm.sterling.dto;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Chenchu Kiran.
 */
public class SfgProfileDetailsDTO implements Serializable {

    private String organizationKey;
    private String transportEntityKey;
    private String sciProfileObjectId;
    private String userName;
    private String communityName;
    private String communityId;
    private String communityProfileId;
    private String profileName;
    private Boolean createProducerProfile;
    private Boolean consumerCreated;
    private Boolean consumerProfile;
    private String receivingProtocol;
    private String profileType;
    private Map<String, String> extensionKeysMap = new LinkedHashMap<>();
    private Boolean onlyProducer;
    private Boolean sfgSubDetailsLoaded;


    public String getOrganizationKey() {
        return organizationKey;
    }

    public SfgProfileDetailsDTO setOrganizationKey(String organizationKey) {
        this.organizationKey = organizationKey;
        return this;
    }

    public String getTransportEntityKey() {
        return transportEntityKey;
    }

    public SfgProfileDetailsDTO setTransportEntityKey(String transportEntityKey) {
        this.transportEntityKey = transportEntityKey;
        return this;
    }

    public String getSciProfileObjectId() {
        return sciProfileObjectId;
    }

    public SfgProfileDetailsDTO setSciProfileObjectId(String sciProfileObjectId) {
        this.sciProfileObjectId = sciProfileObjectId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public SfgProfileDetailsDTO setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getCommunityName() {
        return communityName;
    }

    public SfgProfileDetailsDTO setCommunityName(String communityName) {
        this.communityName = communityName;
        return this;
    }

    public String getProfileName() {
        return profileName;
    }

    public SfgProfileDetailsDTO setProfileName(String profileName) {
        this.profileName = profileName;
        return this;
    }

    public String getCommunityId() {
        return communityId;
    }

    public SfgProfileDetailsDTO setCommunityId(String communityId) {
        this.communityId = communityId;
        return this;
    }

    public Boolean getCreateProducerProfile() {
        return createProducerProfile;
    }

    public SfgProfileDetailsDTO setCreateProducerProfile(Boolean createProducerProfile) {
        this.createProducerProfile = createProducerProfile;
        return this;
    }

    public Boolean getConsumerCreated() {
        return consumerCreated;
    }

    public SfgProfileDetailsDTO setConsumerCreated(Boolean consumerCreated) {
        this.consumerCreated = consumerCreated;
        return this;
    }

    public Boolean getConsumerProfile() {
        return consumerProfile;
    }

    public SfgProfileDetailsDTO setConsumerProfile(Boolean consumerProfile) {
        this.consumerProfile = consumerProfile;
        return this;
    }

    public String getReceivingProtocol() {
        return receivingProtocol;
    }

    public SfgProfileDetailsDTO setReceivingProtocol(String receivingProtocol) {
        this.receivingProtocol = receivingProtocol;
        return this;
    }

    public Map<String, String> getExtensionKeysMap() {
        return extensionKeysMap;
    }

    public SfgProfileDetailsDTO setExtensionKeysMap(Map<String, String> extensionKeysMap) {
        this.extensionKeysMap = extensionKeysMap;
        return this;
    }

    public String getProfileType() {
        return profileType;
    }

    public SfgProfileDetailsDTO setProfileType(String profileType) {
        this.profileType = profileType;
        return this;
    }

    public Boolean getOnlyProducer() {
        return onlyProducer;
    }

    public SfgProfileDetailsDTO setOnlyProducer(Boolean onlyProducer) {
        this.onlyProducer = onlyProducer;
        return this;
    }

    public Boolean getSfgSubDetailsLoaded() {
        return sfgSubDetailsLoaded;
    }

    public SfgProfileDetailsDTO setSfgSubDetailsLoaded(Boolean sfgSubDetailsLoaded) {
        this.sfgSubDetailsLoaded = sfgSubDetailsLoaded;
        return this;
    }

    public String getCommunityProfileId() {
        return communityProfileId;
    }

    public SfgProfileDetailsDTO setCommunityProfileId(String communityProfileId) {
        this.communityProfileId = communityProfileId;
        return this;
    }
}
