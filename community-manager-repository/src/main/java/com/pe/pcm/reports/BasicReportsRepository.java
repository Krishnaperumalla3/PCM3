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

import com.pe.pcm.common.GenericBuilder;
import com.pe.pcm.profile.ProfileModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.pe.pcm.utils.PCMConstants.*;
import static com.pe.pcm.utils.ReportsCommonFunctions.*;
import static org.springframework.util.StringUtils.hasText;

@Repository
public class BasicReportsRepository {

    private static final String COUNT_QUERY_TRANSFER_INFO = "SELECT COUNT(SEQID) FROM PETPE_TRANSFERINFO WHERE FILEARRIVED BETWEEN ? AND ? ";

    private static final Logger LOGGER = LoggerFactory.getLogger(BasicReportsRepository.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BasicReportsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<NameCountModel> fileProcessByMonthLast36Months(BasicReportRequestModel basicReportRequestModel) {
        String query = "SELECT trunc(FILEARRIVED,'MM') AS \"date\", count(*) \"count\" from PETPE_Transferinfo where FILEARRIVED >= add_months(trunc(sysdate,'MM'),-36) and FILEARRIVED <  trunc(sysdate,'MM')  ? group by trunc(FILEARRIVED,'MM') order by 1 desc";
        return loadData(buildQuery(query, basicReportRequestModel, ORACLE), basicReportRequestModel, false, false);
    }

    public List<NameCountModel> fileProcessByDayLast12Months(BasicReportRequestModel basicReportRequestModel) {
        String query = "SELECT trunc(FILEARRIVED) AS \"date\", count(*) AS \"count\"  from PETPE_Transferinfo where FILEARRIVED > trunc(add_months(sysdate,-12),'MM') ? group by trunc  (FILEARRIVED) order by 1 desc";
        return loadData(buildQuery(query, basicReportRequestModel, ORACLE), basicReportRequestModel, false, false);
    }

    public List<NameCountModel> fileProcessByHourLast30Days(BasicReportRequestModel basicReportRequestModel) {
        String query = "SELECT trunc(FILEARRIVED,'HH') AS \"date\", sum(src_file_size) AS \"count\"  from PETPE_Transferinfo where FILEARRIVED > sysdate -31  ? group by trunc(FILEARRIVED,'HH') order by 1 desc";
        return loadData(buildQuery(query, basicReportRequestModel, ORACLE), basicReportRequestModel, false, true);
    }

    public List<NameCountModel> fileSizeProcessByHourLast30Days(BasicReportRequestModel basicReportRequestModel) {
        String query = "SELECT trunc(FILEARRIVED,'HH') AS \"date\", sum(src_file_size) AS \"count\"  from PETPE_Transferinfo where FILEARRIVED > sysdate -31  ? group by trunc(FILEARRIVED,'HH') order by 1 desc";
        return loadData(buildQuery(query, basicReportRequestModel, ORACLE), basicReportRequestModel, false, true);
    }

    public List<NameCountModel> fileSizeProcessByMonthLast36Months(BasicReportRequestModel basicReportRequestModel) {
        String query = "SELECT trunc(FILEARRIVED,'MM') AS \"date\", sum(src_file_size) \"count\" from PETPE_Transferinfo where FILEARRIVED >= add_months(trunc(sysdate,'MM'),-36) and FILEARRIVED <  trunc(sysdate,'MM')  ? group by trunc(FILEARRIVED,'MM') order by 1 desc";
        return loadData(buildQuery(query, basicReportRequestModel, ORACLE), basicReportRequestModel, false, false);
    }

    public List<NameCountModel> fileSizeProcessByDayLast12Months(BasicReportRequestModel basicReportRequestModel) {
        String query = "SELECT trunc(FILEARRIVED) AS \"date\", sum(src_file_size) AS \"count\"  from PETPE_Transferinfo where FILEARRIVED > trunc(add_months(sysdate,-12),'MM') ? group by trunc  (FILEARRIVED) order by 1 desc";
        return loadData(buildQuery(query, basicReportRequestModel, ORACLE), basicReportRequestModel, false, false);
    }

    public List<NameCountModel> fileProcessByDayInWeekAvgLast4Weeks(BasicReportRequestModel basicReportRequestModel) {
        String query = "SELECT FILEARRIVED AS \"date\", cnt AS \"count\",  round(SUM(cnt) OVER( partition by FILEARRIVED_W ORDER BY FILEARRIVED ROWS BETWEEN  4 PRECEDING and 1 PRECEDING)/4)  avg  from " +
                "(" +
                "select trunc(FILEARRIVED) FILEARRIVED, to_char(FILEARRIVED, 'd') FILEARRIVED_W,  count(*) cnt " +
                "from PETPE_Transferinfo " +
                "where FILEARRIVED > sysdate -91 " +
                " ? group by trunc(FILEARRIVED), to_char(FILEARRIVED, 'd')" +
                ")" +
                " where FILEARRIVED >= next_day(sysdate - 14,'SUN')  and FILEARRIVED < next_day(sysdate -7,'SAT') " +
                " order by 1 desc";
        return loadData(buildQuery(query, basicReportRequestModel, ORACLE), basicReportRequestModel, true, false);
    }

    public List<NameCountModel> fileProcessPeakHoursLast31Days(BasicReportRequestModel basicReportRequestModel) {
        String query = "SELECT to_char(FILEARRIVED, 'HH24') AS \"date\",  round(count(*)/31)  count " +
                "from PETPE_Transferinfo " +
                "where FILEARRIVED >= trunc(sysdate) - 31 and  FILEARRIVED < trunc(sysdate) " +
                " ? group by to_char(FILEARRIVED, 'HH24')" +
                "order by 2 desc ";
        return loadData(buildQuery(query, basicReportRequestModel, ORACLE), basicReportRequestModel, false, false)
                .stream()
                .map(nameCountModel -> nameCountModel.setName(nameCountModel.getName() + (Integer.parseInt(nameCountModel.getName()) < 12 ? " AM" : " PM")))
                .collect(Collectors.toList());
    }

    public List<ProfileModel> inactivePartnersLastOneYear(Integer days) {
        String query = "select TP_NAME, EMAIL, PHONE from petpe_tradingpartner where TP_NAME NOT IN( select distinct b.TP_NAME from petpe_transferinfo a , petpe_tradingpartner b where b.TP_IS_ACTIVE ='N' and a.FILEARRIVED > (sysdate-?) group by TP_NAME)";
        return loadInactiveProfiles(query.replace("?", String.valueOf(days)), new BasicReportRequestModel());
    }

    public int filesProcessedThisHour(BasicReportRequestModel basicReportRequestModel) {
        List<String> queryParamValues = new ArrayList<>();
        return loadCount(buildQueryForCount(COUNT_QUERY_TRANSFER_INFO, basicReportRequestModel, queryParamValues), getThisHourTimeRange(), queryParamValues);
    }

    public int filesProcessedThisDay(BasicReportRequestModel basicReportRequestModel) {
        List<String> queryParamValues = new ArrayList<>();
        return loadCount(buildQueryForCount(COUNT_QUERY_TRANSFER_INFO, basicReportRequestModel, queryParamValues), getThisDayTimeRange(), queryParamValues);
    }

    public int filesProcessedThisWeek(BasicReportRequestModel basicReportRequestModel) {
        List<String> queryParamValues = new ArrayList<>();
        return loadCount(buildQueryForCount(COUNT_QUERY_TRANSFER_INFO, basicReportRequestModel, queryParamValues), getThisWeekTimeRange(), queryParamValues);
    }

    public int filesProcessedThisMonth(BasicReportRequestModel basicReportRequestModel) {
        List<String> queryParamValues = new ArrayList<>();
        return loadCount(buildQueryForCount(COUNT_QUERY_TRANSFER_INFO, basicReportRequestModel, queryParamValues), getThisMonthTimeRange(), queryParamValues);
    }

    private List<ProfileModel> loadInactiveProfiles(String query, BasicReportRequestModel basicReportRequestModel) {
        return jdbcTemplate.query(
                query,
                preparedStatement -> applyFilters(preparedStatement, basicReportRequestModel),
                (rs, rowNum) -> GenericBuilder.of(ProfileModel::new)
                        .with(ProfileModel::setProfileName, rs.getString("TP_NAME"))
                        .with(ProfileModel::setEmailId, rs.getString("EMAIL"))
                        .with(ProfileModel::setPhone, rs.getString("PHONE"))
                        .build()
        );
    }

    private int loadCount(String query, Timestamp[] timestamps, List<String> queryParamValues) {

        List<Integer> list = jdbcTemplate.query(
                query,
                preparedStatement -> {
                    AtomicInteger atomicInteger = new AtomicInteger(1);
                    preparedStatement.setTimestamp(atomicInteger.getAndIncrement(), timestamps[0]);
                    preparedStatement.setTimestamp(atomicInteger.getAndIncrement(), timestamps[1]);
                    queryParamValues.forEach(value -> {
                        try {
                            preparedStatement.setString(atomicInteger.getAndIncrement(), value);
                        } catch (Exception ignored) {

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

    private List<NameCountModel> loadData(String query, BasicReportRequestModel basicReportRequestModel, boolean isAvg, boolean isPerHour) {
        LOGGER.info("Query for Basic Report: {};", query);
        return jdbcTemplate.query(
                query,
                preparedStatement -> applyFilters(preparedStatement, basicReportRequestModel),
                (rs, rowNum) -> {
                    if (isAvg) {
                        return GenericBuilder.of(NameCountModel::new)
                                .with(NameCountModel::setName, rs.getString("date").replace(" 00:00:00.0", ""))
                                .with(NameCountModel::setCount, rs.getInt("count"))
                                .with(NameCountModel::setAvg, rs.getInt("avg"))
                                .build();
                    } else {
                        return GenericBuilder.of(NameCountModel::new)
                                .with(NameCountModel::setName, isPerHour ? rs.getString("date") : rs.getString("date").replace(" 00:00:00.0", ""))
                                .with(NameCountModel::setCount, rs.getInt("count"))
                                .build();
                    }
                }
        );
    }

    private String buildQueryForCount(String query, BasicReportRequestModel basicReportRequestModel, List<String> queryParamValues) {
        StringBuilder sb = new StringBuilder();
        if (hasText(basicReportRequestModel.getPartner())) {
            sb.append(" AND LOWER(PARTNER) LIKE ? ");
            queryParamValues.add("%" + basicReportRequestModel.getPartner().toLowerCase() + "%");
        }

        if (hasText(basicReportRequestModel.getStatus())) {
            sb.append(" AND LOWER(STATUS) LIKE ? ");
            queryParamValues.add("%" + basicReportRequestModel.getStatus().toLowerCase() + "%");
        }

        if (hasText(basicReportRequestModel.getDirection())) {
            sb.append(" AND LOWER(FLOWINOUT) LIKE ? ");
            queryParamValues.add("%" + basicReportRequestModel.getDirection().toLowerCase() + "%");
        }

        if (hasText(basicReportRequestModel.getTransferType())) {
            sb.append(" AND LOWER(TYPEOFTRANSFER) LIKE ? ");
            queryParamValues.add("%" + basicReportRequestModel.getTransferType().toLowerCase() + "%");
        }

        if (hasText(basicReportRequestModel.getTransaction())) {
            sb.append(" AND LOWER(DOCTRANS) LIKE ? ");
            queryParamValues.add("%" + basicReportRequestModel.getTransaction().toLowerCase() + "%");
        }

        if (hasText(basicReportRequestModel.getDocType())) {
            sb.append(" AND LOWER(DOCTYPE) LIKE ? ");
            queryParamValues.add("%" + basicReportRequestModel.getDocType().toLowerCase() + "%");
        }
        return query + sb;
    }

    private String buildQuery(String query, BasicReportRequestModel basicReportRequestModel, String dbType) {
        StringBuilder sb = new StringBuilder();
        if (hasText(basicReportRequestModel.getDataRangeStart()) && hasText(basicReportRequestModel.getDateRangeEnd())) {
            sb.append(" AND FILEARRIVED BETWEEN ");
            if (dbType.equals(ORACLE)) {
                sb.append(" ?  AND  ? ");
                //TODO we need to support other databases soon
//                sb.append(SQL_TO_TIMESTAMP + "( ? ").append(",'").append(PCMConstants.DATE_FORMAT_ORACLE).append("') AND ")
//                        .append(SQL_TO_TIMESTAMP + "( ? ").append(",'").append(DATE_FORMAT_ORACLE).append("') ");
            } else if (dbType.equals(SQL_SERVER) || dbType.equals(DB2)) {
                sb.append(" ?  AND  ? ");
            }
        }

        if (hasText(basicReportRequestModel.getPartner())) {
            sb.append(" AND LOWER(PARTNER) LIKE ? ");
        }

        if (hasText(basicReportRequestModel.getStatus())) {
            sb.append(" AND LOWER(STATUS) LIKE ? ");
        }

        if (hasText(basicReportRequestModel.getDirection())) {
            sb.append(" AND LOWER(FLOWINOUT) LIKE ? ");
        }

        if (hasText(basicReportRequestModel.getTransferType())) {
            sb.append(" AND LOWER(TYPEOFTRANSFER) LIKE ? ");
        }

        if (hasText(basicReportRequestModel.getTransaction())) {
            sb.append(" AND LOWER(DOCTRANS) LIKE ? ");
        }

        if (hasText(basicReportRequestModel.getDocType())) {
            sb.append(" AND LOWER(DOCTYPE) LIKE ? ");
        }
        return query.replace("?", sb.toString());
    }

    private String dbString(String value) {
        return "%" + value.toLowerCase() + "%";
    }

    private void applyFilters(PreparedStatement ps, BasicReportRequestModel basicReportRequestModel) throws SQLException {
        int index = 1;
        if (hasText(basicReportRequestModel.getDataRangeStart()) && hasText(basicReportRequestModel.getDateRangeEnd())) {
            ps.setTimestamp(index++, Timestamp.valueOf(basicReportRequestModel.getDataRangeStart()));
            ps.setTimestamp(index++, Timestamp.valueOf(basicReportRequestModel.getDateRangeEnd()));
        }

        if (hasText(basicReportRequestModel.getPartner())) {
            ps.setString(index++, dbString(basicReportRequestModel.getPartner()));
        }

        if (hasText(basicReportRequestModel.getStatus())) {
            ps.setString(index++, dbString(basicReportRequestModel.getStatus()));
        }

        if (hasText(basicReportRequestModel.getDirection())) {
            ps.setString(index++, dbString(basicReportRequestModel.getDirection()));
        }

        if (hasText(basicReportRequestModel.getTransferType())) {
            ps.setString(index++, dbString(basicReportRequestModel.getTransferType()));
        }

        if (hasText(basicReportRequestModel.getTransaction())) {
            ps.setString(index++, dbString(basicReportRequestModel.getTransferType()));
        }

        if (hasText(basicReportRequestModel.getDocType())) {
            ps.setString(index++, dbString(basicReportRequestModel.getDocType()));
        }
    }

}
