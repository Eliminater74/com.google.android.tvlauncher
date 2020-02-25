package com.google.android.gms.common;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.GuardedBy;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.p001v4.app.FragmentActivity;
import android.support.p001v4.app.NotificationCompat;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ProgressBar;

import com.google.android.gms.base.C0946R;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.common.api.internal.zzbn;
import com.google.android.gms.common.api.internal.zzby;
import com.google.android.gms.common.api.internal.zzbz;
import com.google.android.gms.common.api.internal.zzcf;
import com.google.android.gms.common.api.internal.zzco;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzau;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.common.util.zzi;
import com.google.android.gms.common.util.zzp;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;

import java.util.ArrayList;
import java.util.Arrays;

public class GoogleApiAvailability extends GoogleApiAvailabilityLight {
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = GoogleApiAvailabilityLight.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    private static final Object zza = new Object();
    private static final GoogleApiAvailability zzb = new GoogleApiAvailability();
    @GuardedBy("mLock")
    private String zzc;

    GoogleApiAvailability() {
    }

    public static GoogleApiAvailability getInstance() {
        return zzb;
    }

    @Hide
    public static Dialog zza(Activity activity, DialogInterface.OnCancelListener onCancelListener) {
        ProgressBar progressBar = new ProgressBar(activity, null, 16842874);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(0);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(progressBar);
        builder.setMessage(zzf.zzc(activity, 18));
        builder.setPositiveButton("", (DialogInterface.OnClickListener) null);
        AlertDialog create = builder.create();
        zza(activity, create, "GooglePlayServicesUpdatingDialog", onCancelListener);
        return create;
    }

    @Nullable
    @Hide
    public static zzby zza(Context context, zzbz zzbz) {
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
        intentFilter.addDataScheme("package");
        zzby zzby = new zzby(zzbz);
        context.registerReceiver(zzby, intentFilter);
        zzby.zza(context);
        if (GooglePlayServicesUtilLight.zza(context, "com.google.android.gms")) {
            return zzby;
        }
        zzbz.zza();
        zzby.zza();
        return null;
    }

    static Dialog zza(Context context, int i, zzg zzg, DialogInterface.OnCancelListener onCancelListener) {
        AlertDialog.Builder builder = null;
        if (i == 0) {
            return null;
        }
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(16843529, typedValue, true);
        if ("Theme.Dialog.Alert".equals(context.getResources().getResourceEntryName(typedValue.resourceId))) {
            builder = new AlertDialog.Builder(context, 5);
        }
        if (builder == null) {
            builder = new AlertDialog.Builder(context);
        }
        builder.setMessage(zzf.zzc(context, i));
        if (onCancelListener != null) {
            builder.setOnCancelListener(onCancelListener);
        }
        String zze = zzf.zze(context, i);
        if (zze != null) {
            builder.setPositiveButton(zze, zzg);
        }
        String zza2 = zzf.zza(context, i);
        if (zza2 != null) {
            builder.setTitle(zza2);
        }
        return builder.create();
    }

    static void zza(Activity activity, Dialog dialog, String str, DialogInterface.OnCancelListener onCancelListener) {
        if (activity instanceof FragmentActivity) {
            SupportErrorDialogFragment.newInstance(dialog, onCancelListener).show(((FragmentActivity) activity).getSupportFragmentManager(), str);
            return;
        }
        ErrorDialogFragment.newInstance(dialog, onCancelListener).show(activity.getFragmentManager(), str);
    }

    @MainThread
    public Task<Void> makeGooglePlayServicesAvailable(Activity activity) {
        zzau.zzb("makeGooglePlayServicesAvailable must be called from the main thread");
        int isGooglePlayServicesAvailable = isGooglePlayServicesAvailable(activity);
        if (isGooglePlayServicesAvailable == 0) {
            return Tasks.forResult(null);
        }
        zzco zza2 = zzco.zza(activity);
        zza2.zzb(new ConnectionResult(isGooglePlayServicesAvailable, null), 0);
        return zza2.zzf();
    }

    public Dialog getErrorDialog(Activity activity, int i, int i2) {
        return getErrorDialog(activity, i, i2, null);
    }

    public Dialog getErrorDialog(Activity activity, int i, int i2, DialogInterface.OnCancelListener onCancelListener) {
        return zza(activity, i, zzg.zza(activity, getErrorResolutionIntent(activity, i, "d"), i2), onCancelListener);
    }

    public boolean showErrorDialogFragment(Activity activity, int i, int i2) {
        return showErrorDialogFragment(activity, i, i2, null);
    }

    @Hide
    public final boolean zza(Activity activity, @NonNull zzcf zzcf, int i, int i2, DialogInterface.OnCancelListener onCancelListener) {
        Dialog zza2 = zza(activity, i, zzg.zza(zzcf, getErrorResolutionIntent(activity, i, "d"), 2), onCancelListener);
        if (zza2 == null) {
            return false;
        }
        zza(activity, zza2, GooglePlayServicesUtil.GMS_ERROR_DIALOG, onCancelListener);
        return true;
    }

    public boolean showErrorDialogFragment(Activity activity, int i, int i2, DialogInterface.OnCancelListener onCancelListener) {
        Dialog errorDialog = getErrorDialog(activity, i, i2, onCancelListener);
        if (errorDialog == null) {
            return false;
        }
        zza(activity, errorDialog, GooglePlayServicesUtil.GMS_ERROR_DIALOG, onCancelListener);
        return true;
    }

    public void showErrorNotification(Context context, int i) {
        zza(context, i, (String) null, getErrorResolutionPendingIntent(context, i, 0, "n"));
    }

    public void showErrorNotification(Context context, ConnectionResult connectionResult) {
        zza(context, connectionResult.getErrorCode(), (String) null, getErrorResolutionPendingIntent(context, connectionResult));
    }

    @Hide
    public final boolean zza(Context context, ConnectionResult connectionResult, int i) {
        PendingIntent errorResolutionPendingIntent = getErrorResolutionPendingIntent(context, connectionResult);
        if (errorResolutionPendingIntent == null) {
            return false;
        }
        zza(context, connectionResult.getErrorCode(), (String) null, GoogleApiActivity.zza(context, errorResolutionPendingIntent, i));
        return true;
    }

    public Task<Void> checkApiAvailability(GoogleApi<?> googleApi, GoogleApi<?>... googleApiArr) {
        zzau.zza(googleApi, "Requested API must not be null.");
        for (GoogleApi<?> zza2 : googleApiArr) {
            zzau.zza(zza2, "Requested API must not be null.");
        }
        ArrayList arrayList = new ArrayList(googleApiArr.length + 1);
        arrayList.add(googleApi);
        arrayList.addAll(Arrays.asList(googleApiArr));
        return zzbn.zza().zza(arrayList).continueWith(new zzd(this));
    }

    @VisibleForTesting(otherwise = 2)
    private final String zza() {
        String str;
        synchronized (zza) {
            str = this.zzc;
        }
        return str;
    }

    @TargetApi(26)
    public void setDefaultNotificationChannelId(@NonNull Context context, @NonNull String str) {
        if (zzp.zzk()) {
            zzau.zza(((NotificationManager) context.getSystemService("notification")).getNotificationChannel(str));
        }
        synchronized (zza) {
            this.zzc = str;
        }
    }

    public int isGooglePlayServicesAvailable(Context context) {
        return super.isGooglePlayServicesAvailable(context);
    }

    public final boolean isUserResolvableError(int i) {
        return super.isUserResolvableError(i);
    }

    @Nullable
    @Hide
    @Deprecated
    public Intent getErrorResolutionIntent(int i) {
        return super.getErrorResolutionIntent(i);
    }

    @Nullable
    @Hide
    public Intent getErrorResolutionIntent(Context context, int i, @Nullable String str) {
        return super.getErrorResolutionIntent(context, i, str);
    }

    @Nullable
    public PendingIntent getErrorResolutionPendingIntent(Context context, int i, int i2) {
        return super.getErrorResolutionPendingIntent(context, i, i2);
    }

    @Nullable
    public PendingIntent getErrorResolutionPendingIntent(Context context, ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            return connectionResult.getResolution();
        }
        return getErrorResolutionPendingIntent(context, connectionResult.getErrorCode(), 0);
    }

    @Nullable
    @Hide
    public PendingIntent getErrorResolutionPendingIntent(Context context, int i, int i2, @Nullable String str) {
        return super.getErrorResolutionPendingIntent(context, i, i2, str);
    }

    @Hide
    public int getClientVersion(Context context) {
        return super.getClientVersion(context);
    }

    @Hide
    public int getApkVersion(Context context) {
        return super.getApkVersion(context);
    }

    @Hide
    public boolean isPlayServicesPossiblyUpdating(Context context, int i) {
        return super.isPlayServicesPossiblyUpdating(context, i);
    }

    @Hide
    public boolean isPlayStorePossiblyUpdating(Context context, int i) {
        return super.isPlayStorePossiblyUpdating(context, i);
    }

    public final String getErrorString(int i) {
        return super.getErrorString(i);
    }

    @TargetApi(20)
    private final void zza(Context context, int i, String str, PendingIntent pendingIntent) {
        Notification notification;
        int i2;
        if (i == 18) {
            zza(context);
        } else if (pendingIntent != null) {
            String zzb2 = zzf.zzb(context, i);
            String zzd = zzf.zzd(context, i);
            Resources resources = context.getResources();
            NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
            if (zzi.zzb(context)) {
                zzau.zza(zzp.zzf());
                Notification.Builder addAction = new Notification.Builder(context).setSmallIcon(context.getApplicationInfo().icon).setPriority(2).setAutoCancel(true).setContentTitle(zzb2).setStyle(new Notification.BigTextStyle().bigText(zzd)).addAction(C0946R.C0947drawable.common_full_open_on_phone, resources.getString(C0946R.string.common_open_on_phone), pendingIntent);
                if (zzp.zzk() && zzp.zzk()) {
                    addAction.setChannelId(zza(context, notificationManager));
                }
                notification = addAction.build();
            } else {
                NotificationCompat.Builder style = new NotificationCompat.Builder(context).setSmallIcon(17301642).setTicker(resources.getString(C0946R.string.common_google_play_services_notification_ticker)).setWhen(System.currentTimeMillis()).setAutoCancel(true).setContentIntent(pendingIntent).setContentTitle(zzb2).setContentText(zzd).setLocalOnly(true).setStyle(new NotificationCompat.BigTextStyle().bigText(zzd));
                if (zzp.zzk() && zzp.zzk()) {
                    style.setChannelId(zza(context, notificationManager));
                }
                notification = style.build();
            }
            if (i == 1 || i == 2 || i == 3) {
                i2 = 10436;
                GooglePlayServicesUtilLight.zza.set(false);
            } else {
                i2 = 39789;
            }
            notificationManager.notify(i2, notification);
        } else if (i == 6) {
            Log.w("GoogleApiAvailability", "Missing resolution for ConnectionResult.RESOLUTION_REQUIRED. Call GoogleApiAvailability#showErrorNotification(Context, ConnectionResult) instead.");
        }
    }

    @TargetApi(26)
    private final String zza(Context context, NotificationManager notificationManager) {
        zzau.zza(zzp.zzk());
        String zza2 = zza();
        if (zza2 == null) {
            zza2 = "com.google.android.gms.availability";
            NotificationChannel notificationChannel = notificationManager.getNotificationChannel(zza2);
            String zza3 = zzf.zza(context);
            if (notificationChannel == null) {
                notificationManager.createNotificationChannel(new NotificationChannel(zza2, zza3, 4));
            } else if (!zza3.equals(notificationChannel.getName())) {
                notificationChannel.setName(zza3);
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        return zza2;
    }

    /* access modifiers changed from: package-private */
    public final void zza(Context context) {
        new zza(context).sendEmptyMessageDelayed(1, 120000);
    }

    @SuppressLint({"HandlerLeak"})
    class zza extends Handler {
        private final Context zza;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public zza(Context context) {
            super(Looper.myLooper() == null ? Looper.getMainLooper() : Looper.myLooper());
            this.zza = context.getApplicationContext();
        }

        public final void handleMessage(Message message) {
            if (message.what != 1) {
                int i = message.what;
                StringBuilder sb = new StringBuilder(50);
                sb.append("Don't know how to handle this message: ");
                sb.append(i);
                Log.w("GoogleApiAvailability", sb.toString());
                return;
            }
            int isGooglePlayServicesAvailable = GoogleApiAvailability.this.isGooglePlayServicesAvailable(this.zza);
            if (GoogleApiAvailability.this.isUserResolvableError(isGooglePlayServicesAvailable)) {
                GoogleApiAvailability.this.showErrorNotification(this.zza, isGooglePlayServicesAvailable);
            }
        }
    }
}
