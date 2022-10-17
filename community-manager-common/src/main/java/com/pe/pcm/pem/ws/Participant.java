package com.pe.pcm.pem.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
public class Participant implements Serializable {

    @XmlAttribute
    private String participantKey;

    @XmlElement
    private ContextDataNode contextDataNodes;

    public String getParticipantKey() {
        return participantKey;
    }

    public Participant setParticipantKey(String participantKey) {
        this.participantKey = participantKey;
        return this;
    }

    public ContextDataNode getContextDataNodes() {
        return contextDataNodes;
    }

    public Participant setContextDataNodes(ContextDataNode contextDataNodes) {
        this.contextDataNodes = contextDataNodes;
        return this;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "participantKey='" + participantKey + '\'' +
                ", contextDataNodes=" + contextDataNodes +
                '}';
    }
}
