/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
