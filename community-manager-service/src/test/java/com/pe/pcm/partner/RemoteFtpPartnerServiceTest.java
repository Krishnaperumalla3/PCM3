package com.pe.pcm.partner;

import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.b2b.B2BRemoteFtpService;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.miscellaneous.UserUtilityService;
import com.pe.pcm.partner.entity.PartnerEntity;
import com.pe.pcm.partner.entity.RemotePartnerStagingEntity;
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

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class RemoteFtpPartnerServiceTest {

    @Mock
    private PartnerService partnerService;
    @Mock
    private RemoteFtpService remoteFtpService;
    @Mock
    private ActivityHistoryService activityHistoryService;
    @Mock
    private B2BRemoteFtpService b2BRemoteFtpService;
    @Mock
    private ProcessService processService;
    @Mock
    private ManageProtocolService manageProtocolService;
    @Mock
    private UserUtilityService userUtilityService;
    //@InjectMocks
    private RemoteFtpPartnerService remoteFtpPartnerService;

    @BeforeEach
    void inIt() {
        remoteFtpPartnerService = new RemoteFtpPartnerService(partnerService,remoteFtpService,activityHistoryService,
                processService,manageProtocolService,userUtilityService);
    }

    @Test
    @DisplayName("Save Remote Ftp PartnerService")
    public void testSave() {
        Mockito.when(partnerService.find(Mockito.anyString())).thenReturn(Optional.ofNullable(null));
        remoteFtpPartnerService.save(generateRemoteFtpModel("SFGFTP"));
        Mockito.verify(partnerService, Mockito.times(1)).find(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).savePartnerActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Save Remote Ftp PartnerService with Existing Partner Error")
    public void testSave1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.find(Mockito.anyString())).thenReturn(Optional.ofNullable(getPartnerEntity()));
            remoteFtpPartnerService.save(generateRemoteFtpModel("SFGFTP"));
        });
        Mockito.verify(partnerService, Mockito.times(1)).find(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.never()).savePartnerActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Save Remote Ftp PartnerService With Unknown Protocol")
    public void testSave2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            remoteFtpPartnerService.save(generateRemoteFtpModel("SFGFTP!"));
        });
        Mockito.verify(partnerService, Mockito.never()).find(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.never()).savePartnerActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Update Not-Existing RemoteFtpPartner")
    public void testUpdate() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenThrow(notFound("Partner"));
            remoteFtpPartnerService.update(generateRemoteFtpModel("SFGFTP"));
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(remoteFtpService, Mockito.never()).get(Mockito.anyString());
    }


    @Test
    @DisplayName("Update RemoteFtpPartner with Not-Existing Protocol")
    public void testUpdate2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenThrow(notFound("Protocol"));
            remoteFtpPartnerService.update(generateRemoteFtpModel("SFGFTP"));
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(remoteFtpService, Mockito.never()).get(Mockito.anyString());
        Mockito.verify(remoteFtpService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.never()).updatePartnerActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
    }

    @Test
    @DisplayName("Update RemoteFtpPartner with valid data")
    public void testUpdate4() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(remoteFtpService.get(Mockito.anyString())).thenReturn(getRemoteFtpEntity("N"));
        Mockito.when(remoteFtpService.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyBoolean(), Mockito.anyString(), Mockito.anyString())).thenReturn(getRemoteFtpEntity("N"));
        Mockito.when(partnerService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getPartnerEntity());
        remoteFtpPartnerService.update(generateRemoteFtpModel("SFGFTP"));
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(remoteFtpService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(remoteFtpService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyBoolean(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).updatePartnerActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
    }

    @Test
    @DisplayName("Update with Protocol change")
    public void testUpdate5() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(remoteFtpService.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyBoolean(), Mockito.anyString(), Mockito.anyString())).thenReturn(getRemoteFtpEntity("N"));
        Mockito.when(partnerService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getPartnerEntity());
        remoteFtpPartnerService.update(generateRemoteFtpModel("SFGFTPS"));
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(remoteFtpService, Mockito.never()).get(Mockito.anyString());
        Mockito.verify(remoteFtpService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(remoteFtpService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyBoolean(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).updatePartnerActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
    }

    @Test
    @DisplayName("Update with Protocol not change")
    public void testUpdate6() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(remoteFtpService.get(Mockito.anyString())).thenReturn(getRemoteFtpEntity("N"));
        remoteFtpPartnerService.update(generateRemoteFtpModel("SFGFTP"));
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(remoteFtpService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(remoteFtpService, Mockito.never()).delete(Mockito.anyString());
    }

    @Test
    @DisplayName("Get Remote Ftp Partner with isHubInfo FALSE")
    public void testGet() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(remoteFtpService.get(Mockito.anyString())).thenReturn(getRemoteFtpEntity("N"));
        remoteFtpPartnerService.get(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(remoteFtpService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(remoteFtpService, Mockito.times(1)).getRemoteFtp(Mockito.any());
    }

    @Test
    @DisplayName("Get Remote Ftp Partner with isHubInfo TRUE")
    public void testGet1() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(remoteFtpService.get(Mockito.anyString())).thenReturn(getRemoteFtpEntity("y"));
        remoteFtpPartnerService.get(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(remoteFtpService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(remoteFtpService, Mockito.never()).getRemoteFtp(Mockito.any());
    }

    @Test
    @DisplayName("Get Remote Ftp Partner with No Partner")
    public void testGet2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenThrow(notFound("Partner"));
            remoteFtpPartnerService.get(Mockito.anyString());
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(remoteFtpService, Mockito.never()).get(Mockito.anyString());
        Mockito.verify(remoteFtpService, Mockito.never()).getRemoteFtp(Mockito.any());
    }

    @Test
    @DisplayName("Get Remote Ftp Partner with No Protocol")
    public void testGet3() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
            Mockito.when(remoteFtpService.get(Mockito.anyString())).thenThrow(notFound("Protocol"));
            ;
            remoteFtpPartnerService.get(Mockito.anyString());
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(remoteFtpService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(remoteFtpService, Mockito.never()).getRemoteFtp(Mockito.any());
    }

    @Test
    @DisplayName("Delete RemoteFtpPartner")
    public void testDelete() {
        Mockito.when(processService.findByPartnerProfile(Mockito.anyString())).thenReturn(new ArrayList<>());
        Mockito.when(partnerService.findPartnerById(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(remoteFtpService.get(Mockito.anyString())).thenReturn(getRemoteFtpEntity("N"));
        final String PkId = "123456";
        remoteFtpPartnerService.delete(PkId, true, true, true, true);
        Mockito.verify(processService, Mockito.times(1)).findByPartnerProfile(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).findPartnerById(Mockito.anyString());
        Mockito.verify(remoteFtpService, Mockito.never()).deleteProtocol(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyBoolean());
        Mockito.verify(partnerService, Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    @DisplayName("Delete not Existing RemoteFtpPartner")
    public void testDelete1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.findPartnerById(Mockito.anyString())).thenThrow(notFound("Partner"));
            final String PkId = "123456";
            remoteFtpPartnerService.delete(PkId, false, false, false, false);
        });
        Mockito.verify(partnerService, Mockito.times(1)).findPartnerById(Mockito.anyString());
        Mockito.verify(remoteFtpService, Mockito.never()).deleteProtocol(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyBoolean());
        Mockito.verify(partnerService, Mockito.never()).delete(Mockito.any());
    }

    @Test
    @DisplayName("Delete not Existing RemoteFtpPartner")
    public void testDelete2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(processService.findByPartnerProfile(Mockito.anyString())).thenReturn(getProcessEntities());
            final String PkId = "123456";
            remoteFtpPartnerService.delete(PkId, false, false, false, false);
        });
        Mockito.verify(processService, Mockito.times(1)).findByPartnerProfile(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).findPartnerById(Mockito.anyString());
        Mockito.verify(remoteFtpService, Mockito.never()).deleteProtocol(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyBoolean());
        Mockito.verify(partnerService, Mockito.never()).delete(Mockito.any());
    }

    @Test
    @DisplayName("Save Remote Staging Profile")
    public void testSaveRemoteStagingProfile() {
        remoteFtpPartnerService.saveRemoteStagingProfile(getRemotePartnerStagingEntity(), getRemoteFtpEntity("N"));
        Mockito.verify(remoteFtpService, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(b2BRemoteFtpService, Mockito.never()).saveRemoteFtpProfile(Mockito.any(), Mockito.anyBoolean(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).savePartnerActivity(Mockito.anyString(), Mockito.anyString());
    }

    RemoteProfileModel generateRemoteFtpModel(String protocol) {
        RemoteProfileModel remoteProfileModel = new RemoteProfileModel();
        remoteProfileModel.setPkId("123456");
        remoteProfileModel.setProfileName("ProfileName");
        remoteProfileModel.setProfileId("123456");
        remoteProfileModel.setAddressLine1("AddressLine1");
        remoteProfileModel.setAddressLine2("AddressLine2");
        remoteProfileModel.setProtocol(protocol);
        remoteProfileModel.setEmailId("Email@Email.com");
        remoteProfileModel.setPhone("9876543210");
        remoteProfileModel.setStatus(false);
        remoteProfileModel.setUserName("UserId");
        remoteProfileModel.setPassword("Password");
        remoteProfileModel.setTransferType("TransferType");
        //Start : Extra adding for Bulk uploads
        remoteProfileModel.setRemoteHost("RemoteHost");
        remoteProfileModel.setRemotePort("52.30.62.3");
        remoteProfileModel.setCertificateId("certificateId");
        remoteProfileModel.setUserIdentityKey("IdentityKey");
        remoteProfileModel.setKnownHostKey("KnownHost");
        remoteProfileModel.setConnectionType("ConnectionType");
        remoteProfileModel.setEncryptionStrength("Strength");
        remoteProfileModel.setRetryInterval("2");
        remoteProfileModel.setNoOfRetries("2");
        remoteProfileModel.setUseCCC(false);
        remoteProfileModel.setUseImplicitSSL(false);
        //END : Extra adding for Bulk uploads
        remoteProfileModel.setInDirectory("/Indirectory");
        remoteProfileModel.setOutDirectory("/OutDirectory");
        remoteProfileModel.setFileType("FileType");
        remoteProfileModel.setDeleteAfterCollection(false);
        remoteProfileModel.setAdapterName("AdapterName");
        remoteProfileModel.setPoolingInterval("22");
        remoteProfileModel.setHubInfo(false);
        remoteProfileModel.setSubscriberType("TP");
        return remoteProfileModel;
    }

    PartnerEntity getPartnerEntity() {
        PartnerEntity partnerEntity = new PartnerEntity();
        partnerEntity.setPkId("123456");
        partnerEntity.setTpId("123456");
        partnerEntity.setTpName("tpname");
        partnerEntity.setTpProtocol("SFGFTP");
        partnerEntity.setPartnerProtocolRef("123456");
        partnerEntity.setEmail("email@email.com");
        partnerEntity.setPhone("987654321");
        partnerEntity.setTpPickupFiles("Y");
        partnerEntity.setFileTpServer("TpServer");
        partnerEntity.setPartnerProtocolRef("ProtocolRef");
        partnerEntity.setStatus("Y");
        partnerEntity.setIsProtocolHubInfo("y");
        return partnerEntity;
    }

    RemoteFtpEntity getRemoteFtpEntity(String IsHubInfo) {
        RemoteFtpEntity remoteFtpEntity = new RemoteFtpEntity();
        remoteFtpEntity.setProfileId("123456");
        remoteFtpEntity.setPkId("123456");
        remoteFtpEntity.setAdapterName("AdapterName");
        remoteFtpEntity.setHostName("HostName");
        remoteFtpEntity.setIsHubInfo(IsHubInfo);
        remoteFtpEntity.setUserId("pragma");
        return remoteFtpEntity;
    }

    RemotePartnerStagingEntity getRemotePartnerStagingEntity() {
        RemotePartnerStagingEntity remotePartnerStagingEntity = new RemotePartnerStagingEntity();
        remotePartnerStagingEntity.setPkId("123456");
        remotePartnerStagingEntity.setTpId("123456");
        remotePartnerStagingEntity.setTpProtocol("SFGFTP");
        return remotePartnerStagingEntity;
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
