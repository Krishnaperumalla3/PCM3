package com.pe.pcm.application;

import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.application.entity.ApplicationEntity;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.WebserviceModel;
import com.pe.pcm.protocol.WebserviceService;
import com.pe.pcm.protocol.webservice.entity.WebserviceEntity;
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
class WsApplicationServiceTest {
    @MockBean
    private ApplicationService applicationService;
    @MockBean
    private ActivityHistoryService activityHistoryService;
    @MockBean
    private WebserviceService webserviceService;
    @MockBean
    private ProcessService processService;
    @MockBean
    private ManageProtocolService manageProtocolService;
    @InjectMocks
    private WsApplicationService wsApplicationService;

    @Test
    @DisplayName(value = "Check Duplicate Application")
    void duplicateApplicationTest() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.find(Mockito.anyString())).thenReturn(Optional.of(getApplicationEntity()));
            wsApplicationService.duplicateApplication(Mockito.anyString());
        });
        Mockito.verify(applicationService, Mockito.times(1)).find(Mockito.anyString());
        Assertions.assertEquals(CommunityManagerServiceException.class, CommunityManagerServiceException.class);
        Assertions.assertNotEquals(GlobalExceptionHandler.conflict("Application"), CommunityManagerServiceException.class);
    }

    @Test
    @DisplayName("Create a Web Application with a unknown protocol")
    void testCreate() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            wsApplicationService.save(getWebserviceModel("Webservice1"));
        });
        Mockito.verify(applicationService, Mockito.never()).find(Mockito.anyString());
    }

    @Test
    @DisplayName("Create a Web Application with a known protocol")
    void testCreate1() {
        wsApplicationService.save(getWebserviceModel("Webservice"));
        Mockito.verify(applicationService, Mockito.times(1)).find(Mockito.anyString());
    }

    @Test
    @DisplayName("Create a Web Application with a duplicate protocol")
    void testCreate2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.find(Mockito.anyString())).thenReturn(Optional.ofNullable(getApplicationEntity()));
            wsApplicationService.save(getWebserviceModel("Webservice"));
        });
        Mockito.verify(applicationService, Mockito.times(1)).find(Mockito.anyString());
        Mockito.verify(webserviceService, Mockito.never()).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.never()).saveApplicationActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Create a new WebApplication with an valid data")
    void testCreate4() {
        Mockito.when(applicationService.find(Mockito.anyString())).thenReturn(Optional.empty());
        wsApplicationService.save(getWebserviceModel("Webservice"));
        Mockito.verify(webserviceService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).saveApplicationActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Delete a Web Application with a valid data")
    void testDelete() {
        Mockito.when(processService.findByApplicationProfile(Mockito.any())).thenReturn(new ArrayList<>());
        String pkId = "123456";
        wsApplicationService.delete(pkId);
        Mockito.verify(processService, Mockito.times(1)).findByApplicationProfile(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(webserviceService, Mockito.times(1)).delete(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).delete(getApplicationEntity());
    }

    @Test
    @DisplayName("Delete a Ws Application")
    void testDelete1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(processService.findByApplicationProfile(Mockito.anyString())).thenThrow(internalServerError("Unable to delete this Application, Because it has workflow"));
            String pkId = "123456";
            wsApplicationService.delete(pkId);
        });
        Mockito.verify(applicationService, Mockito.never()).get(Mockito.anyString());
        Mockito.verify(webserviceService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).delete(getApplicationEntity());
    }

    @Test
    @DisplayName("Delete a Ws Application")
    void testDelete2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(processService.findByApplicationProfile(Mockito.anyString())).thenReturn(getProcessEntities());
            String pkId = "123456";
            wsApplicationService.delete(pkId);
        });
        Mockito.verify(applicationService, Mockito.never()).get(Mockito.anyString());
        Mockito.verify(webserviceService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).delete(getApplicationEntity());
    }

    @Test
    @DisplayName("Get Web Application with passing an valid data")
    void testGet3() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity());
        Mockito.when(webserviceService.get(Mockito.anyString())).thenReturn(getWebserviceEntity());
        String pkId = "123456";
        wsApplicationService.get(pkId);
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(webserviceService, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Update Web Application")
    void testUpdate1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.get(Mockito.anyString())).thenThrow(notFound("protocol"));
            wsApplicationService.update(getWebserviceModel("Webservice"));
        });
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(webserviceService, Mockito.never()).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Update Web Application")
    void testUpdate2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity());
            Mockito.when(webserviceService.get(Mockito.anyString())).thenThrow(notFound("Protocol"));
            wsApplicationService.update(getWebserviceModel("Webservice"));
        });
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(webserviceService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(webserviceService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Update Web Application returning entities with a valid data")
    void testUpdate3() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity());
        Mockito.when(webserviceService.get(Mockito.anyString())).thenReturn(getWebserviceEntity());
        Mockito.when(webserviceService.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getWebserviceEntity());
        Mockito.when(applicationService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getApplicationEntity());
        wsApplicationService.update(getWebserviceModel("Webservice"));
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(webserviceService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(webserviceService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Update Web Application with protocol change")
    void testUpdate4() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity());
        Mockito.when(webserviceService.get(Mockito.anyString())).thenReturn(getWebserviceEntity());
        Mockito.when(webserviceService.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getWebserviceEntity());
        Mockito.when(applicationService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getApplicationEntity());
        wsApplicationService.update(getWebserviceModel("FTP"));
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(manageProtocolService, Mockito.times(1)).deleteProtocol(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Update Web Application with protocol NOT change")
    void testUpdate5() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity());
        Mockito.when(webserviceService.get(Mockito.anyString())).thenReturn(getWebserviceEntity());
        Mockito.when(webserviceService.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getWebserviceEntity());
        Mockito.when(applicationService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getApplicationEntity());
        wsApplicationService.update(getWebserviceModel("Webservice"));
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(webserviceService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(webserviceService, Mockito.never()).delete(Mockito.anyString());

    }

    WebserviceModel getWebserviceModel(String protocol) {
        WebserviceModel webserviceModel = new WebserviceModel();
        webserviceModel.setPkId("123456");
        webserviceModel.setProfileName("PofileName");
        webserviceModel.setProfileId("ProfileId");
        webserviceModel.setProtocol(protocol);
        webserviceModel.setEmailId("Email@email.com");
        webserviceModel.setPhone("346587934");
        webserviceModel.setStatus(false);
        webserviceModel.setName("Name");
        webserviceModel.setInMailBox("MailBox");
        webserviceModel.setOutBoundUrl("OutBound");
        webserviceModel.setCertificateId("Certificate");
        webserviceModel.setPoolingInterval("PoolingInterval");
        webserviceModel.setAdapterName("AdapterName");
        webserviceModel.setHubInfo(false);
        return webserviceModel;
    }

    ApplicationEntity getApplicationEntity() {
        ApplicationEntity applicationEntity = new ApplicationEntity();
        applicationEntity.setAppDropFiles("AppDropfiles");
        applicationEntity.setAppIntegrationProtocol("Webservice");
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

    WebserviceEntity getWebserviceEntity() {
        WebserviceEntity webserviceEntity = new WebserviceEntity();
        webserviceEntity.setAdapterName("AdapterName");
        webserviceEntity.setCertificate("Certificate");
        webserviceEntity.setInDirectory("InDirectory");
        webserviceEntity.setIsActive("Active");
        webserviceEntity.setPkId("31435");
        webserviceEntity.setPoolingIntervalMins("poolingInterval");
        return webserviceEntity;
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
