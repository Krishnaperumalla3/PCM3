/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc.
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
--  DDL STATEMENTS
--------------------------------------------------------

CREATE TABLE PETPE_SFG_CUSTOMPROTOCOL
(
    PK_ID                      VARCHAR2(20 BYTE)            NOT NULL ENABLE,
    SUBSCRIBER_TYPE            VARCHAR2(5 BYTE)             NOT NULL ENABLE,
    SUBSCRIBER_ID              VARCHAR2(20 BYTE)            NOT NULL ENABLE,
    PROTOCOL_TYPE              VARCHAR2(50 BYTE)            NOT NULL ENABLE,
    IS_ACTIVE                  VARCHAR2(1 BYTE) DEFAULT 'Y' NOT NULL ENABLE,
    APPEND_SUFFIX_TO_USERNAME  VARCHAR2(1 BYTE) DEFAULT 'N',
    ASCII_ARMOR                VARCHAR2(1 BYTE) DEFAULT 'N',
    AUTHENTICATION_HOST        VARCHAR2(200 BYTE),
    AUTHENTICATION_TYPE        VARCHAR2(200 BYTE),
    AUTHORIZED_USER_KEY_NAME   VARCHAR2(200 BYTE),
    CITY                       VARCHAR2(100 BYTE),
    CODE                       VARCHAR2(100 BYTE),
    COMMUNITY                  VARCHAR2(200 BYTE),
    COUNTRY_OR_REGION          VARCHAR2(200 BYTE),
    CUSTOM_PROTOCOL_EXTENSIONS VARCHAR2(200 BYTE),
    CUSTOM_PROTOCOL_NAME       VARCHAR2(200 BYTE),
    DOES_REQ_COMPRESSED_DATA   VARCHAR2(1 BYTE) DEFAULT 'N',
    DOES_REQ_ENCRYPTED_DATA    VARCHAR2(1 BYTE) DEFAULT 'N',
    DOES_REQ_SIGNED_DATA       VARCHAR2(1 BYTE) DEFAULT 'N',
    DOES_USE_SSH               VARCHAR2(1 BYTE) DEFAULT 'N',
    GIVEN_NAME                 VARCHAR2(200 BYTE),
    IS_INITIATING_CONSUMER     VARCHAR2(1 BYTE) DEFAULT 'N',
    IS_INITIATING_PRODUCER     VARCHAR2(1 BYTE) DEFAULT 'N',
    IS_LISTENING_CONSUMER      VARCHAR2(1 BYTE) DEFAULT 'N',
    IS_LISTENING_PRODUCER      VARCHAR2(1 BYTE) DEFAULT 'N',
    KEY_ENABLED                VARCHAR2(1 BYTE) DEFAULT 'N',
    MAILBOX                    VARCHAR2(200 BYTE),
    PASSWORD                   VARCHAR2(50 BYTE),
    PASSWORD_POLICY            VARCHAR2(200 BYTE),
    POLLING_INTERVAL           VARCHAR2(10 BYTE),
    POSTAL_CODE                VARCHAR2(100 BYTE),
    PUBLIC_KEY_ID              VARCHAR2(200 BYTE),
    REMOTE_FILE_PATTERN        VARCHAR2(100 BYTE),
    SESSION_TIMEOUT            NUMBER(1, 10),
    STATE_OR_PROVINCE          VARCHAR2(100 BYTE),
    SURNAME                    VARCHAR2(100 BYTE),
    TEXT_MODE                  VARCHAR2(100 BYTE),
    TIME_ZONE                  VARCHAR2(100 BYTE),
    USE_GLOBAL_MAILBOX         VARCHAR2(100 BYTE),
    USERNAME                   VARCHAR2(200 BYTE),
    CREATED_BY                 VARCHAR2(15 BYTE)            NOT NULL ENABLE,
    LAST_UPDATED_BY            VARCHAR2(15 BYTE),
    LAST_UPDATED_DT            TIMESTAMP(6),
    PRIMARY KEY (PK_ID)
);
