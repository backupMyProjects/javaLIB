package LeoLib.tools;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import static java.lang.System.err;
import java.util.List;
import java.util.zip.GZIPInputStream;
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

public class HttpConnector {

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

    public static String postData(String url, List<NameValuePair> nameValuePairs) throws UnsupportedEncodingException, IOException {
        return postData(url, nameValuePairs, currentTimeout);
    }
    public static String postData(String url, List<NameValuePair> nameValuePairs, int timeout) throws UnsupportedEncodingException, IOException {
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

    public static String postDataInGZIP(String url, List<NameValuePair> nameValuePairs, int timeout) throws UnsupportedEncodingException, IOException {
        String result = "";

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

        String str = null;
        while ((str = br.readLine()) != null) {
            result += str;
        }

        return result;

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
