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

package com.pe.pcm.miscellaneous;

import com.pe.pcm.reports.ReportRepository;
import com.pe.pcm.workflow.ProcessDocsService;
import com.pe.pcm.workflow.ProcessRulesService;
import com.pe.pcm.workflow.entity.ProcessRulesEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.PCMConstants.COMMA;

/**
 * @author Kiran Reddy.
 */
@Service
public class ManageAdminActionsService {

    private final ProcessDocsService processDocsService;
    private final ProcessRulesService processRulesService;
    private final ReportRepository reportRepository;

    @Autowired
    public ManageAdminActionsService(ProcessDocsService processDocsService,
                                     ProcessRulesService processRulesService,
                                     ReportRepository reportRepository) {
        this.processDocsService = processDocsService;
        this.processRulesService = processRulesService;
        this.reportRepository = reportRepository;
    }

    @Transactional
    public void manageWorkFlow() {
        Map<String, ProcessRulesEntity> processRulesEntityMap = new HashMap<>();
        List<ProcessRulesEntity> processRulesEntities = new ArrayList<>();
        processRulesService.findAll()
                .forEach(processRulesEntity -> {
                    ProcessRulesEntity prEntity = new ProcessRulesEntity();
                    BeanUtils.copyProperties(processRulesEntity, prEntity);
                    processRulesEntityMap.put(processRulesEntity.getPkId(), prEntity);
                });

        processDocsService.findAll().forEach(processDocsEntity -> {
            List<String> processRulesRefs =
                    isNotNull(processDocsEntity.getProcessRuleseq()) ? Arrays.asList(processDocsEntity.getProcessRuleseq().split(COMMA)) : new ArrayList<>();
            int ruleSequence = 0;
            for (String processRuleRef : processRulesRefs) {
                ProcessRulesEntity processRulesEntityClone = processRulesEntityMap.get(processRuleRef);
                if (processRulesEntityClone != null && isNotNull(processRulesEntityClone.getPkId())) {
                    processRulesEntities.add(processRulesEntityClone.setSeqId(++ruleSequence).setProcessDocRef(processDocsEntity.getPkId()));
                }
            }
        });
        processRulesService.saveAll(processRulesEntities);
    }

    @Transactional
    public String purgeTransactions(Long days) {
        return reportRepository.purgeTransactions(days);
    }

}
