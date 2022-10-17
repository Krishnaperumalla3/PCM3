/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://pragmaedge.com/licenseagreement
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pe.pcm.workflow;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.pe.pcm.activity.ActivityHistoryService;
import com.pe.pcm.application.ApplicationService;
import com.pe.pcm.application.ManageApplicationService;
import com.pe.pcm.application.entity.ApplicationEntity;
import com.pe.pcm.common.CommunityManagerKeyValueModel;
import com.pe.pcm.common.CommunityManagerMapModel;
import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.common.CommunityMangerModel;
import com.pe.pcm.constants.AuthoritiesConstants;
import com.pe.pcm.enums.Protocol;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.functions.TriFunction;
import com.pe.pcm.miscellaneous.IndependentService;
import com.pe.pcm.miscellaneous.PasswordUtilityService;
import com.pe.pcm.miscellaneous.UserUtilityService;
import com.pe.pcm.partner.ManagePartnerService;
import com.pe.pcm.partner.PartnerService;
import com.pe.pcm.partner.entity.PartnerEntity;
import com.pe.pcm.pem.PemStringDataModel;
import com.pe.pcm.reports.DataFlowMapper;
import com.pe.pcm.reports.DataFlowModel;
import com.pe.pcm.reports.FileTransferService;
import com.pe.pcm.reports.ReportRepository;
import com.pe.pcm.reports.entity.TransferInfoEntity;
import com.pe.pcm.rule.RuleModel;
import com.pe.pcm.rule.RuleService;
import com.pe.pcm.rule.entity.RuleEntity;
import com.pe.pcm.sterling.MapRepository;
import com.pe.pcm.utils.CommonFunctions;
import com.pe.pcm.workflow.entity.*;
import com.pe.pcm.workflow.pem.PemContentFileTypeModel;
import com.pe.pcm.workflow.pem.PemContentWorkFlowModel;
import com.pe.pcm.workflow.pem.PemCopyWorkFlowModel;
import com.pe.pcm.workflow.pem.PemFileTypeModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.ValueChange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.utils.CommonFunctions.*;
import static com.pe.pcm.utils.KeyGeneratorUtil.getPrimaryKey;
import static com.pe.pcm.utils.PCMConstants.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * @author Kiran Reddy.
 */
@Service
public class ManageWorkFlowService {

    private final Logger logger = LoggerFactory.getLogger(ManageWorkFlowService.class);

    private final ProcessService processService;
    private final ProcessDocsService processDocsService;
    private final ProcessRulesService processRulesService;
    private PartnerService partnerService;
    private ApplicationService applicationService;
    private final FileTransferService fileTransferService;
    private RuleService ruleService;
    private final ManagePartnerService managePartnerService;
    private final ManageApplicationService manageApplicationService;
    private final ActivityHistoryService activityHistoryService;
    private final ReportRepository reportRepository;
    private final IndependentService independentService;
    private final PemTempRepository pemTempRepository;
    private final UserUtilityService userUtilityService;
    private final PasswordUtilityService passwordUtilityService;
    private final MapRepository mapRepository;

    private final Boolean isDHDuplicate;
    private final Boolean isMFTDuplicate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public ManageWorkFlowService(ProcessService processService, ProcessDocsService processDocsService, ProcessRulesService processRulesService,
                                 PartnerService partnerService, ApplicationService applicationService, FileTransferService fileTransferService,
                                 RuleService ruleService, ManagePartnerService managePartnerService, ManageApplicationService manageApplicationService,
                                 ActivityHistoryService activityHistoryService, ReportRepository reportRepository, IndependentService independentService,
                                 @Value("${workFlow.duplicate.docHandling}") Boolean isDHDuplicate, @Value("${workFlow.duplicate.mft}") Boolean isMFTDuplicate,
                                 PemTempRepository pemTempRepository, UserUtilityService userUtilityService, PasswordUtilityService passwordUtilityService, MapRepository mapRepository) {
        this.processService = processService;
        this.processDocsService = processDocsService;
        this.processRulesService = processRulesService;
        this.partnerService = partnerService;
        this.applicationService = applicationService;
        this.fileTransferService = fileTransferService;
        this.ruleService = ruleService;
        this.managePartnerService = managePartnerService;
        this.manageApplicationService = manageApplicationService;
        this.activityHistoryService = activityHistoryService;
        this.reportRepository = reportRepository;
        this.independentService = independentService;
        this.isDHDuplicate = isDHDuplicate;
        this.isMFTDuplicate = isMFTDuplicate;
        this.pemTempRepository = pemTempRepository;
        this.userUtilityService = userUtilityService;
        this.passwordUtilityService = passwordUtilityService;
        this.mapRepository = mapRepository;
    }

    @Transactional
    public List<PemFileTypeModel> pemTemplateFileTypesList(PemFileTypeModel pemFileTypeModel, boolean isFileStatus, boolean isPartnerID) {
        List<PemFileTypeModel> pemFileTypeModelList = new ArrayList<>();
        Map<String, String> getPartnerTemplateMap;
        Map<String, String> getApplicationTemplateMap;
        List<ProcessEntity> processEntityList;
        if (!pemFileTypeModel.isRegexFind()) {
            TransferInfoEntity transferInfoEntity = fileTransferService.searchByFileType(pemFileTypeModel.setDocType("MFT"));
            pemFileTypeModelList.add(new PemFileTypeModel().setStatus(isNotNull(transferInfoEntity.getStatus()) ? transferInfoEntity.getStatus() : "transaction file not found")
                    .setErrorInfo(isNotNull(transferInfoEntity.getAdverrorstatus()) ? transferInfoEntity.getAdverrorstatus() : transferInfoEntity.getErrorstatus())
                    .setPartner(pemFileTypeModel.getPartner())
                    .setApplication(pemFileTypeModel.getApplication()));
            return pemFileTypeModelList;
        }

        if (isNotNull(pemFileTypeModel.getPartner())) {
            getPartnerTemplateMap = partnerService.findByPartnerName(pemFileTypeModel.getPartner())
                    .stream().collect(Collectors.toMap(PartnerEntity::getPkId, PartnerEntity::getTpName));
        } else {
            getPartnerTemplateMap = partnerService.getAllTemplateProfiles()
                    .stream().collect(Collectors.toMap(PartnerEntity::getPkId, PartnerEntity::getTpName));
        }

        processEntityList = processService.findByPartnerProfileIn(new ArrayList<>(getPartnerTemplateMap.keySet()));

        if (isNotNull(pemFileTypeModel.getApplication())) {
            getApplicationTemplateMap = applicationService.searchByApplicationName(pemFileTypeModel.getApplication())
                    .stream()
                    .collect(Collectors.toMap(ApplicationEntity::getPkId, ApplicationEntity::getApplicationName));
            processEntityList.removeIf(processEntity -> !processEntity.getApplicationProfile().contains(new ArrayList<>(getApplicationTemplateMap.keySet()).get(0)));
        } else {
            getApplicationTemplateMap = applicationService.getAllTemplateApplicationProfiles()
                    .stream()
                    .collect(Collectors.toMap(ApplicationEntity::getPkId, ApplicationEntity::getApplicationName));
        }

        for (ProcessEntity processEntity : processEntityList) {
            List<ProcessDocsEntity> processDocsEntities = processDocsService.searchByProcessRef(processEntity.getSeqId());
            processDocsEntities.forEach(processDocsEntity ->
                    pemFileTypeModelList.add(new PemFileTypeModel().setPkId(processDocsEntity.getPkId())
                            .setPartner(getPartnerTemplateMap.get(processEntity.getPartnerProfile()))
                            .setApplication(getApplicationTemplateMap.get(processEntity.getApplicationProfile()))
                            .setFlowType(processEntity.getFlow())
                            .setDocType(isNotNull(processDocsEntity.getDoctype()) ? processDocsEntity.getDoctype() : MFT)
                            .setFileName(processDocsEntity.getFilenamePattern())
                            .setSenderId((isFileStatus || isPartnerID) ? processDocsEntity.getPartnerid() : "")
                            .setReceiverId((isFileStatus || isPartnerID) ? processDocsEntity.getReciverid() : "")
                            .setTransaction(processDocsEntity.getDoctrans())
                            .setOperation(pemFileTypeModel.getOperation())
                            .setRegexFind(pemFileTypeModel.isRegexFind())));
        }

        if (isFileStatus) {
            List<PemFileTypeModel> pemFileTypeModelListD = new ArrayList<>();
            pemFileTypeModelList.forEach(pemFileTypeModel1 -> {
                        TransferInfoEntity transferInfoEntity = fileTransferService.searchByFileType(pemFileTypeModel1);
                        String dbType = independentService.getDbType();
                        if (dbType.equalsIgnoreCase(DB2) || dbType.equalsIgnoreCase(SQL_SERVER)) {
                            logger.info("FileName: {}, SourceFileName: {}", pemFileTypeModel1.getFileName(), transferInfoEntity.getSrcfilename());
                            if (isNotNull(pemFileTypeModel1.getFileName())) {
                                if (isNotNull(transferInfoEntity.getSrcfilename()) &&
                                        Pattern.compile(pemFileTypeModel1.getFileName()).matcher(transferInfoEntity.getSrcfilename()).matches()) {
                                    pemFileTypeModel1.setStatus(isNotNull(transferInfoEntity.getStatus()) ? transferInfoEntity.getStatus() : "transaction file not found")
                                            .setErrorInfo(isNotNull(transferInfoEntity.getAdverrorstatus()) ? transferInfoEntity.getAdverrorstatus() : transferInfoEntity.getErrorstatus());
                                    pemFileTypeModelListD.add(pemFileTypeModel1);
                                } else {
                                    pemFileTypeModel1.setStatus(isNotNull(transferInfoEntity.getStatus()) ? transferInfoEntity.getStatus() : "transaction file not found")
                                            .setErrorInfo(isNotNull(transferInfoEntity.getAdverrorstatus()) ? transferInfoEntity.getAdverrorstatus() : transferInfoEntity.getErrorstatus());
                                    pemFileTypeModelListD.add(pemFileTypeModel1);
                                }
                            } else {
                                pemFileTypeModel1.setStatus(isNotNull(transferInfoEntity.getStatus()) ? transferInfoEntity.getStatus() : "transaction file not found")
                                        .setErrorInfo(isNotNull(transferInfoEntity.getAdverrorstatus()) ? transferInfoEntity.getAdverrorstatus() : transferInfoEntity.getErrorstatus());
                                pemFileTypeModelListD.add(pemFileTypeModel1);
                            }
                        } else if (dbType.equalsIgnoreCase(ORACLE)) {
                            pemFileTypeModel1.setStatus(isNotNull(transferInfoEntity.getStatus()) ? transferInfoEntity.getStatus() : "transaction file not found")
                                    .setErrorInfo(isNotNull(transferInfoEntity.getAdverrorstatus()) ? transferInfoEntity.getAdverrorstatus() : transferInfoEntity.getErrorstatus());
                            pemFileTypeModelListD.add(pemFileTypeModel1);
                        }
                    }
            );
            return pemFileTypeModelListD;
        }
        return pemFileTypeModelList;
    }

    @Transactional
    public void pemCreateWorkFlowBase64(PemStringDataModel pemStringDataModel) {
        try {
            if (isNotNull(pemStringDataModel.getData())) {
                pemCreateWorkFlow(objectMapper.readValue(Base64.decodeBase64(pemStringDataModel.getData()), PemContentWorkFlowModel.class));
            }
        } catch (IOException e) {
            throw internalServerError(e.getMessage());
        }
    }

    @Transactional
    public void pemCreateWorkFlow(PemContentWorkFlowModel pemContentWorkFlowModel) {
        String profileId;
        String applicationId;

        if (!pemContentWorkFlowModel.getContent().isEmpty()) {
            profileId = partnerService.getUniquePartner(pemContentWorkFlowModel.getPartnerProfile()).getPkId();
            applicationId = applicationService.getUniqueApplication(pemContentWorkFlowModel.getApplicationProfile()).getPkId();
        } else {
            throw internalServerError("Please Provide at lease one rule selected");
        }
        createWorkflow(convertDataFlowMapperToWfModel(pemContentWorkFlowModel.getContent()), profileId, applicationId);
    }

    @Transactional
    public void pemUpdateWorkFlowFileTypes(PemContentWorkFlowModel pemContentWorkFlowModel) {
        Set<String> pkIdSet = new LinkedHashSet<>();
        if (pemContentWorkFlowModel.getContent() != null && !pemContentWorkFlowModel.getContent().isEmpty()) {
            pemContentWorkFlowModel.getContent().forEach(dataFlowMapper -> {
                if (isNotNull(dataFlowMapper.getPkId()) && !pkIdSet.contains(dataFlowMapper.getPkId())) {
                    pkIdSet.add(dataFlowMapper.getPkId());
                    PemTempEntity pemTempEntity = pemTempRepository.findById(dataFlowMapper.getPkId()).orElse(new PemTempEntity());
                    if (isNotNull(pemTempEntity.getProcessDocPkId())) {
                        processDocsService.findById(pemTempEntity.getProcessDocPkId())
                                .setDoctype(pemTempEntity.getDocType())
                                .setPartnerid(pemTempEntity.getSenderId())
                                .setReciverid(pemTempEntity.getReceiverId())
                                .setDoctrans(pemTempEntity.getTrans())
                                .setFilenamePattern(pemTempEntity.getFileName());
                    } else {
                        logger.info("PemTempEntity not available, PkId = {}", dataFlowMapper.getPkId());
                    }
                }
                if (isNotNull(dataFlowMapper.getFlow()) && isNotNull(dataFlowMapper.getRuleProperty2())) {
                    if (dataFlowMapper.getFlow().equalsIgnoreCase("inbound")) {
                        Optional<ApplicationEntity> applicationEntity = Optional.of(applicationService.find(dataFlowMapper.getRuleProperty2()).orElse(new ApplicationEntity()));
                        applicationEntity.ifPresent(applicationEntity1 -> dataFlowMapper.setRuleProperty2(applicationEntity1.getPkId()));
                    } else {
                        if (dataFlowMapper.getFlow().equalsIgnoreCase("outbound")) {
                            Optional<PartnerEntity> partnerEntity = Optional.of(partnerService.find(dataFlowMapper.getRuleProperty2()).orElse(new PartnerEntity()));
                            partnerEntity.ifPresent(partnerEntity1 -> dataFlowMapper.setRuleProperty2(partnerEntity1.getPkId()));
                        }
                    }
                }
                if (isNotNull(dataFlowMapper.getProcessRulePkId())) {
                    processRulesService.findById(dataFlowMapper.getProcessRulePkId())
                            .setRuleName(dataFlowMapper.getRuleName())
                            .setPropertyName1(dataFlowMapper.getRuleProperty1())
                            .setPropertyName2(dataFlowMapper.getRuleProperty2())
                            .setPropertyName3(dataFlowMapper.getRuleProperty3())
                            .setPropertyName4(dataFlowMapper.getRuleProperty4())
                            .setPropertyName5(dataFlowMapper.getRuleProperty5())
                            .setPropertyName6(dataFlowMapper.getRuleProperty6())
                            .setPropertyName7(dataFlowMapper.getRuleProperty7())
                            .setPropertyName8(dataFlowMapper.getRuleProperty8())
                            .setPropertyName9(dataFlowMapper.getRuleProperty9())
                            .setPropertyName10(dataFlowMapper.getRuleProperty10())
                            .setPropertyName11(dataFlowMapper.getRuleProperty11())
                            .setPropertyName12(dataFlowMapper.getRuleProperty12())
                            .setPropertyName13(dataFlowMapper.getRuleProperty13())
                            .setPropertyName14(dataFlowMapper.getRuleProperty14())
                            .setPropertyName15(dataFlowMapper.getRuleProperty15())
                            .setPropertyName16(dataFlowMapper.getRuleProperty16())
                            .setPropertyName17(dataFlowMapper.getRuleProperty17())
                            .setPropertyName18(dataFlowMapper.getRuleProperty18())
                            .setPropertyName19(dataFlowMapper.getRuleProperty19())
                            .setPropertyName20(dataFlowMapper.getRuleProperty20())
                            .setPropertyName21(dataFlowMapper.getRuleProperty21())
                            .setPropertyName22(dataFlowMapper.getRuleProperty22())
                            .setPropertyName23(dataFlowMapper.getRuleProperty23())
                            .setPropertyName24(dataFlowMapper.getRuleProperty24())
                            .setPropertyName25(dataFlowMapper.getRuleProperty25());
                }
            });
        }
    }

    @Transactional
    public void pemUpdateWorkFlowFileTypesBase64(PemStringDataModel pemStringDataModel) {
        try {
            if (isNotNull(pemStringDataModel.getData())) {
                pemUpdateWorkFlowFileTypes(objectMapper.readValue(Base64.decodeBase64(pemStringDataModel.getData()), PemContentWorkFlowModel.class));
            } else {
                throw internalServerError("Please provide data to process request");
            }
        } catch (IOException e) {
            throw internalServerError(e.getMessage());
        }
    }

    private WorkFlowUIModel convertDataFlowMapperToWfModel(List<DataFlowMapper> dataFlowMappers) {
        WorkFlowUIModel workFlowUIModel = new WorkFlowUIModel();
        SortedSet<String> pkIdsSortedSet = new TreeSet<>();

        ProcessFlowModel inboundFlow = new ProcessFlowModel();
        ProcessFlowModel outboundFlow = new ProcessFlowModel();

        //List<PemTempEntity> pemTempEntities = new ArrayList<>();
        if (!dataFlowMappers.isEmpty()) {
            dataFlowMappers.forEach(dataFlowMapper -> pkIdsSortedSet.add(dataFlowMapper.getPkId()));
        }
        if (pkIdsSortedSet.isEmpty()) {
            throw internalServerError("at least provide one pkId in content list");
        }
        pkIdsSortedSet.forEach(pemTempPkId -> {
            ProcessDocModel processDocModel = new ProcessDocModel();
            List<ProcessRuleModel> processRuleModels = new ArrayList<>();
            AtomicReference<String> flowType = new AtomicReference<>();
            AtomicReference<String> flow = new AtomicReference<>();
            dataFlowMappers.forEach(dataFlowMapper -> {
                if (pemTempPkId.equals(dataFlowMapper.getPkId())) {
                    PemTempEntity pemTempEntity = pemTempRepository.findById(dataFlowMapper.getPkId())
                            .orElseThrow(() -> internalServerError("PEM Flow Entity not found with provided Id, Id : " + dataFlowMapper.getPkId()));
                    //pemTempEntities.add(pemTempEntity);
                    flowType.set(pemTempEntity.getSeqType());
                    flow.set(pemTempEntity.getFlowType());
                    processDocModel.setFileNamePattern(pemTempEntity.getFileName());
                    processDocModel.setPartnerId(pemTempEntity.getSenderId());
                    processDocModel.setReceiverId(pemTempEntity.getReceiverId());
                    processDocModel.setDocTrans(pemTempEntity.getTrans());
                    processDocModel.setDocType(pemTempEntity.getDocType());
                    ProcessRuleModel processRuleModel = new ProcessRuleModel();
                    processRuleModel.setRuleName(dataFlowMapper.getRuleName());
                    processRuleModel.setPropertyValue1(dataFlowMapper.getRuleProperty1());
                    processRuleModel.setPropertyValue2(getProfilePkId.apply(pemTempEntity.getFlowType(), dataFlowMapper.getRuleProperty1(), dataFlowMapper.getRuleProperty2()));
                    processRuleModel.setPropertyValue3(dataFlowMapper.getRuleProperty3());
                    processRuleModel.setPropertyValue4(dataFlowMapper.getRuleProperty4());
                    processRuleModel.setPropertyValue5(dataFlowMapper.getRuleProperty5());
                    processRuleModel.setPropertyValue6(dataFlowMapper.getRuleProperty6());
                    processRuleModel.setPropertyValue7(dataFlowMapper.getRuleProperty7());
                    processRuleModel.setPropertyValue8(dataFlowMapper.getRuleProperty8());
                    processRuleModel.setPropertyValue9(dataFlowMapper.getRuleProperty9());
                    processRuleModel.setPropertyValue10(dataFlowMapper.getRuleProperty10());
                    processRuleModel.setPropertyValue11(dataFlowMapper.getRuleProperty11());
                    processRuleModel.setPropertyValue12(dataFlowMapper.getRuleProperty12());
                    processRuleModel.setPropertyValue13(dataFlowMapper.getRuleProperty13());
                    processRuleModel.setPropertyValue14(dataFlowMapper.getRuleProperty14());
                    processRuleModel.setPropertyValue15(dataFlowMapper.getRuleProperty15());
                    processRuleModel.setPropertyValue16(dataFlowMapper.getRuleProperty16());
                    processRuleModel.setPropertyValue17(dataFlowMapper.getRuleProperty17());
                    processRuleModel.setPropertyValue18(dataFlowMapper.getRuleProperty18());
                    processRuleModel.setPropertyValue19(dataFlowMapper.getRuleProperty19());
                    processRuleModel.setPropertyValue20(dataFlowMapper.getRuleProperty20());
                    processRuleModel.setPropertyValue21(dataFlowMapper.getRuleProperty21());
                    processRuleModel.setPropertyValue22(dataFlowMapper.getRuleProperty22());
                    processRuleModel.setPropertyValue23(dataFlowMapper.getRuleProperty23());
                    processRuleModel.setPropertyValue24(dataFlowMapper.getRuleProperty24());
                    processRuleModel.setPropertyValue25(dataFlowMapper.getRuleProperty25());
                    processRuleModels.add(processRuleModel);
                }
            });
            /*We should do delete the entities once we read the values to create the workflow*/
//            if (!pemTempEntities.isEmpty()) {
//                pemTempRepository.deleteAll(pemTempEntities);
//            }

            if (isNotNull(flowType.get()) && isNotNull(flow.get())) {
                processDocModel.setProcessRulesList(processRuleModels);
                if (flow.get().equals(INBOUND)) {
                    if (flowType.get().equals(MFT)) {
                        inboundFlow.getMfts().getProcessDocModels().add(processDocModel);
                    } else if (flowType.get().equals(DOC_HANDLING)) {
                        inboundFlow.getDocHandlings().getProcessDocModels().add(processDocModel);
                    }
                } else {
                    if (flowType.get().equals(MFT)) {
                        outboundFlow.getMfts().getProcessDocModels().add(processDocModel);
                    } else if (flowType.get().equals(DOC_HANDLING)) {
                        outboundFlow.getDocHandlings().getProcessDocModels().add(processDocModel);
                    }
                }
            }
        });

        return workFlowUIModel.setInboundFlow(inboundFlow).setOutboundFlow(outboundFlow);
    }

    public String exportEncryptedWorkFlow(String profileId, String applicationId, Boolean isPem, boolean isIncludeProfile) {
        return passwordUtilityService.encrypt(exportWorkFlow(profileId, applicationId, isPem, isIncludeProfile));
    }

    public String exportWorkFlow(String profileId, String applicationId, Boolean isPem, boolean isIncludeProfile) {
        WorkFlowModel workFlowModel = fetchWorkflowFromDB(profileId, applicationId, isPem, true, isIncludeProfile, false, true);
        ObjectMapper objectMapper = new XmlMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        StringBuilder builder = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>\n");
        try {
            builder.append(objectMapper.writeValueAsString(workFlowModel));
        } catch (JsonProcessingException e) {
            throw new CommunityManagerServiceException(500, "Unable to export workflow. Please try after sometime");
        }
        return builder.toString();
    }

    @Transactional(rollbackOn = RuntimeException.class)
    public void importWorkFlow(@Nullable MultipartFile file, String partnerProfileId, String applicationProfileId, Boolean isPem, String content) {

        if (file != null) {
            throwIfFilePathNull(file.getOriginalFilename());
        }
        try {
            String fileContent;
            XmlMapper xmlMapper = new XmlMapper();
            if (file != null) {
                byte[] bytes = file.getBytes();
                fileContent = new String(bytes);
            } else {
                logger.info("decrypting workFlow content");
                fileContent = passwordUtilityService.decrypt(content);
            }

            WorkFlowModel workFlowModel = xmlMapper.readValue(fileContent, WorkFlowModel.class);

            if (isPem) {
                logger.info("Pem Operations");
                partnerProfileId = partnerService.getUniquePartner(partnerProfileId).getPkId();
                applicationProfileId = applicationService.getUniqueApplication(applicationProfileId).getPkId();
            }
            buildWorkFLow(partnerProfileId, applicationProfileId, workFlowModel, true);
            activityHistoryService.saveWorkflowActivity(partnerProfileId, applicationProfileId, "Workflow Imported.");

        } catch (IOException e) {
            throw new CommunityManagerServiceException(500, "Sorry! Unable to read " + file);
        }
    }

    @Transactional
    public void importWorkFlowWithProfile(MultipartFile file, String xmlContent, Boolean isPem) {

        if (!isPem) {
            throwIfFilePathNull(file.getOriginalFilename());
        }

        String fileContent;
        try {
            XmlMapper xmlMapper = new XmlMapper();
            if (!isPem) {
                byte[] bytes = file.getBytes();
                fileContent = new String(bytes);
            } else {
                if (!isNotNull(xmlContent)) {
                    throw new CommunityManagerServiceException(500, "Please Provide the valid data.");
                }
                fileContent = xmlContent;
            }
            WorkFlowModel workFlowModel = xmlMapper.readValue(fileContent, WorkFlowModel.class);
            String partnerProfileId = managePartnerService.savePartner(workFlowModel.getPartnerInfoModel());
            String applicationProfileId = saveApplication(workFlowModel.getApplicationInfoModel());
            buildWorkFLow(partnerProfileId, applicationProfileId, workFlowModel, true);

        } catch (IOException e) {
            throw new CommunityManagerServiceException(500, "Sorry! Unable to read file/content : " + e.getMessage());
        }
    }

    private String saveApplication(ApplicationInfoModel applicationInfoModel) {
        try {
            return manageApplicationService.saveApplication(applicationInfoModel);
        } catch (CommunityManagerServiceException ex) {
            if (ex.getStatusCode() != 409) {
                throw internalServerError(ex.getErrorMessage());
            }
            return manageApplicationService.getApplication(applicationInfoModel);
        }
    }

    @Transactional(rollbackOn = RuntimeException.class)
    public void createWorkflow(WorkFlowUIModel workFlowUIModel, String profileId, String applicationId) {
        //Checking MFT Duplicate
        duplicateMftTransactionsCheck(workFlowUIModel);

        //Transform WorkFlowUIModel model to WorkFlowModel model
        WorkFlowModel workFlowModel = transform1(workFlowUIModel);

        //Checking Duplicate WorkFlow
        Boolean isDuplicate = isDuplicateWorkFlow(new PemCopyWorkFlowModel().setDestinationPartner(profileId).setDestinationApplication(applicationId));

        //Get WorkFlow if we already have WorkFlow
        WorkFlowUIModel workFlowUIModelOld = isDuplicate ? getWorkFlow(profileId, applicationId, false, true) : new WorkFlowUIModel();

        //Checking DOC-HANDLING Duplicate
        workFlowConstraints(workFlowModel, isDuplicate);

        if (isDuplicate) {
            deleteWorkFlow(profileId, applicationId, false);
            // Dont remove below commented lines
//            List<WorkFlowActivityHistoryEntity> workFlowActivityHistoryEntities = new ArrayList<>();
//            activityHistoryService.getWorkFlowActivity(partnerService.get(profileId).getTpName() + "_" + applicationService.get(applicationId).getApplicationName())
//                    .forEach(workFlowActivityHistoryEntity -> {
//                        WorkFlowActivityHistoryEntity wfActivityHistoryEntity = new WorkFlowActivityHistoryEntity();
//                        BeanUtils.copyProperties(workFlowActivityHistoryEntity, wfActivityHistoryEntity);
//                        workFlowActivityHistoryEntities.add(wfActivityHistoryEntity.setProcessRefId(profileId + "_" + applicationId));
//                    });
//            activityHistoryService.saveWorkflowActivity(workFlowActivityHistoryEntities);
        }
        buildWorkFLow(profileId, applicationId, workFlowModel, false);
        StringBuilder stringBuilder = new StringBuilder();
        if (isDuplicate) {
            stringBuilder.append(getDifference(workFlowUIModel.getInboundFlow().getDocHandlings().getProcessDocModels(), workFlowUIModelOld.getInboundFlow().getDocHandlings().getProcessDocModels(), "DocHandling-Inbound", "DocType"));
            stringBuilder.append(getDifference(workFlowUIModel.getInboundFlow().getMfts().getProcessDocModels(), workFlowUIModelOld.getInboundFlow().getMfts().getProcessDocModels(), "MFT-Inbound", "FileName"));
            stringBuilder.append(getDifference(workFlowUIModel.getOutboundFlow().getDocHandlings().getProcessDocModels(), workFlowUIModelOld.getOutboundFlow().getDocHandlings().getProcessDocModels(), "docHandling-Outbound", "DocType"));
            stringBuilder.append(getDifference(workFlowUIModel.getOutboundFlow().getMfts().getProcessDocModels(), workFlowUIModelOld.getOutboundFlow().getMfts().getProcessDocModels(), "MFT-Outbound", "FileName"));
        } else {
            stringBuilder.append("WorkFlow Created.");
        }
        if (StringUtils.isNotEmpty(stringBuilder.toString().trim())) {
            activityHistoryService.saveWorkflowActivity(profileId, applicationId, stringBuilder.toString());
        }

    }

    private void workFlowConstraints(WorkFlowModel workFlowModel, Boolean isUpdate) {

        AtomicReference<Boolean> isEmptyFlow = new AtomicReference<>(true);
        workFlowModel.getProcessList().forEach(processModel -> {
            if (processModel.getProcessDocsList() != null && !processModel.getProcessDocsList().isEmpty()) {
                isEmptyFlow.set(false);
                processModel.getProcessDocsList().forEach(processDocModel -> {
                    if (!isDHDuplicate && processModel.getSeqType().equals(DOC_HANDLING) && isDuplicateFlow(processDocModel, isUpdate)) {
                        throw internalServerError("Trying to create a duplicate Record, Flow-Seq : DocType-Trans-SendId-RecId == "
                                + processModel.getFlow() + "-" + processModel.getSeqType() + " : "
                                + processDocModel.getDocType() + "-"
                                + processDocModel.getDocTrans() + "-"
                                + processDocModel.getPartnerId() + "-"
                                + processDocModel.getReceiverId());
                    }
                    if (processDocModel.getProcessRulesList() == null || processDocModel.getProcessRulesList().isEmpty()) {
                        if (processModel.getSeqType().equals(DOC_HANDLING)) {
                            throw internalServerError("Please add at least one Rule for "
                                    + processModel.getFlow() + "-" + processModel.getSeqType() + " : "
                                    + processDocModel.getDocType() + "-"
                                    + processDocModel.getDocTrans() + "-"
                                    + processDocModel.getPartnerId() + "-"
                                    + processDocModel.getReceiverId()
                                    + " Transaction");
                        } else {
                            throw internalServerError("Please add at least one Rule for "
                                    + processModel.getFlow() + "-" + processModel.getSeqType() + " : "
                                    + processDocModel.getFileNamePattern()
                                    + " Transaction");
                        }
                    }
                });
            }
        });
        if (isEmptyFlow.get()) {
            throw internalServerError("Please add at least one Flow MFT/DocHandling.");
        }
    }

    private void duplicateMftTransactionsCheck(WorkFlowUIModel workFlowUIModel) {
        if (!isMFTDuplicate) {
            mftDupCheck(workFlowUIModel.getInboundFlow().getMfts().getProcessDocModels(), "Inbound");
            mftDupCheck(workFlowUIModel.getOutboundFlow().getMfts().getProcessDocModels(), "Outbound");
        }

    }

    private void mftDupCheck(List<ProcessDocModel> processDocModels, String flowType) {
        processDocModels.forEach(processDocModel -> {
                    AtomicReference<Boolean> isCurrentRecord = new AtomicReference<>(true);
                    AtomicReference<Boolean> isCurrentRecord2 = new AtomicReference<>(true);
                    processDocModels.forEach(processDocModel1 -> {
                        if (processDocModel.getFileNamePattern().equals(processDocModel1.getFileNamePattern())) {
                            if (isNotNull(processDocModel.getDocType()) && isNotNull(processDocModel1.getDocType())) {
                                if (processDocModel.getDocType().equals(processDocModel1.getDocType())) {
                                    if (!isCurrentRecord.get()) {
                                        throw internalServerError("Trying to create a duplicate " + flowType + " MFT Record, FileName :" + processDocModel.getFileNamePattern());
                                    }
                                    isCurrentRecord.set(false);
                                }
                            } else if (!isNotNull(processDocModel.getDocType()) && !isNotNull(processDocModel1.getDocType())) {
                                if (!isCurrentRecord2.get()) {
                                    throw internalServerError("Trying to create a duplicate " + flowType + " MFT Record, FileName :" + processDocModel.getFileNamePattern());
                                }
                                isCurrentRecord2.set(false);
                            }
                        }
                    });
                }
        );
    }

    /*This Method will Persist the Workflow in DB*/
    private void buildWorkFLow(String partnerProfileId, String applicationProfileId, WorkFlowModel workFlowModel, boolean isFromImport) {

        if (isDuplicateWorkFlow(new PemCopyWorkFlowModel().setDestinationPartner(partnerProfileId).setDestinationApplication(applicationProfileId))) {
            throw new CommunityManagerServiceException(409, "Trying to create duplicate WorkFlow");
        }
        //Save all the rules
        ruleService.saveRules(workFlowModel.getRulesList());
        List<String> dependentProfilesList = isFromImport ? getDependentProfilesList(workFlowModel) : null;
        //Save workFlow
        workFlowModel.getProcessList().forEach(processModel -> {
            if (processModel.getProcessDocsList() != null && !processModel.getProcessDocsList().isEmpty()) {
                String processPrimaryKey = getPrimaryKey.apply(PROCESS_PKEY_PRE_APPEND,
                        PROCESS_PKEY_RANDOM_COUNT);
                processModel.getProcessDocsList().forEach(processDocModel -> {
                    String pdPrimaryKey = getPrimaryKey.apply(PROCESS_DOCS_PKEY_PRE_APPEND,
                            PROCESS_DOCS_PKEY_RANDOM_COUNT);
                    List<String> ruleSeqList = new ArrayList<>();
                    AtomicInteger atomicInteger = new AtomicInteger(1);
                    if (processDocModel.getProcessRulesList() != null) {
                        processDocModel.getProcessRulesList().forEach(processRuleModel -> {
                                    if (isNotNull(processRuleModel.getRuleName())) {
                                        if (isFromImport) {
                                            try {
                                                processRuleModel.setPropertyValue2(getProfilePkIdByProfileId.apply(processModel.getFlow(), processRuleModel.getPropertyValue1(), processRuleModel.getPropertyValue2()));
                                            } catch (CommunityManagerServiceException e) {
                                                if (e.getErrorMessage().equals("Given Application ID is not found.") || e.getErrorMessage().equals("Given Partner ID is not found.")) {
                                                    throw internalServerError("Dependent Application/Partner : " + dependentProfilesList + " not available. create/import profiles end points before you import.");
                                                } else {
                                                    throw internalServerError(e.getErrorMessage());
                                                }
                                            }
                                        }
                                        ruleSeqList.add(processRulesService.save(processRuleModel, pdPrimaryKey, atomicInteger.getAndIncrement()).getPkId());
                                    }
                                }
                        );
                    }
                    processDocsService.save(processDocModel, pdPrimaryKey, ruleSeqList, processPrimaryKey);
                });
                processService.save(processPrimaryKey, processModel, partnerProfileId, applicationProfileId);
            }
        });
    }

    private List<String> getDependentProfilesList(WorkFlowModel workFlowModel) {
        List<String> dependentProfilesList = new ArrayList<>();
        workFlowModel.getProcessList().forEach(processModel -> {
            if (processModel.getProcessDocsList() != null && !processModel.getProcessDocsList().isEmpty()) {
                processModel.getProcessDocsList().forEach(processDocModel -> {
                    if (processDocModel.getProcessRulesList() != null) {
                        processDocModel.getProcessRulesList().forEach(processRuleModel -> {
                                    if (isNotNull(processRuleModel.getRuleName()) &&
                                            isNotNull(processRuleModel.getPropertyValue1()) &&
                                            isNotNull(processRuleModel.getPropertyValue2())) {
                                        dependentProfilesList.add(processRuleModel.getPropertyValue2());
                                    }
                                }
                        );
                    }
                });
            }
        });
        return dependentProfilesList;
    }

    private Boolean isDuplicateFlow(ProcessDocModel processDocModel, Boolean isUpdate) {
        return isUpdate ? processDocsService.findAllByDocHandling(processDocModel).size() > 1 : !processDocsService.findAllByDocHandling(processDocModel).isEmpty();
    }

    private Boolean isDuplicateWorkFlow(PemCopyWorkFlowModel pemCopyWorkFlowModel) {
        if (isNotNull(pemCopyWorkFlowModel.getDestinationApplication())) {
            return !processService.searchByTpPkIdAndAppPkId(pemCopyWorkFlowModel.getDestinationPartner(), pemCopyWorkFlowModel.getDestinationApplication()).stream()
                    .map(ProcessEntity::getApplicationProfile).collect(Collectors.toSet()).isEmpty();
        } else {
            String existingAppPkId = processService.searchByTpPkId(pemCopyWorkFlowModel.getSourcePartner()).get(0).getApplicationProfile();
            return !processService.searchByTpPkIdAndAppPkId(pemCopyWorkFlowModel.getDestinationPartner(), existingAppPkId).stream()
                    .map(ProcessEntity::getApplicationProfile).collect(Collectors.toSet()).isEmpty();
        }
    }

    private String getPropertyValue(String transactionType, String propertyName, String propertyValue) {
        if (isNotNull(propertyValue) && isNotNull(propertyName) && propertyName.equalsIgnoreCase("ProtocolReference")) {
            return transactionType.endsWith(INBOUND) ? applicationService.getNoThrow(propertyValue).getApplicationName() : partnerService.getNoThrow(propertyValue).getTpName();
        }
        return propertyValue;
    }

    private String getDifference(List<ProcessDocModel> newList, List<ProcessDocModel> oldList, String text, String type) {

        StringBuilder stringBuilder = new StringBuilder();
        oldList.forEach(processDocModel -> {
            Optional<ProcessDocModel> processDocModelOptional = newList.stream().filter(processDocModel1 -> processDocModel1.getIndex() == processDocModel.getIndex()).findFirst();
            if (processDocModelOptional.isPresent()) {

                //process docs changes
                Javers javers = JaversBuilder.javers().build();
                Diff diff = javers.compare(processDocModel, processDocModelOptional.get());
                List<ValueChange> changes = new ArrayList<>(diff.getChangesByType(ValueChange.class));
                changes.forEach(valueChange -> {
                    if (!valueChange.getPropertyNameWithPath().contains("processRulesList")) {
                        StringBuilder temp = new StringBuilder();
                        if (type.equalsIgnoreCase(DOC_TYPE)) {
                            temp.append(" " + DOC_TYPE + "=").append(processDocModel.getDocType());
                        } else {
                            temp.append(" " + FILE_NAME + "=").append(processDocModel.getFileNamePattern());
                        }
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
                                    stringBuilderRules.append(" " + RULE_NAME + ":").append(processRuleModel.getRuleName()).append(rulePropertyName);
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
                            if (type.equalsIgnoreCase(DOC_TYPE)) {
                                temp.append(" " + DOC_TRAN_SEN_REC + " = ")
                                        .append(processDocModel.getDocType()).append("-")
                                        .append(processDocModel.getDocTrans()).append("-")
                                        .append(processDocModel.getPartnerId()).append("-")
                                        .append(processDocModel.getReceiverId());
                            } else {
                                temp.append(" " + FILE_NAME + "=").append(processDocModel.getFileNamePattern());
                            }
                            stringBuilder.append(text).append(temp).append(stringBuilderRules);
                        }
                    } else {
                        StringBuilder temp = new StringBuilder();
                        if (type.equalsIgnoreCase(DOC_TYPE)) {
                            temp.append(" " + DOC_TRAN_SEN_REC + " = ")
                                    .append(processDocModel.getDocType()).append("-")
                                    .append(processDocModel.getDocTrans()).append("-")
                                    .append(processDocModel.getPartnerId()).append("- ")
                                    .append(processDocModel.getReceiverId());
                        } else {
                            temp.append(" " + FILE_NAME + "=").append(processDocModel.getFileNamePattern());
                        }
                        stringBuilder.append("Rule deleted in ").append(text).append(temp).append(" " + RULE_NAME + "= ").append(processRuleModel.getRuleName()).append(";");
                    }
                });

                processDocModelOptional.get().getProcessRulesList().forEach(processRuleModel -> {
                    Optional<ProcessRuleModel> processRuleModelOptional = processDocModel.getProcessRulesList().stream().filter((processRuleModel1 -> processRuleModel1.getIndex() == processRuleModel.getIndex())).findFirst();
                    if (!processRuleModelOptional.isPresent()) {
                        StringBuilder temp = new StringBuilder();
                        if (type.equalsIgnoreCase(DOC_TYPE)) {
                            temp.append(" " + DOC_TRAN_SEN_REC + " = ")
                                    .append(processDocModel.getDocType()).append("-")
                                    .append(processDocModel.getDocTrans()).append("-")
                                    .append(processDocModel.getPartnerId()).append("-")
                                    .append(processDocModel.getReceiverId());
                        } else {
                            temp.append(" " + FILE_NAME + "=").append(processDocModelOptional.get().getFileNamePattern());
                        }
                        stringBuilder.append("New Rule Created in ").append(text).append(temp).append(" " + RULE_NAME + " = ").append(processRuleModel.getRuleName()).append("; ");
                    }
                });

            } else {
                StringBuilder temp = new StringBuilder();
                if (type.equalsIgnoreCase(DOC_TYPE)) {
                    temp.append(" " + DOC_TRAN_SEN_REC + " = ")
                            .append(processDocModel.getDocType()).append("-")
                            .append(processDocModel.getDocTrans()).append(" -")
                            .append(processDocModel.getPartnerId()).append("-")
                            .append(processDocModel.getReceiverId());
                } else {
                    temp.append(" " + FILE_NAME + "=").append(processDocModel.getFileNamePattern());
                }
                stringBuilder.append("Transaction deleted in ").append(text).append(temp.toString()).append(";");
            }
        });
        newList.forEach(processDocModel -> {
            Optional<ProcessDocModel> processDocModelOptional = oldList.stream().filter(processDocModel1 -> processDocModel1.getIndex() == processDocModel.getIndex()).findAny();
            if (!processDocModelOptional.isPresent()) {
                StringBuilder temp = new StringBuilder();
                if (type.equalsIgnoreCase(DOC_TYPE)) {
                    temp.append(" " + DOC_TRAN_SEN_REC + " = ")
                            .append(processDocModel.getDocType()).append(" -")
                            .append(processDocModel.getDocTrans()).append("-")
                            .append(processDocModel.getPartnerId()).append("-")
                            .append(processDocModel.getReceiverId());
                } else {
                    temp.append(" " + FILE_NAME + "=").append(processDocModel.getFileNamePattern());
                }
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

    private WorkFlowModel transform1(WorkFlowUIModel workFlowUIModel) {
        WorkFlowModel workFlowModel = new WorkFlowModel();
        workFlowModel.getProcessList().add(new ProcessModel(MFT, INBOUND, workFlowUIModel.getInboundFlow().getMfts().getProcessDocModels()));
        workFlowModel.getProcessList().add(new ProcessModel(DOC_HANDLING, INBOUND, workFlowUIModel.getInboundFlow().getDocHandlings().getProcessDocModels()));
        workFlowModel.getProcessList().add(new ProcessModel(MFT, OUTBOUND, workFlowUIModel.getOutboundFlow().getMfts().getProcessDocModels()));
        workFlowModel.getProcessList().add(new ProcessModel(DOC_HANDLING, OUTBOUND, workFlowUIModel.getOutboundFlow().getDocHandlings().getProcessDocModels()));
        return workFlowModel;
    }

    @Transactional
    public void delete(String profileId, String applicationId, boolean isDelete) {
        deleteWorkFlow(profileId, applicationId, isDelete);
    }

    //TODO: Need to improve the performance
    private void deleteWorkFlow(String profileId, String applicationId, boolean isDelete) {
        List<String> seqIds = processService.searchByTpPkIdAndAppPkId(profileId, applicationId)
                .stream()
                .map(ProcessEntity::getSeqId)
                .collect(Collectors.toList());
        if (seqIds.isEmpty()) {
            throw GlobalExceptionHandler.internalServerError("No Workflow found with the provided information.");
        }
        //TODO : Dont remove this commented lines
//        processDocsService.findAllByProcessRefIn(seqIds).parallelStream()
//                .forEach(processDocsEntity -> {
//                    if (StringUtils.isNotBlank(processDocsEntity.getProcessRuleseq()))
//                        processRulesService.deleteAll(Arrays.asList(processDocsEntity.getProcessRuleseq().split(COMMA)));
//                });
        for (ProcessDocsEntity processDocsEntity : processDocsService.findAllByProcessRefIn(seqIds)) {
            if (StringUtils.isNotBlank(processDocsEntity.getProcessRuleseq()))
                processRulesService.deleteAll(Arrays.asList(processDocsEntity.getProcessRuleseq().split(COMMA)));
        }
        processDocsService.deleteAll(seqIds);
        processService.deleteAll(seqIds);
        if (isDelete) {
            //TODO : Dont remove this commented lines
//            List<WorkFlowActivityHistoryEntity> workFlowActivityHistoryEntities = new ArrayList<>()
//            activityHistoryService.getWorkFlowActivity(profileId + "_" + applicationId).forEach(workFlowActivityHistoryEntity -> {
//                WorkFlowActivityHistoryEntity wfActivityHistoryEntity = new WorkFlowActivityHistoryEntity();
//                BeanUtils.copyProperties(workFlowActivityHistoryEntity, wfActivityHistoryEntity);
//                workFlowActivityHistoryEntities.add(wfActivityHistoryEntity.setProcessRefId(partnerService.get(profileId).getTpName() + "_" + applicationService.get(applicationId).getApplicationName()));
//            });
//            activityHistoryService.saveWorkflowActivity(workFlowActivityHistoryEntities);
            activityHistoryService.saveWorkflowActivity(profileId, applicationId, "WorkFlow Deleted.");
        }

    }

    public WorkFlowUIModel getWorkFlow(String profileId, String applicationId, Boolean isPem, Boolean isCreateFlow) {
        WorkFlowModel workFlowModel = fetchWorkflowFromDB(profileId, applicationId, isPem, false, false, isCreateFlow, false);
        return transform(workFlowModel);
    }

    private WorkFlowUIModel transform(WorkFlowModel workFlowModel) {
        WorkFlowUIModel workFlowUIModel = new WorkFlowUIModel();
        workFlowModel.getProcessList().forEach(processModel -> {
            if (processModel.getFlow().equalsIgnoreCase(INBOUND) && processModel.getSeqType().equalsIgnoreCase(MFT)) {
                workFlowUIModel.getInboundFlow().getMfts().getProcessDocModels().addAll(processModel.getProcessDocsList());
            }
            if (processModel.getFlow().equalsIgnoreCase(INBOUND) && processModel.getSeqType().equalsIgnoreCase(DOC_HANDLING)) {
                workFlowUIModel.getInboundFlow().getDocHandlings().getProcessDocModels().addAll(processModel.getProcessDocsList());
            }
            if (processModel.getFlow().equalsIgnoreCase(OUTBOUND) && processModel.getSeqType().equalsIgnoreCase(MFT)) {
                workFlowUIModel.getOutboundFlow().getMfts().getProcessDocModels().addAll(processModel.getProcessDocsList());
            }
            if (processModel.getFlow().equalsIgnoreCase(OUTBOUND) && processModel.getSeqType().equalsIgnoreCase(DOC_HANDLING)) {
                workFlowUIModel.getOutboundFlow().getDocHandlings().getProcessDocModels().addAll(processModel.getProcessDocsList());
            }
        });
        return workFlowUIModel;
    }

    private WorkFlowModel fetchWorkflowFromDB(String profileId, String applicationId, Boolean isPem, boolean isMasterRulesRequired, boolean isIncludeProfile, boolean isCreateFlow, boolean isExport) {

        WorkFlowModel workFlowModel = new WorkFlowModel();
        List<ProcessEntity> processList;

        if (isPem) {
            profileId = partnerService.getUniquePartner(profileId).getPkId();
            applicationId = applicationService.getUniqueApplication(applicationId).getPkId();
        }

        processList = processService.searchByTpPkIdAndAppPkId(profileId, applicationId);

        if (!processList.isEmpty()) {
            if (isMasterRulesRequired) {
                List<RuleEntity> rulesList = ruleService.findAll();
                List<RuleModel> rulesXMLList = new ArrayList<>();
                rulesList.forEach(rulesEntity -> {
                    RuleModel ruleModel = new RuleModel();
                    ruleModel.setRuleName(rulesEntity.getRuleName());
                    ruleModel.setBusinessProcessId(rulesEntity.getBusinessProcessId());
                    ruleModel.setPropertyName1(rulesEntity.getPropertyName1());
                    ruleModel.setPropertyName2(rulesEntity.getPropertyName2());
                    ruleModel.setPropertyName3(rulesEntity.getPropertyName3());
                    ruleModel.setPropertyName4(rulesEntity.getPropertyName4());
                    ruleModel.setPropertyName5(rulesEntity.getPropertyName5());
                    ruleModel.setPropertyName6(rulesEntity.getPropertyName6());
                    ruleModel.setPropertyName7(rulesEntity.getPropertyName7());
                    ruleModel.setPropertyName8(rulesEntity.getPropertyName8());
                    ruleModel.setPropertyName9(rulesEntity.getPropertyName9());
                    ruleModel.setPropertyName10(rulesEntity.getPropertyName10());
                    ruleModel.setPropertyName11(rulesEntity.getPropertyName11());
                    ruleModel.setPropertyName12(rulesEntity.getPropertyName12());
                    ruleModel.setPropertyName13(rulesEntity.getPropertyName13());
                    ruleModel.setPropertyName14(rulesEntity.getPropertyName14());
                    ruleModel.setPropertyName15(rulesEntity.getPropertyName15());
                    ruleModel.setPropertyName16(rulesEntity.getPropertyName16());
                    ruleModel.setPropertyName17(rulesEntity.getPropertyName17());
                    ruleModel.setPropertyName18(rulesEntity.getPropertyName18());
                    ruleModel.setPropertyName19(rulesEntity.getPropertyName19());
                    ruleModel.setPropertyName20(rulesEntity.getPropertyName20());
                    ruleModel.setPropertyName21(rulesEntity.getPropertyName21());
                    ruleModel.setPropertyName22(rulesEntity.getPropertyName22());
                    ruleModel.setPropertyName23(rulesEntity.getPropertyName23());
                    ruleModel.setPropertyName24(rulesEntity.getPropertyName24());
                    ruleModel.setPropertyName25(rulesEntity.getPropertyName25());
                    rulesXMLList.add(ruleModel);
                });
                workFlowModel.setRulesList(rulesXMLList);
            }

            List<ProcessModel> processXMLList = new ArrayList<>();
            processList.forEach(processEntity -> {
                ProcessModel processModel = new ProcessModel();
                processModel.setFlow(processEntity.getFlow());
                processModel.setSeqType(processEntity.getSeqType());
                List<ProcessDocsEntity> processDocsList = processDocsService.searchByProcessRef(processEntity.getSeqId());
                List<ProcessDocModel> processDocsXMLList = new ArrayList<>();
                IntStream.range(0, processDocsList.size()).forEach(index -> {
                    ProcessDocsEntity processDocsEntity = processDocsList.get(index);
                    ProcessDocModel pdXMLBean = new ProcessDocModel();
                    pdXMLBean.setFileNamePattern(processDocsEntity.getFilenamePattern());
                    pdXMLBean.setDocType(processDocsEntity.getDoctype());
                    pdXMLBean.setVersionNo(processDocsEntity.getVersion());
                    pdXMLBean.setPartnerId(processDocsEntity.getPartnerid());
                    pdXMLBean.setReceiverId(processDocsEntity.getReciverid());
                    pdXMLBean.setDocTrans(processDocsEntity.getDoctrans());
                    pdXMLBean.setIndex(index + 1);
                    List<ProcessRulesEntity> processRulesList;
                    if (isNotNull(processDocsEntity.getProcessRuleseq())) {
                        List<String> seqIds = Arrays.asList(processDocsEntity.getProcessRuleseq().split(COMMA));
                        processRulesList = processRulesService.findByPkIds(seqIds);
                        List<ProcessRuleModel> processRulesXMLList = new ArrayList<>();
                        IntStream.range(0, seqIds.size()).forEach(ruleIndex -> {
                            Optional<ProcessRulesEntity> processRulesEntityOptional = processRulesList.stream().filter(pre -> pre.getPkId().equalsIgnoreCase(seqIds.get(ruleIndex))).findFirst();
                            processRulesEntityOptional.ifPresent(processRulesEntity -> {
                                ProcessRuleModel prXMLBean = convertRuleEntityToProcessRuleModel.apply(processRulesEntity, processEntity.getFlow(), isExport);
                                prXMLBean.setIndex(ruleIndex + 1);
                                processRulesXMLList.add(prXMLBean);
                            });
                        });
                        pdXMLBean.setProcessRulesList(processRulesXMLList);
                    }
                    processDocsXMLList.add(pdXMLBean);
                });
                processModel.setProcessDocsList(processDocsXMLList);
                processXMLList.add(processModel);
            });
            workFlowModel.setProcessList(processXMLList);
            if (isIncludeProfile) {
                workFlowModel.setPartnerInfoModel(managePartnerService.getPartner(profileId));
                workFlowModel.setApplicationInfoModel(manageApplicationService.getApplication(applicationId));
            }
        } else if (isCreateFlow) {
            return new WorkFlowModel();
        } else {
            throw new CommunityManagerServiceException(404, "No workflow exists with the combination provided.");
        }
        return workFlowModel;
    }

    public String exportAll() {

        try {
            StringBuilder rulesExportBuffer = new StringBuilder(DataFlowMapper.getHeader());
            List<DataFlowMapper> dataFlows = reportRepository.exportAllWorkFlows(partnerService.getPartnersListAssignedToUserMap(userUtilityService.getUserOrRole(TRUE))
                    .stream()
                    .map(CommunityManagerKeyValueModel::getKey)
                    .collect(Collectors.toList()));
            dataFlows.forEach(dataFlowMapper -> rulesExportBuffer.append(dataFlowMapper.delimiterString()));

            return rulesExportBuffer.toString();
        } catch (Exception e) {
            logger.error("Exception in WorkFlow Reports ", e);
            throw internalServerError("May -DdbType not matched with actual DB, Please contact Admin Team.");
        }

    }

    public Page<WorkFlowActivityHistoryEntity> getHistory(String processRefId, Pageable pageable) {
        return activityHistoryService.getWorkFlowHistory(processRefId, pageable);
    }

    public Page<DataFlowMapper> findAllWorkFlows(DataFlowModel dataFlowModel, Pageable pageable, Boolean isOnlyFlows) {
        try {
            String userRole = userUtilityService.getUserOrRole(FALSE);
            if (userRole.equalsIgnoreCase(AuthoritiesConstants.SUPER_ADMIN) || userRole.equalsIgnoreCase(AuthoritiesConstants.ADMIN)) {
                return reportRepository.findAllWorkFlows(dataFlowModel, pageable, independentService.getActiveProfile(), isOnlyFlows,
                        new ArrayList<>()
                );
            } else {
                return reportRepository.findAllWorkFlows(dataFlowModel, pageable, independentService.getActiveProfile(), isOnlyFlows,
                        partnerService.getPartnersListAssignedToUserMap(userUtilityService.getUserOrRole(TRUE))
                                .stream()
                                .map(CommunityManagerKeyValueModel::getKey)
                                .collect(Collectors.toList())
                );
            }

        } catch (CommunityManagerServiceException cme) {
            logger.error("Exception in WorkFlow Reports CME ", cme);
            throw internalServerError(cme.getErrorMessage());
        } catch (Exception e) {
            logger.error("Exception in WorkFlow Reports ", e);
            throw internalServerError("There are some mismatches in Database Entity, please contact system admin.");
        }
    }

    @Transactional
    public List<DataFlowMapper> editAndProduceRule(PemContentFileTypeModel pemContentFileTypeModel) {
        isNullThrowError.apply(pemContentFileTypeModel.getPartnerProfile(), "partnerProfile");
        Optional.ofNullable(Protocol.findProtocol(isNullThrowError.apply(pemContentFileTypeModel.getPartnerProtocol(), "partnerProtocol")))
                .orElseThrow(() -> internalServerError("Provided Protocol(" + pemContentFileTypeModel.getPartnerProtocol() + ") is not implemented."));
        List<DataFlowMapper> dataFlowMappers = new ArrayList<>();
        List<PemFileTypeModel> pemFileTypeModels = pemContentFileTypeModel.getContent();
        try {
            if (!pemFileTypeModels.isEmpty()) {
                pemFileTypeModels.forEach(pemFileTypeModel -> {
                    ProcessDocsEntity processDocsEntity = processDocsService.findByIdThrowIfNotFound(isNullThrowError.apply(pemFileTypeModel.getPkId(), "PkId"));
                    ProcessEntity processEntity = processService.findById(processDocsEntity.getProcessRef());
                    List<ProcessRulesEntity> processRulesEntities = processRulesService.findByPkIds(Arrays.asList(processDocsEntity.getProcessRuleseq().split(COMMA)));
                    if (!processRulesEntities.isEmpty()) {
                        String pemTempPkId = getPrimaryKey.apply("PT", PROCESS_PKEY_RANDOM_COUNT);
                        pemTempRepository.save(new PemTempEntity()
                                .setPkId(pemTempPkId)
                                .setProcessDocPkId(processDocsEntity.getPkId())
                                .setPartnerProfile(pemFileTypeModel.getPartner())
                                .setApplicationProfile(pemFileTypeModel.getApplication())
                                .setDocType(pemFileTypeModel.getDocType())
                                .setFileName(pemFileTypeModel.getFileName())
                                .setFlowType(pemFileTypeModel.getFlowType())
                                .setReceiverId(pemFileTypeModel.getReceiverId())
                                .setSenderId(pemFileTypeModel.getSenderId())
                                .setSeqType(processEntity.getSeqType())
                                .setSeqId(processEntity.getSeqId())
                                .setTrans(pemFileTypeModel.getTransaction()));
                        List<String> seqIds = Arrays.asList(processDocsEntity.getProcessRuleseq().split(COMMA));
                        List<ProcessRulesEntity> processRulesList = processRulesService.findByPkIds(seqIds);
                        IntStream.range(0, seqIds.size()).forEach(ruleIndex -> {
                            Optional<ProcessRulesEntity> processRulesEntityOptional = processRulesList
                                    .stream()
                                    .filter(ascend -> ascend.getPkId().equalsIgnoreCase(seqIds.get(ruleIndex)))
                                    .findFirst();
                            processRulesEntityOptional.ifPresent(processRulesEntity -> {
                                boolean isProtocol;
                                RuleModel ruleModel;
                                try {
                                    ruleModel = ruleService.get(processRulesEntity.getRuleId());
                                    logger.debug("Rule Id {}", ruleModel.getRuleId());
                                    isProtocol = isNotNull(ruleModel.getPropertyName1()) && ruleModel.getPropertyName1().equalsIgnoreCase("Protocol");
                                } catch (Exception e) {
                                    logger.debug("Given RuleId  {}", processRulesEntity.getRuleId());
                                    throw internalServerError("The selected Rule is not Available In Rules");
                                }
                                DataFlowMapper dataFlowMapper = new DataFlowMapper()
                                        .setPkId(pemTempPkId)
                                        .setProcessDocPkId(processDocsEntity.getPkId())
                                        .setApplicationProfile(pemFileTypeModel.getApplication())
                                        .setPartnerProfile(pemFileTypeModel.getPartner())
                                        .setFlow(pemFileTypeModel.getFlowType())
                                        .setFileName(pemFileTypeModel.getFileName())
                                        .setDocType(pemFileTypeModel.getDocType())
                                        .setPartnerId(pemFileTypeModel.getSenderId())
                                        .setReceiverId(pemFileTypeModel.getReceiverId())
                                        .setTransaction(pemFileTypeModel.getTransaction())
                                        .setSeqType(processEntity.getSeqType())
                                        .setProcessRulePkId(processRulesEntity.getPkId())
                                        .setRuleName(processRulesEntity.getRuleName())
                                        .setRuleProperty1(getProtocolName(pemFileTypeModel.getFlowType(),
                                                isProtocol,
                                                processRulesEntity.getPropertyName1(),
                                                pemContentFileTypeModel.getPartnerProtocol()))
//                                    .setRuleProperty1(getMapNamesOrName(processRulesEntity.getRuleName(), processRulesEntity.getPropertyName1()))
//                                    .setRuleProperty2(getProfileNamesByProtocolAsString(pemFileTypeModel.getFlowType(),
//                                            processRulesEntity.getRuleId(),
//                                            processRulesEntity.getPropertyName1(),
//                                            processRulesEntity.getPropertyName2()))
//                                        .setRuleProperty2(getProfileName.apply(pemFileTypeModel.getFlowType(),
//                                                processRulesEntity.getRuleId(),
//                                                processRulesEntity.getPropertyName2()))
                                        .setRuleProperty2(getProfileName(pemFileTypeModel.getFlowType(),
                                                isProtocol,
                                                processRulesEntity.getPropertyName2(),
                                                pemContentFileTypeModel.getPartnerProfile()))
                                        .setRuleProperty3(processRulesEntity.getPropertyName3())
                                        .setRuleProperty4(processRulesEntity.getPropertyName4())
                                        .setRuleProperty5(processRulesEntity.getPropertyName5())
                                        .setRuleProperty6(processRulesEntity.getPropertyName6())
                                        .setRuleProperty7(processRulesEntity.getPropertyName7())
                                        .setRuleProperty8(processRulesEntity.getPropertyName8())
                                        .setRuleProperty9(processRulesEntity.getPropertyName9())
                                        .setRuleProperty10(processRulesEntity.getPropertyName10())
                                        .setRuleProperty11(processRulesEntity.getPropertyName11())
                                        .setRuleProperty12(processRulesEntity.getPropertyName12())
                                        .setRuleProperty13(processRulesEntity.getPropertyName13())
                                        .setRuleProperty14(processRulesEntity.getPropertyName14())
                                        .setRuleProperty15(processRulesEntity.getPropertyName15());
                                dataFlowMappers.add(dataFlowMapper);
                            });
                        });
                    } else {
                        if (processEntity.getSeqType().equals(MFT)) {
                            throw internalServerError("Selected MFT Flow Should have at lease one rule  : FileName = " + processDocsEntity.getFilenamePattern());
                        } else {
                            throw internalServerError("Selected Doc-Handling Flow Should have at lease one rule  : SenderId-ReceiverId-Transaction-DocType = "
                                    + processDocsEntity.getPartnerid() + "-"
                                    + processDocsEntity.getReciverid() + "-"
                                    + processDocsEntity.getDoctrans() + "-"
                                    + processDocsEntity.getDoctype());
                        }
                    }
                });
            } else {
                throw internalServerError("Please provide proper data");
            }
        } catch (CommunityManagerServiceException ce) {
            logger.error("Error : ", ce);
            throw internalServerError(ce.getErrorMessage());
        } catch (Exception e) {
            logger.error("Error : ", e);
            throw internalServerError(e.getMessage());
        }
        return dataFlowMappers;
    }

    //Dont Remove need to use this in next PEM Releases
    private String getProfileNamesByProtocolAsString(String flow, String ruleId, String propertyValue1, String propertyValue2) {
        if (isNotNull(flow) && isNotNull(ruleId) && isNotNull(propertyValue1) && ruleService.get(ruleId).getPropertyName1().equalsIgnoreCase("Protocol")) {
            if (flow.equalsIgnoreCase(INBOUND)) {
                return applicationService.finaAllByProtocol(propertyValue1)
                        .stream()
                        .map(ApplicationEntity::getApplicationName)
                        .collect(Collectors.joining("|"));
            } else {
                return partnerService.findAllByTpProtocol(propertyValue1)
                        .stream()
                        .map(PartnerEntity::getTpName)
                        .collect(Collectors.joining("|"));
            }
        }
        return propertyValue2;
    }

    private String getProtocolName(String flow, boolean isProtocol, String propertyValue, String partnerProtocol) {
        return (isNotNull(flow) && isNotNull(propertyValue) && flow.equalsIgnoreCase(OUTBOUND) && isProtocol) ? partnerProtocol : propertyValue;
    }

    private String getProfileName(String flow, boolean isProtocol, String propertyValue, String profileName) {
        if (isNotNull(flow) && isNotNull(propertyValue) && isProtocol) {
            if (flow.equalsIgnoreCase(INBOUND)) {
                return applicationService.getNoThrow(propertyValue).getApplicationName();
            } else {
                return profileName;
            }
        }
        return propertyValue;
    }

    private final TriFunction<String, String, String, String> getProfileId = (flow, ruleId, propertyValue) -> {
        if (isNotNull(flow) && isNotNull(ruleId) && isNotNull(propertyValue) && ruleService.get(ruleId).getPropertyName1().equalsIgnoreCase("Protocol")) {
            if (flow.equalsIgnoreCase(INBOUND)) {
                return applicationService.getNoThrow(propertyValue).getApplicationId();
            } else {
                return partnerService.getNoThrow(propertyValue).getTpId();
            }
        }
        return propertyValue;
    };

    private final TriFunction<String, String, String, String> getProfilePkId = (flow, propertyValue1, propertyValue2) -> {

        if (isNotNull(flow) && isNotNull(propertyValue1) && isNotNull(propertyValue2) && Optional.ofNullable(Protocol.findProtocol(propertyValue1)).isPresent()) {
            if (flow.equalsIgnoreCase(INBOUND)) {
                return applicationService.getUniqueApplication(propertyValue2).getPkId();
            } else {
                return partnerService.getUniquePartner(propertyValue2).getPkId();
            }
        }
        return propertyValue2;
    };

    private final TriFunction<String, String, String, String> getProfilePkIdByProfileId = (flow, propertyValue1, propertyValue2) -> {

        if (isNotNull(flow) && isNotNull(propertyValue1) && isNotNull(propertyValue2) && Optional.ofNullable(Protocol.findProtocol(propertyValue1)).isPresent()) {
            if (flow.equalsIgnoreCase(INBOUND)) {
                return applicationService.find(propertyValue2).orElseThrow(() -> internalServerError("Given Application ID is not found.")).getPkId();
            } else {
                return partnerService.find(propertyValue2).orElseThrow(() -> internalServerError("Given Partner ID is not found.")).getPkId();
            }
        }
        return propertyValue2;
    };

    // PEM : Calculator
    public CommunityManagerKeyValueModel calculator(Map<String, String> hashMap) {
        ArrayList<String> list = new ArrayList<>();
        for (Map.Entry<String, String> map : hashMap.entrySet()) {
            list.add(isNotNull(map.getValue()) ? map.getValue() : "0");
        }
        String expression = String.join("", list);
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        try {
            return new CommunityManagerKeyValueModel("output", engine.eval(expression).toString());
        } catch (ScriptException e) {
            throw internalServerError("Please provide proper Mathematical Expression");
        }
    }

    // PEM : Remove File Types from a workflow for PEM Application
    @Transactional
    public void removeFileTypes(PemContentWorkFlowModel pemContentWorkFlowModel) {

        processDocsService.findAllByIds(pemContentWorkFlowModel.getContent().stream().map(DataFlowMapper::getProcessDocPkId).collect(Collectors.toList())).forEach(processDocsEntity -> {
            processRulesService.deleteAll(Arrays.asList(processDocsEntity.getProcessRuleseq().split(",")));
            processDocsService.delete(processDocsEntity);
        });

    }

    // PEM : Remove File Types from a workflow for PEM Application
    @Transactional
    public void removeFileTypesBase64(PemStringDataModel pemStringDataModel) {
        try {
            if (isNotNull(pemStringDataModel.getData())) {
                removeFileTypes(objectMapper.readValue(Base64.decodeBase64(pemStringDataModel.getData()), PemContentWorkFlowModel.class));
            } else {
                throw internalServerError("Please provide data to process request");
            }
        } catch (IOException e) {
            throw internalServerError(e.getMessage());
        }
    }

    // PEM : ADD File Types to a workflow for PEM Application
    @Transactional
    public void addFileTypesBase64(PemStringDataModel pemStringDataModel) {
        try {
            if (isNotNull(pemStringDataModel.getData())) {
                addFileTypes(objectMapper.readValue(Base64.decodeBase64(pemStringDataModel.getData()), PemContentWorkFlowModel.class));
            } else {
                throw internalServerError("Please provide data to process request");
            }
        } catch (IOException e) {
            throw internalServerError(e.getMessage());
        }
    }

    // PEM : ADD File Types to a workflow for PEM Application
    @Transactional
    public void addFileTypes(PemContentWorkFlowModel pemContentWorkFlowModel) {

        WorkFlowUIModel workFlowUIModel = getWorkFlow(pemContentWorkFlowModel.getPartnerProfile(), pemContentWorkFlowModel.getApplicationProfile(), true, true);

        List<String> existingInBoundMftNames = workFlowUIModel.getInboundFlow()
                .getMfts()
                .getProcessDocModels()
                .stream()
                .filter(processDocModel -> isNotNull(processDocModel.getFileNamePattern()))
                .map(ProcessDocModel::getFileNamePattern)
                .collect(Collectors.toList());
        List<String> existingOutBoundMftNames = workFlowUIModel.getOutboundFlow()
                .getMfts()
                .getProcessDocModels()
                .stream()
                .filter(processDocModel -> isNotNull(processDocModel.getFileNamePattern()))
                .map(ProcessDocModel::getFileNamePattern)
                .collect(Collectors.toList());
        List<String> existingInBoundDOCHNames = workFlowUIModel.getInboundFlow()
                .getDocHandlings()
                .getProcessDocModels()
                .stream()
                .map(processDocModel -> processDocModel.getPartnerId() + processDocModel.getReceiverId() + processDocModel.getDocTrans() + processDocModel.getDocType())
                .collect(Collectors.toList());
        List<String> existingOutBoundDOCHNames = workFlowUIModel.getOutboundFlow()
                .getDocHandlings()
                .getProcessDocModels()
                .stream()
                .map(processDocModel -> processDocModel.getPartnerId() + processDocModel.getReceiverId() + processDocModel.getDocTrans() + processDocModel.getDocType())
                .collect(Collectors.toList());

        List<ProcessDocModel> inBoundMftModels = new ArrayList<>();
        List<ProcessDocModel> inBoundDOCHModels = new ArrayList<>();
        List<ProcessDocModel> outBoundMftModels = new ArrayList<>();
        List<ProcessDocModel> outBoundDOCHModels = new ArrayList<>();
        //TODO - You must work here

        Map<String, PemTempEntity> stringPemTempEntityMap = pemTempRepository.findAllByPkIdIn(pemContentWorkFlowModel.getContent()
                .stream()
                .map(DataFlowMapper::getPkId)
                .collect(Collectors.toList())
        ).stream().collect(Collectors.toMap(PemTempEntity::getProcessDocPkId, o -> o));

        processDocsService.findAllByIds(new ArrayList<>(stringPemTempEntityMap.keySet())).forEach(processDocsEntity -> {
            //This was changed on Alok.J Request
            PemTempEntity pemTempEntity = Optional.of(stringPemTempEntityMap.get(processDocsEntity.getPkId())).orElseThrow(() -> GlobalExceptionHandler.internalServerError("Please call the edit and produce rule API before this API call."));
            ProcessDocModel processDocModel = new ProcessDocModel();
            List<ProcessRuleModel> processRuleModels = new ArrayList<>();

            ProcessEntity processEntity = processService.findById(processDocsEntity.getProcessRef());
            if (processEntity.getFlow().equalsIgnoreCase(INBOUND) &&
                    processEntity.getSeqType().equalsIgnoreCase(MFT)
                    && existingInBoundMftNames.contains(pemTempEntity.getFileName())) {
                logger.info("Trying to Add Duplicate Inbound MFT, File Name : {}", pemTempEntity.getFileName());
            } else if (processEntity.getFlow().equalsIgnoreCase(OUTBOUND) &&
                    processEntity.getSeqType().equalsIgnoreCase(MFT)
                    && existingOutBoundMftNames.contains(pemTempEntity.getFileName())) {
                logger.info("Trying to Add Duplicate Outbound MFT, File Name : {}", pemTempEntity.getFileName());
            } else if (processEntity.getFlow().equalsIgnoreCase(INBOUND) &&
                    processEntity.getSeqType().equalsIgnoreCase(DOC_HANDLING)
                    && existingInBoundDOCHNames.contains(pemTempEntity.getSenderId() + pemTempEntity.getReceiverId() + pemTempEntity.getTrans() + pemTempEntity.getDocType())) {
                logger.info("Trying to Add Duplicate InBound DocHandling, SenderId: {}, ReceiverId: {}, Transaction: {}, DocType: {}", pemTempEntity.getSenderId(), pemTempEntity.getReceiverId(), pemTempEntity.getTrans(), pemTempEntity.getDocType());
            } else if (processEntity.getFlow().equalsIgnoreCase(OUTBOUND) &&
                    processEntity.getSeqType().equalsIgnoreCase(DOC_HANDLING)
                    && existingOutBoundDOCHNames.contains(pemTempEntity.getSenderId() + pemTempEntity.getReceiverId() + pemTempEntity.getTrans() + pemTempEntity.getDocType())) {
                logger.info("Trying to Add Duplicate OutBound DocHandling, SenderId: {}, ReceiverId: {}, Transaction: {}, DocType: {}", pemTempEntity.getSenderId(), pemTempEntity.getReceiverId(), pemTempEntity.getTrans(), pemTempEntity.getDocType());
            } else {
                processRulesService.findByPkIds(Arrays.asList(processDocsEntity.getProcessRuleseq().split(","))).forEach(processRulesEntity -> {
                    pemContentWorkFlowModel.getContent().forEach(dataFlowMapper -> {
                        if (dataFlowMapper.getProcessRulePkId().equalsIgnoreCase(processRulesEntity.getPkId())) {
                            ProcessRuleModel processRuleModel = convertDataFlowMapperToProcessRuleModel.apply(processRulesEntity, dataFlowMapper, true);
                            processRuleModels.add(processRuleModel);
                        }
                    });

                });
                processDocModel.setProcessRulesList(processRuleModels);
                processDocModel.setDocType(pemTempEntity.getDocType());
                processDocModel.setDocTrans(pemTempEntity.getTrans());
                processDocModel.setFileNamePattern(pemTempEntity.getFileName());
                processDocModel.setPartnerId(pemTempEntity.getSenderId());
                processDocModel.setReceiverId(pemTempEntity.getReceiverId());
                processDocModel.setProcessRuleSeq(processDocsEntity.getProcessRuleseq());
                processDocModel.setVersionNo(processDocsEntity.getVersion());

                if (processEntity.getFlow().equalsIgnoreCase(INBOUND)) {
                    if (processEntity.getSeqType().equalsIgnoreCase(MFT)) {
                        inBoundMftModels.add(processDocModel);
                    } else {
                        inBoundDOCHModels.add(processDocModel);
                    }
                } else {
                    if (processEntity.getSeqType().equalsIgnoreCase(MFT)) {
                        outBoundMftModels.add(processDocModel);
                    } else {
                        outBoundDOCHModels.add(processDocModel);
                    }
                }
            }
            /*We should do delete the entity once we get the values for workflow creation */
            pemTempRepository.delete(pemTempEntity);
        });
        workFlowUIModel.getInboundFlow().getMfts().getProcessDocModels().addAll(inBoundMftModels);
        workFlowUIModel.getInboundFlow().getDocHandlings().getProcessDocModels().addAll(inBoundDOCHModels);
        workFlowUIModel.getOutboundFlow().getMfts().getProcessDocModels().addAll(outBoundMftModels);
        workFlowUIModel.getOutboundFlow().getDocHandlings().getProcessDocModels().addAll(outBoundDOCHModels);

        createWorkflow(workFlowUIModel, partnerService.getUniquePartner(pemContentWorkFlowModel.getPartnerProfile()).getPkId(), applicationService.getUniqueApplication(pemContentWorkFlowModel.getApplicationProfile()).getPkId());
    }

    //For Multiple copy workFLow Service : Get Applied rules
    public CommunityMangerModel<CommunityManagerMapModel<String, ProcessRuleModel>> getAppliedRules(List<String> pkIdList) {
        List<CommunityManagerMapModel<String, ProcessRuleModel>> communityManagerMapModels = new ArrayList<>();
        if (!pkIdList.isEmpty()) {
            processDocsService.findAllByIds(pkIdList).forEach(processDocsEntity -> {
                if (isNotNull(processDocsEntity.getProcessRuleseq())) {
                    communityManagerMapModels.add(new CommunityManagerMapModel<>(processDocsEntity.getPkId(),
                            processRulesService.findByPkIds(Arrays.asList(processDocsEntity.getProcessRuleseq().split(",")))
                                    .stream()
                                    .map(processRulesEntity -> convertRuleEntityToProcessRuleModel.apply(processRulesEntity, null, false)).collect(Collectors.toList())));
                }
            });
        }
        return new CommunityMangerModel<>(communityManagerMapModels);
    }

    @Transactional
    public void deleteAllWorkflowsByApplicationIds(CommunityMangerModel<CommunityManagerNameModel> communityMangerModel) {
        if (communityMangerModel.getContent() != null && !communityMangerModel.getContent().isEmpty()) {
            final List<String> applicationPkIds = communityMangerModel.getContent().stream().map(CommunityManagerNameModel::getName).filter(CommonFunctions::isNotNull).collect(Collectors.toList());
            processService.findByApplicationProfilesIn(applicationPkIds).forEach(processEntity -> {
                processDocsService.searchByProcessRef(processEntity.getSeqId()).forEach(processDocsEntity -> {
                    if (isNotNull(processDocsEntity.getProcessRuleseq())) {
                        processRulesService.deleteAll(Arrays.asList(processDocsEntity.getProcessRuleseq().split(",")));
                    }
                    processDocsService.delete(processDocsEntity);
                });
                processService.delete(processEntity);
            });
        } else {
            logger.info("deleteAllWorkflowsByApplicationIds(), Application PKID list may null or empty");
        }
    }

    @Transactional
    public void deleteAllWorkflowsByPartnerIds(CommunityMangerModel<CommunityManagerNameModel> communityMangerModel) {
        if (communityMangerModel.getContent() != null && !communityMangerModel.getContent().isEmpty()) {
            final List<String> partnerPkIds = communityMangerModel.getContent()
                    .stream()
                    .map(CommunityManagerNameModel::getName)
                    .filter(CommonFunctions::isNotNull)
                    .collect(Collectors.toList());
            if (!partnerPkIds.isEmpty()) {
                processService.findByPartnerProfileIn(partnerPkIds).forEach(processEntity -> {
                    processDocsService.searchByProcessRef(processEntity.getSeqId()).forEach(processDocsEntity -> {
                        if (isNotNull(processDocsEntity.getProcessRuleseq())) {
                            processRulesService.deleteAll(Arrays.asList(processDocsEntity.getProcessRuleseq().split(",")));
                        }
                        processDocsService.delete(processDocsEntity);
                    });
                    processService.delete(processEntity);
                });
            }
        } else {
            logger.info("deleteAllWorkflowsByPartnerIds(), Partner PKID list may null or empty");
        }
    }

    private final TriFunction<ProcessRulesEntity, String, Boolean, ProcessRuleModel> convertRuleEntityToProcessRuleModel = (processRulesEntity, flow, isExport) -> {
        ProcessRuleModel processRuleModel = new ProcessRuleModel();
        processRuleModel.setRuleName(processRulesEntity.getRuleName());
        processRuleModel.setPropertyValue1(processRulesEntity.getPropertyName1());

        if (isNotNull(flow)) {
            processRuleModel.setPropertyValue2(isExport ? getProfileId.apply(flow, processRulesEntity.getRuleId(), processRulesEntity.getPropertyName2()) :
                    processRulesEntity.getPropertyName2());
        } else {
            processRuleModel.setPropertyValue2(processRulesEntity.getPropertyName2());
        }

        processRuleModel.setPropertyValue3(processRulesEntity.getPropertyName3());
        processRuleModel.setPropertyValue4(processRulesEntity.getPropertyName4());
        processRuleModel.setPropertyValue5(processRulesEntity.getPropertyName5());
        processRuleModel.setPropertyValue6(processRulesEntity.getPropertyName6());
        processRuleModel.setPropertyValue7(processRulesEntity.getPropertyName7());
        processRuleModel.setPropertyValue8(processRulesEntity.getPropertyName8());
        processRuleModel.setPropertyValue9(processRulesEntity.getPropertyName9());
        processRuleModel.setPropertyValue10(processRulesEntity.getPropertyName10());
        processRuleModel.setPropertyValue11(processRulesEntity.getPropertyName11());
        processRuleModel.setPropertyValue12(processRulesEntity.getPropertyName12());
        processRuleModel.setPropertyValue13(processRulesEntity.getPropertyName13());
        processRuleModel.setPropertyValue14(processRulesEntity.getPropertyName14());
        processRuleModel.setPropertyValue15(processRulesEntity.getPropertyName15());
        processRuleModel.setPropertyValue16(processRulesEntity.getPropertyName16());
        processRuleModel.setPropertyValue17(processRulesEntity.getPropertyName17());
        processRuleModel.setPropertyValue18(processRulesEntity.getPropertyName18());
        processRuleModel.setPropertyValue19(processRulesEntity.getPropertyName19());
        processRuleModel.setPropertyValue20(processRulesEntity.getPropertyName20());
        processRuleModel.setPropertyValue21(processRulesEntity.getPropertyName21());
        processRuleModel.setPropertyValue22(processRulesEntity.getPropertyName22());
        processRuleModel.setPropertyValue23(processRulesEntity.getPropertyName23());
        processRuleModel.setPropertyValue24(processRulesEntity.getPropertyName24());
        processRuleModel.setPropertyValue25(processRulesEntity.getPropertyName25());
        return processRuleModel;
    };

    private final TriFunction<ProcessRulesEntity, DataFlowMapper, Boolean, ProcessRuleModel> convertDataFlowMapperToProcessRuleModel = (processRulesEntity, dataFlowMapper, isPem) -> {
        ProcessRuleModel processRuleModel = new ProcessRuleModel();
        processRuleModel.setRuleName(dataFlowMapper.getRuleName());
        processRuleModel.setPropertyValue1(dataFlowMapper.getRuleProperty1());

        if (isNotNull(dataFlowMapper.getFlow())) {
            String prop = isPem ? getProfilePkId.apply(dataFlowMapper.getFlow(), dataFlowMapper.getRuleProperty1(), dataFlowMapper.getRuleProperty2()) :
                    dataFlowMapper.getRuleProperty2();
            processRuleModel.setPropertyValue2(prop);
        } else {
            processRuleModel.setPropertyValue2(dataFlowMapper.getRuleProperty2());
        }
        processRuleModel.setPropertyValue3(dataFlowMapper.getRuleProperty3());
        processRuleModel.setPropertyValue4(dataFlowMapper.getRuleProperty4());
        processRuleModel.setPropertyValue5(dataFlowMapper.getRuleProperty5());
        processRuleModel.setPropertyValue6(dataFlowMapper.getRuleProperty6());
        processRuleModel.setPropertyValue7(dataFlowMapper.getRuleProperty7());
        processRuleModel.setPropertyValue8(dataFlowMapper.getRuleProperty8());
        processRuleModel.setPropertyValue9(dataFlowMapper.getRuleProperty9());
        processRuleModel.setPropertyValue10(dataFlowMapper.getRuleProperty10());
        processRuleModel.setPropertyValue11(dataFlowMapper.getRuleProperty11());
        processRuleModel.setPropertyValue12(dataFlowMapper.getRuleProperty12());
        processRuleModel.setPropertyValue13(dataFlowMapper.getRuleProperty13());
        processRuleModel.setPropertyValue14(dataFlowMapper.getRuleProperty14());
        processRuleModel.setPropertyValue15(dataFlowMapper.getRuleProperty15());
        processRuleModel.setPropertyValue16(dataFlowMapper.getRuleProperty16());
        processRuleModel.setPropertyValue17(dataFlowMapper.getRuleProperty17());
        processRuleModel.setPropertyValue18(dataFlowMapper.getRuleProperty18());
        processRuleModel.setPropertyValue19(dataFlowMapper.getRuleProperty19());
        processRuleModel.setPropertyValue20(dataFlowMapper.getRuleProperty20());
        processRuleModel.setPropertyValue21(dataFlowMapper.getRuleProperty21());
        processRuleModel.setPropertyValue22(dataFlowMapper.getRuleProperty22());
        processRuleModel.setPropertyValue23(dataFlowMapper.getRuleProperty23());
        processRuleModel.setPropertyValue24(dataFlowMapper.getRuleProperty24());
        processRuleModel.setPropertyValue25(dataFlowMapper.getRuleProperty25());
        return processRuleModel;
    };


    private String getMapNamesOrName(String ruleName, String propertyValue) {
        if (isNotNull(ruleName) && ruleName.toLowerCase().contains("translation")) {
            return String.join("|", mapRepository.findAllByOrderByMapIdentity().orElse(new ArrayList<>()));
        }
        return propertyValue;
    }

}


