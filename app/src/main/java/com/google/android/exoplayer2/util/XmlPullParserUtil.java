package com.google.android.exoplayer2.util;

import android.support.annotation.Nullable;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class XmlPullParserUtil {
    private XmlPullParserUtil() {
    }

    public static boolean isEndTag(XmlPullParser xpp, String name) throws XmlPullParserException {
        return isEndTag(xpp) && xpp.getName().equals(name);
    }

    public static boolean isEndTag(XmlPullParser xpp) throws XmlPullParserException {
        return xpp.getEventType() == 3;
    }

    public static boolean isStartTag(XmlPullParser xpp, String name) throws XmlPullParserException {
        return isStartTag(xpp) && xpp.getName().equals(name);
    }

    public static boolean isStartTag(XmlPullParser xpp) throws XmlPullParserException {
        return xpp.getEventType() == 2;
    }

    public static boolean isStartTagIgnorePrefix(XmlPullParser xpp, String name) throws XmlPullParserException {
        return isStartTag(xpp) && stripPrefix(xpp.getName()).equals(name);
    }

    @Nullable
    public static String getAttributeValue(XmlPullParser xpp, String attributeName) {
        int attributeCount = xpp.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            if (xpp.getAttributeName(i).equals(attributeName)) {
                return xpp.getAttributeValue(i);
            }
        }
        return null;
    }

    @Nullable
    public static String getAttributeValueIgnorePrefix(XmlPullParser xpp, String attributeName) {
        int attributeCount = xpp.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            if (stripPrefix(xpp.getAttributeName(i)).equals(attributeName)) {
                return xpp.getAttributeValue(i);
            }
        }
        return null;
    }

    private static String stripPrefix(String name) {
        int prefixSeparatorIndex = name.indexOf(58);
        return prefixSeparatorIndex == -1 ? name : name.substring(prefixSeparatorIndex + 1);
    }
}
