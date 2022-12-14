--------------------------------------------------------
--  DDL for ALL Tables
--------------------------------------------------------
ALTER TABLE PETPE_FILESYSTEM ADD USER_NAME VARCHAR2(50);
ALTER TABLE PETPE_FILESYSTEM ADD PASSWORD VARCHAR2(50);

ALTER TABLE PETPE_HTTP ADD ADAPTER_NAME VARCHAR2(200);
ALTER TABLE PETPE_HTTP ADD POOLING_INTERVAL_MINS VARCHAR2(5);

ALTER TABLE PETPE_MAILBOX ADD FILTER VARCHAR2(200);
ALTER TABLE PETPE_MAILBOX ADD MBPARTNER_PRIORITY VARCHAR2(10);

ALTER TABLE PETPE_MQ ADD ADAPTER_NAME VARCHAR2(200);
ALTER TABLE PETPE_MQ ADD HOST_NAME VARCHAR2(200);

ALTER TABLE PETPE_PROCESSDOCS ADD VERSION VARCHAR2(100);

ALTER TABLE PETPE_PROCESSRULES ADD SEQ_ID NUMBER(3);
ALTER TABLE PETPE_PROCESSRULES ADD PROCESS_DOC_REF VARCHAR2(50);

ALTER TABLE PETPE_TRADINGPARTNER ADD PARTNER_PRIORITY VARCHAR2(50);

ALTER TABLE PETPE_TRANSFERINFO ADD CORRELATION_VALUE_11 CLOB;
ALTER TABLE PETPE_TRANSFERINFO ADD CORRELATION_VALUE_12 CLOB;
ALTER TABLE PETPE_TRANSFERINFO ADD CORRELATION_VALUE_13 CLOB;
ALTER TABLE PETPE_TRANSFERINFO ADD CORRELATION_VALUE_14 CLOB;
ALTER TABLE PETPE_TRANSFERINFO ADD CORRELATION_VALUE_15 CLOB;
ALTER TABLE PETPE_TRANSFERINFO ADD CORRELATION_VALUE_16 CLOB;
ALTER TABLE PETPE_TRANSFERINFO ADD CORRELATION_VALUE_17 CLOB;
ALTER TABLE PETPE_TRANSFERINFO ADD CORRELATION_VALUE_18 CLOB;
ALTER TABLE PETPE_TRANSFERINFO ADD CORRELATION_VALUE_19 CLOB;
ALTER TABLE PETPE_TRANSFERINFO ADD CORRELATION_VALUE_20 CLOB;
ALTER TABLE PETPE_TRANSFERINFO ADD CORRELATION_VALUE_21 CLOB;
ALTER TABLE PETPE_TRANSFERINFO ADD CORRELATION_VALUE_22 CLOB;
ALTER TABLE PETPE_TRANSFERINFO ADD CORRELATION_VALUE_23 CLOB;
ALTER TABLE PETPE_TRANSFERINFO ADD CORRELATION_VALUE_24 CLOB;
ALTER TABLE PETPE_TRANSFERINFO ADD CORRELATION_VALUE_25 CLOB;
ALTER TABLE PETPE_TRANSFERINFO ADD TRANSFILE VARCHAR2(200);
ALTER TABLE PETPE_TRANSFERINFO ADD STATUS_COMMENTS VARCHAR2(500);
ALTER TABLE PETPE_TRANSFERINFO ADD IS_ENCRYPTED VARCHAR(1);

ALTER TABLE PETPE_WEBSERVICE ADD OUTBOUND_URL VARCHAR2(500);
ALTER TABLE PETPE_WEBSERVICE ADD IN_DIRECTORY VARCHAR2(100);
ALTER TABLE PETPE_WEBSERVICE ADD CERTIFICATE VARCHAR2(200);
ALTER TABLE PETPE_WEBSERVICE ADD ADAPTER_NAME VARCHAR2(200) ;
ALTER TABLE PETPE_WEBSERVICE ADD POOLING_INTERVAL_MINS VARCHAR2(5);

ALTER TABLE PETPE_SFG_FTP ADD HOST_NAME VARCHAR2(100 BYTE);
ALTER TABLE PETPE_SFG_FTP ADD PORT_NO VARCHAR2(10 BYTE);
ALTER TABLE PETPE_SFG_FTP ADD CONNECTION_TYPE VARCHAR2(50 BYTE) DEFAULT 'active';
ALTER TABLE PETPE_SFG_FTP ADD RETRY_INTERVAL VARCHAR2(10 BYTE) DEFAULT '5';
ALTER TABLE PETPE_SFG_FTP ADD NO_OF_RETRIES VARCHAR2(10 BYTE) DEFAULT '5';
ALTER TABLE PETPE_SFG_FTP ADD ENCRYPTION_STRENGTH VARCHAR2(50 BYTE) DEFAULT 'ALL';
ALTER TABLE PETPE_SFG_FTP ADD USE_CCC CHAR(1 BYTE) DEFAULT 'N';
ALTER TABLE PETPE_SFG_FTP ADD USE_IMPLICIT_SSL CHAR(1 BYTE) DEFAULT 'N';

ALTER TABLE PETPE_RULES ADD CONSTRAINT PETPE_RULES_RULE_NAME_UNIQUE UNIQUE (RULE_NAME);

ALTER TABLE PETPE_SO_USERS ADD ACTIVATION_KEY VARCHAR2(250);

ALTER TABLE PETPE_AS2 MODIFY COMPRESS_DATA VARCHAR2(10);

ALTER TABLE PETPE_APPLICATION MODIFY APP_INTEGRATION_PROTOCOL VARCHAR2(20 BYTE);
