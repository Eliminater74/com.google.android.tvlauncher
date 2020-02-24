package com.google.android.tvlauncher.util;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.google.android.tvlauncher.appsview.LaunchItem;
import com.google.android.tvlauncher.appsview.data.LaunchItemsManager;
import com.google.android.tvlauncher.appsview.data.LaunchItemsManagerProvider;
import com.google.android.tvlauncher.settings.ProfilesManager;
import com.google.android.tvlauncher.util.OemAppPromotionsXmlParser;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class OemAppPromotions implements LaunchItemsManager.AppsViewChangeListener {
    private static final long PROMOTIONS_LOAD_THROTTLE_TIME = 900000;
    private static final String TAG = "OemAppPromotions";
    private static final Object sLock = new Object();
    @SuppressLint({"StaticFieldLeak"})
    private static OemAppPromotions sOemAppPromotions = null;
    /* access modifiers changed from: private */
    public final Set<String> mAllPromotionIds = new HashSet();
    /* access modifiers changed from: private */
    public final List<OemPromotionApp> mAllPromotions = new ArrayList();
    /* access modifiers changed from: private */
    public List<OnAppPromotionsLoadedListener> mAppPromotionsLoadedListeners;
    private ContentObserver mAppPromotionsObserver = new ContentObserver(new Handler()) {
        public void onChange(boolean selfChange, Uri uri) {
            if (PartnerCustomizationContract.OEM_APP_RECS_URI.equals(uri)) {
                OemAppPromotions.this.readAppPromotions(false);
            }
        }
    };
    /* access modifiers changed from: private */
    public final Context mContext;
    private final String mPackageName;
    private long mPromotionsLastLoadedTime = -1;
    /* access modifiers changed from: private */
    public String mPromotionsRowTitle;
    /* access modifiers changed from: private */
    public final List<OemPromotionApp> mVisiblePromotions = new ArrayList();

    public interface OnAppPromotionsLoadedListener {
        void onAppPromotionsLoaded(List<OemPromotionApp> list);
    }

    private OemAppPromotions(Context context, String packageName) {
        this.mPackageName = packageName;
        this.mContext = context != null ? context.getApplicationContext() : null;
        this.mAppPromotionsLoadedListeners = new ArrayList(2);
    }

    public static OemAppPromotions get(Context context) {
        synchronized (sLock) {
            if (sOemAppPromotions == null) {
                String packageName = getPromotionsApp(context.getPackageManager());
                if (packageName != null) {
                    sOemAppPromotions = new OemAppPromotions(context, packageName);
                }
                if (sOemAppPromotions == null) {
                    sOemAppPromotions = new OemAppPromotions(null, null);
                }
            }
        }
        return sOemAppPromotions;
    }

    public static void resetIfNecessary(Context context, String packageName) {
        synchronized (sLock) {
            if (sOemAppPromotions != null && !TextUtils.isEmpty(packageName) && packageName.equals(sOemAppPromotions.getPackageName())) {
                get(context).readAppPromotions(true);
            }
        }
    }

    private static String getPromotionsApp(PackageManager pm) {
        ProviderInfo info = pm.resolveContentProvider("tvlauncher.apprecs", 0);
        if (info == null || (info.applicationInfo.flags & 1) == 0) {
            return null;
        }
        return info.packageName;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String getAppsPromotionRowTitle() {
        return this.mPromotionsRowTitle;
    }

    public void registerOnAppPromotionsLoadedListener(OnAppPromotionsLoadedListener listener) {
        if (!this.mAppPromotionsLoadedListeners.contains(listener)) {
            this.mAppPromotionsLoadedListeners.add(listener);
        }
        try {
            if (this.mContext != null && this.mAppPromotionsLoadedListeners.size() == 1) {
                this.mContext.getContentResolver().registerContentObserver(PartnerCustomizationContract.OEM_APP_RECS_URI, true, this.mAppPromotionsObserver);
            }
        } catch (SecurityException e) {
            Log.e(TAG, "failed to register content observer for app promotions", e);
        }
    }

    public void unregisterOnAppPromotionsLoadedListener(OnAppPromotionsLoadedListener listener) {
        if (this.mContext != null && this.mAppPromotionsLoadedListeners.size() == 1 && this.mAppPromotionsLoadedListeners.contains(listener)) {
            this.mContext.getContentResolver().unregisterContentObserver(this.mAppPromotionsObserver);
        }
        this.mAppPromotionsLoadedListeners.remove(listener);
    }

    public void readAppPromotions(boolean forceRefresh) {
        Context context = this.mContext;
        if (context != null && !ProfilesManager.getInstance(context).isRestrictedProfile() && this.mAppPromotionsLoadedListeners.size() > 0) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            long j = this.mPromotionsLastLoadedTime;
            if (elapsedRealtime - j > PROMOTIONS_LOAD_THROTTLE_TIME || j < 0 || forceRefresh) {
                this.mPromotionsLastLoadedTime = SystemClock.elapsedRealtime();
                new AppPromotionsLoadingTask(this.mContext).execute(new Void[0]);
            }
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public boolean isPromotionApp(String id) {
        return this.mAllPromotionIds.contains(id);
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    public boolean isVisiblePromotionApp(String id) {
        for (OemPromotionApp promotion : this.mVisiblePromotions) {
            if (promotion.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public boolean removePackageNamesFromPromotions(List<OemPromotionApp> promotions, Set<String> packageNames) {
        boolean update = false;
        Iterator<OemPromotionApp> iterator = promotions.iterator();
        while (iterator.hasNext()) {
            if (packageNames.contains(iterator.next().getId())) {
                update = true;
                iterator.remove();
            }
        }
        return update;
    }

    private void updateIfNeeded(boolean update) {
        if (update) {
            for (OnAppPromotionsLoadedListener listener : this.mAppPromotionsLoadedListeners) {
                listener.onAppPromotionsLoaded(this.mVisiblePromotions);
            }
        }
    }

    public void onLaunchItemsLoaded() {
        updateIfNeeded(removePackageNamesFromPromotions(this.mVisiblePromotions, LaunchItemsManagerProvider.getInstance(this.mContext).getAllLaunchItemsPackageName()));
    }

    public void onLaunchItemsAddedOrUpdated(ArrayList<LaunchItem> addedOrUpdatedItems) {
        Set<String> addedOrUpdatedPackageName = new HashSet<>();
        Iterator<LaunchItem> it = addedOrUpdatedItems.iterator();
        while (it.hasNext()) {
            addedOrUpdatedPackageName.add(it.next().getPackageName());
        }
        updateIfNeeded(removePackageNamesFromPromotions(this.mVisiblePromotions, addedOrUpdatedPackageName));
    }

    public void onLaunchItemsRemoved(ArrayList<LaunchItem> removedItems) {
        boolean update = false;
        Iterator<LaunchItem> it = removedItems.iterator();
        while (true) {
            if (it.hasNext()) {
                if (this.mAllPromotionIds.contains(it.next().getPackageName())) {
                    update = true;
                    break;
                }
            } else {
                break;
            }
        }
        if (update) {
            this.mVisiblePromotions.clear();
            this.mVisiblePromotions.addAll(this.mAllPromotions);
            removePackageNamesFromPromotions(this.mVisiblePromotions, LaunchItemsManagerProvider.getInstance(this.mContext).getAllLaunchItemsPackageName());
            updateIfNeeded(true);
        }
    }

    public void onEditModeItemOrderChange(ArrayList<LaunchItem> arrayList, boolean isGameItems, Pair<Integer, Integer> pair) {
    }

    private class AppPromotionsLoadingTask extends AsyncTask<Void, Void, Boolean> {
        private OemAppPromotionsXmlParser.PromotionsData mPromotionsData;
        private ContentResolver mResolver;

        AppPromotionsLoadingTask(Context context) {
            this.mResolver = context.getContentResolver();
        }

        /* access modifiers changed from: protected */
        public Boolean doInBackground(Void... params) {
            boolean z = false;
            try {
                InputStream promotionsFile = this.mResolver.openInputStream(PartnerCustomizationContract.OEM_APP_RECS_URI);
                if (promotionsFile != null) {
                    this.mPromotionsData = OemAppPromotionsXmlParser.getInstance(OemAppPromotions.this.mContext).parse(promotionsFile);
                    try {
                        promotionsFile.close();
                    } catch (IOException e) {
                        String valueOf = String.valueOf(PartnerCustomizationContract.OEM_APP_RECS_URI);
                        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 14);
                        sb.append("Error closing ");
                        sb.append(valueOf);
                        Log.e(OemAppPromotions.TAG, sb.toString(), e);
                    }
                } else {
                    String valueOf2 = String.valueOf(PartnerCustomizationContract.OEM_APP_RECS_URI);
                    StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 14);
                    sb2.append("Error opening ");
                    sb2.append(valueOf2);
                    Log.e(OemAppPromotions.TAG, sb2.toString());
                }
                if (this.mPromotionsData != null) {
                    z = true;
                }
                return Boolean.valueOf(z);
            } catch (Exception e2) {
                String valueOf3 = String.valueOf(PartnerCustomizationContract.OEM_APP_RECS_URI);
                StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf3).length() + 14);
                sb3.append("Error opening ");
                sb3.append(valueOf3);
                Log.e(OemAppPromotions.TAG, sb3.toString(), e2);
                return false;
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Boolean result) {
            super.onPostExecute((Object) result);
            if (result.booleanValue()) {
                if (!TextUtils.isEmpty(this.mPromotionsData.getRowTitle())) {
                    String unused = OemAppPromotions.this.mPromotionsRowTitle = this.mPromotionsData.getRowTitle();
                }
                OemAppPromotions.this.mAllPromotions.clear();
                OemAppPromotions.this.mAllPromotionIds.clear();
                OemAppPromotions.this.mVisiblePromotions.clear();
                OemAppPromotions.this.mAllPromotions.addAll(this.mPromotionsData.getPromotions());
                OemAppPromotions.this.mVisiblePromotions.addAll(this.mPromotionsData.getPromotions());
                for (OemPromotionApp promotion : OemAppPromotions.this.mAllPromotions) {
                    OemAppPromotions.this.mAllPromotionIds.add(promotion.getId());
                }
                OemAppPromotions oemAppPromotions = OemAppPromotions.this;
                boolean unused2 = oemAppPromotions.removePackageNamesFromPromotions(oemAppPromotions.mVisiblePromotions, LaunchItemsManagerProvider.getInstance(OemAppPromotions.this.mContext).getAllLaunchItemsPackageName());
                for (OnAppPromotionsLoadedListener listener : OemAppPromotions.this.mAppPromotionsLoadedListeners) {
                    listener.onAppPromotionsLoaded(OemAppPromotions.this.mVisiblePromotions);
                }
            }
        }
    }
}
