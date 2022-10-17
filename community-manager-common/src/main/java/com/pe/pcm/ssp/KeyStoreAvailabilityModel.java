package com.pe.pcm.ssp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "XmlRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class KeyStoreAvailabilityModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String certificateStore;
    @XmlElement
    private String certName;

    public KeyStoreAvailabilityModel() {
    }

    public KeyStoreAvailabilityModel(String certificateStore, String certName) {
        this.certificateStore = certificateStore;
        this.certName = certName;
    }

    public String getCertificateStore() {
        return certificateStore;
    }

    public KeyStoreAvailabilityModel setCertificateStore(String certificateStore) {
        this.certificateStore = certificateStore;
        return this;
    }

    public String getCertName() {
        return certName;
    }

    public KeyStoreAvailabilityModel setCertName(String certName) {
        this.certName = certName;
        return this;
    }
}
