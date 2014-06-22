create or replace package PKG_CC_LOG is
-----------------------------------------------------------------------------
-- skapitonenko 25.09.2013: ����� ������
-- 
-----------------------------------------------------------------------------

-----------------------------------------------------------------------------  
-- ���������
      -- ��������� ���� ��������:
  C_OP_LOG_INIT_MSG        constant number(3) := 0;   -- ��������� � ������ ��������
  C_OP_LOG_SUCCESS_MSG     constant number(3) := 1;   -- ��������� �� �������� ���������
  C_OP_LOG_ERROR_MSG       constant number(3) := 2;   -- ��������� �� ������ � ���� ���������� ��������
  C_OP_LOG_PARAM           constant number(3) := 3;   -- ��������
  C_OP_LOG_WARNING         constant number(3) := 4;   -- ��������� �� ������, �� ���������� ��������� ������ ��������
  C_OP_LOG_INFO_MSG        constant number(3) := 5;   -- �������������� ���������
  C_OP_LOG_VAR             constant number(3) := 6;   -- ����������
-----------------------------------------------------------------------------  
  
  -- ��������� session id
  function get_session_id return NUMBER;
  
  
  -- �������� ��������� � ��� ��������...
  procedure op_log(
    prc_id    in l001_cc_op_log.prc_id%type,
    op_id     in l001_cc_op_log.op_id%type,
    sender    in l001_cc_op_log.sender%type,
    action    in l001_cc_op_log.action%type,
    msg       in l001_cc_op_log.msg%type,
    msg_type  in l001_cc_op_log.msg_type%type,
    err_msg   in l001_cc_op_log.err_msg%type
  );
  
   
end PKG_CC_LOG;
/
create or replace package body PKG_CC_LOG is
  
  --��������� ������ oracle-������
  function get_session_id return NUMBER
  is
    sid number;
  begin
	  select 
      sys_context('USERENV','SID') 
    into sid
    from dual;
    
    return sid;
  exception
    when others then
      return null;
  end get_session_id;
  
  -- �������� ��������� � ��� ��������...
  procedure op_log(
    prc_id   in  l001_cc_op_log.prc_id%type,
    op_id     in l001_cc_op_log.op_id%type,
    sender    in l001_cc_op_log.sender%type,
    action    in l001_cc_op_log.action%type,
    msg       in l001_cc_op_log.msg%type,
    msg_type  in l001_cc_op_log.msg_type%type,
    err_msg   in l001_cc_op_log.err_msg%type
  )
  is
    PRAGMA AUTONOMOUS_TRANSACTION;
    
    opid number;
  begin
    opid := op_id;
     
    if (opid is null) then
      select seq_l001_op_id.nextval into opid from dual;
    end if;

    insert into l001_cc_op_log values(prc_id, opid, sender, action, msg, sysdate, msg_type, err_msg);

    commit;
    
  end  op_log;
  

end PKG_CC_LOG;
/
