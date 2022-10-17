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
--  DDL for Table PETPE_SFG_FTP
--------------------------------------------------------
ALTER TABLE PETPE_SFG_FTP
    ADD COLUMN AUTH_USER_KEYS VARCHAR(355);
ALTER TABLE PETPE_SFG_FTP
    ADD COLUMN CA_CERT_ID VARCHAR(100);
ALTER TABLE PETPE_SFG_FTP
    ADD COLUMN KNOWN_HOST_KEY_ID VARCHAR(100);
ALTER TABLE PETPE_SFG_FTP
    ADD COLUMN USER_IDENTITY_KEY_ID VARCHAR(100);

--------------------------------------------------------
--  DDL for Table PETPE_FTP
--------------------------------------------------------
ALTER TABLE PETPE_FTP
    ADD COLUMN CA_CERT_NAME VARCHAR(100);
ALTER TABLE PETPE_FTP
    ADD COLUMN KNOWN_HOST_KEY_ID VARCHAR(100);
ALTER TABLE PETPE_FTP
    ADD COLUMN SSH_IDENTITY_KEY_NAME VARCHAR(100);

--------------------------------------------------------
--  DDL for Table PETPE_HTTP
--------------------------------------------------------
ALTER TABLE PETPE_HTTP
    ADD COLUMN CERTIFICATE_ID VARCHAR(100);

--------------------------------------------------------
--  DDL for Table PETPE_AS2
--------------------------------------------------------
ALTER TABLE PETPE_AS2
    ADD COLUMN CA_CERT_ID VARCHAR(100);
ALTER TABLE PETPE_AS2
    ADD COLUMN EXCHG_CERT_NAME VARCHAR(100);
ALTER TABLE PETPE_AS2
    ADD COLUMN SIGNING_CERT_NAME VARCHAR(100);

--------------------------------------------------------
--  DDL for Table PETPE_SFG_CD
--------------------------------------------------------
ALTER TABLE PETPE_SFG_CD
    ADD COLUMN CA_CERTIFICATE_ID VARCHAR(100);
