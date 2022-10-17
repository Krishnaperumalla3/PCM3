package com.pe.pcm.partner;

import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.miscellaneous.UserUtilityService;
import com.pe.pcm.partner.entity.PartnerEntity;
import com.pe.pcm.protocol.EcModel;
import com.pe.pcm.protocol.EcService;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.ec.entity.EcEntity;
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
class EcPartnerServiceTest {

    @Mock
    private PartnerService partnerService;
    @Mock
    private EcService ecService;
    @Mock
    private ActivityHistoryService activityHistoryService;
    @Mock
    private ProcessService processService;
    @Mock
    private ManageProtocolService manageProtocolService;
    @Mock
    private UserUtilityService userUtilityService;
    //@InjectMocks
    private EcPartnerService ecPartnerService;

    @BeforeEach
    void inIt() {
        ecPartnerService = new EcPartnerService(partnerService,ecService,activityHistoryService,
                processService,manageProtocolService,userUtilityService);
    }

    @Test
    @DisplayName("Create a new EcPartner With Unknown Protocol")
    void testCreate() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            ecPartnerService.save(generateEcModel("ExistingConnection1"));
        });
        Mockito.verify(partnerService, Mockito.never()).find(Mockito.any());
    }

    @Test
    @DisplayName("Create a new EcPartner passing null in Protocol")
    void testCreate1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> ecPartnerService.save(generateEcModel(null)));
        Mockito.verify(partnerService, Mockito.never()).find(Mockito.any());
        Mockito.verify(ecService, Mockito.never()).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.never()).savePartnerActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Create a new EcPartner With known Protocol")
    void testCreate2() {
        ecPartnerService.save(generateEcModel("ExistingConnection"));
        Mockito.verify(partnerService, Mockito.times(1)).find(Mockito.any());
        Mockito.verify(ecService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(userUtilityService,Mockito.times(1)).addPartnerToCurrentUser(Mockito.anyString(),Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).savePartnerActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Create a new EcPartner With Duplicate Partner")
    void testCreate3() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.find(Mockito.anyString())).thenReturn(Optional.ofNullable(getPartnerEntity()));
            ecPartnerService.save(generateEcModel("ExistingConnection"));
        });
        Mockito.verify(partnerService, Mockito.times(1)).find(Mockito.anyString());
        Mockito.verify(ecService, Mockito.never()).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(userUtilityService, Mockito.never()).addPartnerToCurrentUser(Mockito.anyString(),Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.never()).savePartnerActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Create a new EcPartner With Valid Partner")
    void testCreate4() {
        Mockito.when(partnerService.find(Mockito.anyString())).thenReturn(Optional.empty());
        ecPartnerService.save(generateEcModel("ExistingConnection"));
        Mockito.verify(partnerService, Mockito.times(1)).find(Mockito.any());
        Mockito.verify(ecService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).savePartnerActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Delete EcPartner")
    void testDelete() {
        Mockito.when(processService.findByPartnerProfile(Mockito.anyString())).thenReturn(new ArrayList<>());
        Mockito.when(partnerService.findPartnerById(Mockito.anyString())).thenReturn(getPartnerEntity());
        final String PkId = "123456";
        ecPartnerService.delete(PkId);
        Mockito.verify(processService, Mockito.times(1)).findByPartnerProfile(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).findPartnerById(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).delete(Mockito.any());
        Mockito.verify(ecService, Mockito.times(1)).delete(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete not Existing EcPartner")
    void testDelete2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(processService.findByPartnerProfile(Mockito.anyString())).thenReturn(getProcessEntities());
            final String PkId = "123456";
            ecPartnerService.delete(PkId);
        });
        Mockito.verify(processService, Mockito.times(1)).findByPartnerProfile(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).findPartnerById(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).delete(Mockito.any());
        Mockito.verify(ecService, Mockito.never()).delete(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete not Existing EcPartner")
    void testDelete1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.findPartnerById(Mockito.anyString())).thenThrow(notFound("Partner"));
            final String PkId = "123456";
            ecPartnerService.delete(PkId);
        });
        Mockito.verify(partnerService, Mockito.times(1)).findPartnerById(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).delete(Mockito.any());
        Mockito.verify(ecService, Mockito.never()).delete(Mockito.anyString());
    }

    @Test
    @DisplayName("Get EcPartner With not Existing Protocol")
    void testGetPartner1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
            Mockito.when(ecService.get(Mockito.anyString())).thenThrow(notFound("Protocol"));
            ecPartnerService.get(Mockito.anyString());
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(ecService, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Get EcPartner With not Existing Protocol and Partner")
    void testGetPartner2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenThrow(notFound("Protocol"));
            ecPartnerService.get(Mockito.anyString());
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(ecService, Mockito.never()).get(Mockito.anyString());
    }


    @Test
    @DisplayName("Get EcPartner with not Existing partner")
    void testGetPartner3() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenThrow(notFound("Partner"));
            final String pkId = "123456";
            ecPartnerService.get(pkId);
        });
        Mockito.verify(ecService, Mockito.never()).get(Mockito.anyString());
    }


    @Test
    @DisplayName("Get EcPartner with Passing valid data")
    void testGetPartner4() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(ecService.get(Mockito.anyString())).thenReturn(getEcEntity());
        final String pkId = "123456";
        EcModel ecModel = ecPartnerService.get(pkId);
        Mockito.verify(partnerService, Mockito.times(1)).get(pkId);
        Mockito.verify(ecService, Mockito.times(1)).get(pkId);
        Assertions.assertEquals(ecModel.getEmailId(), "email@email.com");
        Assertions.assertEquals(ecModel.getEcProtocol(), "FTP");
    }

    @Test
    @DisplayName("Update Not-Existing EcPartner")
    void testUpdate() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenThrow(notFound("Partner"));
            ecPartnerService.update(generateEcModel("ExistingConnection"));
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(ecService, Mockito.never()).get(Mockito.anyString());
    }


    @Test
    @DisplayName("Update EcPartner with returning Entity but Unknown protocol")
    void testUpdate1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
            ecPartnerService.update(generateEcModel("ExistingConnection1"));
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(ecService, Mockito.never()).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Update EcPartner with Not-Existing Protocol")
    void testUpdate2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
            Mockito.when(ecService.get(Mockito.anyString())).thenThrow(notFound("Protocol"));
            ecPartnerService.update(generateEcModel("ExistingConnection"));
        });
        Mockito.verify(manageProtocolService,Mockito.never()).deleteProtocol(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(ecService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(ecService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.never()).updatePartnerActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
    }

    @Test
    @DisplayName("Update EcPartner with valid data")
    void testUpdate4() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(ecService.get(Mockito.anyString())).thenReturn(getEcEntity());
        Mockito.when(ecService.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getEcEntity());
        Mockito.when(partnerService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getPartnerEntity());
        ecPartnerService.update(generateEcModel("ExistingConnection"));
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(ecService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(ecService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).updatePartnerActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
    }

    @Test
    @DisplayName("Update with Protocol change")
    void testUpdate5() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity().setTpId("9966696"));
        Mockito.when(ecService.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getEcEntity());
        Mockito.when(partnerService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getPartnerEntity());
        ecPartnerService.duplicatePartner(Mockito.anyString());
        ecPartnerService.update(generateEcModel("FTP"));
        Mockito.verify(manageProtocolService,Mockito.times(1)).deleteProtocol(Mockito.anyString(),Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(ecService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).updatePartnerActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
    }

    @Test
    @DisplayName("Update with Protocol not change")
    void testUpdate6() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(ecService.get(Mockito.anyString())).thenReturn(getEcEntity());
        ecPartnerService.update(generateEcModel("ExistingConnection"));
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(ecService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(ecService, Mockito.never()).delete(Mockito.anyString());
    }

    EcModel generateEcModel(String protocol) {
        EcModel ecModel = new EcModel();
        ecModel.setPkId("123456");
        ecModel.setProfileName("ProfileName");
        ecModel.setProfileId("123456");
        ecModel.setAddressLine1("Address1");
        ecModel.setAddressLine2("Address2");
        ecModel.setProtocol(protocol);
        ecModel.setEmailId("EmailId@email.com");
        ecModel.setPhone("9876543210");
        ecModel.setStatus(false);
        ecModel.setEcProtocol("FTP");
        ecModel.setEcProtocolReference("Protocol Ref");
        ecModel.setHubInfo(false);
        return ecModel;
    }

    PartnerEntity getPartnerEntity() {
        PartnerEntity partnerEntity = new PartnerEntity();
        partnerEntity.setPkId("123456");
        partnerEntity.setTpId("123456");
        partnerEntity.setTpName("tpname");
        partnerEntity.setTpProtocol("ExistingConnection");
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

    EcEntity getEcEntity() {
        EcEntity ecEntity = new EcEntity();
        ecEntity.setSubscriberId("123456");
        ecEntity.setSubscriberType("TP");
        ecEntity.setEcProtocol("FTP");
        ecEntity.setEcProtocolRef("ProtocolRef");
        return ecEntity;
    }

    ProcessEntity getProcessEntity() {
        ProcessEntity processEntity = new ProcessEntity();
        processEntity.setApplicationProfile("AppProfile");
        processEntity.setPartnerProfile("PaternerProfilr");
        processEntity.setSeqId("123");
        return processEntity;
    }

    List<ProcessEntity> getProcessEntities() {
        List<ProcessEntity> processEntities = new ArrayList<>();
        processEntities.add(getProcessEntity());
        return processEntities;
    }

}
