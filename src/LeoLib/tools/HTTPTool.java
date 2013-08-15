/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LeoLib.tools;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author leo
 */
public class HTTPTool {
    
    public static String parmGetter(HttpServletRequest request, String key) {
        return request.getParameter(key) != null ? request.getParameter(key) : "";
    }
    public static void showParm(HttpServletRequest request){
        Enumeration enu = request.getParameterNames();

        while( enu.hasMoreElements() ){
            String parmKey = enu.nextElement().toString();
            //System.out.println("parm key "+ parmKey );
            //System.out.println("parm value "+request.getParameter(parmKey));
        }

    }
    
}
