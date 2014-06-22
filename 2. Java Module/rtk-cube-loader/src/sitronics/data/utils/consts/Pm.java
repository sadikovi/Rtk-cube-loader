package sitronics.data.utils.consts;

public class Pm {
    
    //bi analytics url
    public static String BI_ANALYTICS_URL = "http://msk-02-orabits.tsretail.ru:9704";
    
    //services names
    public static String METADATA_SERVICE = "MetadataService";
    public static String SAW_SESSION_SERVICE = "SAWSessionService";
    
    //at least required role for users to use the application
    public static String BI_REQUIRED_ROLE = "BIAuthor";
    public static String ADMIN_ROLE = "BIAdministrator";
    
    // these tags are not used in configuration file
    public static String PM_NAME = "PM_NAME";
    public static String PM_DESC = "PM_DESC";
    public static String LOAD_NAME = "LOAD_NAME";
    public static String USER_NAME = "USER_NAME";
    
    //form ids and names are used on html page
    //upload file
    public static String FORM_UPLOAD_NAME = "excelUploadName"; 
    //input name for new model
    public static String FORM_PM_NAME_INPUT = "excelPMNameInput";
    // input value for a new model using in value parameter for "FORM_PM_NAME_INPUT"
    public static String FORM_PM_NAME_INPUT_VALUE = "input";
    //select name from list for existed model
    public static String FORM_PM_NAME_SELECT = "excelPMNameSelect";
    // select value for an existing model using in value parameter for "FORM_PM_NAME_SELECT"
    public static String FORM_PM_NAME_SELECT_VALUE = "select";
    //flag for new and existed model
    public static String FORM_PM_NAME_RADIONAME = "excelPMNameRadioName";
    //import file
    public static String FORM_IMPORT_FILE = "excelImportFile";
    // input for pm description
    public static String FORM_PM_DESCRIPTION = "excelPMDescription";
    // input flag for pm load only data without model rebuilding
    public static String FORM_PM_LOAD_ONLY_DATA_RADIO = "excelPMNameLoadOnlyDataRadio";
    // radio value for an only data loading using in value parameter for "FORM_PM_LOAD_ONLY_DATA_RADIO"
    public static String FORM_PM_LOAD_ONLY_DATA_RADIO_VALUE = "loadonlydata";
    
    // used only to define whether presentation model is in rpd or not
    // do not include it in configuration file!!!
    public static boolean pmIsInRPD = false;
    
    public Pm() {
        super();
    }
    
    private static Pm Instance = new Pm();
    
    public static Pm getPm() {
        return Instance;
    }
}
