--------------------------------------------------------
--  DDL for Table PETPE_ENDPOINTS
--------------------------------------------------------
CREATE TABLE "PETPE_ENDPOINTS"

   (    "ENDPOINT_ID" VARCHAR2(20 BYTE) NOT NULL ENABLE,
    "ENDPOINT_NAME" VARCHAR2(50 BYTE) NOT NULL ENABLE,
    "BUSINESS_PROCESS_ID" VARCHAR2(120 BYTE) NOT NULL ENABLE,
    "PROPERTY_NAME_1" VARCHAR2(200 BYTE),
    "PROPERTY_NAME_2" VARCHAR2(200 BYTE),
    "PROPERTY_NAME_3" VARCHAR2(200 BYTE),
    "PROPERTY_NAME_4" VARCHAR2(200 BYTE),
    "PROPERTY_NAME_5" VARCHAR2(200 BYTE),
    "PROPERTY_NAME_6" VARCHAR2(200 BYTE),
    "PROPERTY_NAME_7" VARCHAR2(200 BYTE),
    "PROPERTY_NAME_8" VARCHAR2(200 BYTE),
    "PROPERTY_NAME_9" VARCHAR2(200 BYTE),
    "PROPERTY_NAME_10" VARCHAR2(200 BYTE),
    "PROPERTY_NAME_11" VARCHAR2(200 BYTE),
    "PROPERTY_NAME_12" VARCHAR2(200 BYTE),
    "PROPERTY_NAME_13" VARCHAR2(200 BYTE),
    "PROPERTY_NAME_14" VARCHAR2(200 BYTE),
    "PROPERTY_NAME_15" VARCHAR2(200 BYTE),
     PRIMARY KEY ("ENDPOINT_ID"));

--------------------------------------------------------
--  DDL for Table PETPE_PROCESSENDPOINTS
--------------------------------------------------------

CREATE TABLE "PETPE_PROCESSENDPOINTS"
       ("PK_ID" VARCHAR2(20 BYTE) NOT NULL ENABLE,
    "ENDPOINT_ID" VARCHAR2(20 BYTE) NOT NULL ENABLE,
    "ENDPOINT_NAME" VARCHAR2(50 BYTE) NOT NULL ENABLE,
    "PROPERTY_NAME_1" VARCHAR2(500 BYTE),
    "PROPERTY_NAME_2" VARCHAR2(200 BYTE),
    "PROPERTY_NAME_3" VARCHAR2(200 BYTE),
    "PROPERTY_NAME_4" VARCHAR2(200 BYTE),
    "PROPERTY_NAME_5" VARCHAR2(200 BYTE),
    "PROPERTY_NAME_6" VARCHAR2(200 BYTE),
    "PROPERTY_NAME_7" VARCHAR2(200 BYTE),
    "PROPERTY_NAME_8" VARCHAR2(200 BYTE),
    "PROPERTY_NAME_9" VARCHAR2(200 BYTE),
    "PROPERTY_NAME_10" VARCHAR2(200 BYTE),
    "PROPERTY_NAME_11" VARCHAR2(200 BYTE),
    "PROPERTY_NAME_12" VARCHAR2(200 BYTE),
    "PROPERTY_NAME_13" VARCHAR2(200 BYTE),
    "PROPERTY_NAME_14" VARCHAR2(200 BYTE),
    "PROPERTY_NAME_15" VARCHAR2(200 BYTE),
     PRIMARY KEY ("PK_ID"));
