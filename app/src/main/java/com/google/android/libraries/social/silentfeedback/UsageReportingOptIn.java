package com.google.android.libraries.social.silentfeedback;

import android.content.Context;
import android.os.Build;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.usagereporting.UsageReporting;
import com.google.android.gms.usagereporting.UsageReportingApi;

class UsageReportingOptIn {
    private static final String KEY_OPT_IN = "optedIn";
    private static final String PREF_FILE_NAME = "com.google.android.libraries.social.silentfeedback.usageReporting";
    private static final String TAG = "UsageReportingOptIn";
    /* access modifiers changed from: private */
    public volatile boolean optedIn;

    UsageReportingOptIn(Context context) {
        this.optedIn = context.getSharedPreferences(PREF_FILE_NAME, 0).getBoolean(KEY_OPT_IN, false);
        if (Log.isLoggable(TAG, 3)) {
            boolean z = this.optedIn;
            StringBuilder sb = new StringBuilder(40);
            sb.append("Initial isOptedInForUsageReporting=");
            sb.append(z);
            Log.d(TAG, sb.toString());
        }
    }

    /* access modifiers changed from: package-private */
    @MainThread
    public void refresh(Context context) {
        refreshOptedInForUsageReporting(context);
    }

    /* access modifiers changed from: package-private */
    public boolean isOptedIn() {
        return this.optedIn;
    }

    private void refreshOptedInForUsageReporting(final Context context) {
        if (!Build.TYPE.equals("unknown")) {
            final GoogleApiClient apiClient = new GoogleApiClient.Builder(context).addApi(UsageReporting.API).build();
            UsageReporting.UsageReportingApi.getOptInOptions(apiClient).setResultCallback(new ResultCallback<UsageReportingApi.OptInOptionsResult>() {
                public void onResult(@NonNull UsageReportingApi.OptInOptionsResult result) {
                    if (result.getStatus().isSuccess()) {
                        boolean unused = UsageReportingOptIn.this.optedIn = result.isOptedInForUsageReporting();
                        if (Log.isLoggable(UsageReportingOptIn.TAG, 3)) {
                            boolean access$000 = UsageReportingOptIn.this.optedIn;
                            StringBuilder sb = new StringBuilder(40);
                            sb.append("Current isOptedInForUsageReporting=");
                            sb.append(access$000);
                            Log.d(UsageReportingOptIn.TAG, sb.toString());
                        }
                        context.getSharedPreferences(UsageReportingOptIn.PREF_FILE_NAME, 0).edit().putBoolean(UsageReportingOptIn.KEY_OPT_IN, UsageReportingOptIn.this.optedIn).apply();
                    }
                    apiClient.disconnect();
                }
            });
            apiClient.connect();
        }
    }
}
