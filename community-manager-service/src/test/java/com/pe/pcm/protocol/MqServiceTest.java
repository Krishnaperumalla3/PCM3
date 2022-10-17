package com.pe.pcm.protocol;


import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.protocol.mq.MqRepository;
import com.pe.pcm.protocol.mq.entity.MqEntity;
import org.junit.jupiter.api.Assertions;
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

import java.util.Optional;

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class MqServiceTest {
    @MockBean
    private MqRepository mqRepository;
    //@InjectMocks
    private MqService mqService;

    @BeforeEach
    void inIt() {
        mqService = new MqService(mqRepository);
    }

    @Test
    @DisplayName("Save Mq Service")
    public void testSave() {
        mqService.save(getMqEntity());
        Mockito.verify(mqRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Get Mq Service")
    public void testGet() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(mqService.get(Mockito.anyString())).thenThrow(notFound("Application"));
            String pkId = "123456";
            mqService.get(pkId);
        });
        Mockito.verify(mqRepository, Mockito.times(1)).findBySubscriberId(Mockito.anyString());
    }

    @Test
    @DisplayName("Get Mq Service1")
    public void testGet1() {
        Mockito.when(mqRepository.findBySubscriberId(Mockito.anyString())).thenReturn(Optional.of(getMqEntity()));
        String pkId = "123456";
        mqService.get(pkId);
        Mockito.verify(mqRepository, Mockito.times(1)).findBySubscriberId(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete Mq Service1")
    public void testDelete() {
        Mockito.when(mqRepository.findBySubscriberId(Mockito.anyString())).thenReturn(Optional.of(getMqEntity()));
        String pkId = "123456";
        mqService.delete(pkId);
        Mockito.verify(mqRepository, Mockito.times(1)).findBySubscriberId(Mockito.anyString());
        Mockito.verify(mqRepository, Mockito.times(1)).delete(Mockito.any());
    }

    MqModel getMqModel(String protocol) {
        MqModel mqModel = new MqModel();
        mqModel.setPkId("123456");
        mqModel.setProfileName("ProfileName");
        mqModel.setProfileId("ProfileId");
        mqModel.setProtocol(protocol);
        mqModel.setEmailId("Email@email.com");
        mqModel.setPhone("5378787868");
        mqModel.setStatus(false);
        mqModel.setHostName("Hostname");
        mqModel.setFileType("FileType");
        mqModel.setQueueManager("queue");
        mqModel.setQueueName("queueName");
        mqModel.setAdapterName("Adapter");
        mqModel.setPoolingInterval("poolingInterval");
        mqModel.setHubInfo(false);
        return mqModel;
    }

    MqEntity getMqEntity() {
        MqEntity mqEntity = new MqEntity();
        mqEntity.setAdapterName("AdapterName");
        mqEntity.setFileType("FileType");
        mqEntity.setHostName("HostName");
        mqEntity.setIsActive("Active");
        mqEntity.setIsHubInfo("HubInfo");
        mqEntity.setPkId("1234456");
        mqEntity.setPoolingIntervalMins("poolingInterval");
        mqEntity.setQueueName("queueName");
        mqEntity.setQueueManager("QueueManager");
        return mqEntity;
    }
}
