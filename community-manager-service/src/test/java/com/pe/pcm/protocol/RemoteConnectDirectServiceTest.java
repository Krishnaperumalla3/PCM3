package com.pe.pcm.protocol;

import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.miscellaneous.CertificateUserUtilityService;
import com.pe.pcm.protocol.remotecd.RemoteConnectDirectRepository;
import com.pe.pcm.protocol.remotecd.entity.RemoteConnectDirectEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
public class RemoteConnectDirectServiceTest {
    @MockBean
    private RemoteConnectDirectRepository remoteConnectDirectRepository;
    @MockBean
    private CertificateUserUtilityService certificateUserUtilityService;
    //@InjectMocks
    private RemoteConnectDirectService remoteConnectDirectService;

    @BeforeEach
    void inIt() {
        remoteConnectDirectService = new RemoteConnectDirectService(remoteConnectDirectRepository,certificateUserUtilityService);
    }

    @Test
    @DisplayName("Save Cd Service")
    public void testSave() {
        Mockito.when(remoteConnectDirectRepository.findBySubscriberId(Mockito.anyString())).thenReturn(Optional.of(getCdEntity()));
        remoteConnectDirectService.save(getCdEntity());
        Mockito.verify(remoteConnectDirectRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("SaveProtocol Cd Service")
    public void testSaveProtocol() {
        Mockito.when(remoteConnectDirectRepository.findBySubscriberId(Mockito.anyString())).thenReturn(Optional.of(getCdEntity()));
        remoteConnectDirectService.saveProtocol(generateCdModel("Connect:Direct"), "1", "2", "sub");
        Mockito.verify(remoteConnectDirectRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Get Cd Service")
    public void testGet() {
        Mockito.when(remoteConnectDirectRepository.findBySubscriberId(Mockito.anyString())).thenReturn(Optional.of(getCdEntity()));
        String pkId = "123456";
        remoteConnectDirectService.get(pkId);
        Mockito.verify(remoteConnectDirectRepository, Mockito.times(1)).findBySubscriberId(Mockito.anyString());
    }

    @Test
    @DisplayName("Get Cd Service1")
    public void testGet1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(remoteConnectDirectRepository.findBySubscriberId(Mockito.anyString())).thenThrow(notFound("Prottocol"));
            String pkId = "123456";
            remoteConnectDirectService.get(pkId);
        });
        Mockito.verify(remoteConnectDirectRepository, Mockito.times(1)).findBySubscriberId(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete Cd Service")
    public void testDelete() {
        Mockito.when(remoteConnectDirectRepository.findBySubscriberId(Mockito.anyString())).thenReturn(Optional.of(getCdEntity()));
        String pkId = "123456";
        remoteConnectDirectService.delete(pkId);
        Mockito.verify(remoteConnectDirectRepository, Mockito.times(1)).findBySubscriberId(Mockito.anyString());
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
        remoteCdModel.setOperatingSystem("Windows");
        return remoteCdModel;
    }

    RemoteConnectDirectEntity getCdEntity() {
        RemoteConnectDirectEntity remoteConnectDirectEntity = new RemoteConnectDirectEntity();
        remoteConnectDirectEntity.setPoolingIntervalMins("PoolingInterval");
        remoteConnectDirectEntity.setPkId("123456");
        remoteConnectDirectEntity.setIsHubInfo("HubInfo");
        return remoteConnectDirectEntity;
    }
}
