-- Create table
create table D001_CC_PM_INFO
(
  d001_name          VARCHAR2(30 CHAR),
  d001_desc          VARCHAR2(255 CHAR),
  d001_parent        VARCHAR2(255 CHAR),
  prc_id             NUMBER(10),
  d001_db_xml_doc    CLOB,
  d001_excel_xml_doc CLOB,
  created_date       DATE default sysdate
)
nologging;
-- Add comments to the table 
comment on table D001_CC_PM_INFO
  is 'Справочник моделей';
