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

package com.pe.pcm.certificate.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.StringJoiner;

/**
 * @author Chenchu Kiran.
 */
@Entity
@Table(name = "SSH_KEY_PAIR")
public class SshKeyPairEntity {

    @Id
    private String objectId;
    private String name;
    private String username;
    private String modifiedBy;
    private Timestamp createDate;


    public String getObjectId() {
        return objectId;
    }

    public SshKeyPairEntity setObjectId(String objectId) {
        this.objectId = objectId;
        return this;
    }

    public String getName() {
        return name;
    }

    public SshKeyPairEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public SshKeyPairEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public SshKeyPairEntity setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public SshKeyPairEntity setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
        return this;
    }
}
