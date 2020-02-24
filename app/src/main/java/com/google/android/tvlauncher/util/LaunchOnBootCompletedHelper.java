package com.google.android.tvlauncher.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;
import android.util.Log;

public class LaunchOnBootCompletedHelper {
    private static final String CURRENT_SDK_INT_KEY = "current_sdk_int";
    private static final String LAUNCH_BOOT_COUNT_KEY = "launch_boot_count";
    /* access modifiers changed from: private */
    public static final String[] PROJECTION = {"package_name"};
    private static final String TAG = "LaunchOnBootCompletedHelper";
    private boolean mCheckedLaunchAfterBoot;
    private final Context mContext;
    private boolean mForceLaunchOemPackage;
    private boolean mForegroundActivityWasLaunchPackage;
    private boolean mIsLoaded;
    private boolean mIsOperatorTier;
    private String mOemPackage;

    public interface OnDataLoadCompleteListener {
        void onDataLoadComplete();
    }

    private interface SetLoadedDataCallback {
        void setLoadedData(boolean z, boolean z2, boolean z3, String str);
    }

    public LaunchOnBootCompletedHelper(Context context) {
        this.mContext = context;
    }

    public boolean isFirstLaunchAfterBoot() {
        if (!this.mCheckedLaunchAfterBoot) {
            this.mCheckedLaunchAfterBoot = PreferenceManager.getDefaultSharedPreferences(this.mContext).getInt(LAUNCH_BOOT_COUNT_KEY, -1) >= getBootCount();
        }
        return !this.mCheckedLaunchAfterBoot;
    }

    public boolean tryLaunchingOemPackage() {
        boolean oemPackageWasLaunched = false;
        if (!TextUtils.isEmpty(this.mOemPackage) && ((this.mIsOperatorTier && this.mForceLaunchOemPackage) || (!isFirstBootOrPostOta() && this.mForegroundActivityWasLaunchPackage))) {
            oemPackageWasLaunched = launchOemPackage(this.mOemPackage);
        }
        PreferenceManager.getDefaultSharedPreferences(this.mContext).edit().putInt(LAUNCH_BOOT_COUNT_KEY, getBootCount()).apply();
        this.mCheckedLaunchAfterBoot = true;
        return oemPackageWasLaunched;
    }

    public void loadLaunchOnBootFlagsAsync(OnDataLoadCompleteListener listener) {
        new LoadLaunchOnBootFlagsTask(new LaunchOnBootCompletedHelper$$Lambda$0(this), listener).execute(this.mContext);
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ void lambda$loadLaunchOnBootFlagsAsync$0$LaunchOnBootCompletedHelper(boolean forceLaunch, boolean isOperatorTier, boolean foregroundActivityWasLaunchPackage, String oemPackage) {
        this.mForceLaunchOemPackage = forceLaunch;
        this.mIsOperatorTier = isOperatorTier;
        this.mForegroundActivityWasLaunchPackage = foregroundActivityWasLaunchPackage;
        this.mOemPackage = oemPackage;
        this.mIsLoaded = true;
    }

    public boolean isLoaded() {
        return this.mIsLoaded;
    }

    private boolean isFirstBootOrPostOta() {
        SharedPreferences bootPrefs = PreferenceManager.getDefaultSharedPreferences(this.mContext);
        int currentSdkInt = bootPrefs.getInt(CURRENT_SDK_INT_KEY, -1);
        if (currentSdkInt == -1) {
            currentSdkInt = Build.VERSION.SDK_INT;
            bootPrefs.edit().putInt(CURRENT_SDK_INT_KEY, currentSdkInt).apply();
        }
        boolean postOta = currentSdkInt != Build.VERSION.SDK_INT;
        if (postOta) {
            bootPrefs.edit().putInt(CURRENT_SDK_INT_KEY, currentSdkInt).apply();
        }
        if (postOta || getBootCount() == 1) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public boolean launchOemPackage(String pkgName) {
        Intent intent = IntentUtil.createForceLaunchOnBootIntent(pkgName);
        try {
            this.mContext.startActivity(intent);
            return true;
        } catch (ActivityNotFoundException e) {
            String valueOf = String.valueOf(intent);
            String valueOf2 = String.valueOf(e);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 40 + String.valueOf(valueOf2).length());
            sb.append("Activity for intent : ");
            sb.append(valueOf);
            sb.append(", was not found : ");
            sb.append(valueOf2);
            Log.e(TAG, sb.toString());
            return false;
        }
    }

    private int getBootCount() {
        return Settings.Global.getInt(this.mContext.getContentResolver(), "boot_count", 0);
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public void setIsOperatorTier(boolean isOperator) {
        this.mIsOperatorTier = isOperator;
    }

    private static class LoadLaunchOnBootFlagsTask extends AsyncTask<Context, Void, Void> {
        private OnDataLoadCompleteListener mDataLoadCompleteListener;
        private SetLoadedDataCallback mSetLoadedDataCallback;

        LoadLaunchOnBootFlagsTask(SetLoadedDataCallback callback, OnDataLoadCompleteListener listener) {
            this.mSetLoadedDataCallback = callback;
            this.mDataLoadCompleteListener = listener;
        }

        /* JADX INFO: Multiple debug info for r1v1 boolean: [D('e' java.lang.Exception), D('isOperatorTier' boolean)] */
        /* access modifiers changed from: protected */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0036, code lost:
            r7 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
            r6.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x003f, code lost:
            throw r7;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Void doInBackground(android.content.Context... r13) {
            /*
                r12 = this;
                r0 = 0
                r1 = 0
                r2 = r13[r1]
                com.google.android.tvlauncher.util.OemConfiguration r3 = com.google.android.tvlauncher.util.OemConfiguration.get(r2)
                java.lang.String r4 = r3.getPackageNameLaunchAfterBoot()
                boolean r5 = r3.shouldForceLaunchPackageAfterBoot()
                android.content.ContentResolver r6 = r2.getContentResolver()     // Catch:{ Exception -> 0x0046 }
                android.net.Uri r7 = com.google.android.tvlauncher.util.ForegroundActivityContract.FOREGROUND_ACTIVITY_URI     // Catch:{ Exception -> 0x0046 }
                java.lang.String[] r8 = com.google.android.tvlauncher.util.LaunchOnBootCompletedHelper.PROJECTION     // Catch:{ Exception -> 0x0046 }
                r9 = 0
                r10 = 0
                r11 = 0
                android.database.Cursor r6 = r6.query(r7, r8, r9, r10, r11)     // Catch:{ Exception -> 0x0046 }
                if (r6 == 0) goto L_0x0040
                boolean r7 = r6.moveToFirst()     // Catch:{ all -> 0x0034 }
                if (r7 == 0) goto L_0x0040
                java.lang.String r1 = r6.getString(r1)     // Catch:{ all -> 0x0034 }
                boolean r1 = android.text.TextUtils.equals(r4, r1)     // Catch:{ all -> 0x0034 }
                r0 = r1
                goto L_0x0040
            L_0x0034:
                r1 = move-exception
                throw r1     // Catch:{ all -> 0x0036 }
            L_0x0036:
                r7 = move-exception
                r6.close()     // Catch:{ all -> 0x003b }
                goto L_0x003f
            L_0x003b:
                r8 = move-exception
                com.google.devtools.build.android.desugar.runtime.ThrowableExtension.addSuppressed(r1, r8)     // Catch:{ Exception -> 0x0046 }
            L_0x003f:
                throw r7     // Catch:{ Exception -> 0x0046 }
            L_0x0040:
                if (r6 == 0) goto L_0x0045
                r6.close()     // Catch:{ Exception -> 0x0046 }
            L_0x0045:
                goto L_0x006b
            L_0x0046:
                r1 = move-exception
                java.lang.String r6 = java.lang.String.valueOf(r1)
                java.lang.String r7 = java.lang.String.valueOf(r6)
                int r7 = r7.length()
                int r7 = r7 + 55
                java.lang.StringBuilder r8 = new java.lang.StringBuilder
                r8.<init>(r7)
                java.lang.String r7 = "Error in retrieving foreground activity package name : "
                r8.append(r7)
                r8.append(r6)
                java.lang.String r6 = r8.toString()
                java.lang.String r7 = "LaunchOnBootCompletedHelper"
                android.util.Log.e(r7, r6)
            L_0x006b:
                boolean r1 = com.google.android.tvlauncher.util.Util.isOperatorTierDevice(r2)
                com.google.android.tvlauncher.util.LaunchOnBootCompletedHelper$SetLoadedDataCallback r6 = r12.mSetLoadedDataCallback
                r6.setLoadedData(r5, r1, r0, r4)
                com.google.android.tvlauncher.util.LaunchOnBootCompletedHelper$OnDataLoadCompleteListener r6 = r12.mDataLoadCompleteListener
                r6.onDataLoadComplete()
                r6 = 0
                return r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.tvlauncher.util.LaunchOnBootCompletedHelper.LoadLaunchOnBootFlagsTask.doInBackground(android.content.Context[]):java.lang.Void");
        }
    }
}
