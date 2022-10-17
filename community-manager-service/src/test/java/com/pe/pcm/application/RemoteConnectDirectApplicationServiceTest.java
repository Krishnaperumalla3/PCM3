package com.pe.pcm.application;


import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.application.entity.ApplicationEntity;
import com.pe.pcm.application.sfg.RemoteConnectDirectApplicationService;
import com.pe.pcm.b2b.B2BApiService;
import com.pe.pcm.b2b.B2BCdNodeService;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.RemoteCdModel;
import com.pe.pcm.protocol.RemoteConnectDirectService;
import com.pe.pcm.protocol.remotecd.entity.RemoteConnectDirectEntity;
import com.pe.pcm.workflow.ProcessService;
import com.pe.pcm.workflow.entity.ProcessEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class RemoteConnectDirectApplicationServiceTest {

    @Mock
    private RemoteConnectDirectService remoteConnectDirectService;
    @Mock
    private ActivityHistoryService activityHistoryService;
    @Mock
    private B2BApiService b2BApiService;
    @Mock
    private ApplicationService applicationService;
    @Mock
    private ProcessService processService;
    @Mock
    private ManageProtocolService manageProtocolService;
    @Mock
    private B2BCdNodeService b2BCdNodeService;
    //@InjectMocks
    private RemoteConnectDirectApplicationService remoteConnectDirectApplicationService;

    @BeforeEach
    void inIt() {
        remoteConnectDirectApplicationService = new RemoteConnectDirectApplicationService(remoteConnectDirectService, activityHistoryService,
                b2BApiService, applicationService, processService, manageProtocolService, b2BCdNodeService, "CM_PEMCommunity");
    }

    @Test
    @DisplayName("Create a new CdApplicationService with unknown protocol")
    void testCreate() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            remoteConnectDirectApplicationService.save(generateCdModel("SFG_CONNECT_DIRECT1"));
        });
        verify(applicationService, never()).find(anyString());
    }

    @Test
    @DisplayName("Create a new CdApplicationService with known protocol")
    void testCreate1() {
        remoteConnectDirectApplicationService.save(generateCdModel("SFG_CONNECT_DIRECT"));
        verify(applicationService, times(1)).find(anyString());
    }

    @Test
    @DisplayName("Create a new CdApplicationService ")
    void testCreate2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            when(applicationService.find(anyString())).thenThrow(notFound("Application"));
            remoteConnectDirectApplicationService.save(generateCdModel("SFG_CONNECT_DIRECT"));
        });
        verify(applicationService, times(1)).find(anyString());
    }

    @Test
    @DisplayName("Create a new CdApplicationService with passing duplicate protocol")
    void testCreate3() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            when(applicationService.find(anyString())).thenReturn(Optional.ofNullable(getApplicationEntity()));
            remoteConnectDirectApplicationService.save(generateCdModel("SFG_CONNECT_DIRECT"));
            verify(applicationService, times(1)).find(anyString());
            verify(applicationService, never()).save(any(), anyString(), anyString());
            verify(activityHistoryService, never()).saveApplicationActivity(anyString(), anyString());
            verify(b2BApiService, never()).createMailBoxInSI(anyBoolean(), anyString(), anyString());
        });
    }

    @Test
    @DisplayName("Create a CdApplication with an valid data")
    void testCreate4() {
        when(applicationService.find(anyString())).thenReturn(Optional.empty());
        remoteConnectDirectApplicationService.save(generateCdModel("SFG_CONNECT_DIRECT"));
        verify(applicationService, times(1)).save(any(), anyString(), anyString());
        verify(activityHistoryService, times(1)).saveApplicationActivity(anyString(), anyString());
        verify(b2BApiService, never()).createMailBoxInSI(anyBoolean(), anyString(), anyString());
    }

    @Test
    @DisplayName("Delete a CdApplication with an valid data")
    void testDelete() {
        when(processService.findByApplicationProfile(any())).thenReturn(new ArrayList<>());
        when(applicationService.get(anyString())).thenReturn(getApplicationEntity());
        String pkId = "123456";
        remoteConnectDirectApplicationService.delete(pkId, false);
        verify(processService, times(1)).findByApplicationProfile(anyString());
        verify(applicationService, times(1)).get(anyString());
        verify(remoteConnectDirectService, times(1)).delete(anyString());
        verify(applicationService, never()).delete(getApplicationEntity());
    }

    @Test
    @DisplayName("Delete a CdApplication with an valid data")
    void testDelete1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            when(processService.findByApplicationProfile(any())).thenReturn(getProcessEntities());
            String pkId = "123456";
            remoteConnectDirectApplicationService.delete(pkId, false);
        });
        Assertions.assertEquals(CommunityManagerServiceException.class, CommunityManagerServiceException.class);
        verify(processService, times(1)).findByApplicationProfile(anyString());
        verify(applicationService, never()).get(anyString());
        verify(remoteConnectDirectService, never()).delete(anyString());
        verify(applicationService, never()).delete(getApplicationEntity());
    }


    @Test
    @DisplayName("Delete a CdApplication")
    void testDelete2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            when(processService.findByApplicationProfile(anyString())).thenThrow(internalServerError("Unable to delete this Application, Because it has workflow"));
            String pkId = "123456";
            remoteConnectDirectApplicationService.delete(pkId, false);
        });
        verify(applicationService, never()).get(anyString());
        verify(remoteConnectDirectService, never()).delete(anyString());
        verify(applicationService, never()).delete(getApplicationEntity());
    }

    @Test
    @DisplayName("Get CdApplication with passing an valid data")
    void testGet() {
        when(applicationService.get(anyString())).thenReturn(getApplicationEntity());
        when(remoteConnectDirectService.get(anyString())).thenReturn(getCdEntity());
        when(b2BCdNodeService.getNodeInSI(any())).thenReturn(generateCdModel("SFG_CONNECT_DIRECT"));
        final String pkId = "12345";
        remoteConnectDirectApplicationService.get(pkId);
        verify(applicationService, times(1)).get(anyString());
        verify(remoteConnectDirectService, times(1)).get(anyString());
    }

    @Test
    @DisplayName("Update CdApplication ")
    void testUpdate() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            when(applicationService.get(anyString())).thenThrow(notFound("protocol not implemented"));
            remoteConnectDirectApplicationService.update(generateCdModel("SFG_CONNECT_DIRECT"));
        });
        verify(applicationService, times(1)).get(anyString());
    }

    @Test
    @DisplayName("Update CdApplication  and throws an protocol ")
    void testUpdate1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            when(applicationService.get(anyString())).thenThrow(notFound("protocol"));
            remoteConnectDirectApplicationService.update(generateCdModel("SFG_CONNECT_DIRECT2"));
        });
        verify(applicationService, times(1)).get(anyString());
    }

    @Test
    @DisplayName("Update CdApplication1")
    void testUpdate2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            when(applicationService.get(anyString())).thenReturn(getApplicationEntity());
            when(remoteConnectDirectService.get(anyString())).thenThrow(notFound("protocol"));
            remoteConnectDirectApplicationService.update(generateCdModel("SFG_CONNECT_DIRECT"));
            verify(applicationService, times(1)).get(anyString());
            verify(remoteConnectDirectService, times(1)).get(anyString());
            verify(remoteConnectDirectService, never()).delete(anyString());
            verify(activityHistoryService, never()).saveApplicationActivity(anyString(), anyString());
            verify(b2BApiService, never()).createMailBoxInSI(anyBoolean(), anyString(), anyString());
            verify(manageProtocolService, never()).deleteProtocol(any(), any());
        });
    }

    @Test
    @DisplayName("Update CdApplication with a valid data")
    void testUpdate3() {
        when(applicationService.get(anyString())).thenReturn(getApplicationEntity());
        when(remoteConnectDirectService.get(anyString())).thenReturn(getCdEntity());
        when(remoteConnectDirectService.saveProtocol(any(), anyString(), anyString(), anyString())).thenReturn(getCdEntity());
        when(applicationService.save(any(), anyString(), anyString())).thenReturn(getApplicationEntity());
        remoteConnectDirectApplicationService.update(generateCdModel("SFG_CONNECT_DIRECT"));
        verify(remoteConnectDirectService, times(1)).saveProtocol(any(), anyString(), anyString(), anyString());
        verify(applicationService, times(1)).save(any(), anyString(), anyString());
        verify(activityHistoryService, times(1)).updateApplicationActivity(any(), any(), anyMap(), anyMap(), anyBoolean());
        verify(b2BApiService, never()).createMailBoxInSI(anyBoolean(), anyString(), anyString());
    }

    @Test
    @DisplayName("Update CdApplication with protocol change")
    void testUpdate4() {
        when(applicationService.get(anyString())).thenReturn(getApplicationEntity());
        remoteConnectDirectApplicationService.update(generateCdModel("FTPS"));
        verify(manageProtocolService, times(1)).deleteProtocol(anyString(), anyString());
        verify(remoteConnectDirectService, never()).get(anyString());
        verify(applicationService, times(1)).get(anyString());
    }

    @Test
    @DisplayName("Update CdApplication with protocol not change")
    void testUpdate5() {
        when(applicationService.get(anyString())).thenReturn(getApplicationEntity());
        when(remoteConnectDirectService.get(anyString())).thenReturn(getCdEntity());
        remoteConnectDirectApplicationService.update(generateCdModel("SFG_CONNECT_DIRECT"));
        verify(applicationService, times(1)).get(anyString());
        verify(remoteConnectDirectService, times(1)).get(anyString());
        verify(remoteConnectDirectService, never()).delete(anyString());
    }

    RemoteCdModel generateCdModel(String protocol) {
        RemoteCdModel remoteCdModel = new RemoteCdModel();
        remoteCdModel.setPkId("123456");
        remoteCdModel.setProfileName("ProfileName");
        remoteCdModel.setProfileId("ProfileId");
        remoteCdModel.setProtocol(protocol);
        remoteCdModel.setEmailId("Email@email.com");
        remoteCdModel.setPhone("9785436730");
        remoteCdModel.setStatus(false);
        remoteCdModel.setHubInfo(false);
        return remoteCdModel;
    }

    ApplicationEntity getApplicationEntity() {
        ApplicationEntity applicationEntity = new ApplicationEntity();
        applicationEntity.setAppDropFiles("AppDropfiles");
        applicationEntity.setAppIntegrationProtocol("SFG_CONNECT_DIRECT");
        applicationEntity.setAppIsActive("AppIsActive");
        applicationEntity.setApplicationName("ApplicationName");
        applicationEntity.setApplicationId("ApplicationId");
        applicationEntity.setAppPickupFiles("AppPickupFiles");
        applicationEntity.setAppProtocolRef("AppProtocolRef");
        applicationEntity.setEmailId("Email@e.com");
        applicationEntity.setPhone("7598008944");
        applicationEntity.setPkId("12345");
        return applicationEntity;
    }

    RemoteConnectDirectEntity getCdEntity() {
        RemoteConnectDirectEntity remoteConnectDirectEntity = new RemoteConnectDirectEntity();
        remoteConnectDirectEntity.setPort("9080");
        remoteConnectDirectEntity.setAdapterName("AdapterName");
        remoteConnectDirectEntity.setDirectory("InDirectory");
        remoteConnectDirectEntity.setIsActive("IsActive");
        remoteConnectDirectEntity.setIsHubInfo("N");
        remoteConnectDirectEntity.setLocalNodeName("LnodeName");
        remoteConnectDirectEntity.setPkId("1234");
        remoteConnectDirectEntity.setPoolingIntervalMins("pooling");
        remoteConnectDirectEntity.setRemoteFileName("Remote");
        remoteConnectDirectEntity.setCaCertificateName("cacertname,cacert2");
        remoteConnectDirectEntity.setCipherSuits("chiper1,chiper2");
        return remoteConnectDirectEntity;
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
