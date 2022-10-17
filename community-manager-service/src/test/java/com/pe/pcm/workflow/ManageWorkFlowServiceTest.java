package com.pe.pcm.workflow;

import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.application.ApplicationService;
import com.pe.pcm.application.ManageApplicationService;
import com.pe.pcm.miscellaneous.IndependentService;
import com.pe.pcm.miscellaneous.PasswordUtilityService;
import com.pe.pcm.miscellaneous.UserUtilityService;
import com.pe.pcm.partner.ManagePartnerService;
import com.pe.pcm.partner.PartnerService;
import com.pe.pcm.partner.entity.PartnerEntity;
import com.pe.pcm.reports.FileTransferService;
import com.pe.pcm.reports.ReportRepository;
import com.pe.pcm.reports.entity.TransferInfoEntity;
import com.pe.pcm.rule.RuleService;
import com.pe.pcm.sterling.MapRepository;
import com.pe.pcm.workflow.entity.ProcessEntity;
import com.pe.pcm.workflow.pem.PemFileTypeModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class ManageWorkFlowServiceTest {

    @MockBean
    private ProcessService processService;
    @MockBean
    private ProcessDocsService processDocsService;
    @MockBean
    private ProcessRulesService processRulesService;
    @MockBean
    private PartnerService partnerService;
    @MockBean
    private ApplicationService applicationService;
    @MockBean
    private FileTransferService fileTransferService;
    @MockBean
    private RuleService ruleService;
    @MockBean
    private ManagePartnerService managePartnerService;
    @MockBean
    private ManageApplicationService manageApplicationService;
    @MockBean
    private ActivityHistoryService activityHistoryService;
    @MockBean
    private ReportRepository reportRepository;
    @MockBean
    private IndependentService independentService;
    @Mock
    private PemTempRepository pemTempRepository;
    @Mock
    private UserUtilityService userUtilityService;
    @Mock
    private PasswordUtilityService passwordUtilityService;
    @Mock
    private MapRepository mapRepository;
    //@InjectMocks
    private ManageWorkFlowService manageWorkFlowService;

    @BeforeEach
    void inIt() {
        manageWorkFlowService = new ManageWorkFlowService(processService,processDocsService,
                processRulesService,partnerService,applicationService,fileTransferService,
                ruleService,managePartnerService,manageApplicationService,activityHistoryService,reportRepository,
                independentService,false,false,pemTempRepository,userUtilityService,
                passwordUtilityService,mapRepository);
    }

    @Test
    @DisplayName("Pem Template FileTypes List Getting Partner List")
    public void pemTemplateFileTypesList() {
        Mockito.when(partnerService.findByPartnerName(Mockito.anyString())).thenReturn(getListOfPartners());
        manageWorkFlowService.pemTemplateFileTypesList(generatePemFileTypeModel1(), false, false);
        Mockito.verify(partnerService, Mockito.never()).findByPartnerName(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).getAllTemplateProfiles();
    }

    @Test
    @DisplayName("Pem Template FileTypes List Getting Exception")
    public void pemTemplateFileTypesList1() {
        Mockito.when(fileTransferService.searchByFileType(Mockito.any())).thenReturn(getTransferInfoEntity());
        manageWorkFlowService.pemTemplateFileTypesList(generatePemFileTypeModel(), false, false);
        Mockito.verify(fileTransferService, Mockito.times(1)).searchByFileType(Mockito.any());
        Mockito.verify(partnerService, Mockito.never()).getAllTemplateProfiles();
    }

    @Test
    @DisplayName("Pem Template FileTypes List Getting All TemplateProfiles")
    public void pemTemplateFileTypesList2() {
        Mockito.when(partnerService.getAllTemplateProfiles()).thenReturn(getListOfPartners());
        manageWorkFlowService.pemTemplateFileTypesList(generatePemFileTypeModel1(), false, false);
        Mockito.verify(partnerService, Mockito.never()).findByPartnerName(Mockito.anyString());
        Mockito.verify(partnerService, Mockito.times(1)).getAllTemplateProfiles();
    }

    PemFileTypeModel generatePemFileTypeModel1() {
        PemFileTypeModel pemFileTypeModel = new PemFileTypeModel();
        pemFileTypeModel.setRegexFind(true);
        pemFileTypeModel.setPkId("123456");
        pemFileTypeModel.setPartner(null);
        pemFileTypeModel.setApplication("Application");
        pemFileTypeModel.setFileName("FileName");
        return pemFileTypeModel;
    }

    PemFileTypeModel generatePemFileTypeModel() {
        PemFileTypeModel pemFileTypeModel = new PemFileTypeModel();
        pemFileTypeModel.setPkId("123456");
        pemFileTypeModel.setPartner("Partner");
        pemFileTypeModel.setApplication("Application");
        pemFileTypeModel.setFileName("FileName");
        return pemFileTypeModel;
    }

    PartnerEntity getPartnerEntity() {
        PartnerEntity partnerEntity = new PartnerEntity();
        partnerEntity.setPkId("123456");
        partnerEntity.setTpId("123456");
        partnerEntity.setTpName("Partner");
        return partnerEntity;
    }

    PartnerEntity getPartnerEntity1() {
        PartnerEntity partnerEntity = new PartnerEntity();
        partnerEntity.setPkId("654321");
        partnerEntity.setTpId("654321");
        partnerEntity.setTpName("PartnerName");
        return partnerEntity;
    }

    PartnerEntity getPartnerEntity2() {
        PartnerEntity partnerEntity = new PartnerEntity();
        partnerEntity.setPkId("1212");
        partnerEntity.setTpId("1212");
        partnerEntity.setTpName("template");
        return partnerEntity;
    }

    PartnerEntity getPartnerEntity3() {
        PartnerEntity partnerEntity = new PartnerEntity();
        partnerEntity.setPkId("2121");
        partnerEntity.setTpId("123456");
        partnerEntity.setTpName("template");
        return partnerEntity;
    }

    ProcessEntity generateProcessEntity() {
        ProcessEntity processEntity = new ProcessEntity();
        processEntity.setApplicationProfile("ApplicationProfile");
        return processEntity;
    }

    List<PartnerEntity> getListOfPartners() {
        List al = new ArrayList();
        al.add(getPartnerEntity());
        al.add(getPartnerEntity1());
        al.add(getPartnerEntity2());
        al.add(getPartnerEntity3());
        return al;
    }

    TransferInfoEntity getTransferInfoEntity() {
        TransferInfoEntity transferInfoEntity = new TransferInfoEntity();
        transferInfoEntity.setStatus("Y");
        return transferInfoEntity;
    }
}
