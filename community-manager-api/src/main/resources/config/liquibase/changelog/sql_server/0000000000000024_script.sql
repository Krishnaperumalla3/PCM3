--------------------------------------------------------
--  DDL STATEMENTS
--------------------------------------------------------
ALTER TABLE PETPE_WEBSERVICE ADD CA_CERT_NAME VARCHAR(255);
ALTER TABLE PETPE_SFG_CD ALTER COLUMN CA_CERTIFICATE_ID VARCHAR(500);
ALTER TABLE PETPE_CD ADD CA_CERTIFICATE_ID VARCHAR(255);
ALTER TABLE PETPE_SFG_FTP ADD AUTH_USERKEY_ID VARCHAR(255);
