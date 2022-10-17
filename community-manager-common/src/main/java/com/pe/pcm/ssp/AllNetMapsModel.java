package com.pe.pcm.ssp;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
public class AllNetMapsModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String formatVersion;

    @XmlElement
    @JacksonXmlElementWrapper(useWrapping = false)
    private NetMapsModel netmapsTag;

    public AllNetMapsModel() {
    }

    public AllNetMapsModel(String formatVersion, NetMapsModel netmapsTag) {
        this.formatVersion = formatVersion;
        this.netmapsTag = netmapsTag;
    }

    public String getFormatVersion() {
        return formatVersion;
    }

    public void setFormatVersion(String formatVersion) {
        this.formatVersion = formatVersion;
    }

    public NetMapsModel getNetmapsTag() {
        return netmapsTag;
    }

    public void setNetmapsTag(NetMapsModel netmapsTag) {
        this.netmapsTag = netmapsTag;
    }

    @Override
    public String toString() {
        return "AllNetMapsModel{" +
                "formatVersion='" + formatVersion + '\'' +
                ", netmapsTag=" + netmapsTag +
                '}';
    }
}
