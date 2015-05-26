/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javalib.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.out;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

/**
 *
 * @author Leo
 */
public class SOAPTest {
    public static void main(String[] args){
        List<NameValuePair> nvpList = new ArrayList<NameValuePair>(2);
            nvpList.add(new BasicNameValuePair("email:example", "mutantninja@gmail.com"));
            nvpList.add(new BasicNameValuePair("LicenseKey:example","123"));

        String result = test("http://ws.cdyne.com/emailverify/Emailvernotestemail.asmx",nvpList);
        System.out.println(result);
    }
    
    public static String test(String url, List<NameValuePair> nvpList){
        try {

            HttpPost request = new HttpPost(url);
            //StringEntity input = new UrlEncodedFormEntity(nvpList);
            
            StringEntity input = new StringEntity("<example:VerifyEmail><example:email>mutantninja@gmail.com</example:email><example:LicenseKey>123</example:LicenseKey></example:VerifyEmail>");
            
            input.setContentType("application/json;charset=UTF-8");
            request.setHeader("Content-Type","application/soap+xml;charset=UTF-8");
            request.setEntity(input);
            HttpClient httpClient = new DefaultHttpClient();
            input.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/soap+xml;charset=UTF-8"));
            request.setEntity(input);

            HttpResponse response = httpClient.execute(request);

            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
            String output = "";
            for (String temp = ""; temp != null; temp = br.readLine()) {
                //out.println(temp);
                output += temp;
            }

            httpClient.getConnectionManager().shutdown();
            return output;

        } catch (MalformedURLException e) {
           // e.printStackTrace();
            out.println("MalformedURLException: " +e.getMessage());
        } catch (IOException e) {
            //e.printStackTrace();
            out.println("IOException: " +e.getMessage());
        }catch (Exception e) {
           // e.printStackTrace();
            out.println("Exception: " +e.getMessage());
        }
        return null;
    }
}
