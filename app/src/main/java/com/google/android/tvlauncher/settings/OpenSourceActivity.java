package com.google.android.tvlauncher.settings;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.android.tvlauncher.C1188R;

public class OpenSourceActivity extends Activity {
    private static final String TAG = "OpenSourceActivity";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(C1188R.layout.open_source_licenses);
            ((WebView) findViewById(C1188R.C1191id.content)).loadUrl("file:///android_asset/licenses.html");
        } catch (RuntimeException e) {
            String message = e.getMessage();
            if (message == null || !message.contains("WebView")) {
                throw e;
            }
            Log.e(TAG, "Failed to open WebView for open source text ", e);
            Toast.makeText(this, C1188R.string.failed_launch, 0).show();
            finish();
        }
    }
}
