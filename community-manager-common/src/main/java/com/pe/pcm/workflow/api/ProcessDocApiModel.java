package com.pe.pcm.workflow.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pe.pcm.annotations.constraint.Required;
import com.pe.pcm.workflow.ProcessRuleModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kiran Reddy.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProcessDocApiModel implements Serializable {

    private String pkId;
    private int index;
    @Required(customMessage = "methodName")
    private String methodName;
    private String filter;
    private String description;
    private List<ProcessRuleModel> processRulesList;

    public String getPkId() {
        return pkId;
    }

    public ProcessDocApiModel setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public int getIndex() {
        return index;
    }

    public ProcessDocApiModel setIndex(int index) {
        this.index = index;
        return this;
    }

    public String getMethodName() {
        return methodName;
    }

    public ProcessDocApiModel setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public String getFilter() {
        return filter;
    }

    public ProcessDocApiModel setFilter(String filter) {
        this.filter = filter;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProcessDocApiModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<ProcessRuleModel> getProcessRulesList() {
        if (processRulesList == null) {
            return new ArrayList<>();
        }
        return processRulesList;
    }

    public ProcessDocApiModel setProcessRulesList(List<ProcessRuleModel> processRulesList) {
        this.processRulesList = processRulesList;
        return this;
    }

    @Override
    public String toString() {
        return "ProcessDocApiModel{" +
                "pkId='" + pkId + '\'' +
                ", index=" + index +
                ", methodName='" + methodName + '\'' +
                ", filter='" + filter + '\'' +
                ", description='" + description + '\'' +
                ", processRulesList=" + processRulesList +
                '}';
    }
}
