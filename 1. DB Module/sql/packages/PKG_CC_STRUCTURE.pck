create or replace package PKG_CC_STRUCTURE is
-----------------------------------------------------------------------------
-- skapitonenko 25.09.2013: пакет создан
-- 
-----------------------------------------------------------------------------

-----------------------------------------------------------------------------  
-- Константы

-- КЭШ
     -- для кеширования родительских справочников и непустых таблиц фактов
  type tNumberTable is table of number(10) index by PLS_INTEGER; 
  type tNumberTable_2 is table of tNumberTable index by PLS_INTEGER; 
  type tNumberTable_3 is table of tNumberTable_2 index by PLS_INTEGER; 
  type tNumberTable_4 is table of tNumberTable_3 index by PLS_INTEGER; 
  
  -- для кеширования названий таблиц, колонок, и префикса модели
  type tCharTable is table of varchar2(32767) index by PLS_INTEGER;
  type tCharTable_2 is table of tCharTable index by PLS_INTEGER;
  type tCharTable_3 is table of tCharTable_2 index by PLS_INTEGER;
  type tCharTable_4 is table of tCharTable_3 index by PLS_INTEGER;
  
  g_GetIsRepeatDimPrcCol         tNumberTable_2;   -- флаг повтора справочника
  
  g_GetGrpByPrcIx               tNumberTable_2;   -- группы в xml
  
  g_tmpDimNameIx                tCharTable;       -- для опреления повотором справочников
  
  g_GetColGrpPosByPrcGrpIx      tNumberTable_3;   -- колонки в группе
  
  g_GetColXmlPosByPrcGrpCol     tNumberTable_3;   -- привязка позиции колонки в xml к позиции в группе
  
  g_GetGrpByPrcCol              tNumberTable_2;   -- привязка номера группы к позиции колонки в xml
  g_GetColGrpPosByPrcCol        tNumberTable_2;   -- привязка позиции колонки в группе к позиции в xml
  
  g_GetAttrByPrcGrpColIx        tCharTable_4;     -- атрибуты колонки по позициям атрибутов у позиции колонки в группе 

  g_GetPmPfxByPrc               tCharTable;       -- кэш префиксов  
  g_GetTblNameByPrcCol          tCharTable_2;     -- кэш названий таблиц
  g_GetColNameByPrcCol          tCharTable_2;     -- кэш названий колонок
  
  g_GetVirtColPosByPrcCol       tNumberTable_2;   -- подмена позиции для повторяющихся справочников
  
  g_GetNotDummyFactByPrcIx      tNumberTable_2;   -- список групп с непустыми таблицами фактов
  
  g_GetPrntXmlPosByPrcIx        tNumberTable_2;   -- список позиций родительских справочников в xml
  g_GetPrntGrpPosByPrcGrpIx     tNumberTable_3;   -- список позиций родительских справочников в группе

  g_GetChldGrpPosByPrcGrpColIx  tNumberTable_4;   -- список позиции подчиненных справочников в группах
  
  g_GetChldXmlPosByPrcColIx     tNumberTable_3;   -- список позиций подчиненных справочников в xml  

  -- для очистки
  g_NumEmpty                    tNumberTable;
  g_NumEmpty_2                  tNumberTable_2;
  g_NumEmpty_3                  tNumberTable_3;
  g_NumEmpty_4                  tNumberTable_4;
  
  g_CharEmpty                   tCharTable;
  g_CharEmpty_2                 tCharTable_2;
  g_CharEmpty_3                 tCharTable_3;
  g_CharEmpty_4                 tCharTable_4;
-----------------------------------------------------------------------------  
 
  function fnc_get_cnt_by_separator(
    p_prc_id in l001_cc_op_log.prc_id%type,
    p_str in varchar2,
    p_sptr in varchar2
  )  
  return number;
  
  function fnc_boolToChar(
		p_bool boolean
  ) return varchar2;
  
  function fnc_get_pm_names(
    p_prc_id in l001_cc_op_log.prc_id%type,
	  p_user in varchar2
  ) return varchar2;
  
  -- xml структуры модели для дозагрузки
  function fnc_get_model_xml(
    p_prc_id in l001_cc_op_log.prc_id%type,
    p_pm_name in varchar2
  ) return clob;
  
  function fnc_get_tbl_name_by_pos_num ( 
    p_prc_id in l001_cc_op_log.prc_id%type,
		p_pfx_pm in varchar2,
    p_pos_num in number
  ) return varchar2;
  
  function fnc_get_col_name_by_pos_num (
    p_prc_id in l001_cc_op_log.prc_id%type,
		p_pfx_pm in varchar2,
    p_pos_num in number
  ) return varchar2;
  
  -- нормализация префикса 
  function fnc_get_prefix_by_num(
    p_prc_id in l001_cc_op_log.prc_id%type,
    p_pfx_type in varchar2,
    p_num in number
  ) return varchar2;
  
  -- поиск префикса
  function fnc_get_prefix_by_name(
    p_prc_id in l001_cc_op_log.prc_id%type,
    p_get_pfx_type in varchar2,
    p_pm_name  in varchar2,
    p_tbl_name in varchar2,
    p_col_name in varchar2,
    p_col_pos in number
  ) return varchar2;
  
  -- генерация префиков для таблиц
  function fnc_get_next_prefix(
    p_prc_id in l001_cc_op_log.prc_id%type,
    p_get_pfx_type in varchar2,
    p_pfx_pm  in varchar2,
    p_pfx_tbl in varchar2
  ) return varchar2;
  
  procedure prc_reset_seq (
    p_prc_id in l001_cc_op_log.prc_id%type,
    p_seq_name in varchar2 
  );
  
    -- процедура создания таблицы
  procedure prc_create_table (
    p_prc_id in l001_cc_op_log.prc_id%type,
    p_tbl_name varchar2,
    p_tbl_desc varchar2,
    p_tbl_cols varchar2,
    p_tbl_comms varchar2
  );
  
  function fnc_create_cube (
		p_prc_id in l001_cc_op_log.prc_id%type,
    p_pm_name in varchar2,
    p_pm_desc in varchar2,
    p_excel_xml_doc in clob,
    p_user in varchar2
  ) return Clob;
  
  -- основной метод загрузки куба
  function fnc_load_cube (
    p_prc_id in l001_cc_op_log.prc_id%type,
    p_pm_name varchar2,
    p_values clob,
    p_is_first_load number
  ) return number;
  
  procedure prc_drop_cube (
    p_prc_id in l001_cc_op_log.prc_id%type,
    p_pfx_pm in varchar2
  );
  
end PKG_CC_STRUCTURE;
/
create or replace package body PKG_CC_STRUCTURE is
  
/*  procedure template_prc (
    prc_id in l001_cc_op_log.prc_id%type)
  is
     logmsg  varchar2(255) := 'краткое описание, например "Процедура работы с индексами в таблице F001_ACSI"';
     logsender varchar2(255) := 'services_load.template_prc';
  begin
		
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Старт процедуры', logmsg, pkg_cc_log.C_OP_LOG_INIT_MSG, null);    
end if;
    
    null;
    
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then 
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Завершение процедуры', logmsg, pkg_cc_log.C_OP_LOG_SUCCESS_MSG, null);
end if;

  exception
    when others then

-- log
if (pkg_cc_const.C_lOG_LEVEL >= 0) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Ошибки процедуры', logmsg, pkg_cc_log.C_OP_LOG_ERROR_MSG, sqlerrm);
end if;

      raise;
  end template_prc;*/
  
     doc dbms_xmldom.DOMDocument;
     doc_node dbms_xmldom.DOMNode;
        
     cube_el dbms_xmldom.DOMElement;
      tbls_el dbms_xmldom.DOMElement;
     
     cube_nd dbms_xmldom.DOMNode;
      tbls_nd dbms_xmldom.DOMNode;
         
  function fnc_boolToChar(
		p_bool boolean
  ) return varchar2
  is 
  begin
		if (p_bool) then
			return 'TRUE';
    else
	  	return 'FALSE';
    end if;
  end fnc_boolToChar;
  
  -- список моделей 
  function fnc_get_cnt_by_separator(
    p_prc_id in l001_cc_op_log.prc_id%type,
    p_str in varchar2,
    p_sptr in varchar2
  )  
  return number
  is
     logmsg  varchar2(255) := 'Список моделей';
     logsender varchar2(255) := 'pkg_cc_structure.fnc_get_cnt_by_separator'; 
     
     l_cnt number;
     l_str varchar2(32767);
  begin
    
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Старт функции', logmsg, pkg_cc_log.C_OP_LOG_INIT_MSG, null);    
end if;
 
 -- входные параметры
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'IN p_str=' || p_str, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'IN p_sptr=' || p_sptr, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
end if;
    
    l_str := rtrim(ltrim(p_str, p_sptr), p_sptr);
    
    if (l_str is not null) then
      l_cnt := 1;
      
      while (instr(l_str, p_sptr, 1, l_cnt) != 0)
        loop
          l_cnt := l_cnt + 1;
        end loop;
    end if;
      
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '  l_cnt=' || l_cnt, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
end if;
 
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then 
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Завершение функции', logmsg, pkg_cc_log.C_OP_LOG_SUCCESS_MSG, null);
end if;
 
    return l_cnt;
    
  exception
    when others then
 
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 0) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Ошибки фукнции', logmsg, pkg_cc_log.C_OP_LOG_ERROR_MSG, sqlerrm);
end if;
 
      raise;  
  end fnc_get_cnt_by_separator;
  
  -- список моделей 
  function fnc_get_pm_names(
    p_prc_id in l001_cc_op_log.prc_id%type,
    p_user in varchar2
  ) return varchar2
  is
     logmsg  varchar2(255) := 'Список моделей';
     logsender varchar2(255) := 'pkg_cc_structure.fnc_get_pm_names'; 
     
     l_names varchar2(32767);
     
  begin
    
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 2) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Старт функции', logmsg, pkg_cc_log.C_OP_LOG_INIT_MSG, null);    
end if;
 
 -- входные параметры
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 3) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'IN p_user=' || p_user, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
end if;
 
    for name_ix in (select 
                      substr(d001.d001_name, length(pkg_cc_const.C_PFX_PM_PART) + pkg_cc_const.C_PFX_PM_DGT_NUM + nvl(length(pkg_cc_const.C_PART_NAME_DELIMETER), 0)+ 1) d001_name
                    from
                      d001_cc_pm_info d001
                    where 1 = 1 
                      and (p_user is null or d001.d001_parent = p_user)
                    order by 1)  
      loop
        l_names := l_names || pkg_cc_const.C_PM_NAME_SEPARATOR || name_ix.d001_name;
      end loop;
      
      l_names := ltrim(l_names, pkg_cc_const.C_PM_NAME_SEPARATOR);
    
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 3) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '  l_names=' || l_names, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
end if;
 
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 2) then 
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Завершение функции', logmsg, pkg_cc_log.C_OP_LOG_SUCCESS_MSG, null);
end if;
 
    return l_names;
    
  exception
    when others then
 
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 0) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Ошибки фукнции', logmsg, pkg_cc_log.C_OP_LOG_ERROR_MSG, sqlerrm);
end if;
 
      raise;  
  end fnc_get_pm_names;
  
  -- строка структуры модели
  function fnc_get_model_xml(
    p_prc_id in l001_cc_op_log.prc_id%type,
    p_pm_name in varchar2
  ) return clob
  is
     logmsg  varchar2(255) := 'Строка структуры модели';
     logsender varchar2(255) := 'pkg_cc_structure.fnc_get_model_xml'; 
     
     l_clob clob;
     
  begin
    
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Старт функции', logmsg, pkg_cc_log.C_OP_LOG_INIT_MSG, null);    
end if;
 
 -- входные параметры
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'IN p_pm_name=' || p_pm_name, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
end if;
 
    select 
      d001.d001_excel_xml_doc
    into l_clob
    from
      d001_cc_pm_info d001
    where 1 = 1 
      and substr(d001.d001_name, length(pkg_cc_const.C_PFX_PM_PART) + pkg_cc_const.C_PFX_PM_DGT_NUM + nvl(length(pkg_cc_const.C_PART_NAME_DELIMETER), 0) + 1) = p_pm_name;   
 
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then 
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Завершение функции', logmsg, pkg_cc_log.C_OP_LOG_SUCCESS_MSG, null);
end if;
 
    return l_clob;
    
  exception
    when NO_DATA_FOUND then
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then 
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Завершение функции', logmsg, pkg_cc_log.C_OP_LOG_SUCCESS_MSG, null);
end if;

			return null;
    when others then
 
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 0) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Ошибки фукнции', logmsg, pkg_cc_log.C_OP_LOG_ERROR_MSG, sqlerrm);
end if;
 
      raise;  
  end fnc_get_model_xml;
  
  --Поиск таблицы по номеру объекта
  function fnc_get_tbl_name_by_pos_num ( 
    p_prc_id in l001_cc_op_log.prc_id%type,
    p_pfx_pm in varchar2,
    p_pos_num in number
  ) return varchar2
  is
     logmsg  varchar2(255) := 'Поиск таблицы по номеру объекта';
     logsender varchar2(255) := 'pkg_cc_structure.fnc_get_tbl_name_by_pos_num'; 
     
     l_name varchar2(30);
     l_prefix varchar2(30);
     
  begin
    
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Старт функции', logmsg, pkg_cc_log.C_OP_LOG_INIT_MSG, null);    
end if;
 
 -- входные параметры
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'IN p_pfx_pm=' || p_pfx_pm, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'IN p_pos_num=' || p_pos_num, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
end if;
 
 
    l_prefix := fnc_get_prefix_by_num(p_prc_id, 'OB', p_pos_num);
      
    select 
      uc.table_name
    into l_name
    from
      user_tab_cols uc
    where 1 = 1 
      and uc.table_name  like p_pfx_pm || '%'
      and uc.column_name like l_prefix || '%';

-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '  l_name=' || l_name, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
end if;
    
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then 
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Завершение функции', logmsg, pkg_cc_log.C_OP_LOG_SUCCESS_MSG, null);
end if;
 
    return l_name;
      
  exception
    when NO_DATA_FOUND then
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then 
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Завершение функции', logmsg, pkg_cc_log.C_OP_LOG_SUCCESS_MSG, null);
end if;

      return null;
    when others then
 
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 0) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Ошибки функции', logmsg, pkg_cc_log.C_OP_LOG_ERROR_MSG, sqlerrm);
end if;
 
      raise;       
  end fnc_get_tbl_name_by_pos_num;
  
  -- Поиск колонки по номеру объекта
  function fnc_get_col_name_by_pos_num (
    p_prc_id in l001_cc_op_log.prc_id%type,
    p_pfx_pm in varchar2,
    p_pos_num in number
  ) return varchar2
  is
     logmsg  varchar2(255) := 'Поиск колонки по номеру объекта';
     logsender varchar2(255) := 'pkg_cc_structure.fnc_get_col_name_by_pos_num'; 
     
     l_name varchar2(30);
     l_prefix varchar2(30);
  begin
    
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Старт функции', logmsg, pkg_cc_log.C_OP_LOG_INIT_MSG, null);    
end if;
 
 -- входные параметры
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'IN p_pfx_pm=' || p_pfx_pm, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'IN p_pos_num=' || p_pos_num, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
end if;
 
    l_prefix := fnc_get_prefix_by_num(p_prc_id, 'OB', p_pos_num);
      
    select 
      uc.column_name
    into l_name
    from
      user_tab_cols uc
    where 1 = 1 
      and uc.table_name  like p_pfx_pm || '%'
      and uc.column_name like l_prefix || '%';
 
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '  l_name=' || l_name, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
end if;
    
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then 
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Завершение функции', logmsg, pkg_cc_log.C_OP_LOG_SUCCESS_MSG, null);
end if;
 
    return l_name;
      
  exception
    when NO_DATA_FOUND then
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then 
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Завершение функции', logmsg, pkg_cc_log.C_OP_LOG_SUCCESS_MSG, null);
end if;

      return null;
    when others then
 
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 0) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Ошибки функции', logmsg, pkg_cc_log.C_OP_LOG_ERROR_MSG, sqlerrm);
end if;
 
      raise;        
  end fnc_get_col_name_by_pos_num;
  
  -- номер по префиксу
  function fnc_get_num_by_pfx (
    p_prc_id in l001_cc_op_log.prc_id%type,
    p_pfx in varchar2
  ) return varchar2
  is
     logmsg  varchar2(255) := 'Получение номера из префикса';
     logsender varchar2(255) := 'pkg_cc_structure.fnc_get_num_by_pfx'; 
     
     l_num number;
  begin 
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Старт функции', logmsg, pkg_cc_log.C_OP_LOG_INIT_MSG, null);    
end if;
 
 -- входные параметры
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '  p_pfx=' || p_pfx, null, PKG_CC_LOG.C_OP_LOG_VAR, null); 
end if;
      
    l_num := ltrim(substr(p_pfx, 2), '0');
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '  l_num=' || l_num, null, PKG_CC_LOG.C_OP_LOG_VAR, null); 
end if;
 
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then 
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Завершение функции', logmsg, pkg_cc_log.C_OP_LOG_SUCCESS_MSG, null);
end if;
 
 
    return l_num;
      
  exception
    when NO_DATA_FOUND then
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then 
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Завершение функции', logmsg, pkg_cc_log.C_OP_LOG_SUCCESS_MSG, null);
end if;

      return null;
    when others then
 
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 0) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Ошибки функции', logmsg, pkg_cc_log.C_OP_LOG_ERROR_MSG, sqlerrm);
end if;
 
      raise;        
  end fnc_get_num_by_pfx;

  -- Поиск позиции по префиксу
  function fnc_get_col_pos_by_pfx_dim (
    p_prc_id in l001_cc_op_log.prc_id%type,
    p_pfx_pm in varchar2,
    p_pfx_tbl in varchar2
  ) return varchar2
  is
     logmsg  varchar2(255) := 'Поиск позиции по префиксу';
     logsender varchar2(255) := 'pkg_cc_structure.fnc_get_col_pos_by_pfx_dim'; 
     
     l_name varchar2(30);
     l_num number;
  begin 
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Старт функции', logmsg, pkg_cc_log.C_OP_LOG_INIT_MSG, null);    
end if;
 
 -- входные параметры
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '  p_pfx_pm=' || p_pfx_pm, null, PKG_CC_LOG.C_OP_LOG_VAR, null); 
  pkg_cc_log.op_log(p_prc_id, null, logsender, '  p_pfx_tbl=' || p_pfx_tbl, null, PKG_CC_LOG.C_OP_LOG_VAR, null); 
end if;
      
    select 
      uc.column_name
    into l_name
    from
      user_tab_cols uc
    where 1 = 1 
      and uc.table_name  like p_pfx_pm || pkg_cc_const.C_PART_NAME_DELIMETER || p_pfx_tbl || '%'
      and uc.column_name like pkg_cc_const.C_PFX_OBJ_PART || '%';
 
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   l_name=' || l_name, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
end if;
    
     l_num := fnc_get_num_by_pfx(p_prc_id, substr(l_name, 1, 4));
     
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then 
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Завершение функции', logmsg, pkg_cc_log.C_OP_LOG_SUCCESS_MSG, null);
end if;
 
 
    return l_num;
      
  exception
    when NO_DATA_FOUND then
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then 
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Завершение функции', logmsg, pkg_cc_log.C_OP_LOG_SUCCESS_MSG, null);
end if;

      return null;
    when others then
 
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 0) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Ошибки функции', logmsg, pkg_cc_log.C_OP_LOG_ERROR_MSG, sqlerrm);
end if;
 
      raise;        
  end fnc_get_col_pos_by_pfx_dim;
  
  -- поиск префикса
  function fnc_get_prefix_by_name(
    p_prc_id in l001_cc_op_log.prc_id%type,
    p_get_pfx_type in varchar2,
    p_pm_name  in varchar2,
    p_tbl_name in varchar2,
    p_col_name in varchar2,
    p_col_pos in number
  ) return varchar2
  is
     logmsg  varchar2(255) := 'Поиск префикса';
     logsender varchar2(255) := 'pkg_cc_structure.fnc_get_prefix_by_name'; 
     
     l_pfx_pm varchar2(30);
     l_prefix varchar2(30);
  begin
    
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Старт функции', logmsg, pkg_cc_log.C_OP_LOG_INIT_MSG, null);    
end if;
 
 -- входные параметры
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   p_get_pfx_type=' || p_get_pfx_type, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   p_pm_name=' || p_pm_name, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   p_tbl_name=' ||  p_tbl_name, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   p_col_name=' || p_col_name, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
end if;

    if (p_get_pfx_type = pkg_cc_const.C_PFX_PM_TYPE) then 
      select
        substr(d001.d001_name, 1, length(pkg_cc_const.C_PFX_PM_PART) + pkg_cc_const.C_PFX_PM_DGT_NUM) pm_pfx
      into l_prefix
      from
        D001_CC_PM_INFO d001
      where 1 = 1
        and (substr(d001.d001_name, length(pkg_cc_const.C_PFX_PM_PART) + pkg_cc_const.C_PFX_PM_DGT_NUM + nvl(length(pkg_cc_const.C_PART_NAME_DELIMETER), 0) + 1) = p_pm_name);
        
    elsif (p_get_pfx_type = pkg_cc_const.C_PFX_DIM_TBL_TYPE) then
      -- вызываем себя же отработает ветка выше
      l_pfx_pm := fnc_get_prefix_by_name(p_prc_id, pkg_cc_const.C_PFX_PM_TYPE, p_pm_name, null, null, null);
      
      select 
        substr(ut.table_name, length(pkg_cc_const.C_PFX_PM_PART) + pkg_cc_const.C_PFX_PM_DGT_NUM + nvl(length(pkg_cc_const.C_PART_NAME_DELIMETER), 0) + 1, length(pkg_cc_const.C_PFX_DIM_TBL_PART) + pkg_cc_const.C_PFX_DIM_TBL_DGT_NUM)
      into l_prefix
      from
        user_tables ut
      where 1 = 1 
        and ut.table_name like l_pfx_pm || pkg_cc_const.C_PART_NAME_DELIMETER || pkg_cc_const.C_PFX_DIM_TBL_PART || rpad('_', pkg_cc_const.C_PFX_DIM_TBL_DGT_NUM, '_') || pkg_cc_const.C_PART_NAME_DELIMETER || p_tbl_name; 
           
    elsif (p_get_pfx_type = pkg_cc_const.C_PFX_FACT_TBL_TYPE) then
      -- вызываем себя же отработает ветка выше
      l_pfx_pm := fnc_get_prefix_by_name(p_prc_id, pkg_cc_const.C_PFX_PM_TYPE, p_pm_name, null, null, null);
      
      select 
        substr(ut.table_name, length(pkg_cc_const.C_PFX_PM_PART) + pkg_cc_const.C_PFX_PM_DGT_NUM + nvl(length(pkg_cc_const.C_PART_NAME_DELIMETER), 0) + 1, length(pkg_cc_const.C_PFX_FACT_TBL_PART) + pkg_cc_const.C_PFX_FACT_TBL_DGT_NUM)
      into l_prefix
      from
        user_tables ut
      where 1 = 1 
        and ut.table_name like l_pfx_pm || pkg_cc_const.C_PART_NAME_DELIMETER || pkg_cc_const.C_PFX_FACT_TBL_PART || rpad('_', pkg_cc_const.C_PFX_FACT_TBL_DGT_NUM, '_'); 
           
    elsif (p_get_pfx_type = pkg_cc_const.C_PFX_DIM_ATTR_TYPE) then 
      l_pfx_pm := fnc_get_prefix_by_name(p_prc_id, pkg_cc_const.C_PFX_PM_TYPE, p_pm_name, null, null, null);
      
      select 
        substr(uc.column_name, 1, length(pkg_cc_const.C_PFX_DIM_ATTR_PART) + pkg_cc_const.C_PFX_DIM_ATTR_DGT_NUM)
      into l_prefix
      from
        user_tab_cols uc
      where 1 = 1 
        and uc.table_name like l_pfx_pm || '%'
        and uc.column_name like 
         pkg_cc_const.C_PFX_OBJ_PART 
         || rpad('_', pkg_cc_const.C_PFX_OBJ_DGT_NUM, '_') 
         || pkg_cc_const.C_PART_NAME_DELIMETER 
         || pkg_cc_const.C_PFX_DIM_ATTR_PART 
         || rpad('_', pkg_cc_const.C_PFX_DIM_ATTR_DGT_NUM, '_') 
         || pkg_cc_const.C_PART_NAME_DELIMETER 
         || p_col_name;
          
    elsif (p_get_pfx_type = pkg_cc_const.C_PFX_FACT_ATTR_TYPE) then 
      l_pfx_pm := fnc_get_prefix_by_name(p_prc_id, pkg_cc_const.C_PFX_PM_TYPE, p_pm_name, null, null, null);
      
      select 
        substr(uc.column_name, 1, length(pkg_cc_const.C_PFX_FACT_ATTR_PART) + pkg_cc_const.C_PFX_DIM_ATTR_DGT_NUM)
      into l_prefix
      from
        user_tab_cols uc
      where 1 = 1 
        and uc.table_name like l_pfx_pm || '%'
        and uc.column_name like
         pkg_cc_const.C_PFX_OBJ_PART 
         || rpad('_', pkg_cc_const.C_PFX_OBJ_DGT_NUM, '_') 
         || pkg_cc_const.C_PART_NAME_DELIMETER 
         || pkg_cc_const.C_PFX_FACT_ATTR_PART
         || rpad('_', pkg_cc_const.C_PFX_DIM_ATTR_DGT_NUM, '_')
         || pkg_cc_const.C_PART_NAME_DELIMETER 
         || p_col_name;
          
    elsif (p_get_pfx_type = pkg_cc_const.C_PFX_FACT_MEAS_TYPE) then 
      l_pfx_pm := fnc_get_prefix_by_name(p_prc_id, pkg_cc_const.C_PFX_PM_TYPE, p_pm_name, null, null, null);
      
      select 
        substr(uc.column_name, 1, length(pkg_cc_const.C_PFX_FACT_MEAS_PART) + pkg_cc_const.C_PFX_DIM_ATTR_DGT_NUM)
      into l_prefix
      from
        user_tab_cols uc
      where 1 = 1 
        and uc.table_name like l_pfx_pm || '%'
        and uc.column_name like 
         pkg_cc_const.C_PFX_OBJ_PART 
         || rpad('_', pkg_cc_const.C_PFX_OBJ_DGT_NUM, '_') 
         || pkg_cc_const.C_PART_NAME_DELIMETER 
         || pkg_cc_const.C_PFX_FACT_MEAS_PART 
         || rpad('_', pkg_cc_const.C_PFX_FACT_MEAS_DGT_NUM, '_') 
         || pkg_cc_const.C_PART_NAME_DELIMETER 
         || p_col_name;
          
    elsif (p_get_pfx_type = pkg_cc_const.C_PFX_OBJ_TYPE) then
      l_pfx_pm := fnc_get_prefix_by_name(p_prc_id, pkg_cc_const.C_PFX_PM_TYPE, p_pm_name, null, null, null);
      
      select 
        substr(uc.column_name, 1, length(pkg_cc_const.C_PFX_OBJ_PART) + pkg_cc_const.C_PFX_OBJ_DGT_NUM)
      into l_prefix
      from
        user_tab_cols uc
      where 1 = 1 
        and uc.table_name like l_pfx_pm || '%'
        and uc.column_name like 
         pkg_cc_const.C_PFX_OBJ_PART 
         || fnc_get_prefix_by_num(p_prc_id, pkg_cc_const.C_PFX_OBJ_TYPE, p_col_pos) || '%';
          
    end if;
    
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   l_prefix=' || l_prefix, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
end if;
 
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then 
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Завершение функции', logmsg, pkg_cc_log.C_OP_LOG_SUCCESS_MSG, null);
end if;
 
    return l_prefix;
    
  exception
		
    when NO_DATA_FOUND then
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then 
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Завершение функции', logmsg, pkg_cc_log.C_OP_LOG_SUCCESS_MSG, null);
end if;

      return null;
      
    when others then
 
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 0) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Ошибки функции', logmsg, pkg_cc_log.C_OP_LOG_ERROR_MSG, sqlerrm);
end if;
 
      raise;  
  end fnc_get_prefix_by_name;
  
  -- нормализация префикса 
  function fnc_get_prefix_by_num(
    p_prc_id in l001_cc_op_log.prc_id%type,
    p_pfx_type in varchar2,
    p_num in number
  ) return varchar2
  is
     logmsg  varchar2(255) := 'Нормализация префикса';
     logsender varchar2(255) := 'pkg_cc_structure.fnc_get_prefix_by_num'; 
     
     l_prefix varchar2(30);
  begin

-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Старт функции', logmsg, pkg_cc_log.C_OP_LOG_INIT_MSG, null);    
end if;
 
 -- входные параметры
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   p_pfx_type=' || p_pfx_type, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   p_num=' || p_num, null, PKG_CC_LOG.C_OP_LOG_VAR, null);      
end if;
 
    if (p_pfx_type = pkg_cc_const.C_PFX_PM_TYPE) then
			l_prefix := pkg_cc_const.C_PFX_PM_PART || lpad(p_num, pkg_cc_const.C_PFX_PM_DGT_NUM, '0');
	  elsif (p_pfx_type = pkg_cc_const.C_PFX_FACT_TBL_TYPE) then
      l_prefix := pkg_cc_const.C_PFX_FACT_TBL_PART || lpad(p_num, pkg_cc_const.C_PFX_FACT_TBL_DGT_NUM, '0');
    elsif (p_pfx_type = pkg_cc_const.C_PFX_DIM_TBL_TYPE) then 
      l_prefix := pkg_cc_const.C_PFX_DIM_TBL_PART || lpad(p_num, pkg_cc_const.C_PFX_DIM_TBL_DGT_NUM, '0');
    elsif (p_pfx_type = pkg_cc_const.C_PFX_OBJ_TYPE) then
      l_prefix := pkg_cc_const.C_PFX_OBJ_PART || lpad(p_num, pkg_cc_const.C_PFX_DIM_TBL_DGT_NUM, '0');
    elsif (p_pfx_type = pkg_cc_const.C_PFX_FACT_MEAS_TYPE) then
      l_prefix := pkg_cc_const.C_PFX_FACT_MEAS_PART || lpad(p_num, pkg_cc_const.C_PFX_FACT_MEAS_DGT_NUM, '0');
    elsif (p_pfx_type = pkg_cc_const.C_PFX_FACT_ATTR_TYPE) then
      l_prefix := pkg_cc_const.C_PFX_FACT_ATTR_PART || lpad(p_num, pkg_cc_const.C_PFX_FACT_ATTR_DGT_NUM, '0');
    elsif (p_pfx_type = pkg_cc_const.C_PFX_DIM_ATTR_TYPE) then 
      l_prefix := pkg_cc_const.C_PFX_DIM_ATTR_PART || lpad(p_num, pkg_cc_const.C_PFX_DIM_ATTR_DGT_NUM, '0');
    end if;
    
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   l_prefix=' || l_prefix, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
end if;
 
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then 
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Завершение функции', logmsg, pkg_cc_log.C_OP_LOG_SUCCESS_MSG, null);
end if;
 
    return l_prefix;
    
  exception
    when others then
 
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 0) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Ошибки функции', logmsg, pkg_cc_log.C_OP_LOG_ERROR_MSG, sqlerrm);
end if;
 
      raise;   
  end fnc_get_prefix_by_num;
  
  -- генерация префиков
  function fnc_get_next_prefix(
    p_prc_id in l001_cc_op_log.prc_id%type,
    p_get_pfx_type in varchar2,
    p_pfx_pm  in varchar2,
    p_pfx_tbl in varchar2
  ) return varchar2
  is
     logmsg  varchar2(255) := 'Генерация префиксов';
     logsender varchar2(255) := 'pkg_cc_structure.fnc_get_next_prefix'; 
     
     l_next_val number;
     l_prefix varchar2(30);
     
     l_stmt varchar2(32767);
  begin
    
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Старт функции', logmsg, pkg_cc_log.C_OP_LOG_INIT_MSG, null);    
end if;
 
 -- входные параметры
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   p_get_pfx_type=' || p_get_pfx_type, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   p_pfx_pm=' || p_pfx_pm, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   p_pfx_tbl=' || p_pfx_tbl, null, PKG_CC_LOG.C_OP_LOG_VAR, null);      
end if;

    if (p_get_pfx_type = pkg_cc_const.C_PFX_DIM_ATTR_TYPE) then
			-- для атрибутов и показателей
      l_stmt := 'select ' || pkg_cc_const.C_SEQ_PREFIX_NAME_PART || pkg_cc_const.C_PART_NAME_DELIMETER || p_pfx_pm || pkg_cc_const.C_PART_NAME_DELIMETER || p_pfx_tbl || pkg_cc_const.C_PART_NAME_DELIMETER || p_get_pfx_type ||'.nextval from dual';
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   l_stmt=' || l_stmt, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
end if;

      execute immediate
        l_stmt INTO l_next_val;
        
  	elsif (p_get_pfx_type = pkg_cc_const.C_PFX_FACT_ATTR_TYPE) then
      -- для атрибутов и показателей
      l_stmt := 'select ' || pkg_cc_const.C_SEQ_PREFIX_NAME_PART || pkg_cc_const.C_PART_NAME_DELIMETER || p_pfx_pm || pkg_cc_const.C_PART_NAME_DELIMETER || p_pfx_tbl || pkg_cc_const.C_PART_NAME_DELIMETER || p_get_pfx_type ||'.nextval from dual';
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   l_stmt=' || l_stmt, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
end if;

      execute immediate
        l_stmt INTO l_next_val;
           
    elsif (p_get_pfx_type = pkg_cc_const.C_PFX_FACT_MEAS_TYPE) then 
      -- для атрибутов и показателей
      l_stmt := 'select ' || pkg_cc_const.C_SEQ_PREFIX_NAME_PART || pkg_cc_const.C_PART_NAME_DELIMETER || p_pfx_pm || pkg_cc_const.C_PART_NAME_DELIMETER || p_pfx_tbl || pkg_cc_const.C_PART_NAME_DELIMETER || p_get_pfx_type ||'.nextval from dual';
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   l_stmt=' || l_stmt, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
end if;

      execute immediate
        l_stmt INTO l_next_val;
        
    elsif (p_get_pfx_type = pkg_cc_const.C_PFX_DIM_TBL_TYPE) then 
      -- для таблиц и объектов
      l_stmt := 'select ' || pkg_cc_const.C_SEQ_PREFIX_NAME_PART || pkg_cc_const.C_PART_NAME_DELIMETER || p_pfx_pm || pkg_cc_const.C_PART_NAME_DELIMETER || p_get_pfx_type ||'.nextval from dual';
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   l_stmt=' || l_stmt, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
end if;

      execute immediate
        l_stmt INTO l_next_val;
            
    elsif (p_get_pfx_type = pkg_cc_const.C_PFX_FACT_TBL_TYPE) then
      -- для таблиц и объектов
      l_stmt := 'select ' || pkg_cc_const.C_SEQ_PREFIX_NAME_PART || pkg_cc_const.C_PART_NAME_DELIMETER || p_pfx_pm || pkg_cc_const.C_PART_NAME_DELIMETER || p_get_pfx_type ||'.nextval from dual';
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   l_stmt=' || l_stmt, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
end if;

      execute immediate
        l_stmt INTO l_next_val;

    elsif (p_get_pfx_type = pkg_cc_const.C_PFX_OBJ_TYPE) then
      -- для таблиц и объектов
      l_stmt := 'select ' || pkg_cc_const.C_SEQ_PREFIX_NAME_PART || pkg_cc_const.C_PART_NAME_DELIMETER || p_pfx_pm || pkg_cc_const.C_PART_NAME_DELIMETER || p_get_pfx_type ||'.nextval from dual';
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   l_stmt=' || l_stmt, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
end if;

      execute immediate
        l_stmt INTO l_next_val;
        
    elsif (p_get_pfx_type = pkg_cc_const.C_PFX_PM_TYPE) then
      -- для модели
      l_stmt := 'select ' || pkg_cc_const.C_SEQ_PREFIX_NAME_PART || pkg_cc_const.C_PART_NAME_DELIMETER || p_get_pfx_type ||'.nextval from dual';
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   l_stmt=' || l_stmt, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
end if;

      execute immediate
        l_stmt INTO l_next_val;
    end if; 
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   l_next_val=' || l_next_val, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
end if;

    l_prefix := fnc_get_prefix_by_num(p_prc_id, p_get_pfx_type, l_next_val);
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   l_prefix=' || l_prefix, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
end if;

-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then 
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Завершение функции', logmsg, pkg_cc_log.C_OP_LOG_SUCCESS_MSG, null);
end if;
 
    return l_prefix;
    
  exception
    when others then
 
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 0) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Ошибки функции', logmsg, pkg_cc_log.C_OP_LOG_ERROR_MSG, sqlerrm);
end if;
 
      raise;    
  end fnc_get_next_prefix;
  
  -- процедура создания модели
  procedure prc_create_model (
    p_prc_id in l001_cc_op_log.prc_id%type,
    p_pm_name varchar2,
    p_pm_desc varchar2,
    p_user varchar2
  )
  is
     logmsg  varchar2(255) := 'Создание модели';
     logsender varchar2(255) := 'pkg_cc_structure.prc_create_model'; 
  begin
    
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Старт процедуры', logmsg, pkg_cc_log.C_OP_LOG_INIT_MSG, null);    
 end if;
 
 -- входные параметры
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   p_pm_name=' || p_pm_name, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   p_pm_desc=' || p_pm_desc, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   p_user=' || p_user, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
 end if;
 
    insert into D001_CC_PM_INFO 
      values(p_pm_name, replace(p_pm_desc, pkg_cc_const.C_QUOT_REPLACE, ''''), p_user, p_prc_id, null, null, sysdate);
    commit;
    
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then 
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Завершение процедуры', logmsg, pkg_cc_log.C_OP_LOG_SUCCESS_MSG, null);
end if;
 
  exception
    when others then      
 
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 0) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Ошибки процедуры', logmsg, pkg_cc_log.C_OP_LOG_ERROR_MSG, sqlerrm);
end if;
 
      raise;    
  end prc_create_model;
  
  -- процедура добавления информации о структуре
  procedure prc_add_info_in_model (
    p_prc_id in l001_cc_op_log.prc_id%type,
    p_db_xml in clob,
    p_excel_xml in clob
  )
  is
     logmsg  varchar2(255) := 'Добавление информации о структуре в модель';
     logsender varchar2(255) := 'pkg_cc_structure.prc_add_info_in_model'; 
  begin
    
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Старт процедуры', logmsg, pkg_cc_log.C_OP_LOG_INIT_MSG, null);    
end if;
 
 
    update D001_CC_PM_INFO d
      set 
        d.d001_db_xml_doc = p_db_xml,
        d.d001_excel_xml_doc = p_excel_xml
    where 1 = 1 
      and d.prc_id = p_prc_id;

    commit;
    
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then 
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Завершение процедуры', logmsg, pkg_cc_log.C_OP_LOG_SUCCESS_MSG, null);
end if;
 
  exception
    when others then      
 
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 0) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Ошибки процедуры', logmsg, pkg_cc_log.C_OP_LOG_ERROR_MSG, sqlerrm);
end if;
 
      raise;    
  end prc_add_info_in_model;
  
  -- процедура создания таблицы
  procedure prc_create_table (
    p_prc_id in l001_cc_op_log.prc_id%type,
    p_tbl_name varchar2,
    p_tbl_desc varchar2,
    p_tbl_cols varchar2,
    p_tbl_comms varchar2
  )
  is
     logmsg  varchar2(255) := 'Создание таблицы';
     logsender varchar2(255) := 'pkg_cc_structure.prc_create_table'; 
    
     l_comm_count number;
     l_comm_cur_pos number := 1;
     l_comm_next_pos number;
    
     l_comm_str varchar2(32767);
     
     l_comm_prt_count number;
     l_comm_prt_cur_pos number := 1;
     l_comm_prt_next_pos number;
    
     l_comm_col varchar2(30);
     l_comm_comm varchar2(4000);
    
  begin
    
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Старт процедуры', logmsg, pkg_cc_log.C_OP_LOG_INIT_MSG, null);    
end if;
 
 -- входные параметры
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   p_tbl_name=' || p_tbl_name, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   p_tbl_cols=' || p_tbl_cols, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   p_tbl_comms=' || p_tbl_comms, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
end if;
 
    execute immediate
      'create table ' || p_tbl_name || '('|| p_tbl_cols ||') nologging';
    
    execute immediate 
      'comment on table '|| p_tbl_name ||' is ''' || p_tbl_desc || '''';
      
    -- кол-во колонок = кол-во разделителей + 1
    l_comm_count := regexp_count(p_tbl_comms, pkg_cc_const.C_COL_SEPARATOR) + 1;
    
        -- парсим колонки
    for comm_ix in 1 .. l_comm_count
      loop
        l_comm_next_pos := instr(p_tbl_comms, pkg_cc_const.C_COL_SEPARATOR, 1, comm_ix);
        
        if (comm_ix = l_comm_count) then
          l_comm_str := substr(p_tbl_comms, l_comm_cur_pos);
        else
          l_comm_str := substr(p_tbl_comms, l_comm_cur_pos, l_comm_next_pos - l_comm_cur_pos);
        end if;
        
        -- кол-во атрибутов = кол-во разделителей + 1
        l_comm_prt_count := regexp_count(l_comm_str, pkg_cc_const.C_COMM_PRT_SEPARATOR) + 1;
 
        -- парсим атрибуты
        -- кол-во циклов = кол-во разделителей атрибутов + 1
        for comm_prt_ix in 1 .. l_comm_count
          loop
            l_comm_prt_next_pos := instr(l_comm_str, pkg_cc_const.C_COMM_PRT_SEPARATOR, 1, comm_prt_ix);
            if (comm_prt_ix = l_comm_prt_count) then
                l_comm_comm := substr(l_comm_str, l_comm_prt_cur_pos);
            else
              if (comm_prt_ix = 1) then 
                l_comm_col := substr(l_comm_str, l_comm_prt_cur_pos, l_comm_prt_next_pos - l_comm_prt_cur_pos);
             end if;
            end if;
            
            l_comm_prt_cur_pos := l_comm_prt_next_pos + length(pkg_cc_const.C_COMM_PRT_SEPARATOR);
            
          end loop;
          
        -- декодируем разделители
        l_comm_comm := replace(l_comm_comm, pkg_cc_const.C_COMM_SEPARATOR_REPLACE, pkg_cc_const.C_COMM_SEPARATOR);
        l_comm_comm := replace(l_comm_comm, pkg_cc_const.C_COMM_PRT_SEPARATOR_REPLACE, pkg_cc_const.C_COMM_PRT_SEPARATOR);
        l_comm_comm := replace(l_comm_comm, pkg_cc_const.C_QUOT_REPLACE, ''''''); -- заменяем на экранированные ''
        
        execute immediate 
          'comment on column '|| p_tbl_name ||'.'|| l_comm_col ||' is ''' || l_comm_comm || '''';
        
        l_comm_cur_pos := l_comm_next_pos + length(pkg_cc_const.C_COMM_SEPARATOR); -- пропускаем разделитель
        
      end loop;
    
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then 
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Завершение процедуры', logmsg, pkg_cc_log.C_OP_LOG_SUCCESS_MSG, null);
end if;
 
  exception
    when others then           
 
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 0) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Ошибки процедуры', logmsg, pkg_cc_log.C_OP_LOG_ERROR_MSG, sqlerrm);
end if;
 
      raise;    
  end prc_create_table;
  
  -- процедура вставки фиктивной записи
  procedure prc_insert_dummy (
    p_prc_id in l001_cc_op_log.prc_id%type,
    p_tbl_name varchar2,
    p_dummy_count number
  )
  is
     logmsg  varchar2(255) := 'Вставка фиктивной записи';
     logsender varchar2(255) := 'pkg_cc_structure.prc_insert_dummy'; 
     
     l_dummy_str varchar2(32767);
     l_stmt varchar2(32767);
  begin
    
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Старт процедуры', logmsg, pkg_cc_log.C_OP_LOG_INIT_MSG, null);    
end if;
 
 -- входные параметры
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   p_tbl_name=' || p_tbl_name, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   p_dummy_count=' || p_dummy_count, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
 
    for dummy_ix in 1 .. p_dummy_count
			loop
				if (pkg_cc_const.C_DIM_DUMMY_REF_ID is null) then
				  l_dummy_str := l_dummy_str || 'null' || ', ';
        else
					l_dummy_str := l_dummy_str || '''' || pkg_cc_const.C_DIM_DUMMY_REF_ID || '''' || ', ';
        end if;
      end loop;
    l_dummy_str := l_dummy_str || '''' || pkg_cc_const.C_DIM_DUMMY_VAL || '''';
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   l_dummy_str=' || l_dummy_str, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
end if;
    
    l_stmt := 'insert into ' || p_tbl_name || ' values(' || pkg_cc_const.C_DIM_DUMMY_DIM_ID || ', ' || l_dummy_str || ', ' || pkg_cc_const.C_CREATED_DATE_VAL || ')';
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   l_stmt=' || l_stmt, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
end if;

    execute immediate
      l_stmt;
    
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then 
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Завершение процедуры', logmsg, pkg_cc_log.C_OP_LOG_SUCCESS_MSG, null);
end if;
 
  exception
    when others then
 
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 0) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Ошибки процедуры', logmsg, pkg_cc_log.C_OP_LOG_ERROR_MSG, sqlerrm);
end if;
 
      raise;   
  end prc_insert_dummy;

  -- процедура создания последовательности
  procedure prc_create_sequence (
    p_prc_id in l001_cc_op_log.prc_id%type,
    p_seq_name varchar2
  )
  is
     logmsg  varchar2(255) := 'Создание последовательности';
     logsender varchar2(255) := 'pkg_cc_structure.prc_create_sequence';  
  begin
    
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Старт процедуры', logmsg, pkg_cc_log.C_OP_LOG_INIT_MSG, null);    
end if;
 
 -- входные параметры
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   p_seq_name=' || p_seq_name, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
end if;
 
     execute immediate   
       'create sequence ' || p_seq_name ||' 
         START WITH 1 
         MAXVALUE '|| pkg_cc_const.C_SEQ_MAXVALUE || '
         MINVALUE 1 
         NOCYCLE NOCACHE NOORDER';
    
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then 
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Завершение процедуры', logmsg, pkg_cc_log.C_OP_LOG_SUCCESS_MSG, null);
end if;
 
  exception
    when others then
 
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 0) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Ошибки процедуры', logmsg, pkg_cc_log.C_OP_LOG_ERROR_MSG, sqlerrm);
end if;
 
      raise;   
  end prc_create_sequence;
  
  -- сброс последовательностей
  procedure prc_reset_seq (
    p_prc_id in l001_cc_op_log.prc_id%type,
    p_seq_name in varchar2 
  )
  is
     logmsg  varchar2(255) := 'Сброс последовательности';
     logsender varchar2(255) := 'pkg_cc_structure.prc_reset_seq';
     
     l_val number;
  begin
/*-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Старт процедуры', logmsg, pkg_cc_log.C_OP_LOG_INIT_MSG, null);    
 end if;*/
 
/*-- входные параметры
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   p_seq_name=' || p_seq_name, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
end if;*/
 
    execute immediate
      'select ' || p_seq_name || '.nextval from dual' INTO l_val;
     
    execute immediate
      'alter sequence ' || p_seq_name || ' increment by -' || l_val || ' minvalue 0';
    execute immediate
      'select ' || p_seq_name || '.nextval from dual' INTO l_val;
     
    execute immediate
      'alter sequence ' || p_seq_name || ' increment by 1 minvalue 0';
    
/*-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then 
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Завершение процедуры', logmsg, pkg_cc_log.C_OP_LOG_SUCCESS_MSG, null);
 end if;*/
 
  exception
    when others then
 
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 0) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Ошибки процедуры', logmsg, pkg_cc_log.C_OP_LOG_ERROR_MSG, sqlerrm);
end if;
 
      raise;  
  end prc_reset_seq;
  
  -- удаление объектов
  procedure prc_drop_object (
    p_prc_id in l001_cc_op_log.prc_id%type,
    p_obj_name in varchar2,
    p_obj_type in varchar2
  )
  is
     logmsg  varchar2(255) := 'Удаление объекта';
     logsender varchar2(255) := 'pkg_cc_structure.prc_drop_object';
     
  begin
    
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Старт процедуры', logmsg, pkg_cc_log.C_OP_LOG_INIT_MSG, null);    
end if;
 
 -- входные параметры
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   p_obj_name=' || p_obj_name, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   p_obj_type=' || p_obj_type, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
end if;
    
    if (p_obj_type = 'TABLE') then 
      execute immediate
        'drop table ' || p_obj_name;
    elsif (p_obj_type = 'SEQUENCE') then
      execute immediate
        'drop sequence ' || p_obj_name;
    elsif (p_obj_type = 'INDEX') then
      execute immediate
        'drop index ' || p_obj_name;
    end if;
    
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then 
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Завершение процедуры', logmsg, pkg_cc_log.C_OP_LOG_SUCCESS_MSG, null);
end if;
 
  exception
    when others then
 
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 0) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Ошибки процедуры', logmsg, pkg_cc_log.C_OP_LOG_ERROR_MSG, sqlerrm);
end if;
 
      raise;  
  end prc_drop_object; 
  
  -- очистка модели
  procedure prc_drop_cube (
    p_prc_id in l001_cc_op_log.prc_id%type,
    p_pfx_pm in varchar2
  )
  is
     logmsg  varchar2(255) := 'Удаление элементов модели';
     logsender varchar2(255) := 'pkg_cc_structure.prc_drop_cube';
  begin
    
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Старт процедуры', logmsg, pkg_cc_log.C_OP_LOG_INIT_MSG, null);    
end if;
 
 -- входные параметры
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   p_pfx_pm=' || p_pfx_pm, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
end if;


-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   1 tmpt=' || p_pfx_pm || pkg_cc_const.C_PART_NAME_DELIMETER || pkg_cc_const.C_PFX_FACT_TBL_PART || rpad('_', pkg_cc_const.C_PFX_FACT_TBL_DGT_NUM, '_'), null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   2 tmpt=' || p_pfx_pm || pkg_cc_const.C_PART_NAME_DELIMETER || pkg_cc_const.C_PFX_DIM_TBL_PART || rpad('_', pkg_cc_const.C_PFX_DIM_TBL_DGT_NUM, '_') || '%', null, PKG_CC_LOG.C_OP_LOG_VAR, null);
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   3 tmpt=' || pkg_cc_const.C_SEQ_PREFIX_NAME_PART || pkg_cc_const.C_PART_NAME_DELIMETER || p_pfx_pm || pkg_cc_const.C_PART_NAME_DELIMETER || rpad('_', length(pkg_cc_const.C_PFX_FACT_TBL_TYPE), '_'), null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   4 tmpt=' || pkg_cc_const.C_SEQ_PREFIX_NAME_PART || pkg_cc_const.C_PART_NAME_DELIMETER || p_pfx_pm || pkg_cc_const.C_PART_NAME_DELIMETER || rpad('_', length(pkg_cc_const.C_PFX_DIM_TBL_TYPE), '_'), null, PKG_CC_LOG.C_OP_LOG_VAR, null);
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   5 tmpt=' || pkg_cc_const.C_SEQ_PREFIX_NAME_PART || pkg_cc_const.C_PART_NAME_DELIMETER || p_pfx_pm || pkg_cc_const.C_PART_NAME_DELIMETER || rpad('_', length(pkg_cc_const.C_PFX_OBJ_TYPE), '_'), null, PKG_CC_LOG.C_OP_LOG_VAR, null);
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   6 tmpt=' || pkg_cc_const.C_SEQ_PREFIX_NAME_PART || pkg_cc_const.C_PART_NAME_DELIMETER || p_pfx_pm || pkg_cc_const.C_PART_NAME_DELIMETER || pkg_cc_const.C_PFX_FACT_TBL_PART || rpad('_', pkg_cc_const.C_PFX_FACT_TBL_DGT_NUM, '_') || pkg_cc_const.C_PART_NAME_DELIMETER || rpad('_', length(pkg_cc_const.C_PFX_FACT_MEAS_TYPE), '_'), null, PKG_CC_LOG.C_OP_LOG_VAR, null);
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   7 tmpt=' || pkg_cc_const.C_SEQ_PREFIX_NAME_PART || pkg_cc_const.C_PART_NAME_DELIMETER || p_pfx_pm || pkg_cc_const.C_PART_NAME_DELIMETER || pkg_cc_const.C_PFX_FACT_TBL_PART || rpad('_', pkg_cc_const.C_PFX_FACT_TBL_DGT_NUM, '_') || pkg_cc_const.C_PART_NAME_DELIMETER || rpad('_', length(pkg_cc_const.C_PFX_FACT_ATTR_TYPE), '_'), null, PKG_CC_LOG.C_OP_LOG_VAR, null);
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   8 tmpt=' || pkg_cc_const.C_SEQ_PREFIX_NAME_PART || pkg_cc_const.C_PART_NAME_DELIMETER || p_pfx_pm || pkg_cc_const.C_PART_NAME_DELIMETER || pkg_cc_const.C_PFX_DIM_TBL_PART || rpad('_', pkg_cc_const.C_PFX_DIM_TBL_DGT_NUM, '_') || pkg_cc_const.C_PART_NAME_DELIMETER || rpad('_', length(pkg_cc_const.C_PFX_FACT_ATTR_TYPE), '_'), null, PKG_CC_LOG.C_OP_LOG_VAR, null);
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   9 tmpt=' || pkg_cc_const.C_SEQ_NAME_PART || pkg_cc_const.C_PART_NAME_DELIMETER || p_pfx_pm || pkg_cc_const.C_PART_NAME_DELIMETER || pkg_cc_const.C_PFX_FACT_TBL_PART || rpad('_', pkg_cc_const.C_PFX_FACT_TBL_DGT_NUM, '_') || pkg_cc_const.C_PART_NAME_DELIMETER || rpad('_', length(pkg_cc_const.C_FACT_ID_NAME), '_'), null, PKG_CC_LOG.C_OP_LOG_VAR, null);
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   10 tmpt=' || pkg_cc_const.C_SEQ_NAME_PART || pkg_cc_const.C_PART_NAME_DELIMETER || p_pfx_pm || pkg_cc_const.C_PART_NAME_DELIMETER || pkg_cc_const.C_PFX_DIM_TBL_PART || rpad('_', pkg_cc_const.C_PFX_DIM_TBL_DGT_NUM, '_') || pkg_cc_const.C_PART_NAME_DELIMETER || rpad('_', length(pkg_cc_const.C_DIM_ID_NAME), '_'), null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
end if;
 
    for obj_el in (select
                     uo.OBJECT_NAME OBJECT_NAME,
                     uo.OBJECT_TYPE OBJECT_TYPE
                   from
                     user_objects uo
                   where 1 = 1
                     and (
                       uo.OBJECT_NAME like p_pfx_pm || pkg_cc_const.C_PART_NAME_DELIMETER || pkg_cc_const.C_PFX_FACT_TBL_PART || rpad('_', pkg_cc_const.C_PFX_FACT_TBL_DGT_NUM, '_')
                       or
                       uo.OBJECT_NAME like p_pfx_pm || pkg_cc_const.C_PART_NAME_DELIMETER || pkg_cc_const.C_PFX_DIM_TBL_PART || rpad('_', pkg_cc_const.C_PFX_DIM_TBL_DGT_NUM, '_') || '%'
                       or 
                       uo.OBJECT_NAME like pkg_cc_const.C_SEQ_PREFIX_NAME_PART || pkg_cc_const.C_PART_NAME_DELIMETER || p_pfx_pm || pkg_cc_const.C_PART_NAME_DELIMETER || rpad('_', length(pkg_cc_const.C_PFX_FACT_TBL_TYPE), '_') 
                       or
                       uo.OBJECT_NAME like pkg_cc_const.C_SEQ_PREFIX_NAME_PART || pkg_cc_const.C_PART_NAME_DELIMETER || p_pfx_pm || pkg_cc_const.C_PART_NAME_DELIMETER || rpad('_', length(pkg_cc_const.C_PFX_DIM_TBL_TYPE), '_')
                       or 
                       uo.OBJECT_NAME like pkg_cc_const.C_SEQ_PREFIX_NAME_PART || pkg_cc_const.C_PART_NAME_DELIMETER || p_pfx_pm || pkg_cc_const.C_PART_NAME_DELIMETER || rpad('_', length(pkg_cc_const.C_PFX_OBJ_TYPE), '_') 
                       or
                       uo.OBJECT_NAME like pkg_cc_const.C_SEQ_PREFIX_NAME_PART || pkg_cc_const.C_PART_NAME_DELIMETER || p_pfx_pm || pkg_cc_const.C_PART_NAME_DELIMETER || pkg_cc_const.C_PFX_FACT_TBL_PART || rpad('_', pkg_cc_const.C_PFX_FACT_TBL_DGT_NUM, '_') || pkg_cc_const.C_PART_NAME_DELIMETER || rpad('_', length(pkg_cc_const.C_PFX_FACT_MEAS_TYPE), '_')
                       or 
                       uo.OBJECT_NAME like pkg_cc_const.C_SEQ_PREFIX_NAME_PART || pkg_cc_const.C_PART_NAME_DELIMETER || p_pfx_pm || pkg_cc_const.C_PART_NAME_DELIMETER || pkg_cc_const.C_PFX_FACT_TBL_PART || rpad('_', pkg_cc_const.C_PFX_FACT_TBL_DGT_NUM, '_') || pkg_cc_const.C_PART_NAME_DELIMETER || rpad('_', length(pkg_cc_const.C_PFX_FACT_ATTR_TYPE), '_')
                       or 
                       uo.OBJECT_NAME like pkg_cc_const.C_SEQ_PREFIX_NAME_PART || pkg_cc_const.C_PART_NAME_DELIMETER || p_pfx_pm || pkg_cc_const.C_PART_NAME_DELIMETER || pkg_cc_const.C_PFX_DIM_TBL_PART || rpad('_', pkg_cc_const.C_PFX_DIM_TBL_DGT_NUM, '_') || pkg_cc_const.C_PART_NAME_DELIMETER || rpad('_', length(pkg_cc_const.C_PFX_DIM_ATTR_TYPE), '_')
                       or 
                       uo.OBJECT_NAME like pkg_cc_const.C_SEQ_NAME_PART || pkg_cc_const.C_PART_NAME_DELIMETER || p_pfx_pm || pkg_cc_const.C_PART_NAME_DELIMETER || pkg_cc_const.C_PFX_FACT_TBL_PART || rpad('_', pkg_cc_const.C_PFX_FACT_TBL_DGT_NUM, '_') || pkg_cc_const.C_PART_NAME_DELIMETER || rpad('_', length(pkg_cc_const.C_FACT_ID_NAME), '_') 
                       or 
                       uo.OBJECT_NAME like pkg_cc_const.C_SEQ_NAME_PART || pkg_cc_const.C_PART_NAME_DELIMETER || p_pfx_pm || pkg_cc_const.C_PART_NAME_DELIMETER || pkg_cc_const.C_PFX_DIM_TBL_PART || rpad('_', pkg_cc_const.C_PFX_DIM_TBL_DGT_NUM, '_') || pkg_cc_const.C_PART_NAME_DELIMETER || rpad('_', length(pkg_cc_const.C_DIM_ID_NAME), '_')
                      )
                   )
      loop
 
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   obj_el.object_type= ' || obj_el.object_type || ' obj_el.object_name=' || obj_el.object_name, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
end if;
 
        prc_drop_object(p_prc_id, obj_el.object_name, obj_el.object_type);
        
      end loop; 
      
      delete 
      from
        D001_CC_PM_INFO d001
      where 1 = 1
        and d001.d001_name like p_pfx_pm || '%';
      
      commit;
    
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then 
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Завершение процедуры', logmsg, pkg_cc_log.C_OP_LOG_SUCCESS_MSG, null);
end if;
 
  exception
    when others then
 
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 0) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Ошибки процедуры', logmsg, pkg_cc_log.C_OP_LOG_ERROR_MSG, sqlerrm);
end if;
 
      raise;  
  end prc_drop_cube; 
  
  -- формирование глобальных настроек модели
  procedure prc_set_settings(
    p_prc_id in l001_cc_op_log.prc_id%type,
    p_structure_clob in clob
  )
  is      
     logmsg  varchar2(255) := 'Формирование глобальных настроек модели для создания структуры';
     logsender varchar2(255) := 'pkg_cc_structure.prc_set_structure_settings';
     
     doc xmldom.DOMDocument;   
     parser xmlparser.parser := xmlparser.newparser;
     
     groups_nodes xmldom.DOMNodeList;
     cnt_groups number;
       
     groups_node xmldom.DOMNode;
     groups_el xmldom.DOMNodeList;
       
     cnt_group number;
     group_node xmldom.DOMNode;
     group_el  xmldom.DOMElement;
     
     grp_number number;
     
     cols_node xmldom.DOMNodeList;
     cnt_col number;
       
     col_node xmldom.DOMNode;
     col_el xmldom.DOMElement;
       
     col_number number;
     
     l_prnt number;
     l_prnt_count number;
     l_prnt_cur_pos number := 1;
     l_prnt_next_pos number;
     
  begin
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Старт процедуры', logmsg, pkg_cc_log.C_OP_LOG_INIT_MSG, null);    
end if;

    xmlparser.parseCLOB(parser, p_structure_clob);
    doc := xmlparser.getDocument(parser);
    xmlparser.freeParser(parser);
    
    groups_nodes := xmldom.getElementsByTagName(doc, 'Groups');
    cnt_groups := xmldom.getLength(groups_nodes);
      
    for grps_ix in 0 .. cnt_groups - 1
      loop
        groups_node := xmldom.item(groups_nodes, grps_ix);
        groups_el := xmldom.getChildNodes(groups_node);
        cnt_group := xmldom.getLength(groups_el);
        
        for gpr_ix in 0 .. cnt_group - 1 
          loop
            
            group_node := xmldom.item(groups_el, gpr_ix);
            group_el := xmldom.makeElement(group_node);
            
            grp_number := xmldom.getAttribute(group_el, 'name');
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'grp_number=' || grp_number, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;

            begin
              g_GetGrpByPrcIx(p_prc_id)(g_GetGrpByPrcIx(p_prc_id).count + 1) := grp_number;
            exception
              when NO_DATA_FOUND then
                -- если упали сюда, значит это первая запись в массив 
                g_GetGrpByPrcIx(p_prc_id)(1) := grp_number;
              when others then
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 0) then
  pkg_cc_log.op_log(p_prc_id, null, 'Блок настроек', 'Ошибки настроек групп', logmsg, pkg_cc_log.C_OP_LOG_ERROR_MSG, sqlerrm);
end if;
             
                raise;
            end;
              
            cols_node := xmldom.getChildNodes(group_node);
            cnt_col := xmldom.getLength(cols_node);
            
            for col_ix in 0 .. cnt_col - 1
              loop
                col_node := xmldom.item(cols_node, col_ix);
                col_el := xmldom.makeElement(col_node);
                
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '    col_number=' || xmldom.getAttribute(col_el, 'absGroupPos'), null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
 
                g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(xmldom.getAttribute(col_el, 'absGroupPos'))(pkg_cc_const.C_ATTR_NAME_POS) := upper(xmldom.getAttribute(col_el, 'name'));
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '        name=' || upper(xmldom.getAttribute(col_el, 'name')), null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;

                g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(xmldom.getAttribute(col_el, 'absGroupPos'))(pkg_cc_const.C_ATTR_CTYPE_POS) := upper(xmldom.getAttribute(col_el, 'type'));
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '        ctype=' || upper(xmldom.getAttribute(col_el, 'type')), null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
                    
                g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(xmldom.getAttribute(col_el, 'absGroupPos'))(pkg_cc_const.C_ATTR_DESC_POS) := xmldom.getAttribute(col_el, 'desc');
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 3) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '        desc=' || upper(xmldom.getAttribute(col_el, 'desc')), null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;

                g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(xmldom.getAttribute(col_el, 'absGroupPos'))(pkg_cc_const.C_ATTR_TYPE_POS) := upper(xmldom.getAttribute(col_el, 'valueType'));
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '        valueType=' || upper(xmldom.getAttribute(col_el, 'valueType')), null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
   
                g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(xmldom.getAttribute(col_el, 'absGroupPos'))(pkg_cc_const.C_ATTR_AGGRTYPE_POS) := xmldom.getAttribute(col_el, 'aggrType');
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '        aggrType=' || upper(xmldom.getAttribute(col_el, 'aggrType')), null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;

                g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(xmldom.getAttribute(col_el, 'absGroupPos'))(pkg_cc_const.C_ATTR_GRPPOS_POS) := xmldom.getAttribute(col_el, 'absGroupPos');
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '        absGroupPos=' || upper(xmldom.getAttribute(col_el, 'absGroupPos')), null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;               
                    
                g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(xmldom.getAttribute(col_el, 'absGroupPos'))(pkg_cc_const.C_ATTR_PRNTGRPPOS_POS) := xmldom.getAttribute(col_el, 'parentPos');
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '        parentPos=' || upper(xmldom.getAttribute(col_el, 'parentPos')), null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;

                g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(xmldom.getAttribute(col_el, 'absGroupPos'))(pkg_cc_const.C_ATTR_XMLPOS_POS) := xmldom.getAttribute(col_el, 'absPos');
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '        absPos=' || upper(xmldom.getAttribute(col_el, 'absPos')), null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;

                g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(xmldom.getAttribute(col_el, 'absGroupPos'))(pkg_cc_const.C_ATTR_PIVOTPOS_POS) := xmldom.getAttribute(col_el, 'pivotPos');
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '        pivotPos=' || upper(xmldom.getAttribute(col_el, 'pivotPos')), null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if; 
 
                g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(xmldom.getAttribute(col_el, 'absGroupPos'))(pkg_cc_const.C_ATTR_PIVOTROW_POS) := xmldom.getAttribute(col_el, 'pivotRowPos');
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '        pivotRowPos=' || upper(xmldom.getAttribute(col_el, 'pivotRowPos')), null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
       
         g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(xmldom.getAttribute(col_el, 'absGroupPos'))(pkg_cc_const.C_ATTR_PIVOTROW_POS) := xmldom.getAttribute(col_el, 'posInGroup');
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '        posInGroup=' || upper(xmldom.getAttribute(col_el, 'posInGroup')), null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;

                -- если колонка является атрибутом таблицы фактов или показателем то 
                -- добавляем непустую таблицу фактов
                if (upper(xmldom.getAttribute(col_el, 'type')) = pkg_cc_const.C_PFX_FACT_ATTR_TYPE 
									   or upper(xmldom.getAttribute(col_el, 'type')) = pkg_cc_const.C_PFX_FACT_MEAS_TYPE) then
                  begin
										-- если ещё не добавляли текущюю группу, то добавляем
										if (g_GetNotDummyFactByPrcIx(p_prc_id)(g_GetNotDummyFactByPrcIx(p_prc_id).count) != grp_number) then
                      g_GetNotDummyFactByPrcIx(p_prc_id)(g_GetNotDummyFactByPrcIx(p_prc_id).count + 1) := grp_number;
                    end if;
                  exception
                    when NO_DATA_FOUND then
                      -- если упали сюда, значит это первая запись в массив 
                      g_GetNotDummyFactByPrcIx(p_prc_id)(1) := grp_number;
                    when others then
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 0) then
  pkg_cc_log.op_log(p_prc_id, null, 'Блок настроек', 'Ошибки настроек непустых таблиц фактов', logmsg, pkg_cc_log.C_OP_LOG_ERROR_MSG, sqlerrm);
end if;
             
                    raise;
                  end;
                end if;

                -- привязка позиции колонки в группе к позиции в xml
                g_GetColGrpPosByPrcCol(p_prc_id)(xmldom.getAttribute(col_el, 'absPos')) := xmldom.getAttribute(col_el, 'absGroupPos');

                -- привязка позиции в xml к позиции в группе
                g_GetColXmlPosByPrcGrpCol(p_prc_id)(grp_number)(xmldom.getAttribute(col_el, 'absGroupPos')) := xmldom.getAttribute(col_el, 'absPos');
                
                begin 
                  g_GetColGrpPosByPrcGrpIx(p_prc_id)(grp_number)(g_GetColGrpPosByPrcGrpIx(p_prc_id)(grp_number).count + 1) := xmldom.getAttribute(col_el, 'absGroupPos');
                exception
                  when NO_DATA_FOUND then
                    -- если упали сюда, значит это первая запись в массив 
                    g_GetColGrpPosByPrcGrpIx(p_prc_id)(grp_number)(1) := xmldom.getAttribute(col_el, 'absGroupPos');
                  when others then
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 0) then
  pkg_cc_log.op_log(p_prc_id, null, 'Блок настроек', 'Ошибки настроек позиций колонок в группах', logmsg, pkg_cc_log.C_OP_LOG_ERROR_MSG, sqlerrm);
end if;
             
                  raise;
                end; 
                
                g_GetGrpByPrcCol(p_prc_id)(xmldom.getAttribute(col_el, 'absPos')) := grp_number;
                
                if (g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(xmldom.getAttribute(col_el, 'absGroupPos'))(pkg_cc_const.C_ATTR_CTYPE_POS) = pkg_cc_const.C_PFX_DIM_ATTR_TYPE) then
                  if (g_tmpDimNameIx.count = 0) then
                      g_tmpDimNameIx(1) := g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(xmldom.getAttribute(col_el, 'absGroupPos'))(pkg_cc_const.C_ATTR_NAME_POS);
                      g_GetIsRepeatDimPrcCol(p_prc_id)(g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(xmldom.getAttribute(col_el, 'absGroupPos'))(pkg_cc_const.C_ATTR_XMLPOS_POS)) := 0;
                  
                  else
                    -- заполняем массив флагов повторов справочников
                    for name_ix in 1 .. g_tmpDimNameIx.count
                      loop
                        if (g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(xmldom.getAttribute(col_el, 'absGroupPos'))(pkg_cc_const.C_ATTR_NAME_POS) = g_tmpDimNameIx(name_ix)) then
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '        ПОВТОР в позиции =' || g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(xmldom.getAttribute(col_el, 'absGroupPos'))(pkg_cc_const.C_ATTR_XMLPOS_POS), null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
                          g_GetIsRepeatDimPrcCol(p_prc_id)(g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(xmldom.getAttribute(col_el, 'absGroupPos'))(pkg_cc_const.C_ATTR_XMLPOS_POS)) := 1;
                          exit;
                          
                        else
                          if (name_ix = g_tmpDimNameIx.count) then
                            g_tmpDimNameIx(g_tmpDimNameIx.count + 1) := g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(xmldom.getAttribute(col_el, 'absGroupPos'))(pkg_cc_const.C_ATTR_NAME_POS);
                            g_GetIsRepeatDimPrcCol(p_prc_id)(g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(xmldom.getAttribute(col_el, 'absGroupPos'))(pkg_cc_const.C_ATTR_XMLPOS_POS)) := 0;
                          end if;
                        end if;
                      end loop;
                   
                  end if;
                
                else
									g_GetIsRepeatDimPrcCol(p_prc_id)(g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(xmldom.getAttribute(col_el, 'absGroupPos'))(pkg_cc_const.C_ATTR_XMLPOS_POS)) := 0;
                end if;
                
              end loop;
               
          end loop;
      end loop;
      
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'g_GetGrpByPrcIx(p_prc_id).count=' || g_GetGrpByPrcIx(p_prc_id).count, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if; 


-- Привязка родительских и подчиненных справочников
      for grp_ix in 1 .. g_GetGrpByPrcIx(p_prc_id).count
        loop
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'grp_ix=' || grp_ix, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if; 

          grp_number := g_GetGrpByPrcIx(p_prc_id)(grp_ix);
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'grp_number=' || grp_number, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;

-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'g_GetColGrpPosByPrcGrpIx(p_prc_id)(grp_number).count=' || g_GetColGrpPosByPrcGrpIx(p_prc_id)(grp_number).count, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if; 
     
          for col_ix in 1 .. g_GetColGrpPosByPrcGrpIx(p_prc_id)(grp_number).count
						loop 
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '    col_ix=' || col_ix, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
              col_number := g_GetColGrpPosByPrcGrpIx(p_prc_id)(grp_number)(col_ix);
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '    col_number=' || col_number, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;


                  if (g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(col_number)(pkg_cc_const.C_ATTR_PRNTGRPPOS_POS) != '0') then
                    -- кол-во родительских справочников у колонки
                    l_prnt_count := fnc_get_cnt_by_separator(
                                      p_prc_id, 
                                      g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(col_number)(pkg_cc_const.C_ATTR_PRNTGRPPOS_POS), 
                                      pkg_cc_const.C_PRNT_SEPARATOR);
                  else
										l_prnt_count := 0;
                  end if;
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '    l_prnt_count=' || l_prnt_count, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
                  -- парсим колонки для посчета кол-ва фактовых таблиц
                  for prnt_ix in 1 .. l_prnt_count
                    loop
-- log              
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '        prnt_ix=' || prnt_ix, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
                      
                      l_prnt_next_pos := instr(g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(col_number)(pkg_cc_const.C_ATTR_PRNTGRPPOS_POS), pkg_cc_const.C_PRNT_SEPARATOR, 1, prnt_ix);
-- log              
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '        l_prnt_next_pos=' || l_prnt_next_pos, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
                      -- вырезаем строку с атрибутами колонки
                      if (prnt_ix = l_prnt_count) then
                        l_prnt := substr(g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(col_number)(pkg_cc_const.C_ATTR_PRNTGRPPOS_POS), l_prnt_cur_pos);
                      else
                        l_prnt := substr(g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(col_number)(pkg_cc_const.C_ATTR_PRNTGRPPOS_POS), l_prnt_cur_pos, l_prnt_next_pos - l_prnt_cur_pos);
                      end if;
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '        l_prnt=' || l_prnt, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if; 
                      begin
                        -- смотрим есть ли родитель в кэше
                        if (l_prnt != 0) then

                          -- ищем родителя если находим выходим из цикла иначе возбуждаем исключение
                          -- если поиск первый то массив g_GetPrntXmlPosByPrcIx(p_prc_id) падет с NO_DATA_FOUND
                          for p_ix in 1 .. g_GetPrntXmlPosByPrcIx(p_prc_id).count
														loop
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '            p_ix=' || p_ix, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;  
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '            g_GetPrntXmlPosByPrcIx(p_prc_id)(p_ix)=' || g_GetPrntXmlPosByPrcIx(p_prc_id)(p_ix), null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '            g_GetPrntXmlPosByPrcIx(p_prc_id).count=' || g_GetPrntXmlPosByPrcIx(p_prc_id).count, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '            g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(l_prnt)(pkg_cc_const.C_ATTR_XMLPOS_POS)=' || g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(l_prnt)(pkg_cc_const.C_ATTR_XMLPOS_POS), null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;

                              -- если позиция родительского справочника в строке соответствует позиции текущего найденного родительского справочника
                              -- то делаем привязки
															if (g_GetPrntXmlPosByPrcIx(p_prc_id)(p_ix) = g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(l_prnt)(pkg_cc_const.C_ATTR_XMLPOS_POS)) then 
                                
                                -- привязываем подчиненный справочник к позиции колонки в строке
                                g_GetChldXmlPosByPrcColIx(p_prc_id)(g_GetPrntXmlPosByPrcIx(p_prc_id)(p_ix))(g_GetChldXmlPosByPrcColIx(p_prc_id)(g_GetPrntXmlPosByPrcIx(p_prc_id)(p_ix)).count + 1) := g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(col_number)(pkg_cc_const.C_ATTR_GRPPOS_POS);
                                
                                -- привязываем подчиненный справочник к позиции колонки в группе
                                g_GetChldGrpPosByPrcGrpColIx(p_prc_id)(grp_number)(l_prnt)(g_GetChldGrpPosByPrcGrpColIx(p_prc_id)(grp_number)(l_prnt).count + 1) := g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(col_number)(pkg_cc_const.C_ATTR_GRPPOS_POS);
                                
                                exit;
                                
                              else
																if (p_ix = g_GetPrntXmlPosByPrcIx(p_prc_id).count) then
																  RAISE NO_DATA_FOUND;
                                end if;
                              end if;
                            end loop;
                          
                        end if;
                              
                      exception
                        when NO_DATA_FOUND then
                          -- список позиций родительских справочников в строке
                          begin
                            g_GetPrntXmlPosByPrcIx(p_prc_id)(g_GetPrntXmlPosByPrcIx(p_prc_id).count + 1) := g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(l_prnt)(pkg_cc_const.C_ATTR_XMLPOS_POS);
                          exception
														when NO_DATA_FOUND then
														  g_GetPrntXmlPosByPrcIx(p_prc_id)(1) := g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(l_prnt)(pkg_cc_const.C_ATTR_XMLPOS_POS);
													end;

													-- привязываем родительский справочник к группе
                          begin
                            g_GetPrntGrpPosByPrcGrpIx(p_prc_id)(g_GetGrpByPrcIx(p_prc_id)(grp_ix))(g_GetPrntGrpPosByPrcGrpIx(p_prc_id)(grp_number).count + 1) := l_prnt;
                          exception
                            when NO_DATA_FOUND then
                              g_GetPrntGrpPosByPrcGrpIx(p_prc_id)(g_GetGrpByPrcIx(p_prc_id)(grp_ix))(1) := l_prnt;
                          end;
                                                      
                          -- привязываем подчиненный справочник к позиции колонки в строке
                          begin
                            g_GetChldXmlPosByPrcColIx(p_prc_id)(g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(l_prnt)(pkg_cc_const.C_ATTR_XMLPOS_POS))(g_GetChldXmlPosByPrcColIx(p_prc_id)(g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(l_prnt)(pkg_cc_const.C_ATTR_XMLPOS_POS)).count + 1) := g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(col_number)(pkg_cc_const.C_ATTR_XMLPOS_POS); 
                          exception
														when NO_DATA_FOUND then
															g_GetChldXmlPosByPrcColIx(p_prc_id)(g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(l_prnt)(pkg_cc_const.C_ATTR_XMLPOS_POS))(1) := g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(col_number)(pkg_cc_const.C_ATTR_XMLPOS_POS);
													end;
                          
                          -- привязываем подчиненный справочник к позиции колонки в группе
                          begin
														g_GetChldGrpPosByPrcGrpColIx(p_prc_id)(grp_number)(l_prnt)(g_GetChldGrpPosByPrcGrpColIx(p_prc_id)(grp_number)(l_prnt).count + 1) := g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(col_number)(pkg_cc_const.C_ATTR_GRPPOS_POS);
                          exception
                            when NO_DATA_FOUND then
                              g_GetChldGrpPosByPrcGrpColIx(p_prc_id)(grp_number)(l_prnt)(1) := g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(col_number)(pkg_cc_const.C_ATTR_GRPPOS_POS);
                          end;
                          
                        when others then
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 0) then
  pkg_cc_log.op_log(p_prc_id, null, 'Блок кэша', 'Ошибки кэша родительских справочников', logmsg, pkg_cc_log.C_OP_LOG_ERROR_MSG, sqlerrm);
end if;
               
                          raise;
                      end; 
                            
                      l_prnt_cur_pos := l_prnt_next_pos + length(pkg_cc_const.C_PRNT_SEPARATOR); -- пропускаем разделитель
                            
                    end loop; -- цикл по родителям
                  
            end loop; -- цикл колонок
            
        end loop; -- цикл групп
        
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Завершение процедуры', logmsg, pkg_cc_log.C_OP_LOG_INIT_MSG, null);    
end if;

  exception
    when others then
 
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 0) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Ошибки процедуры', logmsg, pkg_cc_log.C_OP_LOG_ERROR_MSG, sqlerrm);
end if;
 
      raise;
  end prc_set_settings;
  
  -- кэширование
  procedure prc_set_cash (
    p_prc_id in l001_cc_op_log.prc_id%type,
    p_pm_name in varchar2
  )
  is 
     logmsg  varchar2(255) := 'Заполнение кэша';
     logsender varchar2(255) := 'pkg_cc_structure.prc_set_cash';
     
     l_pm_pfx varchar2(30);
     l_tbl_pfx varchar2(30);
     l_tbl_name varchar2(30);
     l_col_name varchar2(30);
     
     l_obj_pos number;
     
     l_grp number;
     l_col_grppos number;
     l_col_xmlpos number;
  begin
		 
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Старт процедуры', logmsg, pkg_cc_log.C_OP_LOG_INIT_MSG, null);    
end if;

    l_pm_pfx := fnc_get_prefix_by_name(p_prc_id, pkg_cc_const.C_PFX_PM_TYPE, p_pm_name, null, null, null);
    
    if (l_pm_pfx is null) then
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 5) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '  Не найдет префикс модели', null, PKG_CC_LOG.C_OP_LOG_INIT_MSG, null);
end if;
			raise NO_DATA_FOUND;
    end if;
    
    -- кэшируем префикс модели для процесса
    g_GetPmPfxByPrc(p_prc_id) := l_pm_pfx;

-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '  g_GetGrpByPrcIx(p_prc_id).count=' || g_GetGrpByPrcIx(p_prc_id).count, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;

    for grp_ix in 1 .. g_GetGrpByPrcIx(p_prc_id).count
      loop
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'grp_ix=' || grp_ix, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;

        l_grp := g_GetGrpByPrcIx(p_prc_id)(grp_ix);
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'l_grp=' || l_grp, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;

-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '  g_GetColGrpPosByPrcGrpIx(p_prc_id)(l_grp).count=' || g_GetColGrpPosByPrcGrpIx(p_prc_id)(l_grp).count, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
     
      for col_ix in 1 .. g_GetColGrpPosByPrcGrpIx(p_prc_id)(l_grp).count
        loop
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '     col_ix=' || col_ix, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;

          l_col_grppos := g_GetColGrpPosByPrcGrpIx(p_prc_id)(l_grp)(col_ix);

-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '     l_col_grppos=' || l_col_grppos, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;

          l_col_xmlpos := g_GetColXmlPosByPrcGrpCol(p_prc_id)(l_grp)(l_col_grppos);
          
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '     l_col_xmlpos=' || l_col_xmlpos, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;

-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '     g_GetIsRepeatDimPrcCol(p_prc_id)(l_col_xmlpos)=' || g_GetIsRepeatDimPrcCol(p_prc_id)(l_col_xmlpos), null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;

          -- если это повтор справочника то переопределяем имя таблицы
          if (g_GetIsRepeatDimPrcCol(p_prc_id)(l_col_xmlpos) = 1) then
            l_tbl_name := g_GetAttrByPrcGrpColIx(p_prc_id)(l_grp)(l_col_grppos)(pkg_cc_const.C_ATTR_NAME_POS);
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '     l_tbl_name=' || l_tbl_name, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;

            l_tbl_pfx := fnc_get_prefix_by_name(p_prc_id, pkg_cc_const.C_PFX_DIM_TBL_TYPE, p_pm_name, l_tbl_name, null, null);
            
            l_obj_pos := fnc_get_col_pos_by_pfx_dim(p_prc_id, l_pm_pfx, l_tbl_pfx);
            
            l_col_name := fnc_get_col_name_by_pos_num(p_prc_id, l_pm_pfx, l_obj_pos);
             
            l_tbl_name := fnc_get_tbl_name_by_pos_num(p_prc_id, l_pm_pfx, l_obj_pos);
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '     l_tbl_name=' || l_tbl_name, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;

-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '     ПОДМЕНА на=' || l_obj_pos, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;

            g_GetVirtColPosByPrcCol(p_prc_id)(l_col_xmlpos) := l_obj_pos;
          else
            
            l_tbl_name := fnc_get_tbl_name_by_pos_num(p_prc_id, l_pm_pfx, l_col_xmlpos);
            l_col_name := fnc_get_col_name_by_pos_num(p_prc_id, l_pm_pfx, l_col_xmlpos);
            
						g_GetVirtColPosByPrcCol(p_prc_id)(l_col_xmlpos) := l_col_xmlpos;
          end if;

          -- кэшируем название колонки
          g_GetColNameByPrcCol(p_prc_id)(l_col_xmlpos) := l_col_name;
            
          -- кэшируем название таблицы
          g_GetTblNameByPrcCol(p_prc_id)(l_col_xmlpos) := l_tbl_name;
            
        end loop; -- колонки
      end loop; -- группы
    
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then 
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Завершение процедуры', logmsg, pkg_cc_log.C_OP_LOG_SUCCESS_MSG, null);
end if;

  exception
    when others then  
 
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 0) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Ошибки процедуры', logmsg, pkg_cc_log.C_OP_LOG_ERROR_MSG, sqlerrm);
end if;
 
			raise;
  end prc_set_cash;
  
  -- получение префикса модели
  function fnc_get_cash(
    p_prc_id in l001_cc_op_log.prc_id%type,
    p_cash_type in varchar2,
    p_col_xmlpos in number
  ) return varchar2
  is
     logmsg  varchar2(255) := 'Получение значений из кэша';
     logsender varchar2(255) := 'pkg_cc_structure.fnc_get_cash';
     
     l_cash_val varchar2(30);
  begin
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Старт функции', logmsg, pkg_cc_log.C_OP_LOG_INIT_MSG, null);    
end if;
 -- входные параметры
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'IN p_cash_type=' || p_cash_type, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'IN p_col_xmlpos=' || p_col_xmlpos, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
          
    if (p_cash_type = pkg_cc_const.C_CASH_PM_TYPE) then
      l_cash_val := pkg_cc_structure.g_GetPmPfxByPrc(p_prc_id);
    
    elsif (p_cash_type = pkg_cc_const.C_CASH_TBL_TYPE) then
      l_cash_val := g_GetTblNameByPrcCol(p_prc_id)(p_col_xmlpos);
      
    elsif (p_cash_type = pkg_cc_const.C_CASH_COL_TYPE) then
      l_cash_val := g_GetColNameByPrcCol(p_prc_id)(p_col_xmlpos);
      
    end if;
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'l_cash_val=' || l_cash_val, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;

-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Завершение функции', logmsg, pkg_cc_log.C_OP_LOG_INIT_MSG, null);    
end if;

    return l_cash_val;
    
  exception
	  when NO_DATA_FOUND then
			
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'l_cash_val=' || l_cash_val, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Завершение функции', logmsg, pkg_cc_log.C_OP_LOG_INIT_MSG, null);    
end if;      
      
      return null;
                                              
    when others then
		
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 0) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Ошибки функции', logmsg, pkg_cc_log.C_OP_LOG_ERROR_MSG, sqlerrm);
end if;

      raise;
  end fnc_get_cash;
  
  -- очистка кэша
  procedure prc_clear_cash (
    p_prc_id in l001_cc_op_log.prc_id%type
  )
  is 
     logmsg  varchar2(255) := 'Очистка кэша';
     logsender varchar2(255) := 'pkg_cc_structure.clear_cash';
  begin
		 
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Старт процедуры', logmsg, pkg_cc_log.C_OP_LOG_INIT_MSG, null);    
end if;
    
    g_tmpDimNameIx := g_CharEmpty; 
    g_GetIsRepeatDimPrcCol := g_NumEmpty_2;
    
    
    g_GetGrpByPrcIx := g_NumEmpty_2;
    
    g_GetColGrpPosByPrcGrpIx := g_NumEmpty_3;
    g_GetColXmlPosByPrcGrpCol := g_NumEmpty_3;
    
    g_GetGrpByPrcCol := g_NumEmpty_2;
    g_GetColGrpPosByPrcCol := g_NumEmpty_2;
    
    g_GetAttrByPrcGrpColIx := g_CharEmpty_4;
    g_GetPmPfxByPrc := g_CharEmpty;
    g_GetTblNameByPrcCol := g_CharEmpty_2;
    g_GetColNameByPrcCol := g_CharEmpty_2;
    g_GetVirtColPosByPrcCol := g_NumEmpty_2;
    g_GetNotDummyFactByPrcIx := g_NumEmpty_2;
    g_GetPrntXmlPosByPrcIx := g_NumEmpty_2;    
    g_GetPrntGrpPosByPrcGrpIx := g_NumEmpty_3;
    g_GetChldGrpPosByPrcGrpColIx := g_NumEmpty_4;
    g_GetChldXmlPosByPrcColIx := g_NumEmpty_3;
    
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then 
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Завершение процедуры', logmsg, pkg_cc_log.C_OP_LOG_SUCCESS_MSG, null);
end if;

  exception
    when others then  
 
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 0) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Ошибки процедуры', logmsg, pkg_cc_log.C_OP_LOG_ERROR_MSG, sqlerrm);
end if;
 
			raise;
  end prc_clear_cash;

  -- процедура создания модели
  function fnc_create_dim (
    p_prc_id in l001_cc_op_log.prc_id%type,
    p_pm_name varchar2,
    p_attr_name varchar2,
    p_attr_type varchar2,
    p_attr_comm varchar2,
    p_grp number,
    p_attr_position number,
    p_obj_position number
  ) return varchar2
  is
     logmsg  varchar2(255) := 'Создание справочника';
     logsender varchar2(255) := 'pkg_cc_structure.fnc_create_dim'; 
     
     l_prefix_pm varchar2(30);
     l_prefix_dim_tbl varchar2(30);
     l_prefix_inner_dim_tbl varchar2(30);
     l_prefix_attr varchar2(30);
     l_prefix_obj varchar2(30);
     
     l_dim_tbl_name varchar2(30);
     l_dim_tbl_desc varchar2(4000);
     l_dim_inner_tbl_name varchar2(30);
     l_dim_tbl_cols varchar2(32767);
     l_dim_inner_tbl_cols varchar2(32767);
     
     l_dim_tbl_comm varchar2(32767);
     l_dim_inner_tbl_comm varchar2(32767);

     l_seq_name varchar2(30);
     
     l_col_count number;
     
     l_attr_comm varchar2(4000);
     
     l_chld_pos number;
     l_obj_pos number;
     l_flag_parent boolean := FALSE; 
     
     table_el dbms_xmldom.DOMElement;
      tcols_el dbms_xmldom.DOMElement;
       col_el dbms_xmldom.DOMElement;
       
      Dtable_nd dbms_xmldom.DOMNode;
       Dtcols_nd dbms_xmldom.DOMNode;
        col_nd dbms_xmldom.DOMNode;
        
             
     -- темповая переменная для проверки наличия значений в кэшэ
     --tmp_number NUMBER := 0;
  begin
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Старт функции', logmsg, pkg_cc_log.C_OP_LOG_INIT_MSG, null);    
end if;
 
 -- входные параметры
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'IN p_pm_name=' || p_pm_name, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'IN p_attr_name=' || p_attr_name, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'IN p_attr_type=' || p_attr_type, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'IN p_attr_comm=' || p_attr_comm, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'IN p_grp=' || p_grp, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'IN p_attr_position=' || p_attr_position, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'IN p_obj_position=' || p_obj_position, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
  
    begin        
      for prnt_ix in 1 .. g_GetPrntGrpPosByPrcGrpIx(p_prc_id)(p_grp).count
        loop
          if (g_GetPrntGrpPosByPrcGrpIx(p_prc_id)(p_grp)(prnt_ix) = p_attr_position) then
            l_flag_parent := TRUE;
          end if;
        end loop;
    exception
			when NO_DATA_FOUND then
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 5) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'В группе нет родительских справочников', null , PKG_CC_LOG.C_OP_LOG_INFO_MSG, null);
end if;
    end;
    
    l_prefix_pm := fnc_get_prefix_by_name(p_prc_id, pkg_cc_const.C_PFX_PM_TYPE, p_pm_name, null, null, null);
    
    l_prefix_dim_tbl := fnc_get_next_prefix(p_prc_id, pkg_cc_const.C_PFX_DIM_TBL_TYPE, l_prefix_pm, null);
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'l_prefix_dim_tbl=' || l_prefix_dim_tbl, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;

    l_dim_tbl_name := l_prefix_pm || pkg_cc_const.C_PART_NAME_DELIMETER || l_prefix_dim_tbl || pkg_cc_const.C_PART_NAME_DELIMETER || p_attr_name;
    
    l_dim_tbl_desc := pkg_cc_const.C_DIM_TBL_DESC_PRT  || '=' || p_attr_comm || '=';
          
    l_seq_name := pkg_cc_const.C_SEQ_PREFIX_NAME_PART || pkg_cc_const.C_PART_NAME_DELIMETER || l_prefix_pm || pkg_cc_const.C_PART_NAME_DELIMETER || l_prefix_dim_tbl || pkg_cc_const.C_PART_NAME_DELIMETER || pkg_cc_const.C_PFX_DIM_ATTR_TYPE; 
    prc_create_sequence(p_prc_id, upper(l_seq_name));   
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 5) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Последовательность для структуры справочника '|| l_prefix_dim_tbl ||' создана', null, PKG_CC_LOG.C_OP_LOG_INFO_MSG, null);   
end if;

    l_prefix_obj := fnc_get_next_prefix(p_prc_id, pkg_cc_const.C_PFX_OBJ_TYPE, l_prefix_pm, null);
    l_prefix_attr := fnc_get_next_prefix(p_prc_id, pkg_cc_const.C_PFX_DIM_ATTR_TYPE, l_prefix_pm, l_prefix_dim_tbl);
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'l_prefix_obj + ' || pkg_cc_const.C_PART_NAME_DELIMETER ||' + l_prefix_attr=' || l_prefix_obj || pkg_cc_const.C_PART_NAME_DELIMETER || l_prefix_attr, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
    
    -- + обязательное поле;
    l_dim_tbl_cols := 
      pkg_cc_const.C_DIM_ID_NAME 
      || ' '
      || pkg_cc_const.C_DIM_ID_TYPE 
      || ', ';
    
    -- комментарий = имя:комментарий 
    -- + обязательное поле в комментарий   
    l_dim_tbl_comm := 
      pkg_cc_const.C_DIM_ID_NAME 
      || pkg_cc_const.C_COMM_PRT_SEPARATOR 
      || pkg_cc_const.C_DIM_ID_DESC
      || pkg_cc_const.C_COMM_SEPARATOR;

-- XML для справочника
    -- вкладываем конкретную DIM таблицу в таблицы
    table_el := dbms_xmldom.createElement(doc, pkg_cc_const.C_TABLE_TAG);
    Dtable_nd := dbms_xmldom.makeNode(table_el);
    Dtable_nd := dbms_xmldom.appendChild(tbls_nd, Dtable_nd);
                  
    dbms_xmldom.setAttribute(table_el, pkg_cc_const.C_TABLE_ATTR1, l_dim_tbl_name);
    dbms_xmldom.setAttribute(table_el, pkg_cc_const.C_TABLE_ATTR2, l_dim_tbl_desc);
    
    if (l_flag_parent) then    
      dbms_xmldom.setAttribute(table_el, pkg_cc_const.C_TABLE_ATTR3, pkg_cc_const.C_TABLE_TYPE_VAL4);
    else
			if (g_GetAttrByPrcGrpColIx(p_prc_id)(p_grp)(p_attr_position)(pkg_cc_const.C_ATTR_PIVOTPOS_POS) is null) then
        dbms_xmldom.setAttribute(table_el, pkg_cc_const.C_TABLE_ATTR3, pkg_cc_const.C_TABLE_TYPE_VAL2);
      else
				dbms_xmldom.setAttribute(table_el, pkg_cc_const.C_TABLE_ATTR3, pkg_cc_const.C_TABLE_TYPE_VAL5);
      end if;
    end if;
    
    dbms_xmldom.setAttribute(table_el, pkg_cc_const.C_TABLE_ATTR4, p_grp);
    
    -- вкладываем колонки в таблицу
    tcols_el := dbms_xmldom.createElement(doc, pkg_cc_const.C_TABLE_COLS_TAG);
    Dtcols_nd := dbms_xmldom.makeNode(tcols_el);
    Dtcols_nd := dbms_xmldom.appendChild(Dtable_nd, Dtcols_nd);
                  
    -- + обязательное поле
    -- вкладываем конкретную колонку в колонки таблицы
    col_el := dbms_xmldom.createElement(doc, pkg_cc_const.C_COL_TAG);
    col_nd := dbms_xmldom.makeNode(col_el);           
    col_nd := dbms_xmldom.appendChild(Dtcols_nd, col_nd);
                  
    dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR1, pkg_cc_const.C_DIM_ID_NAME);
    dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR2, pkg_cc_const.C_DIM_ID_TYPE);
    dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR3, pkg_cc_const.C_DIM_ID_DESC);
    dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR4, 'true');
    dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR5, 'false');
    dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR6, '');
    dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR7, '');
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 5) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Начато формирование XML для справочника ' || l_dim_tbl_name, null , PKG_CC_LOG.C_OP_LOG_INFO_MSG, null);
end if;
    
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'l_flag_parent=' || fnc_boolToChar(l_flag_parent), null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if; 
 
    if (l_flag_parent) then
    
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 5) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Старт поиска подчиненных справочников', null , PKG_CC_LOG.C_OP_LOG_INFO_MSG, null);
end if;
 
      for chld_ix in 1 .. g_GetChldGrpPosByPrcGrpColIx(p_prc_id)(p_grp)(p_attr_position).count
        loop
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '  chld_ix=' || chld_ix, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;

					l_chld_pos := g_GetChldGrpPosByPrcGrpColIx(p_prc_id)(p_grp)(p_attr_position)(chld_ix);
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '  l_chld_pos=' || l_chld_pos, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
 
          l_obj_pos := g_GetAttrByPrcGrpColIx(p_prc_id)(p_grp)(l_chld_pos)(pkg_cc_const.C_ATTR_XMLPOS_POS); 
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '  l_obj_pos=' || l_obj_pos, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
          
            -- смотрим есть лу уже такой справочник
            l_prefix_inner_dim_tbl := fnc_get_prefix_by_name(
                                        p_prc_id, 
                                        pkg_cc_const.C_PFX_DIM_TBL_TYPE, 
                                        p_pm_name, 
                                        g_GetAttrByPrcGrpColIx(p_prc_id)(p_grp)(l_obj_pos)(pkg_cc_const.C_ATTR_NAME_POS), 
                                        null,
                                        null
                                      );
                
            -- если справочника ещё нет то создаем
            if (l_prefix_inner_dim_tbl is null) then
              -- создаем справочник вызывая себя же ещё раз
              l_prefix_inner_dim_tbl := fnc_create_dim(
                                          p_prc_id, 
                                          p_pm_name, 
                                          g_GetAttrByPrcGrpColIx(p_prc_id)(p_grp)(l_obj_pos)(pkg_cc_const.C_ATTR_NAME_POS), 
                                          g_GetAttrByPrcGrpColIx(p_prc_id)(p_grp)(l_obj_pos)(pkg_cc_const.C_ATTR_TYPE_POS), 
                                          g_GetAttrByPrcGrpColIx(p_prc_id)(p_grp)(l_obj_pos)(pkg_cc_const.C_ATTR_DESC_POS), 
                                          g_GetAttrByPrcGrpColIx(p_prc_id)(p_grp)(l_obj_pos)(pkg_cc_const.C_ATTR_GRP_POS), 
                                          g_GetAttrByPrcGrpColIx(p_prc_id)(p_grp)(l_obj_pos)(pkg_cc_const.C_ATTR_GRPPOS_POS), 
                                          g_GetAttrByPrcGrpColIx(p_prc_id)(p_grp)(l_obj_pos)(pkg_cc_const.C_ATTR_XMLPOS_POS)
                                        );
            end if;
   
            l_dim_inner_tbl_name := 
              l_prefix_pm 
              || pkg_cc_const.C_PART_NAME_DELIMETER 
              || l_prefix_inner_dim_tbl 
              || pkg_cc_const.C_PART_NAME_DELIMETER
              || g_GetAttrByPrcGrpColIx(p_prc_id)(p_grp)(l_obj_pos)(pkg_cc_const.C_ATTR_NAME_POS);
                
            -- делаем ссылку в родительском справочнике
            -- переопределяем на имя и тип ссылки
            l_dim_inner_tbl_cols := 
              l_dim_inner_tbl_cols 
              || l_prefix_inner_dim_tbl || pkg_cc_const.C_PART_NAME_DELIMETER || pkg_cc_const.C_REF_ID_PART_NAME
              || ' '
              || pkg_cc_const.C_REF_ID_TYPE
              || ', ';
              
            -- делаем комментарий на ссылку
            l_dim_inner_tbl_comm := 
              l_dim_inner_tbl_comm
              || l_prefix_inner_dim_tbl || pkg_cc_const.C_PART_NAME_DELIMETER || pkg_cc_const.C_REF_ID_PART_NAME
              || pkg_cc_const.C_COMM_PRT_SEPARATOR  
              || pkg_cc_const.C_REF_ID_PART_DESC || l_prefix_inner_dim_tbl
              || pkg_cc_const.C_COMM_SEPARATOR;
              
-- XML для справочника (ветка ссылок на справочники)
            -- + поле в факт
            -- вкладываем ссылку в колонки таблицы
            col_el := dbms_xmldom.createElement(doc, pkg_cc_const.C_COL_TAG);
            col_nd := dbms_xmldom.makeNode(col_el);
            col_nd := dbms_xmldom.appendChild(Dtcols_nd, col_nd);
                    
            dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR1, l_prefix_inner_dim_tbl || pkg_cc_const.C_PART_NAME_DELIMETER || pkg_cc_const.C_REF_ID_PART_NAME);
            dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR2, pkg_cc_const.C_REF_ID_TYPE);
            dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR3, pkg_cc_const.C_REF_ID_PART_DESC || l_prefix_inner_dim_tbl);
            dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR4, 'false');
            dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR5, 'true');
            dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR6, '');
            dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR7, l_dim_inner_tbl_name);
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 5) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Завершено формирование ссылки на справочник '|| l_dim_inner_tbl_name || ' в XML для справочника ' || l_dim_tbl_name, null , PKG_CC_LOG.C_OP_LOG_INFO_MSG, null);   
end if;

        end loop;
    end if;
    
-- добавляем атрибут из входящих параметров, если справочник не подчиненный то l_dim_inner_tbl_cols будет пустой
    l_dim_tbl_cols :=  
      l_dim_tbl_cols 
      || l_dim_inner_tbl_cols 
      || l_prefix_obj || pkg_cc_const.C_PART_NAME_DELIMETER || l_prefix_attr || pkg_cc_const.C_PART_NAME_DELIMETER || p_attr_name 
      || ' '
      || p_attr_type 
      || ', ';    
      
    l_dim_tbl_comm :=  
      l_dim_tbl_comm 
      || l_dim_inner_tbl_comm
      || l_prefix_obj || pkg_cc_const.C_PART_NAME_DELIMETER || l_prefix_attr || pkg_cc_const.C_PART_NAME_DELIMETER || p_attr_name 
      || pkg_cc_const.C_COMM_PRT_SEPARATOR 
      || p_attr_comm 
      || pkg_cc_const.C_COMM_SEPARATOR;
   
-- XML по ветке атрибутов справочника
    -- + поле справочника
    -- вкладываем конкретную колонку в колонки таблицы
    col_el := dbms_xmldom.createElement(doc, pkg_cc_const.C_COL_TAG);
    col_nd := dbms_xmldom.makeNode(col_el);
    col_nd := dbms_xmldom.appendChild(Dtcols_nd, col_nd);
                
    dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR1, l_prefix_obj || pkg_cc_const.C_PART_NAME_DELIMETER || l_prefix_attr || pkg_cc_const.C_PART_NAME_DELIMETER || p_attr_name);
    dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR2, p_attr_type);
    
    l_attr_comm := p_attr_comm;
            
    -- декодируем кодированные символы
    l_attr_comm := replace(l_attr_comm, pkg_cc_const.C_COMM_SEPARATOR_REPLACE, pkg_cc_const.C_COMM_SEPARATOR);
    l_attr_comm := replace(l_attr_comm, pkg_cc_const.C_COMM_PRT_SEPARATOR_REPLACE, pkg_cc_const.C_COMM_PRT_SEPARATOR);
    l_attr_comm := replace(l_attr_comm, pkg_cc_const.C_QUOT_REPLACE, ''''); -- заменяем на экранированную '
                
    dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR3, l_attr_comm);
    dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR4, 'false');
    dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR5, 'false');
    dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR6, '');
    dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR7, '');
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 5) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Поле в XML добавлено', null , PKG_CC_LOG.C_OP_LOG_INFO_MSG, null);
end if;

    -- + обязательное поле
    l_dim_tbl_cols := l_dim_tbl_cols 
      || pkg_cc_const.C_CREATED_DATE_NAME 
      || ' ' 
      || pkg_cc_const.C_CREATED_DATE_TYPE;
 -- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'l_dim_tbl_cols=' || l_dim_tbl_cols, null, PKG_CC_LOG.C_OP_LOG_VAR, null); 
end if;
 
    -- + обязательное поле в комментарий
    l_dim_tbl_comm := l_dim_tbl_comm 
      || pkg_cc_const.C_CREATED_DATE_NAME 
      || pkg_cc_const.C_COMM_PRT_SEPARATOR 
      || pkg_cc_const.C_CREATED_DATE_TYPE;
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'l_dim_tbl_comm=' || l_dim_tbl_comm, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
               
    prc_create_table(p_prc_id, l_dim_tbl_name, l_dim_tbl_desc, l_dim_tbl_cols, l_dim_tbl_comm);
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 5) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Cправочник '|| l_dim_tbl_name ||' создан', null, PKG_CC_LOG.C_OP_LOG_INFO_MSG, null);   
end if; 
 
    -- кол-во колонок для dummy вставки
    -- кол-во запятых - 3 (потому что 2 обязательный стоблца и одно поле справочника)
    l_col_count := fnc_get_cnt_by_separator(p_prc_id, l_dim_tbl_cols, ',') - 3;
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'для null значений l_col_count=' || l_col_count, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
    
    -- + фиктивная запись в справочник
    prc_insert_dummy(p_prc_id, l_dim_tbl_name, l_col_count);
                    
    l_seq_name := pkg_cc_const.C_SEQ_NAME_PART || pkg_cc_const.C_PART_NAME_DELIMETER || l_prefix_pm || pkg_cc_const.C_PART_NAME_DELIMETER || l_prefix_dim_tbl || pkg_cc_const.C_PART_NAME_DELIMETER || pkg_cc_const.C_DIM_ID_NAME; 
    prc_create_sequence(p_prc_id, l_seq_name);
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 5) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Последовательность '|| l_seq_name ||' для справочника создана', null, PKG_CC_LOG.C_OP_LOG_INFO_MSG, null);   
end if;

-- XML для справочника
    -- + обязательное поле
    -- вкладываем конкретную колонку в колонки таблицы
    col_el := dbms_xmldom.createElement(doc, pkg_cc_const.C_COL_TAG);
    col_nd := dbms_xmldom.makeNode(col_el);
    col_nd := dbms_xmldom.appendChild(Dtcols_nd, col_nd);
                
    dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR1, pkg_cc_const.C_CREATED_DATE_NAME);
    dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR2, pkg_cc_const.C_CREATED_DATE_TYPE);
    dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR3, pkg_cc_const.C_CREATED_DATE_DESC);
    dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR4, 'false');
    dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR5, 'false');
    dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR6, '');
    dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR7, '');
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 5) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Завершено формирование XML для справочника ' || l_dim_tbl_name, null , PKG_CC_LOG.C_OP_LOG_INFO_MSG, null);   
end if;

-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then 
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Завершение функции', logmsg, pkg_cc_log.C_OP_LOG_SUCCESS_MSG, null);
end if;
   
    return l_prefix_dim_tbl;
    
  exception
    when others then      
 
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 0) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Ошибки функции', logmsg, pkg_cc_log.C_OP_LOG_ERROR_MSG, sqlerrm);
end if;
 
      raise;    
  end fnc_create_dim;
  
  -- основной метод формирования куба
  function fnc_create_cube (
    p_prc_id in l001_cc_op_log.prc_id%type,
    p_pm_name in varchar2,
    p_pm_desc in varchar2,
    p_excel_xml_doc in clob,
    p_user in varchar2
  ) return Clob
  is
     logmsg  varchar2(255) := 'Формирования куба';
     logsender varchar2(255) := 'pkg_cc_structure.fnc_create_cube';
      
     l_fact_tbl_name varchar2(30);
     l_fact_tbl_desc varchar2(4000);
     l_fact_tbl_cols varchar2(32767);
     l_fact_tbl_comm varchar2(32767);
    
     l_dim_tbl_name varchar2(30);
    
     l_seq_name varchar2(30);
    
     l_prefix_pm varchar2(30);
     l_prefix_fact_tbl varchar2(30);
     l_prefix_dim_tbl varchar2(30);
     l_prefix_attr varchar2(30);
     l_prefix_obj varchar2(30);
     
     l_attr_comm varchar2(4000);
     
     l_return_res Clob;
      
     l_is_New_Model varchar2(10);
     
     table_el dbms_xmldom.DOMElement;
      tcols_el dbms_xmldom.DOMElement;
       col_el dbms_xmldom.DOMElement;
       
     Ftable_nd dbms_xmldom.DOMNode;
       Ftcols_nd dbms_xmldom.DOMNode;
        col_nd dbms_xmldom.DOMNode;
        
     grp_number number;
     col_number number;
     
     tmp_char varchar2(30);
     
  begin
    -- сброс последовательности операций
    prc_reset_seq (p_prc_id, pkg_cc_const.C_SEQ_LOG_OP_ID);
    
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 1) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Старт функции', logmsg, pkg_cc_log.C_OP_LOG_INIT_MSG, null);    
end if;
 
-- входные параметры 
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 3) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'IN p_prc_id=' ||  p_prc_id, null,  PKG_CC_LOG.C_OP_LOG_PARAM, null);
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'IN p_pm_name=' || p_pm_name, null, PKG_CC_LOG.C_OP_LOG_PARAM, null);
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'IN p_pm_desc=' || p_pm_desc, null, PKG_CC_LOG.C_OP_LOG_PARAM, null);
end if;
 
    -- очистка кэша
    prc_clear_cash(p_prc_id);
    
    -- парсим строку в глобальные массивы
    prc_set_settings(p_prc_id, p_excel_xml_doc);

    begin 
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 3) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '  g_GetPrntXmlPosByPrcIx(p_prc_id).count=' || g_GetPrntXmlPosByPrcIx(p_prc_id).count, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
        -- смотрим родительский ли справочник
      for prnt_ix in 1 .. g_GetPrntXmlPosByPrcIx(p_prc_id).count
        loop
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 3) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '  g_GetPrntXmlPosByPrcIx(p_prc_id)(prnt_ix)=' || g_GetPrntXmlPosByPrcIx(p_prc_id)(prnt_ix), null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 3) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '  g_GetChldXmlPosByPrcColIx(p_prc_id)(g_GetPrntXmlPosByPrcIx(p_prc_id)(prnt_ix)).count=' || g_GetChldXmlPosByPrcColIx(p_prc_id)(g_GetPrntXmlPosByPrcIx(p_prc_id)(prnt_ix)).count, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
          for chld_ix in 1 .. g_GetChldXmlPosByPrcColIx(p_prc_id)(g_GetPrntXmlPosByPrcIx(p_prc_id)(prnt_ix)).count
            loop
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 3) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '    chld_ix=' || chld_ix, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 3) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '    g_GetChldXmlPosByPrcColIx(p_prc_id)(g_GetPrntXmlPosByPrcIx(p_prc_id)(prnt_ix))(chld_ix)=' || g_GetChldXmlPosByPrcColIx(p_prc_id)(g_GetPrntXmlPosByPrcIx(p_prc_id)(prnt_ix))(chld_ix), null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
            end loop;
        end loop;
    exception
			when NO_DATA_FOUND then
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 2) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '  Нет родительских справочников в строке', null , PKG_CC_LOG.C_OP_LOG_INFO_MSG, null);
end if;
    end;  

    l_prefix_pm := fnc_get_prefix_by_name(p_prc_id, pkg_cc_const.C_PFX_PM_TYPE, p_pm_name, null, null, null);
    
    if l_prefix_pm is null then
      l_prefix_pm := fnc_get_next_prefix(p_prc_id, pkg_cc_const.C_PFX_PM_TYPE, null, null);
      l_is_New_Model := 'true';
    else
		  l_is_New_Model := 'false';
    end if;
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 3) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'l_prefix_pm=' || l_prefix_pm, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
    
    -- чистим модель
    prc_drop_cube(p_prc_id, l_prefix_pm);
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 2) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Объекты модели удалены', null , PKG_CC_LOG.C_OP_LOG_INFO_MSG, null);
end if;

     -- создаём модель
    prc_create_model(p_prc_id, l_prefix_pm || pkg_cc_const.C_PART_NAME_DELIMETER || p_pm_name, p_pm_desc, p_user);
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 2) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Модель создана', null , PKG_CC_LOG.C_OP_LOG_INFO_MSG, null);
end if; 
  
    -- последовательности для структуры модели
    l_seq_name := pkg_cc_const.C_SEQ_PREFIX_NAME_PART || pkg_cc_const.C_PART_NAME_DELIMETER || l_prefix_pm || pkg_cc_const.C_PART_NAME_DELIMETER || pkg_cc_const.C_PFX_FACT_TBL_TYPE; 
    prc_create_sequence(p_prc_id, upper(l_seq_name));
    
    l_seq_name := pkg_cc_const.C_SEQ_PREFIX_NAME_PART || pkg_cc_const.C_PART_NAME_DELIMETER || l_prefix_pm || pkg_cc_const.C_PART_NAME_DELIMETER || pkg_cc_const.C_PFX_DIM_TBL_TYPE; 
    prc_create_sequence(p_prc_id, upper(l_seq_name));
    
    l_seq_name := pkg_cc_const.C_SEQ_PREFIX_NAME_PART || pkg_cc_const.C_PART_NAME_DELIMETER || l_prefix_pm || pkg_cc_const.C_PART_NAME_DELIMETER || pkg_cc_const.C_PFX_OBJ_TYPE; 
    prc_create_sequence(p_prc_id, upper(l_seq_name));
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 2) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Последовательности для структуры модели созданы', null , PKG_CC_LOG.C_OP_LOG_INFO_MSG, null);
end if;

-- XML
    -- открываем новый документ xml
    doc := dbms_xmldom.createDocument('http://www.w3.org/2001/XMLSchema', null, null);
    dbms_xmldom.setVersion(doc, '1.0');
    doc_node := dbms_xmldom.makeNode(doc);
    
    -- вкладываем куб в документ
    cube_el := dbms_xmldom.createElement(doc, pkg_cc_const.C_CUBE_TAG);
    cube_nd := dbms_xmldom.makeNode(cube_el);
    cube_nd := dbms_xmldom.appendChild(doc_node, cube_nd);
    
    dbms_xmldom.setAttribute(cube_el, pkg_cc_const.C_CUBE_ATTR1, p_pm_name);
    dbms_xmldom.setAttribute(cube_el, pkg_cc_const.C_CUBE_ATTR2, p_pm_desc);
    dbms_xmldom.setAttribute(cube_el, pkg_cc_const.C_CUBE_ATTR3, l_is_New_Model);
    
    -- вкладываем таблицы в куб
    tbls_el := dbms_xmldom.createElement(doc, pkg_cc_const.C_TABLES_TAG);
    tbls_nd := dbms_xmldom.makeNode(tbls_el);
    tbls_nd := dbms_xmldom.appendChild(cube_nd, tbls_nd);
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 2) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Начато формирование XML-документа', null , PKG_CC_LOG.C_OP_LOG_INFO_MSG, null);
end if;
    
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 2) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Старт разворачивания модели для таблиц фактов', null , PKG_CC_LOG.C_OP_LOG_INFO_MSG, null);
end if;

-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 3) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '  g_GetGrpByPrcIx(p_prc_id).count=' || g_GetGrpByPrcIx(p_prc_id).count, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;

    for grp_ix in 1 .. g_GetGrpByPrcIx(p_prc_id).count
      loop
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 3) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'grp_ix=' || grp_ix, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;

        grp_number := g_GetGrpByPrcIx(p_prc_id)(grp_ix);
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 3) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'grp_number=' || grp_number, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
 
      -- получаем префикс для таблицы
      l_prefix_fact_tbl := fnc_get_next_prefix(p_prc_id, pkg_cc_const.C_PFX_FACT_TBL_TYPE, l_prefix_pm, null);    
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 3) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '  l_prefix_fact_tbl=' || l_prefix_fact_tbl, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
   
      l_fact_tbl_name := upper(l_prefix_pm || pkg_cc_const.C_PART_NAME_DELIMETER || l_prefix_fact_tbl);
      l_fact_tbl_desc := pkg_cc_const.C_FACT_TBL_DESC_PRT || grp_number;
      
      -- последовательности для структуры факта
      l_seq_name := pkg_cc_const.C_SEQ_PREFIX_NAME_PART || pkg_cc_const.C_PART_NAME_DELIMETER || l_prefix_pm || pkg_cc_const.C_PART_NAME_DELIMETER || l_prefix_fact_tbl || pkg_cc_const.C_PART_NAME_DELIMETER || pkg_cc_const.C_PFX_FACT_ATTR_TYPE; 
      prc_create_sequence(p_prc_id, upper(l_seq_name));
      
      l_seq_name := pkg_cc_const.C_SEQ_PREFIX_NAME_PART || pkg_cc_const.C_PART_NAME_DELIMETER || l_prefix_pm || pkg_cc_const.C_PART_NAME_DELIMETER || l_prefix_fact_tbl || pkg_cc_const.C_PART_NAME_DELIMETER || pkg_cc_const.C_PFX_FACT_MEAS_TYPE; 
      prc_create_sequence(p_prc_id, upper(l_seq_name));
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 2) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '  Последовательности для структуры таблицы фактов '|| l_fact_tbl_name ||' созданы', null , PKG_CC_LOG.C_OP_LOG_INFO_MSG, null);   
end if;
      
  -- XML: факты      
      -- вкладываем конкретную FACT таблицу в таблицы
      table_el := dbms_xmldom.createElement(doc, pkg_cc_const.C_TABLE_TAG);
      Ftable_nd := dbms_xmldom.makeNode(table_el);
      Ftable_nd := dbms_xmldom.appendChild(tbls_nd, Ftable_nd);
      
      dbms_xmldom.setAttribute(table_el, pkg_cc_const.C_TABLE_ATTR1, l_fact_tbl_name);
      dbms_xmldom.setAttribute(table_el, pkg_cc_const.C_TABLE_ATTR2, l_fact_tbl_desc);
      
      begin
		    for dummy_ix in 1 .. g_GetNotDummyFactByPrcIx(p_prc_id).count
					loop
						if (g_GetNotDummyFactByPrcIx(p_prc_id)(dummy_ix) = grp_number) then
							-- пишем в XML что это fact
							dbms_xmldom.setAttribute(table_el, pkg_cc_const.C_TABLE_ATTR3, pkg_cc_const.C_TABLE_TYPE_VAL1);
              exit;
              
            else
							if (dummy_ix = g_GetNotDummyFactByPrcIx(p_prc_id).count) then
								-- если не нашли то пишем в XML что это dummy fact
								dbms_xmldom.setAttribute(table_el, pkg_cc_const.C_TABLE_ATTR3, pkg_cc_const.C_TABLE_TYPE_VAL3);
              end if;
              
            end if;
          end loop;
      exception
         when NO_DATA_FOUND then
					 -- пишем в XML что это dummy fact
           dbms_xmldom.setAttribute(table_el, pkg_cc_const.C_TABLE_ATTR3, pkg_cc_const.C_TABLE_TYPE_VAL3);
      end;
      
      dbms_xmldom.setAttribute(table_el, pkg_cc_const.C_TABLE_ATTR4, grp_number);
  
      -- вкладываем колонки в таблицу
      tcols_el := dbms_xmldom.createElement(doc, pkg_cc_const.C_TABLE_COLS_TAG);
      Ftcols_nd := dbms_xmldom.makeNode(tcols_el);
      Ftcols_nd := dbms_xmldom.appendChild(Ftable_nd, Ftcols_nd);
      
  -- + обязательное поле
      -- вкладываем конкретную колонку в колонки таблицы
      col_el := dbms_xmldom.createElement(doc, pkg_cc_const.C_COL_TAG);
      col_nd := dbms_xmldom.makeNode(col_el);
      col_nd := dbms_xmldom.appendChild(Ftcols_nd, col_nd);
      
      dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR1, pkg_cc_const.C_FACT_ID_NAME);
      dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR2, pkg_cc_const.C_FACT_ID_TYPE);
      dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR3, pkg_cc_const.C_FACT_ID_DESC);
      dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR4, 'true');
      dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR5, 'false');
      dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR6, '');
      dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR7, '');
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 2) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '  Начато формирование XML для таблицы фактов '|| l_fact_tbl_name, null , PKG_CC_LOG.C_OP_LOG_INFO_MSG, null);   
end if;

-- log
if (pkg_cc_const.C_lOG_LEVEL >= 2) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '  Старт разворачивания модели для таблицы фактов ' || l_fact_tbl_name, null , PKG_CC_LOG.C_OP_LOG_INFO_MSG, null);
end if;

-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 3) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '  g_GetColGrpPosByPrcGrpIx(p_prc_id)(grp_number).count=' || g_GetColGrpPosByPrcGrpIx(p_prc_id)(grp_number).count, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
     
      for col_ix in 1 .. g_GetColGrpPosByPrcGrpIx(p_prc_id)(grp_number).count
        loop
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 3) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '     col_ix=' || col_ix, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;

          col_number := g_GetColGrpPosByPrcGrpIx(p_prc_id)(grp_number)(col_ix);

-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 3) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '     col_number=' || col_number, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;

          -- переопределяем название и тип для измерений
          if (g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(col_number)(pkg_cc_const.C_ATTR_CTYPE_POS) = pkg_cc_const.C_PFX_DIM_ATTR_TYPE) then
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 2) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '     Ветка переопределения для измерения', null, PKG_CC_LOG.C_OP_LOG_INFO_MSG, null);
end if;
              
              -- смотрим есть ли уже такой справочник
              l_prefix_dim_tbl := fnc_get_prefix_by_name(
                                    p_prc_id, 
                                    pkg_cc_const.C_PFX_DIM_TBL_TYPE, 
                                    p_pm_name, 
                                    g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(col_number)(pkg_cc_const.C_ATTR_NAME_POS), 
                                    null,
                                    null
                                  );
              
              -- если справочник ещё не создавался, то создаём иначе сразу переопределяем элемент факта на ссылку на справочник
              if (l_prefix_dim_tbl is null) then
                -- создаем справочник и получаем его префикс
                l_prefix_dim_tbl := fnc_create_dim(
                                      p_prc_id, 
                                      p_pm_name, 
                                      g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(col_number)(pkg_cc_const.C_ATTR_NAME_POS), 
                                      g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(col_number)(pkg_cc_const.C_ATTR_TYPE_POS), 
                                      g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(col_number)(pkg_cc_const.C_ATTR_DESC_POS), 
                                      grp_number, 
                                      g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(col_number)(pkg_cc_const.C_ATTR_GRPPOS_POS), 
                                      g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(col_number)(pkg_cc_const.C_ATTR_XMLPOS_POS)
                                    );
              else
								-- пропускаем объект
                tmp_char := fnc_get_next_prefix(p_prc_id, pkg_cc_const.C_PFX_OBJ_TYPE, l_prefix_pm, null);
              
              end if;
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 3) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '     l_prefix_dim_tbl=' || l_prefix_dim_tbl, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
              -- ставим ссылку в таблице фактов только для родительских справочников
              if (g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(col_number)(pkg_cc_const.C_ATTR_PRNTGRPPOS_POS) = '0') then
           
                l_dim_tbl_name := l_prefix_pm || pkg_cc_const.C_PART_NAME_DELIMETER 
                  || l_prefix_dim_tbl || pkg_cc_const.C_PART_NAME_DELIMETER 
                  || g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(col_number)(pkg_cc_const.C_ATTR_NAME_POS);
                
                -- переопределяем имя и тип ссылки   
                -- строка колонок для факта (ветка измерений)
                l_fact_tbl_cols := l_fact_tbl_cols 
                  || l_prefix_dim_tbl || pkg_cc_const.C_PART_NAME_DELIMETER || pkg_cc_const.C_REF_ID_PART_NAME
                  || ' ' 
                  || pkg_cc_const.C_REF_ID_TYPE 
                  || ', ';
                
                -- строка комментариев для факта (ветка измерений)
                -- комментарий = имя(переопределенное):комментарий  
                l_fact_tbl_comm := l_fact_tbl_comm 
                  || pkg_cc_const.C_COMM_SEPARATOR 
                  || l_prefix_dim_tbl || pkg_cc_const.C_PART_NAME_DELIMETER || pkg_cc_const.C_REF_ID_PART_NAME
                  || pkg_cc_const.C_COMM_PRT_SEPARATOR  
                  || pkg_cc_const.C_REF_ID_PART_DESC || l_prefix_dim_tbl; 
                
  -- XML для факта (ветка измерений)
                -- + поле в факт
                -- вкладываем конкретную колонку в колонки таблицы
                col_el := dbms_xmldom.createElement(doc, pkg_cc_const.C_COL_TAG);
                col_nd := dbms_xmldom.makeNode(col_el);
                col_nd := dbms_xmldom.appendChild(Ftcols_nd, col_nd);
                
                dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR1, l_prefix_dim_tbl || pkg_cc_const.C_PART_NAME_DELIMETER || pkg_cc_const.C_REF_ID_PART_NAME);
                dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR2, pkg_cc_const.C_REF_ID_TYPE);
                dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR3, pkg_cc_const.C_REF_ID_PART_DESC || l_prefix_dim_tbl);
                dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR4, 'false');
                dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR5, 'true');
                dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR6, '');
                dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR7, l_dim_tbl_name);
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 2) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '       Формирование ссылки на справочник ' || l_prefix_dim_tbl || ' в XML для таблицы фактов '|| l_prefix_fact_tbl ||' завершено', null , PKG_CC_LOG.C_OP_LOG_INFO_MSG, null);   
end if;
              else
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 2) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '       Формирование ссылки на справочник ' || l_prefix_dim_tbl || ' в XML для таблицы фактов '|| l_prefix_fact_tbl ||' пропущено', null , PKG_CC_LOG.C_OP_LOG_INFO_MSG, null);   
end if;
              end if;
              
          -- если атбирут или показатель факта (только в текущей группе таблиц)
          elsif (g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(col_number)(pkg_cc_const.C_ATTR_CTYPE_POS) = pkg_cc_const.C_PFX_FACT_ATTR_TYPE or g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(col_number)(pkg_cc_const.C_ATTR_CTYPE_POS) = pkg_cc_const.C_PFX_FACT_MEAS_TYPE) then
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 2) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '     Ветка элементов факта', null, PKG_CC_LOG.C_OP_LOG_INFO_MSG, null);
end if;

            l_prefix_obj := fnc_get_next_prefix(p_prc_id, pkg_cc_const.C_PFX_OBJ_TYPE, l_prefix_pm, null);

            l_prefix_attr := fnc_get_next_prefix(p_prc_id, g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(col_number)(pkg_cc_const.C_ATTR_CTYPE_POS), l_prefix_pm, l_prefix_fact_tbl);
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 3) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '       l_prefix_obj + ' || pkg_cc_const.C_PART_NAME_DELIMETER || ' + l_prefix_attr=' || l_prefix_obj || pkg_cc_const.C_PART_NAME_DELIMETER || l_prefix_attr, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
          
            l_fact_tbl_cols := l_fact_tbl_cols 
              || l_prefix_obj || pkg_cc_const.C_PART_NAME_DELIMETER 
              || l_prefix_attr || pkg_cc_const.C_PART_NAME_DELIMETER 
              || g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(col_number)(pkg_cc_const.C_ATTR_NAME_POS) 
              || ' ' 
              || g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(col_number)(pkg_cc_const.C_ATTR_TYPE_POS) 
              || ', '; 
          
            l_fact_tbl_comm := 
              l_fact_tbl_comm 
              || pkg_cc_const.C_COMM_SEPARATOR 
              || l_prefix_obj || pkg_cc_const.C_PART_NAME_DELIMETER || l_prefix_attr || pkg_cc_const.C_PART_NAME_DELIMETER
              || g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(col_number)(pkg_cc_const.C_ATTR_NAME_POS) 
              || pkg_cc_const.C_COMM_PRT_SEPARATOR 
              || g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(col_number)(pkg_cc_const.C_ATTR_DESC_POS);

   -- XML для факта (ветка фактов)     
            -- поле в факт
            -- вкладываем конкретную колонку в колонки таблицы
            col_el := dbms_xmldom.createElement(doc, pkg_cc_const.C_COL_TAG);
            col_nd := dbms_xmldom.makeNode(col_el);
            col_nd := dbms_xmldom.appendChild(Ftcols_nd, col_nd);  -- ТУТ УКАЗАНО ЧТО ВСТАВЛЯЕМ В ФАКТ
                
            dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR1, l_prefix_obj || pkg_cc_const.C_PART_NAME_DELIMETER || l_prefix_attr || pkg_cc_const.C_PART_NAME_DELIMETER || g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(col_number)(pkg_cc_const.C_ATTR_NAME_POS));
            dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR2, g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(col_number)(pkg_cc_const.C_ATTR_TYPE_POS));
            
            l_attr_comm := g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(col_number)(pkg_cc_const.C_ATTR_DESC_POS);
            
            l_attr_comm := replace(l_attr_comm, pkg_cc_const.C_COMM_SEPARATOR_REPLACE, pkg_cc_const.C_COMM_SEPARATOR);
            l_attr_comm := replace(l_attr_comm, pkg_cc_const.C_COMM_PRT_SEPARATOR_REPLACE, pkg_cc_const.C_COMM_PRT_SEPARATOR);
            l_attr_comm := replace(l_attr_comm, pkg_cc_const.C_QUOT_REPLACE, ''''); -- заменяем на экранированную '
                
            dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR3, l_attr_comm);
            dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR4, 'false');
            dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR5, 'false');
            dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR6, g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(col_number)(pkg_cc_const.C_ATTR_AGGRTYPE_POS));
            dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR7, '');
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 2) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '       Завершено формирование элементов XML для таблицы фактов '|| l_prefix_fact_tbl, null , PKG_CC_LOG.C_OP_LOG_INFO_MSG, null);   
end if;

          end if; -- закрылся if веток измерение/факт

        end loop;
        
-- + обязательные поля
      l_fact_tbl_cols := 
        pkg_cc_const.C_FACT_ID_NAME
        || ' ' 
        || pkg_cc_const.C_FACT_ID_TYPE 
        || ', '
        || l_fact_tbl_cols 
        || pkg_cc_const.C_CREATED_DATE_NAME
        || ' '
        || pkg_cc_const.C_CREATED_DATE_TYPE;
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 3) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '  l_fact_tbl_cols=' || l_fact_tbl_cols, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;

      -- + обязательные поля
      l_fact_tbl_comm := 
        pkg_cc_const.C_FACT_ID_NAME
        || pkg_cc_const.C_COMM_PRT_SEPARATOR 
        || pkg_cc_const.C_FACT_ID_DESC
        || l_fact_tbl_comm 
        || pkg_cc_const.C_COMM_SEPARATOR 
        || pkg_cc_const.C_CREATED_DATE_NAME
        || pkg_cc_const.C_COMM_PRT_SEPARATOR 
        || pkg_cc_const.C_CREATED_DATE_DESC;
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 3) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '  l_fact_tbl_comm=' || l_fact_tbl_comm, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
end if;
 
      prc_create_table(p_prc_id, l_fact_tbl_name, l_fact_tbl_desc, l_fact_tbl_cols, l_fact_tbl_comm);
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 2) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '  Таблица фактов ' || l_fact_tbl_name || ' создана', null, PKG_CC_LOG.C_OP_LOG_INFO_MSG, null);
end if;

      l_seq_name := upper(pkg_cc_const.C_SEQ_NAME_PART || pkg_cc_const.C_PART_NAME_DELIMETER || l_prefix_pm || pkg_cc_const.C_PART_NAME_DELIMETER || l_prefix_fact_tbl || pkg_cc_const.C_PART_NAME_DELIMETER || pkg_cc_const.C_FACT_ID_NAME);
      prc_create_sequence(p_prc_id, upper(l_seq_name));
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 2) then   
  pkg_cc_log.op_log(p_prc_id, null, logsender, '  Последовательность ' || l_seq_name || ' для таблицы фактов создана', null, PKG_CC_LOG.C_OP_LOG_INFO_MSG, null);
end if;

 -- XML для факта 
       -- + обязательное поле
      -- вкладываем конкретную колонку в колонки таблицы
      col_el := dbms_xmldom.createElement(doc, pkg_cc_const.C_COL_TAG);
      col_nd := dbms_xmldom.makeNode(col_el);
      col_nd := dbms_xmldom.appendChild(Ftcols_nd, col_nd);
      
      dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR1, pkg_cc_const.C_CREATED_DATE_NAME);
      dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR2, pkg_cc_const.C_CREATED_DATE_TYPE);
      dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR3, pkg_cc_const.C_CREATED_DATE_DESC);
      dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR4, 'false');
      dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR5, 'false');
      dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR6, '');
      dbms_xmldom.setAttribute(col_el, pkg_cc_const.C_COL_ATTR7, '');
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 2) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '  Завершено формирование XML для таблицы фактов ' || l_prefix_fact_tbl, null , PKG_CC_LOG.C_OP_LOG_INFO_MSG, null);   
end if;
 
        -- сбрасываем переменные
      	l_fact_tbl_name := null;
        l_fact_tbl_cols := null;
        l_fact_tbl_comm := null;
        
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 2) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '  Завершение разворачивания модели для таблицы фактов ' || l_fact_tbl_name, null , PKG_CC_LOG.C_OP_LOG_INFO_MSG, null);
end if;
    end loop;
    
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 2) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Завершение разворачивания модели для таблиц фактов', null , PKG_CC_LOG.C_OP_LOG_INFO_MSG, null);
end if;
    
    -- кэширование
    prc_set_cash(p_prc_id, p_pm_name);
 
    -- закрываем xml
    dbms_lob.createtemporary(l_return_res, true, dbms_lob.session);
    dbms_xmldom.writeToClob(doc, l_return_res);
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 2) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Завершено формирование XML-документа', null , PKG_CC_LOG.C_OP_LOG_INFO_MSG, null);   
end if;

    -- добавляем XML-документ в модель
    prc_add_info_in_model(p_prc_id, l_return_res, p_excel_xml_doc);
     
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 2) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'XML-документ добавлен в модель ' || p_pm_name, null , PKG_CC_LOG.C_OP_LOG_INFO_MSG, null);   
end if;
  
    -- log
if (pkg_cc_const.C_lOG_LEVEL >= 1) then 
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Завершение функции', logmsg, pkg_cc_log.C_OP_LOG_SUCCESS_MSG, null);
end if;
 
    return l_return_res;

  exception
    when others then
      
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 0) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Ошибки функции', logmsg, pkg_cc_log.C_OP_LOG_ERROR_MSG, sqlerrm);
end if;
 
      return null;

  end fnc_create_cube;
   
  -- поиск либо заполнение справочников
  function fnc_load_dim ( 
    p_prc_id in l001_cc_op_log.prc_id%type,
    p_tbl_name in varchar2,
    p_col_name in varchar2,
    p_val in varchar2,
    p_grp in number,
    p_pos_in_grp in number,
    p_pos_in_xml in number,
    p_vals_node in xmldom.DOMNodeList
  ) return number
  is
     logmsg  varchar2(255) := 'Поиск либо заполнение справочников';
     logsender varchar2(255) := 'pkg_cc_structure.fnc_load_dim';
     
     l_pfx varchar2(10);
     l_dim_id number;
     l_stmt varchar2(32767);
     
     cnt_val number;
     
        
     val_node xmldom.DOMNode;
     val_el xmldom.DOMElement;
       
     val_text_node xmldom.DOMNode;
     val_text varchar2(32767);
     
     col_xmlpos number;
     col_grppos number;
     
     l_inner_tbl_name  varchar2(30);
     l_inner_col_name varchar2(30);
     
     l_inner_dim_id varchar2(32767);     

     l_inner_vals_str varchar2(32767);
     
     l_flag_is_parent boolean := FALSE;
     l_flag_is_child boolean;
     l_flag_not_in_grp boolean;

  begin
 
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Старт функции', logmsg, pkg_cc_log.C_OP_LOG_INIT_MSG, null);    
end if;
 
-- входные параметры
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   p_tbl_name=' || p_tbl_name, null, PKG_CC_LOG.C_OP_LOG_VAR, null);   
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   p_col_name=' || p_col_name, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   p_val=' || p_val, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   p_grp=' || p_grp, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   p_pos_in_grp=' || p_pos_in_grp, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   p_pos_in_xml=' || p_pos_in_xml, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;  
    begin
			 
      l_stmt := 'select dim_id from '|| p_tbl_name || ' where ' || p_col_name || ' = ' || p_val;
      
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   l_stmt=' || l_stmt, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if; 
      
      execute immediate
       l_stmt into l_dim_id;
 
    exception
			when NO_DATA_FOUND then
				l_dim_id := 0;
			when others 
				then 
					raise;
    end;
    
    if (p_val = 'null') then
      l_dim_id := pkg_cc_const.C_DIM_DUMMY_DIM_ID; 
    end if;

-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   l_dim_id=' || l_dim_id, null,  PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
 
    if (l_dim_id = 0) then
      l_pfx := substr(p_tbl_name, 1, 10);
      
      execute immediate
        'select ' || pkg_cc_const.C_SEQ_NAME_PART || pkg_cc_const.C_PART_NAME_DELIMETER || l_pfx || pkg_cc_const.C_PART_NAME_DELIMETER || pkg_cc_const.C_DIM_ID_NAME || '.nextval from dual' into l_dim_id;
    
    begin
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '  g_GetPrntXmlPosByPrcIx(p_prc_id).count=' || g_GetPrntXmlPosByPrcIx(p_prc_id).count, null , PKG_CC_LOG.C_OP_LOG_INFO_MSG, null);
end if;
       -- смотрим родительский ли справочник
      for prnt_ix in 1 .. g_GetPrntXmlPosByPrcIx(p_prc_id).count
        loop
          if (g_GetPrntXmlPosByPrcIx(p_prc_id)(prnt_ix) = g_GetVirtColPosByPrcCol(p_prc_id)(p_pos_in_xml)) then
            l_flag_is_parent := TRUE;
          end if;
        end loop;
    exception
			when NO_DATA_FOUND then
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 5) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '  Нет родительских справочников в структуре', null , PKG_CC_LOG.C_OP_LOG_INFO_MSG, null);
end if;
    end; 
           
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   l_flag_is_parent=' || fnc_boolToChar(l_flag_is_parent), null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
      
      if (l_flag_is_parent) then
				
        cnt_val := xmldom.getLength(p_vals_node);
        
        for val_ix in 0 .. cnt_val - 1
          loop
            val_node := xmldom.item(p_vals_node, val_ix);
            val_el := xmldom.makeElement(val_node);
            val_text_node := xmldom.getFirstChild(val_node);
            val_text := nvl(xmldom.getNodeValue (val_text_node), 'null');
                      
            col_xmlpos := xmldom.getAttribute(val_el, 'absPos');  
            col_grppos := g_GetColGrpPosByPrcCol(p_prc_id)(col_xmlpos);
            
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '        col_xmlpos=' || col_xmlpos, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '            val=' || val_text, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;

            l_flag_is_child := FALSE;
            l_flag_not_in_grp := FALSE;
            
            begin

-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then 
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   cnt_chld=' || g_GetChldGrpPosByPrcGrpColIx(p_prc_id)(p_grp)(p_pos_in_grp).count, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
               -- смотрим подчиненный ли справочник
              for chld_ix in 1 .. g_GetChldGrpPosByPrcGrpColIx(p_prc_id)(p_grp)(p_pos_in_grp).count
                loop
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   g_GetChldGrpPosByPrcGrpColIx(p_prc_id)(p_grp)(p_pos_in_grp)(chld_ix)=' || g_GetChldGrpPosByPrcGrpColIx(p_prc_id)(p_grp)(p_pos_in_grp)(chld_ix), null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
                  if (g_GetChldGrpPosByPrcGrpColIx(p_prc_id)(p_grp)(p_pos_in_grp)(chld_ix) = col_grppos) then
                    l_flag_is_child := TRUE;
                    exit;
                  end if;
                end loop;
                
            exception
							when NO_DATA_FOUND then
								l_flag_not_in_grp := true;
								l_flag_is_child := FALSE;          
            end;
            
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   l_flag_is_child=' || fnc_boolToChar(l_flag_is_child), null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   l_flag_not_in_grp=' || fnc_boolToChar(l_flag_not_in_grp), null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
            
            -- если подчиненный
            if (l_flag_is_child) then   
-- РАБОТА С НАСТРОКАМИ
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 5) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '        Старт работы с НАСТРОЙКАМИ', null , PKG_CC_LOG.C_OP_LOG_INFO_MSG, null);
end if;
                    
                    l_inner_tbl_name := fnc_get_cash(p_prc_id, pkg_cc_const.C_CASH_TBL_TYPE, col_xmlpos);

                    l_inner_col_name := fnc_get_cash(p_prc_id, pkg_cc_const.C_CASH_COL_TYPE, col_xmlpos);
           
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 5) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '        Завершение работы с НАСТРОЙКАМИ', null , PKG_CC_LOG.C_OP_LOG_INFO_MSG, null);
end if;
								
              l_inner_dim_id := l_inner_dim_id || fnc_load_dim(p_prc_id, l_inner_tbl_name, l_inner_col_name, nvl(val_text, 'null'), p_grp, col_grppos, col_xmlpos, p_vals_node) || ', ';
            
            -- если не в группе и последнее значение
            elsif (l_flag_not_in_grp and val_ix = cnt_val - 1) then
            
              for chld_ix in 1 .. g_GetChldXmlPosByPrcColIx(p_prc_id)(g_GetVirtColPosByPrcCol(p_prc_id)(p_pos_in_xml)).count
								loop
                  l_inner_dim_id := l_inner_dim_id || pkg_cc_const.C_DIM_DUMMY_REF_ID || ', ';
                end loop;
                
            end if;
            
          end loop;
          
          l_inner_vals_str := l_inner_vals_str || l_inner_dim_id;
          
       end if;
       
       
       l_stmt := 'insert into ' || p_tbl_name || ' values(' || l_dim_id || ', ' || l_inner_vals_str || p_val || ', ' || pkg_cc_const.C_CREATED_DATE_VAL || ')';    
 -- log
if (pkg_cc_const.C_lOG_LEVEL >= 6) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   l_stmt=' || l_stmt, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
      
        execute immediate
       l_stmt;
       
     end if;
 
 -- log
if (pkg_cc_const.C_lOG_LEVEL >= 4) then 
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Завершение процедуры', logmsg, pkg_cc_log.C_OP_LOG_SUCCESS_MSG, null);
end if;
 
    return l_dim_id;
    
  exception

    when others then
 -- log
if (pkg_cc_const.C_lOG_LEVEL >= 0) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Ошибки функции', logmsg, pkg_cc_log.C_OP_LOG_ERROR_MSG, sqlerrm);
end if;
 
			raise;
  end fnc_load_dim;
  
  -- основной метод загрузки куба
  function fnc_load_cube (
    p_prc_id in l001_cc_op_log.prc_id%type,
    p_pm_name varchar2,
    p_values clob,
    p_is_first_load number
  ) return number
  is
     logmsg  varchar2(255) := 'Загрузки куба';
     logsender varchar2(255) := 'pkg_cc_structure.fnc_load_cube';
    
     doc xmldom.DOMDocument;
     parser xmlparser.parser := xmlparser.newparser;
     
     groups_nodes xmldom.DOMNodeList;
     cnt_groups number;
       
     groups_node xmldom.DOMNode;
     groups_el xmldom.DOMNodeList;
       
     cnt_group number;
     group_node xmldom.DOMNode;
     group_el  xmldom.DOMElement;
       
     rows_node xmldom.DOMNodeList;
     cnt_row number;
       
     row_node xmldom.DOMNode;
     row_el xmldom.DOMElement;
       
     vals_node xmldom.DOMNodeList;
     cnt_val number;
       
     val_node xmldom.DOMNode;
     val_el xmldom.DOMElement;
       
     val_text_node xmldom.DOMNode;
     val_text varchar2(32767);
     
     pfx_pm varchar2(30);
     l_tbl_name  varchar2(30);
     l_col_name varchar2(30);
     l_ins_str varchar2(32767);
     
     l_dim_id number;
     l_ins_name varchar2(30);
     l_stmt varchar2(32767);
     
     l_flag_not_empty_dim boolean;
     
     grp_number number;
     row_number number;
     
     col_xmlpos number;
     col_grppos number;
     
     l_tbl_type varchar2(32767); 
     
  begin
		
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 1) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Старт функции', logmsg, pkg_cc_log.C_OP_LOG_INIT_MSG, null);    
end if;
      
-- входные параметры 
-- log 
if (pkg_cc_const.C_lOG_LEVEL >= 3) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, ' p_prc_id=' || p_prc_id, null, PKG_CC_LOG.C_OP_LOG_PARAM, null); 
  pkg_cc_log.op_log(p_prc_id, null, logsender, ' p_pm_name=' ||  p_pm_name, null, PKG_CC_LOG.C_OP_LOG_PARAM, null);
  pkg_cc_log.op_log(p_prc_id, null, logsender, ' p_is_first_load=' ||  p_is_first_load, null, PKG_CC_LOG.C_OP_LOG_PARAM, null);
end if;
 
    if (p_is_first_load = 0) then
			prc_set_settings(p_prc_id, fnc_get_model_xml(p_prc_id, p_pm_name));
      prc_set_cash(p_prc_id, p_pm_name);
    end if;
    
    xmlparser.parseCLOB(parser, p_values);
    doc := xmlparser.getDocument(parser);
    xmlparser.freeParser(parser);
    
    groups_nodes := xmldom.getElementsByTagName(doc, 'Groups');
    cnt_groups := xmldom.getLength(groups_nodes);
      
    for grps_ix in 0 .. cnt_groups - 1
      loop
				
        groups_node := xmldom.item(groups_nodes, grps_ix);
        groups_el := xmldom.getChildNodes(groups_node);
        cnt_group := xmldom.getLength(groups_el);
          
        for gpr_ix in 0 .. cnt_group - 1 
          loop
            
            group_node := xmldom.item(groups_el, gpr_ix);
            group_el := xmldom.makeElement(group_node);
            
            grp_number := xmldom.getAttribute(group_el, 'name');
            
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 3) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'grp_number=' || grp_number, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;

            rows_node := xmldom.getChildNodes(group_node);
            cnt_row := xmldom.getLength(rows_node);
              
            for row_ix in 0 .. cnt_row - 1
              loop
								
                l_ins_name := null;
                l_ins_str := null;
                l_flag_not_empty_dim := FALSE;
        
                row_node := xmldom.item(rows_node, row_ix);
                row_el := xmldom.makeElement(row_node);
              
                row_number := xmldom.getAttribute(row_el, 'number');
                
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 3) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '    row_number=' || row_number, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if; 

                vals_node := xmldom.getChildNodes(row_node);
                cnt_val := xmldom.getLength(vals_node);
                   
                for val_ix in 0 .. cnt_val - 1
                  loop
										
                    val_node := xmldom.item(vals_node, val_ix);
                    val_el := xmldom.makeElement(val_node);
                    val_text_node := xmldom.getFirstChild(val_node);
                    val_text := nvl(xmldom.getNodeValue (val_text_node), 'null');
                    
                    col_xmlpos := xmldom.getAttribute(val_el, 'absPos');  
                    col_grppos := g_GetColGrpPosByPrcCol(p_prc_id)(col_xmlpos);
                    
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 3) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '        col_xmlpos=' || col_xmlpos, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 3) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '            val=' || val_text, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;

-- РАБОТА С НАСТРОКАМИ
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 2) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '        Старт работы с НАСТРОЙКАМИ', null , PKG_CC_LOG.C_OP_LOG_INFO_MSG, null);
end if;
           
                    pfx_pm := fnc_get_cash(p_prc_id, pkg_cc_const.C_CASH_PM_TYPE, null);
                    
                    l_tbl_name := fnc_get_cash(p_prc_id, pkg_cc_const.C_CASH_TBL_TYPE, col_xmlpos);

                    l_col_name := fnc_get_cash(p_prc_id, pkg_cc_const.C_CASH_COL_TYPE, col_xmlpos);

-- log
if (pkg_cc_const.C_lOG_LEVEL >= 2) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '        Завершение работы с НАСТРОЙКАМИ', null , PKG_CC_LOG.C_OP_LOG_INFO_MSG, null);
end if;
                 
                    l_tbl_type := substr(l_tbl_name, length(pkg_cc_const.C_PFX_PM_PART) + pkg_cc_const.C_PFX_PM_DGT_NUM + length(pkg_cc_const.C_PART_NAME_DELIMETER) + 1, length(pkg_cc_const.C_PFX_DIM_TBL_PART));
                    
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 3) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '        l_tbl_type=' || l_tbl_type, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;

                    if (l_tbl_type = pkg_cc_const.C_PFX_DIM_TBL_PART and g_GetAttrByPrcGrpColIx(p_prc_id)(grp_number)(col_grppos)(pkg_cc_const.C_ATTR_PRNTGRPPOS_POS) = '0') then
											

if (pkg_cc_const.C_lOG_LEVEL >= 2) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '        Ветка измерений', null , PKG_CC_LOG.C_OP_LOG_INFO_MSG, null);
end if;
	

                      l_dim_id := pkg_cc_structure.fnc_load_dim(
                                    p_prc_id, 
                                    l_tbl_name, 
                                    l_col_name, 
                                    nvl(val_text, 'null'), 
                                    grp_number, 
                                    col_grppos,
                                    col_xmlpos,
                                    vals_node
                                  );
                            
                      l_ins_str := l_ins_str || ', ' || l_dim_id;
                      
                      -- если хоть одна ссылка не -1 то ставим флаг доступности вставки факта
                      if (l_dim_id != pkg_cc_const.C_DIM_DUMMY_DIM_ID) then
                        l_flag_not_empty_dim := TRUE;
                      end if;

                    elsif (l_tbl_type = pkg_cc_const.C_PFX_FACT_TBL_PART) then
                    

if (pkg_cc_const.C_lOG_LEVEL >= 2) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '        Ветка показателей', null , PKG_CC_LOG.C_OP_LOG_INFO_MSG, null);
end if;
                
                      l_ins_name := l_tbl_name;
                      l_ins_str := l_ins_str || ', ' || nvl(val_text, 'null');
                      
                      -- если есть хоть один факт то ставим флаг доступности вставки факта
                      if (val_text is not null) then
                        l_flag_not_empty_dim := TRUE;
                      end if;
                      
                    end if;
                    
                  end loop; -- значения
                  
                if (l_ins_name is not null and l_flag_not_empty_dim) then 
									
                  l_ins_str := ltrim(l_ins_str, ', ');

                  l_stmt := 'insert into ' || l_ins_name || ' values (' || pkg_cc_const.C_SEQ_NAME_PART || pkg_cc_const.C_PART_NAME_DELIMETER || l_ins_name || pkg_cc_const.C_PART_NAME_DELIMETER || pkg_cc_const.C_FACT_ID_NAME || '.nextval, ' || l_ins_str || ', ' || pkg_cc_const.C_CREATED_DATE_VAL || ')';
                  
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 3) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, '   l_stmt=' || l_stmt, null, PKG_CC_LOG.C_OP_LOG_VAR, null);
end if;
                   
                   execute immediate  
                     l_stmt;
                    
                   --коммитим в JAVA-модуле      
                   --commit;

                else
									
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 2) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Таблица фактов не заполнялась', null , PKG_CC_LOG.C_OP_LOG_INFO_MSG, null);
end if;
     
                end if;
          
              end loop; -- строки
               
          end loop; -- группы
      end loop;
   
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 1) then 
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Завершение функции', logmsg, pkg_cc_log.C_OP_LOG_SUCCESS_MSG, null);
end if;
 
    return 1;
    
  exception
    when others then
			
-- log
if (pkg_cc_const.C_lOG_LEVEL >= 0) then
  pkg_cc_log.op_log(p_prc_id, null, logsender, 'Ошибки функции', logmsg, pkg_cc_log.C_OP_LOG_ERROR_MSG, sqlerrm);
end if;
  
    return 0;
  end fnc_load_cube;
  
end PKG_CC_STRUCTURE;
/
