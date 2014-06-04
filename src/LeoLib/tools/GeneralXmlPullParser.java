/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LeoLib.tools;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/**
 * 
 * @author leo
 * Parsing XML to ArrayList<HashMap<String, String>> by parse("XML String")
 * Reverting ArrayList<HashMap<String, String>> to XML String by revert(ArrayList<hashMap<String,String>>)
 * 
 */

public class GeneralXmlPullParser {

	static String tag = GeneralXmlPullParser.class.getName();
	static String result;

	static int level = 0;

	public static ArrayList<HashMap<String, String>> parse(String xmlPath) {

		// Log.i(tag, tag+".parse");

		// 回傳物件
		ArrayList<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>();

		try {

			// 使用xmlPull解析xml
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();

			// 取得資料

			/*
			 * no support http. if ( xmlPath.contains("http://") ){
			 * parser.setInput(new InputStreamReader( (new
			 * URL(xmlPath)).openStream() )); }else{ // xmlPath should be a xml
			 * result string. InputStream is = new
			 * ByteArrayInputStream(xmlPath.getBytes("UTF-8"));
			 * parser.setInput(is, null); }
			 */

			InputStream is = new ByteArrayInputStream(xmlPath.getBytes("UTF-8"));
			parser.setInput(is, null);

			// 解析xml
			int eventType = parser.getEventType();

			// 處理標籤
			String tagName = "";
			HashMap<String, String> resultHashMap = new HashMap<String, String>();
			while (eventType != XmlPullParser.END_DOCUMENT) {

				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					// System.out.println("Start document");
					break;
				case XmlPullParser.START_TAG:
					level++;
					tagName = parser.getName();
					// Log.i(tag, "tagName : "+tagName);

					// Define the TAG "||" for separate the tag name and the
					// attribute value 0<tagName>, 1<attribute>, ...
					String attrStr = "";
					for (int i = 0; i < parser.getAttributeCount(); i++) {
						attrStr += "||" + parser.getAttributeName(i) + "=\""
								+ parser.getAttributeValue(i) + "\"";
					}
					// Log.i( tag, "<"+parser.getName() + attrStr +">" );

					// ---do attribute here---
					if (parser.getAttributeCount() > 0) {
						// resultList.add(parser.getName() + attrStr);
						resultHashMap.put(parser.getName(), attrStr);
					}
					// ----------------------

					break;
				case XmlPullParser.TEXT:
					// Log.i( tag, parser.getText() );

					// ---do tag value here---
					if (!"".equals(parser.getText())) {
						// resultList.add(tagName+":"+parser.getText());
						resultHashMap.put(tagName, parser.getText());
					}
					// ----------------------

					break;
				case XmlPullParser.END_TAG:
					level--;
					// Log.i( tag, "</"+parser.getName()+">" );
					if (level == 1) {
						resultList.add((HashMap<String, String>) resultHashMap.clone());
						resultHashMap.clear();
					}
					break;
				case XmlPullParser.END_DOCUMENT:
					// System.out.println("End document");
					break;
				}
				eventType = parser.next();
			}
			// System.out.println(resultList);
			// Toolets.printHashMapList(resultList);
			return resultList;
		} catch (XmlPullParserException e) {
			System.out.println(tag + ":" + "XmlPullParserException !!");
			e.printStackTrace();
			return resultList;
		} catch (IOException e) {
			System.out.println(tag + ":" + "IOException !!");
			e.printStackTrace();
			return resultList;
		}

		// return resultList;
	}
        
        public static String reverse(List<HashMap<String, String>> resultList){
            String result = null;
            if ( null != resultList ) {
                result = "<data>";
                for(int i = 0 ; i< resultList.size() ; i++){
                    result += "<item>";
                    HashMap<String, Object> resultHM = (HashMap)resultList.get(i);
                    int size = resultHM.keySet().size();
                    for (String key : resultHM.keySet()) {
                        result += "<"+key+">";
                        result += "<![CDATA[";
                        result += resultHM.get(key) ;
                        result +="]]>";
                        result +="</"+key+">";
                    }
                    result += "</item>";
                }
                result += "</data>";
            }else{
                result = "<data />";
            }
            return result;
        }

}
