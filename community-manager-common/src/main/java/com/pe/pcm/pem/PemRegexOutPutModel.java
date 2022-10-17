package com.pe.pcm.pem;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.pe.pcm.ssp.ValueModel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * @author Shameer.v.
 */

@XmlRootElement(name = "output")
@XmlAccessorType(XmlAccessType.FIELD)
public class PemRegexOutPutModel implements Serializable {

    @XmlElement
    private String validation;
    @XmlElement
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<ValueModel> data;

    public PemRegexOutPutModel() {
    }

    public PemRegexOutPutModel(String validation, List<ValueModel> data) {
        this.validation = validation;
        this.data = data;
    }

    public String getValidation() {
        return validation;
    }

    public PemRegexOutPutModel setValidation(String validation) {
        this.validation = validation;
        return this;
    }

    public List<ValueModel> getData() {
        return data;
    }

    public PemRegexOutPutModel setData(List<ValueModel> data) {
        this.data = data;
        return this;
    }
}
