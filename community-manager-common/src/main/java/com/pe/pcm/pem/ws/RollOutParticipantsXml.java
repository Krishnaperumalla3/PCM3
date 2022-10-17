package com.pe.pcm.pem.ws;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class RollOutParticipantsXml implements Serializable {

    @XmlElement(name = "Participant")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Participant> participant;

    public List<Participant> getParticipant() {
        if (participant == null) {
            return new ArrayList<>();
        }
        return participant;
    }

    public RollOutParticipantsXml setParticipant(List<Participant> participant) {
        this.participant = participant;
        return this;
    }
}
