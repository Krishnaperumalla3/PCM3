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

package com.pe.pcm.config.liquibase;


import liquibase.integration.spring.SpringLiquibase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

import static com.pe.pcm.utils.CommonFunctions.isNotNull;

/**
 * @author Kiran Reddy.
 */

@Configuration
@EnableAutoConfiguration
@EnableConfigurationProperties(LiquibaseProperties.class)
public class LiquibaseConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(LiquibaseConfiguration.class);

    private final DataSource dataSource;
    private final Environment environment;
    private final LiquibaseProperties properties;

    @Autowired
    public LiquibaseConfiguration(LiquibaseProperties properties, DataSource dataSource, Environment environment) {
        this.properties = properties;
        this.dataSource = dataSource;
        this.environment = environment;
    }

    @Bean(name = "liquibase")
    @ConditionalOnExpression("'${spring.datasource.driver-class-name}'.contains('OracleDriver')")
    public SpringLiquibase oracle() {
        LOGGER.info("Oracle Database Registered for Liquibase");
        nullifyLiquibaseCheckSum();
        return setParams("classpath:/config/liquibase/oracle_master.xml");
    }

    @Bean(name = "liquibase")
    @ConditionalOnExpression("'${spring.datasource.driver-class-name}'.contains('DB2Driver')")
    public SpringLiquibase db2() {
        LOGGER.info("DB2 Database Registered for Liquibase");
        createTableSpace();
        nullifyLiquibaseCheckSum();
        return setParams("classpath:/config/liquibase/db2_master.xml");
    }

    @Bean(name = "liquibase")
    @ConditionalOnExpression("'${spring.datasource.driver-class-name}'.contains('SQLServerDriver')")
    public SpringLiquibase sqlServer() {
        LOGGER.info("SQL Server Database Registered for Liquibase");
        nullifyLiquibaseCheckSum();
        return setParams("classpath:/config/liquibase/sql_server_master.xml");
    }

    private void nullifyLiquibaseCheckSum() {
        try (Connection connection = dataSource.getConnection();) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("SELECT * FROM DATABASECHANGELOG WHERE AUTHOR = ''");
                LOGGER.info("Nullifying Liquibase checkSum.");
                try(Statement st = connection.createStatement()) {
                    st.execute("UPDATE DATABASECHANGELOG SET MD5SUM = NULL WHERE AUTHOR = 'community-manager' OR AUTHOR = 'PragmaEdge'");
                    connection.commit();
                    LOGGER.info("Nullified Liquibase checkSum successfully.");
                } catch (Exception ee) {
                    LOGGER.error("Exception in nullifyLiquibaseCheckSum(): ");
                }
            } catch (Exception e) {
                LOGGER.info("DATABASECHANGELOG table not available ignoring nullifyLiquibaseCheckSum.");
            }
        } catch (Exception e) {
            LOGGER.error("Exception in nullifyLiquibaseCheckSum(): ");
        }
    }

    private void createTableSpace() {
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("CREATE BUFFERPOOL PCMPOOL PAGESIZE 32K");
                connection.commit();
                LOGGER.info("PCMPOOL buffer pool created successfully.");
            } catch (Exception es) {
                LOGGER.info("PCMPOOL buffer pool already exist or this user dont have permissions to create the buffer pool");
                LOGGER.info("SQL Script to create BufferPool: CREATE BUFFERPOOL PCMPOOL PAGESIZE 32K");
            }
            try (Statement statement = connection.createStatement()) {
                statement.execute("CREATE TABLESPACE PCMTBSPACE PAGESIZE 32K MANAGED BY AUTOMATIC STORAGE BUFFERPOOL PCMPOOL");
                connection.commit();
                LOGGER.info("PCMTBSPACE tablespace created successfully.");
            } catch (Exception es) {
                LOGGER.info("PCMTBSPACE tablespace already exist.");
                LOGGER.info("SQL Script to create Tablespace: CREATE TABLESPACE PCMTBSPACE PAGESIZE 32K MANAGED BY AUTOMATIC STORAGE BUFFERPOOL PCMPOOL");
            }
        } catch (Exception e) {
            LOGGER.error("Exception in createTableSpace(): ");
        }
    }

    private SpringLiquibase setParams(String path) {
        String flag = environment.getProperty("spring.liquibase.enabled");
        if (!isNotNull(flag)) {
            System.setProperty("spring.liquibase.enabled", "true");
        }
        SpringLiquibase liquibase = new SpringLiquibase();
        properties.setChangeLog(path);
        liquibase.setChangeLog(this.properties.getChangeLog());
        liquibase.setClearCheckSums(this.properties.isClearChecksums());
        liquibase.setContexts(this.properties.getContexts());
        liquibase.setDefaultSchema(this.properties.getDefaultSchema());
        liquibase.setLiquibaseSchema(this.properties.getLiquibaseSchema());
        if (path.contains("db2_master.xml")) {
            liquibase.setLiquibaseTablespace("PCMTBSPACE");
        }
        liquibase.setDatabaseChangeLogTable(this.properties.getDatabaseChangeLogTable());
        liquibase.setDatabaseChangeLogLockTable(this.properties.getDatabaseChangeLogLockTable());
        liquibase.setDropFirst(this.properties.isDropFirst());
        liquibase.setShouldRun(this.properties.isEnabled());
        liquibase.setLabels(this.properties.getLabels());
        liquibase.setChangeLogParameters(this.properties.getParameters());
        liquibase.setRollbackFile(this.properties.getRollbackFile());
        liquibase.setTestRollbackOnUpdate(this.properties.isTestRollbackOnUpdate());
        liquibase.setTag(this.properties.getTag());
        liquibase.setDataSource(this.dataSource);
        return liquibase;
    }

    //    //    @PostConstruct
//    void validation() {
//        try {
//            liquibaseHostName = String.format("%s (%s)", InetAddress.getLocalHost().getHostName(), InetAddress.getLocalHost().getHostAddress());
//            String query = "SELECT * FROM " + properties.getDatabaseChangeLogLockTable() + " WHERE  LOCKEDBY= '" + liquibaseHostName + "'";
//            System.out.println("Query: " + query);
////            try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
////
////                try (ResultSet resultSet = preparedStatement.executeQuery()) {
////                    int locked = 0;
////                    while (resultSet.next()) {
////                        locked = resultSet.getInt("LOCKED");
////                        System.out.println("LOCKED BY" + resultSet.getString("LOCKEDBY"));
////
////                        System.out.println("LOCKED " + locked);
////
////                    }
////                    if (locked != 0) {
////                        try (PreparedStatement ps = connection.prepareStatement("UPDATE " +
////                                properties.getDatabaseChangeLogLockTable() +
////                                " SET LOCKED = 0, LOCKGRANTED = '', LOCKEDBY = '' WHERE LOCKEDBY = '" +
////                                InetAddress.getLocalHost().getHostName() + "'")) {
////                            ps.executeUpdate();
////                            System.out.println("Change Log Lock released by Community Manager");
////                            LOGGER.info("Change Log Lock released by Community Manager");
////                            connection.commit();
////                        }
////                    } else {
////                        System.out.println("not locked");
////                    }
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
//        } catch (Exception e) {
//            //No need to Handle
//        }
//    }
//
//    //    @PreDestroy
//    void shutDownLiquibase() {
//        System.out.println("Trying to release the Liquibase change log lock");
//        LOGGER.info("Trying to release the Liquibase change log lock");
//        try {
//            String query = "DELETE FROM " + properties.getDatabaseChangeLogLockTable() + " WHERE LOCKEDBY = ?";
//            try (Connection connection = dataSource.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//                preparedStatement.setString(1, liquibaseHostName);
//                LOGGER.info("Records updated: {}", preparedStatement.executeUpdate());
//                connection.commit();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            LOGGER.info("Successfully released Liquibase change log lock");
//        } catch (Exception e) {
//            //No need to handle
//        }
//    }

}
