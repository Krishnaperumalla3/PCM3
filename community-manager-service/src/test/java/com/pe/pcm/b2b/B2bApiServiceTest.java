package com.pe.pcm.b2b;

import com.pe.pcm.b2b.fileprocess.RemoteProcessFlowModel;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.miscellaneous.AppShutDownService;
import com.pe.pcm.miscellaneous.PasswordUtilityService;
import com.pe.pcm.pem.PemUtilityService;
import com.pe.pcm.sterling.SterlingAuthUserXrefSshService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.Silent.class)
@ActiveProfiles("test")
class B2bApiServiceTest {

    @Mock
    private SterlingAuthUserXrefSshService sterlingAuthUserXrefSshService;
    @Mock
    private PasswordUtilityService passwordUtilityService;
    @Mock
    private AppShutDownService appShutDownService;
    @Mock
    private PemUtilityService pemUtilityService;
    private B2BApiService b2BApiService;

    @BeforeEach
    void inIt() {
        b2BApiService = new B2BApiService("","","",false,sterlingAuthUserXrefSshService,passwordUtilityService,
                appShutDownService,pemUtilityService);
    }

    @Test
    void testRestartWorkflow() {
        //B2BApiService b2BApiService = Mockito.mock(B2BApiService.class);
        Assertions.assertThrows(CommunityManagerServiceException.class,() -> b2BApiService.restartWorkFlow(new RemoteProcessFlowModel(123456l)));
    }
}
