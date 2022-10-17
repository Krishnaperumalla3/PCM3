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
--  DML STATEMENTS
--------------------------------------------------------

UPDATE PETPE_TRANSINFOD SET "SEQUENCE" = '11111' WHERE "SEQUENCE" IS NULL;

ALTER TABLE PETPE_TRANSINFOD ADD NEW_COLUMN NUMBER(20);

UPDATE PETPE_TRANSINFOD SET NEW_COLUMN = TO_NUMBER("SEQUENCE");

ALTER TABLE PETPE_TRANSINFOD DROP COLUMN "SEQUENCE" CASCADE CONSTRAINTS;

ALTER TABLE PETPE_TRANSINFOD RENAME COLUMN NEW_COLUMN TO "SEQUENCE";

