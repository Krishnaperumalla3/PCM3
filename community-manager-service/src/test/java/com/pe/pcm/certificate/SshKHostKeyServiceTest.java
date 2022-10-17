package com.pe.pcm.certificate;


import com.pe.pcm.certificate.entity.SshKHostKeyEntity;
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
class SshKHostKeyServiceTest {
    @Mock
    private SshKHostKeyRepository sshKHostKeyRepository;
    //@InjectMocks
    private SshKHostKeyService sshKHostKeyService;

    @BeforeEach
    void inIt() {
        sshKHostKeyService = new SshKHostKeyService(sshKHostKeyRepository);
    }

    @Test
    @DisplayName("Get SshK Host Key List")
    void testGetSshKHostKeyList() {
        Mockito.when(sshKHostKeyRepository.findAllByOrderByNameAsc()).thenReturn(Optional.of(getKHostKeyEntities()));
        sshKHostKeyService.getSshKHostKeyList();
        Mockito.verify(sshKHostKeyRepository, Mockito.times(1)).findAllByOrderByNameAsc();
    }

    @Test
    @DisplayName("Get SshK Host Key List")
    void testGetSshKHostKeyListByName() {
        Mockito.when(sshKHostKeyRepository.findAllByNameContainingIgnoreCaseOrderByNameAsc(Mockito.anyString())).thenReturn(Optional.of(getKHostKeyEntities()));
        sshKHostKeyService.getSshKHostKeyListByName("Name", true);
        Mockito.verify(sshKHostKeyRepository, Mockito.times(1)).findAllByNameContainingIgnoreCaseOrderByNameAsc(Mockito.anyString());
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

}
