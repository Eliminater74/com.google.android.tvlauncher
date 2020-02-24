package com.google.android.tvlauncher.home.operatorbackground;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.google.android.tvlauncher.C1188R;
import com.google.android.tvlauncher.util.Util;
import java.security.MessageDigest;
import java.util.Arrays;

final class AddScrimTransformation extends BitmapTransformation {
    private static final int sHashCode = Arrays.hashCode(sKeyBytes);
    private static final byte[] sKeyBytes = AddScrimTransformation.class.getName().getBytes(CHARSET);
    private final int[] mColors;
    private final float[] mPositions;

    AddScrimTransformation(Context context) {
        Resources resources = context.getResources();
        float startAlpha = Util.getFloat(resources, C1188R.dimen.background_image_scrim_start_alpha);
        float verticalPercentageSlitPoint = resources.getFraction(C1188R.fraction.home_background_image_scrim_midpoint_coordinate, 1, 1);
        this.mColors = new int[]{Color.argb(startAlpha, 0.0f, 0.0f, 0.0f), Color.argb(startAlpha, 0.0f, 0.0f, 0.0f), -16777216};
        this.mPositions = new float[]{0.0f, verticalPercentageSlitPoint, 1.0f};
    }

    /* JADX DEBUG: Failed to find minimal casts for resolve overloaded methods, cast all args instead
     method: ClspMth{android.graphics.LinearGradient.<init>(float, float, float, float, int[], float[], android.graphics.Shader$TileMode):void}
     arg types: [int, int, int, float, int[], float[], android.graphics.Shader$TileMode]
     candidates:
      ClspMth{android.graphics.LinearGradient.<init>(float, float, float, float, long, long, android.graphics.Shader$TileMode):void}
      ClspMth{android.graphics.LinearGradient.<init>(float, float, float, float, long[], float[], android.graphics.Shader$TileMode):void}
      ClspMth{android.graphics.LinearGradient.<init>(float, float, float, float, int, int, android.graphics.Shader$TileMode):void}
      ClspMth{android.graphics.LinearGradient.<init>(float, float, float, float, int[], float[], android.graphics.Shader$TileMode):void} */
    /* access modifiers changed from: protected */
    public Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        int width = toTransform.getWidth();
        int height = toTransform.getHeight();
        Bitmap result = pool.getDirty(outWidth, outHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(toTransform, 0.0f, 0.0f, (Paint) null);
        Paint gradientPaint = new Paint();
        int[] iArr = this.mColors;
        gradientPaint.setShader(new LinearGradient(0.0f, 0.0f, 0.0f, (float) height, iArr, this.mPositions, Shader.TileMode.CLAMP));
        Rect gradientRect = new Rect();
        gradientRect.set(0, 0, width, height);
        canvas.drawRect(gradientRect, gradientPaint);
        return result;
    }

    public boolean equals(Object o) {
        return o instanceof AddScrimTransformation;
    }

    public int hashCode() {
        return sHashCode;
    }

    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(sKeyBytes);
    }
}
