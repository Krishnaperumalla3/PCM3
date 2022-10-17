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
--   SQLSERVER DDL STATEMENT FOR 	PETPE_AWS_S3
-------------------------------------------------------------
ALTER TABLE PETPE_AWS_S3
    ADD FOLDER_NAME VARCHAR(255),
        QUEUE_NAME VARCHAR(255),
        END_POINT_URL VARCHAR(255);

