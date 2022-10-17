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

package com.pe.pcm.utils;

import com.pe.pcm.enums.SortField;
import com.pe.pcm.reports.DataFlowModel;
import com.pe.pcm.reports.OverDueReportModel;
import com.pe.pcm.reports.TransferInfoListModel;
import com.pe.pcm.reports.TransferInfoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;

import static com.pe.pcm.utils.CommonFunctions.*;
import static com.pe.pcm.utils.PCMConstants.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.sql.Date.valueOf;
import static org.springframework.util.StringUtils.hasText;

/**
 * @author Kiran Reddy.
 */
public class ReportsCommonFunctions {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportsCommonFunctions.class);

    private ReportsCommonFunctions() {
    }

    public static StringBuilder getExportDataFlowQuery(String dbType, boolean isAllFlows, Boolean isOnlyFlows, List<String> partnerPkIdList) {

        StringBuilder queryBuilder = new StringBuilder(dbType.equals(ORACLE) ? "SELECT * FROM ( " : "")
                .append("SELECT ")
                .append("A.TP_NAME, ")
                .append("A.APPLICATION_NAME, ")
                .append("A.PRD_PK_ID, ")
                .append("A.SEQ_TYPE, ")
                .append("A.FLOW, ")
                .append("A.FILENAME_PATTERN, ")
                .append("A.DOCTYPE, ")
                .append("A.DOCTRANS, ")
                .append("A.PARTNERID, ")
                .append("A.RECIVERID, ")
                .append("A.VERSION ");
        if (!isOnlyFlows) {
            queryBuilder.append(", A.PRR_PK_ID, ")
                    .append("A.RULE_NAME, ")
                    .append("A.PROPERTY_NAME_1, ")
                    .append("A.PROPERTY_NAME_2, ")
                    .append("A.PROPERTY_NAME_3, ")
                    .append("A.PROPERTY_NAME_4, ")
                    .append("A.PROPERTY_NAME_5, ")
                    .append("A.PROPERTY_NAME_6, ")
                    .append("A.PROPERTY_NAME_7, ")
                    .append("A.PROPERTY_NAME_8, ")
                    .append("A.PROPERTY_NAME_9, ")
                    .append("A.PROPERTY_NAME_10, ")
                    .append("A.PROPERTY_NAME_11, ")
                    .append("A.PROPERTY_NAME_12, ")
                    .append("A.PROPERTY_NAME_13, ")
                    .append("A.PROPERTY_NAME_14, ")
                    .append("A.PROPERTY_NAME_15, ")
                    .append("A.PROPERTY_NAME_16, ")
                    .append("A.PROPERTY_NAME_17, ")
                    .append("A.PROPERTY_NAME_18, ")
                    .append("A.PROPERTY_NAME_19, ")
                    .append("A.PROPERTY_NAME_20, ")
                    .append("A.PROPERTY_NAME_21, ")
                    .append("A.PROPERTY_NAME_22, ")
                    .append("A.PROPERTY_NAME_23, ")
                    .append("A.PROPERTY_NAME_24, ")
                    .append("A.PROPERTY_NAME_25 ");
        }

        queryBuilder.append(isAllFlows || dbType.equals(SQL_SERVER) || dbType.equals(DB2) ? "" : ", ROWNUM RNUM ")
                .append("FROM ( ")
                .append("SELECT ")
                .append("TP.TP_NAME, ")
                .append("AP.APPLICATION_NAME, ")
                .append("PC.SEQ_TYPE, ")
                .append("PC.FLOW, ")
                .append("PD.PK_ID AS PRD_PK_ID, ")
                .append("PD.FILENAME_PATTERN, ")
                .append("PD.DOCTYPE, ")
                .append("PD.DOCTRANS, ")
                .append("PD.PARTNERID, ")
                .append("PD.RECIVERID, ")
                .append("PD.VERSION ");
        if (!isOnlyFlows) {
            queryBuilder.append(", PR.PK_ID AS PRR_PK_ID, ")
                    .append("PR.RULE_NAME, ")
                    .append("PR.PROPERTY_NAME_1, ")
                    .append("PR.PROPERTY_NAME_2, ")
                    .append("PR.PROPERTY_NAME_3, ")
                    .append("PR.PROPERTY_NAME_4, ")
                    .append("PR.PROPERTY_NAME_5, ")
                    .append("PR.PROPERTY_NAME_6, ")
                    .append("PR.PROPERTY_NAME_7, ")
                    .append("PR.PROPERTY_NAME_8, ")
                    .append("PR.PROPERTY_NAME_9, ")
                    .append("PR.PROPERTY_NAME_10, ")
                    .append("PR.PROPERTY_NAME_11, ")
                    .append("PR.PROPERTY_NAME_12, ")
                    .append("PR.PROPERTY_NAME_13, ")
                    .append("PR.PROPERTY_NAME_14, ")
                    .append("PR.PROPERTY_NAME_15, ")
                    .append("PR.PROPERTY_NAME_16, ")
                    .append("PR.PROPERTY_NAME_17, ")
                    .append("PR.PROPERTY_NAME_18, ")
                    .append("PR.PROPERTY_NAME_19, ")
                    .append("PR.PROPERTY_NAME_20, ")
                    .append("PR.PROPERTY_NAME_21, ")
                    .append("PR.PROPERTY_NAME_22, ")
                    .append("PR.PROPERTY_NAME_23, ")
                    .append("PR.PROPERTY_NAME_24, ")
                    .append("PR.PROPERTY_NAME_25 ");
        }

        queryBuilder.append("FROM PETPE_TRADINGPARTNER TP, ")
                .append("PETPE_APPLICATION AP, ")
                .append("PETPE_PROCESS PC,  ")
                .append("PETPE_PROCESSDOCS PD ");
        if (!isOnlyFlows) {
            queryBuilder.append(", PETPE_PROCESSRULES PR ");
        }

        queryBuilder.append("WHERE TP.PK_ID = PC.PARTNER_PROFILE ")
                .append("AND AP.PK_ID = PC.APPLICATION_PROFILE ")
                .append("AND PC.SEQ_ID = PD.PROCESS_REF ");
        if (!isOnlyFlows) {
            queryBuilder.append("AND PD.PK_ID = PR.PROCESS_DOC_REF ");
        }
        if (isAllFlows && !partnerPkIdList.isEmpty()) {
            if (partnerPkIdList.size() > 998) {
                queryBuilder.append(" AND ( PC.PARTNER_PROFILE  IN ( ");
            } else {
                queryBuilder.append(" AND PC.PARTNER_PROFILE  IN ( ");
            }
            AtomicInteger atomicInteger = new AtomicInteger(0);
            AtomicBoolean atomicBoolean = new AtomicBoolean(false);
            partnerPkIdList.forEach(partnerPkId -> {
                if (atomicBoolean.get()) {
                    queryBuilder.append(", ?");
                } else {
                    queryBuilder.append(" ?");
                    atomicBoolean.set(true);
                }
                if (partnerPkIdList.size() > 998 && atomicInteger.getAndIncrement() % 998 == 0) {
                    queryBuilder.append(" ) OR PC.PARTNER_PROFILE IN ( ");
                }

            });
            queryBuilder.append(" ) ");
            if (partnerPkIdList.size() > 998) {
                queryBuilder.append(" ) ");
            }
        }
        return queryBuilder;
    }

    public static String buildValue(String propertyName, String propertyValue, String environment) {
        if (isNotNull(propertyName) || isNotNull(propertyValue)) {
            if (isNotNull(environment) && !environment.equalsIgnoreCase("pcm")) {
                return propertyValue;
            }
            if (isNotNull(propertyValue) && isNotNull(propertyName) && propertyName.equalsIgnoreCase("password")) {
                return propertyName + "= " + "*********";
            }
            return propertyName + "= " + propertyValue;
        }
        return "";
    }

    private static String genDeleteTransactionWhereCon(String dbType, Long days) {
        LocalDateTime localDateTime = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        Date fromDate = Date.from(localDateTime.minusDays(days).atZone(ZoneId.systemDefault()).toInstant());
        Timestamp fromTimeStamp = new Timestamp(fromDate.getTime());
        StringBuilder stringBuilder = new StringBuilder();
        switch (dbType) {
            case ORACLE:
                stringBuilder.append("TO_DATE ")
                        .append("('")
                        .append(new SimpleDateFormat(ONLY_DATE_ORACLE).format(fromDate))
                        .append("'")
                        .append(",'")
                        .append(ONLY_DATE_ORACLE)
                        .append("')");
                break;
            case SQL_SERVER:
            case DB2:
                stringBuilder.append("'")
                        .append(fromTimeStamp)
                        .append("'");
                break;
            default:
                //No Implementation Needed
                break;
        }
        return stringBuilder.toString();
    }

    public static String getDeleteTransactionsQuery(String dbType, Long days) {
        return "DELETE " +
                SQL_FROM_CLAUSE +
                "PETPE_TRANSFERINFO" +
                SQL_WHERE_CLAUSE +
                "FILEARRIVED < " +
                genDeleteTransactionWhereCon(dbType, days);
    }

    public static String getDeleteTransactionsActivityQuery(String dbType, Long days) {
        return "DELETE " +
                SQL_FROM_CLAUSE +
                "PETPE_TRANSINFOD" +
                SQL_WHERE_CLAUSE +
                "BPID " +
                " IN(" +
                SQL_SELECT_CLAUSE +
                " DISTINCT" +
                " COREBPID" +
                SQL_FROM_CLAUSE +
                "PETPE_TRANSFERINFO" +
                SQL_WHERE_CLAUSE +
                "FILEARRIVED < " +
                genDeleteTransactionWhereCon(dbType, days) +
                ")";
    }

    public static void addRequiredFiltersFor997Report(StringBuilder stringBuilder, OverDueReportModel overDueReportModel, List<String> partnerNameList, boolean isCountQuery, List<String> queryValues, String dbType) {
        stringBuilder.append(" WHERE 1=1 ");

        if (isNotNull(overDueReportModel.getDateRangeStart()) && isNotNull(overDueReportModel.getDateRangeEnd())) {
            stringBuilder.append(" AND FILEARRIVED BETWEEN ");
            if (dbType.equals(ORACLE)) {
                stringBuilder.append(SQL_TO_TIMESTAMP + "( ? ").append(",'").append(DATE_FORMAT_ORACLE).append("') AND ")
                        .append(SQL_TO_TIMESTAMP + "( ? ").append(",'").append(DATE_FORMAT_ORACLE).append("') ");
            } else if (dbType.equals(SQL_SERVER) || dbType.equals(DB2)) {
                stringBuilder.append(" ?  AND  ? ");
            }
            if (isCountQuery) {
                queryValues.add(convertDate(overDueReportModel.getDateRangeStart(), dbType));
                queryValues.add(convertDate(overDueReportModel.getDateRangeEnd(), dbType));
            }
        }
        if (isNotNull(overDueReportModel.getStatus())) {
            stringBuilder.append("AND VALUE LIKE ? ");
            if (isCountQuery) {
                queryValues.add(overDueReportModel.getStatus());
            }
        }
        if (isNotNull(overDueReportModel.getDocTrans())) {
            stringBuilder.append("AND DOCTRANS LIKE ? ");
            if (isCountQuery) {
                queryValues.add(overDueReportModel.getDocTrans());
            }
        }
        if (isNotNull(overDueReportModel.getSenderId())) {
            stringBuilder.append(" AND LOWER(SENDERID) LIKE ? ");
            if (isCountQuery) {
                queryValues.add(overDueReportModel.getSenderId());
            }
        }
        if (isNotNull(overDueReportModel.getReceiverId())) {
            stringBuilder.append(" AND LOWER(RECIVERID) LIKE ? ");
            if (isCountQuery) {
                queryValues.add(overDueReportModel.getReceiverId());
            }
        }
        if (isNotNull(overDueReportModel.getPartner())) {
            stringBuilder.append(" AND LOWER(PARTNER) LIKE ? ");
            if (isCountQuery) {
                queryValues.add(overDueReportModel.getPartner());
            }
        }
        if (isNotNull(overDueReportModel.getGroupCount())) {
            stringBuilder.append(" AND LOWER(GroupCount) LIKE ? ");
            if (isCountQuery) {
                queryValues.add(overDueReportModel.getGroupCount());
            }
        }
        if (isNotNull(overDueReportModel.getDestFileName())) {
            stringBuilder.append(" AND LOWER(DESTFILENAME) LIKE ? ");
            if (isCountQuery) {
                queryValues.add(overDueReportModel.getDestFileName());
            }
        }

        if (!partnerNameList.isEmpty()) {
            if (partnerNameList.size() > 998) {
                stringBuilder.append(" AND ( PARTNER  IN ( ");
            } else {
                stringBuilder.append(" AND PARTNER  IN ( ");
            }
            AtomicInteger atomicInteger = new AtomicInteger(1);
            AtomicBoolean atomicBoolean = new AtomicBoolean(false);
            partnerNameList.forEach(partnerName -> {

                if (atomicBoolean.get()) {
                    stringBuilder.append(", ?");
                } else {
                    stringBuilder.append(" ?");
                    atomicBoolean.set(true);
                }
                if (partnerNameList.size() > 998 && atomicInteger.getAndIncrement() % 998 == 0) {
                    stringBuilder.append(" ) OR PARTNER  IN ( ");
                    atomicBoolean.set(false);
                }
                if (isCountQuery) {
                    queryValues.add(partnerName);
                }
            });
            stringBuilder.append(" ) ");
            if (partnerNameList.size() > 998) {
                stringBuilder.append(" ) ");
            }
        }

    }

    /*public static void addRequiredFiltersForDataFlow(StringBuilder stringBuilder, DataFlowModel dataFlowModel, List<String> partnerPkIdList, boolean isCountQuery, List<String> queryValues, Boolean isOnlyFlows) {
        if (isNotNull(dataFlowModel.getPartnerName())) {
            stringBuilder.append("AND LOWER( TP.TP_NAME ) LIKE ? ");
            if (isCountQuery) {
                queryValues.add(addDBContains.apply(dataFlowModel.getPartnerName()));
            }
        }
        if (isNotNull(dataFlowModel.getPartnerPkId())) {
            stringBuilder.append("AND PC.PARTNER_PROFILE != ? ");
            if (isCountQuery) {
                queryValues.add(dataFlowModel.getPartnerPkId());
            }
        }
        if (isNotNull(dataFlowModel.getApplicationName())) {
            stringBuilder.append("AND LOWER( AP.APPLICATION_NAME ) LIKE ? ");
            if (isCountQuery) {
                queryValues.add(addDBContains.apply(dataFlowModel.getApplicationName()));
            }
        }
        if (isNotNull(dataFlowModel.getApplicationPkId())) {
            stringBuilder.append("AND PC.APPLICATION_PROFILE != ? ");
            if (isCountQuery) {
                queryValues.add(dataFlowModel.getApplicationPkId());
            }
        }
        if (isNotNull(dataFlowModel.getSeqType())) {
            stringBuilder.append("AND LOWER( PC.SEQ_TYPE ) LIKE ? ");
            if (isCountQuery) {
                queryValues.add(addDBContains.apply(dataFlowModel.getSeqType()));
            }
        }
        if (isNotNull(dataFlowModel.getFlow())) {
            stringBuilder.append("AND LOWER( PC.FLOW ) LIKE ? ");
            if (isCountQuery) {
                queryValues.add(addDBContains.apply(dataFlowModel.getFlow()));
            }
        }
        if (isNotNull(dataFlowModel.getFileType())) {
            stringBuilder.append("AND LOWER( PD.FILENAME_PATTERN) LIKE ? ");
            if (isCountQuery) {
                queryValues.add(addDBContains.apply(dataFlowModel.getFileType()));
            }
        }
        if (isNotNull(dataFlowModel.getDocType())) {
            stringBuilder.append("AND LOWER( PD.DOCTYPE ) LIKE ? ");
            if (isCountQuery) {
                queryValues.add(addDBContains.apply(dataFlowModel.getDocType()));
            }
        }
        if (isNotNull(dataFlowModel.getTransaction())) {
            stringBuilder.append("AND LOWER( PD.DOCTRANS ) LIKE ? ");
            if (isCountQuery) {
                queryValues.add(addDBContains.apply(dataFlowModel.getTransaction()));
            }
        }
        if (isNotNull(dataFlowModel.getSenderId())) {
            stringBuilder.append("AND LOWER( PD.PARTNERID ) LIKE ? ");
            if (isCountQuery) {
                queryValues.add(addDBContains.apply(dataFlowModel.getSenderId()));
            }
        }
        if (isNotNull(dataFlowModel.getReceiverId())) {
            stringBuilder.append("AND LOWER( PD.RECIVERID ) LIKE ? ");
            if (isCountQuery) {
                queryValues.add(addDBContains.apply(dataFlowModel.getReceiverId()));
            }
        }
        if (!isOnlyFlows) {
            if (isNotNull(dataFlowModel.getRuleName())) {
                stringBuilder.append("AND LOWER( PR.RULE_NAME ) LIKE ? ");
                if (isCountQuery) {
                    queryValues.add(addDBContains.apply(dataFlowModel.getRuleName()));
                }
            }
            if (isNotNull(dataFlowModel.getRuleValue())) {
                stringBuilder.append("AND ( LOWER( PR.PROPERTY_NAME_1 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_2 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_3 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_4 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_5 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_6 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_7 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_8 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_9 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_10 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_11 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_12 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_13 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_14 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_15 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_16 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_17 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_18 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_19 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_20 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_21 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_22 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_23 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_24 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_25 ) LIKE ? ")
                        .append(") ");
                if (isCountQuery) {
                    IntStream.rangeClosed(0, 24).forEach(count -> queryValues.add(addDBContains.apply(dataFlowModel.getRuleValue())));
                }
            }
        }

        if (!partnerPkIdList.isEmpty()) {
            if (partnerPkIdList.size() > 998) {
                stringBuilder.append(" AND ( PC.PARTNER_PROFILE  IN ( ");
            } else {
                stringBuilder.append(" AND PC.PARTNER_PROFILE  IN ( ");
            }
            AtomicInteger atomicInteger = new AtomicInteger(1);
            AtomicBoolean atomicBoolean = new AtomicBoolean(false);
            partnerPkIdList.forEach(partnerPkId -> {
                if (atomicBoolean.get()) {
                    stringBuilder.append(", ?");
                } else {
                    stringBuilder.append(" ?");
                    atomicBoolean.set(true);
                }
                if (partnerPkIdList.size() > 998 && atomicInteger.getAndIncrement() % 998 == 0) {
                    stringBuilder.append(" ) OR PC.PARTNER_PROFILE  IN ( ");
                    atomicBoolean.set(false);
                }
                if (isCountQuery) {
                    queryValues.add(partnerPkId);
                }
            });
            stringBuilder.append(" ) ");
            if (partnerPkIdList.size() > 998) {
                stringBuilder.append(" ) ");
            }
        }

    }
*/
    public static void addRequiredFiltersForDataFlow(StringBuilder stringBuilder, DataFlowModel dataFlowModel, List<String> partnerPkIdList, boolean isCountQuery, List<String> queryValues, Boolean isOnlyFlows) {
        if (isNotNull(dataFlowModel.getPartnerName())) {
            stringBuilder.append("AND LOWER( TP.TP_NAME ) LIKE ? ");
            if (isCountQuery) {
                queryValues.add(addDBContains.apply(dataFlowModel.getPartnerName()));
            }
        }
//        if (isNotNull(dataFlowModel.getPartnerPkId())) {
//            stringBuilder.append("AND PC.PARTNER_PROFILE != ? ");
//            if (isCountQuery) {
//                queryValues.add(/*dataFlowModel.getPartnerPkId()*/ " ");
//            }
//        }
        if (isNotNull(dataFlowModel.getApplicationName())) {
            stringBuilder.append("AND LOWER( AP.APPLICATION_NAME ) LIKE ? ");
            if (isCountQuery) {
                queryValues.add(addDBContains.apply(dataFlowModel.getApplicationName()));
            }
        }
//        if (isNotNull(dataFlowModel.getApplicationPkId())) {
//            stringBuilder.append("AND PC.APPLICATION_PROFILE != ? ");
//            if (isCountQuery) {
//                queryValues.add(/*dataFlowModel.getApplicationPkId()*/ " ");
//            }
//        }
        if (isNotNull(dataFlowModel.getSeqType())) {
            stringBuilder.append("AND LOWER( PC.SEQ_TYPE ) LIKE ? ");
            if (isCountQuery) {
                queryValues.add(addDBContains.apply(dataFlowModel.getSeqType()));
            }
        }
        if (isNotNull(dataFlowModel.getFlow())) {
            stringBuilder.append("AND LOWER( PC.FLOW ) LIKE ? ");
            if (isCountQuery) {
                queryValues.add(addDBContains.apply(dataFlowModel.getFlow()));
            }
        }
        if (isNotNull(dataFlowModel.getFileType())) {
            stringBuilder.append("AND LOWER( PD.FILENAME_PATTERN) LIKE ? ");
            if (isCountQuery) {
                queryValues.add(addDBContains.apply(dataFlowModel.getFileType()));
            }
        }
        if (isNotNull(dataFlowModel.getDocType())) {
            stringBuilder.append("AND LOWER( PD.DOCTYPE ) LIKE ? ");
            if (isCountQuery) {
                queryValues.add(addDBContains.apply(dataFlowModel.getDocType()));
            }
        }
        if (isNotNull(dataFlowModel.getTransaction())) {
            stringBuilder.append("AND LOWER( PD.DOCTRANS ) LIKE ? ");
            if (isCountQuery) {
                queryValues.add(addDBContains.apply(dataFlowModel.getTransaction()));
            }
        }
        if (isNotNull(dataFlowModel.getSenderId())) {
            stringBuilder.append("AND LOWER( PD.PARTNERID ) LIKE ? ");
            if (isCountQuery) {
                queryValues.add(addDBContains.apply(dataFlowModel.getSenderId()));
            }
        }
        if (isNotNull(dataFlowModel.getReceiverId())) {
            stringBuilder.append("AND LOWER( PD.RECIVERID ) LIKE ? ");
            if (isCountQuery) {
                queryValues.add(addDBContains.apply(dataFlowModel.getReceiverId()));
            }
        }
        if (!isOnlyFlows) {
            if (isNotNull(dataFlowModel.getRuleName())) {
                stringBuilder.append("AND LOWER( PR.RULE_NAME ) LIKE ? ");
                if (isCountQuery) {
                    queryValues.add(addDBContains.apply(dataFlowModel.getRuleName()));
                }
            }
            if (isNotNull(dataFlowModel.getRuleValue())) {
                stringBuilder.append("AND ( LOWER( PR.PROPERTY_NAME_1 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_2 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_3 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_4 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_5 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_6 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_7 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_8 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_9 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_10 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_11 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_12 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_13 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_14 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_15 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_16 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_17 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_18 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_19 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_20 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_21 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_22 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_23 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_24 ) LIKE ? ")
                        .append("OR LOWER( PR.PROPERTY_NAME_25 ) LIKE ? ")
                        .append(") ");
                if (isCountQuery) {
                    IntStream.rangeClosed(0, 24).forEach(count -> queryValues.add(addDBContains.apply(dataFlowModel.getRuleValue())));
                }
            }
            if (isNotNull(dataFlowModel.getRuleModel().getRuleName())) {
                stringBuilder.append("AND  PR.RULE_NAME  = ? ");
                if (isCountQuery) {
                    queryValues.add(dataFlowModel.getRuleModel().getRuleName());
                }
            }
            if (isNotNull(dataFlowModel.getRuleModel().getPropertyName1())) {
                stringBuilder.append("AND LOWER( PR.PROPERTY_NAME_1 ) LIKE ? ");
                if (isCountQuery) {
                    queryValues.add(addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName1()));
                }
            }
            if (isNotNull(dataFlowModel.getRuleModel().getPropertyName2())) {
                stringBuilder.append("AND LOWER( PR.PROPERTY_NAME_2 ) LIKE ? ");
                if (isCountQuery) {
                    queryValues.add(addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName2()));
                }
            }
            if (isNotNull(dataFlowModel.getRuleModel().getPropertyName3())) {
                stringBuilder.append("AND LOWER( PR.PROPERTY_NAME_3 ) LIKE ? ");
                if (isCountQuery) {
                    queryValues.add(addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName3()));
                }
            }
            if (isNotNull(dataFlowModel.getRuleModel().getPropertyName4())) {
                stringBuilder.append("AND LOWER( PR.PROPERTY_NAME_4 ) LIKE ? ");
                if (isCountQuery) {
                    queryValues.add(addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName4()));
                }
            }
            if (isNotNull(dataFlowModel.getRuleModel().getPropertyName5())) {
                stringBuilder.append("AND LOWER( PR.PROPERTY_NAME_5 ) LIKE ? ");
                if (isCountQuery) {
                    queryValues.add(addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName5()));
                }
            }
            if (isNotNull(dataFlowModel.getRuleModel().getPropertyName6())) {
                stringBuilder.append("AND LOWER( PR.PROPERTY_NAME_6 ) LIKE ? ");
                if (isCountQuery) {
                    queryValues.add(addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName6()));
                }
            }
            if (isNotNull(dataFlowModel.getRuleModel().getPropertyName7())) {
                stringBuilder.append("AND LOWER( PR.PROPERTY_NAME_7 ) LIKE ? ");
                if (isCountQuery) {
                    queryValues.add(addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName7()));
                }
            }
            if (isNotNull(dataFlowModel.getRuleModel().getPropertyName8())) {
                stringBuilder.append("AND LOWER( PR.PROPERTY_NAME_8 ) LIKE ? ");
                if (isCountQuery) {
                    queryValues.add(addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName8()));
                }
            }
            if (isNotNull(dataFlowModel.getRuleModel().getPropertyName9())) {
                stringBuilder.append("AND LOWER( PR.PROPERTY_NAME_9 ) LIKE ? ");
                if (isCountQuery) {
                    queryValues.add(addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName9()));
                }
            }
            if (isNotNull(dataFlowModel.getRuleModel().getPropertyName10())) {
                stringBuilder.append("AND LOWER( PR.PROPERTY_NAME_10 ) LIKE ? ");
                if (isCountQuery) {
                    queryValues.add(addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName10()));
                }
            }
            if (isNotNull(dataFlowModel.getRuleModel().getPropertyName11())) {
                stringBuilder.append("AND LOWER( PR.PROPERTY_NAME_11 ) LIKE ? ");
                if (isCountQuery) {
                    queryValues.add(addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName11()));
                }
            }
            if (isNotNull(dataFlowModel.getRuleModel().getPropertyName12())) {
                stringBuilder.append("AND LOWER( PR.PROPERTY_NAME_12 ) LIKE ? ");
                if (isCountQuery) {
                    queryValues.add(addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName12()));
                }
            }
            if (isNotNull(dataFlowModel.getRuleModel().getPropertyName13())) {
                stringBuilder.append("AND LOWER( PR.PROPERTY_NAME_13 ) LIKE ? ");
                if (isCountQuery) {
                    queryValues.add(addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName13()));
                }
            }
            if (isNotNull(dataFlowModel.getRuleModel().getPropertyName14())) {
                stringBuilder.append("AND LOWER( PR.PROPERTY_NAME_14 ) LIKE ? ");
                if (isCountQuery) {
                    queryValues.add(addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName14()));
                }
            }
            if (isNotNull(dataFlowModel.getRuleModel().getPropertyName15())) {
                stringBuilder.append("AND LOWER( PR.PROPERTY_NAME_15 ) LIKE ? ");
                if (isCountQuery) {
                    queryValues.add(addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName15()));
                }
            }
            if (isNotNull(dataFlowModel.getRuleModel().getPropertyName16())) {
                stringBuilder.append("AND LOWER( PR.PROPERTY_NAME_16 ) LIKE ? ");
                if (isCountQuery) {
                    queryValues.add(addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName16()));
                }
            }
            if (isNotNull(dataFlowModel.getRuleModel().getPropertyName17())) {
                stringBuilder.append("AND LOWER( PR.PROPERTY_NAME_17 ) LIKE ? ");
                if (isCountQuery) {
                    queryValues.add(addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName17()));
                }
            }
            if (isNotNull(dataFlowModel.getRuleModel().getPropertyName18())) {
                stringBuilder.append("AND LOWER( PR.PROPERTY_NAME_18 ) LIKE ? ");
                if (isCountQuery) {
                    queryValues.add(addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName18()));
                }
            }
            if (isNotNull(dataFlowModel.getRuleModel().getPropertyName19())) {
                stringBuilder.append("AND LOWER( PR.PROPERTY_NAME_19 ) LIKE ? ");
                if (isCountQuery) {
                    queryValues.add(addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName19()));
                }
            }
            if (isNotNull(dataFlowModel.getRuleModel().getPropertyName20())) {
                stringBuilder.append("AND LOWER( PR.PROPERTY_NAME_20 ) LIKE ? ");
                if (isCountQuery) {
                    queryValues.add(addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName20()));
                }
            }
            if (isNotNull(dataFlowModel.getRuleModel().getPropertyName21())) {
                stringBuilder.append("AND LOWER( PR.PROPERTY_NAME_21 ) LIKE ? ");
                if (isCountQuery) {
                    queryValues.add(addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName21()));
                }
            }
            if (isNotNull(dataFlowModel.getRuleModel().getPropertyName22())) {
                stringBuilder.append("AND LOWER( PR.PROPERTY_NAME_22 ) LIKE ? ");
                if (isCountQuery) {
                    queryValues.add(addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName22()));
                }
            }
            if (isNotNull(dataFlowModel.getRuleModel().getPropertyName23())) {
                stringBuilder.append("AND LOWER( PR.PROPERTY_NAME_23 ) LIKE ? ");
                if (isCountQuery) {
                    queryValues.add(addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName23()));
                }
            }
            if (isNotNull(dataFlowModel.getRuleModel().getPropertyName24())) {
                stringBuilder.append("AND LOWER( PR.PROPERTY_NAME_24 ) LIKE ? ");
                if (isCountQuery) {
                    queryValues.add(addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName24()));
                }
            }
            if (isNotNull(dataFlowModel.getRuleModel().getPropertyName25())) {
                stringBuilder.append("AND LOWER( PR.PROPERTY_NAME_25 ) LIKE ? ");
                if (isCountQuery) {
                    queryValues.add(addDBContains.apply(dataFlowModel.getRuleModel().getPropertyName25()));
                }
            }

        }
        if (!partnerPkIdList.isEmpty()) {
            if (partnerPkIdList.size() > 998) {
                stringBuilder.append(" AND ( PC.PARTNER_PROFILE  IN ( ");
            } else {
                stringBuilder.append(" AND PC.PARTNER_PROFILE  IN ( ");
            }
            AtomicInteger atomicInteger = new AtomicInteger(1);
            AtomicBoolean atomicBoolean = new AtomicBoolean(false);
            partnerPkIdList.forEach(partnerPkId -> {
                if (atomicBoolean.get()) {
                    stringBuilder.append(", ?");
                } else {
                    stringBuilder.append(" ?");
                    atomicBoolean.set(true);
                }
                if (partnerPkIdList.size() > 998 && atomicInteger.getAndIncrement() % 998 == 0) {
                    stringBuilder.append(" ) OR PC.PARTNER_PROFILE  IN ( ");
                    atomicBoolean.set(false);
                }
                if (isCountQuery) {
                    queryValues.add(partnerPkId);
                }
            });
            stringBuilder.append(" ) ");
            if (partnerPkIdList.size() > 998) {
                stringBuilder.append(" ) ");
            }
        }

    }

    public static String getDbColumnName(String searchString) {
        if (searchString.equals("receiverId")) {
            return "RECIVERID";
        }
        return SortField.valueOf(searchString.toUpperCase()).getField();
    }

    public static final UnaryOperator<String> addDBContains = value -> "%" + value.toLowerCase() + "%";

    public static void applyFilterForUpdateSql(String operation, StringBuilder stringBuilder, List<String> queryParams, String columnName, String columnValue, AtomicInteger atomicInteger) {
        if (isNotNull(columnName) && isNotNull(columnValue)) {
            if (operation.equalsIgnoreCase(SQL_INSERT_CLAUSE)) {
                if (atomicInteger.getAndIncrement() == 0) {
                    stringBuilder.append("( ").append(columnName);
                } else {
                    stringBuilder.append(", ").append(columnName);
                }
            } else {
                if (atomicInteger.getAndIncrement() == 0) {
                    stringBuilder.append(columnName).append("= ? ");
                } else {
                    stringBuilder.append(", ").append(columnName).append("= ? ");
                }
            }
            queryParams.add(columnValue);
        }
    }

    public static void applyFilterForUpdateSqlWithDataType(String operation, StringBuilder stringBuilder, List<Object> queryParams, String columnName, String columnValue, String dataType, String dbType, AtomicInteger atomicInteger) {
        if (isNotNull(columnName) && isNotNull(columnValue) && isNotNull(dataType)) {
            if (operation.equalsIgnoreCase(SQL_INSERT_CLAUSE)) {
                if (atomicInteger.getAndIncrement() == 0) {
                    stringBuilder.append("( ").append(columnName);
                } else {
                    stringBuilder.append(", ").append(columnName);
                }
            } else {
                if (atomicInteger.getAndIncrement() == 0) {
                    stringBuilder.append(columnName).append("= ? ");
                } else {
                    stringBuilder.append(", ").append(columnName).append("= ? ");
                }
            }
            applyQueryParams(dataType, columnValue, dbType, queryParams);
        }
    }

    public static void applyQueryParams(String dataType, String columnValue, String dbType, List<Object> queryParams) {
        switch (dataType.toUpperCase()) {
            case INTEGER:
            case NUMBER:
                queryParams.add(Integer.parseInt(columnValue));
                break;
            case FLOAT:
                queryParams.add(Float.valueOf(columnValue));
                break;
            case DOUBLE:
                queryParams.add(Double.valueOf(columnValue));
                break;
            case TIMESTAMP:
                queryParams.add(convertDate(columnValue, dbType));
                break;
            case VARCHAR:
            case VARCHAR2:
                queryParams.add(columnValue);
                break;
            case TIME:
                if (dbType.equals(DB2) || dbType.equals(SQL_SERVER)) {
                    try {
                        queryParams.add(Time.valueOf(columnValue));
                    } catch (Exception ex) {
                        if (ex.toString().endsWith("IllegalArgumentException")) {
                            throw new IllegalArgumentException("Please give Time format as HH:MM:SS");
                        }
                    }

                } else {
                    throw new IllegalStateException("Given DataType is not Supported by DB " + dataType);
                }
                break;
            case DATE:
                if (dbType.equals(DB2) || dbType.equals(SQL_SERVER)) {
                    try {
                        queryParams.add(valueOf(columnValue));
                    } catch (Exception ex) {
                        if (ex.toString().endsWith("IllegalArgumentException")) {
                            throw new IllegalArgumentException("Please give Date format as YYYY-MM-DD, Ex: 2020-10-20");
                        }
                    }

                } else if (dbType.equals(ORACLE)) {
                    queryParams.add(convertDateForOracle(columnValue));
                } else {
                    throw new IllegalStateException("Given DataType is not Supported by DB " + dataType);
                }

                break;
            case BOOLEAN:
                if (dbType.equals(DB2)) {
                    queryParams.add(Boolean.valueOf(columnValue));
                } else {
                    throw new IllegalStateException("Given DataType is not Supported by DB " + dataType);
                }
            default:
                queryParams.add(columnValue);
                //throw new IllegalStateException("Unexpected value: " + dataType);
        }

    }

    private static String convertDate(String stringDate, String dbType) {
        if (isNotNull(stringDate)) {
            if (dbType.equals(ORACLE)) {
                return stringDate;
            } else if (dbType.equals(SQL_SERVER) || dbType.equals(DB2)) {
                return convertStringToTimestamp(stringDate).toString();
            }
        }
        return "%";
    }

    public static final UnaryOperator<String> generateQueryToGetSequence = dbType -> {
        if (dbType.equals(ORACLE)) {
            return "SELECT SEQ_PETPE_PEM_GEN.NEXTVAL FROM DUAL";
        } else if (dbType.equals(DB2)) {
            return "SELECT NEXT VALUE FOR SEQ_PETPE_PEM_GEN FROM SYSIBM.SYSDUMMY1";
        } else {
            return "SELECT NEXT VALUE FOR SEQ_PETPE_PEM_GEN";
        }
    };

    // EFX Reports Dependence

    public static void addRequiredFiltersForReportData(StringBuilder stringBuilder, TransferInfoListModel transferInfoListModel, String dbType) {

        if (hasText(transferInfoListModel.getDateRangeStart()) && hasText(transferInfoListModel.getDateRangeEnd())) {
            stringBuilder.append(" AND PE.FILEARRIVED BETWEEN ");
            if (dbType.equals(ORACLE) || dbType.equals(DB2)) {
                stringBuilder.append(SQL_TO_TIMESTAMP + "( ' ").append(transferInfoListModel.getDateRangeStart()).append("','").append(DATE_FORMAT_ORACLE).append("') AND ")
                        .append(SQL_TO_TIMESTAMP + "( '").append(transferInfoListModel.getDateRangeEnd()).append("','").append(DATE_FORMAT_ORACLE).append("') ");
            } else if (dbType.equals(SQL_SERVER)) {
                stringBuilder.append("( ' ").append(convertStringToTimestamp(transferInfoListModel.getDateRangeStart())).append("') AND ")
                        .append("( '").append(convertStringToTimestamp(transferInfoListModel.getDateRangeEnd())).append("') ");
            }
        }

        if (Objects.equals(transferInfoListModel.getReportType(), "SFG")) {
            stringBuilder.append(" AND PE.PNODE IS NULL ");
            stringBuilder.append(" AND PE.SNODE IS NULL ");
        } else if (Objects.equals(transferInfoListModel.getReportType(), "PCD")) {
            stringBuilder.append("AND PE.PNODE IS NOT NULL ");
            stringBuilder.append("AND PE.SNODE IS NOT NULL ");
        }

        if (!transferInfoListModel.getPartner().isEmpty() && !transferInfoListModel.getPartner().contains("")) {
            stringBuilder.append("AND (PE.PARTNER IN ( '");
            AtomicBoolean atomicBoolean = new AtomicBoolean(false);
            transferInfoListModel.getPartner().forEach(partnerName -> {

                if (atomicBoolean.get()) {
                    stringBuilder.append(",'").append(partnerName).append("'");
                } else {
                    stringBuilder.append(partnerName).append("'");
                    atomicBoolean.set(true);
                }
            });
            stringBuilder.append(" )) ");
        }

        if (!transferInfoListModel.getApplication().isEmpty() && !transferInfoListModel.getApplication().contains("")) {
            stringBuilder.append("AND (PE.APPLICATION IN ( '");
            AtomicBoolean atomicBoolean1 = new AtomicBoolean(false);
            transferInfoListModel.getApplication().forEach(applicationName -> {

                if (atomicBoolean1.get()) {
                    stringBuilder.append(",'").append(applicationName).append("'");
                } else {
                    stringBuilder.append(applicationName).append("'");
                    atomicBoolean1.set(true);
                }
            });
            stringBuilder.append(" )) ");
        }

        if (!transferInfoListModel.getUid().isEmpty() && !transferInfoListModel.getUid().contains("")) {
            stringBuilder.append("AND (PE.UID_NUMBER IN ( '");
            AtomicBoolean atomicBoolean2 = new AtomicBoolean(false);
            transferInfoListModel.getUid().forEach(uidColumn -> {

                if (atomicBoolean2.get()) {
                    stringBuilder.append(",'").append(uidColumn).append("'");
                } else {
                    stringBuilder.append(uidColumn).append("'");
                    atomicBoolean2.set(true);
                }
            });
            stringBuilder.append(" )) ");
        }

        if (!transferInfoListModel.getApp().isEmpty() && !transferInfoListModel.getApp().contains("")) {
            stringBuilder.append("AND (PE.APP IN ( '");
            AtomicBoolean atomicBoolean2 = new AtomicBoolean(false);
            transferInfoListModel.getApp().forEach(app -> {

                if (atomicBoolean2.get()) {
                    stringBuilder.append(",'").append(app).append("'");
                } else {
                    stringBuilder.append(app).append("'");
                    atomicBoolean2.set(true);
                }
            });
            stringBuilder.append(" )) ");
        }

        if (!transferInfoListModel.getBu().isEmpty() && !transferInfoListModel.getBu().contains("")) {
            stringBuilder.append("AND (PE.BU IN ( '");
            AtomicBoolean atomicBoolean2 = new AtomicBoolean(false);
            transferInfoListModel.getBu().forEach(bu -> {

                if (atomicBoolean2.get()) {
                    stringBuilder.append(",'").append(bu).append("'");
                } else {
                    stringBuilder.append(bu).append("'");
                    atomicBoolean2.set(true);
                }
            });
            stringBuilder.append(" )) ");
        }

        if (!transferInfoListModel.getSrcFileName().isEmpty()) {
            stringBuilder.append("AND PE.SRCFILENAME LIKE '%").append(transferInfoListModel.getSrcFileName()).append("%'");
        }

        if (!transferInfoListModel.getDestFileName().isEmpty()) {
            stringBuilder.append("AND PE.DESTFILENAME LIKE '%").append(transferInfoListModel.getDestFileName()).append("%'");
        }
    }

    public static final UnaryOperator<String> getGroupByColumnQuery = columnName -> "SELECT " + columnName + ", COUNT(*) FROM PETPE_TRANSFERINFO WHERE FILEARRIVED BETWEEN ? AND ? IN_SECTION AND " + columnName + " IS NOT NULL GROUP BY " + columnName;

    public static Timestamp[] getThisWeekTimeRange() {
        String stringDate = getTodayAsString().trim().split(" ")[0];
        Calendar c = Calendar.getInstance();
        c.setTime(Timestamp.valueOf(stringDate + " 00:00:00"));
        int i = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
        c.add(Calendar.DATE, -i);
        Timestamp startTime = new Timestamp(c.getTimeInMillis());
        c.add(Calendar.DATE, 6);
        c.add(Calendar.HOUR, 23);
        c.add(Calendar.MINUTE, 59);
        return new Timestamp[]{startTime, new Timestamp(c.getTimeInMillis())};
    }

    public static Timestamp[] getThisMonthTimeRange() {
        String stringDate = getTodayAsString().trim().split(" ")[0];
        Calendar c = Calendar.getInstance();
        c.setTime(Timestamp.valueOf(stringDate + " 00:00:00"));
        int i = c.get(Calendar.DAY_OF_MONTH) - 1;
        c.add(Calendar.DATE, -i);
        Timestamp startTime = new Timestamp(c.getTimeInMillis());
        c.add(Calendar.DATE, i);
        c.add(Calendar.HOUR, 23);
        c.add(Calendar.MINUTE, 59);
        return new Timestamp[]{startTime, new Timestamp(c.getTimeInMillis())};
    }

    public static Timestamp[] getThisDayTimeRange() {
        String stringTime = getTodayAsString();
        String stringDate = stringTime.trim().split(" ")[0];
        return new Timestamp[]{
                Timestamp.valueOf(stringDate + " 00:00:00"),
                Timestamp.valueOf(stringDate + " 24:59:59")
        };
    }

    public static void filterQueryTransInfoAndTransInfoD(StringBuilder filterQuery, TransferInfoModel transferInfoModel, List<String> partnerNames, List<String> queryParamValues, boolean isPEM, String dbType) {
        filterQuery.append(" WHERE 1=1 ");
        if (hasText(transferInfoModel.getDateRangeStart()) && hasText(transferInfoModel.getDateRangeEnd())) {
            filterQuery.append(" AND FILEARRIVED BETWEEN ? AND ? ");
        }

        addPredicateForUnionInfoAndD(filterQuery, "FLOWINOUT", transferInfoModel.getFlowInOut(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "TYPEOFTRANSFER", transferInfoModel.getTypeOfTransfer(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "DOCTYPE", transferInfoModel.getDoctype(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "SRCPROTOCOL", transferInfoModel.getSrcProtocol(), queryParamValues, FALSE);
        addPredicateForUnionInfoAndD(filterQuery, "DESTPROTOCOL", transferInfoModel.getDestProtocol(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "PARTNER", transferInfoModel.getPartner(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "APPLICATION", transferInfoModel.getApplication(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "SENDERID", transferInfoModel.getSenderId(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "RECIVERID", transferInfoModel.getReceiverId(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "ERRORSTATUS", transferInfoModel.getErrorStatus(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "SRCFILENAME", transferInfoModel.getSrcFileName(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "DESTFILENAME", transferInfoModel.getDestFileName(), queryParamValues, TRUE);

        if (hasText(transferInfoModel.getStatus())) {
            if (transferInfoModel.getStatus().equalsIgnoreCase("No Action Required")) {
                filterQuery.append(" AND (STATUS = NULL OR STATUS = '' OR STATUS = 'No Action Required'");
            } else {
                filterQuery.append(" AND LOWER(STATUS) LIKE ? ");
                queryParamValues.add(addDBContains.apply(transferInfoModel.getStatus()));
            }
        }
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_1", transferInfoModel.getCorrelationValue1(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_2", transferInfoModel.getCorrelationValue2(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_3", transferInfoModel.getCorrelationValue3(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_4", transferInfoModel.getCorrelationValue4(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_5", transferInfoModel.getCorrelationValue5(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_6", transferInfoModel.getCorrelationValue6(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_7", transferInfoModel.getCorrelationValue7(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_8", transferInfoModel.getCorrelationValue8(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_9", transferInfoModel.getCorrelationValue9(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_10", transferInfoModel.getCorrelationValue10(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_11", transferInfoModel.getCorrelationValue11(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_12", transferInfoModel.getCorrelationValue12(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_13", transferInfoModel.getCorrelationValue13(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_14", transferInfoModel.getCorrelationValue14(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_15", transferInfoModel.getCorrelationValue15(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_16", transferInfoModel.getCorrelationValue16(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_17", transferInfoModel.getCorrelationValue17(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_18", transferInfoModel.getCorrelationValue18(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_19", transferInfoModel.getCorrelationValue19(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_20", transferInfoModel.getCorrelationValue20(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_21", transferInfoModel.getCorrelationValue21(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_22", transferInfoModel.getCorrelationValue22(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_23", transferInfoModel.getCorrelationValue23(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_24", transferInfoModel.getCorrelationValue24(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_25", transferInfoModel.getCorrelationValue25(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_26", transferInfoModel.getCorrelationValue26(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_27", transferInfoModel.getCorrelationValue27(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_28", transferInfoModel.getCorrelationValue28(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_29", transferInfoModel.getCorrelationValue29(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_30", transferInfoModel.getCorrelationValue30(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_31", transferInfoModel.getCorrelationValue31(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_32", transferInfoModel.getCorrelationValue32(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_33", transferInfoModel.getCorrelationValue33(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_34", transferInfoModel.getCorrelationValue34(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_35", transferInfoModel.getCorrelationValue35(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_36", transferInfoModel.getCorrelationValue36(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_37", transferInfoModel.getCorrelationValue37(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_38", transferInfoModel.getCorrelationValue38(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_39", transferInfoModel.getCorrelationValue39(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_40", transferInfoModel.getCorrelationValue40(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_41", transferInfoModel.getCorrelationValue41(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_42", transferInfoModel.getCorrelationValue42(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_43", transferInfoModel.getCorrelationValue43(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_44", transferInfoModel.getCorrelationValue44(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_45", transferInfoModel.getCorrelationValue45(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_46", transferInfoModel.getCorrelationValue46(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_47", transferInfoModel.getCorrelationValue47(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_48", transferInfoModel.getCorrelationValue48(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_49", transferInfoModel.getCorrelationValue49(), queryParamValues, TRUE);
        addPredicateForUnionInfoAndD(filterQuery, "CORRELATION_VALUE_50", transferInfoModel.getCorrelationValue50(), queryParamValues, TRUE);

        if (isNotNull(transferInfoModel.getFileNameRegExpression())) {
            getPredicateWithRegularExp(filterQuery, SRC_FILE_NAME_COLUMN, transferInfoModel.getFileNameRegExpression(), queryParamValues, dbType);
        }

        if (isPEM) {
            addPredicateForUnionInfoAndD(filterQuery, "PARTNER", "template", queryParamValues, TRUE);
        }

        if (!partnerNames.isEmpty()) {
            if (partnerNames.size() > 998) {
                filterQuery.append(" AND ( PARTNER IN ( ");
            } else {
                filterQuery.append(" AND PARTNER IN ( ");
            }
            AtomicInteger check = new AtomicInteger(0);
            AtomicBoolean atomicBoolean = new AtomicBoolean(false);
            partnerNames.forEach(partnerPkId -> {
                if (atomicBoolean.get()) {
                    filterQuery.append(", ?");
                } else {
                    filterQuery.append(" ?");
                    atomicBoolean.set(true);
                }
                if (partnerNames.size() > 998 && check.getAndIncrement() % 998 == 0) {
                    filterQuery.append(" ) OR PC.PARTNER IN ( ");
                }

            });
            filterQuery.append(" ) ");
            if (partnerNames.size() > 998) {
                filterQuery.append(" ) ");
            }
        }

    }

    private static void getPredicateWithRegularExp(StringBuilder filterQuery, String fieldName, String fieldValue, List<String> queryParamValues, String dbType) {
        if (isNotNull(fieldValue)) {
            if (dbType.equalsIgnoreCase(ORACLE)) {
                filterQuery.append("REGEXP_LIKE (").append(fieldName).append(", ?)");
                queryParamValues.add(fieldValue);
            } else if (dbType.equalsIgnoreCase(SQL_SERVER) || dbType.equalsIgnoreCase(DB2)) {
                LOGGER.info("Regular Expressions implementation: we are not applying in query level for Sql Server and DB2");
            }
        }
    }

    private static void addPredicateForUnionInfoAndD(StringBuilder predicateQuery, String fieldName, String fieldValue, List<String> queryParamValues, boolean isLike) {
        if (hasText(fieldValue)) {
            if (isLike) {
                predicateQuery.append("AND LOWER(").append(fieldName).append(") LIKE ? ");
                queryParamValues.add(addDBContains.apply(fieldValue));
            } else {
                predicateQuery.append("AND LOWER(").append(fieldName).append(") = ? ");
                queryParamValues.add(fieldValue.toLowerCase());
            }
        }
    }

    public static Timestamp[] getThisHourTimeRange() {
        String stringTime = getTodayAsString();
        String stringDate = stringTime.trim().split(" ")[0];
        String stringHour = stringTime.trim().split(" ")[1].split(":")[0];
        return new Timestamp[]{
                Timestamp.valueOf(stringDate + " " + stringHour + ":00:00"),
                Timestamp.valueOf(stringDate + " " + stringHour + ":59:59")
        };
    }

    private static String getTodayAsString() {
        java.util.Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(date);
    }

}
