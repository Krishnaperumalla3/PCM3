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

package com.pe.pcm.reports.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Kiran Reddy.
 */
@Entity
@Table(name = "PETPE_TRANSINFOD")
@EntityListeners(AuditingEntityListener.class)
public class TransInfoDEntity implements Serializable {

    private static final long serialVersionUID = 1369715188551266967L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transferinfod_generator")
    @SequenceGenerator(name = "transferinfod_generator", allocationSize = 1, sequenceName = "SEQ_PETPE_TRANSINFOD")
    private Long pkId;
    private String bpid;
    private String bpname;
    private String rulename;
    private String details;
    private Integer sequence;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss"/*, timezone="America/New_York"*/)
    private Timestamp activityDt;

    @CreatedBy
    private String activityBy;

    private String actName;

    public Long getPkId() {
        return pkId;
    }

    public TransInfoDEntity setPkId(Long pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getBpid() {
        return bpid;
    }

    public TransInfoDEntity setBpid(String bpid) {
        this.bpid = bpid;
        return this;
    }

    public String getBpname() {
        return bpname;
    }

    public TransInfoDEntity setBpname(String bpname) {
        this.bpname = bpname;
        return this;
    }

    public String getRulename() {
        return rulename;
    }

    public TransInfoDEntity setRulename(String rulename) {
        this.rulename = rulename;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public TransInfoDEntity setDetails(String details) {
        this.details = details;
        return this;
    }

    public Integer getSequence() {
        return sequence;
    }

    public TransInfoDEntity setSequence(Integer sequence) {
        this.sequence = sequence;
        return this;
    }

    public Timestamp getActivityDt() {
        return activityDt;
    }

    public TransInfoDEntity setActivityDt(Timestamp activityDt) {
        this.activityDt = activityDt;
        return this;
    }

    public String getActivityBy() {
        return activityBy;
    }

    public TransInfoDEntity setActivityBy(String activityBy) {
        this.activityBy = activityBy;
        return this;
    }

    public String getActName() {
        return actName;
    }

    public TransInfoDEntity setActName(String actName) {
        this.actName = actName;
        return this;
    }

    @Override
    public String toString() {
        return "TransInfoDEntity{" +
                "pkId=" + pkId +
                ", bpid='" + bpid + '\'' +
                ", bpname='" + bpname + '\'' +
                ", rulename='" + rulename + '\'' +
                ", details='" + details + '\'' +
                ", sequence=" + sequence +
                ", activityDt=" + activityDt +
                ", activityBy='" + activityBy + '\'' +
                ", actName='" + actName + '\'' +
                '}';
    }
}
