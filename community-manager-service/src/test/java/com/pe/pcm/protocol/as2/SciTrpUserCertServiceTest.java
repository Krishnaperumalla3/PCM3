package com.pe.pcm.protocol.as2;


import com.pe.pcm.certificate.entity.TrustedCertInfoEntity;
import com.pe.pcm.protocol.as2.certificate.SciTrpUserCertService;
import com.pe.pcm.protocol.as2.si.certificate.SciTrpUserCertRepository;
import com.pe.pcm.protocol.as2.si.certificate.entity.SciTrpUserCertEntity;
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
public class SciTrpUserCertServiceTest {
    @MockBean
    private SciTrpUserCertRepository sciTrpUserCertRepository;
    //@InjectMocks
    private SciTrpUserCertService sciTrpUserCertService;

    @BeforeEach
    void inIt() {
        sciTrpUserCertService = new SciTrpUserCertService(sciTrpUserCertRepository);
    }


    @Test
    @DisplayName("Save TrpUser Service")
    public void testSave() {
        sciTrpUserCertService.save("transportObjectId", gettrustedCertInfoEntity(), null);
        Mockito.verify(sciTrpUserCertRepository, Mockito.times(1)).save(Mockito.any());
    }

    TrustedCertInfoEntity gettrustedCertInfoEntity() {
        TrustedCertInfoEntity trustedCertInfoEntity = new TrustedCertInfoEntity();
        trustedCertInfoEntity.setName("Name");
        trustedCertInfoEntity.setKeyUsageBits("KeyUsage");
        trustedCertInfoEntity.setModifiedBy("Modified");
        trustedCertInfoEntity.setCertIssuerRdn("CertIssuer");
        return trustedCertInfoEntity;
    }

    SciTrpUserCertEntity getSciTrpUserCert() {
        SciTrpUserCertEntity sciTrpUserCertEntity = new SciTrpUserCertEntity();
        sciTrpUserCertEntity.setUserCertId("123456");
        sciTrpUserCertEntity.setCertStatus(0);
        sciTrpUserCertEntity.setCertOrder(1);
        sciTrpUserCertEntity.setLockid(0);
        return sciTrpUserCertEntity;
    }

    ;

    List<SciTrpUserCertEntity> getSciTrpUserCerts() {
        List<SciTrpUserCertEntity> sciTrpUserCertEntities = new ArrayList<>();
        sciTrpUserCertEntities.add(getSciTrpUserCert());
        return sciTrpUserCertEntities;
    }


}
