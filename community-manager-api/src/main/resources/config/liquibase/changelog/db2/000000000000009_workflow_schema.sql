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

--------------------------------------------------------
--  DDL for Table PETPE_PROCESS
--------------------------------------------------------
CREATE TABLE PETPE_PROCESS
(
    SEQ_ID              VARCHAR(20) NOT NULL,
    PARTNER_PROFILE     VARCHAR(20) NOT NULL,
    APPLICATION_PROFILE VARCHAR(20) NOT NULL,
    SEQ_TYPE            VARCHAR(50) NOT NULL,
    FLOW                VARCHAR(50) NOT NULL,
    FILE_NAME           VARCHAR(200),
    PRIMARY KEY (SEQ_ID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_PROCESSDOCS
--------------------------------------------------------
CREATE TABLE PETPE_PROCESSDOCS
(
    PK_ID            VARCHAR(254) NOT NULL,
    PROCESS_REF      VARCHAR(50)  NOT NULL,
    DOCTYPE          VARCHAR(50),
    PARTNERID        VARCHAR(50),
    RECIVERID        VARCHAR(50),
    DOCTRANS         VARCHAR(50),
    FILENAME_PATTERN VARCHAR(200),
    PROCESS_RULESEQ  VARCHAR(200),
    PROCESSRULE_REF  VARCHAR(254),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_PROCESSRULES
--------------------------------------------------------
CREATE TABLE PETPE_PROCESSRULES
(
    PK_ID            VARCHAR(20) NOT NULL,
    RULE_ID          VARCHAR(20) NOT NULL,
    RULE_NAME        VARCHAR(50) NOT NULL,
    PROPERTY_NAME_1  VARCHAR(500),
    PROPERTY_NAME_2  VARCHAR(200),
    PROPERTY_NAME_3  VARCHAR(200),
    PROPERTY_NAME_4  VARCHAR(200),
    PROPERTY_NAME_5  VARCHAR(200),
    PROPERTY_NAME_6  VARCHAR(200),
    PROPERTY_NAME_7  VARCHAR(200),
    PROPERTY_NAME_8  VARCHAR(200),
    PROPERTY_NAME_9  VARCHAR(200),
    PROPERTY_NAME_10 VARCHAR(200),
    PROPERTY_NAME_11 VARCHAR(200),
    PROPERTY_NAME_12 VARCHAR(200),
    PROPERTY_NAME_13 VARCHAR(200),
    PROPERTY_NAME_14 VARCHAR(200),
    PROPERTY_NAME_15 VARCHAR(200),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;
--------------------------------------------------------
--  DDL for Table PETPE_RULES
--------------------------------------------------------
CREATE TABLE PETPE_RULES
(
    RULE_ID             VARCHAR(20)  NOT NULL,
    RULE_NAME           VARCHAR(50)  NOT NULL,
    BUSINESS_PROCESS_ID VARCHAR(120) NOT NULL,
    PROPERTY_NAME_1     VARCHAR(200),
    PROPERTY_NAME_2     VARCHAR(200),
    PROPERTY_NAME_3     VARCHAR(200),
    PROPERTY_NAME_4     VARCHAR(200),
    PROPERTY_NAME_5     VARCHAR(200),
    PROPERTY_NAME_6     VARCHAR(200),
    PROPERTY_NAME_7     VARCHAR(200),
    PROPERTY_NAME_8     VARCHAR(200),
    PROPERTY_NAME_9     VARCHAR(200),
    PROPERTY_NAME_10    VARCHAR(200),
    PROPERTY_NAME_11    VARCHAR(200),
    PROPERTY_NAME_12    VARCHAR(200),
    PROPERTY_NAME_13    VARCHAR(200),
    PROPERTY_NAME_14    VARCHAR(200),
    PROPERTY_NAME_15    VARCHAR(200),
    PRIMARY KEY (RULE_ID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_B2B_ACTIVITY_HISTORY
--------------------------------------------------------
CREATE TABLE PETPE_B2B_ACTIVITY_HISTORY
(
    PK_ID          VARCHAR(50)  NOT NULL,
    PROCESS_REF_ID VARCHAR(30),
    USER_NAME      VARCHAR(100) NOT NULL,
    USER_ID        VARCHAR(15)  NOT NULL,
    ACTIVITY_DT    TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
    ACTIVITY       CLOB,
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;
