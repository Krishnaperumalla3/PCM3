package com.pe.pcm.apiconnect;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;

/**
 * @author Shameer.v.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SendMailToOutLookModel implements Serializable {

    private Message message;

    public Message getMessage() {
        return message;
    }

    public SendMailToOutLookModel setMessage(Message message) {
        this.message = message;
        return this;
    }
}
