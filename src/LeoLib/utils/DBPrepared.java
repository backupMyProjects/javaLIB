/*
 * DB.java
 *
 * Created on 2007/12/24, 上午 11:28:38
 *
 */
package LeoLib.utils;

import static LeoLib.utils.Constants.*;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
     */
    public void connectInstance() throws Exception {
        Class.forName(driverStr);
        con = DriverManager.getConnection(connectStr, account, password);
    }

    public void disconnectInstance() throws Exception {
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

    /**
     * Temp functions
     */
    ALHM metaList;

    public ALHM getMetaDataList() {
        return metaList;
    }
    ALHM contentList;

    public ALHM getInstanceCon(String sql, List where) throws Exception {
        this.connectInstance();
        ALHM result = getInstance(sql, where);
        this.disconnectInstance();
        return result;
    }

    public ALHM getInstance(String sql, List where) throws Exception {
        rs = getData(sql, where);
        metaList = new ALHM();
        contentList = new ALHM();
        if (rs.getType() != java.sql.ResultSet.TYPE_FORWARD_ONLY) {
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
            HM hm = new HM();
            for (int j = 1; j <= rs.getMetaData().getColumnCount(); j++) {
                hm.put(metaList.get(j - 1), rs.getString(j));
            }
            contentList.add(hm);
        }
        return contentList;
    }

    public ALHM setInstanceCon(String sql, List where) throws Exception {

        this.connectInstance();
        ALHM contentList = setInstance(sql, where);
        this.disconnectInstance();

        return contentList;
    }

    public ALHM setInstance(String sql, List where) throws Exception {

        int result = setData(sql, where, true);
        HM hm = new HM();
        hm.put("result", result);
        ALHM contentList = new ALHM();
        contentList.add(hm);

        return contentList;
    }

    public ALHM setInstance(String sql, List where, boolean autoCommit) throws Exception {

        int result = setData(sql, where, autoCommit);
        HM hm = new HM();
        hm.put("result", result);
        ALHM contentList = new ALHM();
        contentList.add(hm);

        return contentList;
    }
    
    // Basic Function
    protected ResultSet getData(String sql, List condictionList) throws Exception {
        pstmt = con.prepareStatement(sql);
        for (int i = 0; null != condictionList && i < condictionList.size(); i++) {
            pstmt.setString(i + 1, (String) condictionList.get(i));
        }
        rs = pstmt.executeQuery();
        return rs;
    }

    protected int setData(String sql, List where, boolean autoCommit) throws Exception {
        int result = 0;
        con.setAutoCommit(autoCommit);
        pstmt = con.prepareStatement(sql);
        for (int i = 0; null != where && i < where.size(); i++) {
            pstmt.setString(i + 1, (String) where.get(i));
        }
        result = pstmt.executeUpdate();
        return result;
    }
}
