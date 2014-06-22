package sitronics.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.regex.Pattern;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import sitronics.data.utils.Common;
import sitronics.data.utils.consts.Consts;

public class TableDataGen {
    static String TABLEMAP_TABLE_NAME = "TABLE_NAME";
    static String TABLEMAP_TABLE_DESC = "TABLE_DESC";
    static String TABLEMAP_DIM = "DIM";
    static String TABLEMAP_IS_DUMMY = "IS_DUMMY";
    static String TABLEMAP_GROUP = "GROUP";
    static String TABLEMAP_COLUMNS = "COLUMNS";
    
    static String COLUMN_MAP_COLUMN_NAME = "COLUMN_NAME";
    static String COLUMN_MAP_COLUMN_TYPE = "COLUMN_TYPE";
    static String COLUMN_MAP_COLUMN_FORMAT = "COLUMN_FORMAT";
    static String COLUMN_MAP_IS_PRIMARY_KEY = "IS_PRIMARY_KEY";
    static String COLUMN_MAP_IS_NULLABLE = "IS_NULLABLE";
    static String COLUMN_MAP_IS_FOREIGN_KEY = "IS_FOREIGN_KEY";
    static String COLUMN_MAP_REF_TABLE_NAME = "REF_TABLE_NAME";
    static String COLUMN_MAP_AGGR_RULE = "AGGR_RULE";
    static String COLUMN_MAP_COLUMN_DESC = "COLUMN_DESC";
    
    static String DATALIST_GROUPS = "GROUPS";
    static String DATALIST_TABLES = "TABLES";
    static String DATALIST_PM_NAME = "PM_NAME";
    static String DATALIST_PM_DESC = "PM_DESC";
    static String DATALIST_IS_NEW_MODEL = "IS_NEW_MODEL";
    
    private static String defaultVarcharTypeLength = "36";
    private static String varcharType = "VARCHAR";
    private static String varcharDBType = "varchar2";
    private static String integerType = "INT";
    private static String doubleType = "DOUBLE";
    private static String numberDBType = "number";
    private static String datetimeType = "DATETIME";
    private static String datetimeDBType = "date";
    
    public TableDataGen() {
        super();
    }

    /**
     * Generates Document instance (DOM) from string with xml structure.
     * 
     * @param xmlstring
     * @return
     */
    public static Document getDomFromXmlString(String xmlstring) {
        return Common.generateDom(xmlstring);
    }

    /**
     * Sets "true" or "false" key flag.
     * 
     * @param xmlFlag
     * @return
     */
    public static String setKeyFlag(String xmlFlag) {
        if (xmlFlag.equals("true")) {
            return "true";
        } else {
            return "false";
        }
    }

    /**
     * Method for setting the "is nullable" property.
     * 
     * @param isPrimaryKey
     * @param isForeignKey
     * @return
     */
    public static String setNullable(String isPrimaryKey, String isForeignKey) {
        if (isPrimaryKey.equals("true") && isForeignKey.equals("false")) {
            return "false";
        } else if (isPrimaryKey.equals("false") && isForeignKey.equals("false")) {
            return "true";
        } else {
            return "true";
        }
    }

    /**
     * Returns "true", if table dimension is "dummy_fact" or "dummy_dim".
     * Returns "false" otherwise.
     * 
     * @param dim
     * @return
     */
    public static String isDummy(String dim) {
        //if it is fact or dim return "false", because it's not a dummy table
        //@ isadikov: change the logiccal way for isDummy() method
        // we are using the array of dims which are dummy
        
        for (int i=0; i<Consts.getRE().DUMMY_TABLES_ARRAY.length; i++) {
            if (Consts.getRE().DUMMY_TABLES_ARRAY[i].equals(dim)) {
                return "true";
            } 
        }
        
        return "false";
    }

    /**
     * Returns a column data type.
     * 
     * @param cType
     * @return
     */
    public static String getColumnType(String cType) {
        String t = cType.replace("(", "#").replace(")", "").split("#")[0];
        
        try {
            if (t.equalsIgnoreCase(varcharDBType)) {
                return varcharType;
            } else if (t.equalsIgnoreCase(datetimeDBType)) {
                return datetimeType;
            } else if (t.equalsIgnoreCase(numberDBType)) {
                if (cType.replace("(", "#").replace(")", "").split("#")[1].split(",").length > 1) {
                    return doubleType;
                } else {
                    return integerType;
                }
            } else {
                return varcharType;
            }
        } catch (Exception e) {
            return varcharType;
        }
    }

    /**
     * Returns column data format (in addition to the type)
     * 
     * @param cType
     * @return
     */
    public static String getColumnFormat(String cType) {
        String t = cType.replace("(", "<").replace(")", ">");
        try {
            return t.split("<")[1].split(">")[0].split(",")[0].replace(" CHAR", "");
        } catch (Exception e) {
            return defaultVarcharTypeLength;
        }
    }

    /**
     * Returns aggregate rule.
     * 
     * @param aggRule
     * @return
     */
    public static String getAggrRule(String aggRule) {
        if (aggRule.equals("") || aggRule.equalsIgnoreCase(Consts.getEX().UNKNOWN_STRING)) {
            return "";
        } else {
            return aggRule;
        }
    }

    /**
     * Method for beautyfing a table name.
     * 
     * @param name
     * @return
     */
    public static String beautify(String name) {
        String[] nameList = name.split("_");
        /* 
         * Common object name can be equal several types : 
         * index1 index2 name
         * index1 index2
         * index1 name
         * index1
         * name
         */
        String res = "";
        
        Pattern pattern = Pattern.compile("\\d");
        
        if (nameList.length >= 2) {
            if (pattern.matcher(nameList[0]).find() && pattern.matcher(nameList[1]).find()) {
                res = nameList[0] + "." + nameList[1] + " ";
                
                for (int i=2; i<nameList.length; i++) {
                    res += nameList[i].substring(0, 1).toUpperCase() + nameList[i].substring(1).toLowerCase() + " ";
                }
                
                return res.trim();
            } else if (pattern.matcher(nameList[0]).find() && !pattern.matcher(nameList[1]).find()) {
                res = nameList[0] + " ";
                
                for (int i=1; i<nameList.length; i++) {
                    res += nameList[i].substring(0, 1).toUpperCase() + nameList[i].substring(1).toLowerCase() + " ";
                }
                
                return res.trim();
            } else {
                for (int i=0; i<nameList.length; i++) {
                    res += nameList[i].substring(0, 1).toUpperCase() + nameList[i].substring(1).toLowerCase() + " ";
                }
                return res.trim();
            }
        }
        return res = nameList[0].substring(0, 1).toUpperCase() + nameList[0].substring(1).toLowerCase();
    }

    /**
     * Method for check and adding group if it is not existing in the list.
     * 
     * @param groupsData
     * @param tablesData
     */
    public static void checkAndAddGroupToTheList(List<String> groupsData, List<HashMap> tablesData) {
        List<HashMap> columns = new ArrayList<HashMap>();
        String refTableName = "";
        String tableName = "";
        
        for (HashMap tableMap : tablesData) {
            tableName = (String)tableMap.get(TABLEMAP_TABLE_NAME);
            
            for (HashMap table : tablesData) {
                columns = (List<HashMap>)table.get(TABLEMAP_COLUMNS);
                for (HashMap column : columns) {
                    refTableName = (String)column.get(COLUMN_MAP_REF_TABLE_NAME);
                    
                    if (!refTableName.isEmpty() && refTableName.equals(tableName) 
                        && !table.get(TABLEMAP_GROUP).equals(tableMap.get(TABLEMAP_GROUP))
                        && table.get(TABLEMAP_IS_DUMMY).equals("false")) {
                        
                        tableMap.put(TABLEMAP_GROUP, Consts.getRE().SUBJECT_AREA_LOCAL_COMMON_GROUP_NAME);
                    }
                }
            }
        }
        
        String groupName = "";
        String isDummy = "";
        
        for (HashMap tableMap : tablesData) {
            groupName = (String)tableMap.get(TABLEMAP_GROUP);
            isDummy = (String)tableMap.get(TABLEMAP_IS_DUMMY);
            
            if (groupsData.size() <= 0) {
                groupsData.add(groupName);
            } else {
                if (isDummy.equals("false")) {
                    if (!groupsData.contains(groupName)) {
                        groupsData.add(groupName);
                    }
                }
            }
        }
        
        refTableName = null;
        tableName = null;
        
        groupName = null;
        isDummy = null;
        
    }

    /**
     * Main method for loading table data from xmlstring.
     * 
     * @param xmlstring
     * @return
     */
    public static HashMap loadTableData(String xmlstring) {
        String pmName = "";
        String pmDesc = "";
        List<HashMap> tablesData = new ArrayList<HashMap>();
        // @isadikov: list for groups
        List<String> tablesGroupsData = new ArrayList<String>();
        
        Document d = Common.generateDom(xmlstring);
        
        Element cube = d.getDocumentElement();
        pmName = cube.getAttribute(Consts.getRE().TD_CUBE_NAME_ATTR);
        pmDesc = cube.getAttribute(Consts.getRE().TD_CUBE_DESC_ATTR);
        boolean isNewModel = Boolean.parseBoolean(cube.getAttribute(Consts.getRE().TD_CUBE_NEW_MODEL_ATTR));
        
        Element tablesEl = (Element)cube.getElementsByTagName(Consts.getRE().TD_TABLES_TAG).item(0);
        NodeList tables = tablesEl.getElementsByTagName(Consts.getRE().TD_TABLE_TAG);
        
        //load table info
        for (int i=0; i<tables.getLength(); i++) {
            HashMap tableMap = new HashMap();
            
            Element table = (Element)tables.item(i); 
            tableMap.put(TABLEMAP_TABLE_NAME, table.getAttribute(Consts.getRE().TD_TABLE_NAME_ATTR));
            tableMap.put(TABLEMAP_TABLE_DESC, table.getAttribute(Consts.getRE().TD_TABLE_DESC_ATTR));
            tableMap.put(TABLEMAP_DIM, table.getAttribute(Consts.getRE().TD_TABLE_DIM_ATTR).toLowerCase());
            tableMap.put(TABLEMAP_IS_DUMMY, isDummy(table.getAttribute(Consts.getRE().TD_TABLE_DIM_ATTR).toLowerCase()));
            tableMap.put(TABLEMAP_GROUP, table.getAttribute(Consts.getRE().TD_TABLE_GROUP_ATTR));
            
            Element columnsEl = (Element)table.getElementsByTagName(Consts.getRE().TD_COLUMNS_TAG).item(0);
            NodeList columns = columnsEl.getElementsByTagName(Consts.getRE().TD_COLUMN_TAG);
            
            List<HashMap> columnsMap = new ArrayList<HashMap>();
            
            for (int j=0; j<columns.getLength(); j++) {
                HashMap columnMap = new HashMap();
                
                Element column = (Element)columns.item(j);
                String isPrimaryKey = setKeyFlag(column.getAttribute(Consts.getRE().TD_COLUMN_ISPRIMARYKEY_ATTR));
                String isForeignKey = setKeyFlag(column.getAttribute(Consts.getRE().TD_COLUMN_ISFOREIGNKEY_ATTR));
                String isNullable = setNullable(isPrimaryKey, isForeignKey);
                
                columnMap.put(COLUMN_MAP_COLUMN_NAME, column.getAttribute(Consts.getRE().TD_COLUMN_NAME_ATTR));
                columnMap.put(COLUMN_MAP_COLUMN_TYPE, getColumnType(column.getAttribute(Consts.getRE().TD_COLUMN_TYPE_ATTR)));
                columnMap.put(COLUMN_MAP_COLUMN_FORMAT, getColumnFormat(column.getAttribute(Consts.getRE().TD_COLUMN_TYPE_ATTR)));
                columnMap.put(COLUMN_MAP_IS_PRIMARY_KEY, isPrimaryKey);
                columnMap.put(COLUMN_MAP_IS_NULLABLE, isNullable);
                columnMap.put(COLUMN_MAP_IS_FOREIGN_KEY, isForeignKey);
                columnMap.put(COLUMN_MAP_REF_TABLE_NAME, column.getAttribute(Consts.getRE().TD_COLUMN_REF_TABLENAME_ATTR));
                columnMap.put(COLUMN_MAP_AGGR_RULE, getAggrRule(column.getAttribute(Consts.getRE().TD_COLUMN_AGGR_TYPE_ATTR)));
                columnMap.put(COLUMN_MAP_COLUMN_DESC, column.getAttribute(Consts.getRE().TD_COLUMN_DESC_ATTR));
                
                columnsMap.add(columnMap);    
                
                columnMap = null;
            }
            
            tableMap.put(TABLEMAP_COLUMNS, columnsMap);
            columnsMap = null;
            
            tablesData.add(tableMap);
        }
        
        checkAndAddGroupToTheList(tablesGroupsData, tablesData);
        
        HashMap res = new HashMap();
        
        res.put(DATALIST_GROUPS, tablesGroupsData);
        res.put(DATALIST_TABLES, tablesData);
        res.put(DATALIST_PM_NAME, pmName);
        res.put(DATALIST_PM_DESC, pmDesc);
        res.put(DATALIST_IS_NEW_MODEL, isNewModel);
        
        tablesData = null;
        tablesGroupsData = null;
        
        return res;
    }
    
    /*
    public static void main(String[] args) {
        String xmlstring = "<?xml version=\"1.0\"?>\n" + 
        "<cube name=\"PM012\" desc=\"\" isNewModel=\"true\">\n" + 
        "  <tables>\n" + 
        "    <table name=\"PM001_F001\" desc=\"Показатели группы 1\" type=\"fact\" group=\"1\">\n" + 
        "      <cols>\n" + 
        "        <col name=\"FACT_ID\" type=\"NUMBER(10)\" desc=\"Fact key\" isPrimary=\"true\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "        <col name=\"D001_DIM_ID\" type=\"NUMBER(10)\" desc=\"Dimension ref on D001\" isPrimary=\"false\" isForeignkey=\"true\" aggrType=\"\" refTableName=\"PM001_D001_REGION\"/>\n" + 
        "        <col name=\"D003_DIM_ID\" type=\"NUMBER(10)\" desc=\"Dimension ref on D003\" isPrimary=\"false\" isForeignkey=\"true\" aggrType=\"\" refTableName=\"PM001_D003_CITY\"/>\n" + 
        "        <col name=\"D004_DIM_ID\" type=\"NUMBER(10)\" desc=\"Dimension ref on D004\" isPrimary=\"false\" isForeignkey=\"true\" aggrType=\"\" refTableName=\"PM001_D004_TYPE\"/>\n" + 
        "        <col name=\"O005_M01_SUM\" type=\"NUMBER(18,2)\" desc=\"Сумма\" isPrimary=\"false\" isForeignkey=\"false\" aggrType=\"sum\" refTableName=\"\"/>\n" + 
        "        <col name=\"CREATED_DATE\" type=\"DATE\" desc=\"Dimension key\" isPrimary=\"false\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "      </cols>\n" + 
        "    </table>\n" + 
        "    <table name=\"PM001_D001_REGION\" desc=\"Справочник =Регион=\" type=\"dim\" group=\"1\">\n" + 
        "      <cols>\n" + 
        "        <col name=\"DIM_ID\" type=\"NUMBER(10)\" desc=\"Dimension key\" isPrimary=\"true\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "        <col name=\"O001_A01_REGION\" type=\"VARCHAR2(512 CHAR)\" desc=\"Регион\" isPrimary=\"false\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "        <col name=\"CREATED_DATE\" type=\"DATE\" desc=\"Dimension key\" isPrimary=\"false\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "      </cols>\n" + 
        "    </table>\n" + 
        "    <table name=\"PM001_D002_AREA\" desc=\"Справочник =Область=\" type=\"dim\" group=\"1\">\n" + 
        "      <cols>\n" + 
        "        <col name=\"DIM_ID\" type=\"NUMBER(10)\" desc=\"Dimension key\" isPrimary=\"true\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "        <col name=\"O002_A01_AREA\" type=\"VARCHAR2(512 CHAR)\" desc=\"Область\" isPrimary=\"false\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "        <col name=\"CREATED_DATE\" type=\"DATE\" desc=\"Dimension key\" isPrimary=\"false\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "      </cols>\n" + 
        "    </table>\n" + 
        "    <table name=\"PM001_D003_CITY\" desc=\"Справочник =Город=\" type=\"parent_dim\" group=\"1\">\n" + 
        "      <cols>\n" + 
        "        <col name=\"DIM_ID\" type=\"NUMBER(10)\" desc=\"Dimension key\" isPrimary=\"true\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "        <col name=\"D002_DIM_ID\" type=\"NUMBER(10)\" desc=\"Dimension ref on D002\" isPrimary=\"false\" isForeignkey=\"true\" aggrType=\"\" refTableName=\"PM001_D002_AREA\"/>\n" + 
        "        <col name=\"O003_A01_CITY\" type=\"VARCHAR2(512 CHAR)\" desc=\"Город\" isPrimary=\"false\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "        <col name=\"CREATED_DATE\" type=\"DATE\" desc=\"Dimension key\" isPrimary=\"false\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "      </cols>\n" + 
        "    </table>\n" + 
        "    <table name=\"PM001_D004_TYPE\" desc=\"Справочник =Тип=\" type=\"dim\" group=\"1\">\n" + 
        "      <cols>\n" + 
        "        <col name=\"DIM_ID\" type=\"NUMBER(10)\" desc=\"Dimension key\" isPrimary=\"true\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "        <col name=\"O004_A01_TYPE\" type=\"VARCHAR2(512 CHAR)\" desc=\"Тип\" isPrimary=\"false\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "        <col name=\"CREATED_DATE\" type=\"DATE\" desc=\"Dimension key\" isPrimary=\"false\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "      </cols>\n" + 
        "    </table>\n" + 
        "    <table name=\"PM001_F002\" desc=\"Показатели группы 2\" type=\"dummy_fact\" group=\"2\">\n" + 
        "      <cols>\n" + 
        "        <col name=\"FACT_ID\" type=\"NUMBER(10)\" desc=\"Fact key\" isPrimary=\"true\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "        <col name=\"D002_DIM_ID\" type=\"NUMBER(10)\" desc=\"Dimension ref on D002\" isPrimary=\"false\" isForeignkey=\"true\" aggrType=\"\" refTableName=\"PM001_D002_AREA\"/>\n" + 
        "		<col name=\"D004_DIM_ID\" type=\"NUMBER(10)\" desc=\"Dimension ref on D004\" isPrimary=\"false\" isForeignkey=\"true\" aggrType=\"\" refTableName=\"PM001_D004_MASK\"/>\n" + 
        "        <col name=\"CREATED_DATE\" type=\"DATE\" desc=\"Dimension key\" isPrimary=\"false\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "      </cols>\n" + 
        "    </table>\n" + 
        "	<table name=\"PM001_D004_MASK\" desc=\"Справочник =Mask=\" type=\"dim\" group=\"2\">\n" + 
        "      <cols>\n" + 
        "        <col name=\"DIM_ID\" type=\"NUMBER(10)\" desc=\"Dimension key\" isPrimary=\"true\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "        <col name=\"O004_A01_TYPE\" type=\"VARCHAR2(512 CHAR)\" desc=\"Тип\" isPrimary=\"false\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "        <col name=\"CREATED_DATE\" type=\"DATE\" desc=\"Dimension key\" isPrimary=\"false\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "      </cols>\n" + 
        "    </table>\n" + 
        "	<table name=\"PM001_F003\" desc=\"Показатели группы 3\" type=\"dummy_fact\" group=\"3\">\n" + 
        "      <cols>\n" + 
        "        <col name=\"FACT_ID\" type=\"NUMBER(10)\" desc=\"Fact key\" isPrimary=\"true\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "        <col name=\"D002_DIM_ID\" type=\"NUMBER(10)\" desc=\"Dimension ref on D002\" isPrimary=\"false\" isForeignkey=\"true\" aggrType=\"\" refTableName=\"PM001_D002_AREA\"/>\n" + 
        "        <col name=\"CREATED_DATE\" type=\"DATE\" desc=\"Dimension key\" isPrimary=\"false\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "      </cols>\n" + 
        "    </table>\n" + 
        "	<table name=\"PM001_F003\" desc=\"Показатели группы 4\" type=\"fact\" group=\"4\">\n" + 
        "      <cols>\n" + 
        "        <col name=\"FACT_ID\" type=\"NUMBER(10)\" desc=\"Fact key\" isPrimary=\"true\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "        <col name=\"D001_DIM_ID\" type=\"NUMBER(10)\" desc=\"Dimension ref on D001\" isPrimary=\"false\" isForeignkey=\"true\" aggrType=\"\" refTableName=\"PM001_D001_REGION\"/>\n" + 
        "        <col name=\"CREATED_DATE\" type=\"DATE\" desc=\"Dimension key\" isPrimary=\"false\" isForeignkey=\"false\" aggrType=\"\" refTableName=\"\"/>\n" + 
        "      </cols>\n" + 
        "    </table>\n" + 
        "  </tables>\n" + 
        "</cube>";
        HashMap res = loadTableData(xmlstring);
        System.out.println(res.get(DATALIST_GROUPS));
        System.out.println(res.get(DATALIST_TABLES));
    }
    */
}