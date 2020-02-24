package com.google.android.gms.common.images;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import com.google.android.gms.common.internal.zzak;
import com.google.android.gms.internal.zzbko;
import com.google.android.gms.internal.zzbkt;
import java.lang.ref.WeakReference;

/* compiled from: ImageRequest */
public final class zzc extends zza {
    private WeakReference<ImageView> zzc;

    public zzc(ImageView imageView, Uri uri) {
        super(uri, 0);
        com.google.android.gms.common.internal.zzc.zza(imageView);
        this.zzc = new WeakReference<>(imageView);
    }

    public zzc(ImageView imageView, int i) {
        super(null, i);
        com.google.android.gms.common.internal.zzc.zza(imageView);
        this.zzc = new WeakReference<>(imageView);
    }

    public final int hashCode() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzc)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        ImageView imageView = this.zzc.get();
        ImageView imageView2 = ((zzc) obj).zzc.get();
        if (imageView2 == null || imageView == null || !zzak.zza(imageView2, imageView)) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public final void zza(Drawable drawable, boolean z, boolean z2, boolean z3) {
        ImageView imageView = this.zzc.get();
        if (imageView != null) {
            int i = 0;
            boolean z4 = !z2 && !z3;
            if (z4 && (imageView instanceof zzbkt)) {
                int zza = zzbkt.zza();
                if (this.zzb != 0 && zza == this.zzb) {
                    return;
                }
            }
            boolean zza2 = zza(z, z2);
            Uri uri = null;
            if (zza2) {
                Drawable drawable2 = imageView.getDrawable();
                if (drawable2 == null) {
                    drawable2 = null;
                } else if (drawable2 instanceof zzbko) {
                    drawable2 = ((zzbko) drawable2).zza();
                }
                drawable = new zzbko(drawable2, drawable);
            }
            imageView.setImageDrawable(drawable);
            if (imageView instanceof zzbkt) {
                if (z3) {
                    uri = this.zza.zza;
                }
                zzbkt.zza(uri);
                if (z4) {
                    i = this.zzb;
                }
                zzbkt.zza(i);
            }
            if (zza2) {
                ((zzbko) drawable).zza(250);
            }
        }
    }
}
