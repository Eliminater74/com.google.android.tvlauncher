package com.google.android.gms.internal;

import com.google.wireless.android.play.playlog.proto.ClientAnalytics;
import java.io.IOException;
import java.util.Arrays;

/* compiled from: ClientAnalytics */
public final class zzgtc extends zzgsb<zzgtc> implements Cloneable {
    private byte[] zza = zzgsk.zzh;
    private String zzb = "";
    private byte[][] zzc = zzgsk.zzg;
    private boolean zzd = false;

    public zzgtc() {
        this.zzay = null;
        this.zzaz = -1;
    }

    /* access modifiers changed from: private */
    /* renamed from: zza */
    public final zzgtc clone() {
        try {
            zzgtc zzgtc = (zzgtc) super.clone();
            byte[][] bArr = this.zzc;
            if (bArr != null && bArr.length > 0) {
                zzgtc.zzc = (byte[][]) bArr.clone();
            }
            return zzgtc;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzgtc)) {
            return false;
        }
        zzgtc zzgtc = (zzgtc) obj;
        if (!Arrays.equals(this.zza, zzgtc.zza)) {
            return false;
        }
        String str = this.zzb;
        if (str == null) {
            if (zzgtc.zzb != null) {
                return false;
            }
        } else if (!str.equals(zzgtc.zzb)) {
            return false;
        }
        if (!zzgsf.zza(this.zzc, zzgtc.zzc) || this.zzd != zzgtc.zzd) {
            return false;
        }
        if (this.zzay != null && !this.zzay.zzb()) {
            return this.zzay.equals(zzgtc.zzay);
        }
        if (zzgtc.zzay == null || zzgtc.zzay.zzb()) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        int hashCode = (((getClass().getName().hashCode() + ClientAnalytics.LogRequest.LogSource.SESAME_TRUST_API_PRIMES_VALUE) * 31) + Arrays.hashCode(this.zza)) * 31;
        String str = this.zzb;
        int i = 0;
        int hashCode2 = (((((hashCode + (str == null ? 0 : str.hashCode())) * 31) + zzgsf.zza(this.zzc)) * 31) + (this.zzd ? 1231 : 1237)) * 31;
        if (this.zzay != null && !this.zzay.zzb()) {
            i = this.zzay.hashCode();
        }
        return hashCode2 + i;
    }

    public final void writeTo(zzgrz zzgrz) throws IOException {
        if (!Arrays.equals(this.zza, zzgsk.zzh)) {
            zzgrz.zza(1, this.zza);
        }
        byte[][] bArr = this.zzc;
        if (bArr != null && bArr.length > 0) {
            int i = 0;
            while (true) {
                byte[][] bArr2 = this.zzc;
                if (i >= bArr2.length) {
                    break;
                }
                byte[] bArr3 = bArr2[i];
                if (bArr3 != null) {
                    zzgrz.zza(2, bArr3);
                }
                i++;
            }
        }
        boolean z = this.zzd;
        if (z) {
            zzgrz.zza(3, z);
        }
        String str = this.zzb;
        if (str != null && !str.equals("")) {
            zzgrz.zza(4, this.zzb);
        }
        super.writeTo(zzgrz);
    }

    /* access modifiers changed from: protected */
    public final int computeSerializedSize() {
        int computeSerializedSize = super.computeSerializedSize();
        if (!Arrays.equals(this.zza, zzgsk.zzh)) {
            computeSerializedSize += zzgrz.zzb(1, this.zza);
        }
        byte[][] bArr = this.zzc;
        if (bArr != null && bArr.length > 0) {
            int i = 0;
            int i2 = 0;
            int i3 = 0;
            while (true) {
                byte[][] bArr2 = this.zzc;
                if (i >= bArr2.length) {
                    break;
                }
                byte[] bArr3 = bArr2[i];
                if (bArr3 != null) {
                    i3++;
                    i2 += zzgrz.zzc(bArr3);
                }
                i++;
            }
            computeSerializedSize = computeSerializedSize + i2 + (i3 * 1);
        }
        if (this.zzd) {
            computeSerializedSize += zzgrz.zzb(3) + 1;
        }
        String str = this.zzb;
        if (str == null || str.equals("")) {
            return computeSerializedSize;
        }
        return computeSerializedSize + zzgrz.zzb(4, this.zzb);
    }

    public final /* synthetic */ zzgsh mergeFrom(zzgry zzgry) throws IOException {
        while (true) {
            int zza2 = zzgry.zza();
            if (zza2 == 0) {
                return this;
            }
            if (zza2 == 10) {
                this.zza = zzgry.zzf();
            } else if (zza2 == 18) {
                int zza3 = zzgsk.zza(zzgry, 18);
                byte[][] bArr = this.zzc;
                int length = bArr == null ? 0 : bArr.length;
                byte[][] bArr2 = new byte[(zza3 + length)][];
                if (length != 0) {
                    System.arraycopy(this.zzc, 0, bArr2, 0, length);
                }
                while (length < bArr2.length - 1) {
                    bArr2[length] = zzgry.zzf();
                    zzgry.zza();
                    length++;
                }
                bArr2[length] = zzgry.zzf();
                this.zzc = bArr2;
            } else if (zza2 == 24) {
                this.zzd = zzgry.zzd();
            } else if (zza2 == 34) {
                this.zzb = zzgry.zze();
            } else if (!super.zza(zzgry, zza2)) {
                return this;
            }
        }
    }
}
