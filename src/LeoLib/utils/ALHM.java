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
public class ALHM extends java.util.ArrayList<HM> implements java.io.Serializable {

    private static ArrayList<HM> alhm;

    public ArrayList<HM> getALHM() {
        return alhm;
    }

    public void setALHM(ArrayList<HM> alhm) {
        this.alhm = alhm;
    }
    
    public void printAll(){
        Toolets.printALHM((ArrayList)this);
    }
    public void print(int i){
        Toolets.printHM(alhm.get(i));
    }
}
