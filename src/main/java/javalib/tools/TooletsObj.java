package javalib.tools;

/**
 *
 * @author Leo Chen
 */
public class TooletsObj {
    public String getHomePath(){
        return getClass().getResource("/").getPath().replaceAll("classes/", "").replaceAll("%20", "\\ ");
    }
    
}
