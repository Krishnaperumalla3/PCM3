package com.pe.pcm.protocol.as2;


import com.pe.pcm.protocol.As2Model;
import com.pe.pcm.protocol.as2.si.SciProfileRepository;
import com.pe.pcm.protocol.as2.si.entity.SciProfileEntity;
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
public class ProfileServiceTest {
    @MockBean
    private SciProfileRepository sciProfileRepository;
    //@InjectMocks
    private ProfileService profileService;

    @BeforeEach
    void inIt() {
        profileService = new ProfileService(sciProfileRepository);
    }

    @Test
    @DisplayName("Save Profile")
    public void testSave() {
        profileService.save("transportObjectId", generateAs2Model());
        Mockito.verify(sciProfileRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Update Profile")
    public void testUpdate() {
        Mockito.when(sciProfileRepository.findByObjectName(Mockito.anyString())).thenReturn(Optional.of(getSciProfiles()));
        profileService.update(generateAs2Model());
        Mockito.verify(sciProfileRepository, Mockito.times(1)).findByObjectName(Mockito.anyString());
        Mockito.verify(sciProfileRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Update Profile1")
    public void testUpdate1() {
        Mockito.when(sciProfileRepository.findByObjectName(Mockito.anyString())).thenReturn(Optional.ofNullable(null));
        profileService.update(generateAs2Model());
        Mockito.verify(sciProfileRepository, Mockito.times(1)).findByObjectName(Mockito.anyString());
        Mockito.verify(sciProfileRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    @DisplayName("Get Profile")
    public void testGet() {
        Mockito.when(sciProfileRepository.findByObjectName(Mockito.anyString())).thenReturn(Optional.of(getSciProfiles()));
        String ObjectId = "123456";
        profileService.get(ObjectId);
        Mockito.verify(sciProfileRepository, Mockito.times(1)).findByObjectName(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete Profile")
    public void testDelete() {
        Mockito.when(sciProfileRepository.findByObjectName(Mockito.anyString())).thenReturn(Optional.of(getSciProfiles()));
        String ObjectId = "123456";
        profileService.delete(ObjectId);
        Mockito.verify(sciProfileRepository, Mockito.never()).findByObjectName(Mockito.anyString());
        Mockito.verify(sciProfileRepository, Mockito.never()).delete(Mockito.any());
    }

    @Test
    @DisplayName("Delete Profile1")
    public void testDelete1() {
        Mockito.when(sciProfileRepository.findByObjectName(Mockito.anyString())).thenReturn(Optional.ofNullable(null));
        String ObjectId = "123456";
        profileService.delete(ObjectId);
        Mockito.verify(sciProfileRepository, Mockito.never()).findByObjectName(Mockito.anyString());
        Mockito.verify(sciProfileRepository, Mockito.never()).delete(Mockito.any());
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

    SciProfileEntity getSciProfile() {
        SciProfileEntity sciProfileEntity = new SciProfileEntity();
        sciProfileEntity.setAction("Action");
        sciProfileEntity.setEntityId("1234");
        sciProfileEntity.setExtendsObjectId("ExtendsObjectId");
        sciProfileEntity.setGln("Gln");
        return sciProfileEntity;
    }

    List<SciProfileEntity> getSciProfiles() {
        List<SciProfileEntity> sciProfileEntities = new ArrayList<>();
        sciProfileEntities.add(getSciProfile());
        return sciProfileEntities;
    }

}
