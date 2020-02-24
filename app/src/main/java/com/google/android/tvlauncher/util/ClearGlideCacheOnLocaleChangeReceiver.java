package com.google.android.tvlauncher.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.bumptech.glide.Glide;

public class ClearGlideCacheOnLocaleChangeReceiver extends BroadcastReceiver {
    public static IntentFilter getIntentFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.LOCALE_CHANGED");
        return filter;
    }

    public void onReceive(Context context, Intent intent) {
        Glide.get(context).clearMemory();
    }
}
