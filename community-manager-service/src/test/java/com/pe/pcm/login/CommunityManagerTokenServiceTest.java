package com.pe.pcm.login;


import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.group.GroupService;
import com.pe.pcm.login.entity.YfsUserEntity;
import com.pe.pcm.logout.UserTokenExpRepository;
import com.pe.pcm.miscellaneous.PasswordUtilityService;
import com.pe.pcm.partner.PartnerService;
import com.pe.pcm.partner.entity.PartnerEntity;
import com.pe.pcm.properties.CMJwtProperties;
import com.pe.pcm.properties.CMProperties;
import com.pe.pcm.user.UserAttemptsService;
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
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class CommunityManagerTokenServiceTest {
    @Mock
    private CommunityManagerLoginService communityManagerLoginService;
    @Mock
    private PartnerService partnerService;
    @Mock
    private PasswordUtilityService passwordUtilityService;
    @Mock
    private Environment environment;
    @Mock
    private UserAttemptsService userAttemptsService;
    @Mock
    private CMProperties cmProperties;
    @Mock
    private CMJwtProperties cmJwtProperties;
    @Mock
    private UserTokenExpRepository userTokenExpRepository;
    @Mock
    private GroupService groupService;
    private String secretKey;
    //@InjectMocks
    private CommunityManagerTokenService communityManagerTokenService;

    @BeforeEach
    void inIt() {
        communityManagerTokenService = new CommunityManagerTokenService(communityManagerLoginService,passwordUtilityService,
                "","",false,false,environment,userAttemptsService,1,1,
                cmProperties,cmJwtProperties,userTokenExpRepository);
    }

    @Test
    @DisplayName("Authenticate")
    public void testAuthenticate() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(communityManagerLoginService.getYfsUser(Mockito.anyString())).thenThrow(notFound("user"));
            communityManagerTokenService.authenticate(getCommunityManagerLoginModel(), false);
        });
        Mockito.verify(communityManagerLoginService, Mockito.times(1)).getYfsUser(Mockito.anyString());
    }

    @Test
    @DisplayName("Authenticate1")
    public void testAuthenticate1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(communityManagerLoginService.getYfsUser(Mockito.anyString())).thenReturn(Optional.of(getYfsUserEntity()));
            communityManagerTokenService.authenticate(getCommunityManagerLoginModel(), false);
        });
        Mockito.verify(communityManagerLoginService, Mockito.times(1)).getYfsUser(Mockito.anyString());
    }

    @Test
    @DisplayName("Authenticate2")
    public void testAuthenticate2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(communityManagerLoginService.getYfsUser(Mockito.anyString())).thenReturn(Optional.ofNullable(null));
            communityManagerTokenService.authenticate(getCommunityManagerLoginModel(), false);
        });
        Mockito.verify(communityManagerLoginService, Mockito.times(1)).getYfsUser(Mockito.anyString());
    }

    YfsUserEntity getYfsUserEntity() {
        YfsUserEntity yfsUserEntity = new YfsUserEntity();
        yfsUserEntity.setActivateflag("ActivateFlag");
        yfsUserEntity.setBusinessKey("BusinessKey");
        yfsUserEntity.setConfirmValue("ConfirmValue");
        yfsUserEntity.setImagefile("ImageFile");
        return yfsUserEntity;
    }

    CommunityManagerLoginModel getCommunityManagerLoginModel() {
        CommunityManagerLoginModel communityManagerLoginModel = new CommunityManagerLoginModel();
        communityManagerLoginModel.setPassword("password");
        communityManagerLoginModel.setUserName("UserName");
        return communityManagerLoginModel;
    }

    CommunityManagerUserModel generateCommunityManagerUserModel() {
        CommunityManagerUserModel communityManagerUserModel = new CommunityManagerUserModel("login", "role");
        communityManagerUserModel.setUserId("1324");
        communityManagerUserModel.setUserName("User");
        communityManagerUserModel.setSiUser(false);
        return communityManagerUserModel;
    }

    PartnerEntity getPartnerEntity() {
        PartnerEntity partnerEntity = new PartnerEntity();
        partnerEntity.setTpName("ProfileName");
        partnerEntity.setTpId("123456");
        partnerEntity.setAddressLine1("AddressLine1");
        partnerEntity.setAddressLine2("AddressLine2");
        partnerEntity.setEmail("EmailId@email.com");
        partnerEntity.setPhone("9876543210");
        partnerEntity.setTpProtocol("FTP");
        partnerEntity.setTpPickupFiles("N");
        partnerEntity.setFileTpServer("N");
        partnerEntity.setIsProtocolHubInfo("N");
        return partnerEntity;

    }
}
