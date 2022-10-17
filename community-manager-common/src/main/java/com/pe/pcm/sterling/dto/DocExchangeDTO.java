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
public class DocExchangeDTO implements Serializable {

    private String objectId;
    private String objectName;
    private String entityId;
    private String noOfRetries;
    private String retryInterval;
    private String persistDuration;
    private String envelopProtocol;
    private String objectClass;
    private String profileName;
    private String profileId;
    private String docExchangeKey;
    private String profileType;
    private boolean normalProfile;
    private Protocol protocol;
    private String envEncryptAlg;
    private String msgSigningAlg;

    public String getObjectId() {
        return objectId;
    }

    public DocExchangeDTO setObjectId(String objectId) {
        this.objectId = objectId;
        return this;
    }

    public String getObjectName() {
        return objectName;
    }

    public DocExchangeDTO setObjectName(String objectName) {
        this.objectName = objectName;
        return this;
    }

    public String getEntityId() {
        return entityId;
    }

    public DocExchangeDTO setEntityId(String entityId) {
        this.entityId = entityId;
        return this;
    }

    public String getNoOfRetries() {
        return noOfRetries;
    }

    public DocExchangeDTO setNoOfRetries(String noOfRetries) {
        this.noOfRetries = noOfRetries;
        return this;
    }

    public String getRetryInterval() {
        return retryInterval;
    }

    public DocExchangeDTO setRetryInterval(String retryInterval) {
        this.retryInterval = retryInterval;
        return this;
    }

    public String getPersistDuration() {
        return persistDuration;
    }

    public DocExchangeDTO setPersistDuration(String persistDuration) {
        this.persistDuration = persistDuration;
        return this;
    }

    public String getEnvelopProtocol() {
        return envelopProtocol;
    }

    public DocExchangeDTO setEnvelopProtocol(String envelopProtocol) {
        this.envelopProtocol = envelopProtocol;
        return this;
    }

    public String getObjectClass() {
        return objectClass;
    }

    public DocExchangeDTO setObjectClass(String objectClass) {
        this.objectClass = objectClass;
        return this;
    }

    public String getProfileName() {
        return profileName;
    }

    public DocExchangeDTO setProfileName(String profileName) {
        this.profileName = profileName;
        return this;
    }

    public String getDocExchangeKey() {
        return docExchangeKey;
    }

    public DocExchangeDTO setDocExchangeKey(String docExchangeKey) {
        this.docExchangeKey = docExchangeKey;
        return this;
    }

    public String getProfileType() {
        return profileType;
    }

    public DocExchangeDTO setProfileType(String profileType) {
        this.profileType = profileType;
        return this;
    }

    public boolean isNormalProfile() {
        return normalProfile;
    }

    public DocExchangeDTO setNormalProfile(boolean normalProfile) {
        this.normalProfile = normalProfile;
        return this;
    }

    public String getProfileId() {
        return profileId;
    }

    public DocExchangeDTO setProfileId(String profileId) {
        this.profileId = profileId;
        return this;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public DocExchangeDTO setProtocol(Protocol protocol) {
        this.protocol = protocol;
        return this;
    }

    public String getEnvEncryptAlg() {
        return envEncryptAlg;
    }

    public DocExchangeDTO setEnvEncryptAlg(String envEncryptAlg) {
        this.envEncryptAlg = envEncryptAlg;
        return this;
    }

    public String getMsgSigningAlg() {
        return msgSigningAlg;
    }

    public DocExchangeDTO setMsgSigningAlg(String msgSigningAlg) {
        this.msgSigningAlg = msgSigningAlg;
        return this;
    }
}
