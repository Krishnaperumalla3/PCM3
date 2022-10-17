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

package com.pe.pcm.sterling.partner.sci.entity.identity;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Chenchu Kiran.
 */
@Embeddable
public class SciCommTpXrefIdentity implements Serializable {

    @NotNull
    private String communityId;
    @NotNull
    private String tpProfileId;

    public String getCommunityId() {
        return communityId;
    }

    public SciCommTpXrefIdentity setCommunityId(String communityId) {
        this.communityId = communityId;
        return this;
    }

    public String getTpProfileId() {
        return tpProfileId;
    }

    public SciCommTpXrefIdentity setTpProfileId(String tpProfileId) {
        this.tpProfileId = tpProfileId;
        return this;
    }
}
