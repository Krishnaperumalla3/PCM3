--------------------------------------------------------
--  DDL for ALL Tables
--------------------------------------------------------
ALTER TABLE PETPE_FILESYSTEM ADD USER_NAME VARCHAR(50);
ALTER TABLE PETPE_FILESYSTEM ADD PASSWORD VARCHAR(50);

ALTER TABLE PETPE_HTTP ADD ADAPTER_NAME VARCHAR(200);
ALTER TABLE PETPE_HTTP ADD POOLING_INTERVAL_MINS VARCHAR(5);

ALTER TABLE PETPE_MAILBOX ADD FILTER VARCHAR(200);
ALTER TABLE PETPE_MAILBOX ADD MBPARTNER_PRIORITY VARCHAR(10);

ALTER TABLE PETPE_MQ ADD ADAPTER_NAME VARCHAR(200);
ALTER TABLE PETPE_MQ ADD HOST_NAME VARCHAR(200);

ALTER TABLE PETPE_PROCESSDOCS ADD VERSION VARCHAR(100);

ALTER TABLE PETPE_PROCESSRULES ADD SEQ_ID INTEGER;
ALTER TABLE PETPE_PROCESSRULES ADD PROCESS_DOC_REF VARCHAR(50);

ALTER TABLE PETPE_TRADINGPARTNER ADD PARTNER_PRIORITY VARCHAR(50);

ALTER TABLE PETPE_TRANSFERINFO ADD CORRELATION_VALUE_11 VARCHAR(max);
ALTER TABLE PETPE_TRANSFERINFO ADD CORRELATION_VALUE_12 VARCHAR(max);
ALTER TABLE PETPE_TRANSFERINFO ADD CORRELATION_VALUE_13 VARCHAR(max);
ALTER TABLE PETPE_TRANSFERINFO ADD CORRELATION_VALUE_14 VARCHAR(max);
ALTER TABLE PETPE_TRANSFERINFO ADD CORRELATION_VALUE_15 VARCHAR(max);
ALTER TABLE PETPE_TRANSFERINFO ADD CORRELATION_VALUE_16 VARCHAR(max);
ALTER TABLE PETPE_TRANSFERINFO ADD CORRELATION_VALUE_17 VARCHAR(max);
ALTER TABLE PETPE_TRANSFERINFO ADD CORRELATION_VALUE_18 VARCHAR(max);
ALTER TABLE PETPE_TRANSFERINFO ADD CORRELATION_VALUE_19 VARCHAR(max);
ALTER TABLE PETPE_TRANSFERINFO ADD CORRELATION_VALUE_20 VARCHAR(max);
ALTER TABLE PETPE_TRANSFERINFO ADD CORRELATION_VALUE_21 VARCHAR(max);
ALTER TABLE PETPE_TRANSFERINFO ADD CORRELATION_VALUE_22 VARCHAR(max);
ALTER TABLE PETPE_TRANSFERINFO ADD CORRELATION_VALUE_23 VARCHAR(max);
ALTER TABLE PETPE_TRANSFERINFO ADD CORRELATION_VALUE_24 VARCHAR(max);
ALTER TABLE PETPE_TRANSFERINFO ADD CORRELATION_VALUE_25 VARCHAR(max);
ALTER TABLE PETPE_TRANSFERINFO ADD TRANSFILE VARCHAR(200);
ALTER TABLE PETPE_TRANSFERINFO ADD STATUS_COMMENTS VARCHAR(500);
ALTER TABLE PETPE_TRANSFERINFO ADD IS_ENCRYPTED VARCHAR(1);

ALTER TABLE PETPE_WEBSERVICE ADD OUTBOUND_URL VARCHAR(500);
ALTER TABLE PETPE_WEBSERVICE ADD IN_DIRECTORY VARCHAR(100);
ALTER TABLE PETPE_WEBSERVICE ADD CERTIFICATE VARCHAR(200);
ALTER TABLE PETPE_WEBSERVICE ADD ADAPTER_NAME VARCHAR(200) ;
ALTER TABLE PETPE_WEBSERVICE ADD POOLING_INTERVAL_MINS VARCHAR(5);

ALTER TABLE PETPE_SFG_FTP ADD HOST_NAME VARCHAR(100);
ALTER TABLE PETPE_SFG_FTP ADD PORT_NO VARCHAR(10);
ALTER TABLE PETPE_SFG_FTP ADD CONNECTION_TYPE VARCHAR(50) DEFAULT 'active';
ALTER TABLE PETPE_SFG_FTP ADD RETRY_INTERVAL VARCHAR(10) DEFAULT '5';
ALTER TABLE PETPE_SFG_FTP ADD NO_OF_RETRIES VARCHAR(10) DEFAULT '5';
ALTER TABLE PETPE_SFG_FTP ADD ENCRYPTION_STRENGTH VARCHAR(50) DEFAULT 'ALL';
ALTER TABLE PETPE_SFG_FTP ADD USE_CCC CHAR(1) DEFAULT 'N';
ALTER TABLE PETPE_SFG_FTP ADD USE_IMPLICIT_SSL CHAR(1) DEFAULT 'N';

ALTER TABLE PETPE_RULES ADD CONSTRAINT PETPE_RULES_RULE_NAME_UNIQUE UNIQUE (RULE_NAME);

ALTER TABLE PETPE_SO_USERS ADD ACTIVATION_KEY VARCHAR(250);

ALTER TABLE PETPE_AS2 ALTER COLUMN COMPRESS_DATA VARCHAR(10);

ALTER TABLE PETPE_APPLICATION ALTER COLUMN APP_INTEGRATION_PROTOCOL VARCHAR(20);
