package com.google.android.tvlauncher.doubleclick;

import java.io.IOException;
import java.net.HttpURLConnection;

public interface UrlConnectionFactory {
    HttpURLConnection createGetRequestConnection(String str) throws IOException, NetworkException;
}
