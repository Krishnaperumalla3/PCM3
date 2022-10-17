package com.pe.pcm.ssp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
public class ValueModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String value;

    public ValueModel() {
    }

    public ValueModel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public ValueModel setValue(String value) {
        this.value = value;
        return this;
    }
}
