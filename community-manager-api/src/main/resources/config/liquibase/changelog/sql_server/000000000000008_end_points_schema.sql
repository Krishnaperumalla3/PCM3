--------------------------------------------------------
--  DDL for Table PETPE_ENDPOINTS
--------------------------------------------------------
CREATE TABLE PETPE_ENDPOINTS
   (    ENDPOINT_ID VARCHAR(20) NOT NULL,
    ENDPOINT_NAME VARCHAR(50) NOT NULL,
    BUSINESS_PROCESS_ID VARCHAR(120) NOT NULL,
    PROPERTY_NAME_1 VARCHAR(200),
    PROPERTY_NAME_2 VARCHAR(200),
    PROPERTY_NAME_3 VARCHAR(200),
    PROPERTY_NAME_4 VARCHAR(200),
    PROPERTY_NAME_5 VARCHAR(200),
    PROPERTY_NAME_6 VARCHAR(200),
    PROPERTY_NAME_7 VARCHAR(200),
    PROPERTY_NAME_8 VARCHAR(200),
    PROPERTY_NAME_9 VARCHAR(200),
    PROPERTY_NAME_10 VARCHAR(200),
    PROPERTY_NAME_11 VARCHAR(200),
    PROPERTY_NAME_12 VARCHAR(200),
    PROPERTY_NAME_13 VARCHAR(200),
    PROPERTY_NAME_14 VARCHAR(200),
    PROPERTY_NAME_15 VARCHAR(200), PRIMARY KEY (ENDPOINT_ID));

--------------------------------------------------------
--  DDL for Table PETPE_PROCESSENDPOINTS
--------------------------------------------------------
CREATE TABLE PETPE_PROCESSENDPOINTS
   (    PK_ID VARCHAR(20) NOT NULL,
    ENDPOINT_ID VARCHAR(20) NOT NULL,
    ENDPOINT_NAME VARCHAR(50) NOT NULL,
    PROPERTY_NAME_1 VARCHAR(500),
    PROPERTY_NAME_2 VARCHAR(200),
    PROPERTY_NAME_3 VARCHAR(200),
    PROPERTY_NAME_4 VARCHAR(200),
    PROPERTY_NAME_5 VARCHAR(200),
    PROPERTY_NAME_6 VARCHAR(200),
    PROPERTY_NAME_7 VARCHAR(200),
    PROPERTY_NAME_8 VARCHAR(200),
    PROPERTY_NAME_9 VARCHAR(200),
    PROPERTY_NAME_10 VARCHAR(200),
    PROPERTY_NAME_11 VARCHAR(200),
    PROPERTY_NAME_12 VARCHAR(200),
    PROPERTY_NAME_13 VARCHAR(200),
    PROPERTY_NAME_14 VARCHAR(200),
    PROPERTY_NAME_15 VARCHAR(200), PRIMARY KEY (PK_ID));

