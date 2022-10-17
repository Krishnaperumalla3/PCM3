package com.pe.pcm.partner;

import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.miscellaneous.PasswordUtilityService;
import com.pe.pcm.miscellaneous.UserUtilityService;
import com.pe.pcm.partner.entity.PartnerEntity;
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

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;


@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
 class FileSystemPartnerServiceTest {

    @Mock
    private PartnerService partnerService;
    @Mock
    private FileSystemService fIlesystemService;
    @Mock
    private ActivityHistoryService activityHistoryService;
    @Mock
    private ProcessService processService;
    @Mock
    private ManageProtocolService manageProtocolService;
    @Mock
    private UserUtilityService userUtilityService;
    @Mock
    private SterlingRoutingRuleService sterlingRoutingRuleService;
    @Mock
    private SterlingMailboxService sterlingMailboxService;
    @Mock
    private PasswordUtilityService passwordUtilityService;
    //@InjectMocks
    private FileSystemPartnerService fileSystemPartnerService;

    @BeforeEach
    void inIt() {
        fileSystemPartnerService = new FileSystemPartnerService(partnerService,fIlesystemService,
                activityHistoryService,processService,manageProtocolService,userUtilityService,
                sterlingRoutingRuleService,sterlingMailboxService,passwordUtilityService);
    }

    @Test
    @DisplayName("Create a new FileSystem Partner With Unknown Protocol")
     void testCreate() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            fileSystemPartnerService.save(generateFileSystemModel("FileSystem1"));
        });
        Mockito.verify(partnerService, Mockito.never()).find(Mockito.any());
    }

    @Test
    @DisplayName("Create a new FileSystem Partner passing null in Protocol")
     void testCreate1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            fileSystemPartnerService.save(generateFileSystemModel(null));
        });
        Mockito.verify(userUtilityService,Mockito.never()).addPartnerToCurrentUser(Mockito.anyString(),Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).find(Mockito.any());
        Mockito.verify(fIlesystemService, Mockito.never()).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.never()).savePartnerActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Create a new FileSystem Partner With known Protocol")
     void testCreate2() {
        fileSystemPartnerService.save(generateFileSystemModel("FileSystem"));
        Mockito.verify(userUtilityService,Mockito.times(1)).addPartnerToCurrentUser(Mockito.anyString(),Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).find(Mockito.any());
        Mockito.verify(fIlesystemService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).savePartnerActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Create a new FileSystem Partner With Duplicate Partner")
     void testCreate3() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.find(Mockito.anyString())).thenReturn(Optional.ofNullable(getPartnerEntity()));
            fileSystemPartnerService.save(generateFileSystemModel("FileSystem"));
        });
        Mockito.verify(partnerService, Mockito.times(1)).find(Mockito.anyString());
        Mockito.verify(fIlesystemService, Mockito.never()).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.never()).savePartnerActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Create a new FileSystem Partner With Vaild Partner")
     void testCreate4() {
        Mockito.when(partnerService.find(Mockito.anyString())).thenReturn(Optional.empty());
        fileSystemPartnerService.save(generateFileSystemModel("FileSystem"));
        Mockito.verify(partnerService, Mockito.times(1)).find(Mockito.any());
        Mockito.verify(fIlesystemService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).savePartnerActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Delete FileSystem Partner")
     void testDelete() {
        Mockito.when(processService.findByPartnerProfile(Mockito.anyString())).thenReturn(new ArrayList<>());
        Mockito.when(partnerService.findPartnerById(Mockito.anyString())).thenReturn(getPartnerEntity());
        final String PkId = "123456";
        fileSystemPartnerService.delete(PkId);
        Mockito.verify(processService, Mockito.times(1)).findByPartnerProfile(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).findPartnerById(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).delete(Mockito.any());
        Mockito.verify(fIlesystemService, Mockito.times(1)).delete(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete Existing FileSystem Partner with Existing WorkFlow")
     void testDelete1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(processService.findByPartnerProfile(Mockito.anyString())).thenReturn(getProcessEntities());
            final String PkId = "123456";
            fileSystemPartnerService.delete(PkId);
        });
        Mockito.verify(processService, Mockito.times(1)).findByPartnerProfile(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).findPartnerById(Mockito.any());
        Mockito.verify(partnerService, Mockito.never()).delete(Mockito.any());
        Mockito.verify(fIlesystemService, Mockito.never()).delete(Mockito.anyString());
    }


    @Test
    @DisplayName("Get FileSystem Partner With not Existing Protocol")
     void testGetPartner1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
            Mockito.when(fIlesystemService.get(Mockito.anyString())).thenThrow(notFound("Protocol"));
            fileSystemPartnerService.get(Mockito.anyString());
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(fIlesystemService, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Get FileSystem Partner With not Existing Protocol and Partner")
     void testGetPartner2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenThrow(notFound("Protocol"));
            fileSystemPartnerService.get(Mockito.anyString());
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(fIlesystemService, Mockito.never()).get(Mockito.anyString());
    }


    @Test
    @DisplayName("Get FileSystem Partner with not Existing partner")
     void testGetPartner3() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenThrow(notFound("Partner"));
            final String pkId = "123456";
            fileSystemPartnerService.get(pkId);
        });
        Mockito.verify(fIlesystemService, Mockito.never()).get(Mockito.anyString());
    }


    @Test
    @DisplayName("Get FileSystem Partner with Passing valid data")
     void testGetPartner4() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(fIlesystemService.get(Mockito.anyString())).thenReturn(getFileSystemEntity());
        final String pkId = "123456";
        FileSystemModel fileSystemModel = fileSystemPartnerService.get(pkId);
        Mockito.verify(partnerService, Mockito.times(1)).get(pkId);
        Mockito.verify(fIlesystemService, Mockito.times(1)).get(pkId);
        Assertions.assertEquals(fileSystemModel.getPoolingInterval(), "22");
        Assertions.assertEquals(fileSystemModel.getPassword(), "Password");
    }


    @Test
    @DisplayName("Update Not-Existing FileSystem Partner")
     void testUpdate1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenThrow(notFound("Partner"));
            fileSystemPartnerService.update(generateFileSystemModel("FileSystem"));
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(fIlesystemService, Mockito.never()).get(Mockito.anyString());
    }


    @Test
    @DisplayName("Update FileSystem Partner with Not-Existing Protocol")
     void testUpdate2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenThrow(notFound("Partner"));
            fileSystemPartnerService.update(generateFileSystemModel("FileSystem"));
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(fIlesystemService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.never()).updatePartnerActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
    }

    @Test
    @DisplayName("Update FileSystem Partner with valid data")
     void testUpdate3() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity().setTpId("6986896"));
        Mockito.when(fIlesystemService.get(Mockito.anyString())).thenReturn(getFileSystemEntity());
        Mockito.when(fIlesystemService.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getFileSystemEntity());
        Mockito.when(partnerService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getPartnerEntity());
        fileSystemPartnerService.duplicatePartner(Mockito.anyString());
        fileSystemPartnerService.update(generateFileSystemModel("FileSystem"));
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(fIlesystemService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(fIlesystemService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).updatePartnerActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
    }

    @Test
    @DisplayName("Update with Protocol change")
     void testUpdate4() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(fIlesystemService.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getFileSystemEntity());
        Mockito.when(partnerService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getPartnerEntity());
        fileSystemPartnerService.update(generateFileSystemModel("FTP"));
        Mockito.verify(manageProtocolService,Mockito.times(1)).deleteProtocol(Mockito.anyString(),Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(fIlesystemService, Mockito.never()).get(Mockito.anyString());
        Mockito.verify(fIlesystemService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(fIlesystemService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).updatePartnerActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
    }

    @Test
    @DisplayName("Update with Protocol not change")
     void testUpdate5() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(fIlesystemService.get(Mockito.anyString())).thenReturn(getFileSystemEntity());
        fileSystemPartnerService.update(generateFileSystemModel("FileSystem"));
        Mockito.verify(manageProtocolService,Mockito.never()).deleteProtocol(Mockito.anyString(),Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(fIlesystemService, Mockito.times(1)).get(Mockito.anyString());
    }

    FileSystemModel generateFileSystemModel(String protocol) {
        FileSystemModel fileSystemModel = new FileSystemModel();
        fileSystemModel.setPkId("123456");
        fileSystemModel.setProfileName("ProfileName");
        fileSystemModel.setProfileId("123456");
        fileSystemModel.setPkId("123456");
        fileSystemModel.setAddressLine1("Addressline1");
        fileSystemModel.setAddressLine2("Addressline2");
        fileSystemModel.setProtocol(protocol);
        fileSystemModel.setEmailId("Email@email.com");
        fileSystemModel.setPhone("9876543210");
        fileSystemModel.setAdapterName("FileAdapter");
        fileSystemModel.setUserName("UserName");
        fileSystemModel.setPassword("Password");
        fileSystemModel.setFileType("FileType");
        fileSystemModel.setDeleteAfterCollection(false);
        fileSystemModel.setAdapterName("FsAdapter");
        fileSystemModel.setPoolingInterval("22");
        fileSystemModel.setStatus(false);
        fileSystemModel.setInDirectory("/Indirectory");
        fileSystemModel.setOutDirectory("/OutDirectory");
        fileSystemModel.setHubInfo(false);
        return fileSystemModel;
    }


    PartnerEntity getPartnerEntity() {
        PartnerEntity partnerEntity = new PartnerEntity();
        partnerEntity.setPkId("123456");
        partnerEntity.setTpId("123456");
        partnerEntity.setTpName("tpname");
        partnerEntity.setTpProtocol("FileSystem");
        partnerEntity.setPartnerProtocolRef("123456");
        partnerEntity.setEmail("email@email.com");
        partnerEntity.setPhone("987654321");
        partnerEntity.setTpPickupFiles("Y");
        partnerEntity.setFileTpServer("TpServer");
        partnerEntity.setPartnerProtocolRef("ProtocolRef");
        partnerEntity.setStatus("Y");
        partnerEntity.setIsProtocolHubInfo("HubInfo");
        return partnerEntity;
    }

    FileSystemEntity getFileSystemEntity() {
        FileSystemEntity fileSystemEntity = new FileSystemEntity();
        fileSystemEntity.setPkId("123456");
        fileSystemEntity.setSubscriberId("654321");
        fileSystemEntity.setSubscriberType("TP");
        fileSystemEntity.setPoolingIntervalMins("22");
        fileSystemEntity.setPassword("Password");
        return fileSystemEntity;
    }

    ProcessEntity getProcessEntity() {
        ProcessEntity processEntity = new ProcessEntity();
        processEntity.setApplicationProfile("AppProfile");
        processEntity.setPartnerProfile("PartnerProfile");
        processEntity.setSeqId("123");
        return processEntity;
    }

    List<ProcessEntity> getProcessEntities() {
        List<ProcessEntity> processEntities = new ArrayList<>();
        processEntities.add(getProcessEntity());
        return processEntities;
    }
}
