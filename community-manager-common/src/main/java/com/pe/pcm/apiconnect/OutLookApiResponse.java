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
public class OutLookApiResponse implements Serializable {

    private String address;
    private String name;
    private String subject;

    public OutLookApiResponse() {
    }

    public OutLookApiResponse(String address, String name,String subject) {
        this.address = address;
        this.name = name;
        this.subject = subject;
    }


    public String getAddress() {
        return address;
    }

    public OutLookApiResponse setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getName() {
        return name;
    }

    public OutLookApiResponse setName(String name) {
        this.name = name;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public OutLookApiResponse setSubject(String subject) {
        this.subject = subject;
        return this;
    }
}
