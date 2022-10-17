package com.pe.pcm.partner;

import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.b2b.B2BApiService;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.miscellaneous.UserUtilityService;
import com.pe.pcm.partner.entity.PartnerEntity;
import com.pe.pcm.protocol.MailboxModel;
import com.pe.pcm.protocol.MailboxService;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.mailbox.entity.MailboxEntity;
import com.pe.pcm.sterling.mailbox.SterlingMailboxService;
import com.pe.pcm.sterling.mailbox.routingrule.SterlingRoutingRuleService;
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
class MailboxPartnerServiceTest {

    @Mock
    private PartnerService partnerService;
    @Mock
    private MailboxService mailboxService;
    @Mock
    private ActivityHistoryService activityHistoryService;
    @Mock
    private B2BApiService b2BApiService;
    @Mock
    private ProcessService processService;
    @Mock
    private ManageProtocolService manageProtocolService;
    @Mock
    private UserUtilityService userUtilityService;
    @Mock
    private SterlingMailboxService sterlingMailboxService;
    @Mock
    private SterlingRoutingRuleService sterlingRoutingRuleService;
    //@InjectMocks
    private MailboxPartnerService mailboxPartnerService;

    @BeforeEach
    void inIt() {
        mailboxPartnerService = new MailboxPartnerService(partnerService, mailboxService, processService,
                userUtilityService, manageProtocolService, activityHistoryService,sterlingMailboxService,sterlingRoutingRuleService);
    }

    @Test
    @DisplayName("Create a new MailboxPartner With Unknown Protocol")
    void testCreate() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            mailboxPartnerService.save(generateMailboxModel("Mailbox1"));
        });
        Mockito.verify(partnerService, Mockito.never()).find(Mockito.any());
    }

    @Test
    @DisplayName("Create a new MailboxPartner passing null in Protocol")
    void testCreate1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            mailboxPartnerService.save(generateMailboxModel(null));
        });
        Mockito.verify(userUtilityService, Mockito.never()).addPartnerToCurrentUser(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(b2BApiService, Mockito.never()).createMailBoxInSI(Mockito.anyBoolean(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).find(Mockito.any());
        Mockito.verify(mailboxService, Mockito.never()).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.never()).savePartnerActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Create a new MailboxPartner With known Protocol")
    void testCreate2() {
        mailboxPartnerService.save(generateMailboxModel("Mailbox"));
        Mockito.verify(userUtilityService, Mockito.times(1)).addPartnerToCurrentUser(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(b2BApiService, Mockito.never()).createMailBoxInSI(Mockito.anyBoolean(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).find(Mockito.any());
        Mockito.verify(mailboxService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).savePartnerActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Create a new MailboxPartner With Duplicate Partner")
    void testCreate3() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.find(Mockito.anyString())).thenReturn(Optional.ofNullable(getPartnerEntity()));
            mailboxPartnerService.save(generateMailboxModel("Mailbox"));
        });
        Mockito.verify(partnerService, Mockito.times(1)).find(Mockito.anyString());
        Mockito.verify(mailboxService, Mockito.never()).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.never()).savePartnerActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Create a new MailboxPartner With Vaild Partner")
    void testCreate4() {
        Mockito.when(partnerService.find(Mockito.anyString())).thenReturn(Optional.empty());
        mailboxPartnerService.save(generateMailboxModel("Mailbox"));
        Mockito.verify(partnerService, Mockito.times(1)).find(Mockito.any());
        Mockito.verify(mailboxService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).savePartnerActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Delete MailboxPartner")
    void testDelete() {
        Mockito.when(processService.findByPartnerProfile(Mockito.anyString())).thenReturn(new ArrayList<>());
        Mockito.when(partnerService.findPartnerById(Mockito.anyString())).thenReturn(getPartnerEntity());
        final String PkId = "123456";
        mailboxPartnerService.delete(PkId);
        Mockito.verify(processService, Mockito.times(1)).findByPartnerProfile(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).findPartnerById(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).delete(Mockito.any());
        Mockito.verify(mailboxService, Mockito.times(1)).delete(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete not Existing MailboxPartner")
    void testDelete1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.findPartnerById(Mockito.anyString())).thenThrow(notFound("Partner"));
            final String PkId = "123456";
            mailboxPartnerService.delete(PkId);
        });
        Mockito.verify(partnerService, Mockito.times(1)).findPartnerById(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).delete(Mockito.any());
        Mockito.verify(mailboxService, Mockito.never()).delete(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete not Existing MailboxPartner")
    void testDelete2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(processService.findByPartnerProfile(Mockito.anyString())).thenReturn(getProcessEntities());
            final String PkId = "123456";
            mailboxPartnerService.delete(PkId);
        });
        Mockito.verify(processService, Mockito.times(1)).findByPartnerProfile(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).findPartnerById(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).delete(Mockito.any());
        Mockito.verify(mailboxService, Mockito.never()).delete(Mockito.anyString());
    }


    @Test
    @DisplayName("Get MailboxPartner With not Existing Protocol")
    void testGetPartner1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
            Mockito.when(mailboxService.get(Mockito.anyString())).thenThrow(notFound("Protocol"));
            mailboxPartnerService.get(Mockito.anyString());
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(mailboxService, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Get MailboxPartner With not Exsisting Protocol and Partner")
    void testGetPartner2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenThrow(notFound("Protocol"));
            mailboxPartnerService.get(Mockito.anyString());
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(mailboxService, Mockito.never()).get(Mockito.anyString());
    }


    @Test
    @DisplayName("Get MailboxPartner with not Existing partner")
    void testGetPartner3() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenThrow(notFound("Partner"));
            final String pkId = "123456";
            mailboxPartnerService.get(pkId);
        });
        Mockito.verify(partnerService,Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(mailboxService, Mockito.never()).get(Mockito.anyString());
    }


    @Test
    @DisplayName("Get MailboxPartner with Passing valid data")
    void testGetPartner4() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(mailboxService.get(Mockito.anyString())).thenReturn(getMailboxEntity());
        final String pkId = "123456";
        MailboxModel mailboxModel = mailboxPartnerService.get(pkId);
        Mockito.verify(partnerService, Mockito.times(1)).get(pkId);
        Mockito.verify(mailboxService, Mockito.times(1)).get(pkId);
        Assertions.assertEquals(mailboxModel.getPoolingInterval(), "22");
        Assertions.assertEquals(mailboxModel.getInMailBox(), "/InMailBox");
    }


    @Test
    @DisplayName("Update Not-Existing MailboxPartner")
    void testUpdate() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenThrow(notFound("Partner"));
            mailboxPartnerService.update(generateMailboxModel("Mailbox"));
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(mailboxService, Mockito.never()).get(Mockito.anyString());
    }


    @Test
    @DisplayName("Update MailboxPartner with Not-Existing Protocol")
    void testUpdate2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
            Mockito.when(mailboxService.get(Mockito.anyString())).thenThrow(notFound("Protocol"));
            mailboxPartnerService.update(generateMailboxModel("Mailbox"));
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(mailboxService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(mailboxService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.never()).updatePartnerActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
    }

    @Test
    @DisplayName("Update MailboxPartner with valid data")
    void testUpdate4() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity().setTpId("968696"));
        Mockito.when(mailboxService.get(Mockito.anyString())).thenReturn(getMailboxEntity());
        Mockito.when(mailboxService.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getMailboxEntity());
        Mockito.when(partnerService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getPartnerEntity());
        mailboxPartnerService.duplicatePartner(Mockito.anyString());
        mailboxPartnerService.update(generateMailboxModel("Mailbox"));
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(mailboxService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(mailboxService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).updatePartnerActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
    }

    @Test
    @DisplayName("Update with Protocol change")
    void testUpdate5() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(mailboxService.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getMailboxEntity());
        Mockito.when(partnerService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getPartnerEntity());
        mailboxPartnerService.update(generateMailboxModel("HTTP"));
        Mockito.verify(manageProtocolService, Mockito.times(1)).deleteProtocol(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(mailboxService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).updatePartnerActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
    }

    @Test
    @DisplayName("Update with Protocol not change")
    void testUpdate6() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(mailboxService.get(Mockito.anyString())).thenReturn(getMailboxEntity());
        mailboxPartnerService.update(generateMailboxModel("Mailbox"));
        Mockito.verify(manageProtocolService, Mockito.never()).deleteProtocol(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(mailboxService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(mailboxService, Mockito.never()).delete(Mockito.anyString());
    }


    PartnerEntity getPartnerEntity() {
        PartnerEntity partnerEntity = new PartnerEntity();
        partnerEntity.setPkId("123456");
        partnerEntity.setTpId("123456");
        partnerEntity.setTpName("tpname");
        partnerEntity.setTpProtocol("Mailbox");
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

    MailboxModel generateMailboxModel(String protocol) {
        MailboxModel mailboxModel = new MailboxModel();
        mailboxModel.setPkId("123456");
        mailboxModel.setProfileName("ProfileName");
        mailboxModel.setProfileId("123456");
        mailboxModel.setAddressLine1("AddressLine1");
        mailboxModel.setAddressLine2("AddressLine2");
        mailboxModel.setProtocol(protocol);
        mailboxModel.setEmailId("Email@email.com");
        mailboxModel.setPhone("9876543210");
        mailboxModel.setStatus(false);
        mailboxModel.setInMailBox("/mailbox");
        mailboxModel.setOutMailBox("/outmailbox");
        mailboxModel.setFilter("filter");
        mailboxModel.setPoolingInterval("22");
        mailboxModel.setHubInfo(false);
        return mailboxModel;
    }

    MailboxEntity getMailboxEntity() {
        MailboxEntity mailboxEntity = new MailboxEntity();
        mailboxEntity.setPkId("123456");
        mailboxEntity.setInMailbox("/InMailBox");
        mailboxEntity.setPoolingIntervalMins("22");
        mailboxEntity.setSubscriberId("1234");
        mailboxEntity.setSubscriberType("TP");
        return mailboxEntity;
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
