package com.pe.pcm.protocol;


import com.pe.pcm.b2b.B2BApiService;
import com.pe.pcm.b2b.usermailbox.RemoteUserInfoModel;
import com.pe.pcm.certificate.CaCertInfoService;
import com.pe.pcm.certificate.SshKHostKeyService;
import com.pe.pcm.certificate.SshKeyPairService;
import com.pe.pcm.miscellaneous.PasswordUtilityService;
import com.pe.pcm.protocol.ftp.FtpRepository;
import com.pe.pcm.protocol.ftp.entity.FtpEntity;
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

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class FtpServiceTest {
    @MockBean
    private FtpRepository ftpRepository;
    @MockBean
    private B2BApiService b2BApiService;
    @MockBean
    private CaCertInfoService caCertInfoService;
    @MockBean
    private SshKHostKeyService sshKHostKeyService;
    @MockBean
    private SshKeyPairService sshKeyPairService;
    @MockBean
    private PasswordUtilityService passwordUtilityService;
    //@InjectMocks
    private FtpService ftpService;

    @BeforeEach
    void inIt() {
        ftpService = new FtpService(ftpRepository,caCertInfoService,sshKHostKeyService,
                sshKeyPairService,passwordUtilityService);
    }

    @Test
    @DisplayName("Save Ftp")
    public void testSave() {
        ftpService.save(getFtpEntity());
        Mockito.verify(ftpRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Get Ftp")
    public void testGet() {
        Mockito.when(ftpRepository.findBySubscriberId(Mockito.anyString())).thenReturn(Optional.of(getFtpEntity()));
        String pkId = "123456";
        ftpService.get(pkId);
        Mockito.verify(ftpRepository, Mockito.times(1)).findBySubscriberId(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete Ftp")
    public void testDelete() {
        Mockito.when(ftpRepository.findBySubscriberId(Mockito.anyString())).thenReturn(Optional.of(getFtpEntity()));
        String pkId = "123456";
        ftpService.delete(pkId);
        Mockito.verify(ftpRepository, Mockito.times(1)).findBySubscriberId(Mockito.anyString());
        Mockito.verify(ftpRepository, Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    @DisplayName("Save Protocol Ftp")
    public void testSaveProtocol() {
        Mockito.when(ftpRepository.save(Mockito.any())).thenReturn(getFtpEntity());
        ftpService.saveProtocol(generateFtpModel("FTP"), "1", "2", "Sub");
        Mockito.verify(ftpRepository, Mockito.times(1)).save(Mockito.any());
    }

    FtpEntity getFtpEntity() {
        FtpEntity ftpEntity = new FtpEntity();
        ftpEntity.setProtocolType("protocol");
        ftpEntity.setIsActive("Active");
        ftpEntity.setHostName("Host");
        ftpEntity.setAdapterName("Adapter");
        ftpEntity.setDeleteAfterCollection("Delete");
        ftpEntity.setIsHubInfo("Hub");
        ftpEntity.setPkId("123456");
        return ftpEntity;
    }

    FtpModel generateFtpModel(String protocol) {
        FtpModel ftpModel = new FtpModel();
        ftpModel.setPkId("123456");
        ftpModel.setProfileName("ProfileName");
        ftpModel.setProfileId("ProfileId");
        ftpModel.setProtocol(protocol);
        ftpModel.setEmailId("Email@email.com");
        ftpModel.setPhone("85635900323");
        ftpModel.setStatus(false);
        ftpModel.setHostName("HostName");
        ftpModel.setPortNumber("0012");
        ftpModel.setUserName("UserName");
        ftpModel.setPassword("password");
        ftpModel.setFileType("FileType");
        ftpModel.setTransferType("TransferType");
        ftpModel.setInDirectory("InDirectory");
        ftpModel.setOutDirectory("OutDirectory");
        ftpModel.setCertificateId("135476");
        ftpModel.setKnownHostKey("FTP");
        ftpModel.setAdapterName("AdapterName");
        ftpModel.setPoolingInterval("PoolingInterval");
        ftpModel.setDeleteAfterCollection(false);
        ftpModel.setHubInfo(false);
        return ftpModel;
    }

    RemoteUserInfoModel generateUserModel() {
        RemoteUserInfoModel remoteUserInfoModel = new RemoteUserInfoModel();
        remoteUserInfoModel.setPassword("password");
        remoteUserInfoModel.setUserName("UserName");
        remoteUserInfoModel.setInDirectory("InDirectory");
        remoteUserInfoModel.setOutDirectory("OutDirectory");
        remoteUserInfoModel.setCreateUserInSI(false);
        remoteUserInfoModel.setCreateDirectoryInSI(false);
        return remoteUserInfoModel;
    }


}
