package com.pe.pcm.protocol;


import com.pe.pcm.protocol.ec.EcRepository;
import com.pe.pcm.protocol.ec.entity.EcEntity;
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

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class EcServiceTest {
    @MockBean
    private EcRepository ecRepository;
    @InjectMocks
    private EcService ecService;

    @Test
    @DisplayName("Save Protocol EcService")
    public void testSaveProtocol() {
        Mockito.when(ecRepository.findBySubscriberId(Mockito.anyString())).thenReturn(Optional.of(getEcEntity()));
        ecService.saveProtocol(generateEcModel("FTP"), "1", "2", "sub");
        Mockito.verify(ecRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Save EcService")
    public void testSave() {
        Mockito.when(ecRepository.findBySubscriberId(Mockito.anyString())).thenReturn(Optional.of(getEcEntity()));
        ecService.save(getEcEntity());
        Mockito.verify(ecRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Get EcService")
    public void testGet() {
        Mockito.when(ecRepository.findBySubscriberId(Mockito.anyString())).thenReturn(Optional.of(getEcEntity()));
        String pkId = "123456";
        ecService.get(pkId);
        Mockito.verify(ecRepository, Mockito.times(1)).findBySubscriberId(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete EcService")
    public void testDelete() {
        Mockito.when(ecRepository.findBySubscriberId(Mockito.anyString())).thenReturn(Optional.of(getEcEntity()));
        String pkId = "123456";
        ecService.delete(pkId);
        Mockito.verify(ecRepository, Mockito.times(1)).findBySubscriberId(Mockito.anyString());
        Mockito.verify(ecRepository, Mockito.times(1)).delete(Mockito.any());
    }


    EcEntity getEcEntity() {
        EcEntity ecEntity = new EcEntity();
        ecEntity.setPkId("123456");
        ecEntity.setIsHubInfo("F");
        ecEntity.setEcProtocol("Protocol");
        ecEntity.setSubscriberId("SubscriberId");
        ecEntity.setIsActive("Active");
        return ecEntity;
    }

    EcModel generateEcModel(String protocol) {
        EcModel ecModel = new EcModel();
        ecModel.setPkId("123456");
        ecModel.setProtocol(protocol);
        ecModel.setEmailId("Email@e.com");
        ecModel.setHubInfo(false);
        ecModel.setStatus(false);
        return ecModel;
    }
}
