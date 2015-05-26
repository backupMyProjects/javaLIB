package javalib.utils;

import javalib.tools.PrintTool;
import javalib.tools.Toolets;
import static java.lang.System.out;
import java.util.*;

/**
 *
 * @author leo
 */
public class HM extends java.util.HashMap implements java.io.Serializable{
    
    public HM(){
        super();
    }
    
    public HM(HM hm){
        this.putAll(hm);
    }
    
    public HM(HashMap hm){
        this.putAll(hm);
    }
    
    public HM(Object key, Object value){
        this.put(key, value);
    }
    
    public void printAll(){
        PrintTool.printHM(this);
    }
    
}
