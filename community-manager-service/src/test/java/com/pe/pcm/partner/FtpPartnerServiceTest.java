package com.pe.pcm.partner;

import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.b2b.B2BApiService;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.miscellaneous.PasswordUtilityService;
import com.pe.pcm.miscellaneous.UserUtilityService;
import com.pe.pcm.partner.entity.PartnerEntity;
import com.pe.pcm.protocol.FtpModel;
import com.pe.pcm.protocol.FtpService;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.ftp.entity.FtpEntity;
import com.pe.pcm.sterling.yfs.YfsUserService;
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
class FtpPartnerServiceTest {

    @Mock
    private PartnerService partnerService;
    @Mock
    private FtpService ftpService;
    @Mock
    private ActivityHistoryService activityHistoryService;
    @Mock
    private ProcessService processService;
    @Mock
    private B2BApiService b2BApiService;
    @Mock
    private UserUtilityService userUtilityService;
    @Mock
    private ManageProtocolService manageProtocolService;
    @Mock
    private PasswordUtilityService passwordUtilityService;
    @Mock
    private YfsUserService yfsUserService;
    //@InjectMocks
    private FtpPartnerService ftpPartnerService;

    @BeforeEach
    void inIt() {
        ftpPartnerService = new FtpPartnerService(partnerService,activityHistoryService,ftpService,
                processService,b2BApiService,userUtilityService,manageProtocolService,
                passwordUtilityService,yfsUserService);
    }

    @Test
    @DisplayName("Create a new FtpPartner With Unknown Protocol")
    void testCreate() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            ftpPartnerService.save(generateFtpModel("FTP1"));
        });
        Mockito.verify(partnerService, Mockito.never()).find(Mockito.any());
    }

    @Test
    @DisplayName("Create a new FtpPartner passing null in Protocol")
    void testCreate1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            ftpPartnerService.save(generateFtpModel(null));
        });
        Mockito.verify(partnerService, Mockito.never()).find(Mockito.any());
    }

    @Test
    @DisplayName("Create a new FtpPartner With known Protocol")
    void testCreate2() {
        ftpPartnerService.save(generateFtpModel("FTP"));
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(b2BApiService, Mockito.times(1)).createMailBoxInSI(Mockito.anyBoolean(), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Create a new FtpPartner With Duplicate Partner")
    void testCreate3() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.find(Mockito.anyString())).thenReturn(Optional.ofNullable(getPartnerEntity()));
            ftpPartnerService.save(generateFtpModel("FTP"));
        });
        Mockito.verify(partnerService, Mockito.times(1)).find(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.never()).savePartnerActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Create a new FtpPartner With Valid Partner")
    void testCreate4() {
        Mockito.when(partnerService.find(Mockito.anyString())).thenReturn(Optional.empty());
        ftpPartnerService.save(generateFtpModel("FTP"));
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).savePartnerActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Delete FtpPartner")
    void testDelete() {
        Mockito.when(processService.findByPartnerProfile(Mockito.anyString())).thenReturn(new ArrayList<>());
        Mockito.when(partnerService.findPartnerById(Mockito.anyString())).thenReturn(getPartnerEntity());
        final String PkId = "123456";
        ftpPartnerService.delete(PkId);
        Mockito.verify(processService, Mockito.times(1)).findByPartnerProfile(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).findPartnerById(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).delete(Mockito.any());
        Mockito.verify(ftpService, Mockito.times(1)).delete(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete FtpPartner1")
    void testDelete1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(processService.findByPartnerProfile(Mockito.anyString())).thenReturn(getProcessEntities());
//            Mockito.when(partnerService.findPartnerById(Mockito.anyString())).thenReturn(getPartnerEntity());
            final String PkId = "123456";
            ftpPartnerService.delete(PkId);
        });
        Mockito.verify(processService, Mockito.times(1)).findByPartnerProfile(Mockito.anyString());
        //Mockito.verify(partnerService, Mockito.never()).findPartnerById(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).delete(Mockito.any());
        Mockito.verify(ftpService, Mockito.never()).delete(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete FtpPartner2")
    void testDelete2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(processService.findByPartnerProfile(Mockito.anyString())).thenReturn(new ArrayList<>());
            Mockito.when(partnerService.findPartnerById(Mockito.anyString())).thenThrow(notFound("Partner"));
            final String PkId = "123456";
            ftpPartnerService.delete(PkId);
        });
        Mockito.verify(processService, Mockito.times(1)).findByPartnerProfile(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).findPartnerById(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).delete(Mockito.any());
        Mockito.verify(ftpService, Mockito.never()).delete(Mockito.anyString());
    }


    @Test
    @DisplayName("Get FtpPartner With not Exsisting Protocol")
    void testGetFtpPartner1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(ftpService.get(Mockito.anyString())).thenThrow(notFound("Protocol"));
            ftpPartnerService.get(Mockito.anyString());
        });
        Mockito.verify(ftpService, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Get FTPartner with not Existing partner")
    void testGetFtpPartner2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenThrow(notFound("Partner"));
            //Mockito.when(ftpService.get(Mockito.anyString())).thenReturn(getFtpEntity());
            final String pkId = "123456";
            ftpPartnerService.get(pkId);
        });
        //Mockito.verify(ftpService, Mockito.never()).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Get FTPartner with Passing Null for ftp service")
    void testGetFtpPartner3() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
            Mockito.when(ftpService.get(Mockito.anyString())).thenThrow(notFound("Protocol"));
            final String pkId = "123456";
            ftpPartnerService.get(pkId);
        });
    }

    @Test
    @DisplayName("Get FTPartner with Passing valid data")
    void testGetFtpPartner4() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(ftpService.get(Mockito.anyString())).thenReturn(getFtpEntity());
        final String pkId = "123456";
        FtpModel ftpModelActual = ftpPartnerService.get(pkId);
        FtpModel ftpModelExpected = generateFtpModel("FTP");
        ftpModelExpected.setProfileName("tpname");
        Mockito.verify(partnerService, Mockito.times(1)).get(pkId);
        Mockito.verify(ftpService, Mockito.times(1)).get(pkId);
        Assertions.assertEquals(ftpModelActual.getPoolingInterval(), ftpModelExpected.getPoolingInterval());
        Assertions.assertEquals(ftpModelActual.getProfileName(), ftpModelExpected.getProfileName());
    }

    @Test
    @DisplayName("Update FtpPartner with returning Error from partnerservice")
    void testUpdate() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenThrow(notFound("Partner"));
            ftpPartnerService.update(generateFtpModel("FTP"));
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(ftpService, Mockito.never()).get(Mockito.anyString());
    }


    @Test
    @DisplayName("Update FtpPartner with returning Entity but Unknown protocol")
    void testUpdate1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
            ftpPartnerService.update(generateFtpModel("FTP1"));
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(ftpService, Mockito.never()).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Update FtpPartner with Returning Error from ftpservice")
    void testUpdate2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
            Mockito.when(ftpService.get(Mockito.anyString())).thenThrow(notFound("Protocol"));
            ftpPartnerService.update(generateFtpModel("FTP"));
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(ftpService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(ftpService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.never()).updatePartnerActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
    }

    @Test
    @DisplayName("Update FtpPartner with valid data")
    void testUpdate3() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(ftpService.get(Mockito.anyString())).thenReturn(getFtpEntity());
        Mockito.when(ftpService.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getFtpEntity());
        Mockito.when(partnerService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getPartnerEntity());
        ftpPartnerService.update(generateFtpModel("FTP"));
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(ftpService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(ftpService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).updatePartnerActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
    }

    @Test
    @DisplayName("Update with Protocol change")
    void testUpdate4() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        // Mockito.when(ftpService.get(Mockito.anyString())).thenReturn(getFtpEntity());
        Mockito.when(ftpService.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getFtpEntity());
        Mockito.when(partnerService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getPartnerEntity());
        ftpPartnerService.update(generateFtpModel("FTPS"));
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        //Mockito.verify(ftpService, Mockito.never()).get(Mockito.anyString());
        Mockito.verify(ftpService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(ftpService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).updatePartnerActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
    }

    @Test
    @DisplayName("Update with Protocol not change")
    void testUpdate5() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(ftpService.get(Mockito.anyString())).thenReturn(getFtpEntity());
        ftpPartnerService.update(generateFtpModel("FTP"));
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(ftpService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(ftpService, Mockito.never()).delete(Mockito.anyString());
    }


    @Test
    @DisplayName("Update duplicate FtpPartner ")
    void testUpdate6() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity().setTpId("3214569"));
        ftpPartnerService.update(generateFtpModel("FTP"));

        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(ftpService, Mockito.times(1)).get(Mockito.anyString());
    }


    private FtpModel generateFtpModel(String protocol) {
        FtpModel ftpModel = new FtpModel();
        ftpModel.setPkId("123456");
        ftpModel.setProfileName("profilename");
        ftpModel.setProfileId("123456");
        ftpModel.setAddressLine1("address1");
        ftpModel.setAddressLine2("address2");
        ftpModel.setProtocol(protocol);
        ftpModel.setEmailId("emailid@email.com");
        ftpModel.setPhone("7894561230");
        ftpModel.setStatus(false);
        ftpModel.setHubInfo(false);

        ftpModel.setHostName("hostname")
                .setPortNumber("portnumber")
                .setUserName("userid")
                .setPassword("password")
                .setFileType("filetype")
                .setTransferType("trnsfertype")
                .setInDirectory("Indirectory")
                .setOutDirectory("outdirectory")
                .setCertificateId("certid")
                .setKnownHostKey("knownhost")
                .setAdapterName("adaptername")
                .setPoolingInterval("23")
                .setDeleteAfterCollection(true)
                .setCreateDirectoryInSI(false)
                .setCreateUserInSI(false);
        return ftpModel;
    }

    private PartnerEntity getPartnerEntity() {
        return new PartnerEntity()
                .setPkId("123456")
                .setTpId("123456")
                .setTpName("tpname")
                .setCustomTpName("cTpName")
                .setPgpInfo("pgpInfo")
                .setIpWhitelist("IpwhiteList")
                .setAddressLine1("Line1")
                .setAddressLine2("Line2")
                .setTpProtocol("FTP")
                .setPartnerProtocolRef("123456")
                .setEmail("email@email.com")
                .setPhone("987654321")
                .setTpPickupFiles("Y")
                .setFileTpServer("TpServer")
                .setPartnerProtocolRef("ProtocolRef")
                .setStatus("Y")
                .setIsProtocolHubInfo("HubInfo")
                .setIsOnlyPcm("N");
    }

    private FtpEntity getFtpEntity() {
        return new FtpEntity()
                .setSubscriberId("123456")
                .setSubscriberType("TP")
                .setPoolingIntervalMins("23")
                .setHostName("hostname");

    }

    private ProcessEntity getProcessEntity() {
        return new ProcessEntity()
                .setApplicationProfile("AppProfile")
                .setPartnerProfile("PaternerProfilr")
                .setSeqId("123");

    }

    private List<ProcessEntity> getProcessEntities() {
        List<ProcessEntity> processEntities = new ArrayList<>();
        processEntities.add(getProcessEntity());
        return processEntities;
    }
}
