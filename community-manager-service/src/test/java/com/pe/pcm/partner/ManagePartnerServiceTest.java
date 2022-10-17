package com.pe.pcm.partner;

import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.miscellaneous.UserUtilityService;
import com.pe.pcm.partner.entity.PartnerActivityHistoryEntity;
import com.pe.pcm.partner.entity.PartnerEntity;
import com.pe.pcm.partner.sterling.SterlingAs2PartnerService;
import com.pe.pcm.pem.codelist.PemCodeListService;
import com.pe.pcm.pem.codelist.entity.PemCodeListEntity;
import com.pe.pcm.protocol.FtpModel;
import com.pe.pcm.workflow.PartnerInfoModel;
import com.pe.pcm.workflow.pem.PemFileTypeModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static com.pe.pcm.constants.AuthoritiesConstants.SUPER_ADMIN;
import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class ManagePartnerServiceTest {

    @Mock
    private PartnerService partnerService;
    @Mock
    private GoogleDrivePartnerService googleDrivePartnerService;
    @Mock
    private ActivityHistoryService activityHistoryService;
    @Mock
    private HttpPartnerService httpPartnerService;
    @Mock
    private FtpPartnerService ftpPartnerService;
    @Mock
    private SterlingAs2PartnerService sterlingAs2PartnerService;
    @Mock
    private RemoteConnectDirectPartnerService remoteConnectDirectPartnerService;
    @Mock
    private EcPartnerService ecPartnerService;
    @Mock
    private FileSystemPartnerService fileSystemPartnerService;
    @Mock
    private MailboxPartnerService mailboxPartnerService;
    @Mock
    private MqPartnerService mqPartnerService;
    @Mock
    private RemoteFtpPartnerService remoteFtpPartnerService;
    @Mock
    private SapPartnerService sapPartnerService;
    @Mock
    private WsPartnerService wsPartnerService;
    @Mock
    private UserUtilityService userUtilityService;
    @Mock
    private PemCodeListService pemCodeListService;
    @Mock
    private AwsS3PartnerService awsS3PartnerService;
    @Mock
    private ConnectDirectPartnerService connectDirectPartnerService;
    //@InjectMocks
    private ManagePartnerService managePartnerService;

    @BeforeEach
    void inIt() {
        managePartnerService = new ManagePartnerService(partnerService, activityHistoryService, httpPartnerService,
                ftpPartnerService, remoteConnectDirectPartnerService, ecPartnerService, fileSystemPartnerService, mailboxPartnerService,
                mqPartnerService, remoteFtpPartnerService, sapPartnerService, wsPartnerService, awsS3PartnerService,
                connectDirectPartnerService, pemCodeListService, userUtilityService, sterlingAs2PartnerService, googleDrivePartnerService);
    }

    @Test
    @DisplayName("Get Partner")
    public void testGetPartner() {
        Mockito.when(partnerService.findPartnerById(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(ftpPartnerService.get(Mockito.anyString())).thenReturn(generateFtpModel("FTP"));
        String pkId = "123456";
        managePartnerService.getPartner(pkId);
        Mockito.verify(partnerService, Mockito.times(1)).findPartnerById(Mockito.anyString());
        Mockito.verify(ftpPartnerService, Mockito.times(1)).get(Mockito.anyString());
    }

//    @Test
//    @DisplayName("Get Partner throwing notfound")
//    public void testgetPartner1() {
//        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
//            Mockito.when(partnerService.findPartnerById(Mockito.anyString())).thenThrow(notFound("Partner"));
//            String pkId = "123456";
//            managePartnerService.getPartner(pkId);
//        });
//        Mockito.verify(partnerService, Mockito.times(1)).findPartnerById(Mockito.anyString());
//        Mockito.verify(ftpPartnerService, Mockito.never()).get(Mockito.anyString());
//    }

    @Test
    @DisplayName("Search Partner")
    public void testSearchPartner() {
        Mockito.when(partnerService.search(Mockito.any(), Mockito.any())).thenReturn(generateList());
        managePartnerService.search(Mockito.any(), Mockito.any());
        Mockito.verify(partnerService, Mockito.times(1)).search(Mockito.any(), Mockito.any());
    }

    @Test
    @DisplayName("Get Activity History")
    public void testGetHistory() {
        Mockito.when(activityHistoryService.getTradingPartnerHistory(Mockito.anyString(), Mockito.any())).thenReturn(generateList1());
        managePartnerService.getHistory(Mockito.anyString(), Mockito.any());
        Mockito.verify(activityHistoryService, Mockito.times(1)).getTradingPartnerHistory(Mockito.anyString(), Mockito.any());
    }

    @Test
    @DisplayName("Get All Template Profiles List")
    public void testGetAllTemplateProfilesList() {
        Mockito.when(partnerService.getAllTemplateProfiles()).thenReturn(generateList2());
        managePartnerService.getAllTemplateProfilesList();
        Mockito.verify(partnerService, Mockito.times(1)).getAllTemplateProfiles();
    }

    @Test
    @DisplayName("Get Partner By PartnerName And PartnerId")
    public void testGetPartnerByPartnerNameAndPartnerId() {
        Mockito.when(partnerService.findByPartnerNameAndPartnerId(Mockito.anyString(), Mockito.anyString())).thenReturn(generateList2());
        managePartnerService.getPartnerByPartnerNameAndPartnerId(generatePemFileTypeModel());
        Mockito.verify(partnerService, Mockito.times(1)).findByPartnerNameAndPartnerId(Mockito.anyString(), Mockito.anyString());
    }


    @Test
    @DisplayName("Get Partner By PartnerName And PartnerId notFound")
    public void testGetPartnerByPartnerNameAndPartnerId1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.findByPartnerNameAndPartnerId(Mockito.anyString(), Mockito.anyString())).thenThrow(notFound("Partner"));
            managePartnerService.getPartnerByPartnerNameAndPartnerId(generatePemFileTypeModel());
        });
        Mockito.verify(partnerService, Mockito.times(1)).findByPartnerNameAndPartnerId(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Get Partners List")
    public void testGetPartnersList() {
        Mockito.when(partnerService.findAllPartnerProfiles()).thenReturn(generateList2());
        Mockito.when(userUtilityService.getUserOrRole(Mockito.anyBoolean())).thenReturn(SUPER_ADMIN);
        managePartnerService.getPartnersList();
        Mockito.verify(partnerService, Mockito.times(1)).findAllPartnerProfiles();
    }

    @Test
    @DisplayName("Find All Partners And CodeList")
    public void testFindAllPartnersAndCodeList() {
        Mockito.when(pemCodeListService.findAllByProfilesNotIn(Mockito.anyList())).thenReturn(getPemCodeListEntitiesList());
        Mockito.when(partnerService.findAllPartnerProfiles()).thenReturn(generateList2());
        managePartnerService.finaAllPartnersAndCodeList();
        Mockito.verify(pemCodeListService, Mockito.times(1)).findAllByProfilesNotIn(Mockito.anyList());
        Mockito.verify(partnerService, Mockito.times(1)).findAllPartnerProfiles();
    }

    @Test
    @DisplayName("Get Partners Map")
    public void testGetPartnersMap() {
        Mockito.when(partnerService.findAllPartnerProfiles()).thenReturn(generateList2());
        Mockito.when(userUtilityService.getUserOrRole(Mockito.anyBoolean())).thenReturn(SUPER_ADMIN);
        managePartnerService.getPartnersMap();
        Mockito.verify(partnerService, Mockito.times(1)).findAllPartnerProfiles();
    }

    @Test
    @DisplayName("Status Change with isPem false")
    public void testStatusChange() {
        Mockito.when(partnerService.findPartnerById(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(ftpPartnerService.get(Mockito.anyString())).thenReturn(generateFtpModel("FTP"));
        String pkId = "123456";
        managePartnerService.statusChange(pkId, false, false);
        Mockito.verify(partnerService, Mockito.never()).getUniquePartner(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).findPartnerById(Mockito.anyString());
        Mockito.verify(ftpPartnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(ftpPartnerService, Mockito.times(1)).update(Mockito.any());
    }

    @Test
    @DisplayName("Status Change with isPem true")
    public void testStatusChange1() {
        Mockito.when(partnerService.getUniquePartner(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(partnerService.findPartnerById(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(ftpPartnerService.get(Mockito.anyString())).thenReturn(generateFtpModel("FTP"));
        String pkId = "123456";
        managePartnerService.statusChange(pkId, false, true);
        Mockito.verify(partnerService, Mockito.times(1)).getUniquePartner(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).findPartnerById(Mockito.anyString());
        Mockito.verify(ftpPartnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(ftpPartnerService, Mockito.times(1)).update(Mockito.any());
    }

    @Test
    @DisplayName("Save Partner")
    public void testSavePartner() {
        Mockito.when(ftpPartnerService.save(Mockito.any())).thenReturn(Mockito.anyString());
        managePartnerService.savePartner(generatePartnerInfoModel("FTP"));
        Mockito.verify(ftpPartnerService, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Delete Partner With isPem True")
    public void testDelete() {
        Mockito.when(partnerService.getUniquePartner(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(partnerService.findPartnerById(Mockito.anyString())).thenReturn(getPartnerEntity());
        String pkId = "123456";
        managePartnerService.delete(pkId, true, true, false);
        Mockito.verify(partnerService, Mockito.times(1)).getUniquePartner(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).findPartnerById(Mockito.anyString());
        Mockito.verify(ftpPartnerService, Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    @DisplayName("Delete Partner with isPem False")
    public void testDelete1() {
        Mockito.when(partnerService.findPartnerById(Mockito.anyString())).thenReturn(getPartnerEntity());
        String pkId = "123456";
        managePartnerService.delete(pkId, false, true, false);
        Mockito.verify(partnerService, Mockito.never()).getUniquePartner(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).findPartnerById(Mockito.anyString());
        Mockito.verify(ftpPartnerService, Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    @DisplayName("Get Profiles By Protocol")
    public void testGetProfilesByProtocol() {
        Mockito.when(partnerService.findAllByTpProtocolAndIsProtocolHubInfoLike(Mockito.anyString(), Mockito.anyString())).thenReturn(generateList2());
        managePartnerService.getProfilesByProtocolAndHubInfo(Mockito.anyString(), Mockito.anyBoolean());
        Mockito.verify(partnerService, Mockito.times(1)).findAllByTpProtocolAndIsProtocolHubInfoLike(Mockito.anyString(), Mockito.anyString());
    }


    List<PemCodeListEntity> getPemCodeListEntitiesList() {
        List<PemCodeListEntity> ls1 = new ArrayList<>();
        ls1.add(getPemCodeListEntity());
        return ls1;
    }

    PemCodeListEntity getPemCodeListEntity() {
        PemCodeListEntity pemCodeListEntity = new PemCodeListEntity();
        pemCodeListEntity.setPkId("123456789");
        pemCodeListEntity.setProfileName("ProfileName");
        pemCodeListEntity.setProtocol("FTP");
        pemCodeListEntity.setCorrelationValue1("Cor1");
        return pemCodeListEntity;
    }

    PemFileTypeModel generatePemFileTypeModel() {
        PemFileTypeModel pemFileTypeModel = new PemFileTypeModel();
        pemFileTypeModel.setApplication("Application");
        pemFileTypeModel.setPartner("Partner");
        pemFileTypeModel.setPkId("123456");
        return pemFileTypeModel;
    }

    PartnerEntity getPartnerEntity() {
        PartnerEntity partnerEntity = new PartnerEntity();
        partnerEntity.setPkId("123456");
        partnerEntity.setTpId("123456");
        partnerEntity.setTpName("tpname");
        partnerEntity.setTpProtocol("FTP");
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

    PartnerEntity getPartnerEntity1() {
        PartnerEntity partnerEntity = new PartnerEntity();
        partnerEntity.setTpName("template");
        partnerEntity.setTpId("123456");
        partnerEntity.setAddressLine1("AddressLine1");
        partnerEntity.setAddressLine2("AddressLine2");
        partnerEntity.setEmail("EmailId@email.com");
        partnerEntity.setPhone("1234567890");
        partnerEntity.setTpProtocol("FTP");
        partnerEntity.setTpPickupFiles("N");
        partnerEntity.setFileTpServer("N");
        partnerEntity.setIsProtocolHubInfo("N");
        partnerEntity.setStatus("N");
        return partnerEntity;

    }

    PartnerEntity getPartnerEntity2() {
        PartnerEntity partnerEntity = new PartnerEntity();
        partnerEntity.setTpName("template");
        partnerEntity.setTpId("123456");
        partnerEntity.setAddressLine1("AddressLine1");
        partnerEntity.setAddressLine2("AddressLine2");
        partnerEntity.setEmail("template@email.com");
        partnerEntity.setPhone("9876543210");
        partnerEntity.setTpProtocol("FTP");
        partnerEntity.setTpPickupFiles("N");
        partnerEntity.setFileTpServer("N");
        partnerEntity.setIsProtocolHubInfo("N");
        partnerEntity.setStatus("N");
        return partnerEntity;

    }

    FtpModel generateFtpModel(String protocol) {
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
        ftpModel.setHostName("hostname");
        ftpModel.setPortNumber("portnumber");
        ftpModel.setUserName("userid");
        ftpModel.setPassword("password");
        ftpModel.setFileType("filetype");
        ftpModel.setTransferType("trnsfertype");
        ftpModel.setInDirectory("Indirectory");
        ftpModel.setOutDirectory("outdirectory");
        ftpModel.setCertificateId("certid");
        ftpModel.setKnownHostKey("knownhost");
        ftpModel.setAdapterName("adaptername");
        ftpModel.setPoolingInterval("23");
        ftpModel.setDeleteAfterCollection(true);
        ftpModel.setHubInfo(false);
        ftpModel.setCreateDirectoryInSI(false);
        ftpModel.setCreateUserInSI(false);
        return ftpModel;
    }

    PartnerActivityHistoryEntity generatePartnerActivityHistoryEntity() {
        PartnerActivityHistoryEntity partnerActivityHistoryEntity = new PartnerActivityHistoryEntity();
        partnerActivityHistoryEntity.setActivity("partner Created");
        partnerActivityHistoryEntity.setPkId("123456");
        partnerActivityHistoryEntity.setTpRefId("6543210");
        partnerActivityHistoryEntity.setUserId("Admin");
        partnerActivityHistoryEntity.setUserName("admin");
        return partnerActivityHistoryEntity;
    }

    PartnerInfoModel<FtpModel> generatePartnerInfoModel(String protocol) {
        PartnerInfoModel partnerInfoModel = new PartnerInfoModel<FtpModel>();
        partnerInfoModel.setProtocol(protocol);
        partnerInfoModel.setPartner(generateFtpModel("FTP"));
        return partnerInfoModel;
    }

    Page<PartnerEntity> generateList() {
        List<PartnerEntity> li = new ArrayList<>();
        Page<PartnerEntity> pagedTasks = new PageImpl<>(li);
        li.add(getPartnerEntity());
        return pagedTasks;
    }

    Page<PartnerActivityHistoryEntity> generateList1() {
        List<PartnerActivityHistoryEntity> li1 = new ArrayList<>();
        Page<PartnerActivityHistoryEntity> pagedTasks1 = new PageImpl<>(li1);
        li1.add(generatePartnerActivityHistoryEntity());
        return pagedTasks1;
    }

    List<PartnerEntity> generateList2() {
        List<PartnerEntity> li = new ArrayList<>();
        li.add(getPartnerEntity());
        li.add(getPartnerEntity());
        li.add(getPartnerEntity1());
        li.add(getPartnerEntity2());
        return li;
    }

}
