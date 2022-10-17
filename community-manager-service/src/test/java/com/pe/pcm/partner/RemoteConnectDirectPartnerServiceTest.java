package com.pe.pcm.partner;


import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.b2b.B2BApiService;
import com.pe.pcm.b2b.B2BCdNodeService;
import com.pe.pcm.b2b.other.CaCertGetModel;
import com.pe.pcm.b2b.other.CipherSuiteNameGetModel;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.miscellaneous.UserUtilityService;
import com.pe.pcm.partner.entity.PartnerEntity;
import com.pe.pcm.protocol.RemoteCdModel;
import com.pe.pcm.protocol.RemoteConnectDirectService;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.remotecd.entity.RemoteConnectDirectEntity;
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
import org.mockito.Spy;
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
class RemoteConnectDirectPartnerServiceTest {
    @Mock
    private PartnerService partnerService;
    @Mock
    private RemoteConnectDirectService remoteConnectDirectService;
    @Mock
    private ActivityHistoryService activityHistoryService;
    @Mock
    private B2BApiService b2BApiService;
    @Mock
    private ManageProtocolService manageProtocolService;
    @Mock
    private ProcessService processService;
    @Mock
    private B2BCdNodeService b2BCdNodeService;
    @Mock
    private UserUtilityService userUtilityService;
    //@InjectMocks
    private RemoteConnectDirectPartnerService remoteConnectDirectPartnerService;

    @BeforeEach
    void inIt() {
        remoteConnectDirectPartnerService = new RemoteConnectDirectPartnerService(remoteConnectDirectService,
                b2BApiService,partnerService,processService,userUtilityService,manageProtocolService,
                activityHistoryService,b2BCdNodeService,"CM_Community");
    }

    @Test
    @DisplayName("Create a new CdPartner with unknown protocol")
    void testCreate() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            remoteConnectDirectPartnerService.save(generateCdModel("SFG_CONNECT_DIRECT1"));
        });
        Mockito.verify(partnerService, Mockito.never()).find(Mockito.any());
    }

    @Test
    @DisplayName("Create a new CdPartner with known protocol")
    void testCreate1() {
        remoteConnectDirectPartnerService.save(generateCdModel("SFG_CONNECT_DIRECT"));
        Mockito.verify(partnerService, Mockito.times(1)).find(Mockito.anyString());
    }

    @Test
    @DisplayName("Create a new CdPartner passing null in protocol")
    void testCreate2() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            remoteConnectDirectPartnerService.save(generateCdModel(null));
        });
        Mockito.verify(partnerService, Mockito.never()).find(Mockito.any());

    }

    @Test
    @DisplayName("Create a new CdPartner passing with duplicate partner ")
    void testCreate3() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.find(Mockito.anyString())).thenReturn(Optional.ofNullable(getPartnerEntity()));
            remoteConnectDirectPartnerService.save(generateCdModel("SFG_CONNECT_DIRECT"));
        });
        Mockito.verify(partnerService, Mockito.times(1)).find(Mockito.anyString());
        Mockito.verify(remoteConnectDirectService, Mockito.never()).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());

    }

    @Test
    @DisplayName("Create a new CdPartner with an valid data")
    void testCreate4() {
        Mockito.when(partnerService.find(Mockito.anyString())).thenReturn(Optional.empty());
        remoteConnectDirectPartnerService.save(generateCdModel("SFG_CONNECT_DIRECT"));
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).savePartnerActivity(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(b2BApiService, Mockito.never()).createMailBoxInSI(Mockito.anyBoolean(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(userUtilityService, Mockito.times(1)).addPartnerToCurrentUser(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Delete CdPartner")
    void testDelete() {
        Mockito.when(processService.findByPartnerProfile(Mockito.anyString())).thenReturn(new ArrayList<>());
        Mockito.when(partnerService.findPartnerById(Mockito.anyString())).thenReturn(getPartnerEntity());
        String pkId = "123456";
        remoteConnectDirectPartnerService.delete(pkId, false);
        Mockito.verify(processService, Mockito.times(1)).findByPartnerProfile(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).findPartnerById(Mockito.anyString());
        Mockito.verify(remoteConnectDirectService, Mockito.times(1)).delete(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).delete(Mockito.any(PartnerEntity.class));
    }

    @Test
    @DisplayName("Delete not Existing CdPartner ")
    void testDelete1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(processService.findByPartnerProfile(Mockito.anyString())).thenReturn(new ArrayList<>());
            Mockito.when(partnerService.findPartnerById(Mockito.anyString())).thenThrow(notFound("Partner"));
            String pkId = "123456";
            remoteConnectDirectPartnerService.delete(pkId, false);
        });
        Mockito.verify(remoteConnectDirectService, Mockito.never()).get(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).findPartnerById(Mockito.anyString());
        Mockito.verify(remoteConnectDirectService, Mockito.never()).delete(Mockito.any());
        Mockito.verify(partnerService, Mockito.never()).delete(Mockito.any());
    }

    @Test
    @DisplayName("Delete CdPartner While WorkFlow Exists")
    void testDelete3() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(processService.findByPartnerProfile(Mockito.anyString())).thenReturn(getProcessEntities());
            String pkId = "123456";
            remoteConnectDirectPartnerService.delete(pkId, false);
        });
        Mockito.verify(partnerService, Mockito.never()).findPartnerById(Mockito.any());
        Mockito.verify(remoteConnectDirectService, Mockito.never()).delete(Mockito.any());
        Mockito.verify(partnerService, Mockito.never()).delete(Mockito.any());
    }

    @Test
    @DisplayName("Get CdPartner With not Existing Protocol")
    void testGetPartner1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(remoteConnectDirectService.get(Mockito.anyString())).thenThrow(notFound("Protocol"));
            remoteConnectDirectPartnerService.get(Mockito.anyString());
        });
        Mockito.verify(remoteConnectDirectService, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Get CdPartner with not Existing partner")
    void testGetPartner2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenThrow(notFound("Partner"));
            final String pkId = "123456";
            remoteConnectDirectPartnerService.get(pkId);
        });
        Mockito.verify(remoteConnectDirectService, Mockito.never()).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Get CdPartner with Passing Null for ftp service")
    void testGetPartner3() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
            Mockito.when(remoteConnectDirectService.get(Mockito.anyString())).thenThrow(notFound("Protocol"));
            final String pkId = "123456";
            remoteConnectDirectPartnerService.get(pkId);
        });
        Mockito.verify(remoteConnectDirectService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(remoteConnectDirectService, Mockito.never()).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Get CdPartner with Passing Null for Partner service")
    void testGetPartner4() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenThrow(notFound("Partner"));
            final String pkId = "123456";
            remoteConnectDirectPartnerService.get(pkId);
        });
    }

    @Test
    @DisplayName("Get CdPartner with Passing valid data")
    void testGetPartner5() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(remoteConnectDirectService.get(Mockito.anyString())).thenReturn(getCdEntity());
        Mockito.when(b2BCdNodeService.getNodeInSI(Mockito.any())).thenReturn(generateCdModel("SFG_CONNECT_DIRECT"));
        final String pkId = "123456";
        RemoteCdModel remoteCdModel = remoteConnectDirectPartnerService.get(pkId);
        Mockito.verify(partnerService, Mockito.times(1)).get(pkId);
        Mockito.verify(remoteConnectDirectService, Mockito.times(1)).get(pkId);
        Assertions.assertEquals(remoteCdModel.getPoolingInterval(), "23");
        Assertions.assertEquals(remoteCdModel.getProfileName(), "tpname");
    }

    @Test
    @DisplayName("Update Cd Partner")
    void testUpdateCdPartner() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(remoteConnectDirectService.get(Mockito.anyString())).thenReturn(getCdEntity());
        remoteConnectDirectPartnerService.update(generateCdModel("SFG_CONNECT_DIRECT"));
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(remoteConnectDirectService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(remoteConnectDirectService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(b2BCdNodeService, Mockito.never()).updateNodeInSI(generateCdModel("SFG_CONNECT_DIRECT"), "nodeName");
        Mockito.verify(activityHistoryService, Mockito.never()).updatePartnerActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
        Mockito.verify(b2BApiService, Mockito.never()).createMailBoxInSI(Mockito.anyBoolean(), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Update Cd Partner")
    void testUpdateCdPartner1() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        remoteConnectDirectPartnerService.update(generateCdModel("FTP"));
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(remoteConnectDirectService, Mockito.never()).get(Mockito.anyString());
        Mockito.verify(remoteConnectDirectService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(manageProtocolService,Mockito.times(1)).deleteProtocol(Mockito.anyString(),Mockito.anyString());
        Mockito.verify(b2BCdNodeService, Mockito.times(1)).createNodeInSI(Mockito.any(RemoteCdModel.class));
        Mockito.verify(activityHistoryService, Mockito.never()).updatePartnerActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
        Mockito.verify(b2BApiService, Mockito.never()).createMailBoxInSI(Mockito.anyBoolean(), Mockito.anyString(), Mockito.anyString());
    }

    RemoteCdModel generateCdModel(String protocol) {
        List<CaCertGetModel> caCertGetModels = new ArrayList<>();
        caCertGetModels.add(new CaCertGetModel().setCaCertName("CacertName"));
        caCertGetModels.add(new CaCertGetModel().setCaCertName("Cacertname1"));
        List<CipherSuiteNameGetModel> cipherSuiteNameGetModels = new ArrayList<>();
        cipherSuiteNameGetModels.add(new CipherSuiteNameGetModel().setCipherSuiteName("EdiCipherSuite"));
        cipherSuiteNameGetModels.add(new CipherSuiteNameGetModel().setCipherSuiteName("EdiCipherSuite1"));
        RemoteCdModel remoteCdModel = new RemoteCdModel();
        remoteCdModel.setNodeName("NodeName");
        remoteCdModel.setPkId("12345");
        remoteCdModel.setProfileName("ProfileName");
        remoteCdModel.setProfileId("ProfileId");
        remoteCdModel.setAddressLine1("AddressLine1");
        remoteCdModel.setAddressLine2("AddressLine2");
        remoteCdModel.setProtocol(protocol);
        remoteCdModel.setEmailId("email@e.com");
        remoteCdModel.setPhone("91743284903");
        remoteCdModel.setStatus(false);
        remoteCdModel.setCaCertName(caCertGetModels);
        remoteCdModel.setCipherSuits(cipherSuiteNameGetModels);
        remoteCdModel.setHubInfo(false);
        return remoteCdModel;
    }

    PartnerEntity getPartnerEntity() {
        PartnerEntity partnerEntity = new PartnerEntity();
        partnerEntity.setPkId("123456");
        partnerEntity.setTpId("1234567");
        partnerEntity.setTpName("tpname");
        partnerEntity.setTpProtocol("SFG_CONNECT_DIRECT");
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

    RemoteConnectDirectEntity getCdEntity() {
        RemoteConnectDirectEntity remoteConnectDirectEntity = new RemoteConnectDirectEntity();
        remoteConnectDirectEntity.setPort("9080");
        remoteConnectDirectEntity.setAdapterName("AdapterName");
        remoteConnectDirectEntity.setDirectory("InDirectory");
        remoteConnectDirectEntity.setIsActive("IsActive");
        remoteConnectDirectEntity.setLocalNodeName("LnodeName");
        remoteConnectDirectEntity.setIsHubInfo("IsHub");
        remoteConnectDirectEntity.setPkId("123456");
        remoteConnectDirectEntity.setPoolingIntervalMins("23");
        remoteConnectDirectEntity.setRemoteFileName("RemoteFileName");
        remoteConnectDirectEntity.setSubscriberId("SubscriberId");
        remoteConnectDirectEntity.setSubscriberType("SubscriberType");
        remoteConnectDirectEntity.setCreatedBy("CreatedBy");
        remoteConnectDirectEntity.setCaCertificateName("CaCertName1,CaCert2,CaCert3");
        remoteConnectDirectEntity.setCipherSuits("CipherSuite1,CipherSuite2,CipherSuite3");
        return remoteConnectDirectEntity;
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
