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

package com.pe.pcm.sql;

import com.pe.pcm.common.GenericModel;
import com.pe.pcm.exception.GlobalExceptionHandler;
import com.pe.pcm.miscellaneous.PasswordUtilityService;
import com.pe.pcm.pem.PemDbObjectDataTypeRepository;
import com.pe.pcm.pem.PemDbObjectRepository;
import com.pe.pcm.pem.entity.PemDbObjectDataTypeEntity;
import com.pe.pcm.pem.entity.PemDbObjectEntity;
import com.pe.pcm.utils.PCMConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.CommonFunctions.removeENC;
import static com.pe.pcm.utils.ReportsCommonFunctions.*;

/**
 * @author Kiran Reddy.
 */
@Service
public class DatabaseSetupService {

    private final String url;
    private final String userName;
    private String password;
    private final String driverClassName;
    private String dbType;
    private final PemDbObjectRepository pemDbObjectRepository;
    private final PemDbObjectDataTypeRepository pemDbObjectDataTypeRepository;
    private final PasswordUtilityService passwordUtilityService;

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseSetupService.class);

    @Autowired
    public DatabaseSetupService(@Value("${pem.datasource.url}") String url, @Value("${pem.datasource.username}") String userName,
                                @Value("${pem.datasource.cmks}") String password, @Value("${pem.datasource.driver-class-name}") String driverClassName,
                                PemDbObjectRepository pemDbObjectRepository, PemDbObjectDataTypeRepository pemDbObjectDataTypeRepository, PasswordUtilityService passwordUtilityService) {
        this.url = url;
        this.userName = userName;
        this.password = password;
        this.pemDbObjectRepository = pemDbObjectRepository;
        this.driverClassName = driverClassName;
        this.pemDbObjectDataTypeRepository = pemDbObjectDataTypeRepository;
        this.passwordUtilityService = passwordUtilityService;
    }

    public GenericModel sqlOperations(SingleSqlModel singleSqlModel) {
        String operation = singleSqlModel.getContent().trim().substring(0, 7).toLowerCase();
        if (operation.startsWith("select")) {
            return new GenericModel<>(executeSelectQuery(singleSqlModel));
        } else {
            return new GenericModel<>(executeUpdateQuery(singleSqlModel));
        }
    }

    public GenericModel sqlOperations(SqlRequestModel sqlRequestModel, String operationType) {

        if (operationType.equals("SELECT")) {
            return new GenericModel<>(executeSelectQuery(sqlRequestModel));
        } else {
            return new GenericModel<>(executeUpdateQuery(sqlRequestModel, operationType));
        }
    }

    public GenericModel sqlOperationsWithDataType(SqlRequestModel sqlRequestModel, String operationType) {

        if (operationType.equals("SELECT")) {
            return new GenericModel<>(executeSelectQuery(sqlRequestModel));
        } else {
            return new GenericModel<>(executeUpdateQueryWihDataType(sqlRequestModel, operationType));
        }
    }

    private String executeUpdateQuery(SqlRequestModel sqlRequestModel, String operation) {
        StringBuilder queryBuilder = new StringBuilder(operation);
        List<String> queryParams = new ArrayList<>();
        PemDbObjectEntity pemDbObjectEntity = pemDbObjectRepository.findById(sqlRequestModel.getSeqId()).orElseThrow(() -> internalServerError("provided seqId not configured with any database table."));
        if (operation.equalsIgnoreCase("Delete")) {
            boolean isWhereAdded = false;
            queryBuilder.append(" FROM ").append(pemDbObjectEntity.getTableName());
            if (isNotNull(pemDbObjectEntity.getWhereColumnName1()) && isNotNull(sqlRequestModel.getWhereColumnValue1())) {
                queryBuilder.append(" WHERE ").append(pemDbObjectEntity.getWhereColumnName1())
                        .append(" = ? ");
                queryParams.add(sqlRequestModel.getWhereColumnValue1());
                isWhereAdded = true;
            }
            if (isNotNull(pemDbObjectEntity.getWhereColumnName2()) && isNotNull(sqlRequestModel.getWhereColumnValue2())) {
                queryBuilder.append(isWhereAdded ? " AND " : " WHERE ").append(pemDbObjectEntity.getWhereColumnName2())
                        .append(" = ? ");
                queryParams.add(sqlRequestModel.getWhereColumnValue2());
                isWhereAdded = true;
            }
            if (isNotNull(pemDbObjectEntity.getWhereColumnName3()) && isNotNull(sqlRequestModel.getWhereColumnValue3())) {
                queryBuilder.append(isWhereAdded ? " AND " : " WHERE ").append(pemDbObjectEntity.getWhereColumnName3())
                        .append(" = ? ");
                queryParams.add(sqlRequestModel.getWhereColumnValue3());
                isWhereAdded = true;
            }
            if (isNotNull(pemDbObjectEntity.getWhereColumnName4()) && isNotNull(sqlRequestModel.getWhereColumnValue4())) {
                queryBuilder.append(isWhereAdded ? " AND " : " WHERE ").append(pemDbObjectEntity.getWhereColumnName4())
                        .append(" = ? ");
                queryParams.add(sqlRequestModel.getWhereColumnValue4());
                isWhereAdded = true;
            }
            if (isNotNull(pemDbObjectEntity.getWhereColumnName5()) && isNotNull(sqlRequestModel.getWhereColumnValue5())) {
                queryBuilder.append(isWhereAdded ? " AND " : " WHERE ").append(pemDbObjectEntity.getWhereColumnName5())
                        .append(" = ? ");
                queryParams.add(sqlRequestModel.getWhereColumnValue5());
                isWhereAdded = true;
            }
            if (isNotNull(pemDbObjectEntity.getWhereColumnName6()) && isNotNull(sqlRequestModel.getWhereColumnValue6())) {
                queryBuilder.append(isWhereAdded ? " AND " : " WHERE ").append(pemDbObjectEntity.getWhereColumnName6())
                        .append(" = ? ");
                queryParams.add(sqlRequestModel.getWhereColumnValue6());
                isWhereAdded = true;
            }
            if (isNotNull(pemDbObjectEntity.getWhereColumnName7()) && isNotNull(sqlRequestModel.getWhereColumnValue7())) {
                queryBuilder.append(isWhereAdded ? " AND " : " WHERE ").append(pemDbObjectEntity.getWhereColumnName7())
                        .append(" = ? ");
                queryParams.add(sqlRequestModel.getWhereColumnValue7());
                isWhereAdded = true;
            }
            if (isNotNull(pemDbObjectEntity.getWhereColumnName8()) && isNotNull(sqlRequestModel.getWhereColumnValue8())) {
                queryBuilder.append(isWhereAdded ? " AND " : " WHERE ").append(pemDbObjectEntity.getWhereColumnName8())
                        .append(" = ? ");
                queryParams.add(sqlRequestModel.getWhereColumnValue8());
                isWhereAdded = true;
            }
            if (isNotNull(pemDbObjectEntity.getWhereColumnName9()) && isNotNull(sqlRequestModel.getWhereColumnValue9())) {
                queryBuilder.append(isWhereAdded ? " AND " : " WHERE ").append(pemDbObjectEntity.getWhereColumnName9())
                        .append(" = ? ");
                queryParams.add(sqlRequestModel.getWhereColumnValue9());
                isWhereAdded = true;
            }
            if (isNotNull(pemDbObjectEntity.getWhereColumnName10()) && isNotNull(sqlRequestModel.getWhereColumnValue10())) {
                queryBuilder.append(isWhereAdded ? " AND " : " WHERE ").append(pemDbObjectEntity.getWhereColumnName10())
                        .append(" = ? ");
                queryParams.add(sqlRequestModel.getWhereColumnValue10());
                isWhereAdded = true;
            }
            if (!isWhereAdded) {
                throw GlobalExceptionHandler.internalServerError("You are trying to delete multiple records please provide a value for where condition param");
            }
            if (isNotNull(pemDbObjectEntity.getWhereColumnName2()) && isNotNull(sqlRequestModel.getWhereColumnValue2())) {
                queryBuilder.append(" AND ").append(pemDbObjectEntity.getWhereColumnName2())
                        .append(" = ? ");
                queryParams.add(sqlRequestModel.getWhereColumnValue2());
            }
        } else {
            buildQueryForUpdateSql(queryBuilder, queryParams, sqlRequestModel, operation, pemDbObjectEntity);
        }
        LOGGER.info("Executed Query : {}", queryBuilder);
        try (Connection connection = getDBConnection(); PreparedStatement preparedStatement = connection.prepareStatement(queryBuilder.toString())) {

            IntStream.rangeClosed(1, queryParams.size()).forEach(value -> {
                try {
                    preparedStatement.setString(value, queryParams.get(value - 1));
                } catch (Exception e) {
                    throw internalServerError(e.getMessage());
                }
            });
            if (queryBuilder.toString().endsWith("SET ")) {
                throw internalServerError("Please provide at lease one value to update a table.");
            }
            return preparedStatement.executeUpdate() + " record(s) effected.";
        } catch (SQLException e) {
            LOGGER.error("Error while performing SQL Operation ", e);
            throw internalServerError(e.getMessage());
        }
    }

    private SqlResponseModel executeSelectQuery(SqlRequestModel sqlRequestModel) {
        SqlResponseModel sqlResponseModel = new SqlResponseModel();
        ColumnModel columnModel = new ColumnModel();
        Map<Integer, String> queryParam = new LinkedHashMap<>();
        StringBuilder queryBuilder = new StringBuilder("SELECT ");
        buildQuery(queryBuilder, queryParam, sqlRequestModel);
        LOGGER.info("Executed Query : {}", queryBuilder);
        try (Connection connection = getDBConnection(); PreparedStatement preparedStatement = connection.prepareStatement(queryBuilder.toString())) {

            IntStream.rangeClosed(1, queryParam.size()).forEach(value -> {
                try {
                    preparedStatement.setString(value, sqlRequestModel.isLike() ? addDBContains.apply(queryParam.get(value)) : queryParam.get(value).toLowerCase());
                } catch (Exception e) {
                    throw internalServerError(e.getMessage());
                }
            });

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                AtomicInteger rowNum = new AtomicInteger(1);
                List<SqlRequestModel> rowModels = new ArrayList<>();
                while (resultSet.next()) {
                    SqlRequestModel rowModel = new SqlRequestModel();
                    rowModel.setRowCount(rowNum.get());
                    IntStream.rangeClosed(1, resultSetMetaData.getColumnCount()).forEach(value -> {
                        try {
                            switch (value) {
                                case 1:
                                    rowModel.setColumnValue1(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName1(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 2:
                                    rowModel.setColumnValue2(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName2(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 3:
                                    rowModel.setColumnValue3(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName3(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 4:
                                    rowModel.setColumnValue4(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName4(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 5:
                                    rowModel.setColumnValue5(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName5(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 6:
                                    rowModel.setColumnValue6(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName6(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 7:
                                    rowModel.setColumnValue7(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName7(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 8:
                                    rowModel.setColumnValue8(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName8(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 9:
                                    rowModel.setColumnValue9(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName9(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 10:
                                    rowModel.setColumnValue10(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName10(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 11:
                                    rowModel.setColumnValue11(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName11(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 12:
                                    rowModel.setColumnValue12(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName12(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 13:
                                    rowModel.setColumnValue13(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName13(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 14:
                                    rowModel.setColumnValue14(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName14(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 15:
                                    rowModel.setColumnValue15(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName15(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 16:
                                    rowModel.setColumnValue16(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName16(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 17:
                                    rowModel.setColumnValue17(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName17(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 18:
                                    rowModel.setColumnValue18(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName18(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 19:
                                    rowModel.setColumnValue19(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName19(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 20:
                                    rowModel.setColumnValue20(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName20(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 21:
                                    rowModel.setColumnValue21(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName21(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 22:
                                    rowModel.setColumnValue22(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName22(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 23:
                                    rowModel.setColumnValue23(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName23(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 24:
                                    rowModel.setColumnValue24(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName24(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 25:
                                    rowModel.setColumnValue25(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName25(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 26:
                                    rowModel.setColumnValue26(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName26(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 27:
                                    rowModel.setColumnValue27(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName27(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 28:
                                    rowModel.setColumnValue28(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName28(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 29:
                                    rowModel.setColumnValue29(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName29(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 30:
                                    rowModel.setColumnValue30(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName30(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 31:
                                    rowModel.setColumnValue31(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName31(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 32:
                                    rowModel.setColumnValue32(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName32(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 33:
                                    rowModel.setColumnValue33(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName33(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 34:
                                    rowModel.setColumnValue34(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName34(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 35:
                                    rowModel.setColumnValue35(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName35(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 36:
                                    rowModel.setColumnValue36(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName36(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 37:
                                    rowModel.setColumnValue37(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName37(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 38:
                                    rowModel.setColumnValue38(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName38(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 39:
                                    rowModel.setColumnValue39(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName39(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 40:
                                    rowModel.setColumnValue40(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName40(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 41:
                                    rowModel.setColumnValue41(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName41(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 42:
                                    rowModel.setColumnValue42(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName42(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 43:
                                    rowModel.setColumnValue43(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName43(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 44:
                                    rowModel.setColumnValue44(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName44(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 45:
                                    rowModel.setColumnValue45(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName45(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 46:
                                    rowModel.setColumnValue46(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName46(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 47:
                                    rowModel.setColumnValue47(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName47(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 48:
                                    rowModel.setColumnValue48(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName48(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 49:
                                    rowModel.setColumnValue49(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName49(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 50:
                                    rowModel.setColumnValue50(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName50(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 51:
                                    rowModel.setColumnValue51(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName51(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 52:
                                    rowModel.setColumnValue52(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName52(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 53:
                                    rowModel.setColumnValue53(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName53(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 54:
                                    rowModel.setColumnValue54(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName54(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 55:
                                    rowModel.setColumnValue55(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName55(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 56:
                                    rowModel.setColumnValue56(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName56(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 57:
                                    rowModel.setColumnValue57(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName57(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 58:
                                    rowModel.setColumnValue58(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName58(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 59:
                                    rowModel.setColumnValue59(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName59(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 60:
                                    rowModel.setColumnValue60(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName60(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 61:
                                    rowModel.setColumnValue61(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName61(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 62:
                                    rowModel.setColumnValue62(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName62(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 63:
                                    rowModel.setColumnValue63(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName63(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 64:
                                    rowModel.setColumnValue64(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName64(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 65:
                                    rowModel.setColumnValue65(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName65(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 66:
                                    rowModel.setColumnValue66(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName66(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 67:
                                    rowModel.setColumnValue67(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName67(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 68:
                                    rowModel.setColumnValue68(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName68(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 69:
                                    rowModel.setColumnValue69(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName69(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 70:
                                    rowModel.setColumnValue70(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName70(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 71:
                                    rowModel.setColumnValue71(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName71(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 72:
                                    rowModel.setColumnValue72(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName72(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 73:
                                    rowModel.setColumnValue73(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName73(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 74:
                                    rowModel.setColumnValue74(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName74(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 75:
                                    rowModel.setColumnValue75(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName75(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 76:
                                    rowModel.setColumnValue76(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName76(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 77:
                                    rowModel.setColumnValue77(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName77(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 78:
                                    rowModel.setColumnValue78(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName78(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 79:
                                    rowModel.setColumnValue79(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName79(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 80:
                                    rowModel.setColumnValue80(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName80(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 81:
                                    rowModel.setColumnValue81(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName81(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 82:
                                    rowModel.setColumnValue82(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName82(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 83:
                                    rowModel.setColumnValue83(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName83(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 84:
                                    rowModel.setColumnValue84(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName84(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 85:
                                    rowModel.setColumnValue85(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName85(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 86:
                                    rowModel.setColumnValue86(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName86(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 87:
                                    rowModel.setColumnValue87(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName87(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 88:
                                    rowModel.setColumnValue88(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName88(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 89:
                                    rowModel.setColumnValue89(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName89(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 90:
                                    rowModel.setColumnValue90(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName90(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 91:
                                    rowModel.setColumnValue91(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName91(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 92:
                                    rowModel.setColumnValue92(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName92(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 93:
                                    rowModel.setColumnValue93(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName93(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 94:
                                    rowModel.setColumnValue94(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName94(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 95:
                                    rowModel.setColumnValue95(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName95(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 96:
                                    rowModel.setColumnValue96(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName96(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 97:
                                    rowModel.setColumnValue97(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName97(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 98:
                                    rowModel.setColumnValue98(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName98(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 99:
                                    rowModel.setColumnValue99(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName99(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 100:
                                    rowModel.setColumnValue100(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName100(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 101:
                                    rowModel.setColumnValue101(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName101(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 102:
                                    rowModel.setColumnValue102(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName102(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 103:
                                    rowModel.setColumnValue103(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName103(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 104:
                                    rowModel.setColumnValue104(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName104(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 105:
                                    rowModel.setColumnValue105(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName105(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 106:
                                    rowModel.setColumnValue106(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName106(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 107:
                                    rowModel.setColumnValue107(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName107(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 108:
                                    rowModel.setColumnValue108(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName108(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 109:
                                    rowModel.setColumnValue109(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName109(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 110:
                                    rowModel.setColumnValue110(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName110(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 111:
                                    rowModel.setColumnValue111(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName111(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 112:
                                    rowModel.setColumnValue112(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName112(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 113:
                                    rowModel.setColumnValue113(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName113(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 114:
                                    rowModel.setColumnValue114(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName114(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 115:
                                    rowModel.setColumnValue115(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName115(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 116:
                                    rowModel.setColumnValue116(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName116(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 117:
                                    rowModel.setColumnValue117(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName117(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 118:
                                    rowModel.setColumnValue118(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName118(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 119:
                                    rowModel.setColumnValue119(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName119(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 120:
                                    rowModel.setColumnValue120(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName120(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 121:
                                    rowModel.setColumnValue121(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName121(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 122:
                                    rowModel.setColumnValue122(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName122(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 123:
                                    rowModel.setColumnValue123(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName123(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 124:
                                    rowModel.setColumnValue124(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName124(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 125:
                                    rowModel.setColumnValue125(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName125(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 126:
                                    rowModel.setColumnValue126(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName126(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 127:
                                    rowModel.setColumnValue127(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName127(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 128:
                                    rowModel.setColumnValue128(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName128(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 129:
                                    rowModel.setColumnValue129(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName129(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 130:
                                    rowModel.setColumnValue130(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName130(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 131:
                                    rowModel.setColumnValue131(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName131(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 132:
                                    rowModel.setColumnValue132(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName132(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 133:
                                    rowModel.setColumnValue133(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName133(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 134:
                                    rowModel.setColumnValue134(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName134(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 135:
                                    rowModel.setColumnValue135(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName135(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 136:
                                    rowModel.setColumnValue136(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName136(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 137:
                                    rowModel.setColumnValue137(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName137(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 138:
                                    rowModel.setColumnValue138(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName138(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 139:
                                    rowModel.setColumnValue139(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName139(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 140:
                                    rowModel.setColumnValue140(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName140(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 141:
                                    rowModel.setColumnValue141(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName141(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 142:
                                    rowModel.setColumnValue142(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName142(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 143:
                                    rowModel.setColumnValue143(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName143(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 144:
                                    rowModel.setColumnValue144(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName144(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 145:
                                    rowModel.setColumnValue145(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName145(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 146:
                                    rowModel.setColumnValue146(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName146(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 147:
                                    rowModel.setColumnValue147(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName147(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 148:
                                    rowModel.setColumnValue148(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName148(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 149:
                                    rowModel.setColumnValue149(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName149(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 150:
                                    rowModel.setColumnValue150(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName150(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                default:
                                    //No implementation needed
                            }
                        } catch (Exception e) {
                            throw internalServerError(e.getMessage());
                        }
                    });
                    rowNum.getAndIncrement();
                    rowModels.add(rowModel);
                }
                sqlResponseModel.setColumnModel(columnModel);
                sqlResponseModel.setData(rowModels);
            }
        } catch (SQLException e) {
            LOGGER.error("Error while performing SQL Operation ", e);
            throw internalServerError(e.getMessage());
        }
        return sqlResponseModel;
    }

    private void buildQuery(StringBuilder queryBuilder, Map<Integer, String> queryParamsMap, SqlRequestModel sqlRequestModel) {
        PemDbObjectEntity pemDbObjectEntity = pemDbObjectRepository.findById(sqlRequestModel.getSeqId()).orElseThrow(() -> internalServerError("provided seqId not configured with any database table."));
        if (sqlRequestModel.isDistinct()) {
            if (isNotNull(pemDbObjectEntity.getDistinctColumnName())) {
                queryBuilder.append(" DISTINCT ").append(pemDbObjectEntity.getDistinctColumnName());
            } else {
                throw internalServerError("Distinct Column name not configured in configuration.");
            }
        } else {
            queryBuilder.append(" *");
        }

        queryBuilder.append(" FROM ")
                .append(pemDbObjectEntity.getTableName())
                .append(" ");

        AtomicInteger atomicInteger = new AtomicInteger(0);

        applyFilterForSelectSql(queryBuilder, queryParamsMap, pemDbObjectEntity.getWhereColumnName1(), sqlRequestModel.getWhereColumnValue1(), atomicInteger);
        applyFilterForSelectSql(queryBuilder, queryParamsMap, pemDbObjectEntity.getWhereColumnName2(), sqlRequestModel.getWhereColumnValue2(), atomicInteger);
        applyFilterForSelectSql(queryBuilder, queryParamsMap, pemDbObjectEntity.getWhereColumnName3(), sqlRequestModel.getWhereColumnValue3(), atomicInteger);
        applyFilterForSelectSql(queryBuilder, queryParamsMap, pemDbObjectEntity.getWhereColumnName4(), sqlRequestModel.getWhereColumnValue4(), atomicInteger);
        applyFilterForSelectSql(queryBuilder, queryParamsMap, pemDbObjectEntity.getWhereColumnName5(), sqlRequestModel.getWhereColumnValue5(), atomicInteger);
        applyFilterForSelectSql(queryBuilder, queryParamsMap, pemDbObjectEntity.getWhereColumnName6(), sqlRequestModel.getWhereColumnValue6(), atomicInteger);
        applyFilterForSelectSql(queryBuilder, queryParamsMap, pemDbObjectEntity.getWhereColumnName7(), sqlRequestModel.getWhereColumnValue7(), atomicInteger);
        applyFilterForSelectSql(queryBuilder, queryParamsMap, pemDbObjectEntity.getWhereColumnName8(), sqlRequestModel.getWhereColumnValue8(), atomicInteger);
        applyFilterForSelectSql(queryBuilder, queryParamsMap, pemDbObjectEntity.getWhereColumnName9(), sqlRequestModel.getWhereColumnValue9(), atomicInteger);
        applyFilterForSelectSql(queryBuilder, queryParamsMap, pemDbObjectEntity.getWhereColumnName10(), sqlRequestModel.getWhereColumnValue10(), atomicInteger);

    }

    private void applyFilterForSelectSql(StringBuilder queryBuilder, Map<Integer, String> queryParamsMap, String columnName, String columnValue, AtomicInteger atomicInteger) {

        if (isNotNull(columnName) && isNotNull(columnValue)) {
            if (atomicInteger.getAndIncrement() == 0) {
                queryBuilder.append(" WHERE ");
            } else {
                queryBuilder.append(" AND ");
            }
            queryBuilder.append(" LOWER (").append(columnName.toLowerCase()).append(") ").append(" LIKE ? ");
            queryParamsMap.put(atomicInteger.get(), columnValue);
        }
    }

    private void buildQueryForUpdateSql(StringBuilder stringBuilder, List<String> queryParams, SqlRequestModel sqlRequestModel, String operation, PemDbObjectEntity pemDbObjectEntity) {

        if (operation.equalsIgnoreCase(PCMConstants.SQL_INSERT_CLAUSE)) {
            stringBuilder.append(" INTO ")
                    .append(pemDbObjectEntity.getTableName());
        } else {
            stringBuilder.append(" ")
                    .append(pemDbObjectEntity.getTableName())
                    .append(" ")
                    .append("SET ");
        }

        AtomicInteger atomicInteger = new AtomicInteger(0);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName1(), sqlRequestModel.getColumnValue1(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName2(), sqlRequestModel.getColumnValue2(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName3(), sqlRequestModel.getColumnValue3(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName4(), sqlRequestModel.getColumnValue4(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName5(), sqlRequestModel.getColumnValue5(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName6(), sqlRequestModel.getColumnValue6(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName7(), sqlRequestModel.getColumnValue7(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName8(), sqlRequestModel.getColumnValue8(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName9(), sqlRequestModel.getColumnValue9(), atomicInteger);

        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName10(), sqlRequestModel.getColumnValue10(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName11(), sqlRequestModel.getColumnValue11(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName12(), sqlRequestModel.getColumnValue12(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName13(), sqlRequestModel.getColumnValue13(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName14(), sqlRequestModel.getColumnValue14(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName15(), sqlRequestModel.getColumnValue15(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName16(), sqlRequestModel.getColumnValue16(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName17(), sqlRequestModel.getColumnValue17(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName18(), sqlRequestModel.getColumnValue18(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName19(), sqlRequestModel.getColumnValue19(), atomicInteger);

        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName20(), sqlRequestModel.getColumnValue20(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName21(), sqlRequestModel.getColumnValue21(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName22(), sqlRequestModel.getColumnValue22(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName23(), sqlRequestModel.getColumnValue23(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName24(), sqlRequestModel.getColumnValue24(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName25(), sqlRequestModel.getColumnValue25(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName26(), sqlRequestModel.getColumnValue26(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName27(), sqlRequestModel.getColumnValue27(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName28(), sqlRequestModel.getColumnValue28(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName29(), sqlRequestModel.getColumnValue29(), atomicInteger);

        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName30(), sqlRequestModel.getColumnValue30(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName31(), sqlRequestModel.getColumnValue31(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName32(), sqlRequestModel.getColumnValue32(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName33(), sqlRequestModel.getColumnValue33(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName34(), sqlRequestModel.getColumnValue34(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName35(), sqlRequestModel.getColumnValue35(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName36(), sqlRequestModel.getColumnValue36(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName37(), sqlRequestModel.getColumnValue37(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName38(), sqlRequestModel.getColumnValue38(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName39(), sqlRequestModel.getColumnValue39(), atomicInteger);

        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName40(), sqlRequestModel.getColumnValue40(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName41(), sqlRequestModel.getColumnValue41(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName42(), sqlRequestModel.getColumnValue42(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName43(), sqlRequestModel.getColumnValue43(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName44(), sqlRequestModel.getColumnValue44(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName45(), sqlRequestModel.getColumnValue45(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName46(), sqlRequestModel.getColumnValue46(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName47(), sqlRequestModel.getColumnValue47(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName48(), sqlRequestModel.getColumnValue48(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName49(), sqlRequestModel.getColumnValue49(), atomicInteger);

        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName50(), sqlRequestModel.getColumnValue50(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName51(), sqlRequestModel.getColumnValue51(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName52(), sqlRequestModel.getColumnValue52(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName53(), sqlRequestModel.getColumnValue53(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName54(), sqlRequestModel.getColumnValue54(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName55(), sqlRequestModel.getColumnValue55(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName56(), sqlRequestModel.getColumnValue56(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName57(), sqlRequestModel.getColumnValue57(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName58(), sqlRequestModel.getColumnValue58(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName59(), sqlRequestModel.getColumnValue59(), atomicInteger);

        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName60(), sqlRequestModel.getColumnValue60(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName61(), sqlRequestModel.getColumnValue61(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName62(), sqlRequestModel.getColumnValue62(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName63(), sqlRequestModel.getColumnValue63(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName64(), sqlRequestModel.getColumnValue64(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName65(), sqlRequestModel.getColumnValue65(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName66(), sqlRequestModel.getColumnValue66(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName67(), sqlRequestModel.getColumnValue67(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName68(), sqlRequestModel.getColumnValue68(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName69(), sqlRequestModel.getColumnValue69(), atomicInteger);

        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName70(), sqlRequestModel.getColumnValue70(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName71(), sqlRequestModel.getColumnValue71(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName72(), sqlRequestModel.getColumnValue72(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName73(), sqlRequestModel.getColumnValue73(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName74(), sqlRequestModel.getColumnValue74(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName75(), sqlRequestModel.getColumnValue75(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName76(), sqlRequestModel.getColumnValue76(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName77(), sqlRequestModel.getColumnValue77(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName78(), sqlRequestModel.getColumnValue78(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName79(), sqlRequestModel.getColumnValue79(), atomicInteger);

        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName80(), sqlRequestModel.getColumnValue80(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName81(), sqlRequestModel.getColumnValue81(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName82(), sqlRequestModel.getColumnValue82(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName83(), sqlRequestModel.getColumnValue83(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName84(), sqlRequestModel.getColumnValue84(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName85(), sqlRequestModel.getColumnValue85(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName86(), sqlRequestModel.getColumnValue86(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName87(), sqlRequestModel.getColumnValue87(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName88(), sqlRequestModel.getColumnValue88(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName89(), sqlRequestModel.getColumnValue89(), atomicInteger);

        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName90(), sqlRequestModel.getColumnValue90(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName91(), sqlRequestModel.getColumnValue91(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName92(), sqlRequestModel.getColumnValue92(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName93(), sqlRequestModel.getColumnValue93(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName94(), sqlRequestModel.getColumnValue94(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName95(), sqlRequestModel.getColumnValue95(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName96(), sqlRequestModel.getColumnValue96(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName97(), sqlRequestModel.getColumnValue97(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName98(), sqlRequestModel.getColumnValue98(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName99(), sqlRequestModel.getColumnValue99(), atomicInteger);

        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName100(), sqlRequestModel.getColumnValue100(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName101(), sqlRequestModel.getColumnValue101(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName102(), sqlRequestModel.getColumnValue102(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName103(), sqlRequestModel.getColumnValue103(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName104(), sqlRequestModel.getColumnValue104(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName105(), sqlRequestModel.getColumnValue105(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName106(), sqlRequestModel.getColumnValue106(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName107(), sqlRequestModel.getColumnValue107(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName108(), sqlRequestModel.getColumnValue108(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName109(), sqlRequestModel.getColumnValue109(), atomicInteger);

        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName110(), sqlRequestModel.getColumnValue110(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName111(), sqlRequestModel.getColumnValue111(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName112(), sqlRequestModel.getColumnValue112(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName113(), sqlRequestModel.getColumnValue113(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName114(), sqlRequestModel.getColumnValue114(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName115(), sqlRequestModel.getColumnValue115(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName116(), sqlRequestModel.getColumnValue116(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName117(), sqlRequestModel.getColumnValue117(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName118(), sqlRequestModel.getColumnValue118(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName119(), sqlRequestModel.getColumnValue119(), atomicInteger);

        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName120(), sqlRequestModel.getColumnValue120(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName121(), sqlRequestModel.getColumnValue121(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName122(), sqlRequestModel.getColumnValue122(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName123(), sqlRequestModel.getColumnValue123(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName124(), sqlRequestModel.getColumnValue124(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName125(), sqlRequestModel.getColumnValue125(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName126(), sqlRequestModel.getColumnValue126(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName127(), sqlRequestModel.getColumnValue127(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName128(), sqlRequestModel.getColumnValue128(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName129(), sqlRequestModel.getColumnValue129(), atomicInteger);

        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName130(), sqlRequestModel.getColumnValue130(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName131(), sqlRequestModel.getColumnValue131(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName132(), sqlRequestModel.getColumnValue132(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName133(), sqlRequestModel.getColumnValue133(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName134(), sqlRequestModel.getColumnValue134(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName135(), sqlRequestModel.getColumnValue135(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName136(), sqlRequestModel.getColumnValue136(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName137(), sqlRequestModel.getColumnValue137(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName138(), sqlRequestModel.getColumnValue138(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName139(), sqlRequestModel.getColumnValue139(), atomicInteger);

        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName140(), sqlRequestModel.getColumnValue140(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName141(), sqlRequestModel.getColumnValue141(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName142(), sqlRequestModel.getColumnValue142(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName143(), sqlRequestModel.getColumnValue143(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName144(), sqlRequestModel.getColumnValue144(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName145(), sqlRequestModel.getColumnValue145(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName146(), sqlRequestModel.getColumnValue146(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName147(), sqlRequestModel.getColumnValue147(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName148(), sqlRequestModel.getColumnValue148(), atomicInteger);
        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName149(), sqlRequestModel.getColumnValue149(), atomicInteger);

        applyFilterForUpdateSql(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName150(), sqlRequestModel.getColumnValue150(), atomicInteger);


        if (operation.equalsIgnoreCase(PCMConstants.SQL_INSERT_CLAUSE)) {
            stringBuilder.append(") ").append("VALUES( ");
            AtomicBoolean atomicBoolean = new AtomicBoolean(true);
            IntStream.range(0, atomicInteger.get()).forEach(value -> {
                if (atomicBoolean.get()) {
                    stringBuilder.append("?");
                    atomicBoolean.set(false);
                } else {
                    stringBuilder.append(",")
                            .append(" ?");
                }
            });
            stringBuilder.append(" )");
        } else if (operation.equalsIgnoreCase("Update")) {
            boolean isWhereAdded = false;
            if (isNotNull(pemDbObjectEntity.getWhereColumnName1()) && isNotNull(sqlRequestModel.getWhereColumnValue1())) {
                stringBuilder.append(" WHERE ").append(pemDbObjectEntity.getWhereColumnName1())
                        .append(" = ? ");
                queryParams.add(sqlRequestModel.getWhereColumnValue1());
                atomicInteger.getAndIncrement();
                isWhereAdded = true;
            }
            if (isNotNull(pemDbObjectEntity.getWhereColumnName2()) && isNotNull(sqlRequestModel.getWhereColumnValue2())) {
                stringBuilder.append(isWhereAdded ? " AND " : " WHERE ").append(pemDbObjectEntity.getWhereColumnName2())
                        .append(" = ? ");
                queryParams.add(sqlRequestModel.getWhereColumnValue2());
                atomicInteger.getAndIncrement();
                isWhereAdded = true;
            }
            if (isNotNull(pemDbObjectEntity.getWhereColumnName3()) && isNotNull(sqlRequestModel.getWhereColumnValue3())) {
                stringBuilder.append(isWhereAdded ? " AND " : " WHERE ").append(pemDbObjectEntity.getWhereColumnName3())
                        .append(" = ? ");
                queryParams.add(sqlRequestModel.getWhereColumnValue3());
                atomicInteger.getAndIncrement();
                isWhereAdded = true;
            }
            if (isNotNull(pemDbObjectEntity.getWhereColumnName4()) && isNotNull(sqlRequestModel.getWhereColumnValue4())) {
                stringBuilder.append(isWhereAdded ? " AND " : " WHERE ").append(pemDbObjectEntity.getWhereColumnName4())
                        .append(" = ? ");
                queryParams.add(sqlRequestModel.getWhereColumnValue4());
                atomicInteger.getAndIncrement();
                isWhereAdded = true;
            }
            if (isNotNull(pemDbObjectEntity.getWhereColumnName5()) && isNotNull(sqlRequestModel.getWhereColumnValue5())) {
                stringBuilder.append(isWhereAdded ? " AND " : " WHERE ").append(pemDbObjectEntity.getWhereColumnName5())
                        .append(" = ? ");
                queryParams.add(sqlRequestModel.getWhereColumnValue5());
                atomicInteger.getAndIncrement();
                isWhereAdded = true;
            }
            if (isNotNull(pemDbObjectEntity.getWhereColumnName6()) && isNotNull(sqlRequestModel.getWhereColumnValue6())) {
                stringBuilder.append(isWhereAdded ? " AND " : " WHERE ").append(pemDbObjectEntity.getWhereColumnName6())
                        .append(" = ? ");
                queryParams.add(sqlRequestModel.getWhereColumnValue6());
                atomicInteger.getAndIncrement();
                isWhereAdded = true;
            }
            if (isNotNull(pemDbObjectEntity.getWhereColumnName7()) && isNotNull(sqlRequestModel.getWhereColumnValue7())) {
                stringBuilder.append(isWhereAdded ? " AND " : " WHERE ").append(pemDbObjectEntity.getWhereColumnName7())
                        .append(" = ? ");
                queryParams.add(sqlRequestModel.getWhereColumnValue7());
                atomicInteger.getAndIncrement();
                isWhereAdded = true;
            }
            if (isNotNull(pemDbObjectEntity.getWhereColumnName9()) && isNotNull(sqlRequestModel.getWhereColumnValue9())) {
                stringBuilder.append(isWhereAdded ? " AND " : " WHERE ").append(pemDbObjectEntity.getWhereColumnName9())
                        .append(" = ? ");
                queryParams.add(sqlRequestModel.getWhereColumnValue9());
                atomicInteger.getAndIncrement();
                isWhereAdded = true;
            }
            if (isNotNull(pemDbObjectEntity.getWhereColumnName10()) && isNotNull(sqlRequestModel.getWhereColumnValue10())) {
                stringBuilder.append(isWhereAdded ? " AND " : " WHERE ").append(pemDbObjectEntity.getWhereColumnName10())
                        .append(" = ? ");
                queryParams.add(sqlRequestModel.getWhereColumnValue10());
                atomicInteger.getAndIncrement();
                isWhereAdded = true;
            }
            if (!isWhereAdded) {
                throw GlobalExceptionHandler.internalServerError("You are updating multiple records please provide a value for where condition param");
            }
        }
    }

    private String executeUpdateQueryWihDataType(SqlRequestModel sqlRequestModel, String operation) {
        StringBuilder queryBuilder = new StringBuilder(operation);
        List<Object> queryParams = new ArrayList<>();
        PemDbObjectEntity pemDbObjectEntity = pemDbObjectRepository.findById(sqlRequestModel.getSeqId()).orElseThrow(() -> internalServerError("provided seqId not configured with any database table."));
        PemDbObjectDataTypeEntity pemDbObjectDataTypeEntity = pemDbObjectDataTypeRepository.findById(sqlRequestModel.getSeqId()).orElseThrow(() -> internalServerError("Given Table Columns Data Types Not Configured"));
        if (operation.equalsIgnoreCase("Delete")) {
            boolean isWhereAdded = false;
            queryBuilder.append(" FROM ").append(pemDbObjectEntity.getTableName());
            if (isNotNull(pemDbObjectEntity.getWhereColumnName1()) && isNotNull(sqlRequestModel.getWhereColumnValue1())) {
                queryBuilder.append(" WHERE ").append(pemDbObjectEntity.getWhereColumnName1())
                        .append(" = ? ");
                queryParams.add(sqlRequestModel.getWhereColumnValue1());
                isWhereAdded = true;
            }
            if (isNotNull(pemDbObjectEntity.getWhereColumnName2()) && isNotNull(sqlRequestModel.getWhereColumnValue2())) {
                queryBuilder.append(isWhereAdded ? " AND " : " WHERE ").append(pemDbObjectEntity.getWhereColumnName2())
                        .append(" = ? ");
                queryParams.add(sqlRequestModel.getWhereColumnValue2());
                isWhereAdded = true;
            }
            if (isNotNull(pemDbObjectEntity.getWhereColumnName3()) && isNotNull(sqlRequestModel.getWhereColumnValue3())) {
                queryBuilder.append(isWhereAdded ? " AND " : " WHERE ").append(pemDbObjectEntity.getWhereColumnName3())
                        .append(" = ? ");
                queryParams.add(sqlRequestModel.getWhereColumnValue3());
                isWhereAdded = true;
            }
            if (isNotNull(pemDbObjectEntity.getWhereColumnName4()) && isNotNull(sqlRequestModel.getWhereColumnValue4())) {
                queryBuilder.append(isWhereAdded ? " AND " : " WHERE ").append(pemDbObjectEntity.getWhereColumnName4())
                        .append(" = ? ");
                queryParams.add(sqlRequestModel.getWhereColumnValue4());
                isWhereAdded = true;
            }
            if (isNotNull(pemDbObjectEntity.getWhereColumnName5()) && isNotNull(sqlRequestModel.getWhereColumnValue5())) {
                queryBuilder.append(isWhereAdded ? " AND " : " WHERE ").append(pemDbObjectEntity.getWhereColumnName5())
                        .append(" = ? ");
                queryParams.add(sqlRequestModel.getWhereColumnValue5());
                isWhereAdded = true;
            }
            if (isNotNull(pemDbObjectEntity.getWhereColumnName6()) && isNotNull(sqlRequestModel.getWhereColumnValue6())) {
                queryBuilder.append(isWhereAdded ? " AND " : " WHERE ").append(pemDbObjectEntity.getWhereColumnName6())
                        .append(" = ? ");
                queryParams.add(sqlRequestModel.getWhereColumnValue6());
                isWhereAdded = true;
            }
            if (isNotNull(pemDbObjectEntity.getWhereColumnName7()) && isNotNull(sqlRequestModel.getWhereColumnValue7())) {
                queryBuilder.append(isWhereAdded ? " AND " : " WHERE ").append(pemDbObjectEntity.getWhereColumnName7())
                        .append(" = ? ");
                queryParams.add(sqlRequestModel.getWhereColumnValue7());
                isWhereAdded = true;
            }
            if (isNotNull(pemDbObjectEntity.getWhereColumnName8()) && isNotNull(sqlRequestModel.getWhereColumnValue8())) {
                queryBuilder.append(isWhereAdded ? " AND " : " WHERE ").append(pemDbObjectEntity.getWhereColumnName8())
                        .append(" = ? ");
                queryParams.add(sqlRequestModel.getWhereColumnValue8());
                isWhereAdded = true;
            }
            if (isNotNull(pemDbObjectEntity.getWhereColumnName9()) && isNotNull(sqlRequestModel.getWhereColumnValue9())) {
                queryBuilder.append(isWhereAdded ? " AND " : " WHERE ").append(pemDbObjectEntity.getWhereColumnName9())
                        .append(" = ? ");
                queryParams.add(sqlRequestModel.getWhereColumnValue9());
                isWhereAdded = true;
            }
            if (isNotNull(pemDbObjectEntity.getWhereColumnName10()) && isNotNull(sqlRequestModel.getWhereColumnValue10())) {
                queryBuilder.append(isWhereAdded ? " AND " : " WHERE ").append(pemDbObjectEntity.getWhereColumnName10())
                        .append(" = ? ");
                queryParams.add(sqlRequestModel.getWhereColumnValue10());
                isWhereAdded = true;
            }
            if (!isWhereAdded) {
                throw GlobalExceptionHandler.internalServerError("You are trying to delete multiple records please provide a value for where condition param");
            }
            if (isNotNull(pemDbObjectEntity.getWhereColumnName2()) && isNotNull(sqlRequestModel.getWhereColumnValue2())) {
                queryBuilder.append(" AND ").append(pemDbObjectEntity.getWhereColumnName2())
                        .append(" = ? ");
                queryParams.add(sqlRequestModel.getWhereColumnValue2());
            }
        } else {
            buildQueryForUpdateSqlWithDataType(queryBuilder, queryParams, sqlRequestModel, operation, pemDbObjectEntity, pemDbObjectDataTypeEntity);
        }
        LOGGER.info("Executed Query : {}", queryBuilder);
        try (Connection connection = getDBConnection(); PreparedStatement preparedStatement = connection.prepareStatement(queryBuilder.toString())) {

            IntStream.rangeClosed(1, queryParams.size()).forEach(value -> {
                try {
                    preparedStatement.setString(value, String.valueOf(queryParams.get(value - 1)));
                } catch (Exception e) {
                    throw internalServerError(e.getMessage());
                }
            });
            if (queryBuilder.toString().endsWith("SET ")) {
                throw internalServerError("Please provide at lease one value to update a table.");
            }
            return preparedStatement.executeUpdate() + " record(s) effected.";
        } catch (SQLException e) {
            LOGGER.error("Error while performing SQL Operation ", e);
            throw internalServerError(e.getMessage());
        }
    }


    private void buildQueryForUpdateSqlWithDataType(StringBuilder stringBuilder, List<Object> queryParams, SqlRequestModel sqlRequestModel, String operation, PemDbObjectEntity pemDbObjectEntity, PemDbObjectDataTypeEntity pemDbObjectDataTypeEntity) {
        if (operation.equalsIgnoreCase(PCMConstants.SQL_INSERT_CLAUSE)) {
            stringBuilder.append(" INTO ")
                    .append(pemDbObjectEntity.getTableName());
        } else {
            stringBuilder.append(" ")
                    .append(pemDbObjectEntity.getTableName())
                    .append(" ")
                    .append("SET ");
        }

        AtomicInteger atomicInteger = new AtomicInteger(0);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName1(), sqlRequestModel.getColumnValue1(), pemDbObjectDataTypeEntity.getColumnName1Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName2(), sqlRequestModel.getColumnValue2(), pemDbObjectDataTypeEntity.getColumnName2Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName3(), sqlRequestModel.getColumnValue3(), pemDbObjectDataTypeEntity.getColumnName3Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName4(), sqlRequestModel.getColumnValue4(), pemDbObjectDataTypeEntity.getColumnName4Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName5(), sqlRequestModel.getColumnValue5(), pemDbObjectDataTypeEntity.getColumnName5Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName6(), sqlRequestModel.getColumnValue6(), pemDbObjectDataTypeEntity.getColumnName6Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName7(), sqlRequestModel.getColumnValue7(), pemDbObjectDataTypeEntity.getColumnName7Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName8(), sqlRequestModel.getColumnValue8(), pemDbObjectDataTypeEntity.getColumnName8Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName9(), sqlRequestModel.getColumnValue9(), pemDbObjectDataTypeEntity.getColumnName9Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName10(), sqlRequestModel.getColumnValue10(), pemDbObjectDataTypeEntity.getColumnName10Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName11(), sqlRequestModel.getColumnValue11(), pemDbObjectDataTypeEntity.getColumnName11Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName12(), sqlRequestModel.getColumnValue12(), pemDbObjectDataTypeEntity.getColumnName12Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName13(), sqlRequestModel.getColumnValue13(), pemDbObjectDataTypeEntity.getColumnName13Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName14(), sqlRequestModel.getColumnValue14(), pemDbObjectDataTypeEntity.getColumnName14Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName15(), sqlRequestModel.getColumnValue15(), pemDbObjectDataTypeEntity.getColumnName15Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName16(), sqlRequestModel.getColumnValue16(), pemDbObjectDataTypeEntity.getColumnName16Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName17(), sqlRequestModel.getColumnValue17(), pemDbObjectDataTypeEntity.getColumnName17Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName18(), sqlRequestModel.getColumnValue18(), pemDbObjectDataTypeEntity.getColumnName18Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName19(), sqlRequestModel.getColumnValue19(), pemDbObjectDataTypeEntity.getColumnName19Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName20(), sqlRequestModel.getColumnValue20(), pemDbObjectDataTypeEntity.getColumnName20Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName21(), sqlRequestModel.getColumnValue21(), pemDbObjectDataTypeEntity.getColumnName21Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName22(), sqlRequestModel.getColumnValue22(), pemDbObjectDataTypeEntity.getColumnName22Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName23(), sqlRequestModel.getColumnValue23(), pemDbObjectDataTypeEntity.getColumnName23Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName24(), sqlRequestModel.getColumnValue24(), pemDbObjectDataTypeEntity.getColumnName24Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName25(), sqlRequestModel.getColumnValue25(), pemDbObjectDataTypeEntity.getColumnName25Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName26(), sqlRequestModel.getColumnValue26(), pemDbObjectDataTypeEntity.getColumnName26Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName27(), sqlRequestModel.getColumnValue27(), pemDbObjectDataTypeEntity.getColumnName27Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName28(), sqlRequestModel.getColumnValue28(), pemDbObjectDataTypeEntity.getColumnName28Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName29(), sqlRequestModel.getColumnValue29(), pemDbObjectDataTypeEntity.getColumnName29Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName30(), sqlRequestModel.getColumnValue30(), pemDbObjectDataTypeEntity.getColumnName30Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName31(), sqlRequestModel.getColumnValue31(), pemDbObjectDataTypeEntity.getColumnName31Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName32(), sqlRequestModel.getColumnValue32(), pemDbObjectDataTypeEntity.getColumnName32Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName33(), sqlRequestModel.getColumnValue33(), pemDbObjectDataTypeEntity.getColumnName33Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName34(), sqlRequestModel.getColumnValue34(), pemDbObjectDataTypeEntity.getColumnName34Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName35(), sqlRequestModel.getColumnValue35(), pemDbObjectDataTypeEntity.getColumnName35Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName36(), sqlRequestModel.getColumnValue36(), pemDbObjectDataTypeEntity.getColumnName36Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName37(), sqlRequestModel.getColumnValue37(), pemDbObjectDataTypeEntity.getColumnName37Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName38(), sqlRequestModel.getColumnValue38(), pemDbObjectDataTypeEntity.getColumnName38Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName39(), sqlRequestModel.getColumnValue39(), pemDbObjectDataTypeEntity.getColumnName39Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName40(), sqlRequestModel.getColumnValue40(), pemDbObjectDataTypeEntity.getColumnName40Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName41(), sqlRequestModel.getColumnValue41(), pemDbObjectDataTypeEntity.getColumnName41Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName42(), sqlRequestModel.getColumnValue42(), pemDbObjectDataTypeEntity.getColumnName42Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName43(), sqlRequestModel.getColumnValue43(), pemDbObjectDataTypeEntity.getColumnName43Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName44(), sqlRequestModel.getColumnValue44(), pemDbObjectDataTypeEntity.getColumnName44Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName45(), sqlRequestModel.getColumnValue45(), pemDbObjectDataTypeEntity.getColumnName45Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName46(), sqlRequestModel.getColumnValue46(), pemDbObjectDataTypeEntity.getColumnName46Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName47(), sqlRequestModel.getColumnValue47(), pemDbObjectDataTypeEntity.getColumnName47Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName48(), sqlRequestModel.getColumnValue48(), pemDbObjectDataTypeEntity.getColumnName48Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName49(), sqlRequestModel.getColumnValue49(), pemDbObjectDataTypeEntity.getColumnName49Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName50(), sqlRequestModel.getColumnValue50(), pemDbObjectDataTypeEntity.getColumnName50Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName51(), sqlRequestModel.getColumnValue51(), pemDbObjectDataTypeEntity.getColumnName51Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName52(), sqlRequestModel.getColumnValue52(), pemDbObjectDataTypeEntity.getColumnName52Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName53(), sqlRequestModel.getColumnValue53(), pemDbObjectDataTypeEntity.getColumnName53Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName54(), sqlRequestModel.getColumnValue54(), pemDbObjectDataTypeEntity.getColumnName54Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName55(), sqlRequestModel.getColumnValue55(), pemDbObjectDataTypeEntity.getColumnName55Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName56(), sqlRequestModel.getColumnValue56(), pemDbObjectDataTypeEntity.getColumnName56Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName57(), sqlRequestModel.getColumnValue57(), pemDbObjectDataTypeEntity.getColumnName57Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName58(), sqlRequestModel.getColumnValue58(), pemDbObjectDataTypeEntity.getColumnName58Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName59(), sqlRequestModel.getColumnValue59(), pemDbObjectDataTypeEntity.getColumnName59Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName60(), sqlRequestModel.getColumnValue60(), pemDbObjectDataTypeEntity.getColumnName60Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName61(), sqlRequestModel.getColumnValue61(), pemDbObjectDataTypeEntity.getColumnName61Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName62(), sqlRequestModel.getColumnValue62(), pemDbObjectDataTypeEntity.getColumnName62Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName63(), sqlRequestModel.getColumnValue63(), pemDbObjectDataTypeEntity.getColumnName63Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName64(), sqlRequestModel.getColumnValue64(), pemDbObjectDataTypeEntity.getColumnName64Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName65(), sqlRequestModel.getColumnValue65(), pemDbObjectDataTypeEntity.getColumnName65Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName66(), sqlRequestModel.getColumnValue66(), pemDbObjectDataTypeEntity.getColumnName66Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName67(), sqlRequestModel.getColumnValue67(), pemDbObjectDataTypeEntity.getColumnName67Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName68(), sqlRequestModel.getColumnValue68(), pemDbObjectDataTypeEntity.getColumnName68Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName69(), sqlRequestModel.getColumnValue69(), pemDbObjectDataTypeEntity.getColumnName69Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName70(), sqlRequestModel.getColumnValue70(), pemDbObjectDataTypeEntity.getColumnName70Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName71(), sqlRequestModel.getColumnValue71(), pemDbObjectDataTypeEntity.getColumnName71Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName72(), sqlRequestModel.getColumnValue72(), pemDbObjectDataTypeEntity.getColumnName72Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName73(), sqlRequestModel.getColumnValue73(), pemDbObjectDataTypeEntity.getColumnName73Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName74(), sqlRequestModel.getColumnValue74(), pemDbObjectDataTypeEntity.getColumnName74Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName75(), sqlRequestModel.getColumnValue75(), pemDbObjectDataTypeEntity.getColumnName75Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName76(), sqlRequestModel.getColumnValue76(), pemDbObjectDataTypeEntity.getColumnName76Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName77(), sqlRequestModel.getColumnValue77(), pemDbObjectDataTypeEntity.getColumnName77Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName78(), sqlRequestModel.getColumnValue78(), pemDbObjectDataTypeEntity.getColumnName78Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName79(), sqlRequestModel.getColumnValue79(), pemDbObjectDataTypeEntity.getColumnName79Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName80(), sqlRequestModel.getColumnValue80(), pemDbObjectDataTypeEntity.getColumnName80Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName81(), sqlRequestModel.getColumnValue81(), pemDbObjectDataTypeEntity.getColumnName81Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName82(), sqlRequestModel.getColumnValue82(), pemDbObjectDataTypeEntity.getColumnName82Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName83(), sqlRequestModel.getColumnValue83(), pemDbObjectDataTypeEntity.getColumnName83Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName84(), sqlRequestModel.getColumnValue84(), pemDbObjectDataTypeEntity.getColumnName84Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName85(), sqlRequestModel.getColumnValue85(), pemDbObjectDataTypeEntity.getColumnName85Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName86(), sqlRequestModel.getColumnValue86(), pemDbObjectDataTypeEntity.getColumnName86Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName87(), sqlRequestModel.getColumnValue87(), pemDbObjectDataTypeEntity.getColumnName87Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName88(), sqlRequestModel.getColumnValue88(), pemDbObjectDataTypeEntity.getColumnName88Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName89(), sqlRequestModel.getColumnValue89(), pemDbObjectDataTypeEntity.getColumnName89Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName90(), sqlRequestModel.getColumnValue90(), pemDbObjectDataTypeEntity.getColumnName90Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName91(), sqlRequestModel.getColumnValue91(), pemDbObjectDataTypeEntity.getColumnName91Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName92(), sqlRequestModel.getColumnValue92(), pemDbObjectDataTypeEntity.getColumnName92Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName93(), sqlRequestModel.getColumnValue93(), pemDbObjectDataTypeEntity.getColumnName93Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName94(), sqlRequestModel.getColumnValue94(), pemDbObjectDataTypeEntity.getColumnName94Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName95(), sqlRequestModel.getColumnValue95(), pemDbObjectDataTypeEntity.getColumnName95Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName96(), sqlRequestModel.getColumnValue96(), pemDbObjectDataTypeEntity.getColumnName96Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName97(), sqlRequestModel.getColumnValue97(), pemDbObjectDataTypeEntity.getColumnName97Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName98(), sqlRequestModel.getColumnValue98(), pemDbObjectDataTypeEntity.getColumnName98Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName99(), sqlRequestModel.getColumnValue99(), pemDbObjectDataTypeEntity.getColumnName99Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName100(), sqlRequestModel.getColumnValue100(), pemDbObjectDataTypeEntity.getColumnName100Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName101(), sqlRequestModel.getColumnValue101(), pemDbObjectDataTypeEntity.getColumnName101Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName102(), sqlRequestModel.getColumnValue102(), pemDbObjectDataTypeEntity.getColumnName102Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName103(), sqlRequestModel.getColumnValue103(), pemDbObjectDataTypeEntity.getColumnName103Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName104(), sqlRequestModel.getColumnValue104(), pemDbObjectDataTypeEntity.getColumnName104Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName105(), sqlRequestModel.getColumnValue105(), pemDbObjectDataTypeEntity.getColumnName105Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName106(), sqlRequestModel.getColumnValue106(), pemDbObjectDataTypeEntity.getColumnName106Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName107(), sqlRequestModel.getColumnValue107(), pemDbObjectDataTypeEntity.getColumnName107Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName108(), sqlRequestModel.getColumnValue108(), pemDbObjectDataTypeEntity.getColumnName108Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName109(), sqlRequestModel.getColumnValue109(), pemDbObjectDataTypeEntity.getColumnName109Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName110(), sqlRequestModel.getColumnValue110(), pemDbObjectDataTypeEntity.getColumnName110Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName111(), sqlRequestModel.getColumnValue111(), pemDbObjectDataTypeEntity.getColumnName111Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName112(), sqlRequestModel.getColumnValue112(), pemDbObjectDataTypeEntity.getColumnName112Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName113(), sqlRequestModel.getColumnValue113(), pemDbObjectDataTypeEntity.getColumnName113Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName114(), sqlRequestModel.getColumnValue114(), pemDbObjectDataTypeEntity.getColumnName114Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName115(), sqlRequestModel.getColumnValue115(), pemDbObjectDataTypeEntity.getColumnName115Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName116(), sqlRequestModel.getColumnValue116(), pemDbObjectDataTypeEntity.getColumnName116Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName117(), sqlRequestModel.getColumnValue117(), pemDbObjectDataTypeEntity.getColumnName117Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName118(), sqlRequestModel.getColumnValue118(), pemDbObjectDataTypeEntity.getColumnName118Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName119(), sqlRequestModel.getColumnValue119(), pemDbObjectDataTypeEntity.getColumnName119Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName120(), sqlRequestModel.getColumnValue120(), pemDbObjectDataTypeEntity.getColumnName120Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName121(), sqlRequestModel.getColumnValue121(), pemDbObjectDataTypeEntity.getColumnName121Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName122(), sqlRequestModel.getColumnValue122(), pemDbObjectDataTypeEntity.getColumnName122Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName123(), sqlRequestModel.getColumnValue123(), pemDbObjectDataTypeEntity.getColumnName123Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName124(), sqlRequestModel.getColumnValue124(), pemDbObjectDataTypeEntity.getColumnName124Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName125(), sqlRequestModel.getColumnValue125(), pemDbObjectDataTypeEntity.getColumnName125Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName126(), sqlRequestModel.getColumnValue126(), pemDbObjectDataTypeEntity.getColumnName126Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName127(), sqlRequestModel.getColumnValue127(), pemDbObjectDataTypeEntity.getColumnName127Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName128(), sqlRequestModel.getColumnValue128(), pemDbObjectDataTypeEntity.getColumnName128Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName129(), sqlRequestModel.getColumnValue129(), pemDbObjectDataTypeEntity.getColumnName129Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName130(), sqlRequestModel.getColumnValue130(), pemDbObjectDataTypeEntity.getColumnName130Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName131(), sqlRequestModel.getColumnValue131(), pemDbObjectDataTypeEntity.getColumnName131Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName132(), sqlRequestModel.getColumnValue132(), pemDbObjectDataTypeEntity.getColumnName132Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName133(), sqlRequestModel.getColumnValue133(), pemDbObjectDataTypeEntity.getColumnName133Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName134(), sqlRequestModel.getColumnValue134(), pemDbObjectDataTypeEntity.getColumnName134Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName135(), sqlRequestModel.getColumnValue135(), pemDbObjectDataTypeEntity.getColumnName135Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName136(), sqlRequestModel.getColumnValue136(), pemDbObjectDataTypeEntity.getColumnName136Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName137(), sqlRequestModel.getColumnValue137(), pemDbObjectDataTypeEntity.getColumnName137Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName138(), sqlRequestModel.getColumnValue138(), pemDbObjectDataTypeEntity.getColumnName138Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName139(), sqlRequestModel.getColumnValue139(), pemDbObjectDataTypeEntity.getColumnName139Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName140(), sqlRequestModel.getColumnValue140(), pemDbObjectDataTypeEntity.getColumnName140Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName141(), sqlRequestModel.getColumnValue141(), pemDbObjectDataTypeEntity.getColumnName141Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName142(), sqlRequestModel.getColumnValue142(), pemDbObjectDataTypeEntity.getColumnName142Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName143(), sqlRequestModel.getColumnValue143(), pemDbObjectDataTypeEntity.getColumnName143Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName144(), sqlRequestModel.getColumnValue144(), pemDbObjectDataTypeEntity.getColumnName144Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName145(), sqlRequestModel.getColumnValue145(), pemDbObjectDataTypeEntity.getColumnName145Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName146(), sqlRequestModel.getColumnValue146(), pemDbObjectDataTypeEntity.getColumnName146Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName147(), sqlRequestModel.getColumnValue147(), pemDbObjectDataTypeEntity.getColumnName147Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName148(), sqlRequestModel.getColumnValue148(), pemDbObjectDataTypeEntity.getColumnName148Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName149(), sqlRequestModel.getColumnValue149(), pemDbObjectDataTypeEntity.getColumnName149Datatype(), dbType, atomicInteger);
        applyFilterForUpdateSqlWithDataType(operation, stringBuilder, queryParams, pemDbObjectEntity.getColumnName150(), sqlRequestModel.getColumnValue150(), pemDbObjectDataTypeEntity.getColumnName150Datatype(), dbType, atomicInteger);


        if (operation.equalsIgnoreCase(PCMConstants.SQL_INSERT_CLAUSE)) {
            stringBuilder.append(") ").append("VALUES( ");
            AtomicBoolean atomicBoolean = new AtomicBoolean(true);
            IntStream.range(0, atomicInteger.get()).forEach(value -> {
                if (atomicBoolean.get()) {
                    stringBuilder.append("?");
                    atomicBoolean.set(false);
                } else {
                    stringBuilder.append(",")
                            .append(" ?");
                }
            });
            stringBuilder.append(" )");
        } else if (operation.equalsIgnoreCase("Update")) {
            boolean isWhereAdded = false;
            if (isNotNull(pemDbObjectEntity.getWhereColumnName1()) && isNotNull(sqlRequestModel.getWhereColumnValue1()) && isNotNull(pemDbObjectDataTypeEntity.getWhereColumnName1_datatype())) {
                stringBuilder.append(" WHERE ").append(pemDbObjectEntity.getWhereColumnName1())
                        .append(" = ? ");
                applyQueryParams(pemDbObjectDataTypeEntity.getWhereColumnName1_datatype(), sqlRequestModel.getWhereColumnValue1(), dbType, queryParams);
                atomicInteger.getAndIncrement();
                isWhereAdded = true;
            }
            if (isNotNull(pemDbObjectEntity.getWhereColumnName2()) && isNotNull(sqlRequestModel.getWhereColumnValue2()) && isNotNull(pemDbObjectDataTypeEntity.getWhereColumnName2_datatype())) {
                stringBuilder.append(isWhereAdded ? " AND " : " WHERE ").append(pemDbObjectEntity.getWhereColumnName2())
                        .append(" = ? ");
                applyQueryParams(pemDbObjectDataTypeEntity.getWhereColumnName2_datatype(), sqlRequestModel.getWhereColumnValue2(), dbType, queryParams);
                atomicInteger.getAndIncrement();
                isWhereAdded = true;
            }
            if (isNotNull(pemDbObjectEntity.getWhereColumnName3()) && isNotNull(sqlRequestModel.getWhereColumnValue3()) && isNotNull(pemDbObjectDataTypeEntity.getWhereColumnName3_datatype())) {
                stringBuilder.append(isWhereAdded ? " AND " : " WHERE ").append(pemDbObjectEntity.getWhereColumnName3())
                        .append(" = ? ");
                applyQueryParams(pemDbObjectDataTypeEntity.getWhereColumnName3_datatype(), sqlRequestModel.getWhereColumnValue3(), dbType, queryParams);
                atomicInteger.getAndIncrement();
                isWhereAdded = true;
            }
            if (isNotNull(pemDbObjectEntity.getWhereColumnName4()) && isNotNull(sqlRequestModel.getWhereColumnValue4()) && isNotNull(pemDbObjectDataTypeEntity.getWhereColumnName4_datatype())) {
                stringBuilder.append(isWhereAdded ? " AND " : " WHERE ").append(pemDbObjectEntity.getWhereColumnName4())
                        .append(" = ? ");
                applyQueryParams(pemDbObjectDataTypeEntity.getWhereColumnName4_datatype(), sqlRequestModel.getWhereColumnValue4(), dbType, queryParams);
                atomicInteger.getAndIncrement();
                isWhereAdded = true;
            }
            if (isNotNull(pemDbObjectEntity.getWhereColumnName5()) && isNotNull(sqlRequestModel.getWhereColumnValue5()) && isNotNull(pemDbObjectDataTypeEntity.getWhereColumnName5_datatype())) {
                stringBuilder.append(isWhereAdded ? " AND " : " WHERE ").append(pemDbObjectEntity.getWhereColumnName5())
                        .append(" = ? ");
                applyQueryParams(pemDbObjectDataTypeEntity.getWhereColumnName5_datatype(), sqlRequestModel.getWhereColumnValue5(), dbType, queryParams);
                atomicInteger.getAndIncrement();
                isWhereAdded = true;
            }
            if (isNotNull(pemDbObjectEntity.getWhereColumnName6()) && isNotNull(sqlRequestModel.getWhereColumnValue6()) && isNotNull(pemDbObjectDataTypeEntity.getWhereColumnName6_datatype())) {
                stringBuilder.append(isWhereAdded ? " AND " : " WHERE ").append(pemDbObjectEntity.getWhereColumnName6())
                        .append(" = ? ");
                applyQueryParams(pemDbObjectDataTypeEntity.getWhereColumnName6_datatype(), sqlRequestModel.getWhereColumnValue6(), dbType, queryParams);
                atomicInteger.getAndIncrement();
                isWhereAdded = true;
            }
            if (isNotNull(pemDbObjectEntity.getWhereColumnName7()) && isNotNull(sqlRequestModel.getWhereColumnValue7()) && isNotNull(pemDbObjectDataTypeEntity.getWhereColumnName7_datatype())) {
                stringBuilder.append(isWhereAdded ? " AND " : " WHERE ").append(pemDbObjectEntity.getWhereColumnName7())
                        .append(" = ? ");
                applyQueryParams(pemDbObjectDataTypeEntity.getWhereColumnName7_datatype(), sqlRequestModel.getWhereColumnValue7(), dbType, queryParams);
                atomicInteger.getAndIncrement();
                isWhereAdded = true;
            }
            if (isNotNull(pemDbObjectEntity.getWhereColumnName8()) && isNotNull(sqlRequestModel.getWhereColumnValue8()) && isNotNull(pemDbObjectDataTypeEntity.getWhereColumnName8_datatype())) {
                stringBuilder.append(isWhereAdded ? " AND " : " WHERE ").append(pemDbObjectEntity.getWhereColumnName7())
                        .append(" = ? ");
                applyQueryParams(pemDbObjectDataTypeEntity.getWhereColumnName8_datatype(), sqlRequestModel.getWhereColumnValue8(), dbType, queryParams);
                atomicInteger.getAndIncrement();
                isWhereAdded = true;
            }
            if (isNotNull(pemDbObjectEntity.getWhereColumnName9()) && isNotNull(sqlRequestModel.getWhereColumnValue9()) && isNotNull(pemDbObjectDataTypeEntity.getWhereColumnName9_datatype())) {
                stringBuilder.append(isWhereAdded ? " AND " : " WHERE ").append(pemDbObjectEntity.getWhereColumnName9())
                        .append(" = ? ");
                applyQueryParams(pemDbObjectDataTypeEntity.getWhereColumnName9_datatype(), sqlRequestModel.getWhereColumnValue9(), dbType, queryParams);
                atomicInteger.getAndIncrement();
                isWhereAdded = true;
            }
            if (isNotNull(pemDbObjectEntity.getWhereColumnName10()) && isNotNull(sqlRequestModel.getWhereColumnValue10()) && isNotNull(pemDbObjectDataTypeEntity.getWhereColumnName10_datatype())) {
                stringBuilder.append(isWhereAdded ? " AND " : " WHERE ").append(pemDbObjectEntity.getWhereColumnName10())
                        .append(" = ? ");
                applyQueryParams(pemDbObjectDataTypeEntity.getWhereColumnName10_datatype(), sqlRequestModel.getWhereColumnValue10(), dbType, queryParams);
                atomicInteger.getAndIncrement();
                isWhereAdded = true;
            }
            if (!isWhereAdded) {
                throw GlobalExceptionHandler.internalServerError("You are updating multiple records please provide a value for where condition param");
            }
        }
    }

    private Connection getDBConnection() {

        try {
            Class.forName(driverClassName);
            return DriverManager.getConnection(url, userName, password);
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.error("unable to get connection: ", e);
            throw internalServerError(e.getMessage());
        }
    }

    private SqlResponseModel executeSelectQuery(SingleSqlModel singleSqlModel) {
        SqlResponseModel sqlResponseModel = new SqlResponseModel();
        ColumnModel columnModel = new ColumnModel();

        LOGGER.info("Executed Query : {}", singleSqlModel.getContent());
        try (Connection connection = getDBConnection(); PreparedStatement preparedStatement = connection.prepareStatement(singleSqlModel.getContent())) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                AtomicInteger rowNum = new AtomicInteger(1);
                List<SqlRequestModel> rowModels = new ArrayList<>();
                while (resultSet.next()) {
                    SqlRequestModel rowModel = new SqlRequestModel();
                    rowModel.setRowCount(rowNum.get());
                    IntStream.rangeClosed(1, resultSetMetaData.getColumnCount()).forEach(value -> {
                        try {
                            switch (value) {
                                case 1:
                                    rowModel.setColumnValue1(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName1(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 2:
                                    rowModel.setColumnValue2(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName2(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 3:
                                    rowModel.setColumnValue3(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName3(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 4:
                                    rowModel.setColumnValue4(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName4(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 5:
                                    rowModel.setColumnValue5(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName5(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 6:
                                    rowModel.setColumnValue6(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName6(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 7:
                                    rowModel.setColumnValue7(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName7(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 8:
                                    rowModel.setColumnValue8(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName8(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 9:
                                    rowModel.setColumnValue9(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName9(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 10:
                                    rowModel.setColumnValue10(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName10(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 11:
                                    rowModel.setColumnValue11(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName11(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 12:
                                    rowModel.setColumnValue12(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName12(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 13:
                                    rowModel.setColumnValue13(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName13(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 14:
                                    rowModel.setColumnValue14(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName14(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 15:
                                    rowModel.setColumnValue15(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName15(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 16:
                                    rowModel.setColumnValue16(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName16(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 17:
                                    rowModel.setColumnValue17(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName17(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 18:
                                    rowModel.setColumnValue18(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName18(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 19:
                                    rowModel.setColumnValue19(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName19(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 20:
                                    rowModel.setColumnValue20(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName20(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 21:
                                    rowModel.setColumnValue21(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName21(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 22:
                                    rowModel.setColumnValue22(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName22(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 23:
                                    rowModel.setColumnValue23(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName23(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 24:
                                    rowModel.setColumnValue24(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName24(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 25:
                                    rowModel.setColumnValue25(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName25(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 26:
                                    rowModel.setColumnValue26(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName26(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 27:
                                    rowModel.setColumnValue27(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName27(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 28:
                                    rowModel.setColumnValue28(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName28(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 29:
                                    rowModel.setColumnValue29(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName29(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 30:
                                    rowModel.setColumnValue30(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName30(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 31:
                                    rowModel.setColumnValue31(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName31(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 32:
                                    rowModel.setColumnValue32(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName32(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 33:
                                    rowModel.setColumnValue33(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName33(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 34:
                                    rowModel.setColumnValue34(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName34(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 35:
                                    rowModel.setColumnValue35(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName35(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 36:
                                    rowModel.setColumnValue36(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName36(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 37:
                                    rowModel.setColumnValue37(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName37(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 38:
                                    rowModel.setColumnValue38(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName38(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 39:
                                    rowModel.setColumnValue39(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName39(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 40:
                                    rowModel.setColumnValue40(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName40(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 41:
                                    rowModel.setColumnValue41(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName41(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 42:
                                    rowModel.setColumnValue42(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName42(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 43:
                                    rowModel.setColumnValue43(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName43(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 44:
                                    rowModel.setColumnValue44(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName44(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 45:
                                    rowModel.setColumnValue45(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName45(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 46:
                                    rowModel.setColumnValue46(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName46(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 47:
                                    rowModel.setColumnValue47(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName47(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 48:
                                    rowModel.setColumnValue48(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName48(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 49:
                                    rowModel.setColumnValue49(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName49(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 50:
                                    rowModel.setColumnValue50(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName50(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 51:
                                    rowModel.setColumnValue51(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName51(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 52:
                                    rowModel.setColumnValue52(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName52(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 53:
                                    rowModel.setColumnValue53(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName53(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 54:
                                    rowModel.setColumnValue54(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName54(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 55:
                                    rowModel.setColumnValue55(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName55(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 56:
                                    rowModel.setColumnValue56(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName56(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 57:
                                    rowModel.setColumnValue57(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName57(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 58:
                                    rowModel.setColumnValue58(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName58(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 59:
                                    rowModel.setColumnValue59(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName59(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 60:
                                    rowModel.setColumnValue60(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName60(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 61:
                                    rowModel.setColumnValue61(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName61(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 62:
                                    rowModel.setColumnValue62(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName62(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 63:
                                    rowModel.setColumnValue63(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName63(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 64:
                                    rowModel.setColumnValue64(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName64(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 65:
                                    rowModel.setColumnValue65(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName65(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 66:
                                    rowModel.setColumnValue66(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName66(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 67:
                                    rowModel.setColumnValue67(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName67(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 68:
                                    rowModel.setColumnValue68(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName68(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 69:
                                    rowModel.setColumnValue69(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName69(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 70:
                                    rowModel.setColumnValue70(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName70(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 71:
                                    rowModel.setColumnValue71(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName71(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 72:
                                    rowModel.setColumnValue72(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName72(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 73:
                                    rowModel.setColumnValue73(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName73(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 74:
                                    rowModel.setColumnValue74(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName74(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 75:
                                    rowModel.setColumnValue75(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName75(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 76:
                                    rowModel.setColumnValue76(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName76(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 77:
                                    rowModel.setColumnValue77(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName77(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 78:
                                    rowModel.setColumnValue78(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName78(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 79:
                                    rowModel.setColumnValue79(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName79(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 80:
                                    rowModel.setColumnValue80(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName80(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 81:
                                    rowModel.setColumnValue81(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName81(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 82:
                                    rowModel.setColumnValue82(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName82(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 83:
                                    rowModel.setColumnValue83(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName83(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 84:
                                    rowModel.setColumnValue84(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName84(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 85:
                                    rowModel.setColumnValue85(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName85(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 86:
                                    rowModel.setColumnValue86(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName86(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 87:
                                    rowModel.setColumnValue87(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName87(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 88:
                                    rowModel.setColumnValue88(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName88(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 89:
                                    rowModel.setColumnValue89(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName89(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 90:
                                    rowModel.setColumnValue90(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName90(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 91:
                                    rowModel.setColumnValue91(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName91(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 92:
                                    rowModel.setColumnValue92(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName92(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 93:
                                    rowModel.setColumnValue93(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName93(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 94:
                                    rowModel.setColumnValue94(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName94(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 95:
                                    rowModel.setColumnValue95(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName95(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 96:
                                    rowModel.setColumnValue96(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName96(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 97:
                                    rowModel.setColumnValue97(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName97(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 98:
                                    rowModel.setColumnValue98(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName98(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 99:
                                    rowModel.setColumnValue99(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName99(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 100:
                                    rowModel.setColumnValue100(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName100(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 101:
                                    rowModel.setColumnValue101(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName101(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 102:
                                    rowModel.setColumnValue102(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName102(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 103:
                                    rowModel.setColumnValue103(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName103(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 104:
                                    rowModel.setColumnValue104(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName104(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 105:
                                    rowModel.setColumnValue105(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName105(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 106:
                                    rowModel.setColumnValue106(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName106(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 107:
                                    rowModel.setColumnValue107(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName107(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 108:
                                    rowModel.setColumnValue108(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName108(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 109:
                                    rowModel.setColumnValue109(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName109(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 110:
                                    rowModel.setColumnValue110(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName110(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 111:
                                    rowModel.setColumnValue111(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName111(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 112:
                                    rowModel.setColumnValue112(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName112(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 113:
                                    rowModel.setColumnValue113(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName113(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 114:
                                    rowModel.setColumnValue114(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName114(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 115:
                                    rowModel.setColumnValue115(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName115(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 116:
                                    rowModel.setColumnValue116(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName116(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 117:
                                    rowModel.setColumnValue117(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName117(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 118:
                                    rowModel.setColumnValue118(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName118(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 119:
                                    rowModel.setColumnValue119(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName119(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 120:
                                    rowModel.setColumnValue120(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName120(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 121:
                                    rowModel.setColumnValue121(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName121(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 122:
                                    rowModel.setColumnValue122(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName122(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 123:
                                    rowModel.setColumnValue123(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName123(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 124:
                                    rowModel.setColumnValue124(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName124(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 125:
                                    rowModel.setColumnValue125(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName125(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 126:
                                    rowModel.setColumnValue126(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName126(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 127:
                                    rowModel.setColumnValue127(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName127(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 128:
                                    rowModel.setColumnValue128(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName128(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 129:
                                    rowModel.setColumnValue129(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName129(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 130:
                                    rowModel.setColumnValue130(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName130(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 131:
                                    rowModel.setColumnValue131(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName131(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 132:
                                    rowModel.setColumnValue132(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName132(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 133:
                                    rowModel.setColumnValue133(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName133(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 134:
                                    rowModel.setColumnValue134(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName134(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 135:
                                    rowModel.setColumnValue135(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName135(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 136:
                                    rowModel.setColumnValue136(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName136(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 137:
                                    rowModel.setColumnValue137(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName137(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 138:
                                    rowModel.setColumnValue138(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName138(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 139:
                                    rowModel.setColumnValue139(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName139(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 140:
                                    rowModel.setColumnValue140(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName140(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 141:
                                    rowModel.setColumnValue141(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName141(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 142:
                                    rowModel.setColumnValue142(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName142(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 143:
                                    rowModel.setColumnValue143(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName143(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 144:
                                    rowModel.setColumnValue144(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName144(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 145:
                                    rowModel.setColumnValue145(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName145(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 146:
                                    rowModel.setColumnValue146(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName146(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 147:
                                    rowModel.setColumnValue147(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName147(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 148:
                                    rowModel.setColumnValue148(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName148(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 149:
                                    rowModel.setColumnValue149(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName149(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                case 150:
                                    rowModel.setColumnValue150(resultSet.getString(value));
                                    if (rowNum.get() == 1) {
                                        columnModel.setColumnName150(resultSetMetaData.getColumnName(value));
                                    }
                                    break;
                                default:
                                    //No implementation needed
                            }
                        } catch (Exception e) {
                            throw internalServerError(e.getMessage());
                        }
                    });
                    rowNum.getAndIncrement();
                    rowModels.add(rowModel);
                }
                sqlResponseModel.setColumnModel(columnModel);
                sqlResponseModel.setData(rowModels);
            }
        } catch (SQLException e) {
            LOGGER.error("Error while performing SQL Operation ", e);
            throw internalServerError(e.getMessage());
        }
        return sqlResponseModel;
    }

    private String executeUpdateQuery(SingleSqlModel singleSqlModel) {
        LOGGER.info("Executed Query : {}", singleSqlModel.getContent());
        try (Connection connection = getDBConnection(); PreparedStatement preparedStatement = connection.prepareStatement(singleSqlModel.getContent())) {
            return preparedStatement.executeUpdate() + " record(s) effected.";
        } catch (SQLException e) {
            LOGGER.error("Error while performing SQL Operation ", e);
            throw internalServerError(e.getMessage());
        }
    }

    @PostConstruct
    public void doStuff() {
        if (isNotNull(driverClassName)) {
            if (driverClassName.endsWith("OracleDriver")) {
                dbType = PCMConstants.ORACLE;
            } else if (driverClassName.endsWith("DB2Driver")) {
                dbType = PCMConstants.DB2;
            } else if (driverClassName.endsWith("SQLServerDriver")) {
                dbType = PCMConstants.SQL_SERVER;
            } else {
                LOGGER.error("PEM Database DriverClass Name Not Found.");
            }
        }
        if (isNotNull(password)) {
            if (password.startsWith("ENC")) {
                password = passwordUtilityService.decrypt(removeENC(password));
            }
        }
    }

}
