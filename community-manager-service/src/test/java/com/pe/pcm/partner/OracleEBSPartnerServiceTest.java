
package com.pe.pcm.partner;

import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.miscellaneous.UserUtilityService;
import com.pe.pcm.partner.entity.PartnerEntity;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.OracleEbsModel;
import com.pe.pcm.protocol.OracleEbsService;
import com.pe.pcm.protocol.oracleebs.entity.OracleEbsEntity;
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
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class OracleEBSPartnerServiceTest {
    @Mock
    private PartnerService partnerService;
    @Mock
    private ActivityHistoryService activityHistoryService;
    @Mock
    private OracleEbsService oracleEBSService;
    @Mock
    private ProcessService processService;
    @Mock
    private ManageProtocolService manageProtocolService;
    @Mock
    private UserUtilityService userUtilityService;
    //@InjectMocks
    private OracleEBSPartnerService oracleEBSPartnerService;

    @BeforeEach
    void inIt () {
        oracleEBSPartnerService = new OracleEBSPartnerService(partnerService,activityHistoryService,
                oracleEBSService,processService,userUtilityService,manageProtocolService);
    }

    @Test
    @DisplayName(value = "Check Duplicate Partner")
    void duplicateApplicationTest() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.find(Mockito.anyString())).thenReturn(Optional.of(getPartnerEntity()));
            oracleEBSPartnerService.duplicatePartner(Mockito.anyString());
        });
        Mockito.verify(partnerService, Mockito.times(1)).find(Mockito.anyString());
        Assertions.assertEquals(CommunityManagerServiceException.class, CommunityManagerServiceException.class);
        Assertions.assertNotEquals(GlobalExceptionHandler.conflict("Application"), CommunityManagerServiceException.class);
    }

    @Test
    @DisplayName("Create Oracle EBS with unknown protocol")
    void testSave() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            oracleEBSPartnerService.save(generateOracleEBSModel("OracleEBS1"));
        });
        Mockito.verify(userUtilityService, Mockito.never()).addPartnerToCurrentUser(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).find(Mockito.any());
    }

    @Test
    @DisplayName("Create Oracle EBS with known protocol")
    void testSave1() {
        oracleEBSPartnerService.save(generateOracleEBSModel("ORACLE_EBS"));
        Mockito.verify(userUtilityService, Mockito.times(1)).addPartnerToCurrentUser(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).find(Mockito.any());
    }

    @Test
    @DisplayName("Create Oracle EBS UnKnown Protocol")
    void testSave2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            oracleEBSPartnerService.save(generateOracleEBSModel("OracleEBS1"));
        });
        Mockito.verify(partnerService, Mockito.never()).find(Mockito.anyString());
    }

    @Test
    @DisplayName("Create Oracle EBS with a valid data")
    void testSave3() {
        Mockito.when(partnerService.find(Mockito.anyString())).thenReturn(Optional.empty());
        oracleEBSPartnerService.save(generateOracleEBSModel("ORACLE_EBS"));
        Mockito.verify(partnerService, Mockito.times(1)).find(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).savePartnerActivity(Mockito.anyString(), Mockito.any());
    }

    @Test
    @DisplayName("Delete ORACLE_EBS partner")
    void testDelete() {
        Mockito.when(processService.findByPartnerProfile(Mockito.anyString())).thenReturn(new ArrayList<>());
        oracleEBSPartnerService.delete("25435");
        Mockito.verify(processService, Mockito.times(1)).findByPartnerProfile(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).findPartnerById(Mockito.anyString());
        Mockito.verify(oracleEBSService, Mockito.times(1)).delete(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    @DisplayName("Delete ORACLE_EBS partner1")
    void testDelete1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(processService.findByPartnerProfile(Mockito.anyString())).thenReturn(getPartnersInProcess());
            oracleEBSPartnerService.delete("54566546");
        });
        Mockito.verify(processService, Mockito.times(1)).findByPartnerProfile(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).findPartnerById(Mockito.anyString());
        Mockito.verify(oracleEBSService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).delete(Mockito.any());
    }

    @Test
    @DisplayName("Get Oracle EBS")
    void testGet() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(oracleEBSService.get(Mockito.anyString())).thenReturn(getOracleEBSEntity());
        oracleEBSPartnerService.get("35647");
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(oracleEBSService, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Update ORACLE_EBS Partner")
    void testUpdate() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenThrow(notFound("Application"));
            oracleEBSPartnerService.update(generateOracleEBSModel("OracleEBS1"));
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Update ORACLE_EBS Partner1")
    void testUpdate1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
            Mockito.when(oracleEBSService.get(Mockito.anyString())).thenThrow(notFound("Partner"));
            oracleEBSPartnerService.update(generateOracleEBSModel("ORACLE_EBS"));
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(oracleEBSService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(oracleEBSService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(oracleEBSService, Mockito.never()).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
    }


    @Test
    @DisplayName("Update ORACLE_EBS partner with an valid data")
    void updateSmtpPartner() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(oracleEBSService.get(Mockito.anyString())).thenReturn(getOracleEBSEntity());
        Mockito.when(oracleEBSService.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getOracleEBSEntity());
        Mockito.when(partnerService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getPartnerEntity());
        oracleEBSPartnerService.update(generateOracleEBSModel("ORACLE_EBS"));
        Mockito.verify(manageProtocolService, Mockito.never()).deleteProtocol(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(oracleEBSService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(oracleEBSService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Update with protocol change")
    void testUpdate4() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        oracleEBSPartnerService.update(generateOracleEBSModel("FTP"));
        Mockito.verify(manageProtocolService, Mockito.times(1)).deleteProtocol(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(oracleEBSService, Mockito.never()).get(Mockito.anyString());
        Mockito.verify(oracleEBSService, Mockito.never()).delete(Mockito.anyString());
    }

    @Test
    @DisplayName("Update with Protocol not change")
    void testUpdate6() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(oracleEBSService.get(Mockito.anyString())).thenReturn(getOracleEBSEntity());
        oracleEBSPartnerService.update(generateOracleEBSModel("ORACLE_EBS"));
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(oracleEBSService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(oracleEBSService, Mockito.never()).delete(Mockito.anyString());
    }


    OracleEbsModel generateOracleEBSModel(String protocol) {
        OracleEbsModel oracleEbsModel = new OracleEbsModel();
        oracleEbsModel.setName("Name");
        oracleEbsModel.setPkId("123456");
        oracleEbsModel.setProtocol(protocol);
        oracleEbsModel.setPassword("password");
        oracleEbsModel.setProfileId("09876");
        return oracleEbsModel;
    }

    OracleEbsEntity getOracleEBSEntity() {
        OracleEbsEntity oracleEbsEntity = new OracleEbsEntity();
        oracleEbsEntity.setName("Name");
        oracleEbsEntity.setPkId("123456");
        oracleEbsEntity.setPassword("password");
        oracleEbsEntity.setProtocol("ORACLE_EBS");
        return oracleEbsEntity;
    }

    PartnerEntity getPartnerEntity() {
        PartnerEntity partnerEntity = new PartnerEntity();
        partnerEntity.setTpName("Tpname");
        partnerEntity.setPkId("123456");
        partnerEntity.setTpId("321654");
        partnerEntity.setPartnerProtocolRef("56123");
        partnerEntity.setPhone("9876543210");
        partnerEntity.setEmail("Email@Email.com");
        partnerEntity.setTpProtocol("ORACLE_EBS");
        return partnerEntity;
    }

    ProcessEntity getProcessEntity() {
        ProcessEntity processEntity = new ProcessEntity();
        processEntity.setSeqId("123456");
        processEntity.setPartnerProfile("Partner Profile");
        processEntity.setApplicationProfile("Application Profile");
        processEntity.setFlow("MFT");
        processEntity.setSeqType("");
        return processEntity;
    }

    List<ProcessEntity> getPartnersInProcess() {
        List<ProcessEntity> processEntities = new ArrayList<>();
        processEntities.add(getProcessEntity());
        return processEntities;
    }
}

