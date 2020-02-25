package com.google.android.gms.internal;

import com.google.wireless.android.play.playlog.proto.ClientAnalytics;

import java.io.IOException;
import java.util.Arrays;

/* compiled from: CountersProto */
public final class zzgti extends zzgsb<zzgti> {
    public long zza = 0;
    public zzgth[] zzb = zzgth.zza();
    public byte[] zzc = zzgsk.zzh;
    private long zzd = 0;
    private String zze = "";
    private String zzf = "";

    public zzgti() {
        this.zzay = null;
        this.zzaz = -1;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzgti)) {
            return false;
        }
        zzgti zzgti = (zzgti) obj;
        if (this.zza != zzgti.zza || this.zzd != zzgti.zzd || !zzgsf.zza(this.zzb, zzgti.zzb) || !Arrays.equals(this.zzc, zzgti.zzc)) {
            return false;
        }
        String str = this.zze;
        if (str == null) {
            if (zzgti.zze != null) {
                return false;
            }
        } else if (!str.equals(zzgti.zze)) {
            return false;
        }
        String str2 = this.zzf;
        if (str2 == null) {
            if (zzgti.zzf != null) {
                return false;
            }
        } else if (!str2.equals(zzgti.zzf)) {
            return false;
        }
        if (this.zzay != null && !this.zzay.zzb()) {
            return this.zzay.equals(zzgti.zzay);
        }
        if (zzgti.zzay == null || zzgti.zzay.zzb()) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        long j = this.zza;
        long j2 = this.zzd;
        int hashCode = (((((((((getClass().getName().hashCode() + ClientAnalytics.LogRequest.LogSource.SESAME_TRUST_API_PRIMES_VALUE) * 31) + ((int) (j ^ (j >>> 32)))) * 31) + ((int) (j2 ^ (j2 >>> 32)))) * 31) + zzgsf.zza(this.zzb)) * 31) + Arrays.hashCode(this.zzc)) * 31;
        String str = this.zze;
        int i = 0;
        int hashCode2 = (hashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.zzf;
        int hashCode3 = (hashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        if (this.zzay != null && !this.zzay.zzb()) {
            i = this.zzay.hashCode();
        }
        return hashCode3 + i;
    }

    public final void writeTo(zzgrz zzgrz) throws IOException {
        long j = this.zza;
        if (j != 0) {
            zzgrz.zzb(1, j);
        }
        zzgth[] zzgthArr = this.zzb;
        if (zzgthArr != null && zzgthArr.length > 0) {
            int i = 0;
            while (true) {
                zzgth[] zzgthArr2 = this.zzb;
                if (i >= zzgthArr2.length) {
                    break;
                }
                zzgth zzgth = zzgthArr2[i];
                if (zzgth != null) {
                    zzgrz.zza(2, zzgth);
                }
                i++;
            }
        }
        if (!Arrays.equals(this.zzc, zzgsk.zzh)) {
            zzgrz.zza(3, this.zzc);
        }
        long j2 = this.zzd;
        if (j2 != 0) {
            zzgrz.zzb(4, j2);
        }
        String str = this.zze;
        if (str != null && !str.equals("")) {
            zzgrz.zza(5, this.zze);
        }
        String str2 = this.zzf;
        if (str2 != null && !str2.equals("")) {
            zzgrz.zza(6, this.zzf);
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
        zzgth[] zzgthArr = this.zzb;
        if (zzgthArr != null && zzgthArr.length > 0) {
            int i = 0;
            while (true) {
                zzgth[] zzgthArr2 = this.zzb;
                if (i >= zzgthArr2.length) {
                    break;
                }
                zzgth zzgth = zzgthArr2[i];
                if (zzgth != null) {
                    computeSerializedSize += zzgrz.zzb(2, zzgth);
                }
                i++;
            }
        }
        if (!Arrays.equals(this.zzc, zzgsk.zzh)) {
            computeSerializedSize += zzgrz.zzb(3, this.zzc);
        }
        long j2 = this.zzd;
        if (j2 != 0) {
            computeSerializedSize += zzgrz.zzf(4, j2);
        }
        String str = this.zze;
        if (str != null && !str.equals("")) {
            computeSerializedSize += zzgrz.zzb(5, this.zze);
        }
        String str2 = this.zzf;
        if (str2 == null || str2.equals("")) {
            return computeSerializedSize;
        }
        return computeSerializedSize + zzgrz.zzb(6, this.zzf);
    }

    public final /* synthetic */ zzgsh mergeFrom(zzgry zzgry) throws IOException {
        while (true) {
            int zza2 = zzgry.zza();
            if (zza2 == 0) {
                return this;
            }
            if (zza2 == 8) {
                this.zza = zzgry.zzb();
            } else if (zza2 == 18) {
                int zza3 = zzgsk.zza(zzgry, 18);
                zzgth[] zzgthArr = this.zzb;
                int length = zzgthArr == null ? 0 : zzgthArr.length;
                zzgth[] zzgthArr2 = new zzgth[(zza3 + length)];
                if (length != 0) {
                    System.arraycopy(this.zzb, 0, zzgthArr2, 0, length);
                }
                while (length < zzgthArr2.length - 1) {
                    zzgthArr2[length] = new zzgth();
                    zzgry.zza(zzgthArr2[length]);
                    zzgry.zza();
                    length++;
                }
                zzgthArr2[length] = new zzgth();
                zzgry.zza(zzgthArr2[length]);
                this.zzb = zzgthArr2;
            } else if (zza2 == 26) {
                this.zzc = zzgry.zzf();
            } else if (zza2 == 32) {
                this.zzd = zzgry.zzb();
            } else if (zza2 == 42) {
                this.zze = zzgry.zze();
            } else if (zza2 == 50) {
                this.zzf = zzgry.zze();
            } else if (!super.zza(zzgry, zza2)) {
                return this;
            }
        }
    }
}
