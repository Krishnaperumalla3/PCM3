package com.pe.pcm.ssp;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
public class NetMapDefModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    @JacksonXmlElementWrapper(useWrapping = false)
    private NetMapDefModelFields netmapDef;

    @XmlElement
    @JacksonXmlElementWrapper(useWrapping = false)
    private NetMapDefModelFields keyStoreDef;

    @XmlElement
    @JacksonXmlElementWrapper(useWrapping = false)
    private AllNetMapsModel sspCMConfigTag;

    public NetMapDefModel() {
    }

    public NetMapDefModel(NetMapDefModelFields netmapDef, NetMapDefModelFields keyStoreDef, AllNetMapsModel sspCMConfigTag) {
        this.netmapDef = netmapDef;
        this.keyStoreDef = keyStoreDef;
        this.sspCMConfigTag = sspCMConfigTag;
    }

    public NetMapDefModelFields getNetmapDef() {
        return netmapDef;
    }

    public NetMapDefModel setNetmapDef(NetMapDefModelFields netmapDef) {
        this.netmapDef = netmapDef;
        return this;
    }

    public NetMapDefModelFields getKeyStoreDef() {
        return keyStoreDef;
    }

    public NetMapDefModel setKeyStoreDef(NetMapDefModelFields keyStoreDef) {
        this.keyStoreDef = keyStoreDef;
        return this;
    }

    public AllNetMapsModel getSspCMConfigTag() {
        return sspCMConfigTag;
    }

    public void setSspCMConfigTag(AllNetMapsModel sspCMConfigTag) {
        this.sspCMConfigTag = sspCMConfigTag;
    }

    @Override
    public String toString() {
        return "NetMapDefModel{" +
                "netmapDef=" + netmapDef +
                ", keyStoreDef=" + keyStoreDef +
                ", sspCMConfigTag=" + sspCMConfigTag +
                '}';
    }
}
