/*
 * DB.java
 *
 * Created on 2007/12/24, 上午 11:28:38
 *
 */
package LeoLib.utils;

import static LeoLib.utils.Constants.*;
import java.sql.*;
import java.util.*;

/**
 *
 * @author Leo Chen
 *  TODO
 * 1. setData : update / insert / delete(?)
 * 2. pure functions
 * 3. auto commit checking
 * 4. SQLite support
 */
public class DBPrepared {
    //debug de = new debug(false);
    
    public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";

    private String driverStr;
    private String connectStr;
    private String account;
    private String password;
    public void setDriver(String driverStr) {this.driverStr = driverStr;}
    public String getDriver() {return this.driverStr;}
    public void setConnectStr(String connectStr) {this.connectStr = connectStr;}
    public String getConnectStr() {return this.connectStr;}
    public void setAccount(String account) {this.account = account;}
    public void setPassword(String password) {this.password = password;}

    private Connection con = null;
    public Connection getConnection() {return con;}
    public void setConnection(Connection con) {this.con = con;}
    
    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    /** not support now.
    public DBPrepared(String driverStr, String connectStr) {
        this.driverStr = driverStr;
        this.connectStr = connectStr;
    }
    */
    public DBPrepared(String driverStr, String connectStr, String account, String password) {
        this.driverStr = driverStr;
        this.connectStr = connectStr;
        this.account = account;
        this.password = password;
    }

    /** not support sqlite now : 2014.05.22 */
    public void connect() throws Exception {
        Class.forName(driverStr);
        con = DriverManager.getConnection(connectStr, account, password);
    }

    public void disconnect() throws Exception {
        if (null != rs) {rs.close();}
        if (null != pstmt) {pstmt.close();}
        if (null != con) {con.close();}
    }
    
    /** Temp functions */
    List<String> metaList;
    public List<String> getMetaDataList(){return metaList;}
    List contentList;
    public List getDataList(String sql, List<String> where) throws Exception{
        rs = getData(sql, where);
        metaList = new ArrayList();
        contentList = new ArrayList();
        if (rs.getType() != java.sql.ResultSet.TYPE_FORWARD_ONLY){
            rs.beforeFirst();
        }
        for (int i = 1; rs.next(); i++) {
            // add meta data
            if (i == 1) {
                for (int j = 1; j <= rs.getMetaData().getColumnCount(); j++) {
                    metaList.add(rs.getMetaData().getColumnName(j));
                }
            }

            // add row data
            HashMap hm = new HashMap();
            for (int j = 1; j <= rs.getMetaData().getColumnCount(); j++) {
                hm.put(metaList.get(j - 1), rs.getString(j));
            }
            contentList.add(hm);
        }
        return contentList;
    }
    
    public List getDataListConn(String sql, List<String> where) throws Exception{
        this.connect();
        List result = getDataList(sql, where);
        this.disconnect();
        return result;
    }

    /** END Temp functions */
    
    protected ResultSet getData(String sql, List<String> where) throws Exception {
        pstmt = con.prepareStatement(sql);
        for (int i = 0; i < where.size(); i++) {
            pstmt.setString(i+1, where.get(i));
        }
        rs = pstmt.executeQuery();
        return rs;
    }

    /** Not Yet */
    /*
    protected int setData(String sql) throws Exception {
        int result = 0;
        result = pstmt.executeUpdate(sql);
        return result;
    }

    protected int setData(String sql, boolean autoCommit) throws Exception {
        int result = 0;
        con.setAutoCommit(autoCommit);
        result = pstmt.executeUpdate(sql);
        return result;
    }
    */

}
