package com.google.android.gms.common.internal;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.p001v4.app.Fragment;
import android.util.Log;
import com.google.android.gms.common.api.internal.zzcf;

/* compiled from: DialogRedirect */
public abstract class zzg implements DialogInterface.OnClickListener {
    /* access modifiers changed from: protected */
    public abstract void zza();

    public static zzg zza(Activity activity, Intent intent, int i) {
        return new zzh(intent, activity, i);
    }

    public static zzg zza(@NonNull Fragment fragment, Intent intent, int i) {
        return new zzi(intent, fragment, i);
    }

    public static zzg zza(@NonNull zzcf zzcf, Intent intent, int i) {
        return new zzj(intent, zzcf, 2);
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        try {
            zza();
        } catch (ActivityNotFoundException e) {
            Log.e("DialogRedirect", "Failed to start resolution intent", e);
        } finally {
            dialogInterface.dismiss();
        }
    }
}
