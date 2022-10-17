package com.pe.pcm.certificate;


import com.pe.pcm.certificate.entity.SshKeyPairEntity;
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
class SshKeyPairServiceTest {
    @MockBean
    private SshKeyPairRepository sshKeyPairRepository;
    @InjectMocks
    private SshKeyPairService sshKeyPairService;

    @Test
    @DisplayName("Find All")
    void testFindAll() {
        Mockito.when(sshKeyPairRepository.findAllByOrderByNameAsc()).thenReturn(Optional.of(getSshKeyPairEntities()));
        sshKeyPairService.findAll();
        Mockito.verify(sshKeyPairRepository, Mockito.times(1)).findAllByOrderByNameAsc();
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
}
