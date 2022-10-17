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

package com.pe.pcm.sterling.community.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Chenchu Kiran.
 */
@Entity
@Table(name = "SCI_COMMUNITY")
public class SciCommunityEntity {

    @Id
    private String objectId;
    private String objectName;
    private String entityId;

    public String getObjectId() {
        return objectId;
    }

    public SciCommunityEntity setObjectId(String objectId) {
        this.objectId = objectId;
        return this;
    }

    public String getObjectName() {
        return objectName;
    }

    public SciCommunityEntity setObjectName(String objectName) {
        this.objectName = objectName;
        return this;
    }

    public String getEntityId() {
        return entityId;
    }

    public SciCommunityEntity setEntityId(String entityId) {
        this.entityId = entityId;
        return this;
    }
}
