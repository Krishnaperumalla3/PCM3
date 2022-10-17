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
--  DDL for Table PETPE_SO_USERS
--------------------------------------------------------
CREATE TABLE PETPE_SO_USERS
(
    USERID          VARCHAR(30)  NOT NULL,
    PASSWORD        VARCHAR(100) NOT NULL,
    ROLE            VARCHAR(50)  NOT NULL DEFAULT 'user',
    FIRST_NAME      VARCHAR(50)  NOT NULL,
    MIDDLE_NAME     VARCHAR(50),
    LAST_NAME       VARCHAR(50)  NOT NULL,
    EMAIL           VARCHAR(100) NOT NULL,
    PHONE           VARCHAR(15)  NOT NULL,
    STATUS          VARCHAR(1)   NOT NULL DEFAULT 'Y',
    ONBOARDING_REF  VARCHAR(20),
    PARTNER_REF     CLOB,
    GROUP_REF       CLOB,
    CREATED_DATE    TIMESTAMP(6) NOT NULL,
    LAST_UPDATED_BY VARCHAR(15),
    LAST_UPDATED_DT TIMESTAMP(6),
    IS_B2B_USER     VARCHAR(1),
    IS_FAX_USER     VARCHAR(1),
    PRIMARY KEY (USERID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_SO_TPUSERS
--------------------------------------------------------
CREATE TABLE PETPE_SO_TPUSERS
(
    USERID          VARCHAR(30)  NOT NULL,
    PARTNER_LIST    CLOB,
    GROUP_LIST      CLOB,
    CREATED_DATE    TIMESTAMP(6) NOT NULL,
    LAST_UPDATED_BY VARCHAR(15),
    LAST_UPDATED_DT TIMESTAMP(6),
    PRIMARY KEY (USERID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_SO_TPGROUPS
--------------------------------------------------------
CREATE TABLE PETPE_SO_TPGROUPS
(
    PK_ID           VARCHAR(20)  NOT NULL,
    GROUPNAME       VARCHAR(30)  NOT NULL,
    PARTNER_LIST    CLOB,
    CREATED_DATE    TIMESTAMP(6) NOT NULL,
    LAST_UPDATED_BY VARCHAR(15),
    LAST_UPDATED_DT TIMESTAMP(6),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_SO_ONBOARDING
--------------------------------------------------------
CREATE TABLE PETPE_SO_ONBOARDING
(
    PK_ID           VARCHAR(20)                         NOT NULL,
    EMAIL           VARCHAR(250)                        NOT NULL,
    TP_NAME         VARCHAR(30)                         NOT NULL,
    PROTOCOL        VARCHAR(10)                         NOT NULL,
    PROTOCOL_REF    VARCHAR(20)                         NOT NULL,
    VALIDATION_KEY  VARCHAR(30)                         NOT NULL,
    STATUS          VARCHAR(20) DEFAULT 'InfoRequested' NOT NULL,
    CREATED_BY      VARCHAR(15)                         NOT NULL,
    CREATED_DATE    TIMESTAMP(6)                        NOT NULL,
    LAST_UPDATED_BY VARCHAR(15),
    LAST_UPDATED_DT TIMESTAMP(6),
    GROUP_REF       VARCHAR(20),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;

