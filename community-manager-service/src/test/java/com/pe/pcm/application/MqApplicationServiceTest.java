package com.pe.pcm.application;


import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.application.entity.ApplicationEntity;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.MqModel;
import com.pe.pcm.protocol.MqService;
import com.pe.pcm.protocol.mq.entity.MqEntity;
import com.pe.pcm.workflow.ProcessService;
import com.pe.pcm.workflow.entity.ProcessEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
 class MqApplicationServiceTest {
    @Mock
    private ApplicationService applicationService;
    @Mock
    private ActivityHistoryService activityHistoryService;
    @Mock
    private MqService mqService;
    @Mock
    private ProcessService processService;
    @Mock
    private ManageProtocolService manageProtocolService;
    //@InjectMocks
    private MqApplicationService mqApplicationService;

    @BeforeEach
    void inIt() {
        mqApplicationService = new MqApplicationService(applicationService,activityHistoryService,
                mqService,processService,manageProtocolService);
    }

    @Test
    @DisplayName("Create a new MqApplication with unknown protocol")
     void testCreate() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            mqApplicationService.save(getMqModel("MQ1"));
        });
        Mockito.verify(applicationService, Mockito.never()).find(Mockito.anyString());
    }

    @Test
    @DisplayName("Create a MqApplication with known protocol")
     void testCreate1() {
        mqApplicationService.save(getMqModel("MQ"));
        Mockito.verify(applicationService, Mockito.times(1)).find(Mockito.anyString());
    }

    @Test
    @DisplayName("Create a  MqApplication with duplicate protocol")
     void testCreate2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.find(Mockito.anyString())).thenReturn(Optional.ofNullable(getApplicationEntity()));
            mqApplicationService.save(getMqModel("MQ"));
        });
        Mockito.verify(applicationService, Mockito.times(1)).find(Mockito.anyString());
        Mockito.verify(mqService, Mockito.never()).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Create a MqApplication")
     void testCreate3() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.find(Mockito.anyString())).thenThrow(notFound("Application"));
            mqApplicationService.save(getMqModel("MQ"));
        });
        Mockito.verify(applicationService, Mockito.times(1)).find(Mockito.anyString());
    }

    @Test
    @DisplayName("Create a  MqApplication with passing an valid data")
     void testCreate4() {
        Mockito.when(applicationService.find(Mockito.anyString())).thenReturn(Optional.empty());
        mqApplicationService.save(getMqModel("MQ"));
        Mockito.verify(applicationService, Mockito.times(1)).find(Mockito.anyString());
        Mockito.verify(mqService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Delete a MqApplication with passing an valid data")
     void testDelete() {
        Mockito.when(processService.findByApplicationProfile(Mockito.any())).thenReturn(new ArrayList<>());
        String pkId = "123456";
        mqApplicationService.delete(pkId);
        Mockito.verify(processService, Mockito.times(1)).findByApplicationProfile(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(mqService, Mockito.times(1)).delete(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).delete(getApplicationEntity());
    }

    @Test
    @DisplayName("Delete a MqApplication")
     void testDelete1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(processService.findByApplicationProfile(Mockito.anyString())).thenThrow(internalServerError("Unable to delete this Application, Because it has workflow"));
            String pkId = "123456";
            mqApplicationService.delete(pkId);
        });
        Mockito.verify(applicationService, Mockito.never()).get(Mockito.anyString());
        Mockito.verify(mqService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).delete(getApplicationEntity());
    }

    @Test
    @DisplayName("Delete a MqApplication")
    void testDelete2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(processService.findByApplicationProfile(Mockito.anyString())).thenReturn(getProcessEntities());
            String pkId = "123456";
            mqApplicationService.delete(pkId);
        });
        Mockito.verify(applicationService, Mockito.never()).get(Mockito.anyString());
        Mockito.verify(mqService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).delete(getApplicationEntity());
    }

    @Test
    @DisplayName("Get a Mq Application with a valid data")
     void testGet3() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity());
        Mockito.when(mqService.get(Mockito.anyString())).thenReturn(getMqEntity());
        String pkId = "123456";
        mqApplicationService.get(pkId);
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(mqService, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Update a Mq Application ")
     void testUpdate() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.get(Mockito.anyString())).thenThrow(notFound("Application"));
            mqApplicationService.update(getMqModel("MQ1"));
        });
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Update a Mq Application1")
     void testUpdate1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.get(Mockito.anyString())).thenThrow(notFound("Application"));
            mqApplicationService.update(getMqModel("MQ"));
        });
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(mqService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(mqService, Mockito.never()).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(manageProtocolService,Mockito.never()).deleteProtocol(Mockito.anyString(),Mockito.anyString());
    }

    @Test
    @DisplayName("Update Mq Application with a valid data")
     void testUpdate3() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity());
        Mockito.when(mqService.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getMqEntity());
        Mockito.when(applicationService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getApplicationEntity());
        mqApplicationService.update(getMqModel("Mailbox"));
        Mockito.verify(mqService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).updateApplicationActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
    }

    @Test
    @DisplayName("Update with protocol change")
     void testUpdate4() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity());
        mqApplicationService.update(getMqModel("FTPS"));
        Mockito.verify(manageProtocolService,Mockito.times(1)).deleteProtocol(Mockito.anyString(),Mockito.anyString());
        Mockito.verify(applicationService,Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Update with protocol not change")
     void testUpdate5() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity());
        Mockito.when(mqService.get(Mockito.anyString())).thenReturn(getMqEntity());
        mqApplicationService.update(getMqModel("FTP"));
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(mqService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(mqService, Mockito.never()).delete(Mockito.anyString());
    }

    MqModel getMqModel(String protocol) {
        MqModel mqModel = new MqModel();
        mqModel.setPkId("123456");
        mqModel.setProfileName("ProfileName");
        mqModel.setProfileId("ProfileId");
        mqModel.setProtocol(protocol);
        mqModel.setEmailId("Email@email.com");
        mqModel.setPhone("5378787868");
        mqModel.setStatus(false);
        mqModel.setHostName("Hostname");
        mqModel.setFileType("FileType");
        mqModel.setQueueManager("queue");
        mqModel.setQueueName("queuename");
        mqModel.setAdapterName("Adapter");
        mqModel.setPoolingInterval("poolingInterval");
        mqModel.setHubInfo(false);
        return mqModel;
    }

    ApplicationEntity getApplicationEntity() {
        ApplicationEntity applicationEntity = new ApplicationEntity();
        applicationEntity.setAppDropFiles("AppDropfiles");
        applicationEntity.setAppIntegrationProtocol("FTP");
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

    MqEntity getMqEntity() {
        MqEntity mqEntity = new MqEntity();
        mqEntity.setAdapterName("AdapterName");
        mqEntity.setFileType("FileType");
        mqEntity.setHostName("HostName");
        mqEntity.setIsActive("Active");
        mqEntity.setIsHubInfo("HubInfo");
        mqEntity.setPkId("1234456");
        mqEntity.setPoolingIntervalMins("poolingInterval");
        mqEntity.setQueueName("queueName");
        mqEntity.setQueueManager("QueueManager");
        return mqEntity;
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
