package com.pe.pcm.partner;

import com.pe.pcm.protocol.as2.si.As2TradepartInfoRepository;
import com.pe.pcm.protocol.as2.si.SciContractExtnRepository;
import com.pe.pcm.protocol.as2.si.SciContractRepository;
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

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class SciContractEntityServiceTest {

    @Mock
    private SciContractRepository sciContractRepository;
    @Mock
    private SciContractExtnRepository sciContractExtnRepository;
    @Mock
    private As2TradepartInfoRepository as2TradepartInfoRepository;
    //@InjectMocks
    private SciContractService sciContractService;

    @BeforeEach
    void inIt() {
        sciContractService = new SciContractService(sciContractRepository,sciContractExtnRepository,
                as2TradepartInfoRepository);
    }

    @Test
    @DisplayName("Save SciContractEntity")
    public void testSave() {
        sciContractService.save("SciCon", "ObjectId", "WorkflowName", "ObjectName", "PartnerProfile", "OrgProfile", "ContractKey");
        Mockito.verify(sciContractRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Save SciContractEntity Extn")
    public void testSaveSciContractExtn() {
        sciContractService.saveExtn("SciConExtn", "SciCon", "ObjectId", "ObjectName", "Value", "ContractId");
        Mockito.verify(sciContractExtnRepository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    @DisplayName("Save Trade Part Info")
    public void testSaveTradePartInfo() {
        sciContractService.saveTradePartInfo("OrgProfile", "OrganizationName", "PartnerProfile", "SciCon", "PartnerName", "As2Tpart");
        Mockito.verify(as2TradepartInfoRepository, Mockito.times(1)).save(Mockito.any());
    }
}
