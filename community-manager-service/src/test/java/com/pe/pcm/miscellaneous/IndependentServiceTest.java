package com.pe.pcm.miscellaneous;


import com.pe.pcm.exception.CommunityManagerServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("default")
public class IndependentServiceTest {

    @MockBean
    private Environment environment;
    private String[] activeProfile = {"pcm", "saml", "default"};
    //@InjectMocks
    private IndependentService independentService;

    @BeforeEach
    void inIt() {
        independentService = new IndependentService(environment);
    }

    @Test
    @DisplayName("Get Active Profile")
    public void testGetActiveProfile() {
        Mockito.when(environment.getActiveProfiles()).thenReturn(activeProfile);
        independentService.getActiveProfile();
        Mockito.verify(environment, Mockito.times(1)).getActiveProfiles();
    }

    @Test
    @DisplayName("Get Active Profile1")
    public void test() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(environment.getActiveProfiles()).thenThrow(internalServerError("Please Start the application on PCM/PEM/DEFAULT profiles"));
            independentService.getActiveProfile();
        });
        Mockito.verify(environment, Mockito.times(1)).getActiveProfiles();
    }

}

