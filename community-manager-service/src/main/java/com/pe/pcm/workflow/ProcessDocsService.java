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

import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.workflow.entity.ProcessDocsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Chenchu Kiran.
 */
@Service
public class ProcessDocsService {

    private ProcessDocsRepository processDocsRepository;

    @Autowired
    public ProcessDocsService(ProcessDocsRepository processDocsRepository) {
        this.processDocsRepository = processDocsRepository;
    }

    List<ProcessDocsEntity> searchByProcessRef(String processRef) {
        return processDocsRepository.findByProcessRef(processRef).orElse(Collections.emptyList());
    }

    public ProcessDocsEntity save(ProcessDocsEntity processDocsEntity) {
        return processDocsRepository.save(processDocsEntity);
    }

    public ProcessDocsEntity save(ProcessDocModel processDocModel, String primaryKey, List<String> ruleSeqList, String processPkId) {
        ProcessDocsEntity processDocsEntity = new ProcessDocsEntity();
        processDocsEntity.setPkId(primaryKey);
        processDocsEntity.setProcessRef(processPkId);
        processDocsEntity.setProcessRuleseq(String.join(",", ruleSeqList));
        processDocsEntity.setFilenamePattern(processDocModel.getFileNamePattern());
        processDocsEntity.setDoctype(processDocModel.getDocType());
        processDocsEntity.setVersion(processDocModel.getVersionNo());
        processDocsEntity.setPartnerid(processDocModel.getPartnerId());
        processDocsEntity.setReciverid(processDocModel.getReceiverId());
        processDocsEntity.setDoctrans(processDocModel.getDocTrans());
        return processDocsRepository.save(processDocsEntity);
    }

    List<ProcessDocsEntity> findAllByProcessRefIn(List<String> processRefList) {
        return processDocsRepository.findAllByProcessRefIn(processRefList).orElse(new ArrayList<>());
    }

    public ProcessDocsEntity findById(String pkId) {
        return processDocsRepository.findById(pkId).orElse(new ProcessDocsEntity());
    }

    ProcessDocsEntity findByIdThrowIfNotFound(String pkId) {
        return processDocsRepository.findById(pkId).orElseThrow(() -> GlobalExceptionHandler.notFound("Process Doc Entity, pkId : " + pkId));
    }

    void deleteAll(List<String> processRefs) {
        processDocsRepository.deleteAllByProcessRefIn(processRefs);
    }

    void delete(ProcessDocsEntity processDocsEntity) {
        processDocsRepository.delete(processDocsEntity);
    }

    public List<ProcessDocsEntity> findAll() {
        return processDocsRepository.findAllByOrderByPkId().orElse(new ArrayList<>());
    }

    List<ProcessDocsEntity> findAllByDocHandling(ProcessDocModel processDocModel) {
        return processDocsRepository.findAllByDoctypeAndDoctransAndPartneridAndReciverid(processDocModel.getDocType(), processDocModel.getDocTrans(), processDocModel.getPartnerId(), processDocModel.getReceiverId()).orElse(new ArrayList<>());
    }

    Iterable<ProcessDocsEntity> findAllByIds(List<String> processDocsIds) {
        return processDocsRepository.findAllById(processDocsIds);
    }
}
