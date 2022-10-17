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

package com.pe.pcm.rule;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.Serializable;
import java.util.StringJoiner;

@JacksonXmlRootElement(localName = "RULE")
@JsonPropertyOrder(value = {"ruleId", "ruleName", "businessProcessId",
        "propertyName1", "propertyName2", "propertyName3", "propertyName4",
        "propertyName5", "propertyName6", "propertyName7", "propertyName8",
        "propertyName9", "propertyName10", "propertyName11", "propertyName12",
        "propertyName13", "propertyName14", "propertyName15", "propertyName16",
        "propertyName17", "propertyName18", "propertyName19", "propertyName20",
        "propertyName21", "propertyName22", "propertyName23", "propertyName24",
        "propertyName25"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RuleModel implements Serializable {

    private static final long serialVersionUID = 1607568657405596380L;

    private String ruleId;
    private String ruleName;
    private String businessProcessId;
    private String propertyName1;
    private String propertyName2;
    private String propertyName3;
    private String propertyName4;
    private String propertyName5;
    private String propertyName6;
    private String propertyName7;
    private String propertyName8;
    private String propertyName9;
    private String propertyName10;
    private String propertyName11;
    private String propertyName12;
    private String propertyName13;
    private String propertyName14;
    private String propertyName15;
    private String propertyName16;
    private String propertyName17;
    private String propertyName18;
    private String propertyName19;
    private String propertyName20;
    private String propertyName21;
    private String propertyName22;
    private String propertyName23;
    private String propertyName24;
    private String propertyName25;

    @JacksonXmlProperty(localName = "RULE_ID")
    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    @JacksonXmlProperty(localName = "RULE_NAME")
    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    @JacksonXmlProperty(localName = "BUSINESS_PROCESS_ID")
    public String getBusinessProcessId() {
        return businessProcessId;
    }

    public void setBusinessProcessId(String businessProcessId) {
        this.businessProcessId = businessProcessId;
    }

    @JacksonXmlProperty(localName = "PROPERTY_NAME_1")
    public String getPropertyName1() {
        return propertyName1;
    }

    public void setPropertyName1(String propertyName1) {
        this.propertyName1 = propertyName1;
    }

    @JacksonXmlProperty(localName = "PROPERTY_NAME_2")
    public String getPropertyName2() {
        return propertyName2;
    }

    public void setPropertyName2(String propertyName2) {
        this.propertyName2 = propertyName2;
    }

    @JacksonXmlProperty(localName = "PROPERTY_NAME_3")
    public String getPropertyName3() {
        return propertyName3;
    }

    public void setPropertyName3(String propertyName3) {
        this.propertyName3 = propertyName3;
    }

    @JacksonXmlProperty(localName = "PROPERTY_NAME_4")
    public String getPropertyName4() {
        return propertyName4;
    }

    public void setPropertyName4(String propertyName4) {
        this.propertyName4 = propertyName4;
    }

    @JacksonXmlProperty(localName = "PROPERTY_NAME_5")
    public String getPropertyName5() {
        return propertyName5;
    }

    public void setPropertyName5(String propertyName5) {
        this.propertyName5 = propertyName5;
    }

    @JacksonXmlProperty(localName = "PROPERTY_NAME_6")
    public String getPropertyName6() {
        return propertyName6;
    }

    public void setPropertyName6(String propertyName6) {
        this.propertyName6 = propertyName6;
    }

    @JacksonXmlProperty(localName = "PROPERTY_NAME_7")
    public String getPropertyName7() {
        return propertyName7;
    }

    public void setPropertyName7(String propertyName7) {
        this.propertyName7 = propertyName7;
    }

    @JacksonXmlProperty(localName = "PROPERTY_NAME_8")
    public String getPropertyName8() {
        return propertyName8;
    }

    public void setPropertyName8(String propertyName8) {
        this.propertyName8 = propertyName8;
    }

    @JacksonXmlProperty(localName = "PROPERTY_NAME_9")
    public String getPropertyName9() {
        return propertyName9;
    }

    public void setPropertyName9(String propertyName9) {
        this.propertyName9 = propertyName9;
    }

    @JacksonXmlProperty(localName = "PROPERTY_NAME_10")
    public String getPropertyName10() {
        return propertyName10;
    }

    public void setPropertyName10(String propertyName10) {
        this.propertyName10 = propertyName10;
    }

    @JacksonXmlProperty(localName = "PROPERTY_NAME_11")
    public String getPropertyName11() {
        return propertyName11;
    }

    public void setPropertyName11(String propertyName11) {
        this.propertyName11 = propertyName11;
    }

    @JacksonXmlProperty(localName = "PROPERTY_NAME_12")
    public String getPropertyName12() {
        return propertyName12;
    }

    public void setPropertyName12(String propertyName12) {
        this.propertyName12 = propertyName12;
    }

    @JacksonXmlProperty(localName = "PROPERTY_NAME_13")
    public String getPropertyName13() {
        return propertyName13;
    }

    public void setPropertyName13(String propertyName13) {
        this.propertyName13 = propertyName13;
    }

    @JacksonXmlProperty(localName = "PROPERTY_NAME_14")
    public String getPropertyName14() {
        return propertyName14;
    }

    public void setPropertyName14(String propertyName14) {
        this.propertyName14 = propertyName14;
    }

    @JacksonXmlProperty(localName = "PROPERTY_NAME_15")
    public String getPropertyName15() {
        return propertyName15;
    }

    public void setPropertyName15(String propertyName15) {
        this.propertyName15 = propertyName15;
    }

    @JacksonXmlProperty(localName = "PROPERTY_NAME_16")
    public String getPropertyName16() {
        return propertyName16;
    }

    public void setPropertyName16(String propertyName16) {
        this.propertyName16 = propertyName16;
    }

    @JacksonXmlProperty(localName = "PROPERTY_NAME_17")
    public String getPropertyName17() {
        return propertyName17;
    }

    public void setPropertyName17(String propertyName17) {
        this.propertyName17 = propertyName17;
    }

    @JacksonXmlProperty(localName = "PROPERTY_NAME_18")
    public String getPropertyName18() {
        return propertyName18;
    }

    public void setPropertyName18(String propertyName18) {
        this.propertyName18 = propertyName18;
    }

    @JacksonXmlProperty(localName = "PROPERTY_NAME_19")
    public String getPropertyName19() {
        return propertyName19;
    }

    public void setPropertyName19(String propertyName19) {
        this.propertyName19 = propertyName19;
    }

    @JacksonXmlProperty(localName = "PROPERTY_NAME_20")
    public String getPropertyName20() {
        return propertyName20;
    }

    public void setPropertyName20(String propertyName20) {
        this.propertyName20 = propertyName20;
    }

    @JacksonXmlProperty(localName = "PROPERTY_NAME_21")
    public String getPropertyName21() {
        return propertyName21;
    }

    public void setPropertyName21(String propertyName21) {
        this.propertyName21 = propertyName21;
    }

    @JacksonXmlProperty(localName = "PROPERTY_NAME_22")
    public String getPropertyName22() {
        return propertyName22;
    }

    public void setPropertyName22(String propertyName22) {
        this.propertyName22 = propertyName22;
    }

    @JacksonXmlProperty(localName = "PROPERTY_NAME_23")
    public String getPropertyName23() {
        return propertyName23;
    }

    public void setPropertyName23(String propertyName23) {
        this.propertyName23 = propertyName23;
    }

    @JacksonXmlProperty(localName = "PROPERTY_NAME_24")
    public String getPropertyName24() {
        return propertyName24;
    }

    public void setPropertyName24(String propertyName24) {
        this.propertyName24 = propertyName24;
    }

    @JacksonXmlProperty(localName = "PROPERTY_NAME_25")
    public String getPropertyName25() {
        return propertyName25;
    }

    public void setPropertyName25(String propertyName25) {
        this.propertyName25 = propertyName25;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RuleModel.class.getSimpleName() + "[", "]")
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
