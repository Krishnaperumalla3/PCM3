package com.pe.pcm.protocol.as2;


import com.pe.pcm.certificate.entity.CertsAndPriKeyEntity;
import com.pe.pcm.protocol.As2Model;
import com.pe.pcm.protocol.as2.certificate.SciDocxKeyCertService;
import com.pe.pcm.protocol.as2.si.certificate.SciDocxKeyCertRepository;
import com.pe.pcm.protocol.as2.si.certificate.entity.SciDocxKeyCertEntity;
import org.junit.jupiter.api.BeforeEach;
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

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class SciDocxKeyCertServiceTest {
    @MockBean
    private SciDocxKeyCertRepository sciDocxKeyCertRepository;
    //@InjectMocks
    private SciDocxKeyCertService sciDocxKeyCertService;

    @BeforeEach
    void inIt () {
        sciDocxKeyCertService = new SciDocxKeyCertService(sciDocxKeyCertRepository);
    }

    @Test
    @DisplayName("Save Doc Exchange Key Certificate Repository")
    public void testSave() {
        sciDocxKeyCertService.save("transportObjectId", generateCertsAndPriKeyEntity(), null);
        Mockito.verify(sciDocxKeyCertRepository, Mockito.times(1)).save(Mockito.any());
    }


    As2Model generateAs2Model() {
        As2Model as2Model = new As2Model();
        as2Model.setPassword("password");
        as2Model.setProtocol("protocol");
        as2Model.setPkId("123456");
        as2Model.setEmailId("Email@email.com");
        as2Model.setUsername("UserName");
        as2Model.setPhone("9143474564");
        as2Model.setProfileId("123456");
        as2Model.setProfileName("ProfileName");
        as2Model.setStatus(false);
        as2Model.setHubInfo(false);
        as2Model.setMdn(false);
        as2Model.setCompressData("default");
        return as2Model;
    }

    SciDocxKeyCertEntity getSciDocxKeyCert() {
        SciDocxKeyCertEntity sciDocxKeyCertEntity = new SciDocxKeyCertEntity();
        sciDocxKeyCertEntity.setCertStatus(123);
        sciDocxKeyCertEntity.setCertOrder(123456);
        sciDocxKeyCertEntity.setKeyCertId("1");
        sciDocxKeyCertEntity.setLockid(12);
        sciDocxKeyCertEntity.setCreateprogid("Create");
        return sciDocxKeyCertEntity;
    }

    SciDocxKeyCertEntity getSciDocxKeyCert1() {
        SciDocxKeyCertEntity sciDocxKeyCertEntity = new SciDocxKeyCertEntity();
        sciDocxKeyCertEntity.setCertStatus(1234);
        sciDocxKeyCertEntity.setCertOrder(1234567);
        sciDocxKeyCertEntity.setKeyCertId("12");
        sciDocxKeyCertEntity.setLockid(123);
        sciDocxKeyCertEntity.setCreateprogid("Create");
        return sciDocxKeyCertEntity;
    }

    List<SciDocxKeyCertEntity> generateSciDocxKeyCertEntityList() {
        List<SciDocxKeyCertEntity> sciDocxKeyCertEntities = new ArrayList<>();
        sciDocxKeyCertEntities.add(getSciDocxKeyCert());
        sciDocxKeyCertEntities.add(getSciDocxKeyCert1());
        return sciDocxKeyCertEntities;
    }

    CertsAndPriKeyEntity generateCertsAndPriKeyEntity() {
        String afterDate = "2020/02/13";
        String beforeDate = "1993/10/10";
        CertsAndPriKeyEntity certsAndPriKeyEntity = new CertsAndPriKeyEntity();
        certsAndPriKeyEntity.setName("name");
        certsAndPriKeyEntity.setObjectId("123456");
        certsAndPriKeyEntity.setUsername("username");
        certsAndPriKeyEntity.setStatus(1);
//        certsAndPriKeyEntity.setNotAfter(Date.valueOf(afterDate));
//        certsAndPriKeyEntity.setNotBefore(Date.valueOf(beforeDate));
        return certsAndPriKeyEntity;
    }


}
