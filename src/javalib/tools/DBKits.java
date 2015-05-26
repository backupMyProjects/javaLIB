/*
 * DBKits.java
 *
 * Created on 2007/12/24, 上午 11:28:38
 *
 */

package javalib.tools;

import javalib.utils.Debug;
import static javalib.utils.Constants.*;
import javalib.utils.Constants.DBServer;
import static javalib.utils.Constants.DBServer.*;
import javalib.utils.DB;
import static javalib.tools.Toolets.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Leo Chen
 */
public class DBKits {
    protected Debug de = new Debug(false);
    
    protected Properties dbProp;
//    protected boolean checked = false;
    protected DB db = new DB();
    protected List<String> li_meta;
    protected List li_value;
    protected int modify_result;
    protected String sql;
    protected DBServer dbNow;
    //private Connection conn;
    
    public DBKits(Properties dbProp){
        this.dbProp = dbProp;
    }
    public DBKits(Properties dbProp, DBServer dbNow){
        this.dbProp = dbProp;
        this.dbNow = dbNow;
    }
    public DBKits(Properties dbProp, boolean debug){
        this.dbProp = dbProp;
        this.de.setDebug(debug);
    }
    public DBKits(Properties dbProp, DBServer dbNow, boolean debug){
        this.dbProp = dbProp;
        this.dbNow = dbNow;
        this.de.setDebug(debug);
    }
    
    public void setDBServer(DBServer input){
        dbNow = input;
    }
    public DBServer getDBServer(){
        return dbNow;
    }
    public void setCommit(){
        try {
            de.println("conn.getAutoCommit() : "+db.getConnection().getAutoCommit());
            if ( db.getConnection().getAutoCommit() ){
                de.println("Can not manually call commit!!");
                return;
            }
            db.getConnection().commit();
        } catch (SQLException ex) {
            Logger.getLogger(DBKits.class.getName()).log(Level.SEVERE, null, ex);
            try {
                db.getConnection().rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DBKits.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    public boolean connectFlag = false;
    public boolean setDBConnection(String envFilter){
        //checking
        String connectStr = null;
        if(null == dbNow){return false;}
        String preStr = null;
        switch (dbNow) {
            case MySQL:
                preStr = envFilter+"."+MySQL+".";
                connectStr = dbProp.getProperty(preStr+dbPrefix_connect);
                if( !isNull(connectStr) ) {db.setConnectString(prefixMySQL+connectStr);}
                break;
            case SQLite:
                JavaFunctions jf = new JavaFunctions();
                preStr = envFilter+"."+SQLite+".";
                connectStr = dbProp.getProperty(preStr+dbPrefix_connect);
                if( !isNull(connectStr) ) {db.setConnectString(prefixSQLite+jf.getHomePath()+connectStr);}
                break;
            case Oracle:
                preStr = envFilter+"."+Oracle+".";
                connectStr = dbProp.getProperty(preStr+dbPrefix_connect);
                if( !isNull(connectStr) ) {db.setConnectString(prefixOracle+connectStr);}
                break;
            case SQLServer:
                preStr = envFilter+"."+SQLServer+".";
                connectStr = dbProp.getProperty(preStr+dbPrefix_connect);
                if( !isNull(connectStr) ) {db.setConnectString(prefixSQLServer+connectStr);}
                break;
            default : 
                de.println("Wrong db Type:"+dbNow);
                return connectFlag;
        }
        
        
        String driverStr = dbProp.getProperty(preStr+dbPrefix_driver);
        String account = dbProp.getProperty(preStr+dbPrefix_account);
        String pwd = dbProp.getProperty(preStr+dbPrefix_pwd);

        de.println("connectStr=" + connectStr);
        de.println("driverStr=" + driverStr);
        de.println("user=" + account);
        de.println("password=" + pwd);

        if( !isNull(driverStr) ) {db.setDriverString(driverStr);}
        if( !isNull(account) ) {db.setAccount(account);}
        if( !isNull(pwd) ) {db.setPassword(pwd);}

        try {
            db.connect();
            //conn = db.getConnection();
            connectFlag = true;
        }catch(Exception ex){
            exHandler(ex);
        }
        return connectFlag;

    }
    
    

    public boolean setDBDisconnect(){
        try{
            db.disconnect();
            connectFlag = false;
        } catch(Exception ex){
            exHandler(ex);
        }

        de.println("setDBDisconnect:connectFlag: " + connectFlag);
        return !connectFlag;
    }
    public boolean setDBDisconnect(boolean commitIt){
        try{
            if ( commitIt ){
                this.setCommit();
            }
            db.disconnect();
            connectFlag = false;
        } catch(Exception ex){
            exHandler(ex);
        }

        de.println("setDBDisconnect:connectFlag: " + connectFlag);
        return !connectFlag;
    }

    protected void exHandler(Exception ex){
        Logger.getLogger(DBKits.class.getName()).log(Level.SEVERE, null, ex);
        if ( "java.sql.SQLException".equals(ex.getClass().getName()) ){
            de.println("SQLException@ERROR : "+this.getClass().getName()+" :" + ex.getClass().getName()+" : "+ex.getMessage());
        }else if ( "com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException e".equals(ex.getClass().getName()) ){
            de.println("SQLException@ERROR : "+this.getClass().getName()+" :" + ex.getClass().getName()+" : "+ex.getMessage());
        }else{
            de.println("Error Occurred! : "+ex.getStackTrace()[ex.getStackTrace().length-1].toString());
            de.println("?@ERROR : "+this.getClass().getName()+" :" + ex.getClass().getName()+" : "+ex.getMessage());
            
            //ex.printStackTrace();
        }
    }

    //--------------------------------------------------------------------------
    private boolean selectFlag = false;
    public boolean exeSelectSQL(String selfSQL){
        sql = selfSQL;
        selectFlag = false;
        try{
            ResultSet rs = db.exeSelectSQL(selfSQL);
            li_meta = new ArrayList();
            li_value = new ArrayList();

            //rs.beforeFirst(); //can not work with db setting : TYPE_FORWARD_ONLY
            if (rs.getType() != java.sql.ResultSet.TYPE_FORWARD_ONLY){
                rs.beforeFirst();
            }
            for(int i=1;rs.next();i++)
            {
                // add meta data
                 if(i == 1){
                     for(int j = 1 ; j <= rs.getMetaData().getColumnCount() ; j++){
                         de.println("No."+j+" Column Name is added." );
                         li_meta.add(rs.getMetaData().getColumnName(j));
                         de.println("Meta List is : "+li_meta.get(j-1).toString());
                     }
                 }

                 // add row data
                 HashMap hm = new HashMap();
                 for(int j = 1 ; j <= rs.getMetaData().getColumnCount() ; j++){
                    hm.put(li_meta.get(j-1), rs.getString(j));
                 }
                 li_value.add(hm);
            }
            selectFlag = true;
        }catch(Exception ex){
            exHandler(ex);
        }

        return selectFlag;
    }


    public boolean exeSelect(String targetTable, List<String> selectorList, Map<String, Object> whereCondition, List<String> orderByList, Integer limit, Integer offset){
        sql = getSelectSQL(targetTable, selectorList, whereCondition, orderByList, limit, offset);
        return exeSelectSQL(sql);
    }

    private boolean modifyFlag = false;
    public boolean exeModifySQL(String selfSQL)
    {
        sql = selfSQL;
        modifyFlag = false;
        try{
            modify_result = db.exeModifySQL(selfSQL);
            modifyFlag = true;
        }catch(Exception ex){
            exHandler(ex);
        }
        
        return modifyFlag;
    }
    public boolean exeModifySQL(String selfSQL, boolean autoCommit)
    {
        sql = selfSQL;
        modifyFlag = false;
        try{
            modify_result = db.exeModifySQL(selfSQL, autoCommit);
            modifyFlag = true;
        }catch(Exception ex){
            exHandler(ex);
        }
        
        return modifyFlag;
    }

    public boolean exeInsert(String targetTable, Map<String, Object> keyMap, Map<String, Object> whereCondition)
    {
        sql = getInsertSQL(targetTable, keyMap, whereCondition);
        return exeModifySQL(sql);
    }
    public boolean exeInsert(String targetTable, Map<String, Object> keyMap, Map<String, Object> whereCondition, boolean autoCommit)
    {
        sql = getInsertSQL(targetTable, keyMap, whereCondition);
        return exeModifySQL(sql, autoCommit);
    }

    public boolean exeUpdate(String targetTable, Map<String, Object> keyMap, Map<String, Object> whereCondition)
    {
        sql = getUpdateSQL(targetTable, keyMap, whereCondition);
        return exeModifySQL(sql);
    }

    public boolean exeUpdateInsert(String targetTable, Map<String, Object> keyMap, Map<String, Object> whereCondition)
    {
        sql = getUpdateSQL(targetTable, keyMap, whereCondition);
        de.println("Run SQL : "+sql);
        exeModifySQL(sql);
        if ( 0 == modify_result ){
            sql = getInsertSQL(targetTable, keyMap, null);
            de.println("Run SQL : "+sql);
            exeModifySQL(sql);
        }


        return modifyFlag;
    }

    public boolean exeDelete(String targetTable, Map<String, Object> whereCondition){
        sql = getDeleteSQL(targetTable, whereCondition);
        exeModifySQL(sql);
        return modifyFlag;
    }


    //--------------------------------------------------------------------------
    public List getList(){
        if (!selectFlag){
            de.println("You must run sql first");
        }
        return li_value;
    }
    
    public List getMetaList(){
        if (!selectFlag){
            de.println("You must run sql first");
        }
        return li_meta;
    }
    
    public int getModifyResult(){
        if (!modifyFlag){
            de.println("You must run sql first");
        }
        return modify_result;
    }



    //--------------------------------------------------------------------------

    private String getSelectSQL(String targetTable, List<String> selectorList, Map<String, Object> whereCondition, List<String> orderByList, Integer limit, Integer offset){
    	String re = "";

        // ToDo
        if ( targetTable == null || "".equals(targetTable) ) {
            return "";
        }

        re += "SELECT " + selectorCombine(selectorList);
        re += " FROM "  + "`"+targetTable+"`";
        if (whereCondition != null && !whereCondition.isEmpty()) { re += " WHERE " + whereCombine(whereCondition) ; }
        if (orderByList != null && !orderByList.isEmpty()) { re += " ORDER by " + orderCombine(orderByList) ; }
        if (limit != null) { re += " LIMIT " + limit ; }
        if (offset != null) { re += " OFFSET " + offset ; }

    	return re;
    }
    

    private String tag = this.toString() +" : ";
    private String getInsertSQL(String targetTable, Map<String, Object> keyMap, Map<String, Object> whereCondition){
    	String re = "";
        re += "INSERT into " + "`"+targetTable+"`" + 
              "("+keyCombine(keyMap)+")" +
              " VALUES " + "("+valueCombine(keyMap)+")";
        if (whereCondition != null && !whereCondition.isEmpty()) {
            re += " WHERE " + whereCombine(whereCondition) ;
        }
    	return re;
    }

    private String getUpdateSQL(String targetTable, Map<String, Object> keyMap, Map<String, Object> whereCondition){
    	String re = "";
        re += "UPDATE " + "`"+targetTable+"`" + 
              " SET "+KeyValueCombine(keyMap);
        if (whereCondition != null) {
            re += " WHERE " + whereCombine(whereCondition) ;
        }
    	return re;
    }

    private String getDeleteSQL(String targetTable, Map<String, Object> whereCondition){
        String re = "";
        
        if ( whereCondition == null && whereCondition.isEmpty() ) {
            return "";
        }// prevent the clean table statement.
        
        re += "Delete from " + "`"+targetTable+"`";
        re += " WHERE " + whereCombine(whereCondition) ;
    	return re;
    }


    //--------------------------------------------------------------------------
    
    private String selectorCombine(List<String> selectorList){
        String re = "";
        if ( null != selectorList ) {
            String sepor = "`, `";

            for(String selector : selectorList){
                    de.println(tag+selector);
                    re += sepor + selector;
            }
            re += "`";
            re = re.replaceFirst(sepor, "`");
        } else {
            re = "*";
        }
    	

        return re;
    }

    private String orderCombine(List<String> orderByList){
        String re = "";
        String sepor = "`, `";

        for (String order : orderByList) {
            de.println(tag + order);
            re += sepor + order;
        }
        re += "`";
        re = re.replaceFirst(sepor, "`");
        return re;
    }

    private String whereCombine(Map<String, Object> whereCondition){
        String re = "";
        String sepor = " and ";
        for (String key : whereCondition.keySet()) {
            de.println(tag + key);
            re += sepor + key + " = '" + whereCondition.get(key) + "'";
        }
        re = re.replaceFirst(sepor, "");
        return re;
    }

    private String keyCombine(Map<String, Object> hm){
    	String re = "";
    	String sepor = "`, `";
    	for(String key : hm.keySet()){
                de.println(tag+key);
    		re += sepor + key;
        }
    	re += "`";
    	re = re.replaceFirst(sepor, "`");
    	return re;
    }

    private String valueCombine(Map<String, Object> hm){
    	String re = "";
    	String sepor = "', '";
    	for(Object key : hm.values()){
                de.println(tag+key);
    		re += sepor + key;
        }
    	re += "'";
    	re = re.replaceFirst(sepor, "'");
    	return re;
    }

    private String KeyValueCombine(Map<String, Object> hm){
    	String re = "";
    	String sepor = " , `";
    	for(String key : hm.keySet()){
                de.println(tag+key);
                if ( hm.get(key) != null ){
                    re += sepor + key + "` = '" + hm.get(key) + "'";
                }
        }
    	re = re.replaceFirst(sepor, "`");
    	return re;
    }


    //--------------------------------------------------------------------------

    public String getSQL(){
        return sql;
    }
    
}
