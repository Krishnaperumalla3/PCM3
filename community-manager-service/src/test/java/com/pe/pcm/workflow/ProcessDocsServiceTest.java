package com.pe.pcm.workflow;


import com.pe.pcm.workflow.entity.ProcessDocsEntity;
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
public class ProcessDocsServiceTest {
    @MockBean
    private ProcessDocsRepository processDocsRepository;

    @InjectMocks
    private ProcessDocsService processDocsService;

    @Test
    @DisplayName("Find All")
    public void testFindAll() {
        Mockito.when(processDocsRepository.findAllByOrderByPkId()).thenReturn(Optional.of(getProcessDocsEntityList()));
        processDocsService.findAll();
        Mockito.verify(processDocsRepository, Mockito.times(1)).findAllByOrderByPkId();
    }

    @Test
    @DisplayName("Find All 1")
    public void testFindAll1() {
        Mockito.when(processDocsRepository.findAllByOrderByPkId()).thenReturn(Optional.of(new ArrayList<>()));
        processDocsService.findAll();
        Mockito.verify(processDocsRepository, Mockito.times(1)).findAllByOrderByPkId();
    }

    @Test
    @DisplayName("Delete All")
    public void testDeleteAll() {
        processDocsService.deleteAll(getStrings());
        Mockito.verify(processDocsRepository, Mockito.times(1)).deleteAllByProcessRefIn(Mockito.anyList());
    }

    @Test
    @DisplayName("Find By Id")
    public void testFindById() {
        processDocsService.findById("123456");
        Mockito.verify(processDocsRepository, Mockito.times(1)).findById(Mockito.anyString());
    }

    @Test
    @DisplayName("Find by Id1")
    public void testFindById1() {
        Mockito.when(processDocsRepository.findById(Mockito.anyString())).thenReturn(Optional.of(getProcessDocsEntity()));
        processDocsService.findById("123456");
        Mockito.verify(processDocsRepository, Mockito.times(1)).findById(Mockito.anyString());
    }

    @Test
    @DisplayName("FindAll By Process RefIn")
    public void testFindAllByProcessRefIn() {
        processDocsService.findAllByProcessRefIn(getStrings());
        Mockito.verify(processDocsRepository, Mockito.times(1)).findAllByProcessRefIn(Mockito.anyList());
    }

    @Test
    @DisplayName("Save Process Docs")
    public void testFindAllByProcessRefIn1() {
        Mockito.when(processDocsRepository.save(Mockito.any())).thenReturn(getProcessDocsEntity());
        processDocsService.save(generateProcessDocModel(), "1", getStrings(), "1234");
        Mockito.verify(processDocsRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Save Process Docs1")
    public void testSave1() {
        Mockito.when(processDocsRepository.save(Mockito.any())).thenReturn(getProcessDocsEntity());
        processDocsService.save(getProcessDocsEntity());
        Mockito.verify(processDocsRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Search By Process Ref")
    public void testSearchByProcessRef() {
        processDocsService.searchByProcessRef(Mockito.anyString());
        Mockito.verify(processDocsRepository, Mockito.times(1)).findByProcessRef(Mockito.any());
    }

    ProcessDocsEntity getProcessDocsEntity() {
        ProcessDocsEntity processDocsEntity = new ProcessDocsEntity();
        processDocsEntity.setDoctrans("Doctrans");
        processDocsEntity.setDoctype("Doctype");
        processDocsEntity.setPkId("123456");
        processDocsEntity.setPartnerid("PartnerId");
        return processDocsEntity;
    }

    List<ProcessDocsEntity> getProcessDocsEntityList() {
        List<ProcessDocsEntity> processDocsEntities = new ArrayList<>();
        processDocsEntities.add(getProcessDocsEntity());
        return processDocsEntities;
    }

    List<String> getStrings() {
        List<String> strings = new ArrayList<>();
        return strings;
    }

    ProcessDocModel generateProcessDocModel() {
        ProcessDocModel processDocModel = new ProcessDocModel();
        processDocModel.setPartnerId("123456");
        processDocModel.setDocType("DocType");
        processDocModel.setIndex(125);
        processDocModel.setReceiverId("1243");
        return processDocModel;
    }
}
