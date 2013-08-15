/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LeoLib.tools;

/**
 *
 * @author leo
 */
public class JavaFunctions {
    public String getHomePath(){
        return getClass().getResource("/").getPath().replaceAll("classes/", "").replaceAll("%20", "\\ ");
    }
    
}
