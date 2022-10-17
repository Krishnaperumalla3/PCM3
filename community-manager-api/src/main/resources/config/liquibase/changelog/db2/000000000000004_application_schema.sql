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
--  DDL for Table PETPE_APPLICATION
--------------------------------------------------------
CREATE TABLE PETPE_APPLICATION
(
    PK_ID                    VARCHAR(20)            NOT NULL,
    APPLICATION_NAME         VARCHAR(50)            NOT NULL,
    APPLICATION_ID           VARCHAR(20)            NOT NULL,
    EMAIL_ID                 VARCHAR(250)           NOT NULL,
    PHONE                    VARCHAR(15)            NOT NULL,
    APP_INTEGRATION_PROTOCOL VARCHAR(10)            NOT NULL,
    APP_PICKUP_FILES         VARCHAR(1) DEFAULT 'N' NOT NULL,
    APP_DROP_FILES           VARCHAR(1) DEFAULT 'N' NOT NULL,
    APP_PROTOCOL_REF         VARCHAR(20)            NOT NULL,
    APP_IS_ACTIVE            VARCHAR(1) DEFAULT 'N',
    CREATED_BY               VARCHAR(15)            NOT NULL,
    LAST_UPDATED_BY          VARCHAR(15),
    LAST_UPDATED_DT          TIMESTAMP(6),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_APP_ACTIVITY_HISTORY
--------------------------------------------------------
CREATE TABLE PETPE_APP_ACTIVITY_HISTORY
(
    PK_ID       VARCHAR(50)  NOT NULL,
    APP_REF_ID  VARCHAR(30),
    USER_NAME   VARCHAR(100) NOT NULL,
    USER_ID     VARCHAR(15)  NOT NULL,
    ACTIVITY_DT TIMESTAMP(6) DEFAULT CURRENT_TIMESTAMP,
    ACTIVITY    CLOB,
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;

--------------------------------------------------------
--  DDL for Table PETPE_APPLICATION_SFG
--------------------------------------------------------
CREATE TABLE PETPE_APPLICATION_SFG
(
    PK_ID                    VARCHAR(20)            NOT NULL,
    APPLICATION_NAME         VARCHAR(50)            NOT NULL,
    APPLICATION_ID           VARCHAR(20)            NOT NULL,
    EMAIL_ID                 VARCHAR(250)           NOT NULL,
    PHONE                    VARCHAR(15)            NOT NULL,
    APP_INTEGRATION_PROTOCOL VARCHAR(50)            NOT NULL,
    APP_PICKUP_FILES         VARCHAR(1) DEFAULT 'N' NOT NULL,
    APP_DROP_FILES           VARCHAR(1) DEFAULT 'N' NOT NULL,
    APP_PROTOCOL_REF         VARCHAR(20)            NOT NULL,
    APP_IS_ACTIVE            VARCHAR(1) DEFAULT 'N' NOT NULL,
    CREATED_BY               VARCHAR(15)            NOT NULL,
    LAST_UPDATED_BY          VARCHAR(15),
    LAST_UPDATED_DT          TIMESTAMP(6),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;

