package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.PendingResult.zza;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultStore;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.TransformedResult;
import com.google.android.gms.common.internal.zzau;

import java.util.concurrent.TimeUnit;

/* compiled from: PendingResultFacade */
public abstract class zzcq<A extends Result, B extends Result> extends PendingResult<B> {
    private final PendingResult<A> zza;

    public zzcq(PendingResult<A> pendingResult) {
        zzau.zza(pendingResult);
        this.zza = pendingResult;
    }

    /* access modifiers changed from: protected */
    public abstract B zza(A a);

    public B await() {
        return zza((Result) this.zza.await());
    }

    public B await(long j, TimeUnit timeUnit) {
        return zza((Result) this.zza.await(j, timeUnit));
    }

    public void cancel() {
        this.zza.cancel();
    }

    public boolean isCanceled() {
        return this.zza.isCanceled();
    }

    public void setResultCallback(ResultCallback<? super B> resultCallback) {
        this.zza.setResultCallback(new zzcr(this, resultCallback));
    }

    public void setResultCallback(ResultCallback<? super B> resultCallback, long j, TimeUnit timeUnit) {
        this.zza.setResultCallback(new zzcs(this, resultCallback), j, timeUnit);
    }

    public final void zza(PendingResult.zza zza2) {
        this.zza.zza(zza2);
    }

    public <S extends Result> TransformedResult<S> then(ResultTransform<? super B, ? extends S> resultTransform) {
        return this.zza.then(new zzct(this, resultTransform));
    }

    public final void zzb(int i) {
        this.zza.zzb(i);
    }

    public final Integer zzb() {
        return this.zza.zzb();
    }

    public void store(ResultStore resultStore, int i) {
        this.zza.store(resultStore, i);
    }
}
