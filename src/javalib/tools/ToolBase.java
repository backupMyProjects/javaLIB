package javalib.tools;

import javalib.utils.Debug;

/**
 *
 * @author 1307033
 */
public class ToolBase {
    protected Debug de = new Debug();
    public void showPrint(boolean input){de.setDebug(input);}
    
}
