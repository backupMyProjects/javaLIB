/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LeoLib.utils;

/**
 *
 * @author leo
 */
public class Constants {
    // print seq : ERROR > INFO > WARNING > DEBUG...
    public static final int VERBOSE = 4;
    public static final int DEBUG = 3;
    public static final int WARNING = 2;
    public static final int INFO = 1;
    public static final int ERROR = 0;
    public static final int NONE = -1;

    public static enum DBServer {
        MySQL, SQLite, Oracle, SQLServer
    }

    public static final String propertyFolderStr = "properties/";
    public static final String appStr = "app.properties";
    public static final String dbStr = "db.properties";
    
    public static final String prefixMySQL = "jdbc:mysql:"; // 3306
    public static final String prefixSQLite = "jdbc:sqlite:"; 
    public static final String prefixOracle = "jdbc:oracle:"; // 1521
    public static final String prefixSQLServer = "jdbc:sqlserver:"; // 1433
    
    public static final String dbDriverMySQL = "com.mysql.jdbc.Driver";
    public static final String dbDriverSQLite = "org.sqlite.JDBC";
    public static final String dbDriverOracle = "oracle.jdbc.driver.OracleDriver";
    public static final String dbDriverSQLServer = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    
    public static final String dbPrefix_connect = "connect";
    public static final String dbPrefix_driver = "driver";
    public static final String dbPrefix_account = "account";
    public static final String dbPrefix_pwd = "pwd";
}
