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

package com.pe.pcm.sterling.entity.identity;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
public class WfdIdentity implements Serializable {

    @NotNull
    private Integer wfdId;
    @NotNull
    private Integer wfdVersion;

    public Integer getWfdId() {
        return wfdId;
    }

    public WfdIdentity setWfdId(Integer wfdId) {
        this.wfdId = wfdId;
        return this;
    }

    public Integer getWfdVersion() {
        return wfdVersion;
    }

    public WfdIdentity setWfdVersion(Integer wfdVersion) {
        this.wfdVersion = wfdVersion;
        return this;
    }

}
