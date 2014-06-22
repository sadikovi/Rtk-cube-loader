package sitronics.data.servlets;

import java.io.IOException;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import sitronics.data.DBImport;
import sitronics.data.Excel;
import sitronics.data.PMData;
import sitronics.data.Repository;
import sitronics.data.utils.AuthUtil;
import sitronics.data.utils.Common;
import sitronics.data.utils.DBConnection;
import sitronics.data.utils.Error;
import sitronics.data.utils.consts.Consts;


public class ImportServlet extends HttpServlet {
    @SuppressWarnings("compatibility:4787947712720388149")
    private static final long serialVersionUID = 1L;
    
    private static final String CONTENT_TYPE = "text/html; charset=windows-1251";
    
    HashMap data = new HashMap();
    List<String> scripts;
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
    
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        
        /* we are not allowing to use GET-Method for sending requests */
        Error.thrown(response, Error.COMMON_E_CODE, Consts.getMSG().KMSG_ERROR_GET_METHOD_IS_FORBID);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        
        if (request == null) {
            Error.thrown(response, Error.COMMON_E_CODE, Consts.getMSG().KMSG_ERROR_REQUEST_IS_EMPTY);
        } else if (request != null && !DBConnection.testDirectConnection()) {
            Error.thrown(response, Error.COMMON_E_CODE, Consts.getMSG().KMSG_ERROR_DB_CONNECTION_UNAVAILABLE);
        } else {
            String excelStatus = "";
            String dbStatus = "";
            String rpdStatus = "";
            String sessionID = AuthUtil.getUserID(request);
            
            if (!AuthUtil.checkUserRole(request, Consts.getPM().BI_REQUIRED_ROLE, Consts.getPM().BI_ANALYTICS_URL)) {
                Error.thrown(response, Error.COMMON_E_CODE, Consts.getMSG().KMSG_ERROR_USER_HAS_NO_ACCESS);
            } else {
                /* 1. load excel file and get parameters */
                try {
                    excelStatus = loadData(request, sessionID, AuthUtil.getCurrentUser(sessionID, Consts.getPM().BI_ANALYTICS_URL), 
                                           Consts.getPM().FORM_UPLOAD_NAME, Consts.getPM().FORM_PM_NAME_INPUT, 
                                           Consts.getPM().FORM_PM_NAME_SELECT, Consts.getPM().FORM_PM_NAME_RADIONAME, 
                                           Consts.getPM().FORM_IMPORT_FILE, Consts.getPM().FORM_PM_DESCRIPTION, 
                                           Consts.getPM().FORM_PM_LOAD_ONLY_DATA_RADIO);
                } catch (FileUploadException e) {
                    excelStatus = "#Exception# " + e.toString();
                }

                //[I] branch for full model creation or recreation
                if (excelStatus.equals(Consts.getMSG().KMSG_SUCCESS_MESSAGE)) {
                    /* 2. create db objects and load data */
                    //only if excel was loaded successfully
                    List<String> resList = new ArrayList<String>();
                    
                    try {
                        resList = DBImport.loadDBData(false, data, Consts.getDB().DB_AUTO_COMMIT);
                        dbStatus = resList.get(0);
                        
                        data = null;
                    } catch (Exception e) {
                        data = null;
                        resList = null;
                        
                        dbStatus = "#Exception# " + e.toString();
                    }
                    
                    /* 3. create model in repository */
                    //only if excel and db objects were created successfully
                    if (dbStatus.equals(Consts.getMSG().KMSG_SUCCESS_MESSAGE)) {
                        // @isadikov
                        // if CREATE_VARIABLES_ON_APP_DEPLOY = false we check the first request
                        if (!Consts.getRE().CREATE_VARIABLES_ON_APP_DEPLOY && Consts.getRE().isFirstRequest) {
                            String res_init = Repository.initialiseFilesAndRunStartProcesses();
                            System.out.println(res_init); 
                        }
                        // ... and set isFirstRequest as false
                        Consts.getRE().isFirstRequest = false;
                        
                        
                        String xmlstring = "";
                        try {
                            xmlstring = resList.get(1);
                            rpdStatus = Repository.loadRepositoryDataFromDatabase(xmlstring);
                            
                            xmlstring = null;
                            resList = null;
                        } catch (Exception e) {
                            xmlstring = null;
                            resList = null;
                            
                            rpdStatus = "#Exception# " + e.toString();
                        }
                    }
                //[II] branch for only data loading
                } else if (excelStatus.equals(Consts.getMSG().KMSG_SUCCESS_MESSAGE_LOAD_ONLY_DATA) 
                           && !excelStatus.equals(Consts.getMSG().KMSG_SUCCESS_MESSAGE)) {
                    //this branch is for db data loading. We are not creating tables or something, 
                    //we only load additional data to tables.
                    List<String> resList = new ArrayList<String>();
                    
                    try {
                        resList = DBImport.loadDBData(true, data, Consts.getDB().DB_AUTO_COMMIT);
                        dbStatus = resList.get(0);
                        
                        data = null;
                    } catch (Exception e) {
                        dbStatus = "#Exception# " + e.toString();
                    }
                }
                
                /*
                 * If excel error happened we display error for user.
                 * If db or repository error happened we display it only for administrator role or same granted role.
                 */
                
                if (excelStatus.equals(Consts.getMSG().KMSG_SUCCESS_MESSAGE) && dbStatus.equals(Consts.getMSG().KMSG_SUCCESS_MESSAGE) && rpdStatus.trim().equals(Consts.getMSG().KMSG_SUCCESS_MESSAGE)) {
                    response.getWriter().println("{ \"status\": \"0\", \"message\": \"" + Consts.getMSG().KMSG_SUCCESS_MESSAGE_LOCAL + "\", \"code\": \"" + "000" + "\" }");    
                } else if (excelStatus.equals(Consts.getMSG().KMSG_SUCCESS_MESSAGE_LOAD_ONLY_DATA) && dbStatus.equals(Consts.getMSG().KMSG_SUCCESS_MESSAGE)) {
                    response.getWriter().println("{ \"status\": \"0\", \"message\": \"" + Consts.getMSG().KMSG_SUCCESS_MESSAGE_LOCAL + "\", \"code\": \"" + "000" + "\" }");
                } else {
                    if (excelStatus.length() > 0 && dbStatus.length() <= 0 && rpdStatus.length() <= 0) {
                        Error.thrown(response, Error.EX_E_CODE, excelStatus);
                    } else if (excelStatus.equals(Consts.getMSG().KMSG_SUCCESS_MESSAGE) && dbStatus.length() > 0 && rpdStatus.length() <= 0) {
                        if (!AuthUtil.checkUserRole(request, Consts.getPM().ADMIN_ROLE, Consts.getPM().BI_ANALYTICS_URL)) {
                            Error.thrown(response, Error.DB_E_CODE, Consts.getMSG().KMSG_ERROR_DEFAULT_MSG);
                        } else {
                            Error.thrown(response, Error.DB_E_CODE, dbStatus);
                        }
                    } else if (excelStatus.equals(Consts.getMSG().KMSG_SUCCESS_MESSAGE) && dbStatus.equals(Consts.getMSG().KMSG_SUCCESS_MESSAGE) && rpdStatus.length() > 0) {
                        if (!AuthUtil.checkUserRole(request, Consts.getPM().ADMIN_ROLE, Consts.getPM().BI_ANALYTICS_URL)) {
                            Error.thrown(response, Error.RE_E_CODE, Consts.getMSG().KMSG_ERROR_DEFAULT_MSG);
                        } else {
                            Error.thrown(response, Error.RE_E_CODE, rpdStatus);
                        }
                    } else {
                        if (!AuthUtil.checkUserRole(request, Consts.getPM().ADMIN_ROLE, Consts.getPM().BI_ANALYTICS_URL)) {
                            Error.thrown(response, Error.COMMON_E_CODE, Consts.getMSG().KMSG_ERROR_DEFAULT_MSG);
                        } else {
                            Error.thrown(response, Error.COMMON_E_CODE, excelStatus + ", " + dbStatus + ", " + rpdStatus);
                        }
                    }
                }
            }
        }
    }
    
    public String loadData(HttpServletRequest request,
                           String sessionId,
                           String userName,
                           String fileUploadFieldName,
                           String PMFieldNameInput,
                           String PMFieldNameSelect,
                           String PMFieldNameRadioButton, 
                           String excelFileFieldName,
                           String PMDesciption,
                           String PMLoadOnlyDataRadioButton) throws FileUploadException {
        List<FileItem> list = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
        
        String fileUploadName = null;
        String presentationModelNameInput = null;
        String presentationModelNameSelect = null;
        String presentationModelNameFlag = null; 
        String presentationModelName = null;
        String presentationModelDesc = null;
        //@isadikov: flag for "load only data" added
        String presentationModelLoadOnlyData = null;
        InputStream excelFile = null;
        
        for (FileItem item : list) {
            if (item.getFieldName().equals(fileUploadFieldName)) {
                fileUploadName = new String(item.get());
            }
            if (item.getFieldName().equals(PMFieldNameInput)) {
                presentationModelNameInput = new String(item.get());
            }
            if (item.getFieldName().equals(PMFieldNameSelect)) {
                presentationModelNameSelect = new String(item.get());
            }
            if (item.getFieldName().equals(PMFieldNameRadioButton)) {
                presentationModelNameFlag = new String(item.get());
            }
            if (item.getFieldName().equals(excelFileFieldName) 
                && (item.getContentType().equals("application/vnd.ms-excel") || item.getContentType().equals("application/excel"))) {
                try {
                    excelFile = item.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                    excelFile = null;
                }
            }
            if (item.getFieldName().equals(PMDesciption)) {
                presentationModelDesc = Common.replaceSeparatorInString(
                                                new String(item.get()), 
                                                Consts.getEX().APOSTROPHE, 
                                                Consts.getEX().APOSTROPHE_R);
            }
            if (item.getFieldName().equals(PMLoadOnlyDataRadioButton)) {
                presentationModelLoadOnlyData = new String(item.get());
            }
        }
        
        /*
         * Global check upload consistency
         */
        
        /* file upload name check */
        if (Common.isEmpty(fileUploadName)) {
            return Consts.getMSG().KMSG_ERROR_UPLOAD_NAME_EMPTY;
        /*
        } else if (!Common.isOnlyAllowedSymbols(fileUploadName)) {
            return Consts.getMSG().KMSG_ERROR_UPLOAD_NAME_NOT_ALLOWED_CHARACTERS;
        */
        /* presentation model flag check */
        } else if (!presentationModelNameFlag.equals(Consts.getPM().FORM_PM_NAME_SELECT_VALUE) 
                   && !presentationModelNameFlag.equals(Consts.getPM().FORM_PM_NAME_INPUT_VALUE)) {
            return Consts.getMSG().KMSG_ERROR_UKNOWN_ACTION_FOR_INTERFACE_CALL;
        /* presentation model name empty check */
        } else if (Common.isEmpty(presentationModelNameInput) 
                   && Common.isEmpty(presentationModelNameSelect)) {
            return Consts.getMSG().KMSG_ERROR_PM_NAME_INPUT_AND_SELECT_EMPTY;
        /* presentation model name select branch check */
        } else if (presentationModelNameFlag.equals(Consts.getPM().FORM_PM_NAME_SELECT_VALUE) 
                   && Common.isEmpty(presentationModelNameSelect)) {
            return Consts.getMSG().KMSG_ERROR_PM_NAME_SELECT_IS_EMPTY;
        /* presentation model name input branch check */
        } else if (presentationModelNameFlag.equals(Consts.getPM().FORM_PM_NAME_INPUT_VALUE) 
                   && Common.isEmpty(presentationModelNameInput)) {
            return Consts.getMSG().KMSG_ERROR_PM_NAME_INPUT_IS_EMPTY;
        } else if (presentationModelNameFlag.equals(Consts.getPM().FORM_PM_NAME_INPUT_VALUE) 
                   && !Common.isOnlyAllowedSymbols(presentationModelNameInput)) {
            return Consts.getMSG().KMSG_ERROR_PM_NAME_NOT_ALLOWED_CHARACTERS;
        } else if (presentationModelNameFlag.equals(Consts.getPM().FORM_PM_NAME_INPUT_VALUE)
                   && !Common.isEmpty(presentationModelNameInput) && PMData.hasPresentationModel(Consts.getPM().BI_ANALYTICS_URL, sessionId, presentationModelNameInput)) {
            return Consts.getMSG().KMSG_ERROR_PM_NAME_ALREADY_EXISTS;
        /* excel file parsing check */
        } else if (Common.isEmpty(excelFile)) {
            return Consts.getMSG().KMSG_ERROR_EXCEL_FILE_IS_NOT_VALID;
        } else if (!Common.isEmpty(presentationModelLoadOnlyData) && presentationModelNameFlag.equals(Consts.getPM().FORM_PM_NAME_INPUT_VALUE)) {
            return Consts.getMSG().KMSG_ERROR_LOAD_ONLY_DATA_FORBIDDEN;
        } else if (Common.hasUnsufficientSymbol(presentationModelDesc)) {
            return Consts.getMSG().KMSG_ERROR_DESCRIPTION_INCORRECT;
        } else {
            
            try {
                /* presentation model name defining*/
                presentationModelName = (presentationModelNameFlag.equals(Consts.getPM().FORM_PM_NAME_SELECT_VALUE)?presentationModelNameSelect : presentationModelNameInput);
                
                /* @isadikov: we're trying to identify whether the pm name is in rpd or not */
                // if it is existed we rewrite pmIsInRPD. If it does exist - true, otherwise false.
                Consts.getPM().pmIsInRPD = PMData.hasPresentationModel(Consts.getPM().BI_ANALYTICS_URL, sessionId, presentationModelName);

                /* load excel */
                Excel excel = new Excel();
                // @isadikov: if loadOnlydata is not empty we trying to reload data only in db without repository
                if (!Common.isEmpty(presentationModelLoadOnlyData) && presentationModelLoadOnlyData.equals(Consts.getPM().FORM_PM_LOAD_ONLY_DATA_RADIO_VALUE)) {
                    // we are using this method in Excel which, 
                    // firstly, returns data from DBImport (columnsConfiguration), 
                    // secondly, forms the GlobalColumnsList for downloading data
                    String excelLoadOnlyData = excel.loadOnlyDataInExcel(presentationModelName, excelFile);
                    
                    /* release input stream */
                    excelFile = null;
                    
                    if (excelLoadOnlyData.equals(Consts.getMSG().KMSG_SUCCESS_MESSAGE_LOAD_ONLY_DATA)) {
                        /* add parameters to the data*/
                        excel.ExcelData.put(Consts.getPM().PM_NAME, presentationModelName);
                        excel.ExcelData.put(Consts.getPM().PM_DESC, presentationModelDesc);
                        excel.ExcelData.put(Consts.getPM().LOAD_NAME, fileUploadName);
                        excel.ExcelData.put(Consts.getPM().USER_NAME, userName);
                        
                        data = excel.ExcelData;
                        
                        excel.ExcelData = null;
                        excel = null;
                        
                        return Consts.getMSG().KMSG_SUCCESS_MESSAGE_LOAD_ONLY_DATA;
                    } else {
                        return "#ExcelLoadException# " + excelLoadOnlyData;
                    }
                } else {
                    String excelLoad = excel.loadExcel(excelFile);
                    
                    /* release input stream */
                    excelFile = null;
                    
                    if (excelLoad.equals(Consts.getMSG().KMSG_SUCCESS_MESSAGE)) {
                        /* add parameters to the data */
                        excel.ExcelData.put(Consts.getPM().PM_NAME, presentationModelName);
                        excel.ExcelData.put(Consts.getPM().PM_DESC, presentationModelDesc);
                        excel.ExcelData.put(Consts.getPM().LOAD_NAME, fileUploadName);
                        excel.ExcelData.put(Consts.getPM().USER_NAME, userName);
                        
                        data = excel.ExcelData;
                        
                        excel.ExcelData = null;
                        excel = null;
                        
                        return Consts.getMSG().KMSG_SUCCESS_MESSAGE;
                    } else {
                        return "#ExcelLoadException# " + excelLoad;
                    }
                }
                
            } catch (Exception e) {
                return "#ExcelGlobalException# " + e.toString();
            }
        }
    }
}