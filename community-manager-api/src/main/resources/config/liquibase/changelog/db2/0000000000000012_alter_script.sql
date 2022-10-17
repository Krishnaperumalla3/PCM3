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
--  DDL for ALL Tables
--------------------------------------------------------
ALTER TABLE PETPE_FILESYSTEM
    ADD COLUMN USER_NAME VARCHAR(50);
ALTER TABLE PETPE_FILESYSTEM
    ADD COLUMN PASSWORD VARCHAR(50);

ALTER TABLE PETPE_HTTP
    ADD COLUMN ADAPTER_NAME VARCHAR(200);
ALTER TABLE PETPE_HTTP
    ADD COLUMN POOLING_INTERVAL_MINS VARCHAR(5);

ALTER TABLE PETPE_MAILBOX
    ADD COLUMN FILTER VARCHAR(200);
ALTER TABLE PETPE_MAILBOX
    ADD COLUMN MBPARTNER_PRIORITY VARCHAR(10);

ALTER TABLE PETPE_MQ
    ADD COLUMN ADAPTER_NAME VARCHAR(200);
ALTER TABLE PETPE_MQ
    ADD COLUMN HOST_NAME VARCHAR(200);

ALTER TABLE PETPE_PROCESSDOCS
    ADD COLUMN VERSION VARCHAR(100);

ALTER TABLE PETPE_PROCESSRULES
    ADD COLUMN SEQ_ID SMALLINT;
ALTER TABLE PETPE_PROCESSRULES
    ADD COLUMN PROCESS_DOC_REF VARCHAR(50);

ALTER TABLE PETPE_TRADINGPARTNER
    ADD COLUMN PARTNER_PRIORITY VARCHAR(50);

ALTER TABLE PETPE_TRANSFERINFO
    ADD COLUMN CORRELATION_VALUE_11 CLOB;
ALTER TABLE PETPE_TRANSFERINFO
    ADD COLUMN CORRELATION_VALUE_12 CLOB;
ALTER TABLE PETPE_TRANSFERINFO
    ADD COLUMN CORRELATION_VALUE_13 CLOB;
ALTER TABLE PETPE_TRANSFERINFO
    ADD COLUMN CORRELATION_VALUE_14 CLOB;
ALTER TABLE PETPE_TRANSFERINFO
    ADD COLUMN CORRELATION_VALUE_15 CLOB;
ALTER TABLE PETPE_TRANSFERINFO
    ADD COLUMN CORRELATION_VALUE_16 CLOB;
ALTER TABLE PETPE_TRANSFERINFO
    ADD COLUMN CORRELATION_VALUE_17 CLOB;
ALTER TABLE PETPE_TRANSFERINFO
    ADD COLUMN CORRELATION_VALUE_18 VARCHAR(500);
ALTER TABLE PETPE_TRANSFERINFO
    ADD COLUMN CORRELATION_VALUE_19 VARCHAR(500);
ALTER TABLE PETPE_TRANSFERINFO
    ADD COLUMN CORRELATION_VALUE_20 VARCHAR(500);
ALTER TABLE PETPE_TRANSFERINFO
    ADD COLUMN CORRELATION_VALUE_21 VARCHAR(500);
ALTER TABLE PETPE_TRANSFERINFO
    ADD COLUMN CORRELATION_VALUE_22 VARCHAR(500);
ALTER TABLE PETPE_TRANSFERINFO
    ADD COLUMN CORRELATION_VALUE_23 VARCHAR(500);
ALTER TABLE PETPE_TRANSFERINFO
    ADD COLUMN CORRELATION_VALUE_24 VARCHAR(500);
ALTER TABLE PETPE_TRANSFERINFO
    ADD COLUMN CORRELATION_VALUE_25 VARCHAR(500);
ALTER TABLE PETPE_TRANSFERINFO
    ADD COLUMN TRANSFILE VARCHAR(200);
ALTER TABLE PETPE_TRANSFERINFO
    ADD COLUMN STATUS_COMMENTS VARCHAR(500);
ALTER TABLE PETPE_TRANSFERINFO
    ADD COLUMN IS_ENCRYPTED VARCHAR(1);

ALTER TABLE PETPE_WEBSERVICE
    ADD COLUMN OUTBOUND_URL VARCHAR(500);
ALTER TABLE PETPE_WEBSERVICE
    ADD COLUMN IN_DIRECTORY VARCHAR(100);
ALTER TABLE PETPE_WEBSERVICE
    ADD COLUMN CERTIFICATE VARCHAR(200);
ALTER TABLE PETPE_WEBSERVICE
    ADD COLUMN ADAPTER_NAME VARCHAR(200);
ALTER TABLE PETPE_WEBSERVICE
    ADD COLUMN POOLING_INTERVAL_MINS VARCHAR(5);

ALTER TABLE PETPE_SFG_FTP
    ADD COLUMN HOST_NAME VARCHAR(100);
ALTER TABLE PETPE_SFG_FTP
    ADD COLUMN PORT_NO VARCHAR(10);
ALTER TABLE PETPE_SFG_FTP
    ADD COLUMN CONNECTION_TYPE VARCHAR(50) DEFAULT 'active';
ALTER TABLE PETPE_SFG_FTP
    ADD COLUMN RETRY_INTERVAL VARCHAR(10) DEFAULT '5';
ALTER TABLE PETPE_SFG_FTP
    ADD COLUMN NO_OF_RETRIES VARCHAR(10) DEFAULT '5';
ALTER TABLE PETPE_SFG_FTP
    ADD COLUMN ENCRYPTION_STRENGTH VARCHAR(50) DEFAULT 'ALL';
ALTER TABLE PETPE_SFG_FTP
    ADD COLUMN USE_CCC CHAR(1) DEFAULT 'N';
ALTER TABLE PETPE_SFG_FTP
    ADD COLUMN USE_IMPLICIT_SSL CHAR(1) DEFAULT 'N';

ALTER TABLE PETPE_RULES
    ADD CONSTRAINT PETPE_RULES_RULE_NAME_UNIQUE UNIQUE (RULE_NAME);

ALTER TABLE PETPE_SO_USERS
    ADD COLUMN ACTIVATION_KEY VARCHAR(250);

ALTER TABLE PETPE_AS2
    ALTER COLUMN COMPRESS_DATA SET DATA TYPE VARCHAR(10);

ALTER TABLE PETPE_APPLICATION
    ALTER COLUMN APP_INTEGRATION_PROTOCOL SET DATA TYPE VARCHAR(20);