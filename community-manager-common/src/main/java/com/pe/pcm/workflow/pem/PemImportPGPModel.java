package com.pe.pcm.workflow.pem;

import java.io.Serializable;

/**
 * @author Shameer.V.
 */
public class PemImportPGPModel implements Serializable {

    private String keyString;
    private String name;
    private Boolean verifyOnUse;

    public PemImportPGPModel(String keyString, String name, Boolean verifyOnUse) {
        this.keyString = keyString;
        this.name = name;
        this.verifyOnUse = verifyOnUse;
    }

    public PemImportPGPModel() {
    }

    public String getKeyString() {
        return keyString;
    }

    public PemImportPGPModel setKeyString(String keyString) {
        this.keyString = keyString;
        return this;
    }

    public String getName() {
        return name;
    }

    public PemImportPGPModel setName(String name) {
        this.name = name;
        return this;
    }

    public Boolean getVerifyOnUse() {
        return verifyOnUse;
    }

    public PemImportPGPModel setVerifyOnUse(Boolean verifyOnUse) {
        this.verifyOnUse = verifyOnUse;
        return this;
    }
}
