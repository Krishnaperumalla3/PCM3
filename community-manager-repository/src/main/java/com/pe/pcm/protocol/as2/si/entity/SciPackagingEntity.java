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

import static com.pe.pcm.utils.PCMConstants.*;

@Entity
@Table(name = "SCI_PACKAGING")
public class SciPackagingEntity extends SciAudit implements Serializable {

    @Id
    private String objectId;

    private String externalObjectId;

    private String objectVersion;

    private String objectName;

    private String messageParsable;

    private String pkgType;

    private String pkgTypeId;

    private String pkgTypeVersion;

    private String pkgTypeClass;

    private String pkgTypeTable;

    private String pkgDesc;

    private String messageFormat;

    private String payloadType;

    private String defaultMimeType;

    private String defMimeSubtype;

    private String objectClass;

    @UpdateTimestamp
    private Date lastModification;

    private String lastModifier;

    private String objectState;

    private String entityId;

    private String compressData;

    private String packagingKey;

    public SciPackagingEntity() {
        this.setLockid(1);
        this.setCreateuserid(INSTALL_PROCESS);
        this.setModifyuserid(INSTALL_PROCESS);
        this.setCreateprogid(UI);
        this.setModifyprogid(UI);
        this.setObjectVersion("1");
        this.setObjectClass("PACKAGING");
        this.setObjectState(STRING_TRUE);
        this.setMessageParsable("no");
        this.setPkgTypeTable(DEFAULT.toLowerCase());
        this.setPkgDesc(DEFAULT.toLowerCase());
        this.setMessageFormat("0");
        this.setPayloadType("0");
        this.setDefaultMimeType("Text");
        this.setDefMimeSubtype("EDI-X12");
        this.setLastModifier(INSTALL_PROCESS);
        this.setCompressData(DEFAULT.toLowerCase());
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getExternalObjectId() {
        return externalObjectId;
    }

    public void setExternalObjectId(String externalObjectId) {
        this.externalObjectId = externalObjectId;
    }

    public String getObjectVersion() {
        return objectVersion;
    }

    public void setObjectVersion(String objectVersion) {
        this.objectVersion = objectVersion;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getMessageParsable() {
        return messageParsable;
    }

    public void setMessageParsable(String messageParsable) {
        this.messageParsable = messageParsable;
    }

    public String getPkgType() {
        return pkgType;
    }

    public void setPkgType(String pkgType) {
        this.pkgType = pkgType;
    }

    public String getPkgTypeId() {
        return pkgTypeId;
    }

    public void setPkgTypeId(String pkgTypeId) {
        this.pkgTypeId = pkgTypeId;
    }

    public String getPkgTypeVersion() {
        return pkgTypeVersion;
    }

    public void setPkgTypeVersion(String pkgTypeVersion) {
        this.pkgTypeVersion = pkgTypeVersion;
    }

    public String getPkgTypeClass() {
        return pkgTypeClass;
    }

    public void setPkgTypeClass(String pkgTypeClass) {
        this.pkgTypeClass = pkgTypeClass;
    }

    public String getPkgTypeTable() {
        return pkgTypeTable;
    }

    public void setPkgTypeTable(String pkgTypeTable) {
        this.pkgTypeTable = pkgTypeTable;
    }

    public String getPkgDesc() {
        return pkgDesc;
    }

    public void setPkgDesc(String pkgDesc) {
        this.pkgDesc = pkgDesc;
    }

    public String getMessageFormat() {
        return messageFormat;
    }

    public void setMessageFormat(String messageFormat) {
        this.messageFormat = messageFormat;
    }

    public String getPayloadType() {
        return payloadType;
    }

    public void setPayloadType(String payloadType) {
        this.payloadType = payloadType;
    }

    public String getDefaultMimeType() {
        return defaultMimeType;
    }

    public void setDefaultMimeType(String defaultMimeType) {
        this.defaultMimeType = defaultMimeType;
    }

    public String getDefMimeSubtype() {
        return defMimeSubtype;
    }

    public void setDefMimeSubtype(String defMimeSubtype) {
        this.defMimeSubtype = defMimeSubtype;
    }

    public String getObjectClass() {
        return objectClass;
    }

    public void setObjectClass(String objectClass) {
        this.objectClass = objectClass;
    }

    public Date getLastModification() {
        return lastModification;
    }

    public void setLastModification(Date lastModification) {
        this.lastModification = lastModification;
    }

    public String getLastModifier() {
        return lastModifier;
    }

    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier;
    }

    public String getObjectState() {
        return objectState;
    }

    public void setObjectState(String objectState) {
        this.objectState = objectState;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getCompressData() {
        return compressData;
    }

    public void setCompressData(String compressData) {
        this.compressData = compressData;
    }

    public String getPackagingKey() {
        return packagingKey;
    }

    public void setPackagingKey(String packagingKey) {
        this.packagingKey = packagingKey;
    }

    @Override
    public String toString() {
        return "SciPackagingEntity [objectId=" + objectId + ", externalObjectId=" + externalObjectId + ", objectVersion="
                + objectVersion + ", objectName=" + objectName + ", messageParsable=" + messageParsable + ", pkgType="
                + pkgType + ", pkgTypeId=" + pkgTypeId + ", pkgTypeVersion=" + pkgTypeVersion + ", pkgTypeClass="
                + pkgTypeClass + ", pkgTypeTable=" + pkgTypeTable + ", pkgDesc=" + pkgDesc + ", messageFormat="
                + messageFormat + ", payloadType=" + payloadType + ", defaultMimeType=" + defaultMimeType
                + ", defMimeSubtype=" + defMimeSubtype + ", objectClass=" + objectClass + ", lastModification="
                + lastModification + ", lastModifier=" + lastModifier + ", objectState=" + objectState + ", entityId="
                + entityId + ", compressData=" + compressData + ", packagingKey=" + packagingKey + "]";
    }

}
