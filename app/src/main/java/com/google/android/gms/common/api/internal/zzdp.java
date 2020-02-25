package com.google.android.gms.common.api.internal;

import android.support.annotation.WorkerThread;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;

/* compiled from: TransformedResultImpl */
final class zzdp implements Runnable {
    private final /* synthetic */ Result zza;
    private final /* synthetic */ zzdo zzb;

    zzdp(zzdo zzdo, Result result) {
        this.zzb = zzdo;
        this.zza = result;
    }

    @WorkerThread
    public final void run() {
        try {
            BasePendingResult.zzc.set(true);
            this.zzb.zzh.sendMessage(this.zzb.zzh.obtainMessage(0, this.zzb.zza.onSuccess(this.zza)));
            BasePendingResult.zzc.set(false);
            zzdo.zza(this.zza);
            GoogleApiClient googleApiClient = (GoogleApiClient) this.zzb.zzg.get();
            if (googleApiClient != null) {
                googleApiClient.zzb(this.zzb);
            }
        } catch (RuntimeException e) {
            this.zzb.zzh.sendMessage(this.zzb.zzh.obtainMessage(1, e));
            BasePendingResult.zzc.set(false);
            zzdo.zza(this.zza);
            GoogleApiClient googleApiClient2 = (GoogleApiClient) this.zzb.zzg.get();
            if (googleApiClient2 != null) {
                googleApiClient2.zzb(this.zzb);
            }
        } catch (Throwable th) {
            BasePendingResult.zzc.set(false);
            zzdo.zza(this.zza);
            GoogleApiClient googleApiClient3 = (GoogleApiClient) this.zzb.zzg.get();
            if (googleApiClient3 != null) {
                googleApiClient3.zzb(this.zzb);
            }
            throw th;
        }
    }
}
