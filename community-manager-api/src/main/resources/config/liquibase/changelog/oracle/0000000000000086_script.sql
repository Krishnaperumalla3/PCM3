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
--   ORACLE DDL STATEMENT FOR 	PETPE_API_AUTH_DATA
-------------------------------------------------------------

ALTER TABLE PETPE_API_AUTH_DATA
    ADD CLIENT_ID VARCHAR2(255);

ALTER TABLE PETPE_API_AUTH_DATA
    ADD CLIENT_SECRET VARCHAR2(255);

ALTER TABLE PETPE_API_AUTH_DATA
    ADD GRANT_TYPE VARCHAR2(255);

ALTER TABLE PETPE_API_AUTH_DATA
    ADD RESOURCE_OAUTH VARCHAR2(255);

ALTER TABLE PETPE_API_AUTH_DATA
    ADD SCOPE_OAUTH VARCHAR2(255);

