package com.google.android.gms.common.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.Status;

@Hide
/* compiled from: ApiExceptionUtil */
public final class zzb {
    @NonNull
    public static ApiException zza(@NonNull Status status) {
        if (status.hasResolution()) {
            return new ResolvableApiException(status);
        }
        return new ApiException(status);
    }
}
