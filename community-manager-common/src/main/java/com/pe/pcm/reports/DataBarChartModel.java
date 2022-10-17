package com.pe.pcm.reports;

import java.io.Serializable;
import java.util.Map;

public class DataBarChartModel implements Serializable {

    private String key;
    private Map<String,String> keymap;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Map<String, String> getKeymap() {
        return keymap;
    }

    public void setKeymap(Map<String, String> keymap) {
        this.keymap = keymap;
    }

    public DataBarChartModel() {
    }

    @Override
    public String toString() {
        return "DataBarChartModel{" +
                "key='" + key + '\'' +
                ", keymap=" + keymap +
                '}';
    }
}
