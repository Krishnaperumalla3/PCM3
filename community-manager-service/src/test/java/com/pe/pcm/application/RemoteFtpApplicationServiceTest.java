
package com.pe.pcm.application;

import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.application.entity.ApplicationEntity;
import com.pe.pcm.application.entity.RemoteApplicationStagingEntity;
import com.pe.pcm.application.sfg.RemoteFtpApplicationService;
import com.pe.pcm.b2b.B2BRemoteFtpService;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.RemoteProfileModel;
import com.pe.pcm.protocol.RemoteFtpService;
import com.pe.pcm.protocol.remoteftp.entity.RemoteFtpEntity;
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
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class RemoteFtpApplicationServiceTest {
    @Mock
    private ApplicationService applicationService;
    @Mock
    private RemoteFtpService remoteFtpService;
    @Mock
    private ActivityHistoryService activityHistoryService;
    @Mock
    private B2BRemoteFtpService b2BRemoteFtpService;
    @Mock
    private ManageProtocolService manageProtocolService;
    @Mock
    private ProcessService processService;
    //@InjectMocks
    private RemoteFtpApplicationService remoteFtpApplicationService;

    @BeforeEach
    void inIt() {
        remoteFtpApplicationService = new RemoteFtpApplicationService(applicationService,remoteFtpService,
                activityHistoryService,b2BRemoteFtpService,processService,manageProtocolService);
    }

    @Test
    @DisplayName("Create a new Remote Ftp Application with a unknown protocol")
    void testCreate() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            remoteFtpApplicationService.save(getRemoteFtpModel("SFGFTP1"));
        });
        verify(applicationService, never()).find(anyString());
    }


    @Test
    @DisplayName("Create a new Remote Ftp Application with a known protocol")
    void testCreate1() {
        remoteFtpApplicationService.save(getRemoteFtpModel("SFGFTP"));
        verify(applicationService, times(1)).find(anyString());
    }

    @Test
    @DisplayName("Create a new Remote Ftp Application with a duplicate protocol")
    void testCreate2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            when(applicationService.find(anyString())).thenReturn(Optional.ofNullable(getApplicationEntity()));
            remoteFtpApplicationService.save(getRemoteFtpModel("SFGFTP"));
        });
        verify(applicationService, times(1)).find(anyString());
        verify(remoteFtpService, never()).saveProtocol(any(), anyString(), anyString(), anyBoolean(), anyString(), anyString());
        verify(applicationService, never()).save(any(), anyString(), anyString());
    }

    @Test
    @DisplayName("Create a Remote Ftp Application")
    void testCreate3() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            when(applicationService.find(anyString())).thenThrow(notFound("Application"));
            remoteFtpApplicationService.save(getRemoteFtpModel("SFGFTP"));
        });
        verify(applicationService, times(1)).find(anyString());
        verify(remoteFtpService, never()).saveProtocol(any(), anyString(), anyString(), anyBoolean(), anyString(), anyString());
        verify(applicationService, never()).save(any(), anyString(), anyString());
    }

    @Test
    @DisplayName("Create a Remote Ftp Application with passing an valid data")
    void testCreate4() {
        when(applicationService.find(anyString())).thenReturn(Optional.empty());
        remoteFtpApplicationService.save(getRemoteFtpModel("SFGFTP"));
        verify(applicationService, times(1)).find(anyString());
        verify(remoteFtpService, times(1)).saveProtocol(any(), anyString(), anyString(), anyBoolean(), anyString(), anyString());
        verify(applicationService, times(1)).save(any(), anyString(), anyString());
    }

    @Test
    @DisplayName("Delete a Remote Ftp Application with passing an valid data")
    void testDelete() {
        when(processService.findByApplicationProfile(any())).thenReturn(new ArrayList<>());
        when(applicationService.get(anyString())).thenReturn(getApplicationEntity());
        remoteFtpApplicationService.delete("pkId", false);
        verify(processService, times(1)).findByApplicationProfile(anyString());
        verify(applicationService, times(1)).get(anyString());
        verify(remoteFtpService, times(1)).deleteProtocol(anyString(), anyString(), anyString(), anyBoolean());
        verify(applicationService, never()).delete(getApplicationEntity());
    }

    @Test
    @DisplayName("Delete a Remote Ftp Application ")
    void testDelete1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            when(processService.findByApplicationProfile(anyString())).thenThrow(internalServerError("Unable to delete this Application, Because it has workflow"));
            String pkId = "123456";
            remoteFtpApplicationService.delete(pkId, false);
        });
        verify(applicationService, never()).get(anyString());
        verify(remoteFtpService, never()).deleteProtocol(anyString(), anyString(), anyString(), anyBoolean());
        verify(applicationService, never()).delete(any());
    }

    @Test
    @DisplayName("Delete a Remote Ftp Application ")
    void testDelete2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            when(processService.findByApplicationProfile(anyString())).thenReturn(getProcessEntities());
            String pkId = "123456";
            remoteFtpApplicationService.delete(pkId, false);
        });
        verify(applicationService, never()).get(anyString());
        verify(remoteFtpService, never()).deleteProtocol(anyString(), anyString(), anyString(), anyBoolean());
        verify(applicationService, never()).delete(any());
    }


    @Test
    @DisplayName("Get a Remote Ftp Application")
    void testGet() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            when(applicationService.get(anyString())).thenThrow(notFound("Application"));
            remoteFtpApplicationService.get("123456");
        });
        verify(applicationService, times(1)).get(anyString());
        verify(remoteFtpService, never()).get(anyString());
        verify(remoteFtpService, never()).getRemoteFtp(any());
    }

    @Test
    @DisplayName("Get a Remote Ftp Application with an valid data ")
    void testGet1() {
        when(applicationService.get(anyString())).thenReturn(getApplicationEntity());
        when(remoteFtpService.get(anyString())).thenReturn(getRemoteFtpEntity("n"));
        String pkId = "123456";
        remoteFtpApplicationService.get(pkId);
        verify(applicationService, times(1)).get(anyString());
        verify(remoteFtpService, times(1)).get(anyString());
    }

    @Test
    @DisplayName("Get a Remote Ftp Application with an valid data ")
    void testGet2() {
        when(applicationService.get(anyString())).thenReturn(getApplicationEntity());
        when(remoteFtpService.get(anyString())).thenReturn(getRemoteFtpEntity("y"));
        String pkId = "123456";
        remoteFtpApplicationService.get(pkId);
        verify(applicationService, times(1)).get(anyString());
        verify(remoteFtpService, times(1)).get(anyString());
    }

    @Test
    @DisplayName("Update Remote Ftp Application ")
    void testUpdate() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            when(applicationService.get(anyString())).thenThrow(notFound("Application"));
            remoteFtpApplicationService.update(getRemoteFtpModel("SFGFTP"));
        });
        verify(applicationService, times(1)).get(anyString());
    }

    @Test
    @DisplayName("Update Remote Ftp Application1")
    void testUpdate1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            when(applicationService.get(anyString())).thenReturn(getApplicationEntity());
            when(remoteFtpService.get(anyString())).thenThrow(notFound("protocol"));
            remoteFtpApplicationService.update(getRemoteFtpModel("SFGFTP"));
        });
        verify(applicationService, times(1)).get(anyString());
        verify(remoteFtpService, times(1)).get(anyString());
        verify(remoteFtpService, never()).delete(anyString());
        verify(remoteFtpService, never()).saveProtocol(any(), anyString(), anyString(), anyBoolean(), anyString(), anyString());
        verify(applicationService, never()).save(any(), anyString(), anyString());
    }

    @Test
    @DisplayName("Update Remote Ftp Application with a valid data")
    void testUpdate3() {
        when(applicationService.get(anyString())).thenReturn(getApplicationEntity());
        when(remoteFtpService.get(anyString())).thenReturn(getRemoteFtpEntity("n"));
        when(remoteFtpService.saveProtocol(any(), anyString(), anyString(), any(), anyString(), anyString())).thenReturn(getRemoteFtpEntity("n"));
        when(applicationService.save(any(), anyString(), anyString())).thenReturn(getApplicationEntity());
        remoteFtpApplicationService.update(getRemoteFtpModel("SFGFTP"));
        verify(remoteFtpService, times(1)).saveProtocol(any(), anyString(), anyString(), any(), anyString(), anyString());
        verify(applicationService, times(1)).save(any(), anyString(), anyString());
        verify(activityHistoryService, times(1)).updateApplicationActivity(any(), any(), anyMap(), anyMap(), anyBoolean());
    }

    @Test
    @DisplayName("Update with protocol change")
    void testUpdate4() {
        when(applicationService.get(anyString())).thenReturn(getApplicationEntity());
        remoteFtpApplicationService.update(getRemoteFtpModel("SFGSFTP"));
        verify(applicationService, times(1)).get(anyString());
        verify(manageProtocolService, times(1)).deleteProtocol(anyString(), anyString());
    }

    @Test
    @DisplayName("Update with protocol not change")
    void testUpdate5() {
        when(applicationService.get(anyString())).thenReturn(getApplicationEntity());
        when(remoteFtpService.get(anyString())).thenReturn(getRemoteFtpEntity("n"));
        remoteFtpApplicationService.update(getRemoteFtpModel("SFGFTP"));
        verify(applicationService, times(1)).get(anyString());
        verify(remoteFtpService, times(1)).get(anyString());
        verify(remoteFtpService, never()).delete(anyString());
    }

    @Test
    @DisplayName("Save Remote Staging Profile")
    void testSaveRemoteStagingProfile() {
       // Mockito.when(b2BRemoteFtpService.saveRemoteFtpProfile(getRemoteFtpModel("protocol"), false,"APP", null)).thenReturn("ProfileId");
        remoteFtpApplicationService.saveRemoteStagingProfile(getRemoteApplicationStagingEntity(), getRemoteFtpEntity("n"));
        //verify(b2BRemoteFtpService,times(1)).saveRemoteFtpProfile(any(),anyBoolean(),anyString(),anyString());
        verify(activityHistoryService, times(1)).saveApplicationActivity(getRemoteApplicationStagingEntity().getPkId(), "Application created.");
    }

    RemoteProfileModel getRemoteFtpModel(String protocol) {
        RemoteProfileModel remoteProfileModel = new RemoteProfileModel();
        remoteProfileModel.setPkId("123456");
        remoteProfileModel.setProfileName("ProfileName");
        remoteProfileModel.setProfileId("ProfileId");
        remoteProfileModel.setProtocol(protocol);
        remoteProfileModel.setEmailId("Email@email.com");
        remoteProfileModel.setPhone("458979869507");
        remoteProfileModel.setStatus(false);
        remoteProfileModel.setUserName("UserName");
        remoteProfileModel.setPassword("password");
        remoteProfileModel.setTransferType("TransferType");
        remoteProfileModel.setInDirectory("InDirectory");
        remoteProfileModel.setOutDirectory("Outdirectory");
        remoteProfileModel.setFileType("FileType");
        return remoteProfileModel;
    }

    ApplicationEntity getApplicationEntity() {
        ApplicationEntity applicationEntity = new ApplicationEntity();
        applicationEntity.setAppDropFiles("AppDropfiles");
        applicationEntity.setAppIntegrationProtocol("SFGFTP");
        applicationEntity.setAppIsActive("AppIsActive");
        applicationEntity.setApplicationName("ApplicationName");
        applicationEntity.setApplicationId("ApplicationId");
        applicationEntity.setAppPickupFiles("AppPickupFiles");
        applicationEntity.setAppProtocolRef("SFGFTP");
        applicationEntity.setEmailId("Email@e.com");
        applicationEntity.setPhone("7598008944");
        applicationEntity.setPkId("12345");
        applicationEntity.setAppIntegrationProtocol("SFGFTP");
        return applicationEntity;
    }

    RemoteFtpEntity getRemoteFtpEntity(String isHubInfo) {
        RemoteFtpEntity remoteFtpEntity = new RemoteFtpEntity();
        remoteFtpEntity.setProfileId("ProfileId");
        remoteFtpEntity.setAdapterName("AdapterName");
        remoteFtpEntity.setCertificateId("CertificateId");
        remoteFtpEntity.setDeleteAfterCollection("Collection");
        remoteFtpEntity.setIsActive("Active");
        remoteFtpEntity.setPassword("password");
        remoteFtpEntity.setPkId("123456");
        remoteFtpEntity.setPoolingIntervalMins("PoolingIntervalMinus");
        remoteFtpEntity.setProtocolType("protocol");
        remoteFtpEntity.setIsHubInfo(isHubInfo);
        return remoteFtpEntity;
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

    RemoteApplicationStagingEntity getRemoteApplicationStagingEntity() {
        RemoteApplicationStagingEntity remoteApplicationStagingEntity = new RemoteApplicationStagingEntity();
        remoteApplicationStagingEntity.setAppIntegrationProtocol("protocol");
        remoteApplicationStagingEntity.setPkId("123456");
        remoteApplicationStagingEntity.setEmailId("Email@email.com");
        remoteApplicationStagingEntity.setPhone("9785435243");
        remoteApplicationStagingEntity.setAppIsActive("yes");
        return remoteApplicationStagingEntity;
    }

}







