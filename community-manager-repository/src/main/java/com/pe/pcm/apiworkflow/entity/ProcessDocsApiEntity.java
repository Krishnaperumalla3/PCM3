package com.pe.pcm.apiworkflow.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Kiran Reddy.
 */
@Entity
@Table(name = "PETPE_PROCESSDOCS_API")
public class ProcessDocsApiEntity implements Serializable {

    @Id
    private String pkId;
    private String processRef;
    private String methodName;
    private String filter;
    private String description;
    private String processRuleSeq;

    public String getPkId() {
        return pkId;
    }

    public ProcessDocsApiEntity setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getProcessRef() {
        return processRef;
    }

    public ProcessDocsApiEntity setProcessRef(String processRef) {
        this.processRef = processRef;
        return this;
    }

    public String getMethodName() {
        return methodName;
    }

    public ProcessDocsApiEntity setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public String getFilter() {
        return filter;
    }

    public ProcessDocsApiEntity setFilter(String filter) {
        this.filter = filter;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProcessDocsApiEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getProcessRuleSeq() {
        return processRuleSeq;
    }

    public ProcessDocsApiEntity setProcessRuleSeq(String processRuleSeq) {
        this.processRuleSeq = processRuleSeq;
        return this;
    }
}
