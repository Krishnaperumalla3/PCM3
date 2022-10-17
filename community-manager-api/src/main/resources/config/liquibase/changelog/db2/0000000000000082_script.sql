-------------------------------------------------------------
--   DB2 DDL STATEMENT FOR 	PETPE_GOOGLE_DRIVE
-------------------------------------------------------------

CREATE TABLE PETPE_GOOGLE_DRIVE
(
    PK_ID                    VARCHAR(20)              NOT NULL,
    CLIENT_ID                VARCHAR(255)             NOT NULL,
    CLIENT_EMAIL             VARCHAR(255)             NOT NULL,
    PROJECT_ID               VARCHAR(255),
    SUBSCRIBER_ID            VARCHAR(255)             NOT NULL,
    SUBSCRIBER_TYPE          VARCHAR(255),
    FILE_TYPE                VARCHAR(255),
    IN_DIRECTORY             VARCHAR(255),
    INBOUND_CONNECTION_TYPE  VARCHAR(255),
    OUT_DIRECTORY            VARCHAR(255),
    OUTBOUND_CONNECTION_TYPE VARCHAR(255),
    POOLING_INTERVAL_MINS    VARCHAR(255)             NOT NULL,
    DELETE_AFTER_COLLECTION  VARCHAR(255) DEFAULT 'N',
    IS_ACTIVE                VARCHAR(1)   DEFAULT 'Y' NOT NULL,
    IS_HUB_INFO              VARCHAR(1)   DEFAULT 'N' NOT NULL,
    CREATED_BY               VARCHAR(255),
    LAST_UPDATED_BY          VARCHAR(255),
    LAST_UPDATED_DT          TIMESTAMP(6),
    CREDENTIAL_DIR           VARCHAR(255),
    AUTH_JSON                CLOB,
    PRIMARY KEY (PK_ID)
);
