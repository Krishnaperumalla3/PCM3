package com.pe.pcm.protocol;


import com.pe.pcm.certificate.CaCertInfoService;
import com.pe.pcm.certificate.entity.CaCertInfoEntity;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.protocol.http.HttpRepository;
import com.pe.pcm.protocol.http.entity.HttpEntity;
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
public class HttpServiceTest {

    @Mock
    private HttpRepository httpRepository;
    @Mock
    private CaCertInfoService caCertInfoService;
    //@InjectMocks
    private HttpService httpService;

    @BeforeEach
    void inIt() {
        httpService = new HttpService(httpRepository,caCertInfoService);
    }

    @Test
    @DisplayName("Save Http Service")
    public void testSave() {
        httpService.save(getHttpEntity());
        Mockito.verify(httpRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Get Http Service")
    public void testGet() {
        Mockito.when(httpRepository.findBySubscriberId(Mockito.anyString())).thenReturn(Optional.of(getHttpEntity()));
        String pkId = "123456";
        httpService.get(pkId);
        Mockito.verify(httpRepository, Mockito.times(1)).findBySubscriberId(Mockito.anyString());
    }

    @Test
    @DisplayName("Get Http Service1")
    public void testGet1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(httpRepository.findBySubscriberId(Mockito.anyString())).thenThrow(notFound("Application"));
            String pKId = "123456";
            httpService.get(pKId);
        });
        Mockito.verify(httpRepository, Mockito.times(1)).findBySubscriberId(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete Http Service1")
    public void testDelete() {
        Mockito.when(httpRepository.findBySubscriberId(Mockito.anyString())).thenReturn(Optional.of(getHttpEntity()));
        String pkId = "123456";
        httpService.delete(pkId);
        Mockito.verify(httpRepository, Mockito.times(1)).findBySubscriberId(Mockito.anyString());
        Mockito.verify(httpRepository, Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    @DisplayName("Save protocol Http Service")
    public void testSaveProtocol() {
        Mockito.when(caCertInfoService.findByNameNotThrow(Mockito.anyString())).thenReturn(new CaCertInfoEntity());
        httpService.saveProtocol(getHttpModel("HTTP"), "1", "2", "Sub");
        Mockito.verify(httpRepository, Mockito.never()).findBySubscriberId(Mockito.anyString());
        Mockito.verify(httpRepository, Mockito.times(1)).save(Mockito.any());
    }

    HttpModel getHttpModel(String protocol) {
        HttpModel httpModel = new HttpModel();
        httpModel.setPkId("123456");
        httpModel.setProfileName("ProfileName");
        httpModel.setProfileId("ProfileId");
        httpModel.setProtocol(protocol);
        httpModel.setEmailId("Email@email.com");
        httpModel.setPhone("546589795");
        httpModel.setStatus(false);
        httpModel.setAdapterName("AdaptorName");
        httpModel.setPoolingInterval("PoolingInterval");
        httpModel.setCertificate("Certificate");
        httpModel.setInMailBox("Mailbox");
        httpModel.setOutBoundUrl("OutBoundUrl");
        httpModel.setHubInfo(false);
        return httpModel;
    }

    HttpEntity getHttpEntity() {
        HttpEntity httpEntity = new HttpEntity();
        httpEntity.setAdapterName("AdapterName");
        httpEntity.setCertificate("Certificate");
        httpEntity.setIsActive("IsActive");
        httpEntity.setProtocolType("http");
        httpEntity.setInMailbox("Mailbox");
        httpEntity.setIsHubInfo("HubInfo");
        httpEntity.setPkId("123456");
        return httpEntity;
    }


}
