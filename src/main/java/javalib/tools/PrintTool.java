package javalib.tools;

import javalib.utils.Debug;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Leo Chen
 */
public class PrintTool extends ToolBase{
    protected static String TAG = PrintTool.class.getName();
    
    public static void printArrayListHashMap(ArrayList<HashMap<String, String>> inputALHM) {
        for (HashMap<String, String> stringStringHashMap : inputALHM) {
            HashMap itemHM = stringStringHashMap;
            Iterator<String> keyIt = itemHM.keySet().iterator();
            while (keyIt.hasNext()) {
                String key = keyIt.next();
                //de.println(key + " : " + itemHM.get(key));
                (new Debug(true)).println(key + " : " + itemHM.get(key));
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

        Iterator<String> keyIt = inputHM.keySet().iterator();
        while (keyIt.hasNext()) {
            String key = keyIt.next();
            //out.println(obj.getClass().getName());
            if ("java.util.ArrayList".equals(inputHM.get(key).getClass().getName())) {
            	//de.println(key + " ::: ");
                printALHM((ArrayList) inputHM.get(key));
            } else {
                (new Debug(true)).println(key + " : " + inputHM.get(key));
            }
        }
    }
    
    /** HttpServletRequest Print **/
    
    public static void printParms(HttpServletRequest request) {
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String ele = enu.nextElement();
            (new Debug(true)).println(ele + " : " + request.getParameter(ele));
        }
    }
    
    public static void printAttributes(HttpServletRequest request) {
        Enumeration<String> enu = request.getAttributeNames();
        while (enu.hasMoreElements()) {
            String ele = enu.nextElement();
            (new Debug(true)).println(ele + " : " + request.getAttribute(ele));
        }
    }
    
    public static void printHeaders(HttpServletRequest request) {
        Enumeration<String> enu = request.getHeaderNames();
        while (enu.hasMoreElements()) {
            String ele = enu.nextElement();
            (new Debug(true)).println(ele + " : " + request.getHeader(ele));
        }
    }
}
