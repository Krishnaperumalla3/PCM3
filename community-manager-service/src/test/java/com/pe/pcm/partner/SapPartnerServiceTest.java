package com.pe.pcm.partner;

import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.miscellaneous.UserUtilityService;
import com.pe.pcm.partner.entity.PartnerEntity;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.SapModel;
import com.pe.pcm.protocol.SapService;
import com.pe.pcm.protocol.sap.entity.SapEntity;
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

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class SapPartnerServiceTest {

    @Mock
    private PartnerService partnerService;
    @Mock
    private SapService sapService;
    @Mock
    private ActivityHistoryService activityHistoryService;
    @Mock
    private ProcessService processService;
    @Mock
    private ManageProtocolService manageProtocolService;
    @Mock
    private UserUtilityService userUtilityService;
    //@InjectMocks
    private SapPartnerService sapPartnerService;

    @BeforeEach
    void inIt() {
        sapPartnerService = new SapPartnerService(partnerService, sapService, activityHistoryService,
                processService, manageProtocolService, userUtilityService);
    }

    @Test
    @DisplayName("Create a new SapPartner With Unknown Protocol")
    public void testCreate() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            sapPartnerService.save(generateSapModel("SAP1"));
        });
        Mockito.verify(partnerService, Mockito.never()).find(Mockito.any());
    }

    @Test
    @DisplayName("Create a new SapPartner passing null in Protocol")
    public void testCreate1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            sapPartnerService.save(generateSapModel(null));
        });
        Mockito.verify(partnerService, Mockito.never()).find(Mockito.any());
        Mockito.verify(sapService, Mockito.never()).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.never()).savePartnerActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Create a new SapPartner With known Protocol")
    public void testCreate2() {
        sapPartnerService.save(generateSapModel("SAP"));
        Mockito.verify(partnerService, Mockito.times(1)).find(Mockito.any());
        Mockito.verify(sapService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).savePartnerActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Create a new SapPartner With Duplicate Partner")
    public void testCreate3() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.find(Mockito.anyString())).thenReturn(Optional.ofNullable(getPartnerEntity()));
            sapPartnerService.save(generateSapModel("SAP"));
        });
        Mockito.verify(partnerService, Mockito.times(1)).find(Mockito.anyString());
        Mockito.verify(sapService, Mockito.never()).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.never()).savePartnerActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Create a new SapPartner With valid Partner")
    public void testCreate4() {
        Mockito.when(partnerService.find(Mockito.anyString())).thenReturn(Optional.ofNullable(null));
        sapPartnerService.save(generateSapModel("SAP"));
        Mockito.verify(partnerService, Mockito.times(1)).find(Mockito.any());
        Mockito.verify(sapService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).savePartnerActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Delete SapPartner")
    public void testDelete() {
        Mockito.when(processService.findByPartnerProfile(Mockito.anyString())).thenReturn(new ArrayList<>());
        Mockito.when(partnerService.findPartnerById(Mockito.anyString())).thenReturn(getPartnerEntity());
        final String PkId = "123456";
        sapPartnerService.delete(PkId);
        Mockito.verify(processService, Mockito.times(1)).findByPartnerProfile(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).findPartnerById(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).delete(Mockito.any());
        Mockito.verify(sapService, Mockito.times(1)).delete(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete not Existing SapPartner")
    public void testDelete1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.findPartnerById(Mockito.anyString())).thenThrow(notFound("Partner"));
            final String PkId = "123456";
            sapPartnerService.delete(PkId);
        });
        Mockito.verify(partnerService, Mockito.times(1)).findPartnerById(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).delete(Mockito.any());
        Mockito.verify(sapService, Mockito.never()).delete(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete not Existing SapPartner")
    public void testDelete2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(processService.findByPartnerProfile(Mockito.anyString())).thenReturn(getProcessEntities());
            final String PkId = "123456";
            sapPartnerService.delete(PkId);
        });
        Mockito.verify(processService, Mockito.times(1)).findByPartnerProfile(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).findPartnerById(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).delete(Mockito.any());
        Mockito.verify(sapService, Mockito.never()).delete(Mockito.anyString());
    }


    @Test
    @DisplayName("Get SapPartner With not Existing Protocol")
    public void testGetPartner1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
            Mockito.when(sapService.get(Mockito.anyString())).thenThrow(notFound("Protocol"));
            sapPartnerService.get(Mockito.anyString());
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(sapService, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Get SapPartner With not Existing Protocol and Partner")
    public void testGetPartner2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenThrow(notFound("Protocol"));
            sapPartnerService.get(Mockito.anyString());
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(sapService, Mockito.never()).get(Mockito.anyString());
    }


    @Test
    @DisplayName("Get SapPartner with not Existing partner")
    public void testGetPartner3() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenThrow(notFound("Partner"));
            final String pkId = "123456";
            sapPartnerService.get(pkId);
        });
        Mockito.verify(sapService, Mockito.never()).get(Mockito.anyString());
    }


    @Test
    @DisplayName("Get SapPartner with Passing valid data")
    public void testGetPartner4() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(sapService.get(Mockito.anyString())).thenReturn(getSapEntity());
        final String pkId = "123456";
        SapModel sapModel = sapPartnerService.get(pkId);
        Mockito.verify(partnerService, Mockito.times(1)).get(pkId);
        Mockito.verify(sapService, Mockito.times(1)).get(pkId);
        Assertions.assertEquals(sapModel.getEmailId(), "email@email.com");
        Assertions.assertEquals(sapModel.getAdapterName(), "AdapterName");
    }


    @Test
    @DisplayName("Update Not-Existing SapPartner")
    public void testUpdate() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenThrow(notFound("Partner"));
            sapPartnerService.update(generateSapModel("SAP"));
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(sapService, Mockito.never()).get(Mockito.anyString());
    }


    @Test
    @DisplayName("Update SapPartner with Not-Existing Protocol")
    public void testUpdate2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
            Mockito.when(sapService.get(Mockito.anyString())).thenThrow(notFound("Protocol"));
            sapPartnerService.update(generateSapModel("SAP"));
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(sapService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(sapService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.never()).updatePartnerActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
    }

    @Test
    @DisplayName("Update SapPartner with valid data")
    public void testUpdate4() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(sapService.get(Mockito.anyString())).thenReturn(getSapEntity());
        Mockito.when(sapService.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getSapEntity());
        Mockito.when(partnerService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getPartnerEntity());
        sapPartnerService.update(generateSapModel("SAP"));
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(sapService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(sapService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).updatePartnerActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
    }

    @Test
    @DisplayName("Update with Protocol change")
    public void testUpdate5() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(sapService.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getSapEntity());
        Mockito.when(partnerService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getPartnerEntity());
        sapPartnerService.update(generateSapModel("Mailbox"));
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(sapService, Mockito.never()).get(Mockito.anyString());
        Mockito.verify(sapService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(sapService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).updatePartnerActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
    }

    @Test
    @DisplayName("Update with Protocol not change")
    public void testUpdate6() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(sapService.get(Mockito.anyString())).thenReturn(getSapEntity());
        sapPartnerService.update(generateSapModel("SAP"));
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(sapService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(sapService, Mockito.never()).delete(Mockito.anyString());
    }


    SapModel generateSapModel(String protocol) {
        SapModel sapModel = new SapModel();
        sapModel.setPkId("123456");
        sapModel.setProfileName("ProfileModel");
        sapModel.setProfileId("123456");
        sapModel.setPkId("123456");
        sapModel.setAddressLine1("AddressLine1");
        sapModel.setAddressLine2("AddressLine2");
        sapModel.setProtocol(protocol);
        sapModel.setEmailId("Email@Email.com");
        sapModel.setPhone("9876543210");
        sapModel.setStatus(false);
        sapModel.setAdapterName("adaptername");
        sapModel.setSapRoute("Route");
        sapModel.setHubInfo(false);
        return sapModel;
    }

    PartnerEntity getPartnerEntity() {
        PartnerEntity partnerEntity = new PartnerEntity();
        partnerEntity.setPkId("123456");
        partnerEntity.setTpId("123456");
        partnerEntity.setTpName("tpname");
        partnerEntity.setTpProtocol("SAP");
        partnerEntity.setPartnerProtocolRef("123456");
        partnerEntity.setEmail("email@email.com");
        partnerEntity.setPhone("987654321");
        partnerEntity.setTpPickupFiles("Y");
        partnerEntity.setFileTpServer("TpServer");
        partnerEntity.setStatus("Y");
        partnerEntity.setIsProtocolHubInfo("HubInfo");
        return partnerEntity;
    }

    SapEntity getSapEntity() {
        SapEntity sapEntity = new SapEntity();
        sapEntity.setPkId("123456");
        sapEntity.setSapAdapterName("AdapterName");
        sapEntity.setSapRoute("Route");
        return sapEntity;
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
