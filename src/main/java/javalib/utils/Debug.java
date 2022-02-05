/*
 * debug.java
 *
 * Created on 2007/12/24, 上午 11:28:38
 *
 */
package javalib.utils;

import java.util.Date;

/**
 *
 * @author Leo Chen
 */
public class Debug {
    protected boolean debug;
    
    public Debug() {
        debug = true;
    }
    
    public Debug(boolean input) {
        debug = input;
    }
    
    public void setDebug(boolean input){debug = input;}
    public boolean getDebug(){return debug;}
    
    public void print(Object obj){
      if (debug)
      { System.out.print(obj.toString()); }
    } 
    
    public void println(Object obj){
      if (debug)
      { System.out.println(obj.toString()); }
    } 
    
    public void printDate(){
        Date date = new Date();
        if (debug)
        {println("At "+ date.getTime() +" millisecond."); }
    }
    
}
