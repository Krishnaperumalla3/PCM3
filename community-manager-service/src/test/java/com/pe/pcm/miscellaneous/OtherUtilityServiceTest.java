/*
package com.pe.pcm.miscellaneous;

import com.pe.pcm.application.RemoteApplicationStagingService;
import com.pe.pcm.application.sfg.RemoteFtpApplicationService;
import com.pe.pcm.login.CommunityManagerTokenService;
import com.pe.pcm.mail.MailService;
import com.pe.pcm.partner.RemoteFtpPartnerService;
import com.pe.pcm.partner.staging.RemotePartnerStagingService;
import com.pe.pcm.partner.entity.RemotePartnerStagingEntity;
import com.pe.pcm.protocol.RemoteFtpService;
import com.pe.pcm.user.UserService;
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

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class OtherUtilityServiceTest {
    @MockBean
    private RemoteFtpPartnerService remoteFtpPartnerService;
    @MockBean
    private RemoteFtpApplicationService remoteFtpApplicationService;
    @MockBean
    private RemotePartnerStagingService remotePartnerStagingService;
    @MockBean
    private RemoteApplicationStagingService remoteApplicationStagingService;
    @MockBean
    private CommunityManagerTokenService communityManagerTokenService;
    @MockBean
    private RemoteFtpService remoteFtpService;
    @MockBean
    private IndependentService independentService;
    @MockBean
    private Boolean isSMLogin;
    @MockBean
    private String jwtSecretKey;
    @MockBean
    private Boolean isB2bActive;
    @MockBean
    private String paramName;
    @MockBean
    private Boolean isMFTDuplicate;
    @MockBean
    private UserService userService;
    @MockBean
    private MailService mailService;
    @InjectMocks
    private OtherUtilityService otherUtilityService;

    @Test
    @DisplayName("Activate Remote Profiles")
    public void testRemotePartnerStagingEntity(){
        Mockito.when(remotePartnerStagingService.findAllRemotePartnerProfiles()).thenReturn(remotePartnerStagingEntities());
     otherUtilityService.activateRemoteProfiles();
     Mockito.verify(remotePartnerStagingService,Mockito.times(1)).findAllRemotePartnerProfiles();
    }

    @Test
    @DisplayName("getIsSMLogin")
    public void test_getIsSMLogin(){
        otherUtilityService.getIsSMLogin();
    }

    @Test
    @DisplayName("Get Terminators Map")
    public void test_getTerminatorsMap(){
    otherUtilityService.getTerminatorsMap();
    }

    RemotePartnerStagingEntity getRemotePartnerStagingEntity(){
        RemotePartnerStagingEntity remotePartnerStagingEntity=new RemotePartnerStagingEntity();
        remotePartnerStagingEntity.setAddressLine1("address1");
        remotePartnerStagingEntity.setAddressLine2("address2") ;
        remotePartnerStagingEntity.setEmail("email");
        remotePartnerStagingEntity.setTpProtocol("FTPS");
        return remotePartnerStagingEntity;
    }

    List<RemotePartnerStagingEntity> remotePartnerStagingEntities(){
        List<RemotePartnerStagingEntity> li=new ArrayList<>();
        li.add(getRemotePartnerStagingEntity());
        return li;
    }


}
*/
