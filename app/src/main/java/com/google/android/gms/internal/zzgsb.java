package com.google.android.gms.internal;

import java.io.IOException;

/* compiled from: ExtendableMessageNano */
public abstract class zzgsb<M extends zzgsb<M>> extends zzgsh {
    protected zzgsd zzay;

    /* access modifiers changed from: protected */
    public int computeSerializedSize() {
        if (this.zzay == null) {
            return 0;
        }
        int i = 0;
        for (int i2 = 0; i2 < this.zzay.zza(); i2++) {
            i += this.zzay.zzc(i2).zza();
        }
        return i;
    }

    public void writeTo(zzgrz zzgrz) throws IOException {
        if (this.zzay != null) {
            for (int i = 0; i < this.zzay.zza(); i++) {
                this.zzay.zzc(i).zza(zzgrz);
            }
        }
    }

    public final boolean hasExtension(zzgsc<M, ?> zzgsc) {
        zzgsd zzgsd = this.zzay;
        if (zzgsd == null || zzgsd.zza(zzgsc.zzb >>> 3) == null) {
            return false;
        }
        return true;
    }

    public final <T> T getExtension(zzgsc<M, T> zzgsc) {
        zzgse zza;
        zzgsd zzgsd = this.zzay;
        if (zzgsd == null || (zza = zzgsd.zza(zzgsc.zzb >>> 3)) == null) {
            return null;
        }
        return zza.zza(zzgsc);
    }

    public final <T> M setExtension(zzgsc<M, T> zzgsc, T t) {
        int i = zzgsc.zzb >>> 3;
        zzgse zzgse = null;
        if (t == null) {
            zzgsd zzgsd = this.zzay;
            if (zzgsd != null) {
                zzgsd.zzb(i);
                if (this.zzay.zzb()) {
                    this.zzay = null;
                }
            }
        } else {
            zzgsd zzgsd2 = this.zzay;
            if (zzgsd2 == null) {
                this.zzay = new zzgsd();
            } else {
                zzgse = zzgsd2.zza(i);
            }
            if (zzgse == null) {
                this.zzay.zza(i, new zzgse(zzgsc, t));
            } else {
                zzgse.zza(zzgsc, t);
            }
        }
        return this;
    }

    /* access modifiers changed from: protected */
    public final boolean zza(zzgry zzgry, int i) throws IOException {
        int zzn = zzgry.zzn();
        if (!zzgry.zzb(i)) {
            return false;
        }
        int i2 = i >>> 3;
        zzgsj zzgsj = new zzgsj(i, zzgry.zza(zzn, zzgry.zzn() - zzn));
        zzgse zzgse = null;
        zzgsd zzgsd = this.zzay;
        if (zzgsd == null) {
            this.zzay = new zzgsd();
        } else {
            zzgse = zzgsd.zza(i2);
        }
        if (zzgse == null) {
            zzgse = new zzgse();
            this.zzay.zza(i2, zzgse);
        }
        zzgse.zza(zzgsj);
        return true;
    }

    public M clone() throws CloneNotSupportedException {
        M m = (zzgsb) super.clone();
        zzgsf.zza(this, m);
        return m;
    }

    public final zzgsd getUnknownFieldArray() {
        return this.zzay;
    }
}
