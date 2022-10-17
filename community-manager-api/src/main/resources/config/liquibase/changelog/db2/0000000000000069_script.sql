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
CREATE SEQUENCE SEQ_USER_TOKEN_EXP START WITH 1 INCREMENT BY 1 NO MAXVALUE NO CYCLE CACHE 20;
CREATE TABLE PETPE_USER_TOKEN_EXP
(
    ID           BIGINT       NOT NULL,
    USERID       VARCHAR(200) NOT NULL,
    TOKEN        VARCHAR(500) NOT NULL,
    CREATED_DATE TIMESTAMP(6) NOT NULL,
    PRIMARY KEY (ID)
);
