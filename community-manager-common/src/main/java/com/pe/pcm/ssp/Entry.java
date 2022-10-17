package com.pe.pcm.ssp;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
public class Entry implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    @JacksonXmlElementWrapper(useWrapping = false)
    private KeyValueModel entry;

    public Entry() {
    }

    public Entry(KeyValueModel entry) {
        this.entry = entry;
    }

    public KeyValueModel getEntry() {
        return entry;
    }

    public Entry setEntry(KeyValueModel entry) {
        this.entry = entry;
        return this;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "entry=" + entry +
                '}';
    }
}
