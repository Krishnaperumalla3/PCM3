package com.pe.pcm.partner.staging;

import com.pe.pcm.partner.RemotePartnerStagingRepository;
import com.pe.pcm.partner.entity.RemotePartnerStagingEntity;
import com.pe.pcm.partner.staging.RemotePartnerStagingService;
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
public class RemotePartnerStagingServiceTest {

    @Mock
    private RemotePartnerStagingRepository remotePartnerStagingRepository;

    //@InjectMocks
    private RemotePartnerStagingService remotePartnerStagingService;

    @BeforeEach
    void inIt() {
        remotePartnerStagingService = new RemotePartnerStagingService(remotePartnerStagingRepository);
    }

    @Test
    @DisplayName("Find All Remote Partner Profiles")
    public void testFindAllRemotePartnerProfiles() {
        Mockito.when(remotePartnerStagingRepository.findAllByOrderByTpNameAsc()).thenReturn(Optional.of(generateRemotePartnerStagingEntity()));
        remotePartnerStagingService.findAllRemotePartnerProfiles();
        Mockito.verify(remotePartnerStagingRepository, Mockito.times(1)).findAllByOrderByTpNameAsc();
    }

    @Test
    @DisplayName("Delete Remote Partner Staging Entity")
    public void testDelete() {
        remotePartnerStagingService.delete(Mockito.any());
        Mockito.verify(remotePartnerStagingRepository, Mockito.times(1)).delete(Mockito.any());
    }


    RemotePartnerStagingEntity generateRemotePartnerStagingEntity1() {
        RemotePartnerStagingEntity remotePartnerStagingEntity = new RemotePartnerStagingEntity();
        remotePartnerStagingEntity.setTpId("123456");
        remotePartnerStagingEntity.setPkId("123456");
        remotePartnerStagingEntity.setTpProtocol("SFGFTP");
        return remotePartnerStagingEntity;
    }

    List<RemotePartnerStagingEntity> generateRemotePartnerStagingEntity() {
        List<RemotePartnerStagingEntity> list = new ArrayList<>();
        list.add(generateRemotePartnerStagingEntity1());
        list.add(generateRemotePartnerStagingEntity1());
        return list;
    }
}
