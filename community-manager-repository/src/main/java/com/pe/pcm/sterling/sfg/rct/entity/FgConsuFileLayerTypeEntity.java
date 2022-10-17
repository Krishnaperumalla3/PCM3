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
@Table(name = "FG_C_FLR_TYPE")
public class FgConsuFileLayerTypeEntity extends PcmAudit {

    @Id
    private String cFlrTypeKey;
    private String layerType;
    private String isContainer;
    private String dispLabel;
    private String description;

    public String getcFlrTypeKey() {
        return cFlrTypeKey;
    }

    public FgConsuFileLayerTypeEntity setcFlrTypeKey(String cFlrTypeKey) {
        this.cFlrTypeKey = cFlrTypeKey;
        return this;
    }

    public String getLayerType() {
        return layerType;
    }

    public FgConsuFileLayerTypeEntity setLayerType(String layerType) {
        this.layerType = layerType;
        return this;
    }

    public String getIsContainer() {
        return isContainer;
    }

    public FgConsuFileLayerTypeEntity setIsContainer(String isContainer) {
        this.isContainer = isContainer;
        return this;
    }

    public String getDispLabel() {
        return dispLabel;
    }

    public FgConsuFileLayerTypeEntity setDispLabel(String dispLabel) {
        this.dispLabel = dispLabel;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public FgConsuFileLayerTypeEntity setDescription(String description) {
        this.description = description;
        return this;
    }
}
