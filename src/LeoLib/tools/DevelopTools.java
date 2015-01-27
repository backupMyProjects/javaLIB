/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LeoLib.tools;

import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author 1307033
 */
public class DevelopTools {
    
    public static void showParm(HttpServletRequest request) {
        Enumeration enu = request.getParameterNames();

        while (enu.hasMoreElements()) {
            String parmKey = enu.nextElement().toString();
            out.println("parm key " + parmKey);
            out.println("parm value " + request.getParameter(parmKey));
        }

    }
    
    
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
