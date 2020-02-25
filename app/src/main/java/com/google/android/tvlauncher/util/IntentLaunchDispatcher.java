package com.google.android.tvlauncher.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.support.annotation.VisibleForTesting;
import android.support.p001v4.content.IntentCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.exoplayer2.C0841C;
import com.google.android.tvlauncher.C1188R;

import java.net.URISyntaxException;
import java.util.List;

public class IntentLaunchDispatcher {
    private static final boolean DEBUG = false;
    private static final String TAG = "IntentLaunchDispatcher";
    private Context mContext;
    private Intent mIntent;
    private LeanbackCategoryIntentLauncher mLeanbackCategoryIntentLauncher;
    private PlayStoreIntentLauncher mPlayStoreIntentLauncher;
    private TvRecommendationsIntentLauncher mTvRecommendationsIntentLauncher;
    private View mViewToStartAnimation;

    public IntentLaunchDispatcher(Context context) {
        this(context, new TvRecommendationsIntentLauncher(), new LeanbackCategoryIntentLauncher(), new PlayStoreIntentLauncher());
    }

    @VisibleForTesting
    IntentLaunchDispatcher(Context context, TvRecommendationsIntentLauncher tvRecommendationsFallbackIntentHandler, LeanbackCategoryIntentLauncher leanbackLauncherFallbackIntentHandler, PlayStoreIntentLauncher playStoreFallbackIntentHandler) {
        this.mContext = context;
        this.mTvRecommendationsIntentLauncher = tvRecommendationsFallbackIntentHandler;
        this.mLeanbackCategoryIntentLauncher = leanbackLauncherFallbackIntentHandler;
        this.mPlayStoreIntentLauncher = playStoreFallbackIntentHandler;
    }

    public String launchMediaIntentForDoubleClickAd(String packageName, String deeplinkUrl, String marketUrl) {
        if (PackageUtils.isPackageInstalled(this.mContext, packageName)) {
            if (TextUtils.isEmpty(deeplinkUrl)) {
                this.mLeanbackCategoryIntentLauncher.launchIntent(this.mContext, packageName, null);
                return null;
            } else if (launchIntentFromUriByPackage(packageName, deeplinkUrl, true)) {
                return deeplinkUrl;
            } else {
                this.mLeanbackCategoryIntentLauncher.launchIntent(this.mContext, packageName, null);
                return null;
            }
        } else if (TextUtils.isEmpty(marketUrl)) {
            this.mPlayStoreIntentLauncher.launchIntent(this.mContext, packageName, null);
            return null;
        } else {
            if (!launchIntentFromUri(marketUrl, false)) {
                this.mPlayStoreIntentLauncher.launchIntent(this.mContext, packageName, null);
            }
            return null;
        }
    }

    public String launchMediaIntentForDirectAd(String packageName, String dataUrl) {
        if (TextUtils.isEmpty(packageName)) {
            try {
                packageName = Intent.parseUri(dataUrl, 1).getPackage();
            } catch (URISyntaxException e) {
                String valueOf = String.valueOf(dataUrl);
                Log.e(TAG, valueOf.length() != 0 ? "Bad URI syntax: ".concat(valueOf) : new String("Bad URI syntax: "));
                return null;
            }
        }
        if (TextUtils.isEmpty(packageName)) {
            if (TextUtils.isEmpty(dataUrl)) {
                Log.e(TAG, "Failed to launch. Both packageName and dataUrl are empty.");
                return null;
            } else if (launchIntentFromUri(dataUrl, true)) {
                return dataUrl;
            } else {
                return null;
            }
        } else if (!PackageUtils.isPackageInstalled(this.mContext, packageName)) {
            this.mPlayStoreIntentLauncher.launchIntent(this.mContext, packageName, null);
            return null;
        } else if (TextUtils.isEmpty(dataUrl)) {
            this.mLeanbackCategoryIntentLauncher.launchIntent(this.mContext, packageName, null);
            return null;
        } else if (launchIntentFromUriByPackage(packageName, dataUrl, true)) {
            return dataUrl;
        } else {
            this.mLeanbackCategoryIntentLauncher.launchIntent(this.mContext, packageName, null);
            return null;
        }
    }

    public boolean launchChannelIntentFromUri(String packageName, String uri, boolean launchMedia) {
        boolean success = launchIntentFromUriByPackage(packageName, uri, launchMedia) || this.mTvRecommendationsIntentLauncher.launchIntent(this.mContext, packageName, this.mIntent);
        if (!success) {
            Toast.makeText(this.mContext, C1188R.string.failed_launch, 0).show();
        }
        return success;
    }

    public boolean launchChannelIntentFromUriWithAnimation(String packageName, String uri, boolean launchMedia, View view) {
        boolean success = launchIntentFromUriByPackageWithAnimation(packageName, uri, launchMedia, view) || this.mTvRecommendationsIntentLauncher.launchIntent(this.mContext, packageName, this.mIntent);
        if (!success) {
            Toast.makeText(this.mContext, C1188R.string.failed_launch, 0).show();
        }
        return success;
    }

    public boolean launchIntentFromUri(String uri, boolean launchMedia) {
        this.mIntent = parseUri(uri, launchMedia);
        this.mViewToStartAnimation = null;
        return launchIntent();
    }

    private boolean launchIntentFromUriByPackage(String packageName, String uri, boolean launchMedia) {
        this.mIntent = parseUri(uri, launchMedia);
        if (packageName != null) {
            this.mIntent.setPackage(packageName);
        }
        this.mViewToStartAnimation = null;
        return launchIntent();
    }

    private boolean launchIntentFromUriByPackageWithAnimation(String packageName, String uri, boolean launchMedia, View view) {
        this.mIntent = parseUri(uri, launchMedia);
        if (packageName != null) {
            this.mIntent.setPackage(packageName);
        }
        this.mViewToStartAnimation = view;
        return launchIntent();
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.content.Intent.putExtra(java.lang.String, boolean):android.content.Intent}
     arg types: [java.lang.String, int]
     candidates:
      ClspMth{android.content.Intent.putExtra(java.lang.String, int):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, java.lang.String[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, int[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, double):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, char):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, boolean[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, byte):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, android.os.Bundle):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, float):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, java.lang.CharSequence[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, java.lang.CharSequence):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, long[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, long):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, short):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, android.os.Parcelable[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, java.io.Serializable):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, double[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, android.os.Parcelable):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, float[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, byte[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, java.lang.String):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, short[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, char[]):android.content.Intent}
      ClspMth{android.content.Intent.putExtra(java.lang.String, boolean):android.content.Intent} */
    private Intent parseUri(String uri, boolean launchMedia) {
        if (uri == null) {
            Log.e(TAG, "No URI provided");
            return null;
        }
        try {
            Intent intent = Intent.parseUri(uri, 1);
            if (launchMedia) {
                intent.putExtra(IntentCompat.EXTRA_START_PLAYBACK, true);
            }
            return intent;
        } catch (URISyntaxException e) {
            String valueOf = String.valueOf(uri);
            Log.e(TAG, valueOf.length() != 0 ? "Bad URI syntax: ".concat(valueOf) : new String("Bad URI syntax: "));
            return null;
        }
    }

    private boolean launchIntent() {
        if (this.mIntent == null) {
            return false;
        }
        List<ResolveInfo> activities = this.mContext.getPackageManager().queryIntentActivities(this.mIntent, 0);
        this.mIntent.addFlags(C0841C.ENCODING_PCM_MU_LAW);
        if (activities == null || activities.size() <= 0) {
            return false;
        }
        try {
            if (this.mViewToStartAnimation != null) {
                LaunchUtil.startActivityWithAnimation(this.mIntent, this.mViewToStartAnimation);
                return true;
            }
            this.mContext.startActivity(this.mIntent);
            return true;
        } catch (ActivityNotFoundException | SecurityException e) {
            String valueOf = String.valueOf(this.mIntent);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 17);
            sb.append("Failed to launch ");
            sb.append(valueOf);
            Log.e(TAG, sb.toString(), e);
            return false;
        }
    }
}
