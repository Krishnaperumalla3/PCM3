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

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.pe.pcm.rule.RuleModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@JacksonXmlRootElement(localName = "RULES_SETUP")
@JsonPropertyOrder(value = {"partnerInfoModel", "applicationInfoModel", "rulesList", "processList"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkFlowModel implements Serializable {

    private static final long serialVersionUID = 4311020219609364229L;

    private PartnerInfoModel partnerInfoModel;

    private ApplicationInfoModel applicationInfoModel;

    private List<RuleModel> rulesList = new ArrayList<>();

    private List<ProcessModel> processList = new ArrayList<>();



    @JacksonXmlElementWrapper(localName = "RULES")
    @JacksonXmlProperty(localName = "RULE")
    public List<RuleModel> getRulesList() {
        return rulesList;
    }

    public void setRulesList(List<RuleModel> rulesList) {
        this.rulesList = rulesList;
    }

    @JacksonXmlElementWrapper(localName = "PROCESS_CONTAINER")
    @JacksonXmlProperty(localName = "PROCESS")
    public List<ProcessModel> getProcessList() {
        return processList;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @JacksonXmlProperty(localName = "PARTNER_DETAILS")
    public PartnerInfoModel getPartnerInfoModel() {
        return partnerInfoModel;
    }

    public void setPartnerInfoModel(PartnerInfoModel partnerInfoModel) {
        this.partnerInfoModel = partnerInfoModel;
    }


    @JacksonXmlProperty(localName = "APPLICATION_DETAILS")
    public ApplicationInfoModel getApplicationInfoModel() {
        return applicationInfoModel;
    }

    public WorkFlowModel setApplicationInfoModel(ApplicationInfoModel applicationInfoModel) {
        this.applicationInfoModel = applicationInfoModel;
        return this;
    }

    public void setProcessList(List<ProcessModel> processList) {
        this.processList = processList;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", WorkFlowModel.class.getSimpleName() + "[", "]")
                .add("partnerInfoModel=" + partnerInfoModel)
                .add("rulesList=" + rulesList)
                .add("processList=" + processList)
                .toString();
    }
}
