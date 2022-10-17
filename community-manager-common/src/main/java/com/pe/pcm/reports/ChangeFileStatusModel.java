package com.pe.pcm.reports;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * @author Chenchu Kiran Reddy.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChangeFileStatusModel implements Serializable {

    private String seqId;
    private String coreBpId;
    private String oldStatus;
    private String newStatus;
    private String customMessage;

    public String getSeqId() {
        return seqId;
    }

    public ChangeFileStatusModel setSeqId(String seqId) {
        this.seqId = seqId;
        return this;
    }

    public String getCoreBpId() {
        return coreBpId;
    }

    public ChangeFileStatusModel setCoreBpId(String coreBpId) {
        this.coreBpId = coreBpId;
        return this;
    }

    public String getOldStatus() {
        return oldStatus;
    }

    public ChangeFileStatusModel setOldStatus(String oldStatus) {
        this.oldStatus = oldStatus;
        return this;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public ChangeFileStatusModel setNewStatus(String newStatus) {
        this.newStatus = newStatus;
        return this;
    }

    public String getCustomMessage() {
        return customMessage;
    }

    public ChangeFileStatusModel setCustomMessage(String customMessage) {
        this.customMessage = customMessage;
        return this;
    }
}
