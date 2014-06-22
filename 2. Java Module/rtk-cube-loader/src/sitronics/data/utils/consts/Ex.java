package sitronics.data.utils.consts;

public class Ex {
    
    public static String ENCODE_STRING = "Cp1252";
    public static String COLUMNS_SEPARATOR = ";";
    public static String COLUMNS_SEPARATOR_R = "U+003B";
    public static String COLUMNS_LINE_SEPARATOR = ":";
    public static String COLUMNS_LINE_SEPARATOR_R = "U+003A";
    public static String ROWS_SEPARATOR = "#";
    public static String ROWS_SEPARATOR_R = "U+266F";
    public static String APOSTROPHE = "'";
    public static String APOSTROPHE_R = "U+0027";
    public static String PIVOT_SPLIT_REGEX = ",";
        
    /* configuration sheet constants */
    public static String CONFIGURATION_SHEET = "XDO_CONFIGDATA";
    // @isadikov: configuration sheet start row. Points where the actual data starts 
    // (1 - from first row in sheet)
    public static int CONFIGURATION_LIST_START_ROW = 2;
    public static String ATTRIBUTE_COLUMN = "ATTRIBUTE_COLUMN";
    public static String FACT_COLUMN = "FACT_COLUMN";
    
    /* configuration sheet mapping cells' columns */
    public static String CFG_PROPERTIES = "A";
    public static String CFG_SHEET_NAME = "B";
    public static String CFG_DATA_GROUP = "C";
    public static String CFG_POS_IN_GROUP = "D";
    public static String CFG_START_ROW = "E";
    public static String CFG_PARENT_POS = "F";
    public static String CFG_NAME = "G";
    public static String CFG_TYPE = "H";
    public static String CFG_FORMAT = "I";
    public static String CFG_AGG_RULE = "J";
    public static String CFG_DESCRIPTION = "K";
    public static String CFG_PIVOT_ROW_POSITION = "L";
    
    /* 
     * dimensions for columns
     * DIMENSION - name of object's property
     * ATTRIBUTE_TYPE - type of table (D - for dimension column/table)
     * FACT_TYPE - type of table (F - for fact column/table)
     * ATTRIBUTE_PROPERTY - type of column (A - for attributes without aggregation rule)
     * MEASURE_PROPERTY - type of column (M - for attributes with aggregation rule)
     *
     */
    public static String DIMENSION = "DIMENSION";
    public static String ATTRIBUTE_TYPE = "D";
    public static String FACT_TYPE = "F";
    public static String ATTRIBUTE_PROPERTY = "A";
    public static String MEASURE_PROPERTY = "M";
    
    /* column properties */
    public static String COLUMN_NAME = "COLUMN_NAME";
    public static int COLUMN_NAME_MAX_LENGTH = 12;
    public static String COLUMN_DESCRIPTION = "COLUMN_DESCRIPTION";
    public static String COLUMN_TYPE = "COLUMN_TYPE";
    public static String COLUMN_DB_TYPE = "COLUMN_DB_TYPE";
    public static String COLUMN_FORMAT = "COLUMN_FORMAT";
    public static String COLUMN_AGGR_RULE = "COLUMN_AGGR_RULE";
    public static String COLUMN_SHEET = "COLUMN_SHEET";
    public static String COLUMN_DATA_GROUP = "COLUMN_DATA_GROUP";
    public static String COLUMN_START_ROW = "COLUMN_START_ROW";
    public static String COLUMN_POSITION_IN_GROUP = "COLUMN_POSITION_IN_GROUP";
    public static String COLUMN_PARENT_POSITION = "COLUMN_PARENT_POSITION";
    public static String COLUMN_PIVOT_ROW_POSITION = "COLUMN_PIVOT_ROW_POSITION";
    public static String COLUMN_COMPLEX_GROUP = "COLUMN_COMPLEX_GROUP";
    
    public static String COLUMN_UNIQUE_CODE = "COLUMN_UNIQUE_CODE";
    
    /* list keys for columns info */
    public static String COLUMNS_INFO = "COLUMNS_INFO";
    public static String CELLS = "CELLS";
    
    /* column types and masks */
    /* string */
    public static String STRING_EXCEL_TYPE = "string";
    public static String STRING_TYPE = "varchar2";
    public static String STRING_FORMAT_TYPE = "512 char";
    /* date */
    public static String DATE_TYPE = "date";
    /* number */
    public static String NUMBER_TYPE = "number";
    public static String NUMBER_DOUBLE_MASK = "####,##";
    public static String NUMBER_INT_MASK = "####";
    public static String NUMBER_DOUBLE_TYPE = "18,2";
    public static String NUMBER_INT_TYPE = "10";
    /* aggregation rules */
    public static String AGGR_MAX_TYPE = "max";
    public static String AGGR_MIN_TYPE = "min";
    public static String AGGR_AVG_TYPE = "avg";
    public static String AGGR_SUM_TYPE = "sum";
    public static String AGGR_CNT_TYPE = "count";
    
    /* unknown constants */
    public static String UNKNOWN_STRING = "unknown";
    public static int UNKNOWN_NUMBER = -1;
    
    // XML structure and load structure tags
    // Groups
    public static String XML_GROUPS_TAG = "Groups";
    // Group
    public static String XML_GROUP_TAG = "Group";
    public static String XML_GROUP_NAME_ATTR = "name";
    public static String XML_GROUP_IS_PIVOT_ATTR = "isPivot";
    public static String XML_GROUP_MAX_ROWS_ATTR = "maxRows";
    public static String XML_GROUP_MIN_START_ROW_ATTR = "minStartRow";
    public static String XML_GROUP_SHEET_ATTR = "sheet";
    // Column
    public static String XML_COLUMN_TAG = "Column";
    public static String XML_COLUMN_NAME_ATTR = "name";
    public static String XML_COLUMN_DESC_ATTR = "desc";
    public static String XML_COLUMN_CTYPE_ATTR = "valueType";
    public static String XML_COLUMN_AGGTYPE_ATTR = "aggrType";
    public static String XML_COLUMN_COLUMN_POS_ATTR = "posInGroup";
    public static String XML_COLUMN_PARENT_POS_ATTR = "parentPos";
    public static String XML_COLUMN_ABS_COLUMN_POS_ATTR = "absPos";
    public static String XML_COLUMN_PIVOT_POS_ATTR = "pivotPos";
    public static String XML_COLUMN_PIVOT_ROW_POS_ATTR = "pivotRowPos";
    public static String XML_COLUMN_DIM_ATTR = "type";
    public static String XML_COLUMN_TYPE_ATTR = "columnType";
    public static String XML_COLUMN_FORMAT_ATTR = "columnFormat";
    public static String XML_COLUMN_UNIQUE_CODE_ATTR = "uniqueCode";
    public static String XML_COLUMN_ABS_GROUP_POS_ATTR = "absGroupPos";
    // Row
    public static String XML_ROW_TAG = "Row";
    public static String XML_ROW_NUMBER_ATTR = "number";
    // Value
    public static String XML_VALUE_TAG = "Value";
    public static String XML_VALUE_NAME_ATTR = "name";
    public static String XML_VALUE_ABS_POS_ATTR = "absPos";
    
    public Ex() {
        super();
    }
    
    private static Ex Instance = new Ex();
    
    public static Ex getEx() {
        return Instance;
    }
}
