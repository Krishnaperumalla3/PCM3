package com.pe.pcm.apiworkflow.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * @author Kiran Reddy.
 */
@Entity
@Table(name = "PETPE_B2B_API_ACTIVITY_HIST")
@EntityListeners(AuditingEntityListener.class)
public class WorkflowActivityApiHistoryEntity {

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

    public WorkflowActivityApiHistoryEntity setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getProcessRefId() {
        return processRefId;
    }

    public WorkflowActivityApiHistoryEntity setProcessRefId(String processRefId) {
        this.processRefId = processRefId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public WorkflowActivityApiHistoryEntity setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public WorkflowActivityApiHistoryEntity setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getActivity() {
        return activity;
    }

    public WorkflowActivityApiHistoryEntity setActivity(String activity) {
        this.activity = activity;
        return this;
    }

    public Timestamp getActivityDt() {
        return activityDt;
    }

    public WorkflowActivityApiHistoryEntity setActivityDt(Timestamp activityDt) {
        this.activityDt = activityDt;
        return this;
    }
}
