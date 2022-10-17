package com.pe.pcm.protocol;


import com.pe.pcm.b2b.B2BRemoteFtpService;
import com.pe.pcm.certificate.CaCertInfoService;
import com.pe.pcm.certificate.SshKHostKeyService;
import com.pe.pcm.certificate.SshKeyPairService;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.miscellaneous.CertificateUserUtilityService;
import com.pe.pcm.miscellaneous.PasswordUtilityService;
import com.pe.pcm.protocol.remoteftp.RemoteFtpRepository;
import com.pe.pcm.protocol.remoteftp.entity.RemoteFtpEntity;
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

import java.util.Optional;

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class RemoteFtpServiceTest {
    @MockBean
    private RemoteFtpRepository remoteFtpRepository;
    @MockBean
    private B2BRemoteFtpService b2BRemoteFtpService;
    @Mock
    private CaCertInfoService caCertInfoService;
    @Mock
    private SshKHostKeyService sshKHostKeyService;
    @Mock
    private SshKeyPairService sshKeyPairService;
    @Mock
    private CertificateUserUtilityService certificateUserUtilityService;
    @Mock
    private PasswordUtilityService passwordUtilityService;
    //@InjectMocks
    private RemoteFtpService remoteFtpService;

    @BeforeEach
    void inIt () {
        remoteFtpService = new RemoteFtpService(remoteFtpRepository,b2BRemoteFtpService,caCertInfoService,
                sshKHostKeyService,sshKeyPairService,certificateUserUtilityService,passwordUtilityService);
    }

    @Test
    @DisplayName("Save Remote Ftp Service")
    public void testSave() {
        remoteFtpService.save(getRemoteFtpEntity());
        Mockito.verify(remoteFtpRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Get Remote Ftp Service")
    public void testGet() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(remoteFtpRepository.findBySubscriberId(Mockito.anyString())).thenThrow(notFound("Partner"));
            remoteFtpService.get("pkId");
        });
        Mockito.verify(remoteFtpRepository, Mockito.times(1)).findBySubscriberId(Mockito.anyString());
    }

    @Test
    @DisplayName("Get Remote Ftp Service1")
    public void testGet1() {
        Mockito.when(remoteFtpRepository.findBySubscriberId(Mockito.anyString())).thenReturn(Optional.of(getRemoteFtpEntity()));
        remoteFtpService.get("pkId");
        Mockito.verify(remoteFtpRepository, Mockito.times(1)).findBySubscriberId(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete Remote Ftp Service")
    public void testDelete() {
        Mockito.when(remoteFtpRepository.findBySubscriberId(Mockito.anyString())).thenReturn(Optional.of(getRemoteFtpEntity()));
        remoteFtpService.delete("pkId");
        Mockito.verify(remoteFtpRepository, Mockito.times(1)).findBySubscriberId(Mockito.anyString());
    }

    @Test
    @DisplayName("Save protocol Remote Ftp Service")
    public void testSaveProtocol() {
        Mockito.when(remoteFtpRepository.findBySubscriberId(Mockito.anyString())).thenReturn(Optional.of(getRemoteFtpEntity()));
        remoteFtpService.saveProtocol(getRemoteFtpModel("SFGFTP"), "1", "2", false, "4", "");
        Mockito.verify(remoteFtpRepository, Mockito.never()).findBySubscriberId(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete protocol Remote Ftp Service")
    public void testDeleteProtocol() {
        Mockito.when(remoteFtpRepository.findBySubscriberId(Mockito.anyString())).thenReturn(Optional.of(getRemoteFtpEntity()));
        Mockito.when(b2BRemoteFtpService.getRemoteFtpProfile(Mockito.any())).thenReturn(getRemoteFtpModel("SFGFTP"));
        remoteFtpService.deleteProtocol("123456", "profileName", "protocol", false);
        Mockito.verify(remoteFtpRepository, Mockito.times(1)).findBySubscriberId(Mockito.anyString());
    }

    @Test
    @DisplayName("Get Remote Ftp Service")
    public void testGetRemoteFtp() {
        Mockito.when(remoteFtpRepository.findBySubscriberId(Mockito.anyString())).thenReturn(Optional.of(getRemoteFtpEntity()));
        remoteFtpService.getRemoteFtp(getRemoteFtpModel("SFGFTP"));
        Mockito.verify(remoteFtpRepository, Mockito.never()).findBySubscriberId(Mockito.anyString());
    }

    RemoteProfileModel getRemoteFtpModel(String protocol) {
        RemoteProfileModel remoteProfileModel = new RemoteProfileModel();
        remoteProfileModel.setPkId("123456");
        remoteProfileModel.setProfileName("ProfileName");
        remoteProfileModel.setProfileId("ProfileId");
        remoteProfileModel.setProtocol(protocol);
        remoteProfileModel.setEmailId("Email@email.com");
        remoteProfileModel.setPhone("458979869507");
        remoteProfileModel.setStatus(false);
        remoteProfileModel.setUserName("UserName");
        remoteProfileModel.setPassword("password");
        remoteProfileModel.setTransferType("TransferType");
        remoteProfileModel.setInDirectory("InDirectory");
        remoteProfileModel.setOutDirectory("Out directory");
        remoteProfileModel.setFileType("FileType");
        remoteProfileModel.setHubInfo(false);
        remoteProfileModel.setDeleteAfterCollection(false);
        return remoteProfileModel;
    }

    RemoteFtpEntity getRemoteFtpEntity() {
        RemoteFtpEntity remoteFtpEntity = new RemoteFtpEntity();
        remoteFtpEntity.setProfileId("ProfileId");
        remoteFtpEntity.setAdapterName("AdapterName");
        remoteFtpEntity.setCertificateId("CertificateId");
        remoteFtpEntity.setDeleteAfterCollection("Collection");
        remoteFtpEntity.setIsActive("Active");
        remoteFtpEntity.setPassword("password");
        remoteFtpEntity.setPkId("123456");
        remoteFtpEntity.setPoolingIntervalMins("PoolingIntervalMinus");
        remoteFtpEntity.setProtocolType("protocol");
        remoteFtpEntity.setIsHubInfo("F");
        return remoteFtpEntity;
    }
}
