--------------------------------------------------------
--  DDL STATEMENTS
--------------------------------------------------------
ALTER TABLE PETPE_WEBSERVICE ADD CA_CERT_NAME VARCHAR2(255);
ALTER TABLE PETPE_SFG_CD MODIFY CA_CERTIFICATE_ID VARCHAR2(500);
ALTER TABLE PETPE_CD ADD CA_CERTIFICATE_ID VARCHAR2(255);
ALTER TABLE PETPE_SFG_FTP ADD AUTH_USERKEY_ID VARCHAR2(255);
