/*
 * debug.java
 *
 * Created on 2007/12/24, 上午 11:28:38
 *
 */

package LeoLib.tools;

import java.util.Date;

/**
 *
 * @author Leo Chen
 */
public class debug {
    protected boolean debug;
    
    /** Creates a new instance of debug */
    public debug() {
        this.debug = true;
    }
    
    /** Creates a new instance of debug */
    public debug(boolean debug) {
        this.debug = debug;
    }
    
    public void setDebug(boolean debug){
        this.debug = debug;
    }
    public boolean getDebug(){
        return debug;
    }
    
    public void println(String str)
    {
      if (debug)
      { System.out.println(str); }
    } // end println()
    
    public void printDate(){
        Date date = new Date();
        if (debug)
        {println("At "+ date.getTime() +" millisecond."); }
    }
    
}
