package com.pe.pcm.pem.ws;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "execute")
@XmlAccessorType(XmlAccessType.FIELD)
public class PemWsRollOutXmlModel implements Serializable {

    @XmlAttribute
    private Integer alertInterval;
    @XmlAttribute
    private String alertStartDate;
    @XmlAttribute
    private String contextData;
    @XmlAttribute
    private String description;
    @XmlAttribute
    private String dueDate;
    @XmlAttribute
    private String name;
    @XmlAttribute
    private Boolean rolloutInternally;

    @JacksonXmlElementWrapper(localName = "participants")
    private RollOutParticipantsXml participants;

    public Integer getAlertInterval() {
        return alertInterval;
    }

    public PemWsRollOutXmlModel setAlertInterval(Integer alertInterval) {
        this.alertInterval = alertInterval;
        return this;
    }

    public String getAlertStartDate() {
        return alertStartDate;
    }

    public PemWsRollOutXmlModel setAlertStartDate(String alertStartDate) {
        this.alertStartDate = alertStartDate;
        return this;
    }

    public String getContextData() {
        return contextData;
    }

    public PemWsRollOutXmlModel setContextData(String contextData) {
        this.contextData = contextData;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public PemWsRollOutXmlModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getDueDate() {
        return dueDate;
    }

    public PemWsRollOutXmlModel setDueDate(String dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public String getName() {
        return name;
    }

    public PemWsRollOutXmlModel setName(String name) {
        this.name = name;
        return this;
    }

    public Boolean getRolloutInternally() {
        return rolloutInternally;
    }

    public PemWsRollOutXmlModel setRolloutInternally(Boolean rolloutInternally) {
        this.rolloutInternally = rolloutInternally;
        return this;
    }

    public RollOutParticipantsXml getParticipants() {
        return participants;
    }

    public PemWsRollOutXmlModel setParticipants(RollOutParticipantsXml participants) {
        this.participants = participants;
        return this;
    }

    @Override
    public String toString() {
        return "PemWsRollOutXmlModel{" +
                "alertInterval=" + alertInterval +
                ", alertStartDate='" + alertStartDate + '\'' +
                ", contextData='" + contextData + '\'' +
                ", description='" + description + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", name='" + name + '\'' +
                ", rolloutInternally=" + rolloutInternally +
                ", participants=" + participants +
                '}';
    }
}
