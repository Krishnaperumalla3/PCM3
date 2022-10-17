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
--  DDL STATEMENTS FOR PETPE_TRANSINFOD_STAGING
--------------------------------------------------------

CREATE TABLE PETPE_TRANSINFOD_STAGING
(
    PK_ID    VARCHAR(50) NOT NULL,
    BPID     VARCHAR(256),
    RULENAME VARCHAR(256),
    BPNAME   VARCHAR(256),
    DETAILS  VARCHAR(4000),
    SEQUENCE INTEGER,
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;
		
