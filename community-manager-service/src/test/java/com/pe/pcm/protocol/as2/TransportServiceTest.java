package com.pe.pcm.protocol.as2;


import com.pe.pcm.protocol.As2Model;
import com.pe.pcm.protocol.as2.si.SciTransportRepository;
import com.pe.pcm.protocol.as2.si.entity.SciTransportEntity;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class TransportServiceTest {
    @MockBean
    private SciTransportRepository sciTransportRepository;
    //@InjectMocks
    private TransportService transportService;

    @BeforeEach
    void inIt() {
        transportService  = new TransportService(sciTransportRepository);
    }

    @Test
    @DisplayName("Save Transport Service")
    public void testSave() {
        transportService.save("transportObjectId", generateAs2Model());
        Mockito.verify(sciTransportRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Update Transport Service")
    public void testUpdate() {
        transportService.update(getSciTransport(), generateAs2Model());
        Mockito.verify(sciTransportRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Get Transport Service")
    public void testGet() {
        Mockito.when(sciTransportRepository.findByObjectName(Mockito.anyString())).thenReturn(Optional.of(getSciTransports()));
        transportService.get("transferObject");
        Mockito.verify(sciTransportRepository, Mockito.times(1)).findByObjectName(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete Transport Service")
    public void testDelete() {
        Mockito.when(sciTransportRepository.findByObjectName(Mockito.anyString())).thenReturn(Optional.of(getSciTransports()));
        transportService.delete("transferObject");
        Mockito.verify(sciTransportRepository, Mockito.times(1)).findByObjectName(Mockito.anyString());
        Mockito.verify(sciTransportRepository, Mockito.times(1)).delete(Mockito.any());
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

    SciTransportEntity getSciTransport() {
        SciTransportEntity sciTransportEntity = new SciTransportEntity();
        sciTransportEntity.setBcc("Bcc");
        sciTransportEntity.setCc("Cc");
        sciTransportEntity.setBccInh(1234);
        sciTransportEntity.setDirectory("Directory");
        sciTransportEntity.setCipherStgthInh(123456);
        return sciTransportEntity;
    }

    List<SciTransportEntity> getSciTransports() {
        List<SciTransportEntity> sciProfiles = new ArrayList<>();
        sciProfiles.add(getSciTransport());
        return sciProfiles;
    }

}
