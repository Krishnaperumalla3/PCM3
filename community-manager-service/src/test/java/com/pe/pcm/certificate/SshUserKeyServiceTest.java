package com.pe.pcm.certificate;


import com.pe.pcm.certificate.entity.SshUserKeyEntity;
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
class SshUserKeyServiceTest {

    @Mock
    private SshUserKeyRepository sshUserKeyRepository;
    //@InjectMocks
    private SshUserKeyService sshUserKeyService;

    @BeforeEach
    void inIt() {
        sshUserKeyService = new SshUserKeyService(sshUserKeyRepository);
    }

    @Test
    @DisplayName("Find All By Name")
    void getFindAllByName() {
        Mockito.when(sshUserKeyRepository.findAllByNameContainingIgnoreCaseOrderByName(Mockito.anyString())).thenReturn(Optional.of(getSshUserKeyEntities()));
        sshUserKeyService.findAllByName("Name", true);
        Mockito.verify(sshUserKeyRepository, Mockito.times(1)).findAllByNameContainingIgnoreCaseOrderByName(Mockito.anyString());
    }

//    @Test
//    @DisplayName("Find All")
//    void getFindAll() {
//        Mockito.when(sshUserKeyRepository.findAllByName(Mockito.anyString())).thenReturn(Optional.of(getSshUserKeyEntities()));
//        sshUserKeyService.findAll("Name");
//        Mockito.verify(sshUserKeyRepository, Mockito.times(1)).findAllByName(Mockito.anyString());
//    }

    @Test
    @DisplayName("Find All By Name Asc")
    void getFindAllByNameAsc() {
        Mockito.when(sshUserKeyRepository.findAllByOrderByNameAsc()).thenReturn(Optional.of(getSshUserKeyEntities()));
        sshUserKeyService.findAllByNameAsc();
        Mockito.verify(sshUserKeyRepository, Mockito.times(1)).findAllByOrderByNameAsc();
    }


    SshUserKeyEntity getSshUserKeyEntity() {
        SshUserKeyEntity sshUserKeyEntity = new SshUserKeyEntity();
        sshUserKeyEntity.setName("Name");
        sshUserKeyEntity.setObjectId("ObjectId");
        return sshUserKeyEntity;
    }

    List<SshUserKeyEntity> getSshUserKeyEntities() {
        List<SshUserKeyEntity> sshUserKeyEntities = new ArrayList<>();
        sshUserKeyEntities.add(getSshUserKeyEntity());
        return sshUserKeyEntities;
    }
}
