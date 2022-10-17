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
--  DML, DDL STATEMENT FOR PETPE_PEM_PARTNER_CODES, PETPE_SFG_CUSTOMPROTOCOL
--------------------------------------------------------

INSERT INTO PETPE_PEM_PARTNER_CODES
VALUES ('128-bit AES CBC with PKCS5 padding', 'AES-128');

INSERT INTO PETPE_PEM_PARTNER_CODES
VALUES ('256-bit AES CBC with PKCS5 padding', 'AES-256');

ALTER TABLE PETPE_SFG_CUSTOMPROTOCOL
    MODIFY CUSTOM_PROTOCOL_EXTENSIONS VARCHAR2(500);

