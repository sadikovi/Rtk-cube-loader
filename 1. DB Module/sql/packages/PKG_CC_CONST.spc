create or replace package PKG_CC_CONST is
-----------------------------------------------------------------------------
-- skapitonenko 09.10.2013: пакет создан
--
-----------------------------------------------------------------------------
-- настройки логирования
  C_SEQ_LOG_OP_ID           constant varchar2(30 char) := 'SEQ_L001_OP_ID';
  C_lOG_LEVEL               constant NUMBER(1) := 0; 
   -- -1 - off
   --  0 - error, 
   --  1 - =|= + init and finish, 
   --  2 - =|= + info
   --  3 - =|= + vars
   --  4 - =|= + init and finish inner prc and fnc
   --  5 - =|= + inner info
   --  6 - =|= + inner vars
  
-- настройки кэша
   
  C_CASH_PM_TYPE            constant varchar2(30) := 'PM';        
  C_CASH_TBl_TYPE           constant varchar2(30) := 'TBL';
  C_CASH_COL_TYPE           constant varchar2(30) := 'COL';
  
-- настройки объектов
  C_PART_NAME_DELIMETER     constant varchar2(1) := '_';
  
  C_DIM_TBL_DESC_PRT        constant varchar2(4000) := 'Справочник ';
  C_DIM_ID_NAME             constant varchar2(30) := 'DIM_ID';
  C_DIM_ID_TYPE             constant varchar2(15) := 'NUMBER(10)';
  C_DIM_ID_DESC             constant varchar2(4000) := 'Dimension key';
  
  C_DIM_DUMMY_DIM_ID        constant varchar2(30) := '-1';
  C_DIM_DUMMY_REF_ID        constant varchar2(30) := '-1';
  C_DIM_DUMMY_VAL           constant varchar2(30) := null;--'UNKNOWN';
  
  C_CREATED_DATE_NAME       constant varchar2(30) := 'CREATED_DATE';
  C_CREATED_DATE_TYPE       constant varchar2(15) := 'DATE';
  C_CREATED_DATE_DESC       constant varchar2(4000) := 'Dimension key';
  C_CREATED_DATE_VAL        constant varchar2(30) := 'sysdate';
  
  C_FACT_TBL_DESC_PRT       constant varchar2(4000) := 'Показатели группы ';
  C_FACT_ID_NAME            constant varchar2(30) := 'FACT_ID';
  C_FACT_ID_TYPE            constant varchar2(15) := 'NUMBER(10)';
  C_FACT_ID_DESC            constant varchar2(4000) := 'Fact key';
  
  C_REF_ID_PART_NAME        constant varchar2(30) := 'DIM_ID';
  C_REF_ID_TYPE             constant varchar2(15) := 'NUMBER(10)';
  C_REF_ID_PART_DESC        constant varchar2(4000) := 'Dimension ref on ';

  C_SEQ_NAME_PART           constant varchar2(15) := 'SEQ';  
  C_SEQ_PREFIX_NAME_PART    constant varchar2(15) := 'SEQ_PFX';
  C_SEQ_MAXVALUE            constant number := 9999999999999999999999999999;
  
 -- префиксы
  C_PFX_PM_TYPE             constant varchar2(2) := 'PM';
  C_PFX_PM_PART             constant varchar2(2) := 'PM';
  C_PFX_PM_DGT_NUM          constant number(1) := 3;
  
  C_PFX_FACT_TBL_TYPE       constant varchar2(2) := 'FT';
  C_PFX_FACT_TBL_PART       constant varchar2(1) := 'F';
  C_PFX_FACT_TBL_DGT_NUM    constant number(1) := 3;
  
  C_PFX_DIM_TBL_TYPE        constant varchar2(2) := 'DT';
  C_PFX_DIM_TBL_PART        constant varchar2(1) := 'D';
  C_PFX_DIM_TBL_DGT_NUM     constant number(1) := 3;
  
  C_PFX_OBJ_TYPE            constant varchar2(2) := 'OB';
  C_PFX_OBJ_PART            constant varchar2(1) := 'O';
  C_PFX_OBJ_DGT_NUM         constant number(1) := 3;
  
  C_PFX_FACT_MEAS_TYPE      constant varchar2(2) := 'FM';
  C_PFX_FACT_MEAS_PART      constant varchar2(1) := 'M';
  C_PFX_FACT_MEAS_DGT_NUM   constant number(1) := 2;
  
  C_PFX_FACT_ATTR_TYPE      constant varchar2(2) := 'FA';
  C_PFX_FACT_ATTR_PART      constant varchar2(1) := 'A';
  C_PFX_FACT_ATTR_DGT_NUM   constant number(1) := 2;
  
  C_PFX_DIM_ATTR_TYPE       constant varchar2(2) := 'DA';
  C_PFX_DIM_ATTR_PART       constant varchar2(1) := 'A';
  C_PFX_DIM_ATTR_DGT_NUM    constant number(1) := 2;
  
-- мэпинг атрибутов
  
  C_ATTR_NAME_POS           constant number(10) := 1; -- позиция названия атрибута
  C_ATTR_DESC_POS           constant number(10) := 2; -- позиция описания атрибута
  C_ATTR_TYPE_POS           constant number(10) := 3; -- позиция типа значений атрибута
  C_ATTR_AGGRTYPE_POS       constant number(10) := 4; -- позиция типа агрегации атрибута
  C_ATTR_GRP_POS            constant number(10) := 5; -- позиция группы атрибута
  C_ATTR_GRPPOS_POS         constant number(10) := 6; -- позиция позиции атрибута в группе
  C_ATTR_PRNTGRPPOS_POS     constant number(10) := 7; -- позиция родительского справочника атрибута
  C_ATTR_XMLPOS_POS         constant number(10) := 8; -- позиция позиции атрибута
  C_ATTR_PIVOTPOS_POS       constant number(10) := 9; -- позиция позиции колонок pivot-измерения
  C_ATTR_PIVOTROW_POS       constant number(10) := 10; -- позиция номера строки pivot-измерения
  C_ATTR_CTYPE_POS          constant number(10) := 11; -- позиция типа атрибута
  C_ATTR_POSINGRP_POS       constant number(10) := 12; -- позиция позиции атрибута на листе


  
  
-- Разделители
  -- в строке структуры
  C_PM_NAME_SEPARATOR      constant varchar2(6) := ',';  -- ;
  C_COL_SEPARATOR          constant varchar2(6) := ';';  -- ;
  C_ATTR_SEPARATOR         constant varchar2(6) := ':'; -- :
  
  C_PRNT_SEPARATOR         constant varchar2(6) := ','; -- ,
  
  C_COMM_SEPARATOR          constant varchar2(6) := ';';  -- ;
  C_COMM_PRT_SEPARATOR     constant varchar2(6) := ':'; -- :
  
  -- в строке значений
  C_VAL_SEPARATOR          constant varchar2(6) := '#';
  
-- Декодирование строк
  -- ":", ";", "'" и "#"
  C_COL_SEPARATOR_REPLACE  constant varchar2(6) := 'U+003B';  -- ;
  C_ATTR_SEPARATOR_REPLACE constant varchar2(6) := 'U+003A'; -- :
  
  C_COMM_SEPARATOR_REPLACE constant varchar2(6) := 'U+003B';  -- ;
  C_COMM_PRT_SEPARATOR_REPLACE constant varchar2(6) := 'U+003A'; -- :
  
  C_VAL_SEPARATOR_REPLACE  constant varchar2(6) := 'U+266F'; -- #
  C_QUOT_REPLACE            constant varchar2(6) := 'U+0027'; -- '

-- XML  
  -- Тэги
  C_CUBE_TAG                constant varchar2(10) := 'cube';
   C_TABLES_TAG             constant varchar2(10) := 'tables';
    C_TABLE_TAG             constant varchar2(10) := 'table'; 
    C_TABLE_COLS_TAG        constant varchar2(10) := 'cols';
     C_COL_TAG              constant varchar2(10) := 'col';
   
      -- Атрибуты
      C_CUBE_ATTR1          constant varchar2(10) := 'name';
      C_CUBE_ATTR2          constant varchar2(10) := 'desc';
      C_CUBE_ATTR3          constant varchar2(10) := 'isNewModel';
       
      C_TABLE_ATTR1         constant varchar2(10) := 'name';
      C_TABLE_ATTR2         constant varchar2(10) := 'desc';
      C_TABLE_ATTR3         constant varchar2(10) := 'type';
      C_TABLE_ATTR4         constant varchar2(10) := 'group';
       C_TABLE_TYPE_VAL1    constant varchar2(10) := 'fact';
       C_TABLE_TYPE_VAL2    constant varchar2(10) := 'dim';
       C_TABLE_TYPE_VAL3    constant varchar2(10) := 'dummy_fact';
       C_TABLE_TYPE_VAL4    constant varchar2(10) := 'parent_dim';
       C_TABLE_TYPE_VAL5    constant varchar2(10) := 'pivot_dim';
       
      C_COL_ATTR1           constant varchar2(20) := 'name';
      C_COL_ATTR2           constant varchar2(20) := 'type';
      C_COL_ATTR3           constant varchar2(20) := 'desc';
      C_COL_ATTR4           constant varchar2(20) := 'isPrimary';
      C_COL_ATTR5           constant varchar2(20) := 'isForeignkey';
      C_COL_ATTR6           constant varchar2(20) := 'aggrType';
      C_COL_ATTR7           constant varchar2(20) := 'refTableName';
  
end PKG_CC_CONST;
/
