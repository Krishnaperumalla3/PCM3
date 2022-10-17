package com.pe.pcm.ssp;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
public class KeyValueModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String key;
    @XmlElement
    @JacksonXmlElementWrapper(useWrapping = false)
    private NetMapDefModel value;

    public KeyValueModel() {
    }

    public KeyValueModel(String key, NetMapDefModel value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public KeyValueModel setKey(String key) {
        this.key = key;
        return this;
    }

    public NetMapDefModel getValue() {
        return value;
    }

    public KeyValueModel setValue(NetMapDefModel value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return "KeyValueModel{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }
}
