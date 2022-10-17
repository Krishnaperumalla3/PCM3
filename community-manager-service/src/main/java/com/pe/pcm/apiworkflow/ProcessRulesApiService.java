package com.pe.pcm.apiworkflow;

import com.pe.pcm.apiworkflow.entity.ProcessApiRuleEntity;
import com.pe.pcm.rule.RuleService;
import com.pe.pcm.utils.KeyGeneratorUtil;
import com.pe.pcm.utils.PCMConstants;
import com.pe.pcm.workflow.ProcessRuleModel;
import com.pe.pcm.workflow.entity.ProcessRulesEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author Kiran Reddy.
 */
@Service
public class ProcessRulesApiService {

    private final ProcessRulesApiRepository processRulesApiRepository;
    private RuleService ruleService;

    @Autowired
    public ProcessRulesApiService(ProcessRulesApiRepository processRulesApiRepository, RuleService ruleService) {
        this.processRulesApiRepository = processRulesApiRepository;
        this.ruleService = ruleService;
    }

    public ProcessApiRuleEntity save(ProcessRuleModel processRuleModel, String processDocRef, int seqId) {
        String primaryKey = KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.PROCESS_RULES_PKEY_PRE_APPEND,
                PCMConstants.PROCESS_RULES_PKEY_RANDOM_COUNT);
        return processRulesApiRepository.save(serialize.apply(processRuleModel)
                .setPkId(primaryKey)
                .setProcessDocRef(processDocRef)
                .setSeqId(seqId));
    }

    List<ProcessApiRuleEntity> findByPkIds(List<String> pkIds) {
        return processRulesApiRepository.findAllByPkIdIn(pkIds).orElse(new ArrayList<>());
    }

    void deleteAll(List<String> ruleIds) {
        if (ruleIds != null && !ruleIds.isEmpty()) {
            processRulesApiRepository.deleteAllByPkIdIn(ruleIds);
        }
    }

    private Function<ProcessRuleModel, ProcessApiRuleEntity> serialize = ruleBean ->
            new ProcessApiRuleEntity()
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
}
