package com.pe.pcm.activity;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pe.pcm.application.AppActivityHistoryRepository;
import com.pe.pcm.application.entity.AppActivityHistoryEntity;
import com.pe.pcm.application.entity.ApplicationEntity;
import com.pe.pcm.certificate.CaCertInfoService;
import com.pe.pcm.envelope.EdiActivityHistoryRepository;
import com.pe.pcm.envelope.entity.EdiPropertiesActivityHistoryEntity;
import com.pe.pcm.envelope.entity.EdiPropertiesEntity;
import com.pe.pcm.partner.PartnerActivityHistoryRepository;
import com.pe.pcm.partner.PartnerService;
import com.pe.pcm.partner.entity.PartnerActivityHistoryEntity;
import com.pe.pcm.partner.entity.PartnerEntity;
import com.pe.pcm.protocol.awss3.entity.AwsS3Entity;
import com.pe.pcm.protocol.ec.entity.EcEntity;
import com.pe.pcm.workflow.WorkFlowActivityHistoryRepository;
import com.pe.pcm.workflow.entity.WorkFlowActivityHistoryEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.CommonFunctions.isNullThrowError;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class ActivityHistoryServiceTest {
    @Mock
    private PartnerActivityHistoryRepository partnerActivityHistoryRepository;
    @Mock
    private AppActivityHistoryRepository appActivityHistoryRepository;
    @Mock
    private EdiActivityHistoryRepository ediActivityHistoryRepository;
    @Mock
    private WorkFlowActivityHistoryRepository workFlowActivityHistoryRepository;
    @Mock
    private PartnerService partnerService;
    @Mock
    private CaCertInfoService caCertInfoService;
    //@InjectMocks
    private ActivityHistoryService activityHistoryService;
    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void inIt() {
        activityHistoryService = new ActivityHistoryService(partnerActivityHistoryRepository,appActivityHistoryRepository,
                ediActivityHistoryRepository,workFlowActivityHistoryRepository,partnerService,caCertInfoService);
    }

    @Test
    @DisplayName("Save Partner Activity")
    void testSavePartnerActivity() {
        Mockito.when(partnerActivityHistoryRepository.save(Mockito.any())).thenReturn(generatePartnerActivityHistoryEntity());
        activityHistoryService.savePartnerActivity("1", "2");
        Mockito.verify(partnerActivityHistoryRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Save Application Activity")
    void testSaveApplicationActivity() {
        Mockito.when(appActivityHistoryRepository.save(Mockito.any())).thenReturn(generateAppActivityHistoryEntity());
        activityHistoryService.saveApplicationActivity("1", "2");
        Mockito.verify(appActivityHistoryRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Save Envelope Activity")
    void testSaveEnvelopeActivity() {
        Mockito.when(ediActivityHistoryRepository.save(Mockito.any())).thenReturn(generateEdiPropertiesActivityHistoryEntity());
        activityHistoryService.saveEnvelopeActivity("1", "2");
        Mockito.verify(ediActivityHistoryRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Get Trading Partner History")
    void testGetTradingPartnerHistory() {
        Mockito.when(partnerActivityHistoryRepository.findAllByTpRefId(Mockito.anyString(), Mockito.any())).thenReturn(getPartnerActivityHistoryEntities());
        activityHistoryService.getTradingPartnerHistory("123456", Pageable.unpaged());
        Mockito.verify(partnerActivityHistoryRepository, Mockito.times(1)).findAllByTpRefId(Mockito.anyString(), Mockito.any());
    }

    @Test
    @DisplayName("Get Application History")
    void testGetApplicationHistory() {
        Mockito.when(appActivityHistoryRepository.findAllByAppRefId(Mockito.anyString(), Mockito.any())).thenReturn(getAppActivityHistoryEntities());
        activityHistoryService.getApplicationHistory("123456", Pageable.unpaged());
        Mockito.verify(appActivityHistoryRepository, Mockito.times(1)).findAllByAppRefId(Mockito.anyString(), Mockito.any());
    }

    @Test
    @DisplayName("Get Envelope History")
    void testGetEnvelopeHistory() {
        Mockito.when(ediActivityHistoryRepository.findAllByEdiRefId(Mockito.anyString(), Mockito.any())).thenReturn(getEdiPropertiesActivityHistoryEntities());
        activityHistoryService.getEnvelopeHistory("123456", Pageable.unpaged());
        Mockito.verify(ediActivityHistoryRepository, Mockito.times(1)).findAllByEdiRefId(Mockito.anyString(), Mockito.any());
    }

    @Test
    @DisplayName("WorkFlow Activity History Entity")
    void testWorkFlowActivityHistoryEntity() {
        Mockito.when(workFlowActivityHistoryRepository.findAllByProcessRefId(Mockito.anyString(), Mockito.any())).thenReturn(getWorkFlowActivityHistoryEntities());
        activityHistoryService.getWorkFlowHistory("123456", Pageable.unpaged());
        Mockito.verify(workFlowActivityHistoryRepository, Mockito.times(1)).findAllByProcessRefId(Mockito.anyString(), Mockito.any());
    }

    @Test
    @DisplayName("Save Workflow Activity")
    void testSaveWorkflowActivity() {
        Mockito.when(workFlowActivityHistoryRepository.save(Mockito.any())).thenReturn(generateWorkFlowActivityHistoryEntity());
        activityHistoryService.saveWorkflowActivity("Profile", "123456", "toString");
        Mockito.verify(workFlowActivityHistoryRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Update Application Activity isUpdate false")
    void testUpdateApplicationActivity() {
        activityHistoryService.updateApplicationActivity(getApplicationEntity(), getApplicationEntity(), getOldProtocolEntityMap(), getNewProtocolEntityMap(), false);
        Mockito.verify(appActivityHistoryRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    @DisplayName("Update Application Activity isUpdate true")
    void testUpdateApplicationActivity1() {
        activityHistoryService.updateApplicationActivity(getApplicationEntity(), getOldApplicationEntity(), getOldProtocolEntityMap(), getNewProtocolEntityMap(), true);
        Mockito.verify(appActivityHistoryRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Update Partner Activity isUpdate false")
    void testUpdatePartnerActivity() {
        activityHistoryService.updatePartnerActivity(getPartnerEntity(), getPartnerEntity(), getOldProtocolEntityMap(), getNewProtocolEntityMap(), false);
        Mockito.verify(partnerActivityHistoryRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    @DisplayName("Update Partner Activity isUpdate true")
    void testUpdatePartnerActivity1() {
        activityHistoryService.updatePartnerActivity(getPartnerEntity(), getOldPartnerEntity(), getOldProtocolEntityMap(), getNewProtocolEntityMap(), true);
        Mockito.verify(partnerActivityHistoryRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Update Partner Activity with Existing Connection")
    void testUpdatePartnerActivity2() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getOldPartnerEntity());
        activityHistoryService.updatePartnerActivity(getOldPartnerEntityExistingConn(),getPartnerEntityExistingConn(),getOldProtocolEntityMap1(),  getNewProtocolEntityMap1(), true);
        Mockito.verify(partnerActivityHistoryRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(partnerService,Mockito.times(2)).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Update Envelope Activity")
    void testUpdateEnvelopeActivity() {
        activityHistoryService.updateEnvelopeActivity(getOldEdiPropertiesEntity(), getEdiPropertiesEntity());
        Mockito.verify(ediActivityHistoryRepository, Mockito.times(1)).save(Mockito.any());
    }


    PartnerActivityHistoryEntity generatePartnerActivityHistoryEntity() {
        PartnerActivityHistoryEntity partnerActivityHistoryEntity = new PartnerActivityHistoryEntity();
        partnerActivityHistoryEntity.setActivity("Activity");
        partnerActivityHistoryEntity.setPkId("123456");
        partnerActivityHistoryEntity.setUserId("UserId");
        partnerActivityHistoryEntity.setUserName("UserName");
        return partnerActivityHistoryEntity;
    }

    AppActivityHistoryEntity generateAppActivityHistoryEntity() {
        AppActivityHistoryEntity appActivityHistoryEntity = new AppActivityHistoryEntity();
        appActivityHistoryEntity.setUserName("userName");
        appActivityHistoryEntity.setUserId("userId");
        appActivityHistoryEntity.setPkId("123456");
        return appActivityHistoryEntity;
    }

    EdiPropertiesActivityHistoryEntity generateEdiPropertiesActivityHistoryEntity() {
        EdiPropertiesActivityHistoryEntity ediPropertiesActivityHistoryEntity = new EdiPropertiesActivityHistoryEntity();
        ediPropertiesActivityHistoryEntity.setActivity("Activity");
        ediPropertiesActivityHistoryEntity.setPkId("123456");
        ediPropertiesActivityHistoryEntity.setUserId("userId");
        ediPropertiesActivityHistoryEntity.setUserName("userName");
        return ediPropertiesActivityHistoryEntity;
    }

    WorkFlowActivityHistoryEntity generateWorkFlowActivityHistoryEntity() {
        WorkFlowActivityHistoryEntity workFlowActivityHistoryEntity = new WorkFlowActivityHistoryEntity();
        workFlowActivityHistoryEntity.setActivity("Activity");
        workFlowActivityHistoryEntity.setPkId("123456");
        return workFlowActivityHistoryEntity;
    }

    ApplicationEntity getApplicationEntity() {
        ApplicationEntity applicationEntity = new ApplicationEntity();
        applicationEntity.setAppDropFiles("AppDropfiles");
        applicationEntity.setAppIntegrationProtocol("AppIntegrationProtocol");
        applicationEntity.setAppIsActive("AppIsActive");
        applicationEntity.setApplicationName("ApplicationName");
        applicationEntity.setApplicationId("ApplicationId");
        applicationEntity.setAppPickupFiles("AppPickupFiles");
        applicationEntity.setAppProtocolRef("AppProtocolRef");
        applicationEntity.setEmailId("Email@e.com");
        applicationEntity.setPhone("7598008944");
        applicationEntity.setPkId("123456");
        applicationEntity.setAppIntegrationProtocol("Application");
        return applicationEntity;
    }

    ApplicationEntity getOldApplicationEntity() {
        ApplicationEntity applicationEntity = new ApplicationEntity();
        applicationEntity.setAppDropFiles("AppDropfiles1");
        applicationEntity.setAppIntegrationProtocol("AppIntegrationProtocol1");
        applicationEntity.setAppIsActive("AppIsActive1");
        applicationEntity.setApplicationName("ApplicationName1");
        applicationEntity.setApplicationId("ApplicationId1");
        applicationEntity.setAppPickupFiles("AppPickupFiles1");
        applicationEntity.setAppProtocolRef("AppProtocolRef1");
        applicationEntity.setEmailId("Email@e.com1");
        applicationEntity.setPhone("75980089441");
        applicationEntity.setPkId("1234561");
        applicationEntity.setAppIntegrationProtocol("Application1");
        return applicationEntity;
    }

    PartnerEntity getPartnerEntity() {
        PartnerEntity partnerEntity = new PartnerEntity();
        partnerEntity.setPkId("123456");
        partnerEntity.setAddressLine1("add1");
        partnerEntity.setAddressLine2("add2");
        partnerEntity.setStatus("active");
        partnerEntity.setTpId("123456");
        partnerEntity.setTpName("tp name");
        partnerEntity.setTpProtocol("AS2");
        partnerEntity.setPartnerProtocolRef("123456");
        partnerEntity.setEmail("email@email.com");
        partnerEntity.setPhone("987654321");
        partnerEntity.setTpPickupFiles("Y");
        partnerEntity.setFileTpServer("TpServer");
        partnerEntity.setPartnerProtocolRef("ProtocolRef");
        partnerEntity.setIsProtocolHubInfo("y");
        return partnerEntity;
    }

    PartnerEntity getOldPartnerEntity() {
        PartnerEntity partnerEntity = new PartnerEntity();
        partnerEntity.setPkId("1234561");
        partnerEntity.setTpId("1234561");
        partnerEntity.setAddressLine1("add11");
        partnerEntity.setAddressLine2("add21");
        partnerEntity.setStatus("active1");
        partnerEntity.setTpName("Trading Partner Name");
        partnerEntity.setTpProtocol("AS21");
        partnerEntity.setPartnerProtocolRef("1234561");
        partnerEntity.setEmail("email@email.com1");
        partnerEntity.setPhone("9876543211");
        partnerEntity.setTpPickupFiles("Y1");
        partnerEntity.setFileTpServer("TpServer1");
        partnerEntity.setPartnerProtocolRef("ProtocolRef1");
        partnerEntity.setIsProtocolHubInfo("y1");
        return partnerEntity;
    }

    PartnerEntity getPartnerEntityExistingConn() {
        PartnerEntity partnerEntity = new PartnerEntity();
        partnerEntity.setPkId("123456");
        partnerEntity.setAddressLine1("add1");
        partnerEntity.setAddressLine2("add2");
        partnerEntity.setStatus("active");
        partnerEntity.setTpId("123456");
        partnerEntity.setTpName("tp name");
        partnerEntity.setTpProtocol("ExistingConnection");
        partnerEntity.setPartnerProtocolRef("123456");
        partnerEntity.setEmail("email@email.com");
        partnerEntity.setPhone("987654321");
        partnerEntity.setTpPickupFiles("Y");
        partnerEntity.setFileTpServer("TpServer");
        partnerEntity.setPartnerProtocolRef("ProtocolRef");
        partnerEntity.setIsProtocolHubInfo("y");
        return partnerEntity;
    }

    PartnerEntity getOldPartnerEntityExistingConn() {
        PartnerEntity partnerEntity = new PartnerEntity();
        partnerEntity.setPkId("1234567");
        partnerEntity.setAddressLine1("add12");
        partnerEntity.setAddressLine2("add22");
        partnerEntity.setStatus("active2");
        partnerEntity.setTpId("1234562");
        partnerEntity.setTpName("tp nam2e");
        partnerEntity.setTpProtocol("ExistingConnection");
        partnerEntity.setPartnerProtocolRef("1234562");
        partnerEntity.setEmail("email@email.com2");
        partnerEntity.setPhone("9876543212");
        partnerEntity.setTpPickupFiles("N");
        partnerEntity.setFileTpServer("TpServer2");
        partnerEntity.setPartnerProtocolRef("ProtocolRef2");
        partnerEntity.setIsProtocolHubInfo("N");
        return partnerEntity;
    }

    Map<String, String> getOldProtocolEntityMap() {
        return mapper.convertValue(getOldAwsS3Entity(),
                new TypeReference<Map<String, String>>() {
                });
    }
    Map<String, String> getNewProtocolEntityMap(){
        return mapper.convertValue(getNewAwsS3Entity(),
                new TypeReference<Map<String, String>>() {
                });
    }


    Map<String, String> getOldProtocolEntityMap1() {
        return mapper.convertValue(getOldEcEntity(),
                new TypeReference<Map<String, String>>() {
                });
    }
    Map<String, String> getNewProtocolEntityMap1(){
        return mapper.convertValue(getNewEcEntity(),
                new TypeReference<Map<String, String>>() {
                });
    }

    EcEntity getOldEcEntity() {
        EcEntity ecEntity = new EcEntity();
        ecEntity.setSubscriberId("123456");
        ecEntity.setSubscriberType("TP");
        ecEntity.setEcProtocol("FTP");
        ecEntity.setEcProtocolRef("FTP");
        return ecEntity;
    }

    EcEntity getNewEcEntity() {
        EcEntity ecEntity = new EcEntity();
        ecEntity.setSubscriberId("1234567");
        ecEntity.setSubscriberType("APP");
        ecEntity.setEcProtocol("FTPS");
        ecEntity.setEcProtocolRef("FTPS");
        return ecEntity;
    }

    AwsS3Entity getOldAwsS3Entity() {
        AwsS3Entity awsS3Entity = new AwsS3Entity();
        awsS3Entity.setIsActive("N");
        awsS3Entity.setAccessKey(isNullThrowError.apply("AccessKey", "Access Key").trim());
        awsS3Entity.setBucketName(isNullThrowError.apply("Bucket Name", "Bucket Name").trim());
        awsS3Entity.setFileName("FileName");
        awsS3Entity.setEndpoint("EndPoint");
        awsS3Entity.setInMailbox("/mailbox");
        awsS3Entity.setSecretKey(isNullThrowError.apply("Secret Key", "Secret Key").trim());
        awsS3Entity.setRegion("Region");
        awsS3Entity.setPoolingIntervalMins("5M");
        awsS3Entity.setAdapterName("Adapter Name");
        awsS3Entity.setIsHubInfo("N");
        awsS3Entity.setSourcePath(isNotNull("SourcePath") ? "SourcePath" : "default");
        awsS3Entity.setFileType("FileType");
        return awsS3Entity;
    }

    AwsS3Entity getNewAwsS3Entity() {
        AwsS3Entity awsS3Entity = new AwsS3Entity();
        awsS3Entity.setIsActive("Y");
        awsS3Entity.setAccessKey(isNullThrowError.apply("AccessKey", "Access Key").trim());
        awsS3Entity.setBucketName(isNullThrowError.apply("Bucket Name", "Bucket Name").trim());
        awsS3Entity.setFileName("FileName1");
        awsS3Entity.setEndpoint("EndPoint");
        awsS3Entity.setInMailbox("/mailbox");
        awsS3Entity.setSecretKey(isNullThrowError.apply("Secret Key", "Secret Key").trim());
        awsS3Entity.setRegion("Region1");
        awsS3Entity.setPoolingIntervalMins("5M");
        awsS3Entity.setAdapterName("Adapter Name1");
        awsS3Entity.setIsHubInfo("N1");
        awsS3Entity.setSourcePath(isNotNull("SourcePath") ? "SourcePath" : "default");
        awsS3Entity.setFileType("FileType1");
        return awsS3Entity;
    }


    Page<PartnerActivityHistoryEntity> getPartnerActivityHistoryEntities() {
        List<PartnerActivityHistoryEntity> partnerActivityHistoryEntities = new ArrayList<>();
        Page<PartnerActivityHistoryEntity> pagedtasks = new PageImpl<>(partnerActivityHistoryEntities);
        partnerActivityHistoryEntities.add(generatePartnerActivityHistoryEntity());
        return pagedtasks;
    }

    Page<AppActivityHistoryEntity> getAppActivityHistoryEntities() {
        List<AppActivityHistoryEntity> appActivityHistoryEntities = new ArrayList<>();
        Page<AppActivityHistoryEntity> page = new PageImpl<>(appActivityHistoryEntities);
        appActivityHistoryEntities.add(generateAppActivityHistoryEntity());
        return page;
    }

    Page<EdiPropertiesActivityHistoryEntity> getEdiPropertiesActivityHistoryEntities() {
        List<EdiPropertiesActivityHistoryEntity> ediPropertiesActivityHistoryEntities = new ArrayList<>();
        Page<EdiPropertiesActivityHistoryEntity> page = new PageImpl<>(ediPropertiesActivityHistoryEntities);
        ediPropertiesActivityHistoryEntities.add(generateEdiPropertiesActivityHistoryEntity());
        return page;
    }

    Page<WorkFlowActivityHistoryEntity> getWorkFlowActivityHistoryEntities() {
        List<WorkFlowActivityHistoryEntity> workFlowActivityHistoryEntities = new ArrayList<>();
        Page<WorkFlowActivityHistoryEntity> page = new PageImpl<>(workFlowActivityHistoryEntities);
        workFlowActivityHistoryEntities.add(generateWorkFlowActivityHistoryEntity());
        return page;
    }

    EdiPropertiesEntity getEdiPropertiesEntity() {
        EdiPropertiesEntity ediPropertiesEntity = new EdiPropertiesEntity();
        ediPropertiesEntity.setPkId("123456")
                .setPartnerid("PartnerId")
                .setPartnername("PartnerName")
                .setDirection("Direction")
                .setValidateinput("ValidInput")
                .setValidateoutput("ValidOut")
                .setIsasenderidqal("SenderIDQL")
                .setIsasenderid("SenderId")
                .setIsareceiveridqal("ReceiverIDQL")
                .setIsareceiverid("ReceiverId")
                .setInterversion("InterVersion")
                .setUseindicator("Indicator")
                .setSegterm("SegTerm")
                .setSubeleterm("SubeleTerm")
                .setEleterm("EleTerm")
                .setReleasechar("Release")
                .setRetainenv("Retain")
                .setGssenderid("SenderGS")
                .setGsreceiverid("ReceiverGS")
                .setFuncationalidcode("Functional")
                .setRespagencycode("Res")
                .setGroupversion("group")
                .setStsenderid("stsender")
                .setStreceiverid("streceiver")
                .setGeninack("geninak")
                .setAckdetlevel("ackdetlevel")
                .setTrnsetidcode("trisidcode")
                .setDataextraction("dataextract")
                .setExtractionmailbox("extractionmail")
                .setComplcheck("complcheck")
                .setComplcheckmap("complcheck")
                .setBusinessprocess("business")
                .setInvokebpforisa("invokebp")
                .setExtractionmailboxbp("extractmail")
                .setExpectack("expectack")
                .setIntackreq("intackreq")
                .setUsecorrelation("usecorrelation")
                .setAcceptlookalias("acceptlook")
                .setIsaacceptlookalias("isaaccept")
                .setGlobalcontno("globalcont")
                .setPercontnumcheck("percont")
                .setPerdupnumcheck("perdump")
                .setAckoverduehr("ackoever")
                .setAckoverduemin("ackoevermin")
                .setIsaenvelopeid("ISA1")
                .setIsaenvelopename("ISA1")
                .setGsenvelopeid("GS1")
                .setGsenvelopename("GS1")
                .setStenvelopeid("ST1")
                .setStenvelopename("ST1")
                .setIsacontstd("1")
                .setIsaauthinfo("12345678901")
                .setIsaauthinfoqual("001")
                .setIsaauthsecinfo("12345678901")
                .setIsaauthsecqual("001")
                .setLimitintersize("NO1")
                .setBatchtransaction("1")
                .setHippacompliance("NO1")
                .setHippavallevel("1")
                .setErrorbp("1")
                .setAcceptnoninter("NO1")
                .setSpoutboundencode("1")
                .setTa1alaform("1")
                .setAla999form("1")
                .setAccnongroup("1")
                .setAckdetails("1")
                .setEdipostmode("1")
                .setErrorbpmode("Specify1")
                .setInvokebpmode("SpecifyBP1")
                .setEncodedoc("NO1")
                .setBpinvokesetinpd("Invoke1")
                .setStreamseg("YES1")
                .setLastupdatedby("admin1");
        return ediPropertiesEntity;
    }

    EdiPropertiesEntity getOldEdiPropertiesEntity() {
        EdiPropertiesEntity ediPropertiesEntity = new EdiPropertiesEntity();
        ediPropertiesEntity.setPkId("")
                .setPartnerid("")
                .setPartnername("")
                .setDirection("")
                .setValidateinput("")
                .setValidateoutput("")
                .setIsasenderidqal("")
                .setIsasenderid("")
                .setIsareceiveridqal("")
                .setIsareceiverid("")
                .setInterversion("")
                .setUseindicator("")
                .setSegterm("")
                .setSubeleterm("")
                .setEleterm("")
                .setReleasechar("")
                .setRetainenv("")
                .setGssenderid("")
                .setGsreceiverid("")
                .setFuncationalidcode("")
                .setRespagencycode("")
                .setGroupversion("")
                .setStsenderid("")
                .setStreceiverid("")
                .setGeninack("")
                .setAckdetlevel("")
                .setTrnsetidcode("")
                .setDataextraction("")
                .setExtractionmailbox("")
                .setComplcheck("")
                .setComplcheckmap("")
                .setBusinessprocess("")
                .setInvokebpforisa("")
                .setExtractionmailboxbp("")
                .setExpectack("")
                .setIntackreq("")
                .setUsecorrelation("")
                .setAcceptlookalias("")
                .setIsaacceptlookalias("")
                .setGlobalcontno("")
                .setPercontnumcheck("")
                .setPerdupnumcheck("")
                .setAckoverduehr("")
                .setAckoverduemin("")
                .setIsaenvelopeid("ISA")
                .setIsaenvelopename("ISA")
                .setGsenvelopeid("GS")
                .setGsenvelopename("GS")
                .setStenvelopeid("ST")
                .setStenvelopename("ST")
                .setIsacontstd("")
                .setIsaauthinfo("1234567890")
                .setIsaauthinfoqual("00")
                .setIsaauthsecinfo("1234567890")
                .setIsaauthsecqual("00")
                .setLimitintersize("NO")
                .setBatchtransaction("")
                .setHippacompliance("NO")
                .setHippavallevel("")
                .setErrorbp("")
                .setAcceptnoninter("NO")
                .setSpoutboundencode("")
                .setTa1alaform("")
                .setAla999form("")
                .setAccnongroup("")
                .setAckdetails("")
                .setEdipostmode("")
                .setErrorbpmode("Specify")
                .setInvokebpmode("SpecifyBP")
                .setEncodedoc("NO")
                .setBpinvokesetinpd("Invoke")
                .setStreamseg("YES")
                .setLastupdatedby("admin");
        return ediPropertiesEntity;
    }

}
