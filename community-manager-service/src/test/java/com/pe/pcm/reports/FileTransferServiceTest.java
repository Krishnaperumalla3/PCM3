package com.pe.pcm.reports;

import com.pe.pcm.b2b.B2BApiService;
import com.pe.pcm.b2b.fileprocess.RemoteProcessFlowModel;
import com.pe.pcm.constants.AuthoritiesConstants;
import com.pe.pcm.miscellaneous.IndependentService;
import com.pe.pcm.miscellaneous.UserUtilityService;
import com.pe.pcm.partner.PartnerService;
import com.pe.pcm.reports.entity.TransferInfoEntity;
import com.pe.pcm.settings.FileSchedulerConfigService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class FileTransferServiceTest {
    @MockBean
    private TransferInfoRepository transferInfoRepository;
    @MockBean
    private TransInfoDRepository transInfoDRepository;
    @MockBean
    private B2BApiService b2BApiService;
    @MockBean
    private ReportRepository reportRepository;
    @MockBean
    private UserUtilityService userUtilityService;
    @MockBean
    private IndependentService independentService;
    @MockBean
    private PartnerService partnerService;
    //@InjectMocks
    private FileTransferService fileTransferService;
    @MockBean
    private TransInfoDService transInfoDService;
    @MockBean
    private TransInfoDStagingRepository transInfoDStagingRepository;
    @MockBean
    private FileSchedulerConfigService fileSchedulerConfigService;

    public FileTransferServiceTest() {
    }

    @BeforeEach
    void inIt() {
        fileTransferService = new FileTransferService(transferInfoRepository, transInfoDRepository,
                b2BApiService, reportRepository, independentService, userUtilityService, partnerService, transInfoDService, transInfoDStagingRepository, fileSchedulerConfigService);
    }

    @Test
    @DisplayName("Search Over Due")
    public void test() {
        Mockito.when(userUtilityService.getUserOrRole(Mockito.anyBoolean())).thenReturn(AuthoritiesConstants.SUPER_ADMIN);
        fileTransferService.searchOverDue(getOverDueReportModel(), Pageable.unpaged());
        Mockito.verify(reportRepository, Mockito.times(1)).findAll997OverDues(Mockito.any(), Mockito.any(), Mockito.anyList());
    }

    @Test
    @DisplayName("Search")
    public void test_search() {
        //Pageable pageable = Mockito.mock(Pageable.class);
        Mockito.when(transferInfoRepository.findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class))).thenReturn(generateList());
        fileTransferService.search(generateTransferInfoModel(), Pageable.unpaged(), false);
        Mockito.verify(transferInfoRepository, Mockito.times(1)).findAll(Mockito.any(Specification.class), Mockito.any(Pageable.class));
    }

    @Test
    @DisplayName("Get Activity")
    public void test_getActivity() {
        fileTransferService.getActivity(23456L, "123456");
        Mockito.verify(transInfoDRepository, Mockito.never()).findByBpidOrderBySequenceAsc(Mockito.anyString());
    }

    @Test
    @DisplayName("Restart WorkFlow")
    public void test_restartWorkFlow() {
        Mockito.when(transferInfoRepository.findFirstBySeqid(Mockito.anyLong())).thenReturn(Optional.of(getTransferInfoEntity()));
        b2BApiService.restartWorkFlow(getRemoteProcessFlowModel());
        fileTransferService.restartWorkFlow((long) 12345, (long) 123, "status");
        Mockito.verify(transferInfoRepository, Mockito.times(1)).findFirstBySeqid(Mockito.anyLong());
    }

    @Test
    @DisplayName("Get")
    public void test_get() {
        fileTransferService.get((long) 12345);
        Mockito.verify(transferInfoRepository, Mockito.times(1)).findById(Mockito.anyLong());
    }

    TransferInfoEntity getTransferInfoEntity() {
        TransferInfoEntity transferInfoEntity = new TransferInfoEntity();
        transferInfoEntity.setApplication("Application");
        transferInfoEntity.setAdverrorstatus("Adverrorstatus");
        transferInfoEntity.setCorebpid("corebpid");
        transferInfoEntity.setSeqid(123456L);
        return transferInfoEntity;
    }

    OverDueReportModel getOverDueReportModel() {
        OverDueReportModel overDueReportModel = new OverDueReportModel();
        overDueReportModel.setPartner("partner");
        overDueReportModel.setDocTrans("Documents");
        overDueReportModel.setSenderId("SenderId");
        return overDueReportModel;
    }

    Page<OverDueReportModel> getOverDueReportModels() {
        List<OverDueReportModel> li = new ArrayList<>();
        Page<OverDueReportModel> pagedTasks = new PageImpl<>(li);
        li.add(getOverDueReportModel());
        return pagedTasks;
    }

    TransferInfoModel generateTransferInfoModel() {
        TransferInfoModel transferInfoModel = new TransferInfoModel();
        transferInfoModel.setApplication("Application");
        transferInfoModel.setCorrelationValue1("1");
        transferInfoModel.setCorrelationValue2("2");
        transferInfoModel.setCorrelationValue3("3");
        transferInfoModel.setCorrelationValue4("4");
        transferInfoModel.setCorrelationValue5("5");
        transferInfoModel.setCorrelationValue6("6");
        return transferInfoModel;
    }


    TransferInfoModel generateTransferInfoModel1() {
        TransferInfoModel transferInfoModel = new TransferInfoModel();
        transferInfoModel.setApplication("Application");
        transferInfoModel.setCorrelationValue1("1");
        transferInfoModel.setCorrelationValue2("2");
        transferInfoModel.setCorrelationValue3("3");
        transferInfoModel.setCorrelationValue4("4");
        transferInfoModel.setCorrelationValue5("5");
        transferInfoModel.setCorrelationValue6("6");
        return transferInfoModel;
    }

    Page<TransferInfoEntity> generateList() {
        List<TransferInfoEntity> li = new ArrayList<>();
        Page<TransferInfoEntity> pagedTasks = new PageImpl<>(li);
        li.add(getTransferInfoEntity());
        return pagedTasks;
    }

    List<TransferInfoEntity> generateList2() {
        List<TransferInfoEntity> li = new ArrayList<>();
        li.add(getTransferInfoEntity());
        return li;
    }

    RemoteProcessFlowModel getRemoteProcessFlowModel() {
        RemoteProcessFlowModel remoteProcessFlowModel = new RemoteProcessFlowModel((long) 123);
        remoteProcessFlowModel.setWorkFlowId((long) 1234);
        return remoteProcessFlowModel;
    }
}
