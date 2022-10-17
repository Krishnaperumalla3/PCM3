/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc.
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
--  DDL STATEMENTS
--------------------------------------------------------

ALTER TABLE PETPE_TRANSFERINFO
    ADD COLUMN PGP_KEY_NAME VARCHAR(200);

ALTER TABLE PETPE_TRANSFERINFO
    ADD COLUMN PASS_PHRASE VARCHAR(100);

ALTER TABLE PETPE_TRANSFERINFO
    ADD COLUMN ENC_TYPE VARCHAR(50);

CALL SYSPROC.ADMIN_CMD('REORG TABLE PETPE_TRANSFERINFO');
