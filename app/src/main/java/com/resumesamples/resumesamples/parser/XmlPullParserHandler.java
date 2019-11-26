package com.resumesamples.resumesamples.parser;

import com.resumesamples.resumesamples.model.Title;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class XmlPullParserHandler {

    private final String searchHtmlFile = "htmlFile";
    private final String searchItem = "item";
    private final String searchName = "name";
    private final String adsInterstitial = "interstitial";
    private ArrayList<Title> titles;
    private Title title;
    private String content;

    public XmlPullParserHandler() {
        this.titles = new ArrayList<>();
    }

    public ArrayList<Title> parse(InputStream is) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();

            parser.setInput(is, null);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagName.equalsIgnoreCase(searchItem)) {
                            title = new Title();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        content = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagName.equalsIgnoreCase(searchItem)) {
                            titles.add(title);
                        } else if (tagName.equalsIgnoreCase(searchName)) {
                            title.setName(content);
                        } else if (tagName.equalsIgnoreCase(searchHtmlFile)) {
                            title.setHtmlFile(content);
                        } else if (tagName.equalsIgnoreCase(adsInterstitial)) {
                            title.setInterstitialShown(parseAdsInterstitialValue(content));
                        }
                        break;

                    default:
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return titles;
    }

    private boolean parseAdsInterstitialValue(String value) {
        try {
            return Boolean.valueOf(value);
        } catch (Exception e) {
            return false;
        }

    }
}
