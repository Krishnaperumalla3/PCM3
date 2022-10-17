package com.pe.pcm.mode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class X12OverridesModel implements Serializable {

    private String alias;
    private String docEncoding;
    private String elementSeparator;
    private String grpAckOverdueTime;
    private String grpAckOverdueTimeMinutes;
    private String grpExpectAck;
    private String grpFunctionalIdCode;
    private String grpReceiverId;
    private String grpResponsibleAgencyCode;
    private String grpSenderId;
    private String grpVersionReleaseIdCode;
    private Integer id;
    private String intAckOverdueTime;
    private String intAckOverdueTimeMinutes;
    private String intAckRequested;
    private String intAuthInfoQual;
    private String intAuthInformation;
    private String intControlStandardsId;
    private String intControlVerNum;
    private String intReceiverId;
    private String intReceiverIdQual;
    private String intSecurityInfoQual;
    private String intSecurityInformation;
    private String intSenderId;
    private String intSenderIdQual;
    private String intTestIndicator;
    private String segmentTerminator;
    private String subelementSeparator;
    private String transactionSetIdCode;

    public String getAlias() {
        return alias;
    }

    public X12OverridesModel setAlias(String alias) {
        this.alias = alias;
        return this;
    }

    public String getDocEncoding() {
        return docEncoding;
    }

    public X12OverridesModel setDocEncoding(String docEncoding) {
        this.docEncoding = docEncoding;
        return this;
    }

    public String getElementSeparator() {
        return elementSeparator;
    }

    public X12OverridesModel setElementSeparator(String elementSeparator) {
        this.elementSeparator = elementSeparator;
        return this;
    }

    public String getGrpAckOverdueTime() {
        return grpAckOverdueTime;
    }

    public X12OverridesModel setGrpAckOverdueTime(String grpAckOverdueTime) {
        this.grpAckOverdueTime = grpAckOverdueTime;
        return this;
    }

    public String getGrpAckOverdueTimeMinutes() {
        return grpAckOverdueTimeMinutes;
    }

    public X12OverridesModel setGrpAckOverdueTimeMinutes(String grpAckOverdueTimeMinutes) {
        this.grpAckOverdueTimeMinutes = grpAckOverdueTimeMinutes;
        return this;
    }

    public String getGrpExpectAck() {
        return grpExpectAck;
    }

    public X12OverridesModel setGrpExpectAck(String grpExpectAck) {
        this.grpExpectAck = grpExpectAck;
        return this;
    }

    public String getGrpFunctionalIdCode() {
        return grpFunctionalIdCode;
    }

    public X12OverridesModel setGrpFunctionalIdCode(String grpFunctionalIdCode) {
        this.grpFunctionalIdCode = grpFunctionalIdCode;
        return this;
    }

    public String getGrpReceiverId() {
        return grpReceiverId;
    }

    public X12OverridesModel setGrpReceiverId(String grpReceiverId) {
        this.grpReceiverId = grpReceiverId;
        return this;
    }

    public String getGrpResponsibleAgencyCode() {
        return grpResponsibleAgencyCode;
    }

    public X12OverridesModel setGrpResponsibleAgencyCode(String grpResponsibleAgencyCode) {
        this.grpResponsibleAgencyCode = grpResponsibleAgencyCode;
        return this;
    }

    public String getGrpSenderId() {
        return grpSenderId;
    }

    public X12OverridesModel setGrpSenderId(String grpSenderId) {
        this.grpSenderId = grpSenderId;
        return this;
    }

    public String getGrpVersionReleaseIdCode() {
        return grpVersionReleaseIdCode;
    }

    public X12OverridesModel setGrpVersionReleaseIdCode(String grpVersionReleaseIdCode) {
        this.grpVersionReleaseIdCode = grpVersionReleaseIdCode;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public X12OverridesModel setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getIntAckOverdueTime() {
        return intAckOverdueTime;
    }

    public X12OverridesModel setIntAckOverdueTime(String intAckOverdueTime) {
        this.intAckOverdueTime = intAckOverdueTime;
        return this;
    }

    public String getIntAckOverdueTimeMinutes() {
        return intAckOverdueTimeMinutes;
    }

    public X12OverridesModel setIntAckOverdueTimeMinutes(String intAckOverdueTimeMinutes) {
        this.intAckOverdueTimeMinutes = intAckOverdueTimeMinutes;
        return this;
    }

    public String getIntAckRequested() {
        return intAckRequested;
    }

    public X12OverridesModel setIntAckRequested(String intAckRequested) {
        this.intAckRequested = intAckRequested;
        return this;
    }

    public String getIntAuthInfoQual() {
        return intAuthInfoQual;
    }

    public X12OverridesModel setIntAuthInfoQual(String intAuthInfoQual) {
        this.intAuthInfoQual = intAuthInfoQual;
        return this;
    }

    public String getIntAuthInformation() {
        return intAuthInformation;
    }

    public X12OverridesModel setIntAuthInformation(String intAuthInformation) {
        this.intAuthInformation = intAuthInformation;
        return this;
    }

    public String getIntControlStandardsId() {
        return intControlStandardsId;
    }

    public X12OverridesModel setIntControlStandardsId(String intControlStandardsId) {
        this.intControlStandardsId = intControlStandardsId;
        return this;
    }

    public String getIntControlVerNum() {
        return intControlVerNum;
    }

    public X12OverridesModel setIntControlVerNum(String intControlVerNum) {
        this.intControlVerNum = intControlVerNum;
        return this;
    }

    public String getIntReceiverId() {
        return intReceiverId;
    }

    public X12OverridesModel setIntReceiverId(String intReceiverId) {
        this.intReceiverId = intReceiverId;
        return this;
    }

    public String getIntReceiverIdQual() {
        return intReceiverIdQual;
    }

    public X12OverridesModel setIntReceiverIdQual(String intReceiverIdQual) {
        this.intReceiverIdQual = intReceiverIdQual;
        return this;
    }

    public String getIntSecurityInfoQual() {
        return intSecurityInfoQual;
    }

    public X12OverridesModel setIntSecurityInfoQual(String intSecurityInfoQual) {
        this.intSecurityInfoQual = intSecurityInfoQual;
        return this;
    }

    public String getIntSecurityInformation() {
        return intSecurityInformation;
    }

    public X12OverridesModel setIntSecurityInformation(String intSecurityInformation) {
        this.intSecurityInformation = intSecurityInformation;
        return this;
    }

    public String getIntSenderId() {
        return intSenderId;
    }

    public X12OverridesModel setIntSenderId(String intSenderId) {
        this.intSenderId = intSenderId;
        return this;
    }

    public String getIntSenderIdQual() {
        return intSenderIdQual;
    }

    public X12OverridesModel setIntSenderIdQual(String intSenderIdQual) {
        this.intSenderIdQual = intSenderIdQual;
        return this;
    }

    public String getIntTestIndicator() {
        return intTestIndicator;
    }

    public X12OverridesModel setIntTestIndicator(String intTestIndicator) {
        this.intTestIndicator = intTestIndicator;
        return this;
    }

    public String getSegmentTerminator() {
        return segmentTerminator;
    }

    public X12OverridesModel setSegmentTerminator(String segmentTerminator) {
        this.segmentTerminator = segmentTerminator;
        return this;
    }

    public String getSubelementSeparator() {
        return subelementSeparator;
    }

    public X12OverridesModel setSubelementSeparator(String subelementSeparator) {
        this.subelementSeparator = subelementSeparator;
        return this;
    }

    public String getTransactionSetIdCode() {
        return transactionSetIdCode;
    }

    public X12OverridesModel setTransactionSetIdCode(String transactionSetIdCode) {
        this.transactionSetIdCode = transactionSetIdCode;
        return this;
    }

    @Override
    public String toString() {
        return "X12OverridesModel{" +
                "alias='" + alias + '\'' +
                ", docEncoding='" + docEncoding + '\'' +
                ", elementSeparator='" + elementSeparator + '\'' +
                ", grpAckOverdueTime='" + grpAckOverdueTime + '\'' +
                ", grpAckOverdueTimeMinutes='" + grpAckOverdueTimeMinutes + '\'' +
                ", grpExpectAck='" + grpExpectAck + '\'' +
                ", grpFunctionalIdCode='" + grpFunctionalIdCode + '\'' +
                ", grpReceiverId='" + grpReceiverId + '\'' +
                ", grpResponsibleAgencyCode='" + grpResponsibleAgencyCode + '\'' +
                ", grpSenderId='" + grpSenderId + '\'' +
                ", grpVersionReleaseIdCode='" + grpVersionReleaseIdCode + '\'' +
                ", id=" + id +
                ", intAckOverdueTime='" + intAckOverdueTime + '\'' +
                ", intAckOverdueTimeMinutes='" + intAckOverdueTimeMinutes + '\'' +
                ", intAckRequested='" + intAckRequested + '\'' +
                ", intAuthInfoQual='" + intAuthInfoQual + '\'' +
                ", intAuthInformation='" + intAuthInformation + '\'' +
                ", intControlStandardsId='" + intControlStandardsId + '\'' +
                ", intControlVerNum='" + intControlVerNum + '\'' +
                ", intReceiverId='" + intReceiverId + '\'' +
                ", intReceiverIdQual='" + intReceiverIdQual + '\'' +
                ", intSecurityInfoQual='" + intSecurityInfoQual + '\'' +
                ", intSecurityInformation='" + intSecurityInformation + '\'' +
                ", intSenderId='" + intSenderId + '\'' +
                ", intSenderIdQual='" + intSenderIdQual + '\'' +
                ", intTestIndicator='" + intTestIndicator + '\'' +
                ", segmentTerminator='" + segmentTerminator + '\'' +
                ", subelementSeparator='" + subelementSeparator + '\'' +
                ", transactionSetIdCode='" + transactionSetIdCode + '\'' +
                '}';
    }
}
