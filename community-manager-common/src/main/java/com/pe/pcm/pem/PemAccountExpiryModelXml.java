package com.pe.pcm.pem;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class PemAccountExpiryModelXml implements Serializable {

    @XmlElement
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<PemAccountExpiryXmlModel> expiryAccount = new ArrayList<>();

    public List<PemAccountExpiryXmlModel> getExpiryAccount() {
        return expiryAccount;
    }

    public PemAccountExpiryModelXml setExpiryAccount(List<PemAccountExpiryXmlModel> expiryAccount) {
        this.expiryAccount = expiryAccount;
        return this;
    }

    @Override
    public String toString() {
        return "PemAccountExpiryModelXml{" +
                "expiryAccount=" + expiryAccount +
                '}';
    }
}
