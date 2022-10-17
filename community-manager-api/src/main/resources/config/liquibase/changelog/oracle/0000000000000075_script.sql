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

-------------------------------------------------------------
--   ORACLE DDL STATEMENT FOR 	PETPE_PROCESSDOCS_API
-------------------------------------------------------------

ALTER TABLE PETPE_PROCESSDOCS_API
    ADD PROCESS_RULE_SEQ VARCHAR2(2000);

DROP TABLE PETPE_RULES_API;

CREATE TABLE PETPE_PROCESS_RULE_API
(
    PK_ID            VARCHAR2(20 BYTE) NOT NULL ENABLE,
    RULE_ID          VARCHAR2(30) NOT NULL ENABLE,
    RULE_NAME        VARCHAR2(500) NOT NULL ENABLE,
    PROCESS_DOC_REF  VARCHAR2(30) NOT NULL ENABLE,
    SEQ_ID           NUMBER(3) NOT NULL ENABLE,
    PROPERTY_NAME_1  VARCHAR2(500),
    PROPERTY_NAME_2  VARCHAR2(500),
    PROPERTY_NAME_3  VARCHAR2(500),
    PROPERTY_NAME_4  VARCHAR2(500),
    PROPERTY_NAME_5  VARCHAR2(500),
    PROPERTY_NAME_6  VARCHAR2(500),
    PROPERTY_NAME_7  VARCHAR2(500),
    PROPERTY_NAME_8  VARCHAR2(500),
    PROPERTY_NAME_9  VARCHAR2(500),
    PROPERTY_NAME_10 VARCHAR2(500),
    PROPERTY_NAME_11 VARCHAR2(500),
    PROPERTY_NAME_12 VARCHAR2(500),
    PROPERTY_NAME_13 VARCHAR2(500),
    PROPERTY_NAME_14 VARCHAR2(500),
    PROPERTY_NAME_15 VARCHAR2(500),
    PROPERTY_NAME_16 VARCHAR2(500),
    PROPERTY_NAME_17 VARCHAR2(500),
    PROPERTY_NAME_18 VARCHAR2(500),
    PROPERTY_NAME_19 VARCHAR2(500),
    PROPERTY_NAME_20 VARCHAR2(500),
    PROPERTY_NAME_21 VARCHAR2(500),
    PROPERTY_NAME_22 VARCHAR2(500),
    PROPERTY_NAME_23 VARCHAR2(500),
    PROPERTY_NAME_24 VARCHAR2(500),
    PROPERTY_NAME_25 VARCHAR2(500),
    PRIMARY KEY (PK_ID)
);
