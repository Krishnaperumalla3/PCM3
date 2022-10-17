package com.pe.pcm.workflow.pem;

import java.io.Serializable;

/**
 * @author Shameer.V.
 */
public class PgpPublicKeyModel implements Serializable {

    private String objectId;

    private String name;

    private String usages;

    public String getObjectId() {
        return objectId;
    }

    public PgpPublicKeyModel setObjectId(String objectId) {
        this.objectId = objectId;
        return this;
    }

    public String getName() {
        return name;
    }

    public PgpPublicKeyModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getUsages() {
        return usages;
    }

    public PgpPublicKeyModel setUsages(String usages) {
        this.usages = usages;
        return this;
    }


}
