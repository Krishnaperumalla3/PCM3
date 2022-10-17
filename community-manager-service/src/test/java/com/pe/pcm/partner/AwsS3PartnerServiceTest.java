package com.pe.pcm.partner;

import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.b2b.B2BApiService;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.partner.entity.PartnerEntity;
import com.pe.pcm.protocol.AwsS3Model;
import com.pe.pcm.protocol.AwsS3Service;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.awss3.entity.AwsS3Entity;
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
import static com.pe.pcm.utils.CommonFunctions.*;
import static com.pe.pcm.utils.PCMConstants.PARTNER;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class AwsS3PartnerServiceTest {

    @Mock
    private PartnerService partnerService;
    @Mock
    private ActivityHistoryService activityHistoryService;
    @Mock
    private ProcessService processService;
    @Mock
    private AwsS3Service awsS3Service;
    @Mock
    private ManageProtocolService manageProtocolService;
    @Mock
    private B2BApiService b2BApiService;
    //@InjectMocks
    private AwsS3PartnerService awsS3PartnerService;

    @BeforeEach
    void inIt() {
        awsS3PartnerService = new AwsS3PartnerService(partnerService,activityHistoryService,processService,
                awsS3Service,manageProtocolService,b2BApiService,"CM_Community");
    }

    @Test
    @DisplayName(value = "Check Duplicate Partner")
    void duplicateApplicationTest() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.find(Mockito.anyString())).thenReturn(Optional.of(getPartnerEntity("PartnerId")));
            awsS3PartnerService.duplicatePartner(Mockito.anyString());
        });
        Mockito.verify(partnerService, Mockito.times(1)).find(Mockito.anyString());
        Assertions.assertEquals(CommunityManagerServiceException.class, CommunityManagerServiceException.class);
        Assertions.assertNotEquals(GlobalExceptionHandler.conflict("Application"), CommunityManagerServiceException.class);
    }


    @Test
    @DisplayName(value = "Save AWS-S3 Partner")
    void saveAwsS3Application() {
        Mockito.when(awsS3Service.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getAwsS3Entity());
        Mockito.when(partnerService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getPartnerEntity("PartnerId"));
        awsS3PartnerService.duplicatePartner(Mockito.anyString());
        awsS3PartnerService.save(generateAwsS3Model("AWS_S3"));
        Mockito.verify(awsS3Service, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).savePartnerActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName(value = "Save AWS-S3 Partner with Unknown Protocol")
    void saveAwsS3Application1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            awsS3PartnerService.duplicatePartner(Mockito.anyString());
            awsS3PartnerService.save(generateAwsS3Model("AWS_S31"));
        });
        Assertions.assertEquals(CommunityManagerServiceException.class, CommunityManagerServiceException.class);
        Mockito.verify(awsS3Service, Mockito.never()).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.never()).saveApplicationActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName(value = "Save AWS-S3 Partner with Not-Existing Partner")
    void saveAwsS3Application2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.find(Mockito.anyString())).thenReturn(Optional.of(getPartnerEntity("PartnerId")));
            awsS3PartnerService.duplicatePartner(Mockito.anyString());
            awsS3PartnerService.save(generateAwsS3Model("AWS_S3"));
        });
        Mockito.verify(partnerService, Mockito.times(1)).find(Mockito.anyString());
        Assertions.assertEquals(CommunityManagerServiceException.class, CommunityManagerServiceException.class);
        Assertions.assertNotEquals(GlobalExceptionHandler.conflict("Application"), CommunityManagerServiceException.class);
        Assertions.assertEquals(CommunityManagerServiceException.class, CommunityManagerServiceException.class);
        Mockito.verify(awsS3Service, Mockito.never()).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.never()).saveApplicationActivity(Mockito.anyString(), Mockito.anyString());
    }


    @Test
    @DisplayName("Update AWS-S3 Partner")
    void updateAwsS3Application() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity("PartnerId"));
        Mockito.when(awsS3Service.get(Mockito.anyString())).thenReturn(getAwsS3Entity());
        Mockito.when(awsS3Service.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getAwsS3Entity());
        Mockito.when(partnerService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getPartnerEntity("PartnerId"));
        awsS3PartnerService.update(generateAwsS3Model("AWS_S3"));
        Mockito.verify(awsS3Service, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(awsS3Service, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).updatePartnerActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
    }

    @Test
    @DisplayName(value = "Update AWS-S3 Partner with profileId Change")
    void updateAwsS3Application1() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity("PartnerId1"));
        Mockito.when(awsS3Service.get(Mockito.anyString())).thenReturn(getAwsS3Entity());
        Mockito.when(awsS3Service.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getAwsS3Entity());
        Mockito.when(partnerService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getPartnerEntity("PartnerId"));
        awsS3PartnerService.duplicatePartner(Mockito.anyString());
        awsS3PartnerService.update(generateAwsS3Model("AWS_S3"));
        Mockito.verify(awsS3Service, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(awsS3Service, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).updatePartnerActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
    }

    @Test
    @DisplayName(value = "Update AWS-S3 Partner with protocol change")
    void updateAwsS3Application2() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity("PartnerId"));
        Mockito.when(awsS3Service.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getAwsS3Entity());
        Mockito.when(partnerService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getPartnerEntity("PartnerId"));
        Mockito.doNothing().when(manageProtocolService).deleteProtocol(Mockito.anyString(), Mockito.anyString());
        awsS3PartnerService.update(generateAwsS3Model("FTP"));
        Assertions.assertEquals(CommunityManagerServiceException.class, CommunityManagerServiceException.class);
        Mockito.verify(awsS3Service, Mockito.never()).get(Mockito.anyString());
        Mockito.verify(manageProtocolService, Mockito.times(1)).deleteProtocol(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(awsS3Service, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).updatePartnerActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
    }

    @Test
    @DisplayName(value = "Update Not-Existing AWS-S3 Partner")
    void updateAwsS3Application3() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenThrow(notFound(PARTNER));
            awsS3PartnerService.update(generateAwsS3Model("AWS_S3"));
        });
        Assertions.assertEquals(CommunityManagerServiceException.class, CommunityManagerServiceException.class);
        Mockito.verify(awsS3Service, Mockito.never()).get(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(awsS3Service, Mockito.never()).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.never()).updateApplicationActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
    }

    @Test
    @DisplayName(value = "Update Not-Existing AWS-S3 protocol")
    void updateAwsS3Application4() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity("ApplicationId"));
            Mockito.when(awsS3Service.get(Mockito.anyString())).thenThrow(notFound("protocol"));
            awsS3PartnerService.update(generateAwsS3Model("AWS_S3"));
        });
        Assertions.assertEquals(CommunityManagerServiceException.class, CommunityManagerServiceException.class);
        Mockito.verify(awsS3Service, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(awsS3Service, Mockito.never()).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.never()).updateApplicationActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
    }

    @Test
    @DisplayName(value = "Delete AWS-S3 Protocol With Existing Workflow for Partner")
    void deleteAwsS3Application() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(processService.findByPartnerProfile(Mockito.anyString())).thenReturn(getApplicationsInProcess());
            awsS3PartnerService.delete("123456", false);
        });
        Mockito.verify(processService, Mockito.times(1)).findByPartnerProfile(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).get(Mockito.anyString());
        Mockito.verify(awsS3Service, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).delete(Mockito.any());
    }

    @Test
    @DisplayName(value = "Delete AWS-S3 Protocol With Not-Existing Workflow for Partner")
    void deleteAwsS3Application1() {
        Mockito.when(processService.findByPartnerProfile(Mockito.anyString())).thenReturn(new ArrayList<>());
        Mockito.when(partnerService.findPartnerById(Mockito.anyString())).thenReturn(getPartnerEntity("123456"));
        awsS3PartnerService.delete("123456", false);
        Mockito.verify(processService, Mockito.times(1)).findByPartnerProfile(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).findPartnerById(Mockito.anyString());
        Mockito.verify(awsS3Service, Mockito.times(1)).delete(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    @DisplayName(value = "Delete AWS-S3 Protocol With Not-Existing Partner")
    void deleteAwsS3Application2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(processService.findByPartnerProfile(Mockito.anyString())).thenReturn(new ArrayList<>());
            Mockito.when(partnerService.findPartnerById(Mockito.anyString())).thenThrow(notFound(PARTNER));
            awsS3PartnerService.delete("123456", false);
        });
        Assertions.assertEquals(CommunityManagerServiceException.class, CommunityManagerServiceException.class);
        Assertions.assertNotEquals(GlobalExceptionHandler.class, CommunityManagerServiceException.class);
        Mockito.verify(processService, Mockito.times(1)).findByPartnerProfile(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).findPartnerById(Mockito.anyString());
        Mockito.verify(awsS3Service, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).delete(Mockito.any());
    }

    @Test
    @DisplayName(value = "Get AWS-S3 Partner")
    void getAwsS3Application() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity("123456"));
        Mockito.when(awsS3Service.get(Mockito.anyString())).thenReturn(getAwsS3Entity());
        awsS3PartnerService.get("123456", false);
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(awsS3Service, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    @DisplayName(value = "Get AWS-S3 Partner with Not-Existing Partner")
    void getAwsS3Application1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenThrow(notFound(PARTNER));
            awsS3PartnerService.get("123456",false);
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(awsS3Service, Mockito.never()).get(Mockito.anyString());
    }

    @Test
    @DisplayName(value = "Get AWS-S3 Partner with Not-Existing protocol")
    void getAwsS3Application2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity("123456"));
            Mockito.when(awsS3Service.get(Mockito.anyString())).thenThrow(notFound("Protocol"));
            awsS3PartnerService.get("123456", false);
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(awsS3Service, Mockito.times(1)).get(Mockito.anyString());
    }


    PartnerEntity getPartnerEntity(String partnerId) {
        PartnerEntity partnerEntity = new PartnerEntity();
        partnerEntity.setPkId("123456");
        partnerEntity.setPartnerProtocolRef("654321");
        partnerEntity.setTpId(partnerId);
        partnerEntity.setTpName("AwsS3Parter");
        partnerEntity.setTpProtocol("AWS_S3");
        return partnerEntity;
    }

    AwsS3Entity getAwsS3Entity() {
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

    AwsS3Model generateAwsS3Model(String protocol) {
        AwsS3Model awsS3Model = new AwsS3Model();
        awsS3Model.setPkId("123456");
        awsS3Model.setProfileName("AwsS3Partner");
        awsS3Model.setProfileId("PartnerId");
        awsS3Model.setProtocol(protocol);
        awsS3Model.setEmailId("Email@id.com");
        awsS3Model.setPhone("7894561230");
        awsS3Model.setStatus(convertStringToBoolean("N"));
        awsS3Model.setHubInfo(convertStringToBoolean("N"));
        awsS3Model.setFileType("FileType");
        awsS3Model.setAdapterName("AdapterName");
        awsS3Model.setPoolingInterval("5M");

        awsS3Model.setAccessKey("AccessKey")
                .setBucketName("BucketName")
                .setEndpoint("EndPoint")
                .setFileName("FileName")
                .setInMailbox("MailBox")
                .setSecretKey("SecretKey")
                .setSourcePath("SourcePath")
                .setRegion("Region");
        return awsS3Model;
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

    List<ProcessEntity> getApplicationsInProcess() {
        List<ProcessEntity> processEntities = new ArrayList<>();
        processEntities.add(getProcessEntity());
        return processEntities;
    }

}
