package com.pe.pcm.pem.codelist;

import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.pem.codelist.entity.PemCodeListEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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

public class PemCodeListServiceTest {

    @MockBean
    private PemCodeListRepository pemCodeListRepository;
    @InjectMocks
    private PemCodeListService pemCodeListService;


    @Test
    @DisplayName("Create PEM Code List")
    public void testCreate() {
        Mockito.when(pemCodeListRepository.findByProfileName(Mockito.anyString())).thenReturn(Optional.ofNullable(null));
        pemCodeListService.create(generatePemCodeListModel());
        Mockito.verify(pemCodeListRepository, Mockito.never()).findByProfileName(Mockito.anyString());
    }


    @Test
    @DisplayName("Get PEM Code List")
    public void testGet() {
        Mockito.when(pemCodeListRepository.findById(Mockito.anyString())).thenReturn(Optional.of(getPemCodeListEntity()));
        String pkId = "123456";
        pemCodeListService.get(pkId);
        Mockito.verify(pemCodeListRepository, Mockito.times(1)).findById(Mockito.any());
    }

    @Test
    @DisplayName("Delete PEM Code List with an pkId")
    public void testDelete() {
        Mockito.when(pemCodeListRepository.findById(Mockito.anyString())).thenReturn(Optional.of(getPemCodeListEntity()));
        String pkId = "123456";
        pemCodeListService.delete(pkId);
        Mockito.verify(pemCodeListRepository, Mockito.times(1)).findById(Mockito.anyString());
        Mockito.verify(pemCodeListRepository, Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    @DisplayName("Delete Pem Code List")
    public void testDelete1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(pemCodeListRepository.findById(Mockito.anyString())).thenThrow(notFound("Code List"));
            String pkId = "123456";
            pemCodeListService.delete(pkId);
        });
        Mockito.verify(pemCodeListRepository, Mockito.times(1)).findById(Mockito.anyString());
        Mockito.verify(pemCodeListRepository, Mockito.never()).delete(Mockito.any());
    }

    @Test
    @DisplayName("Find All Pem Code List")
    public void testFindAll() {
        Mockito.when(pemCodeListRepository.findAllByOrderByProfileName()).thenReturn(Optional.of(generateList()));
        pemCodeListService.findAll();
        Mockito.verify(pemCodeListRepository, Mockito.times(1)).findAllByOrderByProfileName();
    }

    @Test
    @DisplayName("Find All By Profiles Not In")
    public void testFindAllByProfilesNotIn() {
        Mockito.when(pemCodeListRepository.findAllByProfileNameNotInOrderByProfileName(Mockito.anyList())).thenReturn(Optional.of(generateList()));
        pemCodeListService.findAllByProfilesNotIn(Mockito.anyList());
        Mockito.verify(pemCodeListRepository, Mockito.times(1)).findAllByProfileNameNotInOrderByProfileName(Mockito.anyList());
    }


    PemCodeListModel generatePemCodeListModel() {
        PemCodeListModel pemCodeListModel = new PemCodeListModel();
        pemCodeListModel.setPkId("123456");
        pemCodeListModel.setProtocol("protocol");
        pemCodeListModel.setCorrelationValue1("1");
        pemCodeListModel.setCorrelationValue2("2");
        pemCodeListModel.setCorrelationValue3("3");
        pemCodeListModel.setCorrelationValue4("4");
        pemCodeListModel.setCorrelationValue6("5");
        return pemCodeListModel;

    }

    PemCodeListEntity getPemCodeListEntity() {
        PemCodeListEntity pemCodeListEntity = new PemCodeListEntity();
        pemCodeListEntity.setCorrelationValue1("1");
        pemCodeListEntity.setCorrelationValue2("2");
        pemCodeListEntity.setCorrelationValue3("3");
        pemCodeListEntity.setCorrelationValue4("4");
        pemCodeListEntity.setPkId("123456");
        pemCodeListEntity.setProtocol("protocol");
        pemCodeListEntity.setProfileName("ProfileName");
        return pemCodeListEntity;
    }


    PemCodeListEntity getPemCodeListEntity1() {
        PemCodeListEntity pemCodeListEntity = new PemCodeListEntity();
        pemCodeListEntity.setCorrelationValue1("1");
        pemCodeListEntity.setCorrelationValue2("2");
        pemCodeListEntity.setCorrelationValue3("3");
        pemCodeListEntity.setCorrelationValue4("4");
        pemCodeListEntity.setPkId("123456");
        pemCodeListEntity.setProtocol("protocol");
        pemCodeListEntity.setProfileName("ProfileName");
        return pemCodeListEntity;
    }

    List<PemCodeListEntity> generateList() {
        List<PemCodeListEntity> pemCodeListEntities = new ArrayList<>();
        pemCodeListEntities.add(getPemCodeListEntity());
        pemCodeListEntities.add(getPemCodeListEntity1());
        return pemCodeListEntities;
    }

}
