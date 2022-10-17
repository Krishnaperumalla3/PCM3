package com.pe.pcm.application;

import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.application.entity.ApplicationEntity;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.protocol.FileSystemModel;
import com.pe.pcm.protocol.FileSystemService;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.filesystem.entity.FileSystemEntity;
import com.pe.pcm.sterling.mailbox.SterlingMailboxService;
import com.pe.pcm.sterling.mailbox.routingrule.SterlingRoutingRuleService;
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
class FileSystemApplicationServiceTest {

    @Mock
    private ApplicationService applicationService;
    @Mock
    private FileSystemService fileSystemService;
    @Mock
    private ActivityHistoryService activityHistoryService;
    @Mock
    private ProcessService processService;
    @Mock
    private ManageProtocolService manageProtocolService;
    @Mock
    private SterlingMailboxService sterlingMailboxService;
    @Mock
    private SterlingRoutingRuleService sterlingRoutingRuleService;

    //@InjectMocks
    private FileSystemApplicationService fileSystemApplicationService;

    @BeforeEach
    void inIt() {
        fileSystemApplicationService = new FileSystemApplicationService(processService,fileSystemService,
                applicationService,manageProtocolService,activityHistoryService,sterlingMailboxService,sterlingRoutingRuleService);
    }

    @Test
    @DisplayName("Create a new File System Application Service with unknown protocol")
    void testCreate() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            fileSystemApplicationService.save(getFileSystemModel("FileSystem1"));
        });
        Mockito.verify(applicationService, Mockito.never()).find(Mockito.anyString());
    }

    @Test
    @DisplayName("Create a new File System Application Service with known protocol")
    void testCreate1() {
        fileSystemApplicationService.save(getFileSystemModel("FileSystem"));
        Mockito.verify(applicationService, Mockito.times(1)).find(Mockito.anyString());
    }

    @Test
    @DisplayName("Create a new File System Application Service")
    void testCreate3() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.find(Mockito.anyString())).thenThrow(notFound("Application"));
            fileSystemApplicationService.save(getFileSystemModel("FileSystem"));
        });
        Mockito.verify(applicationService, Mockito.times(1)).find(Mockito.anyString());
    }

    @Test
    @DisplayName("Create a new File System Application Service with passing duplicate protocol")
    void testCreate4() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.find(Mockito.anyString())).thenReturn(Optional.ofNullable(getApplicationEntity()));
            fileSystemApplicationService.save(getFileSystemModel("FileSystem"));
        });
        Mockito.verify(applicationService, Mockito.times(1)).find(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.never()).saveApplicationActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Create a File System Application with an valid data")
    void testCreate5() {
        Mockito.when(applicationService.find(Mockito.anyString())).thenReturn(Optional.empty());
        fileSystemApplicationService.save(getFileSystemModel("FileSystem"));
        Mockito.verify(applicationService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).saveApplicationActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Delete a FileSystemApplication with an valid data")
    void testDelete() {
        Mockito.when(processService.findByApplicationProfile(Mockito.any())).thenReturn(new ArrayList<>());
        String pkId = "123456";
        fileSystemApplicationService.delete(pkId);
        Mockito.verify(processService, Mockito.times(1)).findByApplicationProfile(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(fileSystemService, Mockito.times(1)).delete(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).delete(getApplicationEntity());
    }

    @Test
    @DisplayName("Delete a FileSystemApplication")
    void testDelete1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(processService.findByApplicationProfile(Mockito.anyString())).thenThrow(internalServerError("Unable to delete this Application, Because it has workflow"));
            String pkId = "123456";
            fileSystemApplicationService.delete(pkId);
        });
        Mockito.verify(applicationService, Mockito.never()).get(Mockito.anyString());
        Mockito.verify(fileSystemService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).delete(getApplicationEntity());
    }

    @Test
    @DisplayName("Delete a FileSystemApplication")
    void testDelete2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(processService.findByApplicationProfile(Mockito.anyString())).thenReturn(getProcessEntities());
            String pkId = "123456";
            fileSystemApplicationService.delete(pkId);
        });
        Assertions.assertEquals(CommunityManagerServiceException.class, CommunityManagerServiceException.class);
        Mockito.verify(processService, Mockito.times(1)).findByApplicationProfile(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).get(Mockito.anyString());
        Mockito.verify(fileSystemService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).delete(getApplicationEntity());
    }


    @Test
    @DisplayName("Get File System Application with passing an valid data")
    void testGet() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity());
        Mockito.when(fileSystemService.get(Mockito.anyString())).thenReturn(getFileSystemEntity());
        final String pkId = "12345";
        fileSystemApplicationService.get(pkId);
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(fileSystemService, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Update File System Application ")
    void testUpdate1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.get(Mockito.anyString())).thenThrow(notFound("Application"));
            fileSystemApplicationService.update(getFileSystemModel("FileSystem"));
        });
        Mockito.verify(manageProtocolService, Mockito.never()).deleteProtocol(Mockito.any(), Mockito.any());
        Mockito.verify(fileSystemService, Mockito.never()).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Update File System Application1 ")
    void testUpdate2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity());
            Mockito.when(fileSystemService.get(Mockito.anyString())).thenThrow(notFound("Protocol"));
            fileSystemApplicationService.update(getFileSystemModel("FileSystem"));
        });
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(fileSystemService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(fileSystemService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.never()).saveApplicationActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Update File System Application with a valid data")
    void testUpdate3() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity());
        Mockito.when(fileSystemService.get(Mockito.anyString())).thenReturn(getFileSystemEntity());
        Mockito.when(fileSystemService.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getFileSystemEntity());
        Mockito.when(applicationService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getApplicationEntity());
        fileSystemApplicationService.update(getFileSystemModel("FileSystem"));
        Mockito.verify(fileSystemService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).updateApplicationActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
    }

    @Test
    @DisplayName("Update File System Application with protocol change")
    void testUpdate4() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity());
        Mockito.when(fileSystemService.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getFileSystemEntity());
        Mockito.when(applicationService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getApplicationEntity());
        fileSystemApplicationService.update(getFileSystemModel("FTP"));
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(fileSystemService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).updateApplicationActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
    }

    @Test
    @DisplayName("Update File System Application with  protocol  not change")
    void testUpdate5() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity());
        Mockito.when(fileSystemService.get(Mockito.anyString())).thenReturn(getFileSystemEntity());
        fileSystemApplicationService.update(getFileSystemModel("FileSystem"));
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(fileSystemService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(fileSystemService, Mockito.never()).delete(Mockito.anyString());
    }

    FileSystemModel getFileSystemModel(String protocol) {
        FileSystemModel fileSystemModel = new FileSystemModel();
        fileSystemModel.setPkId("123456");
        fileSystemModel.setProfileName("ProfileName");
        fileSystemModel.setProfileId("ProfileId");
        fileSystemModel.setProtocol(protocol);
        fileSystemModel.setEmailId("Email@8.com");
        fileSystemModel.setPhone("857486784");
        fileSystemModel.setUserName("UserName");
        fileSystemModel.setPassword("password");
        fileSystemModel.setFileType("File1");
        fileSystemModel.setDeleteAfterCollection(false);
        fileSystemModel.setAdapterName("AdapterName");
        fileSystemModel.setPoolingInterval("poolingInterval");
        fileSystemModel.setStatus(false);
        fileSystemModel.setInDirectory("InDirectory");
        fileSystemModel.setOutDirectory("OutDirectory");
        fileSystemModel.setHubInfo(false);
        return fileSystemModel;
    }

    ApplicationEntity getApplicationEntity() {
        ApplicationEntity applicationEntity = new ApplicationEntity();
        applicationEntity.setAppDropFiles("AppDropfiles");
        applicationEntity.setAppIntegrationProtocol("FileSystem");
        applicationEntity.setAppIsActive("AppIsActive");
        applicationEntity.setApplicationName("ApplicationName");
        applicationEntity.setApplicationId("ApplicationId");
        applicationEntity.setAppPickupFiles("AppPickupFiles");
        applicationEntity.setAppProtocolRef("FileSystem");
        applicationEntity.setEmailId("Email@e.com");
        applicationEntity.setPhone("7598008944");
        applicationEntity.setPkId("12345");
        return applicationEntity;
    }

    FileSystemEntity getFileSystemEntity() {
        FileSystemEntity fileSystemEntity = new FileSystemEntity();
        fileSystemEntity.setPkId("123456");
        fileSystemEntity.setFileType("FileType");
        fileSystemEntity.setFsaAdapter("FsaAdapter");
        fileSystemEntity.setDeleteAfterCollection("DeleteAfterCollection");
        fileSystemEntity.setIsActive("IsActive");
        fileSystemEntity.setPassword("password");
        fileSystemEntity.setPoolingIntervalMins("PoolingIntervalMinus");
        return fileSystemEntity;
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
