package com.pe.pcm.apiconnect;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.List;

/**
 * @author Shameer.v.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message implements Serializable {

    private String subject;
    private BodyModel body;
    private List<ToRecipients> toRecipients;

    public String getSubject() {
        return subject;
    }

    public Message setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public BodyModel getBody() {
        return body;
    }

    public Message setBody(BodyModel body) {
        this.body = body;
        return this;
    }

    public List<ToRecipients> getToRecipients() {
        return toRecipients;
    }

    public Message setToRecipients(List<ToRecipients> toRecipients) {
        this.toRecipients = toRecipients;
        return this;
    }
}
