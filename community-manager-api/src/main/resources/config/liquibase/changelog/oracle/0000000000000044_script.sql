--------------------------------------------------------
--  DML STATEMENT
--------------------------------------------------------
ALTER TABLE PETPE_SFG_CD RENAME COLUMN CONNECTION_TYPE_FLAG TO INBOUND_CONNECTION_TYPE;

ALTER TABLE PETPE_SFG_CD
    ADD OUTBOUND_CONNECTION_TYPE VARCHAR2(1);
