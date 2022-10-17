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

ALTER TABLE PETPE_AS2
    ADD (AS_SSL_TYPE VARCHAR2(15),
         AS_URL VARCHAR2(15),
         AS_RESPONSE_TIMEOUT VARCHAR2(5),
         AS_KEY_CERT_PASSPHRASE VARCHAR2(50),
         AS_CIPHER_STRENGTH VARCHAR2(15),
         AS_KEY_CERT_ID VARCHAR2(50),
         AS_CA_CERT_ID VARCHAR2(50));
