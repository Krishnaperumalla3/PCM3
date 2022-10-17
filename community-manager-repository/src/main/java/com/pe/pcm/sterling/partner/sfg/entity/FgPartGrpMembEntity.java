/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc
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

package com.pe.pcm.sterling.partner.sfg.entity;

import com.pe.pcm.protocol.as2.si.entity.SciAudit;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Chenchu Kiran.
 */
@Entity
@Table(name = "FG_PART_GRP_MEMB")
public class FgPartGrpMembEntity extends SciAudit {

    @Id
    private String partGrpMembKey;
    private String partGrpKey;
    private String organizationKey;

    public String getPartGrpMembKey() {
        return partGrpMembKey;
    }

    public FgPartGrpMembEntity setPartGrpMembKey(String partGrpMembKey) {
        this.partGrpMembKey = partGrpMembKey;
        return this;
    }

    public String getPartGrpKey() {
        if (partGrpKey != null) {
            return partGrpKey.trim();
        }
        return null;
    }

    public FgPartGrpMembEntity setPartGrpKey(String partGrpKey) {
        this.partGrpKey = partGrpKey;
        return this;
    }

    public String getOrganizationKey() {
        if (organizationKey != null) {
            return organizationKey.trim();
        }
        return null;
    }

    public FgPartGrpMembEntity setOrganizationKey(String organizationKey) {
        this.organizationKey = organizationKey;
        return this;
    }

    @Override
    public String toString() {
        return "FgPartGrpMembEntity{" +
                "partGrpMembKey='" + partGrpMembKey + '\'' +
                ", partGrpKey='" + partGrpKey + '\'' +
                ", organizationKey='" + organizationKey + '\'' +
                '}';
    }
}
