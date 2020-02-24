package com.google.android.gms.internal;

import com.google.wireless.android.play.playlog.proto.ClientAnalytics;
import java.io.IOException;

/* compiled from: CountersProto */
public final class zzgth extends zzgsb<zzgth> {
    private static volatile zzgth[] zzc;
    public long zza = 0;
    public zzgtg[] zzb = zzgtg.zza();
    private String zzd = "";

    public static zzgth[] zza() {
        if (zzc == null) {
            synchronized (zzgsf.zzb) {
                if (zzc == null) {
                    zzc = new zzgth[0];
                }
            }
        }
        return zzc;
    }

    public zzgth() {
        this.zzay = null;
        this.zzaz = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzgth)) {
            return false;
        }
        zzgth zzgth = (zzgth) obj;
        if (this.zza != zzgth.zza) {
            return false;
        }
        String str = this.zzd;
        if (str == null) {
            if (zzgth.zzd != null) {
                return false;
            }
        } else if (!str.equals(zzgth.zzd)) {
            return false;
        }
        if (!zzgsf.zza(this.zzb, zzgth.zzb)) {
            return false;
        }
        if (this.zzay != null && !this.zzay.zzb()) {
            return this.zzay.equals(zzgth.zzay);
        }
        if (zzgth.zzay == null || zzgth.zzay.zzb()) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        long j = this.zza;
        int hashCode = (((getClass().getName().hashCode() + ClientAnalytics.LogRequest.LogSource.SESAME_TRUST_API_PRIMES_VALUE) * 31) + ((int) (j ^ (j >>> 32)))) * 31;
        String str = this.zzd;
        int i = 0;
        int hashCode2 = (((hashCode + (str == null ? 0 : str.hashCode())) * 31) + zzgsf.zza(this.zzb)) * 31;
        if (this.zzay != null && !this.zzay.zzb()) {
            i = this.zzay.hashCode();
        }
        return hashCode2 + i;
    }

    public final void writeTo(zzgrz zzgrz) throws IOException {
        long j = this.zza;
        if (j != 0) {
            zzgrz.zzc(1, j);
        }
        String str = this.zzd;
        if (str != null && !str.equals("")) {
            zzgrz.zza(2, this.zzd);
        }
        zzgtg[] zzgtgArr = this.zzb;
        if (zzgtgArr != null && zzgtgArr.length > 0) {
            int i = 0;
            while (true) {
                zzgtg[] zzgtgArr2 = this.zzb;
                if (i >= zzgtgArr2.length) {
                    break;
                }
                zzgtg zzgtg = zzgtgArr2[i];
                if (zzgtg != null) {
                    zzgrz.zza(3, zzgtg);
                }
                i++;
            }
        }
        super.writeTo(zzgrz);
    }

    /* access modifiers changed from: protected */
    public final int computeSerializedSize() {
        int computeSerializedSize = super.computeSerializedSize();
        if (this.zza != 0) {
            computeSerializedSize += zzgrz.zzb(1) + 8;
        }
        String str = this.zzd;
        if (str != null && !str.equals("")) {
            computeSerializedSize += zzgrz.zzb(2, this.zzd);
        }
        zzgtg[] zzgtgArr = this.zzb;
        if (zzgtgArr != null && zzgtgArr.length > 0) {
            int i = 0;
            while (true) {
                zzgtg[] zzgtgArr2 = this.zzb;
                if (i >= zzgtgArr2.length) {
                    break;
                }
                zzgtg zzgtg = zzgtgArr2[i];
                if (zzgtg != null) {
                    computeSerializedSize += zzgrz.zzb(3, zzgtg);
                }
                i++;
            }
        }
        return computeSerializedSize;
    }

    public final /* synthetic */ zzgsh mergeFrom(zzgry zzgry) throws IOException {
        while (true) {
            int zza2 = zzgry.zza();
            if (zza2 == 0) {
                return this;
            }
            if (zza2 == 9) {
                this.zza = zzgry.zzk();
            } else if (zza2 == 18) {
                this.zzd = zzgry.zze();
            } else if (zza2 == 26) {
                int zza3 = zzgsk.zza(zzgry, 26);
                zzgtg[] zzgtgArr = this.zzb;
                int length = zzgtgArr == null ? 0 : zzgtgArr.length;
                zzgtg[] zzgtgArr2 = new zzgtg[(zza3 + length)];
                if (length != 0) {
                    System.arraycopy(this.zzb, 0, zzgtgArr2, 0, length);
                }
                while (length < zzgtgArr2.length - 1) {
                    zzgtgArr2[length] = new zzgtg();
                    zzgry.zza(zzgtgArr2[length]);
                    zzgry.zza();
                    length++;
                }
                zzgtgArr2[length] = new zzgtg();
                zzgry.zza(zzgtgArr2[length]);
                this.zzb = zzgtgArr2;
            } else if (!super.zza(zzgry, zza2)) {
                return this;
            }
        }
    }
}
