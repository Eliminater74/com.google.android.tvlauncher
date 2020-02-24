package com.google.android.gms.common.api;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzau;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;

public class BooleanResult implements Result {
    private final Status zza;
    private final boolean zzb;

    @Hide
    public BooleanResult(Status status, boolean z) {
        this.zza = (Status) zzau.zza(status, "Status must not be null");
        this.zzb = z;
    }

    public Status getStatus() {
        return this.zza;
    }

    public boolean getValue() {
        return this.zzb;
    }

    public final int hashCode() {
        return ((this.zza.hashCode() + ClientAnalytics.LogRequest.LogSource.SESAME_TRUST_API_PRIMES_VALUE) * 31) + (this.zzb ? 1 : 0);
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof BooleanResult)) {
            return false;
        }
        BooleanResult booleanResult = (BooleanResult) obj;
        if (!this.zza.equals(booleanResult.zza) || this.zzb != booleanResult.zzb) {
            return false;
        }
        return true;
    }
}
