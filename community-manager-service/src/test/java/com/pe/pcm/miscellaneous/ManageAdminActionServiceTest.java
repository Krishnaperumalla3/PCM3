/*
package com.pe.pcm.miscellaneous;


import com.pe.pcm.reports.ReportRepository;
import com.pe.pcm.workflow.ProcessDocsService;
import com.pe.pcm.workflow.ProcessRulesRepository;
import com.pe.pcm.workflow.ProcessRulesService;
import com.pe.pcm.workflow.entity.ProcessRulesEntity;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ManageAdminActionServiceTest {

    private ProcessDocsService processDocsService;
    @MockBean
    private ProcessRulesService processRulesService;

    private ReportRepository reportRepository;
    @InjectMocks
    private ManageAdminActionsService adminActionsService;

    @Test
    @DisplayName("Manage Admin Actions Service")
    public void testActivateRemoteProfiles(){
             //reportRepository.purgeTransactions((long) 1234);
              String ss = adminActionsService.purgeTransactions((long) 12345);
        System.out.println(ss);
        // Mockito.verify(reportRepository,Mockito.times(1)).purgeTransactions(Mockito.anyLong());
    }

    @Test
    @DisplayName("manage WorkFlow")
    public void test_manageWorkFlow(){
        Mockito.when(processRulesService.findAll()).thenReturn(getProcessRulesEntities());
        adminActionsService.manageWorkFlow();
        Mockito.verify(processRulesService,Mockito.times(1)).findAll();
    }


    ProcessRulesEntity getProcessRulesEntity(){
        ProcessRulesEntity processRulesEntity=new ProcessRulesEntity();
        processRulesEntity.setPropertyName1("13");
        processRulesEntity.setPkId("123456");
        processRulesEntity.setPropertyName1("property_1");
        return processRulesEntity;
    }

    List<ProcessRulesEntity> getProcessRulesEntities(){
        List<ProcessRulesEntity> li=new ArrayList<>();
        li.add(getProcessRulesEntity());
        return li;

    }


}
*/
