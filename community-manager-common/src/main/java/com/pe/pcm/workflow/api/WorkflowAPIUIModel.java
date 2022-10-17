package com.pe.pcm.workflow.api;

import com.pe.pcm.annotations.constraint.Required;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kiran Reddy.
 */
public class WorkflowAPIUIModel implements Serializable {

    private static final long serialVersionUID = 4311020219609364229L;

    @Required(customMessage = "apiName")
    private String apiName;
    private String seqId;
    private List<ProcessDocApiModel> processDocApiModel;

    public String getApiName() {
        return apiName;
    }

    public WorkflowAPIUIModel setApiName(String apiName) {
        this.apiName = apiName;
        return this;
    }

    public String getSeqId() {
        return seqId;
    }

    public WorkflowAPIUIModel setSeqId(String seqId) {
        this.seqId = seqId;
        return this;
    }

    public List<ProcessDocApiModel> getProcessDocApiModel() {
        if (processDocApiModel == null) {
            return new ArrayList<>();
        }
        return processDocApiModel;
    }

    public WorkflowAPIUIModel setProcessDocApiModel(List<ProcessDocApiModel> processDocApiModel) {
        this.processDocApiModel = processDocApiModel;
        return this;
    }
}
