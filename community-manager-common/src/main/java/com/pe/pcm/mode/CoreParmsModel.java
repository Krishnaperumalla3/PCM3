package com.pe.pcm.mode;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoreParmsModel implements Serializable {

    private Integer id;
    private String parmKey;
    private String parmValue;
    private String sendBpType;

    public Integer getId() {
        return id;
    }

    public CoreParmsModel setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getParmKey() {
        return parmKey;
    }

    public CoreParmsModel setParmKey(String parmKey) {
        this.parmKey = parmKey;
        return this;
    }

    public String getParmValue() {
        return parmValue;
    }

    public CoreParmsModel setParmValue(String parmValue) {
        this.parmValue = parmValue;
        return this;
    }

    public String getSendBpType() {
        return sendBpType;
    }

    public CoreParmsModel setSendBpType(String sendBpType) {
        this.sendBpType = sendBpType;
        return this;
    }

    @Override
    public String toString() {
        return "CoreParmsModel{" +
                "id=" + id +
                ", parmKey='" + parmKey + '\'' +
                ", parmValue='" + parmValue + '\'' +
                ", sendBpType='" + sendBpType + '\'' +
                '}';
    }
}
