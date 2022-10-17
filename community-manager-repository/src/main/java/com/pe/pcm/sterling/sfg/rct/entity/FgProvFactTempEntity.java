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
@Table(name = "FG_PROV_FACT_T")
public class FgProvFactTempEntity extends PcmAudit {

    @Id
    @Column(name = "PROV_FACT_T_KEY")
    private String provFactTKey;
    private String routchanTmplKey;
    private Integer ordinal;
    private String description;
    private String displayLabel;
    private String factName;

    public String getProvFactTKey() {
        return provFactTKey;
    }

    public FgProvFactTempEntity setProvFactTKey(String provFactTKey) {
        this.provFactTKey = provFactTKey;
        return this;
    }

    public String getRoutchanTmplKey() {
        return routchanTmplKey;
    }

    public FgProvFactTempEntity setRoutchanTmplKey(String routchanTmplKey) {
        this.routchanTmplKey = routchanTmplKey;
        return this;
    }

    public Integer getOrdinal() {
        return ordinal;
    }

    public FgProvFactTempEntity setOrdinal(Integer ordinal) {
        this.ordinal = ordinal;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public FgProvFactTempEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getDisplayLabel() {
        return displayLabel;
    }

    public FgProvFactTempEntity setDisplayLabel(String displayLabel) {
        this.displayLabel = displayLabel;
        return this;
    }

    public String getFactName() {
        return factName;
    }

    public FgProvFactTempEntity setFactName(String factName) {
        this.factName = factName;
        return this;
    }
}
