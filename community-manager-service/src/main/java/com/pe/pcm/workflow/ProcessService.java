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

import com.pe.pcm.workflow.entity.ProcessEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;

/**
 * @author Kiran Reddy.
 */
@Service
public class ProcessService {

    private final ProcessRepository processRepository;

    @Autowired
    public ProcessService(ProcessRepository processRepository) {
        this.processRepository = processRepository;
    }

    List<ProcessEntity> searchByTpPkIdAndAppPkId(String tpPkId, String appPkId) {
        return processRepository
                .findAllByPartnerProfileAndApplicationProfile(tpPkId, appPkId).orElse(new ArrayList<>());
    }

    List<ProcessEntity> searchByTpPkId(String tpPkId) {
        return processRepository.findAllByPartnerProfile(tpPkId).orElse(Collections.emptyList());
    }

    public ProcessEntity save(ProcessEntity processEntity) {
        return processRepository.save(processEntity);
    }

    public ProcessEntity save(String primaryKey, ProcessModel processModel, String partnerProfileId, String applicationProfileId) {
        ProcessEntity processEntity = new ProcessEntity();
        processEntity.setSeqId(primaryKey);
        processEntity.setPartnerProfile(partnerProfileId);
        processEntity.setApplicationProfile(applicationProfileId);
        processEntity.setFlow(processModel.getFlow());
        processEntity.setSeqType(processModel.getSeqType());
        return processRepository.save(processEntity);
    }

    List<ProcessEntity> findByPartnerProfileIn(List<String> partnerProfilesList) {
        return processRepository.findByPartnerProfileIn(partnerProfilesList).orElse(new ArrayList<>());
    }

    public ProcessEntity findById(String seqId) {
        return processRepository.findById(seqId).orElseThrow(() -> notFound("Process record"));
    }

    void deleteAll(List<String> seqIds) {
        processRepository.deleteAllBySeqIdIn(seqIds);
    }

    public List<ProcessEntity> findByPartnerProfile(String pkId) {
        return processRepository.findByPartnerProfile(pkId).orElse(new ArrayList<>());
    }

    public List<ProcessEntity> findByApplicationProfile(String pkId) {
        return processRepository.findByApplicationProfile(pkId).orElse(new ArrayList<>());
    }

    public List<ProcessEntity> findByApplicationProfilesIn(List<String> pkIds) {
        return processRepository.findByApplicationProfileIn(pkIds).orElse(new ArrayList<>());
    }

    void delete(ProcessEntity processEntity) {
        processRepository.delete(processEntity);
    }

}
