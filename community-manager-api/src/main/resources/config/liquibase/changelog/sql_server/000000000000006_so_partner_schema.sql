--------------------------------------------------------
--  DDL for Table PETPE_SO_TRADINGPARTNER
--------------------------------------------------------
CREATE TABLE PETPE_SO_TRADINGPARTNER
   (    PK_ID VARCHAR(20) NOT NULL,
    TP_NAME VARCHAR(200) NOT NULL,
    TP_ID VARCHAR(200) NOT NULL,
    ADDRESS_LINE1 VARCHAR(100),
    ADDRESS_LINE2 VARCHAR(100),
    EMAIL VARCHAR(250) NOT NULL,
    PHONE VARCHAR(15) NOT NULL,
    TP_PROTOCOL VARCHAR(20) NOT NULL,
    TP_PICKUP_FILES VARCHAR(1) DEFAULT 'N' NOT NULL,
    FILE_TP_SERVER VARCHAR(1) DEFAULT 'N' NOT NULL,
    PARTNER_PROTOCOL_REF VARCHAR(20) NOT NULL,
    TP_IS_ACTIVE VARCHAR(1)  DEFAULT 'N' NOT NULL,
    CREATED_BY VARCHAR(15) NOT NULL,
    LAST_UPDATED_BY VARCHAR(15),
    LAST_UPDATED_DT DATETIME2 (6) , PRIMARY KEY (PK_ID)
   );