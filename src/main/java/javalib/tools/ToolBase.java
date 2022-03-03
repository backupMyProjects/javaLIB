package javalib.tools;

import javalib.utils.Debug;

/**
 *
 * @author Leo Chen
 */
public class ToolBase {
    protected Debug de = new Debug();
    public void showPrint(boolean input){de.setDebug(input);}
    
}
