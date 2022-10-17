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

package com.pe.pcm.sterling.mailbox.routingrule.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * @author Chenchu Kiran.
 */
@Entity
@Table(name = "MBX_RULE_DESCR")
public class MbxRuleDescriptionEntity {
    @Id
    private String ruleId;
    private String description; //This is the RuleName
    @CreationTimestamp
    private Timestamp createdDatetime;
    private String createdBy;

    public String getRuleId() {
        return ruleId;
    }

    public MbxRuleDescriptionEntity setRuleId(String ruleId) {
        this.ruleId = ruleId;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public MbxRuleDescriptionEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public Timestamp getCreatedDatetime() {
        return createdDatetime;
    }

    public MbxRuleDescriptionEntity setCreatedDatetime(Timestamp createdDatetime) {
        this.createdDatetime = createdDatetime;
        return this;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public MbxRuleDescriptionEntity setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }
}
