--------------------------------------------------------
--  DDL for Table PETPE_TRANSFERINFO
--------------------------------------------------------
CREATE TABLE PETPE_TRANSFERINFO
   (    SEQID NUMBER(11,0) NOT NULL ENABLE,
    FILEARRIVED TIMESTAMP (6),
    FLOWINOUT VARCHAR2(120 BYTE),
    TYPEOFTRANSFER VARCHAR2(100 BYTE),
    SENDERID VARCHAR2(100 BYTE),
    RECIVERID VARCHAR2(100 BYTE),
    APPLICATION VARCHAR2(100 BYTE),
    APPINTSTATUS VARCHAR2(100 BYTE),
    PARTNERACKSTATUS VARCHAR2(100 BYTE),
    SRCPROTOCOL VARCHAR2(100 BYTE),
    SRCFILENAME VARCHAR2(100 BYTE),
    SRCARCFILELOC VARCHAR2(500 BYTE),
    DESTFILENAME VARCHAR2(100 BYTE),
    DESTARCFILELOC VARCHAR2(500 BYTE),
    DESTPROTOCOL VARCHAR2(100 BYTE),
    DOCTYPE VARCHAR2(100 BYTE),
    PICKBPID VARCHAR2(100 BYTE),
    COREBPID VARCHAR2(100 BYTE),
    DELIVERYBPID VARCHAR2(100 BYTE),
    STATUS VARCHAR2(100 BYTE),
    ERRORSTATUS VARCHAR2(100 BYTE),
    ADVERRORSTATUS VARCHAR2(300 BYTE),
    PARTNER VARCHAR2(100 BYTE),
    DOCTRANS VARCHAR2(15 BYTE),
    CORRELATION_VALUE_1 CLOB,
    CORRELATION_VALUE_2 CLOB,
    CORRELATION_VALUE_3 CLOB,
    CORRELATION_VALUE_4 CLOB,
    CORRELATION_VALUE_5 CLOB,
    CORRELATION_VALUE_6 CLOB,
    CORRELATION_VALUE_7 CLOB,
    CORRELATION_VALUE_8 CLOB,
    CORRELATION_VALUE_9 CLOB,
    CORRELATION_VALUE_10 CLOB , PRIMARY KEY (SEQID)

   );

--------------------------------------------------------
--  DDL for Table PETPE_TRANSINFOD
--------------------------------------------------------
CREATE TABLE PETPE_TRANSINFOD
   (	"PK_ID" VARCHAR(50) NOT NULL,
	"BPID" VARCHAR2(256 BYTE),
    "RULENAME" VARCHAR2(256 BYTE),
    "BPNAME" VARCHAR2(256 BYTE),
    "DETAILS" VARCHAR2(4000 BYTE),
    "SEQUENCE" VARCHAR2(20 BYTE) , PRIMARY KEY (PK_ID)
   );
