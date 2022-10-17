-------------------------------------------------------------
--   ORACLE DDL STATEMENT FOR 	PETPE_SO_USERS_ROLE
-------------------------------------------------------------
CREATE TABLE PETPE_SO_USERS_ROLE
(
    ID          NUMBER(5, 0) NOT NULL ENABLE,
    NAME        VARCHAR2(30) NOT NULL ENABLE,
    DESCRIPTION VARCHAR2(50) NOT NULL ENABLE,
    CONSTRAINT PETPE_SO_USR_ROLE_NAM_CON UNIQUE (NAME),
    PRIMARY KEY (ID)
);
