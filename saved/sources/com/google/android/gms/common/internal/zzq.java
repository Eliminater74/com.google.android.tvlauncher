package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.gms.common.internal.GmsClientSupervisor;
import com.google.android.gms.common.stats.zza;
import java.util.HashMap;

/* compiled from: GmsClientSupervisorImpl */
final class zzq extends GmsClientSupervisor implements Handler.Callback {
    /* access modifiers changed from: private */
    public final HashMap<GmsClientSupervisor.ConnectionStatusConfig, zzr> zza = new HashMap<>();
    /* access modifiers changed from: private */
    public final Context zzb;
    /* access modifiers changed from: private */
    public final Handler zzc;
    /* access modifiers changed from: private */
    public final zza zzd;
    private final long zze;
    /* access modifiers changed from: private */
    public final long zzf;

    zzq(Context context) {
        this.zzb = context.getApplicationContext();
        this.zzc = new Handler(context.getMainLooper(), this);
        this.zzd = zza.zza();
        this.zze = DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS;
        this.zzf = 300000;
    }

    /* access modifiers changed from: protected */
    public final boolean bindService(GmsClientSupervisor.ConnectionStatusConfig connectionStatusConfig, ServiceConnection serviceConnection, String str) {
        boolean zza2;
        zzau.zza(serviceConnection, "ServiceConnection must not be null");
        synchronized (this.zza) {
            zzr zzr = this.zza.get(connectionStatusConfig);
            if (zzr == null) {
                zzr = new zzr(this, connectionStatusConfig);
                zzr.zza(serviceConnection, str);
                zzr.zza(str);
                this.zza.put(connectionStatusConfig, zzr);
            } else {
                this.zzc.removeMessages(0, connectionStatusConfig);
                if (!zzr.zza(serviceConnection)) {
                    zzr.zza(serviceConnection, str);
                    int zzb2 = zzr.zzb();
                    if (zzb2 == 1) {
                        serviceConnection.onServiceConnected(zzr.zze(), zzr.zzd());
                    } else if (zzb2 == 2) {
                        zzr.zza(str);
                    }
                } else {
                    String valueOf = String.valueOf(connectionStatusConfig);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 81);
                    sb.append("Trying to bind a GmsServiceConnection that was already connected before.  config=");
                    sb.append(valueOf);
                    throw new IllegalStateException(sb.toString());
                }
            }
            zza2 = zzr.zza();
        }
        return zza2;
    }

    /* access modifiers changed from: protected */
    public final void unbindService(GmsClientSupervisor.ConnectionStatusConfig connectionStatusConfig, ServiceConnection serviceConnection, String str) {
        zzau.zza(serviceConnection, "ServiceConnection must not be null");
        synchronized (this.zza) {
            zzr zzr = this.zza.get(connectionStatusConfig);
            if (zzr == null) {
                String valueOf = String.valueOf(connectionStatusConfig);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 50);
                sb.append("Nonexistent connection status for service config: ");
                sb.append(valueOf);
                throw new IllegalStateException(sb.toString());
            } else if (zzr.zza(serviceConnection)) {
                zzr.zzb(serviceConnection, str);
                if (zzr.zzc()) {
                    this.zzc.sendMessageDelayed(this.zzc.obtainMessage(0, connectionStatusConfig), this.zze);
                }
            } else {
                String valueOf2 = String.valueOf(connectionStatusConfig);
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 76);
                sb2.append("Trying to unbind a GmsServiceConnection  that was not bound before.  config=");
                sb2.append(valueOf2);
                throw new IllegalStateException(sb2.toString());
            }
        }
    }

    public final boolean handleMessage(Message message) {
        int i = message.what;
        if (i == 0) {
            synchronized (this.zza) {
                GmsClientSupervisor.ConnectionStatusConfig connectionStatusConfig = (GmsClientSupervisor.ConnectionStatusConfig) message.obj;
                zzr zzr = this.zza.get(connectionStatusConfig);
                if (zzr != null && zzr.zzc()) {
                    if (zzr.zza()) {
                        zzr.zzb("GmsClientSupervisor");
                    }
                    this.zza.remove(connectionStatusConfig);
                }
            }
            return true;
        } else if (i != 1) {
            return false;
        } else {
            synchronized (this.zza) {
                GmsClientSupervisor.ConnectionStatusConfig connectionStatusConfig2 = (GmsClientSupervisor.ConnectionStatusConfig) message.obj;
                zzr zzr2 = this.zza.get(connectionStatusConfig2);
                if (zzr2 != null && zzr2.zzb() == 3) {
                    String valueOf = String.valueOf(connectionStatusConfig2);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 47);
                    sb.append("Timeout waiting for ServiceConnection callback ");
                    sb.append(valueOf);
                    Log.wtf("GmsClientSupervisor", sb.toString(), new Exception());
                    ComponentName zze2 = zzr2.zze();
                    if (zze2 == null) {
                        zze2 = connectionStatusConfig2.getComponentName();
                    }
                    if (zze2 == null) {
                        zze2 = new ComponentName(connectionStatusConfig2.getPackage(), "unknown");
                    }
                    zzr2.onServiceDisconnected(zze2);
                }
            }
            return true;
        }
    }

    public final void resetForTesting() {
        synchronized (this.zza) {
            for (zzr next : this.zza.values()) {
                this.zzc.removeMessages(0, next.zze);
                if (next.zza()) {
                    next.zzb("GmsClientSupervisor");
                }
            }
            this.zza.clear();
        }
    }
}
