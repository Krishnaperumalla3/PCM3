package com.pe.pcm.mode;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AddRuleModel implements Serializable {

    private String activeFlag;
    private Integer clientId;
    private List<CoreParmsModel> coreParms;
    private String destinationId;
    private String documentType;
    private String ediBatch;
    private String ediDeferFlag;
    private String ediStandard;
    private String ediStreamFlag;
    private EdifactOverridesModel edifactOverrides;
    private Integer id;
    private String initialMessageStatus;
    private List<PreParmsModel> preParms;
    private String preSendBp;
    private Integer ruleVersion;
    private String sendBp;
    private String sourceId;
    private String timestamp;
    private TradacomsOverridesModel tradacomsOverrides;
    private String username;
    private X12OverridesModel x12Overrides;

    public String getActiveFlag() {
        return activeFlag;
    }

    public AddRuleModel setActiveFlag(String activeFlag) {
        this.activeFlag = activeFlag;
        return this;
    }

    public Integer getClientId() {
        return clientId;
    }

    public AddRuleModel setClientId(Integer clientId) {
        this.clientId = clientId;
        return this;
    }


    public List<CoreParmsModel> getCoreParms() {
        return coreParms;
    }

    public AddRuleModel setCoreParms(List<CoreParmsModel> coreParms) {
        this.coreParms = coreParms;
        return this;
    }

    public List<PreParmsModel> getPreParms() {
        return preParms;
    }

    public AddRuleModel setPreParms(List<PreParmsModel> preParms) {
        this.preParms = preParms;
        return this;
    }

    public TradacomsOverridesModel getTradacomsOverrides() {
        return tradacomsOverrides;
    }

    public AddRuleModel setTradacomsOverrides(TradacomsOverridesModel tradacomsOverrides) {
        this.tradacomsOverrides = tradacomsOverrides;
        return this;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public AddRuleModel setDestinationId(String destinationId) {
        this.destinationId = destinationId;
        return this;
    }

    public String getDocumentType() {
        return documentType;
    }

    public AddRuleModel setDocumentType(String documentType) {
        this.documentType = documentType;
        return this;
    }

    public String getEdiBatch() {
        return ediBatch;
    }

    public AddRuleModel setEdiBatch(String ediBatch) {
        this.ediBatch = ediBatch;
        return this;
    }

    public String getEdiDeferFlag() {
        return ediDeferFlag;
    }

    public AddRuleModel setEdiDeferFlag(String ediDeferFlag) {
        this.ediDeferFlag = ediDeferFlag;
        return this;
    }

    public String getEdiStandard() {
        return ediStandard;
    }

    public AddRuleModel setEdiStandard(String ediStandard) {
        this.ediStandard = ediStandard;
        return this;
    }

    public String getEdiStreamFlag() {
        return ediStreamFlag;
    }

    public AddRuleModel setEdiStreamFlag(String ediStreamFlag) {
        this.ediStreamFlag = ediStreamFlag;
        return this;
    }

    public EdifactOverridesModel getEdifactOverridesModel() {
        return edifactOverrides;
    }

    public AddRuleModel setEdifactOverridesModel(EdifactOverridesModel edifactOverrides) {
        this.edifactOverrides = edifactOverrides;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public AddRuleModel setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getInitialMessageStatus() {
        return initialMessageStatus;
    }

    public AddRuleModel setInitialMessageStatus(String initialMessageStatus) {
        this.initialMessageStatus = initialMessageStatus;
        return this;
    }


    public String getPreSendBp() {
        return preSendBp;
    }

    public AddRuleModel setPreSendBp(String preSendBp) {
        this.preSendBp = preSendBp;
        return this;
    }

    public Integer getRuleVersion() {
        return ruleVersion;
    }

    public AddRuleModel setRuleVersion(Integer ruleVersion) {
        this.ruleVersion = ruleVersion;
        return this;
    }

    public String getSendBp() {
        return sendBp;
    }

    public AddRuleModel setSendBp(String sendBp) {
        this.sendBp = sendBp;
        return this;
    }

    public String getSourceId() {
        return sourceId;
    }

    public AddRuleModel setSourceId(String sourceId) {
        this.sourceId = sourceId;
        return this;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public AddRuleModel setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }



    public String getUsername() {
        return username;
    }

    public AddRuleModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public X12OverridesModel getX12Overrides() {
        return x12Overrides;
    }

    public AddRuleModel setX12Overrides(X12OverridesModel x12Overrides) {
        this.x12Overrides = x12Overrides;
        return this;
    }

    @Override
    public String toString() {
        return "AddRuleModel{" +
                "activeFlag='" + activeFlag + '\'' +
                ", clientId=" + clientId +
                ", coreParms=" + coreParms +
                ", destinationId='" + destinationId + '\'' +
                ", documentType='" + documentType + '\'' +
                ", ediBatch='" + ediBatch + '\'' +
                ", ediDeferFlag='" + ediDeferFlag + '\'' +
                ", ediStandard='" + ediStandard + '\'' +
                ", ediStreamFlag='" + ediStreamFlag + '\'' +
                ", edifactOverrides=" + edifactOverrides +
                ", id=" + id +
                ", initialMessageStatus='" + initialMessageStatus + '\'' +
                ", preParms=" + preParms +
                ", preSendBp='" + preSendBp + '\'' +
                ", ruleVersion=" + ruleVersion +
                ", sendBp='" + sendBp + '\'' +
                ", sourceId='" + sourceId + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", tradacomsOverrides=" + tradacomsOverrides +
                ", username='" + username + '\'' +
                ", x12Overrides=" + x12Overrides +
                '}';
    }
}
