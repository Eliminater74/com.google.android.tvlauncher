package com.google.android.tvlauncher.util;

import android.content.Context;
import android.content.Intent;

public interface IntentLauncher {
    boolean launchIntent(Context context, String str, Intent intent);
}
