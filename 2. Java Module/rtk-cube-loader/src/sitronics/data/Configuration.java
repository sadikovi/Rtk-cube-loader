package sitronics.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.io.IOException;

import java.io.InputStreamReader;

import java.util.Properties;

import sitronics.data.utils.Common;
import sitronics.data.utils.consts.Pm;
import sitronics.data.utils.consts.Ex;
import sitronics.data.utils.consts.Db;
import sitronics.data.utils.consts.Msg;
import sitronics.data.utils.consts.Re;


public class Configuration {
    
    public Configuration() {
        super();
    }
    
    // CLProperties.ini is a configuration file name
    public static Properties getPropertiesInstance() throws FileNotFoundException,
                                                            IOException {
        Properties props = new Properties();
        props.load(new FileInputStream(new File("properties/CLProperties.ini")));
        
        return props;
    }
    
    public static Properties getPropertiesInstance(String filepath) throws FileNotFoundException,
                                                                              IOException {
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream(new File(filepath));
        InputStreamReader fisReader = new InputStreamReader(fis, "cp1251");
        //props.load(new FileInputStream(new File(filepath)));
        props.load(fisReader);
        fis.close();
        
        return props;
    }
    
    //if it's null then we do not change the value;
    // @isadikov: and if it does not exists in configuration we also dont change the value
    private static String getProperty(String defaultValue, String newValue) {
        try {
            newValue = newValue.trim();
            defaultValue = defaultValue.trim();
            
            if (Common.isEmpty(newValue)) {
                return defaultValue;
            } else {
                return newValue.substring(0, 1).replace("\"", "") 
                       + newValue.substring(1, newValue.length()-2)
                       + newValue.substring(newValue.length()-2, newValue.length()-1).replace("\"", "") 
                       + newValue.substring(newValue.length()-1, newValue.length()).replace(";", "");
            }
        } catch (Exception e) {
            return defaultValue;
        }
    }
    
    private static int getProperty(Integer defaultValue, String newValue) {
        String defValueString = defaultValue.toString();
        
        return Integer.parseInt(Configuration.getProperty(defValueString, newValue));
    }
    
    private static boolean getProperty(Boolean defaultValue, String newValue) {
        String defValueString = defaultValue.toString();
        String newValueString = Configuration.getProperty(defValueString, newValue);
        
        if (newValueString.equalsIgnoreCase("true") || newValueString.equalsIgnoreCase("false")) {
            return Boolean.parseBoolean(newValueString);
        } else {
            return defaultValue;
        }
    }
    
    private static void loadPMProperties(Properties props) {
        Pm.BI_ANALYTICS_URL = Configuration.getProperty(Pm.BI_ANALYTICS_URL, props.getProperty("BI_ANALYTICS_URL"));
        Pm.METADATA_SERVICE = Configuration.getProperty(Pm.METADATA_SERVICE, props.getProperty("METADATA_SERVICE"));
        Pm.SAW_SESSION_SERVICE = Configuration.getProperty(Pm.SAW_SESSION_SERVICE, props.getProperty("SAW_SESSION_SERVICE"));
        
        Pm.BI_REQUIRED_ROLE = Configuration.getProperty(Pm.BI_REQUIRED_ROLE, props.getProperty("BI_REQUIRED_ROLE"));
        Pm.ADMIN_ROLE = Configuration.getProperty(Pm.ADMIN_ROLE, props.getProperty("ADMIN_ROLE"));
        
        Pm.FORM_UPLOAD_NAME = Configuration.getProperty(Pm.FORM_UPLOAD_NAME, props.getProperty("FORM_UPLOAD_NAME"));
        Pm.FORM_PM_NAME_INPUT = Configuration.getProperty(Pm.FORM_PM_NAME_INPUT, props.getProperty("FORM_PM_NAME_INPUT"));
        Pm.FORM_PM_NAME_INPUT_VALUE = Configuration.getProperty(Pm.FORM_PM_NAME_INPUT_VALUE, props.getProperty("FORM_PM_NAME_INPUT_VALUE"));
        Pm.FORM_PM_NAME_SELECT = Configuration.getProperty(Pm.FORM_PM_NAME_SELECT, props.getProperty("FORM_PM_NAME_SELECT"));
        Pm.FORM_PM_NAME_SELECT_VALUE = Configuration.getProperty(Pm.FORM_PM_NAME_SELECT_VALUE, props.getProperty("FORM_PM_NAME_SELECT_VALUE"));
        Pm.FORM_PM_NAME_RADIONAME = Configuration.getProperty(Pm.FORM_PM_NAME_RADIONAME, props.getProperty("FORM_PM_NAME_RADIONAME"));
        Pm.FORM_IMPORT_FILE = Configuration.getProperty(Pm.FORM_IMPORT_FILE, props.getProperty("FORM_IMPORT_FILE"));
        Pm.FORM_PM_DESCRIPTION = Configuration.getProperty(Pm.FORM_PM_DESCRIPTION, props.getProperty("FORM_PM_DESCRIPTION"));
        
        Pm.FORM_PM_LOAD_ONLY_DATA_RADIO = Configuration.getProperty(Pm.FORM_PM_LOAD_ONLY_DATA_RADIO, props.getProperty("FORM_PM_LOAD_ONLY_DATA_RADIO"));
        Pm.FORM_PM_LOAD_ONLY_DATA_RADIO_VALUE = Configuration.getProperty(Pm.FORM_PM_LOAD_ONLY_DATA_RADIO_VALUE, props.getProperty("FORM_PM_LOAD_ONLY_DATA_RADIO_VALUE"));
    }
    
    private static void loadEXProperties(Properties props) {
        Ex.ENCODE_STRING = Configuration.getProperty(Ex.ENCODE_STRING, props.getProperty("ENCODE_STRING"));
        Ex.COLUMNS_SEPARATOR = Configuration.getProperty(Ex.COLUMNS_SEPARATOR, props.getProperty("COLUMNS_SEPARATOR"));
        Ex.COLUMNS_SEPARATOR_R = Configuration.getProperty(Ex.COLUMNS_SEPARATOR_R, props.getProperty("COLUMNS_SEPARATOR_R"));
        Ex.COLUMNS_LINE_SEPARATOR = Configuration.getProperty(Ex.COLUMNS_LINE_SEPARATOR, props.getProperty("COLUMNS_LINE_SEPARATOR"));
        Ex.COLUMNS_LINE_SEPARATOR_R = Configuration.getProperty(Ex.COLUMNS_LINE_SEPARATOR_R, props.getProperty("COLUMNS_LINE_SEPARATOR_R"));
        Ex.ROWS_SEPARATOR = Configuration.getProperty(Ex.ROWS_SEPARATOR, props.getProperty("ROWS_SEPARATOR"));
        Ex.ROWS_SEPARATOR_R = Configuration.getProperty(Ex.ROWS_SEPARATOR_R, props.getProperty("ROWS_SEPARATOR_R"));
        Ex.APOSTROPHE = Configuration.getProperty(Ex.APOSTROPHE, props.getProperty("APOSTROPHE"));
        Ex.APOSTROPHE_R = Configuration.getProperty(Ex.APOSTROPHE_R, props.getProperty("APOSTROPHE_R"));
        Ex.PIVOT_SPLIT_REGEX = Configuration.getProperty(Ex.PIVOT_SPLIT_REGEX, props.getProperty("PIVOT_SPLIT_REGEX"));
        
        Ex.CONFIGURATION_SHEET = Configuration.getProperty(Ex.CONFIGURATION_SHEET, props.getProperty("CONFIGURATION_SHEET"));
        Ex.CONFIGURATION_LIST_START_ROW = Configuration.getProperty(Ex.CONFIGURATION_LIST_START_ROW, props.getProperty("CONFIGURATION_LIST_START_ROW"));
        Ex.ATTRIBUTE_COLUMN = Configuration.getProperty(Ex.ATTRIBUTE_COLUMN, props.getProperty("ATTRIBUTE_COLUMN"));
        Ex.FACT_COLUMN = Configuration.getProperty(Ex.FACT_COLUMN, props.getProperty("FACT_COLUMN"));
        
        Ex.CFG_PROPERTIES = Configuration.getProperty(Ex.CFG_PROPERTIES, props.getProperty("CFG_PROPERTIES"));
        Ex.CFG_SHEET_NAME = Configuration.getProperty(Ex.CFG_SHEET_NAME, props.getProperty("CFG_SHEET_NAME"));
        Ex.CFG_DATA_GROUP = Configuration.getProperty(Ex.CFG_DATA_GROUP, props.getProperty("CFG_DATA_GROUP"));
        Ex.CFG_POS_IN_GROUP = Configuration.getProperty(Ex.CFG_POS_IN_GROUP, props.getProperty("CFG_POS_IN_GROUP"));
        Ex.CFG_START_ROW = Configuration.getProperty(Ex.CFG_START_ROW, props.getProperty("CFG_START_ROW"));
        Ex.CFG_PARENT_POS = Configuration.getProperty(Ex.CFG_PARENT_POS, props.getProperty("CFG_PARENT_POS"));
        Ex.CFG_NAME = Configuration.getProperty(Ex.CFG_NAME, props.getProperty("CFG_NAME"));
        Ex.CFG_TYPE = Configuration.getProperty(Ex.CFG_TYPE, props.getProperty("CFG_TYPE"));
        Ex.CFG_FORMAT = Configuration.getProperty(Ex.CFG_FORMAT, props.getProperty("CFG_FORMAT"));
        Ex.CFG_AGG_RULE = Configuration.getProperty(Ex.CFG_AGG_RULE, props.getProperty("CFG_AGG_RULE"));
        Ex.CFG_DESCRIPTION = Configuration.getProperty(Ex.CFG_DESCRIPTION, props.getProperty("CFG_DESCRIPTION"));
        Ex.CFG_PIVOT_ROW_POSITION = Configuration.getProperty(Ex.CFG_PIVOT_ROW_POSITION, props.getProperty("CFG_PIVOT_ROW_POSITION"));
        
        Ex.DIMENSION = Configuration.getProperty(Ex.DIMENSION, props.getProperty("DIMENSION"));
        Ex.ATTRIBUTE_TYPE = Configuration.getProperty(Ex.ATTRIBUTE_TYPE, props.getProperty("ATTRIBUTE_TYPE"));
        Ex.FACT_TYPE = Configuration.getProperty(Ex.FACT_TYPE, props.getProperty("FACT_TYPE"));
        Ex.ATTRIBUTE_PROPERTY = Configuration.getProperty(Ex.ATTRIBUTE_PROPERTY, props.getProperty("ATTRIBUTE_PROPERTY"));
        Ex.MEASURE_PROPERTY = Configuration.getProperty(Ex.MEASURE_PROPERTY, props.getProperty("MEASURE_PROPERTY"));
        
        Ex.COLUMN_NAME = Configuration.getProperty(Ex.COLUMN_NAME, props.getProperty("COLUMN_NAME"));
        Ex.COLUMN_NAME_MAX_LENGTH = Configuration.getProperty(Ex.COLUMN_NAME_MAX_LENGTH, props.getProperty("COLUMN_NAME_MAX_LENGTH"));
        Ex.COLUMN_DESCRIPTION = Configuration.getProperty(Ex.COLUMN_DESCRIPTION, props.getProperty("COLUMN_DESCRIPTION"));
        Ex.COLUMN_TYPE = Configuration.getProperty(Ex.COLUMN_TYPE, props.getProperty("COLUMN_TYPE"));
        Ex.COLUMN_DB_TYPE = Configuration.getProperty(Ex.COLUMN_DB_TYPE, props.getProperty("COLUMN_DB_TYPE"));
        Ex.COLUMN_FORMAT = Configuration.getProperty(Ex.COLUMN_FORMAT, props.getProperty("COLUMN_FORMAT"));
        Ex.COLUMN_AGGR_RULE = Configuration.getProperty(Ex.COLUMN_AGGR_RULE, props.getProperty("COLUMN_AGGR_RULE"));
        
        Ex.COLUMN_SHEET = Configuration.getProperty(Ex.COLUMN_SHEET, props.getProperty("COLUMN_SHEET"));
        Ex.COLUMN_DATA_GROUP = Configuration.getProperty(Ex.COLUMN_DATA_GROUP, props.getProperty("COLUMN_DATA_GROUP"));
        Ex.COLUMN_START_ROW = Configuration.getProperty(Ex.COLUMN_START_ROW, props.getProperty("COLUMN_START_ROW"));
        Ex.COLUMN_POSITION_IN_GROUP = Configuration.getProperty(Ex.COLUMN_POSITION_IN_GROUP, props.getProperty("COLUMN_POSITION_IN_GROUP"));
        Ex.COLUMN_PARENT_POSITION = Configuration.getProperty(Ex.COLUMN_PARENT_POSITION, props.getProperty("COLUMN_PARENT_POSITION"));
        Ex.COLUMN_PIVOT_ROW_POSITION = Configuration.getProperty(Ex.COLUMN_PIVOT_ROW_POSITION, props.getProperty("COLUMN_PIVOT_ROW_POSITION"));
        Ex.COLUMN_COMPLEX_GROUP = Configuration.getProperty(Ex.COLUMN_COMPLEX_GROUP, props.getProperty("COLUMN_COMPLEX_GROUP"));
        
        Ex.COLUMN_UNIQUE_CODE = Configuration.getProperty(Ex.COLUMN_UNIQUE_CODE, props.getProperty("COLUMN_UNIQUE_CODE"));
        
        Ex.COLUMNS_INFO = Configuration.getProperty(Ex.COLUMNS_INFO, props.getProperty("COLUMNS_INFO"));
        Ex.CELLS = Configuration.getProperty(Ex.CELLS, props.getProperty("CELLS"));
        
        Ex.STRING_EXCEL_TYPE = Configuration.getProperty(Ex.STRING_EXCEL_TYPE, props.getProperty("STRING_EXCEL_TYPE"));
        Ex.STRING_TYPE = Configuration.getProperty(Ex.STRING_TYPE, props.getProperty("STRING_TYPE"));
        Ex.STRING_FORMAT_TYPE = Configuration.getProperty(Ex.STRING_FORMAT_TYPE, props.getProperty("STRING_FORMAT_TYPE"));

        Ex.DATE_TYPE = Configuration.getProperty(Ex.DATE_TYPE, props.getProperty("DATE_TYPE"));

        Ex.NUMBER_TYPE = Configuration.getProperty(Ex.NUMBER_TYPE, props.getProperty("NUMBER_TYPE"));
        Ex.NUMBER_DOUBLE_MASK = Configuration.getProperty(Ex.NUMBER_DOUBLE_MASK, props.getProperty("NUMBER_DOUBLE_MASK"));
        Ex.NUMBER_INT_MASK = Configuration.getProperty(Ex.NUMBER_INT_MASK, props.getProperty("NUMBER_INT_MASK"));
        Ex.NUMBER_DOUBLE_TYPE = Configuration.getProperty(Ex.NUMBER_DOUBLE_TYPE, props.getProperty("NUMBER_DOUBLE_TYPE"));
        Ex.NUMBER_INT_TYPE = Configuration.getProperty(Ex.NUMBER_INT_TYPE, props.getProperty("NUMBER_INT_TYPE"));

        Ex.AGGR_MAX_TYPE = Configuration.getProperty(Ex.AGGR_MAX_TYPE, props.getProperty("AGGR_MAX_TYPE"));
        Ex.AGGR_MIN_TYPE = Configuration.getProperty(Ex.AGGR_MIN_TYPE, props.getProperty("AGGR_MIN_TYPE"));
        Ex.AGGR_AVG_TYPE = Configuration.getProperty(Ex.AGGR_AVG_TYPE, props.getProperty("AGGR_AVG_TYPE"));
        Ex.AGGR_SUM_TYPE = Configuration.getProperty(Ex.AGGR_SUM_TYPE, props.getProperty("AGGR_SUM_TYPE"));
        Ex.AGGR_CNT_TYPE = Configuration.getProperty(Ex.AGGR_CNT_TYPE, props.getProperty("AGGR_CNT_TYPE"));
        
        Ex.UNKNOWN_STRING = Configuration.getProperty(Ex.UNKNOWN_STRING, props.getProperty("UNKNOWN_STRING"));
        Ex.UNKNOWN_NUMBER = Configuration.getProperty(Ex.UNKNOWN_NUMBER, props.getProperty("UNKNOWN_NUMBER"));
        
        // @isadikov: load xml tags
        Ex.XML_GROUPS_TAG = Configuration.getProperty(Ex.XML_GROUPS_TAG, props.getProperty("XML_GROUPS_TAG"));
        
        Ex.XML_GROUP_TAG = Configuration.getProperty(Ex.XML_GROUP_TAG, props.getProperty("XML_GROUP_TAG"));
        Ex.XML_GROUP_NAME_ATTR = Configuration.getProperty(Ex.XML_GROUP_NAME_ATTR, props.getProperty("XML_GROUP_NAME_ATTR"));
        Ex.XML_GROUP_IS_PIVOT_ATTR = Configuration.getProperty(Ex.XML_GROUP_IS_PIVOT_ATTR, props.getProperty("XML_GROUP_IS_PIVOT_ATTR"));
        Ex.XML_GROUP_MAX_ROWS_ATTR = Configuration.getProperty(Ex.XML_GROUP_MAX_ROWS_ATTR, props.getProperty("XML_GROUP_MAX_ROWS_ATTR"));
        Ex.XML_GROUP_MIN_START_ROW_ATTR = Configuration.getProperty(Ex.XML_GROUP_MIN_START_ROW_ATTR, props.getProperty("XML_GROUP_MIN_START_ROW_ATTR"));
        Ex.XML_GROUP_SHEET_ATTR = Configuration.getProperty(Ex.XML_GROUP_SHEET_ATTR, props.getProperty("XML_GROUP_SHEET_ATTR"));
        
        Ex.XML_COLUMN_TAG = Configuration.getProperty(Ex.XML_COLUMN_TAG, props.getProperty("XML_COLUMN_TAG"));
        Ex.XML_COLUMN_NAME_ATTR = Configuration.getProperty(Ex.XML_COLUMN_NAME_ATTR, props.getProperty("XML_COLUMN_NAME_ATTR"));
        Ex.XML_COLUMN_DESC_ATTR = Configuration.getProperty(Ex.XML_COLUMN_DESC_ATTR, props.getProperty("XML_COLUMN_DESC_ATTR"));
        Ex.XML_COLUMN_CTYPE_ATTR = Configuration.getProperty(Ex.XML_COLUMN_CTYPE_ATTR, props.getProperty("XML_COLUMN_CTYPE_ATTR"));
        Ex.XML_COLUMN_AGGTYPE_ATTR = Configuration.getProperty(Ex.XML_COLUMN_AGGTYPE_ATTR, props.getProperty("XML_COLUMN_AGGTYPE_ATTR"));
        Ex.XML_COLUMN_COLUMN_POS_ATTR = Configuration.getProperty(Ex.XML_COLUMN_COLUMN_POS_ATTR, props.getProperty("XML_COLUMN_COLUMN_POS_ATTR"));
        Ex.XML_COLUMN_PARENT_POS_ATTR = Configuration.getProperty(Ex.XML_COLUMN_PARENT_POS_ATTR, props.getProperty("XML_COLUMN_PARENT_POS_ATTR"));
        Ex.XML_COLUMN_ABS_COLUMN_POS_ATTR = Configuration.getProperty(Ex.XML_COLUMN_ABS_COLUMN_POS_ATTR, props.getProperty("XML_COLUMN_ABS_COLUMN_POS_ATTR"));
        Ex.XML_COLUMN_PIVOT_POS_ATTR = Configuration.getProperty(Ex.XML_COLUMN_PIVOT_POS_ATTR, props.getProperty("XML_COLUMN_PIVOT_POS_ATTR"));
        Ex.XML_COLUMN_PIVOT_ROW_POS_ATTR = Configuration.getProperty(Ex.XML_COLUMN_PIVOT_ROW_POS_ATTR, props.getProperty("XML_COLUMN_PIVOT_ROW_POS_ATTR"));
        Ex.XML_COLUMN_DIM_ATTR = Configuration.getProperty(Ex.XML_COLUMN_DIM_ATTR, props.getProperty("XML_COLUMN_DIM_ATTR"));
        Ex.XML_COLUMN_TYPE_ATTR = Configuration.getProperty(Ex.XML_COLUMN_TYPE_ATTR, props.getProperty("XML_COLUMN_TYPE_ATTR"));
        Ex.XML_COLUMN_FORMAT_ATTR = Configuration.getProperty(Ex.XML_COLUMN_FORMAT_ATTR, props.getProperty("XML_COLUMN_FORMAT_ATTR"));
        Ex.XML_COLUMN_UNIQUE_CODE_ATTR = Configuration.getProperty(Ex.XML_COLUMN_UNIQUE_CODE_ATTR, props.getProperty("XML_COLUMN_UNIQUE_CODE_ATTR"));
        Ex.XML_COLUMN_ABS_GROUP_POS_ATTR = Configuration.getProperty(Ex.XML_COLUMN_ABS_GROUP_POS_ATTR, props.getProperty("XML_COLUMN_ABS_GROUP_POS_ATTR"));
        
        Ex.XML_ROW_TAG = Configuration.getProperty(Ex.XML_ROW_TAG, props.getProperty("XML_ROW_TAG"));
        Ex.XML_ROW_NUMBER_ATTR = Configuration.getProperty(Ex.XML_ROW_NUMBER_ATTR, props.getProperty("XML_ROW_NUMBER_ATTR"));
        
        Ex.XML_VALUE_TAG = Configuration.getProperty(Ex.XML_VALUE_TAG, props.getProperty("XML_VALUE_TAG"));
        Ex.XML_VALUE_NAME_ATTR = Configuration.getProperty(Ex.XML_VALUE_NAME_ATTR, props.getProperty("XML_VALUE_NAME_ATTR"));
        Ex.XML_VALUE_ABS_POS_ATTR = Configuration.getProperty(Ex.XML_VALUE_ABS_POS_ATTR, props.getProperty("XML_VALUE_ABS_POS_ATTR"));
    }
    
    private static void loadDBProperties(Properties props) {
        Db.DB_SCHEME_NAME = Configuration.getProperty(Db.DB_SCHEME_NAME, props.getProperty("DB_SCHEME_NAME"));
        Db.DB_SCHEME_PASSWORD = Configuration.getProperty(Db.DB_SCHEME_PASSWORD, props.getProperty("DB_SCHEME_PASSWORD"));
        
        Db.DB_SOURCE_HOST = Configuration.getProperty(Db.DB_SOURCE_HOST, props.getProperty("DB_SOURCE_HOST"));
        Db.DB_SOURCE_PORT = Configuration.getProperty(Db.DB_SOURCE_PORT, props.getProperty("DB_SOURCE_PORT"));
        Db.DB_SOURCE_SERVICE_NAME = Configuration.getProperty(Db.DB_SOURCE_SERVICE_NAME, props.getProperty("DB_SOURCE_SERVICE_NAME"));
        
        // @depricated
        //Db.DB_SOURCE = Configuration.getProperty(Db.DB_SOURCE, props.getProperty("DB_SOURCE"));
        //Db.DB_DATASOURCE = Configuration.getProperty(Db.DB_DATASOURCE, props.getProperty("DB_DATASOURCE"));

        Db.JNDI_PATH = Configuration.getProperty(Db.JNDI_PATH, props.getProperty("JNDI_PATH"));;
        Db.JNDI_ENV = Configuration.getProperty(Db.JNDI_ENV, props.getProperty("JNDI_ENV"));;
        
        Db.DB_STRUCTURE_PACKAGE = Configuration.getProperty(Db.DB_STRUCTURE_PACKAGE, props.getProperty("DB_STRUCTURE_PACKAGE"));
        Db.DB_FNC_CREATE_CUBE = Configuration.getProperty(Db.DB_FNC_CREATE_CUBE, props.getProperty("DB_FNC_CREATE_CUBE"));
        Db.DB_FNC_GET_PM_NAMES = Configuration.getProperty(Db.DB_FNC_GET_PM_NAMES, props.getProperty("DB_FNC_GET_PM_NAMES"));
        Db.DB_FNC_GET_MODEL_STRUCTURE = Configuration.getProperty(Db.DB_FNC_GET_MODEL_STRUCTURE, props.getProperty("DB_FNC_GET_MODEL_STRUCTURE"));
        Db.DB_CC_LOAD_PACKAGE = Configuration.getProperty(Db.DB_CC_LOAD_PACKAGE, props.getProperty("DB_CC_LOAD_PACKAGE"));
        Db.DB_FNC_LOAD_CUBE = Configuration.getProperty(Db.DB_FNC_LOAD_CUBE, props.getProperty("DB_FNC_LOAD_CUBE"));
        
        Db.DB_SEQ_PRC_ID_NEXTVAL = Configuration.getProperty(Db.DB_SEQ_PRC_ID_NEXTVAL, props.getProperty("DB_SEQ_PRC_ID_NEXTVAL"));
        Db.DB_SEQ_PRC_ID_CURRVAL = Configuration.getProperty(Db.DB_SEQ_PRC_ID_CURRVAL, props.getProperty("DB_SEQ_PRC_ID_CURRVAL"));

        Db.DB_AUTO_COMMIT = Configuration.getProperty(Db.DB_AUTO_COMMIT, props.getProperty("DB_AUTO_COMMIT"));
    }
    
    private static void loadREProperties(Properties props) {
        Re.BIN_DIRECTORY = Configuration.getProperty(Re.BIN_DIRECTORY, props.getProperty("BIN_DIRECTORY"));
        
        Re.SCRIPT_DIRECTORY = Configuration.getProperty(Re.SCRIPT_DIRECTORY, props.getProperty("SCRIPT_DIRECTORY"));
        
        Re.WORK_DIRECTORY = Configuration.getProperty(Re.WORK_DIRECTORY, props.getProperty("WORK_DIRECTORY"));
        
        Re.BI_GENERATE_XML = Configuration.getProperty(Re.BI_GENERATE_XML, props.getProperty("BI_GENERATE_XML"));
        Re.BI_EXECUTE_XML = Configuration.getProperty(Re.BI_EXECUTE_XML, props.getProperty("BI_EXECUTE_XML"));
        Re.BI_EXECUTE_CLI = Configuration.getProperty(Re.BI_EXECUTE_CLI, props.getProperty("BI_EXECUTE_CLI"));
        
        Re.BI_WIN_FILENAME = Configuration.getProperty(Re.BI_WIN_FILENAME, props.getProperty("BI_WIN_FILENAME"));
        Re.BI_UNIX_FILENAME = Configuration.getProperty(Re.BI_UNIX_FILENAME, props.getProperty("BI_UNIX_FILENAME"));
        
        Re.RPD_DECLARE_FILENAME = Configuration.getProperty(Re.RPD_DECLARE_FILENAME, props.getProperty("RPD_DECLARE_FILENAME"));
        Re.RPD_DELETE_FILENAME = Configuration.getProperty(Re.RPD_DELETE_FILENAME, props.getProperty("RPD_DELETE_FILENAME"));
        Re.SCRIPT_DECLARE_FILENAME = Configuration.getProperty(Re.SCRIPT_DECLARE_FILENAME, props.getProperty("SCRIPT_DECLARE_FILENAME"));
        Re.SCRIPT_DELETE_FILENAME = Configuration.getProperty(Re.SCRIPT_DELETE_FILENAME, props.getProperty("SCRIPT_DELETE_FILENAME"));
        
        Re.RPD_NAME = Configuration.getProperty(Re.RPD_NAME, props.getProperty("RPD_NAME"));
        Re.RPD_PASSWORD = Configuration.getProperty(Re.RPD_PASSWORD, props.getProperty("RPD_PASSWORD"));
        
        Re.USER_NAME = Configuration.getProperty(Re.USER_NAME, props.getProperty("USER_NAME"));
        Re.USER_PASSWORD = Configuration.getProperty(Re.USER_PASSWORD, props.getProperty("USER_PASSWORD"));
        
        Re.SERVER_DSN = Configuration.getProperty(Re.SERVER_DSN, props.getProperty("SERVER_DSN"));
        
        Re.CL_DELETE_SH_NAME = Configuration.getProperty(Re.CL_DELETE_SH_NAME, props.getProperty("CL_DELETE_SH_NAME"));
        Re.CL_DELETE_XML_NAME = Configuration.getProperty(Re.CL_DELETE_XML_NAME, props.getProperty("CL_DELETE_XML_NAME"));
        Re.CL_CREATE_SH_NAME = Configuration.getProperty(Re.CL_CREATE_SH_NAME, props.getProperty("CL_CREATE_SH_NAME"));
        Re.CL_CREATE_XML_NAME = Configuration.getProperty(Re.CL_CREATE_XML_NAME, props.getProperty("CL_CREATE_XML_NAME"));
        
        Re.RPD_USERNAME_VARIABLE = Configuration.getProperty(Re.RPD_USERNAME_VARIABLE, props.getProperty("RPD_USERNAME_VARIABLE"));
        Re.RPD_PASSWORD_VARIABLE = Configuration.getProperty(Re.RPD_PASSWORD_VARIABLE, props.getProperty("RPD_PASSWORD_VARIABLE"));
        Re.CP_PASSWORD_ENCRYPTED = Configuration.getProperty(Re.CP_PASSWORD_ENCRYPTED, props.getProperty("CP_PASSWORD_ENCRYPTED"));
        
        Re.SHOW_FULL_STRUCTURE = Configuration.getProperty(Re.SHOW_FULL_STRUCTURE, props.getProperty("SHOW_FULL_STRUCTURE"));
        
        Re.SUBJECT_AREA_LOCAL_GROUP_NAME = Configuration.getProperty(Re.SUBJECT_AREA_LOCAL_GROUP_NAME, props.getProperty("SUBJECT_AREA_LOCAL_GROUP_NAME"));
        Re.SUBJECT_AREA_LOCAL_COMMON_GROUP_NAME = Configuration.getProperty(Re.SUBJECT_AREA_LOCAL_COMMON_GROUP_NAME, props.getProperty("SUBJECT_AREA_LOCAL_COMMON_GROUP_NAME"));
        Re.SHOW_GROUP_FOLDERS = Configuration.getProperty(Re.SHOW_GROUP_FOLDERS, props.getProperty("SHOW_GROUP_FOLDERS"));
        
        Re.TABLE_TYPE_FACT = Configuration.getProperty(Re.TABLE_TYPE_FACT, props.getProperty("TABLE_TYPE_FACT"));
        Re.TABLE_TYPE_DIM = Configuration.getProperty(Re.TABLE_TYPE_DIM, props.getProperty("TABLE_TYPE_DIM"));
        Re.TABLE_TYPE_PARENT_DIM = Configuration.getProperty(Re.TABLE_TYPE_PARENT_DIM, props.getProperty("TABLE_TYPE_PARENT_DIM"));
        Re.TABLE_TYPE_DUMMY_FACT = Configuration.getProperty(Re.TABLE_TYPE_DUMMY_FACT, props.getProperty("TABLE_TYPE_DUMMY_FACT"));
        Re.TABLE_TYPE_DUMMY_DIM = Configuration.getProperty(Re.TABLE_TYPE_DUMMY_DIM, props.getProperty("TABLE_TYPE_DUMMY_DIM"));
        Re.TABLE_TYPE_PIVOT_DIM = Configuration.getProperty(Re.TABLE_TYPE_PIVOT_DIM, props.getProperty("TABLE_TYPE_PIVOT_DIM"));
        
        Re.TD_CUBE_TAG = Configuration.getProperty(Re.TD_CUBE_TAG, props.getProperty("TD_CUBE_TAG"));
        Re.TD_TABLES_TAG = Configuration.getProperty(Re.TD_TABLES_TAG, props.getProperty("TD_TABLES_TAG"));
        Re.TD_TABLE_TAG = Configuration.getProperty(Re.TD_TABLE_TAG, props.getProperty("TD_TABLE_TAG"));
        Re.TD_COLUMNS_TAG = Configuration.getProperty(Re.TD_COLUMNS_TAG, props.getProperty("TD_COLUMNS_TAG"));
        Re.TD_COLUMN_TAG = Configuration.getProperty(Re.TD_COLUMN_TAG, props.getProperty("TD_COLUMN_TAG"));
        
        Re.TD_CUBE_NAME_ATTR = Configuration.getProperty(Re.TD_CUBE_NAME_ATTR, props.getProperty("TD_CUBE_NAME_ATTR"));
        Re.TD_CUBE_DESC_ATTR = Configuration.getProperty(Re.TD_CUBE_DESC_ATTR, props.getProperty("TD_CUBE_DESC_ATTR"));
        Re.TD_CUBE_NEW_MODEL_ATTR = Configuration.getProperty(Re.TD_CUBE_NEW_MODEL_ATTR, props.getProperty("TD_CUBE_NEW_MODEL_ATTR"));
        
        Re.TD_TABLE_NAME_ATTR = Configuration.getProperty(Re.TD_TABLE_NAME_ATTR, props.getProperty("TD_TABLE_NAME_ATTR"));
        Re.TD_TABLE_DIM_ATTR = Configuration.getProperty(Re.TD_TABLE_DIM_ATTR, props.getProperty("TD_TABLE_DIM_ATTR"));
        Re.TD_TABLE_DESC_ATTR = Configuration.getProperty(Re.TD_TABLE_DESC_ATTR, props.getProperty("TD_TABLE_DESC_ATTR"));
        
        Re.TD_COLUMN_NAME_ATTR = Configuration.getProperty(Re.TD_COLUMN_NAME_ATTR, props.getProperty("TD_COLUMN_NAME_ATTR"));
        Re.TD_COLUMN_TYPE_ATTR = Configuration.getProperty(Re.TD_COLUMN_TYPE_ATTR, props.getProperty("TD_COLUMN_TYPE_ATTR"));
        Re.TD_COLUMN_DESC_ATTR = Configuration.getProperty(Re.TD_COLUMN_DESC_ATTR, props.getProperty("TD_COLUMN_DESC_ATTR"));
        Re.TD_COLUMN_ISPRIMARYKEY_ATTR = Configuration.getProperty(Re.TD_COLUMN_ISPRIMARYKEY_ATTR, props.getProperty("TD_COLUMN_ISPRIMARYKEY_ATTR"));
        Re.TD_COLUMN_ISFOREIGNKEY_ATTR = Configuration.getProperty(Re.TD_COLUMN_ISFOREIGNKEY_ATTR, props.getProperty("TD_COLUMN_ISFOREIGNKEY_ATTR"));
        Re.TD_COLUMN_REF_TABLENAME_ATTR = Configuration.getProperty(Re.TD_COLUMN_REF_TABLENAME_ATTR, props.getProperty("TD_COLUMN_REF_TABLENAME_ATTR"));
        Re.TD_COLUMN_AGGR_TYPE_ATTR = Configuration.getProperty(Re.TD_COLUMN_AGGR_TYPE_ATTR, props.getProperty("TD_COLUMN_AGGR_TYPE_ATTR"));
        
        Re.USE_WEB_SERVICES_AGAINST_SHELL = Configuration.getProperty(Re.USE_WEB_SERVICES_AGAINST_SHELL, props.getProperty("USE_WEB_SERVICES_AGAINST_SHELL"));
        Re.SAVE_METADATA_XML_SCRIPTS_ON_SERVER = Configuration.getProperty(Re.SAVE_METADATA_XML_SCRIPTS_ON_SERVER, props.getProperty("SAVE_METADATA_XML_SCRIPTS_ON_SERVER"));
        Re.CREATE_VARIABLES_ON_APP_DEPLOY = Configuration.getProperty(Re.CREATE_VARIABLES_ON_APP_DEPLOY, props.getProperty("CREATE_VARIABLES_ON_APP_DEPLOY"));
    }
    
    private static void loadMSGProperties(Properties props) {
        Msg.KMSG_SUCCESS_MESSAGE = Configuration.getProperty(Msg.KMSG_SUCCESS_MESSAGE, props.getProperty("KMSG_SUCCESS_MESSAGE"));
        Msg.KMSG_SUCCESS_MESSAGE_LOCAL  = Configuration.getProperty(Msg.KMSG_SUCCESS_MESSAGE_LOCAL, props.getProperty("KMSG_SUCCESS_MESSAGE_LOCAL"));
        Msg.KMSG_SUCCESS_MESSAGE_LOAD_ONLY_DATA  = Configuration.getProperty(Msg.KMSG_SUCCESS_MESSAGE_LOAD_ONLY_DATA, props.getProperty("KMSG_SUCCESS_MESSAGE_LOAD_ONLY_DATA"));
        
        Msg.KMSG_ERROR_DEFAULT_MSG = Configuration.getProperty(Msg.KMSG_ERROR_DEFAULT_MSG, props.getProperty("KMSG_ERROR_DEFAULT_MSG"));
        
        Msg.KMSG_ERROR_REQUEST_IS_EMPTY = Configuration.getProperty(Msg.KMSG_ERROR_REQUEST_IS_EMPTY, props.getProperty("KMSG_ERROR_REQUEST_IS_EMPTY"));
        Msg.KMSG_ERROR_GET_METHOD_IS_FORBID = Configuration.getProperty(Msg.KMSG_ERROR_GET_METHOD_IS_FORBID, props.getProperty("KMSG_ERROR_GET_METHOD_IS_FORBID"));
        Msg.KMSG_ERROR_DB_CONNECTION_UNAVAILABLE = Configuration.getProperty(Msg.KMSG_ERROR_DB_CONNECTION_UNAVAILABLE, props.getProperty("KMSG_ERROR_DB_CONNECTION_UNAVAILABLE"));
        Msg.KMSG_ERROR_USER_HAS_NO_ACCESS = Configuration.getProperty(Msg.KMSG_ERROR_USER_HAS_NO_ACCESS, props.getProperty("KMSG_ERROR_USER_HAS_NO_ACCESS"));
        
        Msg.KMSG_ERROR_UPLOAD_NAME_EMPTY = Configuration.getProperty(Msg.KMSG_ERROR_UPLOAD_NAME_EMPTY, props.getProperty("KMSG_ERROR_UPLOAD_NAME_EMPTY"));
        Msg.KMSG_ERROR_UPLOAD_NAME_NOT_ALLOWED_CHARACTERS = Configuration.getProperty(Msg.KMSG_ERROR_UPLOAD_NAME_NOT_ALLOWED_CHARACTERS, props.getProperty("KMSG_ERROR_UPLOAD_NAME_NOT_ALLOWED_CHARACTERS"));
        
        Msg.KMSG_ERROR_UKNOWN_ACTION_FOR_INTERFACE_CALL = Configuration.getProperty(Msg.KMSG_ERROR_UKNOWN_ACTION_FOR_INTERFACE_CALL, props.getProperty("KMSG_ERROR_UKNOWN_ACTION_FOR_INTERFACE_CALL"));
        
        Msg.KMSG_ERROR_PM_NAME_INPUT_AND_SELECT_EMPTY = Configuration.getProperty(Msg.KMSG_ERROR_PM_NAME_INPUT_AND_SELECT_EMPTY, props.getProperty("KMSG_ERROR_PM_NAME_INPUT_AND_SELECT_EMPTY"));
        Msg.KMSG_ERROR_PM_NAME_SELECT_IS_EMPTY = Configuration.getProperty(Msg.KMSG_ERROR_PM_NAME_SELECT_IS_EMPTY, props.getProperty("KMSG_ERROR_PM_NAME_SELECT_IS_EMPTY"));
        
        Msg.KMSG_ERROR_PM_NAME_INPUT_IS_EMPTY = Configuration.getProperty(Msg.KMSG_ERROR_PM_NAME_INPUT_IS_EMPTY, props.getProperty("KMSG_ERROR_PM_NAME_INPUT_IS_EMPTY"));
        Msg.KMSG_ERROR_PM_NAME_NOT_ALLOWED_CHARACTERS = Configuration.getProperty(Msg.KMSG_ERROR_PM_NAME_NOT_ALLOWED_CHARACTERS, props.getProperty("KMSG_ERROR_PM_NAME_NOT_ALLOWED_CHARACTERS"));
        
        Msg.KMSG_ERROR_PM_NAME_ALREADY_EXISTS = Configuration.getProperty(Msg.KMSG_ERROR_PM_NAME_ALREADY_EXISTS, props.getProperty("KMSG_ERROR_PM_NAME_ALREADY_EXISTS"));
        
        Msg.KMSG_ERROR_EXCEL_FILE_IS_NOT_VALID = Configuration.getProperty(Msg.KMSG_ERROR_EXCEL_FILE_IS_NOT_VALID, props.getProperty("KMSG_ERROR_EXCEL_FILE_IS_NOT_VALID"));
        
        Msg.KMSG_ERROR_LOAD_ONLY_DATA_FORBIDDEN = Configuration.getProperty(Msg.KMSG_ERROR_LOAD_ONLY_DATA_FORBIDDEN, props.getProperty("KMSG_ERROR_LOAD_ONLY_DATA_FORBIDDEN"));
        
        Msg.KMSG_ERROR_NO_AVAILABLE_MODEL = Configuration.getProperty(Msg.KMSG_ERROR_NO_AVAILABLE_MODEL, props.getProperty("KMSG_ERROR_NO_AVAILABLE_MODEL"));
        
        Msg.KMSG_ERROR_DESCRIPTION_INCORRECT = Configuration.getProperty(Msg.KMSG_ERROR_DESCRIPTION_INCORRECT, props.getProperty("KMSG_ERROR_DESCRIPTION_INCORRECT"));
        
        Msg.KMSG_ERROR_EXCEL_CONFIGURATION_MISSED = Configuration.getProperty(Msg.KMSG_ERROR_EXCEL_CONFIGURATION_MISSED, props.getProperty("KMSG_ERROR_EXCEL_CONFIGURATION_MISSED"));
        
        Msg.KMSG_ERROR_EXCEL_SHEET_DECLARED_BUT_NOT_EXISTED = Configuration.getProperty(Msg.KMSG_ERROR_EXCEL_SHEET_DECLARED_BUT_NOT_EXISTED, props.getProperty("KMSG_ERROR_EXCEL_SHEET_DECLARED_BUT_NOT_EXISTED"));
        
        Msg.KMSG_ERROR_EXCEL_CS_COLUMNS_LIST_EMPTY = Configuration.getProperty(Msg.KMSG_ERROR_EXCEL_CS_COLUMNS_LIST_EMPTY, props.getProperty("KMSG_ERROR_EXCEL_CS_COLUMNS_LIST_EMPTY"));
        Msg.KMSG_ERROR_EXCEL_CS_ATTRIBUTE_MISSED = Configuration.getProperty(Msg.KMSG_ERROR_EXCEL_CS_ATTRIBUTE_MISSED, props.getProperty("KMSG_ERROR_EXCEL_CS_ATTRIBUTE_MISSED"));
        Msg.KMSG_ERROR_EXCEL_CS_COLUMN_TYPE_UNDEFINED = Configuration.getProperty(Msg.KMSG_ERROR_EXCEL_CS_COLUMN_TYPE_UNDEFINED, props.getProperty("KMSG_ERROR_EXCEL_CS_COLUMN_TYPE_UNDEFINED"));
        Msg.KMSG_ERROR_EXCEL_CS_COLUMN_SHEET_UNKNOWN = Configuration.getProperty(Msg.KMSG_ERROR_EXCEL_CS_COLUMN_SHEET_UNKNOWN, props.getProperty("KMSG_ERROR_EXCEL_CS_COLUMN_SHEET_UNKNOWN"));
        Msg.KMSG_ERROR_EXCEL_CS_COLUMN_GROUP_INCORRECT = Configuration.getProperty(Msg.KMSG_ERROR_EXCEL_CS_COLUMN_GROUP_INCORRECT, props.getProperty("KMSG_ERROR_EXCEL_CS_COLUMN_GROUP_INCORRECT"));
        Msg.KMSG_ERROR_EXCEL_CS_COLUMN_POSITION_INCORRECT = Configuration.getProperty(Msg.KMSG_ERROR_EXCEL_CS_COLUMN_POSITION_INCORRECT, props.getProperty("KMSG_ERROR_EXCEL_CS_COLUMN_POSITION_INCORRECT"));
        Msg.KMSG_ERROR_EXCEL_CS_COLUMN_PARENT_POS_INCORRECT = Configuration.getProperty(Msg.KMSG_ERROR_EXCEL_CS_COLUMN_PARENT_POS_INCORRECT, props.getProperty("KMSG_ERROR_EXCEL_CS_COLUMN_PARENT_POS_INCORRECT"));
        Msg.KMSG_ERROR_EXCEL_CS_COLUMN_START_ROW_INCORRECT = Configuration.getProperty(Msg.KMSG_ERROR_EXCEL_CS_COLUMN_START_ROW_INCORRECT, props.getProperty("KMSG_ERROR_EXCEL_CS_COLUMN_START_ROW_INCORRECT"));
        Msg.KMSG_ERROR_EXCEL_CS_COLUMN_AGG_RULE_INCORRECT = Configuration.getProperty(Msg.KMSG_ERROR_EXCEL_CS_COLUMN_AGG_RULE_INCORRECT, props.getProperty("KMSG_ERROR_EXCEL_CS_COLUMN_AGG_RULE_INCORRECT"));
    }
    
    public static void loadProperties(Properties props) {
        // here we load properties
        // 1. PM properties
        // 2. EX properties
        // 3. DB properties
        // 4. RE properties
        // 5. MSG properties
        loadPMProperties(props);
        loadEXProperties(props);
        loadDBProperties(props);
        loadREProperties(props);
        loadMSGProperties(props);
    }
    
    public static void loadConfiguration(String filepath) {
        try {
            Properties props;
            props = Configuration.getPropertiesInstance(filepath);
            
            Configuration.loadProperties(props);
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
