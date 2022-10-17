package com.pe.pcm.certificate;

import com.pe.pcm.b2b.B2BApiService;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.sfg.SFGApiService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.nio.charset.StandardCharsets;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class ManageCertificateServiceTest {

    @MockBean
    private B2BApiService b2BApiService;
    private SFGApiService sfgApiService;
    private ManageCertificateService manageCertificateService;
    private PgpPublicKeyRepository pgpPublicKeyRepository;

    @BeforeEach
    void inIt() {
        manageCertificateService = new ManageCertificateService(b2BApiService, sfgApiService, pgpPublicKeyRepository, true, true);
    }

    @Test
    @DisplayName("Upload CA Cert")
    void testUploadCACert() {
        byte[] fileContent = "CaCertificate".getBytes(StandardCharsets.UTF_8);
        MockMultipartFile file = new MockMultipartFile("file", "NameOfTheFile", "multipart/form-data", fileContent);
        manageCertificateService.uploadCACert(file, "FileName");
    }

    @Test
    @DisplayName("Upload System Cert")
    void testUploadSystemCertificate() {
        byte[] fileContent = "SystemCertificate".getBytes(StandardCharsets.UTF_8);
        MockMultipartFile file = new MockMultipartFile("file", "NameOfTheFile", "multipart/form-data", fileContent);
        manageCertificateService.uploadSystemCert(file, "FileName", "SystemCert", "PrivateKey");
    }

    @Test
    @DisplayName("Upload Trusted Cert")
    void testUploadTrustedCertificate() {
        byte[] fileContent = "TrustedCertificate".getBytes(StandardCharsets.UTF_8);
        MockMultipartFile file = new MockMultipartFile("file", "NameOfTheFile", "multipart/form-data", fileContent);
        manageCertificateService.uploadTrustedCert(file, "FileName");
    }

    @Test
    @DisplayName("Upload Ssh KnownHostKey")
    void testUploadSshKnownHostKey() {
        byte[] fileContent = "SshKnownHostKey".getBytes(StandardCharsets.UTF_8);
        MockMultipartFile file = new MockMultipartFile("file", "NameOfTheFile", "multipart/form-data", fileContent);
        manageCertificateService.uploadSshKnownHostKey(file, "FileName");
    }

    @Test
    @DisplayName("Upload SSH UID Key")
    void testUploadSSHUIDKey() {
        byte[] fileContent = "SSHUIDKey".getBytes(StandardCharsets.UTF_8);
        MockMultipartFile file = new MockMultipartFile("file", "NameOfTheFile", "multipart/form-data", fileContent);
        manageCertificateService.uploadSSHUIDKey(file, "FileName");
    }

    @Test
    @DisplayName("UploadSshAuthorizedUserKey")
    void testUploadSshAuthorizedUserKey() {
        byte[] fileContent = "SshAuthorizedUserKey".getBytes(StandardCharsets.UTF_8);
        MockMultipartFile file = new MockMultipartFile("file", "NameOfTheFile", "multipart/form-data", fileContent);
        manageCertificateService.uploadSshAuthorizedUserKey(file, "FileName");
    }

    @Test
    @DisplayName("Is B2B Active")
    void testIsB2BActive() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            manageCertificateService = new ManageCertificateService(b2BApiService, sfgApiService, pgpPublicKeyRepository, false, true);
            manageCertificateService.isB2bEnabled();
        });
        Assertions.assertEquals(CommunityManagerServiceException.class, CommunityManagerServiceException.class);
    }

}
