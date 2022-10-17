package com.pe.pcm.common;

import java.io.Serializable;

/**
 * @author Kiran Reddy.
 */
public class DataModel implements Serializable {
    private String data;

    public String getData() {
        return data;
    }

    public DataModel setData(String data) {
        this.data = data;
        return this;
    }
}
