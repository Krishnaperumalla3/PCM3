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
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataFlowMapper implements Serializable {

    private String pkId;

    private String partnerProfile;

    private String applicationProfile;

    private String processDocPkId;

    private String seqType;

    private String flow;

    private String fileName;

    private String docType;

    private String transaction;

    private String partnerId;

    private String receiverId;

    private String versionNo;

    private String processRulePkId;

    private String ruleName;

    private String ruleValue;

    private String businessProcessId;

    private String ruleProperty1;

    private String ruleProperty2;

    private String ruleProperty3;

    private String ruleProperty4;

    private String ruleProperty5;

    private String ruleProperty6;

    private String ruleProperty7;

    private String ruleProperty8;

    private String ruleProperty9;

    private String ruleProperty10;

    private String ruleProperty11;

    private String ruleProperty12;

    private String ruleProperty13;

    private String ruleProperty14;

    private String ruleProperty15;

    private String ruleProperty16;

    private String ruleProperty17;

    private String ruleProperty18;

    private String ruleProperty19;

    private String ruleProperty20;

    private String ruleProperty21;

    private String ruleProperty22;

    private String ruleProperty23;

    private String ruleProperty24;

    private String ruleProperty25;

    private Long rowNum;

    public String getPkId() {
        return pkId;
    }

    public DataFlowMapper setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getPartnerProfile() {
        return partnerProfile;
    }

    public DataFlowMapper setPartnerProfile(String partnerProfile) {
        this.partnerProfile = partnerProfile;
        return this;
    }

    public String getApplicationProfile() {
        return applicationProfile;
    }

    public DataFlowMapper setApplicationProfile(String applicationProfile) {
        this.applicationProfile = applicationProfile;
        return this;
    }

    public String getProcessDocPkId() {
        return processDocPkId;
    }

    public DataFlowMapper setProcessDocPkId(String processDocPkId) {
        this.processDocPkId = processDocPkId;
        return this;
    }

    public String getSeqType() {
        return seqType;
    }

    public DataFlowMapper setSeqType(String seqType) {
        this.seqType = seqType;
        return this;
    }

    public String getFlow() {
        return flow;
    }

    public DataFlowMapper setFlow(String flow) {
        this.flow = flow;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public DataFlowMapper setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getDocType() {
        return docType;
    }

    public DataFlowMapper setDocType(String docType) {
        this.docType = docType;
        return this;
    }

    public String getTransaction() {
        return transaction;
    }

    public DataFlowMapper setTransaction(String transaction) {
        this.transaction = transaction;
        return this;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public DataFlowMapper setPartnerId(String partnerId) {
        this.partnerId = partnerId;
        return this;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public DataFlowMapper setReceiverId(String receiverId) {
        this.receiverId = receiverId;
        return this;
    }

    public String getProcessRulePkId() {
        return processRulePkId;
    }

    public DataFlowMapper setProcessRulePkId(String processRulePkId) {
        this.processRulePkId = processRulePkId;
        return this;
    }

    public String getRuleName() {
        return ruleName;
    }

    public DataFlowMapper setRuleName(String ruleName) {
        this.ruleName = ruleName;
        return this;
    }

    public String getRuleValue() {
        return ruleValue;
    }

    public DataFlowMapper setRuleValue(String ruleValue) {
        this.ruleValue = ruleValue;
        return this;
    }

    public String getBusinessProcessId() {
        return businessProcessId;
    }

    public DataFlowMapper setBusinessProcessId(String businessProcessId) {
        this.businessProcessId = businessProcessId;
        return this;
    }

    public String getRuleProperty1() {
        return ruleProperty1;
    }

    public DataFlowMapper setRuleProperty1(String ruleProperty1) {
        this.ruleProperty1 = ruleProperty1;
        return this;
    }

    public String getRuleProperty2() {
        return ruleProperty2;
    }

    public DataFlowMapper setRuleProperty2(String ruleProperty2) {
        this.ruleProperty2 = ruleProperty2;
        return this;
    }

    public String getRuleProperty3() {
        return ruleProperty3;
    }

    public DataFlowMapper setRuleProperty3(String ruleProperty3) {
        this.ruleProperty3 = ruleProperty3;
        return this;
    }

    public String getRuleProperty4() {
        return ruleProperty4;
    }

    public DataFlowMapper setRuleProperty4(String ruleProperty4) {
        this.ruleProperty4 = ruleProperty4;
        return this;
    }

    public String getRuleProperty5() {
        return ruleProperty5;
    }

    public DataFlowMapper setRuleProperty5(String ruleProperty5) {
        this.ruleProperty5 = ruleProperty5;
        return this;
    }

    public String getRuleProperty6() {
        return ruleProperty6;
    }

    public DataFlowMapper setRuleProperty6(String ruleProperty6) {
        this.ruleProperty6 = ruleProperty6;
        return this;
    }

    public String getRuleProperty7() {
        return ruleProperty7;
    }

    public DataFlowMapper setRuleProperty7(String ruleProperty7) {
        this.ruleProperty7 = ruleProperty7;
        return this;
    }

    public String getRuleProperty8() {
        return ruleProperty8;
    }

    public DataFlowMapper setRuleProperty8(String ruleProperty8) {
        this.ruleProperty8 = ruleProperty8;
        return this;
    }

    public String getRuleProperty9() {
        return ruleProperty9;
    }

    public DataFlowMapper setRuleProperty9(String ruleProperty9) {
        this.ruleProperty9 = ruleProperty9;
        return this;
    }

    public String getRuleProperty10() {
        return ruleProperty10;
    }

    public DataFlowMapper setRuleProperty10(String ruleProperty10) {
        this.ruleProperty10 = ruleProperty10;
        return this;
    }

    public String getRuleProperty11() {
        return ruleProperty11;
    }

    public DataFlowMapper setRuleProperty11(String ruleProperty11) {
        this.ruleProperty11 = ruleProperty11;
        return this;
    }

    public String getRuleProperty12() {
        return ruleProperty12;
    }

    public DataFlowMapper setRuleProperty12(String ruleProperty12) {
        this.ruleProperty12 = ruleProperty12;
        return this;
    }

    public String getRuleProperty13() {
        return ruleProperty13;
    }

    public DataFlowMapper setRuleProperty13(String ruleProperty13) {
        this.ruleProperty13 = ruleProperty13;
        return this;
    }

    public String getRuleProperty14() {
        return ruleProperty14;
    }

    public DataFlowMapper setRuleProperty14(String ruleProperty14) {
        this.ruleProperty14 = ruleProperty14;
        return this;
    }

    public String getRuleProperty15() {
        return ruleProperty15;
    }

    public DataFlowMapper setRuleProperty15(String ruleProperty15) {
        this.ruleProperty15 = ruleProperty15;
        return this;
    }

    public String getRuleProperty16() {
        return ruleProperty16;
    }

    public DataFlowMapper setRuleProperty16(String ruleProperty16) {
        this.ruleProperty16 = ruleProperty16;
        return this;
    }

    public String getRuleProperty17() {
        return ruleProperty17;
    }

    public DataFlowMapper setRuleProperty17(String ruleProperty17) {
        this.ruleProperty17 = ruleProperty17;
        return this;
    }

    public String getRuleProperty18() {
        return ruleProperty18;
    }

    public DataFlowMapper setRuleProperty18(String ruleProperty18) {
        this.ruleProperty18 = ruleProperty18;
        return this;
    }

    public String getRuleProperty19() {
        return ruleProperty19;
    }

    public DataFlowMapper setRuleProperty19(String ruleProperty19) {
        this.ruleProperty19 = ruleProperty19;
        return this;
    }

    public String getRuleProperty20() {
        return ruleProperty20;
    }

    public DataFlowMapper setRuleProperty20(String ruleProperty20) {
        this.ruleProperty20 = ruleProperty20;
        return this;
    }

    public String getRuleProperty21() {
        return ruleProperty21;
    }

    public DataFlowMapper setRuleProperty21(String ruleProperty21) {
        this.ruleProperty21 = ruleProperty21;
        return this;
    }

    public String getRuleProperty22() {
        return ruleProperty22;
    }

    public DataFlowMapper setRuleProperty22(String ruleProperty22) {
        this.ruleProperty22 = ruleProperty22;
        return this;
    }

    public String getRuleProperty23() {
        return ruleProperty23;
    }

    public DataFlowMapper setRuleProperty23(String ruleProperty23) {
        this.ruleProperty23 = ruleProperty23;
        return this;
    }

    public String getRuleProperty24() {
        return ruleProperty24;
    }

    public DataFlowMapper setRuleProperty24(String ruleProperty24) {
        this.ruleProperty24 = ruleProperty24;
        return this;
    }

    public String getRuleProperty25() {
        return ruleProperty25;
    }

    public DataFlowMapper setRuleProperty25(String ruleProperty25) {
        this.ruleProperty25 = ruleProperty25;
        return this;
    }

    public Long getRowNum() {
        return rowNum;
    }

    public DataFlowMapper setRowNum(Long rowNum) {
        this.rowNum = rowNum;
        return this;
    }

    public static String getHeader() {

        return "PARTNER_PROFILE"
                + "," + "APPLICATION_PROFILE"
                + "," + "FLOW"
                + "," + "SEQ_TYPE"
                + "," + "FILE_NAME"
                + "," + "DOC_TYPE"
                + "," + "TRANSACTION"
                + "," + "SENDERID"
                + "," + "RECEIVERID"
                + "," + "VERSION"
                + "," + "RULE_NAME_COLUMN"
                + "," + "BUSINESS_PROCESS_ID"
                + "," + "RULE_PROPERTY_1"
                + "," + "RULE_PROPERTY_2"
                + "," + "RULE_PROPERTY_3"
                + "," + "RULE_PROPERTY_4"
                + "," + "RULE_PROPERTY_5"
                + "," + "RULE_PROPERTY_6"
                + "," + "RULE_PROPERTY_7"
                + "," + "RULE_PROPERTY_8"
                + "," + "RULE_PROPERTY_9"
                + "," + "RULE_PROPERTY_10"
                + "," + "RULE_PROPERTY_11"
                + "," + "RULE_PROPERTY_12"
                + "," + "RULE_PROPERTY_13"
                + "," + "RULE_PROPERTY_14"
                + "," + "RULE_PROPERTY_15"
                + "," + "RULE_PROPERTY_16"
                + "," + "RULE_PROPERTY_17"
                + "," + "RULE_PROPERTY_18"
                + "," + "RULE_PROPERTY_19"
                + "," + "RULE_PROPERTY_20"
                + "," + "RULE_PROPERTY_21"
                + "," + "RULE_PROPERTY_22"
                + "," + "RULE_PROPERTY_23"
                + "," + "RULE_PROPERTY_24"
                + "," + "RULE_PROPERTY_25" + "\n";
    }

    public String delimiterString() {
        return partnerProfile +
                "," + applicationProfile +
                "," + flow +
                "," + seqType +
                "," + fileName +
                "," + docType +
                "," + transaction +
                "," + partnerId +
                "," + receiverId +
                "," + versionNo +
                "," + ruleName +
                "," + businessProcessId +
                "," + ruleProperty1 +
                "," + ruleProperty2 +
                "," + ruleProperty3 +
                "," + ruleProperty4 +
                "," + ruleProperty5 +
                "," + ruleProperty6 +
                "," + ruleProperty7 +
                "," + ruleProperty8 +
                "," + ruleProperty9 +
                "," + ruleProperty10 +
                "," + ruleProperty11 +
                "," + ruleProperty12 +
                "," + ruleProperty13 +
                "," + ruleProperty14 +
                "," + ruleProperty15 +
                "," + ruleProperty16 +
                "," + ruleProperty17 +
                "," + ruleProperty18 +
                "," + ruleProperty19 +
                "," + ruleProperty20 +
                "," + ruleProperty21 +
                "," + ruleProperty22 +
                "," + ruleProperty23 +
                "," + ruleProperty24 +
                "," + ruleProperty25 + "\n";
    }

    public String getVersionNo() {
        return versionNo;
    }

    public DataFlowMapper setVersionNo(String versionNo) {
        this.versionNo = versionNo;
        return this;
    }
}
