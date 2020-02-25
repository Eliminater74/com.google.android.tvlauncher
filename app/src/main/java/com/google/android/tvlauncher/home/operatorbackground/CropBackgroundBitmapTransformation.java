package com.google.android.tvlauncher.home.operatorbackground;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.google.android.tvlauncher.C1188R;

import java.security.MessageDigest;
import java.util.Arrays;

public final class CropBackgroundBitmapTransformation extends BitmapTransformation {
    private static final byte[] sKeyBytes = CropBackgroundBitmapTransformation.class.getName().getBytes(CHARSET);
    private static final int sHashCode = Arrays.hashCode(sKeyBytes);
    private final int mViewPortHeight;
    private final int mViewPortWidth;

    CropBackgroundBitmapTransformation(Context context) {
        this.mViewPortWidth = context.getResources().getDimensionPixelSize(C1188R.dimen.home_background_image_view_port_width);
        this.mViewPortHeight = context.getResources().getDimensionPixelSize(C1188R.dimen.home_background_image_view_port_height);
    }

    /* access modifiers changed from: protected */
    public Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap result = pool.getDirty(this.mViewPortWidth, this.mViewPortHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        int width = toTransform.getWidth();
        int height = toTransform.getHeight();
        Rect source = new Rect();
        source.left = (width - this.mViewPortWidth) / 2;
        source.top = (height - this.mViewPortHeight) / 2;
        source.right = source.left + this.mViewPortWidth;
        int i = source.top;
        int i2 = this.mViewPortHeight;
        source.bottom = i + i2;
        canvas.drawBitmap(toTransform, source, new Rect(0, 0, this.mViewPortWidth, i2), (Paint) null);
        return result;
    }

    public boolean equals(Object o) {
        return o instanceof CropBackgroundBitmapTransformation;
    }

    public int hashCode() {
        return sHashCode;
    }

    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(sKeyBytes);
    }
}
