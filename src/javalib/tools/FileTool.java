/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javalib.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 *
 * @author Leo Chen
 */
public class FileTool{
    protected static String TAG = FileTool.class.getName();
    
    /* File Tool */
    
    static int stringBufferSize = 1000;
    static int charArrBuffSize = 1024;
    public static String readFile2String(String filePath) {
        try {
            StringBuffer fileData = new StringBuffer(stringBufferSize);
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            char[] buf = new char[charArrBuffSize];
            int numRead = 0;
            while ((numRead = reader.read(buf)) != -1) {
                String readData = String.valueOf(buf, 0, numRead);
                fileData.append(readData);
                buf = new char[charArrBuffSize];
            }
            reader.close();
            return fileData.toString();

        } catch (IOException e) {
            System.err.println(e);
        }

        return null;
    }
    
    /** TODO : There is an error : out.close(); **/
    public static boolean writeString2File(String input, String filePath) {
        try {
            
            File file = createFile(filePath);
            //out.println(file.getAbsolutePath());
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            
            //BufferedWriter out = new BufferedWriter(new FileWriter(filePath));
            bw.write(input);
            bw.close();
            return true;
        } catch (IOException e) {
            System.err.println(e);
            //out.close();
            return false;
        }
    }

    /** Download File **/
    
    static int byteArrBufferSize = 153600;
    public static boolean downloadFile(String inFileURL, String outFilePath) {
        try {
            System.out.println("Connecting");
            URL url = new URL(inFileURL);
            url.openConnection();
            InputStream reader = url.openStream();

            FileTool.createParentFolder(outFilePath);
            FileOutputStream writer = new FileOutputStream(outFilePath);
            byte[] buffer = new byte[byteArrBufferSize];
            int totalBytesRead = 0;
            int bytesRead = 0;

            //out.println("Reading file "+buffer.length/1024+"KB blocks at a time.");
            long startTime = System.currentTimeMillis();

            while ((bytesRead = reader.read(buffer)) > 0) {
                writer.write(buffer, 0, bytesRead);
                buffer = new byte[byteArrBufferSize];
                totalBytesRead += bytesRead;
            }

            long endTime = System.currentTimeMillis();

            System.out.println("Done. " + (new Integer(totalBytesRead).toString()) + " bytes read (" + (new Long(endTime - startTime).toString()) + " millseconds).");
            writer.close();
            reader.close();
            return true;
        } catch (Exception e) {
            //e.printStackTrace();
            System.err.println(TAG);
            System.err.println(e);
            return false;
        }
    }
    
    private static int currentTimeout = 30000;
    public static void setCurrentTimeout(int input){currentTimeout = input;}
    public static int getCurrentTimeout(){return currentTimeout;}
    public static boolean downloadFile2(String inFileURL, String outFilePath){
        return downloadFile2(inFileURL, outFilePath, currentTimeout);
    }
    public static boolean downloadFile2(String inFileURL, String outFilePath, int timeout) {
        boolean result = false;
        FileOutputStream fileos = null;
        try {
            //boolean eof = false;
            HttpURLConnection connect = (HttpURLConnection) (new URL(inFileURL))
                    .openConnection();
            connect.setRequestMethod("GET");
            connect.setReadTimeout(timeout);
            connect.setDoOutput(true);
            connect.connect();

            // String PATH_op = Environment.getExternalStorageDirectory() +
            // "/download/" + targetFileName;
            FileTool.createParentFolder(outFilePath);
            fileos = new FileOutputStream(new File(outFilePath));

            InputStream is = connect.getInputStream();
            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = is.read(buffer)) > 0) {
                fileos.write(buffer, 0, len1);
            }

            fileos.close();
            result = true;
        } catch (SocketTimeoutException e) {
            _deleteFile(fileos, outFilePath);
            System.err.println(e);
        } catch (MalformedURLException e) {
            _deleteFile(fileos, outFilePath);
            System.err.println(e);
        } catch (ProtocolException e) {
            _deleteFile(fileos, outFilePath);
            System.err.println(e);
        } catch (FileNotFoundException e) {
            _deleteFile(fileos, outFilePath);
            System.err.println(e);
        } catch (IOException e) {
            _deleteFile(fileos, outFilePath);
            System.err.println(e);
        }finally{
            _closeFileOS(fileos);
            return result;
        }
    }
    private static void _deleteFile(FileOutputStream fileos, String outFilePath) {
        if (null != fileos) {
            try {
                fileos.close();
                fileos = null;
                System.gc();
                File file = new File(outFilePath);
                System.out.println("file exists : "+file.exists());
                //new File(outFilePath).deleteOnExit();
                file.delete();
                System.out.println("file exists : "+file.exists());
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }
    private static void _closeFileOS(FileOutputStream fileos) {
        if (null != fileos) {
            try {
                fileos.close();
                fileos = null;
                System.gc();
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }

    /** File & Folder **/
    
    public static boolean createParentFolder(String filePath) {
        File parent = new File(filePath).getParentFile();
        if (!parent.exists() && !parent.mkdirs()) {
            throw new IllegalStateException("Couldn't create dir: " + parent);
        }

        return false;
    }
    
    public static File createFile(String filePath) {
        File file = new File(filePath);
        System.out.println(file.getAbsolutePath());
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
    
    public static void Serialization(Object input , String file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(input);
            oos.close();
            fos.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }
    
    public static Object DeSerialization(String file) {
    	Object result = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            result = (Object) ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException e) {
            System.err.println(e);
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        }
        return result;
    }
    
}
