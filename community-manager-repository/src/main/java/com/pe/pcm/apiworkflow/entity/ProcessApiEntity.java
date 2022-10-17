package com.pe.pcm.apiworkflow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Kiran Reddy.
 */
@Entity
@Table(name = "PETPE_PROCESS_API")
public class ProcessApiEntity {

    @Id
    @Column(name = "PK_ID")
    private String seqId;

    private String profileId;

    public String getSeqId() {
        return seqId;
    }

    public ProcessApiEntity setSeqId(String seqId) {
        this.seqId = seqId;
        return this;
    }

    public String getProfileId() {
        return profileId;
    }

    public ProcessApiEntity setProfileId(String profileId) {
        this.profileId = profileId;
        return this;
    }
}
