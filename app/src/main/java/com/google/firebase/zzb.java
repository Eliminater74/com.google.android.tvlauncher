package com.google.firebase;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzdg;
import com.google.android.gms.common.internal.Hide;

@Hide
/* compiled from: FirebaseExceptionMapper */
public final class zzb implements zzdg {
    public final Exception zza(Status status) {
        if (status.getStatusCode() == 8) {
            return new FirebaseException(status.zza());
        }
        return new FirebaseApiNotAvailableException(status.zza());
    }
}
