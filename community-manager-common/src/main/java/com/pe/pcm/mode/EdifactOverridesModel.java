package com.pe.pcm.mode;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EdifactOverridesModel implements Serializable {

    private String alias;
    private String decimalSeparator;
    private String elementSeparator;
    private String grpApplicationPswd;
    private String grpApplicationRecipientId;
    private String grpApplicationSenderId;
    private String grpControlAgency;
    private String grpRecipientIdCodeQual;
    private String grpSenderIdCodeQual;
    private Integer id;
    private String intAckOverdueTime;
    private String intAckOverdueTimeMinutes;
    private String intAckRequest;
    private String intAgreementId;
    private String intApplicationReference;
    private String intCharacterEncoding;
    private String intControlReference;
    private String intProcessingPriorityCode;
    private String intRecipientId;
    private String intRecipientIdCodeQual;
    private String intRecipientInternalId;
    private String intRecipientInternalSubId;
    private String intRecipientRefPswd;
    private String intRecipientRefPswdQual;
    private String intSenderId;
    private String intSenderIdCodeQual;
    private String intSenderInternalId;
    private String intSenderInternalSubId;
    private String intSrvCodeListDirVerNum;
    private String intSyntaxId;
    private String intSyntaxVerNum;
    private String intTestIndicator;
    private String msgAssociationAssignedCode;
    private String msgCodeListDirVerNum;
    private String msgCommonAccessReference;
    private String msgControlAgency;
    private String msgFirstAndLastTransfer;
    private String msgImplControlAgency;
    private String msgImplGuidelineId;
    private String msgImplGuidelineRelNum;
    private String msgImplGuidelineVerNum;
    private String msgRelNum;
    private String msgScenarioControlAgency;
    private String msgScenarioId;
    private String msgScenarioRelNum;
    private String msgScenarioVerNum;
    private String msgSequenceOfTransfers;
    private String msgSubsetControlAgency;
    private String msgSubsetId;
    private String msgSubsetRelNum;
    private String msgSubsetVerNum;
    private String msgType;
    private String msgTypeSubFunctionId;
    private String msgVerNum;
    private String releaseCharacter;
    private String repeatingElementSeparator;
    private String segmentTerminator;
    private String subelementSeparator;
    private String una;
    private String useGroups;

    public String getAlias() {
        return alias;
    }

    public EdifactOverridesModel setAlias(String alias) {
        this.alias = alias;
        return this;
    }

    public String getDecimalSeparator() {
        return decimalSeparator;
    }

    public EdifactOverridesModel setDecimalSeparator(String decimalSeparator) {
        this.decimalSeparator = decimalSeparator;
        return this;
    }

    public String getElementSeparator() {
        return elementSeparator;
    }

    public EdifactOverridesModel setElementSeparator(String elementSeparator) {
        this.elementSeparator = elementSeparator;
        return this;
    }

    public String getGrpApplicationPswd() {
        return grpApplicationPswd;
    }

    public EdifactOverridesModel setGrpApplicationPswd(String grpApplicationPswd) {
        this.grpApplicationPswd = grpApplicationPswd;
        return this;
    }

    public String getGrpApplicationRecipientId() {
        return grpApplicationRecipientId;
    }

    public EdifactOverridesModel setGrpApplicationRecipientId(String grpApplicationRecipientId) {
        this.grpApplicationRecipientId = grpApplicationRecipientId;
        return this;
    }

    public String getGrpApplicationSenderId() {
        return grpApplicationSenderId;
    }

    public EdifactOverridesModel setGrpApplicationSenderId(String grpApplicationSenderId) {
        this.grpApplicationSenderId = grpApplicationSenderId;
        return this;
    }

    public String getGrpControlAgency() {
        return grpControlAgency;
    }

    public EdifactOverridesModel setGrpControlAgency(String grpControlAgency) {
        this.grpControlAgency = grpControlAgency;
        return this;
    }

    public String getGrpRecipientIdCodeQual() {
        return grpRecipientIdCodeQual;
    }

    public EdifactOverridesModel setGrpRecipientIdCodeQual(String grpRecipientIdCodeQual) {
        this.grpRecipientIdCodeQual = grpRecipientIdCodeQual;
        return this;
    }

    public String getGrpSenderIdCodeQual() {
        return grpSenderIdCodeQual;
    }

    public EdifactOverridesModel setGrpSenderIdCodeQual(String grpSenderIdCodeQual) {
        this.grpSenderIdCodeQual = grpSenderIdCodeQual;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public EdifactOverridesModel setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getIntAckOverdueTime() {
        return intAckOverdueTime;
    }

    public EdifactOverridesModel setIntAckOverdueTime(String intAckOverdueTime) {
        this.intAckOverdueTime = intAckOverdueTime;
        return this;
    }

    public String getIntAckOverdueTimeMinutes() {
        return intAckOverdueTimeMinutes;
    }

    public EdifactOverridesModel setIntAckOverdueTimeMinutes(String intAckOverdueTimeMinutes) {
        this.intAckOverdueTimeMinutes = intAckOverdueTimeMinutes;
        return this;
    }

    public String getIntAckRequest() {
        return intAckRequest;
    }

    public EdifactOverridesModel setIntAckRequest(String intAckRequest) {
        this.intAckRequest = intAckRequest;
        return this;
    }

    public String getIntAgreementId() {
        return intAgreementId;
    }

    public EdifactOverridesModel setIntAgreementId(String intAgreementId) {
        this.intAgreementId = intAgreementId;
        return this;
    }

    public String getIntApplicationReference() {
        return intApplicationReference;
    }

    public EdifactOverridesModel setIntApplicationReference(String intApplicationReference) {
        this.intApplicationReference = intApplicationReference;
        return this;
    }

    public String getIntCharacterEncoding() {
        return intCharacterEncoding;
    }

    public EdifactOverridesModel setIntCharacterEncoding(String intCharacterEncoding) {
        this.intCharacterEncoding = intCharacterEncoding;
        return this;
    }

    public String getIntControlReference() {
        return intControlReference;
    }

    public EdifactOverridesModel setIntControlReference(String intControlReference) {
        this.intControlReference = intControlReference;
        return this;
    }

    public String getIntProcessingPriorityCode() {
        return intProcessingPriorityCode;
    }

    public EdifactOverridesModel setIntProcessingPriorityCode(String intProcessingPriorityCode) {
        this.intProcessingPriorityCode = intProcessingPriorityCode;
        return this;
    }

    public String getIntRecipientId() {
        return intRecipientId;
    }

    public EdifactOverridesModel setIntRecipientId(String intRecipientId) {
        this.intRecipientId = intRecipientId;
        return this;
    }

    public String getIntRecipientIdCodeQual() {
        return intRecipientIdCodeQual;
    }

    public EdifactOverridesModel setIntRecipientIdCodeQual(String intRecipientIdCodeQual) {
        this.intRecipientIdCodeQual = intRecipientIdCodeQual;
        return this;
    }

    public String getIntRecipientInternalId() {
        return intRecipientInternalId;
    }

    public EdifactOverridesModel setIntRecipientInternalId(String intRecipientInternalId) {
        this.intRecipientInternalId = intRecipientInternalId;
        return this;
    }

    public String getIntRecipientInternalSubId() {
        return intRecipientInternalSubId;
    }

    public EdifactOverridesModel setIntRecipientInternalSubId(String intRecipientInternalSubId) {
        this.intRecipientInternalSubId = intRecipientInternalSubId;
        return this;
    }

    public String getIntRecipientRefPswd() {
        return intRecipientRefPswd;
    }

    public EdifactOverridesModel setIntRecipientRefPswd(String intRecipientRefPswd) {
        this.intRecipientRefPswd = intRecipientRefPswd;
        return this;
    }

    public String getIntRecipientRefPswdQual() {
        return intRecipientRefPswdQual;
    }

    public EdifactOverridesModel setIntRecipientRefPswdQual(String intRecipientRefPswdQual) {
        this.intRecipientRefPswdQual = intRecipientRefPswdQual;
        return this;
    }

    public String getIntSenderId() {
        return intSenderId;
    }

    public EdifactOverridesModel setIntSenderId(String intSenderId) {
        this.intSenderId = intSenderId;
        return this;
    }

    public String getIntSenderIdCodeQual() {
        return intSenderIdCodeQual;
    }

    public EdifactOverridesModel setIntSenderIdCodeQual(String intSenderIdCodeQual) {
        this.intSenderIdCodeQual = intSenderIdCodeQual;
        return this;
    }

    public String getIntSenderInternalId() {
        return intSenderInternalId;
    }

    public EdifactOverridesModel setIntSenderInternalId(String intSenderInternalId) {
        this.intSenderInternalId = intSenderInternalId;
        return this;
    }

    public String getIntSenderInternalSubId() {
        return intSenderInternalSubId;
    }

    public EdifactOverridesModel setIntSenderInternalSubId(String intSenderInternalSubId) {
        this.intSenderInternalSubId = intSenderInternalSubId;
        return this;
    }

    public String getIntSrvCodeListDirVerNum() {
        return intSrvCodeListDirVerNum;
    }

    public EdifactOverridesModel setIntSrvCodeListDirVerNum(String intSrvCodeListDirVerNum) {
        this.intSrvCodeListDirVerNum = intSrvCodeListDirVerNum;
        return this;
    }

    public String getIntSyntaxId() {
        return intSyntaxId;
    }

    public EdifactOverridesModel setIntSyntaxId(String intSyntaxId) {
        this.intSyntaxId = intSyntaxId;
        return this;
    }

    public String getIntSyntaxVerNum() {
        return intSyntaxVerNum;
    }

    public EdifactOverridesModel setIntSyntaxVerNum(String intSyntaxVerNum) {
        this.intSyntaxVerNum = intSyntaxVerNum;
        return this;
    }

    public String getIntTestIndicator() {
        return intTestIndicator;
    }

    public EdifactOverridesModel setIntTestIndicator(String intTestIndicator) {
        this.intTestIndicator = intTestIndicator;
        return this;
    }

    public String getMsgAssociationAssignedCode() {
        return msgAssociationAssignedCode;
    }

    public EdifactOverridesModel setMsgAssociationAssignedCode(String msgAssociationAssignedCode) {
        this.msgAssociationAssignedCode = msgAssociationAssignedCode;
        return this;
    }

    public String getMsgCodeListDirVerNum() {
        return msgCodeListDirVerNum;
    }

    public EdifactOverridesModel setMsgCodeListDirVerNum(String msgCodeListDirVerNum) {
        this.msgCodeListDirVerNum = msgCodeListDirVerNum;
        return this;
    }

    public String getMsgCommonAccessReference() {
        return msgCommonAccessReference;
    }

    public EdifactOverridesModel setMsgCommonAccessReference(String msgCommonAccessReference) {
        this.msgCommonAccessReference = msgCommonAccessReference;
        return this;
    }

    public String getMsgControlAgency() {
        return msgControlAgency;
    }

    public EdifactOverridesModel setMsgControlAgency(String msgControlAgency) {
        this.msgControlAgency = msgControlAgency;
        return this;
    }

    public String getMsgFirstAndLastTransfer() {
        return msgFirstAndLastTransfer;
    }

    public EdifactOverridesModel setMsgFirstAndLastTransfer(String msgFirstAndLastTransfer) {
        this.msgFirstAndLastTransfer = msgFirstAndLastTransfer;
        return this;
    }

    public String getMsgImplControlAgency() {
        return msgImplControlAgency;
    }

    public EdifactOverridesModel setMsgImplControlAgency(String msgImplControlAgency) {
        this.msgImplControlAgency = msgImplControlAgency;
        return this;
    }

    public String getMsgImplGuidelineId() {
        return msgImplGuidelineId;
    }

    public EdifactOverridesModel setMsgImplGuidelineId(String msgImplGuidelineId) {
        this.msgImplGuidelineId = msgImplGuidelineId;
        return this;
    }

    public String getMsgImplGuidelineRelNum() {
        return msgImplGuidelineRelNum;
    }

    public EdifactOverridesModel setMsgImplGuidelineRelNum(String msgImplGuidelineRelNum) {
        this.msgImplGuidelineRelNum = msgImplGuidelineRelNum;
        return this;
    }

    public String getMsgImplGuidelineVerNum() {
        return msgImplGuidelineVerNum;
    }

    public EdifactOverridesModel setMsgImplGuidelineVerNum(String msgImplGuidelineVerNum) {
        this.msgImplGuidelineVerNum = msgImplGuidelineVerNum;
        return this;
    }

    public String getMsgRelNum() {
        return msgRelNum;
    }

    public EdifactOverridesModel setMsgRelNum(String msgRelNum) {
        this.msgRelNum = msgRelNum;
        return this;
    }

    public String getMsgScenarioControlAgency() {
        return msgScenarioControlAgency;
    }

    public EdifactOverridesModel setMsgScenarioControlAgency(String msgScenarioControlAgency) {
        this.msgScenarioControlAgency = msgScenarioControlAgency;
        return this;
    }

    public String getMsgScenarioId() {
        return msgScenarioId;
    }

    public EdifactOverridesModel setMsgScenarioId(String msgScenarioId) {
        this.msgScenarioId = msgScenarioId;
        return this;
    }

    public String getMsgScenarioRelNum() {
        return msgScenarioRelNum;
    }

    public EdifactOverridesModel setMsgScenarioRelNum(String msgScenarioRelNum) {
        this.msgScenarioRelNum = msgScenarioRelNum;
        return this;
    }

    public String getMsgScenarioVerNum() {
        return msgScenarioVerNum;
    }

    public EdifactOverridesModel setMsgScenarioVerNum(String msgScenarioVerNum) {
        this.msgScenarioVerNum = msgScenarioVerNum;
        return this;
    }

    public String getMsgSequenceOfTransfers() {
        return msgSequenceOfTransfers;
    }

    public EdifactOverridesModel setMsgSequenceOfTransfers(String msgSequenceOfTransfers) {
        this.msgSequenceOfTransfers = msgSequenceOfTransfers;
        return this;
    }

    public String getMsgSubsetControlAgency() {
        return msgSubsetControlAgency;
    }

    public EdifactOverridesModel setMsgSubsetControlAgency(String msgSubsetControlAgency) {
        this.msgSubsetControlAgency = msgSubsetControlAgency;
        return this;
    }

    public String getMsgSubsetId() {
        return msgSubsetId;
    }

    public EdifactOverridesModel setMsgSubsetId(String msgSubsetId) {
        this.msgSubsetId = msgSubsetId;
        return this;
    }

    public String getMsgSubsetRelNum() {
        return msgSubsetRelNum;
    }

    public EdifactOverridesModel setMsgSubsetRelNum(String msgSubsetRelNum) {
        this.msgSubsetRelNum = msgSubsetRelNum;
        return this;
    }

    public String getMsgSubsetVerNum() {
        return msgSubsetVerNum;
    }

    public EdifactOverridesModel setMsgSubsetVerNum(String msgSubsetVerNum) {
        this.msgSubsetVerNum = msgSubsetVerNum;
        return this;
    }

    public String getMsgType() {
        return msgType;
    }

    public EdifactOverridesModel setMsgType(String msgType) {
        this.msgType = msgType;
        return this;
    }

    public String getMsgTypeSubFunctionId() {
        return msgTypeSubFunctionId;
    }

    public EdifactOverridesModel setMsgTypeSubFunctionId(String msgTypeSubFunctionId) {
        this.msgTypeSubFunctionId = msgTypeSubFunctionId;
        return this;
    }

    public String getMsgVerNum() {
        return msgVerNum;
    }

    public EdifactOverridesModel setMsgVerNum(String msgVerNum) {
        this.msgVerNum = msgVerNum;
        return this;
    }

    public String getReleaseCharacter() {
        return releaseCharacter;
    }

    public EdifactOverridesModel setReleaseCharacter(String releaseCharacter) {
        this.releaseCharacter = releaseCharacter;
        return this;
    }

    public String getRepeatingElementSeparator() {
        return repeatingElementSeparator;
    }

    public EdifactOverridesModel setRepeatingElementSeparator(String repeatingElementSeparator) {
        this.repeatingElementSeparator = repeatingElementSeparator;
        return this;
    }

    public String getSegmentTerminator() {
        return segmentTerminator;
    }

    public EdifactOverridesModel setSegmentTerminator(String segmentTerminator) {
        this.segmentTerminator = segmentTerminator;
        return this;
    }

    public String getSubelementSeparator() {
        return subelementSeparator;
    }

    public EdifactOverridesModel setSubelementSeparator(String subelementSeparator) {
        this.subelementSeparator = subelementSeparator;
        return this;
    }

    public String getUna() {
        return una;
    }

    public EdifactOverridesModel setUna(String una) {
        this.una = una;
        return this;
    }

    public String getUseGroups() {
        return useGroups;
    }

    public EdifactOverridesModel setUseGroups(String useGroups) {
        this.useGroups = useGroups;
        return this;
    }

    @Override
    public String toString() {
        return "EdifactOverridesModel{" +
                "alias='" + alias + '\'' +
                ", decimalSeparator='" + decimalSeparator + '\'' +
                ", elementSeparator='" + elementSeparator + '\'' +
                ", grpApplicationPswd='" + grpApplicationPswd + '\'' +
                ", grpApplicationRecipientId='" + grpApplicationRecipientId + '\'' +
                ", grpApplicationSenderId='" + grpApplicationSenderId + '\'' +
                ", grpControlAgency='" + grpControlAgency + '\'' +
                ", grpRecipientIdCodeQual='" + grpRecipientIdCodeQual + '\'' +
                ", grpSenderIdCodeQual='" + grpSenderIdCodeQual + '\'' +
                ", id=" + id +
                ", intAckOverdueTime='" + intAckOverdueTime + '\'' +
                ", intAckOverdueTimeMinutes='" + intAckOverdueTimeMinutes + '\'' +
                ", intAckRequest='" + intAckRequest + '\'' +
                ", intAgreementId='" + intAgreementId + '\'' +
                ", intApplicationReference='" + intApplicationReference + '\'' +
                ", intCharacterEncoding='" + intCharacterEncoding + '\'' +
                ", intControlReference='" + intControlReference + '\'' +
                ", intProcessingPriorityCode='" + intProcessingPriorityCode + '\'' +
                ", intRecipientId='" + intRecipientId + '\'' +
                ", intRecipientIdCodeQual='" + intRecipientIdCodeQual + '\'' +
                ", intRecipientInternalId='" + intRecipientInternalId + '\'' +
                ", intRecipientInternalSubId='" + intRecipientInternalSubId + '\'' +
                ", intRecipientRefPswd='" + intRecipientRefPswd + '\'' +
                ", intRecipientRefPswdQual='" + intRecipientRefPswdQual + '\'' +
                ", intSenderId='" + intSenderId + '\'' +
                ", intSenderIdCodeQual='" + intSenderIdCodeQual + '\'' +
                ", intSenderInternalId='" + intSenderInternalId + '\'' +
                ", intSenderInternalSubId='" + intSenderInternalSubId + '\'' +
                ", intSrvCodeListDirVerNum='" + intSrvCodeListDirVerNum + '\'' +
                ", intSyntaxId='" + intSyntaxId + '\'' +
                ", intSyntaxVerNum='" + intSyntaxVerNum + '\'' +
                ", intTestIndicator='" + intTestIndicator + '\'' +
                ", msgAssociationAssignedCode='" + msgAssociationAssignedCode + '\'' +
                ", msgCodeListDirVerNum='" + msgCodeListDirVerNum + '\'' +
                ", msgCommonAccessReference='" + msgCommonAccessReference + '\'' +
                ", msgControlAgency='" + msgControlAgency + '\'' +
                ", msgFirstAndLastTransfer='" + msgFirstAndLastTransfer + '\'' +
                ", msgImplControlAgency='" + msgImplControlAgency + '\'' +
                ", msgImplGuidelineId='" + msgImplGuidelineId + '\'' +
                ", msgImplGuidelineRelNum='" + msgImplGuidelineRelNum + '\'' +
                ", msgImplGuidelineVerNum='" + msgImplGuidelineVerNum + '\'' +
                ", msgRelNum='" + msgRelNum + '\'' +
                ", msgScenarioControlAgency='" + msgScenarioControlAgency + '\'' +
                ", msgScenarioId='" + msgScenarioId + '\'' +
                ", msgScenarioRelNum='" + msgScenarioRelNum + '\'' +
                ", msgScenarioVerNum='" + msgScenarioVerNum + '\'' +
                ", msgSequenceOfTransfers='" + msgSequenceOfTransfers + '\'' +
                ", msgSubsetControlAgency='" + msgSubsetControlAgency + '\'' +
                ", msgSubsetId='" + msgSubsetId + '\'' +
                ", msgSubsetRelNum='" + msgSubsetRelNum + '\'' +
                ", msgSubsetVerNum='" + msgSubsetVerNum + '\'' +
                ", msgType='" + msgType + '\'' +
                ", msgTypeSubFunctionId='" + msgTypeSubFunctionId + '\'' +
                ", msgVerNum='" + msgVerNum + '\'' +
                ", releaseCharacter='" + releaseCharacter + '\'' +
                ", repeatingElementSeparator='" + repeatingElementSeparator + '\'' +
                ", segmentTerminator='" + segmentTerminator + '\'' +
                ", subelementSeparator='" + subelementSeparator + '\'' +
                ", una='" + una + '\'' +
                ", useGroups='" + useGroups + '\'' +
                '}';
    }
}
