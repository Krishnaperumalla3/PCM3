package com.pe.pcm.ssp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
public class OutboundACLNode implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String outboundACLNode;

    public OutboundACLNode() {
    }

    public OutboundACLNode(String outboundACLNode) {
        this.outboundACLNode = outboundACLNode;
    }

    public String getOutboundACLNode() {
        return outboundACLNode;
    }

    public OutboundACLNode setOutboundACLNode(String outboundACLNode) {
        this.outboundACLNode = outboundACLNode;
        return this;
    }
}
