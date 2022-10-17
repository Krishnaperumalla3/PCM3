package com.pe.pcm.mode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SendRuleSearchModel implements Serializable {

    private String active;

    private String clientId;

    private String[] coreBpParmValue;

    private String[] destination;

    private String[] docType;

    private String[] ediBatch;

    private String ediDeferFlag;

    private String[] ediStandard;

    private String ediStreamFlag;

    private String[] id;

    private String[] initialMessageStatus;

    private Integer maxId;

    private Integer minId;

    private Integer page;

    private Integer pageSize;

    private String[] preBpParmValue;

    private String[] preSendBp;

    private String[] sendBp;

    private String sortBy;

    private String sortDir;

    private String[] source;

    public String getActive() {

        return active;
    }

    public SendRuleSearchModel setActive(String active) {
        this.active = active;
        return this;
    }

    public String getClientId() {
        return clientId;
    }

    public SendRuleSearchModel setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String[] getCoreBpParmValue() {
        return coreBpParmValue;
    }

    public SendRuleSearchModel setCoreBpParmValue(String[] coreBpParmValue) {
        this.coreBpParmValue = coreBpParmValue;
        return this;
    }

    public String[] getDestination() {
        return destination;
    }

    public SendRuleSearchModel setDestination(String[] destination) {
        this.destination = destination;
        return this;
    }

    public String[] getDocType() {
        return docType;
    }

    public SendRuleSearchModel setDocType(String[] docType) {
        this.docType = docType;
        return this;
    }

    public String[] getEdiBatch() {
        return ediBatch;
    }

    public SendRuleSearchModel setEdiBatch(String[] ediBatch) {
        this.ediBatch = ediBatch;
        return this;
    }

    public String getEdiDeferFlag() {
        return ediDeferFlag;
    }

    public SendRuleSearchModel setEdiDeferFlag(String ediDeferFlag) {
        this.ediDeferFlag = ediDeferFlag;
        return this;
    }

    public String[] getEdiStandard() {
        return ediStandard;
    }

    public SendRuleSearchModel setEdiStandard(String[] ediStandard) {
        this.ediStandard = ediStandard;
        return this;
    }

    public String getEdiStreamFlag() {
        return ediStreamFlag;
    }

    public SendRuleSearchModel setEdiStreamFlag(String ediStreamFlag) {
        this.ediStreamFlag = ediStreamFlag;
        return this;
    }

    public String[] getId() {
        return id;
    }

    public SendRuleSearchModel setId(String[] id) {
        this.id = id;
        return this;
    }

    public String[] getInitialMessageStatus() {
        return initialMessageStatus;
    }

    public SendRuleSearchModel setInitialMessageStatus(String[] initialMessageStatus) {
        this.initialMessageStatus = initialMessageStatus;
        return this;
    }

    public Integer getMaxId() {
        return maxId;
    }

    public SendRuleSearchModel setMaxId(Integer maxId) {
        this.maxId = maxId;
        return this;
    }

    public Integer getMinId() {
        return minId;
    }

    public SendRuleSearchModel setMinId(Integer minId) {
        this.minId = minId;
        return this;
    }

    public Integer getPage() {
        return page;
    }

    public SendRuleSearchModel setPage(Integer page) {
        this.page = page;
        return this;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public SendRuleSearchModel setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public String[] getPreBpParmValue() {
        return preBpParmValue;
    }

    public SendRuleSearchModel setPreBpParmValue(String[] preBpParmValue) {
        this.preBpParmValue = preBpParmValue;
        return this;
    }

    public String[] getPreSendBp() {
        return preSendBp;
    }

    public SendRuleSearchModel setPreSendBp(String[] preSendBp) {
        this.preSendBp = preSendBp;
        return this;
    }

    public String[] getSendBp() {
        return sendBp;
    }

    public SendRuleSearchModel setSendBp(String[] sendBp) {
        this.sendBp = sendBp;
        return this;
    }

    public String getSortBy() {
        return sortBy;
    }

    public SendRuleSearchModel setSortBy(String sortBy) {
        this.sortBy = sortBy;
        return this;
    }

    public String getSortDir() {
        return sortDir;
    }

    public SendRuleSearchModel setSortDir(String sortDir) {
        this.sortDir = sortDir;
        return this;
    }

    public String[] getSource() {
        return source;
    }

    public SendRuleSearchModel setSource(String[] source) {
        this.source = source;
        return this;
    }

    @Override
    public String toString() {
        return "SendRuleSearchModel{" +
                "active='" + active + '\'' +
                ", clientId='" + clientId + '\'' +
                ", coreBpParmValue=" + Arrays.toString(coreBpParmValue) +
                ", destination=" + Arrays.toString(destination) +
                ", docType=" + Arrays.toString(docType) +
                ", ediBatch=" + Arrays.toString(ediBatch) +
                ", ediDeferFlag='" + ediDeferFlag + '\'' +
                ", ediStandard=" + Arrays.toString(ediStandard) +
                ", ediStreamFlag='" + ediStreamFlag + '\'' +
                ", id=" + Arrays.toString(id) +
                ", initialMessageStatus=" + Arrays.toString(initialMessageStatus) +
                ", maxId=" + maxId +
                ", minId=" + minId +
                ", page=" + page +
                ", pageSize=" + pageSize +
                ", preBpParmValue=" + Arrays.toString(preBpParmValue) +
                ", preSendBp=" + Arrays.toString(preSendBp) +
                ", sendBp=" + Arrays.toString(sendBp) +
                ", sortBy='" + sortBy + '\'' +
                ", sortDir='" + sortDir + '\'' +
                ", source=" + Arrays.toString(source) +
                '}';
    }
}
