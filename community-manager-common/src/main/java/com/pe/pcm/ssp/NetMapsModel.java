package com.pe.pcm.ssp;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class NetMapsModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<NetMapDefModelFields> cdNetmapDef;
    @XmlElement
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<NetMapDefModelFields> ftpNetmapDef;
    @XmlElement
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<NetMapDefModelFields> httpNetmapDef;
    @XmlElement
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<NetMapDefModelFields> sftpNetmapDef;

    public NetMapsModel() {
    }

    public NetMapsModel(List<NetMapDefModelFields> cdNetmapDef, List<NetMapDefModelFields> ftpNetmapDef, List<NetMapDefModelFields> httpNetmapDef, List<NetMapDefModelFields> sftpNetmapDef) {
        this.cdNetmapDef = cdNetmapDef;
        this.ftpNetmapDef = ftpNetmapDef;
        this.httpNetmapDef = httpNetmapDef;
        this.sftpNetmapDef = sftpNetmapDef;
    }

    public List<NetMapDefModelFields> getCdNetmapDef() {
        return cdNetmapDef;
    }

    public void setCdNetmapDef(List<NetMapDefModelFields> cdNetmapDef) {
        this.cdNetmapDef = cdNetmapDef;
    }

    public List<NetMapDefModelFields> getFtpNetmapDef() {
        return ftpNetmapDef;
    }

    public void setFtpNetmapDef(List<NetMapDefModelFields> ftpNetmapDef) {
        this.ftpNetmapDef = ftpNetmapDef;
    }

    public List<NetMapDefModelFields> getHttpNetmapDef() {
        return httpNetmapDef;
    }

    public void setHttpNetmapDef(List<NetMapDefModelFields> httpNetmapDef) {
        this.httpNetmapDef = httpNetmapDef;
    }

    public List<NetMapDefModelFields> getSftpNetmapDef() {
        return sftpNetmapDef;
    }

    public void setSftpNetmapDef(List<NetMapDefModelFields> sftpNetmapDef) {
        this.sftpNetmapDef = sftpNetmapDef;
    }

    @Override
    public String toString() {
        return "NetMapsModel{" +
                "cdNetmapDef=" + cdNetmapDef +
                ", ftpNetmapDef=" + ftpNetmapDef +
                ", httpNetmapDef=" + httpNetmapDef +
                ", sftpNetmapDef=" + sftpNetmapDef +
                '}';
    }
}
