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

package com.pe.pcm.workflow.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.StringJoiner;

/**
 * @author Kiran Reddy.
 */
@Entity
@Table(name = "PETPE_B2B_ACTIVITY_HISTORY")
@EntityListeners(AuditingEntityListener.class)
public class WorkFlowActivityHistoryEntity implements Serializable {

    @Id
    private String pkId;

    private String processRefId;

    @CreatedBy
    private String userName;

    @CreatedBy
    private String userId;

    private String activity;

    @CreationTimestamp
    private Timestamp activityDt;

    public String getPkId() {
        return pkId;
    }

    public WorkFlowActivityHistoryEntity setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getProcessRefId() {
        return processRefId;
    }

    public WorkFlowActivityHistoryEntity setProcessRefId(String processRefId) {
        this.processRefId = processRefId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public WorkFlowActivityHistoryEntity setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public WorkFlowActivityHistoryEntity setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getActivity() {
        return activity;
    }

    public WorkFlowActivityHistoryEntity setActivity(String activity) {
        this.activity = activity;
        return this;
    }

    public Timestamp getActivityDt() {
        return activityDt;
    }

    public WorkFlowActivityHistoryEntity setActivityDt(Timestamp activityDt) {
        this.activityDt = activityDt;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", WorkFlowActivityHistoryEntity.class.getSimpleName() + "[", "]")
                .add("pkId='" + pkId + "'")
                .add("processRefId='" + processRefId + "'")
                .add("userName='" + userName + "'")
                .add("userId='" + userId + "'")
                .add("activity='" + activity + "'")
                .add("activityDt=" + activityDt)
                .toString();
    }
}
