package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzau;
import com.google.android.gms.common.internal.zzbd;
import com.google.android.gms.signin.zzd;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/* compiled from: GoogleApiManager */
public final class zzbp<O extends Api.ApiOptions> implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, zzv {
    final /* synthetic */ zzbn zza;
    private final Queue<zzb> zzb = new LinkedList();
    /* access modifiers changed from: private */
    public final Api.Client zzc;
    private final Api.zzb zzd;
    private final zzi<O> zze;
    private final zzaf zzf;
    private final Set<zzk> zzg = new HashSet();
    private final Map<zzcl<?>, zzcw> zzh = new HashMap();
    private final int zzi;
    private final zzdb zzj;
    private boolean zzk;
    private int zzl = -1;
    private ConnectionResult zzm = null;

    @WorkerThread
    public zzbp(zzbn zzbn, GoogleApi<O> googleApi) {
        this.zza = zzbn;
        this.zzc = googleApi.zza(zzbn.zzq.getLooper(), this);
        Api.Client client = this.zzc;
        if (client instanceof zzbd) {
            this.zzd = zzbd.zzc();
        } else {
            this.zzd = client;
        }
        this.zze = googleApi.zzd();
        this.zzf = new zzaf();
        this.zzi = googleApi.zze();
        if (this.zzc.requiresSignIn()) {
            this.zzj = googleApi.zza(zzbn.zzh, zzbn.zzq);
        } else {
            this.zzj = null;
        }
    }

    public final void onConnected(@Nullable Bundle bundle) {
        if (Looper.myLooper() == this.zza.zzq.getLooper()) {
            zzn();
        } else {
            this.zza.zzq.post(new zzbq(this));
        }
    }

    /* access modifiers changed from: private */
    @WorkerThread
    public final void zzn() {
        zzd();
        zzb(ConnectionResult.zza);
        zzp();
        for (zzcw zzcw : this.zzh.values()) {
            try {
                zzcw.zza.zza(this.zzd, new TaskCompletionSource());
            } catch (DeadObjectException e) {
                onConnectionSuspended(1);
                this.zzc.disconnect();
            } catch (RemoteException e2) {
            }
        }
        while (this.zzc.isConnected() && !this.zzb.isEmpty()) {
            zzb(this.zzb.remove());
        }
        zzq();
    }

    public final void onConnectionSuspended(int i) {
        if (Looper.myLooper() == this.zza.zzq.getLooper()) {
            zzo();
        } else {
            this.zza.zzq.post(new zzbr(this));
        }
    }

    /* access modifiers changed from: private */
    @WorkerThread
    public final void zzo() {
        zzd();
        this.zzk = true;
        this.zzf.zzc();
        this.zza.zzq.sendMessageDelayed(Message.obtain(this.zza.zzq, 9, this.zze), this.zza.zzc);
        this.zza.zzq.sendMessageDelayed(Message.obtain(this.zza.zzq, 11, this.zze), this.zza.zzd);
        this.zza.zzj.zza();
    }

    @WorkerThread
    public final void zza(@NonNull ConnectionResult connectionResult) {
        zzau.zza(this.zza.zzq);
        this.zzc.disconnect();
        onConnectionFailed(connectionResult);
    }

    public final void zza(ConnectionResult connectionResult, Api<?> api, boolean z) {
        if (Looper.myLooper() == this.zza.zzq.getLooper()) {
            onConnectionFailed(connectionResult);
        } else {
            this.zza.zzq.post(new zzbs(this, connectionResult));
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x006a, code lost:
        if (r4.zza.zza(r5, r4.zzi) != false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0072, code lost:
        if (r5.getErrorCode() != 18) goto L_0x0077;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0074, code lost:
        r4.zzk = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0079, code lost:
        if (r4.zzk == false) goto L_0x0099;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x007b, code lost:
        r4.zza.zzq.sendMessageDelayed(android.os.Message.obtain(r4.zza.zzq, 9, r4.zze), r4.zza.zzc);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0098, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0099, code lost:
        r1 = r4.zze.zza();
        r3 = new java.lang.StringBuilder(java.lang.String.valueOf(r1).length() + 38);
        r3.append("API: ");
        r3.append(r1);
        r3.append(" is not available on this device.");
        zza(new com.google.android.gms.common.api.Status(17, r3.toString()));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:?, code lost:
        return;
     */
    @android.support.annotation.WorkerThread
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onConnectionFailed(@android.support.annotation.NonNull com.google.android.gms.common.ConnectionResult r5) {
        /*
            r4 = this;
            com.google.android.gms.common.api.internal.zzbn r0 = r4.zza
            android.os.Handler r0 = r0.zzq
            com.google.android.gms.common.internal.zzau.zza(r0)
            com.google.android.gms.common.api.internal.zzdb r0 = r4.zzj
            if (r0 == 0) goto L_0x0010
            r0.zzb()
        L_0x0010:
            r4.zzd()
            com.google.android.gms.common.api.internal.zzbn r0 = r4.zza
            com.google.android.gms.common.internal.zzv r0 = r0.zzj
            r0.zza()
            r4.zzb(r5)
            int r0 = r5.getErrorCode()
            r1 = 4
            if (r0 != r1) goto L_0x002e
            com.google.android.gms.common.api.Status r5 = com.google.android.gms.common.api.internal.zzbn.zzb
            r4.zza(r5)
            return
        L_0x002e:
            java.util.Queue<com.google.android.gms.common.api.internal.zzb> r0 = r4.zzb
            boolean r0 = r0.isEmpty()
            if (r0 == 0) goto L_0x0039
            r4.zzm = r5
            return
        L_0x0039:
            java.lang.Object r0 = com.google.android.gms.common.api.internal.zzbn.zzf
            monitor-enter(r0)
            com.google.android.gms.common.api.internal.zzbn r1 = r4.zza     // Catch:{ all -> 0x00ca }
            com.google.android.gms.common.api.internal.zzai r1 = r1.zzn     // Catch:{ all -> 0x00ca }
            if (r1 == 0) goto L_0x0061
            com.google.android.gms.common.api.internal.zzbn r1 = r4.zza     // Catch:{ all -> 0x00ca }
            java.util.Set r1 = r1.zzo     // Catch:{ all -> 0x00ca }
            com.google.android.gms.common.api.internal.zzi<O> r2 = r4.zze     // Catch:{ all -> 0x00ca }
            boolean r1 = r1.contains(r2)     // Catch:{ all -> 0x00ca }
            if (r1 == 0) goto L_0x0061
            com.google.android.gms.common.api.internal.zzbn r1 = r4.zza     // Catch:{ all -> 0x00ca }
            com.google.android.gms.common.api.internal.zzai r1 = r1.zzn     // Catch:{ all -> 0x00ca }
            int r2 = r4.zzi     // Catch:{ all -> 0x00ca }
            r1.zzb(r5, r2)     // Catch:{ all -> 0x00ca }
            monitor-exit(r0)     // Catch:{ all -> 0x00ca }
            return
        L_0x0061:
            monitor-exit(r0)     // Catch:{ all -> 0x00ca }
            com.google.android.gms.common.api.internal.zzbn r0 = r4.zza
            int r1 = r4.zzi
            boolean r0 = r0.zza(r5, r1)
            if (r0 != 0) goto L_0x00c9
            int r5 = r5.getErrorCode()
            r0 = 18
            if (r5 != r0) goto L_0x0077
            r5 = 1
            r4.zzk = r5
        L_0x0077:
            boolean r5 = r4.zzk
            if (r5 == 0) goto L_0x0099
            com.google.android.gms.common.api.internal.zzbn r5 = r4.zza
            android.os.Handler r5 = r5.zzq
            com.google.android.gms.common.api.internal.zzbn r0 = r4.zza
            android.os.Handler r0 = r0.zzq
            r1 = 9
            com.google.android.gms.common.api.internal.zzi<O> r2 = r4.zze
            android.os.Message r0 = android.os.Message.obtain(r0, r1, r2)
            com.google.android.gms.common.api.internal.zzbn r1 = r4.zza
            long r1 = r1.zzc
            r5.sendMessageDelayed(r0, r1)
            return
        L_0x0099:
            com.google.android.gms.common.api.Status r5 = new com.google.android.gms.common.api.Status
            r0 = 17
            com.google.android.gms.common.api.internal.zzi<O> r1 = r4.zze
            java.lang.String r1 = r1.zza()
            java.lang.String r2 = java.lang.String.valueOf(r1)
            int r2 = r2.length()
            int r2 = r2 + 38
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>(r2)
            java.lang.String r2 = "API: "
            r3.append(r2)
            r3.append(r1)
            java.lang.String r1 = " is not available on this device."
            r3.append(r1)
            java.lang.String r1 = r3.toString()
            r5.<init>(r0, r1)
            r4.zza(r5)
        L_0x00c9:
            return
        L_0x00ca:
            r5 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00ca }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.zzbp.onConnectionFailed(com.google.android.gms.common.ConnectionResult):void");
    }

    @WorkerThread
    public final void zza(zzb zzb2) {
        zzau.zza(this.zza.zzq);
        if (this.zzc.isConnected()) {
            zzb(zzb2);
            zzq();
            return;
        }
        this.zzb.add(zzb2);
        ConnectionResult connectionResult = this.zzm;
        if (connectionResult == null || !connectionResult.hasResolution()) {
            zzi();
        } else {
            onConnectionFailed(this.zzm);
        }
    }

    @WorkerThread
    public final void zza() {
        zzau.zza(this.zza.zzq);
        zza(zzbn.zza);
        this.zzf.zzb();
        for (zzcl zzg2 : (zzcl[]) this.zzh.keySet().toArray(new zzcl[this.zzh.size()])) {
            zza(new zzg(zzg2, new TaskCompletionSource()));
        }
        zzb(new ConnectionResult(4));
        if (this.zzc.isConnected()) {
            this.zzc.onUserSignOut(new zzbt(this));
        }
    }

    public final Api.Client zzb() {
        return this.zzc;
    }

    public final Map<zzcl<?>, zzcw> zzc() {
        return this.zzh;
    }

    @WorkerThread
    public final void zzd() {
        zzau.zza(this.zza.zzq);
        this.zzm = null;
    }

    @WorkerThread
    public final ConnectionResult zze() {
        zzau.zza(this.zza.zzq);
        return this.zzm;
    }

    @WorkerThread
    private final void zzb(zzb zzb2) {
        zzb2.zza(this.zzf, zzk());
        try {
            zzb2.zza(this);
        } catch (DeadObjectException e) {
            onConnectionSuspended(1);
            this.zzc.disconnect();
        }
    }

    @WorkerThread
    public final void zza(Status status) {
        zzau.zza(this.zza.zzq);
        for (zzb zza2 : this.zzb) {
            zza2.zza(status);
        }
        this.zzb.clear();
    }

    @WorkerThread
    public final void zzf() {
        zzau.zza(this.zza.zzq);
        if (this.zzk) {
            zzi();
        }
    }

    @WorkerThread
    private final void zzp() {
        if (this.zzk) {
            this.zza.zzq.removeMessages(11, this.zze);
            this.zza.zzq.removeMessages(9, this.zze);
            this.zzk = false;
        }
    }

    @WorkerThread
    public final void zzg() {
        Status status;
        zzau.zza(this.zza.zzq);
        if (this.zzk) {
            zzp();
            if (this.zza.zzi.isGooglePlayServicesAvailable(this.zza.zzh) == 18) {
                status = new Status(8, "Connection timed out while waiting for Google Play services update to complete.");
            } else {
                status = new Status(8, "API failed to connect while resuming due to an unknown error.");
            }
            zza(status);
            this.zzc.disconnect();
        }
    }

    private final void zzq() {
        this.zza.zzq.removeMessages(12, this.zze);
        this.zza.zzq.sendMessageDelayed(this.zza.zzq.obtainMessage(12, this.zze), this.zza.zze);
    }

    @WorkerThread
    public final void zzh() {
        zzau.zza(this.zza.zzq);
        if (this.zzc.isConnected() && this.zzh.size() == 0) {
            if (this.zzf.zza()) {
                zzq();
            } else {
                this.zzc.disconnect();
            }
        }
    }

    @WorkerThread
    public final void zzi() {
        zzau.zza(this.zza.zzq);
        if (!this.zzc.isConnected() && !this.zzc.isConnecting()) {
            int zza2 = this.zza.zzj.zza(this.zza.zzh, this.zzc);
            if (zza2 != 0) {
                onConnectionFailed(new ConnectionResult(zza2, null));
                return;
            }
            zzbv zzbv = new zzbv(this.zza, this.zzc, this.zze);
            if (this.zzc.requiresSignIn()) {
                this.zzj.zza(zzbv);
            }
            this.zzc.connect(zzbv);
        }
    }

    @WorkerThread
    public final void zza(zzk zzk2) {
        zzau.zza(this.zza.zzq);
        this.zzg.add(zzk2);
    }

    @WorkerThread
    private final void zzb(ConnectionResult connectionResult) {
        for (zzk next : this.zzg) {
            String str = null;
            if (connectionResult == ConnectionResult.zza) {
                str = this.zzc.zzab();
            }
            next.zza(this.zze, connectionResult, str);
        }
        this.zzg.clear();
    }

    /* access modifiers changed from: package-private */
    public final boolean zzj() {
        return this.zzc.isConnected();
    }

    public final boolean zzk() {
        return this.zzc.requiresSignIn();
    }

    public final int zzl() {
        return this.zzi;
    }

    /* access modifiers changed from: package-private */
    public final zzd zzm() {
        zzdb zzdb = this.zzj;
        if (zzdb == null) {
            return null;
        }
        return zzdb.zza();
    }
}
