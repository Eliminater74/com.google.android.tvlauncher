package com.google.android.gms.internal;

import com.google.wireless.android.play.playlog.proto.ClientAnalytics;
import java.io.IOException;

/* compiled from: CountersProto */
public final class zzgtg extends zzgsb<zzgtg> {
    private static volatile zzgtg[] zzc;
    public long zza = 0;
    public long zzb = 0;

    public static zzgtg[] zza() {
        if (zzc == null) {
            synchronized (zzgsf.zzb) {
                if (zzc == null) {
                    zzc = new zzgtg[0];
                }
            }
        }
        return zzc;
    }

    public zzgtg() {
        this.zzay = null;
        this.zzaz = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzgtg)) {
            return false;
        }
        zzgtg zzgtg = (zzgtg) obj;
        if (this.zza != zzgtg.zza || this.zzb != zzgtg.zzb) {
            return false;
        }
        if (this.zzay != null && !this.zzay.zzb()) {
            return this.zzay.equals(zzgtg.zzay);
        }
        if (zzgtg.zzay == null || zzgtg.zzay.zzb()) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        int i;
        long j = this.zza;
        long j2 = this.zzb;
        int hashCode = (((((getClass().getName().hashCode() + ClientAnalytics.LogRequest.LogSource.SESAME_TRUST_API_PRIMES_VALUE) * 31) + ((int) (j ^ (j >>> 32)))) * 31) + ((int) (j2 ^ (j2 >>> 32)))) * 31;
        if (this.zzay == null || this.zzay.zzb()) {
            i = 0;
        } else {
            i = this.zzay.hashCode();
        }
        return hashCode + i;
    }

    public final void writeTo(zzgrz zzgrz) throws IOException {
        long j = this.zza;
        if (j != 0) {
            zzgrz.zzb(1, j);
        }
        long j2 = this.zzb;
        if (j2 != 0) {
            zzgrz.zzb(2, j2);
        }
        super.writeTo(zzgrz);
    }

    /* access modifiers changed from: protected */
    public final int computeSerializedSize() {
        int computeSerializedSize = super.computeSerializedSize();
        long j = this.zza;
        if (j != 0) {
            computeSerializedSize += zzgrz.zzf(1, j);
        }
        long j2 = this.zzb;
        if (j2 != 0) {
            return computeSerializedSize + zzgrz.zzf(2, j2);
        }
        return computeSerializedSize;
    }

    public final /* synthetic */ zzgsh mergeFrom(zzgry zzgry) throws IOException {
        while (true) {
            int zza2 = zzgry.zza();
            if (zza2 == 0) {
                return this;
            }
            if (zza2 == 8) {
                this.zza = zzgry.zzb();
            } else if (zza2 == 16) {
                this.zzb = zzgry.zzb();
            } else if (!super.zza(zzgry, zza2)) {
                return this;
            }
        }
    }
}
