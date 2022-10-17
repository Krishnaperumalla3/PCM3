package com.pe.pcm.partner;

import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.miscellaneous.UserUtilityService;
import com.pe.pcm.partner.entity.PartnerEntity;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.WebserviceModel;
import com.pe.pcm.protocol.WebserviceService;
import com.pe.pcm.protocol.webservice.entity.WebserviceEntity;
import com.pe.pcm.workflow.ProcessService;
import com.pe.pcm.workflow.entity.ProcessEntity;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class WsPartnerServiceTest {

    @Mock
    private PartnerService partnerService;
    @Mock
    private WebserviceService webserviceService;
    @Mock
    private ActivityHistoryService activityHistoryService;
    @Mock
    private ProcessService processService;
    @Mock
    private ManageProtocolService manageProtocolService;
    @Mock
    private UserUtilityService userUtilityService;
    //@InjectMocks
    private WsPartnerService wsPartnerService;

    @BeforeEach
    void inIt() {
        wsPartnerService = new WsPartnerService(partnerService,webserviceService,activityHistoryService,
                processService,manageProtocolService,userUtilityService);
    }

    @Test
    @DisplayName("Create a new WsPartner With Unknown Protocol")
    public void testCreate() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            wsPartnerService.save(generateWebServiceModel("Webservice1"));
        });
        Mockito.verify(partnerService, Mockito.never()).find(Mockito.any());
    }

    @Test
    @DisplayName("Create a new WsPartner passing null in Protocol")
    public void testCreate1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            wsPartnerService.save(generateWebServiceModel(null));
        });
        Mockito.verify(partnerService, Mockito.never()).find(Mockito.any());
        Mockito.verify(webserviceService, Mockito.never()).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.never()).savePartnerActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Create a new WsPartner With known Protocol")
    public void testCreate2() {
        wsPartnerService.save(generateWebServiceModel("Webservice"));
        Mockito.verify(partnerService, Mockito.times(1)).find(Mockito.any());
        Mockito.verify(webserviceService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).savePartnerActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Create a new WsPartner With Duplicate Partner")
    public void testCreate3() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.find(Mockito.anyString())).thenReturn(Optional.ofNullable(getPartnerEntity()));
            wsPartnerService.save(generateWebServiceModel("Webservice"));
        });
        Mockito.verify(partnerService, Mockito.times(1)).find(Mockito.anyString());
        Mockito.verify(webserviceService, Mockito.never()).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.never()).savePartnerActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Create a new WsPartner With Vaild Partner")
    public void testCreate4() {
        Mockito.when(partnerService.find(Mockito.anyString())).thenReturn(Optional.ofNullable(null));
        wsPartnerService.save(generateWebServiceModel("Webservice"));
        Mockito.verify(partnerService, Mockito.times(1)).find(Mockito.any());
        Mockito.verify(webserviceService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).savePartnerActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Delete WsPartner")
    public void testDelete() {
        Mockito.when(processService.findByPartnerProfile(Mockito.anyString())).thenReturn(new ArrayList<>());
        Mockito.when(partnerService.findPartnerById(Mockito.anyString())).thenReturn(getPartnerEntity());
        final String PkId = "123456";
        wsPartnerService.delete(PkId);
        Mockito.verify(processService, Mockito.times(1)).findByPartnerProfile(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).findPartnerById(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).delete(Mockito.any());
        Mockito.verify(webserviceService, Mockito.times(1)).delete(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete not Existing WsPartner")
    public void testDelete1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.findPartnerById(Mockito.anyString())).thenThrow(notFound("Partner"));
            final String PkId = "123456";
            wsPartnerService.delete(PkId);
        });
        Mockito.verify(partnerService, Mockito.times(1)).findPartnerById(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).delete(Mockito.any());
        Mockito.verify(webserviceService, Mockito.never()).delete(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete not Existing WsPartner")
    public void testDelete2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(processService.findByPartnerProfile(Mockito.anyString())).thenReturn(getProcessEntities());
            final String PkId = "123456";
            wsPartnerService.delete(PkId);
        });
        Mockito.verify(processService, Mockito.times(1)).findByPartnerProfile(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).findPartnerById(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).delete(Mockito.any());
        Mockito.verify(webserviceService, Mockito.never()).delete(Mockito.anyString());
    }

    @Test
    @DisplayName("Get WsPartner With not Existing Protocol")
    public void testGetPartner1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
            Mockito.when(webserviceService.get(Mockito.anyString())).thenThrow(notFound("Protocol"));
            wsPartnerService.get(Mockito.anyString());
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(webserviceService, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Get WsPartner With not Existing Protocol and Partner")
    public void testGetPartner2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenThrow(notFound("Protocol"));
            wsPartnerService.get(Mockito.anyString());
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(webserviceService, Mockito.never()).get(Mockito.anyString());
    }


    @Test
    @DisplayName("Get WsPartner with not Existing partner")
    public void testGetPartner3() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenThrow(notFound("Partner"));
            final String pkId = "123456";
            wsPartnerService.get(pkId);
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Get WsPartner with Passing valid data")
    public void testGetPartner4() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(webserviceService.get(Mockito.anyString())).thenReturn(getWebserviceEntity());
        final String pkId = "123456";
        WebserviceModel webserviceModel = wsPartnerService.get(pkId);
        Mockito.verify(partnerService, Mockito.times(1)).get(pkId);
        Mockito.verify(webserviceService, Mockito.times(1)).get(pkId);
        Assertions.assertEquals(webserviceModel.getEmailId(), "email@email.com");
        Assertions.assertEquals(webserviceModel.getAdapterName(), "AdapterName");
    }

    @Test
    @DisplayName("Update Not-Existing WsPartner")
    public void testUpdate() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenThrow(notFound("Partner"));
            wsPartnerService.update(generateWebServiceModel("Webservice"));
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(webserviceService, Mockito.never()).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Update WsPartner with Not-Existing Protocol")
    public void testUpdate2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
            Mockito.when(webserviceService.get(Mockito.anyString())).thenThrow(notFound("Protocol"));
            wsPartnerService.update(generateWebServiceModel("Webservice"));
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(webserviceService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(webserviceService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.never()).updatePartnerActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
    }

    @Test
    @DisplayName("Update WsPartner with valid data")
    public void testUpdate4() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(webserviceService.get(Mockito.anyString())).thenReturn(getWebserviceEntity());
        Mockito.when(webserviceService.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getWebserviceEntity());
        Mockito.when(partnerService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getPartnerEntity());
        wsPartnerService.update(generateWebServiceModel("Webservice"));
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(webserviceService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(webserviceService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).updatePartnerActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.any(), Mockito.anyBoolean());
    }

    @Test
    @DisplayName("Update with Protocol change")
    public void testUpdate5() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(webserviceService.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getWebserviceEntity());
        Mockito.when(partnerService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getPartnerEntity());
        wsPartnerService.update(generateWebServiceModel("HTTP"));
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(webserviceService, Mockito.never()).get(Mockito.anyString());
        Mockito.verify(webserviceService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(webserviceService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).updatePartnerActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
    }

    @Test
    @DisplayName("Update with Protocol not change")
    public void testUpdate6() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(webserviceService.get(Mockito.anyString())).thenReturn(getWebserviceEntity());
        wsPartnerService.update(generateWebServiceModel("Webservice"));
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(webserviceService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(webserviceService, Mockito.never()).delete(Mockito.anyString());
    }

    PartnerEntity getPartnerEntity() {
        PartnerEntity partnerEntity = new PartnerEntity();
        partnerEntity.setPkId("123456");
        partnerEntity.setTpId("123456");
        partnerEntity.setTpName("tpname");
        partnerEntity.setTpProtocol("Webservice");
        partnerEntity.setPartnerProtocolRef("123456");
        partnerEntity.setEmail("email@email.com");
        partnerEntity.setPhone("987654321");
        partnerEntity.setTpPickupFiles("Y");
        partnerEntity.setFileTpServer("TpServer");
        partnerEntity.setStatus("Y");
        partnerEntity.setIsProtocolHubInfo("HubInfo");
        return partnerEntity;
    }

    WebserviceEntity getWebserviceEntity() {
        WebserviceEntity webserviceEntity = new WebserviceEntity();
        webserviceEntity.setPkId("123456");
        webserviceEntity.setAdapterName("AdapterName");
        webserviceEntity.setCertificate("CertificateId");
        return webserviceEntity;
    }

    WebserviceModel generateWebServiceModel(String protocol) {
        WebserviceModel webserviceModel = new WebserviceModel();
        webserviceModel.setPkId("123456");
        webserviceModel.setProfileName("ProfileName");
        webserviceModel.setProfileId("123456");
        webserviceModel.setAddressLine1("AddressLine1");
        webserviceModel.setAddressLine2("AddressLine2");
        webserviceModel.setProtocol(protocol);
        webserviceModel.setEmailId("Email@email.com");
        webserviceModel.setPhone("9876543210");
        webserviceModel.setStatus(false);
        webserviceModel.setName("Name");
        webserviceModel.setInMailBox("/InDirectory");
        webserviceModel.setOutBoundUrl("/5.6.2.3");
        webserviceModel.setCertificateId("CertificateId");
        webserviceModel.setPoolingInterval("22");
        webserviceModel.setAdapterName("AdapterName");
        webserviceModel.setHubInfo(false);
        return webserviceModel;
    }

    ProcessEntity getProcessEntity() {
        ProcessEntity processEntity = new ProcessEntity();
        processEntity.setApplicationProfile("AppProfile");
        processEntity.setPartnerProfile("PartnerProfile");
        processEntity.setSeqId("123");
        return processEntity;
    }

    List<ProcessEntity> getProcessEntities() {
        List<ProcessEntity> processEntities = new ArrayList<>();
        processEntities.add(getProcessEntity());
        return processEntities;
    }


}
