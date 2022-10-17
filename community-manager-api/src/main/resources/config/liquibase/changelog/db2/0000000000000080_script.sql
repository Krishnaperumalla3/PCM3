-------------------------------------------------------------
--   DB2 DDL STATEMENT FOR 	PETPE_SO_USERS_ROLE
-------------------------------------------------------------
CREATE TABLE PETPE_SO_USERS_ROLE
(
    ID          SMALLINT    NOT NULL,
    NAME        VARCHAR(30) NOT NULL,
    DESCRIPTION VARCHAR(50) NOT NULL,
    CONSTRAINT PETPE_SO_USR_ROLE_NAM_CON UNIQUE (NAME),
    PRIMARY KEY (ID)
);
