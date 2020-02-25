package com.google.android.gms.common;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.p001v4.app.Fragment;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzg;

public final class GooglePlayServicesUtil extends GooglePlayServicesUtilLight {
    public static final String GMS_ERROR_DIALOG = "GooglePlayServicesErrorDialog";
    @Deprecated
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
    @Deprecated
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = GooglePlayServicesUtilLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    public static final String GOOGLE_PLAY_STORE_PACKAGE = "com.android.vending";

    private GooglePlayServicesUtil() {
    }

    @Deprecated
    public static Dialog getErrorDialog(int i, Activity activity, int i2) {
        return getErrorDialog(i, activity, i2, null);
    }

    @Deprecated
    public static Dialog getErrorDialog(int i, Activity activity, int i2, DialogInterface.OnCancelListener onCancelListener) {
        if (isPlayServicesPossiblyUpdating(activity, i)) {
            i = 18;
        }
        return GoogleApiAvailability.getInstance().getErrorDialog(activity, i, i2, onCancelListener);
    }

    @Deprecated
    public static boolean showErrorDialogFragment(int i, Activity activity, int i2, DialogInterface.OnCancelListener onCancelListener) {
        return showErrorDialogFragment(i, activity, null, i2, onCancelListener);
    }

    public static boolean showErrorDialogFragment(int i, Activity activity, Fragment fragment, int i2, DialogInterface.OnCancelListener onCancelListener) {
        if (isPlayServicesPossiblyUpdating(activity, i)) {
            i = 18;
        }
        GoogleApiAvailability instance = GoogleApiAvailability.getInstance();
        if (fragment == null) {
            return instance.showErrorDialogFragment(activity, i, i2, onCancelListener);
        }
        Dialog zza = GoogleApiAvailability.zza(activity, i, zzg.zza(fragment, GoogleApiAvailability.getInstance().getErrorResolutionIntent(activity, i, "d"), i2), onCancelListener);
        if (zza == null) {
            return false;
        }
        GoogleApiAvailability.zza(activity, zza, GMS_ERROR_DIALOG, onCancelListener);
        return true;
    }

    @Deprecated
    public static void showErrorNotification(int i, Context context) {
        GoogleApiAvailability instance = GoogleApiAvailability.getInstance();
        if (isPlayServicesPossiblyUpdating(context, i) || isPlayStorePossiblyUpdating(context, i)) {
            instance.zza(context);
        } else {
            instance.showErrorNotification(context, i);
        }
    }

    @Deprecated
    public static boolean showErrorDialogFragment(int i, Activity activity, int i2) {
        return showErrorDialogFragment(i, activity, i2, null);
    }

    @Hide
    public static void enableUsingApkIndependentContext() {
        GooglePlayServicesUtilLight.enableUsingApkIndependentContext();
    }

    @Deprecated
    public static String getErrorString(int i) {
        return GooglePlayServicesUtilLight.getErrorString(i);
    }

    @Deprecated
    public static int isGooglePlayServicesAvailable(Context context) {
        return GooglePlayServicesUtilLight.isGooglePlayServicesAvailable(context);
    }

    @Hide
    @Deprecated
    public static Intent getGooglePlayServicesAvailabilityRecoveryIntent(int i) {
        return GooglePlayServicesUtilLight.getGooglePlayServicesAvailabilityRecoveryIntent(i);
    }

    @Hide
    public static boolean honorsDebugCertificates(Context context) {
        return GooglePlayServicesUtilLight.honorsDebugCertificates(context);
    }

    @Deprecated
    public static PendingIntent getErrorPendingIntent(int i, Context context, int i2) {
        return GooglePlayServicesUtilLight.getErrorPendingIntent(i, context, i2);
    }

    @Deprecated
    public static boolean isUserRecoverableError(int i) {
        return GooglePlayServicesUtilLight.isUserRecoverableError(i);
    }

    public static Resources getRemoteResource(Context context) {
        return GooglePlayServicesUtilLight.getRemoteResource(context);
    }

    public static Context getRemoteContext(Context context) {
        return GooglePlayServicesUtilLight.getRemoteContext(context);
    }

    @Hide
    @Deprecated
    public static int getClientVersion(Context context) {
        return GooglePlayServicesUtilLight.getClientVersion(context);
    }

    @Hide
    @Deprecated
    public static int getApkVersion(Context context) {
        return GooglePlayServicesUtilLight.getApkVersion(context);
    }

    @Hide
    public static boolean isSidewinderDevice(Context context) {
        return GooglePlayServicesUtilLight.isSidewinderDevice(context);
    }

    @Hide
    @Deprecated
    public static boolean isPlayServicesPossiblyUpdating(Context context, int i) {
        return GooglePlayServicesUtilLight.isPlayServicesPossiblyUpdating(context, i);
    }

    @Hide
    @Deprecated
    public static boolean isPlayStorePossiblyUpdating(Context context, int i) {
        return GooglePlayServicesUtilLight.isPlayStorePossiblyUpdating(context, i);
    }
}
