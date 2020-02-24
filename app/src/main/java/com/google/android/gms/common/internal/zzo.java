package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: GmsClientEventManager */
public final class zzo implements Handler.Callback {
    private final zzp zza;
    private final ArrayList<GoogleApiClient.ConnectionCallbacks> zzb = new ArrayList<>();
    private final ArrayList<GoogleApiClient.ConnectionCallbacks> zzc = new ArrayList<>();
    private final ArrayList<GoogleApiClient.OnConnectionFailedListener> zzd = new ArrayList<>();
    private volatile boolean zze = false;
    private final AtomicInteger zzf = new AtomicInteger(0);
    private boolean zzg = false;
    private final Handler zzh;
    private final Object zzi = new Object();

    public zzo(Looper looper, zzp zzp) {
        this.zza = zzp;
        this.zzh = new Handler(looper, this);
    }

    public final void zza() {
        this.zze = false;
        this.zzf.incrementAndGet();
    }

    public final void zzb() {
        this.zze = true;
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.internal.zzau.zza(boolean, java.lang.Object):void
     arg types: [boolean, java.lang.String]
     candidates:
      com.google.android.gms.common.internal.zzau.zza(int, java.lang.Object):int
      com.google.android.gms.common.internal.zzau.zza(long, java.lang.Object):long
      com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T
      com.google.android.gms.common.internal.zzau.zza(java.lang.String, java.lang.Object):java.lang.String
      com.google.android.gms.common.internal.zzau.zza(boolean, java.lang.Object):void */
    public final void zza(Bundle bundle) {
        boolean z = true;
        zzau.zza(Looper.myLooper() == this.zzh.getLooper(), (Object) "onConnectionSuccess must only be called on the Handler thread");
        synchronized (this.zzi) {
            zzau.zza(!this.zzg);
            this.zzh.removeMessages(1);
            this.zzg = true;
            if (this.zzc.size() != 0) {
                z = false;
            }
            zzau.zza(z);
            ArrayList arrayList = new ArrayList(this.zzb);
            int i = this.zzf.get();
            ArrayList arrayList2 = arrayList;
            int size = arrayList2.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList2.get(i2);
                i2++;
                GoogleApiClient.ConnectionCallbacks connectionCallbacks = (GoogleApiClient.ConnectionCallbacks) obj;
                if (!this.zze || !this.zza.isConnected() || this.zzf.get() != i) {
                    break;
                } else if (!this.zzc.contains(connectionCallbacks)) {
                    connectionCallbacks.onConnected(bundle);
                }
            }
            this.zzc.clear();
            this.zzg = false;
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.internal.zzau.zza(boolean, java.lang.Object):void
     arg types: [boolean, java.lang.String]
     candidates:
      com.google.android.gms.common.internal.zzau.zza(int, java.lang.Object):int
      com.google.android.gms.common.internal.zzau.zza(long, java.lang.Object):long
      com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T
      com.google.android.gms.common.internal.zzau.zza(java.lang.String, java.lang.Object):java.lang.String
      com.google.android.gms.common.internal.zzau.zza(boolean, java.lang.Object):void */
    public final void zza(int i) {
        zzau.zza(Looper.myLooper() == this.zzh.getLooper(), (Object) "onUnintentionalDisconnection must only be called on the Handler thread");
        this.zzh.removeMessages(1);
        synchronized (this.zzi) {
            this.zzg = true;
            ArrayList arrayList = new ArrayList(this.zzb);
            int i2 = this.zzf.get();
            ArrayList arrayList2 = arrayList;
            int size = arrayList2.size();
            int i3 = 0;
            while (i3 < size) {
                Object obj = arrayList2.get(i3);
                i3++;
                GoogleApiClient.ConnectionCallbacks connectionCallbacks = (GoogleApiClient.ConnectionCallbacks) obj;
                if (!this.zze || this.zzf.get() != i2) {
                    break;
                } else if (this.zzb.contains(connectionCallbacks)) {
                    connectionCallbacks.onConnectionSuspended(i);
                }
            }
            this.zzc.clear();
            this.zzg = false;
        }
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: com.google.android.gms.common.internal.zzau.zza(boolean, java.lang.Object):void
     arg types: [boolean, java.lang.String]
     candidates:
      com.google.android.gms.common.internal.zzau.zza(int, java.lang.Object):int
      com.google.android.gms.common.internal.zzau.zza(long, java.lang.Object):long
      com.google.android.gms.common.internal.zzau.zza(java.lang.Object, java.lang.Object):T
      com.google.android.gms.common.internal.zzau.zza(java.lang.String, java.lang.Object):java.lang.String
      com.google.android.gms.common.internal.zzau.zza(boolean, java.lang.Object):void */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0055, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zza(com.google.android.gms.common.ConnectionResult r8) {
        /*
            r7 = this;
            android.os.Looper r0 = android.os.Looper.myLooper()
            android.os.Handler r1 = r7.zzh
            android.os.Looper r1 = r1.getLooper()
            r2 = 0
            r3 = 1
            if (r0 != r1) goto L_0x0010
            r0 = 1
            goto L_0x0011
        L_0x0010:
            r0 = 0
        L_0x0011:
            java.lang.String r1 = "onConnectionFailure must only be called on the Handler thread"
            com.google.android.gms.common.internal.zzau.zza(r0, r1)
            android.os.Handler r0 = r7.zzh
            r0.removeMessages(r3)
            java.lang.Object r0 = r7.zzi
            monitor-enter(r0)
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ all -> 0x0058 }
            java.util.ArrayList<com.google.android.gms.common.api.GoogleApiClient$OnConnectionFailedListener> r3 = r7.zzd     // Catch:{ all -> 0x0058 }
            r1.<init>(r3)     // Catch:{ all -> 0x0058 }
            java.util.concurrent.atomic.AtomicInteger r3 = r7.zzf     // Catch:{ all -> 0x0058 }
            int r3 = r3.get()     // Catch:{ all -> 0x0058 }
            java.util.ArrayList r1 = (java.util.ArrayList) r1     // Catch:{ all -> 0x0058 }
            int r4 = r1.size()     // Catch:{ all -> 0x0058 }
        L_0x0031:
            if (r2 >= r4) goto L_0x0056
            java.lang.Object r5 = r1.get(r2)     // Catch:{ all -> 0x0058 }
            int r2 = r2 + 1
            com.google.android.gms.common.api.GoogleApiClient$OnConnectionFailedListener r5 = (com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener) r5     // Catch:{ all -> 0x0058 }
            boolean r6 = r7.zze     // Catch:{ all -> 0x0058 }
            if (r6 == 0) goto L_0x0054
            java.util.concurrent.atomic.AtomicInteger r6 = r7.zzf     // Catch:{ all -> 0x0058 }
            int r6 = r6.get()     // Catch:{ all -> 0x0058 }
            if (r6 == r3) goto L_0x0048
            goto L_0x0054
        L_0x0048:
            java.util.ArrayList<com.google.android.gms.common.api.GoogleApiClient$OnConnectionFailedListener> r6 = r7.zzd     // Catch:{ all -> 0x0058 }
            boolean r6 = r6.contains(r5)     // Catch:{ all -> 0x0058 }
            if (r6 == 0) goto L_0x0053
            r5.onConnectionFailed(r8)     // Catch:{ all -> 0x0058 }
        L_0x0053:
            goto L_0x0031
        L_0x0054:
            monitor-exit(r0)     // Catch:{ all -> 0x0058 }
            return
        L_0x0056:
            monitor-exit(r0)     // Catch:{ all -> 0x0058 }
            return
        L_0x0058:
            r8 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0058 }
            throw r8
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.zzo.zza(com.google.android.gms.common.ConnectionResult):void");
    }

    public final void zza(GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        zzau.zza(connectionCallbacks);
        synchronized (this.zzi) {
            if (this.zzb.contains(connectionCallbacks)) {
                String valueOf = String.valueOf(connectionCallbacks);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 62);
                sb.append("registerConnectionCallbacks(): listener ");
                sb.append(valueOf);
                sb.append(" is already registered");
                Log.w("GmsClientEvents", sb.toString());
            } else {
                this.zzb.add(connectionCallbacks);
            }
        }
        if (this.zza.isConnected()) {
            Handler handler = this.zzh;
            handler.sendMessage(handler.obtainMessage(1, connectionCallbacks));
        }
    }

    public final boolean zzb(GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        boolean contains;
        zzau.zza(connectionCallbacks);
        synchronized (this.zzi) {
            contains = this.zzb.contains(connectionCallbacks);
        }
        return contains;
    }

    public final void zzc(GoogleApiClient.ConnectionCallbacks connectionCallbacks) {
        zzau.zza(connectionCallbacks);
        synchronized (this.zzi) {
            if (!this.zzb.remove(connectionCallbacks)) {
                String valueOf = String.valueOf(connectionCallbacks);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 52);
                sb.append("unregisterConnectionCallbacks(): listener ");
                sb.append(valueOf);
                sb.append(" not found");
                Log.w("GmsClientEvents", sb.toString());
            } else if (this.zzg) {
                this.zzc.add(connectionCallbacks);
            }
        }
    }

    public final void zza(GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        zzau.zza(onConnectionFailedListener);
        synchronized (this.zzi) {
            if (this.zzd.contains(onConnectionFailedListener)) {
                String valueOf = String.valueOf(onConnectionFailedListener);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 67);
                sb.append("registerConnectionFailedListener(): listener ");
                sb.append(valueOf);
                sb.append(" is already registered");
                Log.w("GmsClientEvents", sb.toString());
            } else {
                this.zzd.add(onConnectionFailedListener);
            }
        }
    }

    public final boolean zzb(GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        boolean contains;
        zzau.zza(onConnectionFailedListener);
        synchronized (this.zzi) {
            contains = this.zzd.contains(onConnectionFailedListener);
        }
        return contains;
    }

    public final void zzc(GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        zzau.zza(onConnectionFailedListener);
        synchronized (this.zzi) {
            if (!this.zzd.remove(onConnectionFailedListener)) {
                String valueOf = String.valueOf(onConnectionFailedListener);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 57);
                sb.append("unregisterConnectionFailedListener(): listener ");
                sb.append(valueOf);
                sb.append(" not found");
                Log.w("GmsClientEvents", sb.toString());
            }
        }
    }

    public final boolean handleMessage(Message message) {
        if (message.what == 1) {
            GoogleApiClient.ConnectionCallbacks connectionCallbacks = (GoogleApiClient.ConnectionCallbacks) message.obj;
            synchronized (this.zzi) {
                if (this.zze && this.zza.isConnected() && this.zzb.contains(connectionCallbacks)) {
                    connectionCallbacks.onConnected(this.zza.getConnectionHint());
                }
            }
            return true;
        }
        int i = message.what;
        StringBuilder sb = new StringBuilder(45);
        sb.append("Don't know how to handle message: ");
        sb.append(i);
        Log.wtf("GmsClientEvents", sb.toString(), new Exception());
        return false;
    }
}
