/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LeoLib.utils;

import LeoLib.tools.Toolets;
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
        super(hm);
    }
    
    public HM(Object key, Object value){
        this.put(key, value);
    }
    
    public void printAll(){
        Toolets.printHM(this);
    }
    
}
