/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LeoLib.test;

/**
 * Required : LeoLib.jar
 */

import LeoLib.tools.*;
import LeoLib.tools.debug;
import LeoLib.utils.DBPrepared;
import static LeoLib.utils.DBPrepared.MYSQL_DRIVER;
import static java.lang.System.out;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author leo
 */
public class test {

    static debug de = new debug(true);
    
    /**
     *
     * @param args
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
//        String test = "";
//        test.getClass().getName();
//        System.out.println(test.getClass().getName());
//        System.out.println(java.lang.String.class.getName());
        
        testDBP("getData");
        
    }
    
    public static void testDBP(String target) throws Exception{
        DBPrepared dbp = new DBPrepared(
                MYSQL_DRIVER, 
                "jdbc:mysql://leochen.i234.me:3306/test?useUnicode=true&characterEncoding=utf-8",
                "acer",
                "qpwoeiruty"
        );
        
        ArrayList valueList = null;
        List result = null;
        switch(target){
            case "getData" : 
                valueList = new ArrayList();
                valueList.add("22");
                valueList.add("1999");
                valueList.add(1);
                dbp.connect();
                result = dbp.getData(
                        "SELECT * "
                        + "FROM bookshelf "
                        + "WHERE user_id = ? AND book_id = ? "
                        + "ORDER BY ? ASC", valueList);
                out.println("size : "+result.size());
                dbp.commit();
                dbp.disconnect();
                Toolets.printArrayListHashMap((ArrayList<HashMap<String, String>>) result);
                break;
            case "setData:Insert" :
                valueList = new ArrayList();
                valueList.add("22");
                valueList.add("1999");
                valueList.add("cover");
                valueList.add("title0");
                valueList.add("number");
                valueList.add("2");
                dbp.connect();
                result = dbp.setData(
                        "INSERT INTO bookshelf "
                        + "(USER_ID, BOOK_ID, BOOK_COVER, BOOK_TITLE, transNo, CHANNEL_ID) "
                        + "VALUES (?,?,?,?,?,?)", valueList);
                out.println("size : "+result.size());
                dbp.commit();
                dbp.disconnect();
                Toolets.printArrayListHashMap((ArrayList<HashMap<String, String>>) result);
                break;
                
            case "setData:Update" :
                valueList = new ArrayList();
                valueList.add("cover3");
                valueList.add("22");
                valueList.add("1999");
                dbp.connect();
                result = dbp.setData(
                        "UPDATE bookshelf SET "
                        + "BOOK_COVER = ? "
                        + "WHERE USER_ID = ? AND book_ID = ?", valueList);
                out.println("size : "+result.size());
                dbp.commit();
                dbp.disconnect();
                Toolets.printArrayListHashMap((ArrayList<HashMap<String, String>>) result);
                break;
                
            case "setget" :
                valueList = new ArrayList();
                valueList.add("cover4");
                valueList.add("22");
                valueList.add("1999");
                dbp.connect();
                result = dbp.setData(
                        "UPDATE bookshelf SET "
                        + "BOOK_COVER = ? "
                        + "WHERE USER_ID = ? AND book_ID = ?", valueList);
                out.println("size : "+result.size());
                valueList = new ArrayList();
                valueList.add("22");
                valueList.add("1999");
                result = dbp.getData(
                        "SELECT * "
                        + "FROM bookshelf "
                        + "WHERE USER_ID = ? AND book_ID = ?", valueList);
                out.println("size : "+result.size());
                dbp.commit();
                dbp.disconnect();
                Toolets.printArrayListHashMap((ArrayList<HashMap<String, String>>) result);
                break;
            default :
                out.println("No Valid Target");
        }
    }
    
    public String getPath() {
        return this.getClass().getResource("/").getPath();
    }
    
    public static void showNowINSecond(){
        out.println(System.currentTimeMillis()/1000);
    }
    
    public static void testLog(){
        Log.setLogLevel(Log.INFO);
        Log.setFormat(Log.Format.FORMAT1);
        Log.v(Thread.currentThread().getName(), "Hello");
        Log.d(Thread.currentThread().getName(), "Hello");
        Log.i(Thread.currentThread().getName(), "Hello");
        Log.w(Thread.currentThread().getName(), "Hello");
        Log.e(Thread.currentThread().getName(), "Hello");
    }
    
    public static void testDate() throws ParseException{
        Date date = new Date();
        Date at = (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).parse("2013-07-17 23:35:21");
        
        out.println(date.getDate());
        out.println(date.getDay());
        out.println(date.getMonth()+1);
        out.println(date.getYear()+1900);
        
        out.println();
        
        out.println(at.getDate());
        out.println(at.getDay());
        out.println(at.getMonth()+1);
        out.println(at.getYear()+1900);
        
    }
    
    public static String customDateTime(String dateTime, String dateTimeFormat) throws ParseException{
        Date date = new Date();
        Date at = (new SimpleDateFormat(dateTimeFormat)).parse(dateTime);
        String outputFormat = null;
        if( date.getYear() - at.getYear() == 0 ){// this year
            if( date.getMonth() - at.getMonth() == 0 ){ // this month
                out.println("this month");
                if ( date.getDate() - at.getDate() < 7 ){
                    if ( date.getDay() - at.getDay() > 0 ){// this week
                        out.println("this week");
                        outputFormat = "E a hh:mm:ss";
                        return getDateTime(outputFormat, at);
                    }else if (date.getDay() - at.getDay() == 0){// today
                        out.println("today");
                        outputFormat = "a hh:mm:ss";
                        return getDateTime(outputFormat, at);
                    }else{
                        outputFormat = "MM-dd hh:mm:ss";
                        return getDateTime(outputFormat, at);
                    }
                }else{
                    outputFormat = "MM-dd hh:mm:ss";
                    return getDateTime(outputFormat, at);
                }
            } else {
                outputFormat = "MM-dd hh:mm:ss";
                return getDateTime(outputFormat, at);
            }
        }else{
            outputFormat = "yyyy-MM-dd hh:mm:ss";
            return getDateTime(outputFormat, at);
        }
        
    }
    
    public static String getDateTime(String pattern, Object... dates) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        if (dates.length == 1) {
            //System.out.println( dates[0].getClass().getName() );
            if ("java.util.Date".equals(dates[0].getClass().getName())) {
                return sdf.format((Date) dates[0]);
            } else if ("java.lang.Long".equals(dates[0].getClass().getName())) {
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis((Long) dates[0]);
                //System.out.println("getTimeInMillis : "+cal.getTimeInMillis());
                Date date = cal.getTime();
                return sdf.format(date);
            } else {/* nothing */

            }

        }
        return sdf.format(new Date());
        //( dates.length == 1 ) ? return sdf.format(dates[0]) : return sdf.format(new Date());
    }

    
}
