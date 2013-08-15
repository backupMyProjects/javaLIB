/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LeoLib.test;

/**
 * Required : LeoLib.jar
 */

import LeoLib.tools.debug;
import LeoLib.tools.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        //testDate();
        System.out.println(customDateTime("2013-07-19 23:35:21", "yyyy-MM-dd hh:mm:ss"));
    }
    
    public String getPath() {
        return this.getClass().getResource("/").getPath();
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
        
        System.out.println(date.getDate());
        System.out.println(date.getDay());
        System.out.println(date.getMonth()+1);
        System.out.println(date.getYear()+1900);
        
        System.out.println();
        
        System.out.println(at.getDate());
        System.out.println(at.getDay());
        System.out.println(at.getMonth()+1);
        System.out.println(at.getYear()+1900);
        
    }
    
    public static String customDateTime(String dateTime, String dateTimeFormat) throws ParseException{
        Date date = new Date();
        Date at = (new SimpleDateFormat(dateTimeFormat)).parse(dateTime);
        String outputFormat = null;
        if( date.getYear() - at.getYear() == 0 ){// this year
            if( date.getMonth() - at.getMonth() == 0 ){ // this month
                System.out.println("this month");
                if ( date.getDate() - at.getDate() < 7 ){
                    if ( date.getDay() - at.getDay() > 0 ){// this week
                        System.out.println("this week");
                        outputFormat = "E a hh:mm:ss";
                        return getDateTime(outputFormat, at);
                    }else if (date.getDay() - at.getDay() == 0){// today
                        System.out.println("today");
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
