package com.pe.pcm.protocol.as2;

import com.pe.pcm.protocol.As2Model;
import com.pe.pcm.protocol.as2.si.SciDocExchangeRepository;
import com.pe.pcm.protocol.as2.si.entity.SciDocExchangeEntity;
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


@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class DoExchangeServiceTest {
    @MockBean
    private SciDocExchangeRepository sciDocExchangeRepository;
    @InjectMocks
    private DocExchangeService docExchangeService;


    @Test
    @DisplayName("Save Do Exchange Service Test")
    public void testSave() {
        docExchangeService.save("transportObjectId", generateAs2Model());
        Mockito.verify(sciDocExchangeRepository, Mockito.times(1)).save(Mockito.any());
    }

    //@Test
    @DisplayName("Update Do Exchange Service Test")
    public void testUpdate() {
        Mockito.when(sciDocExchangeRepository.findByEntityId(Mockito.anyString())).thenReturn(Optional.of(getSciDocExchanges()));
        docExchangeService.update("123456", generateAs2Model());
        Mockito.verify(sciDocExchangeRepository, Mockito.times(1)).findByEntityId(Mockito.anyString());
        Mockito.verify(sciDocExchangeRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Get Do Exchange Service Test")
    public void testGet() {
        Mockito.when(sciDocExchangeRepository.findByObjectName(Mockito.anyString())).thenReturn(Optional.of(getSciDocExchanges()));
        docExchangeService.get(false, "transferObjectId");
        Mockito.verify(sciDocExchangeRepository, Mockito.times(1)).findByObjectName(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete Do Exchange Service Test")
    public void testDelete() {
        Mockito.when(sciDocExchangeRepository.findByObjectName(Mockito.anyString())).thenReturn(Optional.of(getSciDocExchanges()));
        docExchangeService.delete(false, "Identifier");
        Mockito.verify(sciDocExchangeRepository, Mockito.never()).findByObjectName(Mockito.anyString());
        Mockito.verify(sciDocExchangeRepository, Mockito.never()).delete(Mockito.any());
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

    SciDocExchangeEntity getSciDocExchange() {
        SciDocExchangeEntity sciDocExchangeEntity = new SciDocExchangeEntity();
        sciDocExchangeEntity.setObjectId("654321");
        sciDocExchangeEntity.setObjectName("objectname");
        sciDocExchangeEntity.setEntityId("123456");
        sciDocExchangeEntity.setEnvelopeProtocol("Envelope");
        sciDocExchangeEntity.setDocExchangeKey("Document");
        sciDocExchangeEntity.setIdempotency("Idempotency");
        return sciDocExchangeEntity;
    }

    List<SciDocExchangeEntity> getSciDocExchanges() {
        List<SciDocExchangeEntity> sciDocExchangeEntities = new ArrayList<>();
        sciDocExchangeEntities.add(getSciDocExchange());
        return sciDocExchangeEntities;
    }
}
