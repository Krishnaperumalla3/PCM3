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

package com.pe.pcm.workflow;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.pe.pcm.annotations.constraint.Required;

import java.io.Serializable;
import java.util.StringJoiner;

@JacksonXmlRootElement(localName = "PROCESS_RULE")
@JsonPropertyOrder({"ruleId", "ruleName", "propertyValue1",
        "propertyValue2", "propertyValue3", "propertyValue4", "propertyValue5",
        "propertyValue6", "propertyValue7", "propertyValue8", "propertyValue9",
        "propertyValue10", "propertyValue11", "propertyValue12", "propertyValue13",
        "propertyValue14", "propertyValue15", "propertyValue16", "propertyValue17",
        "propertyValue18", "propertyValue19", "propertyValue20", "propertyValue21",
        "propertyValue22", "propertyValue23", "propertyValue24", "propertyValue25"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcessRuleModel implements Serializable {

    private static final long serialVersionUID = 2146980848570611517L;
    @Required(customMessage = "ruleId")
    private String ruleId;
    @Required(customMessage = "ruleName")
    private String ruleName;
    private String propertyValue1;
    private String propertyValue2;
    private String propertyValue3;
    private String propertyValue4;
    private String propertyValue5;
    private String propertyValue6;
    private String propertyValue7;
    private String propertyValue8;
    private String propertyValue9;
    private String propertyValue10;
    private String propertyValue11;
    private String propertyValue12;
    private String propertyValue13;
    private String propertyValue14;
    private String propertyValue15;
    private String propertyValue16;
    private String propertyValue17;
    private String propertyValue18;
    private String propertyValue19;
    private String propertyValue20;
    private String propertyValue21;
    private String propertyValue22;
    private String propertyValue23;
    private String propertyValue24;
    private String propertyValue25;
    private int seqId;
    private int index;


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

    @JacksonXmlProperty(localName = "PROPERTY_VALUE_1")
    public String getPropertyValue1() {
        return propertyValue1;
    }

    public void setPropertyValue1(String propertyValue1) {
        this.propertyValue1 = propertyValue1;
    }

    @JacksonXmlProperty(localName = "PROPERTY_VALUE_2")
    public String getPropertyValue2() {
        return propertyValue2;
    }

    public void setPropertyValue2(String propertyValue2) {
        this.propertyValue2 = propertyValue2;
    }

    @JacksonXmlProperty(localName = "PROPERTY_VALUE_3")
    public String getPropertyValue3() {
        return propertyValue3;
    }

    public void setPropertyValue3(String propertyValue3) {
        this.propertyValue3 = propertyValue3;
    }

    @JacksonXmlProperty(localName = "PROPERTY_VALUE_4")
    public String getPropertyValue4() {
        return propertyValue4;
    }

    public void setPropertyValue4(String propertyValue4) {
        this.propertyValue4 = propertyValue4;
    }

    @JacksonXmlProperty(localName = "PROPERTY_VALUE_5")
    public String getPropertyValue5() {
        return propertyValue5;
    }

    public void setPropertyValue5(String propertyValue5) {
        this.propertyValue5 = propertyValue5;
    }

    @JacksonXmlProperty(localName = "PROPERTY_VALUE_6")
    public String getPropertyValue6() {
        return propertyValue6;
    }

    public void setPropertyValue6(String propertyValue6) {
        this.propertyValue6 = propertyValue6;
    }

    @JacksonXmlProperty(localName = "PROPERTY_VALUE_7")
    public String getPropertyValue7() {
        return propertyValue7;
    }

    public void setPropertyValue7(String propertyValue7) {
        this.propertyValue7 = propertyValue7;
    }

    @JacksonXmlProperty(localName = "PROPERTY_VALUE_8")
    public String getPropertyValue8() {
        return propertyValue8;
    }

    public void setPropertyValue8(String propertyValue8) {
        this.propertyValue8 = propertyValue8;
    }

    @JacksonXmlProperty(localName = "PROPERTY_VALUE_9")
    public String getPropertyValue9() {
        return propertyValue9;
    }

    public void setPropertyValue9(String propertyValue9) {
        this.propertyValue9 = propertyValue9;
    }

    @JacksonXmlProperty(localName = "PROPERTY_VALUE_10")
    public String getPropertyValue10() {
        return propertyValue10;
    }

    public void setPropertyValue10(String propertyValue10) {
        this.propertyValue10 = propertyValue10;
    }

    @JacksonXmlProperty(localName = "PROPERTY_VALUE_11")
    public String getPropertyValue11() {
        return propertyValue11;
    }

    public void setPropertyValue11(String propertyValue11) {
        this.propertyValue11 = propertyValue11;
    }

    @JacksonXmlProperty(localName = "PROPERTY_VALUE_12")
    public String getPropertyValue12() {
        return propertyValue12;
    }

    public void setPropertyValue12(String propertyValue12) {
        this.propertyValue12 = propertyValue12;
    }

    @JacksonXmlProperty(localName = "PROPERTY_VALUE_13")
    public String getPropertyValue13() {
        return propertyValue13;
    }

    public void setPropertyValue13(String propertyValue13) {
        this.propertyValue13 = propertyValue13;
    }

    @JacksonXmlProperty(localName = "PROPERTY_VALUE_14")
    public String getPropertyValue14() {
        return propertyValue14;
    }

    public void setPropertyValue14(String propertyValue14) {
        this.propertyValue14 = propertyValue14;
    }

    @JacksonXmlProperty(localName = "PROPERTY_VALUE_15")
    public String getPropertyValue15() {
        return propertyValue15;
    }

    public void setPropertyValue15(String propertyValue15) {
        this.propertyValue15 = propertyValue15;
    }

    @JacksonXmlProperty(localName = "PROPERTY_VALUE_16")
    public String getPropertyValue16() {
        return propertyValue16;
    }

    public void setPropertyValue16(String propertyValue16) {
        this.propertyValue16 = propertyValue16;
    }

    @JacksonXmlProperty(localName = "PROPERTY_VALUE_17")
    public String getPropertyValue17() {
        return propertyValue17;
    }

    public void setPropertyValue17(String propertyValue17) {
        this.propertyValue17 = propertyValue17;
    }

    @JacksonXmlProperty(localName = "PROPERTY_VALUE_18")
    public String getPropertyValue18() {
        return propertyValue18;
    }

    public void setPropertyValue18(String propertyValue18) {
        this.propertyValue18 = propertyValue18;
    }

    @JacksonXmlProperty(localName = "PROPERTY_VALUE_19")
    public String getPropertyValue19() {
        return propertyValue19;
    }

    public void setPropertyValue19(String propertyValue19) {
        this.propertyValue19 = propertyValue19;
    }

    @JacksonXmlProperty(localName = "PROPERTY_VALUE_20")
    public String getPropertyValue20() {
        return propertyValue20;
    }

    public void setPropertyValue20(String propertyValue20) {
        this.propertyValue20 = propertyValue20;
    }


    @JacksonXmlProperty(localName = "PROPERTY_VALUE_21")
    public String getPropertyValue21() {
        return propertyValue21;
    }

    public void setPropertyValue21(String propertyValue21) {
        this.propertyValue21 = propertyValue21;
    }

    @JacksonXmlProperty(localName = "PROPERTY_VALUE_22")
    public String getPropertyValue22() {
        return propertyValue22;
    }

    public void setPropertyValue22(String propertyValue22) {
        this.propertyValue22 = propertyValue22;
    }

    @JacksonXmlProperty(localName = "PROPERTY_VALUE_23")
    public String getPropertyValue23() {
        return propertyValue23;
    }

    public void setPropertyValue23(String propertyValue23) {
        this.propertyValue23 = propertyValue23;
    }

    @JacksonXmlProperty(localName = "PROPERTY_VALUE_24")
    public String getPropertyValue24() {
        return propertyValue24;
    }

    public void setPropertyValue24(String propertyValue24) {
        this.propertyValue24 = propertyValue24;
    }

    @JacksonXmlProperty(localName = "PROPERTY_VALUE_25")
    public String getPropertyValue25() {
        return propertyValue25;
    }

    public void setPropertyValue25(String propertyValue25) {
        this.propertyValue25 = propertyValue25;
    }

    public int getSeqId() {
        return seqId;
    }

    public ProcessRuleModel setSeqId(int seqId) {
        this.seqId = seqId;
        return this;
    }

    public int getIndex() {
        return index;
    }

    public ProcessRuleModel setIndex(int index) {
        this.index = index;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ProcessRuleModel.class.getSimpleName() + "[", "]")
                .add("ruleId='" + ruleId + "'")
                .add("ruleName='" + ruleName + "'")
                .add("propertyValue1='" + propertyValue1 + "'")
                .add("propertyValue2='" + propertyValue2 + "'")
                .add("propertyValue3='" + propertyValue3 + "'")
                .add("propertyValue4='" + propertyValue4 + "'")
                .add("propertyValue5='" + propertyValue5 + "'")
                .add("propertyValue6='" + propertyValue6 + "'")
                .add("propertyValue7='" + propertyValue7 + "'")
                .add("propertyValue8='" + propertyValue8 + "'")
                .add("propertyValue9='" + propertyValue9 + "'")
                .add("propertyValue10='" + propertyValue10 + "'")
                .add("propertyValue11='" + propertyValue11 + "'")
                .add("propertyValue12='" + propertyValue12 + "'")
                .add("propertyValue13='" + propertyValue13 + "'")
                .add("propertyValue14='" + propertyValue14 + "'")
                .add("propertyValue15='" + propertyValue15 + "'")
                .add("propertyValue16='" + propertyValue16 + "'")
                .add("propertyValue17='" + propertyValue17 + "'")
                .add("propertyValue18='" + propertyValue18 + "'")
                .add("propertyValue19='" + propertyValue19 + "'")
                .add("propertyValue20='" + propertyValue20 + "'")
                .add("propertyValue21='" + propertyValue21 + "'")
                .add("propertyValue22='" + propertyValue22 + "'")
                .add("propertyValue23='" + propertyValue23 + "'")
                .add("propertyValue24='" + propertyValue24 + "'")
                .add("propertyValue25='" + propertyValue25 + "'")
                .add("seqId=" + seqId)
                .add("index=" + index)
                .toString();
    }
}
