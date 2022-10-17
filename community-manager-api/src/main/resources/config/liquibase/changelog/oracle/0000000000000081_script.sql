-------------------------------------------------------------
--   ORACLE DDL,DML STATEMENT FOR 	PETPE_SO_USERS_ROLE
-------------------------------------------------------------

ALTER TABLE PETPE_SO_USERS_ROLE RENAME COLUMN DESCRIPTION TO NAME_DES;

INSERT INTO PETPE_SO_USERS_ROLE (ID,NAME,NAME_DES) VALUES (1,'super_admin','Super Admin');
INSERT INTO PETPE_SO_USERS_ROLE (ID,NAME,NAME_DES) VALUES (2,'admin','Admin');
INSERT INTO PETPE_SO_USERS_ROLE (ID,NAME,NAME_DES) VALUES (3,'on_boarder','On Boarder');
INSERT INTO PETPE_SO_USERS_ROLE (ID,NAME,NAME_DES) VALUES (4,'business_admin','Business Admin');
INSERT INTO PETPE_SO_USERS_ROLE (ID,NAME,NAME_DES) VALUES (5,'business_user','Business User');
--INSERT INTO PETPE_SO_USERS_ROLE (ID,NAME,NAME_DES) VALUES (6,'file_operator','File Operator');
INSERT INTO PETPE_SO_USERS_ROLE (ID,NAME,NAME_DES) VALUES (7,'data_processor','Data Processor');
INSERT INTO PETPE_SO_USERS_ROLE (ID,NAME,NAME_DES) VALUES (8,'data_processor_restricted','Data Processor Restricted');
