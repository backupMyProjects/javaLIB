/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LeoLib.test;

/**
 * Required : LeoLib.jar
 */

import LeoLib.tools.*;
import LeoLib.tools.debug;
import LeoLib.utils.ALHM;
import LeoLib.utils.HM;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import static java.lang.System.out;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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

/**
 *
 * @author leo
 */
public class test {

    static debug de = new debug(true);
    
    /**
     *
     * @param args
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        //testDate();
        //out.println(customDateTime("2013-07-19 23:35:21", "yyyy-MM-dd hh:mm:ss"));
        //showNowINSecond();
        //testLog();
        
        HM hm = (HM) new HashMap();
        
        //HM hm = new HM(new HM("name", "leo"));
        //hm.printAll();
        List<NameValuePair> nvp = new ArrayList<NameValuePair>();
		nvp.add(new BasicNameValuePair("payway", "migs"));
		nvp.add(new BasicNameValuePair("merchantData1", "TEST112345680NTD"));
		nvp.add(new BasicNameValuePair("merchantData2", "9E00A7B2"));
		nvp.add(new BasicNameValuePair("orderId", "1996"));
		nvp.add(new BasicNameValuePair("authAmnt", "100"));
		nvp.add(new BasicNameValuePair("currency", "5"));
		nvp.add(new BasicNameValuePair("cardType", "Visa"));
		nvp.add(new BasicNameValuePair("cardNo", "4005550000000001"));
		nvp.add(new BasicNameValuePair("expiredDate", "1705"));
		nvp.add(new BasicNameValuePair("cvv", "100"));
		//nvp.add(new BasicNameValuePair("exactReq", ""));
		nvp.add(new BasicNameValuePair("returnType", "XML"));
		//nvp.add(new BasicNameValuePair("returnURL", ""));
        String result = HttpConnector.postDataInGIP("http://10.24.100.135:8080/ePayService/payment/Pay.auth.do", nvp);
        //String result = postDataInGIP("http://10.24.100.135:8080/ePayService/payment/Pay.auth.do", nvp);
        out.println(result);
        //new ALHM( new HM("name", "leo") ).printAll();
    }
    
    public static String postDataInGIP(String url, List<NameValuePair> nameValuePairs) {
		DefaultHttpClient httpclient = null;
	    HttpPost httppost = null;
		HttpResponse response = null;
		String result = "";
                out.println("1");
		try {
			httpclient = getHttpClient();
			httppost = getHttpPost(url);
			
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
			while ((s = bf.readLine()) != null) result += s;

		} catch (ClientProtocolException e) {
			result = "<exception>ClientProtocolException</exception>";
			e.printStackTrace();
		}catch (ConnectTimeoutException e){
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
    
    private static HttpPost getHttpPost(String url) {
		HttpPost httpPost = new HttpPost(url);
		// 设置 请求超时时间
		httpPost.getParams().setParameter(HttpConnectionParams.SO_TIMEOUT, 5000);
		httpPost.setHeader("Connection", "Keep-Alive");
		httpPost.addHeader("Accept-Encoding", "gzip");
		return httpPost;
	}
	
	private static DefaultHttpClient getHttpClient() {
		DefaultHttpClient httpClient = new DefaultHttpClient();

		// 设置 连接超时时间
		//httpClient.getParams().setParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 5000);
		//httpClient.getParams().setParameter(HttpConnectionParams.SO_TIMEOUT, 5000);
		httpClient.getParams().setParameter("http.protocol.content-charset", "UTF_8");
		return httpClient;
	}
    
    public String getPath() {
        return this.getClass().getResource("/").getPath();
    }
    
    public static void showNowINSecond(){
        out.println(System.currentTimeMillis()/1000);
    }
    
    public static void testLog(){
        Log.setLogLevel(Log.INFO);
        Log.setFormat(Log.Format.FORMAT1);
        Log.v(Thread.currentThread().getName(), "Hello");
        Log.d(Thread.currentThread().getName(), "Hello");
        Log.i(Thread.currentThread().getName(), "Hello");
        Log.w(Thread.currentThread().getName(), "Hello");
        Log.e(Thread.currentThread().getName(), "Hello");
    }
    
    public static void testDate() throws ParseException{
        Date date = new Date();
        Date at = (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).parse("2013-07-17 23:35:21");
        
        out.println(date.getDate());
        out.println(date.getDay());
        out.println(date.getMonth()+1);
        out.println(date.getYear()+1900);
        
        out.println();
        
        out.println(at.getDate());
        out.println(at.getDay());
        out.println(at.getMonth()+1);
        out.println(at.getYear()+1900);
        
    }
    
    public static String customDateTime(String dateTime, String dateTimeFormat) throws ParseException{
        Date date = new Date();
        Date at = (new SimpleDateFormat(dateTimeFormat)).parse(dateTime);
        String outputFormat = null;
        if( date.getYear() - at.getYear() == 0 ){// this year
            if( date.getMonth() - at.getMonth() == 0 ){ // this month
                out.println("this month");
                if ( date.getDate() - at.getDate() < 7 ){
                    if ( date.getDay() - at.getDay() > 0 ){// this week
                        out.println("this week");
                        outputFormat = "E a hh:mm:ss";
                        return getDateTime(outputFormat, at);
                    }else if (date.getDay() - at.getDay() == 0){// today
                        out.println("today");
                        outputFormat = "a hh:mm:ss";
                        return getDateTime(outputFormat, at);
                    }else{
                        outputFormat = "MM-dd hh:mm:ss";
                        return getDateTime(outputFormat, at);
                    }
                }else{
                    outputFormat = "MM-dd hh:mm:ss";
                    return getDateTime(outputFormat, at);
                }
            } else {
                outputFormat = "MM-dd hh:mm:ss";
                return getDateTime(outputFormat, at);
            }
        }else{
            outputFormat = "yyyy-MM-dd hh:mm:ss";
            return getDateTime(outputFormat, at);
        }
        
    }
    
    public static String getDateTime(String pattern, Object... dates) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        if (dates.length == 1) {
            //System.out.println( dates[0].getClass().getName() );
            if ("java.util.Date".equals(dates[0].getClass().getName())) {
                return sdf.format((Date) dates[0]);
            } else if ("java.lang.Long".equals(dates[0].getClass().getName())) {
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis((Long) dates[0]);
                //System.out.println("getTimeInMillis : "+cal.getTimeInMillis());
                Date date = cal.getTime();
                return sdf.format(date);
            } else {/* nothing */

            }

        }
        return sdf.format(new Date());
        //( dates.length == 1 ) ? return sdf.format(dates[0]) : return sdf.format(new Date());
    }

    
}
