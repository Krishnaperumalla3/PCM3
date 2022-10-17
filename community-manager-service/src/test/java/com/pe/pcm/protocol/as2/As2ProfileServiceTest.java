package com.pe.pcm.protocol.as2;


import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.protocol.As2Model;
import com.pe.pcm.protocol.as2.si.As2ProfileRepository;
import com.pe.pcm.protocol.as2.si.entity.As2Profile;
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
public class As2ProfileServiceTest {
    @MockBean
    private As2ProfileRepository as2ProfileRepository;
    //@InjectMocks
    private AS2ProfileService as2ProfileService;

    @BeforeEach
    void inIt() {
        as2ProfileService = new AS2ProfileService(as2ProfileRepository);
    }

    @Test
    @DisplayName("Save AS2 Profile Service")
    public void testSave() {
        as2ProfileService.save("transportObjectId", generateAs2Model());
        Mockito.verify(as2ProfileRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Update AS2 Profile Service")
    public void testUpdate1() {
        Mockito.when(as2ProfileRepository.findById(Mockito.anyString())).thenReturn(Optional.of(getAs2Profile()));
        as2ProfileService.update("transportObjectId", generateAs2Model());
        Mockito.verify(as2ProfileRepository, Mockito.times(1)).findById(Mockito.anyString());
        Mockito.verify(as2ProfileRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Update a AS2 Profile Service1")
    public void testUpdate2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(as2ProfileRepository.findById(Mockito.anyString())).thenThrow(notFound("Protocol"));
            as2ProfileService.update("transportObjectId", generateAs2Model());
        });
        Mockito.verify(as2ProfileRepository, Mockito.times(1)).findById(Mockito.anyString());
        Mockito.verify(as2ProfileRepository, Mockito.never()).save(Mockito.any());
    }

    @Test
    @DisplayName("Get AS2 Profile Service")
    public void testGet() {
        Mockito.when(as2ProfileRepository.findById(Mockito.anyString())).thenReturn(Optional.of(getAs2Profile()));
        String transportObjectId = "123456";
        as2ProfileService.get(transportObjectId);
        Mockito.verify(as2ProfileRepository, Mockito.times(1)).findById(Mockito.anyString());
    }


    @Test
    @DisplayName("Delete AS2 Profile Service")
    public void testDelete() {
        String transportObjectId = "123456";
        as2ProfileService.delete(transportObjectId);
        Mockito.verify(as2ProfileRepository, Mockito.times(1)).findById(Mockito.anyString());
        Mockito.verify(as2ProfileRepository, Mockito.never()).delete(Mockito.any());
    }

    As2Model generateAs2Model() {
        As2Model as2Model = new As2Model();
        as2Model.setPassword("password");
        as2Model.setProtocol("protocol");
        as2Model.setPkId("123456");
        as2Model.setEmailId("Email@email.com");
        as2Model.setUsername("UserName");
        as2Model.setPhone("9143474564");
        as2Model.setProfileId("ProfileId");
        as2Model.setProfileName("ProfileName");
        as2Model.setHubInfo(false);
        return as2Model;
    }

    As2Profile getAs2Profile() {
        As2Profile as2Profile = new As2Profile();
        as2Profile.setHttpClientAdapter("HTTP");
        as2Profile.setIsOrg("F");
        as2Profile.setIsOriginalOrg("Original");
        return as2Profile;
    }
}
