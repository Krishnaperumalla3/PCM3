package com.pe.pcm.ssp;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class NetMapDefModelFields implements Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private String createdBy;
    @XmlElement
    private String createdTimestamp;
    @XmlElement
    private String description;
    @XmlElement
    private TrustKeyStoreModel elements;
    @XmlElement
    private String forceToUnlock;
    @XmlElement
    private String formatVer;
    @XmlElement
    private String formatVersion;
    @XmlElement
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<InboundNodesModel> inboundNodes;
    @XmlElement
    private String lastModifiedBy;
    @XmlElement
    private String lastModifiedTimestamp;
    @XmlElement
    private String lockedBy;
    @XmlElement
    private String lockedTimestamp;
    @XmlElement
    private String name;
    @XmlElement
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<OutBoundNodesModel> outboundNodes;
    @XmlElement
    private String protocol;
    @XmlElement
    private String outboundACLRequired;
    @XmlElement
    private String status;
    @XmlElement
    private UrlMapModel urlRewriteConfig;
    @XmlElement
    private String verStamp;
    @XmlElement
    private String type;
    @XmlElement
    private String templateName;

    public NetMapDefModelFields() {
    }

    public NetMapDefModelFields(String createdBy, String createdTimestamp, String description, TrustKeyStoreModel elements, String forceToUnlock, String formatVer, String formatVersion, List<InboundNodesModel> inboundNodes, String lastModifiedBy, String lastModifiedTimestamp, String lockedBy, String lockedTimestamp, String name, List<OutBoundNodesModel> outboundNodes, String protocol, String outboundACLRequired, String status, UrlMapModel urlRewriteConfig, String verStamp, String type, String templateName) {
        this.createdBy = createdBy;
        this.createdTimestamp = createdTimestamp;
        this.description = description;
        this.elements = elements;
        this.forceToUnlock = forceToUnlock;
        this.formatVer = formatVer;
        this.formatVersion = formatVersion;
        this.inboundNodes = inboundNodes;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedTimestamp = lastModifiedTimestamp;
        this.lockedBy = lockedBy;
        this.lockedTimestamp = lockedTimestamp;
        this.name = name;
        this.outboundNodes = outboundNodes;
        this.protocol = protocol;
        this.outboundACLRequired = outboundACLRequired;
        this.status = status;
        this.urlRewriteConfig = urlRewriteConfig;
        this.verStamp = verStamp;
        this.type = type;
        this.templateName = templateName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public NetMapDefModelFields setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public String getCreatedTimestamp() {
        return createdTimestamp;
    }

    public NetMapDefModelFields setCreatedTimestamp(String createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public NetMapDefModelFields setDescription(String description) {
        this.description = description;
        return this;
    }

    public TrustKeyStoreModel getElements() {
        return elements;
    }

    public NetMapDefModelFields setElements(TrustKeyStoreModel elements) {
        this.elements = elements;
        return this;
    }

    public String getForceToUnlock() {
        return forceToUnlock;
    }

    public NetMapDefModelFields setForceToUnlock(String forceToUnlock) {
        this.forceToUnlock = forceToUnlock;
        return this;
    }

    public String getFormatVer() {
        return formatVer;
    }

    public NetMapDefModelFields setFormatVer(String formatVer) {
        this.formatVer = formatVer;
        return this;
    }

    public List<InboundNodesModel> getInboundNodes() {
        return inboundNodes;
    }

    public NetMapDefModelFields setInboundNodes(List<InboundNodesModel> inboundNodes) {
        this.inboundNodes = inboundNodes;
        return this;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public NetMapDefModelFields setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public String getLastModifiedTimestamp() {
        return lastModifiedTimestamp;
    }

    public NetMapDefModelFields setLastModifiedTimestamp(String lastModifiedTimestamp) {
        this.lastModifiedTimestamp = lastModifiedTimestamp;
        return this;
    }

    public String getLockedBy() {
        return lockedBy;
    }

    public NetMapDefModelFields setLockedBy(String lockedBy) {
        this.lockedBy = lockedBy;
        return this;
    }

    public String getLockedTimestamp() {
        return lockedTimestamp;
    }

    public NetMapDefModelFields setLockedTimestamp(String lockedTimestamp) {
        this.lockedTimestamp = lockedTimestamp;
        return this;
    }

    public String getName() {
        return name;
    }

    public NetMapDefModelFields setName(String name) {
        this.name = name;
        return this;
    }

    public List<OutBoundNodesModel> getOutboundNodes() {
        return outboundNodes;
    }

    public NetMapDefModelFields setOutboundNodes(List<OutBoundNodesModel> outboundNodes) {
        this.outboundNodes = outboundNodes;
        return this;
    }

    public String getProtocol() {
        return protocol;
    }

    public NetMapDefModelFields setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public String getOutboundACLRequired() {
        return outboundACLRequired;
    }

    public NetMapDefModelFields setOutboundACLRequired(String outboundACLRequired) {
        this.outboundACLRequired = outboundACLRequired;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public NetMapDefModelFields setStatus(String status) {
        this.status = status;
        return this;
    }

    public UrlMapModel getUrlRewriteConfig() {
        return urlRewriteConfig;
    }

    public NetMapDefModelFields setUrlRewriteConfig(UrlMapModel urlRewriteConfig) {
        this.urlRewriteConfig = urlRewriteConfig;
        return this;
    }

    public String getVerStamp() {
        return verStamp;
    }

    public NetMapDefModelFields setVerStamp(String verStamp) {
        this.verStamp = verStamp;
        return this;
    }

    public String getFormatVersion() {
        return formatVersion;
    }

    public NetMapDefModelFields setFormatVersion(String formatVersion) {
        this.formatVersion = formatVersion;
        return this;
    }

    public String getType() {
        return type;
    }

    public NetMapDefModelFields setType(String type) {
        this.type = type;
        return this;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
}
