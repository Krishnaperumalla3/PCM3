package com.pe.pcm.apiworkflow;

import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.apiworkflow.entity.ProcessApiEntity;
import com.pe.pcm.apiworkflow.entity.ProcessApiRuleEntity;
import com.pe.pcm.apiworkflow.entity.ProcessDocsApiEntity;
import com.pe.pcm.application.ApplicationService;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.functions.TriFunction;
import com.pe.pcm.partner.PartnerService;
import com.pe.pcm.rule.RuleService;
import com.pe.pcm.rule.entity.RuleEntity;
import com.pe.pcm.workflow.ProcessRuleModel;
import com.pe.pcm.workflow.api.ProcessDocApiModel;
import com.pe.pcm.workflow.api.WorkflowAPIUIModel;
import com.pe.pcm.workflow.entity.WorkFlowActivityHistoryEntity;
import org.apache.commons.lang3.StringUtils;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.ValueChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

import static com.pe.pcm.apiworkflow.ApiWorkFlowFunctions.serializeToProcessApiEntity;
import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.KeyGeneratorUtil.getPrimaryKey;
import static com.pe.pcm.utils.PCMConstants.*;

/**
 * @author Kiran Reddy.
 */
@Service
public class ManageApiWorkflowService {

    private final ProcessApiService processApiService;
    private final ProcessDocsApiService processDocsApiService;
    private final ProcessRulesApiService processRulesApiService;
    private final ActivityHistoryService activityHistoryService;
    private final RuleService ruleService;
    private final PartnerService partnerService;
    private final ApplicationService applicationService;

    @Autowired
    public ManageApiWorkflowService(ProcessApiService processApiService,
                                    ProcessDocsApiService processDocsApiService,
                                    ProcessRulesApiService processRulesApiService, ActivityHistoryService activityHistoryService, RuleService ruleService, PartnerService partnerService, ApplicationService applicationService) {
        this.processApiService = processApiService;
        this.processDocsApiService = processDocsApiService;
        this.processRulesApiService = processRulesApiService;
        this.activityHistoryService = activityHistoryService;
        this.ruleService = ruleService;
        this.partnerService = partnerService;
        this.applicationService = applicationService;
    }

    @Transactional
    public void save(WorkflowAPIUIModel workflowAPIUIModel) {
        AtomicReference<Boolean> isUpdate = new AtomicReference<>(false);
        AtomicReference<WorkflowAPIUIModel> workflowAPIUIModelOld = new AtomicReference<>();
        processApiService.getByProfileOptional(workflowAPIUIModel.getApiName()).ifPresent(processApiEntity -> {
            isUpdate.set(true);
            workflowAPIUIModelOld.set(get(processApiEntity.getProfileId()));
            delete(processApiEntity.getSeqId());
        });
        String processPrimaryKey = getPrimaryKey.apply(PROCESS_PAPIKEY_PRE_APPEND,
                PROCESS_PKEY_RANDOM_COUNT);
        if (isNotNull(workflowAPIUIModel.getProcessDocApiModel())) {
            workflowAPIUIModel.getProcessDocApiModel().forEach(processDocApiModel -> {
                String pdPrimaryKey = getPrimaryKey.apply(PROCESS_API_DOCS_PKEY_PRE_APPEND,
                        PROCESS_DOCS_PKEY_RANDOM_COUNT);
                List<String> ruleSeqList = new ArrayList<>();
                AtomicInteger atomicInteger = new AtomicInteger(1);
                if (processDocApiModel.getProcessRulesList() != null) {
                    processDocApiModel.getProcessRulesList().forEach(processRuleModel -> {
                                if (isNotNull(processRuleModel.getRuleName())) {
                                    ruleSeqList.add(processRulesApiService.save(processRuleModel, pdPrimaryKey, atomicInteger.getAndIncrement()).getPkId());
                                }
                            }
                    );
                }
                processDocsApiService.save(processDocApiModel, pdPrimaryKey, ruleSeqList, processPrimaryKey);
            });
        }
        processApiService.create(serializeToProcessApiEntity.apply(workflowAPIUIModel.getApiName(), processPrimaryKey));
        StringBuilder stringBuilder = new StringBuilder();
        if (isUpdate.get()) {
            stringBuilder.append(getDifference(workflowAPIUIModel.getProcessDocApiModel(), workflowAPIUIModelOld.get().getProcessDocApiModel(), "", ""));
        } else {
            stringBuilder.append("WorkFlow Created.");
        }
        if (StringUtils.isNotEmpty(stringBuilder.toString().trim())) {
            activityHistoryService.saveApiWorkflowActivity(workflowAPIUIModel.getApiName(), stringBuilder.toString());
        }
    }

    public WorkflowAPIUIModel get(String profileId) {
        return fetchWorkFlowFromDb(profileId);
    }

    @Transactional
    public void delete(String seqId) {
        ProcessApiEntity processApiEntity = processApiService.getBySeqId(seqId);
        for (ProcessDocsApiEntity processDocsApiEntity : processDocsApiService.searchByProcessRef(processApiEntity.getSeqId())) {
            if (StringUtils.isNotBlank(processDocsApiEntity.getProcessRuleSeq()))
                processRulesApiService.deleteAll(Arrays.asList(processDocsApiEntity.getProcessRuleSeq().split(COMMA)));
        }
        processDocsApiService.delete(processDocsApiService.searchByProcessRef(processApiEntity.getSeqId()));
        processApiService.delete(processApiEntity.getSeqId());
    }

    public Page<WorkFlowActivityHistoryEntity> getHistory(String processRefId, Pageable pageable) {
        return activityHistoryService.getWorkFlowHistory(processRefId, pageable);
    }


    public WorkflowAPIUIModel fetchWorkFlowFromDb(String profileId) {
        WorkflowAPIUIModel workflowAPIUIModel = new WorkflowAPIUIModel();
        ProcessApiEntity processApiEntity = processApiService.get(profileId);
        if (isNotNull(processApiEntity)) {
            List<ProcessDocsApiEntity> processDocsApiEntities = processDocsApiService.searchByProcessRef(processApiEntity.getSeqId());
            List<ProcessDocApiModel> processDocsApiList = new ArrayList<>();
            IntStream.range(0, processDocsApiEntities.size()).forEach(index -> {
                ProcessDocsApiEntity processDocsApiEntity = processDocsApiEntities.get(index);
                ProcessDocApiModel pdApiBean = new ProcessDocApiModel();
                pdApiBean.setPkId(processDocsApiEntity.getPkId());
                pdApiBean.setMethodName(processDocsApiEntity.getMethodName());
                pdApiBean.setDescription(processDocsApiEntity.getDescription());
                pdApiBean.setFilter(processDocsApiEntity.getFilter());
                pdApiBean.setIndex(index + 1);
                List<ProcessApiRuleEntity> processApiRulesList;
                if (isNotNull(processDocsApiEntity.getProcessRuleSeq())) {
                    List<String> seqIds = Arrays.asList(processDocsApiEntity.getProcessRuleSeq().split(COMMA));
                    processApiRulesList = processRulesApiService.findByPkIds(seqIds);
                    List<ProcessRuleModel> processRulesXMLList = new ArrayList<>();
                    IntStream.range(0, seqIds.size()).forEach(ruleIndex -> {
                        Optional<ProcessApiRuleEntity> processRulesEntityOptional = processApiRulesList.stream().filter(pre -> pre.getPkId().equalsIgnoreCase(seqIds.get(ruleIndex))).findFirst();
                        processRulesEntityOptional.ifPresent(processApiRulesEntity -> {
                            ProcessRuleModel prXMLBean = convertRuleEntityToProcessRuleModel.apply(processApiRulesEntity, "", false);
                            prXMLBean.setIndex(ruleIndex + 1);
                            processRulesXMLList.add(prXMLBean);
                        });
                    });
                    pdApiBean.setProcessRulesList(processRulesXMLList);
                }
                processDocsApiList.add(pdApiBean);
            });
            workflowAPIUIModel.setSeqId(processApiEntity.getSeqId());
            workflowAPIUIModel.setApiName(processApiEntity.getProfileId());
            workflowAPIUIModel.setProcessDocApiModel(processDocsApiList);
        } else {
            throw new CommunityManagerServiceException(404, "No workflow exists with the Profile provided.");
        }
        return workflowAPIUIModel;
    }

    private final TriFunction<ProcessApiRuleEntity, String, Boolean, ProcessRuleModel> convertRuleEntityToProcessRuleModel = (processApiRuleEntity, flow, isExport) -> {
        ProcessRuleModel processRuleModel = new ProcessRuleModel();
        processRuleModel.setRuleId(processApiRuleEntity.getRuleId());
        processRuleModel.setRuleName(processApiRuleEntity.getRuleName());
        processRuleModel.setPropertyValue1(processApiRuleEntity.getPropertyName1());
        processRuleModel.setPropertyValue2(processApiRuleEntity.getPropertyName2());
        processRuleModel.setPropertyValue3(processApiRuleEntity.getPropertyName3());
        processRuleModel.setPropertyValue4(processApiRuleEntity.getPropertyName4());
        processRuleModel.setPropertyValue5(processApiRuleEntity.getPropertyName5());
        processRuleModel.setPropertyValue6(processApiRuleEntity.getPropertyName6());
        processRuleModel.setPropertyValue7(processApiRuleEntity.getPropertyName7());
        processRuleModel.setPropertyValue8(processApiRuleEntity.getPropertyName8());
        processRuleModel.setPropertyValue9(processApiRuleEntity.getPropertyName9());
        processRuleModel.setPropertyValue10(processApiRuleEntity.getPropertyName10());
        processRuleModel.setPropertyValue11(processApiRuleEntity.getPropertyName11());
        processRuleModel.setPropertyValue12(processApiRuleEntity.getPropertyName12());
        processRuleModel.setPropertyValue13(processApiRuleEntity.getPropertyName13());
        processRuleModel.setPropertyValue14(processApiRuleEntity.getPropertyName14());
        processRuleModel.setPropertyValue15(processApiRuleEntity.getPropertyName15());
        processRuleModel.setPropertyValue16(processApiRuleEntity.getPropertyName16());
        processRuleModel.setPropertyValue17(processApiRuleEntity.getPropertyName17());
        processRuleModel.setPropertyValue18(processApiRuleEntity.getPropertyName18());
        processRuleModel.setPropertyValue19(processApiRuleEntity.getPropertyName19());
        processRuleModel.setPropertyValue20(processApiRuleEntity.getPropertyName20());
        processRuleModel.setPropertyValue21(processApiRuleEntity.getPropertyName21());
        processRuleModel.setPropertyValue22(processApiRuleEntity.getPropertyName22());
        processRuleModel.setPropertyValue23(processApiRuleEntity.getPropertyName23());
        processRuleModel.setPropertyValue24(processApiRuleEntity.getPropertyName24());
        processRuleModel.setPropertyValue25(processApiRuleEntity.getPropertyName25());
        return processRuleModel;
    };

    private String getDifference(List<ProcessDocApiModel> newList, List<ProcessDocApiModel> oldList, String text, String type) {

        StringBuilder stringBuilder = new StringBuilder();
        oldList.forEach(processDocModel -> {
            Optional<ProcessDocApiModel> processDocModelOptional = newList.stream().filter(processDocModel1 -> processDocModel1.getIndex() == processDocModel.getIndex()).findFirst();
            if (processDocModelOptional.isPresent()) {

                //process docs changes
                Javers javers = JaversBuilder.javers().build();
                Diff diff = javers.compare(processDocModel, processDocModelOptional.get());
                List<ValueChange> changes = new ArrayList<>(diff.getChangesByType(ValueChange.class));
                changes.forEach(valueChange -> {
                    if (!valueChange.getPropertyNameWithPath().contains("processRulesList")) {
                        StringBuilder temp = new StringBuilder();
                        stringBuilder.append("Transaction updated in ").append(text).append(temp.toString()).append(" Property Name ").append(valueChange.getPropertyNameWithPath()).append(" is changed from ").append(valueChange.getLeft()).append(" to ").append(valueChange.getRight()).append("; ");
                    }
                });

                // rule changes
                processDocModel.getProcessRulesList().forEach(processRuleModel -> {
                    Optional<ProcessRuleModel> processRuleModelOptional = processDocModelOptional.get().getProcessRulesList().stream().filter((processRuleModel1 -> processRuleModel1.getIndex() == processRuleModel.getIndex())).findFirst();
                    if (processRuleModelOptional.isPresent()) {
                        Diff rulesDiff = javers.compare(processRuleModel, processRuleModelOptional.get());
                        List<ValueChange> ruleChanges = new ArrayList<>(rulesDiff.getChangesByType(ValueChange.class));
                        StringBuilder stringBuilderRules = new StringBuilder();
                        ruleChanges.forEach(valueChange -> {
                            if (!valueChange.getPropertyNameWithPath().contains("ruleId")) {
                                String rulePropertyName = getRulePropertyName(processRuleModel.getRuleName(), valueChange.getPropertyNameWithPath());
                                if (isNotNull(valueChange.getLeft()) || isNotNull(valueChange.getRight())) {
                                    stringBuilderRules.append(" " + RULE_NAME + ":").append(processRuleModel.getRuleName()).append(" ").append(rulePropertyName);
                                }
                                if (!org.springframework.util.StringUtils.isEmpty(valueChange.getLeft()) && !org.springframework.util.StringUtils.isEmpty(valueChange.getRight())) {
                                    stringBuilderRules.append(" value is changed from ").append(getPropertyValue(text, rulePropertyName, valueChange.getLeft().toString())).append(" to ").append(getPropertyValue(text, rulePropertyName, valueChange.getRight().toString())).append(";");
                                } else if (!org.springframework.util.StringUtils.isEmpty(valueChange.getLeft())) {
                                    stringBuilderRules.append(rulePropertyName).append(" value ").append(getPropertyValue(text, rulePropertyName, valueChange.getLeft().toString())).append(" is removed ").append(";");
                                } else if (!org.springframework.util.StringUtils.isEmpty(valueChange.getRight())) {
                                    stringBuilderRules.append(rulePropertyName).append(" value ").append("is set to ").append(getPropertyValue(text, rulePropertyName, valueChange.getRight().toString())).append(";");
                                }
                            }
                        });
                        if (stringBuilderRules.length() > 0) {
                            StringBuilder temp = new StringBuilder();
                            stringBuilder.append(text).append(temp).append(stringBuilderRules);
                        }
                    } else {
                        StringBuilder temp = new StringBuilder();
                        stringBuilder.append("Rule deleted in ").append(text).append(temp).append(" " + RULE_NAME + "= ").append(processRuleModel.getRuleName()).append(";");
                    }
                });

                processDocModelOptional.get().getProcessRulesList().forEach(processRuleModel -> {
                    Optional<ProcessRuleModel> processRuleModelOptional = processDocModel.getProcessRulesList().stream().filter((processRuleModel1 -> processRuleModel1.getIndex() == processRuleModel.getIndex())).findFirst();
                    if (!processRuleModelOptional.isPresent()) {
                        StringBuilder temp = new StringBuilder();
                        stringBuilder.append("New Rule Created in ").append(text).append(temp).append(" " + RULE_NAME + " = ").append(processRuleModel.getRuleName()).append("; ");
                    }
                });

            } else {
                StringBuilder temp = new StringBuilder();
                stringBuilder.append("Transaction deleted in ").append(text).append(temp.toString()).append(";");
            }
        });
        newList.forEach(processDocModel -> {
            Optional<ProcessDocApiModel> processDocModelOptional = oldList.stream().filter(processDocModel1 -> processDocModel1.getIndex() == processDocModel.getIndex()).findAny();
            if (!processDocModelOptional.isPresent()) {
                StringBuilder temp = new StringBuilder();
                stringBuilder.append("New Transaction created in ").append(text).append(temp.toString()).append(";");
                processDocModel.getProcessRulesList().forEach(processRuleModel ->
                        stringBuilder.append("New Rule created in ").append(text).append(temp.toString()).append(" " + RULE_NAME + "= ").append(processRuleModel.getRuleName()).append(";")
                );
            }
        });
        return stringBuilder.toString();
    }

    private String getRulePropertyName(String ruleName, String propertyNameWithPath) {
        Optional<RuleEntity> ruleEntity = ruleService.getEntity(ruleName);
        String propertyName = null;
        if (ruleEntity.isPresent()) {
            switch (propertyNameWithPath) {
                case "propertyValue1":
                    propertyName = ruleEntity.get().getPropertyName1();
                    break;
                case "propertyValue2":
                    propertyName = ruleEntity.get().getPropertyName2();
                    break;
                case "propertyValue3":
                    propertyName = ruleEntity.get().getPropertyName3();
                    break;
                case "propertyValue4":
                    propertyName = ruleEntity.get().getPropertyName4();
                    break;
                case "propertyValue5":
                    propertyName = ruleEntity.get().getPropertyName5();
                    break;
                case "propertyValue6":
                    propertyName = ruleEntity.get().getPropertyName6();
                    break;
                case "propertyValue7":
                    propertyName = ruleEntity.get().getPropertyName7();
                    break;
                case "propertyValue8":
                    propertyName = ruleEntity.get().getPropertyName8();
                    break;
                case "propertyValue9":
                    propertyName = ruleEntity.get().getPropertyName9();
                    break;
                case "propertyValue10":
                    propertyName = ruleEntity.get().getPropertyName10();
                    break;
                case "propertyValue11":
                    propertyName = ruleEntity.get().getPropertyName11();
                    break;
                case "propertyValue12":
                    propertyName = ruleEntity.get().getPropertyName12();
                    break;
                case "propertyValue13":
                    propertyName = ruleEntity.get().getPropertyName13();
                    break;
                case "propertyValue14":
                    propertyName = ruleEntity.get().getPropertyName14();
                    break;
                case "propertyValue15":
                    propertyName = ruleEntity.get().getPropertyName15();
                    break;
                case "propertyValue16":
                    propertyName = ruleEntity.get().getPropertyName16();
                    break;
                case "propertyValue17":
                    propertyName = ruleEntity.get().getPropertyName17();
                    break;
                case "propertyValue18":
                    propertyName = ruleEntity.get().getPropertyName18();
                    break;
                case "propertyValue19":
                    propertyName = ruleEntity.get().getPropertyName19();
                    break;
                case "propertyValue20":
                    propertyName = ruleEntity.get().getPropertyName20();
                    break;
                case "propertyValue21":
                    propertyName = ruleEntity.get().getPropertyName21();
                    break;
                case "propertyValue22":
                    propertyName = ruleEntity.get().getPropertyName22();
                    break;
                case "propertyValue23":
                    propertyName = ruleEntity.get().getPropertyName23();
                    break;
                case "propertyValue24":
                    propertyName = ruleEntity.get().getPropertyName24();
                    break;
                case "propertyValue25":
                    propertyName = ruleEntity.get().getPropertyName25();
                    break;
                case "businessProcessId":
                    propertyName = ruleEntity.get().getBusinessProcessId();
                    break;
                default:
                    break;
            }
        }
        return propertyName;
    }

    public void updateApiName(String oldApiName, String newAPiName){
        processApiService.updateApiName(oldApiName, newAPiName);
    }

    private String getPropertyValue(String transactionType, String propertyName, String propertyValue) {
        if (isNotNull(propertyValue) && isNotNull(propertyName) && propertyName.equalsIgnoreCase("ProtocolReference")) {
            return transactionType.endsWith(INBOUND) ? applicationService.getNoThrow(propertyValue).getApplicationName() : partnerService.getNoThrow(propertyValue).getTpName();
        }
        return propertyValue;
    }

}
