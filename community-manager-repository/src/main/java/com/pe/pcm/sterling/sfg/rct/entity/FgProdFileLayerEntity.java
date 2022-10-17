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
@Table(name = "FG_P_FLR")
public class FgProdFileLayerEntity extends PcmAudit {

    @Id
    private String pFlrKey;
    @Column(name = "P_FST_KEY")
    private String prodFileStrKey;
    private String pFlrTypeKey;
    private String fnPattern;
    private String fnFacts;
    private Integer ordinal;

    public String getpFlrKey() {
        return pFlrKey;
    }

    public FgProdFileLayerEntity setpFlrKey(String pFlrKey) {
        this.pFlrKey = pFlrKey;
        return this;
    }

    public String getProdFileStrKey() {
        return prodFileStrKey;
    }

    public FgProdFileLayerEntity setProdFileStrKey(String prodFileStrKey) {
        this.prodFileStrKey = prodFileStrKey;
        return this;
    }

    public String getpFlrTypeKey() {
        return pFlrTypeKey;
    }

    public FgProdFileLayerEntity setpFlrTypeKey(String pFlrTypeKey) {
        this.pFlrTypeKey = pFlrTypeKey;
        return this;
    }

    public String getFnPattern() {
        return fnPattern;
    }

    public FgProdFileLayerEntity setFnPattern(String fnPattern) {
        this.fnPattern = fnPattern;
        return this;
    }

    public String getFnFacts() {
        return fnFacts;
    }

    public FgProdFileLayerEntity setFnFacts(String fnFacts) {
        this.fnFacts = fnFacts;
        return this;
    }

    public Integer getOrdinal() {
        return ordinal;
    }

    public FgProdFileLayerEntity setOrdinal(Integer ordinal) {
        this.ordinal = ordinal;
        return this;
    }
}
