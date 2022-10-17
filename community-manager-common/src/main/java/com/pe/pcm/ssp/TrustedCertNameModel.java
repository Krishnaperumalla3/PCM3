package com.pe.pcm.ssp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class TrustedCertNameModel {

    @XmlElement
    private String trustedCertName;

    public TrustedCertNameModel() {
    }

    public TrustedCertNameModel(String trustedCertName) {
        this.trustedCertName = trustedCertName;
    }

    public String getTrustedCertName() {
        return trustedCertName;
    }

    public TrustedCertNameModel setTrustedCertName(String trustedCertName) {
        this.trustedCertName = trustedCertName;
        return this;
    }
}
