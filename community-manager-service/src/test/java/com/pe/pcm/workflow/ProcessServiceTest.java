package com.pe.pcm.workflow;


import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.workflow.entity.ProcessEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class ProcessServiceTest {
    @MockBean
    private ProcessRepository processRepository;

    //@InjectMocks
    private ProcessService processService;

    @BeforeEach
    void inIt() {
        processService = new ProcessService(processRepository);
    }

    @Test
    @DisplayName("Search By Tp PkId And App PkId")
    public void testSearchByTpPkIdAndAppPkId() {
        Mockito.when(processRepository.findAllByPartnerProfileAndApplicationProfile(Mockito.anyString(), Mockito.anyString())).thenReturn(Optional.of(getProcessEntities()));
        processService.searchByTpPkIdAndAppPkId("1", "2");
        Mockito.verify(processRepository, Mockito.times(1)).findAllByPartnerProfileAndApplicationProfile(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Search By Tp PkId")
    public void testSearchByTpPkId() {
        Mockito.when(processRepository.findAllByPartnerProfile(Mockito.anyString())).thenReturn(Optional.of(getProcessEntities()));
        processService.searchByTpPkId("1234456");
        Mockito.verify(processRepository, Mockito.times(1)).findAllByPartnerProfile(Mockito.anyString());
    }

    @Test
    @DisplayName("Save Process Entity")
    public void testSave() {
        Mockito.when(processRepository.save(Mockito.any())).thenReturn(getProcessEntity());
        processService.save(getProcessEntity());
        Mockito.verify(processRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Save Process Entity1")
    public void testSave1() {
        Mockito.when(processRepository.save(Mockito.any())).thenReturn(getProcessEntity());
        processService.save("1", generateProcessModel(), "2", "3");
        Mockito.verify(processRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Find By Partner ProfileIn")
    public void testFindByPartnerProfileIn() {
        Mockito.when(processRepository.findByPartnerProfileIn(Mockito.anyList())).thenReturn(Optional.of(getProcessEntities()));
        processService.findByPartnerProfileIn(getStrings());
        Mockito.verify(processRepository, Mockito.times(1)).findByPartnerProfileIn(Mockito.anyList());
    }

    @Test
    @DisplayName("Find ById")
    public void testFindById() {
        Mockito.when(processRepository.findById(Mockito.anyString())).thenReturn(Optional.of(getProcessEntity()));
        processService.findById("123");
        Mockito.verify(processRepository, Mockito.times(1)).findById(Mockito.anyString());
    }

    @Test
    @DisplayName("Find ById1")
    public void testFindById1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(processRepository.findById(Mockito.anyString())).thenThrow(notFound("process record"));
            processService.findById("1234");
        });
        Mockito.verify(processRepository, Mockito.times(1)).findById(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete All")
    public void testDeleteAll() {
        processService.deleteAll(getStrings());
        Mockito.verify(processRepository, Mockito.times(1)).deleteAllBySeqIdIn(Mockito.anyList());
    }

    @Test
    @DisplayName("Find By Partner Profile")
    public void testFindByPartnerProfile() {
        Mockito.when(processRepository.findByPartnerProfile(Mockito.anyString())).thenReturn(Optional.of(getProcessEntities()));
        processService.findByPartnerProfile("12345");
        Mockito.verify(processRepository, Mockito.times(1)).findByPartnerProfile(Mockito.anyString());
    }

    @Test
    @DisplayName("Find By Application Profile")
    public void testFindByApplicationProfile() {
        Mockito.when(processRepository.findByApplicationProfile(Mockito.anyString())).thenReturn(Optional.of(getProcessEntities()));
        processService.findByApplicationProfile("1234");
        Mockito.verify(processRepository, Mockito.times(1)).findByApplicationProfile(Mockito.anyString());
    }

    ProcessEntity getProcessEntity() {
        ProcessEntity processEntity = new ProcessEntity();
        processEntity.setFlow("12345");
        processEntity.setSeqId("14343");
        processEntity.setApplicationProfile("ApplicationProfile");
        return processEntity;
    }

    List<ProcessEntity> getProcessEntities() {
        List<ProcessEntity> processEntities = new ArrayList<>();
        processEntities.add(getProcessEntity());
        return processEntities;
    }

    ProcessModel generateProcessModel() {
        ProcessModel processModel = new ProcessModel();
        processModel.setFlow("123456");
        processModel.setSeqType("Sequence");
        return processModel;
    }

    List<String> getStrings() {
        List<String> strings = new ArrayList<>();
        return strings;
    }
}
