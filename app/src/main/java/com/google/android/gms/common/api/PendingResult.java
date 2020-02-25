package com.google.android.gms.common.api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.common.internal.Hide;

import java.util.concurrent.TimeUnit;

public abstract class PendingResult<R extends Result> {

    @NonNull
    public abstract R await();

    @NonNull
    public abstract R await(long j, @NonNull TimeUnit timeUnit);

    public abstract void cancel();

    public abstract boolean isCanceled();

    public abstract void setResultCallback(@NonNull ResultCallback<? super R> resultCallback);

    public abstract void setResultCallback(@NonNull ResultCallback<? super R> resultCallback, long j, @NonNull TimeUnit timeUnit);

    @Hide
    public void store(@NonNull ResultStore resultStore, int i) {
        throw new UnsupportedOperationException();
    }

    @Hide
    public void zza(@NonNull zza zza2) {
        throw new UnsupportedOperationException();
    }

    @NonNull
    public <S extends Result> TransformedResult<S> then(@NonNull ResultTransform<? super R, ? extends S> resultTransform) {
        throw new UnsupportedOperationException();
    }

    @Hide
    public void zzb(int i) {
        throw new UnsupportedOperationException();
    }

    @Nullable
    @Hide
    public Integer zzb() {
        throw new UnsupportedOperationException();
    }

    @Hide
    public interface zza {
        @Hide
        void zza(Status status);
    }
}
