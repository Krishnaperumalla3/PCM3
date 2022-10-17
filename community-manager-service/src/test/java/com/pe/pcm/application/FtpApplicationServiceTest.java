package com.pe.pcm.application;


import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.application.entity.ApplicationEntity;
import com.pe.pcm.b2b.B2BApiService;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.protocol.FtpModel;
import com.pe.pcm.protocol.FtpService;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.ftp.entity.FtpEntity;
import com.pe.pcm.sterling.yfs.YfsUserService;
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
 class FtpApplicationServiceTest {
    @Mock
    private ApplicationService applicationService;
    @Mock
    private ActivityHistoryService activityHistoryService;
    @Mock
    private FtpService ftpService;
    @Mock
    private B2BApiService b2BApiService;
    @Mock
    private ProcessService processService;
    @Mock
    private ManageProtocolService manageProtocolService;
    @Mock
    private YfsUserService yfsUserService;
    //@InjectMocks
    private FtpApplicationService ftpApplicationService;

    @BeforeEach
    void inIt() {
        ftpApplicationService = new FtpApplicationService(applicationService,activityHistoryService
        ,ftpService,b2BApiService,processService,manageProtocolService,yfsUserService);
    }

    @Test
    @DisplayName("Create a new Ftp Application with unknown protocol")
     void testCreate() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            ftpApplicationService.save(getFtpModel("FTP1"));
        });
        Mockito.verify(applicationService, Mockito.never()).find(Mockito.anyString());
    }

    @Test
    @DisplayName("Create a new Ftp Application with known protocol")
     void testCreate1() {
        ftpApplicationService.save(getFtpModel("FTP"));
        Mockito.verify(applicationService, Mockito.times(1)).find(Mockito.anyString());
    }

    @Test
    @DisplayName("Create a new Ftp Application ")
     void testCreate2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.find(Mockito.anyString())).thenThrow(notFound("Application"));
            ftpApplicationService.save(getFtpModel("FTP"));
        });
        Mockito.verify(applicationService, Mockito.times(1)).find(Mockito.anyString());
    }

    @Test
    @DisplayName("Create a new FtpApplication with passing an duplicate Protocol")
     void testCreate3() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.find(Mockito.anyString())).thenReturn(Optional.ofNullable(getApplicationEntity()));
            ftpApplicationService.save(getFtpModel("FTP"));
        });
        Mockito.verify(applicationService, Mockito.times(1)).find(Mockito.anyString());
        Mockito.verify(ftpService, Mockito.never()).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(b2BApiService, Mockito.never()).createMailBoxInSI(Mockito.anyBoolean(), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Create a new FtpApplication with an valid data")
     void testCreate4() {
        Mockito.when(applicationService.find(Mockito.anyString())).thenReturn(Optional.empty());
        ftpApplicationService.save(getFtpModel("FTP"));
        Mockito.verify(applicationService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).saveApplicationActivity(Mockito.anyString(), Mockito.anyString());
    }


    @Test
    @DisplayName("Delete a Ftp Application with passing an valid data")
     void testDelete() {
        Mockito.when(processService.findByApplicationProfile(Mockito.any())).thenReturn(new ArrayList<>());
        String pkId = "123456";
        ftpApplicationService.delete(pkId);
        Mockito.verify(processService, Mockito.times(1)).findByApplicationProfile(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(ftpService, Mockito.times(1)).delete(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).delete(getApplicationEntity());
    }

    @Test
    @DisplayName("Delete a Ftp Application")
     void testDelete1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(processService.findByApplicationProfile(Mockito.anyString())).thenThrow(internalServerError("Unable to delete this Application, Because it has workflow"));
            String pkId = "123456";
            ftpApplicationService.delete(pkId);
        });
        Mockito.verify(applicationService, Mockito.never()).get(Mockito.anyString());
        Mockito.verify(ftpService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).delete(getApplicationEntity());
    }

    @Test
    @DisplayName("Delete a Ftp Application")
    void testDelete2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(processService.findByApplicationProfile(Mockito.anyString())).thenReturn(getProcessEntities());
            String pkId = "123456";
            ftpApplicationService.delete(pkId);
        });
        Assertions.assertEquals(CommunityManagerServiceException.class,CommunityManagerServiceException.class);
        Mockito.verify(processService,Mockito.times(1)).findByApplicationProfile(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).get(Mockito.anyString());
        Mockito.verify(ftpService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).delete(getApplicationEntity());
    }


    @Test
    @DisplayName("Get Ftp Application with passing an valid data")
     void testGet3() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity());
        Mockito.when(ftpService.get(Mockito.anyString())).thenReturn(getFtpEntity());
        final String pkId = "12345";
        ftpApplicationService.get(pkId);
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(ftpService, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Update Ftp Application ")
     void testUpdate() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.get(Mockito.anyString())).thenThrow(notFound("Protocol"));
            ftpApplicationService.update(getFtpModel("FTP"));
        });
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Update Ftp Application ")
     void testUpdate1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity());
            Mockito.when(ftpService.get(Mockito.anyString())).thenThrow(notFound("protocol"));
            ftpApplicationService.update(getFtpModel("FTP"));
        });
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(ftpService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(ftpService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(manageProtocolService,Mockito.never()).deleteProtocol(Mockito.any(),Mockito.any());
        Mockito.verify(ftpService, Mockito.never()).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(b2BApiService, Mockito.never()).createMailBoxInSI(Mockito.anyBoolean(), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Update Ftp Application with a valid data")
     void testUpdate3() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity());
        Mockito.when(ftpService.get(Mockito.anyString())).thenReturn(getFtpEntity());
        Mockito.when(ftpService.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getFtpEntity());
        Mockito.when(applicationService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getApplicationEntity());
        ftpApplicationService.update(getFtpModel("FTP"));
        Mockito.verify(ftpService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).updateApplicationActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
        Mockito.verify(b2BApiService, Mockito.times(1)).createMailBoxInSI(Mockito.anyBoolean(), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Update with protocol change")
     void testUpdate4() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity());
        ftpApplicationService.update(getFtpModel("FTPS"));
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(ftpService, Mockito.never()).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Update with protocol not change")
     void testUpdate5() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity());
        Mockito.when(ftpService.get(Mockito.anyString())).thenReturn(getFtpEntity());
        ftpApplicationService.update(getFtpModel("FTP"));
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(ftpService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(ftpService, Mockito.never()).delete(Mockito.anyString());
    }

    FtpModel getFtpModel(String protocol) {
        FtpModel ftpModel = new FtpModel();
        ftpModel.setPkId("123456");
        ftpModel.setProfileName("ProfileName");
        ftpModel.setProfileId("ProfileId");
        ftpModel.setProtocol(protocol);
        ftpModel.setEmailId("Email@email.com");
        ftpModel.setPhone("85635900323");
        ftpModel.setStatus(false);
        ftpModel.setHostName("HostName");
        ftpModel.setPortNumber("0012");
        ftpModel.setUserName("UserName");
        ftpModel.setPassword("password");
        ftpModel.setFileType("FileType");
        ftpModel.setTransferType("TransferType");
        ftpModel.setInDirectory("InDirectory");
        ftpModel.setOutDirectory("OutDirectory");
        ftpModel.setCertificateId("135476");
        ftpModel.setKnownHostKey("FTP");
        ftpModel.setAdapterName("AdapterName");
        ftpModel.setPoolingInterval("PoolingInterval");
        return ftpModel;
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

    FtpEntity getFtpEntity() {
        FtpEntity ftpEntity = new FtpEntity();
        ftpEntity.setAdapterName("AdapterName");
        ftpEntity.setCertificateId("CertificateId");
        ftpEntity.setFileType("FileType");
        ftpEntity.setHostName("HostName");
        ftpEntity.setIsActive("IsActive");
        ftpEntity.setProtocolType("FTP");
        return ftpEntity;
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
