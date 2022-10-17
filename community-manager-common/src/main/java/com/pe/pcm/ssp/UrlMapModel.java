package com.pe.pcm.ssp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
public class UrlMapModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private UrlMapFields urlMap;

    public UrlMapModel() {
    }

    public UrlMapModel(UrlMapFields urlMap) {
        this.urlMap = urlMap;
    }

    public UrlMapFields getUrlMap() {
        return urlMap;
    }

    public UrlMapModel setUrlMap(UrlMapFields urlMap) {
        this.urlMap = urlMap;
        return this;
    }
}
