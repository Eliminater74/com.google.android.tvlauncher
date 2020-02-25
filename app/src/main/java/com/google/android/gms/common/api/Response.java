package com.google.android.gms.common.api;

import android.support.annotation.NonNull;

public class Response<T extends Result> {
    private T zza;

    public Response() {
    }

    protected Response(@NonNull T t) {
        this.zza = t;
    }

    /* access modifiers changed from: protected */
    @NonNull
    public T getResult() {
        return this.zza;
    }

    public void setResult(@NonNull T t) {
        this.zza = t;
    }
}
