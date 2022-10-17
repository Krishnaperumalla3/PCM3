package com.pe.pcm.ssp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class UrlMapFields implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String forceToUnlock;
    @XmlElement
    private String formatVersion;
    @XmlElement
    private String supportUrlRewrite;
    @XmlElement
    private UrlMapEntry urlMapEntries;

    public UrlMapFields() {
    }

    public UrlMapFields(String forceToUnlock, String formatVersion, String supportUrlRewrite, UrlMapEntry urlMapEntries) {
        this.forceToUnlock = forceToUnlock;
        this.formatVersion = formatVersion;
        this.supportUrlRewrite = supportUrlRewrite;
        this.urlMapEntries = urlMapEntries;
    }

    public String getForceToUnlock() {
        return forceToUnlock;
    }

    public UrlMapFields setForceToUnlock(String forceToUnlock) {
        this.forceToUnlock = forceToUnlock;
        return this;
    }

    public String getFormatVersion() {
        return formatVersion;
    }

    public UrlMapFields setFormatVersion(String formatVersion) {
        this.formatVersion = formatVersion;
        return this;
    }

    public String getSupportUrlRewrite() {
        return supportUrlRewrite;
    }

    public UrlMapFields setSupportUrlRewrite(String supportUrlRewrite) {
        this.supportUrlRewrite = supportUrlRewrite;
        return this;
    }

    public UrlMapEntry getUrlMapEntries() {
        return urlMapEntries;
    }

    public UrlMapFields setUrlMapEntries(UrlMapEntry urlMapEntries) {
        this.urlMapEntries = urlMapEntries;
        return this;
    }
}
