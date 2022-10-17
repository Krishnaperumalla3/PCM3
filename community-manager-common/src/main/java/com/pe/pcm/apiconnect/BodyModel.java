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
public class BodyModel implements Serializable {

    private String contentType;
    private String content;

    public String getContentType() {
        return contentType;
    }

    public BodyModel setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public String getContent() {
        return content;
    }

    public BodyModel setContent(String content) {
        this.content = content;
        return this;
    }
}
