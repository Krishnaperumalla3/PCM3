package com.pe.pcm.ssp;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "XmlResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlResponseGetModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private int httpCode;

    @XmlElement
    private String httpStatus;

    @XmlElement
    private String action;

    @XmlElement
    private String message;

    @XmlElement
    private String messageLevel;

    @XmlElement
    private SspCommonModel objectsList;

    @XmlElement
    @JacksonXmlElementWrapper(useWrapping = false)
    private Entry results;

    public XmlResponseGetModel() {
    }

    public XmlResponseGetModel(int httpCode, String httpStatus, String action, String message, String messageLevel, SspCommonModel objectsList, Entry results) {
        this.httpCode = httpCode;
        this.httpStatus = httpStatus;
        this.action = action;
        this.message = message;
        this.messageLevel = messageLevel;
        this.objectsList = objectsList;
        this.results = results;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public XmlResponseGetModel setHttpCode(int httpCode) {
        this.httpCode = httpCode;
        return this;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public XmlResponseGetModel setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

    public String getAction() {
        return action;
    }

    public XmlResponseGetModel setAction(String action) {
        this.action = action;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public XmlResponseGetModel setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getMessageLevel() {
        return messageLevel;
    }

    public XmlResponseGetModel setMessageLevel(String messageLevel) {
        this.messageLevel = messageLevel;
        return this;
    }

    public SspCommonModel getObjectsList() {
        return objectsList;
    }

    public XmlResponseGetModel setObjectsList(SspCommonModel objectsList) {
        this.objectsList = objectsList;
        return this;
    }

    public Entry getResults() {
        return results;
    }

    public XmlResponseGetModel setResults(Entry results) {
        this.results = results;
        return this;
    }
}
