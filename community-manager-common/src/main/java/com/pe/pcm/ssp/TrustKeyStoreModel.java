package com.pe.pcm.ssp;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "elements")
@XmlAccessorType(XmlAccessType.FIELD)
public class TrustKeyStoreModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "keyDef")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<KeyDefModel> keyDef;

    public TrustKeyStoreModel() {
    }

    public TrustKeyStoreModel(List<KeyDefModel> keyDef) {
        this.keyDef = keyDef;
    }

    public List<KeyDefModel> getKeyDef() {
        return keyDef;
    }

    public TrustKeyStoreModel setKeyDef(List<KeyDefModel> keyDef) {
        this.keyDef = keyDef;
        return this;
    }
}
