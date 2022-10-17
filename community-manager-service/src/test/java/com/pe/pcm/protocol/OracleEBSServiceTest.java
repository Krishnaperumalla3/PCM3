package com.pe.pcm.protocol;

import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.protocol.oracleebs.OracleEBSRepository;
import com.pe.pcm.protocol.oracleebs.entity.OracleEbsEntity;
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

import java.util.Optional;

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;


@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class OracleEBSServiceTest {
    @MockBean
    private OracleEBSRepository oracleEBSRepository;
    @InjectMocks
    private OracleEbsService oracleEBSService;

    @Test
    @DisplayName("Save Oracle EBS Entity")
    public void testSave() {
        oracleEBSService.save(getOracleEbsEntity());
        Mockito.verify(oracleEBSRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Get Oracle EBS Entity with a valid data")
    public void testGet() {
        Mockito.when(oracleEBSRepository.findBySubscriberId(Mockito.anyString())).thenReturn(Optional.of(getOracleEbsEntity()));
        oracleEBSService.get("11123");
        Mockito.verify(oracleEBSRepository, Mockito.times(1)).findBySubscriberId(Mockito.anyString());
    }

    @Test
    @DisplayName("Get  Oracle EBS Entity ")
    public void testGet1() {
        Assertions.assertThrows(CommunityManagerServiceException.class, () -> {
            Mockito.when(oracleEBSRepository.findBySubscriberId(Mockito.anyString())).thenThrow(notFound("Protocol"));
            oracleEBSService.get(Mockito.anyString());
        });
        Mockito.verify(oracleEBSRepository, Mockito.times(1)).findBySubscriberId(Mockito.anyString());
    }

    @Test
    @DisplayName("Delete Oracle EBS Entity")
    public void testDelete() {
        Mockito.when(oracleEBSRepository.findBySubscriberId(Mockito.anyString())).thenReturn(Optional.of(getOracleEbsEntity()));
        oracleEBSService.delete("1234");
        Mockito.verify(oracleEBSRepository, Mockito.times(1)).delete(Mockito.any());
    }

    @Test
    @DisplayName("Save Protocol Oracle EBS Entity")
    public void testSaveProtocol() {
        oracleEBSService.saveProtocol(generateOracleEBSModel("ORACLE_EBS"), "1", "2", "sub");
        Mockito.verify(oracleEBSRepository, Mockito.times(1)).save(Mockito.any());
    }


    OracleEbsEntity getOracleEbsEntity() {
        OracleEbsEntity oracleEBSEntity = new OracleEbsEntity();
        oracleEBSEntity.setBpRecMsgs("BpRecMsgs");
        oracleEBSEntity.setRequestType("RequestType");
        oracleEBSEntity.setName("Name");
        oracleEBSEntity.setPassword("password");
        oracleEBSEntity.setPkId("123456");
        oracleEBSEntity.setProtocol("protocol");
        return oracleEBSEntity;
    }

    OracleEbsModel generateOracleEBSModel(String protocol) {
        OracleEbsModel oracleEBSModel = new OracleEbsModel();
        oracleEBSModel.setName("Name");
        oracleEBSModel.setPassword("password");
        oracleEBSModel.setOrProtocol(protocol);
        oracleEBSModel.setNameBod("nameBod");
        return oracleEBSModel;
    }


}
