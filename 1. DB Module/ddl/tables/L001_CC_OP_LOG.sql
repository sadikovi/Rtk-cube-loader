-- Create table
create table L001_CC_OP_LOG
(
  prc_id      NUMBER(10),
  op_id       NUMBER(10),
  sender      VARCHAR2(4000 CHAR),
  action      VARCHAR2(4000 CHAR),
  msg         VARCHAR2(4000 CHAR),
  insert_date DATE default sysdate,
  msg_type    NUMBER(1),
  err_msg     VARCHAR2(4000 CHAR)
)
nologging;
-- Add comments to the table 
comment on table L001_CC_OP_LOG
  is 'Лог операций куб-лоудера';
