package com.pe.pcm.partner;

import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.miscellaneous.UserUtilityService;
import com.pe.pcm.partner.entity.PartnerEntity;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.SmtpModel;
import com.pe.pcm.protocol.SmtpService;
import com.pe.pcm.protocol.smtp.entity.SmtpEntity;
import com.pe.pcm.workflow.ProcessService;
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
import java.util.Optional;

import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class SmtpPartnerServiceTest {

    @Mock
    private PartnerService partnerService;
    @Mock
    private ActivityHistoryService activityHistoryService;
    @Mock
    private SmtpService smtpService;
    @Mock
    private ProcessService processService;
    @Mock
    private ManageProtocolService manageProtocolService;
    @Mock
    private UserUtilityService userUtilityService;
    //@InjectMocks
    private SmtpPartnerService smtpPartnerService;

    @BeforeEach
    void inIt() {
        smtpPartnerService = new SmtpPartnerService(partnerService,activityHistoryService,smtpService,
                processService,manageProtocolService,userUtilityService);
    }

    @Test
    @DisplayName("Create Smtp with unknown protocol")
    public void testSave() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            smtpPartnerService.save(generateSmtpModel("SMTP1"));
        });
        Mockito.verify(partnerService, Mockito.never()).find(Mockito.anyString());
    }

    @Test
    @DisplayName("Create Smtp with known protocol")
    public void testSave1() {
        smtpPartnerService.save(generateSmtpModel("SMTP"));
        Mockito.verify(partnerService, Mockito.times(1)).find(Mockito.anyString());
    }

    @Test
    @DisplayName("Create Smtp")
    public void testSave2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            smtpPartnerService.save(generateSmtpModel("SMTP1"));
        });
        Mockito.verify(partnerService, Mockito.never()).find(Mockito.anyString());
    }

    @Test
    @DisplayName("Create Smtp1")
    public void testSave3() {
        Mockito.when(partnerService.find(Mockito.anyString())).thenReturn(Optional.empty());
        smtpPartnerService.save(generateSmtpModel("SMTP"));
        Mockito.verify(partnerService, Mockito.times(1)).find(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).savePartnerActivity(Mockito.anyString(), Mockito.any());
    }

    @Test
    @DisplayName("Delete Smtp")
    public void testDelete() {
        Mockito.when(processService.findByPartnerProfile(Mockito.anyString())).thenReturn(new ArrayList<>());
        smtpPartnerService.delete("12435");
        Mockito.verify(processService, Mockito.times(1)).findByPartnerProfile(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).findPartnerById(Mockito.anyString());
        Mockito.verify(smtpService, Mockito.times(1)).delete(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    @DisplayName("Delete Smtp1")
    public void testDelete1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(processService.findByPartnerProfile(Mockito.anyString())).thenThrow(internalServerError("\"Unable to delete this partner, Because it has workflow..\""));
            smtpPartnerService.delete("45657");
        });
        Mockito.verify(processService, Mockito.times(1)).findByPartnerProfile(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).findPartnerById(Mockito.any());
        Mockito.verify(smtpService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).delete(Mockito.any());
    }

    @Test
    @DisplayName("Get Smtp1")
    public void testGet() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(smtpService.get(Mockito.anyString())).thenReturn(getSmtpEntity());
        smtpPartnerService.get("324564");
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(smtpService, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Update Smtp")
    public void testUpdate() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenThrow(notFound("Partner"));
            smtpPartnerService.update(generateSmtpModel("SMTP1"));
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Update Smtp1")
    public void testUpdate1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
            Mockito.when(smtpService.get(Mockito.anyString())).thenThrow(notFound("Partner"));
            smtpPartnerService.update(generateSmtpModel("SMTP"));
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(smtpService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.never()).updatePartnerActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
    }

    @Test
    @DisplayName("update SMTP partner")
    public void updateSmtpPartner() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(smtpService.get(Mockito.anyString())).thenReturn(getSmtpEntity());
        Mockito.when(smtpService.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getSmtpEntity());
        Mockito.when(partnerService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getPartnerEntity());
        smtpPartnerService.update(generateSmtpModel("SMTP"));
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(smtpService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(smtpService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Update with protocol change")
    public void testUpdate4() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        smtpPartnerService.update(generateSmtpModel("FTP"));
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(smtpService, Mockito.never()).get(Mockito.anyString());
        Mockito.verify(smtpService, Mockito.never()).delete(Mockito.anyString());
    }

    @Test
    @DisplayName("Update with protocol not change")
    public void testUpdate5() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(smtpService.get(Mockito.anyString())).thenReturn(getSmtpEntity());
        smtpPartnerService.update(generateSmtpModel("SMTP"));
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(smtpService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(smtpService, Mockito.never()).delete(Mockito.anyString());
    }

    SmtpModel generateSmtpModel(String protocol) {
        SmtpModel smtpModel = new SmtpModel();
        smtpModel.setName("Name");
        smtpModel.setPkId("123456");
        smtpModel.setProtocol(protocol);
        smtpModel.setProfileId("09876");
        smtpModel.setPassword("password");
        return smtpModel;
    }

    SmtpEntity getSmtpEntity() {
        SmtpEntity smtpEntity = new SmtpEntity();
        smtpEntity.setName("Name");
        smtpEntity.setPkId("123456");
        smtpEntity.setPassword("password");
        smtpEntity.setMailServer("Mail");
        return smtpEntity;
    }

    PartnerEntity getPartnerEntity() {
        PartnerEntity partnerEntity = new PartnerEntity();
        partnerEntity.setTpName("Tpname");
        partnerEntity.setPkId("123456");
        partnerEntity.setTpId("321654");
        partnerEntity.setPartnerProtocolRef("56123");
        partnerEntity.setPhone("9876543210");
        partnerEntity.setEmail("Email@Email.com");
        partnerEntity.setTpProtocol("Smtp");
        return partnerEntity;
    }
}
