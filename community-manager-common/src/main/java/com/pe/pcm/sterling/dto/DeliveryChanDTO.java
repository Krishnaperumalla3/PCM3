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
public class DeliveryChanDTO implements Serializable {

    private String objectId;
    private String objectName;
    private String entityId;
    private String transportId;
    private String docExchangeId;
    private String deliveryChannelKey;
    private Boolean hubInfo;
    private String profileId;
    private String profileName;
    private boolean normalProfile;
    private Protocol protocol;
    //MDN
    private Boolean mdn;
    private String mdnType;
    private String mdnEncryption;
    private String receiptToAddress;

    public String getObjectId() {
        return objectId;
    }

    public DeliveryChanDTO setObjectId(String objectId) {
        this.objectId = objectId;
        return this;
    }

    public String getObjectName() {
        return objectName;
    }

    public DeliveryChanDTO setObjectName(String objectName) {
        this.objectName = objectName;
        return this;
    }

    public String getEntityId() {
        return entityId;
    }

    public DeliveryChanDTO setEntityId(String entityId) {
        this.entityId = entityId;
        return this;
    }

    public String getTransportId() {
        return transportId;
    }

    public DeliveryChanDTO setTransportId(String transportId) {
        this.transportId = transportId;
        return this;
    }

    public String getDocExchangeId() {
        return docExchangeId;
    }

    public DeliveryChanDTO setDocExchangeId(String docExchangeId) {
        this.docExchangeId = docExchangeId;
        return this;
    }

    public String getDeliveryChannelKey() {
        return deliveryChannelKey;
    }

    public DeliveryChanDTO setDeliveryChannelKey(String deliveryChannelKey) {
        this.deliveryChannelKey = deliveryChannelKey;
        return this;
    }

    public Boolean getHubInfo() {
        return hubInfo;
    }

    public DeliveryChanDTO setHubInfo(Boolean hubInfo) {
        this.hubInfo = hubInfo;
        return this;
    }

    public String getProfileId() {
        return profileId;
    }

    public DeliveryChanDTO setProfileId(String profileId) {
        this.profileId = profileId;
        return this;
    }

    public String getProfileName() {
        return profileName;
    }

    public DeliveryChanDTO setProfileName(String profileName) {
        this.profileName = profileName;
        return this;
    }

    public boolean isNormalProfile() {
        return normalProfile;
    }

    public DeliveryChanDTO setNormalProfile(boolean normalProfile) {
        this.normalProfile = normalProfile;
        return this;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public DeliveryChanDTO setProtocol(Protocol protocol) {
        this.protocol = protocol;
        return this;
    }

    public Boolean getMdn() {
        return mdn;
    }

    public DeliveryChanDTO setMdn(Boolean mdn) {
        this.mdn = mdn;
        return this;
    }

    public String getMdnType() {
        return mdnType;
    }

    public DeliveryChanDTO setMdnType(String mdnType) {
        this.mdnType = mdnType;
        return this;
    }

    public String getMdnEncryption() {
        return mdnEncryption;
    }

    public DeliveryChanDTO setMdnEncryption(String mdnEncryption) {
        this.mdnEncryption = mdnEncryption;
        return this;
    }

    public String getReceiptToAddress() {
        return receiptToAddress;
    }

    public DeliveryChanDTO setReceiptToAddress(String receiptToAddress) {
        this.receiptToAddress = receiptToAddress;
        return this;
    }
}
