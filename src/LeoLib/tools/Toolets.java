/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LeoLib.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

/**
 *
 * @author leo merge @ 2012/3/5
 */
public class Toolets {
    private static String TAG = Toolets.class.getName();
    
    /* Data Check */
    
    public static boolean isNull(Object input) {
        return (null == input);
    }
    
    public static boolean notEmpList(List input) {
        return (input != null && input.size() > 0);
    }

    public static boolean isNumber(String input) {
    	if( isNull(input) ){return false;}
        try {
            Integer check = Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            System.err.println(e);
            return false;
        }
    }
    
    // allow : date , Long(time million second)
    public static String getDateTime(String pattern, Object... dates) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        if (dates.length == 1) {
            if (null != dates[0].getClass().getName()) {
                //System.out.println( dates[0].getClass().getName() );
                switch (dates[0].getClass().getName()) {
                    case "java.util.Date":
                        return sdf.format((Date) dates[0]);
                    case "java.lang.Long":
                        Calendar cal = Calendar.getInstance();
                        cal.setTimeInMillis((Long) dates[0]);
                        //out.println("getTimeInMillis : "+cal.getTimeInMillis());
                        Date date = cal.getTime();
                        return sdf.format(date);
                    default:
                        break;
                }
            }
        }
        return sdf.format(new Date());
        //( dates.length == 1 ) ? return sdf.format(dates[0]) : return sdf.format(new Date());
    }

    /* Data Sort */
    
    public static Integer[] sortInt(String... inputs) {
        Integer[] intArr = null;
        if (inputs.length > 0) {
            intArr = new Integer[inputs.length];
            for (int i = 0; i < inputs.length; i++) {
            	if ( isNumber(inputs[i]) ){
                    intArr[i] = Integer.parseInt(inputs[i]);
            	}else{
            		//intArr[i] = -65535;
            		System.out.println("FORMAT ERROR : " + inputs[i]);
            		return null;
            	}
            }

            Arrays.sort(intArr);
        } else {
            intArr = new Integer[0];
        }

        return intArr;
    }
    

}
