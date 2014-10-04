package LeoLib.tools;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import static java.lang.System.err;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpConnector {
    
    protected static int timeout = 30000;
    public static int getTimeout(){return timeout;};

    public static String getData(String url) {

        String outputString = "";

        // DefaultHttpClient
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);
        // ResponseHandler
        ResponseHandler<String> responseHandler = new BasicResponseHandler();

        try {
            outputString = httpclient.execute(httpget, responseHandler);
            //Log.i("HttpClientConnector", "Connect Success");
        } catch (Exception e) {
            err.println("Connect Failed");
            e.printStackTrace();
        }
        httpclient.getConnectionManager().shutdown();
        return outputString;

    }

    /**
     * No Yet
     *
     * @param url
     * @param nameValuePairs
     * @return
     */
    public static String getData(String url, HttpParams nameValuePairs) {

        return "";
    }

    public static String postData(String url, List<NameValuePair> nameValuePairs) {
        String outputString = "";
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);

        try {
	        // Add your data
            //httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"UTF-8"));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));

            ResponseHandler<String> responseHandler = new BasicResponseHandler();
	        // Execute HTTP Post Request
            //outputString = httpclient.execute(httppost, responseHandler);
            HttpResponse myhttpResponse = httpclient.execute(httppost);
            if (myhttpResponse.getStatusLine().getStatusCode() == 200) {
                outputString = EntityUtils.toString(myhttpResponse.getEntity(), HTTP.UTF_8);
                //System.out.println(outputString);
            }

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }

        httpclient.getConnectionManager().shutdown();
        return outputString;
    }

    public static String postDataInGIP(String url, List<NameValuePair> nameValuePairs){
        return postDataInGIP( url,  nameValuePairs, timeout);
    }
    public static String postDataInGIP(String url, List<NameValuePair> nameValuePairs, int timeout) {
        DefaultHttpClient httpclient = null;
        HttpPost httppost = null;
        HttpResponse response = null;
        String result = "";
        try {
            httpclient = getHttpClient();
            httppost = getHttpPost(url, timeout);

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));

            response = httpclient.execute(httppost);
            int statusCode = response.getStatusLine().getStatusCode();
            System.out.println("statusCode : "+statusCode);

            InputStream is = response.getEntity().getContent();

            Header contentEncoding = response.getFirstHeader("Content-Encoding");
            if (contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip")) {
                is = new GZIPInputStream(new BufferedInputStream(is));
            }
            //return is;
            BufferedReader bf = new BufferedReader(new InputStreamReader(is));

            String s = null;
            while ((s = bf.readLine()) != null) {
                result += s;
            }

        } catch (ClientProtocolException e) {
            result = "<exception>ClientProtocolException</exception>";
            e.printStackTrace();
        } catch (ConnectTimeoutException e) {
            result = "<exception>ConnectTimeoutException</exception>";
            e.printStackTrace();
        } catch (IOException e) {
            result = "<exception>IOException</exception>";
            e.printStackTrace();
        } catch (Exception e) {
            result = "<exception>Exception</exception>";
            e.printStackTrace();
        } finally {

            /*
             * if (!post.isAborted()) {
             * 
             * post.abort(); } httpclient = null;
             */
        }

        return result;

    }

    private static HttpPost getHttpPost(String url, int timeout) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.getParams().setParameter(HttpConnectionParams.SO_TIMEOUT, timeout);
        httpPost.setHeader("Connection", "Keep-Alive");
        httpPost.addHeader("Accept-Encoding", "gzip");
        return httpPost;
    }

    private static DefaultHttpClient getHttpClient() {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        //httpClient.getParams().setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 5000);
        //httpClient.getParams().setParameter(HttpConnectionParams.SO_TIMEOUT, 5000);
        httpClient.getParams().setParameter("http.protocol.content-charset", "UTF_8");
        return httpClient;
    }

}
