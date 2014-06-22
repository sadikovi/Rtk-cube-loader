package sitronics.data.utils.consts;

public class Re {
    /* directories */
    //bin directory where the execute commands are
    public static String BIN_DIRECTORY = "/u01/oracle/weblogic/Oracle_BI1/bifoundation/server/bin/";
    //script directory where the init files are
    public static String SCRIPT_DIRECTORY = "/u01/oracle/weblogic/instances/instance1/bifoundation/OracleBIApplication/coreapplication/setup/";
    //work directory where the changes files will be created and executed
    public static String WORK_DIRECTORY = "/u01/oracle/weblogic/user_projects/domains/bifoundation_domain/servers/AdminServer/upload/";
    
    //commands
    public static String BI_GENERATE_XML = "biserverxmlgen";
    public static String BI_EXECUTE_XML = "biserverxmlexec";
    public static String BI_EXECUTE_CLI = "biserverxmlcli";
    
    //init files names
    public static String BI_WIN_FILENAME = "bi-init.cmd";
    public static String BI_UNIX_FILENAME = "bi-init.sh";
    
    //rpd changes files names
    public static String RPD_DECLARE_FILENAME = "changes-init.xml";
    public static String RPD_DELETE_FILENAME = "delete-init.xml";
    public static String SCRIPT_DECLARE_FILENAME = "changes-init.sh";
    public static String SCRIPT_DELETE_FILENAME = "delete-init.sh";
    
    //rpd name and password
    public static String RPD_NAME = "sample.rpd";
    public static String RPD_PASSWORD = "Admin123";
    
    //user name and password who can access to repository
    public static String USER_NAME = "sadikov.ivan";
    public static String USER_PASSWORD = "Cdbf53xd";
    
    public static String SERVER_DSN = "rtk-orabits-server";
    
    //connection pool data
    //rpd global vairables which are consist user name and password for scheme
    //can not be changed!!!
    public static String CL_DELETE_SH_NAME = "cube-loader-start-d.sh";
    public static String CL_DELETE_XML_NAME = "cube-loader-start-d.xml";
    public static String CL_CREATE_SH_NAME = "cube-loader-start-c.sh";
    public static String CL_CREATE_XML_NAME = "cube-loader-start-c.xml";
    public static String RPD_USERNAME_VARIABLE = "V_CUBE_LOADER_USERNAME";
    public static String RPD_PASSWORD_VARIABLE = "V_CUBE_LOADER_PASSWORD";
    public static String CP_PASSWORD_ENCRYPTED = "6C6F0BE01FC4111F3AC2236A364D912E9B70195D55A2B76F203F3DDAF9C76F07A5A84F9E5950E70BE9EB233DB98B5E8811898CB172E34CEBBC2932D28B877CFF073A76AC1FB5902F82366F77E589343F7C4188431C4EDF2A6FD0051F24042B8F 71CAF7D0743E6F99F917DD2C7D3C68B12734EB52F1C6816DE8C35FE0463B71F2";
    
    //show full structure flag
    public static boolean SHOW_FULL_STRUCTURE = false;
    
    // group folders
    // local name for group as it will be displayed in a subject area
    public static String SUBJECT_AREA_LOCAL_GROUP_NAME = "Group";
    // local name for common group which includes dimensions connected with different groups
    public static String SUBJECT_AREA_LOCAL_COMMON_GROUP_NAME = "Common (Connects more than 2 Groups)";
    //show group folders in presentation level
    // if it is true then all the tabels wiil be sorted by groups 
    // and put into group folders they are related to
    public static boolean SHOW_GROUP_FOLDERS = true;
    
    //!!! tables attribute "dim" types
    // it is a fact table
    public static String TABLE_TYPE_FACT = "fact";
    // it is a dim table
    public static String TABLE_TYPE_DIM = "dim";
    // it is a parent dim table with reference to the dim
    public static String TABLE_TYPE_PARENT_DIM = "parent_dim";
    // it is a dummy fact table which we should not display at the presentation level in RPD
    public static String TABLE_TYPE_DUMMY_FACT = "dummy_fact";
    // it is a dummy dim table which we should not display at the presentation level in RPD
    public static String TABLE_TYPE_DUMMY_DIM = "dummy_dim";
    // it is a dummy dim table which we should not display at the presentation level in RPD
    public static String TABLE_TYPE_PIVOT_DIM = "pivot_dim";
    
    // it is a dummy tables array. If there are one more dummy tables besides dummy_fact and dummy_dim
    // we should add them to the array
    public static String[] DUMMY_TABLES_ARRAY = {TABLE_TYPE_DUMMY_FACT, TABLE_TYPE_DUMMY_DIM};
    
    
    //xml tags from database
    /*
     * for instance,
     * <?xml version="1.0"?>
        <cube name="PM01" isNewModel="false">
          <tables>
            <table name="PM001_F001" type="fact">
              <cols>
                <col 
                    name="D001_DIM_ID" 
                    type="NUMBER(10)" 
                    desc="Dimension ref on D001" 
                    isPrimary="false" 
                    isForeignkey="true" 
                    aggrType="" 
                    refTableName="PM001_D001_DIM1"/>
                <col name="O002_M01_M_SUM" type="NUMBER(18,2)" desc="summa" isPrimary="false" isForeignkey="false" aggrType="sum" refTableName=""/>
              </cols>
            </table>
            <table name="PM001_D001_DIM1" type="dim">
              <cols>
                <col name="DIM_ID" type="NUMBER(10)" desc="Dimension key" isPrimary="true" isForeignkey="false" aggrType="" refTableName=""/>
                <col name="O001_A01_DIM1" type="VARCHAR2(10)" desc="dimension1" isPrimary="false" isForeignkey="false" aggrType="" refTableName=""/>
              </cols>
            </table>
          </tables>
        </cube>
     *
     */
    
    public static String TD_CUBE_TAG = "cube";
    public static String TD_TABLES_TAG = "tables";
    public static String TD_TABLE_TAG = "table";
    public static String TD_COLUMNS_TAG = "cols";
    public static String TD_COLUMN_TAG = "col";
    
    public static String TD_CUBE_NAME_ATTR = "name";
    public static String TD_CUBE_DESC_ATTR = "desc";
    public static String TD_CUBE_NEW_MODEL_ATTR = "isNewModel";
    
    public static String TD_TABLE_NAME_ATTR = "name";
    public static String TD_TABLE_DIM_ATTR = "type";
    public static String TD_TABLE_DESC_ATTR = "desc";
    public static String TD_TABLE_GROUP_ATTR = "group";
    
    public static String TD_COLUMN_NAME_ATTR = "name";
    public static String TD_COLUMN_TYPE_ATTR = "type";
    public static String TD_COLUMN_DESC_ATTR = "desc";
    public static String TD_COLUMN_ISPRIMARYKEY_ATTR = "isPrimary";
    public static String TD_COLUMN_ISFOREIGNKEY_ATTR = "isForeignkey";
    public static String TD_COLUMN_REF_TABLENAME_ATTR = "refTableName";
    public static String TD_COLUMN_AGGR_TYPE_ATTR = "aggrType";
    
    // @isadikov
    // this variable can be used if you want to change method to deploy metadata XML in RPD
    // by default it uses XUDML which provides API for running commands on server side (value = false);
    // if it is true it means we use WebServices for SOA API which provides deploy management via Java
    public static boolean USE_WEB_SERVICES_AGAINST_SHELL = true;
    
    // @isadikov
    // this variable suggests to save scripts and metadata xml files on server
    // it can be used for none automatic transactions to modify RPD file
    // and just to see what XMLGen generated for current structure
    // if "USE_WEB_SERVICES_AGAINST_SHELL" is true, these files would NOT be run
    public static boolean SAVE_METADATA_XML_SCRIPTS_ON_SERVER = false;
    
    
    // generate order number for models
    // not used in constants (!)
    // we will use "getNextModelNumber()" method in XMLGen.java
    public static String MODEL_NUMBER_GEN = "11.0";
    
    // @isadikov
    // this variable suggests to create repository variables (with Scheme name and password)
    // on the application deploy or with first request
    // defualt value is false, which means creating variables with first request
    public static boolean CREATE_VARIABLES_ON_APP_DEPLOY = false;
    
    //@isadikov
    // first request variable
    // it is not meant to be changed manually
    public static boolean isFirstRequest = true;
    
    
    public Re() {
        super();
    }
    
    public static String getCPUser() {
        return "valueof(" + RPD_USERNAME_VARIABLE + ")";
    }
    
    public static String getCPPassword() {
        //which has the value : "valueof(RPD_PASSWORD_VARIABLE)"
        return CP_PASSWORD_ENCRYPTED;
    }
    
    public static boolean getSaveMetadataXMLScriptsOnServer() {
        if (!USE_WEB_SERVICES_AGAINST_SHELL && !SAVE_METADATA_XML_SCRIPTS_ON_SERVER) {
            return true;
        } else {
            return SAVE_METADATA_XML_SCRIPTS_ON_SERVER;
        }
    }
    
    public static String getNextModelNumber() {
        String firstPart = MODEL_NUMBER_GEN.split(".")[0];
        String secondPart = MODEL_NUMBER_GEN.split(".")[1];
        int num = Integer.parseInt(secondPart);
        
        num++;
        
        MODEL_NUMBER_GEN = firstPart + "." + String.valueOf(num);
        
        return MODEL_NUMBER_GEN;
    }
    
    private static Re Instance = new Re();
    
    public static Re getRe() {
        return Instance;
    }
}
