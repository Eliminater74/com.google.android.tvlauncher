package com.google.android.gms.internal;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;

/* compiled from: CrossFadingDrawable */
public final class zzbko extends Drawable implements Drawable.Callback {
    private int zza;
    private long zzb;
    private int zzc;
    private int zzd;
    private int zze;
    private int zzf;
    private int zzg;
    private boolean zzh;
    private boolean zzi;
    private zzbks zzj;
    private Drawable zzk;
    private Drawable zzl;
    private boolean zzm;
    private boolean zzn;
    private boolean zzo;
    private int zzp;

    public zzbko(Drawable drawable, Drawable drawable2) {
        this(null);
        drawable = drawable == null ? zzbkq.zza : drawable;
        this.zzk = drawable;
        drawable.setCallback(this);
        zzbks zzbks = this.zzj;
        zzbks.zzb = drawable.getChangingConfigurations() | zzbks.zzb;
        drawable2 = drawable2 == null ? zzbkq.zza : drawable2;
        this.zzl = drawable2;
        drawable2.setCallback(this);
        zzbks zzbks2 = this.zzj;
        zzbks2.zzb = drawable2.getChangingConfigurations() | zzbks2.zzb;
    }

    zzbko(zzbks zzbks) {
        this.zza = 0;
        this.zze = 255;
        this.zzg = 0;
        this.zzh = true;
        this.zzj = new zzbks(zzbks);
    }

    public final void invalidateDrawable(Drawable drawable) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    public final void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, runnable, j);
        }
    }

    public final void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        Drawable.Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, runnable);
        }
    }

    public final int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.zzj.zza | this.zzj.zzb;
    }

    public final void setAlpha(int i) {
        if (this.zzg == this.zze) {
            this.zzg = i;
        }
        this.zze = i;
        invalidateSelf();
    }

    public final void setColorFilter(ColorFilter colorFilter) {
        this.zzk.setColorFilter(colorFilter);
        this.zzl.setColorFilter(colorFilter);
    }

    public final int getIntrinsicWidth() {
        return Math.max(this.zzk.getIntrinsicWidth(), this.zzl.getIntrinsicWidth());
    }

    public final int getIntrinsicHeight() {
        return Math.max(this.zzk.getIntrinsicHeight(), this.zzl.getIntrinsicHeight());
    }

    /* access modifiers changed from: protected */
    public final void onBoundsChange(Rect rect) {
        this.zzk.setBounds(rect);
        this.zzl.setBounds(rect);
    }

    public final Drawable.ConstantState getConstantState() {
        if (!zzb()) {
            return null;
        }
        this.zzj.zza = getChangingConfigurations();
        return this.zzj;
    }

    public final int getOpacity() {
        if (!this.zzo) {
            this.zzp = Drawable.resolveOpacity(this.zzk.getOpacity(), this.zzl.getOpacity());
            this.zzo = true;
        }
        return this.zzp;
    }

    private final boolean zzb() {
        if (!this.zzm) {
            this.zzn = (this.zzk.getConstantState() == null || this.zzl.getConstantState() == null) ? false : true;
            this.zzm = true;
        }
        return this.zzn;
    }

    public final Drawable mutate() {
        if (!this.zzi && super.mutate() == this) {
            if (zzb()) {
                this.zzk.mutate();
                this.zzl.mutate();
                this.zzi = true;
            } else {
                throw new IllegalStateException("One or more children of this LayerDrawable does not have constant state; this drawable cannot be mutated.");
            }
        }
        return this;
    }

    public final Drawable zza() {
        return this.zzl;
    }

    public final void zza(int i) {
        this.zzc = 0;
        this.zzd = this.zze;
        this.zzg = 0;
        this.zzf = 250;
        this.zza = 1;
        invalidateSelf();
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{java.lang.Math.min(float, float):float}
     arg types: [float, int]
     candidates:
      ClspMth{java.lang.Math.min(double, double):double}
      ClspMth{java.lang.Math.min(long, long):long}
      ClspMth{java.lang.Math.min(int, int):int}
      ClspMth{java.lang.Math.min(float, float):float} */
    public final void draw(Canvas canvas) {
        int i = this.zza;
        boolean z = true;
        if (i == 1) {
            this.zzb = SystemClock.uptimeMillis();
            this.zza = 2;
            z = false;
        } else if (i == 2 && this.zzb >= 0) {
            float uptimeMillis = ((float) (SystemClock.uptimeMillis() - this.zzb)) / ((float) this.zzf);
            if (uptimeMillis < 1.0f) {
                z = false;
            }
            if (z) {
                this.zza = 0;
            }
            this.zzg = (int) ((((float) this.zzd) * Math.min(uptimeMillis, 1.0f)) + 0.0f);
        }
        int i2 = this.zzg;
        boolean z2 = this.zzh;
        Drawable drawable = this.zzk;
        Drawable drawable2 = this.zzl;
        if (z) {
            if (!z2 || i2 == 0) {
                drawable.draw(canvas);
            }
            int i3 = this.zze;
            if (i2 == i3) {
                drawable2.setAlpha(i3);
                drawable2.draw(canvas);
                return;
            }
            return;
        }
        if (z2) {
            drawable.setAlpha(this.zze - i2);
        }
        drawable.draw(canvas);
        if (z2) {
            drawable.setAlpha(this.zze);
        }
        if (i2 > 0) {
            drawable2.setAlpha(i2);
            drawable2.draw(canvas);
            drawable2.setAlpha(this.zze);
        }
        invalidateSelf();
    }
}
