package com.pe.pcm.certificate;

import com.pe.pcm.certificate.entity.AuthXrefSshEntity;
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
class AuthXrefSshServiceTest {

    @Mock
    private AuthXrefSshRepository authXrefSshRepository;
    //@InjectMocks
    private AuthXrefSshService authXrefSshService;

    @BeforeEach
    void inIt() {
        authXrefSshService = new AuthXrefSshService(authXrefSshRepository);
    }

    @Test
    @DisplayName("Find By User key")
    void testFindByUserKey() {
        Mockito.when(authXrefSshRepository.findAllByOrderByUserIdAsc()).thenReturn(Optional.of(getAuthXrefSshEntityList()));
        authXrefSshService.findByUserkey();
        Mockito.verify(authXrefSshRepository, Mockito.times(1)).findAllByOrderByUserIdAsc();
    }

    AuthXrefSshEntity getAuthXrefSshEntity() {
        AuthXrefSshEntity authXrefSshEntity = new AuthXrefSshEntity();
        authXrefSshEntity.setUserId("UserId");
        authXrefSshEntity.setUserkey("UserKey");
        return authXrefSshEntity;
    }

    AuthXrefSshEntity getAuthXrefSshEntity1() {
        AuthXrefSshEntity authXrefSshEntity = new AuthXrefSshEntity();
        authXrefSshEntity.setUserId("UserId1");
        authXrefSshEntity.setUserkey("UserKey1");
        return authXrefSshEntity;
    }

    AuthXrefSshEntity getAuthXrefSshEntity2() {
        AuthXrefSshEntity authXrefSshEntity = new AuthXrefSshEntity();
        authXrefSshEntity.setUserId("UserId2");
        authXrefSshEntity.setUserkey("UserKey2");
        return authXrefSshEntity;
    }

    List<AuthXrefSshEntity> getAuthXrefSshEntityList() {
        List<AuthXrefSshEntity> authXrefSshEntities = new ArrayList<>();
        authXrefSshEntities.add(getAuthXrefSshEntity());
        authXrefSshEntities.add(getAuthXrefSshEntity1());
        authXrefSshEntities.add(getAuthXrefSshEntity2());
        return authXrefSshEntities;
    }
}
