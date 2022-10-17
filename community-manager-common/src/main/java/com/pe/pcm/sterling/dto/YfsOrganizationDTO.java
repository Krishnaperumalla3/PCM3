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

import com.pe.pcm.enums.Protocol;

import java.io.Serializable;

/**
 * @author Chenchu Kiran.
 */
public class YfsOrganizationDTO implements Serializable {

    private String objectId;
    private String organizationName;
    private String organizationKeyAndCode;
    private Boolean hubInfo;
    private String organizationIdentifier;
    private String corporateAddressKey;
    private Protocol protocol;

    public String getObjectId() {
        return objectId;
    }

    public YfsOrganizationDTO setObjectId(String objectId) {
        this.objectId = objectId;
        return this;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public YfsOrganizationDTO setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
        return this;
    }

    public String getOrganizationKeyAndCode() {
        return organizationKeyAndCode;
    }

    public YfsOrganizationDTO setOrganizationKeyAndCode(String organizationKeyAndCode) {
        this.organizationKeyAndCode = organizationKeyAndCode;
        return this;
    }

    public Boolean getHubInfo() {
        return hubInfo;
    }

    public YfsOrganizationDTO setHubInfo(Boolean hubInfo) {
        this.hubInfo = hubInfo;
        return this;
    }

    public String getOrganizationIdentifier() {
        return organizationIdentifier;
    }

    public YfsOrganizationDTO setOrganizationIdentifier(String organizationIdentifier) {
        this.organizationIdentifier = organizationIdentifier;
        return this;
    }

    public String getCorporateAddressKey() {
        return corporateAddressKey;
    }

    public YfsOrganizationDTO setCorporateAddressKey(String corporateAddressKey) {
        this.corporateAddressKey = corporateAddressKey;
        return this;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public YfsOrganizationDTO setProtocol(Protocol protocol) {
        this.protocol = protocol;
        return this;
    }
}
