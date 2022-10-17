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

package com.pe.pcm.reports;

import com.pe.pcm.b2b.B2BApiService;
import com.pe.pcm.b2b.fileprocess.RemoteProcessFlowModel;
import com.pe.pcm.common.CommunityManagerKeyValueModel;
import com.pe.pcm.common.CommunityMangerModel;
import com.pe.pcm.constants.AuthoritiesConstants;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.miscellaneous.IndependentService;
import com.pe.pcm.miscellaneous.UserUtilityService;
import com.pe.pcm.partner.PartnerService;
import com.pe.pcm.reports.entity.TransInfoDEntity;
import com.pe.pcm.reports.entity.TransferInfoEntity;
import com.pe.pcm.settings.FileSchedulerConfigService;
import com.pe.pcm.workflow.pem.PemFileTypeModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;
import static com.pe.pcm.miscellaneous.CommonQueryPredicate.getPredicate;
import static com.pe.pcm.utils.CommonFunctions.*;
import static com.pe.pcm.utils.PCMConstants.*;
import static com.pe.pcm.utils.ReportsCommonFunctions.getGroupByColumnQuery;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * @author Kiran Reddy.
 */
@Service
public class FileTransferService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileTransferService.class);

    private final TransferInfoRepository transferInfoRepository;
    private final TransInfoDRepository transInfoDRepository;
    private final B2BApiService b2BApiService;
    private final ReportRepository reportRepository;
    private final IndependentService independentService;
    private final UserUtilityService userUtilityService;
    private final PartnerService partnerService;
    private final TransInfoDService transInfoDService;
    private final TransInfoDStagingRepository transInfoDStagingRepository;
    private final FileSchedulerConfigService fileSchedulerConfigService;

    @Autowired
    public FileTransferService(TransferInfoRepository transferInfoRepository,
                               TransInfoDRepository transInfoDRepository,
                               B2BApiService b2BApiService,
                               ReportRepository reportRepository,
                               IndependentService independentService,
                               UserUtilityService userUtilityService,
                               PartnerService partnerService,
                               TransInfoDService transInfoDService,
                               TransInfoDStagingRepository transInfoDStagingRepository,
                               FileSchedulerConfigService fileSchedulerConfigService) {
        this.transferInfoRepository = transferInfoRepository;
        this.transInfoDRepository = transInfoDRepository;
        this.b2BApiService = b2BApiService;
        this.reportRepository = reportRepository;
        this.independentService = independentService;
        this.userUtilityService = userUtilityService;
        this.partnerService = partnerService;
        this.transInfoDService = transInfoDService;
        this.transInfoDStagingRepository = transInfoDStagingRepository;
        this.fileSchedulerConfigService = fileSchedulerConfigService;
    }

    public Page<TransferInfoEntity> search(TransferInfoModel transferInfoModel, Pageable pageable, boolean isPEM) {
        throwIfNullOrEmpty(transferInfoModel.getDateRangeStart(), "dateRangeStart");
        throwIfNullOrEmpty(transferInfoModel.getDateRangeEnd(), "dateRangeEnd");

        AtomicBoolean stagingSearch = new AtomicBoolean(FALSE);
        fileSchedulerConfigService.getOnlyFileSchConfEntity().ifPresent(fileSchedulerConfigEntity -> {
            if (Timestamp.valueOf(convertToLocalLocale(transferInfoModel.getDateRangeStart()))
                    .compareTo(
                            minusDays(
                                    new java.sql.Date(System.currentTimeMillis()),
                                    Integer.parseInt(fileSchedulerConfigEntity.getFileAge())
                            )
                    ) < 0) {
                stagingSearch.set(TRUE);
            }
        });
        if (stagingSearch.get()) {
            LOGGER.info("Loading the data from both TRANSINFO and TRANSINFOD");
            List<String> partnerNames = partnerService.getPartnersByCurrentUser(FALSE);
            return reportRepository.transInfoAndTransInfoD(transferInfoModel, pageable, partnerNames, isPEM);
        } else {
            LOGGER.info("Loading data from TRANSINFO");
            return transferInfo(transferInfoModel, pageable, isPEM);
        }
    }

    public Page<TransferInfoEntity> transferInfo(TransferInfoModel transferInfoModel, Pageable pageable, boolean isPEM) {
        Page<TransferInfoEntity> transferInfoList = transferInfoRepository
                .findAll((Specification<TransferInfoEntity>) (root, query, cb) -> {
                    List<Predicate> predicates = new ArrayList<>();

                    //Timestamp adding
                    predicates.add(cb.between(root.get(FILE_ARRIVED_COLUMN),
                            Timestamp.valueOf(convertToLocalLocale(transferInfoModel.getDateRangeStart())),
                            Timestamp.valueOf(convertToLocalLocale(transferInfoModel.getDateRangeEnd()))));


                    getPredicate(root, cb, predicates, transferInfoModel.getFlowInOut(), FLOW_TYPE_COLUMN, true);
                    getPredicate(root, cb, predicates, transferInfoModel.getTypeOfTransfer(), TYPE_OF_TRANSFER_COLUMN, true);
                    getPredicate(root, cb, predicates, transferInfoModel.getDoctype(), "doctype", true);
                    if (StringUtils.hasText(transferInfoModel.getStatus())) {
                        if (transferInfoModel.getStatus().equalsIgnoreCase("No Action Required")) {
                            predicates.add(cb.or((cb.equal(root.get("status"), "")), (cb.isNull(root.get("status"))), (cb.equal(root.get("status"), "No Action Required"))));
                        } else {
                            getPredicate(root, cb, predicates, transferInfoModel.getStatus(), "status", true);
                        }
                    }
                    getPredicate(root, cb, predicates, transferInfoModel.getSrcProtocol(), "srcprotocol", false);
                    getPredicate(root, cb, predicates, transferInfoModel.getDestProtocol(), "destprotocol", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getPartner(), PARTNER.toLowerCase(), true);
                    getPredicate(root, cb, predicates, transferInfoModel.getApplication(), APPLICATION.toLowerCase(), true);
                    getPredicate(root, cb, predicates, transferInfoModel.getSenderId(), SENDER_ID_COLUMN, true);
                    getPredicate(root, cb, predicates, transferInfoModel.getReceiverId(), RECEIVER_ID_COLUMN, true);
                    getPredicate(root, cb, predicates, transferInfoModel.getDocTrans(), DOC_TRANS_COLUMN, true);
                    getPredicate(root, cb, predicates, transferInfoModel.getErrorStatus(), "errorstatus", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getSrcFileName(), SRC_FILE_NAME_COLUMN, true);
                    getPredicate(root, cb, predicates, transferInfoModel.getDestFileName(), "destfilename", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue1(), "correlationValue1", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue2(), "correlationValue2", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue3(), "correlationValue3", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue4(), "correlationValue4", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue5(), "correlationValue5", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue6(), "correlationValue6", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue7(), "correlationValue7", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue8(), "correlationValue8", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue9(), "correlationValue9", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue10(), "correlationValue10", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue11(), "correlationValue11", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue12(), "correlationValue12", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue13(), "correlationValue13", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue14(), "correlationValue14", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue15(), "correlationValue15", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue16(), "correlationValue16", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue17(), "correlationValue17", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue18(), "correlationValue18", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue19(), "correlationValue19", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue20(), "correlationValue20", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue21(), "correlationValue21", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue22(), "correlationValue22", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue23(), "correlationValue23", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue24(), "correlationValue24", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue25(), "correlationValue25", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue26(), "correlationValue26", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue27(), "correlationValue27", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue28(), "correlationValue28", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue29(), "correlationValue29", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue30(), "correlationValue30", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue31(), "correlationValue31", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue32(), "correlationValue32", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue33(), "correlationValue33", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue34(), "correlationValue34", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue35(), "correlationValue35", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue36(), "correlationValue36", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue37(), "correlationValue37", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue38(), "correlationValue38", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue39(), "correlationValue39", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue40(), "correlationValue40", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue41(), "correlationValue41", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue42(), "correlationValue42", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue43(), "correlationValue43", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue44(), "correlationValue44", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue45(), "correlationValue45", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue46(), "correlationValue46", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue47(), "correlationValue47", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue48(), "correlationValue48", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue49(), "correlationValue49", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getCorrelationValue50(), "correlationValue50", true);

                    if (isNotNull(transferInfoModel.getFileNameRegExpression())) {
                        getPredicateWithRegularExp(root, cb, predicates, transferInfoModel.getFileNameRegExpression());
                    }

                    if (isPEM) {
                        getPredicate(root, cb, predicates, "template", PARTNER.toLowerCase(), true);
                    }

                    //Role base checking
                    partnerService.checkRoleAndPredicateWithPartner(root, cb, predicates, PARTNER.toLowerCase(), false);

                    return cb.and(predicates.toArray(new Predicate[0]));
                }, pageable);
        return new PageImpl<>(new ArrayList<>(transferInfoList.getContent()), pageable, transferInfoList.getTotalElements());
    }

    private void getPredicateWithRegularExp(Root<?> root, CriteriaBuilder cb, List<Predicate> predicates, String searchParam) {
        if (isNotNull(searchParam)) {
            String dbType = independentService.getDbType();
            if (dbType.equalsIgnoreCase(ORACLE) /*|| dbType.equalsIgnoreCase(DB2)*/) {
                Expression<Boolean> regExprLike = cb.function("regexp_like", Boolean.class, root.get(SRC_FILE_NAME_COLUMN), cb.literal(searchParam));
                Predicate predicateNull = cb.isNotNull(root.get(SRC_FILE_NAME_COLUMN));
                predicates.add(cb.and(predicateNull, cb.equal(regExprLike, 1)));
            } else if (dbType.equalsIgnoreCase(SQL_SERVER) || dbType.equalsIgnoreCase(DB2)) {
                LOGGER.info("Regular Expressions implementation: we are not applying in query level for Sql Server and DB2");
            }
        }
    }

    public List<TransInfoDEntity> getActivity(Long seqId, String bpId) {
        List<TransInfoDEntity> transInfoDEntities = new ArrayList<>();
        if (StringUtils.hasLength(bpId)) {
            transInfoDEntities.addAll(transInfoDRepository.findAllByBpidOrderBySequence(bpId));
        }
        if (seqId != null && seqId > 0) {
            transInfoDEntities.addAll(transInfoDRepository.findAllByBpidOrderBySequence(String.valueOf(seqId)));
        }
        //Checking the Activity Data in Staging tables
        if (transInfoDEntities.isEmpty()) {
            if (StringUtils.hasLength(bpId)) {
                transInfoDEntities.addAll(
                        transInfoDStagingRepository.findAllByBpidOrderBySequence(bpId).stream().map(transInfoDStagingEntity -> {
                            TransInfoDEntity transInfoDEntity = new TransInfoDEntity();
                            BeanUtils.copyProperties(transInfoDStagingEntity, transInfoDEntity);
                            return transInfoDEntity;
                        }).collect(Collectors.toList())
                );
            }
            if (seqId != null && seqId > 0) {
                transInfoDEntities.addAll(
                        transInfoDStagingRepository.findAllByBpidOrderBySequence(String.valueOf(seqId)).stream().map(transInfoDStagingEntity -> {
                            TransInfoDEntity transInfoDEntity = new TransInfoDEntity();
                            BeanUtils.copyProperties(transInfoDStagingEntity, transInfoDEntity);
                            return transInfoDEntity;
                        }).collect(Collectors.toList())
                );
            }
        }
        return transInfoDEntities;
    }

    private Integer getActivityMaxCount(String bpId) {
        return transInfoDRepository.findByBpidAndSequenceNotNullOrderBySequenceAsc(bpId)
                .orElse(new ArrayList<>())
                .stream()
                .max(Comparator.comparing(TransInfoDEntity::getSequence))
                .orElse(new TransInfoDEntity()).getSequence();
    }

    @Transactional
    public void restartWorkFlow(Long seqId, Long workFlowId, String status) {
        if (isNotNull(workFlowId)) {
            if (isNotNull(seqId)) {
                b2BApiService.restartWorkFlow(new RemoteProcessFlowModel(workFlowId));
                TransferInfoEntity transferInfoEntity = new TransferInfoEntity();
                BeanUtils.copyProperties(findFirstBySeqid(seqId), transferInfoEntity);
                save(transferInfoEntity.setSeqid(seqId)
                        .setStatus(status)
                        .setStatusComments("file " + status)
                );
                saveTransferInfoDActivity(seqId, status);
            } else {
                throw internalServerError("Provided SeqId should not be null/Empty");
            }

        } else {
            throw internalServerError("Provided WorkFlowId should not be null/Empty");
        }
    }

    private void saveTransferInfoDActivity(Long processId, String fileActivity) {
        Integer count = getActivityMaxCount(String.valueOf(processId));
        transInfoDRepository.save(new TransInfoDEntity()
                .setRulename("PCM-UI")
                .setBpid(String.valueOf(processId))
                .setSequence(count != null ? ++count : 1)
                .setDetails("File "
                        + fileActivity
                        + " by "
                        + userUtilityService.getUserOrRole(true)
                        + " at "
                        + new SimpleDateFormat("MMM-dd-yyyy hh.mm aa").format(new Date())));
    }

    @Transactional
    public void restartWorkFlows(CommunityMangerModel<CommunityManagerKeyValueModel> communityMangerModel, String status) {
        if (communityMangerModel != null && communityMangerModel.getContent() != null) {
            communityMangerModel.getContent().forEach(communityManagerMapModel -> {
                if (isNotNull(communityManagerMapModel.getValue())) {
                    restartWorkFlow(Long.valueOf(communityManagerMapModel.getKey()), Long.valueOf(communityManagerMapModel.getValue()), status);
                }
            });
        }
    }

    public TransferInfoEntity save(TransferInfoEntity transferInfoEntity) {
        return transferInfoRepository.save(transferInfoEntity);
    }

    private TransferInfoEntity findFirstBySeqid(Long seqId) {
        return transferInfoRepository.findFirstBySeqid(seqId).orElseThrow(() -> notFound("Transaction Record "));
    }

    public TransferInfoEntity searchByFileType(PemFileTypeModel pemCopyWorkFlowModel) {

        List<TransferInfoEntity> transferInfoEntities;
        if (pemCopyWorkFlowModel.getDocType().equals("MFT")) {
            transferInfoEntities = transferInfoRepository.findAll((Specification<TransferInfoEntity>) (root, query, cb) -> {
                List<Predicate> predicates = new ArrayList<>();
                getPredicate(root, cb, predicates, pemCopyWorkFlowModel.getPartner(), PARTNER.toLowerCase(), false);
                getPredicate(root, cb, predicates, pemCopyWorkFlowModel.getApplication(), APPLICATION.toLowerCase(), false);

                if (pemCopyWorkFlowModel.isRegexFind()) {
                    getPredicate(root, cb, predicates, pemCopyWorkFlowModel.getFlowType(), FLOW_TYPE_COLUMN, false);
                    getPredicateWithRegularExp(root, cb, predicates, pemCopyWorkFlowModel.getFileName());
                } else {
                    getPredicate(root, cb, predicates, pemCopyWorkFlowModel.getOperation(), STATUS_COMMENTS_COLUMN, true);
                }

                query.orderBy(cb.desc(root.get(FILE_ARRIVED_COLUMN)));

                return cb.and(predicates.toArray(new Predicate[0]));
            });

        } else {
            transferInfoEntities = transferInfoRepository.findAll((Specification<TransferInfoEntity>) (root, query, cb) -> {
                List<Predicate> predicates = new ArrayList<>();
                getPredicate(root, cb, predicates, pemCopyWorkFlowModel.getPartner(), PARTNER.toLowerCase(), false);
                getPredicate(root, cb, predicates, pemCopyWorkFlowModel.getApplication(), APPLICATION.toLowerCase(), false);

                if (pemCopyWorkFlowModel.isRegexFind()) {
                    getPredicate(root, cb, predicates, pemCopyWorkFlowModel.getFlowType(), FLOW_TYPE_COLUMN, false);
                    getPredicate(root, cb, predicates, pemCopyWorkFlowModel.getSenderId(), SENDER_ID_COLUMN, false);
                    getPredicate(root, cb, predicates, pemCopyWorkFlowModel.getReceiverId(), RECEIVER_ID_COLUMN, false);
                    getPredicate(root, cb, predicates, pemCopyWorkFlowModel.getTransaction(), DOC_TRANS_COLUMN, false);
                } else {
                    getPredicate(root, cb, predicates, pemCopyWorkFlowModel.getOperation(), STATUS_COMMENTS_COLUMN, true);
                }
                query.orderBy(cb.desc(root.get(FILE_ARRIVED_COLUMN)));

                return cb.and(predicates.toArray(new Predicate[0]));
            });
        }
        return transferInfoEntities.isEmpty() ? new TransferInfoEntity() : transferInfoEntities.get(0);
    }

    public ReportsModel searchVolumeReports(String startDate, String endDate) {
        List<String> partnerNames = partnerService.getPartnersByCurrentUser(false);
        return new ReportsModel()
                .setStatusVolume(getVolumeByStatus(startDate, endDate, partnerNames))
                .setDocTransVolume(getVolumeByDocTrans(startDate, endDate, partnerNames))
                .setFlowInOutVolume(getVolumeByFlowInOut(startDate, endDate, partnerNames));
    }

    public ReportsModel searchDocTypeVolumeReports(String startDate, String endDate) {
        return new ReportsModel()
                .setDocTypeVolume(getVolumeByDocType(startDate, endDate, partnerService.getPartnersByCurrentUser(false)));
    }

    public ReportsModel searchPartnerVolumeReports(String startDate, String endDate) {
        return new ReportsModel()
                .setPartnerVolume(getVolumeByPartners(startDate, endDate, partnerService.getPartnersByCurrentUser(false)));
    }

    public Page<OverDueMapper> searchOverDue(OverDueReportModel overDueReportModel, Pageable pageable) {
        try {
            String userRole = userUtilityService.getUserOrRole(FALSE);
            if (userRole.equalsIgnoreCase(AuthoritiesConstants.SUPER_ADMIN) || userRole.equalsIgnoreCase(AuthoritiesConstants.ADMIN)) {
                return reportRepository.findAll997OverDues(overDueReportModel, pageable, new ArrayList<>());
            } else {
                return reportRepository.findAll997OverDues(overDueReportModel,
                        pageable,
                        partnerService.getPartnersListAssignedToUserMap(userUtilityService.getUserOrRole(TRUE))
                                .stream()
                                .map(CommunityManagerKeyValueModel::getValue)
                                .collect(Collectors.toList())
                );
            }
        } catch (UncategorizedSQLException e) {
            LOGGER.error(e.getMessage());
            throw internalServerError("In Proper input data.");
        } catch (CommunityManagerServiceException cex) {
            throw internalServerError(cex.getErrorMessage());
        } catch (Exception ex) {
            throw internalServerError("May -DdbType not matched with actual DB, Please contact Admin Team.");
        }
    }

    public TransferInfoEntity searchByOldBpId(String bpId) {
        //TODO - Implementation Pending
        return new TransferInfoEntity();
    }

    public TransferInfoEntity get(Long seqId) {
        return transferInfoRepository.findById(seqId).orElse(new TransferInfoEntity());
    }

    @Transactional
    public void changeStatus(ChangeFileStatusModel changeFileStatusModel) {
        if (StringUtils.hasText(changeFileStatusModel.getSeqId())) {
            saveTransferInfoDActivity(Long.parseLong(changeFileStatusModel.getSeqId()),
                    (StringUtils.hasText(changeFileStatusModel.getCustomMessage()) ?
                            "Custom Message: " + changeFileStatusModel.getCustomMessage() + "; " : "") +
                            "Status has been changed from " + changeFileStatusModel.getOldStatus() + " to " + changeFileStatusModel.getNewStatus());
        } else {
            throw internalServerError("We can't change the status, because seqId NULL/EMPTY");
        }
        transferInfoRepository.findFirstBySeqid(Long.parseLong(changeFileStatusModel.getSeqId())).ifPresent(transferInfoEntity ->
                transferInfoRepository.save(transferInfoEntity.setStatus(changeFileStatusModel.getNewStatus())));
    }

    @Transactional
    public void saveTransferInfoD(String mailbox, Long seqId, String activityType, String fileName, Long fileLength, String fileActivity) {
        transInfoDService.save(mailbox, seqId, activityType, fileName, fileLength, fileActivity);
    }

    public Map<String, Long> getVolumeByPartners(String startDate, String endDate, List<String> partnerNames) {
        LOGGER.info("in getVolumeByPartners()");
        return reportRepository.queryForGroupBy(getGroupByColumnQuery.apply("PARTNER"), "PARTNER", startDate, endDate, partnerNames);
    }

    public Map<String, Long> getVolumeByStatus(String startDate, String endDate, List<String> partnerNames) {
        LOGGER.info("in getVolumeByStatus()");
        return reportRepository.queryForGroupBy(getGroupByColumnQuery.apply("STATUS"), "STATUS", startDate, endDate, partnerNames);
    }

    public Map<String, Long> getVolumeByDocType(String startDate, String endDate, List<String> partnerNames) {
        LOGGER.info("in getVolumeByDocType()");
        return reportRepository.queryForGroupBy(getGroupByColumnQuery.apply("DOCTYPE"), "DOCTYPE", startDate, endDate, partnerNames);
    }

    public Map<String, Long> getVolumeByDocTrans(String startDate, String endDate, List<String> partnerNames) {
        LOGGER.info("in getVolumeByDocTrans()");
        return reportRepository.queryForGroupBy(getGroupByColumnQuery.apply("DOCTRANS"), "DOCTRANS", startDate, endDate, partnerNames);
    }

    public Map<String, Long> getVolumeByFlowInOut(String startDate, String endDate, List<String> partnerNames) {
        LOGGER.info("in getVolumeByFlowInOut()");
        return reportRepository.queryForGroupBy(getGroupByColumnQuery.apply("FLOWINOUT"), "FLOWINOUT", startDate, endDate, partnerNames);
    }

}
