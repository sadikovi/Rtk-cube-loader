spool deploy_cube_constructor_log.log

set serveroutput on size 100000 
set timing on
set pagesize 1000
set long 1000
set linesize 1000


CONNECT SERVICES_DM/SERVICES_DM@DWHQA


PROMPT "Создание таблиц"
PROMPT

PROMPT <<<L001_CC_OP_LOG.sql>>>
@@ddl/tables/L001_CC_OP_LOG.sql
PROMPT

PROMPT <<<D001_CС_PM_INFO.sql>>>
@@ddl/tables/D001_CС_PM_INFO.sql

PROMPT "Создание последовательностей"
PROMPT

PROMPT <<<SEQ_L001_OP_ID.sql>>>
@@ddl/seq/SEQ_L001_OP_ID.sql
PROMPT

PROMPT <<<SEQ_PRC_ID.sql>>>
@@ddl/seq/SEQ_PRC_ID.sql
PROMPT

PROMPT <<<SEQ_PFX_PM.sql>>>
@@ddl/seq/SEQ_PFX_PM.sql

PROMPT "Создание пакетов"
PROMPT

PROMPT <<<PKG_CC_LOG.pck>>>
@@sql/Packages/PKG_CC_LOG.pck
PROMPT

PROMPT <<<PKG_CC_CONST.spc>>>
@@sql/Packages/PKG_CC_CONST.spc
PROMPT

PROMPT <<<PKG_CC_STRUCTURE.pck>>>
@@sql/Packages/PKG_CC_STRUCTURE.pck

DISCONNECT

spool off
exit