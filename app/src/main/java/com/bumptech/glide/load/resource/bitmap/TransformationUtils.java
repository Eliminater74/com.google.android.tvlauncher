package com.bumptech.glide.load.resource.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.util.Preconditions;
import com.google.wireless.android.play.playlog.proto.ClientAnalytics;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class TransformationUtils {
    public static final int PAINT_FLAGS = 6;
    private static final Paint CIRCLE_CROP_BITMAP_PAINT = new Paint(7);
    private static final int CIRCLE_CROP_PAINT_FLAGS = 7;
    private static final Paint CIRCLE_CROP_SHAPE_PAINT = new Paint(7);
    private static final Paint DEFAULT_PAINT = new Paint(6);
    private static final Set<String> MODELS_REQUIRING_BITMAP_LOCK = new HashSet(Arrays.asList("XT1085", "XT1092", "XT1093", "XT1094", "XT1095", "XT1096", "XT1097", "XT1098", "XT1031", "XT1028", "XT937C", "XT1032", "XT1008", "XT1033", "XT1035", "XT1034", "XT939G", "XT1039", "XT1040", "XT1042", "XT1045", "XT1063", "XT1064", "XT1068", "XT1069", "XT1072", "XT1077", "XT1078", "XT1079"));
    private static final Lock BITMAP_DRAWABLE_LOCK = (MODELS_REQUIRING_BITMAP_LOCK.contains(Build.MODEL) ? new ReentrantLock() : new NoLock());
    private static final String TAG = "TransformationUtils";

    static {
        CIRCLE_CROP_BITMAP_PAINT.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    }

    private TransformationUtils() {
    }

    public static Lock getBitmapDrawableLock() {
        return BITMAP_DRAWABLE_LOCK;
    }

    public static Bitmap centerCrop(@NonNull BitmapPool pool, @NonNull Bitmap inBitmap, int width, int height) {
        float dy;
        float dx;
        float scale;
        if (inBitmap.getWidth() == width && inBitmap.getHeight() == height) {
            return inBitmap;
        }
        Matrix m = new Matrix();
        if (inBitmap.getWidth() * height > inBitmap.getHeight() * width) {
            scale = ((float) height) / ((float) inBitmap.getHeight());
            dx = (((float) width) - (((float) inBitmap.getWidth()) * scale)) * 0.5f;
            dy = 0.0f;
        } else {
            scale = ((float) width) / ((float) inBitmap.getWidth());
            dx = 0.0f;
            dy = (((float) height) - (((float) inBitmap.getHeight()) * scale)) * 0.5f;
        }
        m.setScale(scale, scale);
        m.postTranslate((float) ((int) (dx + 0.5f)), (float) ((int) (0.5f + dy)));
        Bitmap result = pool.get(width, height, getNonNullConfig(inBitmap));
        setAlpha(inBitmap, result);
        applyMatrix(inBitmap, result, m);
        return result;
    }

    public static Bitmap fitCenter(@NonNull BitmapPool pool, @NonNull Bitmap inBitmap, int width, int height) {
        Bitmap bitmap = inBitmap;
        int i = width;
        int i2 = height;
        if (inBitmap.getWidth() == i && inBitmap.getHeight() == i2) {
            if (Log.isLoggable(TAG, 2)) {
                Log.v(TAG, "requested target size matches input, returning input");
            }
            return bitmap;
        }
        float minPercentage = Math.min(((float) i) / ((float) inBitmap.getWidth()), ((float) i2) / ((float) inBitmap.getHeight()));
        int targetWidth = Math.round(((float) inBitmap.getWidth()) * minPercentage);
        int targetHeight = Math.round(((float) inBitmap.getHeight()) * minPercentage);
        if (inBitmap.getWidth() == targetWidth && inBitmap.getHeight() == targetHeight) {
            if (Log.isLoggable(TAG, 2)) {
                Log.v(TAG, "adjusted target size matches input, returning input");
            }
            return bitmap;
        }
        Bitmap toReuse = pool.get((int) (((float) inBitmap.getWidth()) * minPercentage), (int) (((float) inBitmap.getHeight()) * minPercentage), getNonNullConfig(inBitmap));
        setAlpha(bitmap, toReuse);
        if (Log.isLoggable(TAG, 2)) {
            StringBuilder sb = new StringBuilder(32);
            sb.append("request: ");
            sb.append(i);
            sb.append("x");
            sb.append(i2);
            Log.v(TAG, sb.toString());
            int width2 = inBitmap.getWidth();
            int height2 = inBitmap.getHeight();
            StringBuilder sb2 = new StringBuilder(32);
            sb2.append("toFit:   ");
            sb2.append(width2);
            sb2.append("x");
            sb2.append(height2);
            Log.v(TAG, sb2.toString());
            int width3 = toReuse.getWidth();
            int height3 = toReuse.getHeight();
            StringBuilder sb3 = new StringBuilder(32);
            sb3.append("toReuse: ");
            sb3.append(width3);
            sb3.append("x");
            sb3.append(height3);
            Log.v(TAG, sb3.toString());
            StringBuilder sb4 = new StringBuilder(25);
            sb4.append("minPct:   ");
            sb4.append(minPercentage);
            Log.v(TAG, sb4.toString());
        }
        Matrix matrix = new Matrix();
        matrix.setScale(minPercentage, minPercentage);
        applyMatrix(bitmap, toReuse, matrix);
        return toReuse;
    }

    public static Bitmap centerInside(@NonNull BitmapPool pool, @NonNull Bitmap inBitmap, int width, int height) {
        if (inBitmap.getWidth() > width || inBitmap.getHeight() > height) {
            if (Log.isLoggable(TAG, 2)) {
                Log.v(TAG, "requested target size too big for input, fit centering instead");
            }
            return fitCenter(pool, inBitmap, width, height);
        }
        if (Log.isLoggable(TAG, 2)) {
            Log.v(TAG, "requested target size larger or equal to input, returning input");
        }
        return inBitmap;
    }

    public static void setAlpha(Bitmap inBitmap, Bitmap outBitmap) {
        outBitmap.setHasAlpha(inBitmap.hasAlpha());
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.graphics.Bitmap.createBitmap(android.graphics.Bitmap, int, int, int, int, android.graphics.Matrix, boolean):android.graphics.Bitmap}
     arg types: [android.graphics.Bitmap, int, int, int, int, android.graphics.Matrix, int]
     candidates:
      ClspMth{android.graphics.Bitmap.createBitmap(android.util.DisplayMetrics, int[], int, int, int, int, android.graphics.Bitmap$Config):android.graphics.Bitmap}
      ClspMth{android.graphics.Bitmap.createBitmap(android.graphics.Bitmap, int, int, int, int, android.graphics.Matrix, boolean):android.graphics.Bitmap} */
    public static Bitmap rotateImage(@NonNull Bitmap imageToOrient, int degreesToRotate) {
        Bitmap result = imageToOrient;
        if (degreesToRotate == 0) {
            return result;
        }
        try {
            Matrix matrix = new Matrix();
            matrix.setRotate((float) degreesToRotate);
            return Bitmap.createBitmap(imageToOrient, 0, 0, imageToOrient.getWidth(), imageToOrient.getHeight(), matrix, true);
        } catch (Exception e) {
            if (!Log.isLoggable(TAG, 6)) {
                return result;
            }
            Log.e(TAG, "Exception when trying to orient image", e);
            return result;
        }
    }

    public static int getExifOrientationDegrees(int exifOrientation) {
        switch (exifOrientation) {
            case 3:
            case 4:
                return ClientAnalytics.LogRequest.LogSource.INBOX_ANDROID_PRIMES_VALUE;
            case 5:
            case 6:
                return 90;
            case 7:
            case 8:
                return ClientAnalytics.LogRequest.LogSource.CLINICAL_STUDIES_VALUE;
            default:
                return 0;
        }
    }

    public static Bitmap rotateImageExif(@NonNull BitmapPool pool, @NonNull Bitmap inBitmap, int exifOrientation) {
        if (!isExifOrientationRequired(exifOrientation)) {
            return inBitmap;
        }
        Matrix matrix = new Matrix();
        initializeMatrixForRotation(exifOrientation, matrix);
        RectF newRect = new RectF(0.0f, 0.0f, (float) inBitmap.getWidth(), (float) inBitmap.getHeight());
        matrix.mapRect(newRect);
        Bitmap result = pool.get(Math.round(newRect.width()), Math.round(newRect.height()), getNonNullConfig(inBitmap));
        matrix.postTranslate(-newRect.left, -newRect.top);
        result.setHasAlpha(inBitmap.hasAlpha());
        applyMatrix(inBitmap, result, matrix);
        return result;
    }

    public static boolean isExifOrientationRequired(int exifOrientation) {
        switch (exifOrientation) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                return true;
            default:
                return false;
        }
    }

    /* JADX INFO: Multiple debug info for r3v2 android.graphics.Bitmap: [D('top' float), D('result' android.graphics.Bitmap)] */
    public static Bitmap circleCrop(@NonNull BitmapPool pool, @NonNull Bitmap inBitmap, int destWidth, int destHeight) {
        BitmapPool bitmapPool = pool;
        int destMinEdge = Math.min(destWidth, destHeight);
        float radius = ((float) destMinEdge) / 2.0f;
        int srcWidth = inBitmap.getWidth();
        int srcHeight = inBitmap.getHeight();
        float maxScale = Math.max(((float) destMinEdge) / ((float) srcWidth), ((float) destMinEdge) / ((float) srcHeight));
        float scaledWidth = maxScale * ((float) srcWidth);
        float scaledHeight = maxScale * ((float) srcHeight);
        float left = (((float) destMinEdge) - scaledWidth) / 2.0f;
        float top = (((float) destMinEdge) - scaledHeight) / 2.0f;
        RectF destRect = new RectF(left, top, left + scaledWidth, top + scaledHeight);
        Bitmap toTransform = getAlphaSafeBitmap(pool, inBitmap);
        Bitmap result = bitmapPool.get(destMinEdge, destMinEdge, getAlphaSafeConfig(inBitmap));
        result.setHasAlpha(true);
        BITMAP_DRAWABLE_LOCK.lock();
        try {
            Canvas canvas = new Canvas(result);
            try {
                canvas.drawCircle(radius, radius, radius, CIRCLE_CROP_SHAPE_PAINT);
                try {
                    canvas.drawBitmap(toTransform, (Rect) null, destRect, CIRCLE_CROP_BITMAP_PAINT);
                    clear(canvas);
                    BITMAP_DRAWABLE_LOCK.unlock();
                    if (!toTransform.equals(inBitmap)) {
                        bitmapPool.put(toTransform);
                    }
                    return result;
                } catch (Throwable th) {
                    th = th;
                    BITMAP_DRAWABLE_LOCK.unlock();
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                BITMAP_DRAWABLE_LOCK.unlock();
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            BITMAP_DRAWABLE_LOCK.unlock();
            throw th;
        }
    }

    private static Bitmap getAlphaSafeBitmap(@NonNull BitmapPool pool, @NonNull Bitmap maybeAlphaSafe) {
        Bitmap.Config safeConfig = getAlphaSafeConfig(maybeAlphaSafe);
        if (safeConfig.equals(maybeAlphaSafe.getConfig())) {
            return maybeAlphaSafe;
        }
        Bitmap argbBitmap = pool.get(maybeAlphaSafe.getWidth(), maybeAlphaSafe.getHeight(), safeConfig);
        new Canvas(argbBitmap).drawBitmap(maybeAlphaSafe, 0.0f, 0.0f, (Paint) null);
        return argbBitmap;
    }

    @NonNull
    private static Bitmap.Config getAlphaSafeConfig(@NonNull Bitmap inBitmap) {
        if (Build.VERSION.SDK_INT < 26 || !Bitmap.Config.RGBA_F16.equals(inBitmap.getConfig())) {
            return Bitmap.Config.ARGB_8888;
        }
        return Bitmap.Config.RGBA_F16;
    }

    @Deprecated
    public static Bitmap roundedCorners(@NonNull BitmapPool pool, @NonNull Bitmap inBitmap, int width, int height, int roundingRadius) {
        return roundedCorners(pool, inBitmap, roundingRadius);
    }

    /* JADX INFO: finally extract failed */
    public static Bitmap roundedCorners(@NonNull BitmapPool pool, @NonNull Bitmap inBitmap, int roundingRadius) {
        Preconditions.checkArgument(roundingRadius > 0, "roundingRadius must be greater than 0.");
        Bitmap.Config safeConfig = getAlphaSafeConfig(inBitmap);
        Bitmap toTransform = getAlphaSafeBitmap(pool, inBitmap);
        Bitmap result = pool.get(toTransform.getWidth(), toTransform.getHeight(), safeConfig);
        result.setHasAlpha(true);
        BitmapShader shader = new BitmapShader(toTransform, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(shader);
        RectF rect = new RectF(0.0f, 0.0f, (float) result.getWidth(), (float) result.getHeight());
        BITMAP_DRAWABLE_LOCK.lock();
        try {
            Canvas canvas = new Canvas(result);
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
            canvas.drawRoundRect(rect, (float) roundingRadius, (float) roundingRadius, paint);
            clear(canvas);
            BITMAP_DRAWABLE_LOCK.unlock();
            if (!toTransform.equals(inBitmap)) {
                pool.put(toTransform);
            }
            return result;
        } catch (Throwable th) {
            BITMAP_DRAWABLE_LOCK.unlock();
            throw th;
        }
    }

    private static void clear(Canvas canvas) {
        canvas.setBitmap(null);
    }

    @NonNull
    private static Bitmap.Config getNonNullConfig(@NonNull Bitmap bitmap) {
        return bitmap.getConfig() != null ? bitmap.getConfig() : Bitmap.Config.ARGB_8888;
    }

    private static void applyMatrix(@NonNull Bitmap inBitmap, @NonNull Bitmap targetBitmap, Matrix matrix) {
        BITMAP_DRAWABLE_LOCK.lock();
        try {
            Canvas canvas = new Canvas(targetBitmap);
            canvas.drawBitmap(inBitmap, matrix, DEFAULT_PAINT);
            clear(canvas);
        } finally {
            BITMAP_DRAWABLE_LOCK.unlock();
        }
    }

    @VisibleForTesting
    static void initializeMatrixForRotation(int exifOrientation, Matrix matrix) {
        switch (exifOrientation) {
            case 2:
                matrix.setScale(-1.0f, 1.0f);
                return;
            case 3:
                matrix.setRotate(180.0f);
                return;
            case 4:
                matrix.setRotate(180.0f);
                matrix.postScale(-1.0f, 1.0f);
                return;
            case 5:
                matrix.setRotate(90.0f);
                matrix.postScale(-1.0f, 1.0f);
                return;
            case 6:
                matrix.setRotate(90.0f);
                return;
            case 7:
                matrix.setRotate(-90.0f);
                matrix.postScale(-1.0f, 1.0f);
                return;
            case 8:
                matrix.setRotate(-90.0f);
                return;
            default:
                return;
        }
    }

    private static final class NoLock implements Lock {
        NoLock() {
        }

        public void lock() {
        }

        public void lockInterruptibly() throws InterruptedException {
        }

        public boolean tryLock() {
            return true;
        }

        public boolean tryLock(long time, @NonNull TimeUnit unit) throws InterruptedException {
            return true;
        }

        public void unlock() {
        }

        @NonNull
        public Condition newCondition() {
            throw new UnsupportedOperationException("Should not be called");
        }
    }
}
