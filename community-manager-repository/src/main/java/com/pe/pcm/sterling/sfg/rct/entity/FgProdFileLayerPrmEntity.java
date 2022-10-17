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
@Table(name = "FG_P_FLR_PRM")
public class FgProdFileLayerPrmEntity extends PcmAudit {
    @Id
    private String pFlrPrmKey;
    @Column(name = "P_FLR_KEY")
    private String prodFlrKey;
    @Column(name = "P_FLR_PRM_TYPE_KEY")
    private String prodFlrPrmTypeKey;
    private String paramValue;

    public String getpFlrPrmKey() {
        return pFlrPrmKey;
    }

    public FgProdFileLayerPrmEntity setpFlrPrmKey(String pFlrPrmKey) {
        this.pFlrPrmKey = pFlrPrmKey;
        return this;
    }

    public String getProdFlrKey() {
        return prodFlrKey;
    }

    public FgProdFileLayerPrmEntity setProdFlrKey(String prodFlrKey) {
        this.prodFlrKey = prodFlrKey;
        return this;
    }

    public String getProdFlrPrmTypeKey() {
        return prodFlrPrmTypeKey;
    }

    public FgProdFileLayerPrmEntity setProdFlrPrmTypeKey(String prodFlrPrmTypeKey) {
        this.prodFlrPrmTypeKey = prodFlrPrmTypeKey;
        return this;
    }

    public String getParamValue() {
        return paramValue;
    }

    public FgProdFileLayerPrmEntity setParamValue(String paramValue) {
        this.paramValue = paramValue;
        return this;
    }
}
