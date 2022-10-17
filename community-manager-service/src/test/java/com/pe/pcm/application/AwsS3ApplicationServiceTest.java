//package com.pe.pcm.application;
//
//import com.pe.pcm.activity.ActivityHistoryService;
//import com.pe.pcm.application.entity.ApplicationEntity;
//import com.pe.pcm.exception.CommunityManagerServiceException;
//import com.pe.pcm.exception.GlobalExceptionHandler;
//import com.pe.pcm.protocol.AwsS3Model;
//import com.pe.pcm.protocol.AwsS3Service;
//import com.pe.pcm.protocol.ManageProtocolService;
//import com.pe.pcm.protocol.awss3.entity.AwsS3Entity;
//import com.pe.pcm.workflow.ProcessService;
//import com.pe.pcm.workflow.entity.ProcessEntity;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;
//import static com.pe.pcm.utils.CommonFunctions.*;
//import static com.pe.pcm.utils.PCMConstants.APPLICATION;
//
//@ExtendWith(SpringExtension.class)
//@ExtendWith(MockitoExtension.class)
//@ActiveProfiles("test")
//class AwsS3ApplicationServiceTest {
//
//    @MockBean
//    private ApplicationService applicationService;
//    @MockBean
//    private ActivityHistoryService activityHistoryService;
//    @MockBean
//    private ProcessService processService;
//    @MockBean
//    private AwsS3Service awsS3Service;
//    @MockBean
//    private ManageProtocolService manageProtocolService;
//    @InjectMocks
//    private AwsS3ApplicationService awsS3ApplicationService;
//
//
//    @Test
//    @DisplayName(value = "Check Duplicate Application")
//    void duplicateApplicationTest() {
//        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
//            Mockito.when(applicationService.find(Mockito.anyString())).thenReturn(Optional.of(getApplication("ApplicationId")));
//            awsS3ApplicationService.duplicateApplication(Mockito.anyString());
//        });
//        Mockito.verify(applicationService, Mockito.times(1)).find(Mockito.anyString());
//        Assertions.assertEquals(CommunityManagerServiceException.class, CommunityManagerServiceException.class);
//        Assertions.assertNotEquals(GlobalExceptionHandler.conflict("Application"), CommunityManagerServiceException.class);
//    }
//
//
//    @Test
//    @DisplayName(value = "Save AWS-S3 Application")
//    void saveAwsS3Application() {
//        Mockito.when(awsS3Service.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getAwsS3Entity());
//        Mockito.when(applicationService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getApplication("ApplicationId"));
//        awsS3ApplicationService.duplicateApplication(Mockito.anyString());
//        awsS3ApplicationService.save(generateAwsS3Model("AWS_S3"));
//        Mockito.verify(awsS3Service, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
//        Mockito.verify(applicationService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
//        Mockito.verify(activityHistoryService, Mockito.times(1)).saveApplicationActivity(Mockito.anyString(), Mockito.anyString());
//    }
//
//    @Test
//    @DisplayName(value = "Save AWS-S3 Application with Unknown Protocol")
//    void saveAwsS3Application1() {
//        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
//            Mockito.when(awsS3Service.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getAwsS3Entity());
//            Mockito.when(applicationService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getApplication("ApplicationId"));
//            awsS3ApplicationService.duplicateApplication(Mockito.anyString());
//            awsS3ApplicationService.save(generateAwsS3Model("AWS_S31"));
//        });
//        Assertions.assertEquals(CommunityManagerServiceException.class, CommunityManagerServiceException.class);
//        Mockito.verify(awsS3Service, Mockito.never()).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
//        Mockito.verify(applicationService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
//        Mockito.verify(activityHistoryService, Mockito.never()).saveApplicationActivity(Mockito.anyString(), Mockito.anyString());
//    }
//
//    @Test
//    @DisplayName(value = "Save AWS-S3 Application with Not-Existing Application")
//    void saveAwsS3Application2() {
//        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
//            Mockito.when(applicationService.find(Mockito.anyString())).thenReturn(Optional.of(getApplication("ApplicationId")));
//            Mockito.when(awsS3Service.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getAwsS3Entity());
//            Mockito.when(applicationService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getApplication("ApplicationId"));
//            awsS3ApplicationService.duplicateApplication(Mockito.anyString());
//            awsS3ApplicationService.save(generateAwsS3Model("AWS_S3"));
//        });
//        Mockito.verify(applicationService, Mockito.times(1)).find(Mockito.anyString());
//        Assertions.assertEquals(CommunityManagerServiceException.class, CommunityManagerServiceException.class);
//        Assertions.assertNotEquals(GlobalExceptionHandler.conflict("Application"), CommunityManagerServiceException.class);
//        Assertions.assertEquals(CommunityManagerServiceException.class, CommunityManagerServiceException.class);
//        Mockito.verify(awsS3Service, Mockito.never()).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
//        Mockito.verify(applicationService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
//        Mockito.verify(activityHistoryService, Mockito.never()).saveApplicationActivity(Mockito.anyString(), Mockito.anyString());
//    }
//
//
//    @Test
//    @DisplayName("Update AWS-S3 Application")
//    void updateAwsS3Application() {
//        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplication("ApplicationId"));
//        Mockito.when(awsS3Service.get(Mockito.anyString())).thenReturn(getAwsS3Entity());
//        Mockito.when(awsS3Service.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getAwsS3Entity());
//        Mockito.when(applicationService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getApplication("ApplicationId"));
//        awsS3ApplicationService.update(generateAwsS3Model("AWS_S3"));
//        Mockito.verify(awsS3Service, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(awsS3Service, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
//        Mockito.verify(applicationService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
//        Mockito.verify(activityHistoryService, Mockito.times(1)).updateApplicationActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
//    }
//
//    @Test
//    @DisplayName(value = "Update AWS-S3 Application with profileId Change")
//    void updateAwsS3Application1() {
//        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplication("ApplicationId1"));
//        Mockito.when(awsS3Service.get(Mockito.anyString())).thenReturn(getAwsS3Entity());
//        Mockito.when(awsS3Service.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getAwsS3Entity());
//        Mockito.when(applicationService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getApplication("ApplicationId"));
//        awsS3ApplicationService.duplicateApplication(Mockito.anyString());
//        awsS3ApplicationService.update(generateAwsS3Model("AWS_S3"));
//        Mockito.verify(awsS3Service, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(awsS3Service, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
//        Mockito.verify(applicationService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
//        Mockito.verify(activityHistoryService, Mockito.times(1)).updateApplicationActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
//    }
//
//    @Test
//    @DisplayName(value = "Update AWS-S3 Application with protocol change")
//    void updateAwsS3Application2() {
//        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplication("ApplicationId"));
//        Mockito.when(awsS3Service.get(Mockito.anyString())).thenReturn(getAwsS3Entity());
//        Mockito.when(awsS3Service.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getAwsS3Entity());
//        Mockito.when(applicationService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getApplication("ApplicationId"));
//        Mockito.doNothing().when(manageProtocolService).deleteProtocol(Mockito.anyString(), Mockito.anyString());
//        awsS3ApplicationService.update(generateAwsS3Model("FTP"));
//        Assertions.assertEquals(CommunityManagerServiceException.class, CommunityManagerServiceException.class);
//        Mockito.verify(awsS3Service, Mockito.never()).get(Mockito.anyString());
//        Mockito.verify(manageProtocolService, Mockito.times(1)).deleteProtocol(Mockito.anyString(), Mockito.anyString());
//        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(awsS3Service, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
//        Mockito.verify(applicationService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
//        Mockito.verify(activityHistoryService, Mockito.times(1)).updateApplicationActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
//    }
//
//    @Test
//    @DisplayName(value = "Update Not-Existing AWS-S3 Application")
//    void updateAwsS3Application3() {
//        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
//            Mockito.when(applicationService.get(Mockito.anyString())).thenThrow(notFound(APPLICATION));
//            Mockito.when(awsS3Service.get(Mockito.anyString())).thenReturn(getAwsS3Entity());
//            Mockito.when(awsS3Service.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getAwsS3Entity());
//            Mockito.when(applicationService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getApplication("ApplicationId"));
//            awsS3ApplicationService.update(generateAwsS3Model("AWS_S3"));
//        });
//        Assertions.assertEquals(CommunityManagerServiceException.class, CommunityManagerServiceException.class);
//        Mockito.verify(awsS3Service, Mockito.never()).get(Mockito.anyString());
//        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(awsS3Service, Mockito.never()).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
//        Mockito.verify(applicationService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
//        Mockito.verify(activityHistoryService, Mockito.never()).updateApplicationActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
//    }
//
//    @Test
//    @DisplayName(value = "Update Not-Existing AWS-S3 protocol")
//    void updateAwsS3Application4() {
//        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
//            Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplication("ApplicationId"));
//            Mockito.when(awsS3Service.get(Mockito.anyString())).thenThrow(notFound("protocol"));
//            Mockito.when(awsS3Service.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getAwsS3Entity());
//            Mockito.when(applicationService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getApplication("ApplicationId"));
//            awsS3ApplicationService.update(generateAwsS3Model("AWS_S3"));
//        });
//        Assertions.assertEquals(CommunityManagerServiceException.class, CommunityManagerServiceException.class);
//        Mockito.verify(awsS3Service, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(awsS3Service, Mockito.never()).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
//        Mockito.verify(applicationService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
//        Mockito.verify(activityHistoryService, Mockito.never()).updateApplicationActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
//    }
//
//    @Test
//    @DisplayName(value = "Delete AWS-S3 Protocol With Existing Workflow for Application")
//    void deleteAwsS3Application() {
//        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
//            Mockito.when(processService.findByApplicationProfile(Mockito.anyString())).thenReturn(getApplicationsInProcess());
//            Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplication("123456"));
//            awsS3ApplicationService.delete(Mockito.anyString());
//        });
//        Mockito.verify(processService, Mockito.times(1)).findByApplicationProfile(Mockito.anyString());
//        Mockito.verify(applicationService, Mockito.never()).get(Mockito.anyString());
//        Mockito.verify(awsS3Service, Mockito.never()).delete(Mockito.anyString());
//        Mockito.verify(applicationService, Mockito.never()).delete(Mockito.any());
//    }
//
//    @Test
//    @DisplayName(value = "Delete AWS-S3 Protocol With Not-Existing Workflow for Application")
//    void deleteAwsS3Application1() {
//        Mockito.when(processService.findByApplicationProfile(Mockito.anyString())).thenReturn(new ArrayList<>());
//        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplication("123456"));
//        awsS3ApplicationService.delete(Mockito.anyString());
//        Mockito.verify(processService, Mockito.times(1)).findByApplicationProfile(Mockito.anyString());
//        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(awsS3Service, Mockito.times(1)).delete(Mockito.anyString());
//        Mockito.verify(applicationService, Mockito.times(1)).delete(Mockito.any());
//    }
//
//    @Test
//    @DisplayName(value = "Delete AWS-S3 Protocol With Not-Existing Application")
//    void deleteAwsS3Application2() {
//        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
//            Mockito.when(processService.findByApplicationProfile(Mockito.anyString())).thenReturn(new ArrayList<>());
//            Mockito.when(applicationService.get(Mockito.anyString())).thenThrow(notFound(APPLICATION));
//            awsS3ApplicationService.delete(Mockito.anyString());
//        });
//        Assertions.assertEquals(CommunityManagerServiceException.class, CommunityManagerServiceException.class);
//        Assertions.assertNotEquals(GlobalExceptionHandler.class, CommunityManagerServiceException.class);
//        Mockito.verify(processService, Mockito.times(1)).findByApplicationProfile(Mockito.anyString());
//        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(awsS3Service, Mockito.never()).delete(Mockito.anyString());
//        Mockito.verify(applicationService, Mockito.never()).delete(Mockito.any());
//    }
//
//    @Test
//    @DisplayName(value = "Get AWS-S3 Application")
//    void getAwsS3Application() {
//        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplication("123456"));
//        Mockito.when(awsS3Service.get(Mockito.anyString())).thenReturn(getAwsS3Entity());
//        awsS3ApplicationService.get(Mockito.anyString());
//        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(awsS3Service, Mockito.times(1)).get(Mockito.anyString());
//    }
//
//    @Test
//    @DisplayName(value = "Get AWS-S3 Application with Not-Existing Application")
//    void getAwsS3Application1() {
//        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
//            Mockito.when(applicationService.get(Mockito.anyString())).thenThrow(notFound(APPLICATION));
//            Mockito.when(awsS3Service.get(Mockito.anyString())).thenReturn(getAwsS3Entity());
//            awsS3ApplicationService.get(Mockito.anyString());
//        });
//        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(awsS3Service, Mockito.never()).get(Mockito.anyString());
//    }
//
//    @Test
//    @DisplayName(value = "Get AWS-S3 Application with Not-Existing protocol")
//    void getAwsS3Application2() {
//        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
//            Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplication("123456"));
//            Mockito.when(awsS3Service.get(Mockito.anyString())).thenThrow(notFound("Protocol"));
//            awsS3ApplicationService.get(Mockito.anyString());
//        });
//        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(awsS3Service, Mockito.times(1)).get(Mockito.anyString());
//    }
//
//
//    ApplicationEntity getApplication(String applicationId) {
//        ApplicationEntity applicationEntity = new ApplicationEntity();
//        applicationEntity.setPkId("123456");
//        applicationEntity.setAppProtocolRef("654321");
//        applicationEntity.setApplicationId(applicationId);
//        applicationEntity.setApplicationName("AwsS3Application");
//        applicationEntity.setAppIntegrationProtocol("AWS_S3");
//        return applicationEntity;
//    }
//
//    AwsS3Entity getAwsS3Entity() {
//        AwsS3Entity awsS3Entity = new AwsS3Entity();
//        awsS3Entity.setIsActive("N");
//        awsS3Entity.setAccessKey(isNullThrowError.apply("AccessKey", "Access Key").trim());
//        awsS3Entity.setBucketName(isNullThrowError.apply("Bucket Name", "Bucket Name").trim());
//        awsS3Entity.setFileName("FileName");
//        awsS3Entity.setEndpoint("EndPoint");
//        awsS3Entity.setInMailbox("/mailbox");
//        awsS3Entity.setSecretKey(isNullThrowError.apply("Secret Key", "Secret Key").trim());
//        awsS3Entity.setRegion("Region");
//        awsS3Entity.setPoolingIntervalMins("5M");
//        awsS3Entity.setAdapterName("Adapter Name");
//        awsS3Entity.setIsHubInfo("N");
//        awsS3Entity.setSourcePath(isNotNull("SourcePath") ? "SourcePath" : "default");
//        awsS3Entity.setFileType("FileType");
//        return awsS3Entity;
//    }
//
//    AwsS3Model generateAwsS3Model(String protocol) {
//        AwsS3Model awsS3Model = new AwsS3Model();
//        awsS3Model.setPkId("123456");
//        awsS3Model.setProfileName("AwsS3Application");
//        awsS3Model.setProfileId("ApplicationId");
//        awsS3Model.setProtocol(protocol);
//        awsS3Model.setEmailId("Email@id.com");
//        awsS3Model.setPhone("7894561230");
//        awsS3Model.setStatus(convertStringToBoolean("N"));
//        awsS3Model.setHubInfo(convertStringToBoolean("N"));
//        awsS3Model.setFileType("FileType");
//        awsS3Model.setAdapterName("AdapterName");
//        awsS3Model.setPoolingInterval("5M");
//
//        awsS3Model.setAccessKey("AccessKey")
//                .setBucketName("BucketName")
//                .setEndpoint("EndPoint")
//                .setFileName("FileName")
//                .setInMailbox("MailBox")
//                .setSecretKey("SecretKey")
//                .setSourcePath("SourcePath")
//                .setRegion("Region");
//        return awsS3Model;
//    }
//
//    ProcessEntity getProcessEntity() {
//        ProcessEntity processEntity = new ProcessEntity();
//        processEntity.setSeqId("123456");
//        processEntity.setPartnerProfile("Partner Profile");
//        processEntity.setApplicationProfile("Application Profile");
//        processEntity.setFlow("MFT");
//        processEntity.setSeqType("");
//        return processEntity;
//    }
//
//    List<ProcessEntity> getApplicationsInProcess() {
//        List<ProcessEntity> processEntities = new ArrayList<>();
//        processEntities.add(getProcessEntity());
//        return processEntities;
//    }
//}
