package LeoLib.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author leo
 */
public class StringUtil {

    public static void println(Object input) {
        System.out.println(input.toString());
    }

    // allow : date , Long(time million second)
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

    public static String stripHTMLTag(String srcStr) {
        String regex = "<(?![!/]?[ABIU][>\\s])[^>]*>|&nbsp;";
        return srcStr.replaceAll(regex, "");
    }

    public static Integer[] sortInt(String... inputs) {
        Integer[] intArr = null;
        if (inputs.length > 0) {
            intArr = new Integer[inputs.length];
            for (int i = 0; i < inputs.length; i++) {
                intArr[i] = Integer.parseInt(inputs[i]);
            }

            Arrays.sort(intArr);
        } else {
            intArr = new Integer[0];
        }

        return intArr;
    }

    // number string with spliter
    public static String sortedString(String spliter, String... inputs) {
        String result = "";

        if (isNumber(spliter)) {
            spliter = ",";
        }// if user forgot to set the spliter

        Integer[] intArr = sortInt(inputs);
        for (int i = 0; i < intArr.length; i++) {
            //System.out.println("intArr : "+intArr[i]);
            result += (i != intArr.length - 1) ? intArr[i] + spliter : intArr[i];
        }

        return result;
    }

    public static boolean isNumber(String input) {
        boolean isNumber = false;

        try {
            Integer check = Integer.parseInt(input);
            isNumber = true;
        } catch (NumberFormatException nfe) {
            isNumber = false;
        }

        return isNumber;
    }

    public static String md5(String input) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes());
            byte[] digest = md.digest();
            result = toHexString(digest);
            //System.out.println(result);
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return result;
    }

    private static String toHexString(byte[] in) {
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < in.length; i++) {
            String hex = Integer.toHexString(0xFF & in[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static boolean isEmpStr(String input) {
        return (checkTwoStr("", input)) ? true : false;
    }

    public static boolean isNull(Object input) {
        return (input == null) ? true : false;
    }

    public static boolean checkTwoStr(String a, String b) {
        return (a.equals(b)) ? true : false;
    }

    public static String urlEncode(String input, String encode) {
        try {
            return URLEncoder.encode(input, encode);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "encoding failed";
        }

    }

    public static String urlDecode(String input, String decode) {
        try {
            return URLDecoder.decode(input, decode);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "encoding failed";
        }

    }
}
