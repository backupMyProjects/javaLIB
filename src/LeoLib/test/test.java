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
import static java.lang.System.out;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
        testDate();
        //out.println(customDateTime("2013-07-19 23:35:21", "yyyy-MM-dd hh:mm:ss"));
        //showNowINSecond();
        //testLog();
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
