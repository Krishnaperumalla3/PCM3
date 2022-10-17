package com.pe.pcm.protocol.as2;


import com.pe.pcm.certificate.entity.TrustedCertInfoEntity;
import com.pe.pcm.protocol.as2.certificate.SciDocxUserCertService;
import com.pe.pcm.protocol.as2.si.certificate.SciDocxUserCertRepository;
import com.pe.pcm.protocol.as2.si.certificate.entity.SciDocxUserCertEntity;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class SciDocxUserCertServiceTest {
    @MockBean
    private SciDocxUserCertRepository sciDocxUserCertRepository;
    //@InjectMocks
    private SciDocxUserCertService sciDocxUserCertService;

    @BeforeEach
    void inIt() {
        sciDocxUserCertService = new SciDocxUserCertService(sciDocxUserCertRepository);
    }

    @Test
    @DisplayName("Save Docx User Service ")
    public void testSave() {
        sciDocxUserCertService.save("transportObjectId", getTrustedCertInfoEntity(), null);
        Mockito.verify(sciDocxUserCertRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Update Docx User Service")
    public void testUpdate() {
        Mockito.when(sciDocxUserCertRepository.findByDocExchangeId(Mockito.any())).thenReturn(Optional.ofNullable(null));
        sciDocxUserCertService.update("transportObjectId", getTrustedCertInfoEntity());
        Mockito.verify(sciDocxUserCertRepository, Mockito.times(1)).findByDocExchangeId(Mockito.anyString());
        Mockito.verify(sciDocxUserCertRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    @DisplayName("Update Docx User Service1")
    public void testUpdate1() {
        Mockito.when(sciDocxUserCertRepository.findByDocExchangeId(Mockito.anyString())).thenReturn(Optional.of(getSciDocxUserCert()));
        sciDocxUserCertService.update("transportObjectId", getTrustedCertInfoEntity());
        Mockito.verify(sciDocxUserCertRepository, Mockito.times(1)).findByDocExchangeId(Mockito.anyString());
        Mockito.verify(sciDocxUserCertRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Get Docx User Service")
    public void testGet() {
        Mockito.when(sciDocxUserCertRepository.findByDocExchangeId(Mockito.anyString())).thenReturn(Optional.of(getSciDocxUserCert()));
        String transportObjectId = "123456";
        sciDocxUserCertService.get(transportObjectId);
        Mockito.verify(sciDocxUserCertRepository, Mockito.times(1)).findByDocExchangeId(Mockito.anyString());
    }


    @Test
    @DisplayName("Get Docx User Service1")
    public void testGet1() {
        Mockito.when(sciDocxUserCertRepository.findByDocExchangeId(Mockito.anyString())).thenReturn(Optional.ofNullable(null));
        sciDocxUserCertService.get(null);
        Mockito.verify(sciDocxUserCertRepository, Mockito.never()).findByDocExchangeId(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete Docx User Service")
    public void testDelete() {
        Mockito.when(sciDocxUserCertRepository.findByDocExchangeId(Mockito.anyString())).thenReturn(Optional.of(getSciDocxUserCert()));
        String transportObjectId = "123456";
        sciDocxUserCertService.delete(transportObjectId);
        Mockito.verify(sciDocxUserCertRepository, Mockito.times(1)).findByDocExchangeId(Mockito.anyString());
        Mockito.verify(sciDocxUserCertRepository, Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    @DisplayName("Delete Docx User Service1")
    public void testDelete1() {
        Mockito.when(sciDocxUserCertRepository.findByDocExchangeId(Mockito.anyString())).thenReturn(Optional.empty());
        sciDocxUserCertService.delete("12343546");
        Mockito.verify(sciDocxUserCertRepository, Mockito.times(1)).findByDocExchangeId(Mockito.anyString());
        Mockito.verify(sciDocxUserCertRepository, Mockito.never()).delete(Mockito.any());
    }


    TrustedCertInfoEntity getTrustedCertInfoEntity() {
        TrustedCertInfoEntity trustedCertInfoEntity = new TrustedCertInfoEntity();
        trustedCertInfoEntity.setCertIssuerRdn("CertIssueRdn");
        trustedCertInfoEntity.setModifiedBy("ModifiedBy");
        trustedCertInfoEntity.setKeyUsageBits("KeyUsageBits");
        trustedCertInfoEntity.setName("Name");
        return trustedCertInfoEntity;
    }


    SciDocxUserCertEntity getSciDocxUserCert1() {
        SciDocxUserCertEntity sciDocxUserCertEntity = new SciDocxUserCertEntity();
        sciDocxUserCertEntity.setCertificateKey("CertificateKey");
        sciDocxUserCertEntity.setCertOrder(1234);
        sciDocxUserCertEntity.setUserCertId("UserCertId");
        sciDocxUserCertEntity.setCreateprogid("Createprogid");
        return sciDocxUserCertEntity;
    }


    List<SciDocxUserCertEntity> getSciDocxUserCert() {
        List<SciDocxUserCertEntity> sciDocxUserCertEntities = new ArrayList<>();
        sciDocxUserCertEntities.add(getSciDocxUserCert1());
        return sciDocxUserCertEntities;
    }


}
