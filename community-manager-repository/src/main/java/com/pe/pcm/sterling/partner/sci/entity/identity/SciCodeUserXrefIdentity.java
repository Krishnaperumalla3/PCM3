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
public class SciCodeUserXrefIdentity implements Serializable {

    @NotNull
    private String userId;
    @NotNull
    private String commcodeId;

    public String getUserId() {
        return userId;
    }

    public SciCodeUserXrefIdentity setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getCommcodeId() {
        return commcodeId;
    }

    public SciCodeUserXrefIdentity setCommcodeId(String commcodeId) {
        this.commcodeId = commcodeId;
        return this;
    }
}
