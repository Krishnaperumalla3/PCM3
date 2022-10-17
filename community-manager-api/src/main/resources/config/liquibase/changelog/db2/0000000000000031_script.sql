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
ALTER TABLE PETPE_TRANSFERINFO DROP COLUMN FA_DTM DROP COLUMN DLE_DTM;

ALTER TABLE PETPE_TRANSFERINFO
    ADD COLUMN FA_DTM VARCHAR(15) ADD COLUMN DLE_DTM VARCHAR(15);

CALL SYSPROC.ADMIN_CMD('REORG TABLE PETPE_TRANSFERINFO');
CREATE
INDEX FA_DTM_PETPE_TRANSFERINFO ON PETPE_TRANSFERINFO (FA_DTM);
CALL SYSPROC.ADMIN_CMD('REORG TABLE PETPE_TRANSFERINFO');
CREATE
INDEX DLE_DTM_PETPE_TRANSFERINFO ON PETPE_TRANSFERINFO (DLE_DTM);
