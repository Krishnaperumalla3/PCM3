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

CREATE SEQUENCE SEQ_PETPE_SO_USERS_ATTEMPTS MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 10 NOORDER  NOCYCLE;

CREATE TABLE PETPE_SO_USERS_ATTEMPTS
(
    ID            NUMBER(11,0) NOT NULL ENABLE,
    USERNAME      VARCHAR2(100) NOT NULL ENABLE,
    ATTEMPTS      NUMBER(3) NOT NULL ENABLE,
    LAST_MODIFIED TIMESTAMP(6) NOT NULL ENABLE,
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
    MODIFY ACCOUNT_NON_LOCKED VARCHAR (1) DEFAULT 'Y' NOT NULL ENABLE;

ALTER TABLE PETPE_SO_USERS
    MODIFY ACCOUNT_NON_EXPIRED VARCHAR (1) DEFAULT 'Y' NOT NULL ENABLE;

ALTER TABLE PETPE_SO_USERS
    MODIFY CREDENTIALS_NON_EXPIRED VARCHAR (1) DEFAULT 'Y' NOT NULL ENABLE;
