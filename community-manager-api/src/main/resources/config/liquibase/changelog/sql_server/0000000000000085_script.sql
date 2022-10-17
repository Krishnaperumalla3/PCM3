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
--   SQLSERVER DDL STATEMENT FOR 	PETPE_TRANSINFOD_STAGING
-------------------------------------------------------------

ALTER TABLE PETPE_TRANSINFOD_STAGING
    ADD ACTIVITY_BY VARCHAR(115);

ALTER TABLE PETPE_TRANSINFOD_STAGING
    ADD ACT_NAME VARCHAR(50);

ALTER TABLE PETPE_TRANSINFOD_STAGING
    ADD ACTIVITY_DT DATETIME2 (6) DEFAULT GETDATE();

UPDATE PETPE_TRANSINFOD_STAGING
SET ACTIVITY_DT = NULL;