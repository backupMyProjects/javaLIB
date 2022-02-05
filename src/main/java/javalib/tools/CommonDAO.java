package javalib.tools;

import javalib.utils.Debug;
import java.util.*;
import java.sql.SQLException;
import static javalib.utils.Constants.*;
import static javalib.utils.Constants.DBServer.*;

/**
 *
 * @author leo
 */
public class CommonDAO {
    Debug de = new Debug(true);
    
    protected APProperty appProperty = new APProperty(true);
    protected Properties appProp = appProperty.getAppProp();
    protected Properties dbProp = appProperty.getDBProp();
    protected DBKits dbk;
    protected String envFilter = appProperty.getPropertyValue("env.filter");
    
    public DBKits getDBKits(){
        return this.dbk;
    }
    
    public CommonDAO(DBServer dbNow){
        dbk = new DBKits(dbProp, dbNow);
    }
    public CommonDAO(DBServer dbNow, boolean debug){
        dbk = new DBKits(dbProp, dbNow);
        de.setDebug(debug);
    }
    
    public void setDBServer(DBServer dbNow){
        dbk.setDBServer(dbNow);
    }

    public List getInstanceListViaSQL(String selfSQL) {
        try{
            dbk.setDBConnection(envFilter);
            dbk.exeSelectSQL(selfSQL);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        de.println("CommonDAO:getInstanceListViaSQL "+dbk.getSQL());
        List re = dbk.getList();
        dbk.setDBDisconnect();

        return re;
    }

    public List getInstanceList(String targetTable,
                                List<String> selectorList,
                                Map<String, Object> whereCondition,
                                List<String> orderByList,
                                Integer limit,
                                Integer offset) {
        dbk.setDBConnection(envFilter);
        dbk.exeSelect(targetTable, selectorList, whereCondition, orderByList, null, null);
        de.println("CommonDAO:getInstanceList "+dbk.getSQL());
        List re = dbk.getList();
        dbk.setDBDisconnect();

        return re;
    }

    public HashMap getInstance(String table, ArrayList selectList, HashMap whereCondition) {
        dbk.setDBConnection(envFilter);

        dbk.exeSelect(table, selectList, whereCondition, null, 1, 0);
        de.println("CommonDAO:getInstance "+dbk.getSQL());
        List<HashMap> result = dbk.getList();
        dbk.setDBDisconnect();
        HashMap re = result.get(0);

        return re;
    }
    
    public int setInstanceViaSQL(String selfSQL) {
        dbk.setDBConnection(envFilter);
        dbk.exeModifySQL(selfSQL);
        de.println("CommonDAO:setInstanceViaSQL "+dbk.getSQL());
        int re = dbk.getModifyResult();
        dbk.setDBDisconnect();

        return re;
    }

    public int setInstance(String table, HashMap keyMap, HashMap whereHM) {
        dbk.setDBConnection(envFilter);
        dbk.exeUpdateInsert(table, keyMap, whereHM);
        de.println("CommonDAO:setInstance "+dbk.getSQL());
        int re = dbk.getModifyResult();
        dbk.setDBDisconnect();

        return re;
    }
    
    public List setInsertNReturnTransactioned(String table, HashMap keyMap, HashMap whereHM) {
        dbk.setDBConnection(envFilter);
        dbk.exeInsert(table, keyMap, null, false);
        de.println("CommonDAO:setInstance "+dbk.getSQL());
        int setRes = dbk.getModifyResult();
        if ( setRes > 0 ){
            switch(dbk.dbNow){
                case MySQL : 
                    dbk.exeSelectSQL("SELECT LAST_INSERT_ID() as last_id"); // MySQL
                    break;
                case SQLite : 
                    //dbk.exeSelectSQL("SELECT LAST_INSERT_ID()"); // SQLite
                    System.out.println("Not Supported, now");
                    break;
                case Oracle : 
                    //dbk.exeSelectSQL("SELECT LAST_INSERT_ID()"); // Oracle
                    System.out.println("Not Supported, now");
                    break;
                case SQLServer : 
                    dbk.exeSelectSQL("SELECT SCOPE_IDENTITY()"); // SQLServer
                    break;
                default : 
                    System.out.println("Not Supported");
            }
            
        }
        //dbk.setCommit();
        List re = dbk.getList();
        dbk.setDBDisconnect(true);

        return re;
    }

    public int deleteInstance(String table, String id) {
        dbk.setDBConnection(envFilter);
        HashMap whereHM = new HashMap();
            whereHM.put("id", id);
        dbk.exeDelete(table, whereHM);
        int re = dbk.getModifyResult();
        dbk.setDBDisconnect();

        return re;
    }

}
