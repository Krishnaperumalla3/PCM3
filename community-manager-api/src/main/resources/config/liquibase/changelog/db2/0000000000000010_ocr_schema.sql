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
--  DDL for Table PETPE_OCR_BR
--------------------------------------------------------
CREATE TABLE PETPE_OCR_BR
(
    PK_ID           VARCHAR(20)  NOT NULL,
    BR_NAME         VARCHAR(100) NOT NULL,
    LOGIC           VARCHAR(100) NOT NULL,
    CREATED_BY      VARCHAR(15)  NOT NULL,
    CREATED_DT      TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
    LAST_UPDATED_BY VARCHAR(15),
    LAST_UPDATED_DT TIMESTAMP(6),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_OCR_DIGITAL_PARTNER
--------------------------------------------------------
CREATE TABLE PETPE_OCR_DIGITAL_PARTNER
(
    PARTNER_ID             VARCHAR(20)  NOT NULL,
    PARTNER_NAME           VARCHAR(100) NOT NULL,
    EMAIL                  VARCHAR(250),
    PHONE                  VARCHAR(15),
    ENRICHMENT_REF         VARCHAR(200),
    BUSINESS_RULES_REF     VARCHAR(200),
    CREATED_BY             VARCHAR(15)  NOT NULL,
    CREATED_DT             TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
    LAST_UPDATED_BY        VARCHAR(15),
    LAST_UPDATED_DT        TIMESTAMP(6),
    APPLY_DEFAULT_ROUNTING VARCHAR(1)   DEFAULT 'N',
    STATUS                 VARCHAR(20)  DEFAULT 'ACTIVE',
    PRIMARY KEY (PARTNER_ID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_OCR_EMAIL_ATTACHMENTS
--------------------------------------------------------
CREATE TABLE PETPE_OCR_EMAIL_ATTACHMENTS
(
    PK_ID                VARCHAR(50) NOT NULL,
    EMAIL_REF_ID         VARCHAR(50) NOT NULL,
    EMAIL_CONTENT_REF_ID VARCHAR(50),
    EMAIL_UID            BIGINT,
    EMAIL_TYPE           VARCHAR(10),
    ATTACHMENT_PATH      VARCHAR(1000),
    ATTACHMENT_NAME      VARCHAR(200),
    ACTIVITY_DT          TIMESTAMP(6),
    OCR_PROCESSING       VARCHAR(1) DEFAULT 'N',
    EMAIL_INDEX          BIGINT     DEFAULT 0,
    ATTACHMENT_TYPE      VARCHAR(50),
    CONTENT_ID           VARCHAR(200),
    PRIMARY KEY (PK_ID, EMAIL_REF_ID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_OCR_EMAIL_CONTENT
--------------------------------------------------------
CREATE TABLE PETPE_OCR_EMAIL_CONTENT
(
    PK_ID           VARCHAR(50) NOT NULL,
    EMAIL_REF_ID    VARCHAR(50) NOT NULL,
    EMAIL_UID       BIGINT,
    EMAIL_TYPE      VARCHAR(10),
    EMAIL_FROM      VARCHAR(320),
    EMAIL_CONTENT   CLOB,
    CONTENT_TYPE    VARCHAR(255),
    ATTACHMENT_PATH VARCHAR(1000),
    ACTIVITY_DT     TIMESTAMP(6),
    EMAIL_CC        CLOB,
    EMAIL_BCC       CLOB,
    EMAIL_TO        CLOB,
    EMAIL_SUBJECT   CLOB,
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_OCR_EMAIL_SYNC_HISTORY
--------------------------------------------------------
CREATE TABLE PETPE_OCR_EMAIL_SYNC_HISTORY

(
    PK_ID               VARCHAR(20) NOT NULL,
    ACTIVITY_DT         TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
    ACTIVITY            CLOB,
    LAST_SYNC_UNTIL_UID BIGINT       DEFAULT 0,
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_OCR_ENRICHMENT
--------------------------------------------------------
CREATE TABLE PETPE_OCR_ENRICHMENT
(
    PK_ID           VARCHAR(20)  NOT NULL,
    ENRICHMENT_NAME VARCHAR(100) NOT NULL,
    LOGIC           VARCHAR(100) NOT NULL,
    CREATED_BY      VARCHAR(15)  NOT NULL,
    CREATED_DT      TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
    LAST_UPDATED_BY VARCHAR(15),
    LAST_UPDATED_DT TIMESTAMP(6),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_OCR_PO
--------------------------------------------------------
CREATE TABLE PETPE_OCR_PO
(
    PK_ID                 VARCHAR(50) NOT NULL,
    QUEUE_REF             VARCHAR(300),
    QUEUE_REF_NAME        VARCHAR(700),
    PARTNER_REF           VARCHAR(50),
    PARTNER_REF_NAME      VARCHAR(1000),
    PO_ADDRESS_LINE1      VARCHAR(1000),
    PO_CITY               VARCHAR(1000),
    PO_STATE              VARCHAR(1000),
    PO_COUNTRY            VARCHAR(1000),
    PO_ZIP                VARCHAR(1000),
    PO_TOTAL              DECIMAL(19, 4),
    PO_NUMBER             VARCHAR(30),
    PO_ORDLINE_COMMENTS   VARCHAR(500),
    ORDER_DATE            VARCHAR(50),
    REQUESTED_SHIP_DATE   VARCHAR(35),
    SOLD_TO_NAME          VARCHAR(200),
    SOLD_TO_ACC_NO        VARCHAR(50),
    SHIP_VIA_CARRIER      VARCHAR(100),
    SHIP_VIA_ACCT_NO      VARCHAR(100),
    SHIP_VIA_TERMS        VARCHAR(2000),
    SHIP_VIA2_CARRIER     VARCHAR(100),
    SHIP_VIA2_ACCT_NO     VARCHAR(100),
    SHIP_VIA2_TERMS       VARCHAR(200),
    SHIP_VIA3_CARRIER     VARCHAR(100),
    SHIP_VIA3_ACCT_NO     VARCHAR(100),
    SHIP_VIA3_TERMS       VARCHAR(200),
    SPECIAL_INSTRNS       VARCHAR(200),
    SHIPPING_INSTRNS      VARCHAR(500),
    SHIPMENT_REF          VARCHAR(500),
    CONTACT_NAME          VARCHAR(100),
    CONTACT_PHONE         VARCHAR(15),
    CONTACT_FAX           VARCHAR(15),
    CONTACT_EMAIL         VARCHAR(250),
    CONTACT_TRK_EMAIL     VARCHAR(250),
    CONTACT_ACK_EMAIL     VARCHAR(250),
    PO_CONFIDENCE_LEVEL   BIGINT,
    OL_CONFIDENCE_LEVEL   BIGINT,
    SOURCE_FILE           VARCHAR(500),
    TEMPLATE_LIB          VARCHAR(50),
    OCR_SKIP_PAGE         BIGINT,
    PO_MATCHED_TEMPLATE   VARCHAR(50),
    OL_MATCHED_TEMPLATE   VARCHAR(50),
    PO_STATUS             VARCHAR(20) NOT NULL,
    PO_STATUS_COMMENTS    VARCHAR(200),
    PO_STATUS_STEP        VARCHAR(100),
    PO_REVIEWER           VARCHAR(50),
    PO_REVIEWER_COMMENTS  VARCHAR(500),
    DD_TYPE               VARCHAR(10),
    EMAIL_RECEIVED_DT     TIMESTAMP(6),
    EMAIL_UID             BIGINT,
    EMAIL_FROM            VARCHAR(320),
    EMAIL_HAVE_ATTACHMENT VARCHAR(1)   DEFAULT 'N',
    EMAIL_HAVE_FAX        VARCHAR(1)   DEFAULT 'N',
    EMAIL_REPLIED         VARCHAR(1)   DEFAULT 'N',
    EMAIL_FORWARDED       VARCHAR(1)   DEFAULT 'N',
    EMAIL_ARCHIVED        VARCHAR(1)   DEFAULT 'N',
    SUBMITTED_TO_ERP      VARCHAR(1)   DEFAULT 'N',
    EMAIL_STATUS_COMMENTS VARCHAR(200),
    EMAIL_STATUS_STEP     VARCHAR(100),
    ERP_SUBMITTED_DT      TIMESTAMP(6),
    CREATED_BY            VARCHAR(15),
    CREATED_DT            TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
    LAST_UPDATED_BY       VARCHAR(15),
    LAST_UPDATED_DT       TIMESTAMP(6),
    SO_NUMBER             VARCHAR(30),
    SHIP_DATE             VARCHAR(35),
    IS_ARCHIVED           VARCHAR(1)   DEFAULT 'N',
    PO_STATUS_PREVIOUS    VARCHAR(20),
    EVENT_LOCKED          VARCHAR(1)   DEFAULT 'N',
    OCR_PARTNER_NAME      VARCHAR(50),
    EMAIL_SYNC_FAIL_MSG   VARCHAR(2000),
    EMAIL_TO              CLOB,
    EMAIL_SUBJECT         CLOB,
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;
--------------------------------------------------------
--  DDL for Table PETPE_OCR_PO_ADDRESS
--------------------------------------------------------
CREATE TABLE PETPE_OCR_PO_ADDRESS
(
    PK_ID           VARCHAR(50) NOT NULL,
    PO_REF_ID       VARCHAR(50) NOT NULL,
    PO_NUMBER       VARCHAR(30),
    NAME            VARCHAR(200),
    ADDRESS_TYPE    VARCHAR(15),
    ADDRESS_LINE1   VARCHAR(100),
    ADDRESS_LINE2   VARCHAR(100),
    CITY            VARCHAR(100),
    ADDR_STATE      VARCHAR(50),
    POSTAL_CODE     VARCHAR(15),
    COUNTRY         VARCHAR(50),
    CREATED_BY      VARCHAR(15) NOT NULL,
    CREATED_DT      TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
    LAST_UPDATED_BY VARCHAR(15),
    LAST_UPDATED_DT TIMESTAMP(6),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_OCR_PO_ADDRESS_EXT
--------------------------------------------------------
CREATE TABLE PETPE_OCR_PO_ADDRESS_EXT
(
    PK_ID           VARCHAR(50) NOT NULL,
    PO_REF_ID       VARCHAR(50) NOT NULL,
    PO_NUMBER       VARCHAR(30),
    NAME            VARCHAR(200),
    ADDRESS_TYPE    VARCHAR(15),
    ADDRESS_LINE1   VARCHAR(100),
    ADDRESS_LINE2   VARCHAR(100),
    CITY            VARCHAR(100),
    ADDR_STATE      VARCHAR(50),
    POSTAL_CODE     VARCHAR(15),
    COUNTRY         VARCHAR(50),
    CREATED_BY      VARCHAR(15) NOT NULL,
    CREATED_DT      TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
    LAST_UPDATED_BY VARCHAR(15),
    LAST_UPDATED_DT TIMESTAMP(6),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_OCR_PO_EXT
--------------------------------------------------------
CREATE TABLE PETPE_OCR_PO_EXT
(
    PK_ID               VARCHAR(50)    NOT NULL,
    PO_ADDRESS_LINE1    VARCHAR(100),
    PO_CITY             VARCHAR(100),
    PO_STATE            VARCHAR(100)   NOT NULL,
    PO_COUNTRY          VARCHAR(50),
    PO_ZIP              VARCHAR(15),
    PO_NUMBER           VARCHAR(30),
    PO_TOTAL            DECIMAL(19, 4) NOT NULL DEFAULT 0.0,
    PO_ORDLINE_COMMENTS VARCHAR(500),
    ORDER_DATE          VARCHAR(50),
    REQUESTED_SHIP_DATE VARCHAR(35),
    SOLD_TO_NAME        VARCHAR(200),
    SOLD_TO_ACC_NO      VARCHAR(50),
    SHIP_VIA_CARRIER    VARCHAR(100),
    SHIP_VIA_ACCT_NO    VARCHAR(100),
    SHIP_VIA_TERMS      VARCHAR(200),
    SPECIAL_INSTRNS     VARCHAR(200),
    SHIPPING_INSTRNS    VARCHAR(500),
    SHIPMENT_REF        VARCHAR(500),
    CONTACT_NAME        VARCHAR(100),
    CONTACT_PHONE       VARCHAR(15),
    CONTACT_FAX         VARCHAR(15),
    CONTACT_EMAIL       VARCHAR(250),
    CONTACT_TRK_EMAIL   VARCHAR(250),
    CONTACT_ACK_EMAIL   VARCHAR(250),
    MEMO_TO             VARCHAR(150),
    MEMO_FROM           VARCHAR(150),
    MEMO_COMPANY        VARCHAR(150),
    MEMO_SUBJECT        VARCHAR(250),
    MEMO_FAX            VARCHAR(150),
    MEMO_DESC           VARCHAR(500),
    PO_CONFIDENCE_LEVEL BIGINT,
    OL_CONFIDENCE_LEVEL BIGINT,
    SOURCE_FILE         VARCHAR(500),
    TEMPLATE_LIB        VARCHAR(50),
    OCR_SKIP_PAGE       BIGINT         NOT NULL DEFAULT -1,
    PO_MATCHED_TEMPLATE VARCHAR(50),
    OL_MATCHED_TEMPLATE VARCHAR(50),
    PO_STATUS           VARCHAR(20),
    PO_STATUS_COMMENTS  VARCHAR(200),
    PO_STATUS_STEP      VARCHAR(100),
    CREATED_BY          VARCHAR(15)    NOT NULL,
    CREATED_DT          TIMESTAMP(6)            DEFAULT CURRENT_TIMESTAMP,
    LAST_UPDATED_BY     VARCHAR(15),
    LAST_UPDATED_DT     TIMESTAMP(6),
    SHIP_VIA2_CARRIER   VARCHAR(100),
    SHIP_VIA2_ACCT_NO   VARCHAR(100),
    SHIP_VIA2_TERMS     VARCHAR(200),
    SHIP_VIA3_CARRIER   VARCHAR(100),
    SHIP_VIA3_ACCT_NO   VARCHAR(100),
    SHIP_VIA3_TERMS     VARCHAR(200),
    SO_NUMBER           VARCHAR(30),
    SHIP_DATE           VARCHAR(35),
    OCR_PARTNER_NAME    VARCHAR(50),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_OCR_PO_ORDLINE
--------------------------------------------------------
CREATE TABLE PETPE_OCR_PO_ORDLINE
(
    PK_ID            VARCHAR(50) NOT NULL,
    PO_REF_ID        VARCHAR(50) NOT NULL,
    PO_NUMBER        VARCHAR(30),
    LINE_NUMBER      VARCHAR(10),
    ORD_QUANTITY     VARCHAR(50),
    ORD_UOM          VARCHAR(50),
    ITEM_NUMBER      VARCHAR(100),
    DESCRIPTION      VARCHAR(500),
    CUST_ITEM_NUMBER VARCHAR(100),
    UNIT_PRICE       VARCHAR(50),
    PRICE_UOM        VARCHAR(50),
    EXTENDED_PRICE   VARCHAR(50),
    SPA              VARCHAR(50),
    REQ_SHIP_DATE    VARCHAR(35),
    SHIPPING_METHOD  VARCHAR(50),
    ACCT             VARCHAR(50),
    SHIPPING_TERMS   VARCHAR(50),
    PAGE_NUMBER      BIGINT,
    CONFIDENCE_LEVEL BIGINT      NOT NULL,
    CREATED_BY       VARCHAR(15) NOT NULL,
    CREATED_DT       TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
    LAST_UPDATED_BY  VARCHAR(15),
    LAST_UPDATED_DT  TIMESTAMP(6),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_OCR_PO_ORDLINE_EXT
--------------------------------------------------------
CREATE TABLE PETPE_OCR_PO_ORDLINE_EXT
(
    PK_ID            VARCHAR(50) NOT NULL,
    PO_REF_ID        VARCHAR(50) NOT NULL,
    PO_NUMBER        VARCHAR(30),
    LINE_NUMBER      VARCHAR(10),
    ORD_QUANTITY     VARCHAR(50),
    ORD_UOM          VARCHAR(50),
    ITEM_NUMBER      VARCHAR(100),
    DESCRIPTION      VARCHAR(500),
    CUST_ITEM_NUMBER VARCHAR(100),
    UNIT_PRICE       VARCHAR(50),
    PRICE_UOM        VARCHAR(50),
    EXTENDED_PRICE   VARCHAR(50),
    SPA              VARCHAR(50),
    REQ_SHIP_DATE    VARCHAR(35),
    SHIPPING_METHOD  VARCHAR(50),
    ACCT             VARCHAR(50),
    SHIPPING_TERMS   VARCHAR(50),
    PAGE_NUMBER      BIGINT,
    CONFIDENCE_LEVEL BIGINT      NOT NULL,
    CREATED_BY       VARCHAR(15) NOT NULL,
    CREATED_DT       TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
    LAST_UPDATED_BY  VARCHAR(15),
    LAST_UPDATED_DT  TIMESTAMP(6),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_OCR_QUEUES
--------------------------------------------------------
CREATE TABLE PETPE_OCR_QUEUES
(
    PK_ID               VARCHAR(20)  NOT NULL,
    QUEUE_NAME          VARCHAR(50)  NOT NULL,
    DESCRIPTION         VARCHAR(500),
    CURRENT_LEVEL       BIGINT,
    PARENT_QUEUES_REF   VARCHAR(300) NOT NULL,
    PARENT_QUEUES_NAMES VARCHAR(700),
    CREATED_BY          VARCHAR(15)  NOT NULL,
    CREATED_DT          TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
    LAST_UPDATED_BY     VARCHAR(15),
    LAST_UPDATED_DT     TIMESTAMP(6),
    IS_DEFAULT_QUEUE    VARCHAR(1)   DEFAULT 'N',
    PRIMARY KEY (PK_ID, PARENT_QUEUES_REF)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_OCR_QUEUESACCESS
--------------------------------------------------------
CREATE TABLE PETPE_OCR_QUEUESACCESS
(
    PK_ID           VARCHAR(20)  NOT NULL,
    USERID          VARCHAR(30)  NOT NULL,
    QUEUE_REF       VARCHAR(300) NOT NULL,
    QUEUE_REF_NAME  VARCHAR(700) NOT NULL,
    ACCESS_LEVEL    VARCHAR(50)  NOT NULL,
    CREATED_BY      VARCHAR(15)  NOT NULL,
    CREATED_DT      TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
    LAST_UPDATED_BY VARCHAR(15),
    LAST_UPDATED_DT TIMESTAMP(6),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_OCR_ROUTING
--------------------------------------------------------
CREATE TABLE PETPE_OCR_ROUTING
(
    ROUTING_ID         VARCHAR(20)   NOT NULL,
    ROUTING_NAME1      VARCHAR(100)  NOT NULL,
    ROUTING_CONDITION1 VARCHAR(20)   NOT NULL,
    ROUTING_VALUE1     VARCHAR(4000) NOT NULL,
    ROUTING_NAME2      VARCHAR(100),
    ROUTING_CONDITION2 VARCHAR(20),
    ROUTING_VALUE2     VARCHAR(4000),
    QUEUE_REF          VARCHAR(300)  NOT NULL,
    QUEUE_REF_NAME     VARCHAR(700)  NOT NULL,
    PARTNER_REF        VARCHAR(20)   NOT NULL,
    PARTNER_REF_NAME   VARCHAR(100)  NOT NULL,
    CREATED_BY         VARCHAR(15)   NOT NULL,
    CREATED_DT         TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
    LAST_UPDATED_BY    VARCHAR(15),
    LAST_UPDATED_DT    TIMESTAMP(6),
    ROUTING_PRIORITY   BIGINT,
    PRIMARY KEY (ROUTING_ID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_OCR_ROUTING_DEFAULT
--------------------------------------------------------
CREATE TABLE PETPE_OCR_ROUTING_DEFAULT
(
    ROUTING_ID         VARCHAR(20)   NOT NULL,
    ROUTING_PRIORITY   BIGINT        NOT NULL,
    ROUTING_NAME1      VARCHAR(100)  NOT NULL,
    ROUTING_CONDITION1 VARCHAR(20)   NOT NULL,
    ROUTING_VALUE1     VARCHAR(4000) NOT NULL,
    ROUTING_NAME2      VARCHAR(100),
    ROUTING_CONDITION2 VARCHAR(20),
    ROUTING_VALUE2     VARCHAR(4000),
    QUEUE_REF          VARCHAR(300)  NOT NULL,
    QUEUE_REF_NAME     VARCHAR(700)  NOT NULL,
    PARTNER_REF        VARCHAR(20),
    PARTNER_REF_NAME   VARCHAR(100),
    CREATED_BY         VARCHAR(15)   NOT NULL,
    CREATED_DT         TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
    LAST_UPDATED_BY    VARCHAR(15),
    LAST_UPDATED_DT    TIMESTAMP(6),
    PRIMARY KEY (ROUTING_ID, ROUTING_PRIORITY)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_OCR_VALIDATION_RULES
--------------------------------------------------------
CREATE TABLE PETPE_OCR_VALIDATION_RULES
(
    PK_ID           VARCHAR(20)  NOT NULL,
    VALIDATION_NAME VARCHAR(100) NOT NULL,
    DESCRIPTION     VARCHAR(200),
    CREATED_BY      VARCHAR(15)  NOT NULL,
    CREATED_DT      TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
    LAST_UPDATED_BY VARCHAR(15),
    LAST_UPDATED_DT TIMESTAMP(6),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_OCR_VALIDATION_VALUES
--------------------------------------------------------
CREATE TABLE PETPE_OCR_VALIDATION_VALUES
(
    PK_ID               VARCHAR(20)  NOT NULL,
    VALIDATION_RULE_REF VARCHAR(20),
    REF_NAME            VARCHAR(100) NOT NULL,
    REF_VALUE1          VARCHAR(100),
    REF_VALUE2          VARCHAR(100),
    REF_VALUE3          VARCHAR(100),
    CREATED_BY          VARCHAR(15)  NOT NULL,
    CREATED_DT          TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
    LAST_UPDATED_BY     VARCHAR(15),
    LAST_UPDATED_DT     TIMESTAMP(6),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_OCR_ACTIVITY_HISTORY
--------------------------------------------------------
CREATE TABLE PETPE_OCR_ACTIVITY_HISTORY
(
    PK_ID       VARCHAR(50)  NOT NULL,
    PO_REF_ID   VARCHAR(50)  NOT NULL,
    PO_NUMBER   VARCHAR(30),
    USER_NAME   VARCHAR(100) NOT NULL,
    USER_ID     VARCHAR(15)  NOT NULL,
    ACTIVITY_DT TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
    ACTIVITY    VARCHAR(2000),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;
