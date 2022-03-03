package javalib.tools;

import javalib.utils.SystemUtil;
import static java.lang.System.*;
import java.util.Date;

/**
 *
 * @author Leo Chen
 */
public final class Log {

    /**
     * use Log.v, Log.d, Log.i, Log.w, Log.e
     */
    
    public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARN = 4;
    public static final int ERROR = 5;
    public static final int ASSERT = 6;
    
    private static int logLevel = VERBOSE;
    public static int getLogLevel(){
        return logLevel;
    }
    public static void setLogLevel(int input){
        if ( VERBOSE <= input & input <= ASSERT ){
            logLevel = input;
        }
    }
    
    public enum Format{
        FORMAT1,
        FORMAT2,
        FORMAT3,
        FORMAT4
    }
    
    public enum ReturnCode{
        OK,
        FAIL,
        ERROR
    }
    
    private static Format format = Format.FORMAT1;
    public static ReturnCode setFormat(Format input){
        format = input;
        return ReturnCode.OK;
    }

    //-----------------------------------------------------
    public static void v(String tag, String msg) {
        if (logLevel <= VERBOSE){
            printLog("VERBOSE", tag, msg);
        }
    }
    public static void d(String tag, String msg) {
        if (logLevel <= DEBUG){
            printLog("DEBUG", tag, msg);
        }
    }
    public static void i(String tag, String msg) {
        if (logLevel <= INFO){
            printLog("INFO", tag, msg);
        }
    }
    public static void w(String tag, String msg) {
        if (logLevel <= WARN){
            printLog("WARN", tag, msg);
        }
    }
    public static void e(String tag, String msg) {
        if (logLevel <= ERROR){
            printLog("ERROR", tag, msg);
        }
    }
    //-----------------------------------------------------
    
    private static void printLog(Object logLevel, Object tag, Object msg) {
        String output = switch (format) {
            case FORMAT1 -> (new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss(SSS)").format(new Date())) + ", " +
                    logLevel.toString() + ", " +
                    SystemUtil.getPID() + ", " +
                    tag.toString() + ", " +
                    msg.toString();
            case FORMAT2 -> logLevel.toString() + ", " +
                    SystemUtil.getPID() + ", " +
                    tag.toString() + ", " +
                    msg.toString();
            case FORMAT3 -> SystemUtil.getPID() + ", " +
                    tag.toString() + ", " +
                    msg.toString();
            case FORMAT4 -> tag.toString() + ", " +
                    msg.toString();
        };
        out.println(output);
    }

}
