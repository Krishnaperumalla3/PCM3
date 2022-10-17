package com.pe.pcm.miscellaneous;


import com.pe.pcm.b2b.B2BUtilityServices;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")

public class B2bUtilityServiceTest {
    @MockBean
    private B2BUtilityServices b2BUtilityServices;
    @InjectMocks
    private B2bUtilityService b2bUtilityService;


    @Test
    @DisplayName("update Routing Rule")
    public void test() {
        b2bUtilityService.updateRoutingRule("Interval", "mailbox");
        Mockito.verify(b2BUtilityServices, Mockito.never()).updateRoutingRule(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("update Routing Rule1")
    public void test1() {
        b2bUtilityService.updateRoutingRule("OldInterval", "Interval", "mailbox");
        Mockito.verify(b2BUtilityServices, Mockito.never()).updateRoutingRule(Mockito.anyString(), Mockito.anyString());
    }
}
