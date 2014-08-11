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
public class ALHM extends java.util.ArrayList<HashMap> implements java.io.Serializable {
    
    public ALHM(){
        super();
    }
    public ALHM(ALHM alhm){
        super(alhm);
    }
    public ALHM(HM hm){
        this.add(hm);
    }

    
    public void printAll(){
        Toolets.printALHM(this);
    }
    public void print(int i){
        Toolets.printHM(this.get(i));
    }
}
