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

package com.pe.pcm.rule;

import com.pe.pcm.common.CommunityManagerKeyValueModel;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.rule.entity.RuleEntity;
import com.pe.pcm.utils.KeyGeneratorUtil;
import com.pe.pcm.utils.PCMConstants;
import com.pe.pcm.workflow.ProcessRulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.pe.pcm.exception.GlobalExceptionHandler.*;
import static com.pe.pcm.miscellaneous.CommonQueryPredicate.getPredicate;
import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.CommonFunctions.isNullThrowError;

/**
 * @author Shameer.
 */
@Service
public class RuleService {

    private RuleRepository ruleRepository;
    private ProcessRulesService processRulesService;

    @Autowired
    public RuleService(RuleRepository ruleRepository, @Lazy ProcessRulesService processRulesService) {
        this.ruleRepository = ruleRepository;
        this.processRulesService = processRulesService;
    }

    @Transactional
    public RuleEntity create(RuleModel ruleModel) {
        if (!isNotNull(ruleModel.getRuleName())) {
            throw badRequest("rule name");
        }
        return createRule(ruleModel);
    }

    @Transactional
    public RuleEntity update(RuleModel ruleModel) {
        return ruleRepository.save(serialize.apply(ruleModel));
    }

    @Transactional
    public void delete(String ruleId) {
        if (isNotNull(processRulesService.findFirstByRuleId(ruleId).getPkId())) {
            throw internalServerError("Unable to delete this Rule, Because this Rule Used in Workflow.");
        }
        ruleRepository.findById(ruleId).ifPresent(ruleEntity -> ruleRepository.delete(ruleEntity));
    }

    public void delete(RuleEntity ruleEntity) {
        ruleRepository.delete(ruleEntity);
    }

    private RuleEntity createRule(RuleModel ruleModel) {
        ruleRepository.findByRuleName(ruleModel.getRuleName())
                .ifPresent(ruleEntity -> {
                    throw conflict("Rule");
                });
        RuleEntity ruleEntity = serialize.apply(ruleModel);
        ruleEntity.setRuleId(KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.RULES_PKEY_PRE_APPEND, PCMConstants.RULES_PKEY_RANDOM_COUNT));
        return ruleRepository.save(ruleEntity);
    }

    public RuleModel get(String ruleId) {
        return ruleRepository
                .findById(ruleId)
                .map(ruleEntity -> mapperToModel.apply(ruleEntity))
                .orElseThrow(() -> notFound("Rule"));
    }

    public Page<RuleEntity> search(RuleModel ruleModel, Pageable pageable) {

        Page<RuleEntity> ruleEntityList = ruleRepository.findAll((Specification<RuleEntity>) (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            getPredicate(root, cb, predicates, ruleModel.getRuleId(), "ruleId", true);
            getPredicate(root, cb, predicates, ruleModel.getRuleName(), "ruleName", true);
            getPredicate(root, cb, predicates, ruleModel.getBusinessProcessId(), "businessProcessId", true);
            return cb.and(predicates.toArray(new Predicate[0]));
        }, pageable);

        return new PageImpl<>(new ArrayList<>(ruleEntityList.getContent()), pageable, ruleEntityList.getTotalElements());
    }

    private Function<RuleModel, RuleEntity> serializeXml = ruleModel -> {
        RuleEntity ruleEntity = new RuleEntity();
        ruleEntity.setRuleName(ruleModel.getRuleName());
        ruleEntity.setBusinessProcessId(ruleModel.getBusinessProcessId());
        ruleEntity.setPropertyName1(ruleModel.getPropertyName1());
        ruleEntity.setPropertyName2(ruleModel.getPropertyName2());
        ruleEntity.setPropertyName3(ruleModel.getPropertyName3());
        ruleEntity.setPropertyName4(ruleModel.getPropertyName4());
        ruleEntity.setPropertyName5(ruleModel.getPropertyName5());
        ruleEntity.setPropertyName6(ruleModel.getPropertyName6());
        ruleEntity.setPropertyName7(ruleModel.getPropertyName7());
        ruleEntity.setPropertyName8(ruleModel.getPropertyName8());
        ruleEntity.setPropertyName9(ruleModel.getPropertyName9());
        ruleEntity.setPropertyName10(ruleModel.getPropertyName10());
        ruleEntity.setPropertyName11(ruleModel.getPropertyName11());
        ruleEntity.setPropertyName12(ruleModel.getPropertyName12());
        ruleEntity.setPropertyName13(ruleModel.getPropertyName13());
        ruleEntity.setPropertyName14(ruleModel.getPropertyName14());
        ruleEntity.setPropertyName15(ruleModel.getPropertyName15());
        ruleEntity.setPropertyName16(ruleModel.getPropertyName16());
        ruleEntity.setPropertyName17(ruleModel.getPropertyName17());
        ruleEntity.setPropertyName18(ruleModel.getPropertyName18());
        ruleEntity.setPropertyName19(ruleModel.getPropertyName19());
        ruleEntity.setPropertyName20(ruleModel.getPropertyName20());
        ruleEntity.setPropertyName21(ruleModel.getPropertyName21());
        ruleEntity.setPropertyName22(ruleModel.getPropertyName22());
        ruleEntity.setPropertyName23(ruleModel.getPropertyName23());
        ruleEntity.setPropertyName24(ruleModel.getPropertyName24());
        ruleEntity.setPropertyName25(ruleModel.getPropertyName25());
        return ruleEntity;
    };


    private Function<RuleModel, RuleEntity> serialize = ruleModel -> {
        RuleEntity ruleEntity = new RuleEntity();
        ruleEntity.setRuleId(ruleModel.getRuleId());
        ruleEntity.setRuleName(isNullThrowError.apply(ruleModel.getRuleName(), "Rule Name"));
        ruleEntity.setBusinessProcessId(ruleModel.getBusinessProcessId());
        ruleEntity.setPropertyName1(ruleModel.getPropertyName1());
        ruleEntity.setPropertyName2(ruleModel.getPropertyName2());
        ruleEntity.setPropertyName3(ruleModel.getPropertyName3());
        ruleEntity.setPropertyName4(ruleModel.getPropertyName4());
        ruleEntity.setPropertyName5(ruleModel.getPropertyName5());
        ruleEntity.setPropertyName6(ruleModel.getPropertyName6());
        ruleEntity.setPropertyName7(ruleModel.getPropertyName7());
        ruleEntity.setPropertyName8(ruleModel.getPropertyName8());
        ruleEntity.setPropertyName9(ruleModel.getPropertyName9());
        ruleEntity.setPropertyName10(ruleModel.getPropertyName10());
        ruleEntity.setPropertyName11(ruleModel.getPropertyName11());
        ruleEntity.setPropertyName12(ruleModel.getPropertyName12());
        ruleEntity.setPropertyName13(ruleModel.getPropertyName13());
        ruleEntity.setPropertyName14(ruleModel.getPropertyName14());
        ruleEntity.setPropertyName15(ruleModel.getPropertyName15());
        ruleEntity.setPropertyName16(ruleModel.getPropertyName16());
        ruleEntity.setPropertyName17(ruleModel.getPropertyName17());
        ruleEntity.setPropertyName18(ruleModel.getPropertyName18());
        ruleEntity.setPropertyName19(ruleModel.getPropertyName19());
        ruleEntity.setPropertyName20(ruleModel.getPropertyName20());
        ruleEntity.setPropertyName21(ruleModel.getPropertyName21());
        ruleEntity.setPropertyName22(ruleModel.getPropertyName22());
        ruleEntity.setPropertyName23(ruleModel.getPropertyName23());
        ruleEntity.setPropertyName24(ruleModel.getPropertyName24());
        ruleEntity.setPropertyName25(ruleModel.getPropertyName25());
        return ruleEntity;
    };


    private Function<RuleEntity, RuleModel> mapperToModel = ruleEntity -> {
        RuleModel ruleModel = new RuleModel();
        ruleModel.setRuleId(ruleEntity.getRuleId());
        ruleModel.setRuleName(ruleEntity.getRuleName());
        ruleModel.setBusinessProcessId(ruleEntity.getBusinessProcessId());
        ruleModel.setPropertyName1(ruleEntity.getPropertyName1());
        ruleModel.setPropertyName2(ruleEntity.getPropertyName2());
        ruleModel.setPropertyName3(ruleEntity.getPropertyName3());
        ruleModel.setPropertyName4(ruleEntity.getPropertyName4());
        ruleModel.setPropertyName5(ruleEntity.getPropertyName5());
        ruleModel.setPropertyName6(ruleEntity.getPropertyName6());
        ruleModel.setPropertyName7(ruleEntity.getPropertyName7());
        ruleModel.setPropertyName8(ruleEntity.getPropertyName8());
        ruleModel.setPropertyName9(ruleEntity.getPropertyName9());
        ruleModel.setPropertyName10(ruleEntity.getPropertyName10());
        ruleModel.setPropertyName11(ruleEntity.getPropertyName11());
        ruleModel.setPropertyName12(ruleEntity.getPropertyName12());
        ruleModel.setPropertyName13(ruleEntity.getPropertyName13());
        ruleModel.setPropertyName14(ruleEntity.getPropertyName14());
        ruleModel.setPropertyName15(ruleEntity.getPropertyName15());
        ruleModel.setPropertyName16(ruleEntity.getPropertyName16());
        ruleModel.setPropertyName17(ruleEntity.getPropertyName17());
        ruleModel.setPropertyName18(ruleEntity.getPropertyName18());
        ruleModel.setPropertyName19(ruleEntity.getPropertyName19());
        ruleModel.setPropertyName20(ruleEntity.getPropertyName20());
        ruleModel.setPropertyName21(ruleEntity.getPropertyName21());
        ruleModel.setPropertyName22(ruleEntity.getPropertyName22());
        ruleModel.setPropertyName23(ruleEntity.getPropertyName23());
        ruleModel.setPropertyName24(ruleEntity.getPropertyName24());
        ruleModel.setPropertyName25(ruleEntity.getPropertyName25());
        return ruleModel;
    };

    public List<RuleEntity> findAll() {
        return ruleRepository.findRulesEntitiesByOrderByRuleNameAsc().orElse(new ArrayList<>());
    }

    public Optional<RuleEntity> getEntity(String ruleName) {
        return ruleRepository.findByRuleName(ruleName);
    }

    public RuleEntity findByName(String name) {
        Optional<RuleEntity> optionalRuleEntity = ruleRepository.findFirstByRuleName(name);
        if (optionalRuleEntity.isPresent()) {
            return optionalRuleEntity.get();
        } else {
            throw GlobalExceptionHandler.internalServerError("Applied rule not available in Main Rules List, Rule Name : " + name);
        }
    }

    public void saveRules(List<RuleModel> ruleModelList) {
        List<String> rulesSet = getDistinctRuleNames();
        ruleModelList.forEach(ruleModel -> {
            if (isNotNull(ruleModel.getRuleName()) && !rulesSet.contains(ruleModel.getRuleName())) {
                saveRule(ruleModel);
            }
        });
    }

    public void saveAll(List<RuleEntity> ruleEntities) {
        ruleRepository.saveAll(ruleEntities);
    }

    private void saveRule(RuleModel ruleModel) {
        RuleEntity ruleEntity = serializeXml.apply(ruleModel);
        String primaryKey = KeyGeneratorUtil.getPrimaryKey.apply(PCMConstants.TP_PKEY_PRE_APPEND,
                PCMConstants.TP_PKEY_RANDOM_COUNT);
        ruleEntity.setRuleId(primaryKey);
        ruleRepository.save(ruleEntity);
    }

    public List<RuleEntity> getRulesList() {
        return ruleRepository.findAllByOrderByRuleName().orElse(new ArrayList<>());
    }

    private List<String> getDistinctRuleNames() {
        return ruleRepository.findDistinctRuleNames().orElse(new ArrayList<>());
    }


    public List<RuleModel> getRulesModel() {
        return getRulesList().stream()
                .map(ruleEntity -> mapperToModel.apply(ruleEntity))
                .collect(Collectors.toList());
    }


}
