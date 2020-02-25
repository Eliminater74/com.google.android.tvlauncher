package com.google.android.gms.internal;

import com.google.wireless.android.play.playlog.proto.ClientAnalytics;

import java.util.Arrays;

/* compiled from: UnknownFieldData */
final class zzgsj {
    final int zza;
    final byte[] zzb;

    zzgsj(int i, byte[] bArr) {
        this.zza = i;
        this.zzb = bArr;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzgsj)) {
            return false;
        }
        zzgsj zzgsj = (zzgsj) obj;
        if (this.zza != zzgsj.zza || !Arrays.equals(this.zzb, zzgsj.zzb)) {
            return false;
        }
        return true;
    }

    public final int hashCode() {
        return ((this.zza + ClientAnalytics.LogRequest.LogSource.SESAME_TRUST_API_PRIMES_VALUE) * 31) + Arrays.hashCode(this.zzb);
    }
}
