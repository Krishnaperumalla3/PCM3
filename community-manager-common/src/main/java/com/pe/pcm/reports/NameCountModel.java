package com.pe.pcm.reports;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NameCountModel {
    private String name;
    private Integer count;
    private Integer avg;

    public String getName() {
        return name;
    }

    public NameCountModel setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getCount() {
        return count;
    }

    public NameCountModel setCount(Integer count) {
        this.count = count;
        return this;
    }

    public Integer getAvg() {
        return avg;
    }

    public NameCountModel setAvg(Integer avg) {
        this.avg = avg;
        return this;
    }
}
