package com.pe.pcm.ssp;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class SspCommonModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<String> policy;

    @XmlElement
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<String> keyStore;

    public SspCommonModel() {
    }

    public SspCommonModel(List<String> policy, List<String> keyStore) {
        this.policy = policy;
        this.keyStore = keyStore;
    }

    public List<String> getPolicy() {
        return policy;
    }

    public SspCommonModel setPolicy(List<String> policy) {
        this.policy = policy;
        return this;
    }

    public List<String> getKeyStore() {
        return keyStore;
    }

    public SspCommonModel setKeyStore(List<String> keyStore) {
        this.keyStore = keyStore;
        return this;
    }
}
