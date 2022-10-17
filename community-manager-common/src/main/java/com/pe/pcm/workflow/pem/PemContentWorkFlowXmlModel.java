package com.pe.pcm.workflow.pem;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.pe.pcm.reports.DataFlowMapper;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class PemContentWorkFlowXmlModel implements Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private String partnerProfile;
    @XmlElement
    private String applicationProfile;
    @XmlElement
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<DataFlowMapper> content;

    public String getPartnerProfile() {
        return partnerProfile;
    }

    public PemContentWorkFlowXmlModel setPartnerProfile(String partnerProfile) {
        this.partnerProfile = partnerProfile;
        return this;
    }

    public String getApplicationProfile() {
        return applicationProfile;
    }

    public PemContentWorkFlowXmlModel setApplicationProfile(String applicationProfile) {
        this.applicationProfile = applicationProfile;
        return this;
    }

    public List<DataFlowMapper> getContent() {
        return content;
    }

    public PemContentWorkFlowXmlModel setContent(List<DataFlowMapper> content) {
        this.content = content;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("partnerProfile", partnerProfile)
                .append("applicationProfile", applicationProfile)
                .append("content", content)
                .toString();
    }
}
