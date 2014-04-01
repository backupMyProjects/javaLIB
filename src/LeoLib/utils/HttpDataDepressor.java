/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LeoLib.utils;

import java.util.*;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author leo
 */
public class HttpDataDepressor {

    public static HashMap getParms(HttpServletRequest request) {
        HashMap<String, String> result = new HashMap();
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String ele = enu.nextElement();
            //System.out.println(ele + " : " + request.getParameter(ele));
            result.put(ele, request.getParameter(ele));
        }
        return result;
    }
    
    public static HashMap getAttributes(HttpServletRequest request) {
        HashMap<String, String> result = new HashMap();
        Enumeration<String> enu = request.getAttributeNames();
        while (enu.hasMoreElements()) {
            String ele = enu.nextElement(); 
            //System.out.println(ele + " : " + request.getAttribute(ele));
            result.put(ele, (String) request.getAttribute(ele));
        }
        return result;
    }
    
    public static HashMap getHeaders(HttpServletRequest request) {
        HashMap<String, String> result = new HashMap();
        Enumeration<String> enu = request.getHeaderNames();
        while (enu.hasMoreElements()) {
            String ele = enu.nextElement();
            //System.out.println(ele + " : " + request.getHeader(ele));
            result.put(ele, request.getHeader(ele));
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
    
    
}
