package com.google.android.tvlauncher.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.exoplayer2.C0841C;

public class LeanbackCategoryIntentLauncher implements IntentLauncher {
    private static final boolean DEBUG = false;
    private static final String TAG = "LeanbackLauncherIntentLauncher";

    public boolean launchIntent(Context context, String packageName, Intent originalIntent) {
        if (TextUtils.isEmpty(packageName)) {
            String valueOf = String.valueOf(originalIntent);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 49);
            sb.append("No package was specified in the original intent: ");
            sb.append(valueOf);
            Log.e(TAG, sb.toString());
            return false;
        }
        Intent intent = context.getPackageManager().getLeanbackLaunchIntentForPackage(packageName);
        if (intent == null) {
            String valueOf2 = String.valueOf(packageName);
            Log.e(TAG, valueOf2.length() != 0 ? "Could not find a Leanback intent for package: ".concat(valueOf2) : new String("Could not find a Leanback intent for package: "));
            return false;
        }
        intent.addFlags(C0841C.ENCODING_PCM_MU_LAW);
        try {
            context.startActivity(intent);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }
}
