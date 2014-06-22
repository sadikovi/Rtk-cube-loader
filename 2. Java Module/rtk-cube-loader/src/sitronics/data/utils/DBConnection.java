package sitronics.data.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;

import oracle.jdbc.driver.OracleDriver;

import sitronics.data.utils.consts.Consts;


public class DBConnection {
    
    /*
     * All the conststans are in Consts class. If you want to use it separately, you should omit comments
        * public static final String JNDIPath = "jdbc/rtk-csv";
        * public static final String JNDIEnv = "java:comp/env";
        * private static final String DBSource = "jdbc:oracle:thin:@192.168.2.165:1521/DWHQA";
        * private static String Username = "SERVICES_DM";
        * private static String Password = "SERVICES_DM";
    */
    
    public DBConnection() {
        super();
    }

    /**
     * Get Direct Connection from database using username and password
     * those parameters are in DBSource, Username, Password
     * it's inappropriate to use just direst connection; it's built for test
     *
     * @return
     */
    public static Connection getDirectConnection() {
        Connection connection = null;
        try {
            DriverManager.registerDriver(new OracleDriver());
            /* connection = DriverManager.getConnection(DBSource, Username, Password); */
            connection = DriverManager.getConnection(Consts.getDB().getDBSource(), Consts.getDB().DB_SCHEME_NAME, Consts.getDB().DB_SCHEME_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static Connection getConnetion() throws NamingException,
                                                   SQLException {
        //driver class name: oracle.jdbc.xa.client.OracleXADataSource
        /* Context envContext = (Context)(new InitialContext()).lookup(JNDIEnv); */
        /* DataSource dtSrc = (DataSource)envContext.lookup(JNDIPath); */
        
        Context envContext = (Context)(new InitialContext()).lookup(Consts.getDB().JNDI_ENV);
        DataSource dtSrc = (DataSource)envContext.lookup(Consts.getDB().JNDI_PATH);
        
        return dtSrc.getConnection();
    }

    public static boolean testConnection() {
        Connection conn = null;
        try {
            conn = DBConnection.getConnetion();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NamingException e) {
            e.printStackTrace();
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean testDirectConnection() {
        Connection conn = null;
        
        conn = DBConnection.getDirectConnection();

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }
}