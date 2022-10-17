package com.pe.pcm.protocol.as2;


import com.pe.pcm.certificate.entity.CaCertInfoEntity;
import com.pe.pcm.protocol.as2.certificate.SciTranspCaCertService;
import com.pe.pcm.protocol.as2.si.certificate.SciTranspCaCertRepository;
import com.pe.pcm.protocol.as2.si.certificate.entity.SciTranspCaCertEntity;
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
public class SciTranspCaCertServiceTest {
    @MockBean
    private SciTranspCaCertRepository sciTranspCaCertRepository;
    //@InjectMocks
    private SciTranspCaCertService sciTranspCaCertService;

    @BeforeEach
    void inIt() {
        sciTranspCaCertService = new SciTranspCaCertService(sciTranspCaCertRepository);
    }

    @Test
    @DisplayName("Save Transp CaCert Service")
    public void testSave() {
        sciTranspCaCertService.save("transportObjectId", getCaCertInfoEntity());
        Mockito.verify(sciTranspCaCertRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Update Transp CaCert Service")
    public void testUpdate() {
        Mockito.when(sciTranspCaCertRepository.findByTransportId(Mockito.anyString())).thenReturn(Optional.of(getSciTranspCaCerts()));
        sciTranspCaCertService.update("transportObjectId", getCaCertInfoEntity());
        Mockito.verify(sciTranspCaCertRepository, Mockito.times(1)).findByTransportId(Mockito.anyString());
        Mockito.verify(sciTranspCaCertRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Get Transp CaCert Service")
    public void testGet() {
        Mockito.when(sciTranspCaCertRepository.findByTransportId(Mockito.anyString())).thenReturn(Optional.of(getSciTranspCaCerts()));
        String ObjectId = "123456";
        sciTranspCaCertService.get(ObjectId);
        Mockito.verify(sciTranspCaCertRepository, Mockito.times(1)).findByTransportId(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete Transp CaCert Service")
    public void testDelete() {
        Mockito.when(sciTranspCaCertRepository.findByTransportId(Mockito.anyString())).thenReturn(Optional.of(getSciTranspCaCerts()));
        String ObjectId = "123456";
        sciTranspCaCertService.delete(ObjectId);
        Mockito.verify(sciTranspCaCertRepository, Mockito.times(1)).findByTransportId(Mockito.anyString());
        Mockito.verify(sciTranspCaCertRepository, Mockito.times(1)).delete(Mockito.any());
    }

    CaCertInfoEntity getCaCertInfoEntity() {
        CaCertInfoEntity caCertInfoEntity = new CaCertInfoEntity();
        caCertInfoEntity.setKeyUsageBits("KeyUsageBits");
        caCertInfoEntity.setModifiedBy("Modified");
        caCertInfoEntity.setName("Name");
        return caCertInfoEntity;
    }

    SciTranspCaCertEntity getsciTranspCaCert() {
        SciTranspCaCertEntity sciTranspCaCertEntity = new SciTranspCaCertEntity();
        sciTranspCaCertEntity.setCaCertId("12345");
        sciTranspCaCertEntity.setObjectState("Object");
        sciTranspCaCertEntity.setTransportId("TransportId");
        sciTranspCaCertEntity.setCertOrder(1234);
        return sciTranspCaCertEntity;
    }

    List<SciTranspCaCertEntity> getSciTranspCaCerts() {
        List<SciTranspCaCertEntity> sciTranspCaCertEntities = new ArrayList<>();
        sciTranspCaCertEntities.add(getsciTranspCaCert());
        return sciTranspCaCertEntities;
    }
}
