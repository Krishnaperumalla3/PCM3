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
@Table(name = "FG_C_FLR_PRM_TYPE")
public class FgConsuFileLayerPrmTypeEntity extends PcmAudit {

    @Id
    private String cFlrPrmTypeKey;
    @Column(name = "C_FLR_TYPE_KEY")
    private String consuFlrTypeKey;
    private Integer ordinal;
    private String description;
    private String displayType;
    private String displayLabel;
    private String paramName;
    private String defaultValue;
    private String allowableValues;

    public String getcFlrPrmTypeKey() {
        return cFlrPrmTypeKey;
    }

    public FgConsuFileLayerPrmTypeEntity setcFlrPrmTypeKey(String cFlrPrmTypeKey) {
        this.cFlrPrmTypeKey = cFlrPrmTypeKey;
        return this;
    }

    public String getConsuFlrTypeKey() {
        return consuFlrTypeKey;
    }

    public FgConsuFileLayerPrmTypeEntity setConsuFlrTypeKey(String consuFlrTypeKey) {
        this.consuFlrTypeKey = consuFlrTypeKey;
        return this;
    }

    public Integer getOrdinal() {
        return ordinal;
    }

    public FgConsuFileLayerPrmTypeEntity setOrdinal(Integer ordinal) {
        this.ordinal = ordinal;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public FgConsuFileLayerPrmTypeEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getDisplayType() {
        return displayType;
    }

    public FgConsuFileLayerPrmTypeEntity setDisplayType(String displayType) {
        this.displayType = displayType;
        return this;
    }

    public String getDisplayLabel() {
        return displayLabel;
    }

    public FgConsuFileLayerPrmTypeEntity setDisplayLabel(String displayLabel) {
        this.displayLabel = displayLabel;
        return this;
    }

    public String getParamName() {
        return paramName;
    }

    public FgConsuFileLayerPrmTypeEntity setParamName(String paramName) {
        this.paramName = paramName;
        return this;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public FgConsuFileLayerPrmTypeEntity setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public String getAllowableValues() {
        return allowableValues;
    }

    public FgConsuFileLayerPrmTypeEntity setAllowableValues(String allowableValues) {
        this.allowableValues = allowableValues;
        return this;
    }

}
