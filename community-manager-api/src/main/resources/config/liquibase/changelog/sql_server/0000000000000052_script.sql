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
--  DDL STATEMENTS FOR PETPE_TRANSFERINFO_STAGING
--------------------------------------------------------

CREATE TABLE PETPE_TRANSFERINFO_STAGING
(
    SEQID               BIGINT NOT NULL,
    FILEARRIVED         DATETIME2(6) NULL,
    FLOWINOUT           VARCHAR(120),
    TYPEOFTRANSFER      VARCHAR(100),
    SENDERID            VARCHAR(255),
    RECIVERID           VARCHAR(255),
    APPLICATION         VARCHAR(100),
    APPINTSTATUS        VARCHAR(100),
    PARTNERACKSTATUS    VARCHAR(100),
    SRCPROTOCOL         VARCHAR(100),
    SRCFILENAME         VARCHAR(255),
    SRCARCFILELOC       VARCHAR(500),
    DESTFILENAME        VARCHAR(255),
    DESTARCFILELOC      VARCHAR(500),
    DESTPROTOCOL        VARCHAR(100),
    DOCTYPE             VARCHAR(100),
    PICKBPID            VARCHAR(100),
    COREBPID            VARCHAR(100),
    DELIVERYBPID        VARCHAR(100),
    STATUS              VARCHAR(100),
    ERRORSTATUS         VARCHAR(500),
    ADVERRORSTATUS      VARCHAR(300),
    PARTNER             VARCHAR(100),
    DOCTRANS            VARCHAR(255),
    CORRELATION_VALUE_1 VARCHAR(MAX),
	CORRELATION_VALUE_2 VARCHAR(MAX),
	CORRELATION_VALUE_3 VARCHAR(MAX),
	CORRELATION_VALUE_4 VARCHAR(MAX),
	CORRELATION_VALUE_5 VARCHAR(MAX),
	CORRELATION_VALUE_6 VARCHAR(MAX),
	CORRELATION_VALUE_7 VARCHAR(MAX),
	CORRELATION_VALUE_8 VARCHAR(MAX),
	CORRELATION_VALUE_9 VARCHAR(MAX),
	CORRELATION_VALUE_10 VARCHAR(MAX),
	CORRELATION_VALUE_11 VARCHAR(MAX),
	CORRELATION_VALUE_12 VARCHAR(MAX),
	CORRELATION_VALUE_13 VARCHAR(MAX),
	CORRELATION_VALUE_14 VARCHAR(MAX),
	CORRELATION_VALUE_15 VARCHAR(MAX),
	CORRELATION_VALUE_16 VARCHAR(MAX),
	CORRELATION_VALUE_17 VARCHAR(MAX),
	CORRELATION_VALUE_18 VARCHAR(MAX),
	CORRELATION_VALUE_19 VARCHAR(MAX),
	CORRELATION_VALUE_20 VARCHAR(MAX),
	CORRELATION_VALUE_21 VARCHAR(MAX),
	CORRELATION_VALUE_22 VARCHAR(MAX),
	CORRELATION_VALUE_23 VARCHAR(MAX),
	CORRELATION_VALUE_24 VARCHAR(MAX),
	CORRELATION_VALUE_25 VARCHAR(MAX),
	TRANSFILE VARCHAR(200),
	STATUS_COMMENTS VARCHAR(500),
	IS_ENCRYPTED VARCHAR(1),
	CORRELATION_VALUE_26 VARCHAR(MAX),
	CORRELATION_VALUE_27 VARCHAR(MAX),
	CORRELATION_VALUE_28 VARCHAR(MAX),
	CORRELATION_VALUE_29 VARCHAR(MAX),
	CORRELATION_VALUE_30 VARCHAR(MAX),
	CORRELATION_VALUE_31 VARCHAR(MAX),
	CORRELATION_VALUE_32 VARCHAR(MAX),
	CORRELATION_VALUE_33 VARCHAR(MAX),
	CORRELATION_VALUE_34 VARCHAR(MAX),
	CORRELATION_VALUE_35 VARCHAR(MAX),
	CORRELATION_VALUE_36 VARCHAR(MAX),
	CORRELATION_VALUE_37 VARCHAR(MAX),
	CORRELATION_VALUE_38 VARCHAR(MAX),
	CORRELATION_VALUE_39 VARCHAR(MAX),
	CORRELATION_VALUE_40 VARCHAR(MAX),
	CORRELATION_VALUE_41 VARCHAR(MAX),
	CORRELATION_VALUE_42 VARCHAR(MAX),
	CORRELATION_VALUE_43 VARCHAR(MAX),
	CORRELATION_VALUE_44 VARCHAR(MAX),
	CORRELATION_VALUE_45 VARCHAR(MAX),
	CORRELATION_VALUE_46 VARCHAR(MAX),
	CORRELATION_VALUE_47 VARCHAR(MAX),
	CORRELATION_VALUE_48 VARCHAR(MAX),
	CORRELATION_VALUE_49 VARCHAR(MAX),
	CORRELATION_VALUE_50 VARCHAR(MAX),
	ISA_CTL VARCHAR(15),
	GS_CTL VARCHAR(15),
	ST_CTL VARCHAR(15),
	FA_REQ VARCHAR(1),
	SEG_CNT VARCHAR(9),
	APP_TRAN_NUM VARCHAR(50),
	APP_FA VARCHAR(15),
	XREF_NAME VARCHAR(30),
	XREF_LOOKUP VARCHAR(15),
	SRC_CHAR_CNT VARCHAR(20),
	TP_ISA_ID VARCHAR(50),
	MY_ISA_ID VARCHAR(50),
	APP_FA_STUS VARCHAR(15),
	APP_FA_DTM VARCHAR(15),
	DST_CHAR_CNT VARCHAR(20),
	DST_PROCL_CNT VARCHAR(20),
	BU_DIVNAME VARCHAR(20),
	TP_REF_ID VARCHAR(50),
	FA_DTM VARCHAR(50),
	DLE_DTM VARCHAR(15),
	PGP_KEY_NAME VARCHAR(200),
	PASS_PHRASE VARCHAR(100),
	ENC_TYPE VARCHAR(50),
	PRIMARY KEY (SEQID)
);
