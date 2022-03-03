package javalib.tools;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import static java.lang.System.err;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.zip.GZIPInputStream;
import javax.servlet.http.HttpServletRequest;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Leo Chen
 */
public class HttpServletTool {
    protected static String TAG = HttpServletTool.class.getName();
    
    public static String getParm(HttpServletRequest request, String target) {
        if ( null != request.getParameter(target) ){
            return request.getParameter(target);
        }else if ( null != request.getAttribute(target) ){
            Object obj = request.getAttribute(target);
            if ( java.lang.String.class.getName().equals(obj.getClass().getName()) ) {
                return (String)request.getAttribute(target);
            }else{
                System.out.println(TAG + ":" + "parmGetter:" + "obj is a " + obj.getClass().getName());
            }
        }
        return "";
    }
    
    //TODO.
    //public static ArrayList<HashMap> getRequestInfo(){
    private static ArrayList<HashMap> getRequestInfo(){
        return null;
    }

    public static HashMap getParmsMap(HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap();
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String item = enu.nextElement();
            result.put(item, request.getParameter(item));
        }
        return result;
    }
    
    public static HashMap getAttributesMap(HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap();
        Enumeration<String> enu = request.getAttributeNames();
        while (enu.hasMoreElements()) {
            String item = enu.nextElement();
            result.put(item, request.getAttribute(item));
        }
        return result;
    }
    
    public static HashMap getHeadersMap(HttpServletRequest request) {
        HashMap<String, Object> result = new HashMap();
        Enumeration<String> enu = request.getHeaderNames();
        while (enu.hasMoreElements()) {
            String item = enu.nextElement();
            result.put(item, request.getHeader(item));
        }
        return result;
    }
    
    public static String urlEncode(String input, String encode) {
        try {
            return URLEncoder.encode(input, encode);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "ENCODE FAILED";
        }
    }

    public static String urlDecode(String input, String decode) {
        try {
            return URLDecoder.decode(input, decode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "DECODE FAILED";
        }

    }
    
    
    protected static int currentTimeout = 30000;

    public static void setCurrentTimeout(int input){currentTimeout = input;}
    public static int getCurrentTimeout(){return currentTimeout;}

    /** TODO **/
    public static String getData(String url) {

        String outputString = "";

        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        try {
            outputString = httpclient.execute(httpget, responseHandler);
            //Log.i("HttpClientConnector", "Connect Success");
        } catch (IOException e) {
            err.println(e);
        }
        httpclient.getConnectionManager().shutdown();
        return outputString;

    }

    public static String postData(String url, List<NameValuePair> nameValuePairs) throws IOException {
        return postData(url, nameValuePairs, currentTimeout);
    }
    public static String postData(String url, List<NameValuePair> nameValuePairs, int timeout) throws IOException {
        String outputString = "";
        DefaultHttpClient httpclient = getDefaultHttpClient();
        HttpPost httppost = getHttpPost(url, timeout);

        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));

        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        //outputString = httpclient.execute(httppost, responseHandler);
        HttpResponse httpResponse = httpclient.execute(httppost);
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            outputString = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8);
        }

        httpclient.getConnectionManager().shutdown();
        return outputString;
    }

    public static String postDataInGZIP(String url, List<NameValuePair> nameValuePairs) throws IOException {
        return postDataInGZIP(url, nameValuePairs, currentTimeout);
    }

    public static String postDataInGZIP(String url, List<NameValuePair> nameValuePairs, int timeout) throws IOException {
        StringBuilder result = new StringBuilder();

        DefaultHttpClient httpclient = getDefaultHttpClient();
        HttpPost httppost = getHttpPostGZIP(url, timeout);
        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));
        HttpResponse response = httpclient.execute(httppost);

        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("statusCode : " + statusCode);

        InputStream is = response.getEntity().getContent();

        Header contentEncoding = response.getFirstHeader("Content-Encoding");
        if (contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip")) {
            is = new GZIPInputStream(new BufferedInputStream(is));
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String str;
        while ((str = br.readLine()) != null) {
            result.append(str);
        }

        return result.toString();

    }

    private static HttpPost getHttpPost(String url, int timeout) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.getParams().setParameter(HttpConnectionParams.SO_TIMEOUT, timeout);
        httpPost.setHeader("Connection", "Keep-Alive");
        return httpPost;
    }

    private static HttpPost getHttpPostGZIP(String url, int timeout) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.getParams().setParameter(HttpConnectionParams.SO_TIMEOUT, timeout);
        httpPost.setHeader("Connection", "Keep-Alive");
        httpPost.addHeader("Accept-Encoding", "gzip");
        return httpPost;
    }

    private static DefaultHttpClient getDefaultHttpClient() {
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        //httpClient.getParams().setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 5000);
        //httpClient.getParams().setParameter(HttpConnectionParams.SO_TIMEOUT, 5000);
        defaultHttpClient.getParams().setParameter("http.protocol.content-charset", "UTF_8");
        return defaultHttpClient;
    }
    
}
