package com.pe.pcm.protocol;


import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.protocol.smtp.SmtpRepository;
import com.pe.pcm.protocol.smtp.entity.SmtpEntity;
import org.junit.jupiter.api.Assertions;
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

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class SmtpServiceTest {
    @MockBean
    private SmtpRepository smtpRepository;

    @InjectMocks
    private SmtpService smtpService;

    @Test
    @DisplayName("Save Smtp")
    public void testSave() {
        smtpService.save(getSmtpEntity());
        Mockito.verify(smtpRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Get Smtp")
    public void testGet() {
        Mockito.when(smtpRepository.findBySubscriberId(Mockito.any())).thenReturn(Optional.of(getSmtpEntity()));
        smtpService.get("1234");
        Mockito.verify(smtpRepository, Mockito.times(1)).findBySubscriberId(Mockito.anyString());
    }

    @Test
    @DisplayName("Get Smtp1")
    public void testGet1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(smtpRepository.findBySubscriberId(Mockito.any())).thenThrow(notFound("Protocol"));
            smtpService.get("123456");
        });
        Mockito.verify(smtpRepository, Mockito.times(1)).findBySubscriberId(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete Smtp")
    public void testDelete() {
        Mockito.when(smtpRepository.findBySubscriberId(Mockito.anyString())).thenReturn(Optional.of(getSmtpEntity()));
        smtpService.delete("12345");
        Mockito.verify(smtpRepository, Mockito.times(1)).findBySubscriberId(Mockito.anyString());
    }

    @Test
    @DisplayName("SaveProtocol Smtp")
    public void testSaveProtocol() {
        smtpService.saveProtocol(generateSmtpModel("SMTP"), "1", "2", "3");
        Mockito.verify(smtpRepository, Mockito.times(1)).save(Mockito.any());
    }


    SmtpEntity getSmtpEntity() {
        SmtpEntity smtpEntity = new SmtpEntity();
        smtpEntity.setName("Name");
        smtpEntity.setPkId("123456");
        smtpEntity.setPassword("password");
        smtpEntity.setMailServer("Mail");
        return smtpEntity;
    }

    SmtpModel generateSmtpModel(String protocol) {
        SmtpModel smtpModel = new SmtpModel();
        smtpModel.setName("Name");
        smtpModel.setPkId("123456");
        smtpModel.setProtocol(protocol);
        smtpModel.setPassword("password");
        return smtpModel;
    }

}
