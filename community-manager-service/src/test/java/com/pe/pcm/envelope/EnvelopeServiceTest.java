package com.pe.pcm.envelope;

import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.envelope.entity.EdiPropertiesActivityHistoryEntity;
import com.pe.pcm.envelope.entity.EdiPropertiesEntity;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.partner.PartnerService;
import com.pe.pcm.partner.entity.PartnerEntity;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;
import static com.pe.pcm.miscellaneous.CommonQueryPredicate.getPredicate;
import static java.lang.Boolean.TRUE;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class EnvelopeServiceTest {

    @Mock
    private EdiPropertiesRepository edipropertiesrepository;
    @Mock
    private ActivityHistoryService activityHistoryService;
    @Mock
    private PartnerService partnerService;
    //@InjectMocks
    private EnvelopeService envelopeService;

    @BeforeEach
    void inIt() {
        envelopeService = new EnvelopeService(edipropertiesrepository,activityHistoryService);
    }

    @Test
    @DisplayName("Create Envelope")
    void testCreate() {
        Mockito.when(edipropertiesrepository.save(Mockito.any())).thenReturn(getEdiPropertiesEntity());
        envelopeService.create(generateEnvelopeModel());
        Mockito.verify(partnerService, Mockito.never()).get(Mockito.anyString());
        Mockito.verify(edipropertiesrepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(activityHistoryService, Mockito.times(1)).saveEnvelopeActivity(Mockito.any(), Mockito.any());
    }

    @Test
    @DisplayName("Update Envelope")
    void testUpdate() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(edipropertiesrepository.findById(Mockito.anyString())).thenThrow(notFound("Envelop"));
            envelopeService.update(generateEnvelopeModel());
        });
        Mockito.verify(activityHistoryService, Mockito.never()).updateEnvelopeActivity(getEdiPropertiesEntity(), getEdiPropertiesEntity());
    }

    @Test
    @DisplayName("Update Envelope1")
    void testUpdate1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            envelopeService.update(generateEnvelopeModel());
        });
        Mockito.verify(edipropertiesrepository, Mockito.never()).save(Mockito.any());
        Mockito.verify(activityHistoryService, Mockito.never()).updateEnvelopeActivity(Mockito.any(), Mockito.any());
    }

    @Test
    @DisplayName("Update Envelope2")
    void testUpdate2() {
        Mockito.when(edipropertiesrepository.findById(Mockito.anyString())).thenReturn(Optional.of(getEdiPropertiesEntity()));
        Mockito.when(edipropertiesrepository.save(Mockito.any())).thenReturn(getEdiPropertiesEntity());
        envelopeService.update(generateEnvelopeModel());
        Mockito.verify(edipropertiesrepository, Mockito.times(1)).findById(Mockito.anyString());
        Mockito.verify(edipropertiesrepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(activityHistoryService, Mockito.times(1)).updateEnvelopeActivity(Mockito.any(), Mockito.any());
    }

    @Test
    @DisplayName("Delete Envelope")
    void testDelete() {
        Mockito.when(edipropertiesrepository.findById(Mockito.anyString())).thenReturn(Optional.of(getEdiPropertiesEntity()));
        String pkId = "123456";
        envelopeService.delete(pkId);
        Mockito.verify(edipropertiesrepository, Mockito.times(1)).findById(Mockito.anyString());
        Mockito.verify(edipropertiesrepository, Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    @DisplayName(" Get Envelope ")
    void testGet() {
        Mockito.when(edipropertiesrepository.findById(Mockito.anyString())).thenReturn(Optional.of(getEdiPropertiesEntity()));
        String pkId = "123456";
        envelopeService.get(pkId);
        Mockito.verify(edipropertiesrepository, Mockito.times(1)).findById(Mockito.anyString());
    }

    @Test
    @DisplayName(" Get Envelope Activity History")
    void testGetActivityHistory() {
        Mockito.when(activityHistoryService.getEnvelopeHistory(Mockito.anyString(), Mockito.any())).thenReturn(getEdiPropertiesActivityHistoryEntities());
        String pkId = "123456";
        envelopeService.getHistory(pkId, Pageable.unpaged());
        Mockito.verify(activityHistoryService, Mockito.times(1)).getEnvelopeHistory(Mockito.anyString(), Mockito.any());
    }

    @Test
    @DisplayName("Get Envelope throws an notFound of Envelope")
    void testGet1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(edipropertiesrepository.findById(Mockito.anyString())).thenThrow(notFound("Envelop"));
            String pkId = "123456";
            envelopeService.get(pkId);
        });
        Mockito.verify(edipropertiesrepository, Mockito.times(1)).findById(Mockito.anyString());
    }

    @Test
    @DisplayName("Search Envelope")
    void testSearch() {
        Pageable pageable = Mockito.mock(Pageable.class);
        Mockito.when(edipropertiesrepository.findAll(Mockito.any(), Mockito.any(Pageable.class))).thenReturn(generateList());
        envelopeService.search(generateEnvelopeModel(), pageable);
        Mockito.verify(edipropertiesrepository, Mockito.times(1)).findAll(Mockito.any(), Mockito.any(Pageable.class));
    }

    //@Test
    @DisplayName("Search Envelope with Specification")
    void testSearch1() {
        Pageable pageable = Mockito.mock(Pageable.class);
        edipropertiesrepository.findAll((Specification<EdiPropertiesEntity>) getCriteriaBuilder(), pageable);
        envelopeService.search(generateEnvelopeModel(), pageable);
        Mockito.verify(edipropertiesrepository, Mockito.times(1)).findAll(Mockito.any(), Mockito.any(Pageable.class));
    }


    EnvelopeModel generateEnvelopeModel() {
        EnvelopeModel envelopeModel = new EnvelopeModel();
        envelopeModel.setPkId("123456");
        envelopeModel.setEdiProperties(generateEdiProperties());
        envelopeModel.setGsSegment(generateGsSegment());
        envelopeModel.setInBound(generateInBound());
        envelopeModel.setIsaSegment(generateIsaSegment());
        envelopeModel.setStSegment(generateStSegment());
        envelopeModel.setOutBound(generateOutBound());
        return envelopeModel;
    }

    EdiProperties generateEdiProperties() {
        EdiProperties ediProperties = new EdiProperties();
        ediProperties.setDirection("direction");
        ediProperties.setPartnerName("PartnerName");
        ediProperties.setPartnerPkId("123456");
        ediProperties.setValidateInput("Input");
        return ediProperties;
    }

    GsSegment generateGsSegment() {
        GsSegment gsSegment = new GsSegment();
        gsSegment.setFunctionalIdCode("123456");
        gsSegment.setGroupVersion("groupVersion");
        return gsSegment;
    }

    InBound generateInBound() {
        InBound inBound = new InBound();
        inBound.setAckDetailLevel("2");
        inBound.setComplianceCheck("98");
        inBound.setComplianceCheckMap("map");
        return inBound;
    }

    IsaSegment generateIsaSegment() {
        IsaSegment isaSegment = new IsaSegment();
        isaSegment.setBusinessProcess("BussinessProces");
        isaSegment.setGlobalContNo("2");
        isaSegment.setInterVersion("in");
        return isaSegment;
    }

    OutBound generateOutBound() {
        OutBound outBound = new OutBound();
        outBound.setAckOverDueHr("AckOverHr");
        outBound.setEleTerm("ElaTerm");
        return outBound;
    }

    StSegment generateStSegment() {
        StSegment stSegment = new StSegment();
        stSegment.setStReceiverId("StReceived");
        stSegment.setStSenderId("StSender");
        return stSegment;
    }

    EdiPropertiesEntity getEdiPropertiesEntity() {
        EdiPropertiesEntity ediPropertiesEntity = new EdiPropertiesEntity();
        ediPropertiesEntity.setPartnername("PartnerName");
        ediPropertiesEntity.setPkId("123456");
        ediPropertiesEntity.setAcceptlookalias("Accept");
        ediPropertiesEntity.setAccnongroup("Aconongroup");
        ediPropertiesEntity.setBatchtransaction("BatchTransactiom");
        ediPropertiesEntity.setAckdetails("Ackdetails");
        ediPropertiesEntity.setBusinessprocess("BusinessProcess");
        return ediPropertiesEntity;
    }

    EdiPropertiesEntity getEdiPropertiesEntity1() {
        EdiPropertiesEntity ediPropertiesEntity = new EdiPropertiesEntity();
        ediPropertiesEntity.setPartnername("PartnerName");
        ediPropertiesEntity.setPkId("123456");
        ediPropertiesEntity.setAcceptlookalias("Accept");
        ediPropertiesEntity.setAccnongroup("Aconongroup");
        ediPropertiesEntity.setBatchtransaction("BatchTransactiom");
        ediPropertiesEntity.setAckdetails("Ackdetails");
        ediPropertiesEntity.setBusinessprocess("BusinessProcess");
        return ediPropertiesEntity;
    }

    Page<EdiPropertiesEntity> generateList() {
        List<EdiPropertiesEntity> ediPropertiesEntity = new ArrayList<>();
        Page<EdiPropertiesEntity> pagedtasks = new PageImpl<>(ediPropertiesEntity);
        ediPropertiesEntity.add(getEdiPropertiesEntity());
        ediPropertiesEntity.add(getEdiPropertiesEntity1());
        return pagedtasks;
    }

    EdiPropertiesActivityHistoryEntity getEdiPropertiesActivityHistoryEntity() {
        EdiPropertiesActivityHistoryEntity ediPropertiesActivityHistoryEntity = new EdiPropertiesActivityHistoryEntity();
        ediPropertiesActivityHistoryEntity.setActivity("Edi Activity");
        ediPropertiesActivityHistoryEntity.setPkId("123456");
        ediPropertiesActivityHistoryEntity.setTpRefId("TpRef");
        ediPropertiesActivityHistoryEntity.setUserId("UserId");
        ediPropertiesActivityHistoryEntity.setUserName("UserName");
        ediPropertiesActivityHistoryEntity.setActivityDt(Timestamp.valueOf("2020-03-31 05:43:36"));
        return ediPropertiesActivityHistoryEntity;
    }

    Page<EdiPropertiesActivityHistoryEntity> getEdiPropertiesActivityHistoryEntities() {
        List<EdiPropertiesActivityHistoryEntity> ediPropertiesActivityHistoryEntities = new ArrayList<>();
        ediPropertiesActivityHistoryEntities.add(getEdiPropertiesActivityHistoryEntity());
        return new PageImpl<>(ediPropertiesActivityHistoryEntities);
    }

    PartnerEntity getPartnerEntity() {
        PartnerEntity partnerEntity = new PartnerEntity();
        partnerEntity.setTpName("ProfileName");
        partnerEntity.setTpId("123456");
        partnerEntity.setAddressLine1("AddressLine1");
        partnerEntity.setAddressLine2("AddressLine2");
        partnerEntity.setEmail("EmailId@email.com");
        partnerEntity.setPhone("9876543210");
        partnerEntity.setTpProtocol("FTP");
        partnerEntity.setTpPickupFiles("N");
        partnerEntity.setFileTpServer("N");
        partnerEntity.setIsProtocolHubInfo("N");
        partnerEntity.setStatus("N");
        return partnerEntity;
    }

    Predicate getCriteriaBuilder() {
        System.out.println(generateEnvelopeModel().getEdiProperties().getPartnerName());
        CriteriaBuilder criteriaBuilderMock = Mockito.mock(CriteriaBuilder.class);
        Root rootMock = Mockito.mock(Root.class);
        List<Predicate> predicates = new ArrayList<>();
        getPredicate(rootMock, criteriaBuilderMock, predicates, generateEnvelopeModel().getEdiProperties().getPartnerName(), "partnername", TRUE);
           /* getPredicate(rootMock, criteriaBuilderMock, predicates, generateEnvelopeModel().getEdiProperties().getDirection(), "direction", TRUE);
            getPredicate(rootMock, criteriaBuilderMock, predicates, generateEnvelopeModel().getIsaSegment().getInterVersion(), "interversion", TRUE);
            getPredicate(rootMock, criteriaBuilderMock, predicates, generateEnvelopeModel().getStSegment().getAcceptLookAlias(), "acceptlookalias", TRUE);
            getPredicate(rootMock, criteriaBuilderMock, predicates, generateEnvelopeModel().getIsaSegment().getIsaSenderId(), "isasenderid", TRUE);
            getPredicate(rootMock, criteriaBuilderMock, predicates, generateEnvelopeModel().getIsaSegment().getIsaReceiverId(), "isareceiverid", TRUE);
            getPredicate(rootMock, criteriaBuilderMock, predicates, generateEnvelopeModel().getGsSegment().getGsSenderId(), "gssenderid", TRUE);
            getPredicate(rootMock, criteriaBuilderMock, predicates, generateEnvelopeModel().getGsSegment().getGsReceiverId(), "gsreceiverid", TRUE);
            getPredicate(rootMock, criteriaBuilderMock, predicates, generateEnvelopeModel().getStSegment().getStSenderId(), "stsenderid", TRUE);
            getPredicate(rootMock, criteriaBuilderMock, predicates, generateEnvelopeModel().getStSegment().getStReceiverId(), "streceiverid", TRUE);*/
        return criteriaBuilderMock.and(predicates.toArray(new Predicate[0]));
    }
}
