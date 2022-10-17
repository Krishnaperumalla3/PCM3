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
--  DDL STATEMENT FOR PETPE_USER_TOKEN_EXP
-------------------------------------------------------------
CREATE SEQUENCE SEQ_USER_TOKEN_EXP MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 10 NOORDER  NOCYCLE;
CREATE TABLE PETPE_USER_TOKEN_EXP
(
    ID           NUMBER(11,0) NOT NULL ENABLE,
    USERID       VARCHAR2(200 BYTE) NOT NULL ENABLE,
    TOKEN        VARCHAR2(500 BYTE) NOT NULL ENABLE,
    CREATED_DATE TIMESTAMP(6) NOT NULL ENABLE,
    PRIMARY KEY (ID)
);
