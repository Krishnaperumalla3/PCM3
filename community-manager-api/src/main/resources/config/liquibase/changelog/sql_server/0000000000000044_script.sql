--------------------------------------------------------
--  DML STATEMENT
--------------------------------------------------------
sp_rename 'PETPE_SFG_CD.CONNECTION_TYPE_FLAG', 'INBOUND_CONNECTION_TYPE', 'COLUMN';

ALTER TABLE PETPE_SFG_CD
    ADD OUTBOUND_CONNECTION_TYPE VARCHAR(1);
