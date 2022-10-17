package com.pe.pcm.rule;


import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.rule.entity.RuleEntity;
import com.pe.pcm.workflow.ProcessRulesService;
import com.pe.pcm.workflow.entity.ProcessRulesEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class RuleServiceTest {

    @MockBean
    private RuleRepository ruleRepository;
    @MockBean
    private ProcessRulesService processRulesService;
    @InjectMocks
    private RuleService ruleService;

    @Test
    @DisplayName("Create a duplicate rule ")
    public void testCreate1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(ruleRepository.findByRuleName(Mockito.anyString())).thenReturn(Optional.of(getRuleEntity()));
            ruleService.create(generateRuleModel());
        });
        Mockito.verify(ruleRepository, Mockito.times(1)).findByRuleName(Mockito.anyString());
        Mockito.verify(ruleRepository, Mockito.never()).save(Mockito.any(RuleEntity.class));

    }

    @Test
    @DisplayName("Create a new rule")
    public void testCreate2() {
        Mockito.when(ruleRepository.findByRuleName(Mockito.anyString())).thenReturn(Optional.ofNullable(null));
        ruleService.create(generateRuleModel());
        Mockito.verify(ruleRepository, Mockito.times(1)).findByRuleName(Mockito.anyString());
        Mockito.verify(ruleRepository, Mockito.times(1)).save(Mockito.any(RuleEntity.class));
    }

    @Test
    @DisplayName("Get rule")
    public void testGetRule1() {
        Mockito.when(ruleRepository.findById(Mockito.anyString())).thenReturn(Optional.of(getRuleEntity()));
        final String ruleId = "123456";
        RuleModel ruleModel = ruleService.get(ruleId);
        Mockito.verify(ruleRepository, Mockito.times(1)).findById(Mockito.anyString());
        Assertions.assertEquals(ruleModel.getRuleId(), ruleId);
    }

    @Test
    @DisplayName("Get non-exist rule")
    public void testGetRule2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(ruleRepository.findById(Mockito.anyString())).thenReturn(Optional.ofNullable(null));
            final String ruleId = "123456";
            ruleService.get(ruleId);
            Mockito.verify(ruleRepository, Mockito.times(1)).findById(Mockito.anyString());
        });
    }

    @Test
    @DisplayName("Delete rule")
    public void testDeleteRule() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(processRulesService.findFirstByRuleId(Mockito.anyString())).thenReturn(getProcessRulesEntity());
            final String ruleId = "123456";
            ruleService.delete(ruleId);
        });
        Mockito.verify(processRulesService, Mockito.times(1)).findFirstByRuleId(Mockito.anyString());
        Mockito.verify(ruleRepository, Mockito.never()).findById(Mockito.anyString());
        Mockito.verify(ruleRepository, Mockito.never()).delete(Mockito.any());
    }

    @Test
    @DisplayName("Delete non-exist rule")
    public void testDeleteRule2() {
        Mockito.when(processRulesService.findFirstByRuleId(Mockito.anyString())).thenReturn(new ProcessRulesEntity());
        Mockito.when(ruleRepository.findById(Mockito.anyString())).thenReturn(Optional.of(getRuleEntity()));
        final String ruleId = "123456";
        ruleService.delete(ruleId);
        Mockito.verify(processRulesService, Mockito.times(1)).findFirstByRuleId(Mockito.anyString());
        Mockito.verify(ruleRepository, Mockito.times(1)).findById(Mockito.anyString());
        Mockito.verify(ruleRepository, Mockito.times(1)).delete(Mockito.any(RuleEntity.class));
    }


    @Test
    @DisplayName("Save import rule when no RuleName in input")
    public void testSaveRules2() {
        ArrayList<RuleEntity> list = new ArrayList<RuleEntity>() {{
            add(getRuleEntity());
        }};
        ArrayList<RuleModel> xmlList = new ArrayList<RuleModel>() {{
            add(generateRuleXmlModel());
        }};
        xmlList.get(0).setRuleName(null);
        Mockito.when(ruleRepository.findAll()).thenReturn(list);
        ruleService.saveRules(xmlList);
        Mockito.verify(ruleRepository, Mockito.never()).findAll();
        Mockito.verify(ruleRepository, Mockito.never()).save(Mockito.any(RuleEntity.class));
    }

    @Test
    @DisplayName("Get Rules List")
    public void test_getRulesList() {
        ruleService.getRulesList();
        Mockito.verify(ruleRepository, Mockito.times(1)).findAllByOrderByRuleName();
    }

    @Test
    @DisplayName("Find All")
    public void test_findAll() {
        ruleService.findAll();
        Mockito.verify(ruleRepository, Mockito.times(1)).findRulesEntitiesByOrderByRuleNameAsc();
    }

    @Test
    @DisplayName("Get Entity")
    public void test_getEntity() {
        ruleService.getEntity("pcmRule");
        Mockito.verify(ruleRepository, Mockito.times(1)).findByRuleName(Mockito.anyString());
    }

    @Test
    @DisplayName("Update Rule")
    public void test_update() {
        ruleService.update(generateRuleModel());
        Mockito.verify(ruleRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Search Rule")
    public void test_search() {
        Pageable pageable = Mockito.mock(Pageable.class);
        Mockito.when(ruleRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class))).thenReturn(generateList());
        ruleService.search(generateRuleModel(), pageable);
        Mockito.verify(ruleRepository, Mockito.times(1)).findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));
    }

    @Test
    @DisplayName("Find Rule By Name")
    public void test_findRuleByName() {
        Mockito.when(ruleRepository.findFirstByRuleName("ruleName")).thenReturn(Optional.of(getRuleEntity()));
        ruleService.findByName("ruleName");
        Mockito.verify(ruleRepository, Mockito.times(1)).findFirstByRuleName(Mockito.anyString());
    }

    @Test
    @DisplayName("Find Rule By Name")
    public void test_findRuleByName1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(ruleRepository.findFirstByRuleName("ruleName")).thenThrow(notFound("Rule not available in Main Rules List"));
            ruleService.findByName("ruleName");
        });
        Mockito.verify(ruleRepository, Mockito.times(1)).findFirstByRuleName(Mockito.anyString());
    }


    Page<RuleEntity> generateList() {
        List<RuleEntity> li = new ArrayList<>();
        Page<RuleEntity> pagedTasks = new PageImpl<>(li);
        li.add(getRuleEntity());
        return pagedTasks;
    }

    RuleModel generateRuleModel() {
        RuleModel ruleModel = new RuleModel();
        ruleModel.setRuleId("123456");
        ruleModel.setRuleName("pcmRule");
        ruleModel.setBusinessProcessId("businessProcess");
        ruleModel.setPropertyName1("propertyName1");
        ruleModel.setPropertyName2("propertyName2");
        ruleModel.setPropertyName3("propertyName3");
        ruleModel.setPropertyName4("propertyName4");
        ruleModel.setPropertyName5("propertyName5");
        ruleModel.setPropertyName6("propertyName6");
        ruleModel.setPropertyName7("propertyName7");
        ruleModel.setPropertyName8("propertyName8");
        ruleModel.setPropertyName9("propertyName9");
        ruleModel.setPropertyName10("propertyName10");
        ruleModel.setPropertyName11("propertyName11");
        ruleModel.setPropertyName12("propertyName12");
        ruleModel.setPropertyName13("propertyName13");
        ruleModel.setPropertyName14("propertyName14");
        ruleModel.setPropertyName15("propertyName15");
        return ruleModel;
    }

    RuleEntity getRuleEntity() {
        return new RuleEntity()
                .setRuleId("123456")
                .setRuleName("pcmRule")
                .setBusinessProcessId("businessProcess")
                .setPropertyName1("propertyName1")
                .setPropertyName2("propertyName2")
                .setPropertyName3("propertyName3")
                .setPropertyName4("propertyName4")
                .setPropertyName5("propertyName5")
                .setPropertyName6("propertyName6")
                .setPropertyName7("propertyName7")
                .setPropertyName8("propertyName8")
                .setPropertyName9("propertyName9")
                .setPropertyName10("propertyName10")
                .setPropertyName11("propertyName11")
                .setPropertyName12("propertyName12")
                .setPropertyName13("propertyName13")
                .setPropertyName14("propertyName14")
                .setPropertyName15("propertyName15");

    }

    ProcessRulesEntity getProcessRulesEntity() {
        ProcessRulesEntity processRulesEntity = new ProcessRulesEntity();
        processRulesEntity.setPkId("123456");
        processRulesEntity.setProcessDocRef("Process");
        processRulesEntity.setPropertyName1("Property1");
        return processRulesEntity;
    }

    RuleModel generateRuleXmlModel() {
        RuleModel ruleModel = new RuleModel();
        ruleModel.setRuleId("123456");
        ruleModel.setRuleName("pcmRule");
        ruleModel.setBusinessProcessId("businessProcess");
        ruleModel.setPropertyName1("propertyName1");
        ruleModel.setPropertyName2("propertyName2");
        ruleModel.setPropertyName3("propertyName3");
        ruleModel.setPropertyName4("propertyName4");
        ruleModel.setPropertyName5("propertyName5");
        ruleModel.setPropertyName6("propertyName6");
        ruleModel.setPropertyName7("propertyName7");
        ruleModel.setPropertyName8("propertyName8");
        ruleModel.setPropertyName9("propertyName9");
        ruleModel.setPropertyName10("propertyName10");
        ruleModel.setPropertyName11("propertyName11");
        ruleModel.setPropertyName12("propertyName12");
        ruleModel.setPropertyName13("propertyName13");
        ruleModel.setPropertyName14("propertyName14");
        ruleModel.setPropertyName15("propertyName15");
        return ruleModel;
    }


}
