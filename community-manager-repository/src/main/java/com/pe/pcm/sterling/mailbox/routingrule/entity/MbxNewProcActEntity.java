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

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Chenchu Kiran.
 */
@Entity
@Table(name = "MBX_NEW_PROC_ACT")
public class MbxNewProcActEntity {
    @Id
    private String ruleId;
    private String processName;

    public String getRuleId() {
        return ruleId;
    }

    public MbxNewProcActEntity setRuleId(String ruleId) {
        this.ruleId = ruleId;
        return this;
    }

    public String getProcessName() {
        return processName;
    }

    public MbxNewProcActEntity setProcessName(String processName) {
        this.processName = processName;
        return this;
    }
}
