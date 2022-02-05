package javalib.tools;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/**
 *
 * @author leo Parsing XML to ALHM by parse("XML String") Reverting ALHM to XML
 * String by revert(ArrayList<hashMap<String,String>>)
 *
 */
public class XmlTool {

    static String tag = XmlTool.class.getName();
    static String result;

    static int level = 0;

    public static ArrayList parse(String xmlPath) throws XmlPullParserException, UnsupportedEncodingException, IOException {

        // Log.i(tag, tag+".parse");
        // 回傳物件
        ArrayList resultList = new ArrayList();
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
        HashMap resultHM = new HashMap();
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
                    StringBuilder attrStr = new StringBuilder();
                    for (int i = 0; i < parser.getAttributeCount(); i++) {
                        attrStr.append("||").append(parser.getAttributeName(i)).append("=\"").append(parser.getAttributeValue(i)).append("\"");
                    }
					// Log.i( tag, "<"+parser.getName() + attrStr +">" );

                    // ---do attribute here---
                    if (parser.getAttributeCount() > 0) {
                        // resultList.add(parser.getName() + attrStr);
                        resultHM.put(parser.getName(), attrStr.toString());
                    }
                    // ----------------------

                    break;
                case XmlPullParser.TEXT:
					// Log.i( tag, parser.getText() );

                    // ---do tag value here---
                    if (!"".equals(parser.getText())) {
                        // resultList.add(tagName+":"+parser.getText());
                        resultHM.put(tagName, parser.getText());
                    }
                    // ----------------------

                    break;
                case XmlPullParser.END_TAG:
                    level--;
                    // Log.i( tag, "</"+parser.getName()+">" );
                    if (level == 1) {
                        resultList.add(resultHM.clone());
                        resultHM.clear();
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

        // return resultList;
    }

    public static String reverse(ArrayList alhm) {
        StringBuilder result = null;
        if (null != alhm) {
            result = new StringBuilder("<data>");
            for (int i = 0; i < alhm.size(); i++) {
                result.append("<item>");
                HashMap resultHM = (HashMap) alhm.get(i);
                int size = resultHM.keySet().size();
                for (Object key : resultHM.keySet()) {
                    result.append("<").append(key).append(">");
                    result.append("<![CDATA[");
                    result.append(resultHM.get(key));
                    result.append("]]>");
                    result.append("</").append(key).append(">");
                }
                result.append("</item>");
            }
            result.append("</data>");
        } else {
            result = new StringBuilder("<data />");
        }
        return result.toString();
    }

}
