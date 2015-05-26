package javalib.tools;

/**
 *
 * @author leo
 */
public class JavaFunctions {
    public String getHomePath(){
        return getClass().getResource("/").getPath().replaceAll("classes/", "").replaceAll("%20", "\\ ");
    }
    
}
