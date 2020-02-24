package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.TransformedResult;
import com.google.android.gms.common.internal.zzau;
import java.lang.ref.WeakReference;

/* compiled from: TransformedResultImpl */
public final class zzdo<R extends Result> extends TransformedResult<R> implements ResultCallback<R> {
    /* access modifiers changed from: private */
    public ResultTransform<? super R, ? extends Result> zza = null;
    /* access modifiers changed from: private */
    public zzdo<? extends Result> zzb = null;
    private volatile ResultCallbacks<? super R> zzc = null;
    private PendingResult<R> zzd = null;
    /* access modifiers changed from: private */
    public final Object zze = new Object();
    private Status zzf = null;
    /* access modifiers changed from: private */
    public final WeakReference<GoogleApiClient> zzg;
    /* access modifiers changed from: private */
    public final zzdq zzh;
    private boolean zzi = false;

    public zzdo(WeakReference<GoogleApiClient> weakReference) {
        zzau.zza(weakReference, "GoogleApiClient reference must not be null");
        this.zzg = weakReference;
        GoogleApiClient googleApiClient = this.zzg.get();
        this.zzh = new zzdq(this, googleApiClient != null ? googleApiClient.zzc() : Looper.getMainLooper());
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
    @NonNull
    public final <S extends Result> TransformedResult<S> then(@NonNull ResultTransform<? super R, ? extends S> resultTransform) {
        zzdo<? extends Result> zzdo;
        synchronized (this.zze) {
            boolean z = true;
            zzau.zza(this.zza == null, (Object) "Cannot call then() twice.");
            if (this.zzc != null) {
                z = false;
            }
            zzau.zza(z, (Object) "Cannot call then() and andFinally() on the same TransformedResult.");
            this.zza = resultTransform;
            zzdo = new zzdo<>(this.zzg);
            this.zzb = zzdo;
            zzb();
        }
        return zzdo;
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
    public final void andFinally(@NonNull ResultCallbacks<? super R> resultCallbacks) {
        synchronized (this.zze) {
            boolean z = true;
            zzau.zza(this.zzc == null, (Object) "Cannot call andFinally() twice.");
            if (this.zza != null) {
                z = false;
            }
            zzau.zza(z, (Object) "Cannot call then() and andFinally() on the same TransformedResult.");
            this.zzc = resultCallbacks;
            zzb();
        }
    }

    public final void onResult(R r) {
        synchronized (this.zze) {
            if (!r.getStatus().isSuccess()) {
                zza(r.getStatus());
                zza((Result) r);
            } else if (this.zza != null) {
                zzcy.zza().submit(new zzdp(this, r));
            } else if (zzc()) {
                this.zzc.onSuccess(r);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r2v0, types: [com.google.android.gms.common.api.PendingResult<?>, com.google.android.gms.common.api.PendingResult<R>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zza(com.google.android.gms.common.api.PendingResult<?> r2) {
        /*
            r1 = this;
            java.lang.Object r0 = r1.zze
            monitor-enter(r0)
            r1.zzd = r2     // Catch:{ all -> 0x000a }
            r1.zzb()     // Catch:{ all -> 0x000a }
            monitor-exit(r0)     // Catch:{ all -> 0x000a }
            return
        L_0x000a:
            r2 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x000a }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.zzdo.zza(com.google.android.gms.common.api.PendingResult):void");
    }

    private final void zzb() {
        if (this.zza != null || this.zzc != null) {
            GoogleApiClient googleApiClient = this.zzg.get();
            if (!(this.zzi || this.zza == null || googleApiClient == null)) {
                googleApiClient.zza(this);
                this.zzi = true;
            }
            Status status = this.zzf;
            if (status != null) {
                zzb(status);
                return;
            }
            PendingResult<R> pendingResult = this.zzd;
            if (pendingResult != null) {
                pendingResult.setResultCallback(this);
            }
        }
    }

    /* access modifiers changed from: private */
    public final void zza(Status status) {
        synchronized (this.zze) {
            this.zzf = status;
            zzb(this.zzf);
        }
    }

    private final void zzb(Status status) {
        synchronized (this.zze) {
            if (this.zza != null) {
                Status onFailure = this.zza.onFailure(status);
                zzau.zza(onFailure, "onFailure must not return null");
                this.zzb.zza(onFailure);
            } else if (zzc()) {
                this.zzc.onFailure(status);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void zza() {
        this.zzc = null;
    }

    private final boolean zzc() {
        return (this.zzc == null || this.zzg.get() == null) ? false : true;
    }

    /* access modifiers changed from: private */
    public static void zza(Result result) {
        if (result instanceof Releasable) {
            try {
                ((Releasable) result).release();
            } catch (RuntimeException e) {
                String valueOf = String.valueOf(result);
                StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 18);
                sb.append("Unable to release ");
                sb.append(valueOf);
                Log.w("TransformedResultImpl", sb.toString(), e);
            }
        }
    }
}
