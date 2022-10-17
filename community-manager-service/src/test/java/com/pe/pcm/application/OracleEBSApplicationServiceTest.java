
package com.pe.pcm.application;


import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.application.entity.ApplicationEntity;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.OracleEbsModel;
import com.pe.pcm.protocol.OracleEbsService;
import com.pe.pcm.protocol.oracleebs.entity.OracleEbsEntity;
import com.pe.pcm.workflow.ProcessService;
import com.pe.pcm.workflow.entity.ProcessEntity;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class OracleEBSApplicationServiceTest {

    @MockBean
    private ApplicationService applicationService;
    @MockBean
    private ActivityHistoryService activityHistoryService;
    @MockBean
    private OracleEbsService oracleEBSService;
    @MockBean
    private ManageProtocolService manageProtocolService;
    @MockBean
    private ProcessService processService;
    @InjectMocks
    private OracleEBSApplicationService oracleEBSApplicationService;


//    @Test
//    @DisplayName(value = "Check Duplicate Application")
//    void duplicateApplicationTest() {
//        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
//            Mockito.when(applicationService.find(Mockito.anyString())).thenReturn(Optional.of(getApplicationEntity("ApplicationId")));
//            oracleEBSApplicationService.duplicateApplication(Mockito.anyString());
//        });
//        Mockito.verify(applicationService, Mockito.times(1)).find(Mockito.anyString());
//        Assertions.assertEquals(CommunityManagerServiceException.class, CommunityManagerServiceException.class);
//        Assertions.assertNotEquals(GlobalExceptionHandler.conflict("Application"), CommunityManagerServiceException.class);
//    }

    @Test
    @DisplayName("Create Oracle EBS with unknown protocol")
    void testCreate() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            oracleEBSApplicationService.save(generateOracleEBSModel("OracleEBS1"));
            Mockito.verify(applicationService, Mockito.never()).find(Mockito.anyString());
            Mockito.verify(oracleEBSService, Mockito.never()).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        });
    }

    @Test
    @DisplayName("Create Oracle EBS with known protocol")
    void testCreate1() {
        oracleEBSApplicationService.save(generateOracleEBSModel("ORACLE_EBS"));
        Mockito.verify(oracleEBSService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Create a Oracle EBS")
    void testCreate3() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.find(Mockito.anyString())).thenThrow(notFound("Application"));
            oracleEBSApplicationService.save(generateOracleEBSModel("OracleEBS1"));
        });
        Mockito.verify(applicationService, Mockito.never()).find(Mockito.anyString());
    }

    @Test
    @DisplayName("Create a  Oracle EBS with passing an valid data")
    void testCreate4() {
        Mockito.when(applicationService.find(Mockito.anyString())).thenReturn(Optional.empty());
        oracleEBSApplicationService.save(generateOracleEBSModel("ORACLE_EBS"));
        Mockito.verify(applicationService, Mockito.never()).find(Mockito.anyString());
        Mockito.verify(oracleEBSService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Delete a  Oracle EBS with passing an valid data")
    void testDelete() {
        Mockito.when(processService.findByApplicationProfile(Mockito.anyString())).thenReturn(new ArrayList<>());
        oracleEBSApplicationService.delete("123456");
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(oracleEBSService, Mockito.times(1)).delete(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    @DisplayName("Delete a  Oracle EBS ")
    void testDelete1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(processService.findByApplicationProfile(Mockito.anyString())).thenThrow(internalServerError("Unable to Delete this Application, Because it has workflow"));
            oracleEBSApplicationService.delete("123456");
        });
        Mockito.verify(applicationService, Mockito.never()).get(Mockito.any());
        Mockito.verify(oracleEBSService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).delete(Mockito.any());
    }

    @Test
    @DisplayName("Delete a  Oracle EBS ")
    void testDelete2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(processService.findByApplicationProfile(Mockito.anyString())).thenReturn(getProcessEntities());
            oracleEBSApplicationService.delete("123456");
        });
        Mockito.verify(applicationService, Mockito.never()).get(Mockito.any());
        Mockito.verify(oracleEBSService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).delete(Mockito.any());
    }

    @Test
    @DisplayName("Get Oracle EBS")
    void testGet() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity("ApplicationId"));
        Mockito.when(oracleEBSService.get(Mockito.anyString())).thenReturn(getOracleEBSEntity());
        oracleEBSApplicationService.get("12345");
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(oracleEBSService, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Update Oracle EBS ")
    void testUpdate() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.get(Mockito.anyString())).thenThrow(notFound("Application"));
            oracleEBSApplicationService.update(generateOracleEBSModel("OracleEBS1"));
        });
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Update a  Oracle EBS AApplication1")
    void testUpdate1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity("ApplicationId"));
            Mockito.when(oracleEBSService.get(Mockito.anyString())).thenThrow(notFound("Application"));
            oracleEBSApplicationService.update(generateOracleEBSModel("OracleEBS1"));
        });
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(oracleEBSService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(oracleEBSService, Mockito.never()).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(applicationService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Update  Oracle EBS Application with a valid data")
    void testUpdate3() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity("ApplicationId"));
        Mockito.when(oracleEBSService.get(Mockito.anyString())).thenReturn(getOracleEBSEntity());
        Mockito.when(oracleEBSService.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getOracleEBSEntity());
        Mockito.when(applicationService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getApplicationEntity("ApplicationId1"));
        oracleEBSApplicationService.update(generateOracleEBSModel("ORACLE_EBS"));
        Mockito.verify(oracleEBSService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).updateApplicationActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
    }

    @Test
    @DisplayName("Update with protocol change")
    void testUpdate4() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity("ApplicationId"));
        Mockito.when(oracleEBSService.get(Mockito.anyString())).thenReturn(getOracleEBSEntity());
        oracleEBSApplicationService.update(generateOracleEBSModel("FTP"));
        Mockito.verify(manageProtocolService, Mockito.times(1)).deleteProtocol(Mockito.anyString(), Mockito.anyString());
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Update with protocol not change")
    void testUpdate5() {
        Mockito.when(applicationService.get(Mockito.anyString())).thenReturn(getApplicationEntity("ApplicationId"));
        Mockito.when(oracleEBSService.get(Mockito.anyString())).thenReturn(getOracleEBSEntity());
        oracleEBSApplicationService.update(generateOracleEBSModel("ORACLE_EBS"));
        Mockito.verify(applicationService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(oracleEBSService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(oracleEBSService, Mockito.never()).delete(Mockito.anyString());
    }

    OracleEbsModel generateOracleEBSModel(String protocol) {
        OracleEbsModel oracleEBSModel = new OracleEbsModel();
        oracleEBSModel.setPkId("123456");
        oracleEBSModel.setNameBod("NameBod");
        oracleEBSModel.setName("Name");
        oracleEBSModel.setOrProtocol("ORACLE_EBS");
        oracleEBSModel.setPassword("password");
        oracleEBSModel.setProtocol(protocol);
        return oracleEBSModel;
    }

    OracleEbsEntity getOracleEBSEntity() {
        OracleEbsEntity oracleEBSEntity = new OracleEbsEntity();
        oracleEBSEntity.setPkId("123456");
        oracleEBSEntity.setPassword("password");
        oracleEBSEntity.setName("Name");
        oracleEBSEntity.setProtocol("protocol");
        return oracleEBSEntity;
    }

    ApplicationEntity getApplicationEntity(String applicationId) {
        ApplicationEntity applicationEntity = new ApplicationEntity();
        applicationEntity.setAppDropFiles("AppDropfiles");
        applicationEntity.setAppIntegrationProtocol("ORACLE_EBS");
        applicationEntity.setAppIsActive("AppIsActive");
        applicationEntity.setApplicationName("ApplicationName");
        applicationEntity.setApplicationId(applicationId);
        applicationEntity.setAppPickupFiles("AppPickupFiles");
        applicationEntity.setAppProtocolRef("AppProtocolRef");
        applicationEntity.setEmailId("Email@e.com");
        applicationEntity.setPhone("7598008944");
        applicationEntity.setPkId("123456");
        return applicationEntity;
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

