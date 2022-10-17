package com.pe.pcm.application;

import com.pe.pcm.application.entity.ApplicationEntity;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.miscellaneous.CommonQueryPredicate;
import com.pe.pcm.profile.ProfileModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;
import static com.pe.pcm.miscellaneous.CommonQueryPredicate.getPredicate;
import static com.pe.pcm.utils.CommonFunctions.convertBooleanToString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class ApplicationServiceTest {
    @Mock
    private ApplicationRepository applicationRepository;
    @Mock
    private CommonQueryPredicate commonQueryPredicate;
    //@InjectMocks
    private ApplicationService applicationService;

    @BeforeEach
    void inIt() {
        applicationService = new ApplicationService(applicationRepository);
    }

    @Test
    @DisplayName("Create ApplicationService ")
    void testCreate() {
        applicationService.save(getProfileModel(), "123456", "654321");
        verify(applicationRepository, times(1)).save(any(ApplicationEntity.class));
    }

    @Test
    @DisplayName("Get ApplicationService2")
    void testGet2() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            when(applicationRepository.findById(anyString())).thenThrow(notFound("Application"));
            applicationService.get(anyString());
        });
        verify(applicationRepository, times(1)).findById(anyString());
    }

    @Test
    @DisplayName("Get ApplicationService1")
    void testGet1() {
        when(applicationRepository.findById(anyString())).thenReturn(Optional.ofNullable(getApplicationEntity()));
        final String pkId = "123456";
        applicationService.get(pkId);
        verify(applicationRepository, times(1)).findById(anyString());
    }

    @Test
    @DisplayName("Delete ApplicationService")
    void testDelete() {
        applicationService.delete(getApplicationEntity());
        verify(applicationRepository, times(1)).delete(any());
    }

    @Test
    @DisplayName("Get All Templates")
    void testGetAll() {
        when(applicationRepository.findAllByApplicationNameContainingIgnoreCaseOrderByApplicationName(anyString())).thenReturn(Optional.of(anyList()));
        applicationService.getAllTemplateApplicationProfiles();
        verify(applicationRepository, times(1)).findAllByApplicationNameContainingIgnoreCaseOrderByApplicationName(anyString());
    }

    @Test
    @DisplayName("Search By Application Name")
    void testSearchByApplicationName() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            when(applicationRepository.findByApplicationName(anyString())).thenThrow(notFound("Application"));
            applicationService.searchByApplicationName(anyString());
        });
        verify(applicationRepository, times(1)).findByApplicationName(anyString());
    }

    @Test
    @DisplayName("Find All Application Profiles")
    void testFindAllApplicationProfiles() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            when(applicationRepository.findAllByOrderByApplicationNameAsc()).thenThrow(notFound("Application"));
            applicationService.findAllApplicationProfiles();
            verify(applicationRepository, times(1)).findAllByOrderByApplicationNameAsc();
        });
    }

    @Test
    @DisplayName("Find All By  Protocol")
    void testFinaAllByProtocol() {
        when(applicationRepository.findAllByAppIntegrationProtocolOrderByApplicationName("Application")).thenReturn(Optional.of(anyList()));
        applicationService.finaAllByProtocol("Application");
        verify(applicationRepository, times(1)).findAllByAppIntegrationProtocolOrderByApplicationName(any());
    }

    @Test
    @DisplayName("Get Unique Application")
    void testGetUniqueApplication() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
//            when(applicationRepository.findByApplicationName("ApplicationName")).thenThrow(notFound("Application"));
            applicationService.getUniqueApplication(anyString());
        });
        verify(applicationRepository, times(1)).findByApplicationName(anyString());
    }

    @Test
    @DisplayName("Get Unique Application1")
    void testGetUniqueApplication1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            when(applicationRepository.findByApplicationName(anyString())).thenReturn(Optional.empty());
            applicationService.getUniqueApplication("Application");
        });
        verify(applicationRepository, times(1)).findByApplicationName(anyString());
    }

    @Test
    @DisplayName("Get Unique Application")
    void testGetUniqueApplication2() {
        when(applicationRepository.findByApplicationName(anyString())).thenReturn(Optional.of(getApplicationEntities()));
        applicationService.getUniqueApplication("Application");
        verify(applicationRepository, times(1)).findByApplicationName(anyString());
    }

    @Test
    @DisplayName("Get Unique Application with multiple records")
    void testGetUniqueApplication3() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            when(applicationRepository.findByApplicationName(anyString())).thenReturn(Optional.of(getApplicationEntities1()));
            applicationService.getUniqueApplication("Application");
        });
        verify(applicationRepository, times(1)).findByApplicationName(anyString());
        Assertions.assertEquals(CommunityManagerServiceException.class, CommunityManagerServiceException.class);
    }


    @Test
    @DisplayName(value = "Find By Application Id")
    void testFindApplication() {
        when(applicationRepository.findByApplicationId(anyString())).thenReturn(getOptionalApplicationEntity());
        applicationService.find(anyString());
        verify(applicationRepository, times(1)).findByApplicationId(anyString());
    }

    @Test
    @DisplayName(value = "Get No Throw Application with returning Application")
    void testGetNoThrow() {
        when(applicationRepository.findById(anyString())).thenReturn(getOptionalApplicationEntity());
        applicationService.getNoThrow(anyString());
        verify(applicationRepository, times(1)).findById(anyString());
    }

    @Test
    @DisplayName(value = "Get No Throw Application with returning Empty Obj")
    void testGetNoThrow1() {
        when(applicationRepository.findById(anyString())).thenReturn(Optional.of(new ApplicationEntity()));
        applicationService.getNoThrow(anyString());
        verify(applicationRepository, times(1)).findById(anyString());
    }

    @Test
    @DisplayName(value = "Search Application")
    void testSearchApplication() {
        when(applicationRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(generateApplicationEntityList());
        applicationService.search(getProfileModel(), Pageable.unpaged());
        verify(applicationRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }


    //@Test
    @DisplayName(value = "Search Application")
    void testSearchApplication1() {
        when(applicationRepository.findAll((Specification<ApplicationEntity>) (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            getPredicate(root, cb, predicates, getProfileModel().getProfileName(), "applicationName", true);
            getPredicate(root, cb, predicates, getProfileModel().getProfileId(), "applicationId", true);
            getPredicate(root, cb, predicates, getProfileModel().getProtocol(), "appIntegrationProtocol", false);
            getPredicate(root, cb, predicates, getProfileModel().getStatus() ? convertBooleanToString(getProfileModel().getStatus()) : "", "appIsActive", false);
            return cb.and(predicates.toArray(new Predicate[0]));
        }, Pageable.unpaged())).thenReturn(getPageApplicationEntities());
        applicationService.search(getProfileModel(), Pageable.unpaged());
        verify(applicationRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }


    ProfileModel getProfileModel() {
        ProfileModel profileModel = new ProfileModel();
        profileModel.setPkId("123456");
        profileModel.setAddressLine1("Address1");
        profileModel.setAddressLine2("Address2");
        profileModel.setEmailId("Email@2020.com");
        profileModel.setPhone("7657898763");
        profileModel.setHubInfo(false);
        profileModel.setProfileId("ProfileId");
        profileModel.setProfileName("ProfileName");
        profileModel.setProtocol("FTP");
        profileModel.setStatus(true);
        return profileModel;
    }

    ApplicationEntity getApplicationEntity() {
        ApplicationEntity applicationEntity = new ApplicationEntity();
        applicationEntity.setAppDropFiles("AppDropfiles");
        applicationEntity.setAppIntegrationProtocol("AppIntegrationProtocol");
        applicationEntity.setAppIsActive("AppIsActive");
        applicationEntity.setApplicationName("ApplicationName");
        applicationEntity.setApplicationId("ApplicationId");
        applicationEntity.setAppPickupFiles("AppPickupFiles");
        applicationEntity.setAppProtocolRef("AppProtocolRef");
        applicationEntity.setEmailId("Email@e.com");
        applicationEntity.setPhone("7598008944");
        applicationEntity.setPkId("123456");
        applicationEntity.setAppIntegrationProtocol("Application");
        return applicationEntity;
    }

    Page<ApplicationEntity> generateApplicationEntityList() {
        List<ApplicationEntity> li = new ArrayList<>();
        li.add(getApplicationEntity());
        return new PageImpl<>(li);
    }

    Page<ApplicationEntity> getPageApplicationEntities() {
        Page<ApplicationEntity> applicationEntities = null;
        return applicationEntities;
    }

    List<ApplicationEntity> getApplicationEntities() {
        List<ApplicationEntity> li = new ArrayList<>();
        li.add(getApplicationEntity());
        return li;
    }

    List<ApplicationEntity> getApplicationEntities1() {
        List<ApplicationEntity> li = new ArrayList<>();
        li.add(getApplicationEntity());
        li.add(getApplicationEntity());
        return li;
    }


    Optional<ApplicationEntity> getOptionalApplicationEntity() {
        return Optional.of(getApplicationEntity());
    }
}
