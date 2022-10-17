package com.pe.pcm.certificate;

import com.pe.pcm.certificate.entity.TrustedCertInfoEntity;
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

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class TrustedCertInfoServiceTest {
    @MockBean
    private TrustedCertInfoRepository trustedCertInfoRepository;
    @InjectMocks
    private TrustedCertInfoService trustedCertInfoService;

    @Test
    @DisplayName("Get TrustedCet Info List")
    void testGetTrustedCetInfoList() {
        Mockito.when(trustedCertInfoRepository.findAllByOrderByNameAsc()).thenReturn(Optional.of(getTrustedCertInfoEntities()));
        trustedCertInfoService.getTrustedCetInfoList();
        Mockito.verify(trustedCertInfoRepository, Mockito.times(1)).findAllByOrderByNameAsc();
    }

    @Test
    @DisplayName("Find By Id")
    void testGetFindById() {
        Mockito.when(trustedCertInfoRepository.findById(Mockito.anyString())).thenReturn(Optional.of(getTrustedCertInfoEntity()));
        trustedCertInfoService.findById("CertId");
        Mockito.verify(trustedCertInfoRepository, Mockito.times(1)).findById(Mockito.anyString());
    }

    @Test
    @DisplayName("Find By Name")
    void testFindByName() {
        Mockito.when(trustedCertInfoRepository.findFirstByName(Mockito.anyString())).thenReturn(Optional.of(getTrustedCertInfoEntity()));
        trustedCertInfoService.findByName("Name");
        Mockito.verify(trustedCertInfoRepository, Mockito.times(1)).findFirstByName(Mockito.anyString());
    }

    TrustedCertInfoEntity getTrustedCertInfoEntity() {
        TrustedCertInfoEntity trustedCertInfoEntity = new TrustedCertInfoEntity();
        trustedCertInfoEntity.setObjectId("CertId");
        trustedCertInfoEntity.setName("Name");
        trustedCertInfoEntity.setModifiedBy("Modified");
        return trustedCertInfoEntity;
    }

    List<TrustedCertInfoEntity> getTrustedCertInfoEntities() {
        List<TrustedCertInfoEntity> trustedCertInfoEntities = new ArrayList<>();
        trustedCertInfoEntities.add(getTrustedCertInfoEntity());
        return trustedCertInfoEntities;
    }

}
