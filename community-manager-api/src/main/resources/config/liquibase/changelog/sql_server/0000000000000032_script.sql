--------------------------------------------------------
--  DDL STATEMENT
--------------------------------------------------------
ALTER TABLE PETPE_SO_USERS ADD LANG VARCHAR(50);

UPDATE PETPE_SO_USERS SET LANG = 'en';
