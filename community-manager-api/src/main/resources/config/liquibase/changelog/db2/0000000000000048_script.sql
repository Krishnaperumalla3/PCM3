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
--  DDL STATEMENT
--------------------------------------------------------

CREATE TABLE PETPE_SFG_CUSTOMPROTOCOL
(
    PK_ID                      VARCHAR(20) NOT NULL,
    SUBSCRIBER_TYPE            VARCHAR(5)  NOT NULL,
    SUBSCRIBER_ID              VARCHAR(20) NOT NULL,
    PROTOCOL_TYPE              VARCHAR(50) NOT NULL,
    IS_ACTIVE                  VARCHAR(1)  NOT NULL DEFAULT 'Y',
    APPEND_SUFFIX_TO_USERNAME  VARCHAR(1)           DEFAULT 'N',
    ASCII_ARMOR                VARCHAR(1)           DEFAULT 'N',
    AUTHENTICATION_HOST        VARCHAR(200),
    AUTHENTICATION_TYPE        VARCHAR(200),
    AUTHORIZED_USER_KEY_NAME   VARCHAR(200),
    CITY                       VARCHAR(100),
    CODE                       VARCHAR(100),
    COMMUNITY                  VARCHAR(200),
    COUNTRY_OR_REGION          VARCHAR(200),
    CUSTOM_PROTOCOL_EXTENSIONS VARCHAR(200),
    CUSTOM_PROTOCOL_NAME       VARCHAR(200),
    DOES_REQ_COMPRESSED_DATA   VARCHAR(1)           DEFAULT 'N',
    DOES_REQ_ENCRYPTED_DATA    VARCHAR(1)           DEFAULT 'N',
    DOES_REQ_SIGNED_DATA       VARCHAR(1)           DEFAULT 'N',
    DOES_USE_SSH               VARCHAR(1)           DEFAULT 'N',
    GIVEN_NAME                 VARCHAR(200),
    IS_INITIATING_CONSUMER     VARCHAR(1)           DEFAULT 'N',
    IS_INITIATING_PRODUCER     VARCHAR(1)           DEFAULT 'N',
    IS_LISTENING_CONSUMER      VARCHAR(1)           DEFAULT 'N',
    IS_LISTENING_PRODUCER      VARCHAR(1)           DEFAULT 'N',
    KEY_ENABLED                VARCHAR(1)           DEFAULT 'N',
    MAILBOX                    VARCHAR(200),
    PASSWORD                   VARCHAR(50),
    PASSWORD_POLICY            VARCHAR(200),
    POLLING_INTERVAL           VARCHAR(10),
    POSTAL_CODE                VARCHAR(100),
    PUBLIC_KEY_ID              VARCHAR(200),
    REMOTE_FILE_PATTERN        VARCHAR(100),
    SESSION_TIMEOUT            BIGINT,
    STATE_OR_PROVINCE          VARCHAR(100),
    SURNAME                    VARCHAR(100),
    TEXT_MODE                  VARCHAR(100),
    TIME_ZONE                  VARCHAR(100),
    USE_GLOBAL_MAILBOX         VARCHAR(100),
    USERNAME                   VARCHAR(200),
    CREATED_BY                 VARCHAR(15) NOT NULL,
    LAST_UPDATED_BY            VARCHAR(15),
    LAST_UPDATED_DT            TIMESTAMP(6),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;

