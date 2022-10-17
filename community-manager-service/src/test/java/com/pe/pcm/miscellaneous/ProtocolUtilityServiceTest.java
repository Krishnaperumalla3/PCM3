//package com.pe.pcm.miscellaneous;
//
//
//import com.pe.pcm.enums.Protocol;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static org.hamcrest.Matchers.hasItems;
//import static org.hamcrest.Matchers.is;
//import static org.junit.Assert.assertThat;
//
///**
// * @author Shameer.
// */
//@ExtendWith(MockitoExtension.class)
//@ExtendWith(SpringExtension.class)
//@ActiveProfiles("test")
//public class ProtocolUtilityServiceTest {
//
//    private Boolean isB2bActiveF = false;
//    private boolean isB2bActive = true;
//    private boolean isCm = false;
//
//    @Test
//    @DisplayName("Get Protocol List")
//    public void test_getProtocolList() {
//        ProtocolUtilityService protocolUtilityService = new ProtocolUtilityService(isB2bActive, isCm);
//        List<String> expected = Arrays.stream(Protocol.values())
//                .map(Protocol::getProtocol)
//                .collect(Collectors.toList());
//        List<String> actual = protocolUtilityService.getProtocolList();
//        assertThat(actual, is(expected));
//        assertThat(actual, hasItems("FTP", "AS2", "SFGFTP", "SFGFTPS", "SFGSFTP"));
//    }
//
//    @Test
//    @DisplayName("Get Protocol List with B2b False")
//    public void test_getProtocolListWithFalse() {
//        ProtocolUtilityService protocolUtilityService = new ProtocolUtilityService(isB2bActiveF, isCm);
//        List<String> expected = Arrays.stream(Protocol.values())
//                .filter(protocol ->
//                        protocol != Protocol.SFG_FTP
//                                && protocol != Protocol.SFG_FTPS
//                                && protocol != Protocol.SFG_SFTP
//                )
//                .map(Protocol::getProtocol)
//                .collect(Collectors.toList());
//        List<String> actual = protocolUtilityService.getProtocolList();
//        assertThat(actual, is(expected));
//        assertThat(actual, hasItems("FTP", "AS2"));
//    }
//
//}
