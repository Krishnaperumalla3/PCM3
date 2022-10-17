package com.pe.pcm.ssp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "XmlRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class NodeAvailabilityModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String netMap;
    @XmlElement
    private String nodeName;

    public NodeAvailabilityModel() {
    }

    public NodeAvailabilityModel(String netMap, String nodeName) {
        this.netMap = netMap;
        this.nodeName = nodeName;
    }

    public String getNetMap() {
        return netMap;
    }

    public NodeAvailabilityModel setNetMap(String netMap) {
        this.netMap = netMap;
        return this;
    }

    public String getNodeName() {
        return nodeName;
    }

    public NodeAvailabilityModel setNodeName(String nodeName) {
        this.nodeName = nodeName;
        return this;
    }
}
