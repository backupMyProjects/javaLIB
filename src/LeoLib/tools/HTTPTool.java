/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LeoLib.tools;

import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author leo
 */
public class HTTPTool {
    private static String TAG = HTTPTool.class.getName();
    
    public static String getParm(HttpServletRequest request, String target) {
        if ( null != request.getParameter(target) ){
            return request.getParameter(target);
        }else if ( null != request.getAttribute(target) ){
            Object obj = request.getAttribute(target);
            if ( java.lang.String.class.getName().equals(obj.getClass().getName()) ) {
                return (String)request.getAttribute(target);
            }else{
                System.out.println(TAG + ":" + "parmGetter:" + "obj is a " + obj.getClass().getName());
            }
        }
        return "";
    }

    public static HashMap getParmsMap(HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap();
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String item = enu.nextElement();
            result.put(item, request.getParameter(item));
        }
        return result;
    }
    
    public static HashMap getAttributesMap(HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap();
        Enumeration<String> enu = request.getAttributeNames();
        while (enu.hasMoreElements()) {
            String item = enu.nextElement(); 
            result.put(item, (String) request.getAttribute(item));
        }
        return result;
    }
    
    public static HashMap getHeadersMap(HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap();
        Enumeration<String> enu = request.getHeaderNames();
        while (enu.hasMoreElements()) {
            String item = enu.nextElement();
            result.put(item, request.getHeader(item));
        }
        return result;
    }
    
    public static void printParms(HttpServletRequest request) {
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String ele = enu.nextElement();
            System.out.println(ele + " : " + request.getParameter(ele));
        }
    }
    
    public static void printAttributes(HttpServletRequest request) {
        Enumeration<String> enu = request.getAttributeNames();
        while (enu.hasMoreElements()) {
            String ele = enu.nextElement();
            System.out.println(ele + " : " + request.getAttribute(ele));
        }
    }
    
    public static void printHeaders(HttpServletRequest request) {
        Enumeration<String> enu = request.getHeaderNames();
        while (enu.hasMoreElements()) {
            String ele = enu.nextElement();
            System.out.println(ele + " : " + request.getHeader(ele));
        }
    }

    private static class V {

        public V() {
        }
    }
    
}
