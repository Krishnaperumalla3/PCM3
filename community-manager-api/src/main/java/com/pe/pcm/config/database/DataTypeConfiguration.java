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

package com.pe.pcm.config.database;

import com.pe.pcm.miscellaneous.AppShutDownService;
import com.pe.pcm.reports.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import static com.pe.pcm.utils.PCMConstants.*;
import static org.springframework.util.StringUtils.hasText;

/**
 * @author Kiran Reddy.
 */

@Configuration
public class DataTypeConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataTypeConfiguration.class);
    private static final String DB_TYPE_KEY = "dbType";

    private final Environment environment;
    private final AppShutDownService appShutDownService;
    private final JdbcTemplateComponent jdbcTemplateComponent;

    @Autowired
    public DataTypeConfiguration(Environment environment,
                                 AppShutDownService appShutDownService,
                                 JdbcTemplateComponent jdbcTemplateComponent) {
        this.environment = environment;
        this.appShutDownService = appShutDownService;
        this.jdbcTemplateComponent = jdbcTemplateComponent;

    }

    @Bean(name = "reportRepository")
    public ReportRepository loadRepository() {
        String driverClassName = environment.getProperty("spring.datasource.driver-class-name");
        if (hasText(driverClassName)) {
            if (driverClassName.contains("OracleDriver")) {
                LOGGER.info("Oracle Database Registered for Reports");
                System.setProperty(DB_TYPE_KEY, ORACLE);
                return new OracleReportRepository(jdbcTemplateComponent);
            } else if (driverClassName.contains("DB2Driver")) {
                LOGGER.info("DB2 Database Registered for Reports");
                System.setProperty(DB_TYPE_KEY, DB2);
                return new Db2ReportRepository(jdbcTemplateComponent);
            } else if (driverClassName.contains("SQLServerDriver")) {
                LOGGER.info("SQL Server Database Registered for Reports");
                System.setProperty(DB_TYPE_KEY, SQL_SERVER);
                return new SqlServerReportRepository(jdbcTemplateComponent);
            } else {
                appShutDownService.initiateShutdown("Database Driver class name is incorrect, Please contact System Admin.");
            }
        } else {
            appShutDownService.initiateShutdown("Please provide the Database Driver classname. @spring.datasource.driver-class-name");
        }
        return null;
    }

//    @Bean(name = "reportRepository")
//    @ConditionalOnExpression("'${spring.datasource.driver-class-name}'.contains('OracleDriver')")
//    public ReportRepository oracleReportRepository() {
//        LOGGER.info("Oracle Database Registered for Reports");
//        System.setProperty(DB_TYPE_KEY, ORACLE);
//        return new OracleReportRepository(jdbcTemplateComponent);
//    }
//
//    @Bean(name = "reportRepository")
//    @ConditionalOnExpression("'${spring.datasource.driver-class-name}'.contains('DB2Driver')")
//    public ReportRepository db2ReportRepository() {
//        LOGGER.info("DB2 Database Registered for Reports");
//        System.setProperty(DB_TYPE_KEY, DB2);
//        return new Db2ReportRepository(jdbcTemplateComponent);
//    }
//
//    @Bean(name = "reportRepository")
//    @ConditionalOnExpression("'${spring.datasource.driver-class-name}'.contains('SQLServerDriver')")
//    public ReportRepository sqlServerReportRepository() {
//        LOGGER.info("SQL Server Database Registered for Reports");
//        System.setProperty(DB_TYPE_KEY, SQL_SERVER);
//        return new SqlServerReportRepository(jdbcTemplateComponent);
//    }

}
