package com.pe.pcm.application;

import com.pe.pcm.application.entity.RemoteApplicationStagingEntity;
import com.pe.pcm.exception.CommunityManagerServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class RemoteApplicationStagingServiceTest {
    @MockBean
    private RemoteApplicationStagingRepository remoteApplicationStagingRepository;

    @InjectMocks
    private RemoteApplicationStagingService remoteApplicationStagingService;


    @Test
    @DisplayName("Find All Remote Application Profiles")
    public void testFindAllRemoteApplicationProfiles() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(remoteApplicationStagingRepository.findAllByOrderByApplicationNameAsc()).thenThrow(notFound("Application"));
            remoteApplicationStagingService.findAllRemoteApplicationProfiles();
        });
        Mockito.verify(remoteApplicationStagingRepository, Mockito.times(1)).findAllByOrderByApplicationNameAsc();
    }

    @Test
    @DisplayName("Delete Remote Application Service ")
    public void testDelete() {
        Mockito.when(remoteApplicationStagingRepository.findById(Mockito.anyString())).thenReturn(Optional.ofNullable(getRemoteApplicationStagingEntity()));
        final String pkId = "123456";
        remoteApplicationStagingService.delete(getRemoteApplicationStagingEntity());
        Mockito.verify(remoteApplicationStagingRepository, Mockito.times(1)).delete(Mockito.any());
    }

    RemoteApplicationStagingEntity getRemoteApplicationStagingEntity() {
        RemoteApplicationStagingEntity remoteApplicationStagingEntity = new RemoteApplicationStagingEntity();
        remoteApplicationStagingEntity.setAppIntegrationProtocol("protocol");
        remoteApplicationStagingEntity.setPkId("123456");
        remoteApplicationStagingEntity.setEmailId("Email@email.com");
        remoteApplicationStagingEntity.setPhone("9785435243");
        remoteApplicationStagingEntity.setAppIsActive("yes");
        return remoteApplicationStagingEntity;
    }
}
