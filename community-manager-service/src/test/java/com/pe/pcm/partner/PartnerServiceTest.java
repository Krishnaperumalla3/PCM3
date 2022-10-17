package com.pe.pcm.partner;

import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.group.GroupService;
import com.pe.pcm.miscellaneous.UserUtilityService;
import com.pe.pcm.partner.entity.PartnerEntity;
import com.pe.pcm.profile.ProfileModel;
import com.pe.pcm.user.TpUserRepository;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class PartnerServiceTest {

    @Mock
    private PartnerRepository partnerRepository;
    @Mock
    private TpUserRepository tpUserRepository;
    @Mock
    private GroupService groupService;
    @Mock
    private UserUtilityService userUtilityService;
    //@InjectMocks
    private PartnerService partnerService;

    @BeforeEach
    void init() {
        partnerService = new PartnerService(partnerRepository, tpUserRepository, groupService, userUtilityService);
    }


    @Test
    @DisplayName("Create a Partner")
    public void testCreate() {
        partnerService.save(generateProfileModel("FTP"), "123456", "654321");
        Mockito.verify(partnerRepository, Mockito.times(1)).save(Mockito.any(PartnerEntity.class));
    }

    @Test
    @DisplayName("Find a Partner")
    public void testFind() {
        Mockito.when(partnerRepository.findByTpId(Mockito.anyString())).thenReturn(Optional.ofNullable(getPartnerEntity()));
        final String pkId = "123456";
        Optional<PartnerEntity> partnerEntity = partnerService.find(pkId);
        Mockito.verify(partnerRepository, Mockito.times(1)).findByTpId(Mockito.anyString());
        Assertions.assertEquals(partnerEntity.get().getTpId(), pkId);
        Assertions.assertEquals(partnerEntity.get().getEmail(), "EmailId@email.com");
    }


    @Test
    @DisplayName("Get Partner")
    public void testGetPartner1() {
        Mockito.when(partnerRepository.findById(Mockito.anyString())).thenReturn(Optional.of(getPartnerEntity()));
        final String pkId = "123456";
        PartnerEntity partnerEntity = partnerService.get(pkId);
        Mockito.verify(partnerRepository, Mockito.times(1)).findById(Mockito.anyString());
        Assertions.assertEquals(partnerEntity.getTpId(), pkId);
        Assertions.assertEquals(partnerEntity.getEmail(), "EmailId@email.com");
    }

    @Test
    @DisplayName("Get non-exist Partner")
    public void testGetPartner2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerRepository.findById(Mockito.anyString())).thenReturn(Optional.ofNullable(null));
            final String pkId = "123456";
            partnerService.get(pkId);
            Mockito.verify(partnerRepository, Mockito.times(1)).findById(Mockito.anyString());
        });
    }

//    @Test
//    @DisplayName("Find First By PartnerName")
//    public void testfindFirstByPartnerName() {
//        Mockito.when(partnerRepository.findFirstByTpName(Mockito.anyString())).thenReturn(Optional.ofNullable(getPartnerEntity()));
//        final String tpName = "ProfileName";
//        Optional<PartnerEntity> partnerEntity = partnerService.findFirstByPartnerName(tpName);
//        Mockito.verify(partnerRepository, Mockito.times(1)).findFirstByTpName(Mockito.anyString());
//        Assertions.assertEquals(partnerEntity.get().getTpName(), tpName);
//        Assertions.assertEquals(partnerEntity.get().getEmail(), "EmailId@email.com");
//    }


    @Test
    @DisplayName("Get Not-Unique Partner")
    public void testGetUniquePartner() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerRepository.findByTpName(Mockito.anyString())).thenReturn(Optional.ofNullable(generateList()));
            final String tpName = "ProfileName";
            partnerService.getUniquePartner(tpName);
        });
        Mockito.verify(partnerRepository, Mockito.times(1)).findByTpName(Mockito.anyString());
    }

    @Test
    @DisplayName("Get Unique Partner with null")
    public void testGetUniquePartner1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerRepository.findByTpName(Mockito.anyString())).thenReturn(Optional.ofNullable(null));
            final String tpName = "ProfileName";
            partnerService.getUniquePartner(tpName);
        });
        Mockito.verify(partnerRepository, Mockito.times(1)).findByTpName(Mockito.anyString());
    }

    @Test
    @DisplayName("Get Unique Partner")
    public void testGetUniquePartner2() {
        Mockito.when(partnerRepository.findByTpName(Mockito.anyString())).thenReturn(Optional.ofNullable(generateList1()));
        final String tpName = "ProfileName";
        PartnerEntity partnerEntity = partnerService.getUniquePartner(tpName);
        Mockito.verify(partnerRepository, Mockito.times(1)).findByTpName(Mockito.anyString());
        Assertions.assertEquals(partnerEntity.getTpName(), tpName);
        Assertions.assertEquals(partnerEntity.getEmail(), "EmailId@email.com");
    }

    @Test
    @DisplayName("Delete Partner")
    public void testDeletePartner() {
        partnerService.delete(getPartnerEntity());
        Mockito.verify(partnerRepository, Mockito.times(1)).delete(Mockito.any(PartnerEntity.class));
    }

    @Test
    @DisplayName("Find All Partner Profiles")
    public void testFindAllPartnerProfiles() {
        Mockito.when(partnerRepository.findAllByOrderByTpNameAsc()).thenReturn(Optional.of(generateList()));
        partnerService.findAllPartnerProfiles();
        Mockito.verify(partnerRepository, Mockito.times(1)).findAllByOrderByTpNameAsc();
    }

    @Test
    @DisplayName("Get All Template Profiles")
    public void testGetAllTemplateProfiles() {
        Mockito.when(partnerRepository.findAllByTpNameContainingIgnoreCaseOrderByTpName("template")).thenReturn(Optional.of(generateList()));
        /*List<PartnerEntity> partnerEntityList = */
        partnerService.getAllTemplateProfiles();
        Mockito.verify(partnerRepository, Mockito.times(1)).findAllByTpNameContainingIgnoreCaseOrderByTpName("template");
        // Assertions.assertEquals(partnerEntityList.get(3).getEmail(),"template@email.com"); need to ask
    }


    @Test
    @DisplayName("Find By Partner Name")
    public void testFindByPartnerName() {
        Mockito.when(partnerRepository.findByTpName(Mockito.anyString())).thenReturn(Optional.ofNullable(generateList1()));
        List<PartnerEntity> partnerEntity = partnerService.findByPartnerName("ProfileName");
        Mockito.verify(partnerRepository, Mockito.times(1)).findByTpName(Mockito.anyString());
        Assertions.assertEquals(partnerEntity.get(0).getEmail(), "EmailId@email.com");
    }

    @Test
    @DisplayName("Find Not Existing Partner By Name")
    public void testFindByPartnerName1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerRepository.findByTpName(Mockito.anyString())).thenReturn(Optional.ofNullable(null));
            partnerService.findByPartnerName("ProfileName");
        });
        Mockito.verify(partnerRepository, Mockito.times(1)).findByTpName(Mockito.anyString());
    }

    @Test
    @DisplayName("Find By PartnerName And PartnerId")
    public void testFindByPartnerNameAndPartnerId() {
        Mockito.when(partnerRepository.findAllByTpNameAndTpId(Mockito.anyString(), Mockito.anyString())).thenReturn(Optional.ofNullable(generateList1()));
        List<PartnerEntity> partnerEntity = partnerService.findByPartnerNameAndPartnerId("ProfileName", "123456");
        Mockito.verify(partnerRepository, Mockito.times(1)).findAllByTpNameAndTpId(Mockito.anyString(), Mockito.anyString());
        Assertions.assertEquals(partnerEntity.get(0).getEmail(), "EmailId@email.com");
    }

    @Test
    @DisplayName("Find  Not Existing partner By PartnerName And PartnerId")
    public void testFindByPartnerNameAndPartnerId1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(partnerRepository.findAllByTpNameAndTpId(Mockito.anyString(), Mockito.anyString())).thenReturn(Optional.ofNullable(null));
            partnerService.findByPartnerNameAndPartnerId("ProfileName", "123456");
        });
        Mockito.verify(partnerRepository, Mockito.times(1)).findAllByTpNameAndTpId(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    @DisplayName("Find All Partners Ids In")
    public void testFindAllPartnersIdsIn() {
        Mockito.when(partnerRepository.findAllByPkIdIn(Mockito.anyList())).thenReturn(Optional.ofNullable(generateList()));
        partnerService.findAllByPkIdIn(Mockito.anyList());
        Mockito.verify(partnerRepository, Mockito.times(1)).findAllByPkIdIn(Mockito.anyList());
    }


    ProfileModel generateProfileModel(String protocol) {
        ProfileModel profileModel = new ProfileModel();
        profileModel.setPkId("123456");
        profileModel.setProfileName("ProfileName");
        profileModel.setProfileId("123456");
        profileModel.setAddressLine1("AddressLine1");
        profileModel.setAddressLine2("AddressLine2");
        profileModel.setEmailId("EmailId@email.com");
        profileModel.setPhone("9876543210");
        profileModel.setProtocol(protocol);
        profileModel.setHubInfo(false);
        profileModel.setStatus(false);
        return profileModel;
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

    PartnerEntity getPartnerEntity1() {
        PartnerEntity partnerEntity = new PartnerEntity();
        partnerEntity.setTpName("template");
        partnerEntity.setTpId("123456");
        partnerEntity.setAddressLine1("AddressLine1");
        partnerEntity.setAddressLine2("AddressLine2");
        partnerEntity.setEmail("EmailId@email.com");
        partnerEntity.setPhone("1234567890");
        partnerEntity.setTpProtocol("FTP");
        partnerEntity.setTpPickupFiles("N");
        partnerEntity.setFileTpServer("N");
        partnerEntity.setIsProtocolHubInfo("N");
        partnerEntity.setStatus("N");
        return partnerEntity;

    }

    PartnerEntity getPartnerEntity2() {
        PartnerEntity partnerEntity = new PartnerEntity();
        partnerEntity.setTpName("template");
        partnerEntity.setTpId("123456");
        partnerEntity.setAddressLine1("AddressLine1");
        partnerEntity.setAddressLine2("AddressLine2");
        partnerEntity.setEmail("template@email.com");
        partnerEntity.setPhone("9876543210");
        partnerEntity.setTpProtocol("FTP");
        partnerEntity.setTpPickupFiles("N");
        partnerEntity.setFileTpServer("N");
        partnerEntity.setIsProtocolHubInfo("N");
        partnerEntity.setStatus("N");
        return partnerEntity;

    }

    List<PartnerEntity> generateList() {
        List<PartnerEntity> li = new ArrayList<>();
        li.add(getPartnerEntity());
        li.add(getPartnerEntity());
        li.add(getPartnerEntity1());
        li.add(getPartnerEntity2());
        return li;
    }

    List<PartnerEntity> generateList1() {
        List<PartnerEntity> li = new ArrayList<>();
        li.add(getPartnerEntity());
        return li;
    }

    Page<PartnerEntity> generateList2() {
        List<PartnerEntity> li = new ArrayList<>();
        Page<PartnerEntity> pagedTasks = new PageImpl<>(li);
        li.add(getPartnerEntity());
        return pagedTasks;
    }

}
