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
--   DB2 DDL STATEMENT FOR 	PETPE_API_AUTH_DATA
-------------------------------------------------------------

ALTER TABLE PETPE_API_AUTH_DATA
    ADD COLUMN CLIENT_ID VARCHAR(255);

ALTER TABLE PETPE_API_AUTH_DATA
    ADD COLUMN CLIENT_SECRET VARCHAR(255);

ALTER TABLE PETPE_API_AUTH_DATA
    ADD COLUMN GRANT_TYPE VARCHAR(255);

ALTER TABLE PETPE_API_AUTH_DATA
    ADD COLUMN RESOURCE_OAUTH VARCHAR(255);

ALTER TABLE PETPE_API_AUTH_DATA
    ADD COLUMN SCOPE_OAUTH VARCHAR(255);

CALL SYSPROC.ADMIN_CMD('REORG TABLE PETPE_API_AUTH_DATA');

