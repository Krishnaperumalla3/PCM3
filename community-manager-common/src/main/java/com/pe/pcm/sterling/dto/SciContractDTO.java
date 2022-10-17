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
public class SciContractDTO implements Serializable {


    private String profileName;
    private String objectId;
    private String sciProfileObjectId;
    private String workFlowName;
    private String contractKey;
    private String communityName;
    private String communityProfileId;
    private String startDate;
    private String endDate;
    private Map<String, String> extensionKeysMap = new LinkedHashMap<>();
    private String transportEntityId;
    private boolean createProducerProfile;
    private String protocol;
    private Boolean consumerCreated;
    private String profileType;

    public String getProfileName() {
        return profileName;
    }

    public SciContractDTO setProfileName(String profileName) {
        this.profileName = profileName;
        return this;
    }

    public String getObjectId() {
        return objectId;
    }

    public SciContractDTO setObjectId(String objectId) {
        this.objectId = objectId;
        return this;
    }

    public String getSciProfileObjectId() {
        return sciProfileObjectId;
    }

    public SciContractDTO setSciProfileObjectId(String sciProfileObjectId) {
        this.sciProfileObjectId = sciProfileObjectId;
        return this;
    }

    public String getWorkFlowName() {
        return workFlowName;
    }

    public SciContractDTO setWorkFlowName(String workFlowName) {
        this.workFlowName = workFlowName;
        return this;
    }

    public String getContractKey() {
        return contractKey;
    }

    public SciContractDTO setContractKey(String contractKey) {
        this.contractKey = contractKey;
        return this;
    }

    public String getCommunityProfileId() {
        return communityProfileId;
    }

    public SciContractDTO setCommunityProfileId(String communityProfileId) {
        this.communityProfileId = communityProfileId;
        return this;
    }

    public String getStartDate() {
        return startDate;
    }

    public SciContractDTO setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public String getEndDate() {
        return endDate;
    }

    public SciContractDTO setEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public Map<String, String> getExtensionKeysMap() {
        return extensionKeysMap;
    }

    public SciContractDTO setExtensionKeysMap(Map<String, String> extensionKeysMap) {
        this.extensionKeysMap = extensionKeysMap;
        return this;
    }

    public String getTransportEntityId() {
        return transportEntityId;
    }

    public SciContractDTO setTransportEntityId(String transportEntityId) {
        this.transportEntityId = transportEntityId;
        return this;
    }

    public boolean isCreateProducerProfile() {
        return createProducerProfile;
    }

    public SciContractDTO setCreateProducerProfile(boolean createProducerProfile) {
        this.createProducerProfile = createProducerProfile;
        return this;
    }

    public String getCommunityName() {
        return communityName;
    }

    public SciContractDTO setCommunityName(String communityName) {
        this.communityName = communityName;
        return this;
    }

    public String getProtocol() {
        return protocol;
    }

    public SciContractDTO setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public Boolean getConsumerCreated() {
        return consumerCreated;
    }

    public SciContractDTO setConsumerCreated(Boolean consumerCreated) {
        this.consumerCreated = consumerCreated;
        return this;
    }

    public String getProfileType() {
        return profileType;
    }

    public SciContractDTO setProfileType(String profileType) {
        this.profileType = profileType;
        return this;
    }
}
