/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://pragmaedge.com/licenseagreement
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pe.pcm.reports;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pe.pcm.rule.RuleModel;

import java.util.StringJoiner;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataFlowModel {

    private String partnerName;

    private String applicationName;

    private String seqType;

    private String flow;

    private String fileType;

    private String docType;

    private String transaction;

    private String senderId;

    private String receiverId;

    private String ruleName;

    private String ruleValue;

    private String partnerPkId;

    private String applicationPkId;

    private RuleModel ruleModel;


    public String getPartnerName() {
        return partnerName;
    }

    public DataFlowModel setPartnerName(String partnerName) {
        this.partnerName = partnerName;
        return this;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public DataFlowModel setApplicationName(String applicationName) {
        this.applicationName = applicationName;
        return this;
    }

    public String getSeqType() {
        return seqType;
    }

    public DataFlowModel setSeqType(String seqType) {
        this.seqType = seqType;
        return this;
    }

    public String getFlow() {
        return flow;
    }

    public DataFlowModel setFlow(String flow) {
        this.flow = flow;
        return this;
    }

    public String getFileType() {
        return fileType;
    }

    public DataFlowModel setFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }

    public String getDocType() {
        return docType;
    }

    public DataFlowModel setDocType(String docType) {
        this.docType = docType;
        return this;
    }

    public String getTransaction() {
        return transaction;
    }

    public DataFlowModel setTransaction(String transaction) {
        this.transaction = transaction;
        return this;
    }

    public String getSenderId() {
        return senderId;
    }

    public DataFlowModel setSenderId(String senderId) {
        this.senderId = senderId;
        return this;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public DataFlowModel setReceiverId(String receiverId) {
        this.receiverId = receiverId;
        return this;
    }

    public String getRuleName() {
        return ruleName;
    }

    public DataFlowModel setRuleName(String ruleName) {
        this.ruleName = ruleName;
        return this;
    }

    public String getRuleValue() {
        return ruleValue;
    }

    public DataFlowModel setRuleValue(String ruleValue) {
        this.ruleValue = ruleValue;
        return this;
    }

    public String getPartnerPkId() {
        return partnerPkId;
    }

    public DataFlowModel setPartnerPkId(String partnerPkId) {
        this.partnerPkId = partnerPkId;
        return this;
    }

    public String getApplicationPkId() {
        return applicationPkId;
    }

    public DataFlowModel setApplicationPkId(String applicationPkId) {
        this.applicationPkId = applicationPkId;
        return this;
    }

    public RuleModel getRuleModel() {
        return ruleModel;
    }

    public DataFlowModel setRuleModel(RuleModel ruleModel) {
        this.ruleModel = ruleModel;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", DataFlowModel.class.getSimpleName() + "[", "]")
                .add("partnerName='" + partnerName + "'")
                .add("applicationName='" + applicationName + "'")
                .add("seqType='" + seqType + "'")
                .add("flow='" + flow + "'")
                .add("fileType='" + fileType + "'")
                .add("docType='" + docType + "'")
                .add("transaction='" + transaction + "'")
                .add("senderId='" + senderId + "'")
                .add("receiverId='" + receiverId + "'")
                .add("ruleName='" + ruleName + "'")
                .add("ruleValue='" + ruleValue + "'")
                .add("partnerPkId='" + partnerPkId + "'")
                .add("applicationPkId='" + applicationPkId + "'")
                .add("ruleModel=" + ruleModel)
                .toString();
    }
}
