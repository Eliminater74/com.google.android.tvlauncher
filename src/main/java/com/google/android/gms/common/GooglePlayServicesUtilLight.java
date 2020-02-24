package com.google.android.gms.common;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.UserManager;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzaj;
import com.google.android.gms.common.internal.zzau;
import com.google.android.gms.common.util.zzd;
import com.google.android.gms.common.util.zzi;
import com.google.android.gms.common.util.zzk;
import com.google.android.gms.common.util.zzp;
import com.google.android.gms.common.util.zzu;
import com.google.android.gms.internal.zzbmk;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

@Hide
public class GooglePlayServicesUtilLight {
    @Deprecated
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
    @Deprecated
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = 12525000;
    public static final String GOOGLE_PLAY_STORE_PACKAGE = "com.android.vending";
    static final AtomicBoolean zza = new AtomicBoolean();
    @Hide
    private static boolean zzb = false;
    @Hide
    private static boolean zzc = false;
    private static boolean zzd = false;
    private static boolean zze = false;
    private static final AtomicBoolean zzf = new AtomicBoolean();

    @Hide
    public static void enableUsingApkIndependentContext() {
        zzf.set(true);
    }

    GooglePlayServicesUtilLight() {
    }

    @Deprecated
    public static String getErrorString(int i) {
        return ConnectionResult.zza(i);
    }

    @Deprecated
    public static int isGooglePlayServicesAvailable(Context context) {
        return isGooglePlayServicesAvailable(context, GOOGLE_PLAY_SERVICES_VERSION_CODE);
    }

    @Deprecated
    public static int isGooglePlayServicesAvailable(Context context, int i) {
        try {
            context.getResources().getString(C0952R.string.common_google_play_services_unknown_issue);
        } catch (Throwable th) {
            Log.e("GooglePlayServicesUtil", "The Google Play services resources were not found. Check your project configuration to ensure that the resources are included.");
        }
        if (!"com.google.android.gms".equals(context.getPackageName()) && !zzf.get()) {
            int zzb2 = zzaj.zzb(context);
            if (zzb2 != 0) {
                int i2 = GOOGLE_PLAY_SERVICES_VERSION_CODE;
                if (zzb2 != i2) {
                    StringBuilder sb = new StringBuilder((int) ClientAnalytics.LogRequest.LogSource.ANDROID_MIGRATE_VALUE);
                    sb.append("The meta-data tag in your app's AndroidManifest.xml does not have the right value.  Expected ");
                    sb.append(i2);
                    sb.append(" but found ");
                    sb.append(zzb2);
                    sb.append(".  You must have the following declaration within the <application> element:     <meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />");
                    throw new IllegalStateException(sb.toString());
                }
            } else {
                throw new IllegalStateException("A required meta-data tag in your app's AndroidManifest.xml does not exist.  You must have the following declaration within the <application> element:     <meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />");
            }
        }
        return zza(context, !zzi.zzb(context) && !zzi.zzd(context), i);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.GoogleSignatureVerifier.zza(android.content.pm.PackageInfo, boolean):boolean
     arg types: [android.content.pm.PackageInfo, int]
     candidates:
      com.google.android.gms.common.GoogleSignatureVerifier.zza(android.content.pm.PackageInfo, com.google.android.gms.common.zzf[]):com.google.android.gms.common.zzf
      com.google.android.gms.common.GoogleSignatureVerifier.zza(android.content.pm.PackageInfo, boolean):boolean */
    private static int zza(Context context, boolean z, int i) {
        zzau.zzb(i >= 0);
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        if (z) {
            try {
                packageInfo = packageManager.getPackageInfo("com.android.vending", 8256);
            } catch (PackageManager.NameNotFoundException e) {
                Log.w("GooglePlayServicesUtil", "Google Play Store is missing.");
                return 9;
            }
        }
        try {
            PackageInfo packageInfo2 = packageManager.getPackageInfo("com.google.android.gms", 64);
            GoogleSignatureVerifier.getInstance(context);
            if (!GoogleSignatureVerifier.zza(packageInfo2, true)) {
                Log.w("GooglePlayServicesUtil", "Google Play services signature invalid.");
                return 9;
            } else if (z && (!GoogleSignatureVerifier.zza(packageInfo, true) || !packageInfo.signatures[0].equals(packageInfo2.signatures[0]))) {
                Log.w("GooglePlayServicesUtil", "Google Play Store signature invalid.");
                return 9;
            } else if (zzk.zza(packageInfo2.versionCode) < zzk.zza(i)) {
                int i2 = packageInfo2.versionCode;
                StringBuilder sb = new StringBuilder(77);
                sb.append("Google Play services out of date.  Requires ");
                sb.append(i);
                sb.append(" but found ");
                sb.append(i2);
                Log.w("GooglePlayServicesUtil", sb.toString());
                return 2;
            } else {
                ApplicationInfo applicationInfo = packageInfo2.applicationInfo;
                if (applicationInfo == null) {
                    try {
                        applicationInfo = packageManager.getApplicationInfo("com.google.android.gms", 0);
                    } catch (PackageManager.NameNotFoundException e2) {
                        Log.wtf("GooglePlayServicesUtil", "Google Play services missing when getting application info.", e2);
                        return 1;
                    }
                }
                if (!applicationInfo.enabled) {
                    return 3;
                }
                return 0;
            }
        } catch (PackageManager.NameNotFoundException e3) {
            Log.w("GooglePlayServicesUtil", "Google Play services is missing.");
            return 1;
        }
    }

    @Hide
    @Deprecated
    public static void zza(Context context) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
        int isGooglePlayServicesAvailable = GoogleApiAvailabilityLight.getInstance().isGooglePlayServicesAvailable(context, GOOGLE_PLAY_SERVICES_VERSION_CODE);
        if (isGooglePlayServicesAvailable != 0) {
            Intent errorResolutionIntent = GoogleApiAvailabilityLight.getInstance().getErrorResolutionIntent(context, isGooglePlayServicesAvailable, "e");
            StringBuilder sb = new StringBuilder(57);
            sb.append("GooglePlayServices not available due to error ");
            sb.append(isGooglePlayServicesAvailable);
            Log.e("GooglePlayServicesUtil", sb.toString());
            if (errorResolutionIntent == null) {
                throw new GooglePlayServicesNotAvailableException(isGooglePlayServicesAvailable);
            }
            throw new GooglePlayServicesRepairableException(isGooglePlayServicesAvailable, "Google Play Services not available", errorResolutionIntent);
        }
    }

    @Hide
    @Deprecated
    public static boolean zza(Context context, int i) {
        return zzu.zza(context, i);
    }

    @Hide
    @TargetApi(19)
    @Deprecated
    public static boolean zza(Context context, int i, String str) {
        return zzu.zza(context, i, str);
    }

    @Hide
    @Deprecated
    public static Intent getGooglePlayServicesAvailabilityRecoveryIntent(int i) {
        return GoogleApiAvailabilityLight.getInstance().getErrorResolutionIntent(null, i, null);
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.GoogleSignatureVerifier.zza(android.content.pm.PackageInfo, boolean):boolean
     arg types: [android.content.pm.PackageInfo, int]
     candidates:
      com.google.android.gms.common.GoogleSignatureVerifier.zza(android.content.pm.PackageInfo, com.google.android.gms.common.zzf[]):com.google.android.gms.common.zzf
      com.google.android.gms.common.GoogleSignatureVerifier.zza(android.content.pm.PackageInfo, boolean):boolean */
    @Hide
    public static boolean honorsDebugCertificates(Context context) {
        if (!zze) {
            try {
                PackageInfo zzb2 = zzbmk.zza(context).zzb("com.google.android.gms", 64);
                GoogleSignatureVerifier.getInstance(context);
                if (zzb2 == null || GoogleSignatureVerifier.zza(zzb2, false) || !GoogleSignatureVerifier.zza(zzb2, true)) {
                    zzd = false;
                } else {
                    zzd = true;
                }
            } catch (PackageManager.NameNotFoundException e) {
                Log.w("GooglePlayServicesUtil", "Cannot find Google Play services package name.", e);
            } finally {
                zze = true;
            }
        }
        return zzd || !"user".equals(Build.TYPE);
    }

    @Deprecated
    public static PendingIntent getErrorPendingIntent(int i, Context context, int i2) {
        return GoogleApiAvailabilityLight.getInstance().getErrorResolutionPendingIntent(context, i, i2);
    }

    @Hide
    @Deprecated
    public static void zzb(Context context) {
        if (!zza.getAndSet(true)) {
            try {
                NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
                if (notificationManager != null) {
                    notificationManager.cancel(10436);
                }
            } catch (SecurityException e) {
            }
        }
    }

    @Deprecated
    public static boolean isUserRecoverableError(int i) {
        if (i == 1 || i == 2 || i == 3 || i == 9) {
            return true;
        }
        return false;
    }

    @Deprecated
    public static String getOpenSourceSoftwareLicenseInfo(Context context) {
        InputStream openInputStream;
        try {
            openInputStream = context.getContentResolver().openInputStream(new Uri.Builder().scheme("android.resource").authority("com.google.android.gms").appendPath("raw").appendPath("oss_notice").build());
            String next = new Scanner(openInputStream).useDelimiter("\\A").next();
            if (openInputStream != null) {
                openInputStream.close();
            }
            return next;
        } catch (NoSuchElementException e) {
            if (openInputStream != null) {
                openInputStream.close();
            }
            return null;
        } catch (Exception e2) {
            return null;
        } catch (Throwable th) {
            if (openInputStream != null) {
                openInputStream.close();
            }
            throw th;
        }
    }

    public static Resources getRemoteResource(Context context) {
        try {
            return context.getPackageManager().getResourcesForApplication("com.google.android.gms");
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    public static Context getRemoteContext(Context context) {
        try {
            return context.createPackageContext("com.google.android.gms", 3);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    @Hide
    @Deprecated
    public static int getClientVersion(Context context) {
        zzau.zza(true);
        return zzd.zza(context, context.getPackageName());
    }

    @Hide
    @Deprecated
    public static int getApkVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo("com.google.android.gms", 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("GooglePlayServicesUtil", "Google Play services is missing.");
            return 0;
        }
    }

    @Hide
    @Deprecated
    public static boolean isSidewinderDevice(Context context) {
        return zzi.zzc(context);
    }

    @Hide
    @Deprecated
    public static boolean isPlayServicesPossiblyUpdating(Context context, int i) {
        if (i == 18) {
            return true;
        }
        if (i == 1) {
            return zza(context, "com.google.android.gms");
        }
        return false;
    }

    @Hide
    @Deprecated
    public static boolean isPlayStorePossiblyUpdating(Context context, int i) {
        if (i == 9) {
            return zza(context, "com.android.vending");
        }
        return false;
    }

    @TargetApi(21)
    static boolean zza(Context context, String str) {
        boolean z;
        Bundle applicationRestrictions;
        boolean equals = str.equals("com.google.android.gms");
        if (zzp.zzg()) {
            try {
                for (PackageInstaller.SessionInfo appPackageName : context.getPackageManager().getPackageInstaller().getAllSessions()) {
                    if (str.equals(appPackageName.getAppPackageName())) {
                        return true;
                    }
                }
            } catch (Exception e) {
                return false;
            }
        }
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(str, 8192);
            if (equals) {
                return applicationInfo.enabled;
            }
            if (applicationInfo.enabled) {
                if (!zzp.zzd() || (applicationRestrictions = ((UserManager) context.getSystemService("user")).getApplicationRestrictions(context.getPackageName())) == null || !"true".equals(applicationRestrictions.getString("restricted_profile"))) {
                    z = false;
                } else {
                    z = true;
                }
                if (!z) {
                    return true;
                }
            }
            return false;
        } catch (PackageManager.NameNotFoundException e2) {
            return false;
        }
    }
}
