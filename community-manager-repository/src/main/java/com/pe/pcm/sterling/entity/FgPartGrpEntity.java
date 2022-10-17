package com.pe.pcm.sterling.entity;

import com.pe.pcm.protocol.as2.si.entity.SciAudit;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Shameer v.
 */

@Entity
@Table(name = "FG_PART_GRP")
public class FgPartGrpEntity extends SciAudit implements Serializable {

    @Id
    private String PartGrpKey;
    private String name;

    public String getPartGrpKey() {
        return PartGrpKey;
    }

    public FgPartGrpEntity setPartGrpKey(String partGrpKey) {
        PartGrpKey = partGrpKey;
        return this;
    }

    public String getName() {
        return name;
    }

    public FgPartGrpEntity setName(String name) {
        this.name = name;
        return this;
    }
}
