package com.pe.pcm.pem;

import com.pe.pcm.pem.ws.ContextDataNodeDup;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
public class PemRollOutModelXml implements Serializable {

    @XmlElement
    private boolean rollOutInternally;
    @XmlElement
    private String activityDefinition;
    @XmlElement
    private Integer alertInterval;
    @XmlElement
    private String alertStartDate;
    @XmlElement
    private String contextData;
    @XmlElement
    private String description;
    @XmlElement
    private String dueDate;
    @XmlElement
    private String name;
    @XmlElement
    private ContextDataNodeDup contextDataNodes;

    @XmlElement
    private PemAccountExpiryModelXml expiryList;

    public boolean isRollOutInternally() {
        return rollOutInternally;
    }

    public PemRollOutModelXml setRollOutInternally(boolean rollOutInternally) {
        this.rollOutInternally = rollOutInternally;
        return this;
    }

    public String getActivityDefinition() {
        return activityDefinition;
    }

    public PemRollOutModelXml setActivityDefinition(String activityDefinition) {
        this.activityDefinition = activityDefinition;
        return this;
    }

    public Integer getAlertInterval() {
        return alertInterval;
    }

    public PemRollOutModelXml setAlertInterval(Integer alertInterval) {
        this.alertInterval = alertInterval;
        return this;
    }

    public String getAlertStartDate() {
        return alertStartDate;
    }

    public PemRollOutModelXml setAlertStartDate(String alertStartDate) {
        this.alertStartDate = alertStartDate;
        return this;
    }

    public String getContextData() {
        return contextData;
    }

    public PemRollOutModelXml setContextData(String contextData) {
        this.contextData = contextData;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public PemRollOutModelXml setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getDueDate() {
        return dueDate;
    }

    public PemRollOutModelXml setDueDate(String dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public String getName() {
        return name;
    }

    public PemRollOutModelXml setName(String name) {
        this.name = name;
        return this;
    }

    public ContextDataNodeDup getContextDataNodes() {
        return contextDataNodes;
    }

    public PemRollOutModelXml setContextDataNodes(ContextDataNodeDup contextDataNodes) {
        this.contextDataNodes = contextDataNodes;
        return this;
    }

    public PemAccountExpiryModelXml getExpiryList() {
        return expiryList;
    }

    public PemRollOutModelXml setExpiryList(PemAccountExpiryModelXml expiryList) {
        this.expiryList = expiryList;
        return this;
    }

    @Override
    public String toString() {
        return "PemRollOutModelXml{" +
                "rollOutInternally=" + rollOutInternally +
                ", activityDefinition='" + activityDefinition + '\'' +
                ", alertInterval=" + alertInterval +
                ", alertStartDate='" + alertStartDate + '\'' +
                ", contextData='" + contextData + '\'' +
                ", description='" + description + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", name='" + name + '\'' +
                ", contextDataNodes=" + contextDataNodes +
                ", expiryList=" + expiryList +
                '}';
    }
}
