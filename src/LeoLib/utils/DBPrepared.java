/*
 * DB.java
 *
 * Created on 2007/12/24, 上午 11:28:38
 *
 */
package LeoLib.utils;

import static java.lang.System.out;
import java.sql.*;
import java.util.List;

/**
 *
 * @author Leo Chen TODO 1. setData : update / insert / delete(?) 2. pure
 * functions 3. auto commit checking 4. SQLite support
 */
public class DBPrepared {
    //debug de = new debug(false);

    public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";

    private String driverStr;
    private String connectStr;
    private String account;
    private String password;
    private Connection con = null;

    public void setDriver(String driverStr) {this.driverStr = driverStr;}
    public String getDriver() {return this.driverStr;}
    public void setConnectStr(String connectStr) {this.connectStr = connectStr;}
    public String getConnectStr() {return this.connectStr;}
    public void setAccount(String account) {this.account = account;}
    public void setPassword(String password) {this.password = password;}
    public Connection getConnection() {return con;}
    public void setConnection(Connection con) {this.con = con;}

    private PreparedStatement pstmt = null;
    private ResultSet rs = null;

    /**
     * not support now. public DBPrepared(String driverStr, String connectStr) {
     * this.driverStr = driverStr; this.connectStr = connectStr; }
     */
    public DBPrepared(String driverStr, String connectStr, String account, String password) {
        this.driverStr = driverStr;
        this.connectStr = connectStr;
        this.account = account;
        this.password = password;
    }

    /**
     * not support sqlite now : 2014.05.22
     * Default AutoCommit : false
     */
    public void connect() throws Exception {
        Class.forName(driverStr);
        con = DriverManager.getConnection(connectStr, account, password);
        con.setAutoCommit(false);
    }

    public void disconnect() throws Exception {
        if (null != rs) {
            rs.close();
        }
        if (null != pstmt) {
            pstmt.close();
        }
        if (null != con) {
            con.close();
        }
    }
    
    public void setAutoCommit(boolean flag) throws Exception {
        con.setAutoCommit(flag);
    }
    
    public void commit() throws Exception {
        con.commit();
    }
    
    public void rollback() throws Exception {
        con.rollback();
    }

    /**
     * Temp functions
     */
    ALHM metaList;

    public ALHM getMetaDataList() {
        return metaList;
    }

    public ALHM getData(String sql, List valueList) throws Exception {
        rs = exeQuery(sql, valueList);
        metaList = new ALHM();
        ALHM resultList = new ALHM();
        if (rs.getType() != java.sql.ResultSet.TYPE_FORWARD_ONLY) {
            rs.beforeFirst();
        }
        for (int i = 1; rs.next(); i++) {
            // add meta data
            if (i == 1) {
                for (int j = 1; j <= rs.getMetaData().getColumnCount(); j++) {
                    metaList.add(rs.getMetaData().getColumnLabel(j));
                }
            }
            // add row data
            HM hm = new HM();
            for (int j = 1; j <= rs.getMetaData().getColumnCount(); j++) {
                hm.put(metaList.get(j - 1), rs.getString(j));
            }
            resultList.add(hm);
        }
        return resultList;
    }

    public ALHM setData(String sql, List valueList) throws Exception {
        int result = exeUpdate(sql, valueList);
        HM hm = new HM();
        hm.put("result", result);
        ALHM resultList = new ALHM();
        resultList.add(hm);
        out.println("test");
        return resultList;
    }
    
    // Basic Function
    protected ResultSet exeQuery(String sql, List valueList) throws Exception {
        pstmt = con.prepareStatement(sql);
        for (int i = 0; null != valueList && i < valueList.size(); i++) {
            Object item = valueList.get(i);
            //out.println(item.getClass().getName());
            if ( "java.lang.String".equals(item.getClass().getName()) ){
                pstmt.setString(i + 1, (String) item);
            }else if( "java.lang.Integer".equals(item.getClass().getName()) ){
                pstmt.setInt(i + 1, (Integer) item);
            }
            
        }
        rs = pstmt.executeQuery();
        return rs;
    }

    protected int exeUpdate(String sql, List valueList) throws Exception {
        int result = 0;
        pstmt = con.prepareStatement(sql);
        for (int i = 0; null != valueList && i < valueList.size(); i++) {
            pstmt.setString(i + 1, (String) valueList.get(i));
        }
        result = pstmt.executeUpdate();
        return result;
    }
}
