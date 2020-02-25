package com.google.android.tvlauncher.appsview.transformation;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;
import java.util.Arrays;

public final class AppBannerInstallingIconTransformation extends BitmapTransformation {
    private static final byte[] sKeyBytes = AppBannerInstallingIconTransformation.class.getName().getBytes(CHARSET);
    private final int mBackgroundColor;
    private final int mBannerHeight;
    private final int mBannerWidth;
    private final float mIconRoundingRadius;
    private final byte[] mKeyBytes;
    private final Paint mPaint;
    private int mHashCode;
    private boolean mHashCodeInitialized = false;

    public AppBannerInstallingIconTransformation(int backgroundColor, float darkenFactor, float saturation, int bannerWidth, int bannerHeight, float iconRoundingRadius) {
        int i = backgroundColor;
        int i2 = bannerWidth;
        int i3 = bannerHeight;
        this.mBackgroundColor = i;
        this.mBannerWidth = i2;
        this.mBannerHeight = i3;
        this.mIconRoundingRadius = iconRoundingRadius;
        byte[] bArr = sKeyBytes;
        this.mKeyBytes = new byte[(bArr.length + 9)];
        System.arraycopy(bArr, 0, this.mKeyBytes, 0, bArr.length);
        byte[] bArr2 = this.mKeyBytes;
        bArr2[bArr2.length - 9] = (byte) (i2 & 15);
        bArr2[bArr2.length - 8] = (byte) (i3 & 15);
        bArr2[bArr2.length - 7] = Float.valueOf(iconRoundingRadius).byteValue();
        byte[] bArr3 = this.mKeyBytes;
        bArr3[bArr3.length - 6] = Float.valueOf(saturation).byteValue();
        byte[] bArr4 = this.mKeyBytes;
        bArr4[bArr4.length - 5] = Float.valueOf(darkenFactor).byteValue();
        byte[] bArr5 = this.mKeyBytes;
        bArr5[bArr5.length - 4] = (byte) ((i >> 24) & 15);
        bArr5[bArr5.length - 3] = (byte) ((i >> 16) & 15);
        bArr5[bArr5.length - 2] = (byte) ((i >> 8) & 15);
        bArr5[bArr5.length - 1] = (byte) (i & 15);
        ColorMatrix darkenMatrix = new ColorMatrix(new float[]{darkenFactor, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, darkenFactor, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, darkenFactor, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f});
        ColorMatrix colorTransformMatrix = new ColorMatrix();
        colorTransformMatrix.setSaturation(saturation);
        colorTransformMatrix.postConcat(darkenMatrix);
        this.mPaint = new Paint();
        this.mPaint.setColorFilter(new ColorMatrixColorFilter(colorTransformMatrix));
    }

    /* access modifiers changed from: protected */
    public Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap icon, int iconBoundingBoxWidth, int iconBoundingBoxHeight) {
        int i = iconBoundingBoxWidth;
        int i2 = iconBoundingBoxHeight;
        Bitmap.Config config = icon.getConfig() != null ? icon.getConfig() : Bitmap.Config.ARGB_8888;
        Bitmap transformedIcon = createIconBitmap(pool, config, icon, iconBoundingBoxWidth, iconBoundingBoxHeight);
        Bitmap bitmap = pool.getDirty(this.mBannerWidth, this.mBannerHeight, config);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(this.mBackgroundColor);
        BitmapShader shader = new BitmapShader(transformedIcon, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);
        canvas.translate((float) ((this.mBannerWidth - i) / 2), (float) ((this.mBannerHeight - i2) / 2));
        RectF rectF = new RectF(0.0f, 0.0f, (float) i, (float) i2);
        float f = this.mIconRoundingRadius;
        canvas.drawRoundRect(rectF, f, f, paint);
        return bitmap;
    }

    private Bitmap createIconBitmap(BitmapPool pool, Bitmap.Config config, Bitmap icon, int width, int height) {
        int i = width;
        int i2 = height;
        float minPercentage = Math.min(((float) i) / ((float) icon.getWidth()), ((float) i2) / ((float) icon.getHeight()));
        Bitmap toReuse = pool.getDirty(i, i2, config);
        Matrix matrix = new Matrix();
        matrix.postScale(minPercentage, minPercentage);
        matrix.postTranslate(((float) (i - ((int) (((float) icon.getWidth()) * minPercentage)))) / 2.0f, ((float) (i2 - ((int) (((float) icon.getHeight()) * minPercentage)))) / 2.0f);
        new Canvas(toReuse).drawBitmap(icon, matrix, this.mPaint);
        return toReuse;
    }

    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(this.mKeyBytes);
    }

    public int hashCode() {
        if (this.mHashCodeInitialized) {
            return this.mHashCode;
        }
        this.mHashCodeInitialized = true;
        this.mHashCode = Arrays.hashCode(this.mKeyBytes);
        return this.mHashCode;
    }
}
