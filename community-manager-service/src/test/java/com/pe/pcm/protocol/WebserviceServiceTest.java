package com.pe.pcm.protocol;


import com.pe.pcm.certificate.CaCertInfoService;
import com.pe.pcm.certificate.entity.CaCertInfoEntity;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.protocol.webservice.WebserviceRepository;
import com.pe.pcm.protocol.webservice.entity.WebserviceEntity;
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

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class WebserviceServiceTest {
    @MockBean
    private WebserviceRepository webserviceRepository;
    @Mock
    private CaCertInfoService caCertInfoService;
    //@InjectMocks
    private WebserviceService webserviceService;

    @BeforeEach
    void inIt() {
        webserviceService = new WebserviceService(webserviceRepository,caCertInfoService);
    }

    @Test
    @DisplayName("Save Webservice")
    public void testSave() {
        webserviceService.save(getWebserviceEntity());
        Mockito.verify(webserviceRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Get Webservice")
    public void testGet() {
        Mockito.when(webserviceRepository.findBySubscriberId(Mockito.anyString())).thenReturn(Optional.of(getWebserviceEntity()));
        String pkId = "123456";
        webserviceService.get(pkId);
        Mockito.verify(webserviceRepository, Mockito.times(1)).findBySubscriberId(Mockito.anyString());
    }

    @Test
    @DisplayName("Get Web Service1")
    public void testGet1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(webserviceRepository.findBySubscriberId(Mockito.anyString())).thenThrow(notFound("Application"));
            String pkId = "123456";
            webserviceService.get(pkId);
        });
        Mockito.verify(webserviceRepository, Mockito.times(1)).findBySubscriberId(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete Webservice")
    public void testDelete() {
        Mockito.when(webserviceRepository.findBySubscriberId(Mockito.anyString())).thenReturn(Optional.of(getWebserviceEntity()));
        String pkId = "123456";
        webserviceService.delete(pkId);
        Mockito.verify(webserviceRepository, Mockito.times(1)).findBySubscriberId(Mockito.anyString());
        Mockito.verify(webserviceRepository, Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    @DisplayName("Save Protocol Webservice")
    public void testSaveProtocol() {
        Mockito.when(webserviceRepository.findBySubscriberId(Mockito.anyString())).thenReturn(Optional.of(getWebserviceEntity()));
        Mockito.when(caCertInfoService.findByIdNotThrow(Mockito.anyString())).thenReturn(new CaCertInfoEntity());
        webserviceService.saveProtocol(getWebserviceModel("WebService"), "1", "2", "SUB");
        Mockito.verify(webserviceRepository, Mockito.never()).findBySubscriberId(Mockito.anyString());
        Mockito.verify(webserviceRepository, Mockito.times(1)).save(Mockito.any());
    }

    WebserviceModel getWebserviceModel(String protocol) {
        WebserviceModel webserviceModel = new WebserviceModel();
        webserviceModel.setPkId("123456");
        webserviceModel.setProfileName("ProfileName");
        webserviceModel.setProfileId("ProfileId");
        webserviceModel.setProtocol(protocol);
        webserviceModel.setEmailId("Email@email.com");
        webserviceModel.setPhone("346587934");
        webserviceModel.setStatus(false);
        webserviceModel.setName("Name");
        webserviceModel.setInMailBox("MailBox");
        webserviceModel.setOutBoundUrl("OutBound");
        webserviceModel.setCertificateId("Certificate");
        webserviceModel.setPoolingInterval("PoolingInterval");
        webserviceModel.setAdapterName("AdapterName");
        webserviceModel.setHubInfo(false);
        return webserviceModel;
    }

    WebserviceEntity getWebserviceEntity() {
        WebserviceEntity webserviceEntity = new WebserviceEntity();
        webserviceEntity.setAdapterName("AdapterName");
        webserviceEntity.setCertificate("Certificate");
        webserviceEntity.setInDirectory("InDirectory");
        webserviceEntity.setIsActive("Active");
        webserviceEntity.setPkId("31435");
        webserviceEntity.setPoolingIntervalMins("poolingInterval");
        return webserviceEntity;
    }
}
