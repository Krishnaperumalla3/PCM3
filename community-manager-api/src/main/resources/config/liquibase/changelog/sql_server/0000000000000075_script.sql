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
--   SQLSERVER DDL STATEMENT FOR 	PETPE_PROCESSDOCS_API
-------------------------------------------------------------
ALTER TABLE PETPE_PROCESSDOCS_API
    ADD PROCESS_RULE_SEQ VARCHAR(2000);

DROP TABLE PETPE_RULES_API;

CREATE TABLE PETPE_PROCESS_RULE_API
(
    PK_ID            VARCHAR(20)  NOT NULL,
    RULE_ID          VARCHAR(30)  NOT NULL,
    RULE_NAME        VARCHAR(500) NOT NULL,
    PROCESS_DOC_REF  VARCHAR(30)  NOT NULL,
    SEQ_ID           TINYINT      NOT NULL,
    PROPERTY_NAME_1  VARCHAR(500),
    PROPERTY_NAME_2  VARCHAR(500),
    PROPERTY_NAME_3  VARCHAR(500),
    PROPERTY_NAME_4  VARCHAR(500),
    PROPERTY_NAME_5  VARCHAR(500),
    PROPERTY_NAME_6  VARCHAR(500),
    PROPERTY_NAME_7  VARCHAR(500),
    PROPERTY_NAME_8  VARCHAR(500),
    PROPERTY_NAME_9  VARCHAR(500),
    PROPERTY_NAME_10 VARCHAR(500),
    PROPERTY_NAME_11 VARCHAR(500),
    PROPERTY_NAME_12 VARCHAR(500),
    PROPERTY_NAME_13 VARCHAR(500),
    PROPERTY_NAME_14 VARCHAR(500),
    PROPERTY_NAME_15 VARCHAR(500),
    PROPERTY_NAME_16 VARCHAR(500),
    PROPERTY_NAME_17 VARCHAR(500),
    PROPERTY_NAME_18 VARCHAR(500),
    PROPERTY_NAME_19 VARCHAR(500),
    PROPERTY_NAME_20 VARCHAR(500),
    PROPERTY_NAME_21 VARCHAR(500),
    PROPERTY_NAME_22 VARCHAR(500),
    PROPERTY_NAME_23 VARCHAR(500),
    PROPERTY_NAME_24 VARCHAR(500),
    PROPERTY_NAME_25 VARCHAR(500),
    PRIMARY KEY (PK_ID)
);