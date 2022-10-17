/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc.
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

package com.pe.pcm.protocol.as2.si.entity.identity;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Chenchu Kiran.
 */
@Embeddable
public class As2EmailInfoIdentity implements Serializable {
    @NotNull
    private String entityId;
    @NotNull
    private String profileId;

    public String getEntityId() {
        return entityId;
    }

    public As2EmailInfoIdentity setEntityId(String entityId) {
        this.entityId = entityId;
        return this;
    }

    public String getProfileId() {
        return profileId;
    }

    public As2EmailInfoIdentity setProfileId(String profileId) {
        this.profileId = profileId;
        return this;
    }
}
