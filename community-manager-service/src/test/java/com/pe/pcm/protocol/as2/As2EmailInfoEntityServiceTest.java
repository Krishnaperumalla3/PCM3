package com.pe.pcm.protocol.as2;


import com.pe.pcm.protocol.As2Model;
import com.pe.pcm.protocol.as2.si.AS2EmailInfoRepository;
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

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class As2EmailInfoEntityServiceTest {
    @MockBean
    private AS2EmailInfoRepository as2EmailInfoRepository;

    //@InjectMocks
    private AS2EmailInfoService as2EmailInfoService;

    @BeforeEach
    void inIt() {
        as2EmailInfoService = new AS2EmailInfoService(as2EmailInfoRepository);
    }

    @Test
    @DisplayName("Save AS2 Mail Info Service")
    public void testSave() {
        as2EmailInfoService.save("transportObjectId", generateAs2Model());
        Mockito.verify(as2EmailInfoRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Update AS2 Mail Info Service")
    public void testUpdate() {
        as2EmailInfoService.update("transportObjectId", generateAs2Model());
        Mockito.verify(as2EmailInfoRepository, Mockito.never()).save(Mockito.any());
    }

//    @Test
//    @DisplayName("Get AS2 Mail Info Service")
//    public void testGet() {
//        Mockito.when(as2EmailInfoRepository.findById(Mockito.anyString())).thenReturn(Mockito.any());
//        String pkId = "123456";
//        as2EmailInfoService.get(pkId);
//        Mockito.verify(as2EmailInfoRepository, Mockito.times(1)).findById(Mockito.anyString());
//    }

    @Test
    @DisplayName("Delete AS2 Mail Info Service")
    public void testDelete() {
        String transportObjectId = "123456";
        as2EmailInfoService.delete(transportObjectId);
        Mockito.verify(as2EmailInfoRepository, Mockito.times(1)).findById(Mockito.any());
        Mockito.verify(as2EmailInfoRepository, Mockito.never()).delete(Mockito.any());
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
        return as2Model;
    }


}
