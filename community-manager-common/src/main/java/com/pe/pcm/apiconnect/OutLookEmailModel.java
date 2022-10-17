package com.pe.pcm.apiconnect;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pe.pcm.common.CommunityManagerNameModel;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OutLookEmailModel implements Serializable {

    private String subject;
    private String contentType;
    private String content;
    private OutLookApiModel outLookApiModel;
    private List<CommunityManagerNameModel> toMails = new ArrayList<>();

    public String getSubject() {
        return subject;
    }

    public OutLookEmailModel setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getContentType() {
        return contentType;
    }

    public OutLookEmailModel setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public String getContent() {
        return content;
    }

    public OutLookEmailModel setContent(String content) {
        this.content = content;
        return this;
    }

    public OutLookApiModel getOutLookApiModel() {
        return outLookApiModel;
    }

    public OutLookEmailModel setOutLookApiModel(OutLookApiModel outLookApiModel) {
        this.outLookApiModel = outLookApiModel;
        return this;
    }

    public List<CommunityManagerNameModel> getToMails() {
        return toMails;
    }

    public OutLookEmailModel setToMails(List<CommunityManagerNameModel> toMails) {
        this.toMails = toMails;
        return this;
    }
}
