package com.pe.pcm.application;

import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.application.entity.ApplicationEntity;
import com.pe.pcm.b2b.B2BApiService;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.protocol.HttpModel;
import com.pe.pcm.protocol.HttpService;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.http.entity.HttpEntity;
import com.pe.pcm.sterling.mailbox.SterlingMailboxService;
import com.pe.pcm.sterling.mailbox.routingrule.SterlingRoutingRuleService;
import com.pe.pcm.workflow.ProcessService;
import com.pe.pcm.workflow.entity.ProcessEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
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
class HttpApplicationServiceTest {
    @Mock
    private ApplicationService applicationService;
    @Mock
    private ActivityHistoryService activityHistoryService;
    @Mock
    private HttpService httpService;
    @Mock
    private B2BApiService b2BApiService;
    @Mock
    private ProcessService processService;
    @Mock
    private ManageProtocolService manageProtocolService;
    @Mock
    private SterlingMailboxService sterlingMailboxService;
    @Mock
    private SterlingRoutingRuleService sterlingRoutingRuleService;
    //@InjectMocks
    private HttpApplicationService httpApplicationService;

    @BeforeEach
    void inIt() {
        httpApplicationService = new HttpApplicationService(applicationService, activityHistoryService,
                httpService, processService, manageProtocolService, sterlingMailboxService, sterlingRoutingRuleService);
    }

    @Test
    @DisplayName("Create a new Http Application with a unknown protocol")
    void testCreate() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            httpApplicationService.save(getHttpModel("HTTPS1"));
        });
        Mockito.verify(applicationService, Mockito.never()).find(Mockito.anyString());
    }

    @Test
    @DisplayName("Create a new Http Application with a known protocol")
    void testCreate1() {
        httpApplicationService.save(getHttpModel("HTTP"));
        Mockito.verify(applicationService, Mockito.times(1)).find(Mockito.anyString());
    }

    @Test
    @DisplayName("Create a new Http Application ")
    void testCreate2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.find(Mockito.anyString())).thenThrow(notFound("Application"));
            httpApplicationService.save(getHttpModel("HTTP"));
        });
        Mockito.verify(applicationService, Mockito.times(1)).find(Mockito.anyString());
    }

    @Test
    @DisplayName("Create a new Http Application with passing duplicate protocol")
    void testCreate3() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.find(Mockito.anyString())).thenReturn(Optional.ofNullable(getApplicationEntity()));
            httpApplicationService.save(getHttpModel("HTTP"));
        });
        Mockito.verify(applicationService, Mockito.times(1)).find(Mockito.anyString());
        Mockito.verify(httpService, Mockito.never()).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.never()).saveApplicationActivity(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(b2BApiService, Mockito.never()).createMailBoxInSI(Mockito.anyBoolean(), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Create a new Http Application with an valid data")
    void testCreate4() {
        Mockito.when(applicationService.find(Mockito.anyString())).thenReturn(Optional.empty());
        httpApplicationService.save(getHttpModel("HTTP"));
        Mockito.verify(httpService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).saveApplicationActivity(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(b2BApiService, Mockito.never()).createMailBoxInSI(Mockito.anyBoolean(), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Delete a Http Application with a valid data")
    void testDelete() {
        Mockito.when(processService.findByApplicationProfile(Mockito.any())).thenReturn(new ArrayList<>());
        String pkId = "123456";
        httpApplicationService.delete(pkId);
        Mockito.verify(processService, Mockito.times(1)).findByApplicationProfile(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(httpService, Mockito.times(1)).delete(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).delete(getApplicationEntity());
    }

    @Test
    @DisplayName("Delete a HttpApplication")
    void testDelete1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(processService.findByApplicationProfile(Mockito.anyString())).thenThrow(internalServerError("Unable to delete this Application, Because it has workflow"));
            String pkId = "123456";
            httpApplicationService.delete(pkId);
        });
        Mockito.verify(applicationService, Mockito.never()).get(Mockito.anyString());
        Mockito.verify(httpService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).delete(getApplicationEntity());
    }

    @Test
    @DisplayName("Delete a HttpApplication")
    void testDelete2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(processService.findByApplicationProfile(Mockito.anyString())).thenReturn(getProcessEntities());
            String pkId = "123456";
            httpApplicationService.delete(pkId);
        });
        Assertions.assertEquals(CommunityManagerServiceException.class, CommunityManagerServiceException.class);
        Mockito.verify(processService, Mockito.times(1)).findByApplicationProfile(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).get(Mockito.anyString());
        Mockito.verify(httpService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).delete(getApplicationEntity());
    }

    @Test
    @DisplayName("Get HttpApplication with passing an valid data")
    void testGet3() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity());
        Mockito.when(httpService.get(Mockito.anyString())).thenReturn(getHttpEntity());
        final String pkId = "12345";
        httpApplicationService.get(pkId);
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(httpService, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Update Http Application ")
    void testUpdate1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.get(Mockito.anyString())).thenThrow(notFound("Application"));
            httpApplicationService.update(getHttpModel("HTTP"));
        });
        Mockito.verify(httpService, Mockito.never()).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Update Http Application1 ")
    void testUpdate2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () ->
        {
            Mockito.when(applicationService.get(Mockito.anyString())).thenThrow(notFound("Application"));
            httpApplicationService.update(getHttpModel("HTTP"));
        });
        Mockito.verify(manageProtocolService, Mockito.never()).deleteProtocol(Mockito.any(), Mockito.any());
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(httpService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(b2BApiService, Mockito.never()).createMailBoxInSI(Mockito.anyBoolean(), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Update HttpApplication returning entities with a valid data")
    void testUpdate3() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity());
        Mockito.when(httpService.get(Mockito.anyString())).thenReturn(getHttpEntity());
        Mockito.when(httpService.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getHttpEntity());
        Mockito.when(applicationService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getApplicationEntity());
        httpApplicationService.update(getHttpModel("HTTP"));
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(httpService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(httpService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(b2BApiService, Mockito.never()).createMailBoxInSI(Mockito.anyBoolean(), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Update HttpApplication with protocol change")
    void testUpdate4() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity());
        Mockito.when(httpService.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getHttpEntity());
        Mockito.when(applicationService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getApplicationEntity());
        httpApplicationService.update(getHttpModel("FTP"));
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Update HttpApplication with protocol NOT change")
    void testUpdate5() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity());
        Mockito.when(httpService.get(Mockito.anyString())).thenReturn(getHttpEntity());
        Mockito.when(httpService.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getHttpEntity());
        Mockito.when(applicationService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getApplicationEntity());
        httpApplicationService.update(getHttpModel("HTTP"));
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(httpService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(httpService, Mockito.never()).delete(Mockito.anyString());
    }

    HttpModel getHttpModel(String protocol) {
        HttpModel httpModel = new HttpModel();
        httpModel.setPkId("123456");
        httpModel.setProfileName("ProfileName");
        httpModel.setProfileId("ProfileId");
        httpModel.setProtocol(protocol);
        httpModel.setEmailId("Email@email.com");
        httpModel.setPhone("546589795");
        httpModel.setStatus(false);
        httpModel.setAdapterName("AdaptorName");
        httpModel.setPoolingInterval("PoolingInterval");
        httpModel.setCertificate("Certificate");
        httpModel.setInMailBox("Mailbox");
        httpModel.setOutBoundUrl("OutBoundUrl");
        httpModel.setHubInfo(false);
        return httpModel;
    }

    ApplicationEntity getApplicationEntity() {
        ApplicationEntity applicationEntity = new ApplicationEntity();
        applicationEntity.setAppDropFiles("AppDropfiles");
        applicationEntity.setAppIntegrationProtocol("HTTP");
        applicationEntity.setAppIsActive("AppIsActive");
        applicationEntity.setApplicationName("ApplicationName");
        applicationEntity.setApplicationId("ApplicationId");
        applicationEntity.setAppPickupFiles("AppPickupFiles");
        applicationEntity.setAppProtocolRef("AppProtocolRef");
        applicationEntity.setEmailId("Email@e.com");
        applicationEntity.setPhone("7598008944");
        applicationEntity.setPkId("12345");
        return applicationEntity;
    }

    HttpEntity getHttpEntity() {
        HttpEntity httpEntity = new HttpEntity();
        httpEntity.setAdapterName("AdapterName");
        httpEntity.setCertificate("Certificate");
        httpEntity.setIsActive("IsActive");
        httpEntity.setProtocolType("http");
        httpEntity.setInMailbox("Mailbox");
        httpEntity.setIsHubInfo("HubInfo");
        httpEntity.setPkId("123456");
        return httpEntity;
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
