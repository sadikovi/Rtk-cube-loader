package sitronics.data.utils.consts;

public class Msg {
    // we use error messages like references in configuration
    // string has the followed structure: k + Error + ErrorDescription
    
    public static String KMSG_SUCCESS_MESSAGE = "success";
    public static String KMSG_SUCCESS_MESSAGE_LOCAL = "Operation succeeded";
    public static String KMSG_SUCCESS_MESSAGE_LOAD_ONLY_DATA = "Data loading succeeded";
    
    public static String KMSG_ERROR_DEFAULT_MSG = "An error occured while proceeding. Please, contact system administrator";
    
    public static String KMSG_ERROR_REQUEST_IS_EMPTY = "Request is empty";
    public static String KMSG_ERROR_GET_METHOD_IS_FORBID = "GET-method is depricated";
    public static String KMSG_ERROR_DB_CONNECTION_UNAVAILABLE = "Can not connect to database";
    public static String KMSG_ERROR_USER_HAS_NO_ACCESS = "User has no access to use the application";
    
    public static String KMSG_ERROR_UPLOAD_NAME_EMPTY = "Please, enter a file upload name";
    public static String KMSG_ERROR_UPLOAD_NAME_NOT_ALLOWED_CHARACTERS = "File upload name contains not allowed characters";
    
    public static String KMSG_ERROR_UKNOWN_ACTION_FOR_INTERFACE_CALL = "Unknown action for presentation model name";
    
    public static String KMSG_ERROR_PM_NAME_INPUT_AND_SELECT_EMPTY = "Please, choose appropriate presentation model name or type one in";
    public static String KMSG_ERROR_PM_NAME_SELECT_IS_EMPTY = "Please, choose available presentation model name in the list";
    
    public static String KMSG_ERROR_PM_NAME_INPUT_IS_EMPTY = "Please, type a presentation model name";
    public static String KMSG_ERROR_PM_NAME_NOT_ALLOWED_CHARACTERS = "Presentation model name contains not allowed characters";
    
    public static String KMSG_ERROR_PM_NAME_ALREADY_EXISTS = "The presentation model name already exists";
    
    public static String KMSG_ERROR_EXCEL_FILE_IS_NOT_VALID = "Excel file has not a valid format or empty";
    
    public static String KMSG_ERROR_LOAD_ONLY_DATA_FORBIDDEN = "Data loading can not be performed for new model";
    
    public static String KMSG_ERROR_NO_AVAILABLE_MODEL = "No available models";
    
    public static String KMSG_ERROR_DESCRIPTION_INCORRECT = "Description is incorrect";
    
    
    public static String KMSG_ERROR_EXCEL_CONFIGURATION_MISSED = "Configuration is missed";
    public static String KMSG_ERROR_EXCEL_SHEET_DECLARED_BUT_NOT_EXISTED = "Sheet declared, but does not exist in workbook";
    // constants for "isCompletedSuccessfully"
    public static String KMSG_ERROR_EXCEL_CS_COLUMNS_LIST_EMPTY = "Column array is empty";
    public static String KMSG_ERROR_EXCEL_CS_ATTRIBUTE_MISSED = "One of the attributes is missed on the configuration sheet";
    public static String KMSG_ERROR_EXCEL_CS_COLUMN_TYPE_UNDEFINED = "Column type is undefined";
    public static String KMSG_ERROR_EXCEL_CS_COLUMN_SHEET_UNKNOWN = "Column sheet is unknown";
    public static String KMSG_ERROR_EXCEL_CS_COLUMN_GROUP_INCORRECT = "Column data group is incorrect (must be a number)";
    public static String KMSG_ERROR_EXCEL_CS_COLUMN_POSITION_INCORRECT = "Column position is incorrect";
    public static String KMSG_ERROR_EXCEL_CS_COLUMN_PARENT_POS_INCORRECT = "Parent position is undefined or incorrect";
    public static String KMSG_ERROR_EXCEL_CS_COLUMN_START_ROW_INCORRECT = "Start row is incorrect (must be a number)";
    public static String KMSG_ERROR_EXCEL_CS_COLUMN_AGG_RULE_INCORRECT = "Aggregation rule is declared for column which is not a number";
    
    
    public Msg() {
        super();
    }
    
    private static Msg Instance = new Msg();
    
    public static Msg getMsg() {
        return Instance;
    }
}
