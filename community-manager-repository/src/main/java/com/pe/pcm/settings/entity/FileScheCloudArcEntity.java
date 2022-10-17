package com.pe.pcm.settings.entity;

import com.pe.pcm.audit.Auditable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Kiran Reddy.
 */
@Entity
@Table(name = "PETPE_TRANS_CLOUD_ARC")
public class FileScheCloudArcEntity extends Auditable {
    @Id
    private String pkId;
    private String traSchRef;
    private String srcRootDir;
    private String destRootDir;

    public String getPkId() {
        return pkId;
    }

    public FileScheCloudArcEntity setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getTraSchRef() {
        return traSchRef;
    }

    public FileScheCloudArcEntity setTraSchRef(String traSchRef) {
        this.traSchRef = traSchRef;
        return this;
    }

    public String getSrcRootDir() {
        return srcRootDir;
    }

    public FileScheCloudArcEntity setSrcRootDir(String srcRootDir) {
        this.srcRootDir = srcRootDir;
        return this;
    }

    public String getDestRootDir() {
        return destRootDir;
    }

    public FileScheCloudArcEntity setDestRootDir(String destRootDir) {
        this.destRootDir = destRootDir;
        return this;
    }
}
