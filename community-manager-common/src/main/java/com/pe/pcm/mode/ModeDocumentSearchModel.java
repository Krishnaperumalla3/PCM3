package com.pe.pcm.mode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ModeDocumentSearchModel implements Serializable {

    private String[] actionFlag;
    private String[] altReference;
    private Boolean archive;
    private Boolean authenticated;
    private String authorities;
    @NotNull
    @NotEmpty
    private String clientId;
    private Object credentials;
    private String[] currencyCode;
    private String[] destination;
    private Object details;
    private String[] direction;
    private String docCreationEndDate;
    private String docCreationStartDate;
    private String docEndDate;
    private String[] docFormat;
    private String docStartDate;
    private String[] docStatus;
    private String[] docType;
    private String[] documentCount;
    private String[] documentStatusReason;
    private String[] docValue;
    private String endDate;
    private String extensions;
    private String[] finalSize;
    private String[] groupNumber;
    private String identityFields;
    private String[] interchangeNumber;
    private String[] inWfid;
    private Integer maxId;
    private Integer minId;
    private String msgEndDate;
    private String[] msgInFilename;
    private String[] msgOutFilename;
    private String[] msgProtocol;
    private String[] msgProtocolInfo;
    private String msgStartDate;
    private String[] msgStatus;
    private String[] msgStatusReason;
    private String[] originalSize;
    private String[] outWfid;
    private Integer page;
    private Integer pageSize;
    private String[] parentWfid;
    private String[] partner;
    private Object principal;
    private String[] reference;
    private String sortBy;
    private String sortDir;
    private String[] source;
    private String[] standard;
    private String startDate;
    private String[] testFlag;
    private String[] transactionNumber;
    private String[] transactionStatus;
    private String[] transactionStatusReason;
    private String[] version;
    private String[] wfid;
    private Boolean withDocExtensions;
    private Boolean withEvents;
    private Boolean withNotes;
    private Boolean withReferences;

    public String[] getActionFlag() {
        return actionFlag;
    }

    public ModeDocumentSearchModel setActionFlag(String[] actionFlag) {
        this.actionFlag = actionFlag;
        return this;
    }

    public String[] getAltReference() {
        return altReference;
    }

    public ModeDocumentSearchModel setAltReference(String[] altReference) {
        this.altReference = altReference;
        return this;
    }

    public Boolean getArchive() {
        return archive;
    }

    public ModeDocumentSearchModel setArchive(Boolean archive) {
        this.archive = archive;
        return this;
    }

    public Boolean getAuthenticated() {
        return authenticated;
    }

    public ModeDocumentSearchModel setAuthenticated(Boolean authenticated) {
        this.authenticated = authenticated;
        return this;
    }

    public String getAuthorities() {
        return authorities;
    }

    public ModeDocumentSearchModel setAuthorities(String authorities) {
        this.authorities = authorities;
        return this;
    }

    public String getClientId() {
        return clientId;
    }

    public ModeDocumentSearchModel setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public Object getCredentials() {
        return credentials;
    }

    public ModeDocumentSearchModel setCredentials(Object credentials) {
        this.credentials = credentials;
        return this;
    }

    public String[] getCurrencyCode() {
        return currencyCode;
    }

    public ModeDocumentSearchModel setCurrencyCode(String[] currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public String[] getDestination() {
        return destination;
    }

    public ModeDocumentSearchModel setDestination(String[] destination) {
        this.destination = destination;
        return this;
    }

    public Object getDetails() {
        return details;
    }

    public ModeDocumentSearchModel setDetails(Object details) {
        this.details = details;
        return this;
    }

    public String[] getDirection() {
        return direction;
    }

    public ModeDocumentSearchModel setDirection(String[] direction) {
        this.direction = direction;
        return this;
    }

    public String getDocCreationEndDate() {
        return docCreationEndDate;
    }

    public ModeDocumentSearchModel setDocCreationEndDate(String docCreationEndDate) {
        this.docCreationEndDate = docCreationEndDate;
        return this;
    }

    public String getDocCreationStartDate() {
        return docCreationStartDate;
    }

    public ModeDocumentSearchModel setDocCreationStartDate(String docCreationStartDate) {
        this.docCreationStartDate = docCreationStartDate;
        return this;
    }

    public String getDocEndDate() {
        return docEndDate;
    }

    public ModeDocumentSearchModel setDocEndDate(String docEndDate) {
        this.docEndDate = docEndDate;
        return this;
    }

    public String[] getDocFormat() {
        return docFormat;
    }

    public ModeDocumentSearchModel setDocFormat(String[] docFormat) {
        this.docFormat = docFormat;
        return this;
    }

    public String getDocStartDate() {
        return docStartDate;
    }

    public ModeDocumentSearchModel setDocStartDate(String docStartDate) {
        this.docStartDate = docStartDate;
        return this;
    }

    public String[] getDocStatus() {
        return docStatus;
    }

    public ModeDocumentSearchModel setDocStatus(String[] docStatus) {
        this.docStatus = docStatus;
        return this;
    }

    public String[] getDocType() {
        return docType;
    }

    public ModeDocumentSearchModel setDocType(String[] docType) {
        this.docType = docType;
        return this;
    }

    public String[] getDocumentCount() {
        return documentCount;
    }

    public ModeDocumentSearchModel setDocumentCount(String[] documentCount) {
        this.documentCount = documentCount;
        return this;
    }

    public String[] getDocumentStatusReason() {
        return documentStatusReason;
    }

    public ModeDocumentSearchModel setDocumentStatusReason(String[] documentStatusReason) {
        this.documentStatusReason = documentStatusReason;
        return this;
    }

    public String[] getDocValue() {
        return docValue;
    }

    public ModeDocumentSearchModel setDocValue(String[] docValue) {
        this.docValue = docValue;
        return this;
    }

    public String getEndDate() {
        return endDate;
    }

    public ModeDocumentSearchModel setEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public String getExtensions() {
        return extensions;
    }

    public ModeDocumentSearchModel setExtensions(String extensions) {
        this.extensions = extensions;
        return this;
    }

    public String[] getFinalSize() {
        return finalSize;
    }

    public ModeDocumentSearchModel setFinalSize(String[] finalSize) {
        this.finalSize = finalSize;
        return this;
    }

    public String[] getGroupNumber() {
        return groupNumber;
    }

    public ModeDocumentSearchModel setGroupNumber(String[] groupNumber) {
        this.groupNumber = groupNumber;
        return this;
    }

    public String getIdentityFields() {
        return identityFields;
    }

    public ModeDocumentSearchModel setIdentityFields(String identityFields) {
        this.identityFields = identityFields;
        return this;
    }

    public String[] getInterchangeNumber() {
        return interchangeNumber;
    }

    public ModeDocumentSearchModel setInterchangeNumber(String[] interchangeNumber) {
        this.interchangeNumber = interchangeNumber;
        return this;
    }

    public String[] getInWfid() {
        return inWfid;
    }

    public ModeDocumentSearchModel setInWfid(String[] inWfid) {
        this.inWfid = inWfid;
        return this;
    }

    public Integer getMaxId() {
        return maxId;
    }

    public ModeDocumentSearchModel setMaxId(Integer maxId) {
        this.maxId = maxId;
        return this;
    }

    public Integer getMinId() {
        return minId;
    }

    public ModeDocumentSearchModel setMinId(Integer minId) {
        this.minId = minId;
        return this;
    }

    public String getMsgEndDate() {
        return msgEndDate;
    }

    public ModeDocumentSearchModel setMsgEndDate(String msgEndDate) {
        this.msgEndDate = msgEndDate;
        return this;
    }

    public String[] getMsgInFilename() {
        return msgInFilename;
    }

    public ModeDocumentSearchModel setMsgInFilename(String[] msgInFilename) {
        this.msgInFilename = msgInFilename;
        return this;
    }

    public String[] getMsgOutFilename() {
        return msgOutFilename;
    }

    public ModeDocumentSearchModel setMsgOutFilename(String[] msgOutFilename) {
        this.msgOutFilename = msgOutFilename;
        return this;
    }

    public String[] getMsgProtocol() {
        return msgProtocol;
    }

    public ModeDocumentSearchModel setMsgProtocol(String[] msgProtocol) {
        this.msgProtocol = msgProtocol;
        return this;
    }

    public String[] getMsgProtocolInfo() {
        return msgProtocolInfo;
    }

    public ModeDocumentSearchModel setMsgProtocolInfo(String[] msgProtocolInfo) {
        this.msgProtocolInfo = msgProtocolInfo;
        return this;
    }

    public String getMsgStartDate() {
        return msgStartDate;
    }

    public ModeDocumentSearchModel setMsgStartDate(String msgStartDate) {
        this.msgStartDate = msgStartDate;
        return this;
    }

    public String[] getMsgStatus() {
        return msgStatus;
    }

    public ModeDocumentSearchModel setMsgStatus(String[] msgStatus) {
        this.msgStatus = msgStatus;
        return this;
    }

    public String[] getMsgStatusReason() {
        return msgStatusReason;
    }

    public ModeDocumentSearchModel setMsgStatusReason(String[] msgStatusReason) {
        this.msgStatusReason = msgStatusReason;
        return this;
    }

    public String[] getOriginalSize() {
        return originalSize;
    }

    public ModeDocumentSearchModel setOriginalSize(String[] originalSize) {
        this.originalSize = originalSize;
        return this;
    }

    public String[] getOutWfid() {
        return outWfid;
    }

    public ModeDocumentSearchModel setOutWfid(String[] outWfid) {
        this.outWfid = outWfid;
        return this;
    }

    public Integer getPage() {
        return page;
    }

    public ModeDocumentSearchModel setPage(Integer page) {
        this.page = page;
        return this;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public ModeDocumentSearchModel setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public String[] getParentWfid() {
        return parentWfid;
    }

    public ModeDocumentSearchModel setParentWfid(String[] parentWfid) {
        this.parentWfid = parentWfid;
        return this;
    }

    public String[] getPartner() {
        return partner;
    }

    public ModeDocumentSearchModel setPartner(String[] partner) {
        this.partner = partner;
        return this;
    }

    public Object getPrincipal() {
        return principal;
    }

    public ModeDocumentSearchModel setPrincipal(Object principal) {
        this.principal = principal;
        return this;
    }

    public String[] getReference() {
        return reference;
    }

    public ModeDocumentSearchModel setReference(String[] reference) {
        this.reference = reference;
        return this;
    }

    public String getSortBy() {
        return sortBy;
    }

    public ModeDocumentSearchModel setSortBy(String sortBy) {
        this.sortBy = sortBy;
        return this;
    }

    public String getSortDir() {
        return sortDir;
    }

    public ModeDocumentSearchModel setSortDir(String sortDir) {
        this.sortDir = sortDir;
        return this;
    }

    public String[] getSource() {
        return source;
    }

    public ModeDocumentSearchModel setSource(String[] source) {
        this.source = source;
        return this;
    }

    public String[] getStandard() {
        return standard;
    }

    public ModeDocumentSearchModel setStandard(String[] standard) {
        this.standard = standard;
        return this;
    }

    public String getStartDate() {
        return startDate;
    }

    public ModeDocumentSearchModel setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public String[] getTestFlag() {
        return testFlag;
    }

    public ModeDocumentSearchModel setTestFlag(String[] testFlag) {
        this.testFlag = testFlag;
        return this;
    }

    public String[] getTransactionNumber() {
        return transactionNumber;
    }

    public ModeDocumentSearchModel setTransactionNumber(String[] transactionNumber) {
        this.transactionNumber = transactionNumber;
        return this;
    }

    public String[] getTransactionStatus() {
        return transactionStatus;
    }

    public ModeDocumentSearchModel setTransactionStatus(String[] transactionStatus) {
        this.transactionStatus = transactionStatus;
        return this;
    }

    public String[] getTransactionStatusReason() {
        return transactionStatusReason;
    }

    public ModeDocumentSearchModel setTransactionStatusReason(String[] transactionStatusReason) {
        this.transactionStatusReason = transactionStatusReason;
        return this;
    }

    public String[] getVersion() {
        return version;
    }

    public ModeDocumentSearchModel setVersion(String[] version) {
        this.version = version;
        return this;
    }

    public String[] getWfid() {
        return wfid;
    }

    public ModeDocumentSearchModel setWfid(String[] wfid) {
        this.wfid = wfid;
        return this;
    }

    public Boolean getWithDocExtensions() {
        return withDocExtensions;
    }

    public ModeDocumentSearchModel setWithDocExtensions(Boolean withDocExtensions) {
        this.withDocExtensions = withDocExtensions;
        return this;
    }

    public Boolean getWithEvents() {
        return withEvents;
    }

    public ModeDocumentSearchModel setWithEvents(Boolean withEvents) {
        this.withEvents = withEvents;
        return this;
    }

    public Boolean getWithNotes() {
        return withNotes;
    }

    public ModeDocumentSearchModel setWithNotes(Boolean withNotes) {
        this.withNotes = withNotes;
        return this;
    }

    public Boolean getWithReferences() {
        return withReferences;
    }

    public ModeDocumentSearchModel setWithReferences(Boolean withReferences) {
        this.withReferences = withReferences;
        return this;
    }
}
