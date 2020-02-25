package com.google.android.tvlauncher.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.exoplayer2.C0841C;

public class PlayStoreIntentLauncher implements IntentLauncher {
    private static final String STORE_AUTHORITY_DETAILS = "details";
    private static final String STORE_QUERY_PARAM_ID = "id";
    private static final String STORE_SCHEME = "market";
    private static final String TAG = "PlayStoreIntentLauncher";

    public boolean launchIntent(Context context, String packageName, Intent originalIntent) {
        if (TextUtils.isEmpty(packageName)) {
            String valueOf = String.valueOf(originalIntent);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 49);
            sb.append("No package was specified in the original intent: ");
            sb.append(valueOf);
            Log.e(TAG, sb.toString());
            return false;
        }
        Intent storeIntent = new Intent("android.intent.action.VIEW");
        storeIntent.setData(new Uri.Builder().scheme(STORE_SCHEME).authority(STORE_AUTHORITY_DETAILS).appendQueryParameter("id", packageName).build());
        storeIntent.addFlags(C0841C.ENCODING_PCM_MU_LAW);
        try {
            context.startActivity(storeIntent);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }
}
