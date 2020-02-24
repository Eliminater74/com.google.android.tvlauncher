package com.google.android.tvlauncher.instantvideo.util;

import android.net.Uri;
import android.text.TextUtils;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YouTubeUriUtils {
    private static List<String> ACCEPTABLE_SCHEMES = Arrays.asList("http", "https");
    private static Pattern YOUTUBE_ID_MATCHER = Pattern.compile("[a-zA-Z0-9_-]+");
    private static Pattern YOUTUBE_REGULAR_DOMAIN_MATCHER = Pattern.compile("^(www[.])?youtube.com$");
    private static Pattern YOUTUBE_SHORTENED_DOMAIN_MATCHER = Pattern.compile("^(www[.])?youtu.be$");
    private static Pattern YOUTUBE_SHORTENED_PATH_MATCHER;
    private static Pattern YOUTUBE_VIDEO_PATH_MATCHER;
    private static Pattern YOUTUBE_WATCH_PATH_MATCHER = Pattern.compile("^/watch$");

    static {
        String valueOf = String.valueOf(YOUTUBE_ID_MATCHER);
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 11);
        sb.append("^/video/(");
        sb.append(valueOf);
        sb.append(")$");
        YOUTUBE_VIDEO_PATH_MATCHER = Pattern.compile(sb.toString());
        String valueOf2 = String.valueOf(YOUTUBE_ID_MATCHER);
        StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 5);
        sb2.append("^/(");
        sb2.append(valueOf2);
        sb2.append(")$");
        YOUTUBE_SHORTENED_PATH_MATCHER = Pattern.compile(sb2.toString());
    }

    public static boolean isYouTubeWatchUri(Uri youTubeUri) {
        return !TextUtils.isEmpty(getYouTubeVideoId(youTubeUri));
    }

    public static String getYouTubeVideoId(Uri youTubeUri) {
        if (!ACCEPTABLE_SCHEMES.contains(youTubeUri.getScheme()) || youTubeUri.getAuthority() == null) {
            return null;
        }
        String path = youTubeUri.getPath();
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        if (YOUTUBE_REGULAR_DOMAIN_MATCHER.matcher(youTubeUri.getAuthority()).matches()) {
            if (YOUTUBE_WATCH_PATH_MATCHER.matcher(path).matches()) {
                return youTubeUri.getQueryParameter("v");
            }
            Matcher videoPathMatcher = YOUTUBE_VIDEO_PATH_MATCHER.matcher(path);
            if (!videoPathMatcher.matches() || videoPathMatcher.groupCount() != 1) {
                return null;
            }
            return videoPathMatcher.group(1);
        } else if (!YOUTUBE_SHORTENED_DOMAIN_MATCHER.matcher(youTubeUri.getAuthority()).matches()) {
            return null;
        } else {
            Matcher shortenedPathMatcher = YOUTUBE_SHORTENED_PATH_MATCHER.matcher(path);
            if (!shortenedPathMatcher.matches() || shortenedPathMatcher.groupCount() != 1) {
                return null;
            }
            return shortenedPathMatcher.group(1);
        }
    }
}
