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
import com.pe.pcm.rule.RuleService;
import com.pe.pcm.utils.KeyGeneratorUtil;
import com.pe.pcm.utils.PCMConstants;
import com.pe.pcm.workflow.entity.ProcessRulesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author Chenchu Kiran.
 */
@Service
public class ProcessRulesService {

    private ProcessRulesRepository processRulesRepository;
    private RuleService ruleService;

    @Autowired
    public ProcessRulesService(ProcessRulesRepository processRulesRepository,
                               RuleService ruleService) {
        this.processRulesRepository = processRulesRepository;
        this.ruleService = ruleService;
    }

    private Function<ProcessRuleModel, ProcessRulesEntity> serialize = ruleBean ->
            new ProcessRulesEntity()
                    .setRuleName(ruleBean.getRuleName())
                    .setRuleId(ruleService.findByName(ruleBean.getRuleName()).getRuleId())
                    .setPropertyName1(ruleBean.getPropertyValue1())
                    .setPropertyName2(ruleBean.getPropertyValue2())
                    .setPropertyName3(ruleBean.getPropertyValue3())
                    .setPropertyName4(ruleBean.getPropertyValue4())
                    .setPropertyName5(ruleBean.getPropertyValue5())
                    .setPropertyName6(ruleBean.getPropertyValue6())
                    .setPropertyName7(ruleBean.getPropertyValue7())
                    .setPropertyName8(ruleBean.getPropertyValue8())
                    .setPropertyName9(ruleBean.getPropertyValue9())
                    .setPropertyName10(ruleBean.getPropertyValue10())
                    .setPropertyName11(ruleBean.getPropertyValue11())
                    .setPropertyName12(ruleBean.getPropertyValue12())
                    .setPropertyName13(ruleBean.getPropertyValue13())
                    .setPropertyName14(ruleBean.getPropertyValue14())
                    .setPropertyName15(ruleBean.getPropertyValue15())
                    .setPropertyName16(ruleBean.getPropertyValue16())
                    .setPropertyName17(ruleBean.getPropertyValue17())
                    .setPropertyName18(ruleBean.getPropertyValue18())
                    .setPropertyName19(ruleBean.getPropertyValue19())
                    .setPropertyName20(ruleBean.getPropertyValue20())
                    .setPropertyName21(ruleBean.getPropertyValue21())
                    .setPropertyName22(ruleBean.getPropertyValue22())
                    .setPropertyName23(ruleBean.getPropertyValue23())
                    .setPropertyName24(ruleBean.getPropertyValue24())
                    .setPropertyName25(ruleBean.getPropertyValue25());


    public ProcessRulesEntity save(ProcessRulesEntity processRulesEntity) {
        return processRulesRepository.save(processRulesEntity);
    }

    public ProcessRulesEntity save(ProcessRuleModel processRuleModel, String processDocRef, int seqId) {
        String primaryKey = KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.PROCESS_RULES_PKEY_PRE_APPEND,
                PCMConstants.PROCESS_RULES_PKEY_RANDOM_COUNT);
        return processRulesRepository.save(serialize.apply(processRuleModel)
                .setPkId(primaryKey)
                .setProcessDocRef(processDocRef)
                .setSeqId(seqId));
    }

    public ProcessRulesEntity findById(String pkId) {
        return processRulesRepository.findById(pkId).orElseThrow(() -> GlobalExceptionHandler.notFound("Rule"));
    }

    List<ProcessRulesEntity> findByPkIds(List<String> pkIds) {
        return processRulesRepository.findAllByPkIdIn(pkIds).orElse(new ArrayList<>());
    }

    public ProcessRulesEntity findFirstByRuleId(String ruleId) {
        return processRulesRepository.findFirstByRuleId(ruleId).orElse(new ProcessRulesEntity());
    }

    void deleteAll(List<String> ruleIds) {
        if (ruleIds != null && !ruleIds.isEmpty()) {
            processRulesRepository.deleteAllByPkIdIn(ruleIds);
        }
    }

    public List<ProcessRulesEntity> findAll() {
        return processRulesRepository.findAllByOrderByPkId().orElse(new ArrayList<>());
    }

    @Transactional
    public void saveAll(List<ProcessRulesEntity> processRulesEntities) {
        processRulesRepository.saveAll(processRulesEntities);
    }

}
