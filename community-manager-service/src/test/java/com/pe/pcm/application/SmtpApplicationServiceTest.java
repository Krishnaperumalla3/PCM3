package com.pe.pcm.application;


import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.application.entity.ApplicationEntity;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.SmtpModel;
import com.pe.pcm.protocol.SmtpService;
import com.pe.pcm.protocol.smtp.entity.SmtpEntity;
import com.pe.pcm.workflow.ProcessService;
import com.pe.pcm.workflow.entity.ProcessEntity;
import org.junit.jupiter.api.Assertions;
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

import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class SmtpApplicationServiceTest {

    @MockBean
    private ApplicationService applicationService;
    @MockBean
    private ActivityHistoryService activityHistoryService;
    @MockBean
    private SmtpService smtpService;
    @MockBean
    private ProcessService processService;
    @MockBean
    private ManageProtocolService manageProtocolService;
    @InjectMocks
    private SmtpApplicationService smtpApplicationService;


    @Test
    @DisplayName(value = "Check Duplicate Application")
    void duplicateApplicationTest() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.find(Mockito.anyString())).thenReturn(Optional.of(getApplicationEntity()));
            smtpApplicationService.duplicateApplication(Mockito.anyString());
        });
        Mockito.verify(applicationService, Mockito.times(1)).find(Mockito.anyString());
        Assertions.assertEquals(CommunityManagerServiceException.class, CommunityManagerServiceException.class);
        Assertions.assertNotEquals(GlobalExceptionHandler.conflict("Application"), CommunityManagerServiceException.class);
    }

    @Test
    @DisplayName("Save Smtp Application with an unknown protocol")
    void testSave() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            smtpApplicationService.save(generateSmtpModel("SMTP1"));
        });
        Mockito.verify(applicationService, Mockito.never()).find(Mockito.anyString());
        Mockito.verify(smtpService, Mockito.never()).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.never()).saveApplicationActivity(Mockito.anyString(), Mockito.anyString());
    }


    @Test
    @DisplayName("Create a Smtp Application with an known protocol")
    void testCreate1() {
        smtpApplicationService.save(generateSmtpModel("SMTP"));
        Mockito.verify(smtpService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).saveApplicationActivity(Mockito.anyString(), Mockito.anyString());

    }

    @Test
    @DisplayName("Save Smtp Application with an valid data ")
    void testSave2() {
        Mockito.when(applicationService.find(Mockito.anyString())).thenReturn(Optional.empty());
        smtpApplicationService.save(generateSmtpModel("SMTP"));
        Mockito.verify(applicationService, Mockito.never()).find(Mockito.anyString());
        Mockito.verify(smtpService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).saveApplicationActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Save Smtp")
    void testSave3() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.find(Mockito.anyString())).thenThrow(notFound("Application"));
            smtpApplicationService.save(generateSmtpModel("SMTP1"));
        });
        Mockito.verify(applicationService, Mockito.never()).find(Mockito.anyString());
        Mockito.verify(smtpService, Mockito.never()).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Get Smtp")
    void testGet() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity());
        Mockito.when(smtpService.get(Mockito.anyString())).thenReturn(getSmtpEntity());
        smtpApplicationService.get("123456");
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(smtpService, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete Smtp")
    void testDelete() {
        Mockito.when(processService.findByApplicationProfile(Mockito.anyString())).thenReturn(new ArrayList<>());
        smtpApplicationService.delete("123456");
        Mockito.verify(processService, Mockito.times(1)).findByApplicationProfile(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(smtpService, Mockito.times(1)).delete(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    @DisplayName("Delete Smtp1")
    void testDelete1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(processService.findByApplicationProfile(Mockito.anyString())).thenThrow(internalServerError("Unable to Delete this Application, Because it has workflow"));
            smtpApplicationService.delete("123456");
        });
        Mockito.verify(processService, Mockito.times(1)).findByApplicationProfile(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).get(Mockito.anyString());
        Mockito.verify(smtpService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).delete(Mockito.any());
    }

    @Test
    @DisplayName("Delete Smtp1")
    void testDelete2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(processService.findByApplicationProfile(Mockito.anyString())).thenReturn(getProcessEntities());
            smtpApplicationService.delete("123456");
        });
        Mockito.verify(processService, Mockito.times(1)).findByApplicationProfile(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).get(Mockito.anyString());
        Mockito.verify(smtpService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).delete(Mockito.any());
    }

    @Test
    @DisplayName("Update Smtp")
    void testUpdate1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () ->
        {
            Mockito.when(applicationService.get(Mockito.anyString())).thenThrow(notFound("Application"));
            smtpApplicationService.update(generateSmtpModel("SMTP"));
        });
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Update Smtp1")
    void testUpdate2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.get(Mockito.anyString())).thenThrow(notFound("Application"));
            Mockito.when(smtpService.get(Mockito.anyString())).thenThrow(notFound("Application"));
            smtpApplicationService.update(generateSmtpModel("SMTP"));
        });
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(smtpService, Mockito.never()).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Update Smtp2")
    void testUpdate3() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity());
        Mockito.when(smtpService.get(Mockito.anyString())).thenReturn(getSmtpEntity());
        smtpApplicationService.update(generateSmtpModel("SMTP"));
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(smtpService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(smtpService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Update with protocol change")
    void testUpdate4() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity());
        Mockito.when(smtpService.get(Mockito.anyString())).thenReturn(getSmtpEntity());
        smtpApplicationService.update(generateSmtpModel("FTP"));
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(manageProtocolService, Mockito.times(1)).deleteProtocol(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Update with protocol not change")
    void testUpdate5() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity());
        Mockito.when(smtpService.get(Mockito.anyString())).thenReturn(getSmtpEntity());
        smtpApplicationService.update(generateSmtpModel("SMTP"));
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(smtpService, Mockito.times(1)).get(Mockito.anyString());
    }

    SmtpModel generateSmtpModel(String protocol) {
        SmtpModel smtpModel = new SmtpModel();
        smtpModel.setName("Name");
        smtpModel.setPkId("123456");
        smtpModel.setProtocol(protocol);
        smtpModel.setPassword("password");
        return smtpModel;
    }

    ApplicationEntity getApplicationEntity() {
        ApplicationEntity applicationEntity = new ApplicationEntity();
        applicationEntity.setAppDropFiles("AppDropfiles");
        applicationEntity.setAppIntegrationProtocol("SMTP");
        applicationEntity.setAppIsActive("AppIsActive");
        applicationEntity.setApplicationName("ApplicationName");
        applicationEntity.setApplicationId("ApplicationId");
        applicationEntity.setAppPickupFiles("AppPickupFiles");
        applicationEntity.setAppProtocolRef("AppProtocolRef");
        applicationEntity.setEmailId("Email@e.com");
        applicationEntity.setPhone("7598008944");
        applicationEntity.setPkId("123456");
        return applicationEntity;
    }

    SmtpEntity getSmtpEntity() {
        SmtpEntity smtpEntity = new SmtpEntity();
        smtpEntity.setPkId("4566142");
        smtpEntity.setPassword("password");
        smtpEntity.setName("Name");
        return smtpEntity;
    }

    ProcessEntity getProcessEntity() {
        ProcessEntity processEntity = new ProcessEntity();
        processEntity.setSeqId("123456");
        processEntity.setApplicationProfile("Application");
        processEntity.setFlow("Flow");
        return processEntity;
    }

    List<ProcessEntity> getProcessEntities() {
        List<ProcessEntity> processEntities = new ArrayList<>();
        processEntities.add(getProcessEntity());
        return processEntities;
    }

}
