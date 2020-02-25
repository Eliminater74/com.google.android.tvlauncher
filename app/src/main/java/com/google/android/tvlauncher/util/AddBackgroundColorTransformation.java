package com.google.android.tvlauncher.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;
import java.util.Arrays;

public final class AddBackgroundColorTransformation extends BitmapTransformation {
    private static final byte[] sKeyBytes = "com.google.android.tvLauncher.util.AddBackgroundColorTransformation".getBytes(CHARSET);
    private final int mBackgroundColor;
    private final byte[] mKeyBytes;
    private int mHashCode;
    private boolean mHashCodeInitialized = false;
    private boolean mUseTargetDimensions;

    public AddBackgroundColorTransformation(int color, boolean useTargetDimensions) {
        this.mBackgroundColor = color;
        byte[] bArr = sKeyBytes;
        this.mKeyBytes = new byte[(bArr.length + 5)];
        System.arraycopy(bArr, 0, this.mKeyBytes, 0, bArr.length);
        byte[] bArr2 = this.mKeyBytes;
        bArr2[bArr2.length - 5] = useTargetDimensions ? (byte) 1 : 0;
        bArr2[bArr2.length - 4] = (byte) ((color >> 24) & 15);
        bArr2[bArr2.length - 3] = (byte) ((color >> 16) & 15);
        bArr2[bArr2.length - 2] = (byte) ((color >> 8) & 15);
        bArr2[bArr2.length - 1] = (byte) (color & 15);
        this.mUseTargetDimensions = useTargetDimensions;
    }

    /* access modifiers changed from: protected */
    public Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        if (this.mUseTargetDimensions) {
            return transformUsingTargetDimens(pool, toTransform, outWidth, outHeight);
        }
        return transformUsingOriginalDimens(pool, toTransform);
    }

    public boolean equals(Object o) {
        if ((o instanceof AddBackgroundColorTransformation) && ((AddBackgroundColorTransformation) o).mBackgroundColor == this.mBackgroundColor) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        if (this.mHashCodeInitialized) {
            return this.mHashCode;
        }
        this.mHashCodeInitialized = true;
        this.mHashCode = Arrays.hashCode(this.mKeyBytes);
        return this.mHashCode;
    }

    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(this.mKeyBytes);
    }

    @NonNull
    private Bitmap transformUsingOriginalDimens(@NonNull BitmapPool pool, @NonNull Bitmap toTransform) {
        if (!toTransform.hasAlpha()) {
            return toTransform;
        }
        Bitmap result = pool.getDirty(toTransform.getWidth(), toTransform.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawColor(this.mBackgroundColor);
        canvas.drawBitmap(toTransform, 0.0f, 0.0f, (Paint) null);
        return result;
    }

    @NonNull
    private Bitmap transformUsingTargetDimens(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        int width = toTransform.getWidth();
        int height = toTransform.getHeight();
        if (width < outWidth || height < outHeight) {
            Bitmap result = pool.getDirty(outWidth, outHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);
            canvas.drawColor(this.mBackgroundColor);
            canvas.drawBitmap(toTransform, (float) ((outWidth - width) / 2), (float) ((outHeight - height) / 2), (Paint) null);
            return result;
        } else if (!toTransform.hasAlpha()) {
            return toTransform;
        } else {
            return transformUsingOriginalDimens(pool, toTransform);
        }
    }
}
