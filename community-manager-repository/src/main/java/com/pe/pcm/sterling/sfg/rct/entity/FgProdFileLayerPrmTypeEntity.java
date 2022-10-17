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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Chenchu Kiran.
 */
@Entity
@Table(name = "FG_P_FLR_PRM_TYPE")
public class FgProdFileLayerPrmTypeEntity extends PcmAudit {

    @Id
    private String pFlrPrmTypeKey;
    @Column(name = "P_FLR_TYPE_KEY")
    private String prodFlrTypeKey;
    private Integer ordinal;
    private String description;
    private String displayType;
    private String displayLabel;
    private String paramName;
    private String defaultValue;
    private String allowableValue;

    public String getpFlrPrmTypeKey() {
        return pFlrPrmTypeKey;
    }

    public FgProdFileLayerPrmTypeEntity setpFlrPrmTypeKey(String pFlrPrmTypeKey) {
        this.pFlrPrmTypeKey = pFlrPrmTypeKey;
        return this;
    }

    public String getProdFlrTypeKey() {
        return prodFlrTypeKey;
    }

    public FgProdFileLayerPrmTypeEntity setProdFlrTypeKey(String prodFlrTypeKey) {
        this.prodFlrTypeKey = prodFlrTypeKey;
        return this;
    }

    public Integer getOrdinal() {
        return ordinal;
    }

    public FgProdFileLayerPrmTypeEntity setOrdinal(Integer ordinal) {
        this.ordinal = ordinal;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public FgProdFileLayerPrmTypeEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getDisplayType() {
        return displayType;
    }

    public FgProdFileLayerPrmTypeEntity setDisplayType(String displayType) {
        this.displayType = displayType;
        return this;
    }

    public String getDisplayLabel() {
        return displayLabel;
    }

    public FgProdFileLayerPrmTypeEntity setDisplayLabel(String displayLabel) {
        this.displayLabel = displayLabel;
        return this;
    }

    public String getParamName() {
        return paramName;
    }

    public FgProdFileLayerPrmTypeEntity setParamName(String paramName) {
        this.paramName = paramName;
        return this;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public FgProdFileLayerPrmTypeEntity setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public String getAllowableValue() {
        return allowableValue;
    }

    public FgProdFileLayerPrmTypeEntity setAllowableValue(String allowableValue) {
        this.allowableValue = allowableValue;
        return this;
    }
}
