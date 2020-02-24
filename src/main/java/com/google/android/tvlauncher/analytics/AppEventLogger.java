package com.google.android.tvlauncher.analytics;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.usagereporting.UsageReporting;
import com.google.android.gms.usagereporting.UsageReportingApi;
import com.google.android.tvlauncher.util.TestUtils;

abstract class AppEventLogger {
    protected static AppEventLogger sInstance;

    public abstract void log(LogEvent logEvent);

    /* access modifiers changed from: package-private */
    public abstract void setName(Activity activity, String str);

    /* access modifiers changed from: package-private */
    public abstract void setUsageReportingOptedIn(boolean z);

    AppEventLogger() {
    }

    @MainThread
    public static AppEventLogger getInstance() {
        return sInstance;
    }

    static void checkOptedInForUsageReporting(Context context) {
        if (!TestUtils.isRunningInTest()) {
            final GoogleApiClient apiClient = new GoogleApiClient.Builder(context).addApi(UsageReporting.API).build();
            UsageReporting.UsageReportingApi.getOptInOptions(apiClient).setResultCallback(new ResultCallback<UsageReportingApi.OptInOptionsResult>() {
                public void onResult(@NonNull UsageReportingApi.OptInOptionsResult result) {
                    if (result.getStatus().isSuccess()) {
                        AppEventLogger.getInstance().setUsageReportingOptedIn(result.isOptedInForUsageReporting());
                    }
                    GoogleApiClient.this.disconnect();
                }
            });
            apiClient.connect();
        }
    }
}
