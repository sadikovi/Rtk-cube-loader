package sitronics.data.utils.consts;

public class Db {
    
    public static String DB_SCHEME_NAME = "SERVICES_DM";
    public static String DB_SCHEME_PASSWORD = "SERVICES_DM";
    
    //db source settings
    /*
     * HOST = db host (f.i. 192.168.2.165)
     * PORT = db port (f.i. 1521)
     * SERVICE_NAME = db service name (it can be the similar UID, f.i. DWHQA)
     * So, consequently you can form this jdbc source : jdbc:oracle:thin:@192.168.2.165:1521/DWHQA
     */
    public static String DB_SOURCE_HOST = "192.168.2.165";
    public static String DB_SOURCE_PORT = "1521";
    public static String DB_SOURCE_SERVICE_NAME = "DWHQA";
    
    // @depricated
    //public static final String DB_SOURCE = "jdbc:oracle:thin:@192.168.2.165:1521/DWHQA";
    //public static final String DB_DATASOURCE = "(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=192.168.2.165)(PORT=1521))(CONNECT_DATA=(SERVER=dedicated)(SERVICE_NAME=DWHQA)))";
    
    //these parameters are for using jndi connection in oracle BI
    // you need to set the jndi path and env
    // and change methods to @getConnection()
    public static String JNDI_PATH = "jdbc/rtk-cube";
    public static String JNDI_ENV = "java:comp/env";
    
    public static String DB_STRUCTURE_PACKAGE = "pkg_cc_structure";
    public static String DB_FNC_CREATE_CUBE = "fnc_create_cube";
    public static String DB_FNC_GET_PM_NAMES = "fnc_get_pm_names";
    public static String DB_FNC_GET_MODEL_STRUCTURE = "fnc_get_model_xml";
    public static String DB_CC_LOAD_PACKAGE = "pkg_cc_structure";
    public static String DB_FNC_LOAD_CUBE = "fnc_load_cube";
    
    public static String DB_SEQ_PRC_ID_NEXTVAL = "seq_prc_id.nextval";
    public static String DB_SEQ_PRC_ID_CURRVAL = "seq_prc_id.currval";

    //set auto commit in db, if "true" - then every transaction will be commited;
    // if "false" - then commit will be executed after all transactions
    public static String DB_AUTO_COMMIT = "false";
    
    
    public Db() {
        super();
    }
    
    // get jdbc source
    public static String getDBSource() {
        return ("jdbc:oracle:thin:" + "@" + DB_SOURCE_HOST  + ":" + DB_SOURCE_PORT + "/" + DB_SOURCE_SERVICE_NAME);
    }
    
    //get data source for repository
    public static String getDBDataSource() {
        return ("(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=" + DB_SOURCE_HOST + ")(PORT=" + DB_SOURCE_PORT + "))(CONNECT_DATA=(SERVER=dedicated)(SERVICE_NAME=" + DB_SOURCE_SERVICE_NAME + ")))");
    }
    
    private static Db Instance = new Db();
    
    public static Db getDb() {
        return Instance;
    }
}
