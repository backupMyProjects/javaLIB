package javalib.tools;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author Leo Chen merge @ 2012/3/5
 */
public class Toolets {
    private static final String TAG = Toolets.class.getName();
    
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
