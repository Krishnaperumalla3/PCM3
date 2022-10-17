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
--  DDL for Table PETPE_TRANSFERINFO
--------------------------------------------------------
CREATE TABLE PETPE_TRANSFERINFO
(
    SEQID                BIGINT NOT NULL,
    FILEARRIVED          TIMESTAMP(6),
    FLOWINOUT            VARCHAR(120),
    TYPEOFTRANSFER       VARCHAR(100),
    SENDERID             VARCHAR(100),
    RECIVERID            VARCHAR(100),
    APPLICATION          VARCHAR(100),
    APPINTSTATUS         VARCHAR(100),
    PARTNERACKSTATUS     VARCHAR(100),
    SRCPROTOCOL          VARCHAR(100),
    SRCFILENAME          VARCHAR(100),
    SRCARCFILELOC        VARCHAR(500),
    DESTFILENAME         VARCHAR(100),
    DESTARCFILELOC       VARCHAR(500),
    DESTPROTOCOL         VARCHAR(100),
    DOCTYPE              VARCHAR(100),
    PICKBPID             VARCHAR(100),
    COREBPID             VARCHAR(100),
    DELIVERYBPID         VARCHAR(100),
    STATUS               VARCHAR(100),
    ERRORSTATUS          VARCHAR(100),
    ADVERRORSTATUS       VARCHAR(300),
    PARTNER              VARCHAR(100),
    DOCTRANS             VARCHAR(15),
    CORRELATION_VALUE_1  CLOB,
    CORRELATION_VALUE_2  CLOB,
    CORRELATION_VALUE_3  CLOB,
    CORRELATION_VALUE_4  CLOB,
    CORRELATION_VALUE_5  CLOB,
    CORRELATION_VALUE_6  CLOB,
    CORRELATION_VALUE_7  CLOB,
    CORRELATION_VALUE_8  CLOB,
    CORRELATION_VALUE_9  CLOB,
    CORRELATION_VALUE_10 CLOB,
    PRIMARY KEY (SEQID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_TRANSINFOD
--------------------------------------------------------
CREATE TABLE PETPE_TRANSINFOD
(
    PK_ID    VARCHAR(50) NOT NULL,
    BPID     VARCHAR(256),
    RULENAME VARCHAR(256),
    BPNAME   VARCHAR(256),
    DETAILS  VARCHAR(4000),
    SEQUENCE VARCHAR(20),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;
