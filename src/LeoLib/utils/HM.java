/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LeoLib.utils;

import LeoLib.tools.Toolets;
import java.util.*;

/**
 *
 * @author leo
 */
public class HM extends java.util.HashMap implements java.io.Serializable{
    private HashMap<?, ?> hm; 
    
    public HashMap<?, ?> getHM(){
        return hm;
    }
    
    public void setHM(HashMap hm){
        this.hm = hm;
    }
    
    public void printAll(){
        Toolets.printHM(this);
    }
    
}
