/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package LeoLib.tools;

import LeoLib.tools.debug;
import java.util.*;
import LeoLib.tools.*;
import java.sql.SQLException;
import static LeoLib.utils.Constants.*;
import static LeoLib.utils.Constants.DBServer.*;

/**
 *
 * @author leo
 */
public class CommonDAO {
    debug de = new debug(true);
    
    APProperty appProperty = new APProperty(true);
    Properties appProp = appProperty.getAppProp();
    Properties dbProp = appProperty.getDBProp();
    DBKits dbk;
    String envFilter = appProperty.getPropertyValue("env.filter");
    
    public CommonDAO(DBServer dbNow){
        dbk = new DBKits(dbProp, dbNow);
    }
    
    public void setDBServer(DBServer dbNow){
        dbk.setDBServer(dbNow);
    }

    public List getInstanceListViaSQL(String selfSQL) {
        List re = null;

        try{
            dbk.setDBConnection(envFilter);
            dbk.exeSelectSQL(selfSQL);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        de.println("CommonDAO:getInstanceListViaSQL "+dbk.getSQL());
        re = dbk.getList();
        dbk.setDBDisconnect();

        return re;
    }

    public List getInstanceList(String targetTable, List<String> selectorList, Map<String, Object> whereCondition, List<String> orderByList, Integer limit, Integer offset) {
        List re = null;

        dbk.setDBConnection(envFilter);
        dbk.exeSelect(targetTable, selectorList, whereCondition, orderByList, null, null);
        de.println("CommonDAO:getInstanceList "+dbk.getSQL());
        re = dbk.getList();
        dbk.setDBDisconnect();

        return re;
    }

    public HashMap getInstance(String table, ArrayList selectList, HashMap whereCondition) {
        HashMap re = null;

        dbk.setDBConnection(envFilter);

        dbk.exeSelect(table, selectList, whereCondition, null, 1, 0);
        de.println("CommonDAO:getInstance "+dbk.getSQL());
        List<HashMap> result = dbk.getList();
        dbk.setDBDisconnect();
        re = result.get(0);

        return re;
    }
    
    public int setInstanceViaSQL(String selfSQL) {
        int re = 0;

        dbk.setDBConnection(envFilter);
        dbk.exeModifySQL(selfSQL);
        de.println("CommonDAO:setInstanceViaSQL "+dbk.getSQL());
        re = dbk.getModifyResult();
        dbk.setDBDisconnect();

        return re;
    }

    public int setInstance(String table, HashMap keyMap, HashMap whereHM) {
        int re = 0;

        dbk.setDBConnection(envFilter);
        dbk.exeUpdateInsert(table, keyMap, whereHM);
        de.println("CommonDAO:setInstance "+dbk.getSQL());
        re = dbk.getModifyResult();
        dbk.setDBDisconnect();

        return re;
    }

    public int deleteInstance(String table, String id) {
        int re = 0;

        dbk.setDBConnection(envFilter);
        HashMap whereHM = new HashMap();
            whereHM.put("id", id);
        dbk.exeDelete(table, whereHM);
        re = dbk.getModifyResult();
        dbk.setDBDisconnect();

        return re;
    }

}
