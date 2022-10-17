package com.pe.pcm.apiworkflow;

import com.pe.pcm.apiworkflow.entity.ProcessDocsApiEntity;
import com.pe.pcm.workflow.ProcessDocModel;
import com.pe.pcm.workflow.api.ProcessDocApiModel;
import com.pe.pcm.workflow.entity.ProcessDocsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

/**
 * @author Kiran Reddy.
 */
@Service
public class ProcessDocsApiService {

    private final ProcessDocsApiRepository processDocsApiRepository;

    @Autowired
    public ProcessDocsApiService(ProcessDocsApiRepository processDocsApiRepository) {
        this.processDocsApiRepository = processDocsApiRepository;
    }

    @Transactional
    void delete(List<ProcessDocsApiEntity> pdList) {
        processDocsApiRepository.deleteAll(pdList);
    }

    public ProcessDocsApiEntity save(ProcessDocApiModel processDocApiModel, String primaryKey, List<String> ruleSeqList, String processPkId) {
        ProcessDocsApiEntity processDocsApiEntity = new ProcessDocsApiEntity();
        processDocsApiEntity.setPkId(primaryKey);
        processDocsApiEntity.setFilter(processDocApiModel.getFilter());
        processDocsApiEntity.setDescription(processDocApiModel.getDescription());
        processDocsApiEntity.setMethodName(processDocApiModel.getMethodName());
        processDocsApiEntity.setProcessRef(processPkId);
        processDocsApiEntity.setProcessRuleSeq(String.join(",", ruleSeqList));
        return processDocsApiRepository.save(processDocsApiEntity);
    }

    List<ProcessDocsApiEntity> searchByProcessRef(String processRef) {
        return processDocsApiRepository.findAllByProcessRef(processRef).orElse(Collections.emptyList());
    }

}
