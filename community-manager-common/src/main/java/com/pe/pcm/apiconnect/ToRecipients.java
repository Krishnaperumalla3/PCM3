package com.pe.pcm.apiconnect;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * @author Shameer.v.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ToRecipients {

    private EmailAddress emailAddress;

    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    public ToRecipients setEmailAddress(EmailAddress emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }
}
