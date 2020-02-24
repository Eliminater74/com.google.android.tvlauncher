package com.google.android.gms.internal;

import com.google.wireless.android.play.playlog.proto.ClientAnalytics;
import java.io.IOException;

/* compiled from: ClientAnalytics */
public final class zzgte extends zzgsb<zzgte> implements Cloneable {
    private static volatile zzgte[] zza;
    private String zzb = "";
    private String zzc = "";

    public static zzgte[] zza() {
        if (zza == null) {
            synchronized (zzgsf.zzb) {
                if (zza == null) {
                    zza = new zzgte[0];
                }
            }
        }
        return zza;
    }

    public zzgte() {
        this.zzay = null;
        this.zzaz = -1;
    }

    /* access modifiers changed from: private */
    /* renamed from: zzb */
    public final zzgte clone() {
        try {
            return (zzgte) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzgte)) {
            return false;
        }
        zzgte zzgte = (zzgte) obj;
        String str = this.zzb;
        if (str == null) {
            if (zzgte.zzb != null) {
                return false;
            }
        } else if (!str.equals(zzgte.zzb)) {
            return false;
        }
        String str2 = this.zzc;
        if (str2 == null) {
            if (zzgte.zzc != null) {
                return false;
            }
        } else if (!str2.equals(zzgte.zzc)) {
            return false;
        }
        if (this.zzay != null && !this.zzay.zzb()) {
            return this.zzay.equals(zzgte.zzay);
        }
        if (zzgte.zzay == null || zzgte.zzay.zzb()) {
            return true;
        }
        return false;
    }

    public final int hashCode() {
        int hashCode = (getClass().getName().hashCode() + ClientAnalytics.LogRequest.LogSource.SESAME_TRUST_API_PRIMES_VALUE) * 31;
        String str = this.zzb;
        int i = 0;
        int hashCode2 = (hashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.zzc;
        int hashCode3 = (hashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        if (this.zzay != null && !this.zzay.zzb()) {
            i = this.zzay.hashCode();
        }
        return hashCode3 + i;
    }

    public final void writeTo(zzgrz zzgrz) throws IOException {
        String str = this.zzb;
        if (str != null && !str.equals("")) {
            zzgrz.zza(1, this.zzb);
        }
        String str2 = this.zzc;
        if (str2 != null && !str2.equals("")) {
            zzgrz.zza(2, this.zzc);
        }
        super.writeTo(zzgrz);
    }

    /* access modifiers changed from: protected */
    public final int computeSerializedSize() {
        int computeSerializedSize = super.computeSerializedSize();
        String str = this.zzb;
        if (str != null && !str.equals("")) {
            computeSerializedSize += zzgrz.zzb(1, this.zzb);
        }
        String str2 = this.zzc;
        if (str2 == null || str2.equals("")) {
            return computeSerializedSize;
        }
        return computeSerializedSize + zzgrz.zzb(2, this.zzc);
    }

    public final /* synthetic */ zzgsh mergeFrom(zzgry zzgry) throws IOException {
        while (true) {
            int zza2 = zzgry.zza();
            if (zza2 == 0) {
                return this;
            }
            if (zza2 == 10) {
                this.zzb = zzgry.zze();
            } else if (zza2 == 18) {
                this.zzc = zzgry.zze();
            } else if (!super.zza(zzgry, zza2)) {
                return this;
            }
        }
    }
}
