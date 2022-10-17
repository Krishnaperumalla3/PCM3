package com.pe.pcm.ssp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
public class UrlMapEntryFields implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String index;
    @XmlElement
    private String proxyUriStr;
    @XmlElement
    private String serverUriStr;

    public UrlMapEntryFields() {
    }

    public UrlMapEntryFields(String index, String proxyUriStr, String serverUriStr) {
        this.index = index;
        this.proxyUriStr = proxyUriStr;
        this.serverUriStr = serverUriStr;
    }

    public String getIndex() {
        return index;
    }

    public UrlMapEntryFields setIndex(String index) {
        this.index = index;
        return this;
    }

    public String getProxyUriStr() {
        return proxyUriStr;
    }

    public UrlMapEntryFields setProxyUriStr(String proxyUriStr) {
        this.proxyUriStr = proxyUriStr;
        return this;
    }

    public String getServerUriStr() {
        return serverUriStr;
    }

    public UrlMapEntryFields setServerUriStr(String serverUriStr) {
        this.serverUriStr = serverUriStr;
        return this;
    }
}
