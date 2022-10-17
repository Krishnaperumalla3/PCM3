package com.pe.pcm.partner;

import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.miscellaneous.UserUtilityService;
import com.pe.pcm.partner.entity.PartnerEntity;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.MqModel;
import com.pe.pcm.protocol.MqService;
import com.pe.pcm.protocol.mq.entity.MqEntity;
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
 class MqPartnerServiceTest {

    @Mock
    private PartnerService partnerService;
    @Mock
    private MqService mqService;
    @Mock
    private ActivityHistoryService activityHistoryService;
    @Mock
    private ProcessService processService;
    @Mock
    private ManageProtocolService manageProtocolService;
    @Mock
    private UserUtilityService userUtilityService;
    //@InjectMocks
    private MqPartnerService mqPartnerService;

    @BeforeEach
    void inIt() {
        mqPartnerService = new MqPartnerService(partnerService,mqService,activityHistoryService,
                processService,manageProtocolService,userUtilityService);
    }

    @Test
    @DisplayName("Create a new MqPartner With Unknown Protocol")
     void testCreate() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            mqPartnerService.save(generateMqModel("MQ1"));
        });
        Mockito.verify(partnerService, Mockito.never()).find(Mockito.any());
    }

    @Test
    @DisplayName("Create a new MqPartner passing null in Protocol")
     void testCreate1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            mqPartnerService.save(generateMqModel(null));
        });
        Mockito.verify(userUtilityService,Mockito.never()).addPartnerToCurrentUser(Mockito.anyString(),Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).find(Mockito.any());
        Mockito.verify(mqService, Mockito.never()).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.never()).savePartnerActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Create a new MqPartner With known Protocol")
     void testCreate2() {
        mqPartnerService.save(generateMqModel("MQ"));
        Mockito.verify(partnerService, Mockito.times(1)).find(Mockito.any());
        Mockito.verify(mqService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).savePartnerActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Create a new MqPartner With Duplicate Partner")
     void testCreate3() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.find(Mockito.anyString())).thenReturn(Optional.ofNullable(getPartnerEntity()));
            mqPartnerService.save(generateMqModel("MQ"));
        });
        Mockito.verify(partnerService, Mockito.times(1)).find(Mockito.anyString());
        Mockito.verify(mqService, Mockito.never()).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.never()).savePartnerActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Create a new MqPartner With Vaild Partner")
     void testCreate4() {
        Mockito.when(partnerService.find(Mockito.anyString())).thenReturn(Optional.empty());
        mqPartnerService.save(generateMqModel("MQ"));
        Mockito.verify(userUtilityService,Mockito.times(1)).addPartnerToCurrentUser(Mockito.anyString(),Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).find(Mockito.any());
        Mockito.verify(mqService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).savePartnerActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Delete MqPartner")
     void testDelete() {
        Mockito.when(processService.findByPartnerProfile(Mockito.anyString())).thenReturn(new ArrayList<>());
        Mockito.when(partnerService.findPartnerById(Mockito.anyString())).thenReturn(getPartnerEntity());
        final String PkId = "123456";
        mqPartnerService.delete(PkId);
        Mockito.verify(processService, Mockito.times(1)).findByPartnerProfile(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).findPartnerById(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).delete(Mockito.any());
        Mockito.verify(mqService, Mockito.times(1)).delete(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete not Existing MqPartner")
     void testDelete1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.findPartnerById(Mockito.anyString())).thenThrow(notFound("Partner"));
            final String PkId = "123456";
            mqPartnerService.delete(PkId);
        });
        Mockito.verify(partnerService, Mockito.times(1)).findPartnerById(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).delete(Mockito.any());
        Mockito.verify(mqService, Mockito.times(1)).delete(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete not Existing MqPartner")
     void testDelete2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(processService.findByPartnerProfile(Mockito.anyString())).thenReturn(getProcessEntities());
            final String PkId = "123456";
            mqPartnerService.delete(PkId);
        });
        Mockito.verify(partnerService, Mockito.never()).findPartnerById(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).delete(Mockito.any());
        Mockito.verify(mqService, Mockito.never()).delete(Mockito.anyString());
    }


    @Test
    @DisplayName("Get MqPartner With not Existing Protocol")
     void testGetPartner1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
            Mockito.when(mqService.get(Mockito.anyString())).thenThrow(notFound("Protocol"));
            mqPartnerService.get(Mockito.anyString());
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(mqService, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Get MqPartner With not Existing Protocol and Partner")
     void testGetPartner2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenThrow(notFound("Protocol"));
            mqPartnerService.get(Mockito.anyString());
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(mqService, Mockito.never()).get(Mockito.anyString());
    }


    @Test
    @DisplayName("Get MqPartner with not Existing partner")
     void testGetPartner3() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenThrow(notFound("Partner"));
            final String pkId = "123456";
            mqPartnerService.get(pkId);
        });
        Mockito.verify(mqService, Mockito.never()).get(Mockito.anyString());
    }


    @Test
    @DisplayName("Get MqPartner with Passing valid data")
     void testGetPartner4() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(mqService.get(Mockito.anyString())).thenReturn(getMqEntity());
        final String pkId = "123456";
        MqModel mqModel = mqPartnerService.get(pkId);
        Mockito.verify(partnerService, Mockito.times(1)).get(pkId);
        Mockito.verify(mqService, Mockito.times(1)).get(pkId);
        Assertions.assertEquals(mqModel.getEmailId(), "email@email.com");
        Assertions.assertEquals(mqModel.getAdapterName(), "AdapterName");
    }


    @Test
    @DisplayName("Update Not-Existing MqPartner")
     void testUpdate() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenThrow(notFound("Partner"));
            mqPartnerService.update(generateMqModel("MQ"));
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(mqService, Mockito.never()).get(Mockito.anyString());
    }


    @Test
    @DisplayName("Update MqPartner with Not-Existing Protocol")
     void testUpdate2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
            Mockito.when(mqService.get(Mockito.anyString())).thenThrow(notFound("Protocol"));
            mqPartnerService.update(generateMqModel("MQ"));
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(mqService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(mqService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.never()).updatePartnerActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
    }

    @Test
    @DisplayName("Update MqPartner with valid data")
     void testUpdate4() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(mqService.get(Mockito.anyString())).thenReturn(getMqEntity());
        Mockito.when(mqService.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getMqEntity());
        Mockito.when(partnerService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getPartnerEntity());
        mqPartnerService.update(generateMqModel("MQ"));
        Mockito.verify(manageProtocolService,Mockito.never()).deleteProtocol(Mockito.anyString(),Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(mqService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(mqService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).updatePartnerActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
    }

    @Test
    @DisplayName("Update with Protocol change")
     void testUpdate5() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(mqService.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getMqEntity());
        Mockito.when(partnerService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getPartnerEntity());
        mqPartnerService.update(generateMqModel("HTTP"));
        Mockito.verify(manageProtocolService,Mockito.times(1)).deleteProtocol(Mockito.anyString(),Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(mqService, Mockito.never()).get(Mockito.anyString());
        Mockito.verify(mqService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(mqService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).updatePartnerActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
    }

    @Test
    @DisplayName("Update with Protocol not change")
     void testUpdate6() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(mqService.get(Mockito.anyString())).thenReturn(getMqEntity());
        mqPartnerService.update(generateMqModel("MQ"));
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(mqService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(mqService, Mockito.never()).delete(Mockito.anyString());
    }

    MqModel generateMqModel(String protocol) {
        MqModel mqModel = new MqModel();
        mqModel.setPkId("123456");
        mqModel.setProfileName("profileName");
        mqModel.setProfileId("654321");
        mqModel.setAddressLine1("Addressline1");
        mqModel.setAddressLine2("Addressline2");
        mqModel.setProtocol(protocol);
        mqModel.setEmailId("Email@Email.com");
        mqModel.setPhone("9876543210");
        mqModel.setStatus(false);
        mqModel.setHostName("HostName");
        mqModel.setFileType("FileType");
        mqModel.setQueueManager("QueueManager");
        mqModel.setQueueName("QueueName");
        mqModel.setAdapterName("AdapterName");
        mqModel.setPoolingInterval("22");
        mqModel.setHubInfo(false);
        return mqModel;
    }

    PartnerEntity getPartnerEntity() {
        PartnerEntity partnerEntity = new PartnerEntity();
        partnerEntity.setPkId("123456");
        partnerEntity.setTpId("123456");
        partnerEntity.setTpName("tpname");
        partnerEntity.setTpProtocol("MQ");
        partnerEntity.setPartnerProtocolRef("123456");
        partnerEntity.setEmail("email@email.com");
        partnerEntity.setPhone("987654321");
        partnerEntity.setTpPickupFiles("Y");
        partnerEntity.setFileTpServer("TpServer");
        partnerEntity.setStatus("Y");
        partnerEntity.setIsProtocolHubInfo("HubInfo");
        return partnerEntity;
    }

    MqEntity getMqEntity() {
        MqEntity mqEntity = new MqEntity();
        mqEntity.setPkId("123456");
        mqEntity.setAdapterName("AdapterName");
        mqEntity.setQueueName("QueueName");
        return mqEntity;
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
