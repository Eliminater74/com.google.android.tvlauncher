package com.google.android.tvlauncher.appsview.data;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.support.annotation.NonNull;
import android.support.p001v4.content.IntentCompat;
import android.util.Log;

import com.google.android.exoplayer2.C0841C;

import java.util.List;

public class FlavorSpecificLaunchItemsManager extends LaunchItemsManager {
    private static final String TAG = "FlavorSpecificLaunchItemsManager";

    public FlavorSpecificLaunchItemsManager(Context context) {
        super(context);
    }

    /* access modifiers changed from: package-private */
    public Intent createNativeAppIntent(ResolveInfo info) {
        Intent intent = this.mContext.getPackageManager().getLeanbackLaunchIntentForPackage(info.activityInfo.packageName);
        if (intent == null) {
            String valueOf = String.valueOf(info.toString());
            Log.e(TAG, valueOf.length() != 0 ? "Could not find a Leanback intent for resolved info: ".concat(valueOf) : new String("Could not find a Leanback intent for resolved info: "));
            return null;
        }
        intent.addFlags(C0841C.ENCODING_PCM_MU_LAW);
        return intent;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public List<ResolveInfo> getRawLaunchItemsForPackage(String packageName) {
        Intent mainIntent = new Intent("android.intent.action.MAIN");
        mainIntent.setPackage(packageName);
        mainIntent.addCategory(IntentCompat.CATEGORY_LEANBACK_LAUNCHER);
        return this.mContext.getPackageManager().queryIntentActivities(mainIntent, 129);
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public List<ResolveInfo> getRawLaunchItems() {
        Intent mainLeanbackLauncherIntent = new Intent("android.intent.action.MAIN");
        mainLeanbackLauncherIntent.addCategory(IntentCompat.CATEGORY_LEANBACK_LAUNCHER);
        return this.mContext.getPackageManager().queryIntentActivities(mainLeanbackLauncherIntent, 129);
    }

    public boolean shouldShowAppStoreLaunchItem() {
        return true;
    }

    public boolean shouldShowGameStoreLaunchItem() {
        return true;
    }
}
