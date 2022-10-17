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
--  DDL for Table PETPE_POOLING_INTERVAL
--------------------------------------------------------
CREATE TABLE PETPE_POOLING_INTERVAL
(
    PK_ID           VARCHAR(50) NOT NULL,
    INTERVAL        VARCHAR(50) NOT NULL,
    SEQ             SMALLINT    NOT NULL,
    DESCRIPTION     VARCHAR(255),
    CREATED_BY      VARCHAR(15) NOT NULL,
    LAST_UPDATED_BY VARCHAR(15),
    LAST_UPDATED_DT TIMESTAMP(6),
    UNIQUE (SEQ),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_CORRELATION_NAMES
--------------------------------------------------------
CREATE TABLE PETPE_CORRELATION_NAMES
(
    PK_ID               VARCHAR(20) NOT NULL,
    CORRELATION_NAME_1  VARCHAR(200),
    CORRELATION_NAME_2  VARCHAR(200),
    CORRELATION_NAME_3  VARCHAR(200),
    CORRELATION_NAME_4  VARCHAR(200),
    CORRELATION_NAME_5  VARCHAR(200),
    CORRELATION_NAME_6  VARCHAR(200),
    CORRELATION_NAME_7  VARCHAR(200),
    CORRELATION_NAME_8  VARCHAR(200),
    CORRELATION_NAME_9  VARCHAR(200),
    CORRELATION_NAME_10 VARCHAR(200),
    CORRELATION_NAME_11 VARCHAR(200),
    CORRELATION_NAME_12 VARCHAR(200),
    CORRELATION_NAME_13 VARCHAR(200),
    CORRELATION_NAME_14 VARCHAR(200),
    CORRELATION_NAME_15 VARCHAR(200),
    CORRELATION_NAME_16 VARCHAR(200),
    CORRELATION_NAME_17 VARCHAR(200),
    CORRELATION_NAME_18 VARCHAR(200),
    CORRELATION_NAME_19 VARCHAR(200),
    CORRELATION_NAME_20 VARCHAR(200),
    CORRELATION_NAME_21 VARCHAR(200),
    CORRELATION_NAME_22 VARCHAR(200),
    CORRELATION_NAME_23 VARCHAR(200),
    CORRELATION_NAME_24 VARCHAR(200),
    CORRELATION_NAME_25 VARCHAR(200),
    CREATED_BY          VARCHAR(15),
    LAST_UPDATED_BY     VARCHAR(15),
    LAST_UPDATED_DT     TIMESTAMP(6),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;
