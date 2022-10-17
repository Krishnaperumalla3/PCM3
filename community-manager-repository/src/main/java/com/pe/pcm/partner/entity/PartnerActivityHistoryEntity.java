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

package com.pe.pcm.partner.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "PETPE_TP_ACTIVITY_HISTORY")
@EntityListeners(AuditingEntityListener.class)
public class PartnerActivityHistoryEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String pkId;

    private String tpRefId;

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

    public PartnerActivityHistoryEntity setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getTpRefId() {
        return tpRefId;
    }

    public PartnerActivityHistoryEntity setTpRefId(String tpRefId) {
        this.tpRefId = tpRefId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public PartnerActivityHistoryEntity setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public PartnerActivityHistoryEntity setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getActivity() {
        return activity;
    }

    public PartnerActivityHistoryEntity setActivity(String activity) {
        this.activity = activity;
        return this;
    }

    public Timestamp getActivityDt() {
        return activityDt;
    }

    public PartnerActivityHistoryEntity setActivityDt(Timestamp activityDt) {
        this.activityDt = activityDt;
        return this;
    }

    @Override
    public String toString() {
        return "PartnerActivityHistoryEntity{" +
                "pkId='" + pkId + '\'' +
                ", tpRefId='" + tpRefId + '\'' +
                ", userName='" + userName + '\'' +
                ", userId='" + userId + '\'' +
                ", activity='" + activity + '\'' +
                ", activityDt=" + activityDt +
                '}';
    }
}
