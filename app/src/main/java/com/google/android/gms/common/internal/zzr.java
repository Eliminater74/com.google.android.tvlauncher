package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.google.android.gms.common.stats.zza;

import java.util.HashSet;
import java.util.Set;

/* compiled from: GmsClientSupervisorImpl */
final class zzr implements ServiceConnection {
    /* access modifiers changed from: private */
    public final GmsClientSupervisor.ConnectionStatusConfig zze;
    private final Set<ServiceConnection> zza = new HashSet();
    private final /* synthetic */ zzq zzg;
    private int zzb = 2;
    private boolean zzc;
    private IBinder zzd;
    private ComponentName zzf;

    public zzr(zzq zzq, GmsClientSupervisor.ConnectionStatusConfig connectionStatusConfig) {
        this.zzg = zzq;
        this.zze = connectionStatusConfig;
    }

    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        synchronized (this.zzg.zza) {
            this.zzg.zzc.removeMessages(1, this.zze);
            this.zzd = iBinder;
            this.zzf = componentName;
            for (ServiceConnection onServiceConnected : this.zza) {
                onServiceConnected.onServiceConnected(componentName, iBinder);
            }
            this.zzb = 1;
        }
    }

    public final void onServiceDisconnected(ComponentName componentName) {
        synchronized (this.zzg.zza) {
            this.zzg.zzc.removeMessages(1, this.zze);
            this.zzd = null;
            this.zzf = componentName;
            for (ServiceConnection onServiceDisconnected : this.zza) {
                onServiceDisconnected.onServiceDisconnected(componentName);
            }
            this.zzb = 2;
        }
    }

    public final void zza(String str) {
        this.zzb = 3;
        this.zzc = this.zzg.zzd.zza(this.zzg.zzb, str, this.zze.getStartServiceIntent(this.zzg.zzb), this, this.zze.getBindFlags());
        if (this.zzc) {
            this.zzg.zzc.sendMessageDelayed(this.zzg.zzc.obtainMessage(1, this.zze), this.zzg.zzf);
            return;
        }
        this.zzb = 2;
        try {
            zza unused = this.zzg.zzd;
            this.zzg.zzb.unbindService(this);
        } catch (IllegalArgumentException e) {
        }
    }

    public final void zzb(String str) {
        this.zzg.zzc.removeMessages(1, this.zze);
        zza unused = this.zzg.zzd;
        this.zzg.zzb.unbindService(this);
        this.zzc = false;
        this.zzb = 2;
    }

    public final void zza(ServiceConnection serviceConnection, String str) {
        zza unused = this.zzg.zzd;
        Context unused2 = this.zzg.zzb;
        this.zze.getStartServiceIntent(this.zzg.zzb);
        this.zza.add(serviceConnection);
    }

    public final void zzb(ServiceConnection serviceConnection, String str) {
        zza unused = this.zzg.zzd;
        Context unused2 = this.zzg.zzb;
        this.zza.remove(serviceConnection);
    }

    public final boolean zza() {
        return this.zzc;
    }

    public final int zzb() {
        return this.zzb;
    }

    public final boolean zza(ServiceConnection serviceConnection) {
        return this.zza.contains(serviceConnection);
    }

    public final boolean zzc() {
        return this.zza.isEmpty();
    }

    public final IBinder zzd() {
        return this.zzd;
    }

    public final ComponentName zze() {
        return this.zzf;
    }
}
