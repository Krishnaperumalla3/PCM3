/*
 *
 *  * Copyright (c) 2020 Pragma Edge Inc
 *  *
 *  * Licensed under the Pragma Edge Inc
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  * https://pragmaedge.com/licenseagreement
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 *
 */

-------------------------------------------------------------
--  DML STATEMENT FOR PETPE_SO_USERS, PETPE_SO_USERS_ATTEMPTS
-------------------------------------------------------------

ALTER TABLE PETPE_SO_USERS
    ADD ACCOUNT_NON_LOCKED VARCHAR(1);

ALTER TABLE PETPE_SO_USERS
    ADD ACCOUNT_NON_EXPIRED VARCHAR(1);

ALTER TABLE PETPE_SO_USERS
    ADD CREDENTIALS_NON_EXPIRED VARCHAR(1);

CREATE SEQUENCE SEQ_PETPE_SO_USERS_ATTEMPTS START WITH 1 INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 NO CYCLE CACHE 10;

CREATE TABLE PETPE_SO_USERS_ATTEMPTS
(
    ID            BIGINT       NOT NULL,
    USERNAME      VARCHAR(100) NOT NULL,
    ATTEMPTS      INT          NOT NULL,
    LAST_MODIFIED DATETIME2(6) NOT NULL,
    CONSTRAINT USER_ATT_UNI UNIQUE (USERNAME),
    PRIMARY KEY (ID)
);

UPDATE PETPE_SO_USERS
SET ACCOUNT_NON_LOCKED = 'Y';

UPDATE PETPE_SO_USERS
SET ACCOUNT_NON_EXPIRED = 'Y';

UPDATE PETPE_SO_USERS
SET CREDENTIALS_NON_EXPIRED = 'Y';

ALTER TABLE PETPE_SO_USERS
ALTER
COLUMN ACCOUNT_NON_LOCKED VARCHAR (1) NOT NULL;

ALTER TABLE PETPE_SO_USERS
ALTER
COLUMN ACCOUNT_NON_EXPIRED VARCHAR (1) NOT NULL;

ALTER TABLE PETPE_SO_USERS
ALTER
COLUMN CREDENTIALS_NON_EXPIRED VARCHAR (1) NOT NULL;

ALTER TABLE PETPE_SO_USERS
    ADD DEFAULT 'Y' FOR ACCOUNT_NON_LOCKED;

ALTER TABLE PETPE_SO_USERS
    ADD DEFAULT 'Y' FOR ACCOUNT_NON_EXPIRED;

ALTER TABLE PETPE_SO_USERS
    ADD DEFAULT 'Y' FOR CREDENTIALS_NON_EXPIRED;
