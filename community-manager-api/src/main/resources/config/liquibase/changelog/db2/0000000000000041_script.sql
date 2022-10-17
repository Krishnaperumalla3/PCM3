/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc.
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
--  DDL STATEMENTS
--------------------------------------------------------
ALTER TABLE PETPE_SFG_CD
    ADD COLUMN OUT_DIRECTORY VARCHAR(100);
ALTER TABLE PETPE_SFG_CD
    ADD COLUMN CONNECTION_TYPE_FLAG VARCHAR(1);

DROP TABLE PETPE_PEM_TEMP;

CREATE TABLE PETPE_PEM_TEMP
(
    PK_ID               VARCHAR(30) NOT NULL,
    PROCESS_DOC_PK_ID   VARCHAR(30) NOT NULL,
    SEQ_ID              VARCHAR(20),
    PARTNER_PROFILE     VARCHAR(30),
    APPLICATION_PROFILE VARCHAR(30),
    SEQ_TYPE            VARCHAR(20),
    FILE_NAME           VARCHAR(100),
    FLOW_TYPE           VARCHAR(20),
    DOC_TYPE            VARCHAR(20),
    SENDER_ID           VARCHAR(100),
    RECEIVER_ID         VARCHAR(100),
    TRANS               VARCHAR(20),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;
