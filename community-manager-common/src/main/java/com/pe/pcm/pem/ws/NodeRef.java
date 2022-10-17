package com.pe.pcm.pem.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
public class NodeRef implements Serializable {

    @XmlElement
    private String nodeRef;
    @XmlElement
    private String nodeValue;

    public String getNodeRef() {
        return nodeRef;
    }

    public NodeRef setNodeRef(String nodeRef) {
        this.nodeRef = nodeRef;
        return this;
    }

    public String getNodeValue() {
        return nodeValue;
    }

    public NodeRef setNodeValue(String nodeValue) {
        this.nodeValue = nodeValue;
        return this;
    }
}
