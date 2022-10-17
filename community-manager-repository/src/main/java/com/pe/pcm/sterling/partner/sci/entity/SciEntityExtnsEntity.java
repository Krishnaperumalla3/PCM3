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

package com.pe.pcm.sterling.partner.sci.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

/**
 * @author Chenchu Kiran.
 */
@Entity
@Table(name = "SCI_ENTITY_EXTNS")
public class SciEntityExtnsEntity {

    @Id
    private String objectId;
    private String entityId;
    private String extensionKey;
    private String extensionValue;
    private String objectVersion;
    private String objectState;
    @CreationTimestamp
    private Date lastModification;
    private String lastModifier;

    public String getObjectId() {
        return objectId;
    }

    public SciEntityExtnsEntity setObjectId(String objectId) {
        this.objectId = objectId;
        return this;
    }

    public String getEntityId() {
        return entityId;
    }

    public SciEntityExtnsEntity setEntityId(String entityId) {
        this.entityId = entityId;
        return this;
    }

    public String getExtensionKey() {
        return extensionKey;
    }

    public SciEntityExtnsEntity setExtensionKey(String extensionKey) {
        this.extensionKey = extensionKey;
        return this;
    }

    public String getExtensionValue() {
        return extensionValue;
    }

    public SciEntityExtnsEntity setExtensionValue(String extensionValue) {
        this.extensionValue = extensionValue;
        return this;
    }

    public String getObjectVersion() {
        return objectVersion;
    }

    public SciEntityExtnsEntity setObjectVersion(String objectVersion) {
        this.objectVersion = objectVersion;
        return this;
    }

    public String getObjectState() {
        return objectState;
    }

    public SciEntityExtnsEntity setObjectState(String objectState) {
        this.objectState = objectState;
        return this;
    }

    public Date getLastModification() {
        return lastModification;
    }

    public SciEntityExtnsEntity setLastModification(Date lastModification) {
        this.lastModification = lastModification;
        return this;
    }

    public String getLastModifier() {
        return lastModifier;
    }

    public SciEntityExtnsEntity setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier;
        return this;
    }
}
