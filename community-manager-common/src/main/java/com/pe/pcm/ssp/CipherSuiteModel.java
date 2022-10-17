package com.pe.pcm.ssp;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class CipherSuiteModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<String> cipherSuite;

    public CipherSuiteModel() {
    }

    public CipherSuiteModel(List<String> cipherSuite) {
        this.cipherSuite = cipherSuite;
    }

    public List<String> getCipherSuite() {
        return cipherSuite;
    }

    public CipherSuiteModel setCipherSuite(List<String> cipherSuite) {
        this.cipherSuite = cipherSuite;
        return this;
    }
}
