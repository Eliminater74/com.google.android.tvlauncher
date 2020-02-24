package com.google.android.libraries.stitch.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Looper;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public final class BitmapUtils {
    public static final int NO_COLOR = 0;
    private static final String TAG = "BitmapUtils";
    private static volatile Thread sMainThread;
    private static final Paint sResizePaintBg = new Paint(2);
    private static final Paint sResizePaintUi = new Paint(2);

    public static Bitmap resizeAndCropBitmap(Bitmap inputBitmap, int width, int height, Bitmap toReuse) {
        Bitmap bitmap;
        if (inputBitmap == null || width == 0 || height == 0) {
            return null;
        }
        if (Log.isLoggable(TAG, 3)) {
            int width2 = inputBitmap.getWidth();
            int height2 = inputBitmap.getHeight();
            StringBuilder sb = new StringBuilder(83);
            sb.append("resizeAndCropBitmap: Input: ");
            sb.append(width2);
            sb.append("x");
            sb.append(height2);
            sb.append(", output:");
            sb.append(width);
            sb.append("x");
            sb.append(height);
            Log.d(TAG, sb.toString());
        }
        if (inputBitmap.getWidth() == width && inputBitmap.getHeight() == height) {
            return inputBitmap;
        }
        if (toReuse == null) {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        } else {
            bitmap = toReuse;
        }
        if (bitmap == null) {
            return null;
        }
        Canvas canvas = new Canvas(bitmap);
        int croppedWidth = inputBitmap.getWidth();
        int croppedHeight = inputBitmap.getHeight();
        if (inputBitmap.getWidth() * height > inputBitmap.getHeight() * width) {
            croppedWidth = (inputBitmap.getHeight() * width) / height;
        } else {
            croppedHeight = (inputBitmap.getWidth() * height) / width;
        }
        int left = (inputBitmap.getWidth() - croppedWidth) / 2;
        int top = (inputBitmap.getHeight() - croppedHeight) / 2;
        drawResizedBitmap(canvas, inputBitmap, new Rect(left, top, left + croppedWidth, top + croppedHeight), new Rect(0, 0, width, height));
        return bitmap;
    }

    public static void drawResizedBitmap(Canvas canvas, Bitmap bitmap, Rect src, Rect dest) {
        if (sMainThread == null) {
            sMainThread = Looper.getMainLooper().getThread();
        }
        if (Thread.currentThread() == sMainThread) {
            canvas.drawBitmap(bitmap, src, dest, sResizePaintUi);
            return;
        }
        synchronized (sResizePaintBg) {
            canvas.drawBitmap(bitmap, src, dest, sResizePaintBg);
        }
    }

    public static Bitmap resizeToSquareBitmap(Bitmap inputBitmap, int size, int backgroundColor) {
        if (inputBitmap == null) {
            return null;
        }
        if (Log.isLoggable(TAG, 3)) {
            int width = inputBitmap.getWidth();
            int height = inputBitmap.getHeight();
            StringBuilder sb = new StringBuilder(84);
            sb.append("resizeToSquareBitmap: Input: ");
            sb.append(width);
            sb.append("x");
            sb.append(height);
            sb.append(", output:");
            sb.append(size);
            sb.append("x");
            sb.append(size);
            Log.d(TAG, sb.toString());
        }
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        if (bitmap == null) {
            return null;
        }
        Canvas canvas = new Canvas(bitmap);
        if (backgroundColor != 0) {
            canvas.drawColor(backgroundColor);
        }
        if (inputBitmap.getWidth() == size && inputBitmap.getHeight() == size) {
            canvas.drawBitmap(inputBitmap, 0.0f, 0.0f, (Paint) null);
        } else {
            drawResizedBitmap(canvas, inputBitmap, new Rect(0, 0, inputBitmap.getWidth(), inputBitmap.getHeight()), new Rect(0, 0, size, size));
        }
        return bitmap;
    }

    public static Bitmap resizeToSquareBitmap(Bitmap inputBitmap, int size) {
        return resizeToSquareBitmap(inputBitmap, size, 0);
    }

    private BitmapUtils() {
    }

    public static byte[] compressBitmap(Bitmap bitmap, int quality, boolean recycle) {
        return compressBitmap(bitmap, Bitmap.CompressFormat.JPEG, quality, recycle);
    }

    public static byte[] compressBitmap(Bitmap bitmap, Bitmap.CompressFormat format, int quality, boolean recycle) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            bitmap.compress(format, quality, stream);
            stream.flush();
            try {
                stream.close();
            } catch (IOException e) {
            }
            if (recycle) {
                bitmap.recycle();
            }
            byte[] imageBytes = stream.toByteArray();
            if (Log.isLoggable(TAG, 3)) {
                int length = imageBytes.length;
                StringBuilder sb = new StringBuilder(39);
                sb.append("compressBitmap: Image size: ");
                sb.append(length);
                Log.d(TAG, sb.toString());
            }
            return imageBytes;
        } catch (IOException e2) {
            throw new AssertionError("IOException in compressBitmap");
        } catch (Throwable th) {
            try {
                stream.close();
            } catch (IOException e3) {
            }
            throw th;
        }
    }
}
