package com.pe.pcm.ssp;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class UrlMapEntry implements Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<UrlMapEntryFields> urlMapEntry;

    public UrlMapEntry() {
    }

    public UrlMapEntry(List<UrlMapEntryFields> urlMapEntry) {
        this.urlMapEntry = urlMapEntry;
    }

    public List<UrlMapEntryFields> getUrlMapEntry() {
        return urlMapEntry;
    }

    public UrlMapEntry setUrlMapEntry(List<UrlMapEntryFields> urlMapEntry) {
        this.urlMapEntry = urlMapEntry;
        return this;
    }
}
