package com.google.android.libraries.gcoreclient.common;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import javax.annotation.CheckReturnValue;

public interface GooglePlayServicesUtil {
    void enableUsingApkIndependentContext();

    int getApkVersion(Context context);

    int getClientVersion();

    Dialog getErrorDialog(int i, Activity activity, int i2, DialogInterface.OnCancelListener onCancelListener);

    Intent getGooglePlayServicesAvailabilityRecoveryIntent(int i);

    String getGooglePlayServicesPackage();

    String getGooglePlayStorePackage();

    int isGooglePlayServicesAvailable(Context context);

    @CheckReturnValue
    boolean isPackageGoogleSigned(Context context, String str);

    boolean isUserRecoverableError(int i);

    boolean showErrorDialogFragment(int i, Activity activity, int i2, DialogInterface.OnCancelListener onCancelListener);

    void showErrorNotification(int i, Context context);

    void verifyPackageIsGoogleSigned(Context context, String str) throws SecurityException;
}
