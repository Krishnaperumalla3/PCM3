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
@Table(name = "FG_C_FLR")
public class FgConsuFileLayerEntity extends PcmAudit {
    @Id
    private String cFlrKey;
    @Column(name = "C_FST_KEY")
    private String consuFstKey;
    @Column(name = "C_FLR_TYPE_KEY")
    private String consuFlrTypeKey;
    private String fnFormat;
    private Integer ordinal;

    public String getcFlrKey() {
        return cFlrKey;
    }

    public FgConsuFileLayerEntity setcFlrKey(String cFlrKey) {
        this.cFlrKey = cFlrKey;
        return this;
    }

    public String getConsuFstKey() {
        return consuFstKey;
    }

    public FgConsuFileLayerEntity setConsuFstKey(String consuFstKey) {
        this.consuFstKey = consuFstKey;
        return this;
    }

    public String getConsuFlrTypeKey() {
        return consuFlrTypeKey;
    }

    public FgConsuFileLayerEntity setConsuFlrTypeKey(String consuFlrTypeKey) {
        this.consuFlrTypeKey = consuFlrTypeKey;
        return this;
    }

    public String getFnFormat() {
        return fnFormat;
    }

    public FgConsuFileLayerEntity setFnFormat(String fnFormat) {
        this.fnFormat = fnFormat;
        return this;
    }

    public Integer getOrdinal() {
        return ordinal;
    }

    public FgConsuFileLayerEntity setOrdinal(Integer ordinal) {
        this.ordinal = ordinal;
        return this;
    }
}
