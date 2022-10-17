package com.pe.pcm.reports.operator;

import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.file.PartnerMailBoxService;
import com.pe.pcm.reports.CountResponseModel;
import com.pe.pcm.reports.TransferInfoModel;
import com.pe.pcm.reports.TransferInfoRepository;
import com.pe.pcm.reports.entity.TransferInfoEntity;
import com.pe.pcm.utils.CommonFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static com.pe.pcm.miscellaneous.CommonQueryPredicate.getPredicate;
import static com.pe.pcm.utils.CommonFunctions.convertToLocalLocale;
import static com.pe.pcm.utils.PCMConstants.*;
import static com.pe.pcm.utils.ReportsCommonFunctions.*;
import static org.springframework.util.StringUtils.hasText;

/**
 * @author Kiran Reddy.
 */
@Service
public class FileOperatorReportsService {
    public static final String COUNT_QUERY = "SELECT COUNT(SEQID) FROM PETPE_TRANSFERINFO WHERE FILEARRIVED BETWEEN ? AND ? AND LOWER(FLOWINOUT) = ? ";

    private final JdbcTemplate jdbcTemplate;
    private final PartnerMailBoxService partnerMailBoxService;
    private final TransferInfoRepository transferInfoRepository;

    @Autowired
    public FileOperatorReportsService(JdbcTemplate jdbcTemplate,
                                      PartnerMailBoxService partnerMailBoxService,
                                      TransferInfoRepository transferInfoRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.partnerMailBoxService = partnerMailBoxService;
        this.transferInfoRepository = transferInfoRepository;
    }

    public CountResponseModel filesActivityReport(String activityType) {
        StringBuilder query = new StringBuilder(COUNT_QUERY);
        List<String> mailboxes = new ArrayList<>(partnerMailBoxService.getAllMailboxesByCurrentUser());
        if (mailboxes.isEmpty()) {
            return new CountResponseModel();
        }
        List<List<String>> mailboxPart = CommonFunctions.getPartitions(10, mailboxes);
        query.append(" AND ")
                .append(getMailboxPathInSubQuery(mailboxPart));
        return new CountResponseModel()
                .setThisHourCount(loadCount(query.toString(), getThisHourTimeRange(), activityType, mailboxes))
                .setThisDayCount(loadCount(query.toString(), getThisDayTimeRange(), activityType, mailboxes))
                .setThisWeekCount(loadCount(query.toString(), getThisWeekTimeRange(), activityType, mailboxes))
                .setThisMonthCount(loadCount(query.toString(), getThisMonthTimeRange(), activityType, mailboxes));
    }

    //Same method is available in BasicReportsRepository, we need to clear this asap
    private int loadCount(String query, Timestamp[] timestamps, String param3, List<String> queryParamValues) {

        List<Integer> list = jdbcTemplate.query(
                query,
                preparedStatement -> {
                    AtomicInteger atomicInteger = new AtomicInteger(1);
                    preparedStatement.setTimestamp(atomicInteger.getAndIncrement(), timestamps[0]);
                    preparedStatement.setTimestamp(atomicInteger.getAndIncrement(), timestamps[1]);
                    preparedStatement.setString(atomicInteger.getAndIncrement(), param3);
                    queryParamValues.forEach(value -> {
                        try {
                            preparedStatement.setString(atomicInteger.getAndIncrement(), value);
                        } catch (Exception ignored) {
                            //No need to handle this
                        }
                    });
                },
                (rs, rowNum) -> rs.getInt(1)
        );

        if (list.isEmpty()) {
            return 0;
        } else {
            return list.get(0) != null ? list.get(0) : 0;
        }
    }

    public Page<TransferInfoEntity> search(TransferInfoModel transferInfoModel, Pageable pageable) {
        Page<TransferInfoEntity> transferInfoList = transferInfoRepository
                .findAll((Specification<TransferInfoEntity>) (root, query, cb) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    if (hasText(transferInfoModel.getDateRangeStart()) && hasText(transferInfoModel.getDateRangeEnd())) {
                        predicates.add(cb.between(root.get(FILE_ARRIVED_COLUMN),
                                Timestamp.valueOf(convertToLocalLocale(transferInfoModel.getDateRangeStart())),
                                Timestamp.valueOf(convertToLocalLocale(transferInfoModel.getDateRangeEnd()))));
                    }

                    getPredicate(root, cb, predicates, transferInfoModel.getFlowInOut(), FLOW_TYPE_COLUMN, true);
                    getPredicate(root, cb, predicates, transferInfoModel.getTypeOfTransfer(), TYPE_OF_TRANSFER_COLUMN, true);
                    getPredicate(root, cb, predicates, transferInfoModel.getDoctype(), "xrefName", false);
                    if (hasText(transferInfoModel.getStatus())) {
                        if (transferInfoModel.getStatus().equalsIgnoreCase("No Action Required")) {
                            predicates.add(cb.or((cb.equal(root.get("status"), "")), (cb.isNull(root.get("status"))), (cb.equal(root.get("status"), "No Action Required"))));
                        } else {
                            getPredicate(root, cb, predicates, transferInfoModel.getStatus(), "status", true);
                        }
                    }
                    getPredicate(root, cb, predicates, transferInfoModel.getSrcProtocol(), "srcprotocol", false);
                    getPredicate(root, cb, predicates, transferInfoModel.getDestProtocol(), "destprotocol", true);
                    getPredicate(root, cb, predicates, transferInfoModel.getPartner(), PARTNER.toLowerCase(), true);
                    getPredicate(root, cb, predicates, transferInfoModel.getSrcFileName(), SRC_FILE_NAME_COLUMN, true);

                    //Mailbox permissions adding
                    predicateWithMailboxesPermissions(root, cb, predicates, "xrefName");

                    //Applying filter only on Download, and Upload
                    predicates.add(cb.in(root.get(FLOW_TYPE_COLUMN)).value(UPLOAD).value(DOWNLOAD));

                    return cb.and(predicates.toArray(new Predicate[0]));
                }, pageable);
        return new PageImpl<>(new ArrayList<>(transferInfoList.getContent()), pageable, transferInfoList.getTotalElements());
    }

    private void predicateWithMailboxesPermissions(Root<TransferInfoEntity> root, CriteriaBuilder cb, List<Predicate> predicates, String field) {
        List<String> mailboxes = new ArrayList<>(partnerMailBoxService.getAllMailboxesByCurrentUser());
        if (!mailboxes.isEmpty()) {
            AtomicInteger atomicInteger = new AtomicInteger(0);
            List<String> parentListDup = new ArrayList<>();
            List<CriteriaBuilder.In<String>> inList = new ArrayList<>();
            mailboxes.forEach(mailbox -> {
                atomicInteger.getAndIncrement();
                parentListDup.add(mailbox);
                if (atomicInteger.get() % 998 == 0 || atomicInteger.get() == mailboxes.size()) {
                    CriteriaBuilder.In<String> in = cb.in(root.get(field));
                    parentListDup.forEach(in::value);
                    inList.add(in);
                    parentListDup.clear();
                }
            });
            parentListDup.clear();
            predicates.add(cb.or(inList.toArray(new Predicate[0])));
        } else {
            throw GlobalExceptionHandler.internalServerError("User should have at least one mailbox permission.");
        }
    }


    private String getMailboxPathInSubQuery(List<List<String>> mailboxesList) {
        StringBuilder sbQuery = new StringBuilder();
        AtomicBoolean isAdded = new AtomicBoolean(Boolean.FALSE);
        mailboxesList.forEach(mailboxes -> {
            if (isAdded.get()) {
                sbQuery.append(" OR XREF_NAME IN ( ");
            } else {
                sbQuery.append(" ( XREF_NAME IN ( ");
                isAdded.set(Boolean.TRUE);
            }
            IntStream.rangeClosed(1, mailboxes.size()).forEach(index -> sbQuery.append(" ?")
                    .append(index == mailboxes.size() ? " )" : ","));
        });
        return sbQuery.append(")").toString();
    }
}
