package com.pe.pcm.protocol.as2;

import com.pe.pcm.b2b.B2BRemoteAS2Service;
import com.pe.pcm.certificate.CaCertInfoService;
import com.pe.pcm.certificate.CertsAndPriKeyService;
import com.pe.pcm.certificate.TrustedCertInfoService;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.partner.entity.PartnerEntity;
import com.pe.pcm.protocol.As2Model;
import com.pe.pcm.protocol.as2.entity.As2Entity;
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
public class As2ServiceTest {
    @MockBean
    private As2Repository as2Repository;
    @Mock
    private B2BRemoteAS2Service b2BRemoteAS2Service;
    @Mock
    private CertsAndPriKeyService certsAndPriKeyService;
    @Mock
    private TrustedCertInfoService trustedCertInfoService;
    @Mock
    private CaCertInfoService caCertInfoService;
    //@InjectMocks
    private As2Service as2Service;

    @BeforeEach
    void inIt() {
        as2Service = new As2Service(as2Repository,b2BRemoteAS2Service,certsAndPriKeyService,trustedCertInfoService,
                caCertInfoService);
    }

    @Test
    @DisplayName("Save AS2 Service")
    public void testCreate() {
        as2Service.save("PrimaryKey", "ChildPrimaryKey","TP" , generateAs2Model(),"");
        Mockito.verify(as2Repository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Get AS2 Service")
    public void testGet() {
        Mockito.when(as2Repository.findBySubscriberId(Mockito.anyString())).thenReturn(Optional.of(getPetpeAs2()));
        String subscriberId = "123456";
        as2Service.get(subscriberId);
        Mockito.verify(as2Repository, Mockito.times(1)).findBySubscriberId(Mockito.anyString());
    }

    @Test
    @DisplayName("Get AS2 Service1")
    public void testGet1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(as2Repository.findBySubscriberId(Mockito.any())).thenThrow(notFound("protocol"));
            String subscriberId = "123456";
            as2Service.get(subscriberId);
        });
        Mockito.verify(as2Repository, Mockito.times(1)).findBySubscriberId(Mockito.any());
    }

    @Test
    @DisplayName("Delete AS2 Service")
    public void testDelete() {
        String subscriberId = "123456";
        Mockito.when(as2Repository.findBySubscriberId(Mockito.anyString())).thenReturn(Optional.of(getPetpeAs2()));
        as2Service.delete(subscriberId);
        Mockito.verify(as2Repository, Mockito.times(1)).findBySubscriberId(Mockito.anyString());
        Mockito.verify(as2Repository, Mockito.times(1)).delete(Mockito.any());
    }

    As2Model generateAs2Model() {
        As2Model as2Model = new As2Model();
        as2Model.setPassword("password");
        as2Model.setProtocol("protocol");
        as2Model.setPkId("123456");
        as2Model.setEmailId("Email@email.com");
        as2Model.setUsername("UserName");
        as2Model.setPhone("9143474564");
        as2Model.setProfileId("ProfileId");
        as2Model.setProfileName("ProfileName");
        as2Model.setStatus(false);
        as2Model.setHubInfo(false);
        as2Model.setMdn(false);
        as2Model.setCompressData("Y");
        return as2Model;
    }

    As2Entity getPetpeAs2() {
        As2Entity as2Entity = new As2Entity();
        as2Entity.setPkId("123456");
        as2Entity.setIsActive("N");
        as2Entity.setSubscriberId("SubscriberId");
        as2Entity.setIsHubInfo("F");
        as2Entity.setIsMdnRequired("F");
        return as2Entity;
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


}
