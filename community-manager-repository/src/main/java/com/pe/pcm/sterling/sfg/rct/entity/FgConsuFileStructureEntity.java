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
@Table(name = "FG_C_FST")
public class FgConsuFileStructureEntity extends PcmAudit {

    @Id
    private String cFstKey;
    @Column(name = "DELIVCHAN_T_KEY")
    private String delivChanTempKey;

    public String getcFstKey() {
        return cFstKey;
    }

    public FgConsuFileStructureEntity setcFstKey(String cFstKey) {
        this.cFstKey = cFstKey;
        return this;
    }

    public String getDelivChanTempKey() {
        return delivChanTempKey;
    }

    public FgConsuFileStructureEntity setDelivChanTempKey(String delivChanTempKey) {
        this.delivChanTempKey = delivChanTempKey;
        return this;
    }
}
