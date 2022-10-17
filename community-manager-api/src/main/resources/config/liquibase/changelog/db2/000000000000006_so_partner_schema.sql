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
--  DDL for Table PETPE_SO_TRADINGPARTNER
--------------------------------------------------------
CREATE TABLE PETPE_SO_TRADINGPARTNER
(
    PK_ID                VARCHAR(20)  NOT NULL,
    TP_NAME              VARCHAR(200) NOT NULL,
    TP_ID                VARCHAR(200) NOT NULL,
    ADDRESS_LINE1        VARCHAR(100),
    ADDRESS_LINE2        VARCHAR(100),
    EMAIL                VARCHAR(250) NOT NULL,
    PHONE                VARCHAR(15)  NOT NULL,
    TP_PROTOCOL          VARCHAR(20)  NOT NULL,
    TP_PICKUP_FILES      VARCHAR(1)   NOT NULL DEFAULT 'N',
    FILE_TP_SERVER       VARCHAR(1)   NOT NULL DEFAULT 'N',
    PARTNER_PROTOCOL_REF VARCHAR(20)  NOT NULL,
    TP_IS_ACTIVE         VARCHAR(1)   NOT NULL DEFAULT 'N',
    CREATED_BY           VARCHAR(15)  NOT NULL,
    LAST_UPDATED_BY      VARCHAR(15),
    LAST_UPDATED_DT      TIMESTAMP(6),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;
