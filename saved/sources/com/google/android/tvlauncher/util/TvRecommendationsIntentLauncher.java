package com.google.android.tvlauncher.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.util.Log;
import com.google.android.tvrecommendations.shared.util.Constants;
import java.util.List;

public class TvRecommendationsIntentLauncher implements IntentLauncher {
    private static final boolean DEBUG = false;
    private static final String TAG = "TvRecommendationsIntentLauncher";

    public boolean launchIntent(Context context, String packageName, Intent originalIntent) {
        if (originalIntent == null) {
            return false;
        }
        originalIntent.setPackage(Constants.TVRECOMMENDATIONS_PACKAGE_NAME);
        List<ResolveInfo> receivers = context.getPackageManager().queryBroadcastReceivers(originalIntent, 0);
        if (receivers == null || receivers.size() <= 0) {
            String valueOf = String.valueOf(originalIntent);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 31);
            sb.append("Activity not found for intent: ");
            sb.append(valueOf);
            Log.e(TAG, sb.toString());
            return false;
        }
        context.sendBroadcast(originalIntent);
        return true;
    }
}
