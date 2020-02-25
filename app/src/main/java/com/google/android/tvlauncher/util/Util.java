package com.google.android.tvlauncher.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.FeatureInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.accessibility.AccessibilityManager;

import com.google.android.tvrecommendations.shared.util.AppUtil;

import java.util.Collections;
import java.util.HashSet;

public class Util {
    private static final String DYNAMIC_CONFIG_FEATURE = "com.google.android.tv.dynamic_config";
    private static final String OPERATOR_TIER_FEATURE = "com.google.android.tv.operator_tier";
    private static final String TAG = "TVLauncher.Util";

    private Util() {
    }

    public static boolean isRtl(Context context) {
        return context.getResources().getConfiguration().getLayoutDirection() == 1;
    }

    public static boolean isAccessibilityEnabled(Context context) {
        AccessibilityManager am = (AccessibilityManager) context.getSystemService("accessibility");
        if (am == null || !am.isEnabled()) {
            return false;
        }
        if (am.isTouchExplorationEnabled()) {
            return true;
        }
        return true ^ am.getEnabledAccessibilityServiceList(16).isEmpty();
    }

    public static boolean isAccessibilityManagerEnabled(Context context) {
        AccessibilityManager am = (AccessibilityManager) context.getSystemService("accessibility");
        return am != null && am.isEnabled();
    }

    @Nullable
    public static CharSequence safeTrim(@Nullable CharSequence string) {
        if (string instanceof String) {
            return ((String) string).trim();
        }
        return string;
    }

    public static boolean isLauncherOrSystemApp(Context context, String packageName) {
        return TextUtils.equals(context.getApplicationContext().getPackageName(), packageName) || AppUtil.isSystemApp(context, packageName);
    }

    public static boolean isOperatorTierDevice(Context context) {
        return hasSystemFeature(context, OPERATOR_TIER_FEATURE);
    }

    public static boolean isDynamicConfigDevice(Context context) {
        return hasSystemFeature(context, DYNAMIC_CONFIG_FEATURE);
    }

    public static boolean hasSystemFeature(Context context, String featureName) {
        FeatureInfo[] features = context.getPackageManager().getSystemAvailableFeatures();
        if (features == null) {
            return false;
        }
        for (FeatureInfo feature : features) {
            if (featureName.equals(feature.name)) {
                return true;
            }
        }
        return false;
    }

    public static void forceLandscapeOrientation(Activity activity) {
        if (activity.getApplicationInfo().targetSdkVersion < 27 || Build.VERSION.SDK_INT != 26) {
            activity.setRequestedOrientation(0);
        }
    }

    @SafeVarargs
    public static <T> HashSet<T> newHashSet(T... itemList) {
        HashSet<T> result = new HashSet<>();
        Collections.addAll(result, itemList);
        return result;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.content.res.Resources.getValue(int, android.util.TypedValue, boolean):void throws android.content.res.Resources$NotFoundException}
     arg types: [int, android.util.TypedValue, int]
     candidates:
      ClspMth{android.content.res.Resources.getValue(java.lang.String, android.util.TypedValue, boolean):void throws android.content.res.Resources$NotFoundException}
      ClspMth{android.content.res.Resources.getValue(int, android.util.TypedValue, boolean):void throws android.content.res.Resources$NotFoundException} */
    public static float getFloat(Resources res, int resourceId) {
        TypedValue resValue = new TypedValue();
        res.getValue(resourceId, resValue, true);
        return resValue.getFloat();
    }

    public static boolean isValidContextForGlide(Context context) {
        if (context == null) {
            return false;
        }
        if (context instanceof Activity) {
            Activity activity = (Activity) context;
            if (activity.isDestroyed() || activity.isFinishing()) {
                return false;
            }
        }
        if (context instanceof ContextThemeWrapper) {
            return isValidContextForGlide(((ContextThemeWrapper) context).getBaseContext());
        }
        return true;
    }

    public static boolean areHomeScreenAnimationsEnabled(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("enable_animations", true);
    }

    public static Uri getUri(String uri) {
        if (uri != null) {
            return Uri.parse(uri);
        }
        return null;
    }

    public static Uri getDrawableUri(@NonNull Context context, @DrawableRes int resourceId) {
        return new Uri.Builder().scheme("android.resource").authority(context.getPackageName()).appendPath("drawable").appendPath(context.getResources().getResourceEntryName(resourceId)).build();
    }
}
