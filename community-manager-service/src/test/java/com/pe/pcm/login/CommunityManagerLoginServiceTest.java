package com.pe.pcm.login;

import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.login.entity.SoUsersEntity;
import com.pe.pcm.login.entity.YfsUserEntity;
import com.pe.pcm.user.TpUserRepository;
import com.pe.pcm.user.entity.TpUserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
public class CommunityManagerLoginServiceTest {
    @Mock
    private SoUsersRepository petpeSoUsersRepository;
    @Mock
    private YfsUserRepository yfsUserRepository;
    @Mock
    private TpUserRepository tpUserRepository;
    //@InjectMocks
    private CommunityManagerLoginService communityManagerLoginService;

    @BeforeEach
    void inIt(){
        communityManagerLoginService = new CommunityManagerLoginService(petpeSoUsersRepository,yfsUserRepository);
    }

    @Test
    @DisplayName("Get Pcm User")
    public void testGetPcmUser() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(petpeSoUsersRepository.findById(Mockito.anyString())).thenThrow(notFound("user"));
            String userName = "pcm";
            communityManagerLoginService.getPcmUser(userName);
        });
        Mockito.verify(petpeSoUsersRepository, Mockito.times(1)).findById(Mockito.anyString());
    }

    @Test
    @DisplayName("Get Pcm User1")
    public void testGetPcmUser1() {
        Mockito.when(petpeSoUsersRepository.findById(Mockito.anyString())).thenReturn(Optional.of(getSoUsersEntity()));
        communityManagerLoginService.getPcmUser("123456");
        Mockito.verify(petpeSoUsersRepository, Mockito.times(1)).findById(Mockito.anyString());
    }

    @Test
    @DisplayName("Get Yfs User")
    public void testGetYfsUser() {
        Mockito.when(yfsUserRepository.findFirstByUsername(Mockito.anyString())).thenReturn(Optional.of(getYfsUserEntity()));
        communityManagerLoginService.getYfsUser("Username");
        Mockito.verify(yfsUserRepository, Mockito.times(1)).findFirstByUsername(Mockito.anyString());
    }


    @Test
    @DisplayName("Minimal")
    public void testMinimal() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            communityManagerLoginService.minimal("login", "role", false);
        });
        Mockito.verify(yfsUserRepository, Mockito.never()).findFirstByUsername(Mockito.anyString());
    }

    SoUsersEntity getSoUsersEntity() {
        SoUsersEntity soUsersEntity = new SoUsersEntity();
        soUsersEntity.setEmail("Email@com");
        soUsersEntity.setRole("Role");
        soUsersEntity.setFirstName("FirstName");
        soUsersEntity.setMiddleName("MiddleName");
        soUsersEntity.setIsFaxUser("IsFaxUser");
        soUsersEntity.setUserid("123456");
        return soUsersEntity;
    }

    CommunityManagerUserModel generateCommunityManagerUserModel() {
        CommunityManagerUserModel communityManagerUserModel = new CommunityManagerUserModel("login", "role");
        communityManagerUserModel.setUserId("1324");
        communityManagerUserModel.setUserName("User");
        communityManagerUserModel.setSiUser(false);
        return communityManagerUserModel;
    }

    YfsUserEntity getYfsUserEntity() {
        YfsUserEntity yfsUserEntity = new YfsUserEntity();
        yfsUserEntity.setActivateflag("ActivateFlag");
        yfsUserEntity.setBusinessKey("BusinessKey");
        yfsUserEntity.setConfirmValue("ConfirmValue");
        yfsUserEntity.setImagefile("ImageFile");
        return yfsUserEntity;
    }

    TpUserEntity getTpUserEntity() {
        TpUserEntity tpUserEntity = new TpUserEntity();
        tpUserEntity.setUserid("123456");
        tpUserEntity.setGroupList("GroupList");
        return tpUserEntity;
    }
}
