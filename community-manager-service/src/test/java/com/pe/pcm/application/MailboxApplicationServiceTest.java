package com.pe.pcm.application;


import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.application.entity.ApplicationEntity;
import com.pe.pcm.b2b.B2BApiService;
import com.pe.pcm.exception.CommunityManagerServiceException;
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

import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class MailboxApplicationServiceTest {

    @Mock
    private ApplicationService applicationService;
    @Mock
    private ActivityHistoryService activityHistoryService;
    @Mock
    private B2BApiService b2BApiService;
    @Mock
    private MailboxService mailboxService;
    @Mock
    private ProcessService processService;
    @Mock
    private ManageProtocolService manageProtocolService;
    @Mock
    private SterlingMailboxService sterlingMailboxService;
    @Mock
    private SterlingRoutingRuleService sterlingRoutingRuleService;
    //@InjectMocks
    private MailboxApplicationService mailboxApplicationService;

    @BeforeEach
    void inIt() {
        mailboxApplicationService = new MailboxApplicationService(processService, mailboxService,
                applicationService, manageProtocolService, activityHistoryService, sterlingMailboxService,
                sterlingRoutingRuleService);
    }

    @Test
    @DisplayName("Create a new MailboxApplication with a unknown protocol")
    void testCreate() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            mailboxApplicationService.save(getMailboxModel("Mailbox1"));
        });
        Mockito.verify(applicationService, Mockito.never()).find(Mockito.anyString());
    }

    @Test
    @DisplayName("Create a new MailboxApplication with a known protocol")
    void testCreate1() {
        applicationService.find(Mockito.anyString());
        mailboxApplicationService.save(getMailboxModel("Mailbox"));
        Mockito.verify(applicationService, Mockito.times(2)).find(Mockito.anyString());
    }

    @Test
    @DisplayName("Create a new MailboxApplication with a duplicate protocol")
    void testCreate2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.find(Mockito.anyString())).thenReturn(Optional.ofNullable(getApplicationEntity()));
            mailboxApplicationService.save(getMailboxModel("Mailbox"));
        });
        Mockito.verify(applicationService, Mockito.times(1)).find(Mockito.anyString());
        Mockito.verify(mailboxService, Mockito.never()).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(b2BApiService, Mockito.never()).createMailBoxInSI(Mockito.anyBoolean(), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Create a Mailbox Application")
    void testCreate3() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.find(Mockito.anyString())).thenThrow(notFound("Application"));
            mailboxApplicationService.save(getMailboxModel("Mailbox"));
        });
        Mockito.verify(applicationService, Mockito.times(1)).find(Mockito.anyString());
    }

    @Test
    @DisplayName("Create a MailboxApplication with passing an valid data")
    void testCreate4() {
        Mockito.when(applicationService.find(Mockito.anyString())).thenReturn(Optional.empty());
        mailboxApplicationService.save(getMailboxModel("Mailbox"));
        Mockito.verify(applicationService, Mockito.times(1)).find(Mockito.anyString());
        Mockito.verify(mailboxService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(b2BApiService, Mockito.never()).createMailBoxInSI(Mockito.anyBoolean(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).saveApplicationActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Delete a MailboxApplication with passing an valid data")
    void testDelete() {
        Mockito.when(processService.findByApplicationProfile(Mockito.any())).thenReturn(new ArrayList<>());
        String pkId = "123456";
        mailboxApplicationService.delete(pkId);
        Mockito.verify(processService, Mockito.times(1)).findByApplicationProfile(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(mailboxService, Mockito.times(1)).delete(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).delete(getApplicationEntity());
    }

    @Test
    @DisplayName("Delete a Mailbox Application")
    void testDelete1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(processService.findByApplicationProfile(Mockito.anyString())).thenThrow(internalServerError("Unable to delete this Application, Because it has workflow"));
            String pkId = "123456";
            mailboxApplicationService.delete(pkId);
        });
        Mockito.verify(applicationService, Mockito.never()).get(Mockito.anyString());
        Mockito.verify(mailboxService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).delete(getApplicationEntity());
    }

    @Test
    @DisplayName("Delete a Mailbox Application")
    void testDelete2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(processService.findByApplicationProfile(Mockito.anyString())).thenReturn(getProcessEntities());
            String pkId = "123456";
            mailboxApplicationService.delete(pkId);
        });
        Assertions.assertEquals(CommunityManagerServiceException.class, CommunityManagerServiceException.class);
        Mockito.verify(processService, Mockito.times(1)).findByApplicationProfile(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).get(Mockito.anyString());
        Mockito.verify(mailboxService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).delete(getApplicationEntity());
    }

    @Test
    @DisplayName("Get Mailbox Application with passing an valid data")
    void testGet3() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity());
        Mockito.when(mailboxService.get(Mockito.anyString())).thenReturn(getMailboxEntity());
        final String pkId = "12345";
        mailboxApplicationService.get(pkId);
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(mailboxService, Mockito.times(1)).get(Mockito.anyString());
    }


    @Test
    @DisplayName("Update Mailbox Application  ")
    void testUpdate1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.get(Mockito.anyString())).thenThrow(notFound("protocol"));
            mailboxApplicationService.update(getMailboxModel("Mailbox"));
        });
        Mockito.verify(mailboxService, Mockito.never()).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Update Mailbox Application1 ")
    void testUpdate2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity());
            Mockito.when(mailboxService.get(Mockito.anyString())).thenThrow(notFound("Application"));
            mailboxApplicationService.update(getMailboxModel("Mailbox"));
        });
        Mockito.verify(mailboxService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(mailboxService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(mailboxService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(b2BApiService, Mockito.never()).createMailBoxInSI(Mockito.anyBoolean(), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Update Mailbox Application returning entities with a valid data")
    void testUpdate3() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity());
        Mockito.when(mailboxService.get(Mockito.anyString())).thenReturn(getMailboxEntity());
        Mockito.when(mailboxService.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getMailboxEntity());
        Mockito.when(applicationService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getApplicationEntity());
        mailboxApplicationService.update(getMailboxModel("Mailbox"));
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(mailboxService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(mailboxService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(b2BApiService, Mockito.never()).createMailBoxInSI(Mockito.anyBoolean(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).updateApplicationActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
        Mockito.verify(manageProtocolService, Mockito.never()).deleteProtocol(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Update Mailbox Application with protocol change")
    void testUpdate4() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity());
        Mockito.when(mailboxService.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getMailboxEntity());
        Mockito.when(applicationService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getApplicationEntity());
        mailboxApplicationService.update(getMailboxModel("FTP"));
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(mailboxService, Mockito.never()).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Update Mailbox Application with protocol NOT change")
    void testUpdate5() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity());
        Mockito.when(mailboxService.get(Mockito.anyString())).thenReturn(getMailboxEntity());
        mailboxApplicationService.update(getMailboxModel("Mailbox"));
        Mockito.verify(mailboxService, Mockito.never()).delete(Mockito.anyString());
    }

    MailboxModel getMailboxModel(String protocol) {
        MailboxModel mailboxModel = new MailboxModel();
        mailboxModel.setPkId("123456");
        mailboxModel.setProfileName("ProfileName");
        mailboxModel.setProfileId("ProfileId");
        mailboxModel.setProtocol(protocol);
        mailboxModel.setEmailId("Email@email.com");
        mailboxModel.setPhone("7859856457");
        mailboxModel.setStatus(false);
        mailboxModel.setInMailBox("Mail");
        mailboxModel.setOutMailBox("OutMail");
        mailboxModel.setFilter("Filter");
        mailboxModel.setPoolingInterval("PoolingInterval");
        mailboxModel.setHubInfo(false);
        return mailboxModel;
    }

    ApplicationEntity getApplicationEntity() {
        ApplicationEntity applicationEntity = new ApplicationEntity();
        applicationEntity.setAppDropFiles("AppDropfiles");
        applicationEntity.setAppIntegrationProtocol("Mailbox");
        applicationEntity.setAppIsActive("AppIsActive");
        applicationEntity.setApplicationName("ApplicationName");
        applicationEntity.setApplicationId("ApplicationId");
        applicationEntity.setAppPickupFiles("AppPickupFiles");
        applicationEntity.setAppProtocolRef("AppProtocolRef");
        applicationEntity.setEmailId("Email@e.com");
        applicationEntity.setPhone("7598008944");
        applicationEntity.setPkId("12345");
        return applicationEntity;
    }

    MailboxEntity getMailboxEntity() {
        MailboxEntity mailboxEntity = new MailboxEntity();
        mailboxEntity.setFilter("Filter");
        mailboxEntity.setInMailbox("Mailbox");
        mailboxEntity.setIsActive("false");
        mailboxEntity.setIsHubInfo("Hub");
        mailboxEntity.setOutMailbox("OutMail");
        mailboxEntity.setPkId("123456");
        mailboxEntity.setPoolingIntervalMins("PooliingInterval");
        mailboxEntity.setProtocolType("Prototype");
        mailboxEntity.setSubscriberId("sub001");
        mailboxEntity.setSubscriberType("pragmasub");
        return mailboxEntity;
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



