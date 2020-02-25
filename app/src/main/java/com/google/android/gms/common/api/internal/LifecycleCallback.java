package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.MainThread;

import java.io.FileDescriptor;
import java.io.PrintWriter;

public class LifecycleCallback {
    protected final zzcf zzd;

    protected LifecycleCallback(zzcf zzcf) {
        this.zzd = zzcf;
    }

    protected static zzcf zzb(zzce zzce) {
        if (zzce.zza()) {
            return zzdi.zza(zzce.zzd());
        }
        if (zzce.zzb()) {
            return zzcg.zza(zzce.zzc());
        }
        throw new IllegalArgumentException("Can't get fragment for unexpected activity.");
    }

    @Keep
    private static zzcf getChimeraLifecycleFragmentImpl(zzce zzce) {
        throw new IllegalStateException("Method not available in SDK.");
    }

    public static zzcf zzb(Activity activity) {
        return zzb(new zzce(activity));
    }

    public final Activity zzg() {
        return this.zzd.zza();
    }

    @MainThread
    public void zza(Bundle bundle) {
    }

    @MainThread
    public void zzb() {
    }

    @MainThread
    public void zze() {
    }

    @MainThread
    public void zzb(Bundle bundle) {
    }

    @MainThread
    public void zza(int i, int i2, Intent intent) {
    }

    @MainThread
    public void zza() {
    }

    @MainThread
    public void zzh() {
    }

    @MainThread
    public void zza(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }
}
