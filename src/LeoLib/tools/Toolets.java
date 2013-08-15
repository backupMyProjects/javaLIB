/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LeoLib.tools;

import java.util.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;

/**
 *
 * @author leo
 * merge @ 2012/3/5
 */
public class Toolets {


    public static void printHashMapList(List<HashMap<String, String>> inputList) {
        for (int i = 0; i < inputList.size(); i++) {
            //String key = data.get(i).keySet().iterator().next();
            printHashMap(inputList.get(i));
        }
    }

    public static void printHashMap(HashMap<String, ?> inputHashMap) {
        System.out.println("size : " + inputHashMap.size());
        Iterator KeyIt = inputHashMap.keySet().iterator();
        while (KeyIt.hasNext()) {
            String key = KeyIt.next().toString();
            System.out.println(key + "=" + inputHashMap.get(key));
        }
    }


    public static boolean notEmpList(List input) {
        return (input != null && input.size() > 0) ? true : false;
    }

    
}
