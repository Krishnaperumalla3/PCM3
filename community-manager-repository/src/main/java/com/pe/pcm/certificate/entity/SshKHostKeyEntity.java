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

/**
 * @author Chenchu Kiran.
 */
@Entity
@Table(name = "SSH_KHOST_KEY")
public class SshKHostKeyEntity {

    @Id
    private String objectId;
    private String name;
    private String username;

    public String getObjectId() {
        return objectId;
    }

    public SshKHostKeyEntity setObjectId(String objectId) {
        this.objectId = objectId;
        return this;
    }

    public String getName() {
        return name;
    }

    public SshKHostKeyEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public SshKHostKeyEntity setUsername(String username) {
        this.username = username;
        return this;
    }
}
