package com.pe.pcm.pem.ws;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class ContextDataNode implements Serializable {

    @XmlElement(name = "ContextDataNode")
    @JacksonXmlElementWrapper
    private List<NodeRefInput> contextDataNode;

    public List<NodeRefInput> getContextDataNode() {
        return contextDataNode;
    }

    public ContextDataNode setContextDataNode(List<NodeRefInput> contextDataNode) {
        this.contextDataNode = contextDataNode;
        return this;
    }

    @Override
    public String toString() {
        return "ContextDataNode{" +
                "contextDataNode=" + contextDataNode +
                '}';
    }
}
