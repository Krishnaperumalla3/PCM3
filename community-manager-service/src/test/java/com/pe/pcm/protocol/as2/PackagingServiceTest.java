package com.pe.pcm.protocol.as2;


import com.pe.pcm.protocol.As2Model;
import com.pe.pcm.protocol.as2.si.SciPackagingRepository;
import com.pe.pcm.protocol.as2.si.entity.SciPackagingEntity;
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
public class PackagingServiceTest {
    @MockBean
    private SciPackagingRepository sciPackagingRepository;
    @InjectMocks
    private PackagingService packagingService;


    @Test
    @DisplayName("Save Packaging Service")
    public void testSave() {
        packagingService.save("transportObjectId", generateAs2Model());
        Mockito.verify(sciPackagingRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Update Packaging Service")
    public void testUpdate() {
        Mockito.when(sciPackagingRepository.findFirstByObjectId(Mockito.anyString())).thenReturn(Optional.of(getSciPackaging()));
        packagingService.update(Mockito.anyString(), generateAs2Model());
        Mockito.verify(sciPackagingRepository, Mockito.times(1)).findFirstByObjectId(Mockito.anyString());
        Mockito.verify(sciPackagingRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Update Packaging Service1")
    public void testUpdate1() {
        Mockito.when(sciPackagingRepository.findFirstByObjectId(Mockito.anyString())).thenReturn(Optional.ofNullable(null));
        packagingService.update(Mockito.anyString(), generateAs2Model());
        Mockito.verify(sciPackagingRepository, Mockito.times(1)).findFirstByObjectId(Mockito.anyString());
        Mockito.verify(sciPackagingRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    @DisplayName("Get Packaging Service")
    public void testGet() {
        Mockito.when(sciPackagingRepository.findByObjectName(Mockito.anyString())).thenReturn(Optional.of(getSciPackagings()));
        packagingService.get(false, "transferObjectId");
        Mockito.verify(sciPackagingRepository, Mockito.times(1)).findByObjectName(Mockito.anyString());
    }

    // @Test
    @DisplayName("Get Packaging Service1")
    public void testGet1() {
        Mockito.when(sciPackagingRepository.findByObjectName(Mockito.anyString())).thenReturn(Optional.ofNullable(null));
        packagingService.get(true, "tranferObjectId");
        Mockito.verify(sciPackagingRepository, Mockito.times(1)).findByObjectName(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete Packaging Service")
    public void testDelete() {
        Mockito.when(sciPackagingRepository.findByObjectName(Mockito.anyString())).thenReturn(Optional.of(getSciPackagings()));
        packagingService.delete(false, "AS2");
        Mockito.verify(sciPackagingRepository, Mockito.never()).findByObjectName(Mockito.anyString());
        Mockito.verify(sciPackagingRepository, Mockito.never()).delete(Mockito.any());
    }

    @Test
    @DisplayName("Delete Packaging Service1")
    public void testDelete1() {
        Mockito.when(sciPackagingRepository.findByObjectName(Mockito.anyString())).thenReturn(Optional.ofNullable(null));
        packagingService.delete(true, "as2");
        Mockito.verify(sciPackagingRepository, Mockito.never()).findByObjectName(Mockito.anyString());
        Mockito.verify(sciPackagingRepository, Mockito.never()).delete(Mockito.any());
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


    SciPackagingEntity getSciPackaging() {
        SciPackagingEntity sciPackagingEntity = new SciPackagingEntity();
        sciPackagingEntity.setEntityId("123456");
        sciPackagingEntity.setCompressData("Compressdata");
        sciPackagingEntity.setLastModifier("LastModifier");
        return sciPackagingEntity;
    }

    List<SciPackagingEntity> getSciPackagings() {
        List<SciPackagingEntity> sciPackagingEntities = new ArrayList<>();
        sciPackagingEntities.add(getSciPackaging());
        return sciPackagingEntities;
    }
}
