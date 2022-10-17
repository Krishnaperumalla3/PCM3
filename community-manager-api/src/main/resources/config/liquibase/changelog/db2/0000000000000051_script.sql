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
--  DDL STATEMENTS FOR PETPE_TRANSFERINFO_SCH_CONF
--------------------------------------------------------

CREATE TABLE PETPE_TRANSFERINFO_SCH_CONF
(
    PK_ID           VARCHAR(20)  NOT NULL,
    FILE_AGE        VARCHAR(10)  NOT NULL,
    IS_ACTIVE       VARCHAR(1)   NOT NULL,
    CLOUD_NAME      VARCHAR(20)  NOT NULL,
    REGION          VARCHAR(50),
    BUCKET_NAME     VARCHAR(100),
    ACCESS_KEY_ID   VARCHAR(200),
    SECRET_KEY_ID   VARCHAR(200),
    CREATED_BY      VARCHAR(200) NOT NULL,
    LAST_UPDATED_BY VARCHAR(15),
    LAST_UPDATED_DT TIMESTAMP(6),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;
