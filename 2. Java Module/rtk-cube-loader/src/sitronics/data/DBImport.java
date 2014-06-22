package sitronics.data;

import java.sql.CallableStatement;
import java.sql.Connection;

import java.sql.SQLException;

import java.sql.Types;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;

import sitronics.data.utils.Common;
import sitronics.data.utils.DBConnection;
import sitronics.data.utils.consts.Consts;


public class DBImport {
    
    public DBImport() {
        super();
    }

    /**
     * Get user models names created.
     * If username == null then all models names are returned.
     * f.i. "PM001_Test,PM002_DWHQA,PM003_DWHQA10000".
     * 
     * @param username
     * @return
     */
    public static String loadUserModelNames(String username) {
        String res = "[ ";

        Connection connection = DBConnection.getDirectConnection();
        if (connection != null) {
            CallableStatement statement;
            try {
                String callMethod = "begin ? := " 
                                + Consts.getDB().DB_SCHEME_NAME + "."
                                + Consts.getDB().DB_STRUCTURE_PACKAGE + "." 
                                + Consts.getDB().DB_FNC_GET_PM_NAMES
                                + "(null, ?);"
                                + " end;";
                statement = connection.prepareCall(callMethod);
                statement.registerOutParameter(1, Types.VARCHAR);
                if (username.length() > 0) {
                    statement.setString(2, username);
                } else {
                    statement.setString(2, null);
                }
                
                statement.execute();
                
                if (statement.getString(1) != null /*|| statement.getString(1).length() > 0*/) {
                    String[] t = statement.getString(1).split(",");
                    for (int i = 0; i < t.length; i++) {
                        res += "{ \"subjectArea\" : " + "\""  + t[i] + "\""  + ", \"subjectAreaIndex\" : " + i + " }";
                        res += (i<t.length-1)?", " : "";
                    }
                    
                    statement.close();
                    statement = null;
                    res += " ]";
                } else {
                    res = "[ { \"subjectArea\" : \"" + Consts.getMSG().KMSG_ERROR_NO_AVAILABLE_MODEL + "\"" + ", \"subjectAreaIndex\" : " + "-1" + " } ]";
                }
            } catch (SQLException e) {
                e.printStackTrace();
                res =  "[ { \"subjectArea\" : \"\", \"subjectAreaIndex\" : \"-2\" } ]";
            } catch (Exception e) {
                e.printStackTrace();
                res = "[ { \"subjectArea\" : \"\", \"subjectAreaIndex\" : \"-2\" } ]";
            }
        } else {
            res += " { \"subjectArea\" : \"\", \"subjectAreaIndex\" : \"-2\" } ]";
        }
            
        return res;
    }

    /**
     * Method for already existed objects in database.
     * Uses as an alternative for "hasPresentationModel()" method for PMData class.
     * 
     * @param pmName
     * @return
     */
    public static List<String> isAlreadyExist(String pmName) {
        List<String> res = new ArrayList<String>();
        
        Connection connection = DBConnection.getDirectConnection();
        CallableStatement statement;
        
        if (connection != null) {
            String callMethod = "begin ? := " 
                            + Consts.getDB().DB_SCHEME_NAME + "."
                            + Consts.getDB().DB_STRUCTURE_PACKAGE + "." 
                            + Consts.getDB().DB_FNC_GET_PM_NAMES
                            + "(null, ?);"
                            + " end;";
            try {
                statement = connection.prepareCall(callMethod);
                statement.registerOutParameter(1, Types.VARCHAR);
                
                statement.setString(2, null);
                
                statement.execute();
                
                if (statement.getString(1) != null) {
                    String[] t = statement.getString(1).split(",");
                    
                    for (String ti : t) {
                        if (ti.equals(pmName.trim())) {
                            res.add("true");
                            res.add("success");
                            
                            return res;
                            
                        } else {
                            continue;
                        }
                    }
                    
                    res.add("false");
                    res.add("success");
                    
                    return res;
                }
            } catch (SQLException e) {
                res.add("true");
                res.add("#SQLException# " + e.toString());
                
                return res;
            }
        }
        
        res.add("true");
        res.add("Can not connect to database");
        
        return res;    
    }

    /**
     * Load DB data.
     * 
     * @param isOnlyDataReload
     * @param data
     * @param AutoCommit
     * @return
     */
    public static List<String> loadDBData(boolean isOnlyDataReload, HashMap data, String AutoCommit) {
        String res = "";
        String xmlstring = "";
        
        List<String> resList = new ArrayList<String>();
        
        //set auto commit
        boolean autoCommit = true;
        if (AutoCommit.equals("false")) {
            autoCommit = false;
        }
        
        String pmName = (String)data.get(Consts.getPM().PM_NAME);
        String pmDesc = (String)data.get(Consts.getPM().PM_DESC);
        String userName = (String)data.get(Consts.getPM().USER_NAME);
        String structureXML = "";
        String loadStructureXML = "";
        
        Connection connection = DBConnection.getDirectConnection();
        
        // try to set autoCommit flag
        try {
            connection.setAutoCommit(autoCommit);
        } catch (SQLException e) {
            //res = "Can not set auto commit in transaction - " + e.toString();
            e.printStackTrace();
        }
        
        if (connection != null) {
            if (!isOnlyDataReload) {
                CallableStatement createSt;
                
                /* create structure */
                structureXML = (String)data.get(Consts.getEX().COLUMNS_INFO);
                
                try {
                    String createCallMethod = "begin ? := " 
                                        + Consts.getDB().DB_SCHEME_NAME + "."
                                        + Consts.getDB().DB_STRUCTURE_PACKAGE + "." 
                                        + Consts.getDB().DB_FNC_CREATE_CUBE
                                        + "(" + Consts.getDB().DB_SEQ_PRC_ID_NEXTVAL + ", ?, ?, ?, ?);"
                                        + " end;";
                    createSt = connection.prepareCall(createCallMethod);
                    
                    createSt.registerOutParameter(1, Types.CLOB);
                    createSt.setString(2, pmName);
                    createSt.setString(3, pmDesc);
                    createSt.setString(4, structureXML);
                    createSt.setString(5, userName);
                    
                    createSt.execute();
                    
                    if (createSt.getString(1) == null || createSt.getString(1).length() < 0) {
                        res = "#CreateStException# Procedure completed with errors";
                    } else {
                        res = Consts.getMSG().KMSG_SUCCESS_MESSAGE;
                        xmlstring = Common.clobStringConversion(createSt.getClob(1));
                    }
                    createSt.close();
                    createSt = null;
                    
                } catch (SQLException e) {
                    e.printStackTrace();
                    res =  "#CreateStSQLException# " + e.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    res = "#CreateStException# " + e.toString();
                }
            }
            
            CallableStatement insertSt;
            /* insert records to the scheme */
            loadStructureXML = (String)data.get(Consts.getEX().CELLS);
            // @isadikov: if it is a just data reload we set seq nextval, currval otherwise
            // 0 - reload data, 1 - full load with structure rebuild
            String seq_value = "";
            int isFirstLoad = 1;
            if (isOnlyDataReload) {
                seq_value = Consts.getDB().DB_SEQ_PRC_ID_NEXTVAL;
                isFirstLoad = 0;
            } else {
                seq_value = Consts.getDB().DB_SEQ_PRC_ID_CURRVAL;
                isFirstLoad = 1;
            }
            
            if ((res.equals(Consts.getMSG().KMSG_SUCCESS_MESSAGE) && !isOnlyDataReload) || isOnlyDataReload) {
                try {
                    String insertCallMethod = "begin ? := " 
                                        + Consts.getDB().DB_SCHEME_NAME + "."
                                        + Consts.getDB().DB_CC_LOAD_PACKAGE + "." 
                                        + Consts.getDB().DB_FNC_LOAD_CUBE
                                        + "(" 
                                        + seq_value
                                        + ", ?, ?, ?);"
                                        + " end;";
                    insertSt = connection.prepareCall(insertCallMethod);
                    
                    try {
                        insertSt.registerOutParameter(1, Types.INTEGER);
                        insertSt.setString(2, pmName);
                        insertSt.setString(3, loadStructureXML);
                        insertSt.setInt(4, isFirstLoad);
                        
                        insertSt.execute();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        res =  "#InsertStSQLException# " + e.toString();
                    }
                    
                    if (Integer.parseInt(insertSt.getString(1)) == 0) {
                        res = "#InsertStException# Procedure completed with errors";
                    }
                    
                    connection.commit();
                    insertSt.close();
                    insertSt = null;
                    
                    // @isadikov: we do not fill res if it is full reload
                    if (isOnlyDataReload) {
                        res = Consts.getMSG().KMSG_SUCCESS_MESSAGE;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    res =  "#InsertStSQLException# " + e.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    res = "#InsertStException# " + e.toString();
                }
            }
            
            try {
                connection.close();
            } catch (SQLException e) {
                res = "#SQLException#" + e.toString();
            }
            connection = null;
            
            pmName = null;
            pmDesc = null;
            structureXML = null;
            userName = null;
            loadStructureXML = null;
        } else {
            if (res.length() < 0) {
                res = "#SQLException# Can not set a connection to the database";
            }
        }
        
        resList.add(res);
        resList.add(xmlstring);
        
        return resList;
    }

    /**
     * Returns model structure string from database.
     * 
     * @param pmName
     * @return
     */
    public static List<String> getModelStructureFromDB(String pmName) {
        List<String> res = new ArrayList<String>();
        
        Connection connection = DBConnection.getDirectConnection();
        CallableStatement statement;
        
        if (connection != null) {
            String callMethod = "begin ? := " 
                            + Consts.getDB().DB_SCHEME_NAME + "."
                            + Consts.getDB().DB_STRUCTURE_PACKAGE + "." 
                            + Consts.getDB().DB_FNC_GET_MODEL_STRUCTURE
                            + "(" + Consts.getDB().DB_SEQ_PRC_ID_NEXTVAL + ", ?);"
                            + " end;";
            try {
                statement = connection.prepareCall(callMethod);
                
                statement.registerOutParameter(1, Types.CLOB);
                statement.setString(2, pmName);
                
                statement.execute();
                
                if (statement.getClob(1) != null) {
                    res.add(Consts.getMSG().KMSG_SUCCESS_MESSAGE);
                    res.add(Common.clobStringConversion(statement.getClob(1)));
                } else {
                    res.add("error");
                    res.add("#Exception# Returned string is null");
                }
                
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                connection = null;
                
                return res;
            } catch (SQLException e) {
                e.printStackTrace();
                res.add("error");
                res.add("#SQLException# " + e.toString());
                
                return res;
            }
        }
        
        res.add("error");
        res.add("#Exception# Can not connect to database");
        
        return res;
    }
}