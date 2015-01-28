/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LeoLib.tools;

import LeoLib.utils.HM;

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
import static java.lang.System.*;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

/**
 *
 * @author leo merge @ 2012/3/5
 */
public class Toolets {
    private static String TAG = Toolets.class.getName();
    
    /* Data Check */
    public static boolean notEmpList(List input) {
        return (input != null && input.size() > 0);
    }

    public static boolean isNumber(String input) {
    	if( isNull(input) ){return false;}
        try {
            Integer check = Integer.parseInt(input);
            return true;
        } catch (NumberFormatException nfe) {
            System.err.println(nfe);
            return false;
        }
    }
    
    @Deprecated
    public static boolean isEmpStr(String input) {
        return (checkTwoStr("", input));
    }
    public static boolean isEmpty(String input) {
    	if( isNull(input) ){return true;}
        return (checkTwoStr("", input));
    }

    public static boolean isNull(Object input) {
        return (null == input);
    }

    public static boolean checkTwoStr(String a, String b) {
    	if ( !isNull(a) && !isNull(b) ){return (a.equals(b));}
        else { return isNull(a) && isNull(b);}
    }

    /* String Tool */
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

    public static String stripHTMLTag(String srcStr) {
        String regex = "<(?![!/]?[ABIU][>\\s])[^>]*>|&nbsp;";
        return srcStr.replaceAll(regex, "");
    }

    public static String md5(String input) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes());
            byte[] digest = md.digest();
            result = toHexString(digest);
            //out.println(result);
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

    public static String getRandomString(int stringLength) {

        String[] baseString = {
        	"0", "1", "2", "3",
            "4", "5", "6", "7", "8", "9",
            "a", "b", "c", "d", "e",
            "f", "g", "h", "i", "j",
            "k", "l", "m", "n", "o",
            "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y",
            "z", "A", "B", "C", "D",
            "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S",
            "T", "U", "V", "W", "X", "Y", "Z"
        };
        Random random = new Random();
        int length = baseString.length;
        String randomString = "";
        for (int i = 0; i < length; i++) {
            randomString += baseString[random.nextInt(length)];
        }
        random = new Random(System.currentTimeMillis());
        String resultStr = "";
        for (int i = 0; i < stringLength; i++) {
            resultStr += randomString.charAt(random.nextInt(randomString.length() - 1));
        }
        return resultStr;
    }

    public static String urlEncode(String input, String encode) {
        try {
            return URLEncoder.encode(input, encode);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "ENCODE FAILED";
        }

    }

    public static String urlDecode(String input, String decode) {
        try {
            return URLDecoder.decode(input, decode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "DECODE FAILED";
        }

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
            		out.println("FORMAT ERROR : " + inputs[i]);
            		return null;
            	}
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
            //out.println("intArr : "+intArr[i]);
            result += (i != intArr.length - 1) ? intArr[i] + spliter : intArr[i];
        }

        return result;
    }

    /* File Tool */
    
    static int stringBufferSize = 1000;
    static int charArrBuffSize = 1024;
    public static String readFile2String(String filePath) {
        try {
            StringBuffer fileData = new StringBuffer(stringBufferSize);
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            char[] buf = new char[charArrBuffSize];
            int numRead = 0;
            while ((numRead = reader.read(buf)) != -1) {
                String readData = String.valueOf(buf, 0, numRead);
                fileData.append(readData);
                buf = new char[charArrBuffSize];
            }
            reader.close();
            return fileData.toString();

        } catch (IOException e) {
            err.println(e);
        }

        return null;
    }
    
    /** TODO : There is an error : out.close(); **/
    public static boolean writeString2File(String input, String filePath) {
        try {
            
            File file = createFile(filePath);
            //out.println(file.getAbsolutePath());
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            
            //BufferedWriter out = new BufferedWriter(new FileWriter(filePath));
            bw.write(input);
            bw.close();
            return true;
        } catch (IOException e) {
            err.println(e);
            //out.close();
            return false;
        }
    }

    /** Download File **/
    
    static int byteArrBufferSize = 153600;
    public static boolean downloadFile(String inFileURL, String outFilePath) {
        try {
            out.println("Connecting");
            URL url = new URL(inFileURL);
            url.openConnection();
            InputStream reader = url.openStream();

            LeoLib.tools.Toolets.createParentFolder(outFilePath);
            FileOutputStream writer = new FileOutputStream(outFilePath);
            byte[] buffer = new byte[byteArrBufferSize];
            int totalBytesRead = 0;
            int bytesRead = 0;

            //out.println("Reading file "+buffer.length/1024+"KB blocks at a time.");
            long startTime = System.currentTimeMillis();

            while ((bytesRead = reader.read(buffer)) > 0) {
                writer.write(buffer, 0, bytesRead);
                buffer = new byte[byteArrBufferSize];
                totalBytesRead += bytesRead;
            }

            long endTime = System.currentTimeMillis();

            out.println("Done. " + (new Integer(totalBytesRead).toString()) + " bytes read (" + (new Long(endTime - startTime).toString()) + " millseconds).");
            writer.close();
            reader.close();
            return true;
        } catch (Exception e) {
            //e.printStackTrace();
            err.println(TAG);
            err.println(e);
            return false;
        }
    }
    
    static int currentTimeout = 30000;
    public static void setCurrentTimeout(int input){currentTimeout = input;}
    public static int getCurrentTimeout(){return currentTimeout;}
    public static boolean downloadFile2(String inFileURL, String outFilePath){
        return downloadFile2(inFileURL, outFilePath, currentTimeout);
    }
    public static boolean downloadFile2(String inFileURL, String outFilePath, int timeout) {
        boolean result = false;
        FileOutputStream fileos = null;
        try {
            //boolean eof = false;
            HttpURLConnection connect = (HttpURLConnection) (new URL(inFileURL))
                    .openConnection();
            connect.setRequestMethod("GET");
            connect.setReadTimeout(timeout);
            connect.setDoOutput(true);
            connect.connect();

            // String PATH_op = Environment.getExternalStorageDirectory() +
            // "/download/" + targetFileName;
            LeoLib.tools.Toolets.createParentFolder(outFilePath);
            fileos = new FileOutputStream(new File(outFilePath));

            InputStream is = connect.getInputStream();
            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = is.read(buffer)) > 0) {
                fileos.write(buffer, 0, len1);
            }

            fileos.close();
            result = true;
        } catch (SocketTimeoutException e) {
            _deleteFile(fileos, outFilePath);
            err.println(e);
        } catch (MalformedURLException e) {
            _deleteFile(fileos, outFilePath);
            err.println(e);
        } catch (ProtocolException e) {
            _deleteFile(fileos, outFilePath);
            err.println(e);
        } catch (FileNotFoundException e) {
            _deleteFile(fileos, outFilePath);
            err.println(e);
        } catch (IOException e) {
            _deleteFile(fileos, outFilePath);
            err.println(e);
        }finally{
            _closeFileOS(fileos);
            return result;
        }
    }
    private static void _deleteFile(FileOutputStream fileos, String outFilePath) {
        if (null != fileos) {
            try {
                fileos.close();
                fileos = null;
                //System.gc();
                File file = new File(outFilePath);
                out.println("file exists : "+file.exists());
                //new File(outFilePath).deleteOnExit();
                file.delete();
                out.println("file exists : "+file.exists());
            } catch (IOException ex) {
                err.println(ex);
            }
        }
    }
    private static void _closeFileOS(FileOutputStream fileos) {
        if (null != fileos) {
            try {
                fileos.close();
                fileos = null;
                //System.gc();
            } catch (IOException ex) {
                err.println(ex);
            }
        }
    }

    /** File & Folder **/
    
    public static boolean createParentFolder(String filePath) {
        File parent = new File(filePath).getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            throw new IllegalStateException("Couldn't create dir: " + parent);
        }
        //out.println("Create Folder." + parent.getAbsolutePath());

        return false;
    }
    
    public static File createFile(String filePath) {
        File file = new File(filePath);
        out.println(file.getAbsolutePath());
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
    
    /** Jason **/
    
    public static Map Json2Map(String json) {
        
        if (null == json) {return null;}
        
        Map map = null;
        ObjectMapper mapper = new ObjectMapper();

        try {

            //convert JSON string to Map
            map = mapper.readValue(json,
                    new TypeReference<Map>() {});

            //out.println("Json2Map:"+map);
            
            return map;

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static String Map2Json(Map map) {
        if (null == map) {return null;}

        try {

            ObjectMapper mapper = new ObjectMapper();
            String json = "";

            //convert map to JSON string
            json = mapper.writeValueAsString(map);
		//out.println("Map2Json:"+json);

            return json;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static boolean Map2JsonFile(Map map, String filePath) {
        if (null == map || null == filePath) {return false;}
        
        ObjectMapper mapper = new ObjectMapper();

        try {
            // write JSON to a file
            File file = Toolets.createFile(filePath);
            mapper.writeValue(file, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static Map JsonFile2Map(String jsonFile){
        if (null == jsonFile) {return null;}
        Map map = null;
        try {

            ObjectMapper mapper = new ObjectMapper();

            // read JSON from a file
            map = mapper.readValue(
                    new File(jsonFile),
                    new TypeReference<Map>() {});

            //out.println("JsonFile2Map:"+map);
            
            return map;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static void Serialization(Object input , String file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(input);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static Object DeSerialization(String file) {
    	Object result = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            result = (Object) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /** Print **/
    public static void printArrayListHashMap(ArrayList<HashMap<String, String>> inputALHM) {
        Iterator<HashMap<String, String>> ita = inputALHM.iterator();
        while (ita.hasNext()) {
            HashMap itemHM = (HashMap) ita.next();
            Iterator<String> keyIt = itemHM.keySet().iterator();
            while (keyIt.hasNext()) {
                String key = keyIt.next();
                out.println(key + " : " + itemHM.get(key));
            }
        }
    }

    public static void printALHM(ArrayList<HashMap> inputALHM) {
        // Check Value
        Iterator<HashMap> ita = inputALHM.iterator();
        while (ita.hasNext()) {
            HashMap itemHM = (HashMap) ita.next();
            printHM(itemHM);
        }
    }

    public static void printHM(HashMap inputHM) {
        /*
         Iterator<String> KeyIt = inputHM.keySet().iterator();
         while (KeyIt.hasNext()) {
         String key = KeyIt.next().toString();
         out.println(key + "=" + inputHM.get(key));
         }
         */

        Iterator<String> keyIt = inputHM.keySet().iterator();
        while (keyIt.hasNext()) {
            String key = keyIt.next();
            //out.println(obj.getClass().getName());
            if ("java.util.ArrayList".equals(inputHM.get(key).getClass().getName())) {
            	out.println(key + " ::: ");
                printALHM((ArrayList) inputHM.get(key));
            } else {
                out.println(key + " : " + inputHM.get(key));
            }
        }
    }

}
