package android.support.p001v4.graphics.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;

import com.google.wireless.android.play.playlog.proto.ClientAnalytics;

/* renamed from: android.support.v4.graphics.drawable.RoundedBitmapDrawable */
public abstract class RoundedBitmapDrawable extends Drawable {
    private static final int DEFAULT_PAINT_FLAGS = 3;
    final Bitmap mBitmap;
    final Rect mDstRect = new Rect();
    private final BitmapShader mBitmapShader;
    private final RectF mDstRectF = new RectF();
    private final Paint mPaint = new Paint(3);
    private final Matrix mShaderMatrix = new Matrix();
    private boolean mApplyGravity = true;
    private int mBitmapHeight;
    private int mBitmapWidth;
    private float mCornerRadius;
    private int mGravity = 119;
    private boolean mIsCircular;
    private int mTargetDensity = ClientAnalytics.LogRequest.LogSource.JAM_KIOSK_ANDROID_PRIMES_VALUE;

    RoundedBitmapDrawable(Resources res, Bitmap bitmap) {
        if (res != null) {
            this.mTargetDensity = res.getDisplayMetrics().densityDpi;
        }
        this.mBitmap = bitmap;
        if (this.mBitmap != null) {
            computeBitmapSize();
            this.mBitmapShader = new BitmapShader(this.mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            return;
        }
        this.mBitmapHeight = -1;
        this.mBitmapWidth = -1;
        this.mBitmapShader = null;
    }

    private static boolean isGreaterThanZero(float toCompare) {
        return toCompare > 0.05f;
    }

    @NonNull
    public final Paint getPaint() {
        return this.mPaint;
    }

    @Nullable
    public final Bitmap getBitmap() {
        return this.mBitmap;
    }

    private void computeBitmapSize() {
        this.mBitmapWidth = this.mBitmap.getScaledWidth(this.mTargetDensity);
        this.mBitmapHeight = this.mBitmap.getScaledHeight(this.mTargetDensity);
    }

    public void setTargetDensity(@NonNull Canvas canvas) {
        setTargetDensity(canvas.getDensity());
    }

    public void setTargetDensity(@NonNull DisplayMetrics metrics) {
        setTargetDensity(metrics.densityDpi);
    }

    public void setTargetDensity(int density) {
        if (this.mTargetDensity != density) {
            this.mTargetDensity = density == 0 ? ClientAnalytics.LogRequest.LogSource.JAM_KIOSK_ANDROID_PRIMES_VALUE : density;
            if (this.mBitmap != null) {
                computeBitmapSize();
            }
            invalidateSelf();
        }
    }

    public int getGravity() {
        return this.mGravity;
    }

    public void setGravity(int gravity) {
        if (this.mGravity != gravity) {
            this.mGravity = gravity;
            this.mApplyGravity = true;
            invalidateSelf();
        }
    }

    public void setMipMap(boolean mipMap) {
        throw new UnsupportedOperationException();
    }

    public boolean hasMipMap() {
        throw new UnsupportedOperationException();
    }

    public void setAntiAlias(boolean aa) {
        this.mPaint.setAntiAlias(aa);
        invalidateSelf();
    }

    public boolean hasAntiAlias() {
        return this.mPaint.isAntiAlias();
    }

    public void setFilterBitmap(boolean filter) {
        this.mPaint.setFilterBitmap(filter);
        invalidateSelf();
    }

    public void setDither(boolean dither) {
        this.mPaint.setDither(dither);
        invalidateSelf();
    }

    /* access modifiers changed from: package-private */
    public void gravityCompatApply(int gravity, int bitmapWidth, int bitmapHeight, Rect bounds, Rect outRect) {
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: package-private */
    public void updateDstRect() {
        if (this.mApplyGravity) {
            if (this.mIsCircular) {
                int minDimen = Math.min(this.mBitmapWidth, this.mBitmapHeight);
                gravityCompatApply(this.mGravity, minDimen, minDimen, getBounds(), this.mDstRect);
                int minDrawDimen = Math.min(this.mDstRect.width(), this.mDstRect.height());
                this.mDstRect.inset(Math.max(0, (this.mDstRect.width() - minDrawDimen) / 2), Math.max(0, (this.mDstRect.height() - minDrawDimen) / 2));
                this.mCornerRadius = ((float) minDrawDimen) * 0.5f;
            } else {
                gravityCompatApply(this.mGravity, this.mBitmapWidth, this.mBitmapHeight, getBounds(), this.mDstRect);
            }
            this.mDstRectF.set(this.mDstRect);
            if (this.mBitmapShader != null) {
                this.mShaderMatrix.setTranslate(this.mDstRectF.left, this.mDstRectF.top);
                this.mShaderMatrix.preScale(this.mDstRectF.width() / ((float) this.mBitmap.getWidth()), this.mDstRectF.height() / ((float) this.mBitmap.getHeight()));
                this.mBitmapShader.setLocalMatrix(this.mShaderMatrix);
                this.mPaint.setShader(this.mBitmapShader);
            }
            this.mApplyGravity = false;
        }
    }

    public void draw(@NonNull Canvas canvas) {
        Bitmap bitmap = this.mBitmap;
        if (bitmap != null) {
            updateDstRect();
            if (this.mPaint.getShader() == null) {
                canvas.drawBitmap(bitmap, (Rect) null, this.mDstRect, this.mPaint);
                return;
            }
            RectF rectF = this.mDstRectF;
            float f = this.mCornerRadius;
            canvas.drawRoundRect(rectF, f, f, this.mPaint);
        }
    }

    public int getAlpha() {
        return this.mPaint.getAlpha();
    }

    public void setAlpha(int alpha) {
        if (alpha != this.mPaint.getAlpha()) {
            this.mPaint.setAlpha(alpha);
            invalidateSelf();
        }
    }

    public ColorFilter getColorFilter() {
        return this.mPaint.getColorFilter();
    }

    public void setColorFilter(ColorFilter cf) {
        this.mPaint.setColorFilter(cf);
        invalidateSelf();
    }

    private void updateCircularCornerRadius() {
        this.mCornerRadius = (float) (Math.min(this.mBitmapHeight, this.mBitmapWidth) / 2);
    }

    public boolean isCircular() {
        return this.mIsCircular;
    }

    public void setCircular(boolean circular) {
        this.mIsCircular = circular;
        this.mApplyGravity = true;
        if (circular) {
            updateCircularCornerRadius();
            this.mPaint.setShader(this.mBitmapShader);
            invalidateSelf();
            return;
        }
        setCornerRadius(0.0f);
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        if (this.mIsCircular) {
            updateCircularCornerRadius();
        }
        this.mApplyGravity = true;
    }

    public float getCornerRadius() {
        return this.mCornerRadius;
    }

    public void setCornerRadius(float cornerRadius) {
        if (this.mCornerRadius != cornerRadius) {
            this.mIsCircular = false;
            if (isGreaterThanZero(cornerRadius)) {
                this.mPaint.setShader(this.mBitmapShader);
            } else {
                this.mPaint.setShader(null);
            }
            this.mCornerRadius = cornerRadius;
            invalidateSelf();
        }
    }

    public int getIntrinsicWidth() {
        return this.mBitmapWidth;
    }

    public int getIntrinsicHeight() {
        return this.mBitmapHeight;
    }

    public int getOpacity() {
        Bitmap bm;
        if (this.mGravity != 119 || this.mIsCircular || (bm = this.mBitmap) == null || bm.hasAlpha() || this.mPaint.getAlpha() < 255 || isGreaterThanZero(this.mCornerRadius)) {
            return -3;
        }
        return -1;
    }
}
