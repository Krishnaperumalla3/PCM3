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
@Table(name = "FG_C_FLR_PRM")
public class FgConsuFileLayerPrmEntity extends PcmAudit {

    @Id
    private String cFlrPrmKey;
    @Column(name = "C_FLR_KEY")
    private String consuFlrKey;
    @Column(name = "C_FLR_PRM_TYPE_KEY")
    private String cFlrPrmTypeKey;
    private String paramValue;

    public String getcFlrPrmKey() {
        return cFlrPrmKey;
    }

    public FgConsuFileLayerPrmEntity setcFlrPrmKey(String cFlrPrmKey) {
        this.cFlrPrmKey = cFlrPrmKey;
        return this;
    }

    public String getConsuFlrKey() {
        return consuFlrKey;
    }

    public FgConsuFileLayerPrmEntity setConsuFlrKey(String consuFlrKey) {
        this.consuFlrKey = consuFlrKey;
        return this;
    }

    public String getcFlrPrmTypeKey() {
        return cFlrPrmTypeKey;
    }

    public FgConsuFileLayerPrmEntity setcFlrPrmTypeKey(String cFlrPrmTypeKey) {
        this.cFlrPrmTypeKey = cFlrPrmTypeKey;
        return this;
    }

    public String getParamValue() {
        return paramValue;
    }

    public FgConsuFileLayerPrmEntity setParamValue(String paramValue) {
        this.paramValue = paramValue;
        return this;
    }
}
