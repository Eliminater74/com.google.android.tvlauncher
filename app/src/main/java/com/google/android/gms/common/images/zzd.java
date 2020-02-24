package com.google.android.gms.common.images;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import com.google.android.gms.common.images.ImageManager;
import com.google.android.gms.common.internal.zzak;
import com.google.android.gms.common.internal.zzc;
import java.lang.ref.WeakReference;
import java.util.Arrays;

/* compiled from: ImageRequest */
public final class zzd extends zza {
    private WeakReference<ImageManager.OnImageLoadedListener> zzc;

    public zzd(ImageManager.OnImageLoadedListener onImageLoadedListener, Uri uri) {
        super(uri, 0);
        zzc.zza(onImageLoadedListener);
        this.zzc = new WeakReference<>(onImageLoadedListener);
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zza});
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzd)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        zzd zzd = (zzd) obj;
        ImageManager.OnImageLoadedListener onImageLoadedListener = this.zzc.get();
        ImageManager.OnImageLoadedListener onImageLoadedListener2 = zzd.zzc.get();
        if (onImageLoadedListener2 == null || onImageLoadedListener == null || !zzak.zza(onImageLoadedListener2, onImageLoadedListener) || !zzak.zza(zzd.zza, this.zza)) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public final void zza(Drawable drawable, boolean z, boolean z2, boolean z3) {
        ImageManager.OnImageLoadedListener onImageLoadedListener;
        if (!z2 && (onImageLoadedListener = this.zzc.get()) != null) {
            onImageLoadedListener.onImageLoaded(this.zza.zza, drawable, z3);
        }
    }
}
