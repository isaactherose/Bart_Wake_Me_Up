package com.oneroseapps.bartwakemeup.app;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by valeriechao on 7/14/14.
 */
public class BartXMLParser {
    private static final String  ns = null;

    //In this snippet, a parser is initialized to not process namespaces, and to use the provided InputStream as its input.
    // It starts the parsing process with a call to nextTag() and invokes the readFeed() method, which extracts and processes the data the app is interested in:
    public List parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }
//this will get the tag in the feed to start reading the feed
    private List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List entries = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, "root");
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("trip")) {
                entries.add(Trip.readEntry(parser));
            } else {
                Trip.skip(parser);
            }
        }
        return entries;
    }

    public static class Trip {
        public final String origTimeMin;
        public final String destTimeMin;

        private Trip(String title,  String link) {
            this.origTimeMin = title;
            this.destTimeMin = link;
        }


        // Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
// to their respective "read" methods for processing. Otherwise, skips the tag.
        private static Trip readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
            parser.require(XmlPullParser.START_TAG, ns, "entry");
            String orig = null;
            String dest = null;
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                if (name.equals("origTimeMin")) {
                    orig = readOrigTimeMin(parser);
                }  else if (name.equals("destTimeMin")) {
                    dest = readDestTimeMin(parser);
                } else {
                    skip(parser);
                }
            }
            return new Trip(orig, dest);
        }

        // Processes title tags in the feed.
        private static String readOrigTimeMin(XmlPullParser parser) throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, ns, "origTimeMin");
            String title = readText(parser);
            parser.require(XmlPullParser.END_TAG, ns, "origTimeMin");
            return title;
        }

        // Processes link tags in the feed.
        private static String readDestTimeMin(XmlPullParser parser) throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, ns, "destTimeMin");
            String title = readText(parser);
            parser.require(XmlPullParser.END_TAG, ns, "destTimeMin");
            return title;
        }



        // For the tags title and summary, extracts their text values.
        private static String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
            String result = "";
            if (parser.next() == XmlPullParser.TEXT) {
                result = parser.getText();
                parser.nextTag();
            }
            return result;
        }

        private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                throw new IllegalStateException();
            }
            int depth = 1;
            while (depth != 0) {
                switch (parser.next()) {
                    case XmlPullParser.END_TAG:
                        depth--;
                        break;
                    case XmlPullParser.START_TAG:
                        depth++;
                        break;
                }
            }
        }
    }


}
