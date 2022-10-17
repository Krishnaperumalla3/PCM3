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
--  DDL for Table PETPE_SO_AS2
--------------------------------------------------------
CREATE TABLE PETPE_SO_AS2
(
    PK_ID                        VARCHAR(20) NOT NULL,
    SUBSCRIBER_TYPE              VARCHAR(5),
    SUBSCRIBER_ID                VARCHAR(20),
    AS2_IDENTIFIER               VARCHAR(50),
    URL                          VARCHAR(250),
    SENDER_ID                    VARCHAR(50),
    SENDER_QUALIFIER             VARCHAR(10),
    PAYLOAD_SECURITY             VARCHAR(30),
    PAYLOAD_ENCRYPTION_ALGORITHM VARCHAR(50),
    IS_HTTPS                     VARCHAR(1) DEFAULT 'N',
    USERNAME                     VARCHAR(50),
    PASSWORD                     VARCHAR(50),
    SSL_TYPE                     VARCHAR(10),
    ENCRYPTION_ALGORITHM         VARCHAR(50),
    SIGNATURE_ALGORITHM          VARCHAR(15),
    IS_MDN_REQUIRED              VARCHAR(1) DEFAULT 'N',
    MDN_TYPE                     VARCHAR(15),
    MDN_ENCRYPTION               VARCHAR(10),
    RECEIPT_TO_ADDRESS           VARCHAR(50),
    IS_ACTIVE                    VARCHAR(1) DEFAULT 'Y',
    CREATED_BY                   VARCHAR(15),
    LAST_UPDATED_BY              VARCHAR(15),
    LAST_UPDATED_DT              TIMESTAMP(6),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_SO_FILESYSTEM
--------------------------------------------------------
CREATE TABLE PETPE_SO_FILESYSTEM
(
    PK_ID                   VARCHAR(20)  NOT NULL,
    SUBSCRIBER_TYPE         VARCHAR(5)   NOT NULL,
    SUBSCRIBER_ID           VARCHAR(20)  NOT NULL,
    FSA_ADAPTER             VARCHAR(100) NOT NULL,
    IN_DIRECTORY            VARCHAR(200),
    OUT_DIRECTORY           VARCHAR(200),
    FILE_TYPE               VARCHAR(50),
    DELETE_AFTER_COLLECTION VARCHAR(1)   NOT NULL DEFAULT 'N',
    POOLING_INTERVAL_MINS   VARCHAR(10)  NOT NULL,
    IS_ACTIVE               VARCHAR(1)   NOT NULL DEFAULT 'Y',
    CREATED_BY              VARCHAR(15)  NOT NULL,
    LAST_UPDATED_BY         VARCHAR(15),
    LAST_UPDATED_DT         TIMESTAMP(6),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_SO_FTP
--------------------------------------------------------
CREATE TABLE PETPE_SO_FTP
(
    PK_ID                   VARCHAR(20)  NOT NULL,
    SUBSCRIBER_TYPE         VARCHAR(5)   NOT NULL,
    SUBSCRIBER_ID           VARCHAR(20)  NOT NULL,
    PROTOCOL_TYPE           VARCHAR(10)  NOT NULL,
    HOST_NAME               VARCHAR(100) NOT NULL,
    PORT_NO                 VARCHAR(6)   NOT NULL,
    USER_ID                 VARCHAR(200) NOT NULL,
    PASSWORD                VARCHAR(50),
    IN_DIRECTORY            VARCHAR(200),
    OUT_DIRECTORY           VARCHAR(200),
    FILE_TYPE               VARCHAR(50),
    DELETE_AFTER_COLLECTION VARCHAR(1)   NOT NULL DEFAULT 'N',
    TRANSFER_TYPE           VARCHAR(10)  NOT NULL,
    POOLING_INTERVAL_MINS   VARCHAR(10)  NOT NULL,
    CERTIFICATE_ID          VARCHAR(100) NOT NULL,
    KNOWN_HOST_KEY          VARCHAR(100),
    IS_ACTIVE               VARCHAR(1)   NOT NULL DEFAULT 'Y',
    CREATED_BY              VARCHAR(15)  NOT NULL,
    LAST_UPDATED_BY         VARCHAR(15),
    LAST_UPDATED_DT         TIMESTAMP(6),
    USER_IDENTITY_KEY       VARCHAR(120),
    ADAPTER_NAME            VARCHAR(200),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_SO_HTTP
--------------------------------------------------------
CREATE TABLE PETPE_SO_HTTP
(
    PK_ID           VARCHAR(20)  NOT NULL,
    SUBSCRIBER_TYPE VARCHAR(5)   NOT NULL,
    SUBSCRIBER_ID   VARCHAR(20)  NOT NULL,
    PROTOCOL_TYPE   VARCHAR(10)  NOT NULL,
    IN_MAILBOX      VARCHAR(50),
    OUTBOUND_URL    VARCHAR(200),
    CERTIFICATE     VARCHAR(200) NOT NULL,
    IS_ACTIVE       VARCHAR(1)   NOT NULL DEFAULT 'Y',
    CREATED_BY      VARCHAR(15)  NOT NULL,
    LAST_UPDATED_BY VARCHAR(15),
    LAST_UPDATED_DT TIMESTAMP(6),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_SO_MAILBOX
--------------------------------------------------------
CREATE TABLE PETPE_SO_MAILBOX
(
    PK_ID                 VARCHAR(20) NOT NULL,
    SUBSCRIBER_TYPE       VARCHAR(5)  NOT NULL,
    SUBSCRIBER_ID         VARCHAR(20) NOT NULL,
    PROTOCOL_TYPE         VARCHAR(10) NOT NULL,
    IN_MAILBOX            VARCHAR(50),
    OUT_MAILBOX           VARCHAR(50),
    POOLING_INTERVAL_MINS VARCHAR(10) NOT NULL,
    IS_ACTIVE             VARCHAR(1)  NOT NULL DEFAULT 'Y',
    CREATED_BY            VARCHAR(15) NOT NULL,
    LAST_UPDATED_BY       VARCHAR(15),
    LAST_UPDATED_DT       TIMESTAMP(6),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_SO_MQ
--------------------------------------------------------
CREATE TABLE PETPE_SO_MQ
(
    PK_ID                 VARCHAR(20) NOT NULL,
    SUBSCRIBER_TYPE       VARCHAR(5)  NOT NULL,
    SUBSCRIBER_ID         VARCHAR(20) NOT NULL,
    QUEUE_MANAGER         VARCHAR(50) NOT NULL,
    QUEUE_NAME            VARCHAR(50) NOT NULL,
    POOLING_INTERVAL_MINS VARCHAR(10) NOT NULL,
    FILE_TYPE             VARCHAR(20),
    IS_ACTIVE             VARCHAR(1)  NOT NULL DEFAULT 'Y',
    CREATED_BY            VARCHAR(15) NOT NULL,
    LAST_UPDATED_BY       VARCHAR(15),
    LAST_UPDATED_DT       TIMESTAMP(6),
    HOST_NAME             VARCHAR(100),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_SO_SAP
--------------------------------------------------------
CREATE TABLE PETPE_SO_SAP
(
    PK_ID            VARCHAR(20) NOT NULL,
    SUBSCRIBER_TYPE  VARCHAR(5)  NOT NULL,
    SUBSCRIBER_ID    VARCHAR(20) NOT NULL,
    SAP_ROUTE        VARCHAR(30) NOT NULL,
    SAP_ADAPTER_NAME VARCHAR(30) NOT NULL,
    IS_ACTIVE        VARCHAR(1)  NOT NULL DEFAULT 'Y',
    CREATED_BY       VARCHAR(15) NOT NULL,
    LAST_UPDATED_BY  VARCHAR(15),
    LAST_UPDATED_DT  TIMESTAMP(6),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_SO_WEBSERVICE
--------------------------------------------------------
CREATE TABLE PETPE_SO_WEBSERVICE
(
    PK_ID           VARCHAR(20)  NOT NULL,
    SUBSCRIBER_TYPE VARCHAR(5)   NOT NULL,
    SUBSCRIBER_ID   VARCHAR(20)  NOT NULL,
    WEBSERVICE_NAME VARCHAR(100) NOT NULL,
    IS_ACTIVE       VARCHAR(1)   NOT NULL DEFAULT 'Y',
    CREATED_BY      VARCHAR(15)  NOT NULL,
    LAST_UPDATED_BY VARCHAR(15),
    LAST_UPDATED_DT TIMESTAMP(6),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;

