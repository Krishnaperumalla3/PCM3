package com.pe.pcm.protocol;

import com.pe.pcm.protocol.mailbox.MailboxRepository;
import com.pe.pcm.protocol.mailbox.entity.MailboxEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;


@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class MailboxServiceTest {
    @MockBean
    private MailboxRepository mailboxRepository;
    //@InjectMocks
    private MailboxService mailboxService;

    @BeforeEach
    void inIt() {
        mailboxService = new MailboxService(mailboxRepository);
    }

    @Test
    @DisplayName("Save Mailbox")
    public void testSave() {
        mailboxService.save(getMailboxEntity());
        Mockito.verify(mailboxRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Get Mailbox")
    public void testGet() {
        Mockito.when(mailboxRepository.findBySubscriberId(Mockito.anyString())).thenReturn(Optional.of(getMailboxEntity()));
        String pkId = "123456";
        mailboxService.get(pkId);
        Mockito.verify(mailboxRepository, Mockito.times(1)).findBySubscriberId(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete Mailbox")
    public void testDelete() {
        Mockito.when(mailboxRepository.findBySubscriberId(Mockito.anyString())).thenReturn(Optional.of(getMailboxEntity()));
        String pkId = "123456";
        mailboxService.delete(pkId);
        Mockito.verify(mailboxRepository, Mockito.times(1)).findBySubscriberId(Mockito.anyString());
        Mockito.verify(mailboxRepository, Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    @DisplayName("Save protocol Mailbox")
    public void testSaveProtocol() {
        Mockito.when(mailboxRepository.findBySubscriberId(Mockito.anyString())).thenReturn(Optional.of(getMailboxEntity()));
        mailboxService.saveProtocol(getMailboxModel("Mailbox"), "1", "2", "sub");
        Mockito.verify(mailboxRepository, Mockito.never()).findBySubscriberId(Mockito.anyString());
        Mockito.verify(mailboxRepository, Mockito.times(1)).save(Mockito.any());
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
}
