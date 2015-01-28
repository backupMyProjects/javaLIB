/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LeoLib.tools;

import java.io.File;
import java.util.Map;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

/**
 *
 * @author Leo Chen
 */
public class JsonTool {
    protected static String TAG = JsonTool.class.getName();
    
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
            File file = FileTool.createFile(filePath);
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
            
            return map;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
