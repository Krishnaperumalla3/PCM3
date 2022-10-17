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

package com.pe.pcm.protocol.as2.si.entity;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.StringJoiner;

@Entity
@Table(name = "SCI_DELIV_CHAN")
public class SciDelivChanEntity extends SciAudit implements Serializable {

    @Id
    private String objectId;

    private String externalObjectId;

    private String objectVersion;

    private String objectName;

    private String entityId;

    private String transportId;

    private String docExchangeId;

    private String syncReplyMode;

    private String nonrepudOfOrigin;

    private String nonrepudOfRcpt;

    private String secureTransport;

    private String confidentiality;

    private String authenticated;

    private String authorized;

    private String receiptType;

    private String rcptSigType;

    private String receiptToAddress;

    private String rcptDelivMode;

    private String alwaysVerify;

    private String receiptTimeout = "300";

    private String objectClass;

    @UpdateTimestamp
    private Date lastModification;

    private String lastModifier;

    private String objectState;

    private Integer syncReplymodeInh;

    private Integer nonrepudOrigInh;

    private Integer nonrepudRcptInh;

    private Integer secureTranspInh;

    private Integer confidentialInh;

    private Integer authenticatedInh;

    private Integer authorizedInh;

    private Integer rcptSigTypeInh;

    private Integer rcptToAddrInh;

    private Integer rcptDelvModeInh;

    private Integer receiptTypeInh;

    private Integer rcptTimeoutInh;

    private Integer alwaysVerifyInh;

    private Integer extObjectVersion;

    private Integer extendsObjectId;

    private String deliveryChannelKey;

    public String getObjectId() {
        return objectId;
    }

    public SciDelivChanEntity setObjectId(String objectId) {
        this.objectId = objectId;
        return this;
    }

    public String getExternalObjectId() {
        return externalObjectId;
    }

    public SciDelivChanEntity setExternalObjectId(String externalObjectId) {
        this.externalObjectId = externalObjectId;
        return this;
    }

    public String getObjectVersion() {
        return objectVersion;
    }

    public SciDelivChanEntity setObjectVersion(String objectVersion) {
        this.objectVersion = objectVersion;
        return this;
    }

    public String getObjectName() {
        return objectName;
    }

    public SciDelivChanEntity setObjectName(String objectName) {
        this.objectName = objectName;
        return this;
    }

    public String getEntityId() {
        return entityId;
    }

    public SciDelivChanEntity setEntityId(String entityId) {
        this.entityId = entityId;
        return this;
    }

    public String getTransportId() {
        return transportId;
    }

    public SciDelivChanEntity setTransportId(String transportId) {
        this.transportId = transportId;
        return this;
    }

    public String getDocExchangeId() {
        return docExchangeId;
    }

    public SciDelivChanEntity setDocExchangeId(String docExchangeId) {
        this.docExchangeId = docExchangeId;
        return this;
    }

    public String getSyncReplyMode() {
        return syncReplyMode;
    }

    public SciDelivChanEntity setSyncReplyMode(String syncReplyMode) {
        this.syncReplyMode = syncReplyMode;
        return this;
    }

    public String getNonrepudOfOrigin() {
        return nonrepudOfOrigin;
    }

    public SciDelivChanEntity setNonrepudOfOrigin(String nonrepudOfOrigin) {
        this.nonrepudOfOrigin = nonrepudOfOrigin;
        return this;
    }

    public String getNonrepudOfRcpt() {
        return nonrepudOfRcpt;
    }

    public SciDelivChanEntity setNonrepudOfRcpt(String nonrepudOfRcpt) {
        this.nonrepudOfRcpt = nonrepudOfRcpt;
        return this;
    }

    public String getSecureTransport() {
        return secureTransport;
    }

    public SciDelivChanEntity setSecureTransport(String secureTransport) {
        this.secureTransport = secureTransport;
        return this;
    }

    public String getConfidentiality() {
        return confidentiality;
    }

    public SciDelivChanEntity setConfidentiality(String confidentiality) {
        this.confidentiality = confidentiality;
        return this;
    }

    public String getAuthenticated() {
        return authenticated;
    }

    public SciDelivChanEntity setAuthenticated(String authenticated) {
        this.authenticated = authenticated;
        return this;
    }

    public String getAuthorized() {
        return authorized;
    }

    public SciDelivChanEntity setAuthorized(String authorized) {
        this.authorized = authorized;
        return this;
    }

    public String getReceiptType() {
        return receiptType;
    }

    public SciDelivChanEntity setReceiptType(String receiptType) {
        this.receiptType = receiptType;
        return this;
    }

    public String getRcptSigType() {
        return rcptSigType;
    }

    public SciDelivChanEntity setRcptSigType(String rcptSigType) {
        this.rcptSigType = rcptSigType;
        return this;
    }

    public String getReceiptToAddress() {
        return receiptToAddress;
    }

    public SciDelivChanEntity setReceiptToAddress(String receiptToAddress) {
        this.receiptToAddress = receiptToAddress;
        return this;
    }

    public String getRcptDelivMode() {
        return rcptDelivMode;
    }

    public SciDelivChanEntity setRcptDelivMode(String rcptDelivMode) {
        this.rcptDelivMode = rcptDelivMode;
        return this;
    }

    public String getAlwaysVerify() {
        return alwaysVerify;
    }

    public SciDelivChanEntity setAlwaysVerify(String alwaysVerify) {
        this.alwaysVerify = alwaysVerify;
        return this;
    }

    public String getReceiptTimeout() {
        return receiptTimeout;
    }

    public SciDelivChanEntity setReceiptTimeout(String receiptTimeout) {
        this.receiptTimeout = receiptTimeout;
        return this;
    }

    public String getObjectClass() {
        return objectClass;
    }

    public SciDelivChanEntity setObjectClass(String objectClass) {
        this.objectClass = objectClass;
        return this;
    }

    public Date getLastModification() {
        return lastModification;
    }

    public SciDelivChanEntity setLastModification(Date lastModification) {
        this.lastModification = lastModification;
        return this;
    }

    public String getLastModifier() {
        return lastModifier;
    }

    public SciDelivChanEntity setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier;
        return this;
    }

    public String getObjectState() {
        return objectState;
    }

    public SciDelivChanEntity setObjectState(String objectState) {
        this.objectState = objectState;
        return this;
    }

    public Integer getSyncReplymodeInh() {
        return syncReplymodeInh;
    }

    public SciDelivChanEntity setSyncReplymodeInh(Integer syncReplymodeInh) {
        this.syncReplymodeInh = syncReplymodeInh;
        return this;
    }

    public Integer getNonrepudOrigInh() {
        return nonrepudOrigInh;
    }

    public SciDelivChanEntity setNonrepudOrigInh(Integer nonrepudOrigInh) {
        this.nonrepudOrigInh = nonrepudOrigInh;
        return this;
    }

    public Integer getNonrepudRcptInh() {
        return nonrepudRcptInh;
    }

    public SciDelivChanEntity setNonrepudRcptInh(Integer nonrepudRcptInh) {
        this.nonrepudRcptInh = nonrepudRcptInh;
        return this;
    }

    public Integer getSecureTranspInh() {
        return secureTranspInh;
    }

    public SciDelivChanEntity setSecureTranspInh(Integer secureTranspInh) {
        this.secureTranspInh = secureTranspInh;
        return this;
    }

    public Integer getConfidentialInh() {
        return confidentialInh;
    }

    public SciDelivChanEntity setConfidentialInh(Integer confidentialInh) {
        this.confidentialInh = confidentialInh;
        return this;
    }

    public Integer getAuthenticatedInh() {
        return authenticatedInh;
    }

    public SciDelivChanEntity setAuthenticatedInh(Integer authenticatedInh) {
        this.authenticatedInh = authenticatedInh;
        return this;
    }

    public Integer getAuthorizedInh() {
        return authorizedInh;
    }

    public SciDelivChanEntity setAuthorizedInh(Integer authorizedInh) {
        this.authorizedInh = authorizedInh;
        return this;
    }

    public Integer getRcptSigTypeInh() {
        return rcptSigTypeInh;
    }

    public SciDelivChanEntity setRcptSigTypeInh(Integer rcptSigTypeInh) {
        this.rcptSigTypeInh = rcptSigTypeInh;
        return this;
    }

    public Integer getRcptToAddrInh() {
        return rcptToAddrInh;
    }

    public SciDelivChanEntity setRcptToAddrInh(Integer rcptToAddrInh) {
        this.rcptToAddrInh = rcptToAddrInh;
        return this;
    }

    public Integer getRcptDelvModeInh() {
        return rcptDelvModeInh;
    }

    public SciDelivChanEntity setRcptDelvModeInh(Integer rcptDelvModeInh) {
        this.rcptDelvModeInh = rcptDelvModeInh;
        return this;
    }

    public Integer getReceiptTypeInh() {
        return receiptTypeInh;
    }

    public SciDelivChanEntity setReceiptTypeInh(Integer receiptTypeInh) {
        this.receiptTypeInh = receiptTypeInh;
        return this;
    }

    public Integer getRcptTimeoutInh() {
        return rcptTimeoutInh;
    }

    public SciDelivChanEntity setRcptTimeoutInh(Integer rcptTimeoutInh) {
        this.rcptTimeoutInh = rcptTimeoutInh;
        return this;
    }

    public Integer getAlwaysVerifyInh() {
        return alwaysVerifyInh;
    }

    public SciDelivChanEntity setAlwaysVerifyInh(Integer alwaysVerifyInh) {
        this.alwaysVerifyInh = alwaysVerifyInh;
        return this;
    }

    public Integer getExtObjectVersion() {
        return extObjectVersion;
    }

    public SciDelivChanEntity setExtObjectVersion(Integer extObjectVersion) {
        this.extObjectVersion = extObjectVersion;
        return this;
    }

    public Integer getExtendsObjectId() {
        return extendsObjectId;
    }

    public SciDelivChanEntity setExtendsObjectId(Integer extendsObjectId) {
        this.extendsObjectId = extendsObjectId;
        return this;
    }

    public String getDeliveryChannelKey() {
        return deliveryChannelKey;
    }

    public SciDelivChanEntity setDeliveryChannelKey(String deliveryChannelKey) {
        this.deliveryChannelKey = deliveryChannelKey;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SciDelivChanEntity.class.getSimpleName() + "[", "]")
                .add("objectId='" + objectId + "'")
                .add("externalObjectId='" + externalObjectId + "'")
                .add("objectVersion='" + objectVersion + "'")
                .add("objectName='" + objectName + "'")
                .add("entityId='" + entityId + "'")
                .add("transportId='" + transportId + "'")
                .add("docExchangeId='" + docExchangeId + "'")
                .add("syncReplyMode='" + syncReplyMode + "'")
                .add("nonrepudOfOrigin='" + nonrepudOfOrigin + "'")
                .add("nonrepudOfRcpt='" + nonrepudOfRcpt + "'")
                .add("secureTransport='" + secureTransport + "'")
                .add("confidentiality='" + confidentiality + "'")
                .add("authenticated='" + authenticated + "'")
                .add("authorized='" + authorized + "'")
                .add("receiptType='" + receiptType + "'")
                .add("rcptSigType='" + rcptSigType + "'")
                .add("receiptToAddress='" + receiptToAddress + "'")
                .add("rcptDelivMode='" + rcptDelivMode + "'")
                .add("alwaysVerify='" + alwaysVerify + "'")
                .add("receiptTimeout='" + receiptTimeout + "'")
                .add("objectClass='" + objectClass + "'")
                .add("lastModification=" + lastModification)
                .add("lastModifier='" + lastModifier + "'")
                .add("objectState='" + objectState + "'")
                .add("syncReplymodeInh=" + syncReplymodeInh)
                .add("nonrepudOrigInh=" + nonrepudOrigInh)
                .add("nonrepudRcptInh=" + nonrepudRcptInh)
                .add("secureTranspInh=" + secureTranspInh)
                .add("confidentialInh=" + confidentialInh)
                .add("authenticatedInh=" + authenticatedInh)
                .add("authorizedInh=" + authorizedInh)
                .add("rcptSigTypeInh=" + rcptSigTypeInh)
                .add("rcptToAddrInh=" + rcptToAddrInh)
                .add("rcptDelvModeInh=" + rcptDelvModeInh)
                .add("receiptTypeInh=" + receiptTypeInh)
                .add("rcptTimeoutInh=" + rcptTimeoutInh)
                .add("alwaysVerifyInh=" + alwaysVerifyInh)
                .add("extObjectVersion=" + extObjectVersion)
                .add("extendsObjectId=" + extendsObjectId)
                .add("deliveryChannelKey='" + deliveryChannelKey + "'")
                .toString();
    }
}
