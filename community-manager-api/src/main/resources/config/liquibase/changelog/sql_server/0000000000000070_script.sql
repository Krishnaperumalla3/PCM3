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
    PK_ID           VARCHAR(30)  NOT NULL,
    API_NAME        VARCHAR(100) NOT NULL,
    API_TYPE        VARCHAR(20)  NOT NULL,
    METHOD_NAME     VARCHAR(10)  NOT NULL,
    PROXY_URL       VARCHAR(255),
    SERVER_URL      VARCHAR(255) NOT NULL,
    REQ_PAYLOAD_SPA VARCHAR(max
) ,
    CREATED_BY      VARCHAR(100),
    CREATED_DT      DATETIME2(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    LAST_UPDATED_BY VARCHAR(100),
    LAST_UPDATED_DT DATETIME2(6),
    PRIMARY KEY (PK_ID)
);

CREATE TABLE PETPE_API_AUTH_DATA
(
    PK_ID           VARCHAR(30) NOT NULL,
    API_ID          VARCHAR(30) NOT NULL,
    API_CONFIG_TYPE VARCHAR(30) NOT NULL,
    AUTH_TYPE       VARCHAR(20) NOT NULL,
    USERNAME        VARCHAR(100),
    PASSWORD        VARCHAR(255),
    TOKEN_API_URL   VARCHAR(500),
    TOKEN_KEY       VARCHAR(20),
    TOKEN_PREFIX    VARCHAR(100),
    TOKEN_HEADER    VARCHAR(100),
    CREATED_DT      DATETIME2(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CREATED_BY      VARCHAR(100),
    LAST_UPDATED_BY VARCHAR(100),
    LAST_UPDATED_DT DATETIME2(6),
    PRIMARY KEY (PK_ID)
);

CREATE TABLE PETPE_API_HP_DATA
(
    PK_ID           VARCHAR(30)  NOT NULL,
    API_ID          VARCHAR(30)  NOT NULL,
    API_CONFIG_TYPE VARCHAR(30)  NOT NULL,
    HP_TYPE         VARCHAR(10)  NOT NULL,
    HP_KEY          VARCHAR(100) NOT NULL,
    HP_VALUE        VARCHAR(255) NOT NULL,
    HP_DESCRIPTION  VARCHAR(500),
    REQUIRED        VARCHAR(3),
    DYNAMIC_VALUE   VARCHAR(3),
    CREATED_BY      VARCHAR(100),
    CREATED_DT      DATETIME2(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    LAST_UPDATED_BY VARCHAR(100),
    LAST_UPDATED_DT DATETIME2(6),
    PRIMARY KEY (PK_ID)
);
