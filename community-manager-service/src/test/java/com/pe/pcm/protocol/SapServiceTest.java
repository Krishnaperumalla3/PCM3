package com.pe.pcm.protocol;

import com.pe.pcm.protocol.sap.SapRepository;
import com.pe.pcm.protocol.sap.entity.SapEntity;
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
public class SapServiceTest {
    @MockBean
    private SapRepository sapRepository;
    @InjectMocks
    private SapService sapService;

    @Test
    @DisplayName("Save Sap Service")
    public void testSave() {
        sapService.save(geSapEntity());
        Mockito.verify(sapRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Get Sap Service")
    public void testGet() {
        Mockito.when(sapRepository.findBySubscriberId(Mockito.anyString())).thenReturn(Optional.of(geSapEntity()));
        sapService.get("pkId");
        Mockito.verify(sapRepository, Mockito.times(1)).findBySubscriberId(Mockito.anyString());
    }

    @Test
    @DisplayName("Get Sap Service1")
    public void testGet1() {
        Mockito.when(sapRepository.findBySubscriberId(Mockito.anyString())).thenReturn(Optional.of(geSapEntity()));
        sapService.get("pkId");
        Mockito.verify(sapRepository, Mockito.times(1)).findBySubscriberId(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete Sap Service")
    public void testDelete() {
        Mockito.when(sapRepository.findBySubscriberId(Mockito.anyString())).thenReturn(Optional.of(geSapEntity()));
        sapService.delete("pkId");
        Mockito.verify(sapRepository, Mockito.times(1)).findBySubscriberId(Mockito.anyString());
        Mockito.verify(sapRepository, Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    @DisplayName("Save Protocol Sap Service")
    public void testSaveProtocol() {
        Mockito.when(sapRepository.findBySubscriberId(Mockito.anyString())).thenReturn(Optional.of(geSapEntity()));
        sapService.saveProtocol(getSapModel("SAP"), "1", "2", "SUB");
        Mockito.verify(sapRepository, Mockito.never()).findBySubscriberId(Mockito.anyString());
        Mockito.verify(sapRepository, Mockito.times(1)).save(Mockito.any());
    }

    SapModel getSapModel(String protocol) {
        SapModel sapModel = new SapModel();
        sapModel.setPkId("123456");
        sapModel.setProfileName("ProfileName");
        sapModel.setProfileId("ProfileId");
        sapModel.setProtocol(protocol);
        sapModel.setEmailId("Email@email.com");
        sapModel.setPhone("468736886");
        sapModel.setStatus(false);
        sapModel.setAdapterName("AdapterName");
        sapModel.setSapRoute("Route");
        sapModel.setHubInfo(false);
        return sapModel;
    }

    SapEntity geSapEntity() {
        SapEntity sapEntity = new SapEntity();
        sapEntity.setPkId("123456");
        sapEntity.setIsActive("Active");
        sapEntity.setSapRoute("Route");
        sapEntity.setIsHubInfo("HubInfo");
        sapEntity.setSubscriberId("SubscriberId");
        sapEntity.setSapAdapterName("SapAdapterName");
        sapEntity.setSubscriberType("SubscriberType");
        sapEntity.setCreatedBy("CreatedBy");
        return sapEntity;
    }

}
