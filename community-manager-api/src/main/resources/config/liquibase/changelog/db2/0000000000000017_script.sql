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
--  DML for Table PETPE_SO_USERS
--------------------------------------------------------
UPDATE PETPE_SO_USERS SET ROLE = 'super_admin' WHERE ROLE = 'admin';
UPDATE PETPE_SO_USERS SET ROLE = 'data_processor' WHERE ROLE = 'user';
UPDATE PETPE_SO_USERS SET ROLE = 'super_admin' WHERE ROLE = 'SUPER_ADMIN';
UPDATE PETPE_SO_USERS SET ROLE = 'admin' WHERE ROLE = 'ADMIN';
UPDATE PETPE_SO_USERS SET ROLE = 'on_boarder' WHERE ROLE = 'ON_BOARDER';
UPDATE PETPE_SO_USERS SET ROLE = 'business_admin' WHERE ROLE = 'BUSINESS_ADMIN';
UPDATE PETPE_SO_USERS SET ROLE = 'business_user' WHERE ROLE = 'BUSINESS_USER';
UPDATE PETPE_SO_USERS SET ROLE = 'data_processor' WHERE ROLE = 'DATA_PROCESSOR';
UPDATE PETPE_SO_USERS SET ROLE = 'data_processor_restricted' WHERE ROLE = 'DATA_PROCESSOR_RESTRICTED';

--------------------------------------------------------
--  DDL for PETPE_TRADINGPARTNER
--------------------------------------------------------
ALTER TABLE PETPE_TRADINGPARTNER
    ADD COLUMN CUSTOM_TP_NAME VARCHAR(255);
ALTER TABLE PETPE_TRADINGPARTNER
    ADD COLUMN PGP_INFO VARCHAR(255);
ALTER TABLE PETPE_TRADINGPARTNER
    ADD COLUMN IP_WHITELIST VARCHAR(255);
ALTER TABLE PETPE_TRADINGPARTNER
    ADD COLUMN IS_ONLY_PCM VARCHAR(1);

--------------------------------------------------------
--  DDL for PETPE_SO_USERS
--------------------------------------------------------
ALTER TABLE PETPE_SO_USERS
    ADD COLUMN OTP VARCHAR(6);

--------------------------------------------------------
--  DDL for PETPE_CD
--------------------------------------------------------
--ALTER TABLE PETPE_CD ADD COLUMN OPERATING_SYSTEM VARCHAR(20);
--ALTER TABLE PETPE_CD ADD COLUMN HOST_NAME VARCHAR(255);
--ALTER TABLE PETPE_CD ADD COLUMN PORT VARCHAR(10 BYTE);
--ALTER TABLE PETPE_CD ADD COLUMN COPY_SIS_OPTS VARCHAR(255);
--ALTER TABLE PETPE_CD ADD COLUMN CHECK_POINT VARCHAR(10);
--ALTER TABLE PETPE_CD ADD COLUMN COMPRESSION_LEVEL VARCHAR(2);
--ALTER TABLE PETPE_CD ADD COLUMN DISPOSITION VARCHAR(10);
--ALTER TABLE PETPE_CD ADD COLUMN SUBMIT VARCHAR(255);
--ALTER TABLE PETPE_CD ADD COLUMN SECURE VARCHAR(1);
--ALTER TABLE PETPE_CD ADD COLUMN RUN_JOB VARCHAR(255);
--ALTER TABLE PETPE_CD ADD COLUMN RUN_TASK VARCHAR(255);
--ALTER TABLE PETPE_CD ADD COLUMN CA_CERTIFICATE VARCHAR(255);
--ALTER TABLE PETPE_CD ADD COLUMN CIPHER_SUITS VARCHAR(2000);
--ALTER TABLE PETPE_CD ADD COLUMN DCB_PARAM VARCHAR(255);
--ALTER TABLE PETPE_CD ADD COLUMN DNS_NAME VARCHAR(255);
--ALTER TABLE PETPE_CD ADD COLUMN UNIT VARCHAR(255);
--ALTER TABLE PETPE_CD ADD COLUMN STORAGE_CLASS VARCHAR(255);
--ALTER TABLE PETPE_CD ADD COLUMN SPACE VARCHAR(255);

--------------------------------------------------------
--  DDL for PETPE_AWS_S3
--------------------------------------------------------
CREATE TABLE PETPE_AWS_S3
(
    PK_ID                 VARCHAR(20)  NOT NULL,
    SUBSCRIBER_TYPE       VARCHAR(5)   NOT NULL,
    SUBSCRIBER_ID         VARCHAR(20)  NOT NULL,
    SOURCE_PATH           VARCHAR(255),
    BUCKET_NAME           VARCHAR(255) NOT NULL,
    FILE_NAME             VARCHAR(255),
    ACCESS_KEY            VARCHAR(255) NOT NULL,
    SECRET_KEY            VARCHAR(255) NOT NULL,
    ENDPOINT              VARCHAR(255),
    REGION                VARCHAR(255),
    ADAPTER_NAME          VARCHAR(200),
    POOLING_INTERVAL_MINS VARCHAR(10)  NOT NULL,
    IN_MAILBOX            VARCHAR(255),
    FILE_TYPE             VARCHAR(100),
    IS_ACTIVE             VARCHAR(1)   NOT NULL DEFAULT 'Y',
    IS_HUB_INFO           VARCHAR(1)   NOT NULL DEFAULT 'N',
    CREATED_BY            VARCHAR(15)  NOT NULL,
    LAST_UPDATED_BY       VARCHAR(15),
    LAST_UPDATED_DT       TIMESTAMP(6),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for PETPE_AWS_S3
--------------------------------------------------------
CREATE TABLE PETPE_CD
(
    PK_ID                 VARCHAR(20) NOT NULL,
    SUBSCRIBER_TYPE       VARCHAR(5)  NOT NULL,
    SUBSCRIBER_ID         VARCHAR(20) NOT NULL,
    LOCAL_NODE_NAME       VARCHAR(255),
    REMOTE_FILE_NAME      VARCHAR(255),
    NODE_NAME             VARCHAR(255),
    SNODE_ID              VARCHAR(255),
    SNODE_ID_PASSWORD     VARCHAR(255),
    OPERATING_SYSTEM      VARCHAR(255),
    DIRECTORY             VARCHAR(255),
    HOST_NAME             VARCHAR(200),
    PORT                  VARCHAR(50),
    COPY_SIS_OPTS         VARCHAR(255),
    CHECK_POINT           VARCHAR(100),
    COMPRESSION_LEVEL     VARCHAR(255),
    DISPOSITION           VARCHAR(255),
    SUBMIT                VARCHAR(200),
    SECURE                VARCHAR(10),
    RUN_JOB               VARCHAR(255),
    RUN_TASK              VARCHAR(100),
    CA_CERTIFICATE_NAME   VARCHAR(255),
    CIPHER_SUITS          CLOB,
    DCB_PARAM             VARCHAR(255),
    DNS_NAME              VARCHAR(255),
    UNIT                  VARCHAR(255),
    STORAGE_CLASS         VARCHAR(255),
    SECURITY_PROTOCOL     VARCHAR(255),
    SPACE                 VARCHAR(255),
    ADAPTER_NAME          VARCHAR(200),
    POOLING_INTERVAL_MINS VARCHAR(10),
    IS_ACTIVE             VARCHAR(1)  NOT NULL DEFAULT 'Y',
    IS_HUB_INFO           VARCHAR(1)  NOT NULL DEFAULT 'N',
    CREATED_BY            VARCHAR(15) NOT NULL,
    LAST_UPDATED_BY       VARCHAR(15),
    LAST_UPDATED_DT       TIMESTAMP(6),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;


CREATE SEQUENCE SEQ_PETPE_PEM_GEN START WITH 1 INCREMENT BY 1 NO MAXVALUE NO CYCLE CACHE 20;

ALTER TABLE PETPE_SFG_FTP
    ADD COLUMN PRF_AUTH_TYPE VARCHAR(15);

ALTER TABLE PETPE_PROCESSDOCS
    ALTER COLUMN PROCESS_RULESEQ SET DATA TYPE VARCHAR(2000);
