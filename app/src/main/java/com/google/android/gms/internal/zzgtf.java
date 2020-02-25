package com.google.android.gms.internal;

import com.google.wireless.android.play.playlog.proto.ClientAnalytics;

import java.io.IOException;

/* compiled from: ClientAnalytics */
public final class zzgtf extends zzgsb<zzgtf> implements Cloneable {
    private int zza = -1;
    private int zzb = 0;

    public zzgtf() {
        this.zzay = null;
        this.zzaz = -1;
    }

    /* access modifiers changed from: private */
    /* renamed from: zza */
    public final zzgtf clone() {
        try {
            return (zzgtf) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzgtf)) {
            return false;
        }
        zzgtf zzgtf = (zzgtf) obj;
        if (this.zza != zzgtf.zza || this.zzb != zzgtf.zzb) {
            return false;
        }
        if (this.zzay != null && !this.zzay.zzb()) {
            return this.zzay.equals(zzgtf.zzay);
        }
        if (zzgtf.zzay == null || zzgtf.zzay.zzb()) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        int i;
        int hashCode = (((((getClass().getName().hashCode() + ClientAnalytics.LogRequest.LogSource.SESAME_TRUST_API_PRIMES_VALUE) * 31) + this.zza) * 31) + this.zzb) * 31;
        if (this.zzay == null || this.zzay.zzb()) {
            i = 0;
        } else {
            i = this.zzay.hashCode();
        }
        return hashCode + i;
    }

    public final void writeTo(zzgrz zzgrz) throws IOException {
        int i = this.zza;
        if (i != -1) {
            zzgrz.zza(1, i);
        }
        int i2 = this.zzb;
        if (i2 != 0) {
            zzgrz.zza(2, i2);
        }
        super.writeTo(zzgrz);
    }

    /* access modifiers changed from: protected */
    public final int computeSerializedSize() {
        int computeSerializedSize = super.computeSerializedSize();
        int i = this.zza;
        if (i != -1) {
            computeSerializedSize += zzgrz.zzb(1, i);
        }
        int i2 = this.zzb;
        if (i2 != 0) {
            return computeSerializedSize + zzgrz.zzb(2, i2);
        }
        return computeSerializedSize;
    }

    /* access modifiers changed from: private */
    /* renamed from: zza */
    public final zzgtf mergeFrom(zzgry zzgry) throws IOException {
        while (true) {
            int zza2 = zzgry.zza();
            if (zza2 == 0) {
                return this;
            }
            if (zza2 == 8) {
                try {
                    int zzc = zzgry.zzc();
                    switch (zzc) {
                        default:
                            StringBuilder sb = new StringBuilder(43);
                            sb.append(zzc);
                            sb.append(" is not a valid enum NetworkType");
                            throw new IllegalArgumentException(sb.toString());
                        case -1:
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                        case 9:
                        case 10:
                        case 11:
                        case 12:
                        case 13:
                        case 14:
                        case 15:
                        case 16:
                        case 17:
                            this.zza = zzc;
                            continue;
                    }
                } catch (IllegalArgumentException e) {
                    zzgry.zze(zzgry.zzn());
                    zza(zzgry, zza2);
                }
            } else if (zza2 == 16) {
                try {
                    int zzc2 = zzgry.zzc();
                    if (zzc2 != 100) {
                        switch (zzc2) {
                            case 0:
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                            case 7:
                            case 8:
                            case 9:
                            case 10:
                            case 11:
                            case 12:
                            case 13:
                            case 14:
                            case 15:
                            case 16:
                                break;
                            default:
                                StringBuilder sb2 = new StringBuilder(45);
                                sb2.append(zzc2);
                                sb2.append(" is not a valid enum MobileSubtype");
                                throw new IllegalArgumentException(sb2.toString());
                        }
                    }
                    this.zzb = zzc2;
                } catch (IllegalArgumentException e2) {
                    zzgry.zze(zzgry.zzn());
                    zza(zzgry, zza2);
                }
            } else if (!super.zza(zzgry, zza2)) {
                return this;
            }
        }
    }
}
