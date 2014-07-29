/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LeoLib.tools;

/**
 *
 * @author leo
 */
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.NameValuePair;

public class HttpConnector {
    

    public static String postData(String url, List<NameValuePair> nameValuePairs) {
        DefaultHttpClient httpclient = null;
        HttpPost httppost = null;
        HttpResponse response = null;
        String result = "";
        try {
            httpclient = commonHttpClient();
            httppost = commonHttpPost(url);

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));

            response = httpclient.execute(httppost);
            int statusCode = response.getStatusLine().getStatusCode();

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
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
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

    private static HttpPost commonHttpPost(String url) {
        HttpPost httpPost = new HttpPost(url);
        // 设置 请求超时时间
        httpPost.getParams().setParameter(HttpConnectionParams.SO_TIMEOUT, 5000);
        httpPost.setHeader("Connection", "Keep-Alive");
        httpPost.addHeader("Accept-Encoding", "gzip");
        return httpPost;
    }

    private static DefaultHttpClient commonHttpClient() {
        DefaultHttpClient httpClient = new DefaultHttpClient();

	// 设置 连接超时时间
        //httpClient.getParams().setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 5000);
        //httpClient.getParams().setParameter(HttpConnectionParams.SO_TIMEOUT, 5000);
        httpClient.getParams().setParameter("http.protocol.content-charset", "UTF_8");
        return httpClient;
    }

}
