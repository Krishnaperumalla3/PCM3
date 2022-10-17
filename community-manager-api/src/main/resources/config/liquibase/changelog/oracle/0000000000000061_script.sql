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

--------------------------------------------------------
--  DML STATEMENT FOR PETPE_SO_USERS_AUDIT
--------------------------------------------------------

CREATE SEQUENCE SEQ_PETPE_SO_USERS_AUDIT MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 10 NOORDER  NOCYCLE;

CREATE TABLE PETPE_SO_USERS_AUDIT
(
    ID         NUMBER(11,0) NOT NULL ENABLE,
    PRINCIPLE  VARCHAR2(100) NOT NULL ENABLE,
    EVENT_TYPE VARCHAR2(25) NOT NULL ENABLE,
    EVENT_DATA VARCHAR2(256),
    EVENT_DATE TIMESTAMP(6),
    PRIMARY KEY (ID)
);
   