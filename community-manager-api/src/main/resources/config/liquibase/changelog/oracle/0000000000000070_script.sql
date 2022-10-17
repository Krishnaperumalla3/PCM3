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
--  DDL STATEMENT FOR 	PETPE_API_PROXY_ENDPOINT, 
--						PETPE_API_AUTH_DATA,
--						PETPE_API_HP_DATA
-------------------------------------------------------------

CREATE TABLE PETPE_API_PROXY_ENDPOINT
(
    PK_ID           VARCHAR2(30) NOT NULL ENABLE,
    API_NAME        VARCHAR2(100) NOT NULL ENABLE,
    API_TYPE        VARCHAR2(20) NOT NULL ENABLE,
    METHOD_NAME     VARCHAR2(10) NOT NULL ENABLE,
    PROXY_URL       VARCHAR2(255),
    SERVER_URL      VARCHAR2(255) NOT NULL ENABLE,
    REQ_PAYLOAD_SPA CLOB,
    CREATED_BY      VARCHAR2(100),
    CREATED_DT      TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
    LAST_UPDATED_BY VARCHAR2(100),
    LAST_UPDATED_DT TIMESTAMP(6),
    PRIMARY KEY (PK_ID)
);

CREATE TABLE PETPE_API_AUTH_DATA
(
    PK_ID           VARCHAR2(30) NOT NULL ENABLE,
    API_ID          VARCHAR2(30) NOT NULL ENABLE,
    API_CONFIG_TYPE VARCHAR2(30) NOT NULL ENABLE,
    AUTH_TYPE       VARCHAR2(20) NOT NULL ENABLE,
    USERNAME        VARCHAR2(100),
    PASSWORD        VARCHAR2(255),
    TOKEN_API_URL   VARCHAR2(500),
    TOKEN_KEY       VARCHAR2(20),
    TOKEN_PREFIX    VARCHAR2(100),
    TOKEN_HEADER    VARCHAR2(100),
    CREATED_DT      TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
    CREATED_BY      VARCHAR2(100),
    LAST_UPDATED_BY VARCHAR2(100),
    LAST_UPDATED_DT TIMESTAMP(6),
    PRIMARY KEY (PK_ID)
);

CREATE TABLE PETPE_API_HP_DATA
(
    PK_ID           VARCHAR2(30) NOT NULL ENABLE,
    API_ID          VARCHAR2(30) NOT NULL ENABLE,
    API_CONFIG_TYPE VARCHAR2(30) NOT NULL ENABLE,
    HP_TYPE         VARCHAR2(10) NOT NULL ENABLE,
    HP_KEY          VARCHAR2(100) NOT NULL ENABLE,
    HP_VALUE        VARCHAR2(255) NOT NULL ENABLE,
    HP_DESCRIPTION  VARCHAR2(500),
    REQUIRED        VARCHAR2(3),
    DYNAMIC_VALUE   VARCHAR2(3),
    CREATED_BY      VARCHAR2(100),
    CREATED_DT      TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
    LAST_UPDATED_BY VARCHAR2(100),
    LAST_UPDATED_DT TIMESTAMP(6),
    PRIMARY KEY (PK_ID)
);
