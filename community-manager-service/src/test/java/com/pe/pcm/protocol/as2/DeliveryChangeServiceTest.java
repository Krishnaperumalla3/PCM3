package com.pe.pcm.protocol.as2;


import com.pe.pcm.protocol.As2Model;
import com.pe.pcm.protocol.as2.si.SciDeliveryChangeRepository;
import com.pe.pcm.protocol.as2.si.entity.SciDelivChanEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
public class DeliveryChangeServiceTest {

    @MockBean
    private SciDeliveryChangeRepository sciDeliveryChangeRepository;

    private DeliveryChangeService deliveryChangeService;

    @BeforeEach
    public void inIt() {
        deliveryChangeService = new DeliveryChangeService(sciDeliveryChangeRepository);
    }


    @Test
    @DisplayName("Save Delivery Change Service")
    public void testSave() {
        deliveryChangeService.save("transferObjectId", generateAs2Model());
        Mockito.verify(sciDeliveryChangeRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Update Delivery Change Service")
    public void testUpdate() {
        Mockito.when(sciDeliveryChangeRepository.findByTransportId(Mockito.anyString())).thenReturn(Optional.of(getSCIDelivChan()));
        deliveryChangeService.update("trnsId1", generateAs2Model());
        Mockito.verify(sciDeliveryChangeRepository, Mockito.times(1)).findByTransportId(Mockito.anyString());
        Mockito.verify(sciDeliveryChangeRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Update Delivery Change Service1")
    public void testUpdate1() {
        Mockito.when(sciDeliveryChangeRepository.findByTransportId(Mockito.anyString())).thenReturn(Optional.of(getSCIDelivChan()));
        deliveryChangeService.update("trnsId", generateAs2Model());
        Mockito.verify(sciDeliveryChangeRepository, Mockito.times(1)).findByTransportId(Mockito.anyString());
        Mockito.verify(sciDeliveryChangeRepository, Mockito.times(1)).save(Mockito.any());
    }


    @Test
    @DisplayName("Get Delivery Change Service")
    public void testGet() {
        Mockito.when(sciDeliveryChangeRepository.findByObjectName(Mockito.anyString())).thenReturn(Optional.of(getSCIDelivChan()));
        deliveryChangeService.get(true, "Object");
        Mockito.verify(sciDeliveryChangeRepository, Mockito.times(1)).findByObjectName(Mockito.anyString());
    }

    @Test
    @DisplayName("Get Delivery Change Service1")
    public void testGet1() {
        Mockito.when(sciDeliveryChangeRepository.findByObjectName(Mockito.anyString())).thenReturn(Optional.of(getSCIDelivChan()));
        deliveryChangeService.get(false, "Object1");
        Mockito.verify(sciDeliveryChangeRepository, Mockito.times(1)).findByObjectName(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete Delivery Change Service")
    public void testDelete() {
        Mockito.when(sciDeliveryChangeRepository.findByObjectName(Mockito.anyString())).thenReturn(Optional.of(getSCIDelivChan()));
        deliveryChangeService.delete(false, "Object1");
        Mockito.verify(sciDeliveryChangeRepository, Mockito.never()).findByObjectName(Mockito.anyString());
    }

    As2Model generateAs2Model() {
        As2Model as2Model = new As2Model();
        as2Model.setPassword("password");
        as2Model.setProtocol("protocol");
        as2Model.setPkId("123456");
        as2Model.setEmailId("Email@email.com");
        as2Model.setUsername("UserName");
        as2Model.setPhone("9143474564");
        as2Model.setProfileId("123456");
        as2Model.setProfileName("ProfileName");
        as2Model.setStatus(false);
        as2Model.setHubInfo(false);
        as2Model.setMdn(false);
        as2Model.setCompressData("default");
        return as2Model;
    }

    SciDelivChanEntity getSciDelivChan() {
        SciDelivChanEntity sciDelivChanEntity = new SciDelivChanEntity();
        sciDelivChanEntity.setTransportId("trnsId");
        sciDelivChanEntity.setAlwaysVerify("AlwaysVerify");
        sciDelivChanEntity.setAuthenticated("Authenticated");
        sciDelivChanEntity.setConfidentiality("Confidentiality");
        sciDelivChanEntity.setObjectName("Object");
        return sciDelivChanEntity;
    }


    SciDelivChanEntity getSciDelivChan11() {
        SciDelivChanEntity sciDelivChanEntity = new SciDelivChanEntity();
        sciDelivChanEntity.setAlwaysVerify("AlwaysVerify");
        sciDelivChanEntity.setTransportId("trnsId1");
        sciDelivChanEntity.setAuthenticated("Authenticated");
        sciDelivChanEntity.setConfidentiality("Confidentiality");
        sciDelivChanEntity.setObjectName("Object");
        return sciDelivChanEntity;
    }


    List<SciDelivChanEntity> getSCIDelivChan() {
        List<SciDelivChanEntity> sciDelivChanEntities = new ArrayList<>();
        sciDelivChanEntities.add(getSciDelivChan11());
        sciDelivChanEntities.add(getSciDelivChan());
        return sciDelivChanEntities;
    }

}





