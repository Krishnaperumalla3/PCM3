//package com.pe.pcm.partner;
//
//import com.pe.pcm.activity.ActivityHistoryService;
//import com.pe.pcm.certificate.CaCertInfoService;
//import com.pe.pcm.certificate.CertsAndPriKeyService;
//import com.pe.pcm.certificate.TrustedCertInfoService;
//import com.pe.pcm.certificate.entity.CaCertInfoEntity;
//import com.pe.pcm.certificate.entity.TrustedCertInfoEntity;
//import com.pe.pcm.exception.CommunityManagerServiceException;
//import com.pe.pcm.miscellaneous.UserUtilityService;
//import com.pe.pcm.partner.entity.PartnerEntity;
//import com.pe.pcm.protocol.As2Model;
//import com.pe.pcm.protocol.As2RelationMapModel;
//import com.pe.pcm.protocol.ManageProtocolService;
//import com.pe.pcm.protocol.as2.*;
//import com.pe.pcm.protocol.as2.certificate.*;
//import com.pe.pcm.protocol.as2.entity.As2Entity;
//import com.pe.pcm.protocol.as2.si.certificate.entity.SciDocxKeyCertEntity;
//import com.pe.pcm.protocol.as2.si.certificate.entity.SciDocxUserCertEntity;
//import com.pe.pcm.protocol.as2.si.certificate.entity.SciTranspCaCertEntity;
//import com.pe.pcm.protocol.as2.si.certificate.entity.SciTrpUserCertEntity;
//import com.pe.pcm.protocol.as2.si.entity.*;
//import com.pe.pcm.workflow.ProcessService;
//import com.pe.pcm.workflow.entity.ProcessEntity;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;
//
///*
// * @Author V.Shameer
// */
//@ExtendWith(SpringExtension.class)
//@ExtendWith(MockitoExtension.class)
//@ActiveProfiles("test")
//class AS2PartnerServiceTest {
//    @MockBean
//    private DeliveryChangeService deliveryChangeService;
//    @MockBean
//    private SciDocxKeyCertService sciDocxKeyCertService;
//    @MockBean
//    private DocExchangeService docExchangeService;
//    @MockBean
//    private PackagingService packagingService;
//    @MockBean
//    private As2Service as2Service;
//    @MockBean
//    private ProfileService profileService;
//    @MockBean
//    private TransportService transportService;
//    @MockBean
//    private AS2ProfileService as2ProfileService;
//    @MockBean
//    private YfsOrganizationService yfsOrganizationService;
//    @MockBean
//    private TrustedCertInfoService trustedCertInfoService;
//    @MockBean
//    private AS2EmailInfoService as2EmailInfoService;
//    @MockBean
//    private CaCertInfoService caCertInfoService;
//    @MockBean
//    private SciDocxUserCertService sciDocxUserCertService;
//    @MockBean
//    private SciTrpUserCertService sciTrpUserCertService;
//    @MockBean
//    private SciTranspCaCertService sciTranspCaCertService;
//    @MockBean
//    private PartnerService partnerService;
//    @MockBean
//    private ActivityHistoryService activityHistoryService;
//    @MockBean
//    private SciContractService sciContractService;
//    @MockBean
//    private ProcessService processService;
//    @Value("${b2b.as2.active}")
//    private String isAs2B2bApiActive;
//    @Value("${b2b.active}")
//    private String isB2bApiActive;
//    private Boolean isAs2B2bApiActiveB = Boolean.valueOf(isAs2B2bApiActive);
//    private Boolean isB2bApiActiveB = Boolean.valueOf(isAs2B2bApiActive);
//    @MockBean
//    private CertsAndPriKeyService certsAndPriKeyService;
//    @MockBean
//    private SciTrpKeyCertService sciTrpKeyCertService;
//    @MockBean
//    private UserUtilityService userUtilityService;
//    @MockBean
//    private ManageProtocolService manageProtocolService;
//
//    private AS2PartnerService as2PartnerService;
//
//    @BeforeEach
//    void inIt() {
//        as2PartnerService = new AS2PartnerService(deliveryChangeService, sciDocxKeyCertService, docExchangeService,
//                packagingService, as2Service, profileService,
//                transportService, as2ProfileService,
//                yfsOrganizationService, trustedCertInfoService,
//                sciDocxUserCertService, sciTrpUserCertService,
//                caCertInfoService, sciTranspCaCertService,
//                as2EmailInfoService, partnerService, activityHistoryService,
//                sciContractService, processService, isAs2B2bApiActiveB,
//                isB2bApiActiveB, certsAndPriKeyService, sciTrpKeyCertService, userUtilityService, manageProtocolService);
//    }
//
//
//    @Test
//    @DisplayName("Save AS2 Partner with Unknown Protocol")
//    void testSave() {
//        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
//            Mockito.when(packagingService.get(Mockito.anyBoolean(), Mockito.anyString())).thenReturn(Optional.empty());
//            Mockito.when(partnerService.find(Mockito.anyString())).thenReturn(Optional.empty());
//            as2PartnerService.saveAs2(getAs2Model("AS21"), false);
//        });
//        Mockito.verify(packagingService, Mockito.never()).get(Mockito.anyBoolean(), Mockito.anyString());
//        Mockito.verify(partnerService, Mockito.never()).find(Mockito.anyString());
//    }
//
//    @Test
//    @DisplayName("Save AS2 Partner with is BulkUpload true")
//    void testSave1() {
//        Mockito.when(partnerService.find(Mockito.anyString())).thenReturn(Optional.empty());
//        as2PartnerService.saveAs2(getAs2Model("AS2"), false);
//        packagingService.save("1234", getAs2Model("AS2"));
//        Mockito.verify(partnerService, Mockito.times(1)).find(Mockito.anyString());
//    }
//
//    @Test
//    @DisplayName("Save AS2 Partner with is BulkUpload False")
//    void testSave2() {
//        Mockito.when(packagingService.get(Mockito.anyBoolean(), Mockito.anyString())).thenReturn(Optional.empty());
//        Mockito.when(partnerService.find(Mockito.anyString())).thenReturn(Optional.empty());
//        as2PartnerService.saveAs2(getAs2Model("AS2"), false);
//        Mockito.verify(packagingService, Mockito.never()).get(Mockito.anyBoolean(), Mockito.anyString());
//        Mockito.verify(partnerService, Mockito.times(1)).find(Mockito.anyString());
//    }
//
//    @Test
//    @DisplayName("Save AS2 Partner with Existing Partner(SI)")
//    void testSave3() {
//        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
//            Mockito.when(packagingService.get(Mockito.anyBoolean(), Mockito.anyString())).thenReturn(Optional.ofNullable(getSciPackaging()));
//            Mockito.when(partnerService.find(Mockito.anyString())).thenReturn(Optional.empty());
//            as2PartnerService.saveAs2(getAs2Model("AS2"), true);
//        });
//        Mockito.verify(packagingService, Mockito.times(1)).get(Mockito.anyBoolean(), Mockito.anyString());
//        Mockito.verify(partnerService, Mockito.never()).find(Mockito.anyString());
//    }
//
//    @Test
//    @DisplayName("Save AS2 Partner with Existing Partner")
//    void testSave4() {
//        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
//            Mockito.when(packagingService.get(Mockito.anyBoolean(), Mockito.anyString())).thenReturn(Optional.empty());
//            Mockito.when(partnerService.find(Mockito.anyString())).thenReturn(Optional.ofNullable(getPartnerEntity()));
//            as2PartnerService.saveAs2(getAs2Model("AS2"), false);
//        });
//        Mockito.verify(packagingService, Mockito.never()).get(Mockito.anyBoolean(), Mockito.anyString());
//        Mockito.verify(partnerService, Mockito.times(1)).find(Mockito.anyString());
//    }
//
//    @Test
//    @DisplayName("Save2 As2 with IsBulkUpload FALSE")
//    void testSave5() {
//        Mockito.when(trustedCertInfoService.findByName(Mockito.anyString())).thenReturn(Optional.ofNullable(getTrustedCertInfoEntity()));
//        Mockito.when(caCertInfoService.getCaCertInfoByName(Mockito.anyString())).thenReturn(Optional.ofNullable(getCaCertInfoEntity()));
//        as2PartnerService.saveAs2(getAs2Model("AS2"), false);
//        Mockito.verify(transportService, Mockito.times(1)).save(Mockito.anyString(), Mockito.any());
//        Mockito.verify(docExchangeService, Mockito.times(1)).save(Mockito.anyString(), Mockito.any());
//        Mockito.verify(deliveryChangeService, Mockito.times(1)).save(Mockito.anyString(), Mockito.any());
//        Mockito.verify(packagingService, Mockito.times(1)).save(Mockito.anyString(), Mockito.any());
//        Mockito.verify(profileService, Mockito.times(1)).save(Mockito.anyString(), Mockito.any());
//        Mockito.verify(yfsOrganizationService, Mockito.times(1)).save(Mockito.anyString(), Mockito.any());
//        Mockito.verify(as2ProfileService, Mockito.times(1)).save(Mockito.anyString(), Mockito.any());
//        Mockito.verify(as2EmailInfoService, Mockito.times(1)).save(Mockito.anyString(), Mockito.any());
//        Mockito.verify(trustedCertInfoService, Mockito.never()).findByName(Mockito.anyString());
//        Mockito.verify(caCertInfoService, Mockito.times(1)).getCaCertInfoByName(Mockito.anyString());
//        Mockito.verify(as2Service, Mockito.times(1)).save(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.any(), "");
//        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
//        Mockito.verify(activityHistoryService, Mockito.times(1)).savePartnerActivity(Mockito.anyString(), Mockito.anyString());
//    }
//
//    @Test
//    @DisplayName("Save2 As2 with IsBulkUpload TRUE")
//    void testSave6() {
//        Mockito.when(trustedCertInfoService.findByName(Mockito.anyString())).thenReturn(Optional.ofNullable(getTrustedCertInfoEntity()));
//        Mockito.when(caCertInfoService.getCaCertInfoByName(Mockito.anyString())).thenReturn(Optional.ofNullable(getCaCertInfoEntity()));
//        as2PartnerService.saveAs2(getAs2Model("AS2"), false);
//        Mockito.verify(transportService, Mockito.times(1)).save(Mockito.anyString(), Mockito.any());
//        Mockito.verify(docExchangeService, Mockito.times(1)).save(Mockito.anyString(), Mockito.any());
//        Mockito.verify(deliveryChangeService, Mockito.times(1)).save(Mockito.anyString(), Mockito.any());
//        Mockito.verify(packagingService, Mockito.times(1)).save(Mockito.anyString(), Mockito.any());
//        Mockito.verify(profileService, Mockito.times(1)).save(Mockito.anyString(), Mockito.any());
//        Mockito.verify(yfsOrganizationService, Mockito.times(1)).save(Mockito.anyString(), Mockito.any());
//        Mockito.verify(as2ProfileService, Mockito.times(1)).save(Mockito.anyString(), Mockito.any());
//        Mockito.verify(as2EmailInfoService, Mockito.times(1)).save(Mockito.anyString(), Mockito.any());
//        Mockito.verify(trustedCertInfoService, Mockito.never()).findByName(Mockito.anyString());
//        Mockito.verify(caCertInfoService, Mockito.times(1)).getCaCertInfoByName(Mockito.anyString());
//        Mockito.verify(as2Service, Mockito.times(1)).save(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.any(), "");
//        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
//        Mockito.verify(activityHistoryService, Mockito.times(1)).savePartnerActivity(Mockito.anyString(), Mockito.anyString());
//    }
//
//    @Test
//    @DisplayName("Save AS2 Partner")
//    void testSave7() {
//        as2PartnerService = new AS2PartnerService(deliveryChangeService, sciDocxKeyCertService, docExchangeService,
//                packagingService, as2Service, profileService,
//                transportService, as2ProfileService,
//                yfsOrganizationService, trustedCertInfoService,
//                sciDocxUserCertService, sciTrpUserCertService,
//                caCertInfoService, sciTranspCaCertService,
//                as2EmailInfoService, partnerService, activityHistoryService,
//                sciContractService, processService, true,
//                true, certsAndPriKeyService, sciTrpKeyCertService, userUtilityService, manageProtocolService);
//        Mockito.when(partnerService.find(Mockito.anyString())).thenReturn(Optional.empty());
//        as2PartnerService.saveAs2(getAs2Model("AS2"), false);
//        packagingService.save("1234", getAs2Model("AS2"));
//        Mockito.verify(partnerService, Mockito.times(1)).find(Mockito.anyString());
//    }
//
//    @Test
//    @DisplayName("Save AS2 Partner")
//    void testSave8() {
//        as2PartnerService = new AS2PartnerService(deliveryChangeService, sciDocxKeyCertService, docExchangeService,
//                packagingService, as2Service, profileService,
//                transportService, as2ProfileService,
//                yfsOrganizationService, trustedCertInfoService,
//                sciDocxUserCertService, sciTrpUserCertService,
//                caCertInfoService, sciTranspCaCertService,
//                as2EmailInfoService, partnerService, activityHistoryService,
//                sciContractService, processService, false,
//                true, certsAndPriKeyService, sciTrpKeyCertService, userUtilityService, manageProtocolService);
//        Mockito.when(partnerService.find(Mockito.anyString())).thenReturn(Optional.empty());
//        as2PartnerService.saveAs2(getAs2Model("AS2"), false);
//        packagingService.save("1234", getAs2Model("AS2"));
//        Mockito.verify(partnerService, Mockito.times(1)).find(Mockito.anyString());
//    }
//
//    @Test
//    @DisplayName("Update AS2 Partner")
//    void testUpdate() {
//        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
//        Mockito.when(as2Service.get(Mockito.anyString())).thenReturn(getPetpeAs2());
//        Mockito.when(transportService.get(Mockito.anyString())).thenReturn(Optional.ofNullable(getSciTransport()));
//        Mockito.when(trustedCertInfoService.findByName(Mockito.anyString())).thenReturn(Optional.ofNullable(getTrustedCertInfoEntity()));
//        Mockito.when(caCertInfoService.getCaCertInfoByName(Mockito.anyString())).thenReturn(Optional.ofNullable(getCaCertInfoEntity()));
//        Mockito.when(as2Service.save(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.any(), "")).thenReturn(getPetpeAs2());
//        Mockito.when(partnerService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getPartnerEntity());
//        as2PartnerService.update(getAs2Model("AS2"));
//        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(as2Service, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(as2Service, Mockito.never()).delete(Mockito.anyString());
//        Mockito.verify(as2Service, Mockito.times(1)).save(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.any(), "");
//        Mockito.verify(transportService, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(transportService, Mockito.times(1)).update(Mockito.any(), Mockito.any());
//        Mockito.verify(docExchangeService, Mockito.times(1)).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(packagingService, Mockito.never()).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(as2ProfileService, Mockito.times(1)).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(as2EmailInfoService, Mockito.times(1)).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(trustedCertInfoService, Mockito.never()).findByName(Mockito.anyString());
//        Mockito.verify(caCertInfoService, Mockito.times(1)).getCaCertInfoByName(Mockito.anyString());
//        Mockito.verify(yfsOrganizationService, Mockito.times(1)).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
//    }
//
//    @Test
//    @DisplayName("Update AS2 Partner1")
//    void testUpdate1() {
//        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
//            Mockito.when(partnerService.get(Mockito.anyString())).thenThrow(notFound("Patner"));
//            Mockito.when(as2Service.get(Mockito.anyString())).thenReturn(getPetpeAs2());
//            Mockito.when(transportService.get(Mockito.anyString())).thenReturn(Optional.ofNullable(getSciTransport()));
//            Mockito.when(trustedCertInfoService.findByName(Mockito.anyString())).thenReturn(Optional.ofNullable(getTrustedCertInfoEntity()));
//            Mockito.when(caCertInfoService.getCaCertInfoByName(Mockito.anyString())).thenReturn(Optional.ofNullable(getCaCertInfoEntity()));
//            Mockito.when(as2Service.save(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.any(), "")).thenReturn(getPetpeAs2());
//            Mockito.when(partnerService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getPartnerEntity());
//            as2PartnerService.update(getAs2Model("AS2"));
//        });
//        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(as2Service, Mockito.never()).get(Mockito.anyString());
//        Mockito.verify(as2Service, Mockito.never()).delete(Mockito.anyString());
//        Mockito.verify(as2Service, Mockito.never()).save(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.any(), "");
//        Mockito.verify(transportService, Mockito.never()).get(Mockito.anyString());
//        Mockito.verify(transportService, Mockito.never()).update(Mockito.any(), Mockito.any());
//        Mockito.verify(docExchangeService, Mockito.never()).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(packagingService, Mockito.never()).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(as2ProfileService, Mockito.never()).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(as2EmailInfoService, Mockito.never()).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(trustedCertInfoService, Mockito.never()).findByName(Mockito.anyString());
//        Mockito.verify(caCertInfoService, Mockito.never()).getCaCertInfoByName(Mockito.anyString());
//        Mockito.verify(yfsOrganizationService, Mockito.never()).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(partnerService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
//        Mockito.verify(activityHistoryService, Mockito.never()).updatePartnerActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
//    }
//
//    @Test
//    @DisplayName("Update AS2 Partner2")
//    void testUpdate2() {
//        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
//            Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
//            Mockito.when(as2Service.get(Mockito.anyString())).thenThrow(notFound("Protocol"));
//            Mockito.when(transportService.get(Mockito.anyString())).thenReturn(Optional.ofNullable(getSciTransport()));
//            Mockito.when(trustedCertInfoService.findByName(Mockito.anyString())).thenReturn(Optional.ofNullable(getTrustedCertInfoEntity()));
//            Mockito.when(caCertInfoService.getCaCertInfoByName(Mockito.anyString())).thenReturn(Optional.ofNullable(getCaCertInfoEntity()));
//            Mockito.when(as2Service.save(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.any(), "")).thenReturn(getPetpeAs2());
//            Mockito.when(partnerService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getPartnerEntity());
//            as2PartnerService.update(getAs2Model("AS2"));
//        });
//        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(as2Service, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(as2Service, Mockito.never()).delete(Mockito.anyString());
//        Mockito.verify(as2Service, Mockito.never()).save(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.any(), "");
//        Mockito.verify(transportService, Mockito.never()).get(Mockito.anyString());
//        Mockito.verify(transportService, Mockito.never()).update(Mockito.any(), Mockito.any());
//        Mockito.verify(docExchangeService, Mockito.never()).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(packagingService, Mockito.never()).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(as2ProfileService, Mockito.never()).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(as2EmailInfoService, Mockito.never()).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(trustedCertInfoService, Mockito.never()).findByName(Mockito.anyString());
//        Mockito.verify(caCertInfoService, Mockito.never()).getCaCertInfoByName(Mockito.anyString());
//        Mockito.verify(yfsOrganizationService, Mockito.never()).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(partnerService, Mockito.never()).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
//        Mockito.verify(activityHistoryService, Mockito.never()).updatePartnerActivity(Mockito.any(), Mockito.any(), Mockito.anyMap(), Mockito.anyMap(), Mockito.anyBoolean());
//    }
//
//    @Test
//    @DisplayName("Update AS2 Partner")
//    void testUpdate3() {
//        as2PartnerService = new AS2PartnerService(deliveryChangeService, sciDocxKeyCertService, docExchangeService,
//                packagingService, as2Service, profileService,
//                transportService, as2ProfileService,
//                yfsOrganizationService, trustedCertInfoService,
//                sciDocxUserCertService, sciTrpUserCertService,
//                caCertInfoService, sciTranspCaCertService,
//                as2EmailInfoService, partnerService, activityHistoryService,
//                sciContractService, processService, true,
//                true, certsAndPriKeyService, sciTrpKeyCertService, userUtilityService, manageProtocolService);
//        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
//        Mockito.when(as2Service.get(Mockito.anyString())).thenReturn(getPetpeAs2());
//        Mockito.when(transportService.get(Mockito.anyString())).thenReturn(Optional.ofNullable(getSciTransport()));
//        Mockito.when(trustedCertInfoService.findByName(Mockito.anyString())).thenReturn(Optional.ofNullable(getTrustedCertInfoEntity()));
//        Mockito.when(caCertInfoService.getCaCertInfoByName(Mockito.anyString())).thenReturn(Optional.ofNullable(getCaCertInfoEntity()));
//        Mockito.when(as2Service.save(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.any(), "")).thenReturn(getPetpeAs2());
//        Mockito.when(partnerService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getPartnerEntity());
//        as2PartnerService.update(getAs2Model("AS2"));
//        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(as2Service, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(as2Service, Mockito.never()).delete(Mockito.anyString());
//        Mockito.verify(as2Service, Mockito.never()).save(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.any(), "");
//        Mockito.verify(transportService, Mockito.never()).get(Mockito.anyString());
//        Mockito.verify(transportService, Mockito.never()).update(Mockito.any(), Mockito.any());
//        Mockito.verify(docExchangeService, Mockito.never()).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(packagingService, Mockito.never()).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(as2ProfileService, Mockito.never()).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(as2EmailInfoService, Mockito.never()).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(trustedCertInfoService, Mockito.never()).findByName(Mockito.anyString());
//        Mockito.verify(caCertInfoService, Mockito.never()).getCaCertInfoByName(Mockito.anyString());
//        Mockito.verify(yfsOrganizationService, Mockito.never()).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
//    }
//
//    @Test
//    @DisplayName("Update AS2 Partner")
//    void testUpdate5() {
//        as2PartnerService = new AS2PartnerService(deliveryChangeService, sciDocxKeyCertService, docExchangeService,
//                packagingService, as2Service, profileService,
//                transportService, as2ProfileService,
//                yfsOrganizationService, trustedCertInfoService,
//                sciDocxUserCertService, sciTrpUserCertService,
//                caCertInfoService, sciTranspCaCertService,
//                as2EmailInfoService, partnerService, activityHistoryService,
//                sciContractService, processService, true,
//                true, certsAndPriKeyService, sciTrpKeyCertService, userUtilityService, manageProtocolService);
//        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
//        Mockito.when(as2Service.get(Mockito.anyString())).thenReturn(getPetpeAs2());
//        Mockito.when(transportService.get(Mockito.anyString())).thenReturn(Optional.ofNullable(getSciTransport()));
//        Mockito.when(trustedCertInfoService.findByName(Mockito.anyString())).thenReturn(Optional.ofNullable(getTrustedCertInfoEntity()));
//        Mockito.when(caCertInfoService.getCaCertInfoByName(Mockito.anyString())).thenReturn(Optional.ofNullable(getCaCertInfoEntity()));
//        Mockito.when(as2Service.save(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.any(), "")).thenReturn(getPetpeAs2());
//        Mockito.when(partnerService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getPartnerEntity());
//        as2PartnerService.update(getAs2Model("FTP"));
//        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(as2Service, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(as2Service, Mockito.times(1)).delete(Mockito.anyString());
//        Mockito.verify(as2Service, Mockito.never()).save(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.any(), "");
//        Mockito.verify(transportService, Mockito.never()).get(Mockito.anyString());
//        Mockito.verify(transportService, Mockito.never()).update(Mockito.any(), Mockito.any());
//        Mockito.verify(docExchangeService, Mockito.never()).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(packagingService, Mockito.never()).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(as2ProfileService, Mockito.never()).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(as2EmailInfoService, Mockito.never()).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(trustedCertInfoService, Mockito.never()).findByName(Mockito.anyString());
//        Mockito.verify(caCertInfoService, Mockito.never()).getCaCertInfoByName(Mockito.anyString());
//        Mockito.verify(yfsOrganizationService, Mockito.never()).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
//    }
//
//    @Test
//    @DisplayName("Update AS2 Partner")
//    void testUpdate4() {
//        as2PartnerService = new AS2PartnerService(deliveryChangeService, sciDocxKeyCertService, docExchangeService,
//                packagingService, as2Service, profileService,
//                transportService, as2ProfileService,
//                yfsOrganizationService, trustedCertInfoService,
//                sciDocxUserCertService, sciTrpUserCertService,
//                caCertInfoService, sciTranspCaCertService,
//                as2EmailInfoService, partnerService, activityHistoryService,
//                sciContractService, processService, false,
//                true, certsAndPriKeyService, sciTrpKeyCertService, userUtilityService, manageProtocolService);
//        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
//        Mockito.when(as2Service.get(Mockito.anyString())).thenReturn(getPetpeAs2());
//        Mockito.when(transportService.get(Mockito.anyString())).thenReturn(Optional.ofNullable(getSciTransport()));
//        Mockito.when(trustedCertInfoService.findByName(Mockito.anyString())).thenReturn(Optional.ofNullable(getTrustedCertInfoEntity()));
//        Mockito.when(caCertInfoService.getCaCertInfoByName(Mockito.anyString())).thenReturn(Optional.ofNullable(getCaCertInfoEntity()));
//        Mockito.when(as2Service.save(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.any(), "")).thenReturn(getPetpeAs2());
//        Mockito.when(partnerService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getPartnerEntity());
//        as2PartnerService.update(getAs2Model("AS2"));
//        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(as2Service, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(as2Service, Mockito.never()).delete(Mockito.anyString());
//        Mockito.verify(as2Service, Mockito.times(1)).save(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.any(), "");
//        Mockito.verify(transportService, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(transportService, Mockito.times(1)).update(Mockito.any(), Mockito.any());
//        Mockito.verify(docExchangeService, Mockito.times(1)).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(packagingService, Mockito.never()).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(as2ProfileService, Mockito.times(1)).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(as2EmailInfoService, Mockito.times(1)).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(trustedCertInfoService, Mockito.never()).findByName(Mockito.anyString());
//        Mockito.verify(caCertInfoService, Mockito.times(1)).getCaCertInfoByName(Mockito.anyString());
//        Mockito.verify(yfsOrganizationService, Mockito.times(1)).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
//    }
//
//    @Test
//    @DisplayName("Update AS2 Partner")
//    void testUpdate6() {
//        as2PartnerService = new AS2PartnerService(deliveryChangeService, sciDocxKeyCertService, docExchangeService,
//                packagingService, as2Service, profileService,
//                transportService, as2ProfileService,
//                yfsOrganizationService, trustedCertInfoService,
//                sciDocxUserCertService, sciTrpUserCertService,
//                caCertInfoService, sciTranspCaCertService,
//                as2EmailInfoService, partnerService, activityHistoryService,
//                sciContractService, processService, false,
//                true, certsAndPriKeyService, sciTrpKeyCertService, userUtilityService, manageProtocolService);
//        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
//        Mockito.when(as2Service.get(Mockito.anyString())).thenReturn(getPetpeAs2());
//        Mockito.when(transportService.get(Mockito.anyString())).thenReturn(Optional.ofNullable(getSciTransport()));
//        Mockito.when(trustedCertInfoService.findByName(Mockito.anyString())).thenReturn(Optional.ofNullable(getTrustedCertInfoEntity()));
//        Mockito.when(caCertInfoService.getCaCertInfoByName(Mockito.anyString())).thenReturn(Optional.ofNullable(getCaCertInfoEntity()));
//        Mockito.when(as2Service.save(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.any(), "")).thenReturn(getPetpeAs2());
//        Mockito.when(partnerService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getPartnerEntity());
//        as2PartnerService.update(getAs2Model("FTP"));
//        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(as2Service, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(as2Service, Mockito.times(1)).delete(Mockito.anyString());
//        Mockito.verify(as2Service, Mockito.times(1)).save(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.any(), "");
//        Mockito.verify(transportService, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(transportService, Mockito.times(1)).update(Mockito.any(), Mockito.any());
//        Mockito.verify(docExchangeService, Mockito.times(1)).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(packagingService, Mockito.never()).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(as2ProfileService, Mockito.times(1)).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(as2EmailInfoService, Mockito.times(1)).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(trustedCertInfoService, Mockito.never()).findByName(Mockito.anyString());
//        Mockito.verify(caCertInfoService, Mockito.times(1)).getCaCertInfoByName(Mockito.anyString());
//        Mockito.verify(yfsOrganizationService, Mockito.times(1)).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
//    }
//
//    @Test
//    @DisplayName("Update AS2 Partner")
//    void testUpdate7() {
//        as2PartnerService = new AS2PartnerService(deliveryChangeService, sciDocxKeyCertService, docExchangeService,
//                packagingService, as2Service, profileService,
//                transportService, as2ProfileService,
//                yfsOrganizationService, trustedCertInfoService,
//                sciDocxUserCertService, sciTrpUserCertService,
//                caCertInfoService, sciTranspCaCertService,
//                as2EmailInfoService, partnerService, activityHistoryService,
//                sciContractService, processService, false,
//                true, certsAndPriKeyService, sciTrpKeyCertService, userUtilityService, manageProtocolService);
//        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
//        Mockito.when(as2Service.get(Mockito.anyString())).thenReturn(getPetpeAs2());
//        Mockito.when(transportService.get(Mockito.anyString())).thenReturn(Optional.ofNullable(getSciTransport()));
//        Mockito.when(trustedCertInfoService.findByName(Mockito.anyString())).thenReturn(Optional.ofNullable(getTrustedCertInfoEntity()));
//        Mockito.when(caCertInfoService.getCaCertInfoByName(Mockito.anyString())).thenReturn(Optional.ofNullable(getCaCertInfoEntity()));
//        Mockito.when(as2Service.save(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.any(), "")).thenReturn(getPetpeAs2());
//        Mockito.when(partnerService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getPartnerEntity());
//        Mockito.when(docExchangeService.update(Mockito.anyString(), Mockito.any())).thenReturn(Optional.of(getSciDocExchange()));
//        as2PartnerService.update(getAs2Model("AS2"));
//        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(as2Service, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(as2Service, Mockito.never()).delete(Mockito.anyString());
//        Mockito.verify(as2Service, Mockito.times(1)).save(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.any(), "");
//        Mockito.verify(transportService, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(transportService, Mockito.times(1)).update(Mockito.any(), Mockito.any());
//        Mockito.verify(docExchangeService, Mockito.times(1)).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(packagingService, Mockito.never()).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(as2ProfileService, Mockito.times(1)).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(as2EmailInfoService, Mockito.times(1)).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(trustedCertInfoService, Mockito.never()).findByName(Mockito.anyString());
//        Mockito.verify(caCertInfoService, Mockito.times(1)).getCaCertInfoByName(Mockito.anyString());
//        Mockito.verify(yfsOrganizationService, Mockito.times(1)).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
//    }
//
//    @Test
//    @DisplayName("Update AS2 Partner")
//    void testUpdate8() {
//        as2PartnerService = new AS2PartnerService(deliveryChangeService, sciDocxKeyCertService, docExchangeService,
//                packagingService, as2Service, profileService,
//                transportService, as2ProfileService,
//                yfsOrganizationService, trustedCertInfoService,
//                sciDocxUserCertService, sciTrpUserCertService,
//                caCertInfoService, sciTranspCaCertService,
//                as2EmailInfoService, partnerService, activityHistoryService,
//                sciContractService, processService, false,
//                true, certsAndPriKeyService, sciTrpKeyCertService, userUtilityService, manageProtocolService);
//        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
//        Mockito.when(as2Service.get(Mockito.anyString())).thenReturn(getPetpeAs2());
//        Mockito.when(transportService.get(Mockito.anyString())).thenReturn(Optional.ofNullable(getSciTransport()));
//        Mockito.when(trustedCertInfoService.findByName(Mockito.anyString())).thenReturn(Optional.ofNullable(getTrustedCertInfoEntity()));
//        Mockito.when(caCertInfoService.getCaCertInfoByName(Mockito.anyString())).thenReturn(Optional.ofNullable(getCaCertInfoEntity()));
//        Mockito.when(as2Service.save(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.any(), "")).thenReturn(getPetpeAs2());
//        Mockito.when(partnerService.save(Mockito.any(), Mockito.anyString(), Mockito.anyString())).thenReturn(getPartnerEntity());
//        Mockito.when(docExchangeService.update(Mockito.anyString(), Mockito.any())).thenReturn(Optional.of(getSciDocExchange()));
//        As2Model as2Model = getAs2Model("AS2");
//        as2Model.setHubInfo(true);
//        as2PartnerService.update(as2Model);
//        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(as2Service, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(as2Service, Mockito.never()).delete(Mockito.anyString());
//        Mockito.verify(as2Service, Mockito.times(1)).save(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.any(), "");
//        Mockito.verify(transportService, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(transportService, Mockito.times(1)).update(Mockito.any(), Mockito.any());
//        Mockito.verify(docExchangeService, Mockito.times(1)).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(packagingService, Mockito.never()).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(as2ProfileService, Mockito.times(1)).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(as2EmailInfoService, Mockito.times(1)).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(trustedCertInfoService, Mockito.never()).findByName(Mockito.anyString());
//        Mockito.verify(caCertInfoService, Mockito.times(1)).getCaCertInfoByName(Mockito.anyString());
//        Mockito.verify(yfsOrganizationService, Mockito.times(1)).update(Mockito.anyString(), Mockito.any());
//        Mockito.verify(partnerService, Mockito.times(1)).save(Mockito.any(), Mockito.anyString(), Mockito.anyString());
//    }
//
//    @Test
//    @DisplayName("Get AS2 Partner")
//    void testGetAS2Partner() {
//        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
//        Mockito.when(transportService.get(Mockito.anyString())).thenReturn(Optional.ofNullable(getSciTransport()));
//        Mockito.when(as2Service.get(Mockito.anyString())).thenReturn(getPetpeAs2());
//        Mockito.when(trustedCertInfoService.findByName(Mockito.anyString())).thenReturn(Optional.ofNullable(getTrustedCertInfoEntity()));
//        Mockito.when(caCertInfoService.getCaCertById(Mockito.anyString())).thenReturn(Optional.ofNullable(getCaCertInfoEntity()));
//        Mockito.when(docExchangeService.get(Mockito.anyBoolean(), Mockito.anyString())).thenReturn(Optional.ofNullable(getSciDocExchange()));
//        as2PartnerService.get(Mockito.anyString());
//        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(transportService, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(as2Service, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(caCertInfoService, Mockito.never()).getCaCertById(Mockito.anyString());
//        Mockito.verify(docExchangeService, Mockito.never()).get(Mockito.anyBoolean(), Mockito.anyString());
//    }
//
//    @Test
//    @DisplayName("Get AS2 Partner1")
//    void testGetAS2Partner1() {
//        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
//            Mockito.when(partnerService.get(Mockito.anyString())).thenThrow(notFound("Partner"));
//            Mockito.when(transportService.get(Mockito.anyString())).thenReturn(Optional.ofNullable(getSciTransport()));
//            Mockito.when(as2Service.get(Mockito.anyString())).thenReturn(getPetpeAs2());
//            Mockito.when(trustedCertInfoService.findByName(Mockito.anyString())).thenReturn(Optional.ofNullable(getTrustedCertInfoEntity()));
//            Mockito.when(caCertInfoService.getCaCertById(Mockito.anyString())).thenReturn(Optional.ofNullable(getCaCertInfoEntity()));
//            Mockito.when(docExchangeService.get(Mockito.anyBoolean(), Mockito.anyString())).thenReturn(Optional.ofNullable(getSciDocExchange()));
//            as2PartnerService.get(Mockito.anyString());
//        });
//        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(transportService, Mockito.never()).get(Mockito.anyString());
//        Mockito.verify(as2Service, Mockito.never()).get(Mockito.anyString());
//        Mockito.verify(trustedCertInfoService, Mockito.never()).findByName(Mockito.anyString());
//        Mockito.verify(caCertInfoService, Mockito.never()).getCaCertById(Mockito.anyString());
//        Mockito.verify(docExchangeService, Mockito.never()).get(Mockito.anyBoolean(), Mockito.anyString());
//    }
//
//    @Test
//    @DisplayName("Get AS2 Partner")
//    void testGetAS2Partner2() {
//        Mockito.when(as2Service.get(Mockito.anyString())).thenReturn(getPetpeAs2());
//        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
//        as2PartnerService.get("123456");
//        Mockito.verify(as2Service, Mockito.times(1)).get(Mockito.anyString());
//    }
//
//    @Test
//    @DisplayName("Get AS2 Partner")
//    void testGetAS2Partner3() {
//        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
//            Mockito.when(as2Service.get(Mockito.anyString())).thenThrow(notFound("Protocol"));
//            Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
//            as2PartnerService.get("123456");
//        });
//        Mockito.verify(as2Service, Mockito.times(1)).get(Mockito.anyString());
//    }
//
//    @Test
//    @DisplayName("Get AS2 Partner")
//    void testGetAS2Partner4() {
//        as2PartnerService = new AS2PartnerService(deliveryChangeService, sciDocxKeyCertService, docExchangeService,
//                packagingService, as2Service, profileService,
//                transportService, as2ProfileService,
//                yfsOrganizationService, trustedCertInfoService,
//                sciDocxUserCertService, sciTrpUserCertService,
//                caCertInfoService, sciTranspCaCertService,
//                as2EmailInfoService, partnerService, activityHistoryService,
//                sciContractService, processService, true,
//                true, certsAndPriKeyService, sciTrpKeyCertService, userUtilityService, manageProtocolService);
//        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
//        Mockito.when(transportService.get(Mockito.anyString())).thenReturn(Optional.ofNullable(getSciTransport()));
//        Mockito.when(as2Service.get(Mockito.anyString())).thenReturn(getPetpeAs2());
//        Mockito.when(trustedCertInfoService.findByName(Mockito.anyString())).thenReturn(Optional.ofNullable(getTrustedCertInfoEntity()));
//        Mockito.when(caCertInfoService.getCaCertById(Mockito.anyString())).thenReturn(Optional.ofNullable(getCaCertInfoEntity()));
//        Mockito.when(docExchangeService.get(Mockito.anyBoolean(), Mockito.anyString())).thenReturn(Optional.ofNullable(getSciDocExchange()));
//        as2PartnerService.get(Mockito.anyString());
//        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(transportService, Mockito.never()).get(Mockito.anyString());
//        Mockito.verify(as2Service, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(caCertInfoService, Mockito.never()).getCaCertById(Mockito.anyString());
//        Mockito.verify(docExchangeService, Mockito.never()).get(Mockito.anyBoolean(), Mockito.anyString());
//    }
//
//    @Test
//    @DisplayName("Get AS2 Partner")
//    void testGetAS2Partner5() {
//        as2PartnerService = new AS2PartnerService(deliveryChangeService, sciDocxKeyCertService, docExchangeService,
//                packagingService, as2Service, profileService,
//                transportService, as2ProfileService,
//                yfsOrganizationService, trustedCertInfoService,
//                sciDocxUserCertService, sciTrpUserCertService,
//                caCertInfoService, sciTranspCaCertService,
//                as2EmailInfoService, partnerService, activityHistoryService,
//                sciContractService, processService, false,
//                true, certsAndPriKeyService, sciTrpKeyCertService, userUtilityService, manageProtocolService);
//        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
//        Mockito.when(transportService.get(Mockito.anyString())).thenReturn(Optional.ofNullable(getSciTransport()));
//        Mockito.when(as2Service.get(Mockito.anyString())).thenReturn(getPetpeAs2());
//        Mockito.when(trustedCertInfoService.findByName(Mockito.anyString())).thenReturn(Optional.ofNullable(getTrustedCertInfoEntity()));
//        Mockito.when(caCertInfoService.getCaCertById(Mockito.anyString())).thenReturn(Optional.ofNullable(getCaCertInfoEntity()));
//        Mockito.when(docExchangeService.get(Mockito.anyBoolean(), Mockito.anyString())).thenReturn(Optional.ofNullable(getSciDocExchange()));
//        as2PartnerService.get(Mockito.anyString());
//        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(transportService, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(as2Service, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(caCertInfoService, Mockito.never()).getCaCertById(Mockito.anyString());
//        Mockito.verify(docExchangeService, Mockito.never()).get(Mockito.anyBoolean(), Mockito.anyString());
//    }
//
//    @Test
//    @DisplayName("Delete AS2 Partner")
//    void testDeleteAs2Partner() {
//        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
//        Mockito.when(transportService.get(Mockito.anyString())).thenReturn(Optional.ofNullable(getSciTransport()));
//        as2PartnerService.delete(Mockito.anyString());
//        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(transportService, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(transportService, Mockito.times(1)).delete(Mockito.anyString());
//        Mockito.verify(docExchangeService, Mockito.times(1)).delete(Mockito.anyBoolean(), Mockito.anyString());
//        Mockito.verify(deliveryChangeService, Mockito.times(1)).delete(Mockito.anyBoolean(), Mockito.anyString());
//        Mockito.verify(packagingService, Mockito.times(1)).delete(Mockito.anyBoolean(), Mockito.anyString());
//        Mockito.verify(as2ProfileService, Mockito.times(1)).delete(Mockito.anyString());
//        Mockito.verify(profileService, Mockito.times(1)).delete(Mockito.anyString());
//        Mockito.verify(as2EmailInfoService, Mockito.times(1)).delete(Mockito.anyString());
//        Mockito.verify(yfsOrganizationService, Mockito.times(1)).delete(Mockito.anyString());
//        Mockito.verify(as2Service, Mockito.times(1)).delete(Mockito.anyString());
//        Mockito.verify(partnerService, Mockito.times(1)).delete(Mockito.any());
//    }
//
//    @Test
//    @DisplayName("Delete AS2 Partner")
//    void testDeleteAs2Partner1() {
//        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
//            Mockito.when(processService.findByPartnerProfile(Mockito.anyString())).thenReturn(getProcessEntities());
//            Mockito.when(partnerService.get(Mockito.anyString())).thenThrow(notFound("Partner"));
//            Mockito.when(transportService.get(Mockito.anyString())).thenReturn(Optional.ofNullable(getSciTransport()));
//            as2PartnerService.delete(Mockito.anyString());
//        });
//        Mockito.verify(partnerService, Mockito.never()).get(Mockito.anyString());
//        Mockito.verify(transportService, Mockito.never()).get(Mockito.anyString());
//        Mockito.verify(transportService, Mockito.never()).delete(Mockito.anyString());
//        Mockito.verify(docExchangeService, Mockito.never()).delete(Mockito.anyBoolean(), Mockito.anyString());
//        Mockito.verify(deliveryChangeService, Mockito.never()).delete(Mockito.anyBoolean(), Mockito.anyString());
//        Mockito.verify(packagingService, Mockito.never()).delete(Mockito.anyBoolean(), Mockito.anyString());
//        Mockito.verify(as2ProfileService, Mockito.never()).delete(Mockito.anyString());
//        Mockito.verify(profileService, Mockito.never()).delete(Mockito.anyString());
//        Mockito.verify(as2EmailInfoService, Mockito.never()).delete(Mockito.anyString());
//        Mockito.verify(yfsOrganizationService, Mockito.never()).delete(Mockito.anyString());
//        Mockito.verify(as2Service, Mockito.never()).delete(Mockito.anyString());
//        Mockito.verify(partnerService, Mockito.never()).delete(Mockito.any());
//    }
//
//    @Test
//    @DisplayName("Delete AS2 Partner")
//    void testDeleteAs2Partner4() {
//        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
//            Mockito.when(processService.findByPartnerProfile(Mockito.anyString())).thenReturn(new ArrayList<>());
//            Mockito.when(partnerService.get(Mockito.anyString())).thenThrow(notFound("Partner"));
//            Mockito.when(transportService.get(Mockito.anyString())).thenReturn(Optional.ofNullable(getSciTransport()));
//            as2PartnerService.delete(Mockito.anyString());
//        });
//        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(transportService, Mockito.never()).get(Mockito.anyString());
//        Mockito.verify(transportService, Mockito.never()).delete(Mockito.anyString());
//        Mockito.verify(docExchangeService, Mockito.never()).delete(Mockito.anyBoolean(), Mockito.anyString());
//        Mockito.verify(deliveryChangeService, Mockito.never()).delete(Mockito.anyBoolean(), Mockito.anyString());
//        Mockito.verify(packagingService, Mockito.never()).delete(Mockito.anyBoolean(), Mockito.anyString());
//        Mockito.verify(as2ProfileService, Mockito.never()).delete(Mockito.anyString());
//        Mockito.verify(profileService, Mockito.never()).delete(Mockito.anyString());
//        Mockito.verify(as2EmailInfoService, Mockito.never()).delete(Mockito.anyString());
//        Mockito.verify(yfsOrganizationService, Mockito.never()).delete(Mockito.anyString());
//        Mockito.verify(as2Service, Mockito.never()).delete(Mockito.anyString());
//        Mockito.verify(partnerService, Mockito.never()).delete(Mockito.any());
//    }
//
//    @Test
//    @DisplayName("Delete AS2 Partner")
//    void testDeleteAs2Partner2() {
//        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
//        Mockito.when(transportService.get(Mockito.anyString())).thenReturn(Optional.empty());
//        as2PartnerService.delete(Mockito.anyString());
//        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(transportService, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(transportService, Mockito.never()).delete(Mockito.anyString());
//        Mockito.verify(docExchangeService, Mockito.never()).delete(Mockito.anyBoolean(), Mockito.anyString());
//        Mockito.verify(deliveryChangeService, Mockito.never()).delete(Mockito.anyBoolean(), Mockito.anyString());
//        Mockito.verify(packagingService, Mockito.never()).delete(Mockito.anyBoolean(), Mockito.anyString());
//        Mockito.verify(as2ProfileService, Mockito.never()).delete(Mockito.anyString());
//        Mockito.verify(profileService, Mockito.never()).delete(Mockito.anyString());
//        Mockito.verify(as2EmailInfoService, Mockito.never()).delete(Mockito.anyString());
//        Mockito.verify(yfsOrganizationService, Mockito.never()).delete(Mockito.anyString());
//        Mockito.verify(as2Service, Mockito.times(1)).delete(Mockito.anyString());
//        Mockito.verify(partnerService, Mockito.times(1)).delete(Mockito.any());
//    }
//
//    @Test
//    @DisplayName("Delete AS2 Partner")
//    void testDeleteAs2Partner3() {
//        as2PartnerService = new AS2PartnerService(deliveryChangeService, sciDocxKeyCertService, docExchangeService,
//                packagingService, as2Service, profileService,
//                transportService, as2ProfileService,
//                yfsOrganizationService, trustedCertInfoService,
//                sciDocxUserCertService, sciTrpUserCertService,
//                caCertInfoService, sciTranspCaCertService,
//                as2EmailInfoService, partnerService, activityHistoryService,
//                sciContractService, processService, true,
//                true, certsAndPriKeyService, sciTrpKeyCertService, userUtilityService, manageProtocolService);
//        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
//        Mockito.when(transportService.get(Mockito.anyString())).thenReturn(Optional.empty());
//        as2PartnerService.delete(Mockito.anyString());
//        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(transportService, Mockito.never()).get(Mockito.anyString());
//        Mockito.verify(transportService, Mockito.never()).delete(Mockito.anyString());
//        Mockito.verify(docExchangeService, Mockito.never()).delete(Mockito.anyBoolean(), Mockito.anyString());
//        Mockito.verify(deliveryChangeService, Mockito.never()).delete(Mockito.anyBoolean(), Mockito.anyString());
//        Mockito.verify(packagingService, Mockito.never()).delete(Mockito.anyBoolean(), Mockito.anyString());
//        Mockito.verify(as2ProfileService, Mockito.never()).delete(Mockito.anyString());
//        Mockito.verify(profileService, Mockito.never()).delete(Mockito.anyString());
//        Mockito.verify(as2EmailInfoService, Mockito.never()).delete(Mockito.anyString());
//        Mockito.verify(yfsOrganizationService, Mockito.never()).delete(Mockito.anyString());
//        Mockito.verify(as2Service, Mockito.times(1)).delete(Mockito.anyString());
//        Mockito.verify(partnerService, Mockito.times(1)).delete(Mockito.any());
//    }
//
//    @Test
//    @DisplayName("Delete AS2 Partner")
//    void testDeleteAs2Partner5() {
//        as2PartnerService = new AS2PartnerService(deliveryChangeService, sciDocxKeyCertService, docExchangeService,
//                packagingService, as2Service, profileService,
//                transportService, as2ProfileService,
//                yfsOrganizationService, trustedCertInfoService,
//                sciDocxUserCertService, sciTrpUserCertService,
//                caCertInfoService, sciTranspCaCertService,
//                as2EmailInfoService, partnerService, activityHistoryService,
//                sciContractService, processService, false,
//                true, certsAndPriKeyService, sciTrpKeyCertService, userUtilityService, manageProtocolService);
//        Mockito.when(partnerService.get(Mockito.anyString())).thenReturn(getPartnerEntity());
//        Mockito.when(transportService.get(Mockito.anyString())).thenReturn(Optional.empty());
//        as2PartnerService.delete(Mockito.anyString());
//        Mockito.verify(partnerService, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(transportService, Mockito.times(1)).get(Mockito.anyString());
//        Mockito.verify(transportService, Mockito.never()).delete(Mockito.anyString());
//        Mockito.verify(docExchangeService, Mockito.never()).delete(Mockito.anyBoolean(), Mockito.anyString());
//        Mockito.verify(deliveryChangeService, Mockito.never()).delete(Mockito.anyBoolean(), Mockito.anyString());
//        Mockito.verify(packagingService, Mockito.never()).delete(Mockito.anyBoolean(), Mockito.anyString());
//        Mockito.verify(as2ProfileService, Mockito.never()).delete(Mockito.anyString());
//        Mockito.verify(profileService, Mockito.never()).delete(Mockito.anyString());
//        Mockito.verify(as2EmailInfoService, Mockito.never()).delete(Mockito.anyString());
//        Mockito.verify(yfsOrganizationService, Mockito.never()).delete(Mockito.anyString());
//        Mockito.verify(as2Service, Mockito.times(1)).delete(Mockito.anyString());
//        Mockito.verify(partnerService, Mockito.times(1)).delete(Mockito.any());
//    }
//
//    @Test
//    @DisplayName("Create AS2 Mapping")
//    void testCreateAs2Mapping() {
//        Mockito.when(sciContractService.getAs2Relation(Mockito.anyString(), Mockito.anyString())).thenReturn(Optional.empty());
//        Mockito.when(profileService.get(Mockito.anyString())).thenReturn(Optional.of(generateSciProfile()));
//        Mockito.when(profileService.get(Mockito.anyString())).thenReturn(Optional.of(generateSciProfile()));
//        Mockito.when(yfsOrganizationService.findById(Mockito.anyString())).thenReturn(Optional.of(generateYfsOrganizationEntity()));
//        as2PartnerService.createAs2Mapping(genereateAs2RelationMapModel());
//        Mockito.verify(sciContractService, Mockito.times(1)).getAs2Relation(Mockito.anyString(), Mockito.anyString());
//        Mockito.verify(profileService, Mockito.times(2)).get(Mockito.anyString());
//        Mockito.verify(yfsOrganizationService, Mockito.times(1)).findById(Mockito.anyString());
//        Mockito.verify(sciContractService, Mockito.times(2)).save(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
//        Mockito.verify(sciContractService, Mockito.times(4)).saveExtn(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
//        Mockito.verify(sciContractService, Mockito.times(1)).saveTradePartInfo(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
//    }
//
//    @Test
//    @DisplayName("Create AS2 Mapping")
//    void testCreateAs2Mapping1() {
//        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
//            Mockito.when(sciContractService.getAs2Relation(Mockito.anyString(), Mockito.anyString())).thenReturn(Optional.of(generateAs2TradePartInfo()));
//            Mockito.when(profileService.get(Mockito.anyString())).thenReturn(Optional.of(generateSciProfile()));
//            Mockito.when(profileService.get(Mockito.anyString())).thenReturn(Optional.of(generateSciProfile()));
//            Mockito.when(yfsOrganizationService.findById(Mockito.anyString())).thenReturn(Optional.of(generateYfsOrganizationEntity()));
//            as2PartnerService.createAs2Mapping(genereateAs2RelationMapModel());
//        });
//        Mockito.verify(sciContractService, Mockito.times(1)).getAs2Relation(Mockito.anyString(), Mockito.anyString());
//        Mockito.verify(profileService, Mockito.never()).get(Mockito.anyString());
//        Mockito.verify(yfsOrganizationService, Mockito.never()).findById(Mockito.anyString());
//        Mockito.verify(sciContractService, Mockito.never()).save(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
//        Mockito.verify(sciContractService, Mockito.never()).saveExtn(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
//        Mockito.verify(sciContractService, Mockito.never()).saveTradePartInfo(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
//    }
//
//    As2Model getAs2Model(String protocol) {
//        As2Model as2Model = new As2Model();
//        as2Model.setPkId("123456");
//        as2Model.setProfileId("123456");
//        as2Model.setEmailId("Email@email.com");
//        as2Model.setProfileName("ProfileName");
//        as2Model.setProtocol(protocol);
//        as2Model.setSenderId("SenderId");
//        as2Model.setSenderQualifier("SenderQualifier");
//        as2Model.setResponseTimeout(5);
//        as2Model.setPayloadType("tls");
//        as2Model.setMimeType("MimeType");
//        as2Model.setMimeSubType("MimeSubType");
//        as2Model.setSslType("SSLType");
//        as2Model.setCipherStrength("CipherStrenght");
//        as2Model.setCompressData("false");    //
//        as2Model.setPayloadEncryptionAlgorithm("PayLoadEncryption");
//        as2Model.setPayloadSecurity("LoadSecurity");
//        as2Model.setEncryptionAlgorithm("EncryptionAlgorithm");
//        as2Model.setSignatureAlgorithm("signature");
//        as2Model.setMdn(false);
//        as2Model.setMdnType("MDNType");
//        as2Model.setMdnEncryption("MDNEncryption");
//        as2Model.setPassword("Password");
//        as2Model.setReceiptToAddress("ReceiptToAddress");
//        as2Model.setUsername("UserName");
//        as2Model.setHubInfo(false);
//        as2Model.setExchangeCertificate("ExchangeCertificate");
//        as2Model.setSigningCertification("SingningCertification");
//        as2Model.setCaCertificate("CaCertificate");
//        as2Model.setKeyCertification("KeyCertificate");
//        as2Model.setKeyCertificatePassphrase("KeyCertificatePassPhrase");
//        return as2Model;
//    }
//
//    PartnerEntity getPartnerEntity() {
//        PartnerEntity partnerEntity = new PartnerEntity();
//        partnerEntity.setPkId("123456");
//        partnerEntity.setTpId("123456");
//        partnerEntity.setTpName("tpname");
//        partnerEntity.setTpProtocol("AS2");
//        partnerEntity.setPartnerProtocolRef("123456");
//        partnerEntity.setEmail("email@email.com");
//        partnerEntity.setPhone("987654321");
//        partnerEntity.setTpPickupFiles("Y");
//        partnerEntity.setFileTpServer("TpServer");
//        partnerEntity.setPartnerProtocolRef("ProtocolRef");
//        partnerEntity.setStatus("Y");
//        partnerEntity.setIsProtocolHubInfo("y");
//        return partnerEntity;
//    }
//
//    SciPackagingEntity getSciPackaging() {
//        SciPackagingEntity sciPackagingEntity = new SciPackagingEntity();
//        sciPackagingEntity.setObjectId("123456");
//        sciPackagingEntity.setObjectName("ObjectName");
//        return sciPackagingEntity;
//    }
//
//    TrustedCertInfoEntity getTrustedCertInfoEntity() {
//        return new TrustedCertInfoEntity();
//    }
//
//    CaCertInfoEntity getCaCertInfoEntity() {
//        return new CaCertInfoEntity();
//    }
//
//    As2Entity getPetpeAs2() {
//        As2Entity as2Entity = new As2Entity();
//        as2Entity.setIsHubInfo("N");
//        as2Entity.setAs2Identifier("123456");
//        as2Entity.setSignatureAlgorithm("1");
//        as2Entity.setMdnEncryption("1");
//        as2Entity.setEncryptionAlgorithm("1");
//        as2Entity.setMdnType("1");
//        as2Entity.setPayloadType("1");
//        return as2Entity;
//    }
//
//    SciTransportEntity getSciTransport() {
//        SciTransportEntity sciTransport = new SciTransportEntity();
//        sciTransport.setEntityId("entityId");
//        sciTransport.setObjectId("123456");
//        sciTransport.setObjectName("123456");
//        return sciTransport;
//    }
//
//    SciDocxUserCertEntity getsciDocxUserCert() {
//        return new SciDocxUserCertEntity();
//    }
//
//    SciTrpUserCertEntity getSciTrpUserCert() {
//        return new SciTrpUserCertEntity();
//    }
//
//    SciTranspCaCertEntity getSciTranspCaCert() {
//        SciTranspCaCertEntity sciTranspCaCert = new SciTranspCaCertEntity();
//        sciTranspCaCert.setTransportId("123456");
//        sciTranspCaCert.setCaCertId("123456");
//        return sciTranspCaCert;
//    }
//
//    SciDocExchangeEntity getSciDocExchange() {
//        SciDocExchangeEntity sciDocExchangeEntity = new SciDocExchangeEntity();
//        sciDocExchangeEntity.setDocExchangeKey("123456");
//        sciDocExchangeEntity.setObjectId("123456");
//        return sciDocExchangeEntity;
//    }
//
//    SciDocxKeyCertEntity getSciDocxKeyCert() {
//        SciDocxKeyCertEntity sciDocxKeyCert = new SciDocxKeyCertEntity();
//        sciDocxKeyCert.setDocExchangeId("123456");
//        return sciDocxKeyCert;
//    }
//
//    ProcessEntity genereateProcessEntity() {
//        ProcessEntity processEntity = new ProcessEntity();
//        processEntity.setApplicationProfile("ApplicationProfile");
//        processEntity.setSeqId("123456");
//        processEntity.setFlow("MFT");
//        return processEntity;
//    }
//
//    As2TradePartInfo generateAs2TradePartInfo() {
//        As2TradePartInfo as2TradePartInfo = new As2TradePartInfo();
//        as2TradePartInfo.setInContractId("123456");
//        as2TradePartInfo.setOrgAs2Id("123456");
//        as2TradePartInfo.setOrgProfileId("987654");
//        return as2TradePartInfo;
//    }
//
//    As2RelationMapModel genereateAs2RelationMapModel() {
//        As2RelationMapModel as2RelationMapModel = new As2RelationMapModel();
//        as2RelationMapModel.setPartnerName("partneranme");
//        as2RelationMapModel.setOrganizationName("Pragmaedge");
//        return as2RelationMapModel;
//    }
//
//    SciProfileEntity generateSciProfile() {
//        SciProfileEntity sciProfileEntity = new SciProfileEntity();
//        sciProfileEntity.setObjectName("objectname");
//        sciProfileEntity.setObjectId("123456");
//        return sciProfileEntity;
//    }
//
//    YFSOrganizationEntity generateYfsOrganizationEntity() {
//        YFSOrganizationEntity yfsOrganizationEntity = new YFSOrganizationEntity();
//        yfsOrganizationEntity.setActivateFlag("ActiveFlag");
//        yfsOrganizationEntity.setIsHubOrganization("IsHuborag");
//        yfsOrganizationEntity.setCorporateAddressKey("Croprate");
//        yfsOrganizationEntity.setDunsNumber("DnsNumber");
//        return yfsOrganizationEntity;
//    }
//
//    List<ProcessEntity> getProcessEntities() {
//        List<ProcessEntity> processEntities = new ArrayList<>();
//        processEntities.add(genereateProcessEntity());
//        return processEntities;
//    }
//}
