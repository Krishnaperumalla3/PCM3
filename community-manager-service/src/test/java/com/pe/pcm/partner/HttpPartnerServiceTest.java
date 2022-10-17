package com.pe.pcm.partner;

import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.b2b.B2BApiService;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.miscellaneous.B2bUtilityService;
import com.pe.pcm.miscellaneous.UserUtilityService;
import com.pe.pcm.partner.entity.PartnerEntity;
import com.pe.pcm.protocol.HttpModel;
import com.pe.pcm.protocol.HttpService;
import com.pe.pcm.protocol.ManageProtocolService;
import com.pe.pcm.protocol.http.entity.HttpEntity;
import com.pe.pcm.sterling.mailbox.SterlingMailboxService;
import com.pe.pcm.sterling.mailbox.routingrule.SterlingRoutingRuleService;
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
public class HttpPartnerServiceTest {

    @Mock
    private PartnerService partnerService;
    @Mock
    private HttpService httpService;
    @Mock
    private ActivityHistoryService activityHistoryService;
    @Mock
    private ProcessService processService;
    @Mock
    private ManageProtocolService manageProtocolService;
    @Mock
    private SterlingMailboxService sterlingMailboxService;
    @Mock
    private SterlingRoutingRuleService sterlingRoutingRuleService;
    //@InjectMocks
    private HttpPartnerService httpPartnerService;

    @BeforeEach
    void inIt() {
        httpPartnerService = new HttpPartnerService(httpService,processService,partnerService,
                activityHistoryService,manageProtocolService,sterlingMailboxService,sterlingRoutingRuleService);
    }

    @Test
    @DisplayName("Create a new HttpPartner With Unknown Protocol")
    public void testCreate() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            httpPartnerService.save(generateHttpModel("HTTP1"));
        });
        Mockito.verify(partnerService, Mockito.never()).find(Mockito.any());
    }

    @Test
    @DisplayName("Create a new HttpPartner passing null in Protocol")
    public void testCreate1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            httpPartnerService.save(generateHttpModel(null));
        });
        Mockito.verify(partnerService, Mockito.never()).find(Mockito.any());
        Mockito.verify(httpService, Mockito.never()).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.never()).savePartnerActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Create a new HttpPartner With known Protocol")
    public void testCreate2() {
        httpPartnerService.save(generateHttpModel("HTTP"));
        Mockito.verify(partnerService, Mockito.times(1)).find(Mockito.any());
        Mockito.verify(httpService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).savePartnerActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Create a new HttpPartner With Duplicate Partner")
    public void testCreate3() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.find(Mockito.anyString())).thenReturn(Optional.ofNullable(getPartnerEntity()));
            httpPartnerService.save(generateHttpModel("HTTP"));
        });
        Mockito.verify(partnerService, Mockito.times(1)).find(Mockito.anyString());
        Mockito.verify(httpService, Mockito.never()).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.never()).savePartnerActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Create a new HttpPartner With valid Partner")
    public void testCreate4() {
        Mockito.when(partnerService.find(Mockito.anyString())).thenReturn(Optional.ofNullable(null));
        httpPartnerService.save(generateHttpModel("HTTP"));
        Mockito.verify(partnerService, Mockito.times(1)).find(Mockito.any());
        Mockito.verify(httpService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).savePartnerActivity(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Delete HttpPartner")
    public void testDelete() {
        Mockito.when(processService.findByPartnerProfile(Mockito.anyString())).thenReturn(new ArrayList<>());
        Mockito.when(partnerService.findPartnerById(Mockito.anyString())).thenReturn(getPartnerEntity());
        final String PkId = "123456";
        httpPartnerService.delete(PkId);
        Mockito.verify(processService, Mockito.times(1)).findByPartnerProfile(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).findPartnerById(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).delete(Mockito.any());
        Mockito.verify(httpService, Mockito.times(1)).delete(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete not Existing HttpPartner")
    public void testDelete1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.findPartnerById(Mockito.anyString())).thenThrow(notFound("Partner"));
            final String PkId = "123456";
            httpPartnerService.delete(PkId);
        });
        Mockito.verify(partnerService, Mockito.times(1)).findPartnerById(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).delete(Mockito.any());
        Mockito.verify(httpService, Mockito.never()).delete(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete not Existing HttpPartner")
    public void testDelete2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(processService.findByPartnerProfile(Mockito.anyString())).thenReturn(getProcessEntities());
            final String PkId = "123456";
            httpPartnerService.delete(PkId);
        });
        Mockito.verify(processService, Mockito.times(1)).findByPartnerProfile(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).findPartnerById(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).delete(Mockito.any());
        Mockito.verify(httpService, Mockito.never()).delete(Mockito.anyString());
    }


    @Test
    @DisplayName("Get HttpPartner With not Existing Protocol")
    public void testGetPartner1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
            Mockito.when(httpService.get(Mockito.anyString())).thenThrow(notFound("Protocol"));
            httpPartnerService.get(Mockito.anyString());
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(httpService, Mockito.times(1)).get(Mockito.anyString());
    }

    @Test
    @DisplayName("Get HttpPartner With not Exsisting Protocol and Partner")
    public void testGetPartner2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenThrow(notFound("Protocol"));
            httpPartnerService.get(Mockito.anyString());
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(httpService, Mockito.never()).get(Mockito.anyString());
    }


    @Test
    @DisplayName("Get HttpPartner with not Existing partner")
    public void testGetPartner3() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenThrow(notFound("Partner"));
            final String pkId = "123456";
            httpPartnerService.get(pkId);
        });
        Mockito.verify(partnerService,Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(httpService, Mockito.never()).get(Mockito.anyString());
    }


    @Test
    @DisplayName("Get HttpPartner with Passing valid data")
    public void testGetPartner4() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(httpService.get(Mockito.anyString())).thenReturn(getHttpEntity());
        final String pkId = "123456";
        HttpModel httpModel = httpPartnerService.get(pkId);
        Mockito.verify(partnerService, Mockito.times(1)).get(pkId);
        Mockito.verify(httpService, Mockito.times(1)).get(pkId);
        Assertions.assertEquals(httpModel.getPoolingInterval(), "22");
        Assertions.assertEquals(httpModel.getAdapterName(), "AdapterName");
    }


    @Test
    @DisplayName("Update Not-Existing HttpPartner")
    public void testUpdate1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenThrow(notFound("Partner"));
            httpPartnerService.update(generateHttpModel("HTTP"));
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(httpService, Mockito.never()).get(Mockito.anyString());
    }


    @Test
    @DisplayName("Update HttpPartner with Not-Existing Protocol")
    public void testUpdate2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
            Mockito.when(httpService.get(Mockito.anyString())).thenThrow(notFound("Protocol"));
            httpPartnerService.update(generateHttpModel("HTTP"));
        });
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(httpService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(httpService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.never()).updatePartnerActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
    }

    @Test
    @DisplayName("Update HttpPartner with valid data")
    public void testUpdate3() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(httpService.get(Mockito.anyString())).thenReturn(getHttpEntity());
        Mockito.when(httpService.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getHttpEntity());
        Mockito.when(partnerService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getPartnerEntity());
        httpPartnerService.update(generateHttpModel("HTTP"));
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(httpService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(httpService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).updatePartnerActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
    }

    @Test
    @DisplayName("Update with Protocol change")
    public void testUpdate4() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(httpService.saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(getHttpEntity());
        Mockito.when(partnerService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getPartnerEntity());
        httpPartnerService.update(generateHttpModel("FTP"));
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(httpService, Mockito.never()).get(Mockito.anyString());
        Mockito.verify(httpService, Mockito.never()).delete(Mockito.anyString());
        Mockito.verify(httpService, Mockito.times(1)).saveProtocol(Mockito.any(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
        Mockito.verify(activityHistoryService, Mockito.times(1)).updatePartnerActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
    }

    @Test
    @DisplayName("Update with Protocol not change")
    public void testUpdate5() {
        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
        Mockito.when(httpService.get(Mockito.anyString())).thenReturn(getHttpEntity());
        httpPartnerService.update(generateHttpModel("HTTP"));
        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(httpService, Mockito.times(1)).get(Mockito.anyString());
        Mockito.verify(httpService, Mockito.never()).delete(Mockito.anyString());
    }

    HttpModel generateHttpModel(String protocol) {
        HttpModel httpModel = new HttpModel();
        httpModel.setPkId("123456");
        httpModel.setProfileName("ProfileName");
        httpModel.setProfileId("12345");
        httpModel.setAddressLine1("AddressLine1");
        httpModel.setAddressLine2("AddressLine2");
        httpModel.setProtocol(protocol);
        httpModel.setEmailId("Email@email.com");
        httpModel.setPhone("9876543210");
        httpModel.setStatus(false);
        httpModel.setAdapterName("AdapterName");
        httpModel.setPoolingInterval("12");
        httpModel.setCertificate("Certificate");
        httpModel.setInMailBox("/MailBox");
        httpModel.setOutBoundUrl("/OutBound");
        httpModel.setHubInfo(false);
        return httpModel;
    }

    PartnerEntity getPartnerEntity() {
        PartnerEntity partnerEntity = new PartnerEntity();
        partnerEntity.setPkId("123456");
        partnerEntity.setTpId("123456");
        partnerEntity.setTpName("tpname");
        partnerEntity.setTpProtocol("HTTP");
        partnerEntity.setPartnerProtocolRef("123456");
        partnerEntity.setEmail("email@email.com");
        partnerEntity.setPhone("987654321");
        partnerEntity.setTpPickupFiles("Y");
        partnerEntity.setFileTpServer("TpServer");
        partnerEntity.setPartnerProtocolRef("ProtocolRef");
        partnerEntity.setStatus("Y");
        partnerEntity.setIsProtocolHubInfo("HubInfo");
        return partnerEntity;
    }

    HttpEntity getHttpEntity() {
        HttpEntity httpEntity = new HttpEntity();
        httpEntity.setPkId("123456");
        httpEntity.setSubscriberId("654321");
        httpEntity.setSubscriberType("TP");
        httpEntity.setPoolingIntervalMins("22");
        httpEntity.setAdapterName("AdapterName");
        return httpEntity;
    }

    ProcessEntity getProcessEntity() {
        ProcessEntity processEntity = new ProcessEntity();
        processEntity.setApplicationProfile("AppProfile");
        processEntity.setPartnerProfile("PaternerProfilr");
        processEntity.setSeqId("123");
        return processEntity;
    }

    List<ProcessEntity> getProcessEntities() {
        List<ProcessEntity> processEntities = new ArrayList<>();
        processEntities.add(getProcessEntity());
        return processEntities;
    }
}
