--------------------------------------------------------
--  DDL for Table PETPE_SFG_FTP
--------------------------------------------------------
ALTER TABLE PETPE_SFG_FTP ADD AUTH_USER_KEYS VARCHAR2(355);
ALTER TABLE PETPE_SFG_FTP ADD CA_CERT_ID VARCHAR2(100);
ALTER TABLE PETPE_SFG_FTP ADD KNOWN_HOST_KEY_ID VARCHAR2(100);
ALTER TABLE PETPE_SFG_FTP ADD USER_IDENTITY_KEY_ID VARCHAR2(100);

--------------------------------------------------------
--  DDL for Table PETPE_FTP
--------------------------------------------------------
ALTER TABLE PETPE_FTP ADD CA_CERT_NAME VARCHAR2(100);
ALTER TABLE PETPE_FTP ADD KNOWN_HOST_KEY_ID VARCHAR2(100);
ALTER TABLE PETPE_FTP ADD SSH_IDENTITY_KEY_NAME VARCHAR2(100);

--------------------------------------------------------
--  DDL for Table PETPE_HTTP
--------------------------------------------------------
ALTER TABLE PETPE_HTTP ADD CERTIFICATE_ID VARCHAR2(100);

--------------------------------------------------------
--  DDL for Table PETPE_AS2
--------------------------------------------------------
ALTER TABLE PETPE_AS2 ADD CA_CERT_ID VARCHAR2(100);
ALTER TABLE PETPE_AS2 ADD EXCHG_CERT_NAME VARCHAR2(100);
ALTER TABLE PETPE_AS2 ADD SIGNING_CERT_NAME VARCHAR2(100);

--------------------------------------------------------
--  DDL for Table PETPE_SFG_CD
--------------------------------------------------------
ALTER TABLE PETPE_SFG_CD ADD CA_CERTIFICATE_ID VARCHAR2(100);
