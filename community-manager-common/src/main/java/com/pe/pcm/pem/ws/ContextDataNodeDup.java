package com.pe.pcm.pem.ws;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class ContextDataNodeDup implements Serializable {

    @XmlElement
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<NodeRef> contextDataNode;

    public List<NodeRef> getContextDataNode() {
        return contextDataNode;
    }

    public ContextDataNodeDup setContextDataNode(List<NodeRef> contextDataNode) {
        this.contextDataNode = contextDataNode;
        return this;
    }
}
