/*
 * Property.java
 *
 * Created on 2007/12/24, 上午 11:28:38
 *
 */

package LeoLib.tools;

import static LeoLib.utils.Constants.*;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * <BR>This is a kit for loading property file
 * @author Leo Chen
 */
public class APProperty {
    protected debug de = new debug(false);
    
    protected Properties appProp;
    protected Properties dbProp;
    JavaFunctions jf = new JavaFunctions();
    String homePath = jf.getHomePath();
    
    public Properties getAppProp(){
        return appProp;
    }
    public Properties getDBProp(){
        return dbProp;
    }
        
    /**
     * <BR>Creates a new instance of APProperty with <U>SettingFile</U>
     * <BR>eg: <I>WebApp.properties</I>
     * <BR>And the file inside should be : 
     * <BR>eg: <I>db.properties.[env_filter]=D:/=Programmer=/NetBeans/WebApp/web/WEB-INF/[properties_folder]/db.properties</I>
     * <BR>Replace web base as the ap root path ; so dose the standlone ap within the root of package
     * @param SettingFile what the property setting file is
     */
    /*
    public APProperty(String SettingFile) {
//        start_prop = loadPropertyFile(SettingFile);
        appProp = loadPropertyFile(homePath+propertFolderStr+SettingFile);
    }
    */
    
    /**
     * <BR>Creates a new instance of APProperty with <U>Setting File & File Path</U>
     * <BR>eg: <I>D:/=Programmer=/NetBeans/WebApp/web/</I> & <I>WebApp.properties</I>
     * <BR>And the file inside should be :
     * <BR>eg: <I>db.properties.[env_filter]=D:/=Programmer=/NetBeans/WebApp/web/WEB-INF/[properties_folder]/db.properties</I>
     * @param SettingPath/SettingFile where/what the property setting file is
     */
    /*
    public APProperty(String SettingPath, String SettingFile) {
        appProp = loadPropertyFile(SettingPath + SettingFile);
    }
    */
    public APProperty(Boolean autoLoad) {
        if ( autoLoad ){
            appProp = loadPropertyFile(homePath+propertyFolderStr+appStr);
            dbProp = loadPropertyFile(homePath+propertyFolderStr+dbStr);
        }else{
            // do nothing
        }
    }

    /**
     * <BR>eg: <I>WebApp.properties</I>
     */
    public static Properties loadPropertyFile(String PropertyFile)
    {
        Properties prop = null;

        try {
            prop = new Properties();
            FileInputStream in = new FileInputStream(PropertyFile);
            prop.load(in);
            
            in.close();

        } catch (Exception ex) {
          ex.printStackTrace();
        } // end catch

        return prop;

    } // end loadProperties()
    
    /**
     * To get a Setting property value by property name
     * @param propName The property name which you want to get the value of.
     */
    public String getPropertyValue(String propName) {
        String propValue;
        
        propValue = appProp.getProperty(propName);
        de.println("propName is " + propName);
        de.println("propValue is " + propValue);
        
        return propValue;
    }
    
    /**
     * <BR>eg: <I>load db.properties</I>
     */
    /*
    public Properties loadDBProperty(){
        dbProp = loadPropertyFile(homePath+propertFolderStr+dbStr);
        return dbProp;
    }
    */
    
    
    /**
     * <BR>eg: <I>load application.properties</I>
     */
    /*
    public Properties loadAppProperty(){
        appProp = loadPropertyFile(homePath+propertFolderStr+startStr);
        return appProp;
    }
    */
    
    
    
}
