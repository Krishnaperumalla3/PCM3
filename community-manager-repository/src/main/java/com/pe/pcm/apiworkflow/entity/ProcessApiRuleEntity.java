package com.pe.pcm.apiworkflow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Kiran Reddy.
 */
@Entity
@Table(name = "PETPE_PROCESS_RULE_API")
public class ProcessApiRuleEntity {

    @Id
    private String pkId;
    private String ruleName;
    private String ruleId;

    @Column(name = "PROPERTY_NAME_1")
    private String propertyName1;

    @Column(name = "PROPERTY_NAME_2")
    private String propertyName2;

    @Column(name = "PROPERTY_NAME_3")
    private String propertyName3;

    @Column(name = "PROPERTY_NAME_4")
    private String propertyName4;

    @Column(name = "PROPERTY_NAME_5")
    private String propertyName5;

    @Column(name = "PROPERTY_NAME_6")
    private String propertyName6;

    @Column(name = "PROPERTY_NAME_7")
    private String propertyName7;

    @Column(name = "PROPERTY_NAME_8")
    private String propertyName8;

    @Column(name = "PROPERTY_NAME_9")
    private String propertyName9;

    @Column(name = "PROPERTY_NAME_10")
    private String propertyName10;

    @Column(name = "PROPERTY_NAME_11")
    private String propertyName11;

    @Column(name = "PROPERTY_NAME_12")
    private String propertyName12;

    @Column(name = "PROPERTY_NAME_13")
    private String propertyName13;

    @Column(name = "PROPERTY_NAME_14")
    private String propertyName14;

    @Column(name = "PROPERTY_NAME_15")
    private String propertyName15;

    @Column(name = "PROPERTY_NAME_16")
    private String propertyName16;

    @Column(name = "PROPERTY_NAME_17")
    private String propertyName17;

    @Column(name = "PROPERTY_NAME_18")
    private String propertyName18;

    @Column(name = "PROPERTY_NAME_19")
    private String propertyName19;

    @Column(name = "PROPERTY_NAME_20")
    private String propertyName20;

    @Column(name = "PROPERTY_NAME_21")
    private String propertyName21;

    @Column(name = "PROPERTY_NAME_22")
    private String propertyName22;

    @Column(name = "PROPERTY_NAME_23")
    private String propertyName23;

    @Column(name = "PROPERTY_NAME_24")
    private String propertyName24;

    @Column(name = "PROPERTY_NAME_25")
    private String propertyName25;

    private String processDocRef;

    private Integer seqId;

    public String getPkId() {
        return pkId;
    }

    public ProcessApiRuleEntity setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getRuleName() {
        return ruleName;
    }

    public ProcessApiRuleEntity setRuleName(String ruleName) {
        this.ruleName = ruleName;
        return this;
    }

    public String getRuleId() {
        return ruleId;
    }

    public ProcessApiRuleEntity setRuleId(String ruleId) {
        this.ruleId = ruleId;
        return this;
    }

    public String getPropertyName1() {
        return propertyName1;
    }

    public ProcessApiRuleEntity setPropertyName1(String propertyName1) {
        this.propertyName1 = propertyName1;
        return this;
    }

    public String getPropertyName2() {
        return propertyName2;
    }

    public ProcessApiRuleEntity setPropertyName2(String propertyName2) {
        this.propertyName2 = propertyName2;
        return this;
    }

    public String getPropertyName3() {
        return propertyName3;
    }

    public ProcessApiRuleEntity setPropertyName3(String propertyName3) {
        this.propertyName3 = propertyName3;
        return this;
    }

    public String getPropertyName4() {
        return propertyName4;
    }

    public ProcessApiRuleEntity setPropertyName4(String propertyName4) {
        this.propertyName4 = propertyName4;
        return this;
    }

    public String getPropertyName5() {
        return propertyName5;
    }

    public ProcessApiRuleEntity setPropertyName5(String propertyName5) {
        this.propertyName5 = propertyName5;
        return this;
    }

    public String getPropertyName6() {
        return propertyName6;
    }

    public ProcessApiRuleEntity setPropertyName6(String propertyName6) {
        this.propertyName6 = propertyName6;
        return this;
    }

    public String getPropertyName7() {
        return propertyName7;
    }

    public ProcessApiRuleEntity setPropertyName7(String propertyName7) {
        this.propertyName7 = propertyName7;
        return this;
    }

    public String getPropertyName8() {
        return propertyName8;
    }

    public ProcessApiRuleEntity setPropertyName8(String propertyName8) {
        this.propertyName8 = propertyName8;
        return this;
    }

    public String getPropertyName9() {
        return propertyName9;
    }

    public ProcessApiRuleEntity setPropertyName9(String propertyName9) {
        this.propertyName9 = propertyName9;
        return this;
    }

    public String getPropertyName10() {
        return propertyName10;
    }

    public ProcessApiRuleEntity setPropertyName10(String propertyName10) {
        this.propertyName10 = propertyName10;
        return this;
    }

    public String getPropertyName11() {
        return propertyName11;
    }

    public ProcessApiRuleEntity setPropertyName11(String propertyName11) {
        this.propertyName11 = propertyName11;
        return this;
    }

    public String getPropertyName12() {
        return propertyName12;
    }

    public ProcessApiRuleEntity setPropertyName12(String propertyName12) {
        this.propertyName12 = propertyName12;
        return this;
    }

    public String getPropertyName13() {
        return propertyName13;
    }

    public ProcessApiRuleEntity setPropertyName13(String propertyName13) {
        this.propertyName13 = propertyName13;
        return this;
    }

    public String getPropertyName14() {
        return propertyName14;
    }

    public ProcessApiRuleEntity setPropertyName14(String propertyName14) {
        this.propertyName14 = propertyName14;
        return this;
    }

    public String getPropertyName15() {
        return propertyName15;
    }

    public ProcessApiRuleEntity setPropertyName15(String propertyName15) {
        this.propertyName15 = propertyName15;
        return this;
    }

    public String getPropertyName16() {
        return propertyName16;
    }

    public ProcessApiRuleEntity setPropertyName16(String propertyName16) {
        this.propertyName16 = propertyName16;
        return this;
    }

    public String getPropertyName17() {
        return propertyName17;
    }

    public ProcessApiRuleEntity setPropertyName17(String propertyName17) {
        this.propertyName17 = propertyName17;
        return this;
    }

    public String getPropertyName18() {
        return propertyName18;
    }

    public ProcessApiRuleEntity setPropertyName18(String propertyName18) {
        this.propertyName18 = propertyName18;
        return this;
    }

    public String getPropertyName19() {
        return propertyName19;
    }

    public ProcessApiRuleEntity setPropertyName19(String propertyName19) {
        this.propertyName19 = propertyName19;
        return this;
    }

    public String getPropertyName20() {
        return propertyName20;
    }

    public ProcessApiRuleEntity setPropertyName20(String propertyName20) {
        this.propertyName20 = propertyName20;
        return this;
    }

    public String getPropertyName21() {
        return propertyName21;
    }

    public ProcessApiRuleEntity setPropertyName21(String propertyName21) {
        this.propertyName21 = propertyName21;
        return this;
    }

    public String getPropertyName22() {
        return propertyName22;
    }

    public ProcessApiRuleEntity setPropertyName22(String propertyName22) {
        this.propertyName22 = propertyName22;
        return this;
    }

    public String getPropertyName23() {
        return propertyName23;
    }

    public ProcessApiRuleEntity setPropertyName23(String propertyName23) {
        this.propertyName23 = propertyName23;
        return this;
    }

    public String getPropertyName24() {
        return propertyName24;
    }

    public ProcessApiRuleEntity setPropertyName24(String propertyName24) {
        this.propertyName24 = propertyName24;
        return this;
    }

    public String getPropertyName25() {
        return propertyName25;
    }

    public ProcessApiRuleEntity setPropertyName25(String propertyName25) {
        this.propertyName25 = propertyName25;
        return this;
    }

    public String getProcessDocRef() {
        return processDocRef;
    }

    public ProcessApiRuleEntity setProcessDocRef(String processDocRef) {
        this.processDocRef = processDocRef;
        return this;
    }

    public Integer getSeqId() {
        return seqId;
    }

    public ProcessApiRuleEntity setSeqId(Integer seqId) {
        this.seqId = seqId;
        return this;
    }
}
