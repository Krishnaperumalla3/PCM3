package com.pe.pcm.application;


import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.application.entity.ApplicationEntity;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.SapModel;
import com.pe.pcm.protocol.SapService;
import com.pe.pcm.protocol.sap.entity.SapEntity;
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
class SapApplicationServiceTest {
    @MockBean
    private ApplicationService applicationService;
    @MockBean
    private SapService sapService;
    @MockBean
    private ActivityHistoryService activityHistoryService;
    @MockBean
    private ProcessService processService;
    @MockBean
    private ManageProtocolService manageProtocolService;
    @InjectMocks
    private SapApplicationService sapApplicationService;

    @Test
    @DisplayName("Create a Sap Application with an unknown protocol")
    void testCreate() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            sapApplicationService.save(getSapModel("SAP1"));
        });
        Mockito.verify(applicationService, Mockito.never()).find(Mockito.anyString());
    }

    @Test
    @DisplayName("Create a Sap Application with an known protocol")
    void testCreate1() {
        sapApplicationService.save(getSapModel("SAP"));
        Mockito.verify(applicationService, Mockito.times(1)).find(Mockito.anyString());
    }

    @Test
    @DisplayName("Create a Sap Application with an duplicate protocol")
    void testCreate2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.find(Mockito.anyString())).thenReturn(Optional.ofNullable(getApplicationEntity()));
            sapApplicationService.save(getSapModel("SAP"));
        });
        Mockito.verify(applicationService, Mockito.times(1)).find(Mockito.anyString());
    }

    @Test
    @DisplayName("Create a Sap Application")
    void testCreate3() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.find(Mockito.anyString())).thenThrow(notFound("Application"));
            sapApplicationService.save(getSapModel("SAP"));
        });
        Mockito.verify(applicationService, Mockito.times(1)).find(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Create a Sap Application with passing an valid data")
    void testCreate4() {
        Mockito.when(applicationService.find(Mockito.anyString())).thenReturn(Optional.empty());
        sapApplicationService.save(getSapModel("SAP"));
        Mockito.verify(applicationService, Mockito.times(1)).find(Mockito.anyString());
        Mockito.verify(sapService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).saveApplicationActivity(Mockito.any(), Mockito.any());
    }

    @Test
    @DisplayName("Delete a Sap Application with passing an valid data")
    void testDelete() {
        Mockito.when(processService.findByApplicationProfile(Mockito.any())).thenReturn(new ArrayList<>());
        String pkId = "123456";
        sapApplicationService.delete(pkId);
        Mockito.verify(processService, Mockito.times(1)).findByApplicationProfile(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(sapService, Mockito.times(1)).delete(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).delete(getApplicationEntity());
    }

    @Test
    @DisplayName("Delete a SapApplication")
    void testDelete1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(processService.findByApplicationProfile(Mockito.anyString())).thenThrow(internalServerError("Unable to delete this Application, Because it has workflow"));
            String pkId = "123456";
            sapApplicationService.delete(pkId);
        });
        Mockito.verify(applicationService, Mockito.never()).get(Mockito.anyString());
        Mockito.verify(sapService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).delete(getApplicationEntity());
    }

    @Test
    @DisplayName("Delete a SapApplication")
    void testDelete2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(processService.findByApplicationProfile(Mockito.anyString())).thenReturn(getProcessEntities());
            String pkId = "123456";
            sapApplicationService.delete(pkId);
        });
        Mockito.verify(applicationService, Mockito.never()).get(Mockito.anyString());
        Mockito.verify(sapService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).delete(getApplicationEntity());
    }


    @Test
    @DisplayName("Get a Sap Application ")
    void testGet() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.get(Mockito.anyString())).thenThrow(notFound("Application"));
            Mockito.when(sapService.get(Mockito.anyString())).thenThrow(notFound("Partner"));
            sapApplicationService.get("123456");
        });
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(sapService, Mockito.never()).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Get a Sap Application with passing an data")
    void testGet1() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity());
        Mockito.when(sapService.get(Mockito.anyString())).thenReturn(geSapEntity());
        sapApplicationService.get("123456");
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(sapService, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Update Sap Application ")
    void testUpdate1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.get(Mockito.anyString())).thenThrow(notFound("Application"));
            sapApplicationService.update(getSapModel("SAP"));
        });
        Mockito.verify(sapService, Mockito.never()).get(Mockito.anyString());
        Mockito.verify(manageProtocolService,Mockito.never()).deleteProtocol(Mockito.anyString(),Mockito.anyString());
    }

    @Test
    @DisplayName(" Update Sap Application1")
    void testUpdate2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.get(Mockito.anyString())).thenThrow(notFound("Protocol"));
            Mockito.when(sapService.get(Mockito.anyString())).thenThrow(notFound("Protocol"));
            sapApplicationService.update(getSapModel("SAP"));
        });
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(sapService, Mockito.never()).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.never()).saveApplicationActivity(Mockito.any(), Mockito.any());
    }

    @Test
    @DisplayName("Update RemoteApplication returning entities with a valid data")
    void testUpdate3() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity());
        Mockito.when(sapService.get(Mockito.anyString())).thenReturn(geSapEntity());
        Mockito.when(sapService.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(geSapEntity());
        Mockito.when(applicationService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getApplicationEntity());
        sapApplicationService.update(getSapModel("SFGFTP"));
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(sapService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(sapService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.never()).saveApplicationActivity(Mockito.any(), Mockito.any());
    }

    @Test
    @DisplayName("Update with protocol change")
    void testUpdate4() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity());
        Mockito.when(sapService.get(Mockito.anyString())).thenReturn(geSapEntity());
        Mockito.when(sapService.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(geSapEntity());
        Mockito.when(applicationService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getApplicationEntity());
        sapApplicationService.update(getSapModel("FTP"));
        Mockito.verify(manageProtocolService,Mockito.times(1)).deleteProtocol(Mockito.anyString(),Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Update with protocol not change")
    void testUpdate5() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity());
        Mockito.when(sapService.get(Mockito.anyString())).thenReturn(geSapEntity());
        sapApplicationService.update(getSapModel("SFGFTP"));
        Mockito.verify(sapService, Mockito.never()).delete(Mockito.anyString());
    }

    SapModel getSapModel(String protocol) {
        SapModel sapModel = new SapModel();
        sapModel.setPkId("123456");
        sapModel.setProfileName("ProfileName");
        sapModel.setProfileId("ProfileId");
        sapModel.setProtocol(protocol);
        sapModel.setEmailId("Email@email.com");
        sapModel.setPhone("468736886");
        sapModel.setStatus(false);
        sapModel.setAdapterName("AdapterName");
        sapModel.setSapRoute("Route");
        sapModel.setHubInfo(false);
        return sapModel;
    }

    ApplicationEntity getApplicationEntity() {
        ApplicationEntity applicationEntity = new ApplicationEntity();
        applicationEntity.setAppDropFiles("AppDropfiles");
        applicationEntity.setAppIntegrationProtocol("SFGFTP");
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

    SapEntity geSapEntity() {
        SapEntity sapEntity = new SapEntity();
        sapEntity.setPkId("123456");
        sapEntity.setIsActive("Active");
        sapEntity.setSapRoute("Route");
        sapEntity.setIsHubInfo("HubInfo");
        sapEntity.setSubscriberId("SubscriberId");
        sapEntity.setSapAdapterName("SapAdapterName");
        sapEntity.setSubscriberType("SubscriberType");
        sapEntity.setCreatedBy("CreatedBy");
        return sapEntity;
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
