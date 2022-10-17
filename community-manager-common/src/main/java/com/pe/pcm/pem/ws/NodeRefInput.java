package com.pe.pcm.pem.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "ContextDataNode")
@XmlAccessorType(XmlAccessType.FIELD)
public class NodeRefInput implements Serializable {

    @XmlAttribute
    private String nodeRef;
    @XmlAttribute
    private String nodeValue;

    public String getNodeRef() {
        return nodeRef;
    }

    public NodeRefInput setNodeRef(String nodeRef) {
        this.nodeRef = nodeRef;
        return this;
    }

    public String getNodeValue() {
        return nodeValue;
    }

    public NodeRefInput setNodeValue(String nodeValue) {
        this.nodeValue = nodeValue;
        return this;
    }

    @Override
    public String toString() {
        return "NodeRefInput{" +
                "nodeRef='" + nodeRef + '\'' +
                ", nodeValue='" + nodeValue + '\'' +
                '}';
    }
}
