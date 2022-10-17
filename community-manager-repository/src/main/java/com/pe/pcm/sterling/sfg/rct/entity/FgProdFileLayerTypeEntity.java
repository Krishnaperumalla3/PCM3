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

package com.pe.pcm.sterling.sfg.rct.entity;

import com.pe.pcm.login.entity.PcmAudit;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Chenchu Kiran.
 */
@Entity
@Table(name = "FG_P_FLR_TYPE")
public class FgProdFileLayerTypeEntity extends PcmAudit {

    @Id
    private String pFlrTypeKey;
    private String layerType;
    private String isContainer;
    private String containsName;
    private String dispLabel;
    private String description;

    public String getpFlrTypeKey() {
        return pFlrTypeKey;
    }

    public FgProdFileLayerTypeEntity setpFlrTypeKey(String pFlrTypeKey) {
        this.pFlrTypeKey = pFlrTypeKey;
        return this;
    }

    public String getLayerType() {
        return layerType;
    }

    public FgProdFileLayerTypeEntity setLayerType(String layerType) {
        this.layerType = layerType;
        return this;
    }

    public String getIsContainer() {
        return isContainer;
    }

    public FgProdFileLayerTypeEntity setIsContainer(String isContainer) {
        this.isContainer = isContainer;
        return this;
    }

    public String getContainsName() {
        return containsName;
    }

    public FgProdFileLayerTypeEntity setContainsName(String containsName) {
        this.containsName = containsName;
        return this;
    }

    public String getDispLabel() {
        return dispLabel;
    }

    public FgProdFileLayerTypeEntity setDispLabel(String dispLabel) {
        this.dispLabel = dispLabel;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public FgProdFileLayerTypeEntity setDescription(String description) {
        this.description = description;
        return this;
    }
}
