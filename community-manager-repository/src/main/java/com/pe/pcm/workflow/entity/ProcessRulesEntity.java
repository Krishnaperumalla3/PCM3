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

package com.pe.pcm.workflow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "PETPE_PROCESSRULES")
public class ProcessRulesEntity implements Serializable {

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

    public ProcessRulesEntity setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getRuleName() {
        return ruleName;
    }

    public ProcessRulesEntity setRuleName(String ruleName) {
        this.ruleName = ruleName;
        return this;
    }

    public String getRuleId() {
        return ruleId;
    }

    public ProcessRulesEntity setRuleId(String ruleId) {
        this.ruleId = ruleId;
        return this;
    }

    public String getPropertyName1() {
        return propertyName1;
    }

    public ProcessRulesEntity setPropertyName1(String propertyName1) {
        this.propertyName1 = propertyName1;
        return this;
    }

    public String getPropertyName2() {
        return propertyName2;
    }

    public ProcessRulesEntity setPropertyName2(String propertyName2) {
        this.propertyName2 = propertyName2;
        return this;
    }

    public String getPropertyName3() {
        return propertyName3;
    }

    public ProcessRulesEntity setPropertyName3(String propertyName3) {
        this.propertyName3 = propertyName3;
        return this;
    }

    public String getPropertyName4() {
        return propertyName4;
    }

    public ProcessRulesEntity setPropertyName4(String propertyName4) {
        this.propertyName4 = propertyName4;
        return this;
    }

    public String getPropertyName5() {
        return propertyName5;
    }

    public ProcessRulesEntity setPropertyName5(String propertyName5) {
        this.propertyName5 = propertyName5;
        return this;
    }

    public String getPropertyName6() {
        return propertyName6;
    }

    public ProcessRulesEntity setPropertyName6(String propertyName6) {
        this.propertyName6 = propertyName6;
        return this;
    }

    public String getPropertyName7() {
        return propertyName7;
    }

    public ProcessRulesEntity setPropertyName7(String propertyName7) {
        this.propertyName7 = propertyName7;
        return this;
    }

    public String getPropertyName8() {
        return propertyName8;
    }

    public ProcessRulesEntity setPropertyName8(String propertyName8) {
        this.propertyName8 = propertyName8;
        return this;
    }

    public String getPropertyName9() {
        return propertyName9;
    }

    public ProcessRulesEntity setPropertyName9(String propertyName9) {
        this.propertyName9 = propertyName9;
        return this;
    }

    public String getPropertyName10() {
        return propertyName10;
    }

    public ProcessRulesEntity setPropertyName10(String propertyName10) {
        this.propertyName10 = propertyName10;
        return this;
    }

    public String getPropertyName11() {
        return propertyName11;
    }

    public ProcessRulesEntity setPropertyName11(String propertyName11) {
        this.propertyName11 = propertyName11;
        return this;
    }

    public String getPropertyName12() {
        return propertyName12;
    }

    public ProcessRulesEntity setPropertyName12(String propertyName12) {
        this.propertyName12 = propertyName12;
        return this;
    }

    public String getPropertyName13() {
        return propertyName13;
    }

    public ProcessRulesEntity setPropertyName13(String propertyName13) {
        this.propertyName13 = propertyName13;
        return this;
    }

    public String getPropertyName14() {
        return propertyName14;
    }

    public ProcessRulesEntity setPropertyName14(String propertyName14) {
        this.propertyName14 = propertyName14;
        return this;
    }

    public String getPropertyName15() {
        return propertyName15;
    }

    public ProcessRulesEntity setPropertyName15(String propertyName15) {
        this.propertyName15 = propertyName15;
        return this;
    }

    public String getPropertyName16() {
        return propertyName16;
    }

    public ProcessRulesEntity setPropertyName16(String propertyName16) {
        this.propertyName16 = propertyName16;
        return this;
    }

    public String getPropertyName17() {
        return propertyName17;
    }

    public ProcessRulesEntity setPropertyName17(String propertyName17) {
        this.propertyName17 = propertyName17;
        return this;
    }

    public String getPropertyName18() {
        return propertyName18;
    }

    public ProcessRulesEntity setPropertyName18(String propertyName18) {
        this.propertyName18 = propertyName18;
        return this;
    }

    public String getPropertyName19() {
        return propertyName19;
    }

    public ProcessRulesEntity setPropertyName19(String propertyName19) {
        this.propertyName19 = propertyName19;
        return this;
    }

    public String getPropertyName20() {
        return propertyName20;
    }

    public ProcessRulesEntity setPropertyName20(String propertyName20) {
        this.propertyName20 = propertyName20;
        return this;
    }

    public String getPropertyName21() {
        return propertyName21;
    }

    public ProcessRulesEntity setPropertyName21(String propertyName21) {
        this.propertyName21 = propertyName21;
        return this;
    }

    public String getPropertyName22() {
        return propertyName22;
    }

    public ProcessRulesEntity setPropertyName22(String propertyName22) {
        this.propertyName22 = propertyName22;
        return this;
    }

    public String getPropertyName23() {
        return propertyName23;
    }

    public ProcessRulesEntity setPropertyName23(String propertyName23) {
        this.propertyName23 = propertyName23;
        return this;
    }

    public String getPropertyName24() {
        return propertyName24;
    }

    public ProcessRulesEntity setPropertyName24(String propertyName24) {
        this.propertyName24 = propertyName24;
        return this;
    }

    public String getPropertyName25() {
        return propertyName25;
    }

    public ProcessRulesEntity setPropertyName25(String propertyName25) {
        this.propertyName25 = propertyName25;
        return this;
    }

    public String getProcessDocRef() {
        return processDocRef;
    }

    public ProcessRulesEntity setProcessDocRef(String processDocRef) {
        this.processDocRef = processDocRef;
        return this;
    }

    public Integer getSeqId() {
        return seqId;
    }

    public ProcessRulesEntity setSeqId(Integer seqId) {
        this.seqId = seqId;
        return this;
    }


}
