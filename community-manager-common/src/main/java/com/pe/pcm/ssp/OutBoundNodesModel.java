package com.pe.pcm.ssp;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

public class OutBoundNodesModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "outboundNodeDef")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<SSPNodeModel> outboundNodeDef;

    @XmlElement(name = "sftpOutboundNodeDef")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<SSPNodeModel> sftpOutboundNodeDef;

    public OutBoundNodesModel() {
    }

    public OutBoundNodesModel(List<SSPNodeModel> outboundNodeDef, List<SSPNodeModel> sftpOutboundNodeDef) {
        this.outboundNodeDef = outboundNodeDef;
        this.sftpOutboundNodeDef = sftpOutboundNodeDef;
    }

    public List<SSPNodeModel> getOutboundNodeDef() {
        return outboundNodeDef;
    }

    public OutBoundNodesModel setOutboundNodeDef(List<SSPNodeModel> outboundNodeDef) {
        this.outboundNodeDef = outboundNodeDef;
        return this;
    }

    public List<SSPNodeModel> getSftpOutboundNodeDef() {
        return sftpOutboundNodeDef;
    }

    public OutBoundNodesModel setSftpOutboundNodeDef(List<SSPNodeModel> sftpOutboundNodeDef) {
        this.sftpOutboundNodeDef = sftpOutboundNodeDef;
        return this;
    }
}
