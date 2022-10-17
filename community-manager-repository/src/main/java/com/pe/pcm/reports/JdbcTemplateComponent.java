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

import com.pe.pcm.application.ApplicationRepository;
import com.pe.pcm.application.entity.ApplicationEntity;
import com.pe.pcm.common.CommunityManagerNameModel;
import com.pe.pcm.common.GenericBuilder;
import com.pe.pcm.enums.SortField;
import com.pe.pcm.functions.TriFunction;
import com.pe.pcm.partner.PartnerRepository;
import com.pe.pcm.partner.entity.PartnerEntity;
import com.pe.pcm.reports.entity.TransferInfoEntity;
import com.pe.pcm.rule.RuleRepository;
import com.pe.pcm.rule.entity.RuleEntity;
import com.pe.pcm.utils.CommonFunctions;
import com.pe.pcm.utils.PCMConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.pe.pcm.constants.PCMSqlConstants.DELETE_AUTH_USER_XREF;
import static com.pe.pcm.constants.PCMSqlConstants.INSERT_AUTH_USER_XREF;
import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.utils.CommonFunctions.convertToLocalLocale;
import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.PCMConstants.*;
import static com.pe.pcm.utils.ReportsCommonFunctions.*;
import static org.springframework.util.StringUtils.hasLength;

/**
 * @author Kiran Reddy.
 */
@Component
public class JdbcTemplateComponent {

    private static final String TRANSFER_INFO_AND_D_COUNT_QUERY = "SELECT COUNT(SEQID) " +
            "FROM ((SELECT SEQID FROM PETPE_TRANSFERINFO FILTER)" +
            "      UNION ALL" +
            "      (SELECT SEQID FROM PETPE_TRANSFERINFO_STAGING FILTER)) A";

    private final JdbcTemplate jdbcTemplate;

    private final RuleRepository ruleRepository;

    private ApplicationRepository applicationRepository;

    private PartnerRepository partnerRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcTemplateComponent.class);

    @Autowired
    public JdbcTemplateComponent(JdbcTemplate jdbcTemplate,
                                 RuleRepository ruleRepository,
                                 ApplicationRepository applicationRepository,
                                 PartnerRepository partnerRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.ruleRepository = ruleRepository;
        this.applicationRepository = applicationRepository;
        this.partnerRepository = partnerRepository;
    }

    int getCount(String query, List<String> queryParamValues) {
        String count = jdbcTemplate.queryForObject(query, (rs, rowNum) -> rs.getString(1), queryParamValues.toArray());
        return count != null ? Integer.parseInt(count) : 0;
    }

    List<OverDueMapper> getOverDueReports(String query, OverDueReportModel overDueReportModel, Pageable pageable, String dbType, List<String> partnerNameList) {

        LOGGER.info("Query: {}", query);
        return jdbcTemplate.query(
                query, preparedStatement -> {
                    AtomicInteger atomicInteger = new AtomicInteger(1);
                    set997ReportValues(preparedStatement, overDueReportModel, partnerNameList, atomicInteger);
                    setPageLimitValues(preparedStatement, pageable, dbType, atomicInteger.getAndIncrement());

                }, (rs, rowNum) -> GenericBuilder.of(OverDueMapper::new)
                        .with(OverDueMapper::setRowNum, rowNum)
                        .with(OverDueMapper::setValue, rs.getString("VALUE"))
                        .with(OverDueMapper::setFileArrived, rs.getString("FILEARRIVED"))
                        .with(OverDueMapper::setDestFileName, rs.getString("DESTFILENAME"))
                        .with(OverDueMapper::setPartner, rs.getString("PARTNER"))
                        .with(OverDueMapper::setDocTrans, rs.getString("DOCTRANS"))
                        .with(OverDueMapper::setSenderId, rs.getString("SENDERID"))
                        .with(OverDueMapper::setReceiverId, rs.getString("RECIVERID"))
                        .with(OverDueMapper::setWfId, rs.getString("WF_ID"))
                        .with(OverDueMapper::setGroupCount, rs.getString("GROUPCOUNT"))
                        .with(OverDueMapper::setOverdue, rs.getString("OVERDUETIME"))
                        .build()
        );
    }

    private void set997ReportValues(PreparedStatement preparedStatement, OverDueReportModel overDueReportModel, List<String> partnerNameList, AtomicInteger atomicInteger) throws SQLException {

        if (hasLength(overDueReportModel.getDateRangeStart())) {
            preparedStatement.setString(atomicInteger.getAndIncrement(), overDueReportModel.getDateRangeStart());
        }

        if (hasLength(overDueReportModel.getDateRangeEnd())) {
            preparedStatement.setString(atomicInteger.getAndIncrement(), overDueReportModel.getDateRangeEnd());
        }
        if (hasLength(overDueReportModel.getStatus())) {
            preparedStatement.setString(atomicInteger.getAndIncrement(), overDueReportModel.getStatus());
        }
        if (hasLength(overDueReportModel.getDocTrans())) {
            preparedStatement.setString(atomicInteger.getAndIncrement(), overDueReportModel.getDocTrans());
        }
        if (hasLength(overDueReportModel.getReceiverId())) {
            preparedStatement.setString(atomicInteger.getAndIncrement(), addDBContains.apply(overDueReportModel.getReceiverId()));
        }
        if (hasLength(overDueReportModel.getSenderId())) {
            preparedStatement.setString(atomicInteger.getAndIncrement(), addDBContains.apply(overDueReportModel.getSenderId()));
        }
        if (hasLength(overDueReportModel.getPartner())) {
            preparedStatement.setString(atomicInteger.getAndIncrement(), addDBContains.apply(overDueReportModel.getPartner()));
        }
        if (hasLength(overDueReportModel.getGroupCount())) {
            preparedStatement.setString(atomicInteger.getAndIncrement(), addDBContains.apply(overDueReportModel.getGroupCount()));
        }
        if (hasLength(overDueReportModel.getDestFileName())) {
            preparedStatement.setString(atomicInteger.getAndIncrement(), addDBContains.apply(overDueReportModel.getDestFileName()));
        }
        if (!partnerNameList.isEmpty()) {
            partnerNameList.forEach(partnerName -> {
                try {
                    preparedStatement.setString(atomicInteger.getAndIncrement(), partnerName);
                } catch (SQLException e) {
                    // No need to handle
                }
            });
        }
    }

    private void setPageLimitValues(PreparedStatement preparedStatement, Pageable pageable, String dbType, int count) throws SQLException {
        if (dbType.equals(ORACLE) || dbType.equals(DB2)) {
            preparedStatement.setLong(count++, pageable.getOffset() + pageable.getPageSize());
            preparedStatement.setLong(count, pageable.getOffset());
        } else if (dbType.equals(SQL_SERVER)) {
            preparedStatement.setLong(count++, pageable.getOffset());
            preparedStatement.setLong(count, pageable.getPageSize());
        }
    }

    Page<DataFlowMapper> findAllWorkFlows(DataFlowModel dataFlowModel, Pageable pageable, String dbType, String environment, Boolean isOnlyFlows, List<String> partnerPkIdList) {

        StringBuilder queryBuilder = getExportDataFlowQuery(dbType, false, isOnlyFlows, partnerPkIdList);
        StringBuilder rowCountQueryBuilder = new StringBuilder(WORKFLOW_COUNT_QUERY);

        List<String> queryParamValues = new ArrayList<>();

        addRequiredFiltersForDataFlow(queryBuilder, dataFlowModel, partnerPkIdList, false, queryParamValues, isOnlyFlows);
        addRequiredFiltersForDataFlow(rowCountQueryBuilder, dataFlowModel, partnerPkIdList, true, queryParamValues, isOnlyFlows);

        String[] sort = pageable.getSort().toString().split(":");
        switch (dbType) {
            case ORACLE:
                queryBuilder.append(SQL_ORDER_BY).append(SortField.valueOf(sort[0].trim().toUpperCase()).getField()).append(sort[1]).append(" ) A")
                        .append(" WHERE ROWNUM <= ? ")
                        .append(") ")
                        .append("WHERE RNUM > ? ");
                break;
            case DB2:
                String finalQueryBuilder = "SELECT * FROM ( SELECT inner2_.*, rownumber() over(order by order of inner2_) as rownumber_ FROM ("
                        + queryBuilder.substring(queryBuilder.indexOf("SELECT TP.TP_NAME")) + SQL_ORDER_BY +
                        SortField.valueOf(sort[0].trim().toUpperCase()).getField() +
                        sort[1] +
                        " FETCH FIRST ? ROWS ONLY ) as inner2_ ) as inner1_ where rownumber_ > ? order by rownumber_";
                queryBuilder.setLength(0);
                queryBuilder.append(finalQueryBuilder);
                break;
            case SQL_SERVER:
                String sortField = SortField.valueOf(sort[0].trim().toUpperCase()).getField();
                queryBuilder.append(" ) A").append(SQL_ORDER_BY).append(sortField.substring(sortField.indexOf('.') + 1)).append(sort[1]);
                queryBuilder.append(" OFFSET ? ")
                        .append(" ROWS FETCH FIRST ? ROWS ONLY ");
                break;
            default:
                // No Implementation needed
        }
        return new PageImpl<>(getWorkFlowsReport(queryBuilder.toString(), dataFlowModel, partnerPkIdList, pageable, dbType, environment, isOnlyFlows),
                pageable, getCount(rowCountQueryBuilder.toString(), queryParamValues));
    }

    List<DataFlowMapper> getWorkFlowsReport(String query, DataFlowModel dataFlowModel, List<String> partnerPkIdList, Pageable pageable, String dbType, String environment, Boolean isOnlyFlows) {

        Map<String, RuleEntity> rulesEntityMap = ruleRepository.findAllByOrderByRuleName()
                .orElse(new ArrayList<>())
                .stream()
                .collect(Collectors.toMap(RuleEntity::getRuleName, ruleEntity -> ruleEntity));

        AtomicLong atomicLong = new AtomicLong(pageable == null ? 1 : pageable.getOffset() + 1);
        LOGGER.debug("WorkFlow Reports Query : {}", query);
        return jdbcTemplate.query(
                query, preparedStatement -> {
                    if (dataFlowModel != null) {
                        AtomicInteger atomicInteger = new AtomicInteger(1);
                        setWorkFlowValues(preparedStatement, dataFlowModel, partnerPkIdList, atomicInteger);
                        if (pageable != null) {
                            setPageLimitValues(preparedStatement, pageable, dbType, atomicInteger.getAndIncrement());
                        }

                    }
                }, (resultSet, rowNum) -> generateData((Boolean.TRUE.equals(isOnlyFlows) ? null : rulesEntityMap.get(resultSet.getString(RULE_NAME_COLUMN))), atomicLong.getAndIncrement(), resultSet, environment, isOnlyFlows));
    }

    private void setWorkFlowValues(PreparedStatement preparedStatement, DataFlowModel dataFlowModel, List<String> partnerPkIdList, AtomicInteger atomicInteger) throws SQLException {

        if (hasLength(dataFlowModel.getPartnerName())) {
            preparedStatement.setString(atomicInteger.getAndIncrement(), addDBContains.apply(dataFlowModel.getPartnerName()));
        }
        if (hasLength(dataFlowModel.getApplicationName())) {
            preparedStatement.setString(atomicInteger.getAndIncrement(), addDBContains.apply(dataFlowModel.getApplicationName()));
        }
        if (hasLength(dataFlowModel.getSeqType())) {
            preparedStatement.setString(atomicInteger.getAndIncrement(), addDBContains.apply(dataFlowModel.getSeqType()));
        }
        if (hasLength(dataFlowModel.getFlow())) {
            preparedStatement.setString(atomicInteger.getAndIncrement(), addDBContains.apply(dataFlowModel.getFlow()));
        }
        if (hasLength(dataFlowModel.getFileType())) {
            preparedStatement.setString(atomicInteger.getAndIncrement(), addDBContains.apply(dataFlowModel.getFileType()));
        }
        if (hasLength(dataFlowModel.getDocType())) {
            preparedStatement.setString(atomicInteger.getAndIncrement(), addDBContains.apply(dataFlowModel.getDocType()));
        }
        if (hasLength(dataFlowModel.getTransaction())) {
            preparedStatement.setString(atomicInteger.getAndIncrement(), addDBContains.apply(dataFlowModel.getTransaction()));
        }
        if (hasLength(dataFlowModel.getSenderId())) {
            preparedStatement.setString(atomicInteger.getAndIncrement(), addDBContains.apply(dataFlowModel.getSenderId()));
        }
        if (hasLength(dataFlowModel.getReceiverId())) {
            preparedStatement.setString(atomicInteger.getAndIncrement(), addDBContains.apply(dataFlowModel.getReceiverId()));
        }
        if (hasLength(dataFlowModel.getRuleName())) {
            preparedStatement.setString(atomicInteger.getAndIncrement(), addDBContains.apply(dataFlowModel.getRuleName()));
        }
        if (hasLength(dataFlowModel.getRuleValue())) {
            IntStream.rangeClosed(0, 24).forEach(count -> {
                try {
                    preparedStatement.setString(atomicInteger.getAndIncrement(), addDBContains.apply(dataFlowModel.getRuleValue()));
                } catch (SQLException e) {
                    // No need to handle
                }
            });
        }

        if (dataFlowModel.getRuleModel() != null) {
            if (hasLength(dataFlowModel.getRuleModel().getRuleName())) {
                try {
                    preparedStatement.setString(atomicInteger.getAndIncrement(), dataFlowModel.getRuleModel().getRuleName());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (hasLength(dataFlowModel.getRuleModel().getPropertyName1())) {
                try {
                    preparedStatement.setString(atomicInteger.getAndIncrement(), addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName1()));
                } catch (SQLException e) {
                    // No need to handle
                }
            }
            if (hasLength(dataFlowModel.getRuleModel().getPropertyName2())) {
                try {
                    preparedStatement.setString(atomicInteger.getAndIncrement(), addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName2()));
                } catch (SQLException e) {
                    // No need to handle
                }
            }
            if (hasLength(dataFlowModel.getRuleModel().getPropertyName3())) {
                try {
                    preparedStatement.setString(atomicInteger.getAndIncrement(), addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName3()));
                } catch (SQLException e) {
                    // No need to handle
                }
            }
            if (hasLength(dataFlowModel.getRuleModel().getPropertyName4())) {
                try {
                    preparedStatement.setString(atomicInteger.getAndIncrement(), addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName4()));
                } catch (SQLException e) {
                    // No need to handle
                }
            }
            if (hasLength(dataFlowModel.getRuleModel().getPropertyName5())) {
                try {
                    preparedStatement.setString(atomicInteger.getAndIncrement(), addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName5()));
                } catch (SQLException e) {
                    // No need to handle
                }
            }
            if (hasLength(dataFlowModel.getRuleModel().getPropertyName6())) {
                try {
                    preparedStatement.setString(atomicInteger.getAndIncrement(), addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName6()));
                } catch (SQLException e) {
                    // No need to handle
                }
            }
            if (hasLength(dataFlowModel.getRuleModel().getPropertyName7())) {
                try {
                    preparedStatement.setString(atomicInteger.getAndIncrement(), addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName7()));
                } catch (SQLException e) {
                    // No need to handle
                }
            }
            if (hasLength(dataFlowModel.getRuleModel().getPropertyName8())) {
                try {
                    preparedStatement.setString(atomicInteger.getAndIncrement(), addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName8()));
                } catch (SQLException e) {
                    // No need to handle
                }
            }
            if (hasLength(dataFlowModel.getRuleModel().getPropertyName9())) {
                try {
                    preparedStatement.setString(atomicInteger.getAndIncrement(), addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName9()));
                } catch (SQLException e) {
                    // No need to handle
                }
            }
            if (hasLength(dataFlowModel.getRuleModel().getPropertyName10())) {
                try {
                    preparedStatement.setString(atomicInteger.getAndIncrement(), addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName10()));
                } catch (SQLException e) {
                    // No need to handle
                }
            }
            if (hasLength(dataFlowModel.getRuleModel().getPropertyName11())) {
                try {
                    preparedStatement.setString(atomicInteger.getAndIncrement(), addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName11()));
                } catch (SQLException e) {
                    // No need to handle
                }
            }
            if (hasLength(dataFlowModel.getRuleModel().getPropertyName12())) {
                try {
                    preparedStatement.setString(atomicInteger.getAndIncrement(), addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName12()));
                } catch (SQLException e) {
                    // No need to handle
                }
            }
            if (hasLength(dataFlowModel.getRuleModel().getPropertyName13())) {
                try {
                    preparedStatement.setString(atomicInteger.getAndIncrement(), addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName13()));
                } catch (SQLException e) {
                    // No need to handle
                }
            }
            if (hasLength(dataFlowModel.getRuleModel().getPropertyName14())) {
                try {
                    preparedStatement.setString(atomicInteger.getAndIncrement(), addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName14()));
                } catch (SQLException e) {
                    // No need to handle
                }
            }
            if (hasLength(dataFlowModel.getRuleModel().getPropertyName15())) {
                try {
                    preparedStatement.setString(atomicInteger.getAndIncrement(), addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName15()));
                } catch (SQLException e) {
                    // No need to handle
                }
            }
            if (hasLength(dataFlowModel.getRuleModel().getPropertyName16())) {
                try {
                    preparedStatement.setString(atomicInteger.getAndIncrement(), addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName16()));
                } catch (SQLException e) {
                    // No need to handle
                }
            }
            if (hasLength(dataFlowModel.getRuleModel().getPropertyName17())) {
                try {
                    preparedStatement.setString(atomicInteger.getAndIncrement(), addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName17()));
                } catch (SQLException e) {
                    // No need to handle
                }
            }
            if (hasLength(dataFlowModel.getRuleModel().getPropertyName18())) {
                try {
                    preparedStatement.setString(atomicInteger.getAndIncrement(), addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName18()));
                } catch (SQLException e) {
                    // No need to handle
                }
            }
            if (hasLength(dataFlowModel.getRuleModel().getPropertyName19())) {
                try {
                    preparedStatement.setString(atomicInteger.getAndIncrement(), addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName19()));
                } catch (SQLException e) {
                    // No need to handle
                }
            }
            if (hasLength(dataFlowModel.getRuleModel().getPropertyName20())) {
                try {
                    preparedStatement.setString(atomicInteger.getAndIncrement(), addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName20()));
                } catch (SQLException e) {
                    // No need to handle
                }
            }
            if (hasLength(dataFlowModel.getRuleModel().getPropertyName21())) {
                try {
                    preparedStatement.setString(atomicInteger.getAndIncrement(), addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName21()));
                } catch (SQLException e) {
                    // No need to handle
                }
            }
            if (hasLength(dataFlowModel.getRuleModel().getPropertyName22())) {
                try {
                    preparedStatement.setString(atomicInteger.getAndIncrement(), addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName22()));
                } catch (SQLException e) {
                    // No need to handle
                }
            }
            if (hasLength(dataFlowModel.getRuleModel().getPropertyName23())) {
                try {
                    preparedStatement.setString(atomicInteger.getAndIncrement(), addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName23()));
                } catch (SQLException e) {
                    // No need to handle
                }
            }
            if (hasLength(dataFlowModel.getRuleModel().getPropertyName24())) {
                try {
                    preparedStatement.setString(atomicInteger.getAndIncrement(), addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName24()));
                } catch (SQLException e) {
                    // No need to handle
                }
            }
            if (hasLength(dataFlowModel.getRuleModel().getPropertyName25())) {
                try {
                    preparedStatement.setString(atomicInteger.getAndIncrement(), addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName25()));
                } catch (SQLException e) {
                    // No need to handle
                }
            }

        }

        if (!partnerPkIdList.isEmpty()) {
            partnerPkIdList.forEach(partnerPkId -> {
                try {
                    preparedStatement.setString(atomicInteger.getAndIncrement(), partnerPkId);
                } catch (SQLException e) {
                    // No need to handle
                }
            });
        }

    }

    String purgeTransactions(String dbType, Long days) {

        int transactionRecordsCount = jdbcTemplate.update(getDeleteTransactionsQuery(dbType, days));
        int activityRecordsCount = jdbcTemplate.update(getDeleteTransactionsActivityQuery(dbType, days));
        return "No of records deleted in PETPE_TRANSFERINFO is : " + transactionRecordsCount
                + " AND PETPE_TRANSFERINFOD is : " + activityRecordsCount;
    }

    private DataFlowMapper generateData(RuleEntity ruleEntity, Long rowNum, ResultSet resultSet, String environment, Boolean isOnlyFlows) {

        GenericBuilder<DataFlowMapper> dataFlowMapperGenericBuilder;
        try {
            dataFlowMapperGenericBuilder = GenericBuilder.of(DataFlowMapper::new).with(DataFlowMapper::setRowNum, rowNum)
                    .with(DataFlowMapper::setPartnerProfile, resultSet.getString("TP_NAME"))
                    .with(DataFlowMapper::setApplicationProfile, resultSet.getString("APPLICATION_NAME"))
                    .with(DataFlowMapper::setProcessDocPkId, resultSet.getString("PRD_PK_ID"))
                    .with(DataFlowMapper::setSeqType, resultSet.getString("SEQ_TYPE"))
                    .with(DataFlowMapper::setFlow, resultSet.getString("FLOW"))
                    .with(DataFlowMapper::setFileName, resultSet.getString("FILENAME_PATTERN"))
                    .with(DataFlowMapper::setDocType, resultSet.getString("DOCTYPE"))
                    .with(DataFlowMapper::setTransaction, resultSet.getString("DOCTRANS"))
                    .with(DataFlowMapper::setPartnerId, resultSet.getString("PARTNERID"))
                    .with(DataFlowMapper::setReceiverId, resultSet.getString("RECIVERID"))
                    .with(DataFlowMapper::setVersionNo, resultSet.getString("VERSION"));

            if (!isOnlyFlows) {

                if (ruleEntity == null) {
                    ruleEntity = new RuleEntity();
                }
                dataFlowMapperGenericBuilder.with(DataFlowMapper::setProcessRulePkId, resultSet.getString("PRR_PK_ID"))
                        .with(DataFlowMapper::setRuleName, resultSet.getString("RULE_NAME"))
                        .with(DataFlowMapper::setBusinessProcessId, ruleEntity.getBusinessProcessId())
                        .with(DataFlowMapper::setRuleProperty1, buildValue(ruleEntity.getPropertyName1(), resultSet.getString("PROPERTY_NAME_1"), environment))
                        .with(DataFlowMapper::setRuleProperty2, buildValue(ruleEntity.getPropertyName2(),
                                getProfileName.apply(resultSet.getString("FLOW"), ruleEntity.getPropertyName1(), resultSet.getString("PROPERTY_NAME_2")), environment))
                        .with(DataFlowMapper::setRuleProperty3, buildValue(ruleEntity.getPropertyName3(), resultSet.getString("PROPERTY_NAME_3"), environment))
                        .with(DataFlowMapper::setRuleProperty4, buildValue(ruleEntity.getPropertyName4(), resultSet.getString("PROPERTY_NAME_4"), environment))
                        .with(DataFlowMapper::setRuleProperty5, buildValue(ruleEntity.getPropertyName5(), resultSet.getString("PROPERTY_NAME_5"), environment))
                        .with(DataFlowMapper::setRuleProperty6, buildValue(ruleEntity.getPropertyName6(), resultSet.getString("PROPERTY_NAME_6"), environment))
                        .with(DataFlowMapper::setRuleProperty7, buildValue(ruleEntity.getPropertyName7(), resultSet.getString("PROPERTY_NAME_7"), environment))
                        .with(DataFlowMapper::setRuleProperty8, buildValue(ruleEntity.getPropertyName8(), resultSet.getString("PROPERTY_NAME_8"), environment))
                        .with(DataFlowMapper::setRuleProperty9, buildValue(ruleEntity.getPropertyName9(), resultSet.getString("PROPERTY_NAME_9"), environment))
                        .with(DataFlowMapper::setRuleProperty10, buildValue(ruleEntity.getPropertyName10(), resultSet.getString("PROPERTY_NAME_10"), environment))
                        .with(DataFlowMapper::setRuleProperty11, buildValue(ruleEntity.getPropertyName11(), resultSet.getString("PROPERTY_NAME_11"), environment))
                        .with(DataFlowMapper::setRuleProperty12, buildValue(ruleEntity.getPropertyName12(), resultSet.getString("PROPERTY_NAME_12"), environment))
                        .with(DataFlowMapper::setRuleProperty13, buildValue(ruleEntity.getPropertyName13(), resultSet.getString("PROPERTY_NAME_13"), environment))
                        .with(DataFlowMapper::setRuleProperty14, buildValue(ruleEntity.getPropertyName14(), resultSet.getString("PROPERTY_NAME_14"), environment))
                        .with(DataFlowMapper::setRuleProperty15, buildValue(ruleEntity.getPropertyName15(), resultSet.getString("PROPERTY_NAME_15"), environment))
                        .with(DataFlowMapper::setRuleProperty16, buildValue(ruleEntity.getPropertyName16(), resultSet.getString("PROPERTY_NAME_16"), environment))
                        .with(DataFlowMapper::setRuleProperty17, buildValue(ruleEntity.getPropertyName17(), resultSet.getString("PROPERTY_NAME_17"), environment))
                        .with(DataFlowMapper::setRuleProperty18, buildValue(ruleEntity.getPropertyName18(), resultSet.getString("PROPERTY_NAME_18"), environment))
                        .with(DataFlowMapper::setRuleProperty19, buildValue(ruleEntity.getPropertyName19(), resultSet.getString("PROPERTY_NAME_19"), environment))
                        .with(DataFlowMapper::setRuleProperty20, buildValue(ruleEntity.getPropertyName20(), resultSet.getString("PROPERTY_NAME_20"), environment))
                        .with(DataFlowMapper::setRuleProperty21, buildValue(ruleEntity.getPropertyName21(), resultSet.getString("PROPERTY_NAME_21"), environment))
                        .with(DataFlowMapper::setRuleProperty22, buildValue(ruleEntity.getPropertyName22(), resultSet.getString("PROPERTY_NAME_22"), environment))
                        .with(DataFlowMapper::setRuleProperty23, buildValue(ruleEntity.getPropertyName23(), resultSet.getString("PROPERTY_NAME_23"), environment))
                        .with(DataFlowMapper::setRuleProperty24, buildValue(ruleEntity.getPropertyName24(), resultSet.getString("PROPERTY_NAME_24"), environment))
                        .with(DataFlowMapper::setRuleProperty25, buildValue(ruleEntity.getPropertyName25(), resultSet.getString("PROPERTY_NAME_25"), environment));
            }
        } catch (SQLException e) {
            throw internalServerError("Oops... Server is busy, Please try after some time");
        }
        return dataFlowMapperGenericBuilder.build();
    }

    public final TriFunction<String, String, String, String> getProfileName = (flow, propertyName, propertyValue) -> {
        if (isNotNull(propertyName) && propertyName.equalsIgnoreCase("Protocol") && isNotNull(propertyValue) && isNotNull(flow)) {
            if (flow.equalsIgnoreCase(PCMConstants.INBOUND)) {
                return applicationRepository.findById(propertyValue).orElse(new ApplicationEntity()).getApplicationName();
            } else {
                return partnerRepository.findById(propertyValue).orElse(new PartnerEntity()).getTpName();
            }
        }
        return propertyValue;
    };

    String generateUniqueKey(String dbType) {
        return String.valueOf(jdbcTemplate.queryForObject(
                generateQueryToGetSequence.apply(dbType), Long.class, new Object[]{}));
    }

    public int[] saveAuthUserXrefSsh(String userId, List<String> authUserXrefSshList) {
        return this.jdbcTemplate.batchUpdate(INSERT_AUTH_USER_XREF,
                new BatchPreparedStatementSetter() {
                    @SuppressWarnings("NullableProblems")
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, userId);
                        ps.setString(2, authUserXrefSshList.get(i));
                    }

                    public int getBatchSize() {
                        return authUserXrefSshList.size();
                    }
                });
    }

    public int deleteAuthUserXrefSsh(String userId) {
        return jdbcTemplate.update(DELETE_AUTH_USER_XREF, userId);
    }


    // ## EFX Report Dependence

    List<FileNameCountModel> getProducerData(TransferInfoListModel transferInfoListModel, String dbType) {

        StringBuilder stringBuilder = new StringBuilder("SELECT PE.PARTNER ,COUNT(PE.PARTNER) AS FILE_COUNT FROM PETPE_TRANSFERINFO_REPORT PE");
        stringBuilder.append(" WHERE 1=1 ");
        addRequiredFiltersForReportData(stringBuilder, transferInfoListModel, dbType);
        stringBuilder.append(" AND PE.PARTNER IS NOT NULL ");
        stringBuilder.append(" GROUP BY PE.PARTNER ");
        LOGGER.info(" Partner Data Query: {}", stringBuilder);

        List<FileNameCountModel> fileNameCountModel;
        fileNameCountModel = jdbcTemplate.query(stringBuilder.toString(),
                (result, rowNum) -> new FileNameCountModel(
                        result.getString("PARTNER"),
                        result.getString("FILE_COUNT")
                ));
        return fileNameCountModel;
    }

    List<FileNameCountModel> getConsumerData(TransferInfoListModel transferInfoListModel, String dbType) {

        StringBuilder stringBuilder = new StringBuilder("SELECT PE.APPLICATION ,COUNT(PE.APPLICATION) AS FILE_COUNT FROM PETPE_TRANSFERINFO_REPORT PE");
        stringBuilder.append(" WHERE 1=1 ");
        addRequiredFiltersForReportData(stringBuilder, transferInfoListModel, dbType);
        stringBuilder.append(" AND PE.APPLICATION IS NOT NULL ");
        stringBuilder.append(" GROUP BY PE.APPLICATION ");
        LOGGER.info(" Consumer Data Query: {} ", stringBuilder);

        List<FileNameCountModel> fileNameCountModel;
        fileNameCountModel = jdbcTemplate.query(stringBuilder.toString(),
                (result, rowNum) -> new FileNameCountModel(
                        result.getString("APPLICATION"),
                        result.getString("FILE_COUNT")
                ));
        return fileNameCountModel;
    }

    List<CommunityManagerNameModel> getUidData(TransferInfoListModel transferInfoListModel, String dbType) {

        StringBuilder stringBuilder = new StringBuilder("SELECT DISTINCT(PE.UID_NUMBER) AS UID_NUMBER FROM PETPE_TRANSFERINFO_REPORT PE");
        stringBuilder.append(" WHERE 1=1 ");
        addRequiredFiltersForReportData(stringBuilder, transferInfoListModel, dbType);
        stringBuilder.append(" AND PE.UID_NUMBER IS NOT NULL ");
        stringBuilder.append(" GROUP BY PE.UID_NUMBER ");
        LOGGER.info(" UID Data Query: ", stringBuilder);

        List<CommunityManagerNameModel> communityManagerNameModel;
        communityManagerNameModel = jdbcTemplate.query(stringBuilder.toString(),
                (result, rowNum) -> new CommunityManagerNameModel(
                        result.getString("UID_NUMBER")
                ));
        return communityManagerNameModel;
    }


    ReportTotalCountsModel getTotalCountData(TransferInfoListModel transferInfoListModel, String dbType) {

        StringBuilder stringBuilder = new StringBuilder("SELECT COUNT(*) AS FILE_COUNT,SUM(PE.FILE_SIZE) AS TOTAL_FILESIZE,MAX(PE.FILE_SIZE) AS MAX_FILESIZE,MIN(PE.FILE_SIZE) AS MIN_FILESIZE ,SUM(PE.CHARGEBACK) AS TOTAL_CHARGEBACK FROM PETPE_TRANSFERINFO_REPORT PE");
        stringBuilder.append(" WHERE 1=1 ");
        addRequiredFiltersForReportData(stringBuilder, transferInfoListModel, dbType);
        LOGGER.info(" Total Count  Data Query: {}", stringBuilder);

        ReportTotalCountsModel reportTotalCountsModel;
        reportTotalCountsModel = jdbcTemplate.queryForObject(stringBuilder.toString(),
                (result, rowNum) -> new ReportTotalCountsModel(
                        result.getString("FILE_COUNT"),
                        result.getString("TOTAL_FILESIZE"),
                        result.getString("MAX_FILESIZE"),
                        result.getString("MIN_FILESIZE"),
                        result.getString("TOTAL_CHARGEBACK")
                ));
        return reportTotalCountsModel;
    }

    List<DataBarChartModel> getProducerConsumerData(TransferInfoListModel transferInfoListModel, String dbType) {

        StringBuilder stringBuilder = new StringBuilder("SELECT PE.APPLICATION,PE.PARTNER,COUNT(PE.PARTNER) AS COUNT FROM PETPE_TRANSFERINFO_REPORT PE");
        stringBuilder.append(" WHERE 1=1 ");
        addRequiredFiltersForReportData(stringBuilder, transferInfoListModel, dbType);
        stringBuilder.append(" GROUP BY PE.APPLICATION,PE.PARTNER ");
        LOGGER.info(" Bar Chart Data Query: {}", stringBuilder);

        List<DataProducerConsumerFilecountModel> dataProducerConsumerFilecountModel;
        dataProducerConsumerFilecountModel = jdbcTemplate.query(stringBuilder.toString(),
                (result, rowNum) -> new DataProducerConsumerFilecountModel(
                        result.getString("APPLICATION"),
                        result.getString("PARTNER"),
                        result.getString("COUNT")
                ));

        Map<String, Map<String, String>> mapApplicationPartner = dataProducerConsumerFilecountModel.stream()
                .filter(e -> isNotNull(e.getConsumer()))
                .filter(e -> isNotNull(e.getProducer()))
                .collect(Collectors.groupingBy(DataProducerConsumerFilecountModel::getConsumer,
                        Collectors.toMap(DataProducerConsumerFilecountModel::getProducer, DataProducerConsumerFilecountModel::getCount)));

        List<DataBarChartModel> dataBarChartModel = new ArrayList<>();

        mapApplicationPartner.forEach((k, v) -> {
            DataBarChartModel data0 = new DataBarChartModel();
            data0.setKey(k);
            data0.setKeymap(v);
            dataBarChartModel.add(data0);
        });
        return dataBarChartModel;
    }

    List<DataModel> getDateFileCountFileSizeData(TransferInfoListModel transferInfoListModel, String dbType) {

        List<DataModel> dataModel;
        StringBuilder stringBuilder = new StringBuilder();

        if (dbType.equals(ORACLE)) {
            stringBuilder.append("SELECT (TO_CHAR(PE.FILEARRIVED,'yyyy-MM-dd')) AS DATE_COLUMN, COUNT(PE.FILEARRIVED) AS FILE_COUNT,(SUM(PE.FILE_SIZE)) AS FILE_SIZE,(SUM(PE.CHARGEBACK)) AS CHARGEBACK FROM PETPE_TRANSFERINFO_REPORT PE ");
            stringBuilder.append(" WHERE 1=1 ");
            addRequiredFiltersForReportData(stringBuilder, transferInfoListModel, dbType);
            stringBuilder.append(" AND PE.FILE_SIZE IS NOT NULL ");
            stringBuilder.append(" AND PE.CHARGEBACK IS NOT NULL ");
            stringBuilder.append(" GROUP BY (TO_CHAR(PE.FILEARRIVED,'yyyy-MM-dd'))");
            stringBuilder.append(" ORDER BY (TO_CHAR(PE.FILEARRIVED,'yyyy-MM-dd'))");
            LOGGER.info(" Line Chart query: {} ", stringBuilder);
        } else if (dbType.equals(SQL_SERVER)) {
            stringBuilder.append("SELECT (CONVERT(VARCHAR, datepart(year,PE.FILEARRIVED))+'-'+CONVERT(VARCHAR, datepart(month,PE.FILEARRIVED))+'-'+CONVERT(VARCHAR, datepart(dd,PE.FILEARRIVED))) AS DATE_COLUMN, COUNT(PE.FILEARRIVED) AS FILE_COUNT,(SUM(PE.FILE_SIZE)) AS FILE_SIZE,(SUM(PE.CHARGEBACK)) AS CHARGEBACK FROM PETPE_TRANSFERINFO_REPORT PE ");
            stringBuilder.append(" WHERE 1=1 ");
            addRequiredFiltersForReportData(stringBuilder, transferInfoListModel, dbType);
            stringBuilder.append(" AND PE.FILE_SIZE IS NOT NULL ");
            stringBuilder.append(" AND PE.CHARGEBACK IS NOT NULL ");
            stringBuilder.append(" GROUP BY (CONVERT(VARCHAR, datepart(year,PE.FILEARRIVED))+'-'+CONVERT(VARCHAR, datepart(month,PE.FILEARRIVED))+'-'+CONVERT(VARCHAR, datepart(dd,PE.FILEARRIVED)))");
            stringBuilder.append(" ORDER BY (CONVERT(VARCHAR, datepart(year,PE.FILEARRIVED))+'-'+CONVERT(VARCHAR, datepart(month,PE.FILEARRIVED))+'-'+CONVERT(VARCHAR, datepart(dd,PE.FILEARRIVED)))");
            LOGGER.info(" Line Chart query: {}", stringBuilder);
        } else {
            stringBuilder.append("SELECT (TO_CHAR(PE.FILEARRIVED,'%Y-%m-%d')) AS DATE_COLUMN, COUNT(PE.FILEARRIVED) AS FILE_COUNT,(SUM(PE.FILE_SIZE)) AS FILE_SIZE,(SUM(PE.CHARGEBACK)) AS CHARGEBACK FROM PETPE_TRANSFERINFO_REPORT PE ");
            stringBuilder.append(" WHERE 1=1 ");
            addRequiredFiltersForReportData(stringBuilder, transferInfoListModel, dbType);
            stringBuilder.append(" AND PE.FILE_SIZE IS NOT NULL ");
            stringBuilder.append(" AND PE.CHARGEBACK IS NOT NULL ");
            stringBuilder.append(" GROUP BY (TO_CHAR(PE.FILEARRIVED,'%Y-%m-%d'))");
            stringBuilder.append(" ORDER BY (TO_CHAR(PE.FILEARRIVED,'%Y-%m-%d'))");
            LOGGER.info(" Line Chart query, {}", stringBuilder);
        }
        dataModel = jdbcTemplate.query(stringBuilder.toString(),
                (result, rowNum) -> new DataModel(
                        result.getString("DATE_COLUMN"),
                        result.getString("FILE_COUNT"),
                        result.getString("FILE_SIZE"),
                        result.getString("CHARGEBACK")
                ));
        return dataModel;
    }


    ExternalInternalTotalCounts getExtIntTotalCounts(TransferInfoListModel transferInfoListModel, String dbType) {

        List<FileNameCountModel> producerData = getProducerData(transferInfoListModel, dbType);
        List<FileNameCountModel> consumerData = getConsumerData(transferInfoListModel, dbType);

        ExternalInternalTotalCounts externalInternalTotalCounts = new ExternalInternalTotalCounts();

        List<String> listExtProd = new ArrayList<>();
        producerData.stream()
                .filter(e -> isNotNull(e.getName()))
                .filter(map -> map.getName().startsWith("EXT"))
                .forEachOrdered(e -> listExtProd.add(e.getName()));

        externalInternalTotalCounts.setExternalProducerCount(String.valueOf(listExtProd.size()));

        List<String> listIntProd = new ArrayList<>();
        producerData.stream()
                .filter(e -> isNotNull(e.getName()))
                .filter(map -> map.getName().startsWith("INT"))
                .forEachOrdered(e -> listIntProd.add(e.getName()));

        externalInternalTotalCounts.setInternalProducerCount(String.valueOf(listIntProd.size()));

        List<String> listExtConsumer = new ArrayList<>();
        consumerData.stream()
                .filter(e -> isNotNull(e.getName()))
                .filter(map -> map.getName().startsWith("EXT"))
                .forEachOrdered(e -> listExtConsumer.add(e.getName()));

        externalInternalTotalCounts.setExternalConsumerCount(String.valueOf(listExtConsumer.size()));

        List<String> listIntConsumer = new ArrayList<>();
        consumerData.stream()
                .filter(e -> isNotNull(e.getName()))
                .filter(map -> map.getName().startsWith("INT"))
                .forEachOrdered(e -> listIntConsumer.add(e.getName()));
        externalInternalTotalCounts.setInternalConsumerCount(String.valueOf(listIntConsumer.size()));

        return externalInternalTotalCounts;
    }

    List<CommunityManagerNameModel> getFileSizeData(TransferInfoListModel transferInfoListModel, String dbType) {

        StringBuilder stringBuilder = new StringBuilder("SELECT DISTINCT(PE.FILE_SIZE) AS FILE_SIZE FROM PETPE_TRANSFERINFO_REPORT PE");
        stringBuilder.append(" WHERE 1=1 ");
        addRequiredFiltersForReportData(stringBuilder, transferInfoListModel, dbType);
        stringBuilder.append(" AND PE.FILE_SIZE IS NOT NULL ");
        stringBuilder.append(" ORDER BY PE.FILE_SIZE DESC");
        if (dbType.equals(ORACLE) || dbType.equals(DB2)) {
            stringBuilder.append(" FETCH FIRST 10 ROWS ONLY ");
        } else if (dbType.equals(SQL_SERVER)) {
            stringBuilder.append(" LIMIT 10");
        }
        LOGGER.info(" FileSize Data Query, {}", stringBuilder);

        List<CommunityManagerNameModel> communityManagerNameModel;
        communityManagerNameModel = jdbcTemplate.query(stringBuilder.toString(),
                (result, rowNum) -> new CommunityManagerNameModel(
                        result.getString("FILE_SIZE")
                ));
        return communityManagerNameModel;
    }

    List<CommunityManagerNameModel> getChargebackData(TransferInfoListModel transferInfoListModel, String dbType) {

        StringBuilder stringBuilder = new StringBuilder("SELECT DISTINCT(PE.CHARGEBACK) AS CHARGEBACK FROM PETPE_TRANSFERINFO_REPORT PE");
        stringBuilder.append(" WHERE 1=1 ");
        addRequiredFiltersForReportData(stringBuilder, transferInfoListModel, dbType);
        stringBuilder.append(" AND PE.CHARGEBACK IS NOT NULL ");
        stringBuilder.append(" ORDER BY PE.CHARGEBACK DESC");
        if (dbType.equals(ORACLE) || dbType.equals(DB2)) {
            stringBuilder.append(" FETCH FIRST 10 ROWS ONLY ");
        } else if (dbType.equals(SQL_SERVER)) {
            stringBuilder.append(" LIMIT 10");
        }
        LOGGER.info(" ChargeBack Data Query: {}", stringBuilder);

        List<CommunityManagerNameModel> communityManagerNameModel;
        communityManagerNameModel = jdbcTemplate.query(stringBuilder.toString(),
                (result, rowNum) -> new CommunityManagerNameModel(
                        result.getString("CHARGEBACK")
                ));
        return communityManagerNameModel;
    }

    List<CommunityManagerNameModel> getAppData(TransferInfoListModel transferInfoListModel, String dbType) {

        StringBuilder stringBuilder = new StringBuilder("SELECT DISTINCT(PE.APP) AS APP FROM PETPE_TRANSFERINFO_REPORT PE");
        stringBuilder.append(" WHERE 1=1 ");
        addRequiredFiltersForReportData(stringBuilder, transferInfoListModel, dbType);
        stringBuilder.append(" AND PE.APP IS NOT NULL ");
        LOGGER.info(" APP DATA Query: {}", stringBuilder);

        List<CommunityManagerNameModel> communityManagerNameModel;
        communityManagerNameModel = jdbcTemplate.query(stringBuilder.toString(),
                (result, rowNum) -> new CommunityManagerNameModel(
                        result.getString("APP")
                ));
        return communityManagerNameModel;
    }

    List<CommunityManagerNameModel> getBuData(TransferInfoListModel transferInfoListModel, String dbType) {

        StringBuilder stringBuilder = new StringBuilder("SELECT DISTINCT(PE.BU) AS BU FROM PETPE_TRANSFERINFO_REPORT PE");
        stringBuilder.append(" WHERE 1=1 ");
        addRequiredFiltersForReportData(stringBuilder, transferInfoListModel, dbType);
        stringBuilder.append(" AND PE.BU IS NOT NULL ");
        LOGGER.info(" BU DATA Query: {}", stringBuilder);

        List<CommunityManagerNameModel> communityManagerNameModel;
        communityManagerNameModel = jdbcTemplate.query(stringBuilder.toString(),
                (result, rowNum) -> new CommunityManagerNameModel(
                        result.getString("BU")
                ));
        return communityManagerNameModel;
    }


    List<DataModel> getMonthlyData(TransferInfoListModel transferInfoListModel, String dbType) {

        List<DataModel> dataModel;
        StringBuilder stringBuilder = new StringBuilder();
        if (dbType.equals(ORACLE)) {
            stringBuilder.append("SELECT TO_CHAR(PE.FILEARRIVED, 'YYYY-MM') AS DATE_COLUMN,COUNT(PE.FILEARRIVED) AS FILE_COUNT,SUM(PE.FILE_SIZE) AS FILE_SIZE,SUM(PE.CHARGEBACK) AS CHARGEBACK FROM PETPE_TRANSFERINFO_REPORT PE");
            stringBuilder.append(" WHERE 1=1 ");
            addRequiredFiltersForReportData(stringBuilder, transferInfoListModel, dbType);
            stringBuilder.append(" GROUP BY (TO_CHAR(PE.FILEARRIVED, 'YYYY-MM')) ");
            stringBuilder.append(" ORDER BY (TO_CHAR(PE.FILEARRIVED, 'YYYY-MM')) ");
        } else if (dbType.equals(SQL_SERVER)) {
            stringBuilder.append("SELECT (CONVERT(VARCHAR, datepart(year,PE.FILEARRIVED))+'-'+CONVERT(VARCHAR, datepart(month,PE.FILEARRIVED))) AS DATE_COLUMN,COUNT(PE.FILEARRIVED) AS FILE_COUNT,SUM(PE.FILE_SIZE) AS FILE_SIZE,SUM(PE.CHARGEBACK) AS CHARGEBACK FROM PETPE_TRANSFERINFO_REPORT PE");
            stringBuilder.append(" WHERE 1=1 ");
            addRequiredFiltersForReportData(stringBuilder, transferInfoListModel, dbType);
            stringBuilder.append(" GROUP BY (CONVERT(VARCHAR, datepart(year,PE.FILEARRIVED))+'-'+CONVERT(VARCHAR, datepart(month,PE.FILEARRIVED))) ");
        } else {
            stringBuilder.append("SELECT TO_CHAR(PE.FILEARRIVED, '%Y-%m') AS DATE_COLUMN,COUNT(PE.FILEARRIVED) AS FILE_COUNT,SUM(PE.FILE_SIZE) AS FILE_SIZE,SUM(PE.CHARGEBACK) AS CHARGEBACK FROM PETPE_TRANSFERINFO_REPORT PE");
            stringBuilder.append(" WHERE 1=1 ");
            addRequiredFiltersForReportData(stringBuilder, transferInfoListModel, dbType);
            stringBuilder.append(" GROUP BY (TO_CHAR(PE.FILEARRIVED, '%Y-%m')) ");
            stringBuilder.append(" ORDER BY (TO_CHAR(PE.FILEARRIVED, '%Y-%m')) ");
        }

        LOGGER.info(" Monthly DATA Query: {}", stringBuilder);
        dataModel = jdbcTemplate.query(stringBuilder.toString(),
                (result, rowNum) -> new DataModel(
                        result.getString("DATE_COLUMN"),
                        result.getString("FILE_COUNT"),
                        result.getString("FILE_SIZE"),
                        result.getString("CHARGEBACK")
                ));
        return dataModel;
    }

    List<DataModel> getQuarterlyData(TransferInfoListModel transferInfoListModel, String dbType) {

        List<DataModel> dataModel;
        StringBuilder stringBuilder = new StringBuilder();

        if (dbType.equals(ORACLE)) {
            stringBuilder.append("SELECT TO_CHAR(PE.FILEARRIVED, 'YYYY-Q') AS DATE_COLUMN,COUNT(PE.FILEARRIVED) AS FILE_COUNT,SUM(PE.FILE_SIZE) AS FILE_SIZE,SUM(PE.CHARGEBACK) AS CHARGEBACK FROM PETPE_TRANSFERINFO_REPORT PE");
            stringBuilder.append(" WHERE 1=1 ");
            addRequiredFiltersForReportData(stringBuilder, transferInfoListModel, dbType);
            stringBuilder.append(" GROUP BY (TO_CHAR(PE.FILEARRIVED, 'YYYY-Q')) ");
            stringBuilder.append(" ORDER BY (TO_CHAR(PE.FILEARRIVED, 'YYYY-Q')) ");

        } else if (dbType.equals(SQL_SERVER)) {
            stringBuilder.append("SELECT (CONVERT(VARCHAR, datepart(year,PE.FILEARRIVED))+'-'+CONVERT(VARCHAR, datepart(qq,PE.FILEARRIVED))) AS DATE_COLUMN,COUNT(PE.FILEARRIVED) AS FILE_COUNT,SUM(PE.FILE_SIZE) AS FILE_SIZE,SUM(PE.CHARGEBACK) AS CHARGEBACK FROM PETPE_TRANSFERINFO_REPORT PE");
            stringBuilder.append(" WHERE 1=1 ");
            addRequiredFiltersForReportData(stringBuilder, transferInfoListModel, dbType);
            stringBuilder.append(" GROUP BY (CONVERT(VARCHAR, datepart(year,PE.FILEARRIVED))+'-'+CONVERT(VARCHAR, datepart(qq,PE.FILEARRIVED))) ");
            stringBuilder.append(" ORDER BY (CONVERT(VARCHAR, datepart(year,PE.FILEARRIVED))+'-'+CONVERT(VARCHAR, datepart(qq,PE.FILEARRIVED))) ");
        } else {
            stringBuilder.append("SELECT TO_CHAR(PE.FILEARRIVED, '%Y-%q') AS DATE_COLUMN,COUNT(PE.FILEARRIVED) AS FILE_COUNT,SUM(PE.FILE_SIZE) AS FILE_SIZE,SUM(PE.CHARGEBACK) AS CHARGEBACK FROM PETPE_TRANSFERINFO_REPORT PE");
            stringBuilder.append(" WHERE 1=1 ");
            addRequiredFiltersForReportData(stringBuilder, transferInfoListModel, dbType);
            stringBuilder.append(" GROUP BY (TO_CHAR(PE.FILEARRIVED, '%Y-%q')) ");
            stringBuilder.append(" ORDER BY (TO_CHAR(PE.FILEARRIVED, '%Y-%q')) ");
        }

        LOGGER.info(" Quarterly DATA Query: {}", stringBuilder);
        dataModel = jdbcTemplate.query(stringBuilder.toString(),
                (result, rowNum) -> new DataModel(
                        result.getString("DATE_COLUMN"),
                        result.getString("FILE_COUNT"),
                        result.getString("FILE_SIZE"),
                        result.getString("CHARGEBACK")
                ));

        return dataModel;
    }

    List<CommunityManagerNameModel> getPNODEData(TransferInfoListModel transferInfoListModel, String dbType) {

        StringBuilder stringBuilder = new StringBuilder("SELECT DISTINCT(PE.PNODE) AS PNODE FROM PETPE_TRANSFERINFO_REPORT PE");
        stringBuilder.append(" WHERE 1=1 ");
        addRequiredFiltersForReportData(stringBuilder, transferInfoListModel, dbType);
        stringBuilder.append(" AND PE.PNODE IS NOT NULL ");
        LOGGER.info(" PNODE DATA Query: {}", stringBuilder);

        List<CommunityManagerNameModel> communityManagerNameModel;
        communityManagerNameModel = jdbcTemplate.query(stringBuilder.toString(),
                (result, rowNum) -> new CommunityManagerNameModel(
                        result.getString("PNODE")
                ));
        return communityManagerNameModel;
    }

    List<CommunityManagerNameModel> getSNODEData(TransferInfoListModel transferInfoListModel, String dbType) {

        StringBuilder stringBuilder = new StringBuilder("SELECT DISTINCT(PE.SNODE) AS SNODE FROM PETPE_TRANSFERINFO_REPORT PE");
        stringBuilder.append(" WHERE 1=1 ");
        addRequiredFiltersForReportData(stringBuilder, transferInfoListModel, dbType);
        stringBuilder.append(" AND PE.SNODE IS NOT NULL ");
        LOGGER.info(" SNODE DATA Query: {}", stringBuilder);

        List<CommunityManagerNameModel> communityManagerNameModel;
        communityManagerNameModel = jdbcTemplate.query(stringBuilder.toString(),
                (result, rowNum) -> new CommunityManagerNameModel(
                        result.getString("SNODE")
                ));
        return communityManagerNameModel;
    }

    ProducerConsumerCountsModel getProducerConsumerTotalCount(TransferInfoListModel transferInfoListModel, String dbType) {

        StringBuilder stringBuilder = new StringBuilder("SELECT COUNT(PARTNER) AS PARTNER_COUNT,COUNT(APPLICATION) AS APPLICATION_COUNT FROM PETPE_TRANSFERINFO_REPORT PE");
        stringBuilder.append(" WHERE 1=1 ");
        addRequiredFiltersForReportData(stringBuilder, transferInfoListModel, dbType);
        LOGGER.info(" Producer Consumer Total Count DATA Query: {}", stringBuilder);

        ProducerConsumerCountsModel producerConsumerCountsModel;
        producerConsumerCountsModel = jdbcTemplate.queryForObject(stringBuilder.toString(),
                (result, rowNum) -> new ProducerConsumerCountsModel(
                        result.getString("PARTNER_COUNT"),
                        result.getString("APPLICATION_COUNT")
                ));

        return producerConsumerCountsModel;
    }

    List<DataTableModel> getSrcDestFilenameTotalCount(TransferInfoListModel transferInfoListModel, String dbType) {

        StringBuilder stringBuilder = new StringBuilder("SELECT PE.SRCFILENAME AS SRCFILENAME,PE.DESTFILENAME AS DESTFILENAME,COUNT(*) AS TOTALCOUNT FROM PETPE_TRANSFERINFO_REPORT PE");
        stringBuilder.append(" WHERE 1=1 ");
        addRequiredFiltersForReportData(stringBuilder, transferInfoListModel, dbType);
        stringBuilder.append(" GROUP BY PE.SRCFILENAME,PE.DESTFILENAME ");
        LOGGER.info(" SRC AND DEST FILENAME DATA Query: {}", stringBuilder);

        List<DataTableModel> dataTableModel;
        dataTableModel = jdbcTemplate.query(stringBuilder.toString(),
                (result, rowNum) -> new DataTableModel(
                        result.getString("SRCFILENAME"),
                        result.getString("DESTFILENAME"),
                        result.getString("TOTALCOUNT")
                ));
        return dataTableModel;
    }

    public Map<String, Long> queryForGroupBy(String query, String groupByColumnName, String startDate, String endDate, List<String> partnerNames) {
        List<List<String>> partnerNamesList;
        if (partnerNames != null && !partnerNames.isEmpty()) {
            partnerNamesList = CommonFunctions.getPartitions(998, partnerNames);
            StringBuilder sb = new StringBuilder();
            AtomicBoolean isAdded = new AtomicBoolean(false);
            partnerNamesList.forEach(pNames -> {
                if (isAdded.get()) {
                    sb.append(" OR ");
                } else {
                    isAdded.set(true);
                    sb.append(" AND ( ");
                }
                sb.append(" PARTNER IN (")
                        .append(
                                pNames.stream()
                                        .map(v -> "?")
                                        .collect(Collectors.joining(", ")))
                        .append(")");
            });
            sb.append(" ) ");
            query = query.replace("IN_SECTION", sb.toString());
        } else {
            partnerNamesList = null;
            query = query.replace("IN_SECTION", "");
        }
        LOGGER.info("Query for Group By {}: {}", groupByColumnName, query);
        return jdbcTemplate.query(query,
                ps -> {
                    AtomicInteger index = new AtomicInteger(1);
                    ps.setTimestamp(index.getAndIncrement(), Timestamp.valueOf(convertToLocalLocale(startDate)));
                    ps.setTimestamp(index.getAndIncrement(), Timestamp.valueOf(convertToLocalLocale(endDate)));
                    if (partnerNamesList != null && !partnerNamesList.isEmpty()) {
                        partnerNamesList.forEach(pNames -> pNames.forEach(pName -> {
                            try {
                                ps.setString(index.getAndIncrement(), pName);
                            } catch (SQLException ignored) {
                                //No need to handle
                            }
                        }));
                    }
                },
                (ResultSetExtractor<Map<String, Long>>) rs -> {
                    HashMap<String, Long> mapRet = new HashMap<>();
                    while (rs.next()) {
                        mapRet.put(rs.getString(groupByColumnName), rs.getLong("COUNT(*)"));
                    }
                    return mapRet;
                });
    }

    public Page<TransferInfoEntity> transInfoAndTransInfoD(String baseQuery,
                                                           TransferInfoModel transferInfoModel,
                                                           Pageable pageable,
                                                           List<String> partnerNames,
                                                           boolean isPEM,
                                                           String dbType) {
        StringBuilder filterQuery = new StringBuilder();
        List<String> queryParamValues = new ArrayList<>();
        filterQueryTransInfoAndTransInfoD(filterQuery, transferInfoModel, partnerNames, queryParamValues, isPEM, dbType);
        Timestamp startTime = Timestamp.valueOf(convertToLocalLocale(transferInfoModel.getDateRangeStart()));
        Timestamp endTime = Timestamp.valueOf(convertToLocalLocale(transferInfoModel.getDateRangeEnd()));
        String[] sort = pageable.getSort().toString().split(":");
        String sortingColumn = SortField.valueOf(sort[0].trim().toUpperCase()).getField();
        String finalQuery = baseQuery.replace("FILTER", filterQuery).replace("SORTING_DETAILS", sortingColumn + " " + sort[1]);
        String finalCountQuery = TRANSFER_INFO_AND_D_COUNT_QUERY.replace("FILTER", filterQuery);
        LOGGER.info("Main Query: {}", finalQuery);
        LOGGER.info("Count Query: {}", finalCountQuery);
        LOGGER.info("Query params: {}", queryParamValues);
        List<TransferInfoEntity> transferInfoEntities = jdbcTemplate.query(
                finalQuery, preparedStatement -> {
                    //INFO: This is for TransferInfo table
                    AtomicInteger index = new AtomicInteger(1);
                    preparedStatement.setTimestamp(index.getAndIncrement(), startTime);
                    preparedStatement.setTimestamp(index.getAndIncrement(), endTime);
                    if (!queryParamValues.isEmpty()) {
                        queryParamValues.forEach(queryParam -> {
                            try {
                                preparedStatement.setString(index.getAndIncrement(), queryParam);
                            } catch (SQLException ignored) {
                                //No need to handle
                            }
                        });
                    }
                    if (!partnerNames.isEmpty()) {
                        partnerNames.forEach(partnerName -> {
                            try {
                                preparedStatement.setString(index.getAndIncrement(), partnerName);
                            } catch (SQLException e) {
                                //No need to handle
                            }
                        });
                    }

                    //INFO: This is for TransferInfoStaging table
                    preparedStatement.setTimestamp(index.getAndIncrement(), startTime);
                    preparedStatement.setTimestamp(index.getAndIncrement(), endTime);
                    if (!queryParamValues.isEmpty()) {
                        queryParamValues.forEach(queryParam -> {
                            try {
                                preparedStatement.setString(index.getAndIncrement(), queryParam);
                            } catch (SQLException ignored) {
                                //No need to handle
                            }
                        });
                    }
                    if (!partnerNames.isEmpty()) {
                        partnerNames.forEach(partnerName -> {
                            try {
                                preparedStatement.setString(index.getAndIncrement(), partnerName);
                            } catch (SQLException e) {
                                //No need to handle
                            }
                        });
                    }
                    setPageLimitValues(preparedStatement, pageable, dbType, index.getAndIncrement());

                }, (resultSet, rowNum) -> loadData(resultSet));

        return new PageImpl<>(transferInfoEntities, pageable, getCount(finalCountQuery, queryParamValues, partnerNames, startTime, endTime));
    }

    int getCount(String countQuery, List<String> queryParamValues, List<String> partnerNames, Timestamp startTime, Timestamp endTime) {

        List<Integer> count = jdbcTemplate.query(countQuery, preparedStatement -> {
                    AtomicInteger index = new AtomicInteger(1);
                    //For TransferInfo Table
                    preparedStatement.setTimestamp(index.getAndIncrement(), startTime);
                    preparedStatement.setTimestamp(index.getAndIncrement(), endTime);
                    if (!queryParamValues.isEmpty()) {
                        queryParamValues.forEach(queryParam -> {
                            try {
                                preparedStatement.setString(index.getAndIncrement(), queryParam);
                            } catch (SQLException ignored) {
                                //No need to handle
                            }
                        });
                    }
                    if (!partnerNames.isEmpty()) {
                        partnerNames.forEach(partnerPkId -> {
                            try {
                                preparedStatement.setString(index.getAndIncrement(), partnerPkId);
                            } catch (SQLException e) {
                                //No need to handle
                            }
                        });
                    }
                    //For TransferInfoStaging Table
                    preparedStatement.setTimestamp(index.getAndIncrement(), startTime);
                    preparedStatement.setTimestamp(index.getAndIncrement(), endTime);
                    if (!queryParamValues.isEmpty()) {
                        queryParamValues.forEach(queryParam -> {
                            try {
                                preparedStatement.setString(index.getAndIncrement(), queryParam);
                            } catch (SQLException ignored) {
                                //No need to handle
                            }
                        });
                    }
                    if (!partnerNames.isEmpty()) {
                        partnerNames.forEach(partnerPkId -> {
                            try {
                                preparedStatement.setString(index.getAndIncrement(), partnerPkId);
                            } catch (SQLException e) {
                                //No need to handle
                            }
                        });
                    }
                }, (rs, rowNum) -> rs.getInt(1)
        );
        return count.isEmpty() ? 0 : count.get(0);
    }

    private TransferInfoEntity loadData(ResultSet resultSet) {
        GenericBuilder<TransferInfoEntity> transferInfoEntityGenericBuilder;

        try {
            transferInfoEntityGenericBuilder = GenericBuilder.of(TransferInfoEntity::new)
                    .with(TransferInfoEntity::setSeqid, resultSet.getLong("SEQID"))
                    .with(TransferInfoEntity::setFilearrived, resultSet.getTimestamp("filearrived"))
                    .with(TransferInfoEntity::setFlowinout, resultSet.getString("FLOWINOUT"))
                    .with(TransferInfoEntity::setTypeoftransfer, resultSet.getString("TYPEOFTRANSFER"))
                    .with(TransferInfoEntity::setSenderid, resultSet.getString("SENDERID"))
                    .with(TransferInfoEntity::setReciverid, resultSet.getString("RECIVERID"))
                    .with(TransferInfoEntity::setPartner, resultSet.getString("PARTNER"))
                    .with(TransferInfoEntity::setApplication, resultSet.getString("APPLICATION"))
                    .with(TransferInfoEntity::setAppintstatus, resultSet.getString("APPINTSTATUS"))
                    .with(TransferInfoEntity::setPartnerackstatus, resultSet.getString("PARTNERACKSTATUS"))
                    .with(TransferInfoEntity::setSrcprotocol, resultSet.getString("SRCPROTOCOL"))
                    .with(TransferInfoEntity::setSrcfilename, resultSet.getString("SRCFILENAME"))
                    .with(TransferInfoEntity::setSrcarcfileloc, resultSet.getString("SRCARCFILELOC"))
                    .with(TransferInfoEntity::setDestfilename, resultSet.getString("DESTFILENAME"))
                    .with(TransferInfoEntity::setDestarcfileloc, resultSet.getString("DESTARCFILELOC"))
                    .with(TransferInfoEntity::setDestprotocol, resultSet.getString("DESTPROTOCOL"))
                    .with(TransferInfoEntity::setDoctype, resultSet.getString("DOCTYPE"))
                    .with(TransferInfoEntity::setPickbpid, resultSet.getString("PICKBPID"))
                    .with(TransferInfoEntity::setCorebpid, resultSet.getString("COREBPID"))
                    .with(TransferInfoEntity::setDeliverybpid, resultSet.getString("DELIVERYBPID"))
                    .with(TransferInfoEntity::setStatus, resultSet.getString("STATUS"))
                    .with(TransferInfoEntity::setErrorstatus, resultSet.getString("ERRORSTATUS"))
                    .with(TransferInfoEntity::setAdverrorstatus, resultSet.getString("ADVERRORSTATUS"))
                    .with(TransferInfoEntity::setDoctrans, resultSet.getString("DOCTRANS"))
                    .with(TransferInfoEntity::setEncType, resultSet.getString("ENC_TYPE"))
                    .with(TransferInfoEntity::setSrcFileSize, resultSet.getLong("SRC_FILE_SIZE"))

                    .with(TransferInfoEntity::setTransfile, resultSet.getString("TRANSFILE"))
                    .with(TransferInfoEntity::setStatusComments, resultSet.getString("STATUS_COMMENTS"))
                    .with(TransferInfoEntity::setIsEncrypted, resultSet.getString("IS_ENCRYPTED"))
                    .with(TransferInfoEntity::setXrefName, resultSet.getString("XREF_NAME"))
                    .with(TransferInfoEntity::setCorrelationValue1, resultSet.getString("CORRELATION_VALUE_1"))
                    .with(TransferInfoEntity::setCorrelationValue2, resultSet.getString("CORRELATION_VALUE_2"))
                    .with(TransferInfoEntity::setCorrelationValue3, resultSet.getString("CORRELATION_VALUE_3"))
                    .with(TransferInfoEntity::setCorrelationValue4, resultSet.getString("CORRELATION_VALUE_4"))
                    .with(TransferInfoEntity::setCorrelationValue5, resultSet.getString("CORRELATION_VALUE_5"))
                    .with(TransferInfoEntity::setCorrelationValue6, resultSet.getString("CORRELATION_VALUE_6"))
                    .with(TransferInfoEntity::setCorrelationValue7, resultSet.getString("CORRELATION_VALUE_7"))
                    .with(TransferInfoEntity::setCorrelationValue8, resultSet.getString("CORRELATION_VALUE_8"))
                    .with(TransferInfoEntity::setCorrelationValue9, resultSet.getString("CORRELATION_VALUE_9"))
                    .with(TransferInfoEntity::setCorrelationValue10, resultSet.getString("CORRELATION_VALUE_10"))
                    .with(TransferInfoEntity::setCorrelationValue11, resultSet.getString("CORRELATION_VALUE_11"))
                    .with(TransferInfoEntity::setCorrelationValue12, resultSet.getString("CORRELATION_VALUE_12"))
                    .with(TransferInfoEntity::setCorrelationValue13, resultSet.getString("CORRELATION_VALUE_13"))
                    .with(TransferInfoEntity::setCorrelationValue14, resultSet.getString("CORRELATION_VALUE_14"))
                    .with(TransferInfoEntity::setCorrelationValue15, resultSet.getString("CORRELATION_VALUE_15"))
                    .with(TransferInfoEntity::setCorrelationValue16, resultSet.getString("CORRELATION_VALUE_16"))
                    .with(TransferInfoEntity::setCorrelationValue17, resultSet.getString("CORRELATION_VALUE_17"))
                    .with(TransferInfoEntity::setCorrelationValue18, resultSet.getString("CORRELATION_VALUE_18"))
                    .with(TransferInfoEntity::setCorrelationValue19, resultSet.getString("CORRELATION_VALUE_19"))
                    .with(TransferInfoEntity::setCorrelationValue20, resultSet.getString("CORRELATION_VALUE_20"))
                    .with(TransferInfoEntity::setCorrelationValue21, resultSet.getString("CORRELATION_VALUE_21"))
                    .with(TransferInfoEntity::setCorrelationValue22, resultSet.getString("CORRELATION_VALUE_22"))
                    .with(TransferInfoEntity::setCorrelationValue23, resultSet.getString("CORRELATION_VALUE_23"))
                    .with(TransferInfoEntity::setCorrelationValue24, resultSet.getString("CORRELATION_VALUE_24"))
                    .with(TransferInfoEntity::setCorrelationValue25, resultSet.getString("CORRELATION_VALUE_25"))
                    .with(TransferInfoEntity::setCorrelationValue26, resultSet.getString("CORRELATION_VALUE_26"))
                    .with(TransferInfoEntity::setCorrelationValue27, resultSet.getString("CORRELATION_VALUE_27"))
                    .with(TransferInfoEntity::setCorrelationValue28, resultSet.getString("CORRELATION_VALUE_28"))
                    .with(TransferInfoEntity::setCorrelationValue29, resultSet.getString("CORRELATION_VALUE_29"))
                    .with(TransferInfoEntity::setCorrelationValue30, resultSet.getString("CORRELATION_VALUE_30"))
                    .with(TransferInfoEntity::setCorrelationValue31, resultSet.getString("CORRELATION_VALUE_31"))
                    .with(TransferInfoEntity::setCorrelationValue32, resultSet.getString("CORRELATION_VALUE_32"))
                    .with(TransferInfoEntity::setCorrelationValue33, resultSet.getString("CORRELATION_VALUE_33"))
                    .with(TransferInfoEntity::setCorrelationValue34, resultSet.getString("CORRELATION_VALUE_34"))
                    .with(TransferInfoEntity::setCorrelationValue35, resultSet.getString("CORRELATION_VALUE_35"))
                    .with(TransferInfoEntity::setCorrelationValue36, resultSet.getString("CORRELATION_VALUE_36"))
                    .with(TransferInfoEntity::setCorrelationValue37, resultSet.getString("CORRELATION_VALUE_37"))
                    .with(TransferInfoEntity::setCorrelationValue38, resultSet.getString("CORRELATION_VALUE_38"))
                    .with(TransferInfoEntity::setCorrelationValue39, resultSet.getString("CORRELATION_VALUE_39"))
                    .with(TransferInfoEntity::setCorrelationValue40, resultSet.getString("CORRELATION_VALUE_40"))
                    .with(TransferInfoEntity::setCorrelationValue41, resultSet.getString("CORRELATION_VALUE_41"))
                    .with(TransferInfoEntity::setCorrelationValue42, resultSet.getString("CORRELATION_VALUE_42"))
                    .with(TransferInfoEntity::setCorrelationValue43, resultSet.getString("CORRELATION_VALUE_43"))
                    .with(TransferInfoEntity::setCorrelationValue44, resultSet.getString("CORRELATION_VALUE_44"))
                    .with(TransferInfoEntity::setCorrelationValue45, resultSet.getString("CORRELATION_VALUE_45"))
                    .with(TransferInfoEntity::setCorrelationValue46, resultSet.getString("CORRELATION_VALUE_46"))
                    .with(TransferInfoEntity::setCorrelationValue47, resultSet.getString("CORRELATION_VALUE_47"))
                    .with(TransferInfoEntity::setCorrelationValue48, resultSet.getString("CORRELATION_VALUE_48"))
                    .with(TransferInfoEntity::setCorrelationValue49, resultSet.getString("CORRELATION_VALUE_49"))
                    .with(TransferInfoEntity::setCorrelationValue50, resultSet.getString("CORRELATION_VALUE_50"));
        } catch (SQLException e) {
            LOGGER.error("", e);
            throw internalServerError("Oops... Server is busy, Please try after some time");
        }
        return transferInfoEntityGenericBuilder.build();
    }


}
