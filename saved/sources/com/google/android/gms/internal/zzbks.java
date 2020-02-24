package com.google.android.gms.internal;

import android.graphics.drawable.Drawable;

/* compiled from: CrossFadingDrawable */
final class zzbks extends Drawable.ConstantState {
    int zza;
    int zzb;

    zzbks(zzbks zzbks) {
        if (zzbks != null) {
            this.zza = zzbks.zza;
            this.zzb = zzbks.zzb;
        }
    }

    public final Drawable newDrawable() {
        return new zzbko(this);
    }

    public final int getChangingConfigurations() {
        return this.zza;
    }
}
