package com.pe.pcm.pem;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.pe.pcm.ssp.ValueModel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomProtocolResponseModel implements Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<ValueModel> data;

    @XmlElement
    private String bp;

    @XmlElement
    private String customProtocol;

    public CustomProtocolResponseModel() {
    }

    public CustomProtocolResponseModel(List<ValueModel> data, String bp, String customProtocol) {
        this.data = data;
        this.bp = bp;
        this.customProtocol = customProtocol;
    }

    public List<ValueModel> getData() {
        return data;
    }

    public CustomProtocolResponseModel setData(List<ValueModel> data) {
        this.data = data;
        return this;
    }

    public String getBp() {
        return bp;
    }

    public CustomProtocolResponseModel setBp(String bp) {
        this.bp = bp;
        return this;
    }

    public String getCustomProtocol() {
        return customProtocol;
    }

    public CustomProtocolResponseModel setCustomProtocol(String customProtocol) {
        this.customProtocol = customProtocol;
        return this;
    }
}
