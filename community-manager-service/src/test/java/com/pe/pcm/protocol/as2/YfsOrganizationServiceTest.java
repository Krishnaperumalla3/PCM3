package com.pe.pcm.protocol.as2;


import com.pe.pcm.protocol.As2Model;
import com.pe.pcm.protocol.as2.si.YFSOrganizationRepository;
import com.pe.pcm.protocol.as2.si.entity.YFSOrganizationEntity;
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
public class YfsOrganizationServiceTest {
    @MockBean
    private YFSOrganizationRepository yfsOrganizationRepository;
    //@InjectMocks
    private YfsOrganizationService yfsOrganizationService;

    @BeforeEach
    void inIt() {
        yfsOrganizationService = new YfsOrganizationService(yfsOrganizationRepository);
    }

    @Test
    @DisplayName("Save YFSOrganizationEntity Service")
    public void testSave() {
        yfsOrganizationService.save("transportObjectId", generateAs2Model());
        Mockito.verify(yfsOrganizationRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Update YFSOrganizationEntity Service")
    public void testUpdate() {
        Mockito.when(yfsOrganizationRepository.findByObjectId(Mockito.anyString())).thenReturn(Optional.of(getYfsOrganizations()));
        yfsOrganizationService.update("transportObject", generateAs2Model());
        Mockito.verify(yfsOrganizationRepository, Mockito.times(1)).findByObjectId(Mockito.anyString());
        Mockito.verify(yfsOrganizationRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Get YFSOrganizationEntity Service")
    public void testGet() {
        Mockito.when(yfsOrganizationRepository.findByObjectId(Mockito.anyString())).thenReturn(Optional.of(getYfsOrganizations()));
        yfsOrganizationService.get("transferObject");
        Mockito.verify(yfsOrganizationRepository, Mockito.times(1)).findByObjectId(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete YFSOrganizationEntity Service")
    public void testDelete() {
        Mockito.when(yfsOrganizationRepository.findByObjectId(Mockito.anyString())).thenReturn(Optional.of(getYfsOrganizations()));
        yfsOrganizationService.delete("transferObject");
        Mockito.verify(yfsOrganizationRepository, Mockito.times(1)).findByObjectId(Mockito.anyString());
        Mockito.verify(yfsOrganizationRepository, Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    @DisplayName("Get1 YFSOrganizationEntity Service")
    public void testGet1() {
        Mockito.when(yfsOrganizationRepository.findById(Mockito.anyString())).thenReturn(Optional.of(getYfsOrganization()));
        yfsOrganizationService.findById("transferObject");
        Mockito.verify(yfsOrganizationRepository, Mockito.times(1)).findById(Mockito.anyString());
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

    YFSOrganizationEntity getYfsOrganization() {
        YFSOrganizationEntity yfsOrganizationEntity = new YFSOrganizationEntity();
        yfsOrganizationEntity.setActivateFlag("Activate");
        yfsOrganizationEntity.setDunsNumber("DumsNumber");
        yfsOrganizationEntity.setEmailAddr("EmailAddr");
        return yfsOrganizationEntity;
    }

    List<YFSOrganizationEntity> getYfsOrganizations() {
        List<YFSOrganizationEntity> yfsOrganizationEntities = new ArrayList<>();
        yfsOrganizationEntities.add(getYfsOrganization());
        return yfsOrganizationEntities;
    }


}
