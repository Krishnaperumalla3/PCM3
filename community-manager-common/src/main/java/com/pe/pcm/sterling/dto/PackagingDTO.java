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
public class PackagingDTO implements Serializable {

    private String objectId;
    private String entityId;
    private String packagingKey;
    private String profileName;
    private String profileId;
    private String profileType;
    private boolean isNormalProfile;
    private Protocol protocol;
    private String payloadType;
    private String mimeType;
    private String mimeSubType;
    private String compressData;
    private Boolean hubInfo;

    public String getObjectId() {
        return objectId;
    }

    public PackagingDTO setObjectId(String objectId) {
        this.objectId = objectId;
        return this;
    }

    public String getEntityId() {
        return entityId;
    }

    public PackagingDTO setEntityId(String entityId) {
        this.entityId = entityId;
        return this;
    }

    public String getPackagingKey() {
        return packagingKey;
    }

    public PackagingDTO setPackagingKey(String packagingKey) {
        this.packagingKey = packagingKey;
        return this;
    }

    public String getProfileName() {
        return profileName;
    }

    public PackagingDTO setProfileName(String profileName) {
        this.profileName = profileName;
        return this;
    }

    public String getProfileType() {
        return profileType;
    }

    public PackagingDTO setProfileType(String profileType) {
        this.profileType = profileType;
        return this;
    }

    public boolean isNormalProfile() {
        return isNormalProfile;
    }

    public PackagingDTO setNormalProfile(boolean normalProfile) {
        isNormalProfile = normalProfile;
        return this;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public PackagingDTO setProtocol(Protocol protocol) {
        this.protocol = protocol;
        return this;
    }

    public String getProfileId() {
        return profileId;
    }

    public PackagingDTO setProfileId(String profileId) {
        this.profileId = profileId;
        return this;
    }

    public String getPayloadType() {
        return payloadType;
    }

    public PackagingDTO setPayloadType(String payloadType) {
        this.payloadType = payloadType;
        return this;
    }

    public String getMimeType() {
        return mimeType;
    }

    public PackagingDTO setMimeType(String mimeType) {
        this.mimeType = mimeType;
        return this;
    }

    public String getMimeSubType() {
        return mimeSubType;
    }

    public PackagingDTO setMimeSubType(String mimeSubType) {
        this.mimeSubType = mimeSubType;
        return this;
    }

    public String getCompressData() {
        return compressData;
    }

    public PackagingDTO setCompressData(String compressData) {
        this.compressData = compressData;
        return this;
    }

    public Boolean getHubInfo() {
        return hubInfo;
    }

    public PackagingDTO setHubInfo(Boolean hubInfo) {
        this.hubInfo = hubInfo;
        return this;
    }
}
