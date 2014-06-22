package sitronics.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.io.PrintStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.ws.Holder;

import oracle.obiee.admin.ws.Admin;

import oracle.obiee.admin.ws.AdminService;

import oracle.obiee.admin.ws.ServerException_Exception;
import oracle.obiee.admin.ws.UnsupportedTypeException_Exception;
import oracle.obiee.admin.ws.ValidationException_Exception;
import oracle.obiee.admin.ws.types.Column;
import oracle.obiee.admin.ws.types.Parameter;
import oracle.obiee.admin.ws.types.Results;
import oracle.obiee.admin.ws.types.ResultsRow;
import oracle.obiee.admin.ws.types.StringParameter;

import org.w3c.dom.Document;

import sitronics.data.utils.BIService;
import sitronics.data.utils.Common;
import sitronics.data.utils.consts.Consts;


public class Repository {
    
    public Repository() {
        super();
    }
    //Methods for operation system defining
    /**
     * Returns true, if OS is Windows.
     * 
     * @return
     */
    public static boolean isWindows() {
        return (System.getProperty("os.name").toLowerCase().contains("windows"));
    }

    /**
     * Returns true, if OS is MacOS.
     * 
     * @return
     */
    public static boolean isMac() {
        return (System.getProperty("os.name").toLowerCase().contains("mac"));
    }

    /**
     * Returns true, if OS is Unix system.
     * 
     * @return
     */
    public static boolean isUnix() {
        if (System.getProperty("os.name").toLowerCase().contains("nix") ||
            System.getProperty("os.name").toLowerCase().contains("nux") ||
            System.getProperty("os.name").toLowerCase().contains("aix")) {
            return true;
        } else {
            return false;
        }
    }
    
    /** 
     * Returns process output.
     * 
     * @param p
     * @return 
     */
    private static String returnProcessOutput(Process p) throws IOException {
        String res = "";
        BufferedReader brin = new BufferedReader(new InputStreamReader(p.getInputStream()));
        while (true) {
            res += brin.readLine();
            if (brin.readLine() == null) {
                break;
            }
        }
        p.getInputStream().close();
        return res;
    }

    /** 
     * Returns process error.
     * 
     * @param p
     * @return 
     */
    private static String returnProcessError(Process p) throws IOException {
        String res = "";
        BufferedReader brer = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        while (true) {
            res += brer.readLine();
            if (brer.readLine() == null) {
                break;
            }
        }
        p.getInputStream().close();
        return res;
    }

    /**
     * Generates xml from RPD file using biserverxmlgen.
     * 
     * @param binDirectory
     * @param scriptDirectory
     * @param initFile
     * @param workDirectory
     * @param rpdName
     * @param rpdPassword
     * @param xmlName
     * @return
     */
    public static String biGenerateRPDXML(String binDirectory, String scriptDirectory, String initFile, String workDirectory, String rpdName, String rpdPassword, String xmlName) {
        if (isUnix()) {
            String s = ". " + scriptDirectory + initFile + " && " + binDirectory + Consts.getRE().BI_GENERATE_XML 
                       + " -R " + workDirectory + rpdName 
                       + " -P " + rpdPassword 
                       + " -O " + workDirectory + xmlName + " -8";
            
            return s;
        } else if (isWindows()) {
            String s = scriptDirectory + initFile + " && " + "" + Consts.getRE().BI_GENERATE_XML 
                       + " -R " + workDirectory + rpdName 
                       + " -P " + rpdPassword 
                       + " -O " + workDirectory + xmlName + " -8";
                        
            return s;
        } else {
            return "";
        }
    }
    
    /**
     * Executes changes in offline mode using biserverxmlexec.
     * 
     * @param binDirectory
     * @param scriptDirectory
     * @param initFile
     * @param workDirectory
     * @param changesFile
     * @param rpdName
     * @param rpdPassword
     * @param newRpdName
     * @return
     */
    public static String biExecuteXML(String binDirectory, String scriptDirectory, String initFile, String workDirectory, String changesFile, String rpdName, String rpdPassword, String newRpdName) {
        if (isUnix()) {
            String s = ". " + scriptDirectory + initFile + " && " + binDirectory + Consts.getRE().BI_EXECUTE_XML 
                       + " -I " + workDirectory + changesFile 
                       + " -B " + workDirectory + rpdName
                       + " -P " + rpdPassword
                       + " -O " + workDirectory + newRpdName + " && echo \"" + Consts.getMSG().KMSG_SUCCESS_MESSAGE + "\"";
            
            return s;
        } else if (isWindows()) {
            String s = scriptDirectory + initFile + " && " + "" + Consts.getRE().BI_EXECUTE_XML 
                       + " -I " + workDirectory + changesFile 
                       + " -B " + workDirectory + rpdName
                       + " -P " + rpdPassword
                       + " -O " + workDirectory + newRpdName + " && echo \"" + Consts.getMSG().KMSG_SUCCESS_MESSAGE + "\"";
            
            return s;
        } else {
            return "";
        }
    }

    /**
     * Executes xml in online mode against OBIEE server using biserverxmlcli.
     * 
     * @param binDirectory
     * @param scriptDirectory
     * @param initFile
     * @param workDirectory
     * @param userName
     * @param userPassword
     * @param rpdPassword
     * @param dsn
     * @param changesFile
     * @return
     */
    public static String biExecuteCLI(String binDirectory, String scriptDirectory, String initFile, String workDirectory, String userName, String userPassword, String rpdPassword, String dsn, String changesFile) {
        if (isUnix()) {
            String s = ". " + scriptDirectory + initFile + " && " + binDirectory + Consts.getRE().BI_EXECUTE_CLI 
                       + " -U " + userName
                       + " -P " + userPassword
                       + " -R " + rpdPassword
                       + " -D " + dsn 
                       + " -I " + workDirectory + changesFile 
                       + " && echo \"" + Consts.getMSG().KMSG_SUCCESS_MESSAGE + "\"";
            
            return s;
        } else if (isWindows()) {
            String s = scriptDirectory + initFile + " && " + "" + Consts.getRE().BI_EXECUTE_CLI 
                       + " -U " + userName
                       + " -P " + userPassword
                       + " -R " + rpdPassword
                       + " -D " + dsn 
                       + " -I " + workDirectory + changesFile 
                       + " && echo \"" + Consts.getMSG().KMSG_SUCCESS_MESSAGE + "\"";
            
            return s;
        } else {
            return "";
        }
    }

    /**
     * Creates file with content using custom file name and path.
     * 
     * @param filePath
     * @param fileName
     * @param content
     * @return
     */
    public static String createFileWithContent(String filePath, String fileName, String content) {
        FileOutputStream is;
        PrintStream pis;
        try {
            is = new FileOutputStream(filePath + fileName);
            pis = new PrintStream(is);
            pis.println(content);
            
            // @isadikov: if it is windows os we are trying to include the "exit" command to exit cmd window prompt
            // need to be tested still
            if (isWindows()) {
                pis.println("exit"); 
            }
            
            pis.close();
            is.close();
            
            return Consts.getMSG().KMSG_SUCCESS_MESSAGE;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return e.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    /**
     * Set permissions on file. Use "777" to grant all the permissions for all users.
     * 
     * @param permissions
     * @param filePath
     * @param fileName
     * @throws IOException
     * @throws InterruptedException
     */
    public static void setPermissionsOnFile(String permissions, String filePath, String fileName) throws IOException, InterruptedException {
        List<String> command = new ArrayList<String>();
        command.add("chmod");
        command.add(permissions);
        command.add(fileName);
        
        runCommand(command, filePath);
    }
    
    /**
     * [for Windows only]
     * Set permissions on file. Use "777" to grant all the permissions for all users.
     * 
     * @param permissions
     * @param filePath
     * @param fileName
     * @throws IOException
     * @throws InterruptedException
     */
    public static void setPermissionsOnFileWindows(String permissions, String filePath, String fileName) throws IOException, InterruptedException {
        // we are trying to set grants for open and execute file
        // and set permissions to everyone allowed to access
        List<String> command = new ArrayList<String>();
        command.add("icacls");
        command.add(fileName);
        command.add("/grant");
        command.add("Everyone:" + permissions);
        
        runCommand(command, filePath);
    }
    
    /**
     * Run command using command as string (for simple commands like echo or ls).
     * 
     * @param command
     * @param workDirectory
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static Process runCommand(String command, String workDirectory) throws IOException, InterruptedException {
        Process p;
        
        List<String> t2 = new ArrayList<String>();
        
        t2.add(command);                                             
        ProcessBuilder pb = new ProcessBuilder(t2);
        pb.redirectErrorStream(true);
        pb.directory(new File(workDirectory));
        p = pb.start();
        p.waitFor();
        
        return p;
    }

    /**
     * Run command using command as list of strings.
     * You need to fill the list with every command in your command string.
     * F.I. cd /Users/Common/ will be presented as { "cd", "/Users/Common" }.
     * 
     * @param command
     * @param workDirectory
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static Process runCommand(List<String> command, String workDirectory) throws IOException, InterruptedException {
        Process p;
                                                     
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true);
        pb.directory(new File(workDirectory));
        p = pb.start();
        p.waitFor();
        
        return p;
    }

    /**
     * Run process declared in script file using custom file path.
     * 
     * @param scriptFileName
     * @param workDirectory
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public static Process runProcess(String scriptFileName, String workDirectory) throws IOException, InterruptedException {
        Process p;
        
        List<String> t2 = new ArrayList<String>();
        
        //@isadikov: for windows and linux update
        String runCommandScript = "";
        if (isUnix()) {
            runCommandScript = "./" + scriptFileName;
        } else if (isWindows()) {
            runCommandScript = "" + workDirectory + scriptFileName;
        } else {
            runCommandScript = "";
        }
        
        t2.add(runCommandScript);                                             
        ProcessBuilder pb = new ProcessBuilder(t2);
        pb.redirectErrorStream(true);
        // @isadikov: if it is Unix we try to set work directory. 
        // For windows we use it with script file name.
        if (isUnix()) {
            pb.directory(new File(workDirectory));    
        }
        
        p = pb.start();
        p.waitFor();
        
        return p;
    }
    
    /**
     * Creates RPD changes and write them to the file.
     * 
     * @param pmName
     * @param tablesData
     * @param filePath
     * @param isFullStructure
     * @return
     */
    public static String createRPDChangesInDirectory(String pmName, String nextModelNumber, String pmDesc, List<HashMap> tablesData, List<String> tablesGroupsData, String filePath, boolean isFullStructure, boolean isGroupsFoldersShown) {
        try {
            Document dom = Common.generateDom();
                
            dom = XMLGen.getRepositoryChangesXML(dom, pmName, nextModelNumber, pmDesc, tablesData, tablesGroupsData, isFullStructure, isGroupsFoldersShown);
            
            String xmlstring = Common.DomToXMLString(dom);
            dom = null;
            
            Common.writeXMLStringToTheFile(Common.XMLReplaceSpecCharacters(xmlstring), filePath);
            
            return Consts.getMSG().KMSG_SUCCESS_MESSAGE;
        } catch (Exception e) {
            return e.toString();
        }
    }
    
    /**
     * Creates RPD delete objects list and write them to a file.
     * 
     * @param pmName
     * @param nextModelNumber
     * @param filePath
     * @return
     */
    public static String createRPDDeletesInDirectory(String pmName, String nextModelNumber, String filePath) {
        try {
            String xmlInput = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"
                               + "<Repository xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                               + "<DELETE>"
                               + "</DELETE>"
                               + "</Repository>";
            Document dom = Common.generateDom(xmlInput);
            dom = XMLGen.getRepositoryDeletesXML(dom, xmlInput, pmName, nextModelNumber);
            
            String xmlstring = Common.DomToXMLString(dom);
            dom = null;
            
            Common.writeXMLStringToTheFile(Common.XMLReplaceSpecCharacters(xmlstring), filePath);
            
            return Consts.getMSG().KMSG_SUCCESS_MESSAGE;
        } catch (Exception  e) {
            return e.toString();
        }
    }

    /**
     * Main method for creating xml changes and run process to execute those changes.
     * 
     * @param pmName
     * @param isNewModel
     * @param tablesData
     * @return
     */
    public static String createInitFilesAndRunProcesses(String pmName, String pmDesc, Boolean isNewModel, List<HashMap> tablesData, List<String> tablesGroupsData) {
        String res = "";
        
        String fileName = pmName.toLowerCase() + "-" + Consts.getRE().RPD_DECLARE_FILENAME;
        String scriptFileName = pmName.toLowerCase() + "-" + Consts.getRE().SCRIPT_DECLARE_FILENAME;
        
        String deleteFileName = pmName.toLowerCase() + "-" + Consts.getRE().RPD_DELETE_FILENAME;
        String scriptDeleteFileName = pmName.toLowerCase() + "-" + Consts.getRE().SCRIPT_DELETE_FILENAME;
        
        String nextModelNumber = "";
        
        // @isadikov: windows update
        String initFileName = "";
        if (isUnix()) {
            initFileName = Consts.getRE().BI_UNIX_FILENAME;
        } else if (isWindows()) {
            initFileName = Consts.getRE().BI_WIN_FILENAME;
        }
        
        if (isNewModel) {
            // @isadikov: model is new then
            nextModelNumber = Models.getNextModelNumber();
            Models.setModelNumber(pmName, nextModelNumber);
            
            //0. create init-file.sh
            /* offline */
            //String command = biExecuteXML(Consts.getRE().BIN_DIRECTORY, Consts.getRE().SCRIPT_DIRECTORY, initFileName, Consts.getRE().WORK_DIRECTORY, fileName, Consts.getRE().RPD_NAME, Consts.getRE().RPD_PASSWORD, Consts.getRE().RPD_NAME);
            /* online */
            String command = biExecuteCLI(Consts.getRE().BIN_DIRECTORY, Consts.getRE().SCRIPT_DIRECTORY, initFileName, Consts.getRE().WORK_DIRECTORY, Consts.getRE().USER_NAME, Consts.getRE().USER_PASSWORD, Consts.getRE().RPD_PASSWORD, Consts.getRE().SERVER_DSN, fileName);
            if (Consts.getRE().getSaveMetadataXMLScriptsOnServer()) {
                res = createFileWithContent(Consts.getRE().WORK_DIRECTORY, scriptFileName, command);
            }
            
            //1. create changes xml
            if (Consts.getRE().getSaveMetadataXMLScriptsOnServer()) {
                res = createRPDChangesInDirectory(pmName, nextModelNumber, pmDesc, tablesData, tablesGroupsData, Consts.getRE().WORK_DIRECTORY + fileName, Consts.getRE().SHOW_FULL_STRUCTURE, Consts.getRE().SHOW_GROUP_FOLDERS);
            }
            
            //2. run process
            if (Consts.getRE().USE_WEB_SERVICES_AGAINST_SHELL) {
                String xmlInput = "<?xml version=\"1.0\" encoding=\"iso-8859-1\" ?>" + 
                "<Repository xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" + 
                "<DECLARE></DECLARE>" + 
                "</Repository>";
                
                Document rD = Common.generateDom(xmlInput);
                
                rD = XMLGen.getRepositoryChangesXML(rD, pmName, nextModelNumber, pmDesc, tablesData, tablesGroupsData, Consts.getRE().SHOW_FULL_STRUCTURE, Consts.getRE().SHOW_GROUP_FOLDERS);
                String xudml = Common.DomToXMLString(rD);
                
                Admin admin = getAdminInstance();
                res = modifyMetadata(admin, Consts.getRE().RPD_NAME, Common.XMLReplaceSpecCharacters(xudml));
                
                admin = null;
                xudml = null;
                rD = null;
            } else {
                try {
                    if (isUnix()) {
                        setPermissionsOnFile("777", Consts.getRE().WORK_DIRECTORY, scriptFileName);
                    } else if (isWindows()) {
                        setPermissionsOnFileWindows("F", Consts.getRE().WORK_DIRECTORY, scriptFileName);
                    }
                } catch (IOException e) {
                    return e.toString();
                } catch (InterruptedException e) {
                    return e.toString();
                }
                        
                Process p;
                String output = "";
                String error = "";
                
                try {
                    p = runProcess(scriptFileName, Consts.getRE().WORK_DIRECTORY);
                    output = returnProcessOutput(p); 
                    error = returnProcessError(p);
                    
                    if (error.length() > 0 && !error.equalsIgnoreCase("null")) {
                        res = "#RunProcessException# " + error;    
                    } else {
                        res = output;
                    }
                    
                    p.destroy();
                } catch (IOException e) {
                    e.printStackTrace();
                    res = e.toString();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    res = e.toString();
                }
            }
            
            return res;
        } else {
            //@isadikov : model exists then we look for pmName in Models.ini
            nextModelNumber = Models.getModelNumber(pmName);
            
            //0. create delete-init.sh
            /* offline */
            //String dCommand = biExecuteXML(Consts.getRE().BIN_DIRECTORY, Consts.getRE().SCRIPT_DIRECTORY, Consts.getRE().BI_UNIX_FILENAME, Consts.getRE().WORK_DIRECTORY, deleteFileName, Consts.getRE().RPD_NAME, Consts.getRE().RPD_PASSWORD, Consts.getRE().RPD_NAME);
            /* online */
            String dCommand = biExecuteCLI(Consts.getRE().BIN_DIRECTORY, Consts.getRE().SCRIPT_DIRECTORY, initFileName, Consts.getRE().WORK_DIRECTORY, Consts.getRE().USER_NAME, Consts.getRE().USER_PASSWORD, Consts.getRE().RPD_PASSWORD, Consts.getRE().SERVER_DSN, deleteFileName);
            if (Consts.getRE().getSaveMetadataXMLScriptsOnServer()) {
                res = createFileWithContent(Consts.getRE().WORK_DIRECTORY, scriptDeleteFileName, dCommand);    
            }
            
            //1. create delete-init.xml
            if (Consts.getRE().getSaveMetadataXMLScriptsOnServer()) {
                res = createRPDDeletesInDirectory(pmName, nextModelNumber, Consts.getRE().WORK_DIRECTORY + deleteFileName);    
            }
            
            //2. create init-file.sh
            /* offline */
            //String command = biExecuteXML(Consts.getRE().BIN_DIRECTORY, Consts.getRE().SCRIPT_DIRECTORY, Consts.getRE().BI_UNIX_FILENAME, Consts.getRE().WORK_DIRECTORY, fileName, Consts.getRE().RPD_NAME, Consts.getRE().RPD_PASSWORD, Consts.getRE().RPD_NAME);
            /* online */
            String command = biExecuteCLI(Consts.getRE().BIN_DIRECTORY, Consts.getRE().SCRIPT_DIRECTORY, initFileName, Consts.getRE().WORK_DIRECTORY, Consts.getRE().USER_NAME, Consts.getRE().USER_PASSWORD, Consts.getRE().RPD_PASSWORD, Consts.getRE().SERVER_DSN, fileName);
            if (Consts.getRE().getSaveMetadataXMLScriptsOnServer()) {
                res = createFileWithContent(Consts.getRE().WORK_DIRECTORY, scriptFileName, command);    
            }
            
            //3. create changes xml
            if (Consts.getRE().getSaveMetadataXMLScriptsOnServer()) {
                res = createRPDChangesInDirectory(pmName, nextModelNumber, pmDesc, tablesData, tablesGroupsData, Consts.getRE().WORK_DIRECTORY + fileName, Consts.getRE().SHOW_FULL_STRUCTURE, Consts.getRE().SHOW_GROUP_FOLDERS);
            }
            
            //4. run processes
            if (Consts.getRE().USE_WEB_SERVICES_AGAINST_SHELL) {
                Document rD = XMLGen.WSGetRepositoryChangesAndDeletesXML(pmName, nextModelNumber, pmDesc, tablesData, tablesGroupsData, Consts.getRE().SHOW_FULL_STRUCTURE, Consts.getRE().SHOW_GROUP_FOLDERS);
                String xudml = Common.DomToXMLString(rD);
                
                Admin admin = getAdminInstance();
                res = modifyMetadata(admin, Consts.getRE().RPD_NAME, Common.XMLReplaceSpecCharacters(xudml));
                
                admin = null;
                xudml = null;
                rD = null;
            } else {
                try {
                    if (isUnix()) {
                        setPermissionsOnFile("777", Consts.getRE().WORK_DIRECTORY, scriptDeleteFileName);
                        setPermissionsOnFile("777", Consts.getRE().WORK_DIRECTORY, scriptFileName);
                    } else if (isWindows()) {
                        setPermissionsOnFileWindows("F", Consts.getRE().WORK_DIRECTORY, scriptDeleteFileName);
                        setPermissionsOnFileWindows("F", Consts.getRE().WORK_DIRECTORY, scriptFileName);
                    }
                } catch (IOException e) {
                    return e.toString();
                } catch (InterruptedException e) {
                    return e.toString();
                }
                
                Process p1, p2;
                String output1 = "";
                String error1 = "";
                String output2 = "";
                String error2 = "";
                
                try {
                    // @isadikov: !!! really this needs test!!!
                    // if pm exists we do delete it, if not (unique situation) we do not delete pm
                    if (Consts.getPM().pmIsInRPD) {
                        p1 = runProcess(scriptDeleteFileName, Consts.getRE().WORK_DIRECTORY);
                        output1 = returnProcessOutput(p1); 
                        error1 = returnProcessError(p1);
                        if (error1.length() > 0 && !error1.equalsIgnoreCase("null")) {
                            return "#RunProcessException# process 1 - " + error1;
                        } else {
                            p2 = runProcess(scriptFileName, Consts.getRE().WORK_DIRECTORY);
                            
                            output2 = returnProcessOutput(p2); 
                            error2 = returnProcessError(p2);
                            if (error2.length() > 0 && !error2.equalsIgnoreCase("null")) {
                                return "#RunProcessException# process 2 - " + error2;
                            }
                            
                            res = output2;
                        }
                        p1.destroy();
                        p2.destroy();
                    } else {
                        p2 = runProcess(scriptFileName, Consts.getRE().WORK_DIRECTORY);
                        
                        output2 = returnProcessOutput(p2); 
                        error2 = returnProcessError(p2);
                        if (error2.length() > 0 && !error2.equalsIgnoreCase("null")) {
                            return "#RunProcessException# process 2 - " + error2;
                        }
                        
                        res = output2;
                        
                        p2.destroy();
                    }
                    
                } catch (IOException e) {
                    e.printStackTrace();
                    res = e.toString();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    res = e.toString();
                }
            }
            
            return res;
        }
    }

    /**
     * Init method which calls main method. Is used like wrap method for more usability.
     * 
     * @param xmlstring
     * @return
     */
    public static String loadRepositoryDataFromDatabase(String xmlstring) {
        String res = "";
        
        HashMap cubeMap = TableDataGen.loadTableData(xmlstring);
        
        String pmName = (String)cubeMap.get(TableDataGen.DATALIST_PM_NAME);
        String pmDesc = (String)cubeMap.get(TableDataGen.DATALIST_PM_DESC);
        Boolean isNewModel = (Boolean)cubeMap.get(TableDataGen.DATALIST_IS_NEW_MODEL);
        
        List<HashMap> tablesData = (List<HashMap>)cubeMap.get(TableDataGen.DATALIST_TABLES);
        List<String> tablesGroupsData = (List<String>)cubeMap.get(TableDataGen.DATALIST_GROUPS);
        
        res = createInitFilesAndRunProcesses(pmName, pmDesc, isNewModel, tablesData, tablesGroupsData);
        
        return res;
    }
    
    /**
     * init methods for starting application
     * when it starts these methods will be executed
     */
    public static String initialiseFilesAndRunStartProcesses() {
        String res = "";
        
        String cl_deleteSH = Consts.getRE().CL_DELETE_SH_NAME;
        String cl_deleteXML = Consts.getRE().CL_DELETE_XML_NAME;
        String cl_createSH = Consts.getRE().CL_CREATE_SH_NAME;
        String cl_createXML = Consts.getRE().CL_CREATE_XML_NAME;
        
        // @isadikov: windows update
        String initFileName = "";
        if (isUnix()) {
            initFileName = Consts.getRE().BI_UNIX_FILENAME;
        } else if (isWindows()) {
            initFileName = Consts.getRE().BI_WIN_FILENAME;
        }
        
        try {
            Document dom;
            String xmlstring = "";
            String command = "";
            
            //make init delete files in work directory
            //xml
            dom = XMLGen.initRepositoryDeletes();
            xmlstring = Common.DomToXMLString(dom);
            dom = null;
            
            if (Consts.getRE().getSaveMetadataXMLScriptsOnServer()) {
                Common.writeXMLStringToTheFile(Common.XMLReplaceSpecCharacters(xmlstring), Consts.getRE().WORK_DIRECTORY + cl_deleteXML);
            }
            
            //sh
            if (Consts.getRE().getSaveMetadataXMLScriptsOnServer()) {
                command = biExecuteCLI(
                    Consts.getRE().BIN_DIRECTORY, Consts.getRE().SCRIPT_DIRECTORY, initFileName, 
                    Consts.getRE().WORK_DIRECTORY, Consts.getRE().USER_NAME, Consts.getRE().USER_PASSWORD, 
                    Consts.getRE().RPD_PASSWORD, Consts.getRE().SERVER_DSN, cl_deleteXML);
                res = createFileWithContent(Consts.getRE().WORK_DIRECTORY, cl_deleteSH, command);    
            }
            
            //make init create files in work directory
            //xml
            dom = XMLGen.initRepositoryChanges();
            xmlstring = Common.DomToXMLString(dom);
            dom = null;
            
            if (Consts.getRE().getSaveMetadataXMLScriptsOnServer()) {
                Common.writeXMLStringToTheFile(Common.XMLReplaceSpecCharacters(xmlstring), Consts.getRE().WORK_DIRECTORY + cl_createXML);    
            }
            
            //sh
            if (Consts.getRE().getSaveMetadataXMLScriptsOnServer()) {
                command = biExecuteCLI(
                    Consts.getRE().BIN_DIRECTORY, Consts.getRE().SCRIPT_DIRECTORY, initFileName, 
                    Consts.getRE().WORK_DIRECTORY, Consts.getRE().USER_NAME, Consts.getRE().USER_PASSWORD, 
                    Consts.getRE().RPD_PASSWORD, Consts.getRE().SERVER_DSN, cl_createXML);
                res = createFileWithContent(Consts.getRE().WORK_DIRECTORY, cl_createSH, command);
            }
            
        } catch (Exception e) {
            return e.toString();
        }
        
        //run processes
        if (Consts.getRE().USE_WEB_SERVICES_AGAINST_SHELL) {
            String xudml = Common.DomToXMLString(XMLGen.WSInitRepositoryChangesAndDeletes());
            Admin admin = getAdminInstance();
            
            res = modifyMetadata(admin, Consts.getRE().RPD_NAME, Common.XMLReplaceSpecCharacters(xudml));
            
            admin = null;
            xudml = null;
        } else {
            try {
                if (isUnix()) {
                    setPermissionsOnFile("777", Consts.getRE().WORK_DIRECTORY, cl_deleteSH);
                    setPermissionsOnFile("777", Consts.getRE().WORK_DIRECTORY, cl_createSH);
                } else if (isWindows()) {
                    setPermissionsOnFileWindows("F", Consts.getRE().WORK_DIRECTORY, cl_deleteSH);
                    //setPermissionsOnFileWindows("F", Consts.getRE().WORK_DIRECTORY, cl_deleteXML);
                    setPermissionsOnFileWindows("F", Consts.getRE().WORK_DIRECTORY, cl_createSH);
                    //setPermissionsOnFileWindows("F", Consts.getRE().WORK_DIRECTORY, cl_createXML);
                }
            } catch (IOException e) {
                return e.toString();
            } catch (InterruptedException e) {
                return e.toString();
            }
            
            Process p1, p2;
            String output1 = "";
            String error1 = "";
            String output2 = "";
            String error2 = "";
            
            try {
                if (isUnix()) {
                    p1 = runProcess(cl_deleteSH, Consts.getRE().WORK_DIRECTORY);
                    
                    output1 = returnProcessOutput(p1); 
                    error1 = returnProcessError(p1);
                    if (error1.length() > 0 && !error1.equalsIgnoreCase("null")) {
                        return "#RunProcessException# process 1 - " + error1;
                    } else {
                        
                        p2 = runProcess(cl_createSH, Consts.getRE().WORK_DIRECTORY);
                        
                        output2 = returnProcessOutput(p2); 
                        error2 = returnProcessError(p2);
                        if (error2.length() > 0 && !error2.equalsIgnoreCase("null")) {
                            return "#RunProcessException# process 1 - " + error2;
                        }
                        
                        res = output2;
                    }
                    
                    p1.destroy();
                    p2.destroy();
                } else if (isWindows()) {
                    //Process pW1 = Runtime.getRuntime().exec("start \"\" /B \"" + Consts.getRE().WORK_DIRECTORY + cl_deleteSH + "\"");
                    Process pW1 = Runtime.getRuntime().exec("cmd /c " + Consts.getRE().WORK_DIRECTORY + cl_deleteSH);
                    pW1.getOutputStream().close();
                    pW1.waitFor();
                    
                    output1 = returnProcessOutput(pW1);
                    
                    pW1.destroy();
                    
                    //Process pW2 = Runtime.getRuntime().exec("start \"\" /B \"" + Consts.getRE().WORK_DIRECTORY + cl_createSH + "\"");
                    Process pW2 = Runtime.getRuntime().exec("cmd /c " + Consts.getRE().WORK_DIRECTORY + cl_createSH);
                    pW2.getOutputStream().close();
                    
                    pW2.waitFor();
                    
                    output2 = returnProcessOutput(pW2);
                    
                    pW2.destroy();
                }
            } catch (IOException e) {
                e.printStackTrace();
                res = e.toString();
            } catch (InterruptedException e) {
                e.printStackTrace();
                res = e.toString();
            }
        }
        
        return res;
    }
    
    // methods for Web Services for SOA
    // it is used as an alternative for XUDML patching method
    public static Admin getAdminInstance() {
        String biURL = Consts.getPM().BI_ANALYTICS_URL;
        
        AdminService adminService = new AdminService(BIService.getAdminLocationURL(biURL), BIService.getAdminQName());
        Admin admin = adminService.getAdminPort();
        
        return admin;
    }
    
    public static String modifyMetadata(Admin admin, String rpdName, String XUDML) {
        String res = "";
        
        // 1. declare variables to use them in callProcedure method
        // 1.1. action name
        String metadataModify = "NQSModifyMetadata";
        // 1.2. Output parameters
        String ERROR_MESSAGES = "";
        
        // set parameters
        // 2. Declare list
        List<Parameter> list = new ArrayList<Parameter>();;
        // 2.1. Add parameters:
        // 2.1.1. XUDML (!) it must contain DECLARE tag and DELETE tag (in one file)
        StringParameter xudml = new StringParameter();
        xudml.setName("XUMDL_TEXT");
        xudml.setValue(XUDML);
        list.add(xudml);
        
        // 2.1.2. Original RPD name (if you have a several BI servers in cluster you should fill this value)
        StringParameter originalRpd = new StringParameter();
        originalRpd.setName("ORIGINAL_RPD");
        originalRpd.setValue(rpdName);
        list.add(originalRpd);
        
        // 2.1.3. RPD name (not used now)
        // *** only for future uses
        StringParameter rpd = new StringParameter();
        rpd.setName("RPD");
        rpd.setValue(""); 
        list.add(rpd);
        
        // 2.1.4. Output parameter ("Error message") as returns
        StringParameter errorMessage = new StringParameter();
        errorMessage.setName("ERROR_MESSAGES");
        errorMessage.setValue(ERROR_MESSAGES);
        list.add(errorMessage);
        
        // 3. Call Holder object
        Holder<List<Parameter>> holder = new Holder<List<Parameter>>(list);
        
        // 4. Call Web Services Procedure
        try {
            Results result = admin.callProcedureWithResults(metadataModify, holder);
            
            // put out results
            //System.out.println("getRows size : " + result.getRows().size());
            for (int kl=0; kl<result.getRows().size(); kl++) {
                // just for safekeeping k<=9 :)
                if (kl > 9) {
                    break;
                } else {
                    ResultsRow row = result.getRows().get(kl);
                    
                    for (int km=0; km<row.getColumns().size(); km++) {
                        if (km > 9) {
                            break;
                        } else {
                            Column column = row.getColumns().get(km); 
                            // print error
                            //System.out.println("Value : " + String.valueOf(column.getValue()));
                            res += String.valueOf(column.getValue());
                            column = null;    
                        }
                    }
                    row = null;
                }
            }
            
            result = null;
            admin = null;
            
        } catch (ServerException_Exception e) {
            //e.printStackTrace();
            res = e.toString();
        } catch (UnsupportedTypeException_Exception e) {
            //e.printStackTrace();
            res = e.toString();
        } catch (ValidationException_Exception e) {
            //e.printStackTrace();
            res = e.toString();
        }
        
        return (res.isEmpty() || res.equals("null"))?Consts.getMSG().KMSG_SUCCESS_MESSAGE : res;
    }
}
