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

--------------------------------------------------------
--  DDL STATEMENTS FOR PETPE_TRANSFERINFO_SCH_CONF
--------------------------------------------------------

CREATE TABLE PETPE_TRANSFERINFO_SCH_CONF
(
    PK_ID           VARCHAR2(20 BYTE) NOT NULL ENABLE,
    FILE_AGE        VARCHAR2(10 BYTE) NOT NULL ENABLE,
    IS_ACTIVE       VARCHAR2(1 BYTE) NOT NULL ENABLE,
    CLOUD_NAME      VARCHAR2(20 BYTE) NOT NULL ENABLE,
    REGION          VARCHAR2(50 BYTE),
    BUCKET_NAME     VARCHAR2(100 BYTE),
    ACCESS_KEY_ID   VARCHAR2(200 BYTE),
    SECRET_KEY_ID   VARCHAR2(200 BYTE),
    CREATED_BY      VARCHAR2(200 BYTE) NOT NULL ENABLE,
    LAST_UPDATED_BY VARCHAR2(15 BYTE),
    LAST_UPDATED_DT TIMESTAMP(6),
    PRIMARY KEY (PK_ID)
);
