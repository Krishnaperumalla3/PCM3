package com.pe.pcm.miscellaneous;


import com.pe.pcm.certificate.*;
import com.pe.pcm.certificate.entity.*;
import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.partner.PartnerService;
import com.pe.pcm.protocol.FtpService;
import com.pe.pcm.protocol.HttpService;
import com.pe.pcm.protocol.RemoteConnectDirectService;
import com.pe.pcm.protocol.RemoteFtpService;
import com.pe.pcm.protocol.as2.As2Service;
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

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class CertificateUserUtilityServiceTest {
    @MockBean
    private TrustedCertInfoService trustedCertInfoService;
    @MockBean
    private CaCertInfoService caCertInfoService;
    @MockBean
    private SshKHostKeyService sshKHostKeyService;
    @MockBean
    private SshKeyPairService sshKeyPairService;
    @MockBean
    private CertsAndPriKeyService certsAndPriKeyService;
    @Mock
    private SshUserKeyService sshUserKeyService;
    @Mock
    private AuthXrefSshService authXrefSshService;
    @Mock
    private As2Service as2Service;
    @Mock
    private FtpService ftpService;
    @Mock
    private RemoteFtpService remoteFtpService;
    @Mock
    private HttpService httpService;
    @Mock
    private RemoteConnectDirectService remoteConnectDirectService;
    @Mock
    private PartnerService partnerService;
   // @InjectMocks
    private CertificateUserUtilityService certificateUserUtilityService;

    @BeforeEach
    void inIt() {
        certificateUserUtilityService = new CertificateUserUtilityService(trustedCertInfoService,caCertInfoService,sshKHostKeyService,
                sshKeyPairService,certsAndPriKeyService,sshUserKeyService,authXrefSshService,as2Service,ftpService,
                remoteFtpService,httpService,remoteConnectDirectService,partnerService);
    }

    @Test
    @DisplayName("Get Trusted Cert Info List")
    public void testGetTrustedCertInfoList() {
        Mockito.when(trustedCertInfoService.getTrustedCetInfoList()).thenReturn(Optional.of(getTrustedCertInfoEntities()));
        certificateUserUtilityService.getTrustedCertInfoList();
        Mockito.verify(trustedCertInfoService, Mockito.times(1)).getTrustedCetInfoList();
    }

    @Test
    @DisplayName("Get CaCert Info List")
    public void testGetCaCertInfoList() {
        Mockito.when(caCertInfoService.getCaCertInfoList()).thenReturn(Optional.of(getCaCertInfoEntities()));
        certificateUserUtilityService.getCaCertInfoList();
        Mockito.verify(caCertInfoService, Mockito.times(1)).getCaCertInfoList();
    }

    @Test
    @DisplayName("Get SshK HostKey List")
    public void testGetSshKHostKeyList() {
        Mockito.when(sshKHostKeyService.getSshKHostKeyList()).thenReturn(Optional.of(getKHostKeyEntities()));
        certificateUserUtilityService.getSshKHostKeyList();
        Mockito.verify(sshKHostKeyService, Mockito.times(1)).getSshKHostKeyList();
    }

    @Test
    @DisplayName("Get Ssh KeyPair List")
    public void testGetSshKeyPairList() {
        Mockito.when(sshKeyPairService.findAll()).thenReturn(getSshKeyPairEntities());
        certificateUserUtilityService.getSshKHostKeyList();
        Mockito.verify(sshKHostKeyService, Mockito.times(1)).getSshKHostKeyList();
    }

    @Test
    @DisplayName("Get CaCert Info Map")
    public void testGetCaCertInfoMap() {
        Mockito.when(caCertInfoService.getCaCertInfoList()).thenReturn(Optional.of(getCaCertInfoEntities()));
        certificateUserUtilityService.getCaCertInfoMap();
        Mockito.verify(caCertInfoService, Mockito.times(1)).getCaCertInfoList();
    }

    @Test
    @DisplayName("Get Certs And PriKey Map")
    public void testGetCertsAndPriKeyMap() {
        Mockito.when(certsAndPriKeyService.getCertsAndPriKeyList()).thenReturn(Optional.of(getCertsAndPriKeyEntities()));
        certificateUserUtilityService.getCertsAndPriKeyMap();
        Mockito.verify(certsAndPriKeyService, Mockito.times(1)).getCertsAndPriKeyList();
    }

    CommunityManagerNameModel generateCommunityManagerListModel() {
        CommunityManagerNameModel communityManagerNameModel = new CommunityManagerNameModel("Name");
        communityManagerNameModel.setName("Name");
        return communityManagerNameModel;
    }

    TrustedCertInfoEntity getTrustedCertInfoEntity() {
        TrustedCertInfoEntity trustedCertInfoEntity = new TrustedCertInfoEntity();
        trustedCertInfoEntity.setName("Name");
        trustedCertInfoEntity.setModifiedBy("Modified");
        return trustedCertInfoEntity;
    }

    List<TrustedCertInfoEntity> getTrustedCertInfoEntities() {
        List<TrustedCertInfoEntity> trustedCertInfoEntities = new ArrayList<>();
        trustedCertInfoEntities.add(getTrustedCertInfoEntity());
        return trustedCertInfoEntities;
    }

    CaCertInfoEntity getCaCertInfoEntity() {
        CaCertInfoEntity caCertInfoEntity = new CaCertInfoEntity();
        caCertInfoEntity.setName("Name");
        caCertInfoEntity.setModifiedBy("Modified");
        caCertInfoEntity.setKeyUsageBits("Key");
        caCertInfoEntity.setCaCertificateInfoKey("CertificateKey");
        return caCertInfoEntity;
    }

    List<CaCertInfoEntity> getCaCertInfoEntities() {
        List<CaCertInfoEntity> caCertInfoEntities = new ArrayList<>();
        caCertInfoEntities.add(getCaCertInfoEntity());
        return caCertInfoEntities;
    }

    SshKHostKeyEntity getSshKHostKeyEntity() {
        SshKHostKeyEntity sshKHostKeyEntity = new SshKHostKeyEntity();
        sshKHostKeyEntity.setName("Name");
        sshKHostKeyEntity.setObjectId("12453");
        sshKHostKeyEntity.setUsername("UserName");
        return sshKHostKeyEntity;
    }

    List<SshKHostKeyEntity> getKHostKeyEntities() {
        List<SshKHostKeyEntity> sshKHostKeyEntities = new ArrayList<>();
        sshKHostKeyEntities.add(getSshKHostKeyEntity());
        return sshKHostKeyEntities;
    }

    SshKeyPairEntity getSshKeyPairEntity() {
        SshKeyPairEntity sshKeyPairEntity = new SshKeyPairEntity();
        sshKeyPairEntity.setName("Name");
        sshKeyPairEntity.setUsername("UserName");
        sshKeyPairEntity.setObjectId("Object");
        return sshKeyPairEntity;
    }

    List<SshKeyPairEntity> getSshKeyPairEntities() {
        List<SshKeyPairEntity> sshKeyPairEntities = new ArrayList<>();
        sshKeyPairEntities.add(getSshKeyPairEntity());
        return sshKeyPairEntities;
    }

    CertsAndPriKeyEntity getcertsAndPriKeyEntity() {
        CertsAndPriKeyEntity certsAndPriKeyEntity = new CertsAndPriKeyEntity();
        certsAndPriKeyEntity.setUsername("UserName");
        certsAndPriKeyEntity.setObjectId("123456");
        certsAndPriKeyEntity.setName("Name");
        return certsAndPriKeyEntity;
    }

    List<CertsAndPriKeyEntity> getCertsAndPriKeyEntities() {
        List<CertsAndPriKeyEntity> certsAndPriKeyEntities = new ArrayList<>();
        certsAndPriKeyEntities.add(getcertsAndPriKeyEntity());
        return certsAndPriKeyEntities;
    }

}
