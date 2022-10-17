package com.pe.pcm.certificate;


import com.pe.pcm.certificate.entity.CertsAndPriKeyEntity;
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

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class CertsAndPriKeyServiceTest {
    @Mock
    private CertsAndPriKeyRepository certsAndPriKeyRepository;
    //@InjectMocks
    private CertsAndPriKeyService certsAndPriKeyService;

    @BeforeEach
    void inIt() {
        certsAndPriKeyService = new CertsAndPriKeyService(certsAndPriKeyRepository);
    }

    @Test
    @DisplayName("Get Certs And PriKey List")
    void testGetCertsAndPriKeyList() {
        Mockito.when(certsAndPriKeyRepository.findAllByOrderByNameAsc()).thenReturn(Optional.of(getCertsAndPriKeyEntities()));
        certsAndPriKeyService.getCertsAndPriKeyList();
        Mockito.verify(certsAndPriKeyRepository, Mockito.times(1)).findAllByOrderByNameAsc();
    }

    @Test
    @DisplayName("Find By Id")
    void testFindById(){
        Mockito.when(certsAndPriKeyRepository.findById(Mockito.anyString())).thenReturn(Optional.of(getCertsAndPriKeyEntity()));
        certsAndPriKeyService.findById("123456");
        Mockito.verify(certsAndPriKeyRepository,Mockito.times(1)).findById(Mockito.anyString());
    }

    @Test
    @DisplayName("Find First By Name")
    void testFindFirstByName(){
        Mockito.when(certsAndPriKeyRepository.findFirstByName(Mockito.anyString())).thenReturn(Optional.of(getCertsAndPriKeyEntity()));
        certsAndPriKeyService.findByName("Name");
        Mockito.verify(certsAndPriKeyRepository,Mockito.times(1)).findFirstByName(Mockito.anyString());
    }

    CertsAndPriKeyEntity getCertsAndPriKeyEntity() {
        CertsAndPriKeyEntity certsAndPriKeyEntity = new CertsAndPriKeyEntity();
        certsAndPriKeyEntity.setName("Name");
        certsAndPriKeyEntity.setObjectId("123456");
        certsAndPriKeyEntity.setUsername("UserName");
        return certsAndPriKeyEntity;
    }

    List<CertsAndPriKeyEntity> getCertsAndPriKeyEntities() {
        List<CertsAndPriKeyEntity> certsAndPriKeyEntities = new ArrayList<>();
        certsAndPriKeyEntities.add(getCertsAndPriKeyEntity());
        return certsAndPriKeyEntities;
    }
}
