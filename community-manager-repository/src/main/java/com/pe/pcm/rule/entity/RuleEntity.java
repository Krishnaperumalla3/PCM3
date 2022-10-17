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

package com.pe.pcm.rule.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.StringJoiner;

@Entity
@Table(name = "PETPE_RULES")
public class RuleEntity implements Serializable {

    @Id
    private String ruleId;

    @NotNull
    private String ruleName;

    @NotNull
    private String businessProcessId;

    @Column(name = "property_name_1")
    @JsonProperty(value = "propertyName_1")
    private String propertyName1;

    @Column(name = "property_name_2")
    @JsonProperty(value = "propertyName_2")
    private String propertyName2;

    @Column(name = "property_name_3")
    @JsonProperty(value = "propertyName_3")
    private String propertyName3;

    @Column(name = "property_name_4")
    @JsonProperty(value = "propertyName_4")
    private String propertyName4;

    @Column(name = "property_name_5")
    @JsonProperty(value = "propertyName_5")
    private String propertyName5;

    @Column(name = "property_name_6")
    @JsonProperty(value = "propertyName_6")
    private String propertyName6;

    @Column(name = "property_name_7")
    @JsonProperty(value = "propertyName_7")
    private String propertyName7;

    @Column(name = "property_name_8")
    @JsonProperty(value = "propertyName_8")
    private String propertyName8;

    @Column(name = "property_name_9")
    @JsonProperty(value = "propertyName_9")
    private String propertyName9;

    @Column(name = "property_name_10")
    @JsonProperty(value = "propertyName_10")
    private String propertyName10;

    @Column(name = "property_name_11")
    @JsonProperty(value = "propertyName_11")
    private String propertyName11;

    @Column(name = "property_name_12")
    @JsonProperty(value = "propertyName_12")
    private String propertyName12;

    @Column(name = "property_name_13")
    @JsonProperty(value = "propertyName_13")
    private String propertyName13;

    @Column(name = "property_name_14")
    @JsonProperty(value = "propertyName_14")
    private String propertyName14;

    @Column(name = "property_name_15")
    @JsonProperty(value = "propertyName_15")
    private String propertyName15;

    @Column(name = "property_name_16")
    @JsonProperty(value = "propertyName_16")
    private String propertyName16;

    @Column(name = "property_name_17")
    @JsonProperty(value = "propertyName_17")
    private String propertyName17;

    @Column(name = "property_name_18")
    @JsonProperty(value = "propertyName_18")
    private String propertyName18;

    @Column(name = "property_name_19")
    @JsonProperty(value = "propertyName_19")
    private String propertyName19;

    @Column(name = "property_name_20")
    @JsonProperty(value = "propertyName_20")
    private String propertyName20;

    @Column(name = "property_name_21")
    @JsonProperty(value = "propertyName_21")
    private String propertyName21;

    @Column(name = "property_name_22")
    @JsonProperty(value = "propertyName_22")
    private String propertyName22;

    @Column(name = "property_name_23")
    @JsonProperty(value = "propertyName_23")
    private String propertyName23;

    @Column(name = "property_name_24")
    @JsonProperty(value = "propertyName_24")
    private String propertyName24;

    @Column(name = "property_name_25")
    @JsonProperty(value = "propertyName_25")
    private String propertyName25;

    public String getRuleId() {
        return ruleId;
    }

    public RuleEntity setRuleId(String ruleId) {
        this.ruleId = ruleId;
        return this;
    }

    public String getRuleName() {
        return ruleName;
    }

    public RuleEntity setRuleName(String ruleName) {
        this.ruleName = ruleName;
        return this;
    }

    public String getBusinessProcessId() {
        return businessProcessId;
    }

    public RuleEntity setBusinessProcessId(String businessProcessId) {
        this.businessProcessId = businessProcessId;
        return this;
    }

    public String getPropertyName1() {
        return propertyName1;
    }

    public RuleEntity setPropertyName1(String propertyName1) {
        this.propertyName1 = propertyName1;
        return this;
    }

    public String getPropertyName2() {
        return propertyName2;
    }

    public RuleEntity setPropertyName2(String propertyName2) {
        this.propertyName2 = propertyName2;
        return this;
    }

    public String getPropertyName3() {
        return propertyName3;
    }

    public RuleEntity setPropertyName3(String propertyName3) {
        this.propertyName3 = propertyName3;
        return this;
    }

    public String getPropertyName4() {
        return propertyName4;
    }

    public RuleEntity setPropertyName4(String propertyName4) {
        this.propertyName4 = propertyName4;
        return this;
    }

    public String getPropertyName5() {
        return propertyName5;
    }

    public RuleEntity setPropertyName5(String propertyName5) {
        this.propertyName5 = propertyName5;
        return this;
    }

    public String getPropertyName6() {
        return propertyName6;
    }

    public RuleEntity setPropertyName6(String propertyName6) {
        this.propertyName6 = propertyName6;
        return this;
    }

    public String getPropertyName7() {
        return propertyName7;
    }

    public RuleEntity setPropertyName7(String propertyName7) {
        this.propertyName7 = propertyName7;
        return this;
    }

    public String getPropertyName8() {
        return propertyName8;
    }

    public RuleEntity setPropertyName8(String propertyName8) {
        this.propertyName8 = propertyName8;
        return this;
    }

    public String getPropertyName9() {
        return propertyName9;
    }

    public RuleEntity setPropertyName9(String propertyName9) {
        this.propertyName9 = propertyName9;
        return this;
    }

    public String getPropertyName10() {
        return propertyName10;
    }

    public RuleEntity setPropertyName10(String propertyName10) {
        this.propertyName10 = propertyName10;
        return this;
    }

    public String getPropertyName11() {
        return propertyName11;
    }

    public RuleEntity setPropertyName11(String propertyName11) {
        this.propertyName11 = propertyName11;
        return this;
    }

    public String getPropertyName12() {
        return propertyName12;
    }

    public RuleEntity setPropertyName12(String propertyName12) {
        this.propertyName12 = propertyName12;
        return this;
    }

    public String getPropertyName13() {
        return propertyName13;
    }

    public RuleEntity setPropertyName13(String propertyName13) {
        this.propertyName13 = propertyName13;
        return this;
    }

    public String getPropertyName14() {
        return propertyName14;
    }

    public RuleEntity setPropertyName14(String propertyName14) {
        this.propertyName14 = propertyName14;
        return this;
    }

    public String getPropertyName15() {
        return propertyName15;
    }

    public RuleEntity setPropertyName15(String propertyName15) {
        this.propertyName15 = propertyName15;
        return this;
    }

    public String getPropertyName16() {
        return propertyName16;
    }

    public RuleEntity setPropertyName16(String propertyName16) {
        this.propertyName16 = propertyName16;
        return this;
    }

    public String getPropertyName17() {
        return propertyName17;
    }

    public RuleEntity setPropertyName17(String propertyName17) {
        this.propertyName17 = propertyName17;
        return this;
    }

    public String getPropertyName18() {
        return propertyName18;
    }

    public RuleEntity setPropertyName18(String propertyName18) {
        this.propertyName18 = propertyName18;
        return this;
    }

    public String getPropertyName19() {
        return propertyName19;
    }

    public RuleEntity setPropertyName19(String propertyName19) {
        this.propertyName19 = propertyName19;
        return this;
    }

    public String getPropertyName20() {
        return propertyName20;
    }

    public RuleEntity setPropertyName20(String propertyName20) {
        this.propertyName20 = propertyName20;
        return this;
    }

    public String getPropertyName21() {
        return propertyName21;
    }

    public RuleEntity setPropertyName21(String propertyName21) {
        this.propertyName21 = propertyName21;
        return this;
    }

    public String getPropertyName22() {
        return propertyName22;
    }

    public RuleEntity setPropertyName22(String propertyName22) {
        this.propertyName22 = propertyName22;
        return this;
    }

    public String getPropertyName23() {
        return propertyName23;
    }

    public RuleEntity setPropertyName23(String propertyName23) {
        this.propertyName23 = propertyName23;
        return this;
    }

    public String getPropertyName24() {
        return propertyName24;
    }

    public RuleEntity setPropertyName24(String propertyName24) {
        this.propertyName24 = propertyName24;
        return this;
    }

    public String getPropertyName25() {
        return propertyName25;
    }

    public RuleEntity setPropertyName25(String propertyName25) {
        this.propertyName25 = propertyName25;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RuleEntity.class.getSimpleName() + "[", "]")
                .add("ruleId='" + ruleId + "'")
                .add("ruleName='" + ruleName + "'")
                .add("businessProcessId='" + businessProcessId + "'")
                .add("propertyName1='" + propertyName1 + "'")
                .add("propertyName2='" + propertyName2 + "'")
                .add("propertyName3='" + propertyName3 + "'")
                .add("propertyName4='" + propertyName4 + "'")
                .add("propertyName5='" + propertyName5 + "'")
                .add("propertyName6='" + propertyName6 + "'")
                .add("propertyName7='" + propertyName7 + "'")
                .add("propertyName8='" + propertyName8 + "'")
                .add("propertyName9='" + propertyName9 + "'")
                .add("propertyName10='" + propertyName10 + "'")
                .add("propertyName11='" + propertyName11 + "'")
                .add("propertyName12='" + propertyName12 + "'")
                .add("propertyName13='" + propertyName13 + "'")
                .add("propertyName14='" + propertyName14 + "'")
                .add("propertyName15='" + propertyName15 + "'")
                .add("propertyName16='" + propertyName16 + "'")
                .add("propertyName17='" + propertyName17 + "'")
                .add("propertyName18='" + propertyName18 + "'")
                .add("propertyName19='" + propertyName19 + "'")
                .add("propertyName20='" + propertyName20 + "'")
                .add("propertyName21='" + propertyName21 + "'")
                .add("propertyName22='" + propertyName22 + "'")
                .add("propertyName23='" + propertyName23 + "'")
                .add("propertyName24='" + propertyName24 + "'")
                .add("propertyName25='" + propertyName25 + "'")
                .toString();
    }
}
