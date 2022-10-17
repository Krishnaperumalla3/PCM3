package com.pe.pcm.settings;


import com.pe.pcm.correlation.CorrelationModel;
import com.pe.pcm.settings.entity.CorrelationEntity;
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
public class CorrelationServiceTest {
    @MockBean
    private CorrelationRepository correlationRepository;

    //@InjectMocks
    private CorrelationService correlationService;

    @BeforeEach
    void inIt() {
        correlationService = new CorrelationService(correlationRepository);
    }


    @Test
    @DisplayName("Update Correlation Entity")
    public void testUpdate() {
        Mockito.when(correlationRepository.save(Mockito.any())).thenReturn(getCorrelationEntity());
        correlationService.update(generateCorrelationModel());
        Mockito.verify(correlationRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Get Correlations")
    public void test_getCorrelations() {
        correlationService.getCorrelations();
        Mockito.verify(correlationRepository, Mockito.times(1)).findFirstByPkIdIsNotNull();
    }

    CorrelationModel generateCorrelationModel() {
        CorrelationModel correlationModel = new CorrelationModel();
        correlationModel.setCorrelationName1("1");
        correlationModel.setCorrelationName1("2");
        correlationModel.setCorrelationName3("3");
        correlationModel.setCorrelationName4("4");
        correlationModel.setCorrelationName5("5");
        return correlationModel;
    }

    CorrelationEntity getCorrelationEntity() {
        CorrelationEntity correlationEntity = new CorrelationEntity();
        correlationEntity.setPkId("1")
                .setCorrelationName1("2")
                .setCorrelationName2("3")
                .setCorrelationName3("4")
                .setCorrelationName4("5")
                .setCorrelationName5("6")
                .setCorrelationName6("7")
                .setCorrelationName7("8")
                .setCorrelationName8("9")
                .setCorrelationName9("10")
                .setCorrelationName10("11")
                .setCorrelationName11("12")
                .setCorrelationName12("13")
                .setCorrelationName13("14")
                .setCorrelationName14("15")
                .setCorrelationName15("16")
                .setCorrelationName16("17")
                .setCorrelationName17("18")
                .setCorrelationName18("19")
                .setCorrelationName19("20")
                .setCorrelationName20("21")
                .setCorrelationName21("22")
                .setCorrelationName22("23")
                .setCorrelationName23("24")
                .setCorrelationName24("25")
                .setCorrelationName25("26");
        return correlationEntity;
    }


}
