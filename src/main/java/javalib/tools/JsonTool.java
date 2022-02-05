package javalib.tools;

import java.io.File;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

/**
 *
 * @author Leo Chen
 */
public class JsonTool {
    protected static String TAG = JsonTool.class.getName();
    
    public static Object json2Object(String json) {
        if (null == json) {return null;}
        try {
            return (new ObjectMapper()).readValue(json, new TypeReference<>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String object2Json(Object obj) {
        if (null == obj) {return null;}
        try {
            return (new ObjectMapper()).writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static boolean object2JsonFile(Object obj, String filePath) {
        if (null == obj || null == filePath) {return false;}
        try {
            File file = FileTool.createFile(filePath);
            (new ObjectMapper()).writeValue(file, obj);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static Object jsonFile2Object(String jsonFile){
        if (null == jsonFile) {return null;}
        try {
            return (new ObjectMapper()).readValue( new File(jsonFile), new TypeReference<>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
