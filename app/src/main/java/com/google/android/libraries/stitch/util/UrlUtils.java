package com.google.android.libraries.stitch.util;

import android.text.TextUtils;

public final class UrlUtils {
    public static String prependProtocolIfMissing(String url) {
        if (TextUtils.isEmpty(url) || url.startsWith("http:") || url.startsWith("https:")) {
            return url;
        }
        String valueOf = String.valueOf(url);
        return valueOf.length() != 0 ? "https:".concat(valueOf) : new String("https:");
    }

    public static String getParameter(String url, String param) {
        String queryParam = param.endsWith("=") ? param : String.valueOf(param).concat("=");
        int start = url.indexOf(queryParam);
        if (start == -1) {
            return null;
        }
        int start2 = start + queryParam.length();
        int end = url.indexOf(38, start2);
        if (end == -1) {
            return url.substring(start2);
        }
        return url.substring(start2, end);
    }
}
