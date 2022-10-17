package com.pe.pcm.workflow;


import com.pe.pcm.rule.RuleService;
import com.pe.pcm.rule.entity.RuleEntity;
import com.pe.pcm.workflow.entity.ProcessRulesEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class ProcessRuleServiceTest {
    @MockBean
    private ProcessRulesRepository processRulesRepository;
    @MockBean
    private RuleService ruleService;
    @InjectMocks
    private ProcessRulesService processRulesService;


    @Test
    @DisplayName("Save Process Rule")
    public void testSave() {
        Mockito.when(processRulesRepository.save(Mockito.any())).thenReturn(getProcessRulesEntity());
        processRulesService.save(getProcessRulesEntity());
        Mockito.verify(processRulesRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Save Process Rule1")
    public void testSave1() {
        Mockito.when(processRulesRepository.save(Mockito.any())).thenReturn(getProcessRulesEntity());
        Mockito.when(ruleService.findByName(Mockito.anyString())).thenReturn(getRuleEntity());
        processRulesService.save(getProcessRuleModel(), "1", 123);
        Mockito.verify(processRulesRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Find By Pk Ids")
    public void testFindByPkIds() {
        processRulesService.findByPkIds(getStrings());
        Mockito.verify(processRulesRepository, Mockito.times(1)).findAllByPkIdIn(Mockito.any());
    }

    @Test
    @DisplayName("Find By Pk Ids1")
    public void testFindByPkIds1() {
        Mockito.when(processRulesRepository.findAllByPkIdIn(Mockito.any())).thenReturn(Optional.of(getProcessRulesEntities()));
        processRulesService.findByPkIds(getStrings());
        Mockito.verify(processRulesRepository, Mockito.times(1)).findAllByPkIdIn(Mockito.any());
    }

    @Test
    @DisplayName("Find First ByRule Id")
    public void testFindFirstByRuleId() {
        Mockito.when(processRulesRepository.findFirstByRuleId(Mockito.anyString())).thenReturn(Optional.of(getProcessRulesEntity()));
        processRulesService.findFirstByRuleId("123456");
        Mockito.verify(processRulesRepository, Mockito.times(1)).findFirstByRuleId(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete All")
    public void testDeleteAll() {
        processRulesService.deleteAll(getStrings());
        Mockito.verify(processRulesRepository, Mockito.times(1)).deleteAllByPkIdIn(Mockito.anyList());
    }

    @Test
    @DisplayName("Find All")
    public void testFindAll() {
        Mockito.when(processRulesRepository.findAllByPkIdIn(Mockito.anyList())).thenReturn(Optional.of(getProcessRulesEntities()));
        processRulesService.findAll();
        Mockito.verify(processRulesRepository, Mockito.never()).findAllByPkIdIn(Mockito.anyList());
    }

    @Test
    @DisplayName("Find All1")
    public void testFindAll1() {
        Mockito.when(processRulesRepository.findAllByPkIdIn(Mockito.anyList())).thenReturn(Optional.of(new ArrayList<>()));
        processRulesService.findAll();
        Mockito.verify(processRulesRepository, Mockito.never()).findAllByPkIdIn(Mockito.anyList());
    }

    @Test
    @DisplayName("Save All")
    public void testSaeAll() {
        processRulesService.saveAll(getProcessRulesEntities());
        Mockito.verify(processRulesRepository, Mockito.times(1)).saveAll(Mockito.anyList());
    }

    ProcessRuleModel getProcessRuleModel() {
        ProcessRuleModel processRuleModel = new ProcessRuleModel();
        processRuleModel.setIndex(54);
        processRuleModel.setRuleName("rulename");
        processRuleModel.setPropertyValue1("123456");
        processRuleModel.setPropertyValue2("133");
        return processRuleModel;
    }

    ProcessRulesEntity getProcessRulesEntity() {
        ProcessRulesEntity processRulesEntity = new ProcessRulesEntity();
        processRulesEntity.setPkId("12345");
        processRulesEntity.setProcessDocRef("ProcessRef");
        return processRulesEntity;
    }

    RuleEntity getRuleEntity() {
        RuleEntity ruleEntity = new RuleEntity();
        ruleEntity.setRuleId("123456");
        ruleEntity.setRuleName("rulename");
        ruleEntity.setBusinessProcessId("123654");
        ruleEntity.setPropertyName1("prop1");
        return ruleEntity;
    }

    List<ProcessRulesEntity> getProcessRulesEntities() {
        List<ProcessRulesEntity> processRulesEntities = new ArrayList<>();
        processRulesEntities.add(getProcessRulesEntity());
        return processRulesEntities;
    }

    List<String> getStrings() {
        List<String> strings = new ArrayList<>();
        strings.add("123456");
        strings.add("654321");
        strings.add("320120");
        return strings;
    }

}
