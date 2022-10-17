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
@Table(name = "FG_RCTMPL_GPERM")
public class FgRctTmplGpErmEntity extends PcmAudit {

    @Id
    private String rctGpermKey;
    private String routchanTmplKey;
    private String partGrpKey;
    private String routchanRole;

    public String getRctGpermKey() {
        return rctGpermKey;
    }

    public FgRctTmplGpErmEntity setRctGpermKey(String rctGpermKey) {
        this.rctGpermKey = rctGpermKey;
        return this;
    }

    public String getRoutchanTmplKey() {
        return routchanTmplKey;
    }

    public FgRctTmplGpErmEntity setRoutchanTmplKey(String routchanTmplKey) {
        this.routchanTmplKey = routchanTmplKey;
        return this;
    }

    public String getPartGrpKey() {
        return partGrpKey;
    }

    public FgRctTmplGpErmEntity setPartGrpKey(String partGrpKey) {
        this.partGrpKey = partGrpKey;
        return this;
    }

    public String getRoutchanRole() {
        return routchanRole;
    }

    public FgRctTmplGpErmEntity setRoutchanRole(String routchanRole) {
        this.routchanRole = routchanRole;
        return this;
    }
}
