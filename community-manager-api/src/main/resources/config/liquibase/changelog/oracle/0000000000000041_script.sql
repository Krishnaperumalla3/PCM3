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
    ADD OUT_DIRECTORY VARCHAR2(100);
ALTER TABLE PETPE_SFG_CD
    ADD CONNECTION_TYPE_FLAG VARCHAR2(1);

DROP TABLE PETPE_PEM_TEMP;

CREATE TABLE PETPE_PEM_TEMP
(
    PK_ID               VARCHAR2(30) NOT NULL ENABLE,
    PROCESS_DOC_PK_ID   VARCHAR2(30) NOT NULL ENABLE,
    SEQ_ID              VARCHAR2(20),
    PARTNER_PROFILE     VARCHAR2(30),
    APPLICATION_PROFILE VARCHAR2(30),
    SEQ_TYPE            VARCHAR2(20),
    FILE_NAME           VARCHAR2(100),
    FLOW_TYPE           VARCHAR2(20),
    DOC_TYPE            VARCHAR2(20),
    SENDER_ID           VARCHAR2(100),
    RECEIVER_ID         VARCHAR2(100),
    TRANS               VARCHAR2(20),
    PRIMARY KEY (PK_ID)
);
