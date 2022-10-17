package com.pe.pcm.certificate;


import com.pe.pcm.certificate.entity.CaCertInfoEntity;
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
class CaCertInfoServiceTest {
    @Mock
    private CaCertInfoRepository caCertInfoRepository;
    //@InjectMocks
    private CaCertInfoService caCertInfoService;

    @BeforeEach
    void inIt() {
        caCertInfoService = new CaCertInfoService(caCertInfoRepository);
    }

    @Test
    @DisplayName("Get CaCert Info By Name")
    void testGetCaCertInfoByName() {
        Mockito.when(caCertInfoRepository.findFirstByName(Mockito.anyString())).thenReturn(Optional.of(getCaCertInfoEntity()));
        caCertInfoService.getCaCertInfoByName("Name");
        Mockito.verify(caCertInfoRepository, Mockito.times(1)).findFirstByName(Mockito.anyString());
    }

    @Test
    @DisplayName("Get CaCert By Id")
    void testGetCaCertById() {
        Mockito.when(caCertInfoRepository.findById(Mockito.anyString())).thenReturn(Optional.of(getCaCertInfoEntity()));
        caCertInfoService.getCaCertById("12345");
        Mockito.verify(caCertInfoRepository, Mockito.times(1)).findById(Mockito.anyString());
    }

    @Test
    @DisplayName("Get CaCert Info List")
    void testGetCaCertInfoList() {
        Mockito.when(caCertInfoRepository.findAllByOrderByNameAsc()).thenReturn(Optional.of(getCaCertInfoEntities()));
        caCertInfoService.getCaCertInfoList();
        Mockito.verify(caCertInfoRepository, Mockito.times(1)).findAllByOrderByNameAsc();
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
}
