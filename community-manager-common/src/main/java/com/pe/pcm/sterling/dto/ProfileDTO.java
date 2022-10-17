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
public class ProfileDTO implements Serializable {

    private String objectId;
    private String profileName;
    private String entityId;
    private String delChannelId;
    private String packagingId;
    private String profileKey;
    private String profileType;
    private boolean normalProfile;
    private Protocol protocol;

    public String getObjectId() {
        return objectId;
    }

    public ProfileDTO setObjectId(String objectId) {
        this.objectId = objectId;
        return this;
    }

    public String getProfileName() {
        return profileName;
    }

    public ProfileDTO setProfileName(String profileName) {
        this.profileName = profileName;
        return this;
    }

    public String getEntityId() {
        return entityId;
    }

    public ProfileDTO setEntityId(String entityId) {
        this.entityId = entityId;
        return this;
    }

    public String getDelChannelId() {
        return delChannelId;
    }

    public ProfileDTO setDelChannelId(String delChannelId) {
        this.delChannelId = delChannelId;
        return this;
    }

    public String getPackagingId() {
        return packagingId;
    }

    public ProfileDTO setPackagingId(String packagingId) {
        this.packagingId = packagingId;
        return this;
    }

    public String getProfileKey() {
        return profileKey;
    }

    public ProfileDTO setProfileKey(String profileKey) {
        this.profileKey = profileKey;
        return this;
    }

    public String getProfileType() {
        return profileType;
    }

    public ProfileDTO setProfileType(String profileType) {
        this.profileType = profileType;
        return this;
    }

    public boolean isNormalProfile() {
        return normalProfile;
    }

    public ProfileDTO setNormalProfile(boolean normalProfile) {
        this.normalProfile = normalProfile;
        return this;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public ProfileDTO setProtocol(Protocol protocol) {
        this.protocol = protocol;
        return this;
    }
}
