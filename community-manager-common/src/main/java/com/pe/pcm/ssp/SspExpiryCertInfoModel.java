package com.pe.pcm.ssp;

import java.io.Serializable;

public class SspExpiryCertInfoModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private String scriptInput;

    public String getScriptInput() {
        return scriptInput;
    }

    public SspExpiryCertInfoModel setScriptInput(String scriptInput) {
        this.scriptInput = scriptInput;
        return this;
    }
}
