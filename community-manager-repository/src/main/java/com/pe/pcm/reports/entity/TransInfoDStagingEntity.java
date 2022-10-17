/*
 *
 *  * Copyright (c) 2020 Pragma Edge Inc
 *  *
 *  * Licensed under the Pragma Edge Inc
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  * https://pragmaedge.com/licenseagreement
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 *
 */

package com.pe.pcm.reports.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Chenchu Kiran Reddy.
 */
@Entity
@Table(name = "PETPE_TRANSINFOD_STAGING")
public class TransInfoDStagingEntity implements Serializable {
    @Id
    private Long pkId;
    private String bpid;
    private String bpname;
    private String rulename;
    private String details;
    private Integer sequence;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss"/*, timezone="America/New_York"*/)
    private Timestamp activityDt;

    private String activityBy;

    private String actName;

    public Long getPkId() {
        return pkId;
    }

    public TransInfoDStagingEntity setPkId(Long pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getBpid() {
        return bpid;
    }

    public TransInfoDStagingEntity setBpid(String bpid) {
        this.bpid = bpid;
        return this;
    }

    public String getBpname() {
        return bpname;
    }

    public TransInfoDStagingEntity setBpname(String bpname) {
        this.bpname = bpname;
        return this;
    }

    public String getRulename() {
        return rulename;
    }

    public TransInfoDStagingEntity setRulename(String rulename) {
        this.rulename = rulename;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public TransInfoDStagingEntity setDetails(String details) {
        this.details = details;
        return this;
    }

    public Integer getSequence() {
        return sequence;
    }

    public TransInfoDStagingEntity setSequence(Integer sequence) {
        this.sequence = sequence;
        return this;
    }

    public Timestamp getActivityDt() {
        return activityDt;
    }

    public TransInfoDStagingEntity setActivityDt(Timestamp activityDt) {
        this.activityDt = activityDt;
        return this;
    }

    public String getActivityBy() {
        return activityBy;
    }

    public TransInfoDStagingEntity setActivityBy(String activityBy) {
        this.activityBy = activityBy;
        return this;
    }

    public String getActName() {
        return actName;
    }

    public TransInfoDStagingEntity setActName(String actName) {
        this.actName = actName;
        return this;
    }
}
