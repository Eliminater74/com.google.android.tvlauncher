package com.google.android.gms.common.api.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailabilityLight;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.signin.SignInOptions;
import com.google.android.gms.signin.zzd;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/* compiled from: GoogleApiClientStateHolder */
public final class zzbj implements zzcc, zzv {
    final Map<Api.zzc<?>, Api.Client> zza;
    final Map<Api.zzc<?>, ConnectionResult> zzb = new HashMap();
    int zzc;
    final zzbb zzd;
    final zzcd zze;
    /* access modifiers changed from: private */
    public final Lock zzf;
    private final Condition zzg;
    private final Context zzh;
    private final GoogleApiAvailabilityLight zzi;
    private final zzbl zzj;
    private final ClientSettings zzk;
    private final Map<Api<?>, Boolean> zzl;
    private final Api.zza<? extends zzd, SignInOptions> zzm;
    /* access modifiers changed from: private */
    public volatile zzbi zzn;
    private ConnectionResult zzo = null;

    public zzbj(Context context, zzbb zzbb, Lock lock, Looper looper, GoogleApiAvailabilityLight googleApiAvailabilityLight, Map<Api.zzc<?>, Api.Client> map, ClientSettings clientSettings, Map<Api<?>, Boolean> map2, Api.zza<? extends zzd, SignInOptions> zza2, ArrayList<zzu> arrayList, zzcd zzcd) {
        this.zzh = context;
        this.zzf = lock;
        this.zzi = googleApiAvailabilityLight;
        this.zza = map;
        this.zzk = clientSettings;
        this.zzl = map2;
        this.zzm = zza2;
        this.zzd = zzbb;
        this.zze = zzcd;
        ArrayList arrayList2 = arrayList;
        int size = arrayList2.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList2.get(i);
            i++;
            ((zzu) obj).zza(this);
        }
        this.zzj = new zzbl(this, looper);
        this.zzg = lock.newCondition();
        this.zzn = new zzba(this);
    }

    public final <A extends Api.zzb, R extends Result, T extends zzn<R, A>> T zza(@NonNull zzn zzn2) {
        zzn2.zzg();
        return this.zzn.zza(zzn2);
    }

    public final <A extends Api.zzb, T extends zzn<? extends Result, A>> T zzb(@NonNull zzn zzn2) {
        zzn2.zzg();
        return this.zzn.zzb(zzn2);
    }

    public final void zza() {
        this.zzn.zzc();
    }

    public final ConnectionResult zzb() {
        zza();
        while (zze()) {
            try {
                this.zzg.await();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return new ConnectionResult(15, null);
            }
        }
        if (zzd()) {
            return ConnectionResult.zza;
        }
        ConnectionResult connectionResult = this.zzo;
        if (connectionResult != null) {
            return connectionResult;
        }
        return new ConnectionResult(13, null);
    }

    public final ConnectionResult zza(long j, TimeUnit timeUnit) {
        zza();
        long nanos = timeUnit.toNanos(j);
        while (zze()) {
            if (nanos <= 0) {
                try {
                    zzc();
                    return new ConnectionResult(14, null);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return new ConnectionResult(15, null);
                }
            } else {
                nanos = this.zzg.awaitNanos(nanos);
            }
        }
        if (zzd()) {
            return ConnectionResult.zza;
        }
        ConnectionResult connectionResult = this.zzo;
        if (connectionResult != null) {
            return connectionResult;
        }
        return new ConnectionResult(13, null);
    }

    public final void zzc() {
        if (this.zzn.zzb()) {
            this.zzb.clear();
        }
    }

    @Nullable
    public final ConnectionResult zza(@NonNull Api<?> api) {
        Api.zzc<?> zzc2 = api.zzc();
        if (!this.zza.containsKey(zzc2)) {
            return null;
        }
        if (this.zza.get(zzc2).isConnected()) {
            return ConnectionResult.zza;
        }
        if (this.zzb.containsKey(zzc2)) {
            return this.zzb.get(zzc2);
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public final void zzh() {
        this.zzf.lock();
        try {
            this.zzn = new zzap(this, this.zzk, this.zzl, this.zzi, this.zzm, this.zzf, this.zzh);
            this.zzn.zza();
            this.zzg.signalAll();
        } finally {
            this.zzf.unlock();
        }
    }

    /* access modifiers changed from: package-private */
    public final void zzi() {
        this.zzf.lock();
        try {
            this.zzd.zzf();
            this.zzn = new zzam(this);
            this.zzn.zza();
            this.zzg.signalAll();
        } finally {
            this.zzf.unlock();
        }
    }

    /* access modifiers changed from: package-private */
    public final void zza(ConnectionResult connectionResult) {
        this.zzf.lock();
        try {
            this.zzo = connectionResult;
            this.zzn = new zzba(this);
            this.zzn.zza();
            this.zzg.signalAll();
        } finally {
            this.zzf.unlock();
        }
    }

    public final boolean zzd() {
        return this.zzn instanceof zzam;
    }

    public final boolean zze() {
        return this.zzn instanceof zzap;
    }

    public final boolean zza(zzda zzda) {
        return false;
    }

    public final void zzg() {
    }

    public final void zzf() {
        if (zzd()) {
            ((zzam) this.zzn).zzd();
        }
    }

    public final void zza(@NonNull ConnectionResult connectionResult, @NonNull Api<?> api, boolean z) {
        this.zzf.lock();
        try {
            this.zzn.zza(connectionResult, api, z);
        } finally {
            this.zzf.unlock();
        }
    }

    public final void onConnected(@Nullable Bundle bundle) {
        this.zzf.lock();
        try {
            this.zzn.zza(bundle);
        } finally {
            this.zzf.unlock();
        }
    }

    public final void onConnectionSuspended(int i) {
        this.zzf.lock();
        try {
            this.zzn.zza(i);
        } finally {
            this.zzf.unlock();
        }
    }

    /* access modifiers changed from: package-private */
    public final void zza(zzbk zzbk) {
        this.zzj.sendMessage(this.zzj.obtainMessage(1, zzbk));
    }

    /* access modifiers changed from: package-private */
    public final void zza(RuntimeException runtimeException) {
        this.zzj.sendMessage(this.zzj.obtainMessage(2, runtimeException));
    }

    public final void zza(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String concat = String.valueOf(str).concat("  ");
        printWriter.append((CharSequence) str).append((CharSequence) "mState=").println(this.zzn);
        for (Api next : this.zzl.keySet()) {
            printWriter.append((CharSequence) str).append((CharSequence) next.zzd()).println(":");
            this.zza.get(next.zzc()).dump(concat, fileDescriptor, printWriter, strArr);
        }
    }
}
