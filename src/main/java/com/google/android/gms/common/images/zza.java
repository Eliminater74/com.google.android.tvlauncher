package com.google.android.gms.common.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzc;
import com.google.android.gms.internal.zzbku;

@Hide
/* compiled from: ImageRequest */
public abstract class zza {
    final zzb zza;
    protected int zzb = 0;
    private int zzc = 0;
    private boolean zzd = false;
    private boolean zze = true;
    private boolean zzf = false;
    private boolean zzg = true;

    public zza(Uri uri, int i) {
        this.zza = new zzb(uri);
        this.zzb = i;
    }

    /* access modifiers changed from: protected */
    public abstract void zza(Drawable drawable, boolean z, boolean z2, boolean z3);

    /* access modifiers changed from: package-private */
    public final void zza(Context context, Bitmap bitmap, boolean z) {
        zzc.zza(bitmap);
        zza(new BitmapDrawable(context.getResources(), bitmap), z, false, true);
    }

    /* access modifiers changed from: package-private */
    public final void zza(Context context, zzbku zzbku) {
        if (this.zzg) {
            zza(null, false, true, false);
        }
    }

    /* access modifiers changed from: package-private */
    public final void zza(Context context, zzbku zzbku, boolean z) {
        Drawable drawable;
        int i = this.zzb;
        if (i != 0) {
            drawable = context.getResources().getDrawable(i);
        } else {
            drawable = null;
        }
        zza(drawable, z, false, false);
    }

    /* access modifiers changed from: protected */
    public final boolean zza(boolean z, boolean z2) {
        return this.zze && !z2 && !z;
    }
}
