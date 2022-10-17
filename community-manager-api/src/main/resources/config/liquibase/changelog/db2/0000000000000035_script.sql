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
--  DDL STATEMENT
--------------------------------------------------------

ALTER TABLE PETPE_AS2
    ADD COLUMN SCI_PROFILE_ID VARCHAR(255);

CREATE TABLE PETPE_RULES_STAGING
(
    RULE_ID             VARCHAR(20)  NOT NULL,
    RULE_NAME           VARCHAR(50)  NOT NULL,
    BUSINESS_PROCESS_ID VARCHAR(120) NOT NULL,
    PROPERTY_NAME_1     VARCHAR(200),
    PROPERTY_NAME_2     VARCHAR(200),
    PROPERTY_NAME_3     VARCHAR(200),
    PROPERTY_NAME_4     VARCHAR(200),
    PROPERTY_NAME_5     VARCHAR(200),
    PROPERTY_NAME_6     VARCHAR(200),
    PROPERTY_NAME_7     VARCHAR(200),
    PROPERTY_NAME_8     VARCHAR(200),
    PROPERTY_NAME_9     VARCHAR(200),
    PROPERTY_NAME_10    VARCHAR(200),
    PROPERTY_NAME_11    VARCHAR(200),
    PROPERTY_NAME_12    VARCHAR(200),
    PROPERTY_NAME_13    VARCHAR(200),
    PROPERTY_NAME_14    VARCHAR(200),
    PROPERTY_NAME_15    VARCHAR(200),
    PROPERTY_NAME_16    VARCHAR(200),
    PROPERTY_NAME_17    VARCHAR(200),
    PROPERTY_NAME_18    VARCHAR(200),
    PROPERTY_NAME_19    VARCHAR(200),
    PROPERTY_NAME_20    VARCHAR(200),
    PROPERTY_NAME_21    VARCHAR(200),
    PROPERTY_NAME_22    VARCHAR(200),
    PROPERTY_NAME_23    VARCHAR(200),
    PROPERTY_NAME_24    VARCHAR(200),
    PROPERTY_NAME_25    VARCHAR(200),
    PRIMARY KEY (RULE_ID)
) IN PCMTBSPACE;
