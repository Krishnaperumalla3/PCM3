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
public class MapIdentity implements Serializable {

    @NotNull
    private String mapName;
    @NotNull
    private Integer mapVersion;
    @NotNull
    private Integer mapType;
    @NotNull
    private String organizationKey;

    public String getMapName() {
        return mapName;
    }

    public MapIdentity setMapName(String mapName) {
        this.mapName = mapName;
        return this;
    }

    public Integer getMapVersion() {
        return mapVersion;
    }

    public MapIdentity setMapVersion(Integer mapVersion) {
        this.mapVersion = mapVersion;
        return this;
    }

    public Integer getMapType() {
        return mapType;
    }

    public MapIdentity setMapType(Integer mapType) {
        this.mapType = mapType;
        return this;
    }

    public String getOrganizationKey() {
        return organizationKey;
    }

    public MapIdentity setOrganizationKey(String organizationKey) {
        this.organizationKey = organizationKey;
        return this;
    }

}
