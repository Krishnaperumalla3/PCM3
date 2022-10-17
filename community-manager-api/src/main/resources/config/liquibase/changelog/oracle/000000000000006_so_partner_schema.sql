--------------------------------------------------------
--  DDL for Table PETPE_SO_TRADINGPARTNER
--------------------------------------------------------
CREATE TABLE PETPE_SO_TRADINGPARTNER
   (    PK_ID VARCHAR2(20 BYTE) NOT NULL ENABLE,
    TP_NAME VARCHAR2(200 BYTE) NOT NULL ENABLE,
    TP_ID VARCHAR2(200 BYTE) NOT NULL ENABLE,
    ADDRESS_LINE1 VARCHAR2(100 BYTE),
    ADDRESS_LINE2 VARCHAR2(100 BYTE),
    EMAIL VARCHAR2(250 BYTE) NOT NULL ENABLE,
    PHONE VARCHAR2(15 BYTE) NOT NULL ENABLE,
    TP_PROTOCOL VARCHAR2(20 BYTE) NOT NULL ENABLE,
    TP_PICKUP_FILES VARCHAR2(1 BYTE) DEFAULT 'N' NOT NULL ENABLE,
    FILE_TP_SERVER VARCHAR2(1 BYTE) DEFAULT 'N' NOT NULL ENABLE,
    PARTNER_PROTOCOL_REF VARCHAR2(20 BYTE) NOT NULL ENABLE,
    TP_IS_ACTIVE VARCHAR2(1 BYTE)  DEFAULT 'N' NOT NULL ENABLE,
    CREATED_BY VARCHAR2(15 BYTE) NOT NULL ENABLE,
    LAST_UPDATED_BY VARCHAR2(15 BYTE),
    LAST_UPDATED_DT TIMESTAMP (6) , PRIMARY KEY (PK_ID)
   );