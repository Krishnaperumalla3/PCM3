-------------------------------------------------------------
--   DB2 DDL STATEMENT FOR 	PETPE_TRANS_CLOUD_ARC
-------------------------------------------------------------

CREATE TABLE PETPE_TRANS_CLOUD_ARC
(
    PK_ID           VARCHAR(20)  NOT NULL,
    TRA_SCH_REF     VARCHAR(20)  NOT NULL,
    SRC_ROOT_DIR    VARCHAR(155) NOT NULL,
    DEST_ROOT_DIR   VARCHAR(155) NOT NULL,
    CREATED_BY      VARCHAR(255),
    LAST_UPDATED_BY VARCHAR(255),
    LAST_UPDATED_DT TIMESTAMP(6),
    PRIMARY KEY (PK_ID)
) IN PCMTBSPACE;