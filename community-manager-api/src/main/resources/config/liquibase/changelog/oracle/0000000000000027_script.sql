--------------------------------------------------------
--  DDL STATEMENTS
--------------------------------------------------------

ALTER TABLE PETPE_CORRELATION_NAMES
  ADD (CORRELATION_NAME_26 VARCHAR2(200 BYTE),
    CORRELATION_NAME_27 VARCHAR2(200 BYTE),
    CORRELATION_NAME_28 VARCHAR2(200 BYTE),
    CORRELATION_NAME_29 VARCHAR2(200 BYTE),
    CORRELATION_NAME_30 VARCHAR2(200 BYTE),
    CORRELATION_NAME_31 VARCHAR2(200 BYTE),
    CORRELATION_NAME_32 VARCHAR2(200 BYTE),
    CORRELATION_NAME_33 VARCHAR2(200 BYTE),
    CORRELATION_NAME_34 VARCHAR2(200 BYTE),
    CORRELATION_NAME_35 VARCHAR2(200 BYTE),
    CORRELATION_NAME_36 VARCHAR2(200 BYTE),
    CORRELATION_NAME_37 VARCHAR2(200 BYTE),
    CORRELATION_NAME_38 VARCHAR2(200 BYTE),
    CORRELATION_NAME_39 VARCHAR2(200 BYTE),
    CORRELATION_NAME_40 VARCHAR2(200 BYTE),
    CORRELATION_NAME_41 VARCHAR2(200 BYTE),
    CORRELATION_NAME_42 VARCHAR2(200 BYTE),
    CORRELATION_NAME_43 VARCHAR2(200 BYTE),
    CORRELATION_NAME_44 VARCHAR2(200 BYTE),
    CORRELATION_NAME_45 VARCHAR2(200 BYTE),
    CORRELATION_NAME_46 VARCHAR2(200 BYTE),
    CORRELATION_NAME_47 VARCHAR2(200 BYTE),
    CORRELATION_NAME_48 VARCHAR2(200 BYTE),
    CORRELATION_NAME_49 VARCHAR2(200 BYTE),
    CORRELATION_NAME_50 VARCHAR2(200 BYTE));
   
 ALTER TABLE PETPE_TRANSFERINFO
  ADD (CORRELATION_VALUE_26 CLOB,
    CORRELATION_VALUE_27 CLOB,
    CORRELATION_VALUE_28 CLOB,
    CORRELATION_VALUE_29 CLOB,
    CORRELATION_VALUE_30 CLOB,
    CORRELATION_VALUE_31 CLOB,
    CORRELATION_VALUE_32 CLOB,
    CORRELATION_VALUE_33 CLOB,
    CORRELATION_VALUE_34 CLOB,
    CORRELATION_VALUE_35 CLOB,
    CORRELATION_VALUE_36 CLOB,
    CORRELATION_VALUE_37 CLOB,
    CORRELATION_VALUE_38 CLOB,
    CORRELATION_VALUE_39 CLOB,
    CORRELATION_VALUE_40 CLOB,
    CORRELATION_VALUE_41 CLOB,
    CORRELATION_VALUE_42 CLOB,
    CORRELATION_VALUE_43 CLOB,
    CORRELATION_VALUE_44 CLOB,
    CORRELATION_VALUE_45 CLOB,
    CORRELATION_VALUE_46 CLOB,
    CORRELATION_VALUE_47 CLOB,
    CORRELATION_VALUE_48 CLOB,
    CORRELATION_VALUE_49 CLOB,
    CORRELATION_VALUE_50 CLOB);
   
   
 ALTER TABLE PETPE_TRANSFERINFO
  ADD (ISA_CONTROL_NUMBER VARCHAR2(15),
       GS_CONTROL_NUMBER VARCHAR2(15),
       ST_CONTROL_NUMBER VARCHAR2(15),
       FUNCTIONAL_ACK_DATE VARCHAR2(15),
       FUNCTIONAL_ACK_TIME VARCHAR2(6),
       FUNCTIONAL_ACK_REQUEST VARCHAR2(1),
       SEGMENT_COUNT VARCHAR2(9),
       IDOC_NUMBER VARCHAR2(15),
       SAP_FA VARCHAR2(15),
       XREF_NAME VARCHAR2(30),
       XREF_LOOKUP VARCHAR2(15));
   
   
ALTER TABLE PETPE_MQ ADD PORT VARCHAR2(30);
