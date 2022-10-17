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
--  DDL for Table PETPE_AS2
--------------------------------------------------------
CREATE TABLE PETPE_AS2
(
    PK_ID                        VARCHAR(20)              NOT NULL,
    SUBSCRIBER_TYPE              VARCHAR(5),
    SUBSCRIBER_ID                VARCHAR(20),
    AS2_IDENTIFIER               VARCHAR(50)              NOT NULL,
    URL                          VARCHAR(250),
    SENDER_ID                    VARCHAR(50),
    SENDER_QUALIFIER             VARCHAR(10),
    PAYLOAD_SECURITY             VARCHAR(30)  DEFAULT 'Plain Text',
    PAYLOAD_ENCRYPTION_ALGORITHM VARCHAR(50)  DEFAULT 'Triple DES 168 CBC with PKCS5 padding',
    IS_HTTPS                     VARCHAR(10)  DEFAULT 'N',
    USERNAME                     VARCHAR(50),
    PASSWORD                     VARCHAR(50),
    SSL_TYPE                     VARCHAR(250) DEFAULT 'SSL_NONE',
    ENCRYPTION_ALGORITHM         VARCHAR(50)  DEFAULT 'Triple DES 168 CBC with PKCS5 padding',
    SIGNATURE_ALGORITHM          VARCHAR(10)  DEFAULT 'MD5',
    IS_MDN_REQUIRED              VARCHAR(10)  DEFAULT 'Y',
    MDN_TYPE                     VARCHAR(15)  DEFAULT '0',
    MDN_ENCRYPTION               VARCHAR(10)  DEFAULT 'NONE',
    RECEIPT_TO_ADDRESS           VARCHAR(50),
    IS_ACTIVE                    VARCHAR(1)   DEFAULT 'Y' NOT NULL,
    IS_HUB_INFO                  VARCHAR(1)   DEFAULT 'N',
    CREATED_BY                   VARCHAR(15),
    LAST_UPDATED_BY              VARCHAR(15),
    LAST_UPDATED_DT              TIMESTAMP(6),
    ORG_AS2_ID                   VARCHAR(256),
    PARENT_MBX                   VARCHAR(256),
    HTTP_CLIENT_ADAPTER          VARCHAR(256),
    ERROR_DIR                    VARCHAR(256),
    IDENTITY_NAME                VARCHAR(100),
    PROFILE_NAME                 VARCHAR(100),
    KEY_CERT_PASSPHRASE          VARCHAR(100),
    CIPHER_STRENGTH              VARCHAR(100) DEFAULT 'ALL',
    PAYLOAD_TYPE                 VARCHAR(50)  DEFAULT '0',
    MIME_TYPE                    VARCHAR(50)  DEFAULT 'Application',
    MIME_SUB_TYPE                VARCHAR(50)  DEFAULT 'plain',
    COMPRESS_DATA                VARCHAR(5)   DEFAULT 'Y',
    EMAIL_ADDRESS                VARCHAR(150),
    EMAIL_HOST                   VARCHAR(50),
    EMAIL_PORT                   VARCHAR(10),
    RESPONSE_TIMEOUT             VARCHAR(15)  DEFAULT '5',
    KEY_CERT                     VARCHAR(255),
    KEY_CERT_PATH                VARCHAR(255),
    CA_CERT                      VARCHAR(255),
    CA_CERT_PATH                 VARCHAR(255),
    EXCHG_CERT                   VARCHAR(255),
    EXCHG_CERT_PATH              VARCHAR(255),
    SIGNING_CERT                 VARCHAR(255),
    SIGNING_CERT_PATH            VARCHAR(255),
    SYS_CERT                     VARCHAR(256),
    RETRY_INTERVAL               VARCHAR(10)  DEFAULT '5',
    NO_OF_RETRIES                VARCHAR(10)  DEFAULT '5',
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_FILESYSTEM
--------------------------------------------------------
CREATE TABLE PETPE_FILESYSTEM
(
    PK_ID                   VARCHAR(20)            NOT NULL,
    SUBSCRIBER_TYPE         VARCHAR(5)             NOT NULL,
    SUBSCRIBER_ID           VARCHAR(20)            NOT NULL,
    FSA_ADAPTER             VARCHAR(100)           NOT NULL,
    IN_DIRECTORY            VARCHAR(200),
    OUT_DIRECTORY           VARCHAR(200),
    FILE_TYPE               VARCHAR(50),
    DELETE_AFTER_COLLECTION VARCHAR(1) DEFAULT 'N' NOT NULL,
    POOLING_INTERVAL_MINS   VARCHAR(10)            NOT NULL,
    IS_ACTIVE               VARCHAR(1) DEFAULT 'Y' NOT NULL,
    IS_HUB_INFO             VARCHAR(1) DEFAULT 'N' NOT NULL,
    CREATED_BY              VARCHAR(15)            NOT NULL,
    LAST_UPDATED_BY         VARCHAR(15),
    LAST_UPDATED_DT         TIMESTAMP(6),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_FTP
--------------------------------------------------------
CREATE TABLE PETPE_FTP
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
    IS_HUB_INFO             VARCHAR(1)   NOT NULL DEFAULT 'N',
    CREATED_BY              VARCHAR(15)  NOT NULL,
    LAST_UPDATED_BY         VARCHAR(15),
    LAST_UPDATED_DT         TIMESTAMP(6),
    USER_IDENTITY_KEY       VARCHAR(120),
    ADAPTER_NAME            VARCHAR(200) NOT NULL,
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_HTTP
--------------------------------------------------------
CREATE TABLE PETPE_HTTP
(
    PK_ID           VARCHAR(20)  NOT NULL,
    SUBSCRIBER_TYPE VARCHAR(5)   NOT NULL,
    SUBSCRIBER_ID   VARCHAR(20)  NOT NULL,
    PROTOCOL_TYPE   VARCHAR(10)  NOT NULL,
    IN_MAILBOX      VARCHAR(50)  NOT NULL,
    OUTBOUND_URL    VARCHAR(200),
    CERTIFICATE     VARCHAR(200) NOT NULL,
    IS_ACTIVE       VARCHAR(1)   NOT NULL DEFAULT 'Y',
    IS_HUB_INFO     VARCHAR(1)   NOT NULL DEFAULT 'N',
    CREATED_BY      VARCHAR(15)  NOT NULL,
    LAST_UPDATED_BY VARCHAR(15),
    LAST_UPDATED_DT TIMESTAMP(6),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_MAILBOX
--------------------------------------------------------
CREATE TABLE PETPE_MAILBOX
(
    PK_ID                 VARCHAR(20) NOT NULL,
    SUBSCRIBER_TYPE       VARCHAR(5)  NOT NULL,
    SUBSCRIBER_ID         VARCHAR(20) NOT NULL,
    PROTOCOL_TYPE         VARCHAR(10) NOT NULL,
    IN_MAILBOX            VARCHAR(200),
    OUT_MAILBOX           VARCHAR(200),
    POOLING_INTERVAL_MINS VARCHAR(10) NOT NULL,
    IS_ACTIVE             VARCHAR(1)  NOT NULL DEFAULT 'Y',
    IS_HUB_INFO           VARCHAR(1)  NOT NULL DEFAULT 'N',
    CREATED_BY            VARCHAR(15) NOT NULL,
    LAST_UPDATED_BY       VARCHAR(15),
    LAST_UPDATED_DT       TIMESTAMP(6),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_MQ
--------------------------------------------------------
CREATE TABLE PETPE_MQ
(
    PK_ID                 VARCHAR(20) NOT NULL,
    SUBSCRIBER_TYPE       VARCHAR(5)  NOT NULL,
    SUBSCRIBER_ID         VARCHAR(20) NOT NULL,
    QUEUE_MANAGER         VARCHAR(50) NOT NULL,
    QUEUE_NAME            VARCHAR(50) NOT NULL,
    POOLING_INTERVAL_MINS VARCHAR(10) NOT NULL,
    FILE_TYPE             VARCHAR(20),
    IS_ACTIVE             VARCHAR(1)  NOT NULL DEFAULT 'Y',
    IS_HUB_INFO           VARCHAR(1)  NOT NULL DEFAULT 'N',
    CREATED_BY            VARCHAR(15) NOT NULL,
    LAST_UPDATED_BY       VARCHAR(15),
    LAST_UPDATED_DT       TIMESTAMP(6),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_SAP
--------------------------------------------------------
CREATE TABLE PETPE_SAP
(
    PK_ID            VARCHAR(20) NOT NULL,
    SUBSCRIBER_TYPE  VARCHAR(5)  NOT NULL,
    SUBSCRIBER_ID    VARCHAR(20) NOT NULL,
    SAP_ROUTE        VARCHAR(30) NOT NULL,
    SAP_ADAPTER_NAME VARCHAR(30) NOT NULL,
    IS_ACTIVE        VARCHAR(1)  NOT NULL DEFAULT 'Y',
    IS_HUB_INFO      VARCHAR(1)  NOT NULL DEFAULT 'N',
    CREATED_BY       VARCHAR(15) NOT NULL,
    LAST_UPDATED_BY  VARCHAR(15),
    LAST_UPDATED_DT  TIMESTAMP(6),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_WEBSERVICE
--------------------------------------------------------
CREATE TABLE PETPE_WEBSERVICE
(
    PK_ID           VARCHAR(20)  NOT NULL,
    SUBSCRIBER_TYPE VARCHAR(5)   NOT NULL,
    SUBSCRIBER_ID   VARCHAR(20)  NOT NULL,
    WEBSERVICE_NAME VARCHAR(100) NOT NULL,
    IS_ACTIVE       VARCHAR(1)   NOT NULL DEFAULT 'Y',
    IS_HUB_INFO     VARCHAR(1)   NOT NULL DEFAULT 'N',
    CREATED_BY      VARCHAR(15)  NOT NULL,
    LAST_UPDATED_BY VARCHAR(15),
    LAST_UPDATED_DT TIMESTAMP(6),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_SFG_FTP
--------------------------------------------------------
CREATE TABLE PETPE_SFG_FTP
(
    PK_ID                   VARCHAR(20)            NOT NULL,
    SUBSCRIBER_TYPE         VARCHAR(5)             NOT NULL,
    SUBSCRIBER_ID           VARCHAR(20)            NOT NULL,
    PROTOCOL_TYPE           VARCHAR(10)            NOT NULL,
    PROFILE_ID              VARCHAR(100),
    USER_ID                 VARCHAR(200),
    PASSWORD                VARCHAR(50),
    IN_DIRECTORY            VARCHAR(200),
    OUT_DIRECTORY           VARCHAR(200),
    FILE_TYPE               VARCHAR(50),
    DELETE_AFTER_COLLECTION VARCHAR(1) DEFAULT 'N',
    TRANSFER_TYPE           VARCHAR(10),
    POOLING_INTERVAL_MINS   VARCHAR(10)            NOT NULL,
    CERTIFICATE_ID          VARCHAR(100),
    KNOWN_HOST_KEY          VARCHAR(100),
    IS_ACTIVE               VARCHAR(1) DEFAULT 'Y' NOT NULL,
    IS_HUB_INFO             VARCHAR(1) DEFAULT 'N',
    CREATED_BY              VARCHAR(15)            NOT NULL,
    LAST_UPDATED_BY         VARCHAR(15),
    LAST_UPDATED_DT         TIMESTAMP(6),
    USER_IDENTITY_KEY       VARCHAR(120),
    ADAPTER_NAME            VARCHAR(200),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_EC
--------------------------------------------------------
CREATE TABLE PETPE_EC
(
    PK_ID           VARCHAR(20)            NOT NULL,
    SUBSCRIBER_TYPE VARCHAR(5)             NOT NULL,
    SUBSCRIBER_ID   VARCHAR(20)            NOT NULL,
    EC_PROTOCOL     VARCHAR(50),
    EC_PROTOCOL_REF VARCHAR(50),
    IS_ACTIVE       VARCHAR(1) DEFAULT 'Y' NOT NULL,
    IS_HUB_INFO     VARCHAR(1) DEFAULT 'N' NOT NULL,
    CREATED_BY      VARCHAR(15)            NOT NULL,
    LAST_UPDATED_BY VARCHAR(15),
    LAST_UPDATED_DT TIMESTAMP(6),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;

-------------------------------------------------------
--  DDL for Table PETPE_SMTP
--------------------------------------------------------
CREATE TABLE PETPE_SMTP
(
    PK_ID                  VARCHAR(20) NOT NULL,
    SUBSCRIBER_TYPE        VARCHAR(5)  NOT NULL,
    SUBSCRIBER_ID          VARCHAR(20) NOT NULL,
    NAME                   VARCHAR(100),
    ACCESS_PROTOCOL        VARCHAR(50),
    MAIL_SERVER            VARCHAR(50),
    MAIL_SERVER_PORT       VARCHAR(50),
    USER_NAME              VARCHAR(100),
    PASSWORD               VARCHAR(50),
    CONNECTION_RETRIES     VARCHAR(50),
    RETRY_INTERVAL         VARCHAR(50),
    MAX_MSGS_SESSION       VARCHAR(50),
    REMOVE_INBOX_MAIL_MSGS VARCHAR(100),
    SSL                    VARCHAR(100),
    KEY_CERT_PASS_PHRASE   VARCHAR(50),
    CIPHER_STRENGTH        VARCHAR(50),
    KEY_CERT               VARCHAR(50),
    CA_CERTIFICATES        VARCHAR(50),
    URI_NAME               VARCHAR(50),
    POOLING_INTERVAL       VARCHAR(50),
    CREATED_BY             VARCHAR(15),
    LAST_UPDATED_BY        VARCHAR(15),
    LAST_UPDATED_DT        TIMESTAMP(6),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;
