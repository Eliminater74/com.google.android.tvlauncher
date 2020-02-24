package com.google.android.libraries.gcoreclient.common;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import javax.annotation.Nullable;

public interface GcoreGoogleApiAvailability {
    Dialog getErrorDialog(Activity activity, int i, int i2);

    Dialog getErrorDialog(Activity activity, int i, int i2, DialogInterface.OnCancelListener onCancelListener);

    @Nullable
    PendingIntent getErrorResolutionPendingIntent(Context context, int i, int i2);

    String getErrorString(int i);

    @Nullable
    String getOpenSourceSoftwareLicenseInfo(Context context);

    String googlePlayServicesPackage();

    int googlePlayServicesVersionCode();

    int isGooglePlayServicesAvailable(Context context);

    int isGooglePlayServicesAvailable(Context context, int i);

    boolean isUserResolvableError(int i);

    boolean showErrorDialogFragment(Activity activity, int i, int i2);

    boolean showErrorDialogFragment(Activity activity, int i, int i2, DialogInterface.OnCancelListener onCancelListener);

    void showErrorNotification(Context context, int i);
}
