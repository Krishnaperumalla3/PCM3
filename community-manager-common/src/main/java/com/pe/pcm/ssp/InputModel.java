package com.pe.pcm.ssp;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "input")
@XmlAccessorType(XmlAccessType.FIELD)
public class InputModel implements Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private String pattern;
    @XmlElement
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<ValueModel> data;

    public InputModel() {
    }

    public InputModel(String pattern, List<ValueModel> data) {
        this.pattern = pattern;
        this.data = data;
    }

    public String getPattern() {
        return pattern;
    }

    public InputModel setPattern(String pattern) {
        this.pattern = pattern;
        return this;
    }

    public List<ValueModel> getData() {
        return data;
    }

    public InputModel setData(List<ValueModel> data) {
        this.data = data;
        return this;
    }
}
