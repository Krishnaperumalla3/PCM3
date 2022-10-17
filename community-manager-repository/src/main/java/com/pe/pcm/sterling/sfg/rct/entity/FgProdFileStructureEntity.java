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
@Table(name = "FG_P_FST")
public class FgProdFileStructureEntity extends PcmAudit {

    @Id
    private String pFstKey;
    private String routchanTmplKey;

    public String getpFstKey() {
        return pFstKey;
    }

    public FgProdFileStructureEntity setpFstKey(String pFstKey) {
        this.pFstKey = pFstKey;
        return this;
    }

    public String getRoutchanTmplKey() {
        return routchanTmplKey;
    }

    public FgProdFileStructureEntity setRoutchanTmplKey(String routchanTmplKey) {
        this.routchanTmplKey = routchanTmplKey;
        return this;
    }
}
